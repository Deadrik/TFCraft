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

import net.minecraftforge.common.ForgeHooks;

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
		this.setCreativeTab(TFCTabs.TFC_TOOLS);
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

		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
		addExtraInformation(is, player, arraylist);
	}

	public static void addSmithingBonusInformation(ItemStack is, List<String> arraylist)
	{
		if (AnvilManager.getDurabilityBuff(is) > 0)
			arraylist.add(TFC_Core.translate("gui.SmithingBonus") + " : +" + Helper.roundNumber(AnvilManager.getDurabilityBuff(is) * 100, 10) + "%");
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
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + "tools/" + this.getUnlocalizedName().replace("item.", ""));
		if (TFC_Textures.brokenItem == null) TFC_Textures.brokenItem = registerer.registerIcon(Reference.MOD_ID + ":" + "tools/Broken Item");
		if (TFC_Textures.wip == null) TFC_Textures.wip = registerer.registerIcon(Reference.MOD_ID + ":" + "wip");
	}

	@Override
	public boolean doesContainerItemLeaveCraftingGrid(ItemStack ItemStack)
	{
		return false;
	}

	@Override
	public boolean getShareTag()
	{
		return true;
	}

	@Override
	public ItemStack getContainerItem(ItemStack itemStack)
	{
		ItemStack container = itemStack.copy();
		container.setItemDamage(container.getItemDamage() + 1);
		container.stackSize = 1;
		return container;
	}

	@Override
	public boolean hasContainerItem(ItemStack stack)
	{
		return true;
	}

	@Override
	public boolean isRepairable()
	{
		return false;
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
	public float getDigSpeed(ItemStack stack, Block block, int meta)
	{
		float digSpeed = super.getDigSpeed(stack, block, meta);

		if (ForgeHooks.isToolEffective(stack, block, meta))
		{
			return digSpeed + (digSpeed * AnvilManager.getDurabilityBuff(stack));
		}
		return digSpeed;
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
			return TFC_Textures.brokenItem;
		else
			return getIconFromDamageForRenderPass(stack.getItemDamage(), pass);
	}
}
