package com.bioxx.tfc.Blocks.Terrain;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFCTabs;

public class BlockPeat extends BlockTerra
{
	public BlockPeat()
	{
		super(Material.ground);
		this.setCreativeTab(TFCTabs.TFC_BUILDING);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		blockIcon = registerer.registerIcon(Reference.MOD_ID + ":" + "soil/Peat");
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return false;
	}

	/**
	 * Displays a flat icon image for an ItemStack containing the block, instead of a render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return Reference.MOD_ID + ":" + "peat";
	}
}
