package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.API.Constant.Global;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Util.StringUtil;

public class ItemLooseRock extends ItemTerra
{
	Icon[] icons;
	Item specialCraftingType;
	ItemStack specialCraftingTypeAlternate;
	public ItemLooseRock(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFCMaterials);
		this.MetaNames = Global.STONE_ALL;
		icons = new Icon[MetaNames.length];
	}

	@Override
	public ItemTerra setMetaNames(String[] metanames)
	{
		MetaNames = metanames;
		if(metanames != null)
			icons = new Icon[MetaNames.length];
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

	public ItemTerra setSpecialCraftingType(Item i, ItemStack j)
	{
		specialCraftingType = i;
		specialCraftingTypeAlternate = j;
		return this;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		pi.specialCraftingType = new ItemStack(specialCraftingType, 1, itemstack.getItemDamage());
		if(specialCraftingTypeAlternate != null)
			pi.specialCraftingTypeAlternate = specialCraftingTypeAlternate;
		else
			pi.specialCraftingTypeAlternate = null;

		if(itemstack.stackSize > 1)
		{
			itemstack.stackSize--;
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
			arraylist.add(StringUtil.localize("gui.LooseRock.Inst0"));
		}
		else
		{
			arraylist.add(StringUtil.localize("gui.ShowHelp"));
		}
	}


	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{

	}

	@Override
	public Icon getIconFromDamage(int meta)
	{        
		return icons[meta];
	}


	@Override
	public void registerIcons(IconRegister registerer)
	{
		for(int i = 0; i < MetaNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "rocks/" + MetaNames[i] + " Rock");
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
