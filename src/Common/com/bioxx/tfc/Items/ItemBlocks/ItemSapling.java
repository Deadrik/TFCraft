package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemSapling extends ItemTerraBlock
{
	public ItemSapling(Block b)
	{
		super(b);
		this.MetaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, this.MetaNames, 0, 16);
		this.icons = new IIcon[MetaNames.length];
	}

	@Override
	public IIcon getIconFromDamage(int index)
	{
		return icons[index];
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < this.MetaNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/trees/" + this.MetaNames[i] + " Sapling");
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}
}
