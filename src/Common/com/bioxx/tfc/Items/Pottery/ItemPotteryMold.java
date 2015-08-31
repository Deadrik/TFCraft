package com.bioxx.tfc.Items.Pottery;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;

public class ItemPotteryMold extends ItemPotteryBase
{
	private IIcon copperIcon;
	private IIcon bronzeIcon;
	private IIcon bismuthBronzeIcon;
	private IIcon blackBronzeIcon;

	public ItemPotteryMold()
	{
		super();
		this.setMaxDamage(401);
	}

	@Override
	public boolean isDamageable()
	{
		return this.getMaxDamage() > 0;
	}
	
	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{		
		if (is.getItemDamage() > 5)
		{
			int units = 100 - ((is.getItemDamage() - 2) / 4);
			arraylist.add(TFC_Core.translate("gui.units") + ": " + units + " / 100");
		}
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return stack.getItemDamage() > 5;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.clayIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[0]);
		this.ceramicIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[1]);
		if(metaNames.length > 2)
		{
			copperIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[2]);
			bronzeIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[3]);
			bismuthBronzeIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[4]);
			blackBronzeIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[5]);
		}
	}
	@Override
	public String getUnlocalizedName(ItemStack par1ItemStack)
	{
		if(par1ItemStack !=null && par1ItemStack.getItemDamage() > 5)
		{
			int damage = (par1ItemStack.getItemDamage() - 2) % 4 + 2;
			return super.getUnlocalizedName(par1ItemStack) + "." + metaNames[damage];
		}
		return super.getUnlocalizedName(par1ItemStack);
	}

	@Override
	public IIcon getIconFromDamage(int damage)
	{
		if(damage > 5)
		{
			damage = (damage - 2) % 4 + 2;
		}
		if(damage == 0) return this.clayIcon;
		else if(damage == 1) return this.ceramicIcon;
		else if(damage == 2) return this.copperIcon;
		else if(damage == 3) return this.bronzeIcon;
		else if(damage == 4) return this.bismuthBronzeIcon;
		else if(damage == 5) return this.blackBronzeIcon;

		return this.clayIcon;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(item, 1, 0));
		list.add(new ItemStack(item, 1, 1));
	}
}
