package com.bioxx.tfc.TileEntities;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

public class TELeatherRack extends NetworkTileEntity
{
	ItemStack leatherItem;

	public void setLeather(ItemStack is)
	{
		leatherItem = is;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt)
	{
		super.writeToNBT(nbt);
		NBTTagCompound item = new NBTTagCompound();
		if(leatherItem != null)
		{
			leatherItem.writeToNBT(item);
			nbt.setTag("leatherItem", item);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		if(nbt.hasKey("leatherItem"))
			leatherItem = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbt.getTag("leatherItem"));
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		if(nbt.hasKey("leatherItem"))
			leatherItem = ItemStack.loadItemStackFromNBT((NBTTagCompound)nbt.getTag("leatherItem"));
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		NBTTagCompound item = new NBTTagCompound();
		if(leatherItem != null)
		{
			leatherItem.writeToNBT(item);
			nbt.setTag("leatherItem", item);
		}
	}

}
