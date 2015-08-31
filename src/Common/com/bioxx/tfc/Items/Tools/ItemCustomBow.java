package com.bioxx.tfc.Items.Tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.Player.InventoryPlayerTFC;
import com.bioxx.tfc.Entities.EntityProjectileTFC;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumAmmo;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.ISize;

public class ItemCustomBow extends ItemBow implements ISize
{
	private String[] bowPullIconNameArray = new String[] {"pulling_0", "pulling_1", "pulling_2", "pulling_3"};
	private IIcon[] iconArray;

	public ItemCustomBow()
	{
		super();
		this.maxStackSize = 1;
		this.setMaxDamage(384);
		setCreativeTab(TFCTabs.TFC_WEAPONS);
		setNoRepair();
	}

	public boolean consumeArrowInQuiver(EntityPlayer player, boolean shouldConsume)
	{
		ItemStack quiver = 	((InventoryPlayerTFC) player.inventory).extraEquipInventory[0];

/*		for(int i = 0; i < 9; i++)
		{
			if(player.inventory.getStackInSlot(i) != null && player.inventory.getStackInSlot(i).getItem() instanceof ItemQuiver)
			{
				quiver = player.inventory.getStackInSlot(i);
				break;
			}
		}
*/
		if(quiver != null && quiver.getItem() instanceof ItemQuiver)
			if(((ItemQuiver)quiver.getItem()).consumeAmmo(quiver, EnumAmmo.ARROW, shouldConsume) != null) 
				return true;

		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		ArrowNockEvent event = new ArrowNockEvent(player, is);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return event.result;

		if (player.capabilities.isCreativeMode || player.inventory.hasItem(TFCItems.arrow) || consumeArrowInQuiver(player, false))
			player.setItemInUse(is, this.getMaxItemUseDuration(is));

		return is;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int inUseCount)
	{
		int j = this.getMaxItemUseDuration(is) - inUseCount;

		ArrowLooseEvent event = new ArrowLooseEvent(player, is, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
			return;
		j = event.charge;

		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, is) > 0;

		//First we run the normal ammo check to see if the arrow is in the players inventory
		boolean hasAmmo = flag || player.inventory.hasItem(TFCItems.arrow);
		boolean hasAmmoInQuiver = false;
		//If there was no ammo in the inventory then we need to check if there is a quiver and if there is ammo inside of it.
		if(!hasAmmo)
			hasAmmoInQuiver = consumeArrowInQuiver(player, false);

		if (hasAmmo || hasAmmoInQuiver)
		{
			float forceMult = j / getUseSpeed(player);
			//f = (f * f + f * 2.0F) / 3.0F;

			if (forceMult < 0.25D)
				return;

			if (forceMult > 1.25F)
				forceMult = 1.25F;

			EntityProjectileTFC entityarrow = new EntityProjectileTFC(world, player, forceMult * 2.0F);
			entityarrow.setDamage(forceMult * 150.0);
			if (forceMult == 1.25F)
				entityarrow.setIsCritical(true);

			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, is);

			if (k > 0)
				entityarrow.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, is);

			if (l > 0)
				entityarrow.setKnockbackStrength(l);

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, is) > 0)
				entityarrow.setFire(100);

			is.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + forceMult * 0.5F);

			if (flag)
				entityarrow.canBePickedUp = 2;
			else if(hasAmmo)
				player.inventory.consumeInventoryItem(TFCItems.arrow);
			else if(hasAmmoInQuiver)
				consumeArrowInQuiver(player, true);

			if (!world.isRemote)
				world.spawnEntityInWorld(entityarrow);
		}
	}

	public static float getUseSpeed(EntityPlayer player)
	{
		float speed = 60.0f;
		ItemStack[] armor = player.inventory.armorInventory;
		if(armor[0] != null && armor[0].getItem() instanceof ISize)
			speed += 20.0f / ((ISize)armor[0].getItem()).getWeight(armor[0]).multiplier;
		if(armor[1] != null && armor[1].getItem() instanceof ISize)
			speed += 20.0f / ((ISize)armor[1].getItem()).getWeight(armor[1]).multiplier;
		if(armor[2] != null && armor[2].getItem() instanceof ISize)
			speed += 20.0f / ((ISize)armor[2].getItem()).getWeight(armor[2]).multiplier;
		if(armor[3] != null && armor[3].getItem() instanceof ISize)
			speed += 20.0f / ((ISize)armor[3].getItem()).getWeight(armor[3]).multiplier;

		return speed;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.LIGHT;
	}

	@Override
	public boolean canStack()
	{
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IIconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + this.getIconString() + "_standby");
		iconArray = new IIcon[bowPullIconNameArray.length];

		for (int i = 0; i < iconArray.length; ++i)
			iconArray[i] = par1IconRegister.registerIcon(Reference.MOD_ID + ":" + this.getIconString() + "_" + bowPullIconNameArray[i]);

	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getItemIconForUseDuration(int par1)
	{
		return iconArray[par1];
	}

	@Override
	public IIcon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
        if (usingItem != null && usingItem.getItem() == this)
        {
            int j = usingItem.getMaxItemUseDuration() - useRemaining;
            float force = j / getUseSpeed(player);

			if (force >= 1.25) // Fully drawn
            {
				return getItemIconForUseDuration(3);
			}
			else if (force > 0.75)
            {
				return getItemIconForUseDuration(2);
            }
			else if (force > 0.25) // Minimum required force to fire
            {
                return getItemIconForUseDuration(1);
            }
			else if (force > 0)
            {
                return getItemIconForUseDuration(0);
            }
        }
        return getIcon(stack, renderPass);
	}

	@Override
	public EnumItemReach getReach(ItemStack is) {
		return EnumItemReach.SHORT;
	}
}
