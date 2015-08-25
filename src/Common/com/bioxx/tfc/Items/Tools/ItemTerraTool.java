package com.bioxx.tfc.Items.Tools;

import java.util.List;
import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.ISize;
import com.bioxx.tfc.api.Util.Helper;

public class ItemTerraTool extends ItemTool implements ISize
{
	//private static boolean registeredGlobalTex = false;

	public ItemTerraTool(float par2, ToolMaterial par3, Set<Block> par4)
	{
		super(par2, par3, par4);
		this.setCreativeTab(TFCTabs.TFCTools);
		setNoRepair();
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;

		ItemTerra.addSizeInformation(is, arraylist);
		ItemTerra.addHeatInformation(is, arraylist);

		if(is.getItem() instanceof ICausesDamage)
			arraylist.add(EnumChatFormatting.AQUA + TFC_Core.translate(((ICausesDamage)this).getDamageType().toString()));

		ItemTerraTool.addDurabilityInformation(is, arraylist);
		addExtraInformation(is, player, arraylist);
	}

	public static void addDurabilityInformation(ItemStack is, List<String> arraylist)
	{
		if (AnvilManager.getDurabilityBuff(is) > 0)
			arraylist.add(TFC_Core.translate("gui.Durability") + " : +" + Helper.roundNumber(AnvilManager.getDurabilityBuff(is) * 100, 10) + "%");
	}

	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "tools/" + this.getUnlocalizedName().replace("item.", ""));
		if (TFC_Textures.BrokenItem == null) TFC_Textures.BrokenItem = registerer.registerIcon(Reference.ModID + ":" + "tools/Broken Item");
		if (TFC_Textures.WIP == null) TFC_Textures.WIP = registerer.registerIcon(Reference.ModID + ":" + "wip");
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.MEDIUM;
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return (int) (getMaxDamage()+(getMaxDamage() * AnvilManager.getDurabilityBuff(stack)));
	}

	@Override
	public boolean requiresMultipleRenderPasses()
	{
		return true;
	}

	@Override
	public IIcon getIcon(ItemStack stack, int pass)
	{
		NBTTagCompound nbt = stack.getTagCompound();
		if(pass == 1 && nbt != null && nbt.hasKey("broken"))
			return TFC_Textures.BrokenItem;
		else
			return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
	}
}
