package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.util.AxisAlignedBB;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.HeatIndex;
import TFC.API.HeatRegistry;
import TFC.API.Metal;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.Metal.MetalRegistry;
import TFC.Handlers.PacketHandler;

public class TEBlastFurnace extends TileEntityFireEntity implements IInventory
{
	public boolean isValid;

	public ItemStack input[];
	public ItemStack fireItemStacks[];
	public ItemStack outputItemStacks[];

	public float inputItemTemps[];

	private int prevStackSize;
	private int numAirBlocks;

	public String OreType;

	//We dont save this since its purpose is to just mkae certain parts of the code not run every single tick
	private int slowCounter = 0;


	//Bloomery
	public int charcoalCount;
	public int oreCount;

	ItemStack outMetal1;
	int outMetal1Count;

	public TEBlastFurnace()
	{
		fuelTimeLeft = 0;
		MaxFireTemp = 2500;
		fuelBurnTemp =  0;

		fireTemperature = 0;
		AddedAir = 0F;
		isValid = false;
		fireItemStacks = new ItemStack[20];
		outputItemStacks = new ItemStack[20];
		input = new ItemStack[2];
		inputItemTemps = new float[80];
		ambientTemp = -1000;
		numAirBlocks = 0;
		airFromBellows = 0F;
		airFromBellowsTime = 0;
		charcoalCount = 0;
		oreCount = 0;
		shouldSendInitData = false;
	}

	public void careForInventorySlot(int i, float startTemp)
	{
		NBTTagCompound inputCompound;
		float mod = 1;
		if(oreCount > charcoalCount) 
		{
			float c = (float)charcoalCount/(float)oreCount;
			mod = (mod * c)*3;
			if(mod > 1) {
				mod = 1;
			}
		}

		if(fireItemStacks[i]!= null && fireItemStacks[i].hasTagCompound())
		{
			inputCompound = fireItemStacks[i].getTagCompound();
			inputItemTemps[i] = inputCompound.getFloat("temperature");


			if(fireTemperature*mod > inputItemTemps[i])
			{
				float increase = TFC_ItemHeat.getTempIncrease(fireItemStacks[i], fireTemperature*mod, MaxFireTemp);
				inputItemTemps[i] += increase;
			}
			else if(fireTemperature*mod < inputItemTemps[i])
			{
				float increase = TFC_ItemHeat.getTempDecrease(fireItemStacks[i]);
				inputItemTemps[i] -= increase;
			}
			inputCompound.setFloat("temperature", inputItemTemps[i]);
			fireItemStacks[i].setTagCompound(inputCompound);

			if(inputItemTemps[i] <= ambientTemp)
			{
				fireItemStacks[i].stackTagCompound = null;
				inputItemTemps[i] = 0;
			}
		}
		else if(fireItemStacks[i] != null && !fireItemStacks[i].hasTagCompound())
		{
			if(TFC_ItemHeat.getMeltingPoint(fireItemStacks[i]) != -1)
			{
				inputCompound = new NBTTagCompound();
				inputCompound.setFloat("temperature", startTemp);
				fireItemStacks[i].setTagCompound(inputCompound);
			}
		}
		else if(fireItemStacks[i] == null)
		{
			inputItemTemps[i] = 0;
		}
	}

	private Boolean CheckValidity() 
	{
		int y = yCoord+1;

		if(worldObj.getBlockMaterial(xCoord+1, y, zCoord) == Material.rock || worldObj.getBlockMaterial(xCoord+1, y, zCoord) == Material.iron)
		{
			if(worldObj.getBlockMaterial(xCoord-1, y, zCoord) == Material.rock || worldObj.getBlockMaterial(xCoord-1, y, zCoord) == Material.iron)
			{
				if(worldObj.getBlockMaterial(xCoord, y, zCoord+1) == Material.rock || worldObj.getBlockMaterial(xCoord, y, zCoord+1) == Material.iron)
				{
					if(worldObj.getBlockMaterial(xCoord, y, zCoord-1) == Material.rock || worldObj.getBlockMaterial(xCoord, y, zCoord-1) == Material.iron)
					{
						return true;
					}
				}
			}
		}
		return true;
	}

	@Override
	public void closeChest() {
		// TODO Auto-generated method stub

	}

