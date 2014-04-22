package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.API.IFood;
import TFC.Containers.Slots.SlotBlocked;
import TFC.Containers.Slots.SlotFoodBowl;
import TFC.Containers.Slots.SlotFoodOnly;
import TFC.Core.Player.PlayerInventory;
import TFC.TileEntities.TEFoodPrep;

public class ContainerFoodPrep extends ContainerTFC {
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private TEFoodPrep te;
	private EntityPlayer player;

	public ContainerFoodPrep(InventoryPlayer playerinv, TEFoodPrep pile, World world, int x, int y, int z) {
		this.player = playerinv.player;
		this.te = pile;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		pile.openChest();
		layoutContainer(playerinv, pile, 0, 0);

		PlayerInventory.buildInventoryLayout(this, playerinv, -28, 90, false, true);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		te.closeChest();
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 0, 35, 8));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 1, 35, 26));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 2, 35, 44));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 3, 35, 62));
		this.addSlotToContainer(new SlotBlocked(chestInventory, 4, 79, 35));
		this.addSlotToContainer(new SlotFoodBowl(chestInventory, 5, 57, 35));
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex) {
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null
				&& clickedSlot.getHasStack()
				&& (clickedSlot.getStack().getItem() instanceof IFood || clickedSlot.getStack().itemID == Item.bowlEmpty.itemID))
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 6)
			{
				if (!this.mergeItemStack(clickedStack, 6, inventorySlots.size(), true))
					return null;
			}
			else if (clickedIndex >= 6 && clickedIndex < inventorySlots.size()) {
				if (!this.mergeItemStack(clickedStack, 0, 6, false))
					return null;
			}
			else if (!this.mergeItemStack(clickedStack, 6, inventorySlots.size(), false))
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
