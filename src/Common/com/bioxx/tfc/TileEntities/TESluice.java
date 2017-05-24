package com.bioxx.tfc.TileEntities;

import java.util.*;

import com.bioxx.tfc.Blocks.Terrain.BlockOre2;
import net.minecraft.block.Block;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;

import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Blocks.Devices.BlockSluice;
import com.bioxx.tfc.Blocks.Terrain.BlockOre;
import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;

public class TESluice extends TileEntity implements IInventory
{
	public int soilAmount;
	public long lastUpdateTicks;
	public int processTimeRemaining;
	private ItemStack sluiceItemStacks[];
	public boolean waterInput;
	public boolean waterOutput;
	public byte soilType;
	private boolean initialized;
	private Random random = new Random();
	private Set<Integer> coreSampleTypes = new TreeSet<Integer>();
	private List<ItemStack> coreSampleStacks = new ArrayList<ItemStack>();

	public TESluice()
	{
		sluiceItemStacks = new ItemStack[9];
		soilAmount = 0;
		lastUpdateTicks = 0;
		processTimeRemaining = 0;
		waterInput = false;
		waterOutput = false;
		soilType = 1;
	}

	public void addToInventory(ItemStack is)
	{
		for(int i = 0; i < this.getSizeInventory(); i++)
		{
			ItemStack stackInSlot = this.getStackInSlot(i);
			if(stackInSlot == null)
			{
				// slot is empty
				this.setInventorySlotContents(i, is);
				return;
			}
			else
			{
				// slot has something, does it match the type and subtype?
				if (stackInSlot == is && stackInSlot.getItemDamage() == is.getItemDamage())
				{
					// match, add to this slot but make sure it fits
					if(stackInSlot.stackSize + is.stackSize > this.getInventoryStackLimit())
					{
						// doesn't fit, add as much as possible then leave the remaining for the next slot
						int size = getInventoryStackLimit() - stackInSlot.stackSize;
						stackInSlot.stackSize += size;
						is.stackSize -= size;
						continue;
					}
					else
					{
						// it fits, add it
						stackInSlot.stackSize += is.stackSize;
					}
					return;
				}
				else
				{
					// not the same item, try the next slot
					continue;
				}
			}
		}
		ejectItem(is);
	}