	public int combineMetals(ItemStack InputItem, ItemStack DestItem)
	{
		int D1 = 100-InputItem.getItemDamage();
		int D2 = 100-DestItem.getItemDamage();

		int damage = 100-(D1 + D2);

		DestItem.setItemDamage(damage);
		if(damage < 0) {
			return 100-damage;
		}

		return 0;//returns false if there is no metal left over to combine
	}

	public void CookItemsNew(int i)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		Random R = new Random();
		TECrucible te = (TECrucible) worldObj.getBlockTileEntity(xCoord, yCoord-1, zCoord);
		//Only allow the ore to smelt if there is a valid Tuyere associated with the furnace
		if(fireItemStacks[i] != null && te != null && input[1] != null)
		{
			HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);
			if(index != null && inputItemTemps[i] >= index.meltTemp)
			{
				fireItemStacks[i] = index.getMorph();
				if(fireItemStacks[i] != null)
				{
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setFloat("temperature", inputItemTemps[i]);
					fireItemStacks[i].stackTagCompound = nbt;
				}
				else
				{
					oreCount--;
					charcoalCount--;
				}

				ItemStack output = index.getOutput(fireItemStacks[i], R);
				Metal m = MetalRegistry.instance.getMetalFromItem(output.getItem());
				if(m != null) {
					te.addMetal(m, (short)(100-output.getItemDamage()));
				}
				input[1].setItemDamage(input[1].getItemDamage()+1);
				if( input[1] != null && input[1].getItemDamage() == input[1].getMaxDamage())
				{
					setInventorySlotContents(1,null);
				}
				te.temperature = (int)this.fireTemperature;
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(input[i] != null)
		{
			if(input[i].stackSize <= j)
			{
				ItemStack itemstack = input[i];
				input[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = input[i].splitStack(j);
			if(input[i].stackSize == 0)
			{
				input[i] = null;
			}
			return itemstack1;
		} else
		{
			return null;
		}

	}

	public void ejectContents()
	{
		float f3 = 0.05F;
		EntityItem entityitem;
		Random rand = new Random();
		float f = rand.nextFloat() * 0.8F + 0.1F;
		float f1 = rand.nextFloat() * 2.0F + 0.4F;
		float f2 = rand.nextFloat() * 0.8F + 0.1F;

		for (int i = 0; i < getSizeInventory(); i++)
		{
			if(fireItemStacks[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, 
						fireItemStacks[i]);
				entityitem.motionX = (float)rand.nextGaussian() * f3;
				entityitem.motionY = (float)rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float)rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public String getInvName()
	{
		return "Bloomery";
	}

	@Override
	public int getSizeInventory()
	{
		return input.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO Auto-generated method stub
		return input[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getTemperatureScaled(int s)
	{
		return (int)((fireTemperature * s) / MaxFireTemp);
	}

	public void HandleTemperature()
	{
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

		if(ambientTemp == -1000)	
		{
			ambientTemp = TFC_Climate.getHeightAdjustedTemp(xCoord, yCoord, zCoord);
		}

		//Now we increase the temperature
		//If the fire is still burning and has fuel
		if(fuelTimeLeft > 0)
		{
			float desiredTemp = 0;

			fuelTimeLeft--;
			if(airFromBellowsTime > 0)
			{
				fuelTimeLeft--;
			}

			if(yCoord < 128)
			{
				numAirBlocks = -1 * (256-(yCoord*2));
			}
			float t = 1;
			if(oreCount > charcoalCount) 
			{
				t = oreCount - charcoalCount;
				t*= 0.05F;
				t = 1 - t;
			}
			if(this.input[1] == null && airFromBellows > 0) {
				airFromBellows = 0;
			}

			float bAir = airFromBellows*(1+airFromBellowsTime/120);

			AddedAir = (numAirBlocks+bAir)/25/16;

			desiredTemp = (fuelBurnTemp + fuelBurnTemp * AddedAir)*t;

			if(fireTemperature < desiredTemp)
			{
				float tm = 1.35F;

				fireTemperature+=tm;
			}
			else if(fireTemperature > desiredTemp)
			{
				if(desiredTemp > ambientTemp)
				{
					if(airFromBellows == 0) {
						fireTemperature-=0.225F;
					} else {
						fireTemperature-=0.18F;
					}
				}
			}
		}
		else if(fuelTimeLeft <= 0 && charcoalCount > 0)
		{
			charcoalCount--;

			fuelTimeLeft = 1875;
			fuelBurnTemp = 1350;	
			if(fireTemperature < 210)
			{
				fireTemperature = 220;
			}

			if((meta & 4) == 0) {
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta+4, 3);
			}
			//updateGui();
		}
		else
		{
			if((meta & 4) == 4) {
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta & 3, 3);
			}

			fuelBurnTemp = 0;

			if(fireTemperature > ambientTemp)
			{
				fireTemperature-=0.425F;
			}
			//updateGui();
		}



		//here we set the various temperatures to range
		if(fireTemperature > MaxFireTemp) {
			fireTemperature = MaxFireTemp;
		} else if(fireTemperature < ambientTemp) {
			fireTemperature = ambientTemp;
		}

		//Here we handle the bellows
		if(airFromBellowsTime > 0)
		{
			airFromBellowsTime--;
			airFromBellows = airFromBellowsTime/120*10;
		}

	}

	public boolean isStackValid(int i, int j, int k)
	{
		if((worldObj.getBlockId(i, j-1, k) != TFCBlocks.Molten.blockID && 
				worldObj.getBlockMaterial(i, j-1, k) != Material.rock && worldObj.getBlockId(i, j-1, k) != TFCBlocks.BlastFurnace.blockID))
		{
			return false;
		}
		if(worldObj.getBlockMaterial(i+1, j, k) != Material.rock || !worldObj.isBlockNormalCube(i+1, j, k))
		{
			return false;
		}
		if(worldObj.getBlockMaterial(i-1, j, k) != Material.rock || !worldObj.isBlockNormalCube(i-1, j, k))
		{
			return false;
		}
		if(worldObj.getBlockMaterial(i, j, k+1) != Material.rock || !worldObj.isBlockNormalCube(i, j, k+1))
		{
			return false;
		}
		if(worldObj.getBlockMaterial(i, j, k-1) != Material.rock || !worldObj.isBlockNormalCube(i, j, k-1))
		{
			return false;
		}
		if(input[1] == null) {
			return false;
		}

		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	public boolean AddOreToFire(ItemStack is)
	{
		for (int i = 0; i < fireItemStacks.length; i++)
		{
			if(fireItemStacks[i] == null)
			{
				fireItemStacks[i] = is;
				return true;
			}
		}
		return false;
	}

	public boolean AddOreToOutput(ItemStack is, ItemStack is2)
	{
		ItemStack work = new ItemStack(is.itemID,is.stackSize,is.getItemDamage());
		/* First we check to see if there are any mergeable items*/
		for (int i = 0; i < outputItemStacks.length; i++)
		{
			if(outputItemStacks[i] != null && outputItemStacks[i].getItem().getUnlocalizedName(outputItemStacks[i]) == work.getItem().getUnlocalizedName(work))
			{
				if(outputItemStacks[i].getItemDamage() > 0 && work.getItemDamage() > 0)
				{
					int amt = combineMetals(work,outputItemStacks[i]);
					if(amt != 0)
					{
						work.setItemDamage(amt);
					} else {
						oreCount--;

						return true;
					}
				}
			}
		}
		/*If not then the item takes a new slot*/
		for (int i = 0; i < outputItemStacks.length; i++)
		{
			if(outputItemStacks[i] == null)
			{
				outputItemStacks[i] = work;
				oreCount--;
				TFC_ItemHeat.SetTemperature(outputItemStacks[i], TFC_ItemHeat.GetTemperature(is2));
				return true;
			}
		}
		return false;
	}

	public boolean RemoveOre()
	{
		if(outMetal1 == null && outMetal1Count > 0)
		{
			HeatRegistry manager = HeatRegistry.getInstance();
			HeatIndex index = manager.findMatchingIndex(new ItemStack(TFCItems.OreChunk, 1, 3));
			if(index != null)
			{
				ItemStack output = index.getOutput(worldObj.rand);

				if(outMetal1 == null) {
					outMetal1 = output;
				}
			}
		}

		//Make sure that we have an item in the input slot
		if(input[0] != null)
		{
			/**
			 * First we check if there is an empty mold
			 * */
			if(input[0].itemID == TFCItems.CeramicMold.itemID)
			{
				int dam = 0;
				if(outMetal1Count > 0)
				{
					if(outMetal1Count > 100)
					{
						dam = 100;
						outMetal1Count -= 100;
					}
					else
					{
						dam = outMetal1Count;
						outMetal1Count = 0;
					}  

					if(outMetal1 != null) {
						input[0] = outMetal1.copy();
					}
				}

				if(input[0] != null && input[0].itemID != TFCItems.CeramicMold.itemID) {
					input[0].setItemDamage(100-dam);
				}

				TFC_ItemHeat.SetTemperature(input[0], fireTemperature);

				return true;
			}
			/**
			 * If the input is not an empty mold but instead contains a partial mold matching the first output metal, 
			 * then we handle the process
			 * */
			else if(outMetal1 != null && input[0].itemID == outMetal1.getItem().itemID && input[0].getItemDamage() > 0)
			{
				int i = 100-input[0].getItemDamage();
				if(i + outMetal1Count < 100)
				{
					input[0].setItemDamage(100-(i + outMetal1Count));
					outMetal1Count = 0;
				}
				else
				{
					int j = 100 - i;
					input[0].setItemDamage(0);
					outMetal1Count -= j;
				}

				if(outMetal1Count == 0) {
					outMetal1 = null;
				} 

				TFC_ItemHeat.SetTemperature(input[0], fireTemperature);

				return true;
			}
		}
		return false;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		input[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}


	}

	public void setNumAirBlocks(int n)
	{
		numAirBlocks = n;
	}

	public void CreateTuyereBlock()
	{
		/**
		 * Create a tuyere block if the tuyere slot is not empty.
		 * REMOVED: Code remains for a potential revisit later. For now the tuyere will not be a rendered block.
		 */
		/*if(input[1] != null)
		{
			//get the direction that the bloomery is facing
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;

			if((meta == 0 || meta == 2) && worldObj.getBlockId(xCoord+1, yCoord, zCoord) != TFCBlocks.Tuyere.blockID && 
					worldObj.getBlockId(xCoord-1, yCoord, zCoord) != TFCBlocks.Tuyere.blockID)
			{
				if(worldObj.getBlockId(xCoord+1, yCoord, zCoord) != TFCBlocks.Tuyere.blockID && worldObj.isAirBlock(xCoord+1, yCoord, zCoord))
				{
					worldObj.setBlock(xCoord+1, yCoord, zCoord, TFCBlocks.Tuyere.blockID, ((ItemTuyere)input[1].getItem()).BlockMeta+8, 2);
				}
				else if(worldObj.getBlockId(xCoord-1, yCoord, zCoord) != TFCBlocks.Tuyere.blockID && worldObj.isAirBlock(xCoord-1, yCoord, zCoord))
				{
					worldObj.setBlock(xCoord-1, yCoord, zCoord, TFCBlocks.Tuyere.blockID, ((ItemTuyere)input[1].getItem()).BlockMeta+8, 2);
				}

			}
			else if((meta == 1 || meta == 3) && worldObj.getBlockId(xCoord, yCoord, zCoord+1) != TFCBlocks.Tuyere.blockID && 
					worldObj.getBlockId(xCoord, yCoord, zCoord-1) != TFCBlocks.Tuyere.blockID)
			{
				if(worldObj.getBlockId(xCoord, yCoord, zCoord+1) != TFCBlocks.Tuyere.blockID && worldObj.isAirBlock(xCoord, yCoord, zCoord+1))
				{
					worldObj.setBlock(xCoord, yCoord, zCoord+1, TFCBlocks.Tuyere.blockID, ((ItemTuyere)input[1].getItem()).BlockMeta, 2);
				}
				else if(worldObj.getBlockId(xCoord, yCoord, zCoord-1) != TFCBlocks.Tuyere.blockID && worldObj.isAirBlock(xCoord, yCoord, zCoord-1))
				{
					worldObj.setBlock(xCoord, yCoord, zCoord-1, TFCBlocks.Tuyere.blockID, ((ItemTuyere)input[1].getItem()).BlockMeta, 2);
				}

			}
		}
		else
		{
			if(worldObj.getBlockId(xCoord+1, yCoord, zCoord) == TFCBlocks.Tuyere.blockID)
			{
				worldObj.setBlockToAir(xCoord+1, yCoord, zCoord);
			}
			else if(worldObj.getBlockId(xCoord-1, yCoord, zCoord) == TFCBlocks.Tuyere.blockID )
			{
				worldObj.setBlockToAir(xCoord-1, yCoord, zCoord);
			}
			else if(worldObj.getBlockId(xCoord, yCoord, zCoord+1) == TFCBlocks.Tuyere.blockID)
			{
				worldObj.setBlockToAir(xCoord, yCoord, zCoord+1);
			}
			else if(worldObj.getBlockId(xCoord, yCoord, zCoord-1) == TFCBlocks.Tuyere.blockID)
			{
				worldObj.setBlockToAir(xCoord, yCoord, zCoord-1);
			}
		}*/
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			CreateTuyereBlock();

			if(oreCount < 0) {
				oreCount = 0;
			}
			if(charcoalCount < 0) {
				charcoalCount = 0;
			}

			//Do the funky math to find how many molten blocks should be placed
			float count = charcoalCount+oreCount;

			int moltenCount = 0;
			if(count > 0 && count <= 8) {moltenCount = 1;} 
			else if(count > 8 && count <= 16) {moltenCount = 2;} 
			else if(count > 16 && count <= 24) {moltenCount = 3;} 
			else if(count > 24 && count <= 32) {moltenCount = 4;} 
			else if(count > 32 && count <= 40) {moltenCount = 5;} 

			/*Fill the bloomery stack with molten ore. */
			for (int i = 1; i < 5; i++)
			{
				/*The stack must be air or already be molten rock*/
				if((worldObj.getBlockId(xCoord, yCoord+i, zCoord) == 0 ||
						worldObj.getBlockId(xCoord, yCoord+i, zCoord) == TFCBlocks.Molten.blockID))
				{
					//Make sure that the Stack is surrounded by rock
					if(i < moltenCount && isStackValid(xCoord, yCoord+i, zCoord)) {
						worldObj.setBlock(xCoord, yCoord+i, zCoord, TFCBlocks.Molten.blockID, 0, 0x2);
					} else {
						worldObj.setBlockToAir(xCoord, yCoord+i, zCoord);
					}
				}
			}
			/*Create a list of all the items that are falling into the stack */
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
					xCoord, yCoord+moltenCount, zCoord, 
					xCoord+1, yCoord+moltenCount+1.1, zCoord+1));

			if(moltenCount == 0) {
				moltenCount = 1;
			}
			/*Make sure the list isn't null or empty and that the stack is valid 1 layer above the Molten Ore*/
			if (list != null && !list.isEmpty() && isStackValid(xCoord, yCoord+moltenCount, zCoord))
			{
				/*Iterate through the list and check for charcoal, coke, and ore*/
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityItem entity = (EntityItem)iterator.next();
					if(entity.getEntityItem().itemID == Item.coal.itemID && entity.getEntityItem().getItemDamage() == 1 || entity.getEntityItem().itemID == TFCItems.Coke.itemID)
					{
						for(int c = 0; c < entity.getEntityItem().stackSize; c++)
						{
							if(charcoalCount+oreCount < 40 && charcoalCount < 20)
							{
								charcoalCount++;
								entity.getEntityItem().stackSize--;
							}
						}
						if(entity.getEntityItem().stackSize == 0) {
							entity.setDead();
						}
					}
					/*If the item that's been tossed in is a type of Ore and it can melt down into something then add the ore to the list of items in the fire.*/
					else if(TFC_ItemHeat.getMeltingPoint(entity.getEntityItem()) != -1 && TFC_Core.isOreIron(entity.getEntityItem()))
					{
						int c = entity.getEntityItem().stackSize;
						int nonConsumedOre = 0;
						for(; c > 0; c--)
						{
							if(charcoalCount+oreCount < 40 && oreCount < 20)
							{
								if(foundFlux(moltenCount) && AddOreToFire(new ItemStack(entity.getEntityItem().getItem(),1,entity.getEntityItem().getItemDamage()))) 
								{
									oreCount+=1;
								}
								else{
									nonConsumedOre++;
								}
							}
						}
						if(c+nonConsumedOre == 0) {
							entity.setDead();
						} else {
							ItemStack is = entity.getEntityItem();
							is.stackSize = c+nonConsumedOre;
							entity.setEntityItemStack(is);
						} 
					}
				}
			}

			/*Handle the temperature of the Bloomery*/
			HandleTemperature();

			for(int i = 0; i < fireItemStacks.length; i++)
			{
				/*Handle temperature for each item in the stack*/
				careForInventorySlot(i,100);
				/*Cook each input item */
				if(worldObj.getBlockId(xCoord, yCoord-1, zCoord) == TFCBlocks.Crucible.blockID)
				{
					CookItemsNew(i);
				}
			}

			/*if(input[0]!= null)
			{
				RemoveOre();
				if(input[0].stackSize < 1)
					input[0].stackSize = 1;
			}*/

			if(slowCounter > 200)
			{
				//Here we make sure that the forge is valid
				isValid = CheckValidity();
			}
			slowCounter++;

		}
	}

	private boolean foundFlux(int moltenCount){
		List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
				xCoord, yCoord+moltenCount, zCoord, 
				xCoord+1, yCoord+moltenCount+1.1, zCoord+1));
		boolean found = false;
		for (Iterator iterator = list.iterator(); iterator.hasNext() && !found;)
		{
			EntityItem entity = (EntityItem)iterator.next();
			ItemStack is = entity.getEntityItem();
			if(!entity.isDead && (is.getItemDamage() == 0) && is.itemID == TFCItems.Powder.itemID)
			{				
				is.stackSize--;
				if(is.stackSize == 0) 
				{
					entity.setDead();
				}
				else
				{
					entity.setEntityItemStack(is);
				}
				found = true;
			}
		}
		return found;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setFloat("temperature", fireTemperature);
		nbttagcompound.setFloat("fuelTimeLeft", fuelTimeLeft);
		nbttagcompound.setFloat("fuelBurnTemp", fuelBurnTemp);
		nbttagcompound.setFloat("airFromBellowsTime", airFromBellowsTime);
		nbttagcompound.setFloat("airFromBellows", airFromBellows);
		nbttagcompound.setInteger("charcoalCount", charcoalCount);
		nbttagcompound.setInteger("outMetal1Count", outMetal1Count);
		nbttagcompound.setByte("oreCount", (byte)oreCount);

		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < fireItemStacks.length; i++)
		{
			if(fireItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				fireItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);

		NBTTagList nbttaglist2 = new NBTTagList();
		for(int i = 0; i < input.length; i++)
		{
			if(input[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				input[i].writeToNBT(nbttagcompound1);
				nbttaglist2.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Input", nbttaglist2);

		NBTTagList nbttaglist3 = new NBTTagList();
		for(int i = 0; i < outputItemStacks.length; i++)
		{
			if(outputItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				outputItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist3.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Output", nbttaglist3);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		fireTemperature = nbttagcompound.getFloat("temperature");
		fuelTimeLeft = nbttagcompound.getFloat("fuelTimeLeft");
		fuelBurnTemp = nbttagcompound.getFloat("fuelBurnTemp");
		airFromBellowsTime = nbttagcompound.getFloat("airFromBellowsTime");
		airFromBellows = nbttagcompound.getFloat("airFromBellows");
		charcoalCount = nbttagcompound.getInteger("charcoalCount");
		outMetal1Count = nbttagcompound.getInteger("outMetal1Count");
		oreCount = nbttagcompound.getByte("oreCount");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		fireItemStacks = new ItemStack[20];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < fireItemStacks.length)
			{
				fireItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		NBTTagList nbttaglist2 = nbttagcompound.getTagList("Input");
		input = new ItemStack[2];
		for(int i = 0; i < nbttaglist2.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist2.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < input.length)
			{
				input[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		NBTTagList nbttaglist3 = nbttagcompound.getTagList("Output");
		outputItemStacks = new ItemStack[20];
		for(int i = 0; i < nbttaglist3.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist3.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < outputItemStacks.length)
			{
				outputItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	public void updateGui()
	{
		if(!worldObj.isRemote) {
			TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createUpdatePacket(), 5);
		}
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {

		oreCount = inStream.readInt();
		charcoalCount = inStream.readInt();

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public Packet createUpdatePacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);	
		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(oreCount);
			dos.writeInt(charcoalCount);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {

	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException {

	}

	public int getOreCountScaled(int l)
	{
		return (this.oreCount * l)/20;
	}

	public int getCharcoalCountScaled(int l)
	{
		return (this.charcoalCount * l)/20;
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}
}
