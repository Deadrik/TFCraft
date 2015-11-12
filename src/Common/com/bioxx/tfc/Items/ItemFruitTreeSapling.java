package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEFruitTreeWood;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;

public class ItemFruitTreeSapling extends ItemTerra
{
	private IIcon[] icons;

	public ItemFruitTreeSapling()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(TFCTabs.TFC_FOODS);
		this.metaNames = Global.FRUIT_META_NAMES;
		this.icons = new IIcon[metaNames.length];
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		//int meta = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
		if(side == 1 && world.getBlock(x, y, z).isNormalCube() && world.getBlock(x, y, z).isOpaqueCube() &&
				TFC_Core.isSoil(world.getBlock(x, y, z)) &&
				world.isAirBlock(x, y + 1, z) && !world.isRemote)
		{

			int damage = stack.getItemDamage();
			if (damage >= metaNames.length)
			{
				damage -= 8;
				stack.setItemDamage(damage);
			}
			world.setBlock(x, y + 1, z, TFCBlocks.fruitTreeWood, damage, 0x2);

			((TEFruitTreeWood)world.getTileEntity(x, y + 1, z)).setTrunk(true);
			((TEFruitTreeWood)world.getTileEntity(x, y + 1, z)).setHeight(0);
			((TEFruitTreeWood)world.getTileEntity(x, y + 1, z)).initBirth();

			stack.stackSize = stack.stackSize - 1;
			return true;
		}

		return false;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < metaNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + metaNames[i] + " Sapling");
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	// Check for and correct invalid saplings from prior plum sapling bug
	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconIndex(ItemStack par1ItemStack)
	{
		int damage = par1ItemStack.getItemDamage();
		if (damage >= metaNames.length)
		{
			damage -= 8;
			par1ItemStack.setItemDamage(damage);
		}
		return this.getIconFromDamage(damage);
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
	}
}
