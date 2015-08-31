package com.bioxx.tfc.Items;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;

public class ItemCustomNameTag extends ItemTerra
{
	public ItemCustomNameTag()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		setUnlocalizedName("Nametag");
		setCreativeTab(TFCTabs.TFC_TOOLS);
		setFolder("tools/");
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}
	
	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		return Items.name_tag.getIcon(stack, pass);
	}
	
	@Override
	public IIcon getIconFromDamage(int damage)
	{
		return Items.name_tag.getIconFromDamage(damage);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack stack, World world, EntityPlayer player)
	{

		if(stack.stackTagCompound == null)
		{
			stack.stackTagCompound = new NBTTagCompound();
		}
		if(stack.stackTagCompound != null && !stack.stackTagCompound.hasKey("ItemName"))
		{
			player.openGui(TerraFirmaCraft.instance, 48, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
		}

		return stack;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		//Don't
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		return false;
	}

	@Override
	public String getItemStackDisplayName(ItemStack is)
	{
		if(is.hasTagCompound() && is.stackTagCompound.hasKey("ItemName"))
			return is.stackTagCompound.getString("ItemName");
		else return TFC_Core.translate("gui.Nametag");
	}
}
