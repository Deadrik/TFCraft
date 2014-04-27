package com.bioxx.tfc.Items;

import java.util.List;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

public class ItemMeltedMetal extends ItemTerra
{

	public ItemMeltedMetal() 
	{
		super();
		setMaxDamage(101);
		setCreativeTab(TFCTabs.TFCMaterials);
		this.setFolder("ingots/");
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.SMALL);
	}	

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder+this.getUnlocalizedName().replace("item.", "").replace("Weak ", "").replace("HC ", ""));
	}

	@Override
	public boolean canStack() 
	{
		return false;
	}


	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{

		super.addInformation(is, player, arraylist, flag);

	}

	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
	{		
		if(is.getItemDamage() > 1) {
			arraylist.add(StatCollector.translateToLocal("gui.MeltedMetal.NotFull"));
		}
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		super.onUpdate(is,world,entity,i,isSelected);
		if (is.hasTagCompound())
		{
			NBTTagCompound stackTagCompound = is.getTagCompound();
			if(TFC_ItemHeat.HasTemp(is) && TFC_ItemHeat.GetTemp(is) >= TFC_ItemHeat.IsCookable(is))
			{
				if(is.getItemDamage()==0){
					is.setItemDamage(1);
				}
			}
			else if(is.getItemDamage()==1){
				is.setItemDamage(0);
			}

		}
		else if(is.getItemDamage()==1){
			is.setItemDamage(0);
		}
	}

	@Override
	public boolean isDamaged(ItemStack stack)
	{
		return stack.getItemDamage() > 1;
	}
	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{	
		if(TFC_ItemHeat.getIsLiquid(is) && (is.getItem() == TFCItems.BronzeUnshaped || is.getItem() == TFCItems.BismuthBronzeUnshaped ||
				is.getItem() == TFCItems.BlackBronzeUnshaped || is.getItem() == TFCItems.CopperUnshaped))
		{
			if (TFC_Core.showExtraInformation()) 
			{
				arraylist.add(StatCollector.translateToLocal("gui.Help"));
				arraylist.add(StatCollector.translateToLocal("gui.MeltedMetal.Inst0"));
			}
			else
			{
				arraylist.add(StatCollector.translateToLocal("gui.ShowHelp"));
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.stackSize <= 0) {
			itemstack.stackSize = 1;
		}

		if(TFC_ItemHeat.getIsLiquid(itemstack) && (itemstack.getItem() == TFCItems.BronzeUnshaped  || itemstack.getItem() == TFCItems.BismuthBronzeUnshaped  ||
				itemstack.getItem() == TFCItems.BlackBronzeUnshaped  || itemstack.getItem() == TFCItems.CopperUnshaped))
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
			pi.specialCraftingType = itemstack.copy();

			entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
			entityplayer.openGui(TerraFirmaCraft.instance, 38, world, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		}
		return itemstack;
	}
}
