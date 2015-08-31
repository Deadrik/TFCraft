package com.bioxx.tfc.Items.ItemBlocks;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.TileEntities.TELoom;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemLooms extends ItemTerraBlock
{
	public ItemLooms(Block par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		this.metaNames = Global.WOOD_ALL;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{

		if (!world.setBlock(x, y, z, field_150939_a, metadata&15, 3))
		{
			return false;
		}

		if (world.getBlock(x, y, z) == field_150939_a)
		{
			field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			field_150939_a.onPostBlockPlaced(world, x, y, z, 0);

			if (world.getTileEntity(x, y, z) instanceof TELoom)
			{
				TELoom te = (TELoom) world.getTileEntity(x, y, z);
				if (te != null)
				{
					te.loomType = metadata;
					world.markBlockForUpdate(x, y, z);

					int l = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
					byte byte0 = 0;
					if (l == 0)//+z
						byte0 = 0;
					if (l == 1)//-x
						byte0 = 1;
					if (l == 2)//-z
						byte0 = 2;
					if (l == 3)//+x
						byte0 = 3;
					te.rotation = byte0;
				}
			}
		}
		return true;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
