package TFC.TileEntities;

import TFC.Core.TFC_ItemHeat;
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
import net.minecraft.world.gen.feature.*;

public class TileEntityWorkbench extends TileEntity implements IInventory
{
	public ItemStack[] craftingMatrix;

	public TileEntityWorkbench()
	{
		craftingMatrix = new ItemStack[9];
	}

	@Override
	public void closeChest() {


	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(craftingMatrix[i] != null)
		{
			if(craftingMatrix[i].stackSize <= j)
			{
				ItemStack itemstack = craftingMatrix[i];
				craftingMatrix[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = craftingMatrix[i].splitStack(j);
			if(craftingMatrix[i].stackSize == 0)
			{
				craftingMatrix[i] = null;
			}
			return itemstack1;
		} else
		{
			return null;
		}
	}

	@Override
	public int getInventoryStackLimit()
	{
		// TODO Auto-generated method stub
		return 64;
	}

	@Override
	public String getInvName()
	{
		return "Workbench";
	}

	public int getSizeInventory()
	{
		return craftingMatrix.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		return craftingMatrix[i];
	}


	@Override
	public ItemStack getStackInSlotOnClosing(int var1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer entityplayer) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public void openChest() {
		// TODO Auto-generated method stub

	}

	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		craftingMatrix[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public void updateEntity()
	{
		TFC_ItemHeat.HandleContainerHeat(this.worldObj,craftingMatrix, (int)xCoord,(int)yCoord,(int)zCoord);
	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
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
