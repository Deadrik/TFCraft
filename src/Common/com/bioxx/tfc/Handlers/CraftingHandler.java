package com.bioxx.tfc.Handlers;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
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
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Crafting.AnvilManager;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingHandler
{
	@SubscribeEvent
	public void onCrafting(ItemCraftedEvent e)//(EntityPlayer player, ItemStack itemstack, IInventory iinventory)
	{
		EntityPlayer player = e.player;
		ItemStack itemstack = e.crafting;
		Item item = itemstack.getItem();
		int itemDamage = itemstack.getItemDamage();
		IInventory iinventory = e.craftMatrix;

		//int index = 0;
		if(iinventory != null)
		{
			// Tool Damaging
			if(item == TFCItems.StoneBrick)
			{
				HandleItem(player, iinventory, Recipes.Chisels);
			}
			else if(item == TFCItems.SinglePlank ||
					item == Item.getItemFromBlock(TFCBlocks.WoodSupportH) || item == Item.getItemFromBlock(TFCBlocks.WoodSupportH2) ||
					item == Item.getItemFromBlock(TFCBlocks.WoodSupportV) || item == Item.getItemFromBlock(TFCBlocks.WoodSupportV2))
			{
				HandleItem(player, iinventory, Recipes.Axes);
				HandleItem(player, iinventory, Recipes.Saws);
			}
			else if (item == TFCItems.Wool)
			{
				HandleItem(player, iinventory, Recipes.Knives);
				int size = 0;
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.SheepSkin)
						size = iinventory.getStackInSlot(i).getItemDamage();
				}
				boolean add = !player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Hide, 1, size));
				if (add)
					player.entityDropItem(new ItemStack(TFCItems.Hide, 1, size), 1);
			}
			else if(item == TFCItems.WoolYarn)
			{
				HandleItem(player, iinventory, Recipes.Spindle);
			}
			else if (item == TFCItems.Powder && itemDamage == 0)
			{
				HandleItem(player, iinventory, Recipes.Hammers);
			}

			// Achievements
			else if (item instanceof ItemCustomPickaxe)
			{
				player.triggerAchievement(TFC_Achievements.achPickaxe);
			}
			else if (item instanceof ItemCustomSaw)
			{
				player.triggerAchievement(TFC_Achievements.achSaw);
			}
			else if ((item instanceof ItemAnvil1 && itemDamage == 2) || (item instanceof ItemAnvil2 && (itemDamage == 1 || itemDamage == 2)))
			{
				player.triggerAchievement(TFC_Achievements.achBronzeAge);
			}
			else if (item instanceof ItemMiscToolHead && ((ItemMiscToolHead) (item)).getMaterial() != null && 
					(((ItemMiscToolHead) (item)).getMaterial() == TFCItems.IgInToolMaterial || ((ItemMiscToolHead) (item)).getMaterial() == TFCItems.SedToolMaterial || 
					((ItemMiscToolHead) (item)).getMaterial() == TFCItems.IgExToolMaterial || ((ItemMiscToolHead) (item)).getMaterial() == TFCItems.MMToolMaterial))
			{
				player.triggerAchievement(TFC_Achievements.achStoneAge);
			}

			// Packet Sending
			else if (item == Item.getItemFromBlock(TFCBlocks.Workbench))
			{
				if (!player.getEntityData().hasKey("craftingTable"))
					player.inventory.clearInventory(Item.getItemFromBlock(TFCBlocks.Workbench), -1);

				if (!player.worldObj.isRemote)
				{
					if (!player.getEntityData().hasKey("craftingTable"))
					{
						player.getEntityData().setBoolean("craftingTable", true);
						try
						{
							AbstractPacket pkt = new PlayerUpdatePacket(player, 2);
							TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) player);
						} catch (Exception e1)
						{
							System.out.println("--------------------------------------------------");
							e1.printStackTrace();
							System.out.println("--------------------------------------------------");
						}
						PlayerInventory.upgradePlayerCrafting(player);
					}
				}
			}

			// NBT Transfer - Need to also do down in preCraft for shift-clicking.
			else if (item instanceof ItemIngot)
			{
				float temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemMeltedMetal)
					{
						temperature = TFC_ItemHeat.GetTemp(iinventory.getStackInSlot(i));
						if (player.worldObj.rand.nextInt(20) == 0)
							player.worldObj.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
						else
							TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.CeramicMold, 1, 1), player);
					}
				}
				TFC_ItemHeat.SetTemp(itemstack, temperature);
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
				TFC_ItemHeat.SetTemp(itemstack, temperature);
			}
			else if (item == TFCItems.PotterySmallVessel && itemDamage == 0)
			{
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
						for (int j = 0; j < Global.DYE_NAMES.length; j++)
						{
							if (name.equals(Global.DYE_NAMES[j]))
							{
								c = EntitySheep.fleeceColorTable[j];
								break;
							}
						}
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
				if (iinventory.getStackInSlot(i).getItem() instanceof ItemMiscToolHead && 
						iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("craftingTag"))
				{
					AnvilManager.setCraftTag(itemstack, AnvilManager.getCraftTag(iinventory.getStackInSlot(i)));
				}
			}
		}
	}

	public static void preCraft(EntityPlayer player, ItemStack itemstack, IInventory iinventory)
	{
		Item item = itemstack.getItem();
		int itemDamage = itemstack.getItemDamage();
		if (item instanceof ItemIngot)
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
						player.worldObj.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
					else
						TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.CeramicMold, 1, 1), player);
					iinventory.setInventorySlotContents(i, null);
				}
			}
			TFC_ItemHeat.SetTemp(itemstack, temperature);
		}
		else if (item instanceof ItemMeltedMetal)
		{
			float temperature = 0;
			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if(iinventory.getStackInSlot(i).getItem() instanceof ItemIngot)
					temperature = TFC_ItemHeat.GetTemp(iinventory.getStackInSlot(i));
			}
			TFC_ItemHeat.SetTemp(itemstack, temperature);
		}
		else if (item == TFCItems.PotterySmallVessel && itemDamage == 0)
		{
			int color = -1;
			for (int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if (iinventory.getStackInSlot(i) == null)
					continue;

				int[] ids = OreDictionary.getOreIDs(iinventory.getStackInSlot(i));
				float[] c = null;
				for (int id : ids)
				{
					String name = OreDictionary.getOreName(id);
					for (int j = 0; j < Global.DYE_NAMES.length; j++)
					{
						if (name.equals(Global.DYE_NAMES[j]))
						{
							c = EntitySheep.fleeceColorTable[j];
							break;
						}
					}
				}

				if (c != null)
				{
					int r = (int) (c[0] * 255);
					int g = (int) (c[1] * 255);
					int b = (int) (c[2] * 255);
					r = r << 16;
					g = g << 8;

					color += r += g += b;
				}
			}

			if (color != -1)
			{
				NBTTagCompound nbt = null;
				if (itemstack.hasTagCompound())
					nbt = itemstack.getTagCompound();
				else
					nbt = new NBTTagCompound();

				nbt.setInteger("color", color);
				itemstack.setTagCompound(nbt);
			}
		}

		for (int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if (iinventory.getStackInSlot(i) == null)
				continue;

			// Only transfer the tag when making something out of a tool head. Don't transfer when crafting things with the completed tool.
			// Note: If crafting recipes with armor or completed tools to further refine them are ever added, the instanceof will need to be updated. -Kitty
			if (iinventory.getStackInSlot(i).getItem() instanceof ItemMiscToolHead && 
					iinventory.getStackInSlot(i).hasTagCompound() && iinventory.getStackInSlot(i).getTagCompound().hasKey("craftingTag"))
			{
				AnvilManager.setCraftTag(itemstack, AnvilManager.getCraftTag(iinventory.getStackInSlot(i)));
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
