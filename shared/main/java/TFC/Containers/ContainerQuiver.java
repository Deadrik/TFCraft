package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import TFC.Containers.Slots.SlotQuiver;
import TFC.Core.Player.PlayerInventory;

public class ContainerQuiver extends ContainerTFC {
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	public InventoryCrafting containerInv = new InventoryCrafting(this, 4, 2);

	public ContainerQuiver(InventoryPlayer playerinv, World world, int x, int y, int z) {
		this.player = playerinv.player;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		bagsSlotNum = player.inventory.currentItem;

		this.addSlotToContainer(new SlotQuiver(containerInv, 0, 53, 8));
		this.addSlotToContainer(new SlotQuiver(containerInv, 1, 71, 8));
		this.addSlotToContainer(new SlotQuiver(containerInv, 2, 89, 8));
		this.addSlotToContainer(new SlotQuiver(containerInv, 3, 107, 8));
		this.addSlotToContainer(new SlotQuiver(containerInv, 4, 53, 26));
		this.addSlotToContainer(new SlotQuiver(containerInv, 5, 71, 26));
		this.addSlotToContainer(new SlotQuiver(containerInv, 6, 89, 26));
		this.addSlotToContainer(new SlotQuiver(containerInv, 7, 107, 26));

		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 54, true);

		if(!world.isRemote)
		{
			loadBagInventory();
		}
		this.doItemSaving = true;
	}

	public void loadBagInventory()
	{
		if(player.inventory.getStackInSlot(bagsSlotNum) != null && 
				player.inventory.getStackInSlot(bagsSlotNum).hasTagCompound())
		{
			NBTTagList nbttaglist = player.inventory.getStackInSlot(bagsSlotNum).getTagCompound().getTagList("Items");

			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < 8)
				{
					this.containerInv.setInventorySlotContents(byte0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
				}
			}
		}
	}


	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex) {
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null
				&& clickedSlot.getHasStack())
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 8)
			{
				if (!this.mergeItemStack(clickedStack, 8, inventorySlots.size(), true)) {
					return null;
				}
			}
			else if (clickedIndex >= 8 && clickedIndex < inventorySlots.size()) {
				if (!this.mergeItemStack(clickedStack, 0, 8, false)) {
					return null;
				}
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
