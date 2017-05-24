package com.bioxx.tfc.Items;

import java.util.List;

import com.bioxx.tfc.TileEntities.TECoalPile;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import net.minecraftforge.common.util.ForgeDirection;

public class ItemCoal extends ItemTerra {
	public ItemCoal() {
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.metaNames = new String[]{"coal", "charcoal", "coke"};
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.TINY);
	}

	private int[][] map =
			{{0, -1, 0},
					{0, 1, 0},
					{0, 0, -1},
					{0, 0, 1},
					{-1, 0, 0},
					{1, 0, 0},
			};

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list) {
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
		list.add(new ItemStack(item, 1, 2));
	}

	private boolean createPile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, int l)
	{
		TECoalPile te = null;
		if(world.isAirBlock(x, y, z) && isValid(world, x, y, z))
		{
			world.setBlock(x, y, z, TFCBlocks.coalPile, l, 3);
			te = (TECoalPile)world.getTileEntity(x, y, z);
		}
		else
		{
			return false;
		}

		if(te != null)
		{
			te.storage[0] = new ItemStack(this,1,itemstack.getItemDamage());
			if(entityplayer.capabilities.isCreativeMode)
			{
				te.storage[0] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[1] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[2] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[3] = new ItemStack(this,4,itemstack.getItemDamage());
			}
		}

		return true;
	}

	public boolean isValid(World world, int i, int j, int k) {
		if (world.isSideSolid(i, j - 1, k, ForgeDirection.UP)) {
			TileEntity te = world.getTileEntity(i, j - 1, k);

			if (te instanceof TECoalPile) {
				TECoalPile lp = (TECoalPile) te;

				if (lp.storage[0] == null || lp.storage[0].stackSize < 4)
				{
					return false;
				}
				if (lp.storage[1] == null || lp.storage[1].stackSize < 4)
				{
					return false;
				}
				if (lp.storage[2] == null || lp.storage[2].stackSize < 4)
				{
					return false;
				}
				if (lp.storage[3] == null || lp.storage[3].stackSize < 4)
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) {
		if (is.getItemDamage() == 1 && !world.isRemote) {
			if (world.getBlock(x, y, z) == TFCBlocks.charcoal) {
				int meta = world.getBlockMetadata(x, y, z);
				if (meta < 8) {
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
					is.stackSize--;
					return true;
				} else if (side == 1 && world.isAirBlock(x + map[side][0], y + map[side][1], z + map[side][2])) {
					world.setBlock(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.charcoal, 1, 0x2);
					is.stackSize--;
					return true;
				}
			}

			if (world.getBlock(x + map[side][0], y + map[side][1], z + map[side][2]) == TFCBlocks.charcoal) {
				int meta = world.getBlockMetadata(x + map[side][0], y + map[side][1], z + map[side][2]);
				if (meta < 8) {
					world.setBlockMetadataWithNotify(x + map[side][0], y + map[side][1], z + map[side][2], meta + 1, 3);
					is.stackSize--;
					return true;
				}
			}

			if (world.isAirBlock(x + map[side][0], y + map[side][1], z + map[side][2])) {
				world.setBlock(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.charcoal, 1, 0x2);
				is.stackSize--;
				TFCBlocks.charcoal.onNeighborBlockChange(world, x + map[side][0], y + map[side][1], z + map[side][2], world.getBlock(x + map[side][0], y + map[side][1], z + map[side][2]));
			}
			return true;
		} else if (is.getItemDamage() == 0 && !world.isRemote) {
			if (player.isSneaking() && (world.getBlock(x, y, z) != TFCBlocks.coalPile || side != 1 && side != 0)) {
				int dir = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
				if (side == 0)
					--y;
				else if (side == 1)
					++y;
				else if (side == 2)
					--z;
				else if (side == 3)
					++z;
				else if (side == 4)
					--x;
				else if (side == 5)
					++x;
				if (world.canPlaceEntityOnSide(TFCBlocks.coalPile, x, y, z, false, side, player, is))
					if (createPile(is, player, world, x, y, z, side, dir)) {
						is.stackSize = is.stackSize - 1;
						playSound(world, x, y, z);
					}
				return true;
			} else if (world.getBlock(x, y, z) == TFCBlocks.coalPile) {
				TECoalPile te = (TECoalPile) world.getTileEntity(x, y, z);
				if (te != null) {
					if(te.storage[0] != null && te.contentsMatch(0, is)) {
						te.injectContents(0,1);
					} else if(te.storage[0] == null) {
						te.addContents(0, new ItemStack(this,1, is.getItemDamage()));
					} else if(te.storage[1] != null && te.contentsMatch(1,is)) {
						te.injectContents(1,1);
					} else if(te.storage[1] == null) {
						te.addContents(1, new ItemStack(this,1, is.getItemDamage()));
					} else if(te.storage[2] != null && te.contentsMatch(2,is)) {
						te.injectContents(2,1);
					} else if(te.storage[2] == null) {
						te.addContents(2, new ItemStack(this,1, is.getItemDamage()));
					} else if(te.storage[3] != null && te.contentsMatch(3,is)) {
						te.injectContents(3,1);
					} else if(te.storage[3] == null) {
						te.addContents(3, new ItemStack(this,1, is.getItemDamage()));
					} else
					{
						int dir = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
						if (side == 0)
							--y;
						else if (side == 1)
							++y;
						else if (side == 2)
							--z;
						else if (side == 3)
							++z;
						else if (side == 4)
							--x;
						else if (side == 5)
							++x;
						if (!createPile(is, player, world, x, y, z, side, dir)) {
							return true;
						}

					}
					playSound(world, x, y, z);
					is.stackSize = is.stackSize - 1;
					return true;
				}

			}
		}
		return false;
	}
	private void playSound(World world, int x, int y, int z)
	{
		world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, TFCBlocks.charcoal.stepSound.func_150496_b(), (TFCBlocks.charcoal.stepSound.getVolume() + 1.0F) / 2.0F, TFCBlocks.charcoal.stepSound.getPitch() * 0.8F);
	}
}
