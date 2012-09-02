package TFC.Containers;

import TFC.Items.ItemTerraLogs;
import TFC.TileEntities.TileEntityTerraLogPile;
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

public class ContainerTerraLogPile extends ContainerTFC
{
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private TileEntityTerraLogPile logpile;
	private EntityPlayer player;

	public ContainerTerraLogPile(InventoryPlayer playerinv, TileEntityTerraLogPile pile, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.logpile = pile;
		this.world = world;
		this.posX = x;
		this.posY = y;
		this.posZ = z;
		pile.openChest();

		layoutContainer(playerinv, pile, 0, 0);
	}

	/**
	 * Callback for when the crafting gui is closed.
	 */
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		super.onCraftGuiClosed(par1EntityPlayer);
		
		if(!world.isRemote)
		{
			logpile.closeChest();
			if(logpile.storage[0] == null && logpile.storage[1] == null &&
					logpile.storage[2] == null && logpile.storage[3] == null)
			{
				world.setBlockWithNotify(posX, posY, posZ, 0);
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
			//			 else if (par1 >= 37 && par1 < 46)
			//			 {
			//				 if (!this.mergeItemStack(var4, 10, 37, false))
			//				 {
			//					 return null;
			//				 }
			//			 }
			else if (!this.mergeItemStack(var4, 4, 40, false))
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

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) 
	{
		this.addSlotToContainer(new SlotLogPile(getPlayer(),chestInventory, 0, 71, 25));
		this.addSlotToContainer(new SlotLogPile(getPlayer(),chestInventory, 1, 89, 25));
		this.addSlotToContainer(new SlotLogPile(getPlayer(),chestInventory, 2, 71, 43));
		this.addSlotToContainer(new SlotLogPile(getPlayer(),chestInventory, 3, 89, 43));

		int row;
		int col;


		for (row = 0; row < 3; ++row)
		{
			for (col = 0; col < 9; ++col)
			{
				this.addSlotToContainer(new Slot(playerInventory, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
			}
		}

		for (row = 0; row < 9; ++row)
		{
			this.addSlotToContainer(new Slot(playerInventory, row, 8 + row * 18, 142));
		}
	}
	public EntityPlayer getPlayer() {
		return player;
	}
}
