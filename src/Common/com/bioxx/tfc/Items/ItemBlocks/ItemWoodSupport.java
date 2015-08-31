package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Blocks.BlockWoodSupport;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemWoodSupport extends ItemTerraBlock
{
	public ItemWoodSupport(Block par1) 
	{
		super(par1);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.metaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, metaNames, 0, 16);
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.SMALL;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}

	public Block getBlock()
	{
		return field_150939_a;
	}

	public boolean isValidUnder(World world, int x, int y, int z, int side)
	{
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

		Block b = world.getBlock(x, y-1, z);
		return b == TFCBlocks.woodSupportV || b == TFCBlocks.woodSupportV2 || b.isSideSolid(world, x, y - 1, z, ForgeDirection.UP);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(y < 250 && y > 0 && side == 1 && isValidUnder(world, x, y, z, side))
		{
			if(!player.isSneaking() && world.isAirBlock(x, y+1, z) && world.isAirBlock(x, y+2, z) && world.isAirBlock(x, y+3, z) && itemstack.stackSize >= 3 &&
					world.getBlock(x, y, z) != TFCBlocks.woodSupportV && world.getBlock(x, y, z) != TFCBlocks.woodSupportV2 )
			{
				placeBlockAt(getBlock(), itemstack, player, world, x, y+1, z, itemstack.getItemDamage(), 2);
				placeBlockAt(getBlock(), itemstack, player, world, x, y+2, z, itemstack.getItemDamage(), 2);
				placeBlockAt(getBlock(), itemstack, player, world, x, y+3, z, itemstack.getItemDamage(), 2);
				itemstack.stackSize-=3;
				return true;
			}
			else
			{
				placeBlockAt(getBlock(), itemstack, player, world, x, y+1, z, itemstack.getItemDamage(), 3);
				itemstack.stackSize--;
				return true;
			}
		}
		else if(y < 255 && y > 0 && side == 0)
		{
			boolean shouldGen = false;
			int dist = 0;
			for(dist = 1; dist <= 20 && !shouldGen; dist++)
			{
				if(!world.getBlock(x, y-dist, z).isReplaceable(world, x, y-dist, z))
				{
					//Found a solid block and check if it is solid on top. If it is, then we allow gen and break. 
					//Otherwise we stop scanning and dont allow gen.
					if (world.getBlock(x, y - dist, z).isSideSolid(world, x, y - dist, z, ForgeDirection.UP))
					{
						shouldGen = true;
						break;
					}
					else
					{
						break;
					}
				}
			}
			if (itemstack.stackSize >= dist - 1)
				for(int j = dist-1; j >= 1 && shouldGen; j--)
				{
					if(world.getBlock(x, y-j, z).isReplaceable(world, x, y-j, z))
					{
						placeBlockAt(getBlock(), itemstack, player, world, x, y-j, z, itemstack.getItemDamage(), 3);
						--itemstack.stackSize;
						world.markBlockForUpdate(x, y - j, z);
					}
					else break;
				}
		}
		else
		{
			Block b = TFCBlocks.woodSupportH;
			if(getBlock() == TFCBlocks.woodSupportV2)
				b = TFCBlocks.woodSupportH2;

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

			if (y == 255 && b.getMaterial().isSolid())
			{
				return false;
			}
			else if (world.canPlaceEntityOnSide(b, x, y, z, false, side, player, itemstack))
			{
				ForgeDirection dir = BlockWoodSupport.getSupportDirection(world, x, y, z);

				int[] dist = BlockWoodSupport.getSupportsInRangeDir(world, x, y, z, 5, false);
				int total = BlockWoodSupport.getDistanceFromDirection(dir, dist);
				if(total == Integer.MAX_VALUE)
				{
					total = 1;
					dir = ForgeDirection.getOrientation(side);
				}
				if(itemstack.stackSize < total)
					return false;
				int i1 = this.getMetadata(itemstack.getItemDamage());
				int count = 0;
				while(true)
				{
					int j1 = b.onBlockPlaced(world, x+(dir.offsetX*count), y, z+(dir.offsetZ*count), side, hitX, hitY, hitZ, i1);
					if (placeBlockAt(b, itemstack, player, world, x+(dir.offsetX*count), y, z+(dir.offsetZ*count), j1, 2))
					{
						world.playSoundEffect((float) x + dir.offsetX * count + 0.5F, y + 0.5F, (float) z + dir.offsetZ * count + 0.5F, b.stepSound.func_150496_b(), (b.stepSound.getVolume() + 1.0F) / 2.0F, b.stepSound.getPitch() * 0.8F);
						--itemstack.stackSize;
					}
					count++;
					if(count >= total)
						break;
				}

				return true;
			}
		}
		return false;
	}

	public boolean placeBlockAt(Block b, ItemStack is, EntityPlayer player, World world, int x, int y, int z, int metadata, int flag)
	{
		if (!world.setBlock(x, y, z, b, metadata, flag))
		{
			return false;
		}

		if (world.getBlock(x, y, z) == b)
		{
			b.onBlockPlacedBy(world, x, y, z, player, is);
			b.onPostBlockPlaced(world, x, y, z, metadata);
		}

		return true;
	}
}
