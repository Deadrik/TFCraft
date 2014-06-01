package com.bioxx.tfc.Blocks.Vanilla;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWall;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomWall extends BlockWall
{
	int totalsubTypes = 0;
	Block block;

	public BlockCustomWall(Block blk, int t)
	{
		super(blk);
		this.block = blk;
		totalsubTypes = t;
		this.setCreativeTab(TFCTabs.TFCBuilding);
	}

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return block.getIcon(par1, par2);
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is) 
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(i, j, k);
			System.out.println("Meta="+(new StringBuilder()).append(this.getUnlocalizedName()).append(":").append(metadata).toString());
		}
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)  
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			System.out.println("Meta = "+(new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 32;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < totalsubTypes; i++) {
			par3List.add(new ItemStack(par1, 1, i));
		}
	}

	/**
	 * Determines the damage on the item the block drops. Used in cloth and wood.
	 */
	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}

	@Override
	public boolean canConnectWallTo(IBlockAccess access, int i, int j, int k)
	{
		Block block = access.getBlock(i, j, k);
		if (block != this && block != Blocks.fence_gate && block != TFCBlocks.FenceGate && block != TFCBlocks.FenceGate2 && !(block instanceof BlockCustomWall))
			return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false;
		else
			return true;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z) {
		return true;
	}
}
