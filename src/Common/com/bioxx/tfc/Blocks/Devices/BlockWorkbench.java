package com.bioxx.tfc.Blocks.Devices;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockWorkbench extends BlockTerra
{
	@SideOnly(Side.CLIENT)
	private IIcon topIcon;
	@SideOnly(Side.CLIENT)
	private IIcon frontIcon;

	public BlockWorkbench()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		if (par1 == 1)
			return this.topIcon;
		else if (par1 == 0)
			return TFCBlocks.planks.getBlockTextureFromSide(par1);
		else if (par1 != 2 && par1 != 4)
			return this.blockIcon;
		else
			return this.frontIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("crafting_table_side");
		this.topIcon = par1IconRegister.registerIcon("crafting_table_top");
		this.frontIcon = par1IconRegister.registerIcon("crafting_table_front");
	}

}
