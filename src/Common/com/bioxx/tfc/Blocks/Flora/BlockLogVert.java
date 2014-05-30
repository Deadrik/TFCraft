package com.bioxx.tfc.Blocks.Flora;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Blocks.BlockTerra;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogVert extends BlockTerra
{
	String[] woodNames;
	public BlockLogVert()
	{
		super(Material.wood);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		//we need to make sure teh palyer has the correct tool out
		boolean isAxeorSaw = false;
		boolean isHammer = false;
		ItemStack equip = entityplayer.getCurrentEquippedItem();
		if(equip!=null)
		{
			for(int cnt = 0; cnt < Recipes.Axes.length && !isAxeorSaw; cnt++)
			{
				if(equip.getItem() == Recipes.Axes[cnt])
					isAxeorSaw = true;
			}

			for(int cnt = 0; cnt < Recipes.Saws.length && !isAxeorSaw; cnt++)
			{
				if(equip.getItem() == Recipes.Saws[cnt])
					isAxeorSaw = true;
			}

			for(int cnt = 0; cnt < Recipes.Hammers.length && !isAxeorSaw; cnt++)
			{
				if(equip.getItem() == Recipes.Hammers[cnt])
					isHammer = true;
			}
		}

		if(isAxeorSaw)
		{
			super.harvestBlock(world, entityplayer, x, y, z, meta);
		}
		else if(isHammer)
		{
			EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(TFCItems.Stick, 1 + world.rand.nextInt(3)));
			world.spawnEntityInWorld(item);
		}
		else
		{
			world.setBlock(x, y, z, this, meta, 0x2);
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}	

	@Override
	public Item getItemDropped(int i, Random R, int j)
	{
		return TFCItems.Logs;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if (side == 1)
			return BlockLogNatural.innerIcons[meta];
		if (side == 0)
			return BlockLogNatural.innerIcons[meta];
		return BlockLogNatural.sideIcons[meta];
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
	}
}
