package com.bioxx.tfc.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Events.ItemCookEvent;
import com.bioxx.tfc.api.Interfaces.IFood;
import com.bioxx.tfc.api.Util.Helper;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class FoodCraftingHandler
{
	public static boolean PreCrafted = false;
	@SubscribeEvent
	public void onFoodCook(ItemCookEvent event)
	{
		if(event.input1.getItem() instanceof IFood)
		{

		}
	}

	@SubscribeEvent
	public void onFoodCrafting(ItemCraftedEvent e)
	{
		//EntityPlayer player = e.player;
		Item item = e.crafting.getItem();
		ItemStack craftResult = e.crafting;
		int isDmg = e.crafting.getItemDamage();
		IInventory iinventory = e.craftMatrix;

		if(FoodCraftingHandler.PreCrafted)
		{
			FoodCraftingHandler.PreCrafted = false;
			return;
		}

		if(iinventory != null)
		{
			if((craftResult.getItem() == TFCItems.WheatGrain && gridHasItem(iinventory, TFCItems.WheatWhole)) ||
					(craftResult.getItem() == TFCItems.RyeGrain && gridHasItem(iinventory, TFCItems.RyeWhole)) || 
					(craftResult.getItem() == TFCItems.OatGrain && gridHasItem(iinventory, TFCItems.OatWhole)) || 
					(craftResult.getItem() == TFCItems.BarleyGrain && gridHasItem(iinventory, TFCItems.BarleyWhole)) || 
					(craftResult.getItem() == TFCItems.RiceGrain && gridHasItem(iinventory, TFCItems.RiceWhole)))
			{
				HandleItem(e.player, iinventory, Recipes.Knives);

				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight") && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodDecay"))
					{
						float foodWeight = Food.getWeight(iinventory.getStackInSlot(i));
						float foodDecay = Food.getDecay(iinventory.getStackInSlot(i));
						int strawCount = 0;

						for (int j = 0; j < foodWeight; j += 4)
							strawCount++;

						if (!e.player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Straw, strawCount)))
							e.player.dropItem(TFCItems.Straw, strawCount);

						ItemFoodTFC.createTag(craftResult, foodWeight, foodDecay);
					}
				}
			}
			else if(craftResult.hasTagCompound() && craftResult.getTagCompound().hasKey("foodWeight"))
			{
				craftResult = processFood(e.player, craftResult, iinventory);
			}

			if((craftResult.getItem() == TFCItems.WheatDough || craftResult.getItem() == TFCItems.RyeDough || craftResult.getItem() == TFCItems.OatDough || 
					craftResult.getItem() == TFCItems.BarleyDough || craftResult.getItem() == TFCItems.CornmealDough || craftResult.getItem() == TFCItems.RiceDough) && 
					(gridHasItem(iinventory, TFCItems.WoodenBucketWater) || gridHasItem(iinventory, TFCItems.RedSteelBucketWater)))
			{
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).hasTagCompound() &&
							iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight") &&
							iinventory.getStackInSlot(i).getTagCompound().hasKey("foodDecay"))
					{
						NBTTagCompound grainNBT = iinventory.getStackInSlot(i).getTagCompound();
						float grainWeight = grainNBT.getFloat("foodWeight");
						float breadWeight = Math.min(grainWeight,80);
						float breadDecay = grainNBT.getFloat("foodDecay");
						grainWeight -= breadWeight;
						breadWeight *= 2;
						grainNBT.setFloat("foodWeight", grainWeight);
						if(grainWeight > 0)
							iinventory.getStackInSlot(i).stackSize++;
						ItemFoodTFC.createTag(craftResult, breadWeight, breadDecay);
					}
				}
			}
		}
	}

	private static ItemStack processFood(EntityPlayer player, ItemStack craftResult, IInventory iinventory) 
	{
		float finalWeight = 0;
		float finalDecay = 0;
		int sweetMod = -1;
		int sourMod = -1;
		int saltyMod = -1;
		int bitterMod = -1;
		int umamiMod = -1;
		int[] fuelTasteProfile = new int[] {0,0,0,0,0};
		int[] cookedTasteProfile = new int[] {0,0,0,0,0};
		float cookedTime = 0;
		boolean salted = true;
		boolean pickled = true;
		boolean brined = true;
		boolean dried = true;
		int driedAmt = 0;
		int foodCount = 0;
		int itemCount = 0;
		int foodSlot = 0; //This is used when cutting food to track where the food originally was since the merge code may remove the stack
		for(int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if(iinventory.getStackInSlot(i) == null)
				continue;
			itemCount++;
			if(iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight"))
			{
				ItemStack is = iinventory.getStackInSlot(i);
				foodSlot = i;
				if(foodCount == 0)
				{
					fuelTasteProfile = Food.getFuelProfile(is);
					cookedTasteProfile = Food.getCookedProfile(is);
					cookedTime = Food.getCooked(is);
				}
				if(sweetMod == -1)
					sweetMod = ((ItemFoodTFC)is.getItem()).getTasteSweetMod(is);
				else if(sweetMod != ((ItemFoodTFC)is.getItem()).getTasteSweetMod(is))
					sweetMod = 0;

				if(sourMod == -1)
					sourMod = ((ItemFoodTFC)is.getItem()).getTasteSourMod(is);
				else if(sourMod != ((ItemFoodTFC)is.getItem()).getTasteSourMod(is))
					sourMod = 0;

				if(saltyMod == -1)
					saltyMod = ((ItemFoodTFC)is.getItem()).getTasteSaltyMod(is);
				else if(saltyMod != ((ItemFoodTFC)is.getItem()).getTasteSaltyMod(is))
					saltyMod = 0;

				if(bitterMod == -1)
					bitterMod = ((ItemFoodTFC)is.getItem()).getTasteBitterMod(is);
				else if(bitterMod != ((ItemFoodTFC)is.getItem()).getTasteBitterMod(is))
					bitterMod = 0;

				if(umamiMod == -1)
					umamiMod = ((ItemFoodTFC)is.getItem()).getTasteSavoryMod(is);
				else if(umamiMod != ((ItemFoodTFC)is.getItem()).getTasteSavoryMod(is))
					umamiMod = 0;

				float myWeight = Food.getWeight(is);
				final float myOldWeight = myWeight;
				float myDecayPercent = Food.getDecay(is) / myOldWeight;
				float myDecay = Food.getDecay(is);
				float w = 0;

				salted = salted && Food.isSalted(is);
				pickled = pickled && Food.isPickled(is);
				brined = brined && Food.isBrined(is);
				dried = dried && Food.isDried(is);

				//If the smoked or cooked profile is not the same than we cant combine
				//Check if we can add any more to this bundle of food
				if (finalWeight < Global.FOOD_MAX_WEIGHT && 
						Food.isSameSmoked(cookedTasteProfile, Food.getCookedProfile(is)) &&
						Food.isSameSmoked(fuelTasteProfile, Food.getFuelProfile(is)) &&
						((int)Food.getCooked(is)-600)/120 == ((int)cookedTime-600)/120)
				{
					w = Math.min((Global.FOOD_MAX_WEIGHT-finalWeight), myWeight);
					myWeight -= w;
					finalWeight += w;
				}

				//we only add the decay if food was actually added to the bundle
				if(myWeight != myOldWeight)
				{
					if(myWeight == 0)
					{
						if(finalDecay < 0)
						{
							if(myDecay > finalDecay)
								finalDecay = myDecay;
						}
						else
							finalDecay+=myDecay;
					}
					else
					{
						float d = w * myDecayPercent;
						myDecay-= d;
						if(finalDecay < 0)
						{
							if(myDecay > finalDecay)
								finalDecay = myDecay;
						}
						else
							finalDecay += d;
					}
					foodCount++;
				}

				if(myWeight > 0)
				{
					Food.setWeight(is, Helper.roundNumber(myWeight,100));
					Food.setDecay(is, Helper.roundNumber(myDecay,100));
					is.stackSize = is.stackSize + 1;
					if(is.stackSize > 2)
						is.stackSize = 2;
				}
			}
		}
		craftResult = ItemFoodTFC.createTag(craftResult, Helper.roundNumber(finalWeight,10), Helper.roundNumber(finalDecay,100));
		if(sweetMod > 0) craftResult.getTagCompound().setInteger("tasteSweetMod", sweetMod);
		if(sourMod > 0) craftResult.getTagCompound().setInteger("tasteSourMod", sourMod);
		if(saltyMod > 0) craftResult.getTagCompound().setInteger("tasteSaltyMod", saltyMod);
		if(bitterMod > 0) craftResult.getTagCompound().setInteger("tasteBitterMod", bitterMod);
		if(umamiMod > 0) craftResult.getTagCompound().setInteger("tasteUmamiMod", umamiMod);	

		Food.setCooked(craftResult, cookedTime);
		Food.setFuelProfile(craftResult, fuelTasteProfile);
		Food.setCookedProfile(craftResult, cookedTasteProfile);
		Food.setSalted(craftResult, salted);
		Food.setPickled(craftResult, pickled);
		Food.setBrined(craftResult, brined);
		if(dried)
			Food.setDried(craftResult, Food.DRYHOURS);

		if(craftResult.stackSize == 0)
			craftResult.stackSize = 1;

		if(itemCount == 1)
		{
			if(Food.getDecay(craftResult) > 0)
			{
				int knifeSlot = -1;
				for(int i = 0; i < player.inventory.getSizeInventory(); i++) 
				{
					if(player.inventory.getStackInSlot(i) == null)
						continue;
					if(player.inventory.getStackInSlot(i).getItem() instanceof ItemKnife)
					{
						knifeSlot = i;
						break;
					}
				}
				if(knifeSlot >= 0)
				{
					player.inventory.getStackInSlot(knifeSlot).damageItem(1 , player);
					if(player.inventory.getStackInSlot(knifeSlot).getItemDamage() == player.inventory.getStackInSlot(knifeSlot).getMaxDamage())
						player.inventory.setInventorySlotContents(knifeSlot, null);
					float decay = Food.getDecay(craftResult);
					Food.setDecay(craftResult, 0);
					Food.setWeight(craftResult, Food.getWeight(craftResult)-decay);
				}
			}
		}
		else
		{
			//Check if we are doing anything other than combining the food
			for(int i = 0; i < iinventory.getSizeInventory(); i++) 
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				//If we're salting the food
				if(iinventory.getStackInSlot(i).getItem() == TFCItems.Powder && iinventory.getStackInSlot(i).getItemDamage() == 9)
					Food.setSalted(craftResult, true);
				boolean f = isInvFull(player);

				if(iinventory.getStackInSlot(i).getItem() instanceof ItemKnife && f)
				{
					if(FoodCraftingHandler.PreCrafted)
					{
						//Food.setWeight(craftResult, finalWeight);
					}
					else
					{
						Food.setWeight(craftResult, finalWeight);
						iinventory.getStackInSlot(i).stackSize = 2;
					}
				}

				if(iinventory.getStackInSlot(i).getItem() instanceof ItemKnife && (!f || !FoodCraftingHandler.PreCrafted))
				{
					if(Food.getDecay(craftResult) > 0)
					{
						FoodCraftingHandler.DamageItem(player, iinventory, i, iinventory.getStackInSlot(i).getItem());
						float decay = Food.getDecay(craftResult);
						Food.setDecay(craftResult, 0);
						Food.setWeight(craftResult, Food.getWeight(craftResult)-decay);
					}
					else if(Food.getDecay(craftResult) <= 0)
					{
						if(finalWeight/2f < 1)
						{
							Food.setWeight(craftResult, finalWeight);
							iinventory.getStackInSlot(i).stackSize = 2;
						}
						else
						{
							FoodCraftingHandler.DamageItem(player, iinventory, i, iinventory.getStackInSlot(i).getItem());
							Food.setWeight(iinventory.getStackInSlot(foodSlot),finalWeight/2);
							Food.setWeight(craftResult,finalWeight/2);
							iinventory.getStackInSlot(foodSlot).stackSize = 2;
						}
					}
				}
			}
		}
		return craftResult;
	}

	public static boolean isInvFull(EntityPlayer player)
	{
		for(int i = 0; i < player.inventory.mainInventory.length; i++) 
		{
			if(player.inventory.mainInventory[i] == null)
				return false;
		}
		return true;
	}

	/**
	 * Should only make changes to the craftResult and should allow the normal crafting handler to change the craftMatrix. This is
	 * only ever fired when Shift Clicking
	 */
	public static void preCraft(EntityPlayer player, ItemStack craftResult, IInventory iinventory)
	{
		FoodCraftingHandler.PreCrafted = true;
		if((craftResult.getItem() == TFCItems.WheatGrain && gridHasItem(iinventory, TFCItems.WheatWhole)) ||
				(craftResult.getItem() == TFCItems.RyeGrain && gridHasItem(iinventory, TFCItems.RyeWhole)) || 
				(craftResult.getItem() == TFCItems.OatGrain && gridHasItem(iinventory, TFCItems.OatWhole)) || 
				(craftResult.getItem() == TFCItems.BarleyGrain && gridHasItem(iinventory, TFCItems.BarleyWhole)) || 
				(craftResult.getItem() == TFCItems.RiceGrain && gridHasItem(iinventory, TFCItems.RiceWhole)))
		{
			HandleItem(player, iinventory, Recipes.Knives);
			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if (iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight") && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodDecay"))
					ItemFoodTFC.createTag(craftResult, iinventory.getStackInSlot(i).getTagCompound().getFloat("foodWeight"), iinventory.getStackInSlot(i).getTagCompound().getFloat("foodDecay"));
			}
		}
		else if(craftResult.hasTagCompound() && craftResult.getTagCompound().hasKey("foodWeight"))
		{
			craftResult = processFood(player, craftResult, iinventory);
		}

		if((craftResult.getItem() == TFCItems.WheatDough || craftResult.getItem() == TFCItems.RyeDough || craftResult.getItem() == TFCItems.OatDough || 
				craftResult.getItem() == TFCItems.BarleyDough || craftResult.getItem() == TFCItems.CornmealDough || craftResult.getItem() == TFCItems.RiceDough) && 
				(gridHasItem(iinventory, TFCItems.WoodenBucketWater) || gridHasItem(iinventory, TFCItems.RedSteelBucketWater)))
		{
			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if (iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight") && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodDecay"))
				{
					NBTTagCompound grainNBT = iinventory.getStackInSlot(i).getTagCompound();
					float grainWeight = grainNBT.getFloat("foodWeight");
					float breadWeight = Math.min(grainWeight,80);
					float breadDecay = grainNBT.getFloat("foodDecay");
					breadWeight *= 2;
					ItemFoodTFC.createTag(craftResult, breadWeight, breadDecay);
				}
			}
		}
	}

	public static boolean gridHasItem(IInventory iinventory, Item id)
	{
		for(int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if(iinventory.getStackInSlot(i) == null)
				continue;
			if(iinventory.getStackInSlot(i).getItem() == id)
				return true;
		}
		return false;
	}

	public static void HandleItem(EntityPlayer entityplayer, IInventory iinventory, Item[] Items)
	{
		for(int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if(iinventory.getStackInSlot(i) == null)
				continue;
			for(int j = 0; j < Items.length; j++)
				DamageItem(entityplayer,iinventory,i,Items[j]);
		}
	}
	public static void DamageItem(EntityPlayer entityplayer, IInventory iinventory, int i, Item item)
	{
		if(iinventory.getStackInSlot(i).getItem() == item) 
		{
			int index = i;
			ItemStack is = iinventory.getStackInSlot(index).copy();
			if(is != null)
			{
				is.damageItem(1 , entityplayer);
				if (is.getItemDamage() != 0 || entityplayer.capabilities.isCreativeMode)
				{
					iinventory.setInventorySlotContents(index, is);
					iinventory.getStackInSlot(index).stackSize = iinventory.getStackInSlot(index).stackSize + 1;
					if(iinventory.getStackInSlot(index).stackSize > 2)
						iinventory.getStackInSlot(index).stackSize = 2;
				}
			}
		}
	}

}
