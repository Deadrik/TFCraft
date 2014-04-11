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
import cpw.mods.fml.common.ICraftingHandler;

public class FoodCraftingHandler implements ICraftingHandler
{

	@Override
	public void onCrafting(EntityPlayer player, ItemStack itemstack, IInventory iinventory) 
	{
		if(iinventory != null)
		{
			if((itemstack.getItem() == TFCItems.WheatGrain && gridHasItem(iinventory, TFCItems.WheatWhole)) ||
					(itemstack.getItem() == TFCItems.RyeGrain && gridHasItem(iinventory, TFCItems.RyeWhole)) || 
					(itemstack.getItem() == TFCItems.OatGrain && gridHasItem(iinventory, TFCItems.OatWhole)) || 
					(itemstack.getItem() == TFCItems.BarleyGrain && gridHasItem(iinventory, TFCItems.BarleyWhole)) || 
					(itemstack.getItem() == TFCItems.RiceGrain && gridHasItem(iinventory, TFCItems.RiceWhole)))
			{
				HandleItem(player, iinventory, Recipes.Knives);
				if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Straw,4)))
					player.dropItem(TFCItems.Straw.itemID,4);

				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{             
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight"))
						ItemFoodTFC.createTag(itemstack, iinventory.getStackInSlot(i).getTagCompound().getFloat("foodWeight"));
				}
			}
			else if(itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("foodWeight"))
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
				itemstack = ItemFoodTFC.createTag(itemstack, Helper.roundNumber(finalWeight,10), Helper.roundNumber(finalDecay,100));
				if(itemstack.stackSize == 0)
					itemstack.stackSize = 1;
				if(salted)
					itemstack.getTagCompound().setBoolean("isSalted", true);
				//Check if we are doing anything other than combining the food
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null)
						continue;
					//If we're salting the food
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.Powder && iinventory.getStackInSlot(i).getItemDamage() == 9)
					{
						itemstack.stackTagCompound.setBoolean("isSalted", true);
					}
					//if we're removing decay or splitting the stack
					else if(iinventory.getStackInSlot(i).getItem() instanceof ItemCustomKnife)
					{
						if(itemstack.getTagCompound().hasKey("foodDecay") && itemstack.getTagCompound().getFloat("foodDecay") > 0)
						{
							float decay = itemstack.getTagCompound().getFloat("foodDecay");
							itemstack.getTagCompound().setFloat("foodDecay", 0);
							itemstack.getTagCompound().setFloat("foodWeight", itemstack.getTagCompound().getFloat("foodWeight")-decay);
							FoodCraftingHandler.DamageItem(player, iinventory, i, iinventory.getStackInSlot(i).getItem().itemID);
						}
						else if(itemstack.getTagCompound().hasKey("foodDecay") && itemstack.getTagCompound().getFloat("foodDecay") <= 0)
						{
							FoodCraftingHandler.DamageItem(player, iinventory, i, iinventory.getStackInSlot(i).getItem().itemID);
							if(finalWeight/2 < 1)
								itemstack.getTagCompound().setFloat("foodWeight", finalWeight);
							else
							{
								itemstack.getTagCompound().setFloat("foodWeight", finalWeight/2);
								ItemStack is = itemstack.copy();
								is.stackSize++;
								iinventory.setInventorySlotContents(foodSlot, is);
							}
						}
					}
				}
			}

			if((itemstack.getItem() == TFCItems.WheatDough || itemstack.getItem() == TFCItems.RyeDough || itemstack.getItem() == TFCItems.OatDough || 
					itemstack.getItem() == TFCItems.BarleyDough || itemstack.getItem() == TFCItems.CornmealDough || itemstack.getItem() == TFCItems.RiceDough) && 
					(gridHasItem(iinventory, TFCItems.WoodenBucketWater) || gridHasItem(iinventory, TFCItems.RedSteelBucketWater)))
			{
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{             
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight") && 
							iinventory.getStackInSlot(i).getTagCompound().getFloat("foodDecay") <= 0)
					{
						NBTTagCompound grainNBT = iinventory.getStackInSlot(i).getTagCompound();
						float grainWeight = grainNBT.getFloat("foodWeight");
						float breadWeight = Math.min(grainWeight,80);
						grainWeight -= breadWeight;
						breadWeight *= 2;
						grainNBT.setFloat("foodWeight", grainWeight);
						ItemFoodTFC.createTag(itemstack, breadWeight);
						if(grainWeight > 0)
							iinventory.getStackInSlot(i).stackSize++;
					}
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
				DamageItem(entityplayer,iinventory,i,Items[j].itemID);
		}
	}
	public static void DamageItem(EntityPlayer entityplayer, IInventory iinventory, int i, int shiftedindex)
	{
		if(iinventory.getStackInSlot(i).itemID == shiftedindex) 
		{
			int index = i;
			ItemStack item = iinventory.getStackInSlot(index).copy();
			if(item != null)
			{
				item.damageItem(1 , entityplayer);
				if (item.getItemDamage() != 0 || entityplayer.capabilities.isCreativeMode)
				{
					iinventory.setInventorySlotContents(index, item);
					iinventory.getStackInSlot(index).stackSize = iinventory.getStackInSlot(index).stackSize + 1;
					if(iinventory.getStackInSlot(index).stackSize > 2)
						iinventory.getStackInSlot(index).stackSize = 2;
				}
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub

	}

}
