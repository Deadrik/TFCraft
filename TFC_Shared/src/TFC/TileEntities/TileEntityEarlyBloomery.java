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
import TFC.API.Constant.Global;
import TFC.Blocks.Devices.BlockEarlyBloomery;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_ItemHeat;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemOre;

public class TileEntityEarlyBloomery extends TileEntityFireEntity implements IInventory
{
	public boolean isValid;
	public boolean bloomeryLit;

	public ItemStack fireItemStacks[];
	public ItemStack outputItemStacks[];

	Boolean Item1Melted = false;
	public float[] inputItemTemps;

	private int prevStackSize;
	private int numAirBlocks;

	public String OreType;

	//Bloomery
	public int charcoalCount;
	public int fuelCount;
	public int oreCount;
	public int outCount;
	public int oreDamage = -1;

	ItemStack outMetal1;
	int outMetal1Count;

	public TileEntityEarlyBloomery()
	{
		fuelTimeLeft = 0;
		fuelBurnTemp =  0;

		fireTemperature = 0;
		AddedAir = 0F;
		isValid = false;
		bloomeryLit = false;
		OreType = "";
		fireItemStacks = new ItemStack[20];
		outputItemStacks = new ItemStack[20];
		inputItemTemps = new float[1];
		ambientTemp = -1000;
		numAirBlocks = 0;
		airFromBellows = 0F;
		airFromBellowsTime = 0;
		charcoalCount = 0;
		oreCount = 0;
		outCount = 0;
		shouldSendInitData = false;
		MaxFireTemp = 2500;
	}

