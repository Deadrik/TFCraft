package com.bioxx.tfc.Blocks.Devices;

import static net.minecraftforge.common.util.ForgeDirection.DOWN;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.TileEntities.TEChest;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockChestTFC extends BlockTerraContainer
{
	public BlockChestTFC()
	{
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TEChest();
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int par6, float par7, float par8, float par9)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			IInventory iinventory = this.getInventory(world, x, y, z);

			if (iinventory != null)
				player.openGui(TerraFirmaCraft.instance, 29, world, x, y, z);

			return true;
		}
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		Block l = world.getBlock(i, j, k - 1);
		Block i1 = world.getBlock(i, j, k + 1);
		Block j1 = world.getBlock(i - 1, j, k);
		Block k1 = world.getBlock(i + 1, j, k);
		byte b0 = 0;
		int l1 = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l1 == 0) b0 = 2;
		if (l1 == 1) b0 = 5;
		if (l1 == 2) b0 = 3;
		if (l1 == 3) b0 = 4;

		if (l != this && i1 != this && j1 != this && k1 != this)
		{
			world.setBlockMetadataWithNotify(i, j, k, b0, 3);
		}
		else
		{
			if ((l == this || i1 == this) && (b0 == 4 || b0 == 5))
			{
				if (l == this)
					world.setBlockMetadataWithNotify(i, j, k - 1, b0, 3);
				else
					world.setBlockMetadataWithNotify(i, j, k + 1, b0, 3);

				world.setBlockMetadataWithNotify(i, j, k, b0, 3);
			}

			if ((j1 == this || k1 == this) && (b0 == 2 || b0 == 3))
			{
				if (j1 == this)
					world.setBlockMetadataWithNotify(i - 1, j, k, b0, 3);
				else
					world.setBlockMetadataWithNotify(i + 1, j, k, b0, 3);

				world.setBlockMetadataWithNotify(i, j, k, b0, 3);
			}
		}

		if (par6ItemStack.hasDisplayName())
			((TEChest)world.getTileEntity(i, j, k)).func_145976_a(par6ItemStack.getDisplayName());
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
		return TFCBlocks.chestRenderId;
	}

	public int getType(IBlockAccess access, int x, int y, int z)
	{
		TileEntity te =  access.getTileEntity(x, y, z);
		if(te != null && te instanceof TEChest)
		{
			TEChest chest = (TEChest) access.getTileEntity(x, y, z);
			return chest.type;
		}
		return -1;
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{
		if (access.getBlock(x, y, z - 1) == this)
		{
			this.setBlockBounds(0.0625F, 0.0F, 0.0F, 0.9375F, 0.875F, 0.9375F);
		}
		else if (access.getBlock(x, y, z + 1) == this)
		{
			this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 1.0F);
		}
		else if (access.getBlock(x - 1, y, z) == this)
		{
			this.setBlockBounds(0.0F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		}
		else if (access.getBlock(x + 1, y, z) == this)
		{
			this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 1.0F, 0.875F, 0.9375F);
		}
		else
		{
			this.setBlockBounds(0.0625F, 0.0F, 0.0625F, 0.9375F, 0.875F, 0.9375F);
		}
	}

	@Override
	public void onBlockAdded(World world, int x, int y, int z)
	{
		super.onBlockAdded(world, x, y, z);
		this.func_149954_e(world, x, y, z);
		Block block = world.getBlock(x, y, z - 1);
		Block block1 = world.getBlock(x, y, z + 1);
		Block block2 = world.getBlock(x - 1, y, z);
		Block block3 = world.getBlock(x + 1, y, z);
		TEChest chest = (TEChest) world.getTileEntity(x, y, z);

		if (block == this && chest.type == getType(world, x, y, z-1))
		{
			this.func_149954_e(world, x, y, z - 1);
		}

		if (block1 == this&& chest.type == getType(world, x, y, z+1))
		{
			this.func_149954_e(world, x, y, z + 1);
		}

		if (block2 == this&& chest.type == getType(world, x-1, y, z))
		{
			this.func_149954_e(world, x - 1, y, z);
		}

		if (block3 == this&& chest.type == getType(world, x+1, y, z))
		{
			this.func_149954_e(world, x + 1, y, z);
		}
	}

	public void func_149954_e(World world, int x, int y, int z)
	{
		if (!world.isRemote)
		{
			Block block = world.getBlock(x, y, z - 1);
			Block block1 = world.getBlock(x, y, z + 1);
			Block block2 = world.getBlock(x - 1, y, z);
			Block block3 = world.getBlock(x + 1, y, z);
			boolean flag = true;
			int l;
			Block block4;
			int i1;
			Block block5;
			boolean flag1;
			byte b0;
			int j1;

			if (block != this && block1 != this)
			{
				if (block2 != this && block3 != this)
				{
					b0 = 3;

					if (block.func_149730_j() && !block1.func_149730_j())
					{
						b0 = 3;
					}

					if (block1.func_149730_j() && !block.func_149730_j())
					{
						b0 = 2;
					}

					if (block2.func_149730_j() && !block3.func_149730_j())
					{
						b0 = 5;
					}

					if (block3.func_149730_j() && !block2.func_149730_j())
					{
						b0 = 4;
					}
				}
				else
				{
					l = block2 == this ? x - 1 : x + 1;
					block4 = world.getBlock(l, y, z - 1);
					i1 = block2 == this ? x - 1 : x + 1;
					block5 = world.getBlock(i1, y, z + 1);
					b0 = 3;
					flag1 = true;

					if (block2 == this)
					{
						j1 = world.getBlockMetadata(x - 1, y, z);
					}
					else
					{
						j1 = world.getBlockMetadata(x + 1, y, z);
					}

					if (j1 == 2)
					{
						b0 = 2;
					}

					if ((block.func_149730_j() || block4.func_149730_j()) && !block1.func_149730_j() && !block5.func_149730_j())
					{
						b0 = 3;
					}

					if ((block1.func_149730_j() || block5.func_149730_j()) && !block.func_149730_j() && !block4.func_149730_j())
					{
						b0 = 2;
					}
				}
			}
			else
			{
				l = block == this ? z - 1 : z + 1;
				block4 = world.getBlock(x - 1, y, l);
				i1 = block == this ? z - 1 : z + 1;
				block5 = world.getBlock(x + 1, y, i1);
				b0 = 5;
				flag1 = true;

				if (block == this)
				{
					j1 = world.getBlockMetadata(x, y, z - 1);
				}
				else
				{
					j1 = world.getBlockMetadata(x, y, z + 1);
				}

				if (j1 == 4)
				{
					b0 = 4;
				}

				if ((block2.func_149730_j() || block4.func_149730_j()) && !block3.func_149730_j() && !block5.func_149730_j())
				{
					b0 = 5;
				}

				if ((block3.func_149730_j() || block5.func_149730_j()) && !block2.func_149730_j() && !block4.func_149730_j())
				{
					b0 = 4;
				}
			}

			world.setBlockMetadataWithNotify(x, y, z, b0, 3);
		}
	}

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		int l = 0;

		if (world.getBlock(x - 1, y, z) == this)
		{
			++l;
		}

		if (world.getBlock(x + 1, y, z) == this)
		{
			++l;
		}

		if (world.getBlock(x, y, z - 1) == this)
		{
			++l;
		}

		if (world.getBlock(x, y, z + 1) == this)
		{
			++l;
		}

		return l > 1 ? false : (this.func_149952_n(world, x - 1, y, z) ? false : (this.func_149952_n(world, x + 1, y, z) ? false : (this.func_149952_n(world, x, y, z - 1) ? false : !this.func_149952_n(world, x, y, z + 1))));
	}

	private boolean func_149952_n(World p_149952_1_, int p_149952_2_, int p_149952_3_, int p_149952_4_)
	{
		return p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_) != this ? false : (p_149952_1_.getBlock(p_149952_2_ - 1, p_149952_3_, p_149952_4_) == this ? true : (p_149952_1_.getBlock(p_149952_2_ + 1, p_149952_3_, p_149952_4_) == this ? true : (p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_ - 1) == this ? true : p_149952_1_.getBlock(p_149952_2_, p_149952_3_, p_149952_4_ + 1) == this)));
	}

	public IInventory getInventory(World world, int x, int y, int z)
	{
		Object object = world.getTileEntity(x, y, z);

		if (object == null)
		{
			return null;
		}
		else if (world.isSideSolid(x, y + 1, z, DOWN))
		{
			return null;
		}
		else if (world.getBlock(x - 1, y, z) == this && world.isSideSolid(x - 1, y + 1, z, DOWN))
		{
			return null;
		}
		else if (world.getBlock(x + 1, y, z) == this && world.isSideSolid(x + 1, y + 1, z, DOWN))
		{
			return null;
		}
		else if (world.getBlock(x, y, z - 1) == this && world.isSideSolid(x, y + 1, z - 1, DOWN))
		{
			return null;
		}
		else if (world.getBlock(x, y, z + 1) == this && world.isSideSolid(x, y + 1, z + 1, DOWN))
		{
			return null;
		}
		else
		{
			if (world.getBlock(x - 1, y, z) == this)
			{
				object = new InventoryLargeChest("container.chestDouble", (TEChest)world.getTileEntity(x - 1, y, z), (IInventory)object);
			}

			if (world.getBlock(x + 1, y, z) == this)
			{
				object = new InventoryLargeChest("container.chestDouble", (IInventory)object, (TEChest)world.getTileEntity(x + 1, y, z));
			}

			if (world.getBlock(x, y, z - 1) == this)
			{
				object = new InventoryLargeChest("container.chestDouble", (TEChest)world.getTileEntity(x, y, z - 1), (IInventory)object);
			}

			if (world.getBlock(x, y, z + 1) == this)
			{
				object = new InventoryLargeChest("container.chestDouble", (IInventory)object, (TEChest)world.getTileEntity(x, y, z + 1));
			}

			return (IInventory)object;
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister p_149651_1_)
	{
		this.blockIcon = p_149651_1_.registerIcon("planks_oak");
	}
}
