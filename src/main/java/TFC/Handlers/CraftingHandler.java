package TFC.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.Util.Helper;
import TFC.Core.Recipes;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Sounds;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Food.ItemFoodTFC;
import TFC.Handlers.Network.AbstractPacket;
import TFC.Handlers.Network.PlayerUpdatePacket;
import TFC.Items.ItemIngot;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.Tools.ItemCustomKnife;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingHandler// implements ICraftingHandler
{

	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent e)//(EntityPlayer player, ItemStack itemstack, IInventory iinventory)
	{
		EntityPlayer player = e.player;
		Item item = e.crafting.getItem();
		ItemStack itemstack = e.crafting;
		int isDmg = e.crafting.getItemDamage();
		IInventory iinventory = e.craftMatrix;
		
		int index = 0;
		if(iinventory != null)
		{
			if(item == TFCItems.StoneBrick)
				HandleItem(e.player, e.craftMatrix, Recipes.Chisels);
			else if(item == Item.getItemFromBlock(Blocks.crafting_table))
			{
				player.getEntityData().setBoolean("craftingTable", true);
				player.inventory.clearInventory(Item.getItemFromBlock(Blocks.crafting_table), -1);
				
				AbstractPacket pkt = new PlayerUpdatePacket(player, 2);
				TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) player);

				//PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
				//player.inventoryContainer = new ContainerPlayerTFC(player.inventory, !player.worldObj.isRemote, player);
				//player.openContainer = player.inventoryContainer;
				PlayerInventory.upgradePlayerCrafting(player);
			}
			else if(item == TFCItems.SinglePlank)
			{
				HandleItem(e.player, e.craftMatrix, Recipes.Axes);
				HandleItem(e.player, e.craftMatrix, Recipes.Saws);
			}
			else if((item == TFCItems.WheatGrain && gridHasItem(iinventory, TFCItems.WheatWhole)) ||
					(item == TFCItems.RyeGrain && gridHasItem(iinventory, TFCItems.RyeWhole)) || 
					(item == TFCItems.OatGrain && gridHasItem(iinventory, TFCItems.OatWhole)) || 
					(item == TFCItems.BarleyGrain && gridHasItem(iinventory, TFCItems.BarleyWhole)) || 
					(item == TFCItems.RiceGrain && gridHasItem(iinventory, TFCItems.RiceWhole)))
			{
				HandleItem(e.player, e.craftMatrix, Recipes.Knives);
				if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Straw,4)))
					player.dropItem(TFCItems.Straw,4);
				ItemStack is = null;
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("foodWeight"))
						ItemFoodTFC.createTag(itemstack, iinventory.getStackInSlot(i).getTagCompound().getFloat("foodWeight"));
				}
			}
			else if(item == Item.getItemFromBlock(TFCBlocks.WoodSupportH) || item == Item.getItemFromBlock(TFCBlocks.WoodSupportV))
			{
				HandleItem(e.player, e.craftMatrix, Recipes.Saws);
				HandleItem(e.player, e.craftMatrix, Recipes.Axes);
			}
			else if(item == Items.bowl || /*item instanceof ItemTerraFood ||*/ item == TFCItems.ScrapedHide ||
					item == TFCItems.Wool||item == TFCItems.TerraLeather)
			{
				HandleItem(e.player, e.craftMatrix, Recipes.Knives);
				if (item == TFCItems.Wool && !player.worldObj.isRemote)
				{
					if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Hide, 1, 0)))
						player.entityDropItem(new ItemStack(TFCItems.Hide, 1, 0), 1);
				}
				else if(item == TFCItems.TerraLeather)
				{
					boolean openGui = false;
					for(int i = 0; i < iinventory.getSizeInventory(); i++)
					{
						if(iinventory.getStackInSlot(i) == null)
							continue;
						if(true)//iinventory.getStackInSlot(i) == TFCItems.TerraLeather.shiftedIndex)
						{
							if(iinventory.getStackInSlot(i).stackSize == 1)
								iinventory.setInventorySlotContents(i, null);
							else
							{
								ItemStack is = iinventory.getStackInSlot(i);
								is.stackSize -= 1;
								if(is.stackSize > 0)
									iinventory.setInventorySlotContents(i, is);
							}
							openGui = true;
							//itemstack.stackSize = -1;
						}
					}
					if(openGui)
					{
						PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
						pi.specialCraftingType = new ItemStack(TFCItems.FlatLeather, 1, itemstack.getItemDamage());
						player.openGui(TerraFirmaCraft.instance, 28, player.worldObj, (int)player.posX, (int)player.posY, (int)player.posZ);
					}
				}
			}
			else if(item == TFCItems.WoolYarn)
				HandleItem(e.player,e.craftMatrix,Recipes.Spindle);
			else if(item == TFCItems.Powder && isDmg == 0)
				HandleItem(e.player, e.craftMatrix, Recipes.Hammers);
			else if(item == TFCItems.Mortar)
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.WoodenBucketWater)
						iinventory.setInventorySlotContents(i, new ItemStack(TFCItems.Limewater));
				}
			else if(item == TFCItems.Limewater)
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.WoodenBucketWater)
						iinventory.setInventorySlotContents(i, new ItemStack(TFCItems.Mortar));
				}
			else if(item instanceof ItemIngot)
			{
				if(player.worldObj.rand.nextInt(20) == 0)
					player.playSound(TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
				else if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.CeramicMold, 1, 1)))
					player.entityDropItem(new ItemStack(TFCItems.CeramicMold, 1, 1), 1);
				float temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemMeltedMetal)
						temperature = TFC_ItemHeat.GetTemperature(iinventory.getStackInSlot(i));
				}
				TFC_ItemHeat.SetTemperature(e.crafting, temperature);
			}
			else if(item instanceof ItemMeltedMetal)
			{
				float temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemIngot )
						temperature = TFC_ItemHeat.GetTemperature(iinventory.getStackInSlot(i));
				}
				TFC_ItemHeat.SetTemperature(e.crafting, temperature);
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
						if (finalWeight < 80)
						{
							w = Math.min((80-finalWeight), myWeight);
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
							this.DamageItem(player, iinventory, i, iinventory.getStackInSlot(i).getItem());
						}
						else if(itemstack.getTagCompound().hasKey("foodDecay") && itemstack.getTagCompound().getFloat("foodDecay") <= 0)
						{
							this.DamageItem(player, iinventory, i, iinventory.getStackInSlot(i).getItem());
							itemstack.getTagCompound().setFloat("foodWeight", finalWeight/2);
							ItemStack is = itemstack.copy();
							is.stackSize++;
							iinventory.setInventorySlotContents(foodSlot, is);
						}
					}
				}
			}

			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if(iinventory.getStackInSlot(i).getItem() == TFCItems.WoodenBucketWater)
				{
					if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.WoodenBucketEmpty,1)))
						player.dropItem(TFCItems.WoodenBucketEmpty, 1);
				}
