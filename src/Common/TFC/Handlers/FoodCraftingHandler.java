package TFC.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import TFC.TFCItems;
import TFC.API.Constant.Global;
import TFC.API.Util.Helper;
import TFC.Core.Recipes;
import TFC.Food.ItemFoodTFC;
import TFC.Items.Tools.ItemCustomKnife;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class FoodCraftingHandler
{
	@SubscribeEvent
	public void onFoodCrafting(ItemCraftedEvent e)//(EntityPlayer player, ItemStack itemstack, IInventory iinventory)
	{
		//EntityPlayer player = e.player;
		Item item = e.crafting.getItem();
		ItemStack craftResult = e.crafting;
		int isDmg = e.crafting.getItemDamage();
		IInventory iinventory = e.craftMatrix;
		
		if(iinventory != null)
		{
			if((craftResult.getItem() == TFCItems.WheatGrain && gridHasItem(iinventory, TFCItems.WheatWhole)) ||
					(craftResult.getItem() == TFCItems.RyeGrain && gridHasItem(iinventory, TFCItems.RyeWhole)) || 
					(craftResult.getItem() == TFCItems.OatGrain && gridHasItem(iinventory, TFCItems.OatWhole)) || 
					(craftResult.getItem() == TFCItems.BarleyGrain && gridHasItem(iinventory, TFCItems.BarleyWhole)) || 
					(craftResult.getItem() == TFCItems.RiceGrain && gridHasItem(iinventory, TFCItems.RiceWhole)))
			{
				HandleItem(e.player, iinventory, Recipes.Knives);
				if(!e.player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Straw, 4)))
					e.player.dropItem(TFCItems.Straw, 4);

				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight"))
						ItemFoodTFC.createTag(craftResult, iinventory.getStackInSlot(i).getTagCompound().getFloat("foodWeight"));
				}
			}
			else if(craftResult.hasTagCompound() && craftResult.getTagCompound().hasKey("foodWeight"))
			{
				float finalWeight = 0;
				float finalDecay = 0;
				boolean salted = true;
				int foodSlot = 0; //This is used when cutting food to track where the food originally was since the merge code may remove the stack
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight"))
					{
						foodSlot = i;
						float myWeight = iinventory.getStackInSlot(i).getTagCompound().getFloat("foodWeight");
						final float myOldWeight = myWeight;
						float myDecayPercent = iinventory.getStackInSlot(i).getTagCompound().getFloat("foodDecay") / myOldWeight;
						float myDecay = iinventory.getStackInSlot(i).getTagCompound().getFloat("foodDecay");
						float w = 0;

						if(!iinventory.getStackInSlot(i).getTagCompound().hasKey("isSalted"))
							salted = false;
						//Check if we can add any more to this bundle of food
						if (finalWeight < Global.FOOD_MAX_WEIGHT)
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
						}

						if(myWeight > 0)
						{
							iinventory.getStackInSlot(i).getTagCompound().setFloat("foodWeight", Helper.roundNumber(myWeight,100));
							iinventory.getStackInSlot(i).getTagCompound().setFloat("foodDecay", Helper.roundNumber(myDecay,100));
							iinventory.getStackInSlot(i).stackSize = iinventory.getStackInSlot(i).stackSize + 1;
							if(iinventory.getStackInSlot(i).stackSize > 2)
								iinventory.getStackInSlot(i).stackSize = 2;
						}
					}
				}
				craftResult = ItemFoodTFC.createTag(craftResult, Helper.roundNumber(finalWeight,10), Helper.roundNumber(finalDecay,100));
				if(craftResult.stackSize == 0)
					craftResult.stackSize = 1;
				if(salted)
					craftResult.getTagCompound().setBoolean("isSalted", true);
				//Check if we are doing anything other than combining the food
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					//If we're salting the food
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.Powder && iinventory.getStackInSlot(i).getItemDamage() == 9)
						craftResult.stackTagCompound.setBoolean("isSalted", true);

					if(iinventory.getStackInSlot(i).getItem() instanceof ItemCustomKnife)
					{
						if(craftResult.getTagCompound().hasKey("foodDecay") && craftResult.getTagCompound().getFloat("foodDecay") > 0)
						{
							FoodCraftingHandler.DamageItem(e.player, iinventory, i, iinventory.getStackInSlot(i).getItem());
							float decay = craftResult.getTagCompound().getFloat("foodDecay");
							craftResult.getTagCompound().setFloat("foodDecay", 0);
							craftResult.getTagCompound().setFloat("foodWeight", craftResult.getTagCompound().getFloat("foodWeight")-decay);
						}
						else if(craftResult.getTagCompound().hasKey("foodDecay") && craftResult.getTagCompound().getFloat("foodDecay") <= 0)
						{
							FoodCraftingHandler.DamageItem(e.player, iinventory, i, iinventory.getStackInSlot(i).getItem());
							if(finalWeight/2 < 1)
								craftResult.getTagCompound().setFloat("foodWeight", finalWeight);
							else
							{
								craftResult.getTagCompound().setFloat("foodWeight", finalWeight/2);
								iinventory.getStackInSlot(foodSlot).stackSize++;
								iinventory.getStackInSlot(foodSlot).getTagCompound().setFloat("foodWeight", finalWeight/2);
							}
						}
					}
				}
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

	/**
	 * Should only make changes to the craftResult and should allow the normal crafting handler to change the craftMatrix. This is
	 * only ever fired when Shift Clicking
	 */
	public static void preCraft(EntityPlayer player, ItemStack craftResult, IInventory iinventory)
	{
		if((craftResult.getItem() == TFCItems.WheatGrain && gridHasItem(iinventory, TFCItems.WheatWhole)) ||
				(craftResult.getItem() == TFCItems.RyeGrain && gridHasItem(iinventory, TFCItems.RyeWhole)) || 
				(craftResult.getItem() == TFCItems.OatGrain && gridHasItem(iinventory, TFCItems.OatWhole)) || 
				(craftResult.getItem() == TFCItems.BarleyGrain && gridHasItem(iinventory, TFCItems.BarleyWhole)) || 
				(craftResult.getItem() == TFCItems.RiceGrain && gridHasItem(iinventory, TFCItems.RiceWhole)))
		{
			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if(iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight"))
					ItemFoodTFC.createTag(craftResult, iinventory.getStackInSlot(i).getTagCompound().getFloat("foodWeight"));
			}
		}
		else if(craftResult.hasTagCompound() && craftResult.getTagCompound().hasKey("foodWeight"))
		{
			float finalWeight = 0;
			float finalDecay = 0;
			boolean salted = true;
			int foodSlot = 0; //This is used when cutting food to track where the food originally was since the merge code may remove the stack
			for(int i = 0; i < iinventory.getSizeInventory(); i++) 
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if(iinventory.getStackInSlot(i).hasTagCompound() && 
						iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight"))
				{
					foodSlot = i;
					float myWeight = iinventory.getStackInSlot(i).getTagCompound().getFloat("foodWeight");
					final float myOldWeight = myWeight;
					float myDecayPercent = iinventory.getStackInSlot(i).getTagCompound().getFloat("foodDecay") / myOldWeight;
					float myDecay = iinventory.getStackInSlot(i).getTagCompound().getFloat("foodDecay");
					float w = 0;

					if(!iinventory.getStackInSlot(i).getTagCompound().hasKey("isSalted"))
						salted = false;
					//Check if we can add any more to this bundle of food
					if (finalWeight < Global.FOOD_MAX_WEIGHT)
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
					}
				}
			}
			craftResult = ItemFoodTFC.createTag(craftResult, Helper.roundNumber(finalWeight,10), Helper.roundNumber(finalDecay,100));
			if(craftResult.stackSize == 0)
				craftResult.stackSize = 1;
			if(salted)
				craftResult.getTagCompound().setBoolean("isSalted", true);
			//Check if we are doing anything other than combining the food
			for(int i = 0; i < iinventory.getSizeInventory(); i++) 
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				// If we're salting the food
				if (iinventory.getStackInSlot(i).getItem() == TFCItems.Powder && iinventory.getStackInSlot(i).getItemDamage() == 9)
				{
					craftResult.stackTagCompound.setBoolean("isSalted", true);
				}
				if(iinventory.getStackInSlot(i).getItem() instanceof ItemCustomKnife)
				{
					if(craftResult.getTagCompound().hasKey("foodDecay") && craftResult.getTagCompound().getFloat("foodDecay") > 0)
					{
						float decay = craftResult.getTagCompound().getFloat("foodDecay");
						craftResult.getTagCompound().setFloat("foodDecay", 0);
						craftResult.getTagCompound().setFloat("foodWeight", craftResult.getTagCompound().getFloat("foodWeight")-decay);
					}
					else if(craftResult.getTagCompound().hasKey("foodDecay") && craftResult.getTagCompound().getFloat("foodDecay") <= 0)
					{
						if(finalWeight/2 < 1)
							craftResult.getTagCompound().setFloat("foodWeight", finalWeight);
						else
							craftResult.getTagCompound().setFloat("foodWeight", finalWeight/2);
					}
				}
			}
		}

		if((craftResult.getItem() == TFCItems.WheatDough || craftResult.getItem() == TFCItems.RyeDough || craftResult.getItem() == TFCItems.OatDough || 
				craftResult.getItem() == TFCItems.BarleyDough || craftResult.getItem() == TFCItems.CornmealDough || craftResult.getItem() == TFCItems.RiceDough) && 
				(gridHasItem(iinventory, TFCItems.WoodenBucketWater) || gridHasItem(iinventory, TFCItems.RedSteelBucketWater)))
		{
			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if(iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight"))
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
