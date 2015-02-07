package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotBlocked;
import com.bioxx.tfc.Containers.Slots.SlotFoodOnly;
import com.bioxx.tfc.Containers.Slots.SlotSize;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEFoodPrep;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Interfaces.IFood;

public class ContainerFoodPrep extends ContainerTFC
{
	//private World world;
	//private int posX;
	//private int posY;
	//private int posZ;
	private TEFoodPrep te;
	private EntityPlayer player;
	int guiTab = 0;

	public ContainerFoodPrep(InventoryPlayer playerinv, TEFoodPrep pile, World world, int x, int y, int z, int tab)
	{
		this.player = playerinv.player;
		this.te = pile;
		//this.world = world;
		//this.posX = x;
		//this.posY = y;
		//this.posZ = z;
		guiTab = tab;
		pile.openInventory();
		layoutContainer(playerinv, pile, 0, 0);
		pile.lastTab = tab;
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		te.closeInventory();
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize)
	{
		if(guiTab == 0)
		{
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 0, 44, 24).addItemInclusion(TFCItems.WheatBread, TFCItems.BarleyBread, TFCItems.OatBread,
					TFCItems.RyeBread, TFCItems.RiceBread, TFCItems.CornBread).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 1, 62, 24).addFGException(EnumFoodGroup.Grain).addItemException(TFCItems.WoodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 2, 80, 24).addFGException(EnumFoodGroup.Grain).addItemException(TFCItems.WoodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 3, 98, 24).addFGException(EnumFoodGroup.Grain).addItemException(TFCItems.WoodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 4, 116, 24).addFGException(EnumFoodGroup.Grain).addItemException(TFCItems.WoodenBucketMilk).setSize(EnumSize.HUGE));
			/*this.addSlotToContainer(new SlotFoodOnly(chestInventory, 5, 125, 11).addItemInclusion(TFCItems.WheatBread, TFCItems.BarleyBread, TFCItems.OatBread, 
					TFCItems.RyeBread, TFCItems.RiceBread, TFCItems.CornBread).setSize(EnumSize.HUGE));*/
		}
		else if(guiTab == 1)
		{
			this.addSlotToContainer(new SlotBlocked(chestInventory, 0, 15, 8));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 1, 53, 24).addItemException(TFCItems.WoodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 2, 71, 24).addItemException(TFCItems.WoodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 3, 89, 24).addItemException(TFCItems.WoodenBucketMilk).setSize(EnumSize.HUGE));
			this.addSlotToContainer(new SlotFoodOnly(chestInventory, 4, 107, 24).addItemException(TFCItems.WoodenBucketMilk).setSize(EnumSize.HUGE));
			/*this.addSlotToContainer(new SlotBlocked(chestInventory, 5, 16, 44));*/
		}
		this.addSlotToContainer(new SlotBlocked(chestInventory, 6, 53, 46));
		this.addSlotToContainer(new SlotSize(chestInventory, 7, 145, 8).setSize(EnumSize.SMALL));
		this.addSlotToContainer(new SlotSize(chestInventory, 8, 145, 26).setSize(EnumSize.SMALL));
		this.addSlotToContainer(new SlotSize(chestInventory, 9, 145, 44).setSize(EnumSize.SMALL));
		this.addSlotToContainer(new SlotSize(chestInventory, 10, 145, 62).setSize(EnumSize.SMALL));
	}

	public EntityPlayer getPlayer()
	{
		return player;
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int clickedIndex)
	{
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null
				&& clickedSlot.getHasStack()
				&& (clickedSlot.getStack().getItem() instanceof IFood || clickedSlot.getStack().getItem() == Items.bowl))
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 11)
			{
				if (!this.mergeItemStack(clickedStack, 11, inventorySlots.size(), true))
					return null;
			}
			else if (clickedIndex >= 11 && clickedIndex < inventorySlots.size())
			{
				//if (!this.mergeItemStack(clickedStack, 0, 11, false))
				return null;
			}
			else if (!this.mergeItemStack(clickedStack, 11, inventorySlots.size(), false))
				return null;

			if (clickedStack.stackSize == 0)
				clickedSlot.putStack((ItemStack)null);
			else
				clickedSlot.onSlotChanged();

			if (clickedStack.stackSize == returnedStack.stackSize)
				return null;

			clickedSlot.onPickupFromSlot(player, clickedStack);
		}
		return returnedStack;
	}
}
