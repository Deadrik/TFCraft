package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemCoal extends ItemTerra
{
	public ItemCoal()
	{
		super();
		this.setHasSubtypes(true);
		this.setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.metaNames = new String[] {"coal", "charcoal"};
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.TINY);
	}

	private int[][] map =
		{   {0,-1,0},
			{0,1,0},
			{0,0,-1},
			{0,0,1},
			{-1,0,0},
			{1,0,0},
		};

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(is.getItemDamage() == 1 && !world.isRemote)
		{
			if(world.getBlock(x, y, z) == TFCBlocks.charcoal)
			{
				int meta = world.getBlockMetadata(x, y, z);
				if(meta < 8)
				{
					world.setBlockMetadataWithNotify(x, y, z, meta + 1, 3);
					is.stackSize--;
					return true;
				}
				else if(side == 1 && world.isAirBlock(x + map[side][0], y + map[side][1], z + map[side][2]))
				{
					world.setBlock(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.charcoal, 1, 0x2);
					is.stackSize--;
					return true;
				}
			}

			if(world.getBlock(x + map[side][0], y + map[side][1], z + map[side][2]) == TFCBlocks.charcoal)
			{
				int meta = world.getBlockMetadata(x + map[side][0], y + map[side][1], z + map[side][2]);
				if(meta < 8)
				{
					world.setBlockMetadataWithNotify(x + map[side][0], y + map[side][1], z + map[side][2], meta + 1, 3);
					is.stackSize--;
					return true;
				}
			}

			if(world.isAirBlock(x + map[side][0], y + map[side][1], z + map[side][2]))
			{
				world.setBlock(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.charcoal, 1, 0x2);
				is.stackSize--;
				TFCBlocks.charcoal.onNeighborBlockChange(world, x + map[side][0], y + map[side][1], z + map[side][2], world.getBlock(x + map[side][0], y + map[side][1], z + map[side][2]));
			}
			return true;
		}
		return false;
	}
}
