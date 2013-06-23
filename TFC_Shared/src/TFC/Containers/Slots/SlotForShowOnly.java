package TFC.Containers.Slots;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class SlotForShowOnly extends Slot
{
	public SlotForShowOnly(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	@Override
	public boolean canTakeStack(EntityPlayer par1EntityPlayer)
    {
        return false;
    }

}
