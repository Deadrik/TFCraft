package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Constant.Global;

public class ItemMudBrick extends ItemTerra
{
	private IIcon overlayIcon;
	public ItemMudBrick() 
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setFolder("pottery/");
		this.setCreativeTab(null);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < Global.STONE_ALL.length; i++)
			list.add(new ItemStack(this,1,i));
		for(int i = 0; i < Global.STONE_ALL.length; i++)
			list.add(new ItemStack(this,1,i+32));
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		overlayIcon = registerer.registerIcon(Reference.MOD_ID+":"+this.textureFolder+"Mud Brick Overlay");
		super.registerIcons(registerer);
	}

	@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		if((itemstack.getItemDamage() & 32) > 0)
			return "item.Wet Mud Brick";
		return super.getUnlocalizedName(itemstack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIconFromDamageForRenderPass(int meta, int pass)
	{
		return pass == 1 ? overlayIcon : super.getIconFromDamageForRenderPass(meta, pass);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int getColorFromItemStack(ItemStack is, int pass)
	{
		int color = 0xffffff;
		if(pass == 0)
		{
			switch(is.getItemDamage() & 31)
			{
			case 0:
				color = 0xa7a090; break;//Granite
			case 1:
				color = 0x8c8b89; break;//Diorite
			case 2:
				color = 0x5b5451; break;//Gabbro
			case 3:
				color = 0x84807c; break;//Shale
			case 4:
				color = 0xccb39a; break;//Claystone
			case 5:
				color = 0xd4d0cb; break;//Rock Salt
			case 6:
				color = 0xc5ba9f; break;//Limestone
			case 7:
				color = 0xbbae94; break;//Conglomerate
			case 8:
				color = 0x736f72; break;//Dolomite
			case 9:
				color = 0x845e4a; break;//Chert
			case 10:
				color = 0xd0ccc5; break;//Chalk
			case 11:
				color = 0x9a948a; break;//Rhyolite
			case 12:
				color = 0x545758; break;//Basalt
			case 13:
				color = 0x797e7d; break;//Andesite
			case 14:
				color = 0x8c8b8b; break;//Dacite
			case 15:
				color = 0xcebdc1; break;//Quartzite
			case 16:
				color = 0xa89d8c; break;//Slate
			case 17:
				color = 0x9c918d; break;//Phyllite
			case 18:
				color = 0x999d88; break;//Schist
			case 19:
				color = 0x929181; break;//Gneiss
			case 20:
				color = 0xd2d1d0; break;//Marble
			}
		}
		if(is.getItemDamage() < 32)
			color -= 0x222222;
		return color;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		super.addInformation(is, player, arraylist, flag);
		if ((is.getItemDamage() & 31) < 21)
			arraylist.add(EnumChatFormatting.DARK_GRAY + Global.STONE_ALL[is.getItemDamage() & 31]);
		else
			arraylist.add(EnumChatFormatting.DARK_RED + "Unknown: " + is.getItemDamage());
	}
}
