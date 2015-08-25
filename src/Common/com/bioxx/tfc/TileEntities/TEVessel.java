package com.bioxx.tfc.TileEntities;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TerraFirmaCraft;

public class TEVessel extends TEBarrel implements IInventory
{
	public TEVessel()
	{
		storage = new ItemStack[9];
	}

	@Override
	public int getTechLevel()
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
	@SideOnly(Side.CLIENT)
	public AxisAlignedBB getRenderBoundingBox()
	{
		return AxisAlignedBB.getBoundingBox(xCoord, yCoord, zCoord, xCoord + 1, yCoord + 1, zCoord + 1);
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