	@Override
	public void closeInventory()
	{
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(sluiceItemStacks[i] != null)
		{
			if(sluiceItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = sluiceItemStacks[i];
				sluiceItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = sluiceItemStacks[i].splitStack(j);
			if(sluiceItemStacks[i].stackSize == 0)
				sluiceItemStacks[i] = null;
			return itemstack1;
		}
		else
		{
			return null;
		}
	}

	private void ejectItem(ItemStack is)
	{
		float f = random.nextFloat() * 0.8F + 0.1F;
		float f1 = random.nextFloat() * 2.0F + 0.4F;
		float f2 = random.nextFloat() * 0.8F + 0.1F;
		EntityItem entityitem = new EntityItem(worldObj, xCoord + f, yCoord + f1, zCoord + f2, new ItemStack(is.getItem(), is.stackSize, is.getItemDamage()));
		float f3 = 0.05F;
		entityitem.motionX = (float)random.nextGaussian() * f3;
		entityitem.motionY = (float)random.nextGaussian() * f3 + 0.2F;
		entityitem.motionZ = (float)random.nextGaussian() * f3;
		worldObj.spawnEntityInWorld(entityitem);
	}

	public int getFirstFreeSlot()
	{
		for(int i = 0; i < this.getSizeInventory(); i++)
		{
			if(this.getStackInSlot(i) == null)
				return i;
		}
		return  - 1;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 64;
	}

	@Override
	public String getInventoryName()
	{
		return "Sluice";
	}

	public int getProcessScaled(int i)
	{
		return processTimeRemaining * i / 100;
	}

	@Override
	public int getSizeInventory()
	{
		return sluiceItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return sluiceItemStacks[i];
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1)
	{
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer)
	{
		if(worldObj.getTileEntity(xCoord, yCoord, zCoord) != this)
			return false;
		return entityplayer.getDistanceSq(xCoord + 0.5D, yCoord + 0.5D, zCoord + 0.5D) <= 64D;
	}

	@Override
	public void openInventory()
	{
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		sluiceItemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
		int meta = getBlockMetadata();
		boolean isFoot = BlockSluice.isBlockFootOfBed(meta);
		if (isFoot || soilAmount == -1)
			return;

		/*********************************************************
		 ********************* Server Only Begin
		 *********************************************************/
		if(!worldObj.isRemote)
		{
			if(!initialized)
			{
				for(int x =  - 100; x <= 100; x += 2)
				{
					for(int z =  - 100; z <= 100; z += 2)
					{
						for(int y = yCoord; y > yCoord-50; y--)
						{
							if(worldObj.getBlock(x+xCoord, y, z+zCoord) == TFCBlocks.ore)
							{
								int m = worldObj.getBlockMetadata(x+xCoord, y, z+zCoord);
								if(m != 14 && m != 15)
								{
									if(!coreSampleTypes.contains(m))
									{
										coreSampleTypes.add(m);
										coreSampleStacks.add(new ItemStack(BlockOre.getDroppedItem(m), 1, m));
										coreSampleStacks.add(new ItemStack(BlockOre2.getDroppedItem(m), 1, m));
									}
								}
							}
						}
					}
				}
				initialized = true;
			}

			List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
					xCoord, yCoord, zCoord,
					xCoord + 1, yCoord + 1.1f, zCoord + 1));

			for (Iterator iterator = list.iterator(); iterator.hasNext();)
			{
				EntityItem entity = (EntityItem)iterator.next();
				Item item = entity.getEntityItem().getItem();
				if(item == Item.getItemFromBlock(TFCBlocks.gravel)|| item == Item.getItemFromBlock(TFCBlocks.gravel2) || 
						item == Item.getItemFromBlock(TFCBlocks.sand) || item == Item.getItemFromBlock(TFCBlocks.sand2))
				{
					int stackSize = entity.getEntityItem().stackSize;
					int accept = (50 + 19 - soilAmount) / 20;
					if (stackSize <= accept)
					{
						soilAmount += 20 * stackSize;
						entity.setDead();
						if(soilAmount > 50)
							soilAmount = 50;
						if(item == Item.getItemFromBlock(TFCBlocks.gravel)|| item == Item.getItemFromBlock(TFCBlocks.gravel2))
							soilType = 2;
						else
							soilType = 1;
					}
				}
			}

			// time since last update
			long tickDiff = TFC_Time.getTotalTicks() - lastUpdateTicks;
			if(lastUpdateTicks == 0)
			{
				// first update
				tickDiff = 0;
			}
			lastUpdateTicks = TFC_Time.getTotalTicks();

			//This is where we handle the processing of the material
			if(soilAmount > 0 && waterInput && waterOutput)
			{
				// note that the input & output flags were checked last update, so it should be ok to use the
				// diff if sluice was just re-loaded
				processTimeRemaining += tickDiff;
				if(processTimeRemaining < 0)
				{
					// overflow?
					processTimeRemaining = 0;
				}

				ChunkData cd = TFC_Core.getCDM(worldObj).getData(xCoord >> 4, zCoord >> 4);

				if (TFCOptions.enableOverworkingChunks && cd.sluicedAmount > TFCOptions.sluiceLimit)
				{
					processTimeRemaining = 0;
					soilAmount = -1;
					return;
				}

				while (processTimeRemaining > 100 && soilAmount > 0)
				{
					float gemMod = 1;
					float oreMod = 1;
					if(soilType == 1)
						gemMod = 0.65f;
					else if(soilType == 2)
						oreMod = 0.6f;

					ArrayList<ItemStack> items = new ArrayList<ItemStack>();
					if(random.nextInt((int) (200 * oreMod)) == 0 && !coreSampleStacks.isEmpty())
						addToInventory(coreSampleStacks.get(random.nextInt(coreSampleStacks.size())).copy());
					else if(random.nextInt((int) (400 * gemMod)) == 0)
					{
						items.add(new ItemStack(TFCItems.gemAgate, 1, 0));
						items.add(new ItemStack(TFCItems.gemAmethyst, 1, 0));
						items.add(new ItemStack(TFCItems.gemBeryl, 1, 0));
						items.add(new ItemStack(TFCItems.gemEmerald, 1, 0));
						items.add(new ItemStack(TFCItems.gemGarnet, 1, 0));
						items.add(new ItemStack(TFCItems.gemJade, 1, 0));
						items.add(new ItemStack(TFCItems.gemJasper, 1, 0));
						items.add(new ItemStack(TFCItems.gemOpal, 1, 0));
						items.add(new ItemStack(TFCItems.gemRuby, 1, 0));
						items.add(new ItemStack(TFCItems.gemSapphire, 1, 0));
						items.add(new ItemStack(TFCItems.gemTourmaline, 1, 0));
						items.add(new ItemStack(TFCItems.gemTopaz, 1, 0));
						addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
					}
					else if(random.nextInt((int) (800 * gemMod)) == 0)
					{
						items.add(new ItemStack(TFCItems.gemAgate, 1, 1));
						items.add(new ItemStack(TFCItems.gemAmethyst, 1, 1));
						items.add(new ItemStack(TFCItems.gemBeryl, 1, 1));
						items.add(new ItemStack(TFCItems.gemEmerald, 1, 1));
						items.add(new ItemStack(TFCItems.gemGarnet, 1, 1));
						items.add(new ItemStack(TFCItems.gemJade, 1, 1));
						items.add(new ItemStack(TFCItems.gemJasper, 1, 1));
						items.add(new ItemStack(TFCItems.gemOpal, 1, 1));
						items.add(new ItemStack(TFCItems.gemRuby, 1, 1));
						items.add(new ItemStack(TFCItems.gemSapphire, 1, 1));
						items.add(new ItemStack(TFCItems.gemTourmaline, 1, 1));
						items.add(new ItemStack(TFCItems.gemTopaz, 1, 1));
						addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
					}
					else if(random.nextInt((int) (1600 * gemMod)) == 0)
					{
						items.add(new ItemStack(TFCItems.gemAgate, 1, 2));
						items.add(new ItemStack(TFCItems.gemAmethyst, 1, 2));
						items.add(new ItemStack(TFCItems.gemBeryl, 1, 2));
						items.add(new ItemStack(TFCItems.gemEmerald, 1, 2));
						items.add(new ItemStack(TFCItems.gemGarnet, 1, 2));
						items.add(new ItemStack(TFCItems.gemJade, 1, 2));
						items.add(new ItemStack(TFCItems.gemJasper, 1, 2));
						items.add(new ItemStack(TFCItems.gemOpal, 1, 2));
						items.add(new ItemStack(TFCItems.gemRuby, 1, 2));
						items.add(new ItemStack(TFCItems.gemSapphire, 1, 2));
						items.add(new ItemStack(TFCItems.gemTourmaline, 1, 2));
						items.add(new ItemStack(TFCItems.gemTopaz, 1, 2));
						addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
					}
					else if(random.nextInt((int) (3200 * gemMod)) == 0)
					{
						items.add(new ItemStack(TFCItems.gemAgate, 1, 3));
						items.add(new ItemStack(TFCItems.gemAmethyst, 1, 3));
						items.add(new ItemStack(TFCItems.gemBeryl, 1, 3));
						items.add(new ItemStack(TFCItems.gemEmerald, 1, 3));
						items.add(new ItemStack(TFCItems.gemGarnet, 1, 3));
						items.add(new ItemStack(TFCItems.gemJade, 1, 3));
						items.add(new ItemStack(TFCItems.gemJasper, 1, 3));
						items.add(new ItemStack(TFCItems.gemOpal, 1, 3));
						items.add(new ItemStack(TFCItems.gemRuby, 1, 3));
						items.add(new ItemStack(TFCItems.gemSapphire, 1, 3));
						items.add(new ItemStack(TFCItems.gemTourmaline, 1, 3));
						items.add(new ItemStack(TFCItems.gemTopaz, 1, 3));
						addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
					}
					else if(random.nextInt((int) (6400 * gemMod)) == 0)
					{
						items.add(new ItemStack(TFCItems.gemAgate, 1, 4));
						items.add(new ItemStack(TFCItems.gemAmethyst, 1, 4));
						items.add(new ItemStack(TFCItems.gemBeryl, 1, 4));
						items.add(new ItemStack(TFCItems.gemEmerald, 1, 4));
						items.add(new ItemStack(TFCItems.gemGarnet, 1, 4));
						items.add(new ItemStack(TFCItems.gemJade, 1, 4));
						items.add(new ItemStack(TFCItems.gemJasper, 1, 4));
						items.add(new ItemStack(TFCItems.gemOpal, 1, 4));
						items.add(new ItemStack(TFCItems.gemRuby, 1, 4));
						items.add(new ItemStack(TFCItems.gemSapphire, 1, 4));
						items.add(new ItemStack(TFCItems.gemTourmaline, 1, 4));
						items.add(new ItemStack(TFCItems.gemTopaz, 1, 4));
						addToInventory((ItemStack)items.toArray()[random.nextInt(items.toArray().length)]);
					}
					else if(random.nextInt((int) (12800 * gemMod)) == 0)
					{
						int r = random.nextInt(50);
						if(r == 0)
							addToInventory(new ItemStack(TFCItems.gemDiamond, 1, 3));
						else if(r < 15)
							addToInventory(new ItemStack(TFCItems.gemDiamond, 1, 2));
						else if(r < 25)
							addToInventory(new ItemStack(TFCItems.gemDiamond, 1, 1));
						else if(r < 50)
							addToInventory(new ItemStack(TFCItems.gemDiamond, 1, 0));
					}
					cd.sluicedAmount++;
					processTimeRemaining -= 100;
					soilAmount--;
				}
			}
			if(soilAmount == 0)
				processTimeRemaining = 0;
		}
		/*********************************************************
		 ********************* Server Only End
		 *********************************************************/
		//Here we make sure that the water flags are checked
		if((meta & 3 )== 0)
		{
			waterInput = TFC_Core.isWater(worldObj.getBlock(xCoord, yCoord + 1, zCoord - 1));
			waterOutput = TFC_Core.isWater(worldObj.getBlock(xCoord, yCoord - 1, zCoord + 2)) || 
					TFC_Core.isWater(worldObj.getBlock(xCoord, yCoord - 1, zCoord + 2));
		}
		if((meta & 3 )== 1)
		{
			waterInput = TFC_Core.isWater(worldObj.getBlock(xCoord + 1, yCoord + 1, zCoord));
			waterOutput = TFC_Core.isWater(worldObj.getBlock(xCoord - 2, yCoord - 1, zCoord)) || 
					TFC_Core.isWater(worldObj.getBlock(xCoord - 2, yCoord - 1, zCoord));
		}
		if((meta & 3 )== 2)
		{
			waterInput = TFC_Core.isWater(worldObj.getBlock(xCoord, yCoord + 1, zCoord + 1));
			waterOutput = TFC_Core.isWater(worldObj.getBlock(xCoord, yCoord - 1, zCoord - 2)) || 
					TFC_Core.isWater(worldObj.getBlock(xCoord, yCoord - 1, zCoord - 2));
		}
		if((meta & 3 )== 3)
		{
			waterInput = TFC_Core.isWater(worldObj.getBlock(xCoord - 1, yCoord + 1, zCoord));
			waterOutput = TFC_Core.isWater(worldObj.getBlock(xCoord + 2, yCoord - 1, zCoord)) ||
					TFC_Core.isWater(worldObj.getBlock(xCoord + 2, yCoord - 1, zCoord));
		}

