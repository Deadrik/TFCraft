package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import TFC.Containers.Slots.SlotForShowOnly;
import TFC.Containers.Slots.SlotSizeMedium;

public class ContainerVessel extends ContainerTFC {
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private EntityPlayer player;
	public InventoryCrafting containerInv = new InventoryCrafting(this, 2, 2);
	public IInventory bagslot = new InventoryCraftResult();

	public int bagsSlotNum = 0;

	public ContainerVessel(InventoryPlayer playerinv, World world, int x, int y, int z) {
		this.player = playerinv.player;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		layoutContainer(playerinv, 0, 0);

		if(!world.isRemote)
		{
			this.bagslot.setInventorySlotContents(0, player.inventory.getCurrentItem().copy());
			bagsSlotNum = player.inventory.currentItem;
			player.inventory.setInventorySlotContents(bagsSlotNum, null);
			loadBagInventory();
		}
	}

	public void loadBagInventory()
	{
		if(bagslot.getStackInSlot(0).hasTagCompound())
		{
			NBTTagList nbttaglist = bagslot.getStackInSlot(0).getTagCompound().getTagList("Items");

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
	public void onCraftGuiClosed(EntityPlayer player) {
		super.onCraftGuiClosed(player);
		if (!this.world.isRemote)
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

			if(!bagslot.getStackInSlot(0).hasTagCompound())
				bagslot.getStackInSlot(0).setTagCompound(new NBTTagCompound());
			bagslot.getStackInSlot(0).getTagCompound().setTag("Items", nbttaglist);

			if(player.inventory.getStackInSlot(bagsSlotNum) == null)
				player.inventory.setInventorySlotContents(bagsSlotNum, bagslot.getStackInSlot(0));
			else if(!player.inventory.addItemStackToInventory(bagslot.getStackInSlot(0)))
			{
				player.dropPlayerItem(bagslot.getStackInSlot(0));
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, int xSize, int ySize) {

		this.addSlotToContainer(new SlotSizeMedium(containerInv, 0, 71, 25));
		this.addSlotToContainer(new SlotSizeMedium(containerInv, 1, 89, 25));
		this.addSlotToContainer(new SlotSizeMedium(containerInv, 2, 71, 43));
		this.addSlotToContainer(new SlotSizeMedium(containerInv, 3, 89, 43));
		this.addSlotToContainer(new SlotForShowOnly(bagslot, 0, 80, 64));

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

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
	}

	//Removed because I don't write shift click code. This needs to be worked on. -Bioxx
	/*@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex) {
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null
			&& clickedSlot.getHasStack()
			&& (clickedSlot.getStack().getItem() instanceof ItemTerraFood || clickedSlot.getStack().itemID == Item.bowlEmpty.itemID))
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 6)
			{
				if (!this.mergeItemStack(clickedStack, 6, inventorySlots.size(), true)) {
					return null;
				}
			}
			else if (clickedIndex >= 6 && clickedIndex < inventorySlots.size()) {
				if (!this.mergeItemStack(clickedStack, 0, 6, false)) {
					return null;
				}
			}
			else if (!this.mergeItemStack(clickedStack, 6, inventorySlots.size(), false)) {
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
	}*/
}
