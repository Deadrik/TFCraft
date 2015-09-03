package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotSpecialCraftingOutput;
import com.bioxx.tfc.Core.TFC_Achievements;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.Tools.ItemMiscToolHead;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Crafting.CraftingManagerTFC;

public class ContainerSpecialCrafting extends ContainerTFC
{
	/** The crafting matrix inventory (5x5).
	 *  Used for knapping and leather working */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 5, 5);

	private SlotSpecialCraftingOutput outputSlot;
	private boolean decreasedStack;

	/** The crafting result, size 1. */
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	private InventoryPlayer invPlayer;
	private boolean isConstructing;
	public ContainerSpecialCrafting(InventoryPlayer inventoryplayer, ItemStack is, World world, int x, int y, int z)
	{
		invPlayer = inventoryplayer;
		this.worldObj = world; // Must be set before inventorySlotContents to prevent NPE
		decreasedStack = false;
		isConstructing = true;
		bagsSlotNum = inventoryplayer.currentItem;
		for (int j1 = 0; j1 < 25; j1++)
		{
			if(is != null)
				craftMatrix.setInventorySlotContents(j1, is.copy());
		}

		outputSlot = new SlotSpecialCraftingOutput(this, inventoryplayer.player, craftMatrix, craftResult, 0, 128, 44);
		addSlotToContainer(outputSlot);

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 108, true, true);

		this.onCraftMatrixChanged(this.craftMatrix);
		isConstructing = false;
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		super.onContainerClosed(player);
		if (!this.worldObj.isRemote)
		{
			ItemStack is = this.craftResult.getStackInSlotOnClosing(0);
			if (is != null)
				player.entityDropItem(is, 0);
		}
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory ii)
	{
		ItemStack result = CraftingManagerTFC.getInstance().findMatchingRecipe(this.craftMatrix, worldObj);

		// Handle decreasing the stack of the held item used to open the interface.
		if (!decreasedStack && !isConstructing)
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(invPlayer.player);

			// A valid clay recipe has been formed.
			if (pi.specialCraftingType.getItem() == TFCItems.flatClay)
			{
				if (result != null)
				{
					setDecreasedStack(true); // Mark container so it won't decrease again.
					if (!this.worldObj.isRemote && invPlayer.getCurrentItem().stackSize >= 5) // Server only to prevent it removing multiple times.
						invPlayer.decrStackSize(invPlayer.currentItem, 5);
					else // Clientside or if the player doesn't have enough clay, return before the output slot is set.
					{
						setDecreasedStack(false);
						return;
					}
				}
			}
			// A piece of rock or leather has been removed.
			else if (hasPieceBeenRemoved(pi))
			{
				setDecreasedStack(true); // Mark container so it won't decrease again.
				if (!this.worldObj.isRemote) // Server only to prevent it removing multiple times.
					invPlayer.consumeInventoryItem(invPlayer.getCurrentItem().getItem());
			}
		}

		// The crafting output is only set if the input was consumed
		if (decreasedStack)
		{
			this.craftResult.setInventorySlotContents(0, result);

			// Trigger Achievements
			if (result != null && invPlayer.player != null)
			{
				Item item = result.getItem();
				if (item instanceof ItemMiscToolHead &&((ItemMiscToolHead) (item)).getMaterial() != null &&
					(((ItemMiscToolHead) (item)).getMaterial() == TFCItems.igInToolMaterial ||
						((ItemMiscToolHead) (item)).getMaterial() == TFCItems.sedToolMaterial ||
						((ItemMiscToolHead) (item)).getMaterial() == TFCItems.igExToolMaterial ||
						((ItemMiscToolHead) (item)).getMaterial() == TFCItems.mMToolMaterial))
				{
					invPlayer.player.triggerAchievement(TFC_Achievements.achStoneAge);
					if (item == TFCItems.stoneKnifeHead && result.stackSize == 2)
						invPlayer.player.triggerAchievement(TFC_Achievements.achTwoKnives);
				}
				else if (item == Item.getItemFromBlock(TFCBlocks.crucible))
					invPlayer.player.triggerAchievement(TFC_Achievements.achCrucible);
			}
		}
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 * @return 
	 */
	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot)this.inventorySlots.get(slotNum);

		if (slot != null && slot instanceof SlotSpecialCraftingOutput && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			if (slotNum < 1 && !this.mergeItemStack(slotStack, 1, inventorySlots.size(), true))
				return null;

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

	@Override
	public boolean canInteractWith(EntityPlayer player)
	{
		return true;
	}

	public boolean hasPieceBeenRemoved(PlayerInfo pi)
	{
		// Knapping interface is a boolean array where the value is true if that button has been pushed.
		for (int i = 0; i < this.craftMatrix.getSizeInventory(); i++)
		{
			if (this.craftMatrix.getStackInSlot(i) == null)
				return true;
		}

		// Reset the decreasedStack flag if no pieces have been removed.
		setDecreasedStack(false);
		return false;
	}

	public void setDecreasedStack(Boolean b)
	{
		this.decreasedStack = b;
	}

}
