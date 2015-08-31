package com.bioxx.tfc.Items.Tools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.google.common.collect.Sets;

public class ItemCustomShovel extends ItemTerraTool
{
	/** an array of the blocks this spade is effective against */
	private static final Set<Block> BLOCKS_EFFECTIVE_AGAINST = Sets.newHashSet(new Block[]
	{
			Blocks.grass, Blocks.dirt, Blocks.sand, Blocks.gravel, Blocks.snow, Blocks.snow_layer,
			Blocks.clay, Blocks.farmland, Blocks.soul_sand, Blocks.mycelium,
			TFCBlocks.dirt, TFCBlocks.dirt2, TFCBlocks.grass, TFCBlocks.grass2, TFCBlocks.clayGrass,
			TFCBlocks.clayGrass2, TFCBlocks.peatGrass, TFCBlocks.peat, TFCBlocks.clay, TFCBlocks.clay2
	});

	public ItemCustomShovel(ToolMaterial par2EnumToolMaterial)
	{
		super(1.0F, par2EnumToolMaterial, BLOCKS_EFFECTIVE_AGAINST);
	}

	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	@Override
	public boolean func_150897_b/*canHarvestBlock*/(Block par1Block)
	{
		return par1Block == Blocks.snow_layer ? true : par1Block == Blocks.snow;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = this.getUnlocalizedName().replace("item.", "");
		name = name.replace("IgIn ", "");
		name = name.replace("IgEx ", "");
		name = name.replace("Sed ", "");
		name = name.replace("MM ", "");
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + "tools/" + name);
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.FAR;
	}
}
