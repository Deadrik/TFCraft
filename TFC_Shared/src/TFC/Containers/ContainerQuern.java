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
import TFC.Core.Player.PlayerInventory;
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
		
		PlayerInventory.buildInventoryLayout(this, playerinv, 8, 90, false, true);
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

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize)
	{
		this.addSlotToContainer(new SlotQuernGrain(chestInventory, 0, 66, 47));
		this.addSlotToContainer(new SlotBlocked(chestInventory, 1, 93, 47));
		this.addSlotToContainer(new SlotQuern(chestInventory, 2, 93, 20));
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
					|| (clickedStack.getItem() == TFCItems.SmallOreChunk && clickedStack.getItemDamage() == 11)//Small Limonite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 11)//Limonite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 46)//Rich Limonite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 60)//Poor Limonite
					|| (clickedStack.getItem() == TFCItems.SmallOreChunk && clickedStack.getItemDamage() == 9)//Small Malachite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 9)//Malachite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 44)//Rich Malachite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 58)//Poor Malachite
					|| (clickedStack.getItem() == TFCItems.SmallOreChunk && clickedStack.getItemDamage() == 3)//Small Hematite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 3)//Hematite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 38)//Rich Hematite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 52)//Poor Hematite
					|| (clickedStack.getItem() == TFCItems.OreChunk && clickedStack.getItemDamage() == 31)//Sylvite
					|| (clickedStack.getItem() == TFCItems.LooseRock && clickedStack.getItemDamage() == 5))//Rock Salt

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
