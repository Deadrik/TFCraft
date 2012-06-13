package net.minecraft.src.TFC_Core.Items;

import net.minecraft.src.Item;
import net.minecraft.src.ItemBlock;
import net.minecraft.src.ItemStack;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraGem extends Item implements ITextureProvider
{
	public static String[] gemNames = {"Chipped", "Flawed", "Normal", "Flawless", "Exquisite"};

	public ItemTerraGem(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
	}

	public void addCreativeItems(java.util.ArrayList list)
	{
		list.add(new ItemStack(this,0, 0));
		list.add(new ItemStack(this,0, 1));
		list.add(new ItemStack(this,0, 2));
		list.add(new ItemStack(this,0, 3));
		list.add(new ItemStack(this,0, 4));
	}

	public int getIconFromDamage(int i)
	{
		return iconIndex+i;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(gemNames[itemstack.getItemDamage()]).toString();
		return s;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites.png";
	}

}
