package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotCookableFoodOnly;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.TileEntities.TEGrill;
import com.bioxx.tfc.api.TileEntities.TEFireEntity;

public class ContainerGrill extends ContainerTFC
{
	//private TEGrill grill;
	private TEFireEntity fire;
	private float firetemp;
	//private int charcoal;

	public ContainerGrill(InventoryPlayer inventoryplayer, TEGrill grill, World world, int x, int y, int z)
	{
		//this.grill = grill;
		firetemp = -1111;

		if(world.getTileEntity(x, y-1, z) instanceof TEFireEntity)
		{
			fire = (TEFireEntity)world.getTileEntity(x, y-1, z);
		}

		//Input slot
		addSlotToContainer(new SlotCookableFoodOnly(grill, 0, 71, 17));
		addSlotToContainer(new SlotCookableFoodOnly(grill, 1, 89, 17));
		addSlotToContainer(new SlotCookableFoodOnly(grill, 2, 71, 35));
		addSlotToContainer(new SlotCookableFoodOnly(grill, 3, 89, 35));
		addSlotToContainer(new SlotCookableFoodOnly(grill, 4, 71, 53));
		addSlotToContainer(new SlotCookableFoodOnly(grill, 5, 89, 53));

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);
	}

	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlotTFC(EntityPlayer player, int slotNum)
	{
		ItemStack origStack = null;
		Slot slot = (Slot) inventorySlots.get(slotNum);

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			if (slotNum < 6)
			{
				if (!this.mergeItemStack(slotStack, 6, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				if (!this.mergeItemStack(slotStack, 0, 6, false))
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
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
		{
			ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
			ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

			if (!ItemStack.areItemStacksEqual(var3, var2))
			{
				var3 = var2 == null ? null : var2.copy();
				this.inventoryItemStacks.set(var1, var3);

				for (int var4 = 0; var4 < this.crafters.size(); ++var4)
					((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
			}
		}


		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			if (this.fire != null && this.firetemp != this.fire.fireTemp)
				var2.sendProgressBarUpdate(this, 0, (int)this.fire.fireTemp);
		}

		if(this.fire != null)
			firetemp = this.fire.fireTemp;
		else firetemp = 0;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (this.fire != null && par1 == 0)
			this.fire.fireTemp = par2;
	}
}
