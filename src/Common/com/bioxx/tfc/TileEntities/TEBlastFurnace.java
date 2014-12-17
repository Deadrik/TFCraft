package com.bioxx.tfc.TileEntities;

import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.util.AxisAlignedBB;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Blocks.Devices.BlockBlastFurnace;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.GUI.GuiBlastFurnace;
import com.bioxx.tfc.api.HeatIndex;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

import cpw.mods.fml.client.FMLClientHandler;

public class TEBlastFurnace extends TEFireEntity implements IInventory
{
	public boolean isValid;

	public ItemStack storage[];
	public ItemStack fireItemStacks[];
	public ItemStack outputItemStacks[];

	private int prevStackSize;
	private int numAirBlocks;

	public String OreType;

	// We dont save this since its purpose is to just mkae certain parts of the
	// code not run every single tick
	public int slowCounter = 0;

	// Bloomery
	public int charcoalCount;
	public int oreCount;

	ItemStack outMetal1;
	int outMetal1Count;

	private int cookDelay = 0;
	private int maxValidStackSize = 0;

	public TEBlastFurnace()
	{
		fuelTimeLeft = 0;
		maxFireTempScale = 2100;
		fuelBurnTemp = 0;

		fireTemp = 0;
		isValid = false;
		fireItemStacks = new ItemStack[20];
		outputItemStacks = new ItemStack[20];
		storage = new ItemStack[2];
		numAirBlocks = 0;
		airFromBellows = 0;
		charcoalCount = 0;
		oreCount = 0;
//		shouldSendInitData = false;
	}

