package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.Containers.Slots.SlotBlocked;
import TFC.Containers.Slots.SlotQuern;
import TFC.Containers.Slots.SlotQuernGrain;
import TFC.TileEntities.TileEntityQuern;

public class ContainerQuern extends ContainerTFC {
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private TileEntityQuern te;
	private EntityPlayer player;

	public ContainerQuern(InventoryPlayer playerinv, TileEntityQuern pile, World world, int x, int y, int z) {
		this.player = playerinv.player;
		this.te = pile;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		pile.openChest();
		layoutContainer(playerinv, pile, 0, 0);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer) {
		super.onContainerClosed(par1EntityPlayer);
		if(!world.isRemote) {
			te.closeChest();
		} else {
			te.validate();
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) {
		this.addSlotToContainer(new SlotQuernGrain(chestInventory, 0, 66, 47));
		this.addSlotToContainer(new SlotBlocked(chestInventory, 1, 93, 47));
		this.addSlotToContainer(new SlotQuern(chestInventory, 2, 93, 20));

		int row;
		int col;

		for (row = 0; row < 9; ++row) {
			this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 142));
		}

		for (row = 0; row < 3; ++row) {
			for (col = 0; col < 9; ++col) {
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9+9, 8 + col * 18, 84 + row * 18));
			}
		}
	}

	public EntityPlayer getPlayer() {
		return player;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex) {
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null && clickedSlot.getHasStack()) {
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 3) {
				if (!this.mergeItemStack(clickedStack, 3, inventorySlots.size(), true)) {
					return null;
				}
			}
			else if (clickedIndex >= 3
					&& clickedIndex < inventorySlots.size()
					&& clickedStack.getItem() == TFCItems.WheatGrain
					|| clickedStack.getItem() == TFCItems.BarleyGrain
					|| clickedStack.getItem() == TFCItems.RyeGrain
					|| clickedStack.getItem() == TFCItems.OatGrain
					|| clickedStack.getItem() == TFCItems.RiceGrain
					|| clickedStack.getItem() == TFCItems.MaizeEar		
					|| clickedStack.getItem() == Item.bone
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 16)//Kaolinite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 20)//Graphite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 27)//Cinnabar
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 28)//Cryolite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 34)//Lapis Lazuli
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 11)//Limonite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 9)//Malachite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 3))//Hematite

			{
				if (!this.mergeItemStack(clickedStack, 0, 1, false)) {
					return null;
				}
			}
			else if (clickedIndex >= 3
					&& clickedIndex < inventorySlots.size()
					&& clickedStack.getItem() == TFCItems.Quern)
			{
				if (!this.mergeItemStack(clickedStack, 2, 3, false)) {
					return null;
				}
			}
			else if (clickedIndex >= 3 && clickedIndex < inventorySlots.size()) {
				return null;
			}

			if (clickedStack.stackSize == 0) {
				clickedSlot.putStack((ItemStack)null);
			} else {
				clickedSlot.onSlotChanged();
			}

			if (clickedStack.stackSize == returnedStack.stackSize) {
				return null;
			}

			clickedSlot.onPickupFromSlot(player, clickedStack);
		}

		return returnedStack;
	}
}
