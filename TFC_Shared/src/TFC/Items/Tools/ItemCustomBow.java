package TFC.Items.Tools;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.player.ArrowLooseEvent;
import net.minecraftforge.event.entity.player.ArrowNockEvent;
import TFC.API.ISize;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumAmmo;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Entities.EntityProjectileTFC;
import TFC.Items.ItemQuiver;
import TFC.Items.ItemTerra;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomBow extends ItemBow implements ISize
{
	private Icon[] iconArray;

	public ItemCustomBow(int par1)
	{
		super(par1);
		this.maxStackSize = 1;
		this.setMaxDamage(384);
		setCreativeTab(TFCTabs.TFCWeapons);
	}

	public boolean consumeArrowInQuiver(EntityPlayer player, boolean shouldConsume)
	{
		ItemStack quiver = null;
		for(int i = 0; i < 9; i++) 
		{
			if(player.inventory.getStackInSlot(i).getItem() instanceof ItemQuiver)
			{
				quiver = player.inventory.getStackInSlot(i);
				break;
			}
		}
		if(quiver != null && quiver.getItem() instanceof ItemQuiver)
		{
			if(((ItemQuiver)quiver.getItem()).consumeAmmo(quiver, EnumAmmo.ARROW, shouldConsume) != null) 
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
	{
		ArrowNockEvent event = new ArrowNockEvent(player, is);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return event.result;
		}

		if (player.capabilities.isCreativeMode || player.inventory.hasItem(Item.arrow.itemID) || consumeArrowInQuiver(player, false))
		{
			player.setItemInUse(is, this.getMaxItemUseDuration(is));
		}

		return is;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack is, World world, EntityPlayer player, int inUseCount)
	{
		int j = this.getMaxItemUseDuration(is) - inUseCount;

		ArrowLooseEvent event = new ArrowLooseEvent(player, is, j);
		MinecraftForge.EVENT_BUS.post(event);
		if (event.isCanceled())
		{
			return;
		}
		j = event.charge;

		boolean flag = player.capabilities.isCreativeMode || EnchantmentHelper.getEnchantmentLevel(Enchantment.infinity.effectId, is) > 0;

		//First we run the normal ammo check to see if the arrow is in the players inventory
		boolean hasAmmo = flag || player.inventory.hasItem(Item.arrow.itemID);
		boolean hasAmmoInQuiver = false;
		//If there was no ammo in the inventory then we need to check if there is a quiver and if there is ammo inside of it.
		if(!hasAmmo) 
		{
			hasAmmoInQuiver = consumeArrowInQuiver(player, true);
		}

		if (hasAmmo || hasAmmoInQuiver)
		{
			float forceMult = inUseCount / getUseSpeed(player);
			//f = (f * f + f * 2.0F) / 3.0F;

			if (forceMult < 0.25D)
			{
				return;
			}

			if (forceMult > 1.25F)
			{
				forceMult = 1.25F;
			}

			EntityProjectileTFC entityarrow = new EntityProjectileTFC(world, player, forceMult * 2.0F, Item.arrow.itemID);
			entityarrow.setDamage(forceMult * 150.0);
			if (forceMult == 1.0F)
			{
				entityarrow.setIsCritical(true);
			}

			int k = EnchantmentHelper.getEnchantmentLevel(Enchantment.power.effectId, is);

			if (k > 0)
			{
				entityarrow.setDamage(entityarrow.getDamage() + k * 0.5D + 0.5D);
			}

			int l = EnchantmentHelper.getEnchantmentLevel(Enchantment.punch.effectId, is);

			if (l > 0)
			{
				entityarrow.setKnockbackStrength(l);
			}

			if (EnchantmentHelper.getEnchantmentLevel(Enchantment.flame.effectId, is) > 0)
			{
				entityarrow.setFire(100);
			}

			is.damageItem(1, player);
			world.playSoundAtEntity(player, "random.bow", 1.0F, 1.0F / (itemRand.nextFloat() * 0.4F + 1.2F) + forceMult * 0.5F);

			if (flag)
			{
				entityarrow.canBePickedUp = 2;
			}
			else if(hasAmmo)
			{
				player.inventory.consumeInventoryItem(Item.arrow.itemID);
			}

			if (!world.isRemote)
			{
				world.spawnEntityInWorld(entityarrow);
			}
		}
	}

	public float getUseSpeed(EntityPlayer player)
	{
		float speed = 60.0f;
		ItemStack[] armor = player.inventory.armorInventory;
		if(armor[0].getItem() instanceof ISize)
		{
			speed += 20.0f / ((ISize)armor[0].getItem()).getWeight(armor[0]).multiplier;
		}
		if(armor[1].getItem() instanceof ISize)
		{
			speed += 20.0f / ((ISize)armor[1].getItem()).getWeight(armor[1]).multiplier;
		}
		if(armor[2].getItem() instanceof ISize)
		{
			speed += 20.0f / ((ISize)armor[2].getItem()).getWeight(armor[2]).multiplier;
		}
		if(armor[3].getItem() instanceof ISize)
		{
			speed += 20.0f / ((ISize)armor[3].getItem()).getWeight(armor[3]).multiplier;
		}

		return speed;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(is, arraylist);
	}


	@Override
	public EnumSize getSize(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumSize.LARGE;
	}


	@Override
	public EnumWeight getWeight(ItemStack is) {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}


	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister)
	{
		this.itemIcon = par1IconRegister.registerIcon(this.func_111208_A() + "_standby");
		iconArray = new Icon[bowPullIconNameArray.length];

		for (int i = 0; i < iconArray.length; ++i)
		{
			iconArray[i] = par1IconRegister.registerIcon(this.func_111208_A() + "_" + bowPullIconNameArray[i]);
		}
		Item.bow.registerIcons(par1IconRegister);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getItemIconForUseDuration(int par1)
	{
		return iconArray[par1];
	}

	@Override
	public Icon getIcon(ItemStack stack, int renderPass, EntityPlayer player, ItemStack usingItem, int useRemaining)
	{
		return getIcon(stack, renderPass);
	}
}
