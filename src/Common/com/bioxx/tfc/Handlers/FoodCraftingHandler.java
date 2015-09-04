package com.bioxx.tfc.Handlers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Events.ItemCookEvent;
import com.bioxx.tfc.api.Util.Helper;

public class FoodCraftingHandler
{
	public static boolean preCrafted;
	@SubscribeEvent
	public void onFoodCook(ItemCookEvent event)
	{
		/*if(event.input1.getItem() instanceof IFood)
		{
		
		}*/
	}

	@SubscribeEvent
	public void onFoodCrafting(ItemCraftedEvent e)
	{
		if (FoodCraftingHandler.preCrafted)
		{
			FoodCraftingHandler.preCrafted = false;
			return;
		}

		//EntityPlayer player = e.player;
		//Item item = e.crafting.getItem();
		ItemStack craftResult = e.crafting;
		//int isDmg = e.crafting.getItemDamage();
		IInventory iinventory = e.craftMatrix;

		if(iinventory != null)
		{
			if (craftResult.getItem() == TFCItems.wheatGrain && gridHasItem(iinventory, TFCItems.wheatWhole) ||
				craftResult.getItem() == TFCItems.ryeGrain && gridHasItem(iinventory, TFCItems.ryeWhole) ||
				craftResult.getItem() == TFCItems.oatGrain && gridHasItem(iinventory, TFCItems.oatWhole) ||
				craftResult.getItem() == TFCItems.barleyGrain && gridHasItem(iinventory, TFCItems.barleyWhole) ||
				craftResult.getItem() == TFCItems.riceGrain && gridHasItem(iinventory, TFCItems.riceWhole))
			{
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(e.player, iinventory, knives);

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

						if (!e.player.inventory.addItemStackToInventory(new ItemStack(TFCItems.straw, strawCount)))
							e.player.dropItem(TFCItems.straw, strawCount);

						ItemFoodTFC.createTag(craftResult, foodWeight, foodDecay);
					}
				}
			}
			else if(craftResult.hasTagCompound() && craftResult.getTagCompound().hasKey("foodWeight"))
			{
				craftResult = processFood(e.player, craftResult, iinventory);
			}

			if((craftResult.getItem() == TFCItems.wheatDough || craftResult.getItem() == TFCItems.ryeDough || craftResult.getItem() == TFCItems.oatDough || 
					craftResult.getItem() == TFCItems.barleyDough || craftResult.getItem() == TFCItems.cornmealDough || craftResult.getItem() == TFCItems.riceDough) && 
					(gridHasItem(iinventory, TFCItems.woodenBucketWater) || gridHasItem(iinventory, TFCItems.redSteelBucketWater)))
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

