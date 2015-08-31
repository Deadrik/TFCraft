package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;

public class ItemClay extends ItemLooseRock
{
	public ItemClay()
	{
		super();
		this.setCreativeTab(TFCTabs.TFC_POTTERY);
		this.icons = new IIcon[2];
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		//TerraFirmaCraft.log.info(itemstack.stackSize+", "+itemstack.getItem().getClass() +": "+Items.clay_ball.getClass());
		if(itemstack.stackSize >= 5)
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
			pi.specialCraftingType = new ItemStack(specialCraftingType, 1, 0);

			if(specialCraftingTypeAlternate != null)
				pi.specialCraftingTypeAlternate = specialCraftingTypeAlternate;

			if(itemstack.getItemDamage() == 1)
			{
				pi.specialCraftingType = new ItemStack(specialCraftingType, 1, 2);
				pi.specialCraftingTypeAlternate = new ItemStack(specialCraftingType, 1, 3);
			}

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
			arraylist.add(TFC_Core.translate("gui.Clay.Inst0"));
		}
		else
		{
			arraylist.add(TFC_Core.translate("gui.ShowHelp"));
		}
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		icons[0] = registerer.registerIcon(Reference.MOD_ID + ":" + "Clay");
		icons[1] = registerer.registerIcon(Reference.MOD_ID + ":" + "Fire Clay");
	}
}