	public void careForInventorySlot(int i, float startTemp)
	{
		NBTTagCompound inputCompound;
		float mod = 1;
		if(oreCount > charcoalCount) 
		{
			float c = (float)charcoalCount/(float)oreCount;
			mod = (mod * c)*3;
			if(mod > 1)
				mod = 1;
		}

		if(fireItemStacks[i]!= null && fireItemStacks[i].hasTagCompound() && bloomeryLit)
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
		if(!worldObj.isBlockNormalCube(xCoord, yCoord+1, zCoord))
		{
			return false;
		}
		if(!worldObj.isBlockNormalCube(xCoord, yCoord-1, zCoord))
		{
			return false;
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

	public void subtractFuel(){
		if(!worldObj.isRemote)
		{
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;
			int[] direction = BlockEarlyBloomery.headBlockToFootBlockMap[meta];
			if((worldObj.getBlockId(xCoord+direction[0], yCoord, zCoord+direction[1])==TFCBlocks.Charcoal.blockID)){
				int m1 = worldObj.getBlockMetadata(xCoord+direction[0], yCoord, zCoord+direction[1]);
				if(m1-1 >= 0)
				{
					worldObj.setBlockMetadataWithNotify(xCoord+direction[0], yCoord, zCoord+direction[1], m1-1, 2);
				}
				else if(m1==0){
					worldObj.setBlockToAir(xCoord+direction[0], yCoord, zCoord+direction[1]);
				}
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		return null;
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
		return "NewBloomery";
	}

	@Override
	public int getSizeInventory()
	{
		return fireItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO Auto-generated method stub
		return fireItemStacks[i];
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
		int[] direction = BlockEarlyBloomery.headBlockToFootBlockMap[meta & 3];

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
		else if(fuelTimeLeft <= 0 && fuelCount > 0)
		{
			subtractFuel();

			fuelTimeLeft = 1875;
			fuelBurnTemp = 1450;	
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
		if(((worldObj.getBlockId(i, j-1, k) != TFCBlocks.Molten.blockID && worldObj.getBlockMaterial(i, j-1, k) != Material.rock) || (!worldObj.isBlockNormalCube(i, j-1, k)))&& worldObj.getBlockId(i, j-1, k) != TFCBlocks.Charcoal.blockID)
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
				OreType = is.getDisplayName();
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

	public boolean canLight(){
		if(!worldObj.isRemote)
		{
			//get the direction that the bloomery is facing so that we know where the stack should be
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;
			int[] direction = BlockEarlyBloomery.headBlockToFootBlockMap[meta];

			if(worldObj.getBlockId(xCoord+direction[0], yCoord, zCoord+direction[1])==TFCBlocks.Charcoal.blockID && !bloomeryLit){
				bloomeryLit = true;
				return true;
			}
		}
		return false;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		fireItemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}


	}

	public void setNumAirBlocks(int n)
	{
		numAirBlocks = n;
	}

	public int getOutputCount()
	{
		return outMetal1Count;
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			//to create the bloom at the end
			boolean flag = false;
			//get the direction that the bloomery is facing so that we know where the stack should be
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;
			int[] direction = BlockEarlyBloomery.headBlockToFootBlockMap[meta];
			if(bloomeryLit)
			{
				if(!(worldObj.getBlockId(xCoord+direction[0], yCoord, zCoord+direction[1])==TFCBlocks.Charcoal.blockID))
				{
					bloomeryLit = false;
					System.out.println("unlighting");
					flag = true;
				}
				else
				{
					fuelCount = worldObj.getBlockMetadata(xCoord+direction[0], yCoord, zCoord+direction[1])+1;
					System.out.println(fuelCount);
				}
			}

			outCount = getOutputCount();
			if(outCount < 0)
				outCount = 0;
			if(oreCount < 0)
				oreCount = 0;
			if(charcoalCount < 0)
				charcoalCount = 0;
			if(fuelCount < 0)
				fuelCount = 0;

			if(oreCount == 0 && outCount == 0)
			{
				OreType = "";
				oreDamage = -1;
				outMetal1 = null;
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
			for (int i = 1; i < 3  && (bloomeryLit || fuelCount > 0); i++)
			{
				/*The stack must be air or already be molten rock*/
				if((worldObj.getBlockId(xCoord+direction[0], yCoord+i, zCoord+direction[1]) == 0 ||
						worldObj.getBlockId(xCoord+direction[0], yCoord+i, zCoord+direction[1]) == TFCBlocks.Molten.blockID) &&
						worldObj.getBlockMaterial(xCoord+direction[0], yCoord-1, zCoord+direction[1]) == Material.rock)
				{
					//Make sure that the Stack is surrounded by rock
					if(i-1 < moltenCount && isStackValid(xCoord+direction[0], yCoord+i, zCoord+direction[1])) 
					{
						worldObj.setBlock(xCoord+direction[0], yCoord+i, zCoord+direction[1], TFCBlocks.Molten.blockID, 0, 0x2);
					} 
					else 
					{
						worldObj.setBlockToAir(xCoord+direction[0], yCoord+i, zCoord+direction[1]);
					}
				}
			}
			/*Create a list of all the items that are falling into the stack */
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
					xCoord+direction[0], yCoord+moltenCount, zCoord+direction[1], 
					xCoord+direction[0]+1, yCoord+moltenCount+1.1, zCoord+direction[1]+1));

			if(moltenCount == 0)
				moltenCount = 1;
			/*Make sure the list isn't null or empty and that the stack is valid 1 layer above the Molten Ore*/
			if (list != null && !list.isEmpty() && isStackValid(xCoord+direction[0], yCoord+moltenCount, zCoord+direction[1]) && !bloomeryLit)
			{
				/*Iterate through the list and check for charcoal, coke, and ore*/
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityItem entity = (EntityItem)iterator.next();
					if(entity.getEntityItem().itemID == Item.coal.itemID && entity.getEntityItem().getItemDamage() == 1 || entity.getEntityItem().itemID == TFCItems.Coke.itemID)
					{
						for(int c = 0; c < entity.getEntityItem().stackSize; c++)
						{
							if(charcoalCount+oreCount < 16 && charcoalCount < 8)
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
					else if(TFC_ItemHeat.getMeltingPoint(entity.getEntityItem()) != -1 && entity.getEntityItem().getItem() instanceof ItemOre && 
							((ItemOre)entity.getEntityItem().getItem()).GetMetalType(entity.getEntityItem()) == Global.PIGIRON)
					{
						int c = entity.getEntityItem().stackSize;
						for(; c > 0; c--)
						{
							if(charcoalCount+oreCount < 16 && oreCount < 8 && outCount < 1000)
							{
								if(AddOreToFire(new ItemStack(entity.getEntityItem().getItem(),1,entity.getEntityItem().getItemDamage()))) 
								{
									oreCount+=1;
									oreDamage = entity.getEntityItem().getItemDamage();
								}
							}
						}
						if(c == 0) {
							entity.setDead();
						}
						else
							entity.getEntityItem().stackSize = c; 
					}
				}
			}

			/*Handle the temperature of the Bloomery*/
			HandleTemperature();

			if(flag)
			{

				worldObj.setBlock(xCoord+direction[0], yCoord, zCoord+direction[1], TFCBlocks.Bloom.blockID);
				//worldObj.setBlockMetadataWithNotify(xCoord+direction[0], yCoord, zCoord+direction[1],Math.min((outMetal1Count+outMetal2Count)/100,5),2);

				oreCount = 0;
				charcoalCount = 0;
				fuelCount = 0;
				((TileEntityBloom)(worldObj.getBlockTileEntity(xCoord+direction[0], yCoord, zCoord+direction[1]))).setSize(outCount);
			}

			//Here we make sure that the forge is valid
			isValid = CheckValidity();
		}
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
		nbttagcompound.setBoolean("isValid", isValid);
		nbttagcompound.setInteger("charcoalCount", charcoalCount);
		nbttagcompound.setInteger("fuelCount", fuelCount);
		nbttagcompound.setBoolean("isLit",bloomeryLit);
		nbttagcompound.setInteger("outMetal1Count", outMetal1Count);
		nbttagcompound.setInteger("oreDamage", oreDamage);

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
		isValid = nbttagcompound.getBoolean("isValid");
		charcoalCount = nbttagcompound.getInteger("charcoalCount");
		fuelCount = nbttagcompound.getInteger("fuelCount");
		bloomeryLit = nbttagcompound.getBoolean("isLit");
		outMetal1Count = nbttagcompound.getInteger("outMetal1Count");
		oreDamage = nbttagcompound.getInteger("oreDamage");

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
		if(!worldObj.isRemote)
			TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, createUpdatePacket(), 5);
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {

		oreCount = inStream.readInt();
		charcoalCount = inStream.readInt();
		oreDamage = inStream.readInt();
		outCount = inStream.readInt();

		if(oreDamage == -1)
			this.OreType = "";
		else
			this.OreType = new ItemStack(TFCItems.OreChunk, 1, oreDamage).getDisplayName();

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
			dos.writeInt(oreDamage);
			dos.writeInt(outCount);
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
		return (this.oreCount * l)/40;
	}

	public int getCharcoalCountScaled(int l)
	{
		return (this.charcoalCount * l)/40;
	}

	public int getOutCountScaled(int l)
	{
		return ((this.outCount * l)/1000);
	}

	@Override
	public boolean isInvNameLocalized() 
	{
		return false;
	}

	@Override
	public boolean isStackValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}
}
