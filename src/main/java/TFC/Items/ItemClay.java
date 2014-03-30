package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;

public class ItemClay extends ItemLooseRock
{

	public ItemClay() 
	{
		super();
		this.setCreativeTab(TFCTabs.TFCPottery);
		this.icons = new IIcon[2];
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
			arraylist.add(StatCollector.translateToLocal("gui.Help"));
			arraylist.add(StatCollector.translateToLocal("gui.Clay.Inst0"));
		}
		else
		{
			arraylist.add(StatCollector.translateToLocal("gui.ShowHelp"));
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
		icons[0] = registerer.registerIcon(Reference.ModID + ":" + "Clay");
		icons[1] = registerer.registerIcon(Reference.ModID + ":" + "Fire Clay");
	}
}
