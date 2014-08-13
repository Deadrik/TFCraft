package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.api.Constant.Global;

public class ItemLooseRock extends ItemTerra
{
	IIcon[] icons;
	Item specialCraftingType;
	ItemStack specialCraftingTypeAlternate;

	public ItemLooseRock() 
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFCMaterials);
		this.MetaNames = Global.STONE_ALL;
		icons = new IIcon[MetaNames.length];
	}

	@Override
	public ItemTerra setMetaNames(String[] metanames)
	{
		MetaNames = metanames;
		if(metanames != null)
			icons = new IIcon[MetaNames.length];
		return this;
	}

	public ItemTerra setSpecialCraftingType(Item i)
	{
		specialCraftingType = i;
		return this;
	}

	public ItemTerra setSpecialCraftingType(Item i, Item j)
	{
		specialCraftingType = i;
		specialCraftingTypeAlternate = new ItemStack(j);
		return this;
	}

	public ItemTerra setSpecialCraftingType(Item i, ItemStack is)
	{
		specialCraftingType = i;
		specialCraftingTypeAlternate = is;
		return this;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
		pi.specialCraftingType = new ItemStack(specialCraftingType, 1, is.getItemDamage());
		if(specialCraftingTypeAlternate != null)
			pi.specialCraftingTypeAlternate = specialCraftingTypeAlternate;
		else
			pi.specialCraftingTypeAlternate = null;

		if(is.stackSize > 1)
		{
			is.stackSize--;
			player.openGui(TerraFirmaCraft.instance, 28, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		}
		return is;

	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if (TFC_Core.showShiftInformation()) 
		{
			arraylist.add(StatCollector.translateToLocal("gui.Help"));
			arraylist.add(StatCollector.translateToLocal("gui.LooseRock.Inst0"));
		}
		else
		{
			arraylist.add(StatCollector.translateToLocal("gui.ShowHelp"));
		}
	}


	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int par4, boolean par5)
	{
		
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}


	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < MetaNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "rocks/" + MetaNames[i] + " Rock");
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++)
		{
			list.add(new ItemStack(this, 1, i));
		}
	}
}
