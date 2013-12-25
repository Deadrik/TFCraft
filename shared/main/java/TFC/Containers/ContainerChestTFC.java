package TFC.Containers;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryLargeChest;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCItems;
import TFC.Containers.Slots.SlotChest;
import TFC.TileEntities.TileEntityChestTFC;

public class ContainerChestTFC extends ContainerTFC
{
	private IInventory lowerChestInventory;
	private int numRows;

	public ContainerChestTFC(IInventory playerInv, IInventory chestInv, World world, int x, int y, int z)
	{
		TileEntityChestTFC chest = (TileEntityChestTFC)chestInv;
		this.lowerChestInventory = chestInv;

		if (chest.adjacentChestXNeg != null)
		{
			lowerChestInventory = new InventoryLargeChest("Large chest", chest.adjacentChestXNeg, chestInv);
		}

		if (chest.adjacentChestXPos != null)
		{
			lowerChestInventory = new InventoryLargeChest("Large chest", chestInv, chest.adjacentChestXPos);
		}

		if (chest.adjacentChestZNeg != null)
		{
			lowerChestInventory = new InventoryLargeChest("Large chest", chest.adjacentChestZNeg, chestInv);
		}

		if (chest.adjacentChestZPosition != null)
		{
			lowerChestInventory = new InventoryLargeChest("Large chest", chestInv, chest.adjacentChestZPosition);
		}

		this.numRows = lowerChestInventory.getSizeInventory() / 9;
		lowerChestInventory.openChest();
		int var3 = (this.numRows - 4) * 18;
		int var4;
		int var5;

		ArrayList exceptions = new ArrayList<Item>();
		exceptions.add(TFCItems.Logs);
		exceptions.add(TFCItems.BismuthIngot);
		exceptions.add(TFCItems.BismuthBronzeIngot);
		exceptions.add(TFCItems.BlackBronzeIngot);
		exceptions.add(TFCItems.BlackSteelIngot);
		exceptions.add(TFCItems.BlueSteelIngot);
		exceptions.add(TFCItems.BrassIngot);
		exceptions.add(TFCItems.BronzeIngot);
		exceptions.add(TFCItems.CopperIngot);
		exceptions.add(TFCItems.GoldIngot);
		exceptions.add(TFCItems.WroughtIronIngot);
		exceptions.add(TFCItems.LeadIngot);
		exceptions.add(TFCItems.NickelIngot);
		exceptions.add(TFCItems.PigIronIngot);
		exceptions.add(TFCItems.PlatinumIngot);
		exceptions.add(TFCItems.RedSteelIngot);
		exceptions.add(TFCItems.RoseGoldIngot);
		exceptions.add(TFCItems.SilverIngot);
		exceptions.add(TFCItems.SteelIngot);
		exceptions.add(TFCItems.BismuthIngot);
		exceptions.add(TFCItems.SterlingSilverIngot);
		exceptions.add(TFCItems.TinIngot);
		exceptions.add(TFCItems.ZincIngot);

		for (var4 = 0; var4 < this.numRows; ++var4)
		{
			for (var5 = 0; var5 < 9; ++var5)
			{
				this.addSlotToContainer(new SlotChest(lowerChestInventory, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18).addItemException(exceptions));
			}
		}

		for (var4 = 0; var4 < 3; ++var4)
		{
			for (var5 = 0; var5 < 9; ++var5)
			{
				this.addSlotToContainer(new Slot(playerInv, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
			}
		}

		for (var4 = 0; var4 < 9; ++var4)
		{
			this.addSlotToContainer(new Slot(playerInv, var4, 8 + var4 * 18, 161 + var3));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int par1)
	{
		ItemStack var2 = null;
		Slot var3 = (Slot)this.inventorySlots.get(par1);

		if (var3 != null && var3.getHasStack())
		{
			ItemStack var4 = var3.getStack();
			var2 = var4.copy();

			if (par1 < this.numRows * 9)
			{
				if (!this.mergeItemStack(var4, this.numRows * 9, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(var4, 0, this.numRows * 9, false))
			{
				return null;
			}

			if (var4.stackSize == 0)
			{
				var3.putStack((ItemStack)null);
			}
			else
			{
				var3.onSlotChanged();
			}
		}

		return var2;
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	@Override
	public void onContainerClosed(EntityPlayer par1EntityPlayer)
	{
		super.onContainerClosed(par1EntityPlayer);
		this.lowerChestInventory.closeChest();
	}
}
