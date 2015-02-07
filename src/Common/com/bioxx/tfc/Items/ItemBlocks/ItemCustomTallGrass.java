package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.Core.ColorizerFoliageTFC;
import com.bioxx.tfc.api.TFCBlocks;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomTallGrass extends ItemTerraBlock
{
	public ItemCustomTallGrass(Block b)
	{
		super(b);
		MetaNames = new String[] {"tallgrass", "fern", "shortgrass"};
	}

	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int par2)
	{
		return ColorizerFoliageTFC.getFoliageColorBasic();
	}

	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamage(int par1)
	{
		return TFCBlocks.TallGrass.getIcon(0, par1);
	}
}
