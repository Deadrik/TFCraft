package TFC.Handlers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.Recipes;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Sounds;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Food.ItemTerraFood;
import TFC.Items.ItemIngot;
import TFC.Items.ItemMeltedMetal;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler
{

	@Override
	public void onCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory) 
	{
		int index = 0;
		if(iinventory != null)
		{
			if(itemstack.itemID == TFCItems.StoneBrick.itemID)
			{
				HandleItem(entityplayer, iinventory, Recipes.Chisels);
			}
			else if(itemstack.itemID == TFCItems.SinglePlank.itemID)
			{
				HandleItem(entityplayer, iinventory, Recipes.Axes);
				HandleItem(entityplayer, iinventory, Recipes.Saws);
			}
			else if(itemstack.itemID == TFCItems.WheatGrain.itemID || itemstack.itemID == TFCItems.RyeGrain.itemID || 
					itemstack.itemID == TFCItems.OatGrain.itemID || itemstack.itemID == TFCItems.BarleyGrain.itemID || 
					itemstack.itemID == TFCItems.RiceGrain.itemID)
			{
				HandleItem(entityplayer, iinventory, Recipes.Knives);
				if(!entityplayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.Straw,4))) {
					entityplayer.dropItem(TFCItems.Straw.itemID,4);
				}
			}
			else if(itemstack.itemID == TFCBlocks.WoodSupportH.blockID || itemstack.itemID == TFCBlocks.WoodSupportV.blockID)
			{
				HandleItem(entityplayer, iinventory, Recipes.Saws);
				HandleItem(entityplayer, iinventory, Recipes.Axes);
			}
			else if(itemstack.itemID == Item.bowlEmpty.itemID || 
					itemstack.getItem() instanceof ItemTerraFood || itemstack.itemID == TFCItems.ScrapedHide.itemID
					|| itemstack.itemID == TFCItems.Wool.itemID||itemstack.itemID == TFCItems.TerraLeather.itemID)
			{
				HandleItem(entityplayer, iinventory, Recipes.Knives);
				if (itemstack.itemID == TFCItems.Wool.itemID && !entityplayer.worldObj.isRemote){
					if(!entityplayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.Hide, 1, 0))) 
					{
						entityplayer.entityDropItem(new ItemStack(TFCItems.Hide, 1, 0), 1);
					}
				}
				else if(itemstack.itemID == TFCItems.TerraLeather.itemID){
					boolean openGui = false;
					for(int i = 0; i < iinventory.getSizeInventory(); i++) 
					{             
						if(iinventory.getStackInSlot(i) == null) 
						{
							continue;
						}
						if(true)//iinventory.getStackInSlot(i).itemID == TFCItems.TerraLeather.shiftedIndex)
						{
							if(iinventory.getStackInSlot(i).stackSize == 1) {
								iinventory.setInventorySlotContents(i, null);
							} else
							{
								ItemStack is = iinventory.getStackInSlot(i); is.stackSize-=1;
								if(is.stackSize > 0) {
									iinventory.setInventorySlotContents(i, is);
								}
							}

							openGui = true;
							//itemstack.stackSize = -1;
						}
					}
					if(openGui)
					{
						PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
						pi.specialCraftingType = new ItemStack(TFCItems.FlatLeather, 1, itemstack.getItemDamage());
						entityplayer.openGui(TerraFirmaCraft.instance, 28, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);
					}
				}
			}
			else if(itemstack.itemID == TFCItems.WoolYarn.itemID)
			{
				HandleItem(entityplayer,iinventory,Recipes.Spindle);
			}
			else if(itemstack.itemID == TFCItems.Powder.itemID && itemstack.getItemDamage() == 0)
			{
				HandleItem(entityplayer, iinventory, Recipes.Hammers);
			}
			else if(itemstack.itemID == TFCItems.Mortar.itemID)
			{
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null) 
					{
						continue;
					}
					System.out.println(iinventory.getStackInSlot(i).getItem());
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.WoodenBucketWater)
					{
						iinventory.getStackInSlot(i).itemID = TFCItems.Limewater.itemID;
					}
				}

			}
			else if(itemstack.itemID == TFCItems.Limewater.itemID)
			{
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null) 
					{
						continue;
					}
					if(iinventory.getStackInSlot(i).getItem() == TFCItems.WoodenBucketWater)
					{
						iinventory.getStackInSlot(i).itemID = TFCItems.Mortar.itemID;
					}
				}

			}
			else if(itemstack.getItem() instanceof ItemIngot)
			{
				if(entityplayer.worldObj.rand.nextInt(20) == 0)
				{
					entityplayer.playSound(TFC_Sounds.CERAMICBREAK, 0.7f, entityplayer.worldObj.rand.nextFloat() * 0.2F + 0.8F);
				}
				else if(!entityplayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.CeramicMold, 1, 1))) 
				{
					entityplayer.entityDropItem(new ItemStack(TFCItems.CeramicMold, 1, 1), 1);
				}
				float temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null) 
					{
						continue;
					}
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemMeltedMetal)
					{
						temperature = TFC_ItemHeat.GetTemperature(iinventory.getStackInSlot(i));
					}
				}
				TFC_ItemHeat.SetTemperature(itemstack, temperature);				
			}
			else if(itemstack.getItem() instanceof ItemMeltedMetal)
			{
				float temperature = 0;
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{       
					if(iinventory.getStackInSlot(i) == null) 
					{
						continue;
					}
					if(iinventory.getStackInSlot(i).getItem() instanceof ItemIngot )
					{
						temperature = TFC_ItemHeat.GetTemperature(iinventory.getStackInSlot(i));
					}
				}
				TFC_ItemHeat.SetTemperature(itemstack, temperature);
				
			}

			for(int i = 0; i < iinventory.getSizeInventory(); i++) 
			{             
				if(iinventory.getStackInSlot(i) == null) 
				{
					continue;
				}
				if(iinventory.getStackInSlot(i).itemID == TFCItems.WoodenBucketWater.itemID)
				{
					if(!entityplayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.WoodenBucketEmpty,1))) {
						entityplayer.dropItem(TFCItems.WoodenBucketEmpty.itemID, 1);
					}
				}
				else if(iinventory.getStackInSlot(i).itemID == TFCItems.RedSteelBucketWater.itemID)
				{
					if(!entityplayer.inventory.addItemStackToInventory(new ItemStack(TFCItems.RedSteelBucketEmpty,1))) {
						entityplayer.dropItem(TFCItems.RedSteelBucketEmpty.itemID, 1);
					}
				}

			}
		}
	}

	public static void HandleItem(EntityPlayer entityplayer, IInventory iinventory, Item[] Items)
	{
		ItemStack item = null;
		for(int i = 0; i < iinventory.getSizeInventory(); i++) 
		{             
			if(iinventory.getStackInSlot(i) == null) 
			{
				continue;
			}
			for(int j = 0; j < Items.length; j++) 
			{  
				DamageItem(entityplayer,iinventory,i,Items[j].itemID);
			}
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
					if(iinventory.getStackInSlot(index).stackSize > 2) {
						iinventory.getStackInSlot(index).stackSize = 2;
					}
				}
			}
		}
	}

	@Override
	public void onSmelting(EntityPlayer player, ItemStack item) {
		// TODO Auto-generated method stub

	}

}
