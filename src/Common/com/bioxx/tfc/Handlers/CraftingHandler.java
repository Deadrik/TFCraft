package com.bioxx.tfc.Handlers;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.oredict.OreDictionary;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Sounds;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.PlayerUpdatePacket;
import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.Items.ItemBlocks.ItemAnvil1;
import com.bioxx.tfc.Items.ItemBlocks.ItemAnvil2;
import com.bioxx.tfc.Items.Tools.ItemCustomPickaxe;
import com.bioxx.tfc.Items.Tools.ItemCustomSaw;
import com.bioxx.tfc.Items.Tools.ItemMiscToolHead;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Crafting.AnvilManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingHandler
{
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent e)//(EntityPlayer player, ItemStack itemstack, IInventory iinventory)
	{
		//EntityPlayer player = e.player;
		Item item = e.crafting.getItem();
		ItemStack itemstack = e.crafting;
		int isDmg = e.crafting.getItemDamage();
		IInventory iinventory = e.craftMatrix;

		//int index = 0;
		if(iinventory != null)
		{
			if(item == TFCItems.StoneBrick)
				HandleItem(e.player, e.craftMatrix, Recipes.Chisels);
			else if(item == Item.getItemFromBlock(TFCBlocks.Workbench))
			{
				if (!e.player.getEntityData().hasKey("craftingTable"))
					e.player.inventory.clearInventory(Item.getItemFromBlock(TFCBlocks.Workbench), -1);

				if(!e.player.worldObj.isRemote)
				{
					if(!e.player.getEntityData().hasKey("craftingTable"))
					{
						e.player.getEntityData().setBoolean("craftingTable", true);
						try
						{
							AbstractPacket pkt = new PlayerUpdatePacket(e.player, 2);
							TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) e.player);
						}
						catch (Exception e1)
						{
							System.out.println("--------------------------------------------------");
							e1.printStackTrace();
							System.out.println("--------------------------------------------------");
						}
						PlayerInventory.upgradePlayerCrafting(e.player);
					}
				}
			}
			else if(item == TFCItems.SinglePlank ||
					item == Item.getItemFromBlock(TFCBlocks.WoodSupportH) || item == Item.getItemFromBlock(TFCBlocks.WoodSupportH2) ||
					item == Item.getItemFromBlock(TFCBlocks.WoodSupportV) || item == Item.getItemFromBlock(TFCBlocks.WoodSupportV2))
			{
				HandleItem(e.player, e.craftMatrix, Recipes.Axes);
				HandleItem(e.player, e.craftMatrix, Recipes.Saws);
			}
			else if(itemstack.getItem() instanceof ItemCustomPickaxe)
			{
				e.player.triggerAchievement(TFC_Achievements.achPickaxe);
			}
			else if(itemstack.getItem() instanceof ItemCustomSaw)
			{
				e.player.triggerAchievement(TFC_Achievements.achSaw);
			}
			else if(itemstack.getItem() == Items.bowl || 
					/*itemstack.getItem() instanceof ItemTerraFood ||*/ itemstack.getItem() == TFCItems.ScrapedHide
					|| itemstack.getItem() == TFCItems.Wool||itemstack.getItem() == TFCItems.Leather)
			{
				HandleItem(e.player, e.craftMatrix, Recipes.Knives);
				if (item == TFCItems.Wool /*&& !player.worldObj.isRemote*/)
				{
					int size = 0;
					for(int i = 0; i < iinventory.getSizeInventory(); i++) 
					{
						if(iinventory.getStackInSlot(i) == null)
							continue;
						if(iinventory.getStackInSlot(i).getItem() == TFCItems.SheepSkin)
							size = iinventory.getStackInSlot(i).getItemDamage();
					}
					boolean add =!e.player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Hide, 1, size));
					if(add)
						e.player.entityDropItem(new ItemStack(TFCItems.Hide, 1, size), 1);
				}
				/*else if(item == TFCItems.TerraLeather)
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
				}*/
			}
			else if(item == TFCItems.WoolYarn)
			{
				HandleItem(e.player,e.craftMatrix,Recipes.Spindle);
			}
			else if(item == TFCItems.Powder && isDmg == 0)
			{
				HandleItem(e.player, e.craftMatrix, Recipes.Hammers);
			}
			else if((itemstack.getItem() instanceof ItemAnvil1 && itemstack.getItemDamage() == 2) ||
					(itemstack.getItem() instanceof ItemAnvil2 && (itemstack.getItemDamage() == 1 || itemstack.getItemDamage() == 2)))
			{
				e.player.triggerAchievement(TFC_Achievements.achBronzeAge);
			}
			else if(itemstack.getItem() instanceof ItemIngot)
			{
				float temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemMeltedMetal)
					{
						temperature = TFC_ItemHeat.GetTemp(iinventory.getStackInSlot(i));
						if(e.player.worldObj.rand.nextInt(20) == 0)
							e.player.worldObj.playSoundAtEntity(e.player, TFC_Sounds.CERAMICBREAK, 0.7f, e.player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
						else
							TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.CeramicMold, 1, 1), e.player);
					}
				}
				TFC_ItemHeat.SetTemp(e.crafting, temperature);
			}
			else if(item instanceof ItemMeltedMetal)
			{
				float temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemIngot )
						temperature = TFC_ItemHeat.GetTemp(iinventory.getStackInSlot(i));
				}
				TFC_ItemHeat.SetTemp(e.crafting, temperature);
			}
			else if(itemstack.getItem() instanceof ItemMiscToolHead &&
					((ItemMiscToolHead)(itemstack.getItem())).getMaterial()!= null &&
					(((ItemMiscToolHead)(itemstack.getItem())).getMaterial() == TFCItems.IgInToolMaterial ||
					((ItemMiscToolHead)(itemstack.getItem())).getMaterial() == TFCItems.SedToolMaterial ||
					((ItemMiscToolHead)(itemstack.getItem())).getMaterial() == TFCItems.IgExToolMaterial ||
					((ItemMiscToolHead)(itemstack.getItem())).getMaterial() == TFCItems.MMToolMaterial))
			{
				e.player.triggerAchievement(TFC_Achievements.achStoneAge);
			}
			else if(itemstack.getItem() == TFCItems.PotterySmallVessel && itemstack.getItemDamage() == 0)
			{
				String[] dyes =
					{ "White", "Orange", "Magenta", "LightBlue", "Yellow", "Lime", "Pink", "Gray", "LightGray", "Cyan", "Purple", "Blue", "Brown", "Green", "Red", "Black" };
				int color = -1;
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;

					int[] ids = OreDictionary.getOreIDs(iinventory.getStackInSlot(i));
					float[] c = null;
					for(int id : ids)
					{
						String name = OreDictionary.getOreName(id);
						if(name.equals("dyeWhite"))
							c = EntitySheep.fleeceColorTable[0];
						else if(name.equals("dyeOrange"))
							c = EntitySheep.fleeceColorTable[1];
						else if(name.equals("dyeMagenta"))
							c = EntitySheep.fleeceColorTable[2];
						else if(name.equals("dyeLightBlue"))
							c = EntitySheep.fleeceColorTable[3];
						else if(name.equals("dyeYellow"))
							c = EntitySheep.fleeceColorTable[4];
						else if(name.equals("dyeLime"))
							c = EntitySheep.fleeceColorTable[5];
						else if(name.equals("dyePink"))
							c = EntitySheep.fleeceColorTable[6];
						else if(name.equals("dyeGray"))
							c = EntitySheep.fleeceColorTable[7];
						else if(name.equals("dyeLightGray"))
							c = EntitySheep.fleeceColorTable[8];
						else if(name.equals("dyeCyan"))
							c = EntitySheep.fleeceColorTable[9];
						else if(name.equals("dyePurple"))
							c = EntitySheep.fleeceColorTable[10];
						else if(name.equals("dyeBlue"))
							c = EntitySheep.fleeceColorTable[11];
						else if(name.equals("dyeBrown"))
							c = EntitySheep.fleeceColorTable[12];
						else if(name.equals("dyeGreen"))
							c = EntitySheep.fleeceColorTable[13];
						else if(name.equals("dyeRed"))
							c = EntitySheep.fleeceColorTable[14];
						else if(name.equals("dyeBlack"))
							c = EntitySheep.fleeceColorTable[15];
					}

					if(c != null)
					{
						int r = (int)(c[0]*255);
						int g = (int)(c[1]*255);
						int b = (int)(c[2]*255);
						r = r << 16;
						g = g << 8;

						color += r += g += b;
					}

				}

				if(color != -1)
				{
					NBTTagCompound nbt = null;
					if(itemstack.hasTagCompound())
						nbt = itemstack.getTagCompound();
					else
						nbt = new NBTTagCompound();

					nbt.setInteger("color", color);
					itemstack.setTagCompound(nbt);
				}
			}

			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;

				// Only transfer the tag when making something out of a tool head. Don't transfer when crafting things with the completed tool.
				// Note: If crafting recipes with armor or completed tools to further refine them are ever added, the instanceof will need to be updated. -Kitty
				if(iinventory.getStackInSlot(i).hasTagCompound() && 
						iinventory.getStackInSlot(i).getTagCompound().hasKey("craftingTag") &&
						iinventory.getStackInSlot(i).getItem() instanceof ItemMiscToolHead)
				{
					AnvilManager.setCraftTag(itemstack, AnvilManager.getCraftTag(iinventory.getStackInSlot(i)));
				}
			}
		}
	}

	public static void preCraft(EntityPlayer player, ItemStack craftResult, IInventory iinventory)
	{
		if(craftResult.getItem() instanceof ItemIngot)
		{
			float temperature = 0;
			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if(iinventory.getStackInSlot(i).getItem() instanceof ItemMeltedMetal)
				{
					temperature = TFC_ItemHeat.GetTemp(iinventory.getStackInSlot(i));
					if(player.worldObj.rand.nextInt(20) == 0)
						player.playSound(TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
					else
						TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.CeramicMold, 1, 1), player);
					iinventory.setInventorySlotContents(i, null);
				}
			}
			TFC_ItemHeat.SetTemp(craftResult, temperature);
		}
		else if(craftResult.getItem() instanceof ItemMeltedMetal)
		{
			float temperature = 0;
			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if(iinventory.getStackInSlot(i).getItem() instanceof ItemIngot)
					temperature = TFC_ItemHeat.GetTemp(iinventory.getStackInSlot(i));
			}
			TFC_ItemHeat.SetTemp(craftResult, temperature);
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