	/*
	 * Updates the items in the input slots of the crafting grid.
	 * NOTE: The craftResult input is not altered in this method. By the time that this is called,
	 * craftResult should already have all the NBT data updated due to updateOutput called by
	 * ContainerPlayerTFC.onCraftMatrixChanged()
	 */
	private static ItemStack processFood(EntityPlayer player, ItemStack craftResult, IInventory craftingInv) 
	{
		float finalWeight = 0;
		float finalDecay = 0;
		int[] fuelTasteProfile = new int[] {0,0,0,0,0};
		int[] cookedTasteProfile = new int[] {0,0,0,0,0};
		float cookedTime = 0;
		int foodCount = 0;
		int itemCount = 0;
		int foodSlot = 0; //This is used when cutting food to track where the food originally was since the merge code may remove the stack
		for(int i = 0; i < craftingInv.getSizeInventory(); i++)
		{
			if(craftingInv.getStackInSlot(i) == null)
				continue;
			itemCount++;
			ItemStack is = craftingInv.getStackInSlot(i);
			if (is.getItem() instanceof ItemFoodTFC && is.hasTagCompound() && is.getTagCompound().hasKey("foodWeight"))
			{
				foodSlot = i;
				if(foodCount == 0)
				{
					fuelTasteProfile = Food.getFuelProfile(is);
					cookedTasteProfile = Food.getCookedProfile(is);
					cookedTime = Food.getCooked(is);
				}

				float inputWeight = Food.getWeight(is);
				final float oldInputWeight = inputWeight;
				float inputDecayPercent = Food.getDecay(is) / oldInputWeight;
				float inputDecay = Food.getDecay(is);
				float weightChange = 0;

				// If the smoked or cooked profile is not the same than we can't combine
				// Check if we can add any more to this bundle of food
				if (finalWeight < Global.FOOD_MAX_WEIGHT && 
						Food.isSameSmoked(cookedTasteProfile, Food.getCookedProfile(is)) &&
						Food.isSameSmoked(fuelTasteProfile, Food.getFuelProfile(is)) &&
						((int) Food.getCooked(is) - 600) / 120 == ((int) cookedTime - 600) / 120)
				{
					weightChange = Math.min((Global.FOOD_MAX_WEIGHT - finalWeight), inputWeight);
					inputWeight -= weightChange;
					finalWeight += weightChange;
				}

				// Only add the decay if food was actually added to the bundle
				if(inputWeight != oldInputWeight)
				{
					if (inputWeight == 0) // The input is being completely combined
					{
						if (finalDecay < 0) // Still within the 24 hour grace period
						{
							if (inputDecay > finalDecay) // The input has more decay than the output
								finalDecay = inputDecay; // Set the output's decay to the input's decay
						}
						else
							finalDecay += inputDecay; // Add the decay from the input to the output
					}
					else
					{
						float decayChange = weightChange * inputDecayPercent;
						inputDecay -= decayChange; // Remove the decay from the input
						if (finalDecay < 0) // Still within the 24 hour grace period
						{
							if (decayChange > finalDecay) // The input is losing more decay than what the output currently has
								finalDecay = decayChange; // Set the output's decay to the amount removed from the input
						}
						else
							finalDecay += decayChange; // Add the decay to the output
					}
					foodCount++;
				}

				if (inputWeight > 0) // If we're leaving a piece of food in the crafting grid
				{
					Food.setWeight(is, Helper.roundNumber(inputWeight, 100));
					Food.setDecay(is, Helper.roundNumber(inputDecay, 100));
					is.stackSize += 1;
					if(is.stackSize > 2)
						is.stackSize = 2;
				}
			}
		}

		if(craftResult.stackSize == 0)
			craftResult.stackSize = 1;

		if (itemCount == 1) // Trimming decay with a knife from the inventory, not the crafting grid.
		{
			if (finalDecay > 0)
			{
				for(int i = 0; i < player.inventory.getSizeInventory(); i++) 
				{
					if(player.inventory.getStackInSlot(i) == null)
						continue;
					ItemStack stack = player.inventory.getStackInSlot(i);
					if (stack.getItem() instanceof ItemKnife)
					{
						// Damage the Knife
						stack.damageItem(1, player);
						if (stack.getItemDamage() == stack.getMaxDamage())
							player.inventory.setInventorySlotContents(i, null);
						// No need to increase the stack size since the knife isn't in the crafting grid
						break;
					}
				}
			}
		}
		else
		{
			// Check if we are doing anything other than combining the food
			for(int i = 0; i < craftingInv.getSizeInventory(); i++) 
			{
				if(craftingInv.getStackInSlot(i) == null)
					continue;
				ItemStack itemstack = craftingInv.getStackInSlot(i);
				boolean fullInv = isInvFull(player);

				if (itemstack.getItem() instanceof ItemKnife && fullInv)
				{
					if (!FoodCraftingHandler.preCrafted)
					{
						// Increase the knife's stack size so it will remain in the grid when crafting completes
						itemstack.stackSize += 1;
						if (itemstack.stackSize > 2)
							itemstack.stackSize = 2;
					}
				}

				if (itemstack.getItem() instanceof ItemKnife && (!fullInv || !FoodCraftingHandler.preCrafted))
				{
					if (finalDecay > 0) // Trimming Decay
					{
						FoodCraftingHandler.damageItem(player, craftingInv, i, itemstack.getItem());
					}
					else if (finalDecay <= 0) // Splitting food in half
					{
						if (finalWeight / 2f < 1) // Food is too small to split
						{
							// Increase the knife's stack size so it will remain in the grid when crafting completes
							itemstack.stackSize += 1;
							if (itemstack.stackSize > 2)
								itemstack.stackSize = 2;
						}
						else
						{
							FoodCraftingHandler.damageItem(player, craftingInv, i, itemstack.getItem());
							Food.setWeight(craftingInv.getStackInSlot(foodSlot), Helper.roundNumber(finalWeight / 2f, 100));
							// Increase the food's stack size so it will remain in the grid when crafting completes
							craftingInv.getStackInSlot(foodSlot).stackSize += 1;
							if (craftingInv.getStackInSlot(foodSlot).stackSize > 2)
								craftingInv.getStackInSlot(foodSlot).stackSize = 2;
						}
					}
				}
			}
		}
		return craftResult;
	}

