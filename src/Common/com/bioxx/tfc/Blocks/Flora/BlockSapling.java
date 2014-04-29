package com.bioxx.tfc.Blocks.Flora;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TileEntitySapling;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockSapling extends BlockTerraContainer
{
	protected IIcon[] icons;
	protected String[] woodNames;

	public BlockSapling()
	{
		super(Material.plants);
		float f = 0.4F;
		this.setBlockBounds(0.5F - f, 0.0F, 0.5F - f, 0.5F + f, f * 2.0F, 0.5F + f);
		this.woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, this.woodNames, 0, 16);
		this.setTickRandomly(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.icons = new IIcon[woodNames.length];
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(item, 1, i));
	}

	@Override
	public int damageDropped(int i)
	{
		return i;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		return icons[j];
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < woodNames.length; i++)
			this.icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/trees/" + this.woodNames[i] + " Sapling");
			
	}

	public void growTree(World world, int i, int j, int k, Random random)
	{
		/*int l = world.getBlockMetadata(i, j, k);
		world.setBlockToAir(i, j, k);
		Object obj = TFCBiome.getTreeGen(l, random.nextBoolean());

		if (obj!= null && !((WorldGenerator) obj).generate(world, random, i, j, k))
			world.setBlock(i, j, k, this, l, 3);*/
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block par5)
	{
		Block block = world.getBlock(i, j, k);
		if(!TFC_Core.isGrass(block) && !TFC_Core.isDirt(block) && !this.canBlockStay(world, i, j, k))
		{
			int meta = world.getBlockMetadata(i, j, k);
			this.dropBlockAsItem(world, i, j, k, new ItemStack(this, 1, meta));
			world.setBlockToAir(i, j, k);
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if (world.isRemote)
			return;

		super.updateTick(world, i, j, k, random);
		int meta = world.getBlockMetadata(i, j, k);
		float growSpeed = 1;
		if(meta == 1 || meta == 11)
			growSpeed = 1.2f;
		else if(meta == 5 || meta == 0 || meta == 13)
			growSpeed = 1.4f;
		else if(meta == 9 || meta == 14|| meta == 15)
			growSpeed = 1.6f;

		TileEntitySapling te = (TileEntitySapling) world.getTileEntity(i, j, k);

		if(te != null && te.growTime == 0)
			te.growTime = (long) ((TFC_Time.getTotalTicks() + (TFC_Time.dayLength * 7) * growSpeed) + (world.rand.nextFloat() * TFC_Time.dayLength));

		if (world.getBlockLightValue(i, j + 1, k) >= 9 && te!= null && TFC_Time.getTotalTicks() > te.growTime)
			growTree(world, i, j, k, random);

		//this.checkChange(world, i, j, k);
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
	{
		return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || par1World.canBlockSeeTheSky(par2, par3, par4)) && this.canThisPlantGrowOnThisBlockID(par1World.getBlock(par2, par3 - 1, par4));
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return (par1World.isAirBlock(par2, par3, par4) || par1World.getBlock(par2, par3, par4).getMaterial().isReplaceable()) && this.canThisPlantGrowOnThisBlockID(par1World.getBlock(par2, par3 - 1, par4));
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return true;
	}

	/**
	 * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
	 * blockID passed in. Args: blockID
	 */
	protected boolean canThisPlantGrowOnThisBlockID(Block block)
	{
		return TFC_Core.isSoil(block);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 1;
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

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntitySapling();
	}

	protected void checkChange(World par1World, int par2, int par3, int par4)
	{
		if (!this.canBlockStay(par1World, par2, par3, par4))
		{
			this.dropBlockAsItem(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4), 0);
			par1World.setBlockToAir(par2, par3, par4);
		}
	}
}
