package TFC.Handlers;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.Recipes;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Food.ItemTerraFood;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
import cpw.mods.fml.common.ICraftingHandler;

public class CraftingHandler implements ICraftingHandler
{

	@Override
	public void onCrafting(EntityPlayer entityplayer, ItemStack itemstack, IInventory iinventory) 
	{
		int index = 0;

		if(iinventory != null)
		{
			if(itemstack.itemID == TFCBlocks.StoneSedBrick.blockID || itemstack.itemID == TFCBlocks.StoneIgInBrick.blockID || 
					itemstack.itemID == TFCBlocks.StoneIgExBrick.blockID || itemstack.itemID == TFCBlocks.StoneMMBrick.blockID)
			{
				HandleItem(entityplayer, iinventory, Recipes.Chisels);
			}
			else if(itemstack.itemID == TFCItems.SinglePlank.shiftedIndex)
			{
				HandleItem(entityplayer, iinventory, Recipes.Axes);
				HandleItem(entityplayer, iinventory, Recipes.Saws);
			}
			else if(itemstack.itemID == TFCItems.WoodSupportItemH.shiftedIndex || itemstack.itemID == TFCItems.WoodSupportItemV.shiftedIndex)
			{
				HandleItem(entityplayer, iinventory, Recipes.Saws);
			}
			else if(itemstack.itemID == Item.bowlEmpty.shiftedIndex || 
					itemstack.getItem() instanceof ItemTerraFood)
			{
				HandleItem(entityplayer, iinventory, Recipes.Knives);
			}
			else if(itemstack.itemID == TFCItems.WoodSupportItemV.shiftedIndex || itemstack.itemID == TFCItems.WoodSupportItemH.shiftedIndex)
			{
				HandleItem(entityplayer, iinventory, Recipes.Axes);
			}
			else if(itemstack.itemID == TFCItems.Flux.shiftedIndex)
			{
				HandleItem(entityplayer, iinventory, Recipes.Hammers);
			}
			else if(itemstack.itemID == TFCItems.LooseRock.shiftedIndex)
			{
				boolean openGui = false;
				for(int i = 0; i < iinventory.getSizeInventory(); i++) 
				{             
					if(iinventory.getStackInSlot(i) == null) 
					{
						continue;
					}
					if(iinventory.getStackInSlot(i).itemID == TFCItems.LooseRock.shiftedIndex)
					{
						if(iinventory.getStackInSlot(i).stackSize == 1)
							iinventory.setInventorySlotContents(i, null);
						else
						{
							ItemStack is = iinventory.getStackInSlot(i); is.stackSize-=1;
							iinventory.setInventorySlotContents(i, is);
						}
						itemstack.stackSize = 1;
						PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer);
						pi.knappingRockType = new ItemStack(TFCItems.FlatRock, 1, itemstack.getItemDamage());
						openGui = true;
					}
				}
				if(openGui)
					entityplayer.openGui(TerraFirmaCraft.instance, 28, entityplayer.worldObj, (int)entityplayer.posX, (int)entityplayer.posY, (int)entityplayer.posZ);

				//itemstack = new ItemStack(TFCItems.FlatRock, 1, itemstack.getItemDamage());
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
				DamageItem(entityplayer,iinventory,i,Items[j].shiftedIndex);
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
