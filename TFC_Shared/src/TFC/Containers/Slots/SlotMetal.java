package TFC.Containers.Slots;

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

		if(itemstack.itemID == TFCItems.BismuthUnshaped.itemID || itemstack.itemID == TFCItems.BismuthBronzeUnshaped.itemID || 
				itemstack.itemID == TFCItems.BlackBronzeUnshaped.itemID || itemstack.itemID == TFCItems.BlackSteelUnshaped.itemID || 
				itemstack.itemID == TFCItems.BlueSteelUnshaped.itemID || itemstack.itemID == TFCItems.BrassUnshaped.itemID || 
				itemstack.itemID == TFCItems.BronzeUnshaped.itemID || itemstack.itemID == TFCItems.CopperUnshaped.itemID || 
				itemstack.itemID == TFCItems.GoldUnshaped.itemID || itemstack.itemID == TFCItems.WroughtIronUnshaped.itemID || 
				itemstack.itemID == TFCItems.LeadUnshaped.itemID || itemstack.itemID == TFCItems.NickelUnshaped.itemID || 
				itemstack.itemID == TFCItems.PigIronUnshaped.itemID || itemstack.itemID == TFCItems.PlatinumUnshaped.itemID || 
				itemstack.itemID == TFCItems.RedSteelUnshaped.itemID || itemstack.itemID == TFCItems.RoseGoldUnshaped.itemID || 
				itemstack.itemID == TFCItems.SilverUnshaped.itemID|| itemstack.itemID == TFCItems.SteelUnshaped.itemID || 
				itemstack.itemID == TFCItems.SterlingSilverUnshaped.itemID || 
				itemstack.itemID == TFCItems.TinUnshaped.itemID || itemstack.itemID == TFCItems.ZincUnshaped.itemID || 
				itemstack.itemID == TFCItems.HCBlueSteelUnshaped.itemID || itemstack.itemID == TFCItems.WeakBlueSteelUnshaped.itemID || 
				itemstack.itemID == TFCItems.HCRedSteelUnshaped.itemID || itemstack.itemID == TFCItems.WeakRedSteelUnshaped.itemID || 
				itemstack.itemID == TFCItems.HCSteelUnshaped.itemID || itemstack.itemID == TFCItems.WeakSteelUnshaped.itemID || 
				itemstack.itemID == TFCItems.HCBlackSteelUnshaped.itemID || itemstack.itemID == TFCItems.CeramicMold.itemID)
		{
			return true;
		}
		return false;
	}
}
