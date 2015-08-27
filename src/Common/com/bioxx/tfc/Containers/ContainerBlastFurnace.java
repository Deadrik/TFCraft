package com.bioxx.tfc.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.Containers.Slots.SlotTuyere;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Items.ItemTuyere;
import com.bioxx.tfc.TileEntities.TEBlastFurnace;

public class ContainerBlastFurnace extends ContainerTFC
{
	private TEBlastFurnace tileentity;
	private float firetemp;
	private int orecount;
	private int coalcount;

	public ContainerBlastFurnace(InventoryPlayer inventoryplayer, TEBlastFurnace te, World world, int x, int y, int z)
	{
		tileentity = te;
		firetemp = 0;
		//Input slot
		//addSlotToContainer(new Slot(tileentityforge, 0, 134, 52));

		addSlotToContainer(new SlotTuyere(te, 1, 153, 7));

		PlayerInventory.buildInventoryLayout(this, inventoryplayer, 8, 90, false, true);

		tileentity.updateGui();
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
		Slot slotTuyere = (Slot)inventorySlots.get(0);

		if(slot != null && slot.getHasStack())
		{
			ItemStack slotStack = slot.getStack();
			origStack = slotStack.copy();

			// From tuyere slot to inventory
			if (slotNum < 1)
			{
				if (!this.mergeItemStack(slotStack, 1, this.inventorySlots.size(), true))
					return null;
			}
			else
			{
				if (slotStack.getItem() instanceof ItemTuyere)
				{
					if(slotTuyere.getHasStack())
						return null;
					ItemStack stack = slotStack.copy();
					stack.stackSize = 1;
					slotTuyere.putStack(stack);
					slotStack.stackSize--;
				}
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

	private int updatecounter;
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
		{
			ICrafting var2 = (ICrafting)this.crafters.get(var1);
			if (this.firetemp != this.tileentity.fireTemp)
			{
				var2.sendProgressBarUpdate(this, 0, (int)this.tileentity.fireTemp);
			}
		}

		if(orecount != this.tileentity.oreCount || coalcount != this.tileentity.charcoalCount || updatecounter == 1000)
		{
			//tileentity.broadcastPacketInRange(tileentity.createUpdatePacket());
			tileentity.getWorldObj().markBlockForUpdate(tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
			updatecounter = 0;
		}
		orecount = this.tileentity.oreCount;
		coalcount = this.tileentity.charcoalCount;
		firetemp = this.tileentity.fireTemp;
		updatecounter += 1;
	}

	@Override
	public void updateProgressBar(int par1, int par2)
	{
		if (par1 == 0)
		{
			this.tileentity.fireTemp = par2;
		}

	}
}
