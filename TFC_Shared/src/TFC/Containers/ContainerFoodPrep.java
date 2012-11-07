package TFC.Containers;

import TFC.*;
import TFC.Food.ItemTerraFood;
import TFC.Items.ItemLogs;
import TFC.TileEntities.TileEntityFoodPrep;
import TFC.TileEntities.TileEntityTerraLogPile;
import net.minecraft.src.Container;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IInventory;
import net.minecraft.src.InventoryPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Slot;
import net.minecraft.src.World;

public class ContainerFoodPrep extends Container
{
	private World world;
	private int posX;
	private int posY;
	private int posZ;
	private TileEntityFoodPrep te;
	private EntityPlayer player;

	public ContainerFoodPrep(InventoryPlayer playerinv, TileEntityFoodPrep pile, World world, int x, int y, int z)
	{
		this.player = playerinv.player;
		this.te = pile;
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
	@Override
	public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
	{
		super.onCraftGuiClosed(par1EntityPlayer);
		
		if(!world.isRemote)
		{
			te.closeChest();
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer var1) {
		return true;
	}

	protected void layoutContainer(IInventory playerInventory, IInventory chestInventory, int xSize, int ySize) 
	{
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 0, 53, 24));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 1, 71, 24));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 2, 89, 24));
		this.addSlotToContainer(new SlotFoodOnly(chestInventory, 3, 107, 24));
		this.addSlotToContainer(new SlotBlocked(chestInventory, 4, 80, 53));
		this.addSlotToContainer(new Slot(chestInventory, 5, 53, 53));

		int row;
		int col;
		
		for (row = 0; row < 9; ++row)
		{
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
	
	public EntityPlayer getPlayer() {
		return player;
	}
	
	@Override
	public ItemStack transferStackInSlot(EntityPlayer player, int clickedIndex)
	{
		ItemStack returnedStack = null;
		Slot clickedSlot = (Slot)this.inventorySlots.get(clickedIndex);

		if (clickedSlot != null && clickedSlot.getHasStack() && clickedSlot.getStack().getItem() instanceof ItemTerraFood)
		{
			ItemStack clickedStack = clickedSlot.getStack();
			returnedStack = clickedStack.copy();

			if (clickedIndex < 6)
			{
				if (!this.mergeItemStack(clickedStack, 6, 41, true))
				{
					return null;
				}

			}
			else if (clickedIndex >= 6 && clickedIndex < 41)
			{
				if (!this.mergeItemStack(clickedStack, 0, 4, false))
				{
					return null;
				}
			}
			else if (!this.mergeItemStack(clickedStack, 6, 41, false))
			{
				return null;
			}

			if (clickedStack.stackSize == 0)
			{
				clickedSlot.putStack((ItemStack)null);
			}
			else
			{
				clickedSlot.onSlotChanged();
			}

			if (clickedStack.stackSize == returnedStack.stackSize)
			{
				return null;
			}

			clickedSlot.onPickupFromSlot(player, clickedStack);
		}

		return returnedStack;
	}
	
//	protected boolean mergeItemStack(ItemStack par1ItemStack, int par2, int par3, boolean par4)
//    {
//        boolean var5 = false;
//        int var6 = par2;
//
//        if (par4)
//        {
//            var6 = par3 - 1;
//        }
//
//        Slot var7;
//        ItemStack var8;
//        int stackSize = par1ItemStack.getMaxStackSize();
//
//        if (par1ItemStack.isStackable())
//        {
//            while (par1ItemStack.stackSize > 0 && (!par4 && var6 < par3 || par4 && var6 >= par2))
//            {
//            	if(var6 < 4)
//            		stackSize = 4;
//            	else stackSize = par1ItemStack.getMaxStackSize();
//                var7 = (Slot)this.inventorySlots.get(var6);
//                var8 = var7.getStack();
//
//                if (var8 != null && var8.itemID == par1ItemStack.itemID && (!par1ItemStack.getHasSubtypes() || par1ItemStack.getItemDamage() == var8.getItemDamage()) && ItemStack.func_77970_a(par1ItemStack, var8))
//                {
//                    int var9 = var8.stackSize + par1ItemStack.stackSize;
//
//                    if (var9 <= stackSize)
//                    {
//                        par1ItemStack.stackSize = 0;
//                        var8.stackSize = var9;
//                        var7.onSlotChanged();
//                        var5 = true;
//                    }
//                    else if (var8.stackSize < stackSize)
//                    {
//                        par1ItemStack.stackSize -= stackSize - var8.stackSize;
//                        if(par1ItemStack.stackSize < 0)
//                        	par1ItemStack.stackSize = 0;
//                        var8.stackSize = stackSize;
//                        var7.onSlotChanged();
//                        var5 = true;
//                    }
//                }
//
//                if (par4)
//                {
//                    --var6;
//                }
//                else
//                {
//                    ++var6;
//                }
//            }
//        }
//
//        if (par1ItemStack.stackSize > 0)
//        {
//            if (par4)
//            {
//                var6 = par3 - 1;
//            }
//            else
//            {
//                var6 = par2;
//            }
//
//            while (!par4 && var6 < par3 || par4 && var6 >= par2)
//            {
//            	if(var6 < 4)
//            		stackSize = 4;
//            	else stackSize = par1ItemStack.getMaxStackSize();
//            	
//                var7 = (Slot)this.inventorySlots.get(var6);
//                var8 = var7.getStack();
//
//                if (var8 == null)
//                {
//                    var7.putStack(par1ItemStack.copy());
//                    var7.onSlotChanged();
//                    par1ItemStack.stackSize -= stackSize;
//                    var5 = true;
//                    if(par1ItemStack.stackSize <= 0)
//                    {
//                    	par1ItemStack.stackSize = 0;
//                    	break;
//                    }
//                }
//
//                if (par4)
//                {
//                    --var6;
//                }
//                else
//                {
//                    ++var6;
//                }
//            }
//        }
//
//        return var5;
//    }
}
