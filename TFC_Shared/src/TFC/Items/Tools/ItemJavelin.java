package TFC.Items.Tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.API.TFCTabs;
import TFC.Entities.EntityTerraJavelin;

public class ItemJavelin extends ItemTerraTool
{
	private static int weaponDamage;
	private static double weaponRangeDamage;

	public ItemJavelin(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, weaponDamage, par2EnumToolMaterial, new Block[0]);
		this.maxStackSize = 1;
		this.weaponDamage = Math.round(par2EnumToolMaterial.getDamageVsEntity()*0.4f);
		this.weaponRangeDamage = par2EnumToolMaterial.getDamageVsEntity()*0.5D;
		this.setMaxDamage(par2EnumToolMaterial.getMaxUses()/2);
		setCreativeTab(TFCTabs.TFCWeapons);
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this));
	}

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getItemEnchantability()
	{
		return 1;
	}

	/**
	 * Returns the damage against a given entity.
	 */
	public int getDamageVsEntity(Entity par1Entity)
	{
		return this.weaponDamage;
	}

	/**
	 * returns the action that specifies what animation to play when the items is being used
	 */
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.bow;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	public ItemStack onFoodEaten(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		return par1ItemStack;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	/**
	 * called when the player releases the use item button. Args: itemstack, world, entityplayer, itemInUseCount
	 */
	@Override
	public void onPlayerStoppedUsing(ItemStack itemstack, World world, EntityPlayer player, int itemInUseCount)
	{
		int var6 = this.getMaxItemUseDuration(itemstack) - itemInUseCount;
		float force = Math.min(var6/20.0f, 1.0f);

		EntityTerraJavelin javelin = new EntityTerraJavelin(world, player, 1.5f*force, this.itemID);
		javelin.setDamage(this.weaponRangeDamage);

		int var9 = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, itemstack);

		if (var9 > 0)
		{
			javelin.setDamage(javelin.getDamage() + var9 * 0.5D + 0.5D);
		}

		int var10 = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, itemstack);

		if (var10 > 0)
		{
			javelin.setDamage(javelin.getDamage()+var10);
		}

		if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, itemstack) > 0)
		{
			javelin.setFire(100);
		}

		world.playSoundAtEntity(player, "random.bow", 1.0F, 0.3F);
		player.inventory.consumeInventoryItem(this.itemID);
		javelin.setDamageTaken((short) itemstack.getItemDamage());
		if (!world.isRemote)
		{
			world.spawnEntityInWorld(javelin);
		}
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}
}
