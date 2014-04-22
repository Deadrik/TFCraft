package TFC.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.TFC_ItemHeat;
import TFC.Core.Recipes;
import TFC.Core.TFC_Achievements;
import TFC.Core.TFC_Sounds;
import TFC.Core.Player.PlayerInventory;
import TFC.Handlers.Network.AbstractPacket;
import TFC.Handlers.Network.PlayerUpdatePacket;
import TFC.Items.ItemIngot;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.ItemBlocks.ItemAnvil1;
import TFC.Items.Tools.ItemCustomPickaxe;
import TFC.Items.Tools.ItemCustomSaw;
import TFC.Items.Tools.ItemMiscToolHead;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingHandler
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
				player.inventory.clearInventory(Item.getItemFromBlock(Blocks.crafting_table), -1);
				if(!player.getEntityData().hasKey("craftingTable") || !player.worldObj.isRemote)
				{
					player.getEntityData().setBoolean("craftingTable", true);
					AbstractPacket pkt = new PlayerUpdatePacket(player, 2);
					TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) player);
					PlayerInventory.upgradePlayerCrafting(player);
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
				player.triggerAchievement(TFC_Achievements.achPickaxe);
			}
			else if(itemstack.getItem() instanceof ItemCustomSaw)
			{
				player.triggerAchievement(TFC_Achievements.achSaw);
			}
			else if(itemstack.getItem() == Items.bowl || 
					/*itemstack.getItem() instanceof ItemTerraFood ||*/ itemstack.getItem() == TFCItems.ScrapedHide
					|| itemstack.getItem() == TFCItems.Wool||itemstack.getItem() == TFCItems.TerraLeather)
			{
				HandleItem(e.player, e.craftMatrix, Recipes.Knives);
				if (item == TFCItems.Wool && !player.worldObj.isRemote)
				{
					if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Hide, 1, 0)))
						player.entityDropItem(new ItemStack(TFCItems.Hide, 1, 0), 1);
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
			else if(item == TFCItems.Mortar)
			{
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.WoodenBucketWater)
						iinventory.setInventorySlotContents(i, new ItemStack(TFCItems.Limewater));
				}
			}
			else if(item == TFCItems.Limewater)
			{
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.WoodenBucketWater)
						iinventory.setInventorySlotContents(i, new ItemStack(TFCItems.Mortar));
				}
			}
			else if(itemstack.getItem() instanceof ItemAnvil1 && itemstack.getItemDamage() == 2)
			{
				player.triggerAchievement(TFC_Achievements.achBronzeAge);
			}
			else if(itemstack.getItem() instanceof ItemIngot)
			{
				if(player.worldObj.rand.nextInt(20) == 0)
					player.playSound(TFC_Sounds.CERAMICBREAK, 0.7f, player.worldObj.rand.nextFloat() * 0.2F + 0.8F);
				else if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.CeramicMold, 1, 1)))
					player.entityDropItem(new ItemStack(TFCItems.CeramicMold, 1, 1), 1);

				short temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++)
				{
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemMeltedMetal)
						temperature = TFC_ItemHeat.GetTemp(iinventory.getStackInSlot(i));
				}
				TFC_ItemHeat.SetTemp(e.crafting, temperature);
			}
			else if(item instanceof ItemMeltedMetal)
			{
				short temperature = 0;
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
				player.triggerAchievement(TFC_Achievements.achStoneAge);
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
