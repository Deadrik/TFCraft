package com.bioxx.tfc.Handlers;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Events.ItemCookEvent;
import com.bioxx.tfc.api.Interfaces.IFood;
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

		ItemStack craftResult = e.crafting;
		IInventory craftingInv = e.craftMatrix;

		if(craftingInv != null)
		{
			if (refiningGrain(craftResult, craftingInv))
			{
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(e.player, craftingInv, knives);

				for(int i = 0; i < craftingInv.getSizeInventory(); i++)
				{
					ItemStack inputStack = craftingInv.getStackInSlot(i);
					if (inputStack == null || !(inputStack.getItem() instanceof IFood))
						continue;

					float foodWeight = Food.getWeight(inputStack);
					int strawCount = 0;

					for (int j = 0; j < foodWeight; j += 4)
						strawCount++;

					TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.straw, strawCount), e.player);
				}
			}
			else if (makingDough(craftResult, craftingInv))
			{
				for(int i = 0; i < craftingInv.getSizeInventory(); i++)
				{
					ItemStack inputStack = craftingInv.getStackInSlot(i);
					if (inputStack == null || !(inputStack.getItem() instanceof IFood))
						continue;

					float grainWeight = Food.getWeight(inputStack);
					float grainDecay = Food.getDecay(inputStack);
					if (grainDecay >= 0) // Account for -24.0 decay on fresh food
						grainWeight -= grainDecay;
					grainWeight -= Math.min(grainWeight, 80);

					inputStack = ItemFoodTFC.createTag(inputStack, grainWeight, 0);

					if (grainWeight > 0)
					{
						inputStack.stackSize++;
						if (inputStack.stackSize > 2)
							inputStack.stackSize = 2;
					}
				}
			}
			else if (craftResult.hasTagCompound() && craftResult.getTagCompound().hasKey(Food.WEIGHT_TAG))
			{
				craftResult = processFoodInput(e.player, craftResult, craftingInv);
			}
		}
	}

	/*
	 * Updates the items in the input slots of the crafting grid.
	 * NOTE: The craftResult input is not altered in this method. By the time that this is called,
	 * craftResult should already have all the NBT data updated due to updateOutput called by
	 * ContainerPlayerTFC.onCraftMatrixChanged()
	 */
	private static ItemStack processFoodInput(EntityPlayer player, ItemStack craftResult, IInventory craftingInv)
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
			ItemStack is = craftingInv.getStackInSlot(i);
			if (is == null)
				continue;
			itemCount++;
			if (is.getItem() instanceof ItemFoodTFC && is.hasTagCompound() && is.getTagCompound().hasKey(Food.WEIGHT_TAG))
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
					Food.setWeight(is, inputWeight);
					Food.setDecay(is, inputDecay);
					is.stackSize++;
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
					ItemStack stack = player.inventory.getStackInSlot(i);

					if (stack != null && stack.getItem() instanceof ItemKnife)
					{
						// Damage the Knife
						stack.damageItem(1, player);
						if (stack.getItemDamage() >= stack.getMaxDamage())
							player.inventory.setInventorySlotContents(i, null);
						// No need to increase the stack size since the knife isn't in the crafting grid
						break; // Break so we only damage the first knife we come across
					}
				}
			}
		}
		else
		{
			// Check if we are doing anything other than combining the food
			for(int i = 0; i < craftingInv.getSizeInventory(); i++)
			{
				ItemStack itemstack = craftingInv.getStackInSlot(i);
				if (itemstack == null)
					continue;
				boolean fullInv = isInvFull(player);

				if (itemstack.getItem() instanceof ItemKnife && fullInv)
				{
					if (!FoodCraftingHandler.preCrafted)
					{
						// Increase the knife's stack size so it will remain in the grid when crafting completes
						itemstack.stackSize++;
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
							itemstack.stackSize++;
							if (itemstack.stackSize > 2)
								itemstack.stackSize = 2;
						}
						else
						{
							FoodCraftingHandler.damageItem(player, craftingInv, i, itemstack.getItem());
							Food.setWeight(craftingInv.getStackInSlot(foodSlot), Helper.roundNumber(finalWeight / 2f, 100));
							// Increase the food's stack size so it will remain in the grid when crafting completes
							craftingInv.getStackInSlot(foodSlot).stackSize++;
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
			if (is.getItem() instanceof ItemFoodTFC && is.hasTagCompound() && is.getTagCompound().hasKey(Food.WEIGHT_TAG))
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
					sweetMod = Food.getSweetMod(is);
				else if (sweetMod != Food.getSweetMod(is))
					sweetMod = 0;

				if (sourMod == -1)
					sourMod = Food.getSourMod(is);
				else if (sourMod != Food.getSourMod(is))
					sourMod = 0;

				if (saltyMod == -1)
					saltyMod = Food.getSaltyMod(is);
				else if (saltyMod != Food.getSaltyMod(is))
					saltyMod = 0;

				if (bitterMod == -1)
					bitterMod = Food.getBitterMod(is);
				else if (bitterMod != Food.getBitterMod(is))
					bitterMod = 0;

				if (umamiMod == -1)
					umamiMod = Food.getSavoryMod(is);
				else if (umamiMod != Food.getSavoryMod(is))
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
				ItemStack inputStack = craftingInv.getStackInSlot(i);
				if (inputStack == null)
					continue;

				// If we're salting the food
				if (inputStack.getItem() == TFCItems.powder && inputStack.getItemDamage() == 9)
				{
					salted = true;
				}
				else if (inputStack.getItem() instanceof ItemKnife)
				{
					if (finalDecay > 0) // Trimming Decay
					{
						finalWeight -= finalDecay;
						finalDecay = 0;
					}
					else if (finalDecay <= 0) // Splitting food in half
					{
						if (!refiningGrain(craftResult, craftingInv) && finalWeight / 2f >= 1) // Must be big enough to split
						{
							finalWeight /= 2f;
						}
					}
				}
				else if (makingDough(craftResult, craftingInv) && inputStack.getItem() instanceof IFood)
				{
					float grainWeight = Food.getWeight(inputStack);
					float grainDecay = Food.getDecay(inputStack);
					if (grainDecay >= 0) // Account for -24.0 decay on fresh food
						grainWeight -= grainDecay;
					float doughWeight = Math.min(grainWeight, 80) * 2;
					finalWeight = doughWeight;
					finalDecay = 0;
				}
			}
		}

		craftResult = ItemFoodTFC.createTag(craftResult, Helper.roundNumber(finalWeight, 100), Helper.roundNumber(finalDecay, 100));
		if (sweetMod != 0)
			Food.setSweetMod(craftResult, sweetMod);
		if (sourMod != 0)
			Food.setSourMod(craftResult, sourMod);
		if (saltyMod != 0)
			Food.setSaltyMod(craftResult, saltyMod);
		if (bitterMod != 0)
			Food.setBitterMod(craftResult, bitterMod);
		if (umamiMod != 0)
			Food.setSavoryMod(craftResult, umamiMod);

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

	public static boolean refiningGrain(ItemStack craftResult, IInventory iinventory)
	{
		return craftResult.getItem() == TFCItems.wheatGrain && gridHasItem(iinventory, TFCItems.wheatWhole) ||
				craftResult.getItem() == TFCItems.ryeGrain && gridHasItem(iinventory, TFCItems.ryeWhole) ||
				craftResult.getItem() == TFCItems.oatGrain && gridHasItem(iinventory, TFCItems.oatWhole) ||
				craftResult.getItem() == TFCItems.barleyGrain && gridHasItem(iinventory, TFCItems.barleyWhole) ||
				craftResult.getItem() == TFCItems.riceGrain && gridHasItem(iinventory, TFCItems.riceWhole);
	}

	public static boolean makingDough(ItemStack craftResult, IInventory iinventory)
	{
		return (craftResult.getItem() == TFCItems.wheatDough ||craftResult.getItem() == TFCItems.ryeDough || craftResult.getItem() == TFCItems.oatDough ||
				craftResult.getItem() == TFCItems.barleyDough || craftResult.getItem() == TFCItems.cornmealDough ||
				craftResult.getItem() == TFCItems.riceDough) &&
				(gridHasItem(iinventory, TFCItems.woodenBucketWater) || gridHasItem(iinventory, TFCItems.redSteelBucketWater));
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
	public static void preCraft(EntityPlayer player, ItemStack craftResult, IInventory craftingInv)
	{
		FoodCraftingHandler.preCrafted = true;
		if (refiningGrain(craftResult, craftingInv))
		{
			List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
			handleItem(player, craftingInv, knives);
			for(int i = 0; i < craftingInv.getSizeInventory(); i++)
			{
				ItemStack inputStack = craftingInv.getStackInSlot(i);
				if (inputStack == null || !(inputStack.getItem() instanceof IFood))
					continue;

				float foodWeight = Food.getWeight(inputStack);
				int strawCount = 0;

				for (int j = 0; j < foodWeight; j += 4)
					strawCount++;

				TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.straw, strawCount), player);
			}
		}
		else if (makingDough(craftResult, craftingInv))
		{
			for(int i = 0; i < craftingInv.getSizeInventory(); i++)
			{
				ItemStack inputStack = craftingInv.getStackInSlot(i);
				if (inputStack == null || !(inputStack.getItem() instanceof IFood))
					continue;

				float grainWeight = Food.getWeight(inputStack);
				float grainDecay = Food.getDecay(inputStack);
				if (grainDecay >= 0) // Account for -24.0 decay on fresh food
					grainWeight -= grainDecay;
				float doughWeight = Math.min(grainWeight, 80);
				grainWeight -= doughWeight;

				inputStack = ItemFoodTFC.createTag(inputStack, Helper.roundNumber(grainWeight, 100), 0);

				if (grainWeight > 0)
					inputStack.stackSize++ ;
			}
		}
		else if(craftResult.hasTagCompound() && craftResult.getTagCompound().hasKey(Food.WEIGHT_TAG))
		{
			craftResult = processFoodInput(player, craftResult, craftingInv);
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
					iinventory.getStackInSlot(index).stackSize++;
					if(iinventory.getStackInSlot(index).stackSize > 2)
						iinventory.getStackInSlot(index).stackSize = 2;
				}
			}
		}
	}

}
