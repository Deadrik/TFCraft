package com.bioxx.tfc.Items;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;

public class ItemCustomPotion extends ItemPotion
{
	/** maps potion damage values to lists of effect names */
	private HashMap effectCache = new HashMap();
	private static final Map field_77835_b = new LinkedHashMap();

	public ItemCustomPotion()
	{
		super();
		this.setCreativeTab(TFCTabs.TFCFoods);
	}

	@Override
	public ItemStack onEaten(ItemStack is, World world, EntityPlayer player)
	{
		if (!player.capabilities.isCreativeMode)
		{
			--is.stackSize;
		}

		if (!world.isRemote)
		{
			List var4 = this.getEffects(is);

			if (var4 != null)
			{
				Iterator var5 = var4.iterator();

				while (var5.hasNext())
				{
					PotionEffect var6 = (PotionEffect)var5.next();
					player.addPotionEffect(new PotionEffect(var6));
				}
			}
			else
			{
				TFC_Core.getPlayerFoodStats(player).restoreWater(player, 12000);
			}
		}

		if (!player.capabilities.isCreativeMode)
		{
			boolean broken = false;
			if(world.rand.nextInt(50) == 0)
			{
				player.playSound("random.glass", 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
				broken = true;
			}

			if (!broken && is.stackSize <= 0)
			{
				return new ItemStack(TFCItems.GlassBottle);
			}
		}

		return is;
	}

	@Override
	public void registerIcons(IIconRegister par1IconRegister)
	{
		super.registerIcons(par1IconRegister);
	}
}
