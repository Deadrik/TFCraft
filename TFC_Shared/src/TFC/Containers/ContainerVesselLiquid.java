package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.API.Enums.EnumMetalType;
import TFC.Containers.Slots.SlotForShowOnly;

public class ContainerVesselLiquid extends ContainerTFC {
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private EntityPlayer player;
	public InventoryCrafting containerInv = new InventoryCrafting(this, 1, 1);
	
	public int bagsSlotNum = 0;
	public int metalAmount = 0;

	public ContainerVesselLiquid(InventoryPlayer playerinv, World world, int x, int y, int z) {
		this.player = playerinv.player;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		bagsSlotNum = player.inventory.currentItem;
		layoutContainer(playerinv, 0, 0);

	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, int xSize, int ySize) {

		this.addSlotToContainer(new Slot(containerInv, 0, 71, 25));

		int row;
		int col;

		for (row = 0; row < 9; ++row) 
		{
			if(row == bagsSlotNum)
				this.addSlotToContainer(new SlotForShowOnly(playerInventory, row, 8 + row * 18, 142));
			else
				this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 142));
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
		if(containerInv.getStackInSlot(0).getItem().itemID == TFCItems.CeramicMold.itemID && 
				containerInv.getStackInSlot(0).getItemDamage() == 1)
		{
			//Load the metal info from the liquid container
			NBTTagCompound nbt = player.inventory.getCurrentItem().getTagCompound();
			EnumMetalType m = EnumMetalType.fromID(nbt.getInteger("MetalType"));
			metalAmount = nbt.getInteger("MetalAmount");
			
			//TODO: create a meltedmetal itemstack for the metal and apply the proper damage value
			
		}
		
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

			if (clickedIndex == 0)
			{
				if (!this.mergeItemStack(clickedStack, 1, inventorySlots.size(), true)) {
					return null;
				}
			}
			else if (clickedIndex > 0 && clickedIndex < inventorySlots.size() && clickedStack.getItem().itemID == TFCItems.CeramicMold.itemID && 
					clickedStack.getItemDamage() == 1) {
				if (!this.mergeItemStack(clickedStack, 0, 1, false)) {
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
