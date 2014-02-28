package TFC.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import TFC.Core.TFC_ItemHeat;

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
				craftingMatrix[i] = null;
			return itemstack1;
		} else
			return null;
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

	@Override
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

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack) 
	{
		craftingMatrix[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
			itemstack.stackSize = getInventoryStackLimit();
	}

	@Override
	public void updateEntity()
	{
		TFC_ItemHeat.HandleContainerHeat(this.worldObj,craftingMatrix);
	}

	@Override
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
