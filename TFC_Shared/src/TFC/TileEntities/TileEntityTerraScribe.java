package TFC.TileEntities;

import java.util.HashMap;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.TileEntity;

public class TileEntityTerraScribe extends TileEntity implements IInventory
{
	public static HashMap Recipes;
	public ItemStack scribeItemStacks[];

	static
	{

	}

	public TileEntityTerraScribe()
	{
		scribeItemStacks = new ItemStack[2];
	}

	@Override
	public void closeChest() {


	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(scribeItemStacks[i] != null)
		{
			if(scribeItemStacks[i].stackSize <= j)
			{
				ItemStack itemstack = scribeItemStacks[i];
				scribeItemStacks[i] = null;
				return itemstack;
			}
			ItemStack itemstack1 = scribeItemStacks[i].splitStack(j);
			if(scribeItemStacks[i].stackSize == 0)
			{
				scribeItemStacks[i] = null;
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
		return 1;
	}

	@Override
	public String getInvName()
	{
		return "Scribe";
	}

	public int getSizeInventory()
	{
		return scribeItemStacks.length;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		// TODO Auto-generated method stub
		return scribeItemStacks[i];
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
		//timeleft = nbttagcompound.getInteger("timeleft");

		NBTTagList nbttaglist = nbttagcompound.getTagList("Items");
		scribeItemStacks = new ItemStack[getSizeInventory()];
		for(int i = 0; i < nbttaglist.tagCount(); i++)
		{
			NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
			byte byte0 = nbttagcompound1.getByte("Slot");
			if(byte0 >= 0 && byte0 < scribeItemStacks.length)
			{
				scribeItemStacks[byte0] = ItemStack.loadItemStackFromNBT(nbttagcompound1);
			}
		}
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		scribeItemStacks[i] = itemstack;
		if(itemstack != null && itemstack.stackSize > getInventoryStackLimit())
		{
			itemstack.stackSize = getInventoryStackLimit();
		}
	}

	public void updateEntity()
	{


	}

	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		//nbttagcompound.setInteger("timeleft", timeleft);

		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < scribeItemStacks.length; i++)
		{
			if(scribeItemStacks[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				scribeItemStacks[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbttagcompound.setTag("Items", nbttaglist);


	}

}
