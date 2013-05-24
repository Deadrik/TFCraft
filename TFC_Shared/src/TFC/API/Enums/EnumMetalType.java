package TFC.API.Enums;

import net.minecraft.item.Item;
import TFC.TFCItems;


public enum EnumMetalType 
{
	BISMUTH(0, TFCItems.BismuthIngot), 
	BISMUTHBRONZE(1, TFCItems.BismuthBronzeIngot), 
	BLACKBRONZE(2, TFCItems.BlackBronzeIngot), 
	BLACKSTEEL(3, TFCItems.BlackSteelIngot), 
	BLUESTEEL(4, TFCItems.BlueSteelIngot), 
	BRASS(5, TFCItems.BrassIngot), 
	BRONZE(6, TFCItems.BronzeIngot), 
	COPPER(7, TFCItems.CopperIngot), 
	GOLD(8, TFCItems.GoldIngot), 
	WROUGHTIRON(9, TFCItems.WroughtIronIngot), 
	LEAD(10, TFCItems.LeadIngot), 
	NICKEL(11, TFCItems.NickelIngot), 
	PIGIRON(12, TFCItems.PigIronIngot), 
	PLATINUM(13, TFCItems.PlatinumIngot), 
	REDSTEEL(14, TFCItems.RedSteelIngot), 
	ROSEGOLD(15, TFCItems.RoseGoldIngot), 
	SILVER(16, TFCItems.SilverIngot), 
	STEEL(17, TFCItems.SteelIngot), 
	STERLINGSILVER(18, TFCItems.SterlingSilverIngot), 
	TIN(19, TFCItems.TinIngot), 
	ZINC(20, TFCItems.ZincIngot);

	public int MetalID;
	public Item Ingot;
	
	private EnumMetalType(int id, Item ingot)
	{
		MetalID = id;
		Ingot = ingot;
	}
}
