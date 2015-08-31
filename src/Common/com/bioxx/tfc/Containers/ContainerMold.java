package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotBlocked;
import com.bioxx.tfc.Containers.Slots.SlotMoldTool;
import com.bioxx.tfc.Containers.Slots.SlotMoldTool2;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Crafting.CraftingManagerTFC;

public class ContainerMold extends ContainerTFC
{
	private World world;
	//private int posX;
	//private int posY;
	//private int posZ;
	private EntityPlayer player;
	public InventoryCrafting containerInv = new InventoryCrafting(this, 2, 1);
	public IInventory craftResult = new InventoryCraftResult();

	public ContainerMold(InventoryPlayer playerinv, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.world = world;
		//this.posX = x;
		//this.posY = y;
		//this.posZ = z;
		layoutContainer(playerinv, 0, 0);
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(playerinv.player);
		containerInv.setInventorySlotContents(0, pi.specialCraftingType);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		if (!this.world.isRemote)
		{
			ItemStack itemstack = this.craftResult.getStackInSlotOnClosing(0);
			ItemStack itemstack2 = this.containerInv.getStackInSlotOnClosing(0);
			ItemStack itemstack3 = this.containerInv.getStackInSlotOnClosing(1);
			if (itemstack != null)
				player.entityDropItem(itemstack, 0);
			if (itemstack2 != null)
				player.entityDropItem(itemstack2, 0);
			if (itemstack3 != null)
				player.entityDropItem(itemstack3, 0);
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, int xSize, int ySize)
	{
		this.addSlotToContainer(new SlotMoldTool(containerInv, 0, 41, 34));
		this.addSlotToContainer(new SlotMoldTool2(containerInv, 1, 94, 34));
		this.addSlotToContainer(new SlotBlocked(craftResult, 0, 116, 34));
	}

	@Override
	public void updateProgressBar(int id, int value)
	{
		if (id == 0)
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			pi.moldTransferTimer = (short) value;
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		if(craftResult.getStackInSlot(0) == null)
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			short oldTransferTimer = pi.moldTransferTimer;

			if(containerInv.getStackInSlot(0) != null && containerInv.getStackInSlot(1) != null)
			{
				if(containerInv.getStackInSlot(0).getItem() instanceof ItemMeltedMetal &&
						containerInv.getStackInSlot(1).getItem() == TFCItems.ceramicMold &&
						containerInv.getStackInSlot(1).getItemDamage() == 1 &&
						TFC_ItemHeat.getIsLiquid(containerInv.getStackInSlot(0)))
				{
					ItemStack is = containerInv.getStackInSlot(0).copy();
					is.setItemDamage(100);
					containerInv.setInventorySlotContents(1,is);
					pi.moldTransferTimer = 100;
				}
				else if(containerInv.getStackInSlot(0).getItem() instanceof ItemMeltedMetal && 
						containerInv.getStackInSlot(1).getItem() instanceof ItemMeltedMetal && 
						containerInv.getStackInSlot(0).getItem() == containerInv.getStackInSlot(1).getItem() &&
						containerInv.getStackInSlot(1).getItemDamage() != 0)
				{
					pi.moldTransferTimer = 100;
				}
			}

			if(containerInv.getStackInSlot(1) != null && pi.moldTransferTimer < 100 && 
					CraftingManagerTFC.getInstance().findMatchingRecipe(this.containerInv, world) != null)
			{
				pi.moldTransferTimer++;
			}

			if(containerInv.getStackInSlot(0) != null && containerInv.getStackInSlot(1) != null && pi.moldTransferTimer == 1000)
				pi.moldTransferTimer = 0;

			if(containerInv.getStackInSlot(0) == null || containerInv.getStackInSlot(1) == null)
				pi.moldTransferTimer = 1000;

			if(containerInv.getStackInSlot(0) != null && containerInv.getStackInSlot(1) != null && pi.moldTransferTimer == 100 &&
					containerInv.getStackInSlot(0).getItem() instanceof ItemMeltedMetal && 
					containerInv.getStackInSlot(1).getItem() instanceof ItemMeltedMetal)
			{
				if(containerInv.getStackInSlot(0).getItem() == containerInv.getStackInSlot(1).getItem() && containerInv.getStackInSlot(1).getItemDamage() != 0)
				{
					int s0 = containerInv.getStackInSlot(0).getItemDamage();
					int s1 = containerInv.getStackInSlot(1).getItemDamage();

					containerInv.getStackInSlot(0).setItemDamage(s0+1);
					containerInv.getStackInSlot(1).setItemDamage(s1-1);
					if(containerInv.getStackInSlot(0).getItemDamage() == containerInv.getStackInSlot(0).getMaxDamage())
						containerInv.setInventorySlotContents(0, new ItemStack(TFCItems.ceramicMold, 1, 1));
				}
			}
			else if(containerInv.getStackInSlot(0) != null && containerInv.getStackInSlot(1) != null && pi.moldTransferTimer == 100 &&
					CraftingManagerTFC.getInstance().findMatchingRecipe(this.containerInv, world) != null)
			{
				ItemStack is = CraftingManagerTFC.getInstance().findMatchingRecipe(this.containerInv, world);
				is.setTagCompound(containerInv.getStackInSlot(1).stackTagCompound);
				craftResult.setInventorySlotContents(0, is);
				containerInv.setInventorySlotContents(1, null);
				containerInv.setInventorySlotContents(1, new ItemStack(TFCItems.ceramicMold, 1, 1));
				containerInv.setInventorySlotContents(0, null);
			}

			for (int var1 = 0; var1 < this.crafters.size(); ++var1)
			{
				ICrafting var2 = (ICrafting)this.crafters.get(var1);
				if (pi.moldTransferTimer != oldTransferTimer)
					var2.sendProgressBarUpdate(this, 0, pi.moldTransferTimer);
			}
		}
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)inventorySlots.get(slotNum);

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// To inventory
			if (slotNum < 3)
			{
				if (!this.mergeItemStack(slotStack, 3, inventorySlots.size(), true))
					return null;
			}
			else
			{
				if (!this.mergeItemStack(slotStack, 0, 3, false))
					return null;
			}

			if (slotStack.stackSize <= 0)
				slot.putStack(null);
			else
				slot.onSlotChanged();

			if (slotStack.stackSize == origStack.stackSize)
				return null;

			slot.onPickupFromSlot(player, slotStack);
		}

		return origStack;
	}
}