	/*
	 * Updates the output slot of the crafting grid, transferring taste profiles,
	 * weights, decay amounts, cooking profiles, etc.
	 */
	public static void updateOutput(EntityPlayer player, ItemStack craftResult, IInventory craftingInv)
	{
		float finalWeight = 0;
		float finalDecay = 0;
		int sweetMod = -1;
		int sourMod = -1;
		int saltyMod = -1;
		int bitterMod = -1;
		int umamiMod = -1;
		int[] fuelTasteProfile = new int[] { 0, 0, 0, 0, 0 };
		int[] cookedTasteProfile = new int[] { 0, 0, 0, 0, 0 };
		float cookedTime = 0;
		String infusion = null;
		boolean salted = true;
		boolean pickled = true;
		boolean brined = true;
		boolean dried = true;
		int driedAmt = 0;
		int foodCount = 0;
		int itemCount = 0;
		for (int i = 0; i < craftingInv.getSizeInventory(); i++ )
		{
			if (craftingInv.getStackInSlot(i) == null)
				continue;
			itemCount++ ;
			ItemStack is = craftingInv.getStackInSlot(i);
			if (is.getItem() instanceof ItemFoodTFC && is.hasTagCompound() && is.getTagCompound().hasKey("foodWeight"))
			{
				if (foodCount == 0)
				{
					fuelTasteProfile = Food.getFuelProfile(is);
					cookedTasteProfile = Food.getCookedProfile(is);
					cookedTime = Food.getCooked(is);
					infusion = Food.getInfusion(is);
					driedAmt = Food.getDried(is);
				}
				if (sweetMod == -1)
					sweetMod = ((ItemFoodTFC) is.getItem()).getTasteSweetMod(is);
				else if (sweetMod != ((ItemFoodTFC) is.getItem()).getTasteSweetMod(is))
					sweetMod = 0;

				if (sourMod == -1)
					sourMod = ((ItemFoodTFC) is.getItem()).getTasteSourMod(is);
				else if (sourMod != ((ItemFoodTFC) is.getItem()).getTasteSourMod(is))
					sourMod = 0;

				if (saltyMod == -1)
					saltyMod = ((ItemFoodTFC) is.getItem()).getTasteSaltyMod(is);
				else if (saltyMod != ((ItemFoodTFC) is.getItem()).getTasteSaltyMod(is))
					saltyMod = 0;

				if (bitterMod == -1)
					bitterMod = ((ItemFoodTFC) is.getItem()).getTasteBitterMod(is);
				else if (bitterMod != ((ItemFoodTFC) is.getItem()).getTasteBitterMod(is))
					bitterMod = 0;

				if (umamiMod == -1)
					umamiMod = ((ItemFoodTFC) is.getItem()).getTasteSavoryMod(is);
				else if (umamiMod != ((ItemFoodTFC) is.getItem()).getTasteSavoryMod(is))
					umamiMod = 0;

				float inputWeight = Food.getWeight(is);
				final float oldInputWeight = inputWeight;
				float inputDecayPercent = Food.getDecay(is) / oldInputWeight;
				float inputDecay = Food.getDecay(is);
				float weightChange = 0;

				salted = salted && Food.isSalted(is);
				pickled = pickled && Food.isPickled(is);
				brined = brined && Food.isBrined(is);
				dried = dried && Food.isDried(is);

				// If the smoked or cooked profile is not the same than we can't combine
				// Check if we can add any more to this bundle of food
				if (finalWeight < Global.FOOD_MAX_WEIGHT &&
					Food.isSameSmoked(cookedTasteProfile, Food.getCookedProfile(is)) &&
					Food.isSameSmoked(fuelTasteProfile, Food.getFuelProfile(is)) &&
					((int) Food.getCooked(is) - 600) / 120 == ((int) cookedTime - 600) / 120)
				{
					weightChange = Math.min((Global.FOOD_MAX_WEIGHT - finalWeight), inputWeight);
					inputWeight -= weightChange;
					finalWeight += weightChange;
				}

				// Only add the decay if food was actually added to the bundle
				if (inputWeight != oldInputWeight)
				{
					if (inputWeight == 0) // The input is being completely combined
					{
						if (finalDecay < 0) // Still within the 24 hour grace period
						{
							if (inputDecay > finalDecay) // The input has more decay than the output
								finalDecay = inputDecay; // Set the output's decay to the input's decay
						}
						else
							finalDecay += inputDecay; // Add the decay from the input to the output
					}
					else
					{
						float decayChange = weightChange * inputDecayPercent;
						inputDecay -= decayChange; // Remove the decay from the input
						if (finalDecay < 0) // Still within the 24 hour grace period
						{
							if (decayChange > finalDecay) // The input is losing more decay than what the output currently has
								finalDecay = decayChange; // Set the output's decay to the amount removed from the input
						}
						else
							finalDecay += decayChange; // Add the decay to the output
					}
					foodCount++ ;
				}
			}
		}

		if (itemCount == 1) // Trimming decay with a knife from the inventory, not the crafting grid.
		{
			if (finalDecay > 0)
			{
				for (int i = 0; i < player.inventory.getSizeInventory(); i++ )
				{
					if (player.inventory.getStackInSlot(i) == null)
						continue;
					if (player.inventory.getStackInSlot(i).getItem() instanceof ItemKnife)
					{
						finalWeight -= finalDecay;
						finalDecay = 0;
						break;
					}
				}
			}
		}
		else
		{
			// Check if we are doing anything other than combining the food
			for (int i = 0; i < craftingInv.getSizeInventory(); i++ )
			{
				if (craftingInv.getStackInSlot(i) == null)
					continue;
				// If we're salting the food
				if (craftingInv.getStackInSlot(i).getItem() == TFCItems.powder && craftingInv.getStackInSlot(i).getItemDamage() == 9)
				{
					salted = true;
				}
				else if (craftingInv.getStackInSlot(i).getItem() instanceof ItemKnife)
				{
					if (finalDecay > 0) // Trimming Decay
					{
						finalWeight -= finalDecay;
						finalDecay = 0;
					}
					else if (finalDecay <= 0) // Splitting food in half
					{
						if (finalWeight / 2f >= 1) // Must be big enough to split
						{
							finalWeight /= 2f;
						}
					}
				}
			}
		}

		craftResult = ItemFoodTFC.createTag(craftResult, Helper.roundNumber(finalWeight, 100), Helper.roundNumber(finalDecay, 100));
		if (sweetMod != 0)
			craftResult.getTagCompound().setInteger("tasteSweetMod", sweetMod);
		if (sourMod != 0)
			craftResult.getTagCompound().setInteger("tasteSourMod", sourMod);
		if (saltyMod != 0)
			craftResult.getTagCompound().setInteger("tasteSaltyMod", saltyMod);
		if (bitterMod != 0)
			craftResult.getTagCompound().setInteger("tasteBitterMod", bitterMod);
		if (umamiMod != 0)
			craftResult.getTagCompound().setInteger("tasteUmamiMod", umamiMod);

		if (cookedTime > 0)
			Food.setCooked(craftResult, cookedTime);

		for (int fuelTaste : fuelTasteProfile)
		{
			if (fuelTaste > 0)
			{
				Food.setFuelProfile(craftResult, fuelTasteProfile);
				break;
			}
		}
		for (int cookedTaste : cookedTasteProfile)
		{
			if (cookedTaste > 0)
			{
				Food.setCookedProfile(craftResult, cookedTasteProfile);
				break;
			}
		}

		if (salted)
			Food.setSalted(craftResult, salted);
		if (pickled)
			Food.setPickled(craftResult, pickled);
		if (brined)
			Food.setBrined(craftResult, brined);

		if (dried) // Fully Dried
			Food.setDried(craftResult, Food.DRYHOURS);
		else if (driedAmt > 0) // Partially Dried
			Food.setDried(craftResult, driedAmt);

		if (infusion != null)
			Food.setInfusion(craftResult, infusion);

		if (craftResult.stackSize == 0)
			craftResult.stackSize = 1;
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
		FoodCraftingHandler.preCrafted = true;
		if (craftResult.getItem() == TFCItems.wheatGrain && gridHasItem(iinventory, TFCItems.wheatWhole) ||
			craftResult.getItem() == TFCItems.ryeGrain && gridHasItem(iinventory, TFCItems.ryeWhole) ||
			craftResult.getItem() == TFCItems.oatGrain && gridHasItem(iinventory, TFCItems.oatWhole) ||
			craftResult.getItem() == TFCItems.barleyGrain && gridHasItem(iinventory, TFCItems.barleyWhole) ||
			craftResult.getItem() == TFCItems.riceGrain && gridHasItem(iinventory, TFCItems.riceWhole))
		{
			List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
			handleItem(player, iinventory, knives);
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

		if((craftResult.getItem() == TFCItems.wheatDough || craftResult.getItem() == TFCItems.ryeDough || craftResult.getItem() == TFCItems.oatDough || 
				craftResult.getItem() == TFCItems.barleyDough || craftResult.getItem() == TFCItems.cornmealDough || craftResult.getItem() == TFCItems.riceDough) && 
				(gridHasItem(iinventory, TFCItems.woodenBucketWater) || gridHasItem(iinventory, TFCItems.redSteelBucketWater)))
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

	public static void handleItem(EntityPlayer entityplayer, IInventory iinventory, Item[] items)
	{
		for(int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if(iinventory.getStackInSlot(i) == null)
				continue;
			for(int j = 0; j < items.length; j++)
				damageItem(entityplayer,iinventory,i,items[j]);
		}
	}

	public static void handleItem(EntityPlayer entityplayer, IInventory iinventory, List<ItemStack> items)
	{
		for (int i = 0; i < iinventory.getSizeInventory(); i++ )
		{
			if (iinventory.getStackInSlot(i) == null)
				continue;
			for (ItemStack is : items)
				damageItem(entityplayer, iinventory, i, is.getItem());
		}
	}

	public static void damageItem(EntityPlayer entityplayer, IInventory iinventory, int i, Item item)
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
					iinventory.getStackInSlot(index).stackSize += 1;
					if(iinventory.getStackInSlot(index).stackSize > 2)
						iinventory.getStackInSlot(index).stackSize = 2;
				}
			}
		}
	}

}
