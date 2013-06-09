package TFC.Containers;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.ICrafting;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.Core.HeatManager;
import TFC.Items.ItemOre;
import TFC.TileEntities.TileEntityForge;

public class ContainerForge extends ContainerTFC
{
	private TileEntityForge forge;
	private int coolTime;
	private int freezeTime;
	private int itemFreezeTime;
    private float firetemp;


	public ContainerForge(InventoryPlayer inventoryplayer, TileEntityForge tileentityforge, World world, int x, int y, int z)
	{
		forge = tileentityforge;

		coolTime = 0;
		freezeTime = 0;
		itemFreezeTime = 0;

		//Input slot
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 0, 44, 8));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 1, 62, 26));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 2, 80, 44));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 3, 98, 26));
		addSlotToContainer(new SlotForge(inventoryplayer.player,tileentityforge, 4, 116, 8));
		//fuel stack
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 5, 44, 26));
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 6, 62, 44));
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 7, 80, 62));
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 8, 98, 44));
		addSlotToContainer(new SlotForgeFuel(inventoryplayer.player, tileentityforge, 9, 116, 26));
		//Storage slot
		addSlotToContainer(new Slot(tileentityforge, 10, 152, 8));
		addSlotToContainer(new Slot(tileentityforge, 11, 152, 26));
		addSlotToContainer(new Slot(tileentityforge, 12, 152, 44));
		addSlotToContainer(new Slot(tileentityforge, 13, 152, 62));

		for(int i = 0; i < 3; i++)
		{
			for(int k = 0; k < 9; k++)
			{
				addSlotToContainer(new Slot(inventoryplayer, k + i * 9 + 9, 8 + k * 18, 84 + i * 18));
			}

		}

		for(int j = 0; j < 9; j++)
		{
			addSlotToContainer(new Slot(inventoryplayer, j, 8 + j * 18, 142));
		}

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
		Slot[] slotinput = {(Slot)inventorySlots.get(2), (Slot)inventorySlots.get(1), (Slot)inventorySlots.get(3), (Slot)inventorySlots.get(0), (Slot)inventorySlots.get(4)};
		Slot[] slotstorage = {(Slot)inventorySlots.get(10), (Slot)inventorySlots.get(11), (Slot)inventorySlots.get(12), (Slot)inventorySlots.get(13)};
		Slot[] slotfuel = {(Slot)inventorySlots.get(7), (Slot)inventorySlots.get(6), (Slot)inventorySlots.get(8), (Slot)inventorySlots.get(5), (Slot)inventorySlots.get(9)};
		HeatManager manager = HeatManager.getInstance();
		if(slot != null && slot.getHasStack())
		{
			ItemStack itemstack1 = slot.getStack();
			if(i <= 13)
			{
				if(!this.mergeItemStack(itemstack1, 14, this.inventorySlots.size(), true))
				{
					return null;
				}
			}
			else
			{
				if(itemstack1.itemID == Item.coal.itemID)
				{
					int j = 0;
					while(j < 5)
					{
						if(slotfuel[j].getHasStack())
						{
							j++;
						}
						else
						{
							ItemStack stack = itemstack1.copy();
							stack.stackSize = 1;
							slotfuel[j].putStack(stack);
							itemstack1.stackSize--;
							j = -1;
							break;
						}
					}
					if (j > 0)
					{
						j = 0;
						while(j < 4)
						{
							if(slotstorage[j].getHasStack())
							{
								j++;
							}
							else
							{
								ItemStack stack = itemstack1.copy();
								stack.stackSize = 1;
								slotstorage[j].putStack(stack);
								itemstack1.stackSize--;
								break;
							}
						}
					}
				}
				else if(!(itemstack1.getItem() instanceof ItemOre) && manager.findMatchingIndex(itemstack1) != null)
				{
					int j = 0;
					while(j < 5)
					{
						if(slotinput[j].getHasStack())
						{
							j++;
						}
						else
						{
							ItemStack stack = itemstack1.copy();
							stack.stackSize = 1;
							slotinput[j].putStack(stack);
							itemstack1.stackSize--;
							j = -1;
							break;
						}
					}
					if (j > 0)
					{
						j = 0;
						while(j < 4)
						{
							if(slotstorage[j].getHasStack())
							{
								j++;
							}
							else
							{
								ItemStack stack = itemstack1.copy();
								stack.stackSize = 1;
								slotstorage[j].putStack(stack);
								itemstack1.stackSize--;
								break;
							}
						}
					}
				}
				else
				{
					int j = 0;
					while(j < 4)
					{
						if(slotstorage[j].getHasStack())
						{
							j++;
						}
						else
						{
							ItemStack stack = itemstack1.copy();
							stack.stackSize = 1;
							slotstorage[j].putStack(stack);
							itemstack1.stackSize--;
							break;
						}
					}
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

	@Override
	public void detectAndSendChanges()
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
                    ((ICrafting)this.crafters.get(var4)).sendSlotContents(this, var1, var3);
                }
            }
        }
		
		for (int var1 = 0; var1 < this.crafters.size(); ++var1)
        {
            ICrafting var2 = (ICrafting)this.crafters.get(var1);
            if (this.firetemp != this.forge.fireTemperature)
            {
                var2.sendProgressBarUpdate(this, 0, (int)this.forge.fireTemperature);
            }
        }
        
        firetemp = this.forge.fireTemperature;
	}
	@Override
	public void updateProgressBar(int par1, int par2)
    {
        if (par1 == 0)
        {
            this.forge.fireTemperature = par2;
        }

    }
}
