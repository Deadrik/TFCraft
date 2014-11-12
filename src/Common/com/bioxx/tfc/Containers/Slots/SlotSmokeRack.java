package com.bioxx.tfc.Containers.Slots;

import com.bioxx.tfc.Blocks.BlockSmokeRack;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Food.ItemFoodMeat;
import com.bioxx.tfc.TileEntities.TESmokeRack;
import com.bioxx.tfc.api.Food;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class SlotSmokeRack extends Slot
{
	private EntityPlayer player;
	
	public SlotSmokeRack(EntityPlayer entityplayer, IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);
		
		player = entityplayer;
	}

	@Override
	public boolean isItemValid(ItemStack is)
	{
		if (is == null) return false;

		// check if the player has any hanging items in their inventory.
		Item item = BlockSmokeRack.getCreatedWithItem();
		ItemStack itemStack = TFC_Core.getItemInInventory(item, player.inventory);
		if (itemStack == null) return false;
		
		if (is.getItem() instanceof ItemFoodMeat)
		{
			if (!Food.isCooked(is) && Food.isBrined(is)) return true;
		}

		return false;
	}

	@Override
    public void putStack(ItemStack is)
    {
		TESmokeRack smokeRack = (TESmokeRack)this.inventory;
		if (smokeRack != null)
			smokeRack.putStackInSlot(player, getSlotIndex(), is);
		else
			this.inventory.setInventorySlotContents(getSlotIndex(), is);
        this.onSlotChanged();
    }
}
