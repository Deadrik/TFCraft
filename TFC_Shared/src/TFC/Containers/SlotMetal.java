package TFC.Containers;

import TFC.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;

public class SlotMetal extends Slot

{
	public SlotMetal(IInventory iinventory, int i, int j, int k)
	{
		super(iinventory, i, j, k);

	}

	@Override
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
}
