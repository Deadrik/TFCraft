package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TileEntities.TileEntityFruitTreeWood;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemFruitTreeSapling extends ItemTerra
{
	private IIcon[] icons;

	public ItemFruitTreeSapling()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.MetaNames = Global.FRUIT_META_NAMES;
		this.icons = new IIcon[MetaNames.length];
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int meta = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
		if(side == 1 && world.getBlock(x, y, z).isNormalCube() && world.getBlock(x, y, z).isOpaqueCube() &&
				(world.getBlock(x, y, z).getMaterial() == Material.grass || world.getBlock(x, y, z).getMaterial() == Material.ground) &&
				world.isAirBlock(x, y + 1, z) && !world.isRemote)
		{

			world.setBlock(x, y + 1, z, TFCBlocks.fruitTreeWood, stack.getItemDamage(), 0x2);

			((TileEntityFruitTreeWood)world.getTileEntity(x, y + 1, z)).setTrunk(true);
			((TileEntityFruitTreeWood)world.getTileEntity(x, y + 1, z)).setHeight(0);
			((TileEntityFruitTreeWood)world.getTileEntity(x, y + 1, z)).setBirth();

			stack.stackSize = stack.stackSize - 1;
			return true;
		}

		return false;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < MetaNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/" + MetaNames[i] + " Sapling");
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
		if (damage >= MetaNames.length)
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
