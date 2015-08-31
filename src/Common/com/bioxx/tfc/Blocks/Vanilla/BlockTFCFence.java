package com.bioxx.tfc.Blocks.Vanilla;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFence;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemLead;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class BlockTFCFence extends BlockFence
{
	protected String[] woodNames;
	protected IIcon[] iconsPost;
	protected IIcon[] iconsPostTop;

	public BlockTFCFence(String str, Material mat)
	{
		super(str, mat);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		iconsPost = new IIcon[woodNames.length];
		iconsPostTop = new IIcon[woodNames.length];
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aaBB, List list, Entity entity)
	{
		boolean flag = this.canConnectFenceTo(world, x, y, z - 1);
		boolean flag1 = this.canConnectFenceTo(world, x, y, z + 1);
		boolean flag2 = this.canConnectFenceTo(world, x - 1, y, z);
		boolean flag3 = this.canConnectFenceTo(world, x + 1, y, z);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if (flag) f2 = 0.0F;
		if (flag1) f3 = 1.0F;

		if (flag || flag1)
		{
			this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
			super.addCollisionBoxesToList(world, x, y, z, aaBB, list, entity);
		}

		f2 = 0.375F;
		f3 = 0.625F;

		if (flag2) f = 0.0F;
		if (flag3) f1 = 1.0F;

		if (flag2 || flag3 || !flag && !flag1)
		{
			this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
			super.addCollisionBoxesToList(world, x, y, z, aaBB, list, entity);
		}

		if (flag) f2 = 0.0F;
		if (flag1) f3 = 1.0F;

		this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	/*@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(par5 == 1 || par5 == 0)
			return iconsPostTop[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];

		return iconsPost[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];
	}*/

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < woodNames.length; i++)
		{
			iconsPost[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/" + woodNames[i] + " Fence");
			iconsPostTop[i] = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/" + woodNames[i] + " Fence Top");
		}
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int x, int y, int z)
	{
		boolean flag = this.canConnectFenceTo(bAccess, x, y, z - 1);
		boolean flag1 = this.canConnectFenceTo(bAccess, x, y, z + 1);
		boolean flag2 = this.canConnectFenceTo(bAccess, x - 1, y, z);
		boolean flag3 = this.canConnectFenceTo(bAccess, x + 1, y, z);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if (flag) f2 = 0.0F;
		if (flag1) f3 = 1.0F;
		if (flag2) f = 0.0F;
		if (flag3) f1 = 1.0F;

		this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	/**
	 * Is this block (a) opaque and (b) a full 1m cube?  This determines whether or not to render the shared face of two
	 * adjacent blocks and also whether the player can attach torches, redstone wire, etc to this block.
	 */
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public IIcon getIcon(int par1, int par2)
	{
		if(par1 == 1)
			return iconsPostTop[par2];

		return iconsPost[par2];
	}

	@Override
	public int damageDropped(int par1)
	{
		return par1;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.fenceRenderId;
	}

	/**
	 * Returns true if the specified block can be connected by a fence
	 */
	@Override
	public boolean canConnectFenceTo(IBlockAccess bAccess, int x, int y, int z)
	{
		Block block = bAccess.getBlock(x, y, z);

		if (TFCBlocks.canFenceConnectTo(block))
			return true;
		else
			return block != this && block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false;
	}

	public static boolean isBlockAFence(Block block)
	{
		return TFCBlocks.isBlockAFence(block);
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess bAccess, int x, int y, int z, int side)
	{
		return true;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if(!world.isRemote)
		{
				return ItemLead.func_150909_a(player, world, x, y, z);
		}
		return true;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}
}
