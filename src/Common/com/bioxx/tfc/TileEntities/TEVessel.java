package com.bioxx.tfc.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.TerraFirmaCraft;

public class TEVessel extends TEBarrel implements IInventory
{
	public TEVessel()
	{
		storage = new ItemStack[9];
	}

	@Override
	protected int getTechLevel()
	{
		return 0;
	}

	@Override
	public int getMaxLiquid()
	{
		return 5000;
	}

	@Override
	public int getSizeInventory()
	{
		return 9;
	}

	@Override
	protected void switchTab(EntityPlayer player, int tab)
	{
		if(player != null)
			if(tab == 0)
				player.openGui(TerraFirmaCraft.instance, 46, worldObj, xCoord, yCoord, zCoord);
			else if(tab == 1)
				player.openGui(TerraFirmaCraft.instance, 47, worldObj, xCoord, yCoord, zCoord);
	}
}
