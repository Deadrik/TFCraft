package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import TFC.Containers.Slots.SlotForShowOnly;
import TFC.Containers.Slots.SlotSizeSmallVessel;

public class ContainerVessel extends ContainerTFC {
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	public InventoryCrafting containerInv = new InventoryCrafting(this, 2, 2);
	private ItemStack bagStack = null;

	public ContainerVessel(InventoryPlayer playerinv, World world, int x, int y, int z) {
		this.player = playerinv.player;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		bagsSlotNum = player.inventory.currentItem;
		bagStack = player.inventory.getCurrentItem();
		layoutContainer(playerinv, 0, 0);

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
			this.isLoading = true;
			for(int i = 0; i < nbttaglist.tagCount(); i++)
			{
				NBTTagCompound nbttagcompound1 = (NBTTagCompound)nbttaglist.tagAt(i);
				byte byte0 = nbttagcompound1.getByte("Slot");
				if(byte0 >= 0 && byte0 < 4)
				{
					this.containerInv.setInventorySlotContents(byte0, ItemStack.loadItemStackFromNBT(nbttagcompound1));
				}
			}
		}
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		/*if(ContainerTFC.areItemStacksEqual(bagStack, player.inventory.getCurrentItem())) {
			saveContents(player.inventory.getStackInSlot(bagsSlotNum));
		}*/
	}

	@Override
	public void saveContents(ItemStack is) 
	{
		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < containerInv.getSizeInventory(); i++)
		{
			if(containerInv.getStackInSlot(i) != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				containerInv.getStackInSlot(i).writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		if(is != null)
		{
			if(!is.hasTagCompound()) {
				is.setTagCompound(new NBTTagCompound());
			}
			is.getTagCompound().setTag("Items", nbttaglist);

		}
	}



	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, int xSize, int ySize) {

		this.addSlotToContainer(new SlotSizeSmallVessel(containerInv, 0, 71, 25));
		this.addSlotToContainer(new SlotSizeSmallVessel(containerInv, 1, 89, 25));
		this.addSlotToContainer(new SlotSizeSmallVessel(containerInv, 2, 71, 43));
		this.addSlotToContainer(new SlotSizeSmallVessel(containerInv, 3, 89, 43));

		int row;
		int col;

		for (row = 0; row < 9; ++row) 
		{
			if(row == bagsSlotNum) {
				this.addSlotToContainer(new SlotForShowOnly(playerInventory, row, 8 + row * 18, 142));
			} else {
				this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 142));
			}
		}

		for (row = 0; row < 3; ++row) 
		{
			for (col = 0; col < 9; ++col) 
			{
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9+9, 8 + col * 18, 84 + row * 18));
			}
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
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

			if (clickedIndex < 4)
			{
				if (!this.mergeItemStack(clickedStack, 4, inventorySlots.size(), true)) {
					return null;
				}
			}
			else if (clickedIndex >= 4 && clickedIndex < inventorySlots.size()) {
				if (!this.mergeItemStack(clickedStack, 0, 4, false)) {
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
