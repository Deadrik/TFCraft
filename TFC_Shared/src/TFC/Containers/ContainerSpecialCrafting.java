package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryCraftResult;
import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.API.Crafting.CraftingManagerTFC;
import TFC.Containers.Slots.SlotCraftingMetal;

public class ContainerSpecialCrafting extends ContainerTFC
{
	/** The crafting matrix inventory (3x3). */
	public InventoryCrafting craftMatrix = new InventoryCrafting(this, 5, 5);

	private EntityPlayer EP;
	private SlotCraftingMetal SCM;
	private boolean firstTime = true;

	/** The crafting result, size 1. */
	public IInventory craftResult = new InventoryCraftResult();
	private World worldObj;

	public ContainerSpecialCrafting(InventoryPlayer inventoryplayer, ItemStack is, World world, int x, int y, int z)
	{
		for (int j1 = 0; j1 < 25; j1++)
		{
			if(is != null) {
				craftMatrix.setInventorySlotContents(j1, is.copy());
			}
		}
		this.worldObj = world;

		int var6;
		int var7;

		EP = inventoryplayer.player;
		SCM = new SlotCraftingMetal(this,inventoryplayer.player, craftMatrix, craftResult,0, 128, 35);
		addSlotToContainer(SCM);

		for(int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 151));
		}

		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 93 + i * 18));
			}

		}

		this.onCraftMatrixChanged(this.craftMatrix);
	}

	/**
	 * Callback for when the crafting matrix is changed.
	 */
	@Override
	public void onCraftMatrixChanged(IInventory par1IInventory)
	{
		this.craftResult.setInventorySlotContents(0, CraftingManagerTFC.getInstance().findMatchingRecipe(this.craftMatrix, worldObj));
	}

	/**
	 * Called to transfer a stack from one inventory to the other eg. when shift clicking.
	 * @return 
	 */
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex)
	{
		ItemStack var2 = null;
		Slot grabbedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if(grabbedSlot != null && grabbedSlot.getHasStack())
		{
			ItemStack var4 = grabbedSlot.getStack();
			var2 = var4.copy();

			if(clickedIndex < 10)
			{
				if (!this.mergeItemStack(var4, 10, 36, true))
				{
					return null;
				}
			}
			else if(clickedIndex >= 10 && clickedIndex < 37)
			{
				if (!this.mergeItemStack(var4, 0, 9, true))
				{
					return null;
				}
			}
			else if(clickedIndex >= 37 && clickedIndex < 62)
			{
				if (!this.mergeItemStack(var4, 0, 36, true))
				{
					return null;
				}
			}

			if (var4.stackSize == 0)
			{
				grabbedSlot.putStack((ItemStack)null);
			}
			else
			{
				grabbedSlot.onSlotChanged();
			}

			if (var4.stackSize == var2.stackSize)
			{
				return null;
			}

			grabbedSlot.onPickupFromSlot(player, var4);
		}
		this.onCraftMatrixChanged(this.craftMatrix);
		return var2;
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1)
	{
		return true;
	}
}
