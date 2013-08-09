package TFC.Containers;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.inventory.SlotCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.world.World;
import TFC.TileEntities.TileEntityWorkbench;

public class ContainerWorkbench extends ContainerTFC
{
	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 3, 3);

	/** The crafting result, size 1. */
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;
	private int posX;
	private int posY;
	private int posZ;
	private TileEntityWorkbench workbench;

	public ContainerWorkbench(InventoryPlayer par1InventoryPlayer, TileEntityWorkbench wb, World par2World, int par3, int par4, int par5)
	{
		this.worldObj = par2World;
		this.posX = par3;
		this.posY = par4;
		this.posZ = par5;
		this.addSlotToContainer(new SlotCrafting(par1InventoryPlayer.player, this.craftMatrix, this.craftResult, 0, 124, 35));
		int var6;
		int var7;

		for (var6 = 0; var6 < 3; ++var6)
		{
			for (var7 = 0; var7 < 3; ++var7)
			{
				this.addSlotToContainer(new Slot(this.craftMatrix, var7 + var6 * 3, 30 + var7 * 18, 17 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 3; ++var6)
		{
			for (var7 = 0; var7 < 9; ++var7)
			{
				this.addSlotToContainer(new Slot(par1InventoryPlayer, var7 + var6 * 9 + 9, 8 + var7 * 18, 84 + var6 * 18));
			}
		}

		for (var6 = 0; var6 < 9; ++var6)
		{
			this.addSlotToContainer(new Slot(par1InventoryPlayer, var6, 8 + var6 * 18, 142));
		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	 @Override
	 public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		 this.craftResult.setInventorySlotContents(0, CraftingManager.getInstance().findMatchingRecipe(this.craftMatrix, this.worldObj));
	}

	 /**
	  * Callback for when the crafting gui is closed.
	  */
	 @Override
	 public void onContainerClosed(EntityPlayer par1EntityPlayer)
	 {
		 super.onContainerClosed(par1EntityPlayer);

		 if (!this.worldObj.isRemote)
		 {
			 for (int var2 = 0; var2 < 9; ++var2)
			 {
				 ItemStack var3 = this.craftMatrix.getStackInSlotOnClosing(var2);

				 if (var3 != null)
				 {
					 par1EntityPlayer.dropPlayerItem(var3);
				 }
			 }
		 }
	 }
	 @Override
	 public boolean canInteractWith(EntityPlayer par1EntityPlayer)
	 {
		 return this.worldObj.getBlockId(this.posX, this.posY, this.posZ) != Block.workbench.blockID ? false : par1EntityPlayer.getDistanceSq(this.posX + 0.5D, this.posY + 0.5D, this.posZ + 0.5D) <= 64.0D;
	 }
	 @Override
	 public ItemStack transferStackInSlot(EntityPlayer par1EntityPlayer, int par2)
	 {
		 ItemStack var3 = null;
		 Slot var4 = (Slot)this.inventorySlots.get(par2);

		 if (var4 != null && var4.getHasStack())
		 {
			 ItemStack var5 = var4.getStack();
			 var3 = var5.copy();

			 if (par2 == 0)
			 {
				 if (!this.mergeItemStack(var5, 10, 46, true))
				 {
					 return null;
				 }

				 var4.onSlotChange(var5, var3);
			 }
			 else if (par2 >= 10 && par2 < 37)
			 {
				 if (!this.mergeItemStack(var5, 37, 46, false))
				 {
					 return null;
				 }
			 }
			 else if (par2 >= 37 && par2 < 46)
			 {
				 if (!this.mergeItemStack(var5, 10, 37, false))
				 {
					 return null;
				 }
			 }
			 else if (!this.mergeItemStack(var5, 10, 46, false))
			 {
				 return null;
			 }

			 if (var5.stackSize == 0)
			 {
				 var4.putStack((ItemStack)null);
			 }
			 else
			 {
				 var4.onSlotChanged();
			 }

			 if (var5.stackSize == var3.stackSize)
			 {
				 return null;
			 }

			 var4.onPickupFromSlot(par1EntityPlayer, var5);
		 }

		 return var3;
	 }
}