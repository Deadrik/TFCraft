package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
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
		this.MetaNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, MetaNames, 0, 16);
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

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if((field_150939_a == TFCBlocks.WoodSupportV || field_150939_a == TFCBlocks.WoodSupportV2) && side == 1 && 
					world.getBlock(x, y, z) != TFCBlocks.WoodSupportH && world.getBlock(x, y, z) != TFCBlocks.WoodSupportH2)
			{
				if(!player.isSneaking() && world.isAirBlock(x, y+1, z) && world.isAirBlock(x, y+2, z) && world.isAirBlock(x, y+3, z) && itemstack.stackSize >= 3 &&
						world.getBlock(x, y, z) != TFCBlocks.WoodSupportV && world.getBlock(x, y, z) != TFCBlocks.WoodSupportV2 )
				{
					world.setBlock(x, y+1, z, field_150939_a/*The Block*/, itemstack.getItemDamage(), 0x2);
					world.setBlock(x, y+2, z, field_150939_a/*The Block*/, itemstack.getItemDamage(), 0x2);
					world.setBlock(x, y+3, z, field_150939_a/*The Block*/, itemstack.getItemDamage(), 0x2);
					itemstack.stackSize-=3;
					return true;
				}
				else if((player.isSneaking() && world.isAirBlock(x, y+1, z)) ||
						(!player.isSneaking()&&(world.getBlock(x, y, z) == TFCBlocks.WoodSupportV || world.getBlock(x, y, z) == TFCBlocks.WoodSupportV2)))
				{
					world.setBlock(x, y+1, z, field_150939_a/*The Block*/, itemstack.getItemDamage(), 0x2);
					itemstack.stackSize--;
					return true;
				}
			}
			else if(side != 1)
			{
				Block b = TFCBlocks.WoodSupportH;
				if(field_150939_a == TFCBlocks.WoodSupportV2)
					b = TFCBlocks.WoodSupportH2;

				if (side == 0)
				{
					--y;
				}
				else if (side == 1)
				{
					++y;
				}
				else if (side == 2)
				{
					--z;
				}
				else if (side == 3)
				{
					++z;
				}
				else if (side == 4)
				{
					--x;
				}
				else if (side == 5)
				{

					++x;
				}
				if (y == 255 && b.getMaterial().isSolid())
				{
					return false;
				}
				else if (world.canPlaceEntityOnSide(b, x, y, z, false, side, player, itemstack))
				{
					int i1 = this.getMetadata(itemstack.getItemDamage());
					int j1 = b.onBlockPlaced(world, x, y, z, side, hitX, hitY, hitZ, i1);

					if (placeBlockAt(b, itemstack, player, world, x, y, z, j1))
					{
						world.playSoundEffect((double)((float)x + 0.5F), (double)((float)y + 0.5F), (double)((float)z + 0.5F), b.stepSound.func_150496_b(), (b.stepSound.getVolume() + 1.0F) / 2.0F, b.stepSound.getPitch() * 0.8F);
						--itemstack.stackSize;
					}

					return true;
				}
			}
		}
		return false;
	}

	public boolean placeBlockAt(Block b, ItemStack is, EntityPlayer player, World world, int x, int y, int z, int metadata)
	{
		if (!world.setBlock(x, y, z, b, metadata, 3))
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
