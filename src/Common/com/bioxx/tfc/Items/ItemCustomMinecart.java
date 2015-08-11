package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.block.BlockRailBase;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Entities.EntityCustomMinecart;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemCustomMinecart extends ItemTerra
{
	public int minecartType;

	public ItemCustomMinecart(int par2)
	{
		super();
		this.maxStackSize = 1;
		this.minecartType = par2;
		this.setCreativeTab(CreativeTabs.tabTransport);
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.HUGE);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon("minecart_chest");
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (BlockRailBase.func_150051_a(world.getBlock(x, y, z)))
		{
			if (!world.isRemote)
			{
				world.spawnEntityInWorld(new EntityCustomMinecart(world, x + 0.5F, y + 0.5F, z + 0.5F));
			}

			--itemstack.stackSize;
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public boolean canStack() 
	{
		return false;
	}
}
