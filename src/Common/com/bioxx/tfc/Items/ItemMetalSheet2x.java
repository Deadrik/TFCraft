package com.bioxx.tfc.Items;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ItemMetalSheet2x extends ItemMetalSheet
{
	public ItemMetalSheet2x(int mID)
	{
		super(mID);
		setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFCMaterials);
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.MEDIUM);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}
}
