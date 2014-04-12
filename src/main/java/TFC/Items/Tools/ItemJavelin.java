package TFC.Items.Tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.API.ICausesDamage;
import TFC.API.IProjectile;
import TFC.API.IQuiverAmmo;
import TFC.API.Enums.EnumAmmo;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFCTabs;
import TFC.Entities.EntityJavelin;
import TFC.Items.ItemQuiver;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;

public class ItemJavelin extends ItemTerraTool implements ICausesDamage, IProjectile, IQuiverAmmo
{
	public float weaponDamage;
	private float weaponRangeDamage;

	public ItemJavelin(ToolMaterial par2EnumToolMaterial, float damage)
	{
		super(10F, par2EnumToolMaterial, Sets.newHashSet(new Block[] {Blocks.air}));
		this.maxStackSize = 1;
		this.weaponDamage = damage;
		this.weaponRangeDamage = damage;
		this.setMaxDamage(par2EnumToolMaterial.getMaxUses()/2);
		setCreativeTab(TFCTabs.TFCWeapons);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
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

		EntityJavelin javelin = new EntityJavelin(world, player, 1.5f*force);
		javelin.setDamage(getRangedDamage());

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
		javelin.setDamageTaken((short) itemstack.getItemDamage());

		player.inventory.mainInventory[player.inventory.currentItem] = null;

		if(!consumeJavelin(player))
		{
			player.inventory.mainInventory[player.inventory.currentItem] = consumeJavelinInQuiver(player, true);
		}

		if (!world.isRemote)
		{
			world.spawnEntityInWorld(javelin);
		}

	}

	private int getInventorySlotContainJavelin(EntityPlayer player)
	{
		for (int j = 0; j < player.inventory.mainInventory.length; ++j)
		{
			if (player.inventory.mainInventory[j] != null && player.inventory.mainInventory[j].getItem() instanceof ItemJavelin)
			{
				return j;
			}
		}

		return -1;
	}

	public ItemStack consumeJavelinInQuiver(EntityPlayer player, boolean shouldConsume)
	{
		ItemStack quiver = player.inventory.armorItemInSlot(0);
		if(quiver != null && quiver.getItem() instanceof ItemQuiver)
			return ((ItemQuiver)quiver.getItem()).consumeAmmo(quiver, EnumAmmo.JAVELIN, shouldConsume);
		return null;
	}

	public boolean consumeJavelin(EntityPlayer player)
	{
		int active = player.inventory.currentItem;
		int nextJav = getInventorySlotContainJavelin(player);

		if (nextJav < 0)
		{
			player.inventory.mainInventory[active] = null;
			return false;
		}
		else
		{
			player.inventory.mainInventory[active] = player.inventory.mainInventory[nextJav].copy();
			if (--player.inventory.mainInventory[nextJav].stackSize <= 0)
			{
				player.inventory.mainInventory[nextJav] = null;
			}
		}
		return true;
	}

	@Override
	public boolean isFull3D()
	{
		return true;
	}

	@Override
	public EnumDamageType GetDamageType() 
	{
		return EnumDamageType.PIERCING;
	}

	@Override
	public float getRangedDamage() 
	{
		return weaponRangeDamage;
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		multimap.put(SharedMonsterAttributes.attackDamage.getAttributeUnlocalizedName(), new AttributeModifier(field_111210_e, "Weapon modifier", this.weaponDamage, 0));
		return multimap;
	}

	@Override
	public EnumAmmo getAmmoType() 
	{
		return EnumAmmo.JAVELIN;
	}
}
