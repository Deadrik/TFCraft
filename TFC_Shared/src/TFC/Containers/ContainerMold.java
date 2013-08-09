package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.Containers.Slots.SlotBlocked;
import TFC.Containers.Slots.SlotMoldTool;
import TFC.Core.CraftingManagerTFC;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.ItemMeltedMetal;

public class ContainerMold extends ContainerTFC {
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private EntityPlayer player;
	public InventoryCrafting containerInv = new InventoryCrafting(this, 2, 1);
	public IInventory craftResult = new InventoryCraftResult();

	public ContainerMold(InventoryPlayer playerinv, World world, int x, int y, int z) {
		this.player = playerinv.player;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		layoutContainer(playerinv, 0, 0);
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(playerinv.player);
		containerInv.setInventorySlotContents(0, pi.specialCraftingType);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer player) {
		super.onContainerClosed(player);
		if (!this.world.isRemote)
		{
			ItemStack itemstack = this.craftResult.getStackInSlotOnClosing(0);
			ItemStack itemstack2 = this.containerInv.getStackInSlotOnClosing(0);
			ItemStack itemstack3 = this.containerInv.getStackInSlotOnClosing(1);
			if (itemstack != null)
			{
				player.dropPlayerItem(itemstack);
			}
			if (itemstack2 != null)
			{
				player.dropPlayerItem(itemstack2);
			}
			if (itemstack3 != null)
			{
				player.dropPlayerItem(itemstack3);
			}
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, int xSize, int ySize) {
		this.addSlotToContainer(new SlotMoldTool(containerInv, 0, 41, 17));
		this.addSlotToContainer(new SlotMoldTool(containerInv, 1, 62, 17));
		this.addSlotToContainer(new SlotBlocked(craftResult, 0, 116, 17));

		int row;
		int col;

		for (row = 0; row < 9; ++row) {
			this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 107));
		}

		for (row = 0; row < 3; ++row) {
			for (col = 0; col < 9; ++col) {
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9+9, 8 + col * 18, 49 + row * 18));
			}
		}
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		if(craftResult.getStackInSlot(0) == null)
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
			if(containerInv.getStackInSlot(1) != null && pi.moldTransferTimer < 100) {
				pi.moldTransferTimer++;
			}

			if(containerInv.getStackInSlot(0) != null && containerInv.getStackInSlot(1) != null && pi.moldTransferTimer == 1000) {
				pi.moldTransferTimer = 0;
			}

			if(containerInv.getStackInSlot(0) == null || containerInv.getStackInSlot(1) == null) {
				pi.moldTransferTimer = 1000;
			}

			if(containerInv.getStackInSlot(0) != null && containerInv.getStackInSlot(1) != null && pi.moldTransferTimer == 100 && CraftingManagerTFC.getInstance().findMatchingRecipe(this.containerInv, world) != null)
			{
				ItemStack is = CraftingManagerTFC.getInstance().findMatchingRecipe(this.containerInv, world);
				is.setTagCompound(containerInv.getStackInSlot(1).stackTagCompound);
				craftResult.setInventorySlotContents(0, is);
				containerInv.setInventorySlotContents(1, new ItemStack(TFCItems.CeramicMold, 1, 1));
				containerInv.setInventorySlotContents(0, null);
			}
			else if(containerInv.getStackInSlot(0) != null && containerInv.getStackInSlot(1) != null && pi.moldTransferTimer == 100)
			{
				if(containerInv.getStackInSlot(0).getItem() instanceof ItemMeltedMetal && 
						containerInv.getStackInSlot(1).getItem() instanceof ItemMeltedMetal)
				{
					if(containerInv.getStackInSlot(0).getItem().itemID == containerInv.getStackInSlot(1).getItem().itemID)
					{
						int s0 = 100-containerInv.getStackInSlot(0).getItemDamage();
						int s1 = 100-containerInv.getStackInSlot(1).getItemDamage();
						int total = s0 + s1;
						if(total <= 100)
						{
							ItemStack is = containerInv.getStackInSlot(0).copy();
							is.setItemDamage(100-total);
							craftResult.setInventorySlotContents(0, is);
							containerInv.setInventorySlotContents(1, new ItemStack(TFCItems.CeramicMold, 1));
							containerInv.setInventorySlotContents(0, null);
						}
						else
						{
							ItemStack is = containerInv.getStackInSlot(0).copy();
							is.setItemDamage(0);
							craftResult.setInventorySlotContents(0, is);

							is = containerInv.getStackInSlot(0).copy();
							is.setItemDamage(100-(total-100));
							containerInv.setInventorySlotContents(1, is);

							containerInv.setInventorySlotContents(0, null);

						}
					}
				}
			}
		}
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
