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
import net.minecraft.world.gen.feature.WorldGenerator;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.TileEntities.TESapling;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

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
		this.setCreativeTab(TFCTabs.TFC_DECORATION);
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
			this.icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/trees/" + this.woodNames[i] + " Sapling");

	}

	public void growTree(World world, int i, int j, int k, Random rand, long timestamp)
	{
		int meta = world.getBlockMetadata(i, j, k);
		world.setBlockToAir(i, j, k);
		WorldGenerator worldGen = TFCBiome.getTreeGen(meta, rand.nextBoolean());

		if (worldGen != null && !worldGen.generate(world, rand, i, j, k))
		{
			world.setBlock(i, j, k, this, meta, 3);
			if (world.getTileEntity(i, j, k) instanceof TESapling)
			{
				TESapling te = (TESapling) world.getTileEntity(i, j, k);
				te.growTime = timestamp;
				te.enoughSpace = false;
				te.markDirty();
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block b)
	{
		Block block = world.getBlock(i, j, k);
		if(!TFC_Core.isGrass(block) && !TFC_Core.isDirt(block) && !this.canBlockStay(world, i, j, k))
		{
			int meta = world.getBlockMetadata(i, j, k);
			this.dropBlockAsItem(world, i, j, k, new ItemStack(this, 1, meta));
			world.setBlockToAir(i, j, k);
		}
	}


	// Set the sapling growth timer the moment it is planted, instead of the first random tick it gets after being planted.
	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		float growSpeed = 1;
		if(meta == 1 || meta == 11)
			growSpeed = 1.2f;
		else if(meta == 5 || meta == 0 || meta == 13)
			growSpeed = 1.4f;
		else if(meta == 9 || meta == 14|| meta == 15)
			growSpeed = 1.6f;

		if (world.getTileEntity(i, j, k) instanceof TESapling)
		{
			TESapling te = (TESapling) world.getTileEntity(i, j, k);

			// Set the growTime tick timestamp to be 7-11.2 days times config multiplier from now, plus up to an extra day.
			if (te != null && te.growTime == 0)
				te.growTime = (long) (TFC_Time.getTotalTicks() + (TFC_Time.DAY_LENGTH * 7 * growSpeed * TFCOptions.saplingTimerMultiplier) + (world.rand.nextFloat() * TFC_Time.DAY_LENGTH));
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		if (!world.isRemote)
		{
			super.updateTick(world, i, j, k, rand);

			if (world.getTileEntity(i, j, k) instanceof TESapling)
			{
				long timestamp = ((TESapling) world.getTileEntity(i, j, k)).growTime;

				if (world.getBlockLightValue(i, j + 1, k) >= 9 && TFC_Time.getTotalTicks() > timestamp)
				{
					growTree(world, i, j, k, rand, timestamp);
				}
			}
		}
	}

	/**
	 * Can this block stay at this position.  Similar to canPlaceBlockAt except gets checked often with plants.
	 */
	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return (world.getFullBlockLightValue(x, y, z) >= 8 || world.canBlockSeeTheSky(x, y, z)) && this.canThisPlantGrowOnThisBlockID(world.getBlock(x, y - 1, z));
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		return (world.isAirBlock(x, y, z) || world.getBlock(x, y, z).getMaterial().isReplaceable()) && this.canThisPlantGrowOnThisBlockID(world.getBlock(x, y - 1, z));
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	/**
	 * Gets passed in the blockID of the block below and supposed to return true if its allowed to grow on the type of
	 * blockID passed in. Args: blockID
	 */
	protected boolean canThisPlantGrowOnThisBlockID(Block b)
	{
		return TFC_Core.isSoil(b);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
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
		return new TESapling();
	}

	protected void checkChange(World world, int x, int y, int z)
	{
		if (!this.canBlockStay(world, x, y, z))
		{
			this.dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
			world.setBlockToAir(x, y, z);
		}
	}
}
