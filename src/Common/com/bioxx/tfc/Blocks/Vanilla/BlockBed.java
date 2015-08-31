package com.bioxx.tfc.Blocks.Vanilla;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayer.EnumStatus;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Direction;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class BlockBed extends BlockDirectional
{
	/** Maps the foot-of-bed block to the head-of-bed block. */
	public static final int[][] FOOT_HEAD_BLOCKMAP = new int[][] {{0, 1}, { -1, 0}, {0, -1}, {1, 0}};
	@SideOnly(Side.CLIENT)
	private IIcon[] bedEndIcons;
	@SideOnly(Side.CLIENT)
	private IIcon[] bedSideIcons;
	@SideOnly(Side.CLIENT)
	private IIcon[] bedTopIcons;

	public BlockBed()
	{
		super(Material.grass);
		this.setBounds();
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			int i1 = world.getBlockMetadata(x, y, z);

			if (!isBlockHeadOfBed(i1))
			{
				int j1 = getDirection(i1);
				x += FOOT_HEAD_BLOCKMAP[j1][0];
				z += FOOT_HEAD_BLOCKMAP[j1][1];

				if (world.getBlock(x, y, z) != this)
					return true;

				i1 = world.getBlockMetadata(x, y, z);
			}

			if (world.provider.canRespawnHere() && world.getBiomeGenForCoords(x, z) != TFCBiome.HELL)
			{
				if (isBedOccupied(i1))
				{
					EntityPlayer entityplayer1 = null;
					Iterator iterator = world.playerEntities.iterator();

					while (iterator.hasNext())
					{
						EntityPlayer entityplayer2 = (EntityPlayer)iterator.next();

						if (entityplayer2.isPlayerSleeping())
						{
							ChunkCoordinates chunkcoordinates = entityplayer2.playerLocation;

							if (chunkcoordinates.posX == x && chunkcoordinates.posY == y && chunkcoordinates.posZ == z)
							{
								entityplayer1 = entityplayer2;
							}
						}
					}

					if (entityplayer1 != null)
					{
						TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("tile.bed.occupied"));
						return true;
					}

					setBedOccupied(world, x, y, z, false);
				}

				EnumStatus enumstatus = player.sleepInBedAt(x, y, z);

				if (enumstatus == EnumStatus.OK)
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("tile.customBed.sleep"));
					setBedOccupied(world, x, y, z, true);
					return true;
				}
				else
				{
					if (enumstatus == EnumStatus.NOT_POSSIBLE_NOW)
						TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("tile.bed.noSleep"));
					else if (enumstatus == EnumStatus.NOT_SAFE)
						TFC_Core.sendInfoMessage(player, new ChatComponentTranslation("tile.bed.notSafe"));

					return true;
				}
			}
			else
			{
				double d0 = x + 0.5D;
				double d1 = y + 0.5D;
				double d2 = z + 0.5D;
				world.setBlockToAir(x, y, z);
				int k1 = getDirection(i1);
				x += FOOT_HEAD_BLOCKMAP[k1][0];
				z += FOOT_HEAD_BLOCKMAP[k1][1];

				if (world.getBlock(x, y, z) == this)
				{
					world.setBlockToAir(x, y, z);
					d0 = (d0 + x + 0.5D) / 2.0D;
					d1 = (d1 + y + 0.5D) / 2.0D;
					d2 = (d2 + z + 0.5D) / 2.0D;
				}

				world.newExplosion((Entity)null, x + 0.5F, y + 0.5F, z + 0.5F, 5.0F, true, true);
				return true;
			}
		}
	}

	@Override
	public boolean isBed(IBlockAccess world, int x, int y, int z, EntityLivingBase player)
	{
		World w = (World)world;
		if(!w.isRemote && player!=null)
			((EntityPlayer)player).sleepTimer = 50;

		return true;
	}


	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
	 */
	public IIcon getIcon(int par1, int par2)
	{
		if (par1 == 0)
		{
			return TFCBlocks.planks.getBlockTextureFromSide(par1);
		}
		else
		{
			int k = getDirection(par2);
			int l = Direction.bedDirection[k][par1];
			int i1 = isBlockHeadOfBed(par2) ? 1 : 0;
			return (i1 != 1 || l != 2) && (i1 != 0 || l != 3) ? (l != 5 && l != 4 ? this.bedTopIcons[i1] : this.bedSideIcons[i1]) : this.bedEndIcons[i1];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * When this method is called, your block should register all the icons it needs with the given IconRegister. This
	 * is the only chance you get to register icons.
	 */
	public void registerBlockIcons(IIconRegister par1IconRegister)
	{
		this.bedTopIcons = new IIcon[] {par1IconRegister.registerIcon(Reference.MOD_ID + ":straw bed_feet_top"), par1IconRegister.registerIcon(Reference.MOD_ID + ":straw bed_head_top")};
		this.bedEndIcons = new IIcon[] {par1IconRegister.registerIcon(Reference.MOD_ID + ":straw bed_feet_end"), par1IconRegister.registerIcon(Reference.MOD_ID + ":straw bed_head_end")};
		this.bedSideIcons = new IIcon[] {par1IconRegister.registerIcon(Reference.MOD_ID + ":straw bed_feet_side"), par1IconRegister.registerIcon(Reference.MOD_ID + ":straw bed_head_side")};
	}

	/*
	 * Removed because it is trying to load an item texture that doesn't exist causing a slow down at launch
	 * 
	 * @Override
	public String getItemIconName(){
		return "straw bed";
	}*/

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return 14;
	}

	/**
	 * If this block doesn't render as an ordinary block it will return False (examples: signs, buttons, stairs, etc)
	 */
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
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
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		this.setBounds();
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor blockID
	 */
	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block par5)
	{
		int i1 = par1World.getBlockMetadata(par2, par3, par4);
		int j1 = getDirection(i1);

		if (isBlockHeadOfBed(i1))
		{
			if (par1World.getBlock(par2 - FOOT_HEAD_BLOCKMAP[j1][0], par3, par4 - FOOT_HEAD_BLOCKMAP[j1][1]) != this)
			{
				par1World.setBlockToAir(par2, par3, par4);
			}
		}
		else if (par1World.getBlock(par2 + FOOT_HEAD_BLOCKMAP[j1][0], par3, par4 + FOOT_HEAD_BLOCKMAP[j1][1]) != this)
		{
			par1World.setBlockToAir(par2, par3, par4);

			if (!par1World.isRemote)
			{
				this.dropBlockAsItem(par1World, par2, par3, par4, i1, 0);
			}
		}
	}

	/**
	 * Returns the ID of the items to drop on destruction.
	 */
	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return null;//isBlockHeadOfBed(par1) ? 0 : Item.bed.itemID;
	}

	/**
	 * Set the bounds of the bed block.
	 */
	private void setBounds()
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5625F, 1.0F);
	}

	/**
	 * Returns whether or not this bed block is the head of the bed.
	 */
	public static boolean isBlockHeadOfBed(int par0)
	{
		return (par0 & 8) != 0;
	}

	/**
	 * Return whether or not the bed is occupied.
	 */
	public static boolean isBedOccupied(int par0)
	{
		return (par0 & 4) != 0;
	}

	/**
	 * Sets whether or not the bed is occupied.
	 */
	public static void setBedOccupied(World par0World, int par1, int par2, int par3, boolean par4)
	{
		int l = par0World.getBlockMetadata(par1, par2, par3);

		if (par4)
			l |= 4;
		else
			l &= -5;

		par0World.setBlockMetadataWithNotify(par1, par2, par3, l, 4);
	}

	/**
	 * Gets the nearest empty chunk coordinates for the player to wake up from a bed into.
	 */
	public static ChunkCoordinates getNearestEmptyChunkCoordinates(World par0World, int par1, int par2, int par3, int par4)
	{
		int i1 = par0World.getBlockMetadata(par1, par2, par3);
		int j1 = BlockDirectional.getDirection(i1);

		for (int k1 = 0; k1 <= 1; ++k1)
		{
			int l1 = par1 - FOOT_HEAD_BLOCKMAP[j1][0] * k1 - 1;
			int i2 = par3 - FOOT_HEAD_BLOCKMAP[j1][1] * k1 - 1;
			int j2 = l1 + 2;
			int k2 = i2 + 2;

			for (int l2 = l1; l2 <= j2; ++l2)
			{
				for (int i3 = i2; i3 <= k2; ++i3)
				{
					if (World.doesBlockHaveSolidTopSurface(par0World, l2, par2 - 1, i3) && !par0World.getBlock(l2, par2, i3).getMaterial().isOpaque() && !par0World.getBlock(l2, par2 + 1, i3).getMaterial().isOpaque())
					{
						if (par4 <= 0)
							return new ChunkCoordinates(l2, par2, i3);

						--par4;
					}
				}
			}
		}

		return null;
	}

	/**
	 * Drops the block items with a specified chance of dropping the specified items
	 */
	@Override
	public void dropBlockAsItemWithChance(World par1World, int par2, int par3, int par4, int par5, float par6, int par7)
	{
		if (!isBlockHeadOfBed(par5))
			super.dropBlockAsItemWithChance(par1World, par2, par3, par4, par5, par6, 0);
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		ret.add(new ItemStack(TFCItems.hide,1,2));
		ret.add(new ItemStack(TFCBlocks.thatch,2,0));
		return ret;
	}

	/**
	 * Returns the mobility information of the block, 0 = free, 1 = can't push but can move over, 2 = total immobility
	 * and stop pistons
	 */
	@Override
	public int getMobilityFlag()
	{
		return 1;
	}

	/**
	 * Called when the block is attempted to be harvested
	 */
	@Override
	public void onBlockHarvested(World par1World, int par2, int par3, int par4, int par5, EntityPlayer par6EntityPlayer)
	{
		if (par6EntityPlayer.capabilities.isCreativeMode && isBlockHeadOfBed(par5))
		{
			int i1 = getDirection(par5);
			par2 -= FOOT_HEAD_BLOCKMAP[i1][0];
			par4 -= FOOT_HEAD_BLOCKMAP[i1][1];

			if (par1World.getBlock(par2, par3, par4) == this)
				par1World.setBlockToAir(par2, par3, par4);
		}
	}
}
