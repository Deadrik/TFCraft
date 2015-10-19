package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemMeltedMetal extends ItemTerra
{
	public ItemMeltedMetal() 
	{
		super();
		setMaxDamage(101);
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.setFolder("ingots/");
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
	}	

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder+this.getUnlocalizedName().replace("item.", "").replace("Weak ", "").replace("HC ", ""));
	}

	@Override
	public int getItemStackLimit(ItemStack is)
	{
		// Partially-filled and hot unshaped ingots cannot stack
		if (isDamaged(is) || is.hasTagCompound() && TFC_ItemHeat.hasTemp(is))
		{
			return 1;
		}

		return super.getItemStackLimit(is);
	}

	@Override
	public void addItemInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{		
		if (is.getItemDamage() > 1)
		{
			arraylist.add(TFC_Core.translate("gui.units") + ": " + (100 - is.getItemDamage()) + " / 100");
		}
	}

	@Override
	public void onUpdate(ItemStack is, World world, Entity entity, int i, boolean isSelected) 
	{
		super.onUpdate(is,world,entity,i,isSelected);
		if (is.hasTagCompound())
		{
			//NBTTagCompound stackTagCompound = is.getTagCompound();
			if(TFC_ItemHeat.hasTemp(is) && TFC_ItemHeat.getTemp(is) >= TFC_ItemHeat.isCookable(is))
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
		if (TFC_Core.showShiftInformation())
		{
			arraylist.add(TFC_Core.translate("gui.Help"));
			arraylist.add(TFC_Core.translate("gui.MeltedMetal.Inst0"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(itemstack.stackSize <= 0) {
			itemstack.stackSize = 1;
		}

		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		pi.specialCraftingType = itemstack.copy();

		entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
		entityplayer.openGui(TerraFirmaCraft.instance, 38, world, (int) entityplayer.posX, (int) entityplayer.posY, (int) entityplayer.posZ);

		return itemstack;
	}
}
