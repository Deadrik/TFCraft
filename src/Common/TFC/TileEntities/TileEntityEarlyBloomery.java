package TFC.TileEntities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.ISmeltable;
import TFC.API.Constant.Global;
import TFC.Blocks.Devices.BlockEarlyBloomery;
import TFC.Core.TFC_Time;
import TFC.Items.ItemOre;

public class TileEntityEarlyBloomery extends TileEntity
{
	public boolean isFlipped;
	public boolean bloomeryLit;
	private int prevStackSize;
	private int numAirBlocks;

	private int validationCheck = 60;

	//Bloomery
	public int charcoalCount;
	public long fuelTimeLeft;
	public int oreCount;
	public int outCount;

	public TileEntityEarlyBloomery()
	{
		isFlipped = false;
		bloomeryLit = false;
		numAirBlocks = 0;
		charcoalCount = 0;
		oreCount = 0;
		outCount = 0;
	}

	public void swapFlipped()
	{
		if(isFlipped)isFlipped = false;
		else isFlipped = true;
		if(!worldObj.isRemote)
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//		{
//			try
//			{
//				TerraFirmaCraft.proxy.sendCustomPacket(sendInitPacket());
//			}
//			catch (IOException e)
//			{
//				e.printStackTrace();
//			}
//		}
	}

	public boolean isStackValid(int i, int j, int k)
	{
		Block yNegBlock = worldObj.getBlock(i, j - 1, k);
		if(yNegBlock != TFCBlocks.Molten &&
				worldObj.getBlock(i, j - 1, k).getMaterial() != Material.rock && 
				!worldObj.getBlock(i, j - 1, k).isNormalCube() && 
				yNegBlock != TFCBlocks.Charcoal)
		{
			return false;
		}
		return ((BlockEarlyBloomery)TFCBlocks.EarlyBloomery).checkStack(worldObj, xCoord, j, zCoord, worldObj.getBlockMetadata(xCoord, yCoord, zCoord)&3);
	}

	public boolean AddOreToFire(ItemStack is)
	{
		if(((ISmeltable)is.getItem()).GetMetalType(is) == Global.PIGIRON || ((ISmeltable)is.getItem()).GetMetalType(is) == Global.WROUGHTIRON)
		{
			outCount += ((ISmeltable)is.getItem()).GetMetalReturnAmount(is);
			return true;
		}
		return false;
	}