		/////////////////////////////////////////////////////////
		///////////This is where we handle the water flow
		////////////////////////////////////////////////////////
		boolean isFlowing = (meta & 4) == 4;
		ForgeDirection dir = getDir(meta & 3);
		Block water = worldObj.getBlock(xCoord + dir.offsetX, yCoord + 1, zCoord + dir.offsetZ);
		boolean isInputWater = TFC_Core.isWater(water);
		boolean isOutputAir = worldObj.isAirBlock(xCoord + dir.getOpposite().offsetX * 2, yCoord - 1, zCoord + dir.getOpposite().offsetZ * 2);
		boolean isOutputWater = TFC_Core.isWater(worldObj.getBlock(xCoord + dir.getOpposite().offsetX * 2, yCoord - 1, zCoord + dir.getOpposite().offsetZ * 2));
		boolean isWaterDepth7 = worldObj.getBlockMetadata(xCoord + dir.offsetX, yCoord + 1, zCoord + dir.offsetZ) == 7;
		int meta2 = worldObj.getBlockMetadata(xCoord + dir.getOpposite().offsetX, yCoord, zCoord + dir.getOpposite().offsetZ);
		if(isInputWater && isWaterDepth7 && !isFlowing && (isOutputAir || isOutputWater))
		{
			//set main block to water on
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 4, 3);

