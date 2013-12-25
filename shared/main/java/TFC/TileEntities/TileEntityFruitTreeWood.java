package TFC.TileEntities;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.Blocks.BlockFruitLeaves;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Handlers.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

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
		birthTimeWood = TFC_Time.getTotalDays();
		birthTimeLeaves = TFC_Time.getTotalDays();
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
	
	public void setup(boolean isTrunk, int h, long growTime)
	{
		setTrunk(isTrunk);
		setHeight(h);
		setBirthWood(growTime);
	}
	
	public void setupBirth(boolean isTrunk, int h)
	{
		setTrunk(isTrunk);
		setHeight(h);
		setBirth();
	}

	public void updateEntity()
	{
		if(!worldObj.isRemote)
		{
			int blockID = TFCBlocks.fruitTreeWood.blockID;
			int meta = worldObj.getBlockMetadata(xCoord, yCoord, zCoord);
			if(birthTimeWood + GrowTime < TFC_Time.getTotalDays() && height < 3)
			{
				float temp = TFC_Climate.getHeightAdjustedTempSpecificDay(TFC_Time.getDayOfYearFromDays(birthTimeWood + GrowTime), xCoord, yCoord, zCoord);
				int t = 1;
				if(temp > 8 && temp < 22)
					t = 2;
				
				//First we attempt to grow the trunk of the tree higher
				if(height <= 2 && isTrunk && worldObj.rand.nextInt(16/t) == 0 &&
						(worldObj.getBlockId(xCoord, yCoord+1, zCoord) == 0 || 
						worldObj.getBlockId(xCoord, yCoord+1, zCoord) == TFCBlocks.fruitTreeLeaves.blockID || worldObj.getBlockId(xCoord, yCoord+1, zCoord) == TFCBlocks.fruitTreeLeaves2.blockID))
				{
					worldObj.setBlock(xCoord, yCoord+1, zCoord, TFCBlocks.fruitTreeWood.blockID, meta, 0x2);
					((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord+1, zCoord)).setup(true, height+1, GrowTime);

                    setBirthWood(GrowTime);
				}
				//Otherwise we try to grow the branches outward
				else if(height == 2 && isTrunk && worldObj.rand.nextInt(16/t) == 0)
	            {
					int r = worldObj.rand.nextInt(4);
                    if(r == 0 && BlockSand.canFallBelow(worldObj, xCoord+1, yCoord, zCoord) || worldObj.getBlockId(xCoord+1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                    	worldObj.setBlock(xCoord+1, yCoord, zCoord, TFCBlocks.fruitTreeWood.blockID, meta, 0x2);
                    	((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord+1, yCoord, zCoord)).setupBirth(false, height);
                    }
                    else if(r == 1 && BlockSand.canFallBelow(worldObj, xCoord, yCoord, zCoord-1) || worldObj.getBlockId(xCoord, yCoord, zCoord-1) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                    	worldObj.setBlock(xCoord, yCoord, zCoord-1, TFCBlocks.fruitTreeWood.blockID, meta, 0x2);
                    	((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord-1)).setupBirth(false, height);
                    }
                    else if(r == 2 && BlockSand.canFallBelow(worldObj, xCoord-1, yCoord, zCoord) || worldObj.getBlockId(xCoord-1, yCoord, zCoord) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                    	worldObj.setBlock(xCoord-1, yCoord, zCoord, TFCBlocks.fruitTreeWood.blockID, meta, 0x2);
                    	((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord-1, yCoord, zCoord)).setupBirth(false, height);
                    }
                    else if(r == 3 && BlockSand.canFallBelow(worldObj, xCoord, yCoord, zCoord+1) || worldObj.getBlockId(xCoord, yCoord, zCoord+1) == TFCBlocks.fruitTreeLeaves.blockID)
                    {
                    	worldObj.setBlock(xCoord, yCoord, zCoord+1, TFCBlocks.fruitTreeWood.blockID, meta, 0x2);
                    	((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord+1)).setupBirth(false, height);
                    }

                    ((TileEntityFruitTreeWood)worldObj.getBlockTileEntity(xCoord, yCoord, zCoord)).setBirthWood(branchGrowTime);
	            }
			}
			
			if(birthTimeLeaves + 2 < TFC_Time.getTotalDays() && worldObj.rand.nextInt((int) leafGrowthRate) == 0 && worldObj.getBlockId(xCoord, yCoord+2, zCoord) != TFCBlocks.fruitTreeWood.blockID)
            {
				int m = meta & 7;
				int bid = meta < 8 ? TFCBlocks.fruitTreeLeaves.blockID : TFCBlocks.fruitTreeLeaves2.blockID;
				
                if(worldObj.getBlockId(xCoord, yCoord+1, zCoord) == 0 && worldObj.getBlockId(xCoord, yCoord+2, zCoord) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord+1, zCoord, TFCBlocks.fruitTreeLeaves.blockID))//above
                {
                    worldObj.setBlock(xCoord, yCoord+1, zCoord, bid, m, 0x2);
                    worldObj.markBlockForUpdate(xCoord, yCoord+1, zCoord);
                }
                else if(worldObj.getBlockId(xCoord+1, yCoord, zCoord) == 0 && worldObj.getBlockId(xCoord+1, yCoord+1, zCoord) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord+1, yCoord, zCoord, TFCBlocks.fruitTreeLeaves.blockID))//+x
                {
                    worldObj.setBlock(xCoord+1, yCoord, zCoord, bid, m, 0x2);
                    worldObj.markBlockForUpdate(xCoord+1, yCoord, zCoord);
                }
                else if(worldObj.getBlockId(xCoord-1, yCoord, zCoord) == 0 && worldObj.getBlockId(xCoord-1, yCoord+1, zCoord) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord-1, yCoord, zCoord, TFCBlocks.fruitTreeLeaves.blockID))//-x
                {
                    worldObj.setBlock(xCoord-1, yCoord, zCoord, bid, m, 0x2);
                    worldObj.markBlockForUpdate(xCoord-1, yCoord, zCoord);
                }
                else if(worldObj.getBlockId(xCoord, yCoord, zCoord+1) == 0 && worldObj.getBlockId(xCoord, yCoord+1, zCoord+1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord, zCoord+1, TFCBlocks.fruitTreeLeaves.blockID))//+z
                {
                    worldObj.setBlock(xCoord, yCoord, zCoord+1, bid, m, 0x2);
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord+1);
                }
                else if(worldObj.getBlockId(xCoord, yCoord, zCoord-1) == 0 && worldObj.getBlockId(xCoord, yCoord+1, zCoord-1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord, yCoord, zCoord-1, TFCBlocks.fruitTreeLeaves.blockID))//-z
                {
                    worldObj.setBlock(xCoord, yCoord, zCoord-1, bid, m, 0x2);
                    worldObj.markBlockForUpdate(xCoord, yCoord, zCoord-1);
                }
                else if(worldObj.getBlockId(xCoord+1, yCoord, zCoord-1) == 0 && worldObj.getBlockId(xCoord+1, yCoord+1, zCoord-1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord+1, yCoord, zCoord-1, TFCBlocks.fruitTreeLeaves.blockID))//+x/-z
                {
                    worldObj.setBlock(xCoord+1, yCoord, zCoord-1, bid, m, 0x2);
                    worldObj.markBlockForUpdate(xCoord+1, yCoord, zCoord-1);
                }
                else if(worldObj.getBlockId(xCoord+1, yCoord, zCoord+1) == 0 && worldObj.getBlockId(xCoord+1, yCoord+1, zCoord+1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord+1, yCoord, zCoord+1, TFCBlocks.fruitTreeLeaves.blockID))//+x/+z
                {
                    worldObj.setBlock(xCoord+1, yCoord, zCoord+1, bid, m, 0x2);
                    worldObj.markBlockForUpdate(xCoord+1, yCoord, zCoord+1);
                }
                else if(worldObj.getBlockId(xCoord-1, yCoord, zCoord-1) == 0 && worldObj.getBlockId(xCoord-1, yCoord+1, zCoord-1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord-1, yCoord, zCoord-1, TFCBlocks.fruitTreeLeaves.blockID))//-x/-z
                {
                    worldObj.setBlock(xCoord-1, yCoord, zCoord-1, bid, m, 0x2);
                    worldObj.markBlockForUpdate(xCoord-1, yCoord, zCoord-1);
                }
                else if(worldObj.getBlockId(xCoord-1, yCoord, zCoord+1) == 0 && worldObj.getBlockId(xCoord-1, yCoord+1, zCoord+1) == 0 && BlockFruitLeaves.canStay(worldObj, xCoord-1, yCoord, zCoord+1, TFCBlocks.fruitTreeLeaves.blockID))//-x/+z
                {
                    worldObj.setBlock(xCoord-1, yCoord, zCoord+1, bid, m, 0x2);
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
