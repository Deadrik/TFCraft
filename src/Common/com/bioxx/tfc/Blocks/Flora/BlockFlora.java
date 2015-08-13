package com.bioxx.tfc.Blocks.Flora;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Reference;

public class BlockFlora extends BlockTerra 
{
	IIcon[] icons;
	String[] MetaNames;

	public BlockFlora()
	{
		super(Material.plants);
		MetaNames = new String[]{"Golden Rod", "Cat Tails"};
		icons = new IIcon[MetaNames.length];
		this.setBlockBounds(0.3f, 0.0f, 0.3f, 0.7f, 0.7f, 0.7f);
		this.setCreativeTab(TFCTabs.TFCDecoration);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return 1;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		for (int i = 0; i < icons.length; ++i)
			icons[i] = par1IconRegister.registerIcon(Reference.ModID + ":" + "plants/"+MetaNames[i]);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		if (par2 >= icons.length)
			par2 = 0;
		return icons[par2];
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return (world.getFullBlockLightValue(x, y, z) >= 8 || 
				world.canBlockSeeTheSky(x, y, z)) && 
				this.canThisPlantGrowOnThisBlock(world.getBlock(x, y - 1, z));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block par5)
	{
		super.onNeighborBlockChange(world, i, j, k, par5);
		if(!canBlockStay(world,i,j,k))
			world.setBlockToAir(i, j, k);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);
		return (world.isAirBlock(x, y, z) || block.getMaterial().isReplaceable()) && this.canThisPlantGrowOnThisBlock(world.getBlock(x, y - 1, z));
	}

	protected boolean canThisPlantGrowOnThisBlock(Block block)
	{
		return TFC_Core.isSoil(block) || TFC_Core.isFarmland(block);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemFromBlock(this);
	}

}
