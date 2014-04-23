package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.Containers.Slots.SlotTuyere;
import TFC.Core.Player.PlayerInventory;
import TFC.Items.ItemTuyere;
import TFC.TileEntities.TEBlastFurnace;

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
	public ItemStack transferStackInSlot(EntityPlayer entityplayer, int i)
	{
		Slot slot = (Slot)inventorySlots.get(i);
		Slot slot1 = (Slot)inventorySlots.get(0);
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i <= 0)
			{
				if(!entityplayer.inventory.addItemStackToInventory(itemstack1.copy()))
				{
					return null;
				}
				slot.putStack(null);
			}
			else
			{
				if(itemstack1.getItem() instanceof ItemTuyere)
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
            if (this.firetemp != this.tileentity.fireTemp)
            {
                var2.sendProgressBarUpdate(this, 0, (int)this.tileentity.fireTemp);
            }
        }
        
        if(orecount != this.tileentity.oreCount || coalcount != this.tileentity.charcoalCount || updatecounter == 1000)
        {
        	tileentity.broadcastPacketInRange(tileentity.createUpdatePacket());
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
