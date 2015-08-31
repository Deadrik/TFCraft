package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

public class ItemUnfinishedArmor extends ItemTerra implements ISmeltable
{
	public int metalID;
	private String metal;
	private short metalAmount;
	private short metalAmount2;
	private boolean smeltable = true;
	public ItemUnfinishedArmor() 
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFC_MISC);
		this.setFolder("armor/");
		this.setSize(EnumSize.LARGE);
	}

	public ItemUnfinishedArmor(String tex)
	{
		super();
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", "").replace("Unfinished ", "").replace("Stage2 ", ""));
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		if(is.getItemDamage() == 0)
			arraylist.add(TFC_Core.translate("gui.stage1"));
		else
			arraylist.add(TFC_Core.translate("gui.stage2"));
	}

	@Override
	public String getItemStackDisplayName(ItemStack itemstack)
	{
		return new StringBuilder().append(super.getItemStackDisplayName(itemstack)).toString();
	}

	/*@Override
	public String getUnlocalizedName(ItemStack itemstack)
	{
		return super.getUnlocalizedName(itemstack);
	}*/

	@Override
	public int getItemStackLimit()
	{
		if(canStack()) {
			return this.getSize(null).stackSize;
		} else {
			return 1;
		}
	}

	@Override
	public boolean canStack() 
	{
		return true;
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		if (metal == null)
		{
			return MetalRegistry.instance.getMetalFromItem(this);
		}
		else
		{
			return MetalRegistry.instance.getMetalFromString(metal);
		}
	}

	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		if(is.getItemDamage() == 1)
			return metalAmount2;
		return metalAmount;
	}

	@Override
	public boolean isSmeltable(ItemStack is)
	{
		return smeltable;
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is)
	{
		return EnumTier.TierI;
	}

	public ItemTerra setMetal(String m, int slot)
	{
		metal = m;
		if(slot == 0)
		{
			this.metalAmount = 200;
			this.metalAmount2 = 400;
		}
		else if(slot == 1)
		{
			this.metalAmount = 400;
			this.metalAmount2 = 800;
		}
		else if(slot == 2)
		{
			this.metalAmount = 400;
			this.metalAmount2 = 600;
		}
		else if(slot == 3)
		{
			this.metalAmount = 200;
			this.metalAmount2 = 200;
		}
		return this;
	}
}
