package TFC.Containers;

import TFC.Core.TFCItems;
import net.minecraft.src.*;

public class SlotMetal extends Slot

{
	public SlotMetal(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	public boolean isItemValid(ItemStack itemstack)
	{    	

		if(itemstack.itemID == TFCItems.BismuthUnshaped.shiftedIndex || itemstack.itemID == TFCItems.BismuthBronzeUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.BlackBronzeUnshaped.shiftedIndex || itemstack.itemID == TFCItems.BlackSteelUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.BlueSteelUnshaped.shiftedIndex || itemstack.itemID == TFCItems.BrassUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.BronzeUnshaped.shiftedIndex || itemstack.itemID == TFCItems.CopperUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.GoldUnshaped.shiftedIndex || itemstack.itemID == TFCItems.WroughtIronUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.LeadUnshaped.shiftedIndex || itemstack.itemID == TFCItems.NickelUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.PigIronUnshaped.shiftedIndex || itemstack.itemID == TFCItems.PlatinumUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.RedSteelUnshaped.shiftedIndex || itemstack.itemID == TFCItems.RoseGoldUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.SilverUnshaped.shiftedIndex|| itemstack.itemID == TFCItems.SteelUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.SterlingSilverUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.TinUnshaped.shiftedIndex || itemstack.itemID == TFCItems.ZincUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.HCBlueSteelUnshaped.shiftedIndex || itemstack.itemID == TFCItems.WeakBlueSteelUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.HCRedSteelUnshaped.shiftedIndex || itemstack.itemID == TFCItems.WeakRedSteelUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.HCSteelUnshaped.shiftedIndex || itemstack.itemID == TFCItems.WeakSteelUnshaped.shiftedIndex || 
				itemstack.itemID == TFCItems.HCBlackSteelUnshaped.shiftedIndex || itemstack.itemID == TFCItems.CeramicMold.shiftedIndex)
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
