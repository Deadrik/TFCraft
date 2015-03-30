package com.bioxx.tfc.Blocks.Devices;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Facing;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEHopper;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHopper extends BlockTerraContainer
{
	private final Random random = new Random();
	@SideOnly(Side.CLIENT)
	private static IIcon hopper_outside;
	@SideOnly(Side.CLIENT)
	private static IIcon hopper_top;
	@SideOnly(Side.CLIENT)
	private static IIcon hopper_inside;

	public BlockHopper()
	{
		super(Material.iron);
		this.setCreativeTab(TFCTabs.TFCDevices);
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess p_149719_1_, int p_149719_2_, int p_149719_3_, int p_149719_4_)
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Adds all intersecting collision boxes to a list. (Be sure to only add boxes to the list if they intersect the
	 * mask.) Parameters: World, X, Y, Z, mask, list, colliding entity
	 */
	@Override
	public void addCollisionBoxesToList(World world, int x, int y, int z, AxisAlignedBB aabb, List list, Entity entity)
	{
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		float f = 0.125F;
		this.setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, f);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(1.0F - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(0.0F, 0.0F, 1.0F - f, 1.0F, 1.0F, 1.0F);
		super.addCollisionBoxesToList(world, x, y, z, aabb, list, entity);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
	}

	/**
	 * Called when a block is placed using its ItemBlock. Args: World, X, Y, Z, side, hitX, hitY, hitZ, block metadata
	 */
	@Override
	public int onBlockPlaced(World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int meta)
	{
		int j1 = Facing.oppositeSide[side];

		if (j1 == 1)
		{
			j1 = 0;
		}

		return j1;
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entity, ItemStack is)
	{
		super.onBlockPlacedBy(world, x, y, z, entity, is);

		if (is.hasDisplayName())
		{
			TEHopper tileentityhopper = getHopperTE(world, x, y, z);
			tileentityhopper.setCustomName(is.getDisplayName());
		}
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World p_149726_1_, int p_149726_2_, int p_149726_3_, int p_149726_4_)
	{
		super.onBlockAdded(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
		this.updatePowerState(p_149726_1_, p_149726_2_, p_149726_3_, p_149726_4_);
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		TEHopper te = getHopperTE(world, x, y, z);
		if (world.isRemote)
		{
			if (te != null && te.pressBlock != null && player.isSneaking())
			{
				te.pressBlock = null;
				te.pressCooldown = 0;
			}
			return true;
		}
		else
		{
			if (te != null && te.pressCooldown == 0)
			{
				player.openGui(TerraFirmaCraft.instance, 49, world, x, y, z);
			}
			else if (te != null && te.pressBlock != null && player.isSneaking())
			{
				TFC_Core.giveItemToPlayer(te.pressBlock, player);
				te.pressBlock = null;
				te.pressCooldown = 0;
			}

			return true;
		}
	}

	/**
	 * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
	 * their own) Args: x, y, z, neighbor Block
	 */
	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		this.updatePowerState(world, x, y, z);
	}

	private void updatePowerState(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		int dir = getDirectionFromMetadata(meta);
		boolean recievesPower = !world.isBlockIndirectlyGettingPowered(x, y, z);
		boolean hopperPower = func_149917_c(meta);

		if (recievesPower != hopperPower)
		{
			world.setBlockMetadataWithNotify(x, y, z, dir | (recievesPower ? 0 : 8), 4);
		}
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TEHopper te = (TEHopper)world.getTileEntity(x, y, z);

		if (te != null)
		{
			for (int i1 = 0; i1 < te.getSizeInventory(); ++i1)
			{
				ItemStack itemstack = te.getStackInSlot(i1);

				if (itemstack != null)
				{
					while (itemstack.stackSize > 0)
					{
						int j1 = this.random.nextInt(21) + 10;

						if (j1 > itemstack.stackSize)
						{
							j1 = itemstack.stackSize;
						}

						itemstack.stackSize -= j1;
						EntityItem entityitem = new EntityItem(world, (double)((float)x + 0.5f), (double)((float)y + 0.5f), (double)((float)z + 0.5f), new ItemStack(itemstack.getItem(), j1, itemstack.getItemDamage()));

						if (itemstack.hasTagCompound())
						{
							entityitem.getEntityItem().setTagCompound((NBTTagCompound)itemstack.getTagCompound().copy());
						}

						float f3 = 0.05F;
						world.spawnEntityInWorld(entityitem);
					}
				}
			}
			if(te.pressBlock != null)
			{
				EntityItem entityitem = new EntityItem(world, (double)((float)x+0.5), (double)((float)y + 0.5), (double)((float)z + 0.5), te.pressBlock);
				world.spawnEntityInWorld(entityitem);
			}
			world.func_147453_f(x, y, z, block);
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.HopperRenderId;
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
	 * Returns true if the given side of this block type should be rendered, if the adjacent block is at the given
	 * coordinates.  Args: blockAccess, x, y, z, side
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess p_149646_1_, int p_149646_2_, int p_149646_3_, int p_149646_4_, int p_149646_5_)
	{
		return true;
	}

	/**
	 * Gets the block's texture. Args: side, meta
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		return side == 1 ? hopper_top : hopper_outside;
	}

	public static int getDirectionFromMetadata(int meta)
	{
		return meta & 7;
	}

	public static boolean func_149917_c(int meta)
	{
		return (meta & 8) != 8;
	}

	/**
	 * If this returns true, then comparators facing away from this block will use the value from
	 * getComparatorInputOverride instead of the actual redstone signal strength.
	 */
	@Override
	public boolean hasComparatorInputOverride()
	{
		return true;
	}

	/**
	 * If hasComparatorInputOverride returns true, the return value from this is used instead of the redstone signal
	 * strength when this block inputs to a comparator.
	 */
	@Override
	public int getComparatorInputOverride(World world, int x, int y, int z, int meta)
	{
		return Container.calcRedstoneFromInventory(getHopperTE(world, x, y, z));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer)
	{
		hopper_outside = registerer.registerIcon("hopper_outside");
		hopper_top = registerer.registerIcon("hopper_top");
		hopper_inside = registerer.registerIcon("hopper_inside");
	}

	@SideOnly(Side.CLIENT)
	public static IIcon getHopperIcon(String p_149916_0_)
	{
		return p_149916_0_.equals("hopper_outside") ? hopper_outside : (p_149916_0_.equals("hopper_inside") ? hopper_inside : null);
	}

	public static TEHopper getHopperTE(IBlockAccess access, int x, int y, int z)
	{
		return (TEHopper)access.getTileEntity(x, y, z);
	}

	/**
	 * Gets the icon name of the ItemBlock corresponding to this block. Used by hoppers.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return "hopper";
	}
	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEHopper();
	}
}
