package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.ColorizerFoliageTFC;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemVine extends ItemTerraBlock
{
	public ItemVine(Block b)
	{
		super(b);
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int par2)
	{
		return ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return TFCBlocks.Vine.getIcon(0, 0);
	}
}
