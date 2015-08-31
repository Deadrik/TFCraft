package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Reference;

public class ItemGem extends ItemTerra
{
	private IIcon[] icons = new IIcon[5];
	public ItemGem() 
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		this.metaNames = new String[]{"Chipped", "Flawed", "Normal", "Flawless", "Exquisite"};
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this,1, 0));
		list.add(new ItemStack(this,1, 1));
		list.add(new ItemStack(this,1, 2));
		list.add(new ItemStack(this,1, 3));
		list.add(new ItemStack(this,1, 4));
	}

	@Override
	public IIcon getIconFromDamage(int i)
	{
		return icons[i];
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
    {
		icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "gems/"+metaNames[0] + " " + getUnlocalizedName().replace("item.", ""));
		icons[1] = registerer.registerIcon(Reference.MOD_ID + ":" + "gems/"+metaNames[1] + " " + getUnlocalizedName().replace("item.", ""));
		icons[2] = registerer.registerIcon(Reference.MOD_ID + ":" + "gems/"+metaNames[2] + " " + getUnlocalizedName().replace("item.", ""));
		icons[3] = registerer.registerIcon(Reference.MOD_ID + ":" + "gems/"+metaNames[3] + " " + getUnlocalizedName().replace("item.", ""));
		icons[4] = registerer.registerIcon(Reference.MOD_ID + ":" + "gems/"+metaNames[4] + " " + getUnlocalizedName().replace("item.", ""));
    }

}