			if((worldObj.getBlockMetadata(xCoord + dir.getOpposite().offsetX, yCoord, zCoord + dir.getOpposite().offsetZ) & 4) == 0)
			{
				//set second block to water on
				worldObj.setBlockMetadataWithNotify(xCoord + dir.getOpposite().offsetX, yCoord, zCoord + dir.getOpposite().offsetZ, meta2 + 4, 3);
			}

			//Set output water
			worldObj.setBlock(xCoord + dir.getOpposite().offsetX * 2, yCoord - 1, zCoord + dir.getOpposite().offsetZ * 2, water);
		}
		if((!isInputWater || !isWaterDepth7 || !isOutputAir && !isOutputWater) && isFlowing)
		{
			//set main block to water off
			worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta - 4, 3);
			if((meta2 & 4) != 0)
			{
				//set second block to water off
				worldObj.setBlockMetadataWithNotify(xCoord + dir.getOpposite().offsetX, yCoord, zCoord + dir.getOpposite().offsetZ, meta2 - 4, 3);
			}
			//Set output water
			if(!isOutputAir && isOutputWater)
				worldObj.setBlockToAir(xCoord + dir.getOpposite().offsetX * 2, yCoord - 1, zCoord + dir.getOpposite().offsetZ * 2);
		}
	}

	private ForgeDirection getDir(int r)
	{
		if(r == 0)//+z
		{
			return ForgeDirection.NORTH;
		}
		else if(r == 1)//-x
		{
			return ForgeDirection.EAST;
		}
		else if(r == 2)//-z
		{
			return ForgeDirection.SOUTH;
		}
		else if(r == 3)//+x
		{
			return ForgeDirection.WEST;
		}

		return ForgeDirection.UNKNOWN;
	}

	@Override
	public boolean hasCustomInventoryName() 
	{
		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) 
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		NBTTagList nbttaglist = nbttagcompound.getTagList("Items", 10);
		sluiceItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = nbttaglist.getCompoundTagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < sluiceItemStacks.length)
				sluiceItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
		}
		soilType = nbttagcompound.getByte("soilType");
		soilAmount = nbttagcompound.getInteger("soilAmount");
		processTimeRemaining = nbttagcompound.getInteger("processTimeRemaining");
		lastUpdateTicks = nbttagcompound.getLong("lastUpdateTicks");
		waterInput = nbttagcompound.getBoolean("waterInput");
		waterOutput = nbttagcompound.getBoolean("waterOutput");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setByte("soilType", soilType);
		nbttagcompound.setInteger("soilAmount", soilAmount);
		nbttagcompound.setInteger("processTimeRemaining", processTimeRemaining);
		nbttagcompound.setLong("lastUpdateTicks", lastUpdateTicks);
		nbttagcompound.setBoolean("waterInput", waterInput);
		nbttagcompound.setBoolean("waterOutput", waterOutput);
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < sluiceItemStacks.length; i++)
		{
			if(sluiceItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				sluiceItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbttagcompound.setTag("Items", nbttaglist);
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
	}

}
