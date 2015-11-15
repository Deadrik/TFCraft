package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

import com.bioxx.tfc.Containers.Slots.SlotArmorTFC;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemMeal;
import com.bioxx.tfc.Handlers.CraftingHandler;
import com.bioxx.tfc.Handlers.FoodCraftingHandler;
import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Interfaces.IEquipable;
import com.bioxx.tfc.api.Interfaces.IEquipable.EquipType;
import com.bioxx.tfc.api.Interfaces.IFood;

public class ContainerPlayerTFC extends ContainerPlayer
{
	private final EntityPlayer thePlayer;

	public ContainerPlayerTFC(InventoryPlayer playerInv, boolean par2, EntityPlayer player)
	{
		super(playerInv, par2, player);
		this.craftMatrix = new InventoryCrafting(this, 3, 3);
		this.inventorySlots.clear();
		this.inventoryItemStacks.clear();
		this.thePlayer = player;
		this.addSlotToContainer(new SlotCrafting(player, craftMatrix, craftResult, 0, 152, 36));
		int x;
		int y;

		for (x = 0; x < 2; ++x)
		{
			for (y = 0; y < 2; ++y)
				this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
		}

		for (x = 0; x < playerInv.armorInventory.length; ++x)
		{
			int index = playerInv.getSizeInventory() - 1 - x;
			this.addSlotToContainer(new SlotArmorTFC(this, playerInv, index, 8, 8 + x * 18, x));
		}
		PlayerInventory.buildInventoryLayout(this, playerInv, 8, 90, false, true);

		//Manually built the remaining crafting slots because of an order issue. These have to be created after the default slots
		if(player.getEntityData().hasKey("craftingTable") || !player.worldObj.isRemote)
		{
			x = 2; y = 0; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
			x = 2; y = 1; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
			x = 0; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
			x = 1; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
			x = 2; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18, 18 + x * 18));
		}
		else
		{
			//Have to create some dummy slots
			x = 2; y = 0; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
			x = 2; y = 1; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
			x = 0; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
			x = 1; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
			x = 2; y = 2; this.addSlotToContainer(new Slot(craftMatrix, y + x * 3, 82 + y * 18-50000, 18 + x * 18));
		}
		PlayerInventory.addExtraEquipables(this, playerInv, 8, 90, false);
		this.onCraftMatrixChanged(this.craftMatrix);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory iinventory)
	{
		super.onCraftMatrixChanged(iinventory);

		Slot craftOut = (Slot) this.inventorySlots.get(0);
		if (craftOut != null && craftOut.getHasStack())
		{
			ItemStack craftResult = craftOut.getStack();
			if (craftResult != null)
			{
				if (craftResult.getItem() instanceof ItemFoodTFC)
					FoodCraftingHandler.updateOutput(thePlayer, craftResult, craftMatrix);
				else
					CraftingHandler.transferNBT(false, thePlayer, craftResult, craftMatrix);
			}
		}
	}

	@Override
	public void onContainerClosed(EntityPlayer player)
	{
		if(!player.worldObj.isRemote)
		{
			super.onContainerClosed(player);

			for (int i = 0; i < 9; ++i)
			{
				ItemStack itemstack = this.craftMatrix.getStackInSlotOnClosing(i);
				if (itemstack != null)
					player.dropPlayerItemWithRandomChoice(itemstack, false);
			}

			this.craftResult.setInventorySlotContents(0, (ItemStack)null);
		}
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot) this.inventorySlots.get(slotNum);
		Slot equipmentSlot = (Slot) this.inventorySlots.get(50);

		if (slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack(); 
			origStack = slotStack.copy();

			// Crafting Grid Output
			if (slotNum == 0)
			{
				FoodCraftingHandler.preCraft(player, slotStack, craftMatrix);
				CraftingHandler.preCraft(player, slotStack, craftMatrix);

				if (!this.mergeItemStack(slotStack, 9, 45, true, true))
					return null;

				slot.onSlotChange(slotStack, origStack);
			}
			// From crafting grid input to inventory
			else if (slotNum >= 1 && slotNum < 5 || player.getEntityData().hasKey("craftingTable") && slotNum >= 45 && slotNum < 50)
			{
				if (!this.mergeItemStack(slotStack, 9, 45, true, false))
					return null;
			}
			// From armor or equipment slot to inventory
			else if (slotNum >= 5 && slotNum < 9 || slotNum == 50)
			{
				if (!this.mergeItemStack(slotStack, 9, 45, true, false))
					return null;
			}
			// From inventory to armor slots
			else if (origStack.getItem() instanceof ItemArmor)
			{
				int armorSlotNum = 5 + ((ItemArmor) origStack.getItem()).armorType;
				if (origStack.getItem() instanceof ItemTFCArmor)
				{
					armorSlotNum = 5 + ((ItemTFCArmor) origStack.getItem()).getUnadjustedArmorType();

					if (!((Slot) this.inventorySlots.get(armorSlotNum)).getHasStack())
					{
						if (!this.mergeItemStack(slotStack, armorSlotNum, armorSlotNum + 1, false, false))
							return null;
					}
				}
				else if (!((Slot) this.inventorySlots.get(armorSlotNum)).getHasStack())
				{
					if (!this.mergeItemStack(slotStack, armorSlotNum, armorSlotNum + 1, false, false))
						return null;
				}
			}
			// From inventory to back slot
			else if (!equipmentSlot.getHasStack() && origStack.getItem() instanceof IEquipable)
			{
				IEquipable equipment = (IEquipable) origStack.getItem();
				if (equipment.getEquipType(origStack) == EquipType.BACK && (equipment == TFCItems.quiver || equipment.getTooHeavyToCarry(origStack)))
				{
					ItemStack backStack = slotStack.copy();
					backStack.stackSize = 1;
					equipmentSlot.putStack(backStack);
					slotStack.stackSize--;
				}
			}
			// Food from inventory/hotbar to crafting grid
			else if (slotNum >= 9 && slotNum < 45 && origStack.getItem() instanceof IFood && !(origStack.getItem() instanceof ItemMeal) && !isCraftingGridFull())
			{
				if (!this.mergeItemStack(slotStack, 1, 5, false, false) && slotStack.stackSize == 0)
					return null;
				else if (slotStack.stackSize > 0 && player.getEntityData().hasKey("craftingTable") && !this.mergeItemStack(slotStack, 45, 50, false))
					return null;
				else if (slotStack.stackSize > 0 && slotNum >= 9 && slotNum < 36)
				{
					if (!this.mergeItemStack(slotStack, 36, 45, false, false))
						return null;
				}
				else if (slotStack.stackSize > 0 && slotNum >= 36 && slotNum < 45)
				{
					if (!this.mergeItemStack(slotStack, 9, 36, false, false))
						return null;
				}
			}
			// From inventory to hotbar
			else if (slotNum >= 9 && slotNum < 36)
			{
				if (!this.mergeItemStack(slotStack, 36, 45, false, false))
					return null;
			}
			// From hotbar to inventory
			else if (slotNum >= 36 && slotNum < 45)
			{
				if (!this.mergeItemStack(slotStack, 9, 36, false, false))
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

	@Override
	public ItemStack slotClick(int sourceSlotID, int destSlotID, int clickType, EntityPlayer p)
	{
		if (sourceSlotID >= 0 && sourceSlotID < this.inventorySlots.size())
		{
			Slot sourceSlot = (Slot) this.inventorySlots.get(sourceSlotID);
			ItemStack slotStack = sourceSlot.getStack();

			// Hotbar press to remove from crafting output
			if (clickType == 2 && sourceSlotID == 0 && slotStack != null)
			{
				CraftingHandler.preCraft(p, slotStack, craftMatrix);
			}
			// S and D hotkeys for trimming/combining food
			else if (clickType == 7 && sourceSlotID >= 9 && sourceSlotID < 45)
			{
				if (sourceSlot.canTakeStack(p))
				{
					Slot destSlot = (Slot) this.inventorySlots.get(destSlotID);
					destSlot.putStack(slotStack);
					sourceSlot.putStack(null);
					return null;
				}
			}
			// Couldn't figure out what was causing the food dupe with a full inventory, so we're just going to block shift clicking for that case.
			else if (clickType == 1 && sourceSlotID == 0 && isInventoryFull() && slotStack != null && slotStack.getItem() instanceof IFood)
				return null;
		}
		return super.slotClick(sourceSlotID, destSlotID, clickType, p);
	}

	protected boolean isCraftingGridFull()
	{
		for(int i = 0; i < this.craftMatrix.getSizeInventory(); i++)
		{
			if(this.craftMatrix.getStackInSlot(i) == null)
				return false;
		}
		return true;
	}

	protected boolean isInventoryFull()
	{
		// Slots 9 through 44 are the standard inventory and hotbar.
		for (int i = 9; i < 45; i++)
		{
			if (((Slot) inventorySlots.get(i)).getStack() == null)
				return false;
		}
		return true;
	}

	public EntityPlayer getPlayer()
	{
		return this.thePlayer;
	}

	protected boolean mergeItemStack(ItemStack is, int slotStart, int slotFinish, boolean reverseOrder, boolean craftingOutput)
	{
		boolean merged = false;
		int slotIndex = slotStart;

		if (reverseOrder)
			slotIndex = slotFinish - 1;

		Slot slot;
		ItemStack slotstack;

		if (is.isStackable())
		{
			while (is.stackSize > 0 && (!reverseOrder && slotIndex < slotFinish || reverseOrder && slotIndex >= slotStart))
			{
				slot = (Slot) this.inventorySlots.get(slotIndex);
				slotstack = slot.getStack();

				if (slotstack != null && slotstack.getItem() == is.getItem()
				//&& !is.getHasSubtypes()
				&& is.getItemDamage() == slotstack.getItemDamage() && ItemStack.areItemStackTagsEqual(is, slotstack) && slotstack.stackSize < slot.getSlotStackLimit())
				{
					int mergedStackSize = is.stackSize + getSmaller(slotstack.stackSize, slot.getSlotStackLimit());

					//First we check if we can add the two stacks together and the resulting stack is smaller than the maximum size for the slot or the stack
					if (mergedStackSize <= is.getMaxStackSize() && mergedStackSize <= slot.getSlotStackLimit())
					{
						is.stackSize = 0;
						slotstack.stackSize = mergedStackSize;
						slot.onSlotChanged();
						merged = true;
					}
					// Do not attempt merge stacks resulting in greater than the max size for slot/stack if shift-clicking the crafting grid output
					else if (!craftingOutput && slotstack.stackSize < is.getMaxStackSize() && slotstack.stackSize < slot.getSlotStackLimit())
					{
						// Slot stack size is greater than or equal to the item's max stack size. Most containers are this case.
						if (slot.getSlotStackLimit() >= is.getMaxStackSize())
						{
							is.stackSize -= is.getMaxStackSize() - slotstack.stackSize;
							slotstack.stackSize = is.getMaxStackSize();
							slot.onSlotChanged();
							merged = true;
						}
						// Slot stack size is smaller than the item's normal max stack size. Example: Log Piles
						else if (slot.getSlotStackLimit() < is.getMaxStackSize())
						{
							is.stackSize -= slot.getSlotStackLimit() - slotstack.stackSize;
							slotstack.stackSize = slot.getSlotStackLimit();
							slot.onSlotChanged();
							merged = true;
						}
					}
				}

				if (reverseOrder)
					--slotIndex;
				else
					++slotIndex;
			}
		}

		if (is.stackSize > 0)
		{
			if (reverseOrder)
				slotIndex = slotFinish - 1;
			else
				slotIndex = slotStart;

			while (!reverseOrder && slotIndex < slotFinish || reverseOrder && slotIndex >= slotStart)
			{
				slot = (Slot) this.inventorySlots.get(slotIndex);
				slotstack = slot.getStack();
				if (slotstack == null && slot.isItemValid(is) && slot.getSlotStackLimit() < is.stackSize)
				{
					ItemStack copy = is.copy();
					copy.stackSize = slot.getSlotStackLimit();
					is.stackSize -= slot.getSlotStackLimit();
					slot.putStack(copy);
					slot.onSlotChanged();
					merged = true;
					//this.bagsSlotNum = slotIndex;
					break;
				}
				else if (slotstack == null && slot.isItemValid(is))
				{
					slot.putStack(is.copy());
					slot.onSlotChanged();
					is.stackSize = 0;
					merged = true;
					break;
				}

				if (reverseOrder)
					--slotIndex;
				else
					++slotIndex;
			}
		}

		return merged;
	}

	protected int getSmaller(int i, int j)
	{
		if (i < j)
			return i;
		else
			return j;
	}
}
