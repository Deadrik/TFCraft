package net.minecraft.src.TFC_Game;

import net.minecraft.src.*;

public class SlotMetal extends Slot

{
	public SlotMetal(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{    	

		if(itemstack.itemID == mod_TFC_Game.UnshapedBismuth.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedBismuthBronze.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedBlackBronze.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedBlackSteel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedBlueSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedBrass.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedBronze.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedCopper.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedGold.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedWroughtIron.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedLead.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedNickel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedPigIron.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedPlatinum.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedRedSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedRoseGold.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedSilver.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedSterlingSilver.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedTin.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedZinc.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedHCBlueSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedWeakBlueSteel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedHCRedSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedWeakRedSteel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedHCSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.UnshapedWeakSteel.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.UnshapedHCBlackSteel.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraCeramicMold.shiftedIndex)
		{
			return true;
		}
		return false;
	}

	public void onPickupFromSlot(ItemStack itemstack)
	{
		super.onPickupFromSlot(itemstack);
	}
}
