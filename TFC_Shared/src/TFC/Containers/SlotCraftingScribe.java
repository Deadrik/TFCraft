package TFC.Containers;

import TFC.Core.ServerClientProxy;
import TFC.TileEntities.TileEntityTerraScribe;
import net.minecraft.src.*;
import net.minecraft.src.forge.ForgeHooks;

public class SlotCraftingScribe extends Slot
{
	private final IInventory craftMatrix;
	private EntityPlayer thePlayer;
	private final IInventory paperSlot;

	public SlotCraftingScribe(EntityPlayer entityplayer, IInventory iinventory, IInventory iinventory1,IInventory iinventory2, int i, int j, int k)
	{
		super(iinventory1, i, j, k);
		thePlayer = entityplayer;
		craftMatrix = iinventory;
		paperSlot = iinventory2;
	}

	public boolean isItemValid(ItemStack itemstack)
	{
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		itemstack.onCrafting(thePlayer.worldObj, thePlayer, slotNumber);

		ServerClientProxy.getProxy().takenFromCrafting(thePlayer, itemstack, craftMatrix);
		ForgeHooks.onTakenFromCrafting(thePlayer, itemstack, craftMatrix);
		((TileEntityTerraScribe)paperSlot).scribeItemStacks[1].stackSize--;
		if(((TileEntityTerraScribe)paperSlot).scribeItemStacks[1].stackSize <= 0)
		{
			((TileEntityTerraScribe)paperSlot).scribeItemStacks[1] = null;
		}
		for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
		{
			ItemStack itemstack1 = craftMatrix.getStackInSlot(i);
			if (itemstack1 != null)
			{
				craftMatrix.decrStackSize(i, 1);

				if (itemstack1.getItem().hasContainerItem())
				{
					ItemStack itemstack2 = new ItemStack(itemstack1.getItem().getContainerItem());
					if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !thePlayer.inventory.addItemStackToInventory(itemstack2))
					{
						if (craftMatrix.getStackInSlot(i) == null)
						{
							craftMatrix.setInventorySlotContents(i, itemstack2);
						}
						else
						{
							thePlayer.dropPlayerItem(itemstack2);
						}
					}
				}
			}
		}
	}
}
