package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ItemUnfinishedArmor extends ItemTerra
{
	public ItemUnfinishedArmor() 
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFCMisc);
		this.setFolder("armor/");
		this.setSize(EnumSize.LARGE);
	}

	public ItemUnfinishedArmor(String tex)
	{
		super();
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", "").replace("Unfinished ", "").replace("Stage2 ", ""));
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if(is.getItemDamage() == 0)
			arraylist.add(StatCollector.translateToLocal("word.stage1"));
		else arraylist.add(StatCollector.translateToLocal("word.stage2"));
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack)
	{
		String s = new StringBuilder().append(super.getItemStackDisplayName(itemstack)).toString();
		return s;
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack()) {
			return this.getSize(null).stackSize;
		} else {
			return 1;
		}
	}

	@Override
	public boolean canStack() 
	{
		return true;
	}

}
