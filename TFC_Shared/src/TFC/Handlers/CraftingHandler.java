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
import TFC.API.TFC_ItemHeat;
import TFC.Core.Recipes;
import TFC.Core.TFC_Achievements;
import TFC.Core.TFC_Sounds;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.ItemIngot;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.ItemBlocks.ItemAnvil1;
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
				player.inventory.clearInventory(Block.workbench.blockID, -1);

				if(!player.getEntityData().hasKey("craftingTable") || !player.worldObj.isRemote)
				{
					player.getEntityData().setBoolean("craftingTable", true);
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
			}	
			else if(itemstack.itemID == TFCItems.SinglePlank.itemID)
			{
				HandleItem(player, iinventory, Recipes.Axes);
				HandleItem(player, iinventory, Recipes.Saws);
			}
			else if(itemstack.itemID == TFCBlocks.WoodSupportH.blockID || itemstack.itemID == TFCBlocks.WoodSupportV.blockID || itemstack.itemID == TFCBlocks.WoodSupportH2.blockID || itemstack.itemID == TFCBlocks.WoodSupportV2.blockID)
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
				if (itemstack.itemID == TFCItems.Wool.itemID && !player.worldObj.isRemote)
				{
					if(!player.inventory.addItemStackToInventory(new ItemStack(TFCItems.Hide, 1, 0)))
						player.entityDropItem(new ItemStack(TFCItems.Hide, 1, 0), 1);
				}
				/*else if(itemstack.itemID == TFCItems.TerraLeather.itemID)
				{
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
				}*/
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
				short temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemMeltedMetal)
						temperature = TFC_ItemHeat.GetTemp(iinventory.getStackInSlot(i));
				}
				TFC_ItemHeat.SetTemp(itemstack, temperature);				
			}
			else if(itemstack.getItem() instanceof ItemMeltedMetal)
			{
				short temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null)
						continue;
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemIngot )
						temperature = TFC_ItemHeat.GetTemp(iinventory.getStackInSlot(i));
				}
				TFC_ItemHeat.SetTemp(itemstack, temperature);

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
