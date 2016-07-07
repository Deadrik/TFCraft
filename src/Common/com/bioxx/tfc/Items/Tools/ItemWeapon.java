package com.bioxx.tfc.Items.Tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.minecraftforge.common.ForgeHooks;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ICausesDamage;
import com.bioxx.tfc.api.Interfaces.ISize;
import com.bioxx.tfc.api.Util.Helper;
import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemWeapon extends ItemSword implements ISize, ICausesDamage
{
	private float weaponBaseDamage;
	private final ToolMaterial toolMat;
	public EnumDamageType damageType = EnumDamageType.SLASHING;

	public ItemWeapon(ToolMaterial par2, float damage)
	{
		super(par2);
		this.setMaxDamage(par2.getMaxUses());
		weaponBaseDamage = damage;
		this.toolMat = par2;
		setCreativeTab(TFCTabs.TFC_WEAPONS);
		setNoRepair();
	}

	@Override
	public float func_150931_i()
	{
		return this.toolMat.getDamageVsEntity();
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

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		//Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;

		ItemTerra.addSizeInformation(is, arraylist);
		ItemTerra.addHeatInformation(is, arraylist);

		if(is.getItem() instanceof ICausesDamage)
			arraylist.add(EnumChatFormatting.AQUA + TFC_Core.translate(((ICausesDamage) this).getDamageType().toString()));
		ItemTerraTool.addSmithingBonusInformation(is, arraylist);
		addExtraInformation(is, player, arraylist);
	}

	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + "tools/"+this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		MovingObjectPosition mop = Helper.getMouseOverObject(player, player.worldObj);

		if(mop != null && world.getBlock(mop.blockX, mop.blockY, mop.blockZ) == TFCBlocks.toolRack)
			return is;

		player.setItemInUse(is, this.getMaxItemUseDuration(is));
		return is;
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
	public EnumDamageType getDamageType() 
	{
		return damageType;
	}

	public double getWeaponDamage(ItemStack is)
	{
		return Math.floor(weaponBaseDamage + (weaponBaseDamage * AnvilManager.getDamageBuff(is)));
	}

	@Override
	public Multimap getAttributeModifiers(ItemStack stack)
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", getWeaponDamage(stack), 0));
		return multimap;
	}

	@Override
	public int getMaxDamage(ItemStack is)
	{
		return (int) Math.floor(getMaxDamage() + (getMaxDamage() * AnvilManager.getDurabilityBuff(is)));
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
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.MEDIUM;
	}
}
