package TFC.Handlers;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.Constant.Global;
import TFC.API.Util.Helper;
import TFC.Core.Recipes;
import TFC.Core.TFC_Achievements;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Sounds;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Food.ItemFoodTFC;
import TFC.Items.ItemIngot;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.ItemBlocks.ItemAnvil1;
import TFC.Items.Tools.ItemCustomKnife;
import TFC.Items.Tools.ItemCustomPickaxe;
import TFC.Items.Tools.ItemCustomSaw;
import TFC.Items.Tools.ItemMiscToolHead;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler
{

	@Override
	public void onCrafting(EntityPlayer player, ItemStack itemstack, IInventory iinventory) 
	{
		if(iinventory != null)
		{
			if(itemstack.itemID == TFCItems.StoneBrick.itemID)
				HandleItem(player, iinventory, Recipes.Chisels);
			else if(itemstack.itemID == Block.workbench.blockID)
			{
				player.getEntityData().setBoolean("craftingTable", true);
				player.inventory.clearInventory(Block.workbench.blockID, -1);
				PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
				//Send a request to the server for the skills data.
				ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
				DataOutputStream dos=new DataOutputStream(bos);
				try 
				{
					dos.writeByte(PacketHandler.Packet_Update_Skills_Client);
					dos.writeBoolean(true);
				} 
				catch (IOException e) 
				{
					e.printStackTrace();
				}
				pi.networkManager.addToSendQueue(PacketHandler.getPacket(bos));
				//player.inventoryContainer = new ContainerPlayerTFC(player.inventory, !player.worldObj.isRemote, player);
				//player.openContainer = player.inventoryContainer;
				PlayerInventory.upgradePlayerCrafting(player);
			}	
			else if(itemstack.itemID == TFCItems.SinglePlank.itemID)
			{
				HandleItem(player, iinventory, Recipes.Axes);
				HandleItem(player, iinventory, Recipes.Saws);
			}
			else if((itemstack.itemID == TFCItems.WheatGrain.itemID && gridHasItem(iinventory, TFCItems.WheatWhole.itemID)) ||
					(itemstack.itemID == TFCItems.RyeGrain.itemID && gridHasItem(iinventory, TFCItems.RyeWhole.itemID)) || 
					(itemstack.itemID == TFCItems.OatGrain.itemID && gridHasItem(iinventory, TFCItems.OatWhole.itemID)) || 
					(itemstack.itemID == TFCItems.BarleyGrain.itemID && gridHasItem(iinventory, TFCItems.BarleyWhole.itemID)) || 
					(itemstack.itemID == TFCItems.RiceGrain.itemID && gridHasItem(iinventory, TFCItems.RiceWhole.itemID)))
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
			else if(itemstack.itemID == TFCBlocks.WoodSupportH.blockID || itemstack.itemID == TFCBlocks.WoodSupportV.blockID)
			{
				HandleItem(player, iinventory, Recipes.Saws);
				HandleItem(player, iinventory, Recipes.Axes);
			}
			else if(itemstack.getItem() instanceof ItemCustomPickaxe){
				player.triggerAchievement(TFC_Achievements.achPickaxe);
			}
			else if(itemstack.getItem() instanceof ItemCustomSaw){
				player.triggerAchievement(TFC_Achievements.achSaw);
			}
			else if(itemstack.itemID == Item.bowlEmpty.itemID || 
					/*itemstack.getItem() instanceof ItemTerraFood ||*/ itemstack.itemID == TFCItems.ScrapedHide.itemID
					|| itemstack.itemID == TFCItems.Wool.itemID||itemstack.itemID == TFCItems.TerraLeather.itemID)
			{
				HandleItem(player, iinventory, Recipes.Knives);
				if (itemstack.itemID == TFCItems.Wool.itemID && !player.worldObj.isRemote){
					if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Hide, 1, 0)))
						player.entityDropItem(new ItemStack(TFCItems.Hide, 1, 0), 1);
				}
				else if(itemstack.itemID == TFCItems.TerraLeather.itemID){
					boolean openGui = false;
					for(int i = 0; i < iinventory.getSizeInventory(); i++) 
					{             
						if(iinventory.getStackInSlot(i) == null)
							continue;
						if(true)//iinventory.getStackInSlot(i).itemID == TFCItems.TerraLeather.shiftedIndex)
						{
							if(iinventory.getStackInSlot(i).stackSize == 1)
								iinventory.setInventorySlotContents(i, null);
							else
							{
								ItemStack is = iinventory.getStackInSlot(i); is.stackSize-=1;
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
			else if(itemstack.itemID == TFCItems.WoolYarn.itemID)
				HandleItem(player,iinventory,Recipes.Spindle);
			else if(itemstack.itemID == TFCItems.Powder.itemID && itemstack.getItemDamage() == 0)
				HandleItem(player, iinventory, Recipes.Hammers);
			else if(itemstack.itemID == TFCItems.Mortar.itemID)
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.WoodenBucketWater)
						iinventory.getStackInSlot(i).itemID = TFCItems.Limewater.itemID;
				}
			else if(itemstack.itemID == TFCItems.Limewater.itemID)
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.WoodenBucketWater)
						iinventory.getStackInSlot(i).itemID = TFCItems.Mortar.itemID;
				}
			else if(itemstack.getItem() instanceof ItemAnvil1 && itemstack.getItemDamage() == 2){
				player.triggerAchievement(TFC_Achievements.achBronzeAge);
			}
			else if(itemstack.getItem() instanceof ItemIngot)
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
				TFC_ItemHeat.SetTemperature(itemstack, temperature);				
			}
			else if(itemstack.getItem() instanceof ItemMeltedMetal)
			{
				float temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemIngot )
						temperature = TFC_ItemHeat.GetTemperature(iinventory.getStackInSlot(i));
				}
				TFC_ItemHeat.SetTemperature(itemstack, temperature);

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
							CraftingHandler.DamageItem(player, iinventory, i, iinventory.getStackInSlot(i).getItem().itemID);
						}
						else if(itemstack.getTagCompound().hasKey("foodDecay") && itemstack.getTagCompound().getFloat("foodDecay") <= 0)
						{
							CraftingHandler.DamageItem(player, iinventory, i, iinventory.getStackInSlot(i).getItem().itemID);
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
			else if(itemstack.getItem() instanceof ItemMiscToolHead &&
					((ItemMiscToolHead)(itemstack.getItem())).getMaterial()!= null &&
					(((ItemMiscToolHead)(itemstack.getItem())).getMaterial() == TFCItems.IgInToolMaterial ||
					((ItemMiscToolHead)(itemstack.getItem())).getMaterial() == TFCItems.SedToolMaterial ||
					((ItemMiscToolHead)(itemstack.getItem())).getMaterial() == TFCItems.IgExToolMaterial ||
					((ItemMiscToolHead)(itemstack.getItem())).getMaterial() == TFCItems.MMToolMaterial))
			{
				player.triggerAchievement(TFC_Achievements.achStoneAge);
			}

			for(int i = 0; i < iinventory.getSizeInventory(); i++) 
			{             
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if(iinventory.getStackInSlot(i).itemID == TFCItems.WoodenBucketWater.itemID)
				{
					if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.WoodenBucketEmpty,1)))
						player.dropItem(TFCItems.WoodenBucketEmpty.itemID, 1);
				}
				//				else if(iinventory.getStackInSlot(i).itemID == TFCItems.RedSteelBucketWater.itemID)
				//					if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.RedSteelBucketEmpty,1)))
				//						player.dropItem(TFCItems.RedSteelBucketEmpty.itemID, 1);

				if(iinventory.getStackInSlot(i).hasTagCompound() && 
						iinventory.getStackInSlot(i).getTagCompound().hasKey("craftingTag"))
				{
					if(itemstack.getTagCompound() == null)
						itemstack.setTagCompound(new NBTTagCompound());
					itemstack.getTagCompound().setCompoundTag("craftingTag", iinventory.getStackInSlot(i).getTagCompound().getCompoundTag("craftingTag"));
				}

			}
		}
	}

	public static boolean gridHasItem(IInventory iinventory, int id)
	{
		for(int i = 0; i < iinventory.getSizeInventory(); i++) 
		{             
			if(iinventory.getStackInSlot(i) == null)
				continue;
			if(iinventory.getStackInSlot(i).getItem().itemID == id)
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
