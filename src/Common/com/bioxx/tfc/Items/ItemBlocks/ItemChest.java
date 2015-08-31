package com.bioxx.tfc.Items.ItemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.TileEntities.TEChest;
import com.bioxx.tfc.api.Constant.Global;

public class ItemChest extends ItemTerraBlock
{
	public ItemChest(Block par1) 
	{
		super(par1);
		setHasSubtypes(true);
		this.metaNames = Global.WOOD_ALL;
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{

		if (!world.setBlock(x, y, z, field_150939_a, 0, 3))
		{
			return false;
		}

		if (world.getBlock(x, y, z) == field_150939_a)
		{
			field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			field_150939_a.onPostBlockPlaced(world, x, y, z, 0);

			TEChest chest = (TEChest) world.getTileEntity(x, y, z);
			if(metadata >= Global.WOOD_ALL.length)
			{
				metadata /= 2;
				chest.isDoubleChest = true;
			}
			chest.type = metadata;

		}

		return true;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}