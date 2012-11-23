package TFC.TileEntities;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.Blocks.BlockFruitLeaves;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Handlers.PacketHandler;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public class TileEntityFruitTreeWood extends TileEntity implements IInventory
{
	public boolean isTrunk;
	public int height;
	public long birthTimeWood;
	public long birthTimeLeaves;

	final long leafGrowthRate = 3;
	final long GrowTime = 30;
	final long branchGrowTime = 20;

	public TileEntityFruitTreeWood()
	{
		height = 0;
		isTrunk = false;
		birthTimeWood = 0;
		birthTimeLeaves = 0;
	}

	public void setBirth()
	{
		birthTimeWood = TFC_Time.totalDays();
		birthTimeLeaves = TFC_Time.totalDays();
	}
	public void setBirthWood(long t)
	{
		birthTimeWood += t;
	}
	public void setBirthLeaves(long t)
	{
		birthTimeLeaves += t;
	}

	public void setTrunk(boolean b)
	{
		isTrunk = b;
	}

	public void setHeight(int h)
	{
		height = h;
	}

	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			int blockID = TFCBlocks.fruitTreeWood.blockID;
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if(birthTimeWood + GrowTime < TFC_Time.totalDays() && height < 3)
			{
				float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(TFC_Time.getDayOfYearFromDays(birthTimeWood + GrowTime), xCoord, yCoord, zCoord);
				int t = 1;
				if(temp > 8 && temp < 22)
					t = 2;
				
				//First we attempt to grow the trunk of the tree higher
				if(height <= 2 && isTrunk && worldObj.rand.nextInt(16/t) == 0 &&
						(worldObj.getBlockId(xCoord, yCoord+1, zCoord) == 0 || worldObj.getBlockId(xCoord, yCoord+1, zCoord) == TFCBlocks.fruitTreeLeaves.blockID))
				{
					worldObj.setBlockAndMetadata(xCoord, yCoord+1, zCoord, TFCBlocks.fruitTreeWood.blockID, meta);
                    ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord)).setTrunk(true);
                    ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord)).setHeight(height+1);
                    ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord)).setBirthWood(GrowTime);

                    setBirthWood(GrowTime);
				}
				//Otherwise we try to grow the branches outward
				else if(height == 2 && isTrunk && worldObj.rand.nextInt(16/t) == 0)
	            {
					int r = worldObj.rand.nextInt(4);
                    if(r == 0 && worldObj.getBlockId(xCoord+1, yCoord, zCoord) == 0 || worldObj.getBlockId(xCoord+1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                    	worldObj.setBlockAndMetadata(xCoord+1, yCoord, zCoord, TFCBlocks.fruitTreeWood.blockID, meta);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord)).setTrunk(false);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord)).setHeight(height);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord)).setBirth();
                    }
                    else if(r == 1 && worldObj.getBlockId(xCoord, yCoord, zCoord-1) == 0 || worldObj.getBlockId(xCoord, yCoord, zCoord-1) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                    	worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord-1, TFCBlocks.fruitTreeWood.blockID, meta);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord-1)).setTrunk(false);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord-1)).setHeight(height);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord-1)).setBirth();
                    }
                    else if(r == 2 && worldObj.getBlockId(xCoord-1, yCoord, zCoord) == 0 || worldObj.getBlockId(xCoord-1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                    	worldObj.setBlockAndMetadata(xCoord-1, yCoord, zCoord, TFCBlocks.fruitTreeWood.blockID, meta);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord-1, yCoord, zCoord)).setTrunk(false);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord-1, yCoord, zCoord)).setHeight(height);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord-1, yCoord, zCoord)).setBirth();
                    }
                    else if(r == 3 && worldObj.getBlockId(xCoord, yCoord, zCoord+1) == 0 || worldObj.getBlockId(xCoord, yCoord, zCoord+1) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                    	worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord+1, TFCBlocks.fruitTreeWood.blockID, meta);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1)).setTrunk(false);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1)).setHeight(height);
                        ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1)).setBirth();
                    }

                    ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord)).setBirthWood(branchGrowTime);
	            }
			}
			
			if(birthTimeLeaves + 2 < TFC_Time.totalDays() && worldObj.rand.nextInt((int) leafGrowthRate) == 0 && worldObj.getBlockId(xCoord, yCoord+2, zCoord) != TFCBlocks.fruitTreeWood.blockID)
            {
                if(worldObj.getBlockId(xCoord, yCoord+1, zCoord) == 0 && worldObj.getBlockId(xCoord, yCoord+2, zCoord) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord+1, zCoord, TFCBlocks.fruitTreeLeaves.blockID))//above
                {
                    worldObj.setBlockAndMetadata(xCoord, yCoord+1, zCoord, TFCBlocks.fruitTreeLeaves.blockID, meta);
                    worldObj.markBlockForUpdate(xCoord, yCoord+1, zCoord);
                }
                else if(worldObj.getBlockId(xCoord+1, yCoord, zCoord) == 0 && worldObj.getBlockId(xCoord+1, yCoord+1, zCoord) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord+1, yCoord, zCoord, TFCBlocks.fruitTreeLeaves.blockID))//+x
                {
                    worldObj.setBlockAndMetadata(xCoord+1, yCoord, zCoord, TFCBlocks.fruitTreeLeaves.blockID, meta);
                    worldObj.markBlockForUpdate(xCoord+1, yCoord, zCoord);
                }
                else if(worldObj.getBlockId(xCoord-1, yCoord, zCoord) == 0 && worldObj.getBlockId(xCoord-1, yCoord+1, zCoord) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord-1, yCoord, zCoord, TFCBlocks.fruitTreeLeaves.blockID))//-x
                {
                    worldObj.setBlockAndMetadata(xCoord-1, yCoord, zCoord, TFCBlocks.fruitTreeLeaves.blockID, meta);
                    worldObj.markBlockForUpdate(xCoord-1, yCoord, zCoord);
                }
                else if(worldObj.getBlockId(xCoord, yCoord, zCoord+1) == 0 && worldObj.getBlockId(xCoord, yCoord+1, zCoord+1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord, zCoord+1, TFCBlocks.fruitTreeLeaves.blockID))//+z
                {
                    worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord+1, TFCBlocks.fruitTreeLeaves.blockID, meta);
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord+1);
                }
                else if(worldObj.getBlockId(xCoord, yCoord, zCoord-1) == 0 && worldObj.getBlockId(xCoord, yCoord+1, zCoord-1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord, zCoord-1, TFCBlocks.fruitTreeLeaves.blockID))//-z
                {
                    worldObj.setBlockAndMetadata(xCoord, yCoord, zCoord-1, TFCBlocks.fruitTreeLeaves.blockID, meta);
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord-1);
                }
                else if(worldObj.getBlockId(xCoord+1, yCoord, zCoord-1) == 0 && worldObj.getBlockId(xCoord+1, yCoord+1, zCoord-1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord+1, yCoord, zCoord-1, TFCBlocks.fruitTreeLeaves.blockID))//+x/-z
                {
                    worldObj.setBlockAndMetadata(xCoord+1, yCoord, zCoord-1, TFCBlocks.fruitTreeLeaves.blockID, meta);
                    worldObj.markBlockForUpdate(xCoord+1, yCoord, zCoord-1);
                }
                else if(worldObj.getBlockId(xCoord+1, yCoord, zCoord+1) == 0 && worldObj.getBlockId(xCoord+1, yCoord+1, zCoord+1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord+1, yCoord, zCoord+1, TFCBlocks.fruitTreeLeaves.blockID))//+x/+z
                {
                    worldObj.setBlockAndMetadata(xCoord+1, yCoord, zCoord+1, TFCBlocks.fruitTreeLeaves.blockID, meta);
                    worldObj.markBlockForUpdate(xCoord+1, yCoord, zCoord+1);
                }
                else if(worldObj.getBlockId(xCoord-1, yCoord, zCoord-1) == 0 && worldObj.getBlockId(xCoord-1, yCoord+1, zCoord-1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord-1, yCoord, zCoord-1, TFCBlocks.fruitTreeLeaves.blockID))//-x/-z
                {
                    worldObj.setBlockAndMetadata(xCoord-1, yCoord, zCoord-1, TFCBlocks.fruitTreeLeaves.blockID, meta);
                    worldObj.markBlockForUpdate(xCoord-1, yCoord, zCoord-1);
                }
                else if(worldObj.getBlockId(xCoord-1, yCoord, zCoord+1) == 0 && worldObj.getBlockId(xCoord-1, yCoord+1, zCoord+1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord-1, yCoord, zCoord+1, TFCBlocks.fruitTreeLeaves.blockID))//-x/+z
                {
                    worldObj.setBlockAndMetadata(xCoord-1, yCoord, zCoord+1, TFCBlocks.fruitTreeLeaves.blockID, meta);
                    worldObj.markBlockForUpdate(xCoord-1, yCoord, zCoord+1);
                }
                setBirthLeaves(2);
            }
		}
	}

	@Override
	public void closeChest() 
	{

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
		return "Fruit Tree Wood";
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
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

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		birthTimeWood = nbttagcompound.getLong("birthTime");
		birthTimeLeaves = nbttagcompound.getLong("birthTimeLeaves");
		isTrunk = nbttagcompound.getBoolean("isTrunk");
		height = nbttagcompound.getInteger("height");
	}


	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setLong("birthTime", birthTimeWood);
		nbttagcompound.setLong("birthTimeLeaves", birthTimeLeaves);
		nbttagcompound.setBoolean("isTrunk", isTrunk);
		nbttagcompound.setInteger("height", height);
	}

	public void handlePacketData() 
	{
		TileEntityFruitTreeWood pile = this;
	}

	@Override
	public int getSizeInventory()
	{
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public ItemStack getStackInSlot(int var1)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ItemStack decrStackSize(int var1, int var2)
	{
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setInventorySlotContents(int var1, ItemStack var2)
	{
		// TODO Auto-generated method stub

	}
}
