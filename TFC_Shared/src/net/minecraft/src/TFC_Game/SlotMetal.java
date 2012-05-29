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

		if(itemstack.itemID == mod_TFC_Game.BismuthUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.BismuthBronzeUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.BlackBronzeUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.BlackSteelUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.BlueSteelUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.BrassUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.BronzeUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.CopperUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.GoldUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.WroughtIronUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.LeadUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.NickelUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.PigIronUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.PlatinumUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.RedSteelUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.RoseGoldUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.SilverUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.SterlingSilverUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.TinUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.ZincUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.HCBlueSteelUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.WeakBlueSteelUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.HCRedSteelUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.WeakRedSteelUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.HCSteelUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.WeakSteelUnshaped.shiftedIndex || 
				itemstack.itemID == mod_TFC_Game.HCBlackSteelUnshaped.shiftedIndex || itemstack.itemID == mod_TFC_Game.terraCeramicMold.shiftedIndex)
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
