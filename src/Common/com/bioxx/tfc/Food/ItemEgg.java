package com.bioxx.tfc.Food;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Interfaces.IFood;

public class ItemEgg extends ItemFoodTFC implements IFood
{
	public ItemEgg()
	{
		super(EnumFoodGroup.Protein, 0, 0, 0, 0, 0, false, false);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(createTag(new ItemStack(this, 1), 2));
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);
		arraylist.add(getFoodGroupName(this.getFoodGroup()));
		addHeatInformation(is, arraylist);

		if(is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("Fertilized"))
				arraylist.add(EnumChatFormatting.GOLD + StatCollector.translateToLocal("gui.fertilized"));
			else
				addFoodInformation(is, player, arraylist);
		}
	}

	@Override
	public boolean onUpdate(ItemStack is, World world, int x, int y, int z)
	{
		if (is.hasTagCompound())
		{
			if(is.getTagCompound().hasKey("Fertilized"))
			{
				is.stackTagCompound.removeTag("Fertilized");
				is.stackTagCompound.removeTag("Genes");
			}
			if(is.getTagCompound().hasKey("Fertilized"))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public float getDecayRate(ItemStack is)
	{
		if(is.getTagCompound().hasKey("Pickled"))
			return 0.3f;
		return 0.5f;
	}
}
