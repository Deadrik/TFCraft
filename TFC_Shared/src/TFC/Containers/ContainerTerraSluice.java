package TFC.Containers;

import TFC.TileEntities.TileEntityTerraSluice;
import net.minecraft.src.*;

public class ContainerTerraSluice extends Container
{
	private TileEntityTerraSluice sluice;
	private EntityPlayer player;


	public ContainerTerraSluice(InventoryPlayer inventoryplayer, TileEntityTerraSluice tileentitysluice)
	{
		sluice = tileentitysluice;
		player = inventoryplayer.player;
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 0, 116, 16));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 1, 134, 16));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 2, 152, 16));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 3, 116, 34));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 4, 134, 34));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 5, 152, 34));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 6, 116, 52));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 7, 134, 52));
		addSlot(new SlotSluice(inventoryplayer.player, tileentitysluice, 8, 152, 52));
		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlot(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}

		}

		for(int j = 0; j < 9; j++)
		{
			addSlot(new Slot(inventoryplayer, j, 8 + j * 18, 142));
		}

	}

	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}


	public ItemStack transferStackInSlot(int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slotpaper = (Slot)inventorySlots.get(1);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i <= 8)
			{
				if(!player.inventory.addItemStackToInventory(itemstack1.copy()))
				{
					return null;
				}
				slot.putStack(null);
			}
			if(itemstack1.stackSize == 0)
			{
				slot.putStack(null);
			} else
			{
				slot.onSlotChanged();
			}
		}
		return null;
	}
	private int soilamt = 0;
	private int progress = 0;
	public void updateCraftingResults()
    {
        for (int var1 = 0; var1 < this.inventorySlots.size(); ++var1)
        {
            ItemStack var2 = ((Slot)this.inventorySlots.get(var1)).getStack();
            ItemStack var3 = (ItemStack)this.inventoryItemStacks.get(var1);

            if (!ItemStack.areItemStacksEqual(var3, var2))
            {
                var3 = var2 == null ? null : var2.copy();
                this.inventoryItemStacks.set(var1, var3);

                for (int var4 = 0; var4 < this.crafters.size(); ++var4)
                {
                    ((ICrafting)this.crafters.get(var4)).updateCraftingInventorySlot(this, var1, var3);
                }
            }
        }
        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);
            if (this.soilamt != this.sluice.soilAmount)
            {
                var2.updateCraftingInventoryInfo(this, 0, this.sluice.soilAmount);
            }
            if (this.progress != this.sluice.processTimeRemaining)
            {
                var2.updateCraftingInventoryInfo(this, 1, this.sluice.processTimeRemaining);
            }
        }

        soilamt = this.sluice.soilAmount;
        progress = this.sluice.processTimeRemaining;
    }
	
	public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.sluice.soilAmount = par2;
        }
        if (par1 == 1)
        {
            this.sluice.processTimeRemaining = par2;
        }
    }
}
