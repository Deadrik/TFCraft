package TFC.Items;

import java.util.List;

import com.google.common.collect.ObjectArrays;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.Constant.Global;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;

public class ItemLooseRock extends ItemTerra
{
	Icon[] icons;
	public ItemLooseRock(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.MetaNames = Global.STONE_ALL;
		icons = new Icon[MetaNames.length];
	}

	int[][] map = 
		{   {0,-1,0},
			{0,1,0},
			{0,0,-1},
			{0,0,1},
			{-1,0,0},
			{1,0,0},
		};

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
		pi.knappingRockType = new ItemStack(TFCItems.FlatRock, 1, itemstack.getItemDamage());
		if(itemstack.stackSize > 1)
		{
			itemstack.stackSize--;
			entityplayer.openGui(TerraFirmaCraft.instance, 28, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
		}
		return itemstack;

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
			icons[i] = registerer.registerIcon("rocks/" + MetaNames[i] + " Rock");
    }

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
