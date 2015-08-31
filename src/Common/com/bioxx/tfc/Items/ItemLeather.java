package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;

public class ItemLeather extends ItemLooseRock
{
	public ItemLeather() 
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.metaNames = null;
	}


	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		pi.specialCraftingType = new ItemStack(specialCraftingType, 1, itemstack.getItemDamage());

		boolean hasKnife = false;
		for(int i = 0; i < entityplayer.inventory.mainInventory.length; i++)
		{
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemKnife)
				hasKnife = true;
		}

		if(hasKnife)
		{
			if(specialCraftingTypeAlternate != null)
				pi.specialCraftingTypeAlternate = specialCraftingTypeAlternate;
			else
				pi.specialCraftingTypeAlternate = null;
			entityplayer.openGui(TerraFirmaCraft.instance, 28, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		}
		return itemstack;

	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if (TFC_Core.showShiftInformation()) 
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.Leather.Inst0"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}


	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{

	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{        
		return this.itemIcon;
	}


	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this,1,0));
	}
}
