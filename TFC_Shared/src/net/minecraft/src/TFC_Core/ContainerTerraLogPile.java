package net.minecraft.src.TFC_Core;

import net.minecraft.src.Block;
import net.minecraft.src.Container;
import net.minecraft.src.CraftingManager;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryCraftResult;
import net.minecraft.src.InventoryCrafting;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.SlotCrafting;
import net.minecraft.src.World;

public class ContainerTerraLogPile extends Container
{
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	private TileEntityTerraLogPile workbench;

	public ContainerTerraLogPile(InventoryPlayer par1InventoryPlayer, TileEntityTerraLogPile wb, World par2World, int par3, int par4, int par5)
	{
		this.workbench = wb;
		this.worldObj = par2World;
		this.posX = par3;
		this.posY = par4;
		this.posZ = par5;
		this.addSlot(new SlotLogPile(par1InventoryPlayer.player,wb, 0, 71, 25));
		this.addSlot(new SlotLogPile(par1InventoryPlayer.player,wb, 1, 89, 25));
		this.addSlot(new SlotLogPile(par1InventoryPlayer.player,wb, 2, 71, 43));
		this.addSlot(new SlotLogPile(par1InventoryPlayer.player,wb, 3, 89, 43));
		int var6;
		int var7;


		for (var6 = 0; var6 < 3; ++var6)
		{
			for (var7 = 0; var7 < 9; ++var7)
			{
				this.addSlot(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 9; ++var6)
		{
			this.addSlot(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
		}

	}

	public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	{
		return true;
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	 public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		 super.onCraftGuiClosed(par1EntityPlayer);

		 if (!this.worldObj.isRemote)
		 {
			 for (int var2 = 0; var2 < 9; ++var2)
			 {

				 if(workbench.storage[0] == null && workbench.storage[1] == null &&
						 workbench.storage[2] == null && workbench.storage[3] == null)
				 {
					 worldObj.setBlockWithNotify(posX, posY, posZ, 0);
				 }
			 }
		 }
	}

	 /**
	  * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	  */
	 public ItemStack transferStackInSlot(int par1)
	 {
		 ItemStack var2 = null;
		 Slot var3 = (Slot)this.inventorySlots.get(par1);

		 if (var3 != null && var3.getHasStack() && var3.getStack().getItem() instanceof ItemTerraLogs)
		 {
			 ItemStack var4 = var3.getStack();
			 var2 = var4.copy();

			 if (par1 < 4)
			 {
				 if (!this.mergeItemStack(var4, 4, 40, true))
				 {
					 return null;
				 }

				 //var3.func_48433_a(var4, var2);
			 }
			 else if (par1 >= 4 && par1 < 40)
			 {
				 if (!this.mergeItemStack(var4, 0, 4, false))
				 {
					 return null;
				 }
			 }
			 else if (par1 >= 37 && par1 < 46)
			 {
				 if (!this.mergeItemStack(var4, 10, 37, false))
				 {
					 return null;
				 }
			 }
			 else if (!this.mergeItemStack(var4, 10, 40, false))
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

			 if (var4.stackSize == var2.stackSize)
			 {
				 return null;
			 }

			 var3.onPickupFromSlot(var4);
		 }

		 return var2;
	 }
}
