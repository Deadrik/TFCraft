package com.bioxx.tfc.Blocks.Flora;

import java.util.List;

import com.bioxx.tfc.api.Constant.Global;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogHoriz2 extends BlockLogVert2
{
	int offset = 0;
	String[] woodNames;

	public BlockLogHoriz2(int off)
	{
		super();
		offset = off;
		woodNames = new String[Global.WOOD_ALL.length - 16];
		if(16+off < Global.WOOD_ALL.length)
			System.arraycopy(Global.WOOD_ALL, 16 + off, woodNames, 0, Global.WOOD_ALL.length-16>off?off:Global.WOOD_ALL.length-16);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		super.registerBlockIcons(iconRegisterer);
	}

	@Override
	public IIcon getIcon(int i, int j) 
	{
		int meta = (j & 7) + offset;
		int dir = j >> 3;

		if(dir == 0)
		{
			if(i == 0) {
				return BlockLogNatural2.sideIcons[meta];
			} else if(i == 1) {
				return BlockLogNatural2.sideIcons[meta];
			} else if(i == 2) {
				return BlockLogNatural2.innerIcons[meta];
			} else if(i == 3) {
				return BlockLogNatural2.innerIcons[meta];
			} else if(i == 4) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else {
				return BlockLogNatural2.rotatedSideIcons[meta];
			}
		}
		else
		{
			if(i == 0) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else if(i == 1) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else if(i == 2) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else if(i == 3) {
				return BlockLogNatural2.rotatedSideIcons[meta];
			} else if(i == 4) {
				return BlockLogNatural2.innerIcons[meta];
			} else {
				return BlockLogNatural2.innerIcons[meta];
			}
		}
	}

	@Override
	public int damageDropped(int j)
	{
		return (j & 7) + offset + 16;
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	 public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < (woodNames.length+1)/2; i++) 
			list.add(new ItemStack(this,1,i));
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) 
	{
		int dir = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		int metadata = world.getBlockMetadata(i, j, k);

		if(dir == 1 || dir == 3)
			world.setBlockMetadataWithNotify(i, j, k, metadata+8, 3);

		metadata = world.getBlockMetadata(i, j, k);
	}
}
