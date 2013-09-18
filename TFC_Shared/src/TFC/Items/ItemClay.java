package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.API.TFCTabs;
import TFC.API.Util.StringUtil;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;

public class ItemClay extends ItemLooseRock
{

	public ItemClay(int id) 
	{
		super(id);
		this.setCreativeTab(TFCTabs.TFCPottery);
		this.icons = new Icon[2];
	}


	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
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

			itemstack.stackSize-=5;
			entityplayer.openGui(TerraFirmaCraft.instance, 28, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		}
		return itemstack;

	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{		
		if (TFC_Core.showExtraInformation()) 
		{
			arraylist.add(StringUtil.localize("gui.Help"));
			arraylist.add(StringUtil.localize("gui.Clay.Inst0"));
		}
		else
		{
			arraylist.add(StringUtil.localize("gui.ShowHelp"));
		}
	}

	@Override
	public Icon getIconFromDamage(int meta)
	{        
		return icons[meta];
	}	

	@Override
	public void registerIcons(IconRegister registerer)
	{
		icons[0] = registerer.registerIcon(Reference.ModID + ":" + "Clay");
		icons[1] = registerer.registerIcon(Reference.ModID + ":" + "Fire Clay");
	}
}
