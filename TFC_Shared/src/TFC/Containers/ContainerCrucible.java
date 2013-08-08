package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TileEntities.TECrucible;

public class ContainerCrucible extends ContainerTFC
{
	private TECrucible te;
    private float firetemp;


	public ContainerCrucible(InventoryPlayer inventoryplayer, TECrucible tileentityforge, World world, int x, int y, int z)
	{
	    te = tileentityforge;
	    firetemp = 0;
		//Input slot
	    addSlotToContainer(new Slot(tileentityforge, 0, 98, 5));
	    
	    addSlotToContainer(new Slot(tileentityforge, 1, 152, 92));

		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 112 + i * 18));
			}
		}

		for(int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 170));
		}
		te.updateGui((byte) 0);
	}
	@Override
	public boolean canInteractWith(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slot1 = (Slot)inventorySlots.get(0);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i == 0)
			{
				if(!entityplayer.inventory.addItemStackToInventory(itemstack1.copy()))
				{
					return null;
				}
				slot.putStack(null);
			}
			else
			{
				if(slot1.getHasStack())
				{
					return null;
				}
				ItemStack stack = itemstack1.copy();
				stack.stackSize = 1;                            
				slot1.putStack(stack);                          
				itemstack1.stackSize--;
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

	private int updatecounter = 0;
	@Override
	public void detectAndSendChanges()
    {
        super.detectAndSendChanges();
        
        for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);
            if (this.firetemp != this.te.temperature)
            {
                var2.sendProgressBarUpdate(this, 0, (int)this.te.temperature);
            }
        }
    }
	
	@Override
	public void updateProgressBar(int id, int value)
    {
        if (id == 0)
        {
            this.te.temperature = value;
        }
    }
}
