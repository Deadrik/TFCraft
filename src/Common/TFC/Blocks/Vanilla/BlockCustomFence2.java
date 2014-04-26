package TFC.Blocks.Vanilla;

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
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.IMultipleBlock;
import TFC.API.Constant.Global;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomFence2 extends BlockFence implements IMultipleBlock
{
	private final String field_94464_a;

	String[] woodNames;
	IIcon[] iconsPost;
	IIcon[] iconsPostTop;

	public BlockCustomFence2(String par2Str, Material par3Material)
	{
		super("par2Str", par3Material);
		this.field_94464_a = par2Str;
		woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, woodNames, 0, Global.WOOD_ALL.length - 16);
		iconsPost = new IIcon[woodNames.length];
		iconsPostTop = new IIcon[woodNames.length];
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World par1World, int par2, int par3, int par4, AxisAlignedBB par5AxisAlignedBB, List par6List, Entity par7Entity)
	{
		boolean flag = this.canConnectFenceTo(par1World, par2, par3, par4 - 1);
		boolean flag1 = this.canConnectFenceTo(par1World, par2, par3, par4 + 1);
		boolean flag2 = this.canConnectFenceTo(par1World, par2 - 1, par3, par4);
		boolean flag3 = this.canConnectFenceTo(par1World, par2 + 1, par3, par4);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if (flag)
			f2 = 0.0F;

		if (flag1)
			f3 = 1.0F;

		if (flag || flag1)
		{
			this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}

		f2 = 0.375F;
		f3 = 0.625F;

		if (flag2)
			f = 0.0F;

		if (flag3)
			f1 = 1.0F;

		if (flag2 || flag3 || !flag && !flag1)
		{
			this.setBlockBounds(f, 0.0F, f2, f1, 1.5F, f3);
			super.addCollisionBoxesToList(par1World, par2, par3, par4, par5AxisAlignedBB, par6List, par7Entity);
		}

		if (flag)
			f2 = 0.0F;

		if (flag1)
			f3 = 1.0F;

		this.setBlockBounds(f, 0.0F, f2, f1, 1.0F, f3);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i <  woodNames.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}

	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		if(par5 == 1 || par5 == 0)
			return iconsPostTop[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];

		return iconsPost[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i <  woodNames.length; i++)
		{
			iconsPost[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/" + woodNames[i] + " Fence");
			iconsPostTop[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/" + woodNames[i] + " Fence Top");
		}
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		boolean flag = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 - 1);
		boolean flag1 = this.canConnectFenceTo(par1IBlockAccess, par2, par3, par4 + 1);
		boolean flag2 = this.canConnectFenceTo(par1IBlockAccess, par2 - 1, par3, par4);
		boolean flag3 = this.canConnectFenceTo(par1IBlockAccess, par2 + 1, par3, par4);
		float f = 0.375F;
		float f1 = 0.625F;
		float f2 = 0.375F;
		float f3 = 0.625F;

		if (flag)
			f2 = 0.0F;

		if (flag1)
			f3 = 1.0F;

		if (flag2)
			f = 0.0F;

		if (flag3)
			f1 = 1.0F;

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
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return false;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.FenceRenderId;
	}

	/**
	 * Returns true if the specified block can be connected by a fence
	 */
	@Override
	public boolean canConnectFenceTo(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		Block block = par1IBlockAccess.getBlock(par2, par3, par4);

		if (TFCBlocks.canFenceConnectTo(block))
			return true;
		else
			return block != null && block.getMaterial().isOpaque() && block.renderAsNormalBlock() ? block.getMaterial() != Material.gourd : false;
	}

	public static boolean isBlockAFence(Block par0)
	{
		return TFCBlocks.isBlockAFence(par0);
	}

	/**
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public boolean shouldSideBeRendered(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@SideOnly(Side.CLIENT)
	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		return par1World.isRemote ? true : ItemLead.func_150909_a(par5EntityPlayer, par1World, par2, par3, par4);
	}

	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.Fence2;
	}

	@Override
	public boolean canPlaceTorchOnTop(World world, int x, int y, int z)
	{
		return true;
	}
}