	public boolean canLight()
	{
		if (!worldObj.isRemote)
		{
			// get the direction that the bloomery is facing so that we know
			// where the stack should be
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

			if (this.charcoalCount < this.oreCount) 
				return false;

			if (this.charcoalCount >= 4 && this.fireTemp == 0)
			{
				fireTemp = 1;
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 4, 0x2);
				return true;
			}
		}
		return false;
	}

	private Boolean CheckValidity()
	{
		int y = yCoord + 1;
		if(this.isStackValid(xCoord, y, zCoord))
			return true;
		return false;
	}

	@Override
	public void closeInventory()
	{
	}

	public void CookItem(int i)
	{
		HeatRegistry manager = HeatRegistry.getInstance();
		Random R = new Random();
		TECrucible te = (TECrucible) worldObj.getTileEntity(xCoord, yCoord - 1, zCoord);

		// Only allow the ore to smelt if there is a valid Tuyere associated
		// with the furnace
		if (fireItemStacks[i] != null && te != null && storage[1] != null && cookDelay == 0)
		{
			HeatIndex index = manager.findMatchingIndex(fireItemStacks[i]);
			if (index != null && TFC_ItemHeat.GetTemp(fireItemStacks[i]) >= index.meltTemp)
			{
				oreCount--;
				charcoalCount--;

				int output = 0;
				if (fireItemStacks[i].getItem() instanceof ISmeltable)
				{
					output = ((ISmeltable) fireItemStacks[i].getItem()).GetMetalReturnAmount(fireItemStacks[i]);
					te.addMetal(((ISmeltable) fireItemStacks[i].getItem()).GetMetalType(fireItemStacks[i]), output);
				}
				else
				{
					Metal m = MetalRegistry.instance.getMetalFromItem(fireItemStacks[i].getItem());
					output = index.getOutput(fireItemStacks[i], R).getItemDamage();
					if(m != null)
						te.addMetal(m, (short)(100-output));
				}
				cookDelay = 100;
				fireItemStacks[i] = null;
				storage[1].setItemDamage(storage[1].getItemDamage() + 1);
				if (storage[1] != null && storage[1].getItemDamage() == storage[1].getMaxDamage())
				{
					setInventorySlotContents(1, null);
				}
				te.temperature = (int)fireTemp;
			}
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if (storage[i] != null)
		{
			if (storage[i].stackSize <= j)
			{
				ItemStack itemstack = storage[i];
				storage[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = storage[i].splitStack(j);
			if (storage[i].stackSize == 0)
			{
				storage[i] = null;
			}
			return itemstack1;
		}
		else
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
			if (fireItemStacks[i] != null)
			{
				entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, fireItemStacks[i]);
				entityitem.motionX = (float) rand.nextGaussian() * f3;
				entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
				entityitem.motionZ = (float) rand.nextGaussian() * f3;
				worldObj.spawnEntityInWorld(entityitem);
			}
		}

		//charcoal
		if(this.charcoalCount > 0)
		{
			entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, new ItemStack(TFCItems.Coal, charcoalCount, 1));
			entityitem.motionX = (float) rand.nextGaussian() * f3;
			entityitem.motionY = (float) rand.nextGaussian() * f3 + 0.2F;
			entityitem.motionZ = (float) rand.nextGaussian() * f3;
			worldObj.spawnEntityInWorld(entityitem);
		}

	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public String getInventoryName()
	{
		return "BlastFurnace";
	}

	@Override
	public int getSizeInventory()
	{
		return storage.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return storage[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int i)
	{
		return storage[i];
	}

	public void HandleTemperature()
	{
		int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);

		// Now we increase the temperature
		// If the fire is still burning and has fuel
		if (fuelTimeLeft > 0)
		{
			float desiredTemp = handleTemp();
			handleTempFlux(desiredTemp);
		}
		else if (fuelTimeLeft <= 0 && charcoalCount > 0 && (meta & 4) > 0)
		{
			charcoalCount--;

			fuelTimeLeft = 1875;
			fuelBurnTemp = 1400;
		}
		else
		{
			if ((meta & 4) > 0)
				worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta & 3, 3);

			fuelBurnTemp = 0;
			float desiredTemp = handleTemp();
			handleTempFlux(desiredTemp);
		}

		// Here we handle the bellows
		handleAirReduction();
	}

	@Override
	public void receiveAirFromBellows()
	{
		if(storage[1] != null)
			super.receiveAirFromBellows();
	}

	public boolean isStackValid(int i, int j, int k)
	{
		Block yNegBlock = worldObj.getBlock(i, j-1, k);
		if(yNegBlock != TFCBlocks.Molten &&
				worldObj.getBlock(i, j-1, k).getMaterial() != Material.rock &&
				!worldObj.getBlock(i, j-1, k).isNormalCube() &&
				yNegBlock != TFCBlocks.BlastFurnace && TFC_Core.isTopFaceSolid(worldObj, i, j-1, k))
		{
			return false;
		}

		maxValidStackSize = 0;
		for (int num = 0; num < 5; num++)
		{
			if (!((BlockBlastFurnace) TFCBlocks.BlastFurnace).checkStackAt(worldObj, i, j+num, k))
				break;
			maxValidStackSize++;
		}
		if(maxValidStackSize == 0)
			return false;
		return true;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		return false;
	}

	@Override
	public void openInventory()
	{
	}

	public boolean AddOreToFire(ItemStack is)
	{
		for (int i = 0; i < fireItemStacks.length; i++)
		{
			if (fireItemStacks[i] == null)
			{
				fireItemStacks[i] = is;
				return true;
			}
		}
		return false;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		storage[i] = itemstack;
		if (itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	public void CreateTuyereBlock()
	{
		/**
		 * Create a tuyere block if the tuyere slot is not empty. REMOVED: Code
		 * remains for a potential revisit later. For now the tuyere will not be
		 * a rendered block.
		 */
		/*
		 * if(input[1] != null) { //get the direction that the bloomery is
		 * facing int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) &
		 * 3;
		 * 
		 * if((meta == 0 || meta == 2) && worldObj.getBlockId(xCoord+1, yCoord,
		 * zCoord) != TFCBlocks.Tuyere.blockID && worldObj.getBlockId(xCoord-1,
		 * yCoord, zCoord) != TFCBlocks.Tuyere.blockID) {
		 * if(worldObj.getBlockId(xCoord+1, yCoord, zCoord) !=
		 * TFCBlocks.Tuyere.blockID && worldObj.isAirBlock(xCoord+1, yCoord,
		 * zCoord)) { worldObj.setBlock(xCoord+1, yCoord, zCoord,
		 * TFCBlocks.Tuyere.blockID,
		 * ((ItemTuyere)input[1].getItem()).BlockMeta+8, 2); } else
		 * if(worldObj.getBlockId(xCoord-1, yCoord, zCoord) !=
		 * TFCBlocks.Tuyere.blockID && worldObj.isAirBlock(xCoord-1, yCoord,
		 * zCoord)) { worldObj.setBlock(xCoord-1, yCoord, zCoord,
		 * TFCBlocks.Tuyere.blockID,
		 * ((ItemTuyere)input[1].getItem()).BlockMeta+8, 2); }
		 * 
		 * } else if((meta == 1 || meta == 3) && worldObj.getBlockId(xCoord,
		 * yCoord, zCoord+1) != TFCBlocks.Tuyere.blockID &&
		 * worldObj.getBlockId(xCoord, yCoord, zCoord-1) !=
		 * TFCBlocks.Tuyere.blockID) { if(worldObj.getBlockId(xCoord, yCoord,
		 * zCoord+1) != TFCBlocks.Tuyere.blockID && worldObj.isAirBlock(xCoord,
		 * yCoord, zCoord+1)) { worldObj.setBlock(xCoord, yCoord, zCoord+1,
		 * TFCBlocks.Tuyere.blockID, ((ItemTuyere)input[1].getItem()).BlockMeta,
		 * 2); } else if(worldObj.getBlockId(xCoord, yCoord, zCoord-1) !=
		 * TFCBlocks.Tuyere.blockID && worldObj.isAirBlock(xCoord, yCoord,
		 * zCoord-1)) { worldObj.setBlock(xCoord, yCoord, zCoord-1,
		 * TFCBlocks.Tuyere.blockID, ((ItemTuyere)input[1].getItem()).BlockMeta,
		 * 2); }
		 * 
		 * } } else { if(worldObj.getBlockId(xCoord+1, yCoord, zCoord) ==
		 * TFCBlocks.Tuyere.blockID) { worldObj.setBlockToAir(xCoord+1, yCoord,
		 * zCoord); } else if(worldObj.getBlockId(xCoord-1, yCoord, zCoord) ==
		 * TFCBlocks.Tuyere.blockID ) { worldObj.setBlockToAir(xCoord-1, yCoord,
		 * zCoord); } else if(worldObj.getBlockId(xCoord, yCoord, zCoord+1) ==
		 * TFCBlocks.Tuyere.blockID) { worldObj.setBlockToAir(xCoord, yCoord,
		 * zCoord+1); } else if(worldObj.getBlockId(xCoord, yCoord, zCoord-1) ==
		 * TFCBlocks.Tuyere.blockID) { worldObj.setBlockToAir(xCoord, yCoord,
		 * zCoord-1); } }
		 */
	}

	public int getTotalCount()
	{
		return charcoalCount+oreCount;
	}

	int moltenCount = 0;

	@Override
	public void updateEntity()
	{
		if (!worldObj.isRemote)
		{
			CreateTuyereBlock();

			if(oreCount < 0)
				oreCount = 0;
			if(charcoalCount < 0)
				charcoalCount = 0;

			/* Create a list of all the items that are falling into the stack */
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class,
					AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + moltenCount + 1.1, zCoord + 1));

			/*Create a list of any players that are inside the chimney*/
			List playerList = worldObj.getEntitiesWithinAABB(EntityPlayer.class, AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + moltenCount + 1.1, zCoord + 1));

			if (moltenCount == 0)
				moltenCount = 1;
			/*
			 * Make sure the list isn't null or empty and that the stack is
			 * valid 1 layer above the Molten Ore
			 */
			if (list != null && !list.isEmpty() && ((BlockBlastFurnace) TFCBlocks.BlastFurnace).checkStackAt(worldObj, xCoord, yCoord + moltenCount, zCoord) && (playerList == null || playerList.isEmpty()))
			{
				/*
				 * Iterate through the list and check for charcoal, coke, and
				 * ore
				 */
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityItem entity = (EntityItem) iterator.next();
					boolean _isOre = TFC_Core.isOreIron(entity.getEntityItem());

					if (entity.getEntityItem().getItem() == TFCItems.Coal &&
							entity.getEntityItem().getItemDamage() == 1 ||
							entity.getEntityItem().getItem() == TFCItems.Coke)
					{
						for (int c = 0; c < entity.getEntityItem().stackSize; c++)
						{
							if (getTotalCount() < 40 && charcoalCount < (this.maxValidStackSize*4))
							{
								charcoalCount++;
								entity.getEntityItem().stackSize--;
							}
						}

						if (entity.getEntityItem().stackSize == 0)
							entity.setDead();
					}
					/*
					 * If the item that's been tossed in is a type of Ore and it
					 * can melt down into something then add the ore to the list
					 * of items in the fire.
					 */
					else if ((TFC_ItemHeat.IsCookable(entity.getEntityItem()) != -1 && _isOre)
							|| (!_isOre && entity.getEntityItem().getItem() instanceof ISmeltable))
					{
						int c = entity.getEntityItem().stackSize;
						int nonConsumedOre = 0;
						for (; c > 0; c--)
						{
							if (getTotalCount() < 40 && oreCount < (this.maxValidStackSize*4))
							{
								if (foundFlux(moltenCount) && AddOreToFire(new ItemStack(entity.getEntityItem().getItem(), 1, entity.getEntityItem().getItemDamage())))
									oreCount+=1;
								else
									nonConsumedOre++;
							}
							else
							{
								nonConsumedOre++;
							}
						}

						if (c + nonConsumedOre == 0)
							entity.setDead();
						else
						{
							ItemStack is = entity.getEntityItem();
							is.stackSize = c + nonConsumedOre;
							entity.setEntityItemStack(is);
						}
					}
				}
			}

			/* Handle the temperature of the Bloomery */
			HandleTemperature();

			if (cookDelay > 0)
				cookDelay--;

			for (int i = 0; i < fireItemStacks.length && isValid; i++)
			{
				/* Handle temperature for each item in the stack */
				careForInventorySlot(fireItemStacks[i]);
				/* Cook each input item */
				if (worldObj.getBlock(xCoord, yCoord - 1, zCoord) == TFCBlocks.Crucible)
				{
					CookItem(i);
				}
			}

			// Every 5 seconds we do a validity check and update the molten ore
			// count
			if (slowCounter > 100)
			{
				// Here we make sure that the forge is valid
				isValid = CheckValidity();
				moltenCount = updateMoltenBlocks();
			}
			slowCounter++;
		}
	}

	/**
	 * @return Number of molten blocks
	 */
	private int updateMoltenBlocks()
	{
		int count = charcoalCount + oreCount;

		int moltenCount = 0;
		if(count > 0 && count <= 8) {moltenCount = 1;}
		else if(count > 8 && count <= 16) {moltenCount = 2;}
		else if(count > 16 && count <= 24) {moltenCount = 3;}
		else if(count > 24 && count <= 32) {moltenCount = 4;}
		else if(count > 32 && count <= 40) {moltenCount = 5;}

		/* Fill the bloomery stack with molten ore. */
		for (int i = 1; i <= 5; i++)
		{
			/*The stack must be air or already be molten rock*/
			if((worldObj.isAirBlock(xCoord, yCoord+i, zCoord) ||
					worldObj.getBlock(xCoord, yCoord+i, zCoord) == TFCBlocks.Molten))
			{
				// Make sure that the Stack is surrounded by rock
				/*if (isStackValid(xCoord, yCoord + i, zCoord))
				{
					validCount++;
				}*/
				if (i <= moltenCount && i <= this.maxValidStackSize)
				{
					if (this.fireTemp > 100)
					{
						int m = count > 7 ? 7 : count;
						worldObj.setBlock(xCoord, yCoord + i, zCoord, TFCBlocks.Molten, m + 8, 2);
						count -= 8;
					}
					else
					{
						int m = count > 7 ? 7 : count;
						worldObj.setBlock(xCoord, yCoord + i, zCoord, TFCBlocks.Molten, m, 2);
						count -= 8;
					}
				}
				else
				{
					worldObj.setBlockToAir(xCoord, yCoord + i, zCoord);
				}
			}
		}
		return moltenCount;
	}

	private boolean foundFlux(int moltenCount)
	{
		List list = worldObj.getEntitiesWithinAABB(EntityItem.class,
				AxisAlignedBB.getBoundingBox(xCoord, yCoord + moltenCount, zCoord, xCoord + 1, yCoord + moltenCount + 1.1, zCoord + 1));
		boolean found = false;
		for (Iterator iterator = list.iterator(); iterator.hasNext() && !found;)
		{
			EntityItem entity = (EntityItem) iterator.next();
			ItemStack is = entity.getEntityItem();
			if(!entity.isDead && (is.getItemDamage() == 0) && is.getItem() == TFCItems.Powder)
			{
				is.stackSize--;
				if(is.stackSize == 0)
					entity.setDead();
				else
					entity.setEntityItemStack(is);
				found = true;
			}
		}
		return found;
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
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return false;
	}

	@Override
	public boolean hasCustomInventoryName()
	{
		return false;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		nbt.setInteger("charcoalCount", charcoalCount);
		nbt.setInteger("outMetal1Count", outMetal1Count);
		nbt.setByte("oreCount", (byte) oreCount);
		nbt.setInteger("maxValidStackSize", maxValidStackSize);


		NBTTagList nbttaglist = new NBTTagList();
		for (int i = 0; i < fireItemStacks.length; i++)
		{
			if (fireItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				fireItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);

		NBTTagList nbttaglist2 = new NBTTagList();
		for (int i = 0; i < storage.length; i++)
		{
			if (storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				storage[i].writeToNBT(nbttagcompound1);
				nbttaglist2.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Input", nbttaglist2);

		NBTTagList nbttaglist3 = new NBTTagList();
		for (int i = 0; i < outputItemStacks.length; i++)
		{
			if (outputItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte) i);
				outputItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist3.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Output", nbttaglist3);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		charcoalCount = nbt.getInteger("charcoalCount");
		outMetal1Count = nbt.getInteger("outMetal1Count");
		oreCount = nbt.getByte("oreCount");
		maxValidStackSize = nbt.getInteger("maxValidStackSize");

		NBTTagList nbttaglist = nbt.getTagList("Items", 10);
		fireItemStacks = new ItemStack[20];
		for (int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < fireItemStacks.length)
				fireItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}

		NBTTagList nbttaglist2 = nbt.getTagList("Input", 10);
		storage = new ItemStack[2];
		for (int i = 0; i < nbttaglist2.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist2.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if (byte0 >= 0 && byte0 < storage.length)
			{
				storage[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}

		NBTTagList nbttaglist3 = nbt.getTagList("Output", 10);
		outputItemStacks = new ItemStack[20];
		for (int i = 0; i < nbttaglist3.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist3.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < outputItemStacks.length)
				outputItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
		
		GuiScreen gui = FMLClientHandler.instance().getClient().currentScreen;
		if(gui != null && gui instanceof GuiBlastFurnace)
			((GuiBlastFurnace)gui).updateScreen();
	}

	public void updateGui()
	{
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

}
