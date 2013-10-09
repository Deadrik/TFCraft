package TFC.TileEntities;

import java.util.Iterator;
import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
	public boolean isValid;
	public boolean bloomeryLit;

	private int prevStackSize;
	private int numAirBlocks;

	//Bloomery
	public int charcoalCount;
	public long fuelTimeLeft;
	public int oreCount;
	public int outCount;

	public TileEntityEarlyBloomery()
	{
		isValid = false;
		bloomeryLit = false;
		numAirBlocks = 0;
		charcoalCount = 0;
		oreCount = 0;
		outCount = 0;
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
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord) & 3;
			int[] direction = BlockEarlyBloomery.headBlockToFootBlockMap[meta];

			if (this.charcoalCount < this.oreCount) 
			{
				return false;
			}

			if(worldObj.getBlockId(xCoord+direction[0], yCoord, zCoord+direction[1])==TFCBlocks.Charcoal.blockID && 
					worldObj.getBlockMetadata(xCoord+direction[0], yCoord, zCoord+direction[1]) >= 7 && !bloomeryLit)
			{
				bloomeryLit = true;
				this.fuelTimeLeft = TFC_Time.getTotalTicks() + 14400;
				if((meta & 4) == 0) 
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta + 4, 3);
				}
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			//get the direction that the bloomery is facing so that we know where the stack should be
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			int[] direction = BlockEarlyBloomery.headBlockToFootBlockMap[meta & 3];
			if(bloomeryLit && TFC_Time.getTotalTicks() > fuelTimeLeft)
			{
				if((worldObj.getBlockId(xCoord+direction[0], yCoord, zCoord+direction[1])==TFCBlocks.Charcoal.blockID))
				{
					bloomeryLit = false;
					worldObj.setBlock(xCoord+direction[0], yCoord, zCoord+direction[1], TFCBlocks.Bloom.blockID);

					oreCount = 0;
					charcoalCount = 0;
					((TileEntityBloom)(worldObj.getBlockTileEntity(xCoord+direction[0], yCoord, zCoord+direction[1]))).setSize(outCount);
					outCount = 0;
				}
				if((meta & 4) != 0) 
				{
					worldObj.setBlockMetadataWithNotify(xCoord, yCoord, zCoord, meta & 3, 3);
				}
			}

			if(outCount < 0) {
				outCount = 0;
			}
			if(oreCount < 0) {
				oreCount = 0;
			}
			if(charcoalCount < 0) {
				charcoalCount = 0;
			}


			//Do the funky math to find how many molten blocks should be placed
			int count = charcoalCount+oreCount;

			int moltenCount = count > 0 && count < 8 ? 1 : count / 8;

			int validCount = 0;

			/*Fill the bloomery stack with molten ore. */
			for (int i = 1; i < moltenCount; i++)
			{
				/*The stack must be air or already be molten rock*/
				if((worldObj.getBlockId(xCoord+direction[0], yCoord+i, zCoord+direction[1]) == 0 ||
						worldObj.getBlockId(xCoord+direction[0], yCoord+i, zCoord+direction[1]) == TFCBlocks.Molten.blockID) &&
						worldObj.getBlockMaterial(xCoord+direction[0], yCoord-1, zCoord+direction[1]) == Material.rock)
				{
					//Make sure that the Stack is surrounded by rock
					if(isStackValid(xCoord+direction[0], yCoord+i, zCoord+direction[1])) {
						validCount++;
					}

					if(i-1 < moltenCount && i <= validCount) 
					{
						if(this.bloomeryLit)
						{
							worldObj.setBlock(xCoord+direction[0], yCoord+i, zCoord+direction[1], TFCBlocks.Molten.blockID, 15, 2);
						} else {
							worldObj.setBlock(xCoord+direction[0], yCoord+i, zCoord+direction[1], TFCBlocks.Molten.blockID, 0, 2);
						}
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

			if(moltenCount == 0) {
				moltenCount = 1;
			}
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
							if(charcoalCount+oreCount < 32 && charcoalCount < 16)
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
					else if(entity.getEntityItem().getItem() instanceof ItemOre && ((ItemOre)entity.getEntityItem().getItem()).isSmeltable(entity.getEntityItem()))
					{
						int c = entity.getEntityItem().stackSize;
						for(; c > 0; )
						{
							if(charcoalCount+oreCount < 32 && oreCount < 16 && outCount < 1000)
							{
								if(AddOreToFire(new ItemStack(entity.getEntityItem().getItem(),1,entity.getEntityItem().getItemDamage()))) 
								{
									oreCount+=1;
									c--;
								} else {
									break;
								}
							} else {
								break;
							}
						}
						if(c == 0) {
							entity.setDead();
						} else {
							entity.getEntityItem().stackSize = c;
						} 
					}
					else if(entity.getEntityItem().getItem() instanceof ISmeltable && 
							((ISmeltable)entity.getEntityItem().getItem()).isSmeltable(entity.getEntityItem()))
					{
						int c = entity.getEntityItem().stackSize;
						for(; c > 0; )
						{
							if(((ISmeltable)entity.getEntityItem().getItem()).GetMetalReturnAmount(entity.getEntityItem()) < 100 && outCount < 1000)
							{
								if(AddOreToFire(new ItemStack(entity.getEntityItem().getItem(),1,entity.getEntityItem().getItemDamage()))) 
								{
									oreCount+=1;
									c--;
								} else {
									break;
								}
							} else {
								break;
							}
						}
						if(c == 0) {
							entity.setDead();
						} else {
							entity.getEntityItem().stackSize = c;
						} 
					}
				}
			}

			//Here we make sure that the forge is valid
			isValid = CheckValidity();
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("isValid", isValid);
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
		isValid = nbttagcompound.getBoolean("isValid");
		fuelTimeLeft = nbttagcompound.getLong("fuelTimeLeft");
		charcoalCount = nbttagcompound.getInteger("charcoalCount");
		outCount = nbttagcompound.getInteger("outCount");
		oreCount = nbttagcompound.getInteger("oreCount");
		bloomeryLit = nbttagcompound.getBoolean("isLit");
	}
}
