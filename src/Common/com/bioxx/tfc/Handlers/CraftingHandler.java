package com.bioxx.tfc.Handlers;

import java.util.List;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.oredict.OreDictionary;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

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
			if(item == TFCItems.stoneBrick)
			{
				List<ItemStack> chisels = OreDictionary.getOres("itemChisel", false);
				handleItem(player, iinventory, chisels);
			}
			else if(item == TFCItems.singlePlank ||
					item == Item.getItemFromBlock(TFCBlocks.woodSupportH) || item == Item.getItemFromBlock(TFCBlocks.woodSupportH2) ||
					item == Item.getItemFromBlock(TFCBlocks.woodSupportV) || item == Item.getItemFromBlock(TFCBlocks.woodSupportV2))
			{
				List<ItemStack> axes = OreDictionary.getOres("itemAxe", false);
				List<ItemStack> saws = OreDictionary.getOres("itemSaw", false);
				handleItem(player, iinventory, axes);
				handleItem(player, iinventory, saws);
			}
			else if (item == TFCItems.wool)
			{
				List<ItemStack> knives = OreDictionary.getOres("itemKnife", false);
				handleItem(player, iinventory, knives);
				int size = 0;
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if (iinventory.getStackInSlot(i) == null)
						continue;
					if (iinventory.getStackInSlot(i).getItem() == TFCItems.sheepSkin)
						size = iinventory.getStackInSlot(i).getItemDamage();
				}

				TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.hide, 1, size), player);
			}
			else if(item == TFCItems.woolYarn)
			{
				handleItem(player, iinventory, Recipes.spindle);
			}
			else if (item == TFCItems.powder && itemDamage == 0)
			{
				List<ItemStack> hammers = OreDictionary.getOres("itemHammer", false);
				handleItem(player, iinventory, hammers);
			}

			// Achievements
			triggerAchievements(player, itemstack, item, itemDamage);

			// Packet Sending
			if (item == Item.getItemFromBlock(TFCBlocks.workbench))
			{
				if (!player.getEntityData().hasKey("craftingTable"))
					player.inventory.clearInventory(Item.getItemFromBlock(TFCBlocks.workbench), -1);

				if (!player.worldObj.isRemote)
				{
					if (!player.getEntityData().hasKey("craftingTable"))
					{
						player.getEntityData().setBoolean("craftingTable", true);
						try
						{
							AbstractPacket pkt = new PlayerUpdatePacket(player, 2);
							TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) player);
						} catch (Exception e1)
						{
							TerraFirmaCraft.LOG.info("--------------------------------------------------");
							TerraFirmaCraft.LOG.catching(e1);
							TerraFirmaCraft.LOG.info("--------------------------------------------------");
						}
						PlayerInventory.upgradePlayerCrafting(player);
					}
				}
			}

			if (!player.worldObj.isRemote && item instanceof ItemIngot)
			{
				for (int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					ItemStack is = iinventory.getStackInSlot(i);
					if (is == null)
						continue;
					else if (is.getItem() instanceof ItemMeltedMetal)
					{
						if (player.worldObj.rand.nextInt(20) == 0)
							player.worldObj.playSoundAtEntity(player, TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
						else
							TFC_Core.giveItemToPlayer(new ItemStack(TFCItems.ceramicMold, 1, 1), player);

						break;
					}
				}
			}
		}
	}

	public static void preCraft(EntityPlayer player, ItemStack itemstack, IInventory iinventory)
	{
		triggerAchievements(player, itemstack, itemstack.getItem(), itemstack.getItemDamage());
	}

	public static void triggerAchievements(EntityPlayer player, ItemStack itemstack, Item item, int itemDamage)
	{
		if (item instanceof ItemCustomPickaxe)
		{
			player.triggerAchievement(TFC_Achievements.achPickaxe);
		}
		else if (item instanceof ItemCustomSaw)
		{
			player.triggerAchievement(TFC_Achievements.achSaw);
		}
		else if (item instanceof ItemAnvil1 && itemDamage == 2 || item instanceof ItemAnvil2 && (itemDamage == 1 || itemDamage == 2))
		{
			player.triggerAchievement(TFC_Achievements.achBronzeAge);
		}
		else if (item == Item.getItemFromBlock(TFCBlocks.blastFurnace))
			player.triggerAchievement(TFC_Achievements.achBlastFurnace);
		else if (item == TFCItems.clayBall && itemDamage == 1)
			player.triggerAchievement(TFC_Achievements.achFireClay);
		else if (item == TFCItems.unknownIngot)
			player.triggerAchievement(TFC_Achievements.achUnknown);
	}

	/*
	 * Called during ContainerPlayerTFC's onCraftMatrixChanged to update the item in the output slot.
	 */
	public static void transferNBT(boolean clearContents, EntityPlayer player, ItemStack itemstack, IInventory iinventory)
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
					temperature = TFC_ItemHeat.getTemp(iinventory.getStackInSlot(i));
				}
			}
			TFC_ItemHeat.setTemp(itemstack, temperature);
		}
		else if (item instanceof ItemMeltedMetal)
		{
			float temperature = 0;
			for(int i = 0; i < iinventory.getSizeInventory(); i++)
			{
				if(iinventory.getStackInSlot(i) == null)
					continue;
				if(iinventory.getStackInSlot(i).getItem() instanceof ItemIngot)
					temperature = TFC_ItemHeat.getTemp(iinventory.getStackInSlot(i));
			}
			TFC_ItemHeat.setTemp(itemstack, temperature);
		}
		else if (item == TFCItems.potterySmallVessel && itemDamage == 0)
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

	public static void handleItem(EntityPlayer entityplayer, IInventory iinventory, Item[] items)
	{
		for(int i = 0; i < iinventory.getSizeInventory(); i++)
		{
			if(iinventory.getStackInSlot(i) == null)
				continue;
			for(int j = 0; j < items.length; j++)
				damageItem(entityplayer, iinventory, i, items[j]);
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

	public static void damageItem(EntityPlayer entityplayer, IInventory iinventory, int i, Item shiftedindex)
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
