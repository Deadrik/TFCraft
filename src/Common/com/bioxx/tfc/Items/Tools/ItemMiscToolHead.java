package com.bioxx.tfc.Items.Tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISmeltable;

public class ItemMiscToolHead extends ItemTerra implements ISmeltable
{
	private ToolMaterial material;

	public ItemMiscToolHead()
	{
		super();
		this.setMaxDamage(100);
		this.setMaxStackSize(4);
		setCreativeTab(TFCTabs.TFC_MISC);
		this.setFolder("toolheads/");
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.SMALL);
	}

	public ItemMiscToolHead(ToolMaterial m)
	{
		this();
		material = m;
	}

	public ToolMaterial getMaterial()
	{
		return material;
	}
	
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		String name = this.getUnlocalizedName().replace("item.", "");
		name = name.replace("IgIn ", "");
		name = name.replace("IgEx ", "");
		name = name.replace("Sed ", "");
		name = name.replace("MM ", "");
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + this.textureFolder + name);
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
	}

	@Override
	public Metal getMetalType(ItemStack is)
	{
		if (this == TFCItems.wroughtIronKnifeHead)
			return Global.WROUGHTIRON;
		else
			return null;
	}

	@Override
	public short getMetalReturnAmount(ItemStack is)
	{
		if (this == TFCItems.wroughtIronKnifeHead)
			return 100;
		else
			return 0;
	}

	@Override
	public boolean isSmeltable(ItemStack is)
	{
		return this == TFCItems.wroughtIronKnifeHead;
	}

	@Override
	public EnumTier getSmeltTier(ItemStack is)
	{
		return EnumTier.TierIII;
	}
}
