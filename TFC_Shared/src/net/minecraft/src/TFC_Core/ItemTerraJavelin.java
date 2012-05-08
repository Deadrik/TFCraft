package net.minecraft.src.TFC_Core;

import java.util.ArrayList;
import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class ItemTerraJavelin extends Item implements ITextureProvider
{
	public ItemTerraJavelin(int par1)
	{
		super(par1);
		this.maxStackSize = 1;
	}
	
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public void addCreativeItems(ArrayList itemList)
	{
		itemList.add(new ItemStack(this));
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getItemEnchantability()
	{
		return 1;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}

	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	 */
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4)
	{
		int var6 = this.getMaxItemUseDuration(par1ItemStack) - par4;
		float var7 = (float)var6 / 20.0F;
		var7 = (var7 * var7 + var7 * 2.0F) / 3.0F;

		if ((double)var7 < 0.1D)
		{
			return;
		}

		if (var7 > 1.0F)
		{
			var7 = 1.0F;
		}

		EntityTerraJavelin var8 = new EntityTerraJavelin(par2World, par3EntityPlayer, var7 * 2.0F);

		if (var7 == 1.0F)
		{
			var8.arrowCritical = true;
		}

		int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, par1ItemStack);

		if (var9 > 0)
		{
			var8.setDamage(var8.getDamage() + (double)var9 * 0.5D + 0.5D);
		}

		int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, par1ItemStack);

		if (var10 > 0)
		{
			var8.func_46023_b(var10);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, par1ItemStack) > 0)
		{
			var8.setFire(100);
		}

		par2World.playSoundAtEntity(par3EntityPlayer, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + var7 * 0.5F);
		par3EntityPlayer.inventory.consumeInventoryItem(mod_TFC_Core.Javelin.shiftedIndex);

		if (!par2World.isRemote)
		{
			par2World.spawnEntityInWorld(var8);
		}
	}
}