//				else if(iinventory.getStackInSlot(i).getItem() == TFCItems.RedSteelBucketWater)
//					if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.RedSteelBucketEmpty,1)))
//						player.dropItem(TFCItems.RedSteelBucketEmpty, 1);

				if(iinventory.getStackInSlot(i).hasTagCompound() && 
						iinventory.getStackInSlot(i).getTagCompound().hasKey("craftingTag"))
				{
					if(itemstack.getTagCompound() == null)
						itemstack.setTagCompound(new NBTTagCompound());
					itemstack.getTagCompound().setTag("craftingTag", iinventory.getStackInSlot(i).getTagCompound().getCompoundTag("craftingTag"));
				}
			}
		}
	}

	public static boolean gridHasItem(IInventory iinventory, Item item)
	{
		for(int i = 0; i < iinventory.getSizeInventory(); i++) 
		{
			if(iinventory.getStackInSlot(i) == null)
				continue;
			if(iinventory.getStackInSlot(i).getItem() == item)
				return true;
		}
		return false;
	}

	public static void HandleItem(EntityPlayer entityplayer, IInventory iinventory, Item[] Items)
	{
		ItemStack item = null;
		for(int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if(iinventory.getStackInSlot(i) == null)
				continue;
			for(int j = 0; j < Items.length; j++)
				DamageItem(entityplayer, iinventory, i, Items[j]);
		}
	}

	public static void DamageItem(EntityPlayer entityplayer, IInventory iinventory, int i, Item shiftedindex)
	{
		if(iinventory.getStackInSlot(i).getItem() == shiftedindex)
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

}