	public boolean canLight()
	{
		if(!worldObj.isRemote)
		{
			//get the direction that the bloomery is facing so that we know where the stack should be
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			int[] direction = BlockEarlyBloomery.bloomeryToStackMap[getCharcoalDir(meta)];

			if (this.charcoalCount < this.oreCount)
				return false;

			Block bid = worldObj.getBlock(xCoord + direction[0], yCoord, zCoord + direction[1]);
			if(bid == TFCBlocks.Charcoal && 
					worldObj.getBlockMetadata(xCoord + direction[0], yCoord, zCoord + direction[1]) >= 7 && !bloomeryLit)
			{
				bloomeryLit = true;
				this.fuelTimeLeft = TFC_Time.getTotalTicks() + 14400;
				if((meta & 4) == 0) 
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 4, 3);
				return true;
			}
		}
		return false;
	}

	private int getCharcoalDir(int meta)
	{
		int dir = meta & 3;
		return dir;
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			//Do the funky math to find how many molten blocks should be placed
			int count = charcoalCount+oreCount;

			int moltenCount = count > 0 && count < 8 ? 1 : count / 8;
			int validCount = 0;
			int maxCount = 0;

			//get the direction that the bloomery is facing so that we know where the stack should be
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			int[] direction = BlockEarlyBloomery.bloomeryToStackMap[getCharcoalDir(meta)];
			if(bloomeryLit && TFC_Time.getTotalTicks() > fuelTimeLeft)
			{
				if((worldObj.getBlock(xCoord + direction[0], yCoord, zCoord + direction[1])==TFCBlocks.Molten))
				{
					bloomeryLit = false;
					worldObj.setBlock(xCoord + direction[0], yCoord, zCoord + direction[1], TFCBlocks.Bloom);
					worldObj.setBlockToAir(xCoord + direction[0], yCoord + (moltenCount < 2 ? 2 : moltenCount) - 1, zCoord + direction[1]);

					oreCount = 0;
					charcoalCount = 0;
					((TileEntityBloom)(worldObj.getTileEntity(xCoord + direction[0], yCoord, zCoord + direction[1]))).setSize(outCount);
					outCount = 0;
				}

				if((meta & 4) != 0) 
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta & 3, 3);
				}
			}

			if(outCount < 0)
				outCount = 0;
			if(oreCount < 0)
				oreCount = 0;
			if(charcoalCount < 0)
				charcoalCount = 0;

			/* Calculate how much ore the bloomery can hold. */
			if(isStackValid(xCoord + direction[0], yCoord + 3, zCoord + direction[1]) && 
					isStackValid(xCoord + direction[0], yCoord + 2, zCoord + direction[1]) && 
					isStackValid(xCoord + direction[0], yCoord + 1, zCoord + direction[1]))
			{
				maxCount = 24;
			}
			else if(isStackValid(xCoord + direction[0], yCoord + 2, zCoord + direction[1]) &&
					isStackValid(xCoord + direction[0], yCoord + 1, zCoord + direction[1]))
			{
				maxCount = 16;
			}
			else if(isStackValid(xCoord + direction[0], yCoord + 1, zCoord + direction[1]))
			{
				maxCount = 8;
			}

			int moltenHeight = Math.max((count/2) - 1, 0);
			/*Fill the bloomery stack with molten ore. */
			for (int i = bloomeryLit ? 0:1, j = bloomeryLit ? moltenHeight + 7 : moltenHeight; j > 0; i++, j-=8)
			{
				Block bid = worldObj.getBlock(xCoord + direction[0], yCoord + i, zCoord + direction[1]);
				/*The stack must be air or already be molten rock*/
				if((bid == Blocks.air ||bid == TFCBlocks.Molten || bid == TFCBlocks.Charcoal) &&
						worldObj.getBlock(xCoord + direction[0], yCoord - 1, zCoord + direction[1]).getMaterial() == Material.rock)
				{
					//Make sure that the Stack is surrounded by rock
					if(isStackValid(xCoord + direction[0], yCoord+i, zCoord + direction[1]))
						validCount++;

					if(i <= validCount) 
					{
						int mMeta = worldObj.getBlockMetadata(xCoord, yCoord+i, zCoord);
						int m = j > 7 ? 7 : j;
						if(this.bloomeryLit)
						{
							if((bid == TFCBlocks.Molten && (mMeta & 8) == 0) || bid == Blocks.air || bid == TFCBlocks.Charcoal)
							{
								m += 8;
								worldObj.setBlock(xCoord + direction[0], yCoord + i, zCoord + direction[1], TFCBlocks.Molten, m, 2);
							}
						}
						else
						{
							if(count > 0)
								worldObj.setBlock(xCoord + direction[0], yCoord + i, zCoord + direction[1], TFCBlocks.Molten, m, 2);
							else
								worldObj.setBlockToAir(xCoord + direction[0], yCoord + i, zCoord + direction[1]);
						}
					}
					else
					{
						worldObj.setBlockToAir(xCoord + direction[0], yCoord+i, zCoord + direction[1]);
					}
				}
			}

			if(!bloomeryLit && worldObj.getBlock(xCoord + direction[0], yCoord, zCoord + direction[1]) == TFCBlocks.Bloom)
			{
				if(isStackValid(xCoord + direction[0], yCoord + 3, zCoord + direction[1]) && 
						isStackValid(xCoord + direction[0], yCoord + 2, zCoord + direction[1]) && 
						isStackValid(xCoord + direction[0], yCoord + 1, zCoord + direction[1]))
				{
					if(worldObj.getBlock(xCoord + direction[0], yCoord + 3, zCoord + direction[1]) == TFCBlocks.Molten)
						worldObj.setBlockToAir(xCoord + direction[0], yCoord + 3, zCoord + direction[1]);
				}

				if(isStackValid(xCoord + direction[0], yCoord + 2, zCoord + direction[1]) &&
						isStackValid(xCoord + direction[0], yCoord + 1, zCoord + direction[1]))
				{
					if(worldObj.getBlock(xCoord + direction[0], yCoord + 2, zCoord + direction[1]) == TFCBlocks.Molten)
						worldObj.setBlockToAir(xCoord + direction[0], yCoord + 2, zCoord + direction[1]);
				}

				if(isStackValid(xCoord + direction[0], yCoord + 1, zCoord + direction[1]))
				{
					if(worldObj.getBlock(xCoord + direction[0], yCoord + 1, zCoord + direction[1]) == TFCBlocks.Molten)
						worldObj.setBlockToAir(xCoord + direction[0], yCoord + 1, zCoord + direction[1]);
				}
			}

			if(moltenCount == 0)
				moltenCount = 1;

			/*Create a list of all the items that are falling into the stack */
			List list = worldObj.getEntitiesWithinAABB(EntityItem.class, AxisAlignedBB.getBoundingBox(
					xCoord + direction[0], yCoord, zCoord + direction[1], 
					xCoord + direction[0] + 1, yCoord+(maxCount/8) + 1.1, zCoord + direction[1] + 1));

			/*Make sure the list isn't null or empty and that the stack is valid 1 layer above the Molten Ore*/
			if (list != null && !list.isEmpty() && !bloomeryLit)
			{
				/*Iterate through the list and check for charcoal, coke, and ore*/
				for (Iterator iterator = list.iterator(); iterator.hasNext();)
				{
					EntityItem entity = (EntityItem)iterator.next();
					if(entity.getEntityItem().getItem() == Items.coal && entity.getEntityItem().getItemDamage() == 1 || entity.getEntityItem().getItem() == TFCItems.Coke)
					{
						for(int c = 0; c < entity.getEntityItem().stackSize; c++)
						{
							if(charcoalCount+oreCount < (2*maxCount) && charcoalCount < (maxCount))
							{
								charcoalCount++;
								entity.getEntityItem().stackSize--;
							}
						}
						if(entity.getEntityItem().stackSize == 0)
							entity.setDead();
					}
					/*If the item that's been tossed in is a type of Ore and it can melt down into something then add the ore to the list of items in the fire.*/
					else if(entity.getEntityItem().getItem() instanceof ItemOre && ((ItemOre)entity.getEntityItem().getItem()).isSmeltable(entity.getEntityItem()))
					{
						int c = entity.getEntityItem().stackSize;
						for(; c > 0; )
						{
							if(charcoalCount+oreCount < (2*maxCount) && oreCount < (maxCount) && outCount < 1000)
							{
								if(AddOreToFire(new ItemStack(entity.getEntityItem().getItem(), 1, entity.getEntityItem().getItemDamage())))
								{
									oreCount+=1;
									c--;
								}
								else
									break;
							}
							else
								break;
						}
						if(c == 0)
							entity.setDead();
						else
							entity.getEntityItem().stackSize = c;
					}
					else if(entity.getEntityItem().getItem() instanceof ISmeltable && 
							((ISmeltable)entity.getEntityItem().getItem()).isSmeltable(entity.getEntityItem()))
					{
						int c = entity.getEntityItem().stackSize;
						for(; c > 0; )
						{
							if(((ISmeltable)entity.getEntityItem().getItem()).GetMetalReturnAmount(entity.getEntityItem()) < 100 && outCount < 1000)
							{
								if(AddOreToFire(new ItemStack(entity.getEntityItem().getItem(), 1, entity.getEntityItem().getItemDamage()))) 
								{
									oreCount+=1;
									c--;
								}
								else
									break;
							}
							else
								break;
						}

						if(c == 0)
							entity.setDead();
						else
							entity.getEntityItem().stackSize = c;
					}
				}
			}
			//Here we make sure that the forge is valid
			if(this.validationCheck <= 0)
			{
				if(((BlockEarlyBloomery)worldObj.getBlock(xCoord, yCoord, zCoord)).canBlockStay(worldObj, xCoord, yCoord, zCoord))
					validationCheck = 600;
				else
					worldObj.setBlockToAir(xCoord, yCoord, zCoord);
			}
			else
				validationCheck--;
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("isFlipped", isFlipped);
		nbttagcompound.setLong("fuelTimeLeft", fuelTimeLeft);
		nbttagcompound.setInteger("charcoalCount", charcoalCount);
		nbttagcompound.setInteger("outCount", outCount);
		nbttagcompound.setInteger("oreCount", oreCount);
		nbttagcompound.setBoolean("isLit",bloomeryLit);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		isFlipped = nbttagcompound.getBoolean("isFlipped");
		fuelTimeLeft = nbttagcompound.getLong("fuelTimeLeft");
		charcoalCount = nbttagcompound.getInteger("charcoalCount");
		outCount = nbttagcompound.getInteger("outCount");
		oreCount = nbttagcompound.getInteger("oreCount");
		bloomeryLit = nbttagcompound.getBoolean("isLit");
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








//	@Override
//	public void createInitPacket(DataOutputStream outStream) throws IOException
//	{
//		outStream.writeBoolean(isFlipped);
//	}
//
//	@Override
//	public void handleInitPacket(DataInputStream inStream) throws IOException
//	{
//		isFlipped = inStream.readBoolean();
//		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//	}
}
