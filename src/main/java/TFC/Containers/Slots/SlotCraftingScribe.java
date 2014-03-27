package TFC.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntityScribe;
import cpw.mods.fml.common.registry.GameRegistry;

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

	@Override
	public void onPickupFromSlot(EntityPlayer player, ItemStack itemstack)
	{
		itemstack.onCrafting(thePlayer.worldObj, thePlayer, slotNumber);
		TerraFirmaCraft.proxy.takenFromCrafting(thePlayer, itemstack, craftMatrix);

		((TileEntityScribe)paperSlot).scribeItemStacks[1].stackSize--;
		if(((TileEntityScribe)paperSlot).scribeItemStacks[1].stackSize <= 0)
			((TileEntityScribe)paperSlot).scribeItemStacks[1] = null;

		for (int i = 0; i < craftMatrix.getSizeInventory(); i++)
		{
			ItemStack itemstack1 = craftMatrix.getStackInSlot(i);
			if (itemstack1 != null)
			{
				craftMatrix.decrStackSize(i, 1);
				if (itemstack1.getItem().getContainerItem() != null)
				{
					ItemStack itemstack2 = new ItemStack(itemstack1.getItem().getContainerItem());
					if (!itemstack1.getItem().doesContainerItemLeaveCraftingGrid(itemstack1) || !thePlayer.inventory.addItemStackToInventory(itemstack2))
					{
						if (craftMatrix.getStackInSlot(i) == null)
							craftMatrix.setInventorySlotContents(i, itemstack2);
						else
							thePlayer.dropItem(itemstack2.getItem(), itemstack2.stackSize);
					}
				}
			}
		}
	}
}
