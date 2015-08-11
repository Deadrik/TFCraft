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
	private IIcon field_94385_a;
	@SideOnly(Side.CLIENT)
	private IIcon field_94384_b;

	public BlockWorkbench()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFCDevices);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return par1 == 1 ? this.field_94385_a : (par1 == 0 ? TFCBlocks.Planks.getBlockTextureFromSide(par1) : (par1 != 2 && par1 != 4 ? this.blockIcon : this.field_94384_b));
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.blockIcon = par1IconRegister.registerIcon("crafting_table_side");
		this.field_94385_a = par1IconRegister.registerIcon("crafting_table_top");
		this.field_94384_b = par1IconRegister.registerIcon("crafting_table_front");
	}

}
