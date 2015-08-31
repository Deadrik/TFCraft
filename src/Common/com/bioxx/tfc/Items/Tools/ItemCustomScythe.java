package com.bioxx.tfc.Items.Tools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.google.common.collect.Sets;

public class ItemCustomScythe extends ItemTerraTool
{
	private static final Set<Block> BLOCKS = Sets.newHashSet(new Block[]
	{ TFCBlocks.leaves, TFCBlocks.leaves2 });

	public ItemCustomScythe(ToolMaterial e)
	{
		super((int)-(e.getDamageVsEntity()*0.3f),e, BLOCKS);
		this.setMaxDamage(e.getMaxUses()*3);
//		this.damageVsEntity = e.getDamageVsEntity();
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial();
		setCreativeTab(TFCTabs.TFC_TOOLS);
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		return true;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.FAR;
	}
}