package com.bioxx.tfc;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.EnumHelper;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.Metal.Alloy;
import com.bioxx.tfc.Core.Metal.AlloyManager;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.Food.ItemEgg;
import com.bioxx.tfc.Food.ItemFoodMeat;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemSalad;
import com.bioxx.tfc.Food.ItemSandwich;
import com.bioxx.tfc.Food.ItemSoup;
import com.bioxx.tfc.Handlers.TFCFuelHandler;
import com.bioxx.tfc.Items.ItemAlcohol;
import com.bioxx.tfc.Items.ItemArrow;
import com.bioxx.tfc.Items.ItemBloom;
import com.bioxx.tfc.Items.ItemBlueprint;
import com.bioxx.tfc.Items.ItemClay;
import com.bioxx.tfc.Items.ItemCoal;
import com.bioxx.tfc.Items.ItemCustomLeash;
import com.bioxx.tfc.Items.ItemCustomMinecart;
import com.bioxx.tfc.Items.ItemCustomNameTag;
import com.bioxx.tfc.Items.ItemCustomPotion;
import com.bioxx.tfc.Items.ItemCustomSeeds;
import com.bioxx.tfc.Items.ItemDyeCustom;
import com.bioxx.tfc.Items.ItemFertilizer;
import com.bioxx.tfc.Items.ItemFlatGeneric;
import com.bioxx.tfc.Items.ItemFruitTreeSapling;
import com.bioxx.tfc.Items.ItemGem;
import com.bioxx.tfc.Items.ItemGlassBottle;
import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.Items.ItemLeather;
import com.bioxx.tfc.Items.ItemLogs;
import com.bioxx.tfc.Items.ItemLooseRock;
import com.bioxx.tfc.Items.ItemMeltedMetal;
import com.bioxx.tfc.Items.ItemMetalSheet;
import com.bioxx.tfc.Items.ItemMetalSheet2x;
import com.bioxx.tfc.Items.ItemMudBrick;
import com.bioxx.tfc.Items.ItemOre;
import com.bioxx.tfc.Items.ItemOreSmall;
import com.bioxx.tfc.Items.ItemPlank;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Items.ItemRawHide;
import com.bioxx.tfc.Items.ItemReeds;
import com.bioxx.tfc.Items.ItemSluice;
import com.bioxx.tfc.Items.ItemStick;
import com.bioxx.tfc.Items.ItemStoneBrick;
import com.bioxx.tfc.Items.ItemTFCArmor;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.Items.ItemTuyere;
import com.bioxx.tfc.Items.ItemUnfinishedArmor;
import com.bioxx.tfc.Items.ItemYarn;
import com.bioxx.tfc.Items.ItemBlocks.ItemWoodDoor;
import com.bioxx.tfc.Items.Pottery.ItemPotteryBase;
import com.bioxx.tfc.Items.Pottery.ItemPotteryJug;
import com.bioxx.tfc.Items.Pottery.ItemPotteryMold;
import com.bioxx.tfc.Items.Pottery.ItemPotteryPot;
import com.bioxx.tfc.Items.Pottery.ItemPotterySmallVessel;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemCustomAxe;
import com.bioxx.tfc.Items.Tools.ItemCustomBow;
import com.bioxx.tfc.Items.Tools.ItemCustomBucket;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.Items.Tools.ItemCustomFishingRod;
import com.bioxx.tfc.Items.Tools.ItemCustomHoe;
import com.bioxx.tfc.Items.Tools.ItemCustomPickaxe;
import com.bioxx.tfc.Items.Tools.ItemCustomSaw;
import com.bioxx.tfc.Items.Tools.ItemCustomScythe;
import com.bioxx.tfc.Items.Tools.ItemCustomShovel;
import com.bioxx.tfc.Items.Tools.ItemCustomSword;
import com.bioxx.tfc.Items.Tools.ItemFirestarter;
import com.bioxx.tfc.Items.Tools.ItemFlintSteel;
import com.bioxx.tfc.Items.Tools.ItemGoldPan;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.Items.Tools.ItemJavelin;
import com.bioxx.tfc.Items.Tools.ItemKnife;
import com.bioxx.tfc.Items.Tools.ItemMiscToolHead;
import com.bioxx.tfc.Items.Tools.ItemProPick;
import com.bioxx.tfc.Items.Tools.ItemShears;
import com.bioxx.tfc.Items.Tools.ItemSpindle;
import com.bioxx.tfc.Items.Tools.ItemSteelBucketBlue;
import com.bioxx.tfc.Items.Tools.ItemSteelBucketRed;
import com.bioxx.tfc.Items.Tools.ItemWritableBookTFC;
import com.bioxx.tfc.api.Armor;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemSetup extends TFCItems
{
	public static void Setup()
	{
		//Tier 0
		IgInToolMaterial = EnumHelper.addToolMaterial("IgIn", 1, IgInStoneUses, 7F, 40, 5);
		SedToolMaterial = EnumHelper.addToolMaterial("Sed", 1, SedStoneUses, 6F, 40, 5);
		IgExToolMaterial = EnumHelper.addToolMaterial("IgEx", 1, IgExStoneUses, 7F, 40, 5);
		MMToolMaterial = EnumHelper.addToolMaterial("MM", 1, MMStoneUses, 6.5F, 40, 5);
		//Tier 1
		CopperToolMaterial = EnumHelper.addToolMaterial("Copper", 2, 				CopperUses, 		CopperEff, 			65, 8);
		//Tier 2
		BronzeToolMaterial = EnumHelper.addToolMaterial("Bronze", 2, 				BronzeUses, 		BronzeEff, 			100, 13);
		BismuthBronzeToolMaterial = EnumHelper.addToolMaterial("BismuthBronze", 2, 	BismuthBronzeUses, 	BismuthBronzeEff, 	90, 10);
		BlackBronzeToolMaterial = EnumHelper.addToolMaterial("BlackBronze", 2, 		BlackBronzeUses, 	BlackBronzeEff, 	95, 10);
		//Tier 3
		IronToolMaterial = EnumHelper.addToolMaterial("Iron", 2, 					WroughtIronUses, 	WroughtIronEff, 	135, 10);
		//Tier 4
		SteelToolMaterial = EnumHelper.addToolMaterial("Steel", 2, 					SteelUses, 			SteelEff, 			170, 10);
		//Tier 5
		BlackSteelToolMaterial = EnumHelper.addToolMaterial("BlackSteel", 2, 		BlackSteelUses, 	BlackSteelEff, 		205, 12);
		//Tier 6
		BlueSteelToolMaterial = EnumHelper.addToolMaterial("BlueSteel", 3, 			BlueSteelUses, 		BlueSteelEff, 		240, 22);
		RedSteelToolMaterial = EnumHelper.addToolMaterial("RedSteel", 3, 			RedSteelUses, 		RedSteelEff, 		240, 22);

		System.out.println(new StringBuilder().append("[TFC] Loading Items").toString());

		FishingRod = new ItemCustomFishingRod().setUnlocalizedName("fishingRod").setTextureName("tools/fishing_rod");
		Coal = new ItemCoal().setUnlocalizedName("coal");
		Stick = new ItemStick().setFull3D().setUnlocalizedName("stick");
		Bow = new ItemCustomBow().setUnlocalizedName("bow").setTextureName("bow");
		Items.bow = (ItemBow) Bow;
		Arrow = new ItemArrow().setUnlocalizedName("arrow").setCreativeTab(TFCTabs.TFCWeapons);
		Dye = new ItemDyeCustom().setUnlocalizedName("dyePowder").setTextureName("dye_powder").setCreativeTab(TFCTabs.TFCMaterials);
		GlassBottle = new ItemGlassBottle().setUnlocalizedName("Glass Bottle");
		Potion = new ItemCustomPotion().setUnlocalizedName("potion").setTextureName("potion");
		Rope = new ItemCustomLeash().setUnlocalizedName("Rope").setCreativeTab(TFCTabs.TFCTools);
		Items.lead = Rope;

		minecartCrate = new ItemCustomMinecart(1).setUnlocalizedName("minecartChest").setTextureName("minecart_chest");
		GoldPan = new ItemGoldPan().setUnlocalizedName("GoldPan");
		SluiceItem = new ItemSluice().setFolder("devices/").setUnlocalizedName("SluiceItem").setCreativeTab(TFCTabs.TFCDevices);

		Shears = new ItemShears(0, IronToolMaterial).setUnlocalizedName("shears").setTextureName("shears");

		ProPickBismuthBronze = new ItemProPick().setUnlocalizedName("Bismuth Bronze ProPick").setMaxDamage(BismuthBronzeUses/3);
		ProPickBlackBronze = new ItemProPick().setUnlocalizedName("Black Bronze ProPick").setMaxDamage(BlackBronzeUses/3);
		ProPickBlackSteel = new ItemProPick().setUnlocalizedName("Black Steel ProPick").setMaxDamage(BlackSteelUses/3);
		ProPickBlueSteel = new ItemProPick().setUnlocalizedName("Blue Steel ProPick").setMaxDamage(BlueSteelUses/3);
		ProPickBronze = new ItemProPick().setUnlocalizedName("Bronze ProPick").setMaxDamage(BronzeUses/3);
		ProPickCopper = new ItemProPick().setUnlocalizedName("Copper ProPick").setMaxDamage(CopperUses/3);
		ProPickIron = new ItemProPick().setUnlocalizedName("Wrought Iron ProPick").setMaxDamage(WroughtIronUses/3);
		ProPickRedSteel = new ItemProPick().setUnlocalizedName("Red Steel ProPick").setMaxDamage(RedSteelUses/3);
		ProPickSteel = new ItemProPick().setUnlocalizedName("Steel ProPick").setMaxDamage(SteelUses/3);

		BismuthIngot = new ItemIngot().setUnlocalizedName("Bismuth Ingot");
		BismuthBronzeIngot = new ItemIngot().setUnlocalizedName("Bismuth Bronze Ingot");
		BlackBronzeIngot = new ItemIngot().setUnlocalizedName("Black Bronze Ingot");
		BlackSteelIngot = new ItemIngot().setUnlocalizedName("Black Steel Ingot");
		BlueSteelIngot = new ItemIngot().setUnlocalizedName("Blue Steel Ingot");
		BrassIngot = new ItemIngot().setUnlocalizedName("Brass Ingot");
		BronzeIngot = new ItemIngot().setUnlocalizedName("Bronze Ingot");
		CopperIngot = new ItemIngot().setUnlocalizedName("Copper Ingot");
		GoldIngot = new ItemIngot().setUnlocalizedName("Gold Ingot");
		WroughtIronIngot = new ItemIngot().setUnlocalizedName("Wrought Iron Ingot");
		LeadIngot = new ItemIngot().setUnlocalizedName("Lead Ingot");
		NickelIngot = new ItemIngot().setUnlocalizedName("Nickel Ingot");
		PigIronIngot = new ItemIngot().setUnlocalizedName("Pig Iron Ingot");
		PlatinumIngot = new ItemIngot().setUnlocalizedName("Platinum Ingot");
		RedSteelIngot = new ItemIngot().setUnlocalizedName("Red Steel Ingot");
		RoseGoldIngot = new ItemIngot().setUnlocalizedName("Rose Gold Ingot");
		SilverIngot = new ItemIngot().setUnlocalizedName("Silver Ingot");
		SteelIngot = new ItemIngot().setUnlocalizedName("Steel Ingot");
		SterlingSilverIngot = new ItemIngot().setUnlocalizedName("Sterling Silver Ingot");
		TinIngot = new ItemIngot().setUnlocalizedName("Tin Ingot");
		ZincIngot = new ItemIngot().setUnlocalizedName("Zinc Ingot");

		BismuthIngot2x = ((ItemIngot)new ItemIngot().setUnlocalizedName("Bismuth Double Ingot")).setSize(EnumSize.LARGE).setMetal("Bismuth", 200);
		BismuthBronzeIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Bismuth Bronze Double Ingot")).setSize(EnumSize.LARGE).setMetal("Bismuth Bronze", 200);
		BlackBronzeIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Black Bronze Double Ingot")).setSize(EnumSize.LARGE).setMetal("Black Bronze", 200);
		BlackSteelIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Black Steel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Black Steel", 200);
		BlueSteelIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Blue Steel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Blue Steel", 200);
		BrassIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Brass Double Ingot")).setSize(EnumSize.LARGE).setMetal("Brass", 200);
		BronzeIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Bronze Double Ingot")).setSize(EnumSize.LARGE).setMetal("Bronze", 200);
		CopperIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Copper Double Ingot")).setSize(EnumSize.LARGE).setMetal("Copper", 200);
		GoldIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Gold Double Ingot")).setSize(EnumSize.LARGE).setMetal("Gold", 200);
		WroughtIronIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Wrought Iron Double Ingot")).setSize(EnumSize.LARGE).setMetal("Wrought Iron", 200);
		LeadIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Lead Double Ingot")).setSize(EnumSize.LARGE).setMetal("Lead", 200);
		NickelIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Nickel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Nickel", 200);
		PigIronIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Pig Iron Double Ingot")).setSize(EnumSize.LARGE).setMetal("Pig Iron", 200);
		PlatinumIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Platinum Double Ingot")).setSize(EnumSize.LARGE).setMetal("Platinum", 200);
		RedSteelIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Red Steel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Red Steel", 200);
		RoseGoldIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Rose Gold Double Ingot")).setSize(EnumSize.LARGE).setMetal("Rose Gold", 200);
		SilverIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Silver Double Ingot")).setSize(EnumSize.LARGE).setMetal("Silver", 200);
		SteelIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Steel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Steel", 200);
		SterlingSilverIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Sterling Silver Double Ingot")).setSize(EnumSize.LARGE).setMetal("Sterling Silver", 200);
		TinIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Tin Double Ingot")).setSize(EnumSize.LARGE).setMetal("Tin", 200);
		ZincIngot2x  = ((ItemIngot)new ItemIngot().setUnlocalizedName("Zinc Double Ingot")).setSize(EnumSize.LARGE).setMetal("Zinc", 200);

		GemRuby = new ItemGem().setUnlocalizedName("Ruby");
		GemSapphire = new ItemGem().setUnlocalizedName("Sapphire");
		GemEmerald = new ItemGem().setUnlocalizedName("Emerald");
		GemTopaz = new ItemGem().setUnlocalizedName("Topaz");
		GemTourmaline = new ItemGem().setUnlocalizedName("Tourmaline");
		GemJade = new ItemGem().setUnlocalizedName("Jade");
		GemBeryl = new ItemGem().setUnlocalizedName("Beryl");
		GemAgate = new ItemGem().setUnlocalizedName("Agate");
		GemOpal = new ItemGem().setUnlocalizedName("Opal");
		GemGarnet = new ItemGem().setUnlocalizedName("Garnet");
		GemJasper = new ItemGem().setUnlocalizedName("Jasper");
		GemAmethyst = new ItemGem().setUnlocalizedName("Amethyst");
		GemDiamond = new ItemGem().setUnlocalizedName("Diamond");

		//Tools
		IgInShovel = new ItemCustomShovel(IgInToolMaterial).setUnlocalizedName("IgIn Stone Shovel").setMaxDamage(IgInStoneUses);
		IgInAxe = new ItemCustomAxe(IgInToolMaterial, 60).setUnlocalizedName("IgIn Stone Axe").setMaxDamage(IgInStoneUses);
		IgInHoe = new ItemCustomHoe(IgInToolMaterial).setUnlocalizedName("IgIn Stone Hoe").setMaxDamage(IgInStoneUses);

		SedShovel= new ItemCustomShovel(SedToolMaterial).setUnlocalizedName("Sed Stone Shovel").setMaxDamage(SedStoneUses);
		SedAxe = new ItemCustomAxe(SedToolMaterial, 60).setUnlocalizedName("Sed Stone Axe").setMaxDamage(SedStoneUses);
		SedHoe = new ItemCustomHoe(SedToolMaterial).setUnlocalizedName("Sed Stone Hoe").setMaxDamage(SedStoneUses);

		IgExShovel= new ItemCustomShovel(IgExToolMaterial).setUnlocalizedName("IgEx Stone Shovel").setMaxDamage(IgExStoneUses);
		IgExAxe = new ItemCustomAxe(IgExToolMaterial, 60).setUnlocalizedName("IgEx Stone Axe").setMaxDamage(IgExStoneUses);
		IgExHoe = new ItemCustomHoe(IgExToolMaterial).setUnlocalizedName("IgEx Stone Hoe").setMaxDamage(IgExStoneUses);

		MMShovel = new ItemCustomShovel(MMToolMaterial).setUnlocalizedName("MM Stone Shovel").setMaxDamage(MMStoneUses);
		MMAxe = new ItemCustomAxe(MMToolMaterial, 60).setUnlocalizedName("MM Stone Axe").setMaxDamage(MMStoneUses);
		MMHoe = new ItemCustomHoe(MMToolMaterial).setUnlocalizedName("MM Stone Hoe").setMaxDamage(MMStoneUses);

		BismuthBronzePick = new ItemCustomPickaxe(BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Pick").setMaxDamage(BismuthBronzeUses);
		BismuthBronzeShovel = new ItemCustomShovel(BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Shovel").setMaxDamage(BismuthBronzeUses);
		BismuthBronzeAxe = new ItemCustomAxe(BismuthBronzeToolMaterial, 150).setUnlocalizedName("Bismuth Bronze Axe").setMaxDamage(BismuthBronzeUses);
		BismuthBronzeHoe = new ItemCustomHoe(BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Hoe").setMaxDamage(BismuthBronzeUses);

		BlackBronzePick = new ItemCustomPickaxe(BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Pick").setMaxDamage(BlackBronzeUses);
		BlackBronzeShovel = new ItemCustomShovel(BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Shovel").setMaxDamage(BlackBronzeUses);
		BlackBronzeAxe = new ItemCustomAxe(BlackBronzeToolMaterial, 170).setUnlocalizedName("Black Bronze Axe").setMaxDamage(BlackBronzeUses);
		BlackBronzeHoe = new ItemCustomHoe(BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Hoe").setMaxDamage(BlackBronzeUses);

		BlackSteelPick = new ItemCustomPickaxe(BlackSteelToolMaterial).setUnlocalizedName("Black Steel Pick").setMaxDamage(BlackSteelUses);
		BlackSteelShovel = new ItemCustomShovel(BlackSteelToolMaterial).setUnlocalizedName("Black Steel Shovel").setMaxDamage(BlackSteelUses);
		BlackSteelAxe = new ItemCustomAxe(BlackSteelToolMaterial, 245).setUnlocalizedName("Black Steel Axe").setMaxDamage(BlackSteelUses);
		BlackSteelHoe = new ItemCustomHoe(BlackSteelToolMaterial).setUnlocalizedName("Black Steel Hoe").setMaxDamage(BlackSteelUses);

		BlueSteelPick = new ItemCustomPickaxe(BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Pick").setMaxDamage(BlueSteelUses);
		BlueSteelShovel = new ItemCustomShovel(BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Shovel").setMaxDamage(BlueSteelUses);
		BlueSteelAxe = new ItemCustomAxe(BlueSteelToolMaterial, 270).setUnlocalizedName("Blue Steel Axe").setMaxDamage(BlueSteelUses);
		BlueSteelHoe = new ItemCustomHoe(BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Hoe").setMaxDamage(BlueSteelUses);

		BronzePick = new ItemCustomPickaxe(BronzeToolMaterial).setUnlocalizedName("Bronze Pick").setMaxDamage(BronzeUses);
		BronzeShovel = new ItemCustomShovel(BronzeToolMaterial).setUnlocalizedName("Bronze Shovel").setMaxDamage(BronzeUses);
		BronzeAxe = new ItemCustomAxe(BronzeToolMaterial, 160).setUnlocalizedName("Bronze Axe").setMaxDamage(BronzeUses);
		BronzeHoe = new ItemCustomHoe(BronzeToolMaterial).setUnlocalizedName("Bronze Hoe").setMaxDamage(BronzeUses);

		CopperPick = new ItemCustomPickaxe(CopperToolMaterial).setUnlocalizedName("Copper Pick").setMaxDamage(CopperUses);
		CopperShovel = new ItemCustomShovel(CopperToolMaterial).setUnlocalizedName("Copper Shovel").setMaxDamage(CopperUses);
		CopperAxe = new ItemCustomAxe(CopperToolMaterial, 115).setUnlocalizedName("Copper Axe").setMaxDamage(CopperUses);
		CopperHoe = new ItemCustomHoe(CopperToolMaterial).setUnlocalizedName("Copper Hoe").setMaxDamage(CopperUses);

		WroughtIronPick = new ItemCustomPickaxe(IronToolMaterial).setUnlocalizedName("Wrought Iron Pick").setMaxDamage(WroughtIronUses);
		WroughtIronShovel = new ItemCustomShovel(IronToolMaterial).setUnlocalizedName("Wrought Iron Shovel").setMaxDamage(WroughtIronUses);
		WroughtIronAxe = new ItemCustomAxe(IronToolMaterial, 185).setUnlocalizedName("Wrought Iron Axe").setMaxDamage(WroughtIronUses);
		WroughtIronHoe = new ItemCustomHoe(IronToolMaterial).setUnlocalizedName("Wrought Iron Hoe").setMaxDamage(WroughtIronUses);;

		RedSteelPick = new ItemCustomPickaxe(RedSteelToolMaterial).setUnlocalizedName("Red Steel Pick").setMaxDamage(RedSteelUses);
		RedSteelShovel = new ItemCustomShovel(RedSteelToolMaterial).setUnlocalizedName("Red Steel Shovel").setMaxDamage(RedSteelUses);
		RedSteelAxe = new ItemCustomAxe(RedSteelToolMaterial, 270).setUnlocalizedName("Red Steel Axe").setMaxDamage(RedSteelUses);
		RedSteelHoe = new ItemCustomHoe(RedSteelToolMaterial).setUnlocalizedName("Red Steel Hoe").setMaxDamage(RedSteelUses);

		SteelPick = new ItemCustomPickaxe(SteelToolMaterial).setUnlocalizedName("Steel Pick").setMaxDamage(SteelUses);
		SteelShovel = new ItemCustomShovel(SteelToolMaterial).setUnlocalizedName("Steel Shovel").setMaxDamage(SteelUses);
		SteelAxe = new ItemCustomAxe(SteelToolMaterial, 210).setUnlocalizedName("Steel Axe").setMaxDamage(SteelUses);
		SteelHoe = new ItemCustomHoe(SteelToolMaterial).setUnlocalizedName("Steel Hoe").setMaxDamage(SteelUses);

		//chisels
		BismuthBronzeChisel = new ItemChisel(BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Chisel").setMaxDamage(BismuthBronzeUses);
		BlackBronzeChisel = new ItemChisel(BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Chisel").setMaxDamage(BlackBronzeUses);
		BlackSteelChisel = new ItemChisel(BlackSteelToolMaterial).setUnlocalizedName("Black Steel Chisel").setMaxDamage(BlackSteelUses);
		BlueSteelChisel = new ItemChisel(BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Chisel").setMaxDamage(BlueSteelUses);
		BronzeChisel = new ItemChisel(BronzeToolMaterial).setUnlocalizedName("Bronze Chisel").setMaxDamage(BronzeUses);
		CopperChisel = new ItemChisel(CopperToolMaterial).setUnlocalizedName("Copper Chisel").setMaxDamage(CopperUses);
		WroughtIronChisel = new ItemChisel(IronToolMaterial).setUnlocalizedName("Wrought Iron Chisel").setMaxDamage(WroughtIronUses);
		RedSteelChisel = new ItemChisel(RedSteelToolMaterial).setUnlocalizedName("Red Steel Chisel").setMaxDamage(RedSteelUses);
		SteelChisel = new ItemChisel(SteelToolMaterial).setUnlocalizedName("Steel Chisel").setMaxDamage(SteelUses);

		BismuthBronzeSword = new ItemCustomSword(BismuthBronzeToolMaterial, 210).setUnlocalizedName("Bismuth Bronze Sword").setMaxDamage(BismuthBronzeUses);
		BlackBronzeSword = new ItemCustomSword(BlackBronzeToolMaterial, 	230).setUnlocalizedName("Black Bronze Sword").setMaxDamage(BlackBronzeUses);
		BlackSteelSword = new ItemCustomSword(BlackSteelToolMaterial, 		270).setUnlocalizedName("Black Steel Sword").setMaxDamage(BlackSteelUses);
		BlueSteelSword = new ItemCustomSword(BlueSteelToolMaterial,			315).setUnlocalizedName("Blue Steel Sword").setMaxDamage(BlueSteelUses);
		BronzeSword = new ItemCustomSword(BronzeToolMaterial,				220).setUnlocalizedName("Bronze Sword").setMaxDamage(BronzeUses);
		CopperSword = new ItemCustomSword(CopperToolMaterial, 				165).setUnlocalizedName("Copper Sword").setMaxDamage(CopperUses);
		WroughtIronSword = new ItemCustomSword(IronToolMaterial,			240).setUnlocalizedName("Wrought Iron Sword").setMaxDamage(WroughtIronUses);
		RedSteelSword = new ItemCustomSword(RedSteelToolMaterial,			305).setUnlocalizedName("Red Steel Sword").setMaxDamage(RedSteelUses);
		SteelSword = new ItemCustomSword(SteelToolMaterial,					265).setUnlocalizedName("Steel Sword").setMaxDamage(SteelUses);

		BismuthBronzeMace = new ItemCustomSword(BismuthBronzeToolMaterial,  210,EnumDamageType.CRUSHING).setUnlocalizedName("Bismuth Bronze Mace").setMaxDamage(BismuthBronzeUses);
		BlackBronzeMace = new ItemCustomSword(BlackBronzeToolMaterial, 		230,EnumDamageType.CRUSHING).setUnlocalizedName("Black Bronze Mace").setMaxDamage(BlackBronzeUses);
		BlackSteelMace = new ItemCustomSword(BlackSteelToolMaterial, 		270,EnumDamageType.CRUSHING).setUnlocalizedName("Black Steel Mace").setMaxDamage(BlackSteelUses);
		BlueSteelMace = new ItemCustomSword(BlueSteelToolMaterial, 			315,EnumDamageType.CRUSHING).setUnlocalizedName("Blue Steel Mace").setMaxDamage(BlueSteelUses);
		BronzeMace = new ItemCustomSword(BronzeToolMaterial, 				220,EnumDamageType.CRUSHING).setUnlocalizedName("Bronze Mace").setMaxDamage(BronzeUses);
		CopperMace = new ItemCustomSword(CopperToolMaterial, 				165,EnumDamageType.CRUSHING).setUnlocalizedName("Copper Mace").setMaxDamage(CopperUses);
		WroughtIronMace = new ItemCustomSword(IronToolMaterial, 			240,EnumDamageType.CRUSHING).setUnlocalizedName("Wrought Iron Mace").setMaxDamage(WroughtIronUses);
		RedSteelMace = new ItemCustomSword(RedSteelToolMaterial, 			305,EnumDamageType.CRUSHING).setUnlocalizedName("Red Steel Mace").setMaxDamage(RedSteelUses);
		SteelMace = new ItemCustomSword(SteelToolMaterial, 					265,EnumDamageType.CRUSHING).setUnlocalizedName("Steel Mace").setMaxDamage(SteelUses);

		BismuthBronzeSaw = new ItemCustomSaw(BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Saw").setMaxDamage(BismuthBronzeUses);
		BlackBronzeSaw = new ItemCustomSaw(BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Saw").setMaxDamage(BlackBronzeUses);
		BlackSteelSaw = new ItemCustomSaw(BlackSteelToolMaterial).setUnlocalizedName("Black Steel Saw").setMaxDamage(BlackSteelUses);
		BlueSteelSaw = new ItemCustomSaw(BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Saw").setMaxDamage(BlueSteelUses);
		BronzeSaw = new ItemCustomSaw(BronzeToolMaterial).setUnlocalizedName("Bronze Saw").setMaxDamage(BronzeUses);
		CopperSaw = new ItemCustomSaw(CopperToolMaterial).setUnlocalizedName("Copper Saw").setMaxDamage(CopperUses);
		WroughtIronSaw = new ItemCustomSaw(IronToolMaterial).setUnlocalizedName("Wrought Iron Saw").setMaxDamage(WroughtIronUses);
		RedSteelSaw = new ItemCustomSaw(RedSteelToolMaterial).setUnlocalizedName("Red Steel Saw").setMaxDamage(RedSteelUses);
		SteelSaw = new ItemCustomSaw(SteelToolMaterial).setUnlocalizedName("Steel Saw").setMaxDamage(SteelUses);

		HCBlackSteelIngot = new ItemIngot(false).setUnlocalizedName("HC Black Steel Ingot");
		WeakBlueSteelIngot = new ItemIngot(false).setUnlocalizedName("Weak Blue Steel Ingot");
		WeakRedSteelIngot = new ItemIngot(false).setUnlocalizedName("Weak Red Steel Ingot");
		WeakSteelIngot = new ItemIngot(false).setUnlocalizedName("Weak Steel Ingot");
		HCBlueSteelIngot = new ItemIngot(false).setUnlocalizedName("HC Blue Steel Ingot");
		HCRedSteelIngot = new ItemIngot(false).setUnlocalizedName("HC Red Steel Ingot");
		HCSteelIngot = new ItemIngot(false).setUnlocalizedName("HC Steel Ingot");

		OreChunk = new ItemOre().setFolder("ore/").setUnlocalizedName("Ore");
		SmallOreChunk = new ItemOreSmall().setUnlocalizedName("Small Ore");
		Powder = new ItemTerra().setMetaNames(Global.POWDER).setUnlocalizedName("Powder").setCreativeTab(TFCTabs.TFCMaterials);
		Logs = new ItemLogs().setUnlocalizedName("Log");


		//javelins
		IgInStoneJavelin = new ItemJavelin(IgInToolMaterial, 60).setUnlocalizedName("IgIn Stone Javelin");
		SedStoneJavelin = new ItemJavelin(SedToolMaterial, 60).setUnlocalizedName("Sed Stone Javelin");
		IgExStoneJavelin = new ItemJavelin(IgExToolMaterial, 60).setUnlocalizedName("IgEx Stone Javelin");
		MMStoneJavelin = new ItemJavelin(MMToolMaterial, 60).setUnlocalizedName("MM Stone Javelin");
		CopperJavelin = new ItemJavelin(CopperToolMaterial, 80).setUnlocalizedName("Copper Javelin");
		BismuthBronzeJavelin = new ItemJavelin(BismuthBronzeToolMaterial, 90).setUnlocalizedName("Bismuth Bronze Javelin");
		BronzeJavelin = new ItemJavelin(BronzeToolMaterial, 100).setUnlocalizedName("Bronze Javelin");
		BlackBronzeJavelin = new ItemJavelin(BlackBronzeToolMaterial, 95).setUnlocalizedName("Black Bronze Javelin");
		WroughtIronJavelin = new ItemJavelin(IronToolMaterial, 135).setUnlocalizedName("Wrought Iron Javelin");
		SteelJavelin = new ItemJavelin(SteelToolMaterial, 170).setUnlocalizedName("Steel Javelin");
		BlackSteelJavelin = new ItemJavelin(BlackSteelToolMaterial, 205).setUnlocalizedName("Black Steel Javelin");
		BlueSteelJavelin = new ItemJavelin(BlueSteelToolMaterial, 240).setUnlocalizedName("Blue Steel Javelin");
		RedSteelJavelin = new ItemJavelin(RedSteelToolMaterial, 240).setUnlocalizedName("Red Steel Javelin");

		//javelin heads
		IgInStoneJavelinHead = new ItemMiscToolHead(IgInToolMaterial).setUnlocalizedName("IgIn Stone Javelin Head");
		SedStoneJavelinHead = new ItemMiscToolHead(SedToolMaterial).setUnlocalizedName("Sed Stone Javelin Head");
		IgExStoneJavelinHead = new ItemMiscToolHead(IgExToolMaterial).setUnlocalizedName("IgEx Stone Javelin Head");
		MMStoneJavelinHead = new ItemMiscToolHead(MMToolMaterial).setUnlocalizedName("MM Stone Javelin Head");
		CopperJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Copper Javelin Head");
		BismuthBronzeJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Javelin Head");
		BronzeJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Javelin Head");
		BlackBronzeJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Javelin Head");
		WroughtIronJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Javelin Head");
		SteelJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Steel Javelin Head");
		BlackSteelJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Javelin Head");
		BlueSteelJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Javelin Head");
		RedSteelJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Javelin Head");

		BismuthUnshaped = new ItemMeltedMetal().setUnlocalizedName("Bismuth Unshaped");
		BismuthBronzeUnshaped = new ItemMeltedMetal().setUnlocalizedName("Bismuth Bronze Unshaped");
		BlackBronzeUnshaped = new ItemMeltedMetal().setUnlocalizedName("Black Bronze Unshaped");
		BlackSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("Black Steel Unshaped");
		BlueSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("Blue Steel Unshaped");
		BrassUnshaped = new ItemMeltedMetal().setUnlocalizedName("Brass Unshaped");
		BronzeUnshaped = new ItemMeltedMetal().setUnlocalizedName("Bronze Unshaped");
		CopperUnshaped = new ItemMeltedMetal().setUnlocalizedName("Copper Unshaped");
		GoldUnshaped = new ItemMeltedMetal().setUnlocalizedName("Gold Unshaped");
		WroughtIronUnshaped = new ItemMeltedMetal().setUnlocalizedName("Wrought Iron Unshaped");
		LeadUnshaped = new ItemMeltedMetal().setUnlocalizedName("Lead Unshaped");
		NickelUnshaped = new ItemMeltedMetal().setUnlocalizedName("Nickel Unshaped");
		PigIronUnshaped = new ItemMeltedMetal().setUnlocalizedName("Pig Iron Unshaped");
		PlatinumUnshaped = new ItemMeltedMetal().setUnlocalizedName("Platinum Unshaped");
		RedSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("Red Steel Unshaped");
		RoseGoldUnshaped = new ItemMeltedMetal().setUnlocalizedName("Rose Gold Unshaped");
		SilverUnshaped = new ItemMeltedMetal().setUnlocalizedName("Silver Unshaped");
		SteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("Steel Unshaped");
		SterlingSilverUnshaped = new ItemMeltedMetal().setUnlocalizedName("Sterling Silver Unshaped");
		TinUnshaped = new ItemMeltedMetal().setUnlocalizedName("Tin Unshaped");
		ZincUnshaped = new ItemMeltedMetal().setUnlocalizedName("Zinc Unshaped");

		//Hammers
		StoneHammer = new ItemHammer(IgInToolMaterial, 60).setUnlocalizedName("Stone Hammer").setMaxDamage(IgInStoneUses);
		BismuthBronzeHammer = new ItemHammer(BismuthBronzeToolMaterial, 90).setUnlocalizedName("Bismuth Bronze Hammer").setMaxDamage(BismuthBronzeUses);
		BlackBronzeHammer = new ItemHammer(BlackBronzeToolMaterial, 95).setUnlocalizedName("Black Bronze Hammer").setMaxDamage(BlackBronzeUses);
		BlackSteelHammer = new ItemHammer(BlackSteelToolMaterial, 205).setUnlocalizedName("Black Steel Hammer").setMaxDamage(BlackSteelUses);
		BlueSteelHammer = new ItemHammer(BlueSteelToolMaterial, 240).setUnlocalizedName("Blue Steel Hammer").setMaxDamage(BlueSteelUses);
		BronzeHammer = new ItemHammer(BronzeToolMaterial, 100).setUnlocalizedName("Bronze Hammer").setMaxDamage(BronzeUses);
		CopperHammer = new ItemHammer(CopperToolMaterial, 80).setUnlocalizedName("Copper Hammer").setMaxDamage(CopperUses);
		WroughtIronHammer = new ItemHammer(IronToolMaterial, 135).setUnlocalizedName("Wrought Iron Hammer").setMaxDamage(WroughtIronUses);
		RedSteelHammer = new ItemHammer(RedSteelToolMaterial, 240).setUnlocalizedName("Red Steel Hammer").setMaxDamage(RedSteelUses);
		SteelHammer = new ItemHammer(SteelToolMaterial, 170).setUnlocalizedName("Steel Hammer").setMaxDamage(SteelUses);

		Ink = new ItemTerra().setUnlocalizedName("Ink").setCreativeTab(TFCTabs.TFCMaterials);
		FireStarter = new ItemFirestarter().setFolder("tools/").setUnlocalizedName("Firestarter");

		//Tool heads
		BismuthBronzePickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Pick Head");
		BlackBronzePickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Pick Head");
		BlackSteelPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Pick Head");
		BlueSteelPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Pick Head");
		BronzePickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Pick Head");
		CopperPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Copper Pick Head");
		WroughtIronPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Pick Head");
		RedSteelPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Pick Head");
		SteelPickaxeHead = new ItemMiscToolHead().setUnlocalizedName("Steel Pick Head");

		BismuthBronzeShovelHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Shovel Head");
		BlackBronzeShovelHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Shovel Head");
		BlackSteelShovelHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Shovel Head");
		BlueSteelShovelHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Shovel Head");
		BronzeShovelHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Shovel Head");
		CopperShovelHead = new ItemMiscToolHead().setUnlocalizedName("Copper Shovel Head");
		WroughtIronShovelHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Shovel Head");
		RedSteelShovelHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Shovel Head");
		SteelShovelHead = new ItemMiscToolHead().setUnlocalizedName("Steel Shovel Head");

		BismuthBronzeHoeHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Hoe Head");
		BlackBronzeHoeHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Hoe Head");
		BlackSteelHoeHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Hoe Head");
		BlueSteelHoeHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Hoe Head");
		BronzeHoeHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Hoe Head");
		CopperHoeHead = new ItemMiscToolHead().setUnlocalizedName("Copper Hoe Head");
		WroughtIronHoeHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Hoe Head");
		RedSteelHoeHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Hoe Head");
		SteelHoeHead = new ItemMiscToolHead().setUnlocalizedName("Steel Hoe Head");

		BismuthBronzeAxeHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Axe Head");
		BlackBronzeAxeHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Axe Head");
		BlackSteelAxeHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Axe Head");
		BlueSteelAxeHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Axe Head");
		BronzeAxeHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Axe Head");
		CopperAxeHead = new ItemMiscToolHead().setUnlocalizedName("Copper Axe Head");
		WroughtIronAxeHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Axe Head");
		RedSteelAxeHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Axe Head");
		SteelAxeHead = new ItemMiscToolHead().setUnlocalizedName("Steel Axe Head");

		BismuthBronzeHammerHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Hammer Head");
		BlackBronzeHammerHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Hammer Head");
		BlackSteelHammerHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Hammer Head");
		BlueSteelHammerHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Hammer Head");
		BronzeHammerHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Hammer Head");
		CopperHammerHead = new ItemMiscToolHead().setUnlocalizedName("Copper Hammer Head");
		WroughtIronHammerHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Hammer Head");
		RedSteelHammerHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Hammer Head");
		SteelHammerHead = new ItemMiscToolHead().setUnlocalizedName("Steel Hammer Head");

		//chisel heads
		BismuthBronzeChiselHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Chisel Head");
		BlackBronzeChiselHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Chisel Head");
		BlackSteelChiselHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Chisel Head");
		BlueSteelChiselHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Chisel Head");
		BronzeChiselHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Chisel Head");
		CopperChiselHead = new ItemMiscToolHead().setUnlocalizedName("Copper Chisel Head");
		WroughtIronChiselHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Chisel Head");
		RedSteelChiselHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Chisel Head");
		SteelChiselHead = new ItemMiscToolHead().setUnlocalizedName("Steel Chisel Head");

		BismuthBronzeSwordHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Sword Blade");
		BlackBronzeSwordHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Sword Blade");
		BlackSteelSwordHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Sword Blade");
		BlueSteelSwordHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Sword Blade");
		BronzeSwordHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Sword Blade");
		CopperSwordHead = new ItemMiscToolHead().setUnlocalizedName("Copper Sword Blade");
		WroughtIronSwordHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Sword Blade");
		RedSteelSwordHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Sword Blade");
		SteelSwordHead = new ItemMiscToolHead().setUnlocalizedName("Steel Sword Blade");

		BismuthBronzeMaceHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Mace Head");
		BlackBronzeMaceHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Mace Head");
		BlackSteelMaceHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Mace Head");
		BlueSteelMaceHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Mace Head");
		BronzeMaceHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Mace Head");
		CopperMaceHead = new ItemMiscToolHead().setUnlocalizedName("Copper Mace Head");
		WroughtIronMaceHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Mace Head");
		RedSteelMaceHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Mace Head");
		SteelMaceHead = new ItemMiscToolHead().setUnlocalizedName("Steel Mace Head");

		BismuthBronzeSawHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Saw Blade");
		BlackBronzeSawHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Saw Blade");
		BlackSteelSawHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Saw Blade");
		BlueSteelSawHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Saw Blade");
		BronzeSawHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Saw Blade");
		CopperSawHead = new ItemMiscToolHead().setUnlocalizedName("Copper Saw Blade");
		WroughtIronSawHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Saw Blade");
		RedSteelSawHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Saw Blade");
		SteelSawHead = new ItemMiscToolHead().setUnlocalizedName("Steel Saw Blade");

		HCBlackSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("HC Black Steel Unshaped");
		WeakBlueSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("Weak Blue Steel Unshaped");
		HCBlueSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("HC Blue Steel Unshaped");
		WeakRedSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("Weak Red Steel Unshaped");
		HCRedSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("HC Red Steel Unshaped");
		WeakSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("Weak Steel Unshaped");
		HCSteelUnshaped = new ItemMeltedMetal().setUnlocalizedName("HC Steel Unshaped");
		Coke = (new ItemTerra().setUnlocalizedName("Coke").setCreativeTab(TFCTabs.TFCMaterials));

		BismuthBronzeProPickHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze ProPick Head");
		BlackBronzeProPickHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze ProPick Head");
		BlackSteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel ProPick Head");
		BlueSteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel ProPick Head");
		BronzeProPickHead = new ItemMiscToolHead().setUnlocalizedName("Bronze ProPick Head");
		CopperProPickHead = new ItemMiscToolHead().setUnlocalizedName("Copper ProPick Head");
		WroughtIronProPickHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron ProPick Head");
		RedSteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel ProPick Head");
		SteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Steel ProPick Head");

		/**
		 * Scythe
		 * */
		BismuthBronzeScythe = new ItemCustomScythe(BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Scythe").setMaxDamage(BismuthBronzeUses);
		BlackBronzeScythe = new ItemCustomScythe(BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Scythe").setMaxDamage(BlackBronzeUses);
		BlackSteelScythe = new ItemCustomScythe(BlackSteelToolMaterial).setUnlocalizedName("Black Steel Scythe").setMaxDamage(BlackSteelUses);
		BlueSteelScythe = new ItemCustomScythe(BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Scythe").setMaxDamage(BlueSteelUses);
		BronzeScythe = new ItemCustomScythe(BronzeToolMaterial).setUnlocalizedName("Bronze Scythe").setMaxDamage(BronzeUses);
		CopperScythe = new ItemCustomScythe(CopperToolMaterial).setUnlocalizedName("Copper Scythe").setMaxDamage(CopperUses);
		WroughtIronScythe = new ItemCustomScythe(IronToolMaterial).setUnlocalizedName("Wrought Iron Scythe").setMaxDamage(WroughtIronUses);
		RedSteelScythe = new ItemCustomScythe(RedSteelToolMaterial).setUnlocalizedName("Red Steel Scythe").setMaxDamage(RedSteelUses);
		SteelScythe = new ItemCustomScythe(SteelToolMaterial).setUnlocalizedName("Steel Scythe").setMaxDamage(SteelUses);

		BismuthBronzeScytheHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Scythe Blade");
		BlackBronzeScytheHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Scythe Blade");
		BlackSteelScytheHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Scythe Blade");
		BlueSteelScytheHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Scythe Blade");
		BronzeScytheHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Scythe Blade");
		CopperScytheHead = new ItemMiscToolHead().setUnlocalizedName("Copper Scythe Blade");
		WroughtIronScytheHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Scythe Blade");
		RedSteelScytheHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Scythe Blade");
		SteelScytheHead = new ItemMiscToolHead().setUnlocalizedName("Steel Scythe Blade");

		WoodenBucketEmpty = new ItemCustomBucket(Blocks.air).setUnlocalizedName("Wooden Bucket Empty");
		WoodenBucketWater = new ItemCustomBucket(TFCBlocks.FreshWater, WoodenBucketEmpty).setUnlocalizedName("Wooden Bucket Water");
		WoodenBucketSaltWater = new ItemCustomBucket(TFCBlocks.SaltWater, WoodenBucketEmpty).setUnlocalizedName("Wooden Bucket Salt Water");
		WoodenBucketMilk = new ItemCustomBucketMilk().setUnlocalizedName("Wooden Bucket Milk").setContainerItem(WoodenBucketEmpty).setCreativeTab(TFCTabs.TFCFoods);

		BismuthBronzeKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Knife Blade");
		BlackBronzeKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Knife Blade");
		BlackSteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Knife Blade");
		BlueSteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Knife Blade");
		BronzeKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Knife Blade");
		CopperKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Copper Knife Blade");
		WroughtIronKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Knife Blade");
		RedSteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Knife Blade");
		SteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Steel Knife Blade");

		BismuthBronzeKnife = new ItemKnife(BismuthBronzeToolMaterial, 155).setUnlocalizedName("Bismuth Bronze Knife").setMaxDamage(BismuthBronzeUses);
		BlackBronzeKnife = new ItemKnife(BlackBronzeToolMaterial, 	165).setUnlocalizedName("Black Bronze Knife").setMaxDamage(BlackBronzeUses);
		BlackSteelKnife = new ItemKnife(BlackSteelToolMaterial, 		205).setUnlocalizedName("Black Steel Knife").setMaxDamage(BlackSteelUses);
		BlueSteelKnife = new ItemKnife(BlueSteelToolMaterial, 		250).setUnlocalizedName("Blue Steel Knife").setMaxDamage(BlueSteelUses);
		BronzeKnife = new ItemKnife(BronzeToolMaterial, 				150).setUnlocalizedName("Bronze Knife").setMaxDamage(BronzeUses);
		CopperKnife = new ItemKnife(CopperToolMaterial, 				100).setUnlocalizedName("Copper Knife").setMaxDamage(CopperUses);
		WroughtIronKnife = new ItemKnife(IronToolMaterial, 			175).setUnlocalizedName("Wrought Iron Knife").setMaxDamage(WroughtIronUses);
		RedSteelKnife = new ItemKnife(RedSteelToolMaterial, 			250).setUnlocalizedName("Red Steel Knife").setMaxDamage(RedSteelUses);
		SteelKnife = new ItemKnife(SteelToolMaterial,					200).setUnlocalizedName("Steel Knife").setMaxDamage(SteelUses);

		FlatRock = (new ItemFlatGeneric().setFolder("rocks/flatrocks/").setMetaNames(Global.STONE_ALL).setUnlocalizedName("FlatRock"));
		LooseRock = (new ItemLooseRock().setSpecialCraftingType(FlatRock).setFolder("rocks/").setMetaNames(Global.STONE_ALL).setUnlocalizedName("LooseRock"));

		IgInStoneShovelHead = new ItemMiscToolHead(IgInToolMaterial).setUnlocalizedName("IgIn Stone Shovel Head");
		SedStoneShovelHead = new ItemMiscToolHead(SedToolMaterial).setUnlocalizedName("Sed Stone Shovel Head");
		IgExStoneShovelHead = new ItemMiscToolHead(IgExToolMaterial).setUnlocalizedName("IgEx Stone Shovel Head");
		MMStoneShovelHead = new ItemMiscToolHead(MMToolMaterial).setUnlocalizedName("MM Stone Shovel Head");

		IgInStoneAxeHead = new ItemMiscToolHead(IgInToolMaterial).setUnlocalizedName("IgIn Stone Axe Head");
		SedStoneAxeHead = new ItemMiscToolHead(SedToolMaterial).setUnlocalizedName("Sed Stone Axe Head");
		IgExStoneAxeHead = new ItemMiscToolHead(IgExToolMaterial).setUnlocalizedName("IgEx Stone Axe Head");
		MMStoneAxeHead = new ItemMiscToolHead(MMToolMaterial).setUnlocalizedName("MM Stone Axe Head");

		IgInStoneHoeHead = new ItemMiscToolHead(IgInToolMaterial).setUnlocalizedName("IgIn Stone Hoe Head");
		SedStoneHoeHead = new ItemMiscToolHead(SedToolMaterial).setUnlocalizedName("Sed Stone Hoe Head");
		IgExStoneHoeHead = new ItemMiscToolHead(IgExToolMaterial).setUnlocalizedName("IgEx Stone Hoe Head");
		MMStoneHoeHead = new ItemMiscToolHead(MMToolMaterial).setUnlocalizedName("MM Stone Hoe Head");

		StoneKnifeHead = new ItemMiscToolHead(IgInToolMaterial).setUnlocalizedName("Stone Knife Blade");
		StoneHammerHead = new ItemMiscToolHead(IgInToolMaterial).setUnlocalizedName("Stone Hammer Head");

		StoneKnife = new ItemKnife(IgExToolMaterial, 40).setUnlocalizedName("Stone Knife").setMaxDamage(IgExStoneUses);
		SinglePlank = new ItemPlank().setUnlocalizedName("SinglePlank");

		RedSteelBucketEmpty = (new ItemSteelBucketRed(Blocks.air)).setUnlocalizedName("Red Steel Bucket Empty");
		RedSteelBucketWater = (new ItemSteelBucketRed(TFCBlocks.FreshWater)).setUnlocalizedName("Red Steel Bucket Water").setContainerItem(RedSteelBucketEmpty);
		RedSteelBucketSaltWater = (new ItemSteelBucketRed(TFCBlocks.SaltWater)).setUnlocalizedName("Red Steel Bucket Salt Water").setContainerItem(RedSteelBucketEmpty);

		BlueSteelBucketEmpty = (new ItemSteelBucketBlue(Blocks.air)).setUnlocalizedName("Blue Steel Bucket Empty");
		BlueSteelBucketLava = (new ItemSteelBucketBlue(TFCBlocks.Lava)).setUnlocalizedName("Blue Steel Bucket Lava").setContainerItem(BlueSteelBucketEmpty);

		Quern = ((ItemTerra) new ItemTerra().setUnlocalizedName("Quern").setMaxDamage(250)).setSize(EnumSize.MEDIUM).setWeight(EnumWeight.HEAVY);
		FlintSteel = new ItemFlintSteel().setUnlocalizedName("flintAndSteel").setMaxDamage(200).setTextureName("flint_and_steel");

		DoorOak = new ItemWoodDoor(0).setUnlocalizedName("Oak Door");
		DoorAspen = new ItemWoodDoor(1).setUnlocalizedName("Aspen Door");
		DoorBirch = new ItemWoodDoor(2).setUnlocalizedName("Birch Door");
		DoorChestnut = new ItemWoodDoor(3).setUnlocalizedName("Chestnut Door");
		DoorDouglasFir = new ItemWoodDoor(4).setUnlocalizedName("Douglas Fir Door");
		DoorHickory = new ItemWoodDoor(5).setUnlocalizedName("Hickory Door");
		DoorMaple = new ItemWoodDoor(6).setUnlocalizedName("Maple Door");
		DoorAsh = new ItemWoodDoor(7).setUnlocalizedName("Ash Door");
		DoorPine = new ItemWoodDoor(8).setUnlocalizedName("Pine Door");
		DoorSequoia = new ItemWoodDoor(9).setUnlocalizedName("Sequoia Door");
		DoorSpruce = new ItemWoodDoor(10).setUnlocalizedName("Spruce Door");
		DoorSycamore = new ItemWoodDoor(11).setUnlocalizedName("Sycamore Door");
		DoorWhiteCedar = new ItemWoodDoor(12).setUnlocalizedName("White Cedar Door");
		DoorWhiteElm = new ItemWoodDoor(13).setUnlocalizedName("White Elm Door");
		DoorWillow = new ItemWoodDoor(14).setUnlocalizedName("Willow Door");
		DoorKapok = new ItemWoodDoor(15).setUnlocalizedName("Kapok Door");
		DoorAcacia = new ItemWoodDoor(16).setUnlocalizedName("Acacia Door");

		Beer = new ItemAlcohol().setUnlocalizedName("Beer").setCreativeTab(TFCTabs.TFCFoods);
		Cider = new ItemAlcohol().setUnlocalizedName("Cider").setCreativeTab(TFCTabs.TFCFoods);
		Rum = new ItemAlcohol().setUnlocalizedName("Rum").setCreativeTab(TFCTabs.TFCFoods);
		RyeWhiskey = new ItemAlcohol().setUnlocalizedName("RyeWhiskey").setCreativeTab(TFCTabs.TFCFoods);
		Sake = new ItemAlcohol().setUnlocalizedName("Sake").setCreativeTab(TFCTabs.TFCFoods);
		Vodka = new ItemAlcohol().setUnlocalizedName("Vodka").setCreativeTab(TFCTabs.TFCFoods);
		Whiskey = new ItemAlcohol().setUnlocalizedName("Whiskey").setCreativeTab(TFCTabs.TFCFoods);
		CornWhiskey = new ItemAlcohol().setUnlocalizedName("CornWhiskey").setCreativeTab(TFCTabs.TFCFoods);

		Blueprint = new ItemBlueprint();
		Nametag = new ItemCustomNameTag();
		writabeBookTFC = new ItemWritableBookTFC("Fix Me I'm Broken").setUnlocalizedName("book");
		WoolYarn = new ItemYarn().setUnlocalizedName("WoolYarn").setCreativeTab(TFCTabs.TFCMaterials);
		Wool = new ItemTerra().setUnlocalizedName("Wool").setCreativeTab(TFCTabs.TFCMaterials);
		WoolCloth = new ItemTerra().setUnlocalizedName("WoolCloth").setCreativeTab(TFCTabs.TFCMaterials);
		SilkCloth = new ItemTerra().setUnlocalizedName("SilkCloth").setCreativeTab(TFCTabs.TFCMaterials);
		BurlapCloth = new ItemTerra().setUnlocalizedName("BurlapCloth").setCreativeTab(TFCTabs.TFCMaterials);
		Spindle = new ItemSpindle().setUnlocalizedName("Spindle").setCreativeTab(TFCTabs.TFCPottery);

		SpindleHead = new ItemPotteryBase().setMetaNames(new String[]
				{ "Clay Spindle", "Spindle Head" }).setUnlocalizedName("Spindle Head").setCreativeTab(TFCTabs.TFCPottery);
		StoneBrick = (new ItemStoneBrick().setFolder("tools/").setUnlocalizedName("ItemStoneBrick"));
		Mortar = new ItemTerra().setFolder("tools/").setUnlocalizedName("Mortar").setCreativeTab(TFCTabs.TFCMaterials);
		Vinegar = new ItemCustomBucket(Blocks.air).setFolder("food/").setUnlocalizedName("Vinegar").setContainerItem(WoodenBucketEmpty).setCreativeTab(TFCTabs.TFCFoods);
		Hide = new ItemRawHide().setFolder("tools/").setUnlocalizedName("Hide").setCreativeTab(TFCTabs.TFCMaterials);
		SoakedHide = new ItemRawHide().setFolder("tools/").setUnlocalizedName("Soaked Hide").setCreativeTab(TFCTabs.TFCMaterials);
		ScrapedHide = new ItemRawHide().setFolder("tools/").setUnlocalizedName("Scraped Hide").setCreativeTab(TFCTabs.TFCMaterials);
		PrepHide = new ItemRawHide().setFolder("tools/").setFolder("tools/").setUnlocalizedName("Prep Hide").setCreativeTab(TFCTabs.TFCMaterials);

		SheepSkin = new ItemRawHide().setFolder("tools/").setUnlocalizedName("Sheep Skin").setCreativeTab(TFCTabs.TFCMaterials);
		FlatLeather = (new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Leather"));
		Leather = new ItemLeather().setSpecialCraftingType(FlatLeather).setFolder("tools/").setUnlocalizedName("TFC Leather");

		Straw = new ItemTerra().setFolder("plants/").setUnlocalizedName("Straw").setCreativeTab(TFCTabs.TFCMaterials);
		FlatClay = (new ItemFlatGeneric().setFolder("pottery/").setMetaNames(new String[]{"clay flat light", "clay flat dark", "clay flat fire", "clay flat dark fire"}).setUnlocalizedName("Flat Clay"));

		PotteryJug = new ItemPotteryJug().setUnlocalizedName("Jug");
		PotterySmallVessel = new ItemPotterySmallVessel().setUnlocalizedName("Small Vessel");
		PotteryPot = new ItemPotteryPot().setUnlocalizedName("Pot");
		CeramicMold = new ItemPotteryBase().setMetaNames(new String[]{"Clay Mold","Ceramic Mold"}).setUnlocalizedName("Mold");
		PotteryBowl = new ItemPotteryBase().setMetaNames(new String[]{"Clay Bowl","Ceramic Bowl"}).setUnlocalizedName("ClayBowl");
		ClayBall = new ItemClay().setSpecialCraftingType(FlatClay, new ItemStack(FlatClay, 1, 1)).setMetaNames(new String[]{"Clay", "Fire Clay"}).setUnlocalizedName("Clay");
		FireBrick = new ItemPotteryBase().setMetaNames(new String[]{"Clay Fire Brick","Fire Brick"}).setUnlocalizedName("Fire Brick");

		ClayMoldAxe = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Axe","Ceramic Mold Axe",
				"Ceramic Mold Axe Copper","Ceramic Mold Axe Bronze","Ceramic Mold Axe Bismuth Bronze","Ceramic Mold Axe Black Bronze"}).setUnlocalizedName("Axe Mold");
		ClayMoldChisel = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Chisel","Ceramic Mold Chisel",
				"Ceramic Mold Chisel Copper","Ceramic Mold Chisel Bronze","Ceramic Mold Chisel Bismuth Bronze","Ceramic Mold Chisel Black Bronze"}).setUnlocalizedName("Chisel Mold");
		ClayMoldHammer = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Hammer","Ceramic Mold Hammer",
				"Ceramic Mold Hammer Copper","Ceramic Mold Hammer Bronze","Ceramic Mold Hammer Bismuth Bronze","Ceramic Mold Hammer Black Bronze"}).setUnlocalizedName("Hammer Mold");
		ClayMoldHoe = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Hoe","Ceramic Mold Hoe",
				"Ceramic Mold Hoe Copper","Ceramic Mold Hoe Bronze","Ceramic Mold Hoe Bismuth Bronze","Ceramic Mold Hoe Black Bronze"}).setUnlocalizedName("Hoe Mold");
		ClayMoldKnife = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Knife","Ceramic Mold Knife",
				"Ceramic Mold Knife Copper","Ceramic Mold Knife Bronze","Ceramic Mold Knife Bismuth Bronze","Ceramic Mold Knife Black Bronze"}).setUnlocalizedName("Knife Mold");
		ClayMoldMace = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Mace","Ceramic Mold Mace",
				"Ceramic Mold Mace Copper","Ceramic Mold Mace Bronze","Ceramic Mold Mace Bismuth Bronze","Ceramic Mold Mace Black Bronze"}).setUnlocalizedName("Mace Mold");
		ClayMoldPick = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Pick","Ceramic Mold Pick",
				"Ceramic Mold Pick Copper","Ceramic Mold Pick Bronze","Ceramic Mold Pick Bismuth Bronze","Ceramic Mold Pick Black Bronze"}).setUnlocalizedName("Pick Mold");
		ClayMoldProPick = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold ProPick","Ceramic Mold ProPick",
				"Ceramic Mold ProPick Copper","Ceramic Mold ProPick Bronze","Ceramic Mold ProPick Bismuth Bronze","Ceramic Mold ProPick Black Bronze"}).setUnlocalizedName("ProPick Mold");
		ClayMoldSaw = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Saw","Ceramic Mold Saw",
				"Ceramic Mold Saw Copper","Ceramic Mold Saw Bronze","Ceramic Mold Saw Bismuth Bronze","Ceramic Mold Saw Black Bronze"}).setUnlocalizedName("Saw Mold");
		ClayMoldScythe = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Scythe","Ceramic Mold Scythe",
				"Ceramic Mold Scythe Copper","Ceramic Mold Scythe Bronze","Ceramic Mold Scythe Bismuth Bronze","Ceramic Mold Scythe Black Bronze"}).setUnlocalizedName("Scythe Mold");
		ClayMoldShovel = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Shovel","Ceramic Mold Shovel",
				"Ceramic Mold Shovel Copper","Ceramic Mold Shovel Bronze","Ceramic Mold Shovel Bismuth Bronze","Ceramic Mold Shovel Black Bronze"}).setUnlocalizedName("Shovel Mold");
		ClayMoldSword = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Sword","Ceramic Mold Sword",
				"Ceramic Mold Sword Copper","Ceramic Mold Sword Bronze","Ceramic Mold Sword Bismuth Bronze","Ceramic Mold Sword Black Bronze"}).setUnlocalizedName("Sword Mold");
		ClayMoldJavelin = new ItemPotteryMold().setMetaNames(new String[]{"Clay Mold Javelin","Ceramic Mold Javelin",
				"Ceramic Mold Javelin Copper","Ceramic Mold Javelin Bronze","Ceramic Mold Javelin Bismuth Bronze","Ceramic Mold Javelin Black Bronze"}).setUnlocalizedName("Javelin Mold");

		TuyereCopper = new ItemTuyere(40, 0).setUnlocalizedName("Copper Tuyere");
		TuyereBronze = new ItemTuyere(80, 1).setUnlocalizedName("Bronze Tuyere");
		TuyereBlackBronze = new ItemTuyere(80, 1).setUnlocalizedName("Black Bronze Tuyere");
		TuyereBismuthBronze = new ItemTuyere(80, 1).setUnlocalizedName("Bismuth Bronze Tuyere");
		TuyereWroughtIron = new ItemTuyere(120, 2).setUnlocalizedName("Wrought Iron Tuyere");
		TuyereSteel = new ItemTuyere(180, 3).setUnlocalizedName("Steel Tuyere");
		TuyereBlackSteel = new ItemTuyere(260, 4).setUnlocalizedName("Black Steel Tuyere");
		TuyereRedSteel = new ItemTuyere(400, 5).setUnlocalizedName("Red Steel Tuyere");
		TuyereBlueSteel = new ItemTuyere(500, 6).setUnlocalizedName("Blue Steel Tuyere");

		Bloom = new ItemBloom().setFolder("ingots/").setUnlocalizedName("Iron Bloom");
		RawBloom = new ItemBloom().setFolder("ingots/").setUnlocalizedName("Raw Iron Bloom");

		UnknownIngot = new ItemIngot().setUnlocalizedName("Unknown Ingot");
		UnknownUnshaped = new ItemMeltedMetal().setUnlocalizedName("Unknown Unshaped");

		Jute = new ItemTerra().setFolder("plants/").setUnlocalizedName("Jute").setCreativeTab(TFCTabs.TFCMaterials);
		JuteFibre = new ItemTerra().setFolder("plants/").setUnlocalizedName("Jute Fibre").setCreativeTab(TFCTabs.TFCMaterials);

		Items.reeds.setCreativeTab(null);
		Reeds = new ItemReeds().setUnlocalizedName("Reeds").setCreativeTab(TFCTabs.TFCMaterials).setTextureName("reeds");
		MetalLock = new ItemTerra().setUnlocalizedName("Metal Lock").setCreativeTab(TFCTabs.TFCMisc);
		MudBrick = new ItemMudBrick().setUnlocalizedName("Mud Brick").setCreativeTab(TFCTabs.TFCMaterials).setTextureName("Mud Brick Base");

		// Food related items
		SetupFood();

		Fertilizer = new ItemFertilizer().setUnlocalizedName("Fertilizer").setCreativeTab(TFCTabs.TFCMaterials);

		/**Armor Crafting related items*/
		SetupArmor();

		Recipes.Doors = new Item[]{DoorOak, DoorAspen, DoorBirch, DoorChestnut, DoorDouglasFir, 
				DoorHickory, DoorMaple, DoorAsh, DoorPine, DoorSequoia, DoorSpruce, DoorSycamore, 
				DoorWhiteCedar, DoorWhiteElm, DoorWillow, DoorKapok, DoorAcacia};

		Recipes.Axes = new Item[]{SedAxe,IgInAxe,IgExAxe,MMAxe,
				BismuthBronzeAxe,BlackBronzeAxe,
				BlackSteelAxe,BlueSteelAxe,BronzeAxe,CopperAxe,
				WroughtIronAxe,RedSteelAxe,SteelAxe};

		Recipes.Chisels = new Item[]{BismuthBronzeChisel,BlackBronzeChisel,
				BlackSteelChisel,BlueSteelChisel,BronzeChisel,CopperChisel,
				WroughtIronChisel,RedSteelChisel,SteelChisel};

		Recipes.Saws = new Item[]{BismuthBronzeSaw,BlackBronzeSaw,
				BlackSteelSaw,BlueSteelSaw,BronzeSaw,CopperSaw,
				WroughtIronSaw,RedSteelSaw,SteelSaw};

		Recipes.Knives = new Item[]{StoneKnife,BismuthBronzeKnife,BlackBronzeKnife,
				BlackSteelKnife,BlueSteelKnife,BronzeKnife,CopperKnife,
				WroughtIronKnife,RedSteelKnife,SteelKnife};

		Recipes.MeltedMetal = new Item[]{BismuthUnshaped, BismuthBronzeUnshaped,BlackBronzeUnshaped,
				BlackSteelUnshaped,BlueSteelUnshaped,BrassUnshaped,BronzeUnshaped,
				CopperUnshaped,GoldUnshaped,WroughtIronUnshaped,LeadUnshaped,NickelUnshaped,PigIronUnshaped,
				PlatinumUnshaped,RedSteelUnshaped,RoseGoldUnshaped,SilverUnshaped,
				SteelUnshaped,SterlingSilverUnshaped,TinUnshaped,ZincUnshaped, HCSteelUnshaped, WeakSteelUnshaped,
				HCBlackSteelUnshaped, HCBlueSteelUnshaped, HCRedSteelUnshaped, 
				WeakBlueSteelUnshaped, WeakRedSteelUnshaped};

		Recipes.Hammers  = new Item[]{StoneHammer,BismuthBronzeHammer,BlackBronzeHammer,
				BlackSteelHammer,BlueSteelHammer,BronzeHammer,CopperHammer,
				WroughtIronHammer,RedSteelHammer,SteelHammer};

		Recipes.Scythes = new Item[]{BismuthBronzeScythe,BlackBronzeScythe,
				BlackSteelScythe,BlueSteelScythe,BronzeScythe,CopperScythe,
				WroughtIronScythe,RedSteelScythe,SteelScythe};

		Recipes.Spindle = new Item[]{Spindle};

		Recipes.Gems  = new Item[]{GemAgate, GemAmethyst, GemBeryl, GemDiamond, GemEmerald, GemGarnet, 
				GemJade, GemJasper, GemOpal,GemRuby,GemSapphire,GemTopaz,GemTourmaline};

		Swards = new Item[]{BismuthBronzeSword, BlackBronzeSword, BlackSteelSword, BlueSteelSword,
				BronzeSword, CopperSword, WroughtIronSword, RedSteelSword, SteelSword};

		Maces = new Item[]{BismuthBronzeMace, BismuthBronzeMace, BlackBronzeMace, BlackSteelMace,
				BlueSteelMace, BronzeMace, CopperMace, WroughtIronMace, RedSteelMace, SteelMace};

		Javelins = new Item[]{IgInStoneJavelin, IgInStoneJavelin, SedStoneJavelin, IgExStoneJavelin,
				MMStoneJavelin, CopperJavelin, BismuthBronzeJavelin, BronzeJavelin, BlackBronzeJavelin,
				WroughtIronJavelin, SteelJavelin, BlackSteelJavelin, BlueSteelJavelin, RedSteelJavelin};

		StoneTools = new Item[]{IgInShovel, IgInAxe, IgInHoe, SedShovel, SedAxe,
				SedHoe, IgExShovel, IgExAxe, IgExHoe, MMShovel, MMAxe, MMHoe};

		ProPicks = new Item[]{ProPickBismuthBronze, ProPickBlackBronze, ProPickBlackSteel, ProPickBlueSteel,
				ProPickBronze, ProPickCopper, ProPickIron, ProPickRedSteel, ProPickSteel};

		Picks = new Item[]{BismuthBronzePick, BlackBronzePick, BlackSteelPick, BlueSteelPick, BronzePick,
				CopperPick, WroughtIronPick, RedSteelPick, SteelPick};

		Shovels = new Item[]{IgInShovel, IgExShovel, SedShovel, MMShovel, BismuthBronzeShovel, BlackBronzeShovel,
				BlackSteelShovel, BlueSteelShovel, BronzeShovel, CopperShovel, WroughtIronShovel, RedSteelShovel,
				SteelShovel};

		Hoes = new Item[]{IgInHoe, IgExHoe, SedHoe, MMHoe, BismuthBronzeHoe, BlackBronzeHoe, BlackSteelHoe,
				BlueSteelHoe, BronzeHoe, CopperHoe, WroughtIronHoe, RedSteelHoe, SteelHoe};

		((TFCTabs) TFCTabs.TFCBuilding).setTabIconItemStack(new ItemStack(TFCBlocks.StoneSedBrick));
		((TFCTabs) TFCTabs.TFCDecoration).setTabIconItemStack(new ItemStack(TFCBlocks.Flora));
		((TFCTabs) TFCTabs.TFCDevices).setTabIconItem(SluiceItem);
		((TFCTabs) TFCTabs.TFCPottery).setTabIconItemStack(new ItemStack(PotteryJug, 1, 1));
		((TFCTabs) TFCTabs.TFCMisc).setTabIconItem(BlueSteelBucketLava);
		((TFCTabs) TFCTabs.TFCFoods).setTabIconItem(RedApple);
		((TFCTabs) TFCTabs.TFCTools).setTabIconItem(RedSteelAxe);
		((TFCTabs) TFCTabs.TFCWeapons).setTabIconItem(BismuthBronzeSword);
		((TFCTabs) TFCTabs.TFCArmor).setTabIconItem(BronzeHelmet);
		((TFCTabs) TFCTabs.TFCMaterials).setTabIconItem(BlueSteelIngot);

		registerMetals();

		registerItems();
		System.out.println(new StringBuilder().append("[TFC] Done Loading Items").toString());
	}

	/**
	 * 
	 */
	private static void SetupFood()
	{
		FoodList = new ArrayList<Item>();

		Egg = new ItemEgg().setSize(EnumSize.SMALL).setUnlocalizedName("egg").setTextureName("egg").setCreativeTab(TFCTabs.TFCFoods);

		//Tastes are Sweet, Sour, Salty, Bitter, Umami

		//Proteins
		porkchopRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Porkchop");
		fishRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, true).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Fish");
		beefRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, false, false).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Beef");
		chickenRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Chicken");
		Soybean = new ItemFoodTFC(EnumFoodGroup.Protein, 10, 0, 0, 0, 40, true).setUnlocalizedName("Soybeans");
		EggCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 25).setDecayRate(2.5f).setUnlocalizedName("Egg Cooked");
		calamariRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 20, 0, 35, false, false).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setDecayRate(4.0f).setUnlocalizedName("Calamari");
		muttonRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Mutton");
		venisonRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, false, false).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Venison");
		horseMeatRaw = new ItemFoodMeat(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false).setDecayRate(2.5f).setCanSmoke().setHasCookedIcon().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("HorseMeat");

		//Dairy
		Cheese = new ItemFoodTFC(EnumFoodGroup.Dairy, 0, 10, 20, 0, 35).setDecayRate(0.5f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Cheese");

		//Grains
		WheatGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setDecayRate(0.5f).setUnlocalizedName("Wheat Grain");
		BarleyGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 10, 20).setDecayRate(0.5f).setUnlocalizedName("Barley Grain");
		OatGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setDecayRate(0.5f).setUnlocalizedName("Oat Grain");
		RyeGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 5, 20).setDecayRate(0.5f).setUnlocalizedName("Rye Grain");
		RiceGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setDecayRate(0.5f).setUnlocalizedName("Rice Grain");
		MaizeEar = new ItemFoodTFC(EnumFoodGroup.Grain, 25, 0, 0, 5, 20, true).setUnlocalizedName("Maize Ear");

		WheatWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setFolder("food/").setUnlocalizedName("Wheat Whole");
		BarleyWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 10, 20, false, false).setFolder("food/").setUnlocalizedName("Barley Whole");
		OatWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setFolder("food/").setUnlocalizedName("Oat Whole");
		RyeWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 5, 20, false, false).setFolder("food/").setUnlocalizedName("Rye Whole");
		RiceWhole = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setFolder("food/").setUnlocalizedName("Rice Whole");

		WheatGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setFolder("food/").setUnlocalizedName("Wheat Ground");
		BarleyGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setFolder("food/").setUnlocalizedName("Barley Ground");
		OatGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setFolder("food/").setUnlocalizedName("Oat Ground");
		RyeGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, false, false).setFolder("food/").setUnlocalizedName("Rye Ground");
		RiceGround = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setFolder("food/").setUnlocalizedName("Rice Ground");
		CornmealGround = new ItemFoodTFC(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, false, false).setFolder("food/").setUnlocalizedName("Cornmeal Ground");

		WheatDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setUnlocalizedName("Wheat Dough");
		BarleyDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20, false, false).setUnlocalizedName("Barley Dough");
		OatDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setUnlocalizedName("Oat Dough");
		RyeDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 0, 20, false, false).setUnlocalizedName("Rye Dough");
		RiceDough = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20, false, false).setUnlocalizedName("Rice Dough");
		CornmealDough = new ItemFoodTFC(EnumFoodGroup.Grain, 25, 0, 0, 0, 20, false, false).setUnlocalizedName("Cornmeal Dough");

		WheatBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20).setUnlocalizedName("Wheat Bread");
		BarleyBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setUnlocalizedName("Barley Bread");
		OatBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20).setUnlocalizedName("Oat Bread");
		RyeBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 0, 20).setUnlocalizedName("Rye Bread");
		RiceBread = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 0, 20).setUnlocalizedName("Rice Bread");
		CornBread = new ItemFoodTFC(EnumFoodGroup.Grain, 25, 0, 0, 0, 20).setUnlocalizedName("Corn Bread");

		//Vegetables
		Tomato = new ItemFoodTFC(EnumFoodGroup.Vegetable, 30, 5, 0, 0, 50, true).setUnlocalizedName("Tomato");
		Potato = new ItemFoodTFC(EnumFoodGroup.Vegetable, 0, 0, 10, 15, 20, true).setUnlocalizedName("Potato");
		Onion = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 25, 0, 0, 20, true)
		{
			@Override
			public void registerIcons(IIconRegister registerer)
			{
				super.registerIcons(registerer);
				this.hasSubtypes = true;
				this.MetaIcons = new IIcon[2];
				this.MetaIcons[0] = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
				this.MetaIcons[1] = registerer.registerIcon(Reference.ModID + ":" + this.textureFolder + "Rutabaga");
			}

			@Override
			public IIcon getIconFromDamage(int i)
			{
				if(i == 1)
					return this.MetaIcons[1];
				return super.getIconFromDamage(i);
			}
		}.setUnlocalizedName(TFCOptions.iDontLikeOnions ? "Rutabaga" : "Onion");
		Cabbage = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 30, true).setUnlocalizedName("Cabbage");
		Garlic = new ItemFoodTFC(EnumFoodGroup.Vegetable, 0, 0, 0, 10, 20, true).setUnlocalizedName("Garlic");
		Carrot = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setUnlocalizedName("Carrot");
		Greenbeans = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setUnlocalizedName("Greenbeans");
		GreenBellPepper = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 20, true).setUnlocalizedName("Green Bell Pepper");
		YellowBellPepper = new ItemFoodTFC(EnumFoodGroup.Vegetable, 15, 0, 0, 0, 20, true).setUnlocalizedName("Yellow Bell Pepper");
		RedBellPepper = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setUnlocalizedName("Red Bell Pepper");
		Squash = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setUnlocalizedName("Squash");
		SeaWeed = new ItemFoodTFC(EnumFoodGroup.Vegetable, 0, 0, 10, 10, 10, true).setUnlocalizedName("Sea Weed");
		Sugar = new ItemFoodTFC(EnumFoodGroup.None, 30, 0, 0, 0, 0, true).setDecayRate(0.01f).setUnlocalizedName("Sugar");

		//Fruit are in the foodID range of 50,000
		RedApple = new ItemFoodTFC(EnumFoodGroup.Fruit, 25, 5, 0, 10, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[0]);
		Banana = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 5, 0, 0, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[1]);
		Orange = new ItemFoodTFC(EnumFoodGroup.Fruit, 50, 30, 0, 10, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[2]);
		GreenApple = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 15, 0, 10, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[3]);
		Lemon = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 50, 0, 10, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[4]);
		Olive = new ItemFoodTFC(EnumFoodGroup.Fruit, 10, 0, 3, 10, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[5]);
		Cherry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 5, 0, 0, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[6]);
		Peach = new ItemFoodTFC(EnumFoodGroup.Fruit, 25, 10, 0, 0, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[7]);
		Plum = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 15, 0, 0, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[8]);

		WintergreenBerry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 0, 0, 20, 0).setDecayRate(2.0f).setUnlocalizedName("Wintergreen Berry");
		Blueberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 20, 0, 0, 0).setDecayRate(2.0f).setUnlocalizedName("Blueberry");
		Raspberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 35, 15, 0, 5, 0).setDecayRate(2.0f).setUnlocalizedName("Raspberry");
		Strawberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 5, 0, 5, 0).setDecayRate(2.0f).setUnlocalizedName("Strawberry");
		Blackberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 30, 0, 0, 0).setDecayRate(2.0f).setUnlocalizedName("Blackberry");
		Bunchberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 5, 0, 0, 0).setDecayRate(2.0f).setUnlocalizedName("Bunchberry");
		Cranberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 5, 0, 45, 0).setDecayRate(2.0f).setUnlocalizedName("Cranberry");
		Snowberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 10, 0, 0, 90, 0).setDecayRate(2.0f).setUnlocalizedName("Snowberry");
		Elderberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 40, 0, 10, 0).setDecayRate(2.0f).setUnlocalizedName("Elderberry");
		Gooseberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 40, 0, 0, 0).setDecayRate(2.0f).setUnlocalizedName("Gooseberry");
		Cloudberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 40, 0, 30, 0).setDecayRate(2.0f).setUnlocalizedName("Cloudberry");

		Sandwich = new ItemSandwich().setUnlocalizedName("Sandwich");
		Salad = new ItemSalad().setUnlocalizedName("Salad");
		Soup = new ItemSoup().setUnlocalizedName("Soup");

		Sugarcane = new ItemFoodTFC(EnumFoodGroup.None, 30, 0, 0, 0, 0, false, false).setDecayRate(0.75f).setFolder("plants/").setUnlocalizedName("Sugarcane");
		Hemp = new ItemTerra().setFolder("plants/").setUnlocalizedName("Hemp");

		SeedsWheat = new ItemCustomSeeds(0).setUnlocalizedName("Seeds Wheat");
		SeedsMaize = new ItemCustomSeeds(1).setUnlocalizedName("Seeds Maize");
		SeedsTomato = new ItemCustomSeeds(2).setUnlocalizedName("Seeds Tomato");
		SeedsBarley = new ItemCustomSeeds(3).setUnlocalizedName("Seeds Barley");
		SeedsRye = new ItemCustomSeeds(4).setUnlocalizedName("Seeds Rye");
		SeedsOat = new ItemCustomSeeds(5).setUnlocalizedName("Seeds Oat");
		SeedsRice = new ItemCustomSeeds(6).setUnlocalizedName("Seeds Rice");
		SeedsPotato = new ItemCustomSeeds(7).setUnlocalizedName("Seeds Potato");
		SeedsOnion = new ItemCustomSeeds(8).setUnlocalizedName(TFCOptions.iDontLikeOnions?"Seeds Rutabaga":"Seeds Onion");
		SeedsCabbage = new ItemCustomSeeds(9).setUnlocalizedName("Seeds Cabbage");
		SeedsGarlic = new ItemCustomSeeds(10).setUnlocalizedName("Seeds Garlic");
		SeedsCarrot = new ItemCustomSeeds(11).setUnlocalizedName("Seeds Carrot");
		SeedsYellowBellPepper = new ItemCustomSeeds(12).setUnlocalizedName("Seeds Yellow Bell Pepper");
		SeedsRedBellPepper = new ItemCustomSeeds(13).setUnlocalizedName("Seeds Red Bell Pepper");
		SeedsSoybean = new ItemCustomSeeds(14).setUnlocalizedName("Seeds Soybean");
		SeedsGreenbean = new ItemCustomSeeds(15).setUnlocalizedName("Seeds Greenbean");
		SeedsSquash = new ItemCustomSeeds(16).setUnlocalizedName("Seeds Squash");
		SeedsJute = new ItemCustomSeeds(17).setUnlocalizedName("Seeds Jute");
		SeedsSugarcane = new ItemCustomSeeds(18).setUnlocalizedName("Seeds Sugarcane");
		//SeedsHemp = new ItemCustomSeeds(22).setUnlocalizedName("Seeds Hemp");



		FruitTreeSapling = new ItemFruitTreeSapling().setUnlocalizedName("FruitSapling");

		//mushroom is a food now, with foodID 61
		//pumpkin is a food now, id = 61
		//melon is a food, not currently obtainable. id = 62. See ItemFoodBlock
		WintergreenLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Wintergreen Leaf");
		BlueberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Blueberry Leaf");
		RaspberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Raspberry Leaf");
		StrawberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Strawberry Leaf");
		BlackberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Blackberry Leaf");
		BunchberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Bunchberry Leaf");
		CranberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Cranberry Leaf");
		SnowberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Snowberry Leaf");
		ElderberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Elderberry Leaf");
		GooseberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Gooseberry Leaf");
		CloudberryLeaf = new ItemTerra().setFolder("plants/").setUnlocalizedName("Cloudberry Leaf");
	}

	private static void registerMetals()
	{
		Global.BISMUTH = new Metal("Bismuth", BismuthUnshaped, BismuthIngot);
		Global.BISMUTHBRONZE = new Metal("Bismuth Bronze", BismuthBronzeUnshaped, BismuthBronzeIngot);
		Global.BLACKBRONZE = new Metal("Black Bronze", BlackBronzeUnshaped, BlackBronzeIngot);
		Global.BLACKSTEEL = new Metal("Black Steel", BlackSteelUnshaped, BlackSteelIngot);
		Global.BLUESTEEL = new Metal("Blue Steel", BlueSteelUnshaped, BlueSteelIngot);
		Global.BRASS = new Metal("Brass", BrassUnshaped, BrassIngot);
		Global.BRONZE = new Metal("Bronze", BronzeUnshaped, BronzeIngot);
		Global.COPPER = new Metal("Copper", CopperUnshaped, CopperIngot);
		Global.GOLD = new Metal("Gold", GoldUnshaped, GoldIngot);
		Global.WROUGHTIRON = new Metal("Wrought Iron", WroughtIronUnshaped, WroughtIronIngot);
		Global.LEAD = new Metal("Lead", LeadUnshaped, LeadIngot);
		Global.NICKEL = new Metal("Nickel", NickelUnshaped, NickelIngot);
		Global.PIGIRON = new Metal("Pig Iron", PigIronUnshaped, PigIronIngot);
		Global.PLATINUM = new Metal("Platinum", PlatinumUnshaped, PlatinumIngot);
		Global.REDSTEEL = new Metal("Red Steel", RedSteelUnshaped, RedSteelIngot);
		Global.ROSEGOLD = new Metal("Rose Gold", RoseGoldUnshaped, RoseGoldIngot);
		Global.SILVER = new Metal("Silver", SilverUnshaped, SilverIngot);
		Global.STEEL = new Metal("Steel", SteelUnshaped, SteelIngot);
		Global.STERLINGSILVER = new Metal("Sterling Silver", SterlingSilverUnshaped, SterlingSilverIngot);
		Global.TIN = new Metal("Tin", TinUnshaped, TinIngot);
		Global.ZINC = new Metal("Zinc", ZincUnshaped, ZincIngot);
		Global.WEAKSTEEL = new Metal("Weak Steel", WeakSteelUnshaped, WeakSteelIngot);
		Global.HCBLACKSTEEL = new Metal("HC Black Steel", HCBlackSteelUnshaped, HCBlackSteelIngot);
		Global.WEAKREDSTEEL = new Metal("Weak Red Steel", WeakRedSteelUnshaped, WeakRedSteelIngot);
		Global.HCREDSTEEL = new Metal("HC Red Steel", HCRedSteelUnshaped, HCRedSteelIngot);
		Global.WEAKBLUESTEEL = new Metal("Weak Blue Steel", WeakBlueSteelUnshaped, WeakBlueSteelIngot);
		Global.HCBLUESTEEL = new Metal("HC Blue Steel", HCBlueSteelUnshaped, HCBlueSteelIngot);
		Global.UNKNOWN = new Metal("Unknown", UnknownUnshaped, UnknownIngot, false);

		MetalRegistry.instance.addMetal(Global.BISMUTH, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BISMUTHBRONZE, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BLACKBRONZE, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BLACKSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.BLUESTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.BRASS, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.BRONZE, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.COPPER, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.GOLD, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.WROUGHTIRON, Alloy.EnumTier.TierIII);
		MetalRegistry.instance.addMetal(Global.LEAD, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.NICKEL, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.PIGIRON, Alloy.EnumTier.TierIV);
		MetalRegistry.instance.addMetal(Global.PLATINUM, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.REDSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.ROSEGOLD, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.SILVER, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.STEEL, Alloy.EnumTier.TierIV);
		MetalRegistry.instance.addMetal(Global.STERLINGSILVER, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.TIN, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.ZINC, Alloy.EnumTier.TierI);
		MetalRegistry.instance.addMetal(Global.WEAKSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.HCBLACKSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.WEAKREDSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.HCREDSTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.WEAKBLUESTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.HCBLUESTEEL, Alloy.EnumTier.TierV);
		MetalRegistry.instance.addMetal(Global.UNKNOWN, Alloy.EnumTier.TierI);

		/**
		 * Added the +-0.01 tolerance to hopefully negate most rounding errors
		 */
		Alloy Bronze = new Alloy(Global.BRONZE, Alloy.EnumTier.TierI);
		Bronze.addIngred(Global.COPPER, 87.99f, 92.01f);
		Bronze.addIngred(Global.TIN, 7.99f, 12.01f);
		AlloyManager.instance.addAlloy(Bronze);

		Alloy Brass = new Alloy(Global.BRASS, Alloy.EnumTier.TierI);
		Brass.addIngred(Global.COPPER, 87.99f, 92.01f);
		Brass.addIngred(Global.ZINC, 7.99f, 12.01f);
		AlloyManager.instance.addAlloy(Brass);

		Alloy RoseGold = new Alloy(Global.ROSEGOLD, Alloy.EnumTier.TierI);
		RoseGold.addIngred(Global.GOLD, 69.99f, 85.01f);
		RoseGold.addIngred(Global.COPPER, 14.99f, 30.01f);
		AlloyManager.instance.addAlloy(RoseGold);

		Alloy BlackBronze = new Alloy(Global.BLACKBRONZE, Alloy.EnumTier.TierI);
		BlackBronze.addIngred(Global.GOLD, 9.99f, 25.01f);
		BlackBronze.addIngred(Global.COPPER, 49.99f, 70.01f);
		BlackBronze.addIngred(Global.SILVER, 9.99f, 25.01f);
		AlloyManager.instance.addAlloy(BlackBronze);

		Alloy BismuthBronze = new Alloy(Global.BISMUTHBRONZE, Alloy.EnumTier.TierI);
		BismuthBronze.addIngred(Global.ZINC, 19.99f, 30.01f);
		BismuthBronze.addIngred(Global.COPPER, 49.99f, 70.01f);
		BismuthBronze.addIngred(Global.BISMUTH, 9.99f, 20.01f);
		AlloyManager.instance.addAlloy(BismuthBronze);

		Alloy SterlingSilver = new Alloy(Global.STERLINGSILVER, Alloy.EnumTier.TierI);
		SterlingSilver.addIngred(Global.SILVER, 59.99f, 80.01f);
		SterlingSilver.addIngred(Global.COPPER, 19.99f, 40.01f);
		AlloyManager.instance.addAlloy(SterlingSilver);

		Alloy WeakSteel = new Alloy(Global.WEAKSTEEL, Alloy.EnumTier.TierIII);
		WeakSteel.addIngred(Global.STEEL, 49.99f, 70.01f);
		WeakSteel.addIngred(Global.NICKEL, 14.99f, 25.01f);
		WeakSteel.addIngred(Global.BLACKBRONZE, 14.99f, 25.01f);
		AlloyManager.instance.addAlloy(WeakSteel);

		Alloy WeakRedSteel = new Alloy(Global.WEAKREDSTEEL, Alloy.EnumTier.TierIII);
		WeakRedSteel.addIngred(Global.BLACKSTEEL, 49.99f, 60.01f);
		WeakRedSteel.addIngred(Global.ROSEGOLD, 9.99f, 15.01f);
		WeakRedSteel.addIngred(Global.BRASS, 9.99f, 15.01f);
		WeakRedSteel.addIngred(Global.STEEL, 19.99f, 25.01f);
		AlloyManager.instance.addAlloy(WeakRedSteel);

		Alloy WeakBlueSteel = new Alloy(Global.WEAKBLUESTEEL, Alloy.EnumTier.TierIII);
		WeakBlueSteel.addIngred(Global.BLACKSTEEL, 49.99f, 60.01f);
		WeakBlueSteel.addIngred(Global.BISMUTHBRONZE, 9.99f, 15.01f);
		WeakBlueSteel.addIngred(Global.STERLINGSILVER, 9.99f, 15.01f);
		WeakBlueSteel.addIngred(Global.STEEL, 19.99f, 25.01f);
		AlloyManager.instance.addAlloy(WeakBlueSteel);
	}

	public static void SetupArmor()
	{
		String[] Names = {"Bismuth Bronze", "Black Bronze", "Black Steel", "Blue Steel", "Bronze", "Copper", "Wrought Iron", "Red Steel", "Steel"};
		String[] NamesNSO = {"Brass", "Gold", "Lead", "Nickel", "Pig Iron", "Platinum", "Silver", "Sterling Silver"};
		CommonProxy proxy = TerraFirmaCraft.proxy;
		int i = 0;

		BismuthSheet = 			(((ItemMetalSheet) new ItemMetalSheet(0).setUnlocalizedName("Bismuth Sheet")).setMetal("Bismuth", 200));
		BismuthBronzeSheet = 	(((ItemMetalSheet) new ItemMetalSheet(1).setUnlocalizedName("Bismuth Bronze Sheet")).setMetal("Bismuth Bronze", 200));
		BlackBronzeSheet = 		(((ItemMetalSheet) new ItemMetalSheet(2).setUnlocalizedName("Black Bronze Sheet")).setMetal("Black Bronze", 200));
		BlackSteelSheet = 		(((ItemMetalSheet) new ItemMetalSheet(3).setUnlocalizedName("Black Steel Sheet")).setMetal("Black Steel", 200));
		BlueSteelSheet = 		(((ItemMetalSheet) new ItemMetalSheet(4).setUnlocalizedName("Blue Steel Sheet")).setMetal("Blue Steel", 200));
		BronzeSheet = 			(((ItemMetalSheet) new ItemMetalSheet(6).setUnlocalizedName("Bronze Sheet")).setMetal("Bronze", 200));
		CopperSheet = 			(((ItemMetalSheet) new ItemMetalSheet(7).setUnlocalizedName("Copper Sheet")).setMetal("Copper", 200));
		WroughtIronSheet = 		(((ItemMetalSheet) new ItemMetalSheet(9).setUnlocalizedName("Wrought Iron Sheet")).setMetal("Wrought Iron", 200));
		RedSteelSheet = 		(((ItemMetalSheet) new ItemMetalSheet(14).setUnlocalizedName("Red Steel Sheet")).setMetal("Red Steel", 200));
		RoseGoldSheet = 		(((ItemMetalSheet) new ItemMetalSheet(15).setUnlocalizedName("Rose Gold Sheet")).setMetal("Rose Gold", 200));
		SteelSheet = 			(((ItemMetalSheet) new ItemMetalSheet(17).setUnlocalizedName("Steel Sheet")).setMetal("Steel", 200));
		TinSheet = 				(((ItemMetalSheet) new ItemMetalSheet(19).setUnlocalizedName("Tin Sheet")).setMetal("Tin", 200));
		ZincSheet = 			(((ItemMetalSheet) new ItemMetalSheet(20).setUnlocalizedName("Zinc Sheet")).setMetal("Zinc", 200));

		BismuthSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(0).setUnlocalizedName("Bismuth Double Sheet")).setMetal("Bismuth", 400));
		BismuthBronzeSheet2x = 	(((ItemMetalSheet2x) new ItemMetalSheet2x(1).setUnlocalizedName("Bismuth Bronze Double Sheet")).setMetal("Bismuth Bronze", 400));
		BlackBronzeSheet2x = 	(((ItemMetalSheet2x) new ItemMetalSheet2x(2).setUnlocalizedName("Black Bronze Double Sheet")).setMetal("Black Bronze", 400));
		BlackSteelSheet2x = 	(((ItemMetalSheet2x) new ItemMetalSheet2x(3).setUnlocalizedName("Black Steel Double Sheet")).setMetal("Black Steel", 400));
		BlueSteelSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(4).setUnlocalizedName("Blue Steel Double Sheet")).setMetal("Blue Steel", 400));
		BronzeSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(6).setUnlocalizedName("Bronze Double Sheet")).setMetal("Bronze", 400));
		CopperSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(7).setUnlocalizedName("Copper Double Sheet")).setMetal("Copper", 400));
		WroughtIronSheet2x = 	(((ItemMetalSheet2x) new ItemMetalSheet2x(9).setUnlocalizedName("Wrought Iron Double Sheet")).setMetal("Wrought Iron", 400));
		RedSteelSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(14).setUnlocalizedName("Red Steel Double Sheet")).setMetal("Red Steel", 400));
		RoseGoldSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(15).setUnlocalizedName("Rose Gold Double Sheet")).setMetal("Rose Gold", 400));
		SteelSheet2x = 			(((ItemMetalSheet2x) new ItemMetalSheet2x(17).setUnlocalizedName("Steel Double Sheet")).setMetal("Steel", 400));
		TinSheet2x = 			(((ItemMetalSheet2x) new ItemMetalSheet2x(19).setUnlocalizedName("Tin Double Sheet")).setMetal("Tin", 400));
		ZincSheet2x = 			(((ItemMetalSheet2x) new ItemMetalSheet2x(20).setUnlocalizedName("Zinc Double Sheet")).setMetal("Zinc", 400));

		i = 0;
		BrassSheet = 			new ItemMetalSheet(5).setMetal("Brass", 200).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		GoldSheet = 			new ItemMetalSheet(8).setMetal("Gold", 200).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		LeadSheet = 			new ItemMetalSheet(10).setMetal("Lead", 200).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		NickelSheet = 			new ItemMetalSheet(11).setMetal("Nickel", 200).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		PigIronSheet = 			new ItemMetalSheet(12).setMetal("Pig Iron", 200).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		PlatinumSheet = 		new ItemMetalSheet(13).setMetal("Platinum", 200).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		SilverSheet = 			new ItemMetalSheet(16).setMetal("Silver", 200).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		SterlingSilverSheet = 	new ItemMetalSheet(18).setMetal("Sterling Silver", 200).setUnlocalizedName(NamesNSO[i++]+" Sheet");

		i = 0;
		BrassSheet2x = 			new ItemMetalSheet2x(5).setMetal("Brass", 400).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		GoldSheet2x = 			new ItemMetalSheet2x(8).setMetal("Gold", 400).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		LeadSheet2x = 			new ItemMetalSheet2x(10).setMetal("Lead", 400).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		NickelSheet2x = 		new ItemMetalSheet2x(1).setMetal("Nickel", 400).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		PigIronSheet2x = 		new ItemMetalSheet2x(12).setMetal("Pig Iron", 400).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		PlatinumSheet2x = 		new ItemMetalSheet2x(13).setMetal("Platinum", 400).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		SilverSheet2x = 		new ItemMetalSheet2x(16).setMetal("Silver", 400).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		SterlingSilverSheet2x = new ItemMetalSheet2x(18).setMetal("Sterling Silver", 400).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");

		i = 0;
		BismuthBronzeUnfinishedBoots = 	(new ItemUnfinishedArmor().setMetal("Bismuth Bronze", 3).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		BlackBronzeUnfinishedBoots = 	(new ItemUnfinishedArmor().setMetal("Black Bronze", 3).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		BlackSteelUnfinishedBoots = 	(new ItemUnfinishedArmor().setMetal("Black Steel", 3).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		BlueSteelUnfinishedBoots = 		(new ItemUnfinishedArmor().setMetal("Blue Steel", 3).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		BronzeUnfinishedBoots = 		(new ItemUnfinishedArmor().setMetal("Bronze", 3).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		CopperUnfinishedBoots = 		(new ItemUnfinishedArmor().setMetal("Copper", 3).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		WroughtIronUnfinishedBoots = 	(new ItemUnfinishedArmor().setMetal("Wrought Iron", 3).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		RedSteelUnfinishedBoots = 		(new ItemUnfinishedArmor().setMetal("Red Steel", 3).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		SteelUnfinishedBoots = 			(new ItemUnfinishedArmor().setMetal("Steel", 3).setUnlocalizedName(Names[i]+" Unfinished Boots"));

		i = 0;
		BismuthBronzeBoots = 	(new ItemTFCArmor(Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 3, 50,0).setUnlocalizedName(Names[i]+" Boots")); i++;
		BlackBronzeBoots = 		(new ItemTFCArmor(Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 3, 50,0).setUnlocalizedName(Names[i]+" Boots")); i++;
		BlackSteelBoots = 		(new ItemTFCArmor(Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 3, 50,0).setUnlocalizedName(Names[i]+" Boots")); i++;
		BlueSteelBoots = 		(new ItemTFCArmor(Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 3, 50,0).setUnlocalizedName(Names[i]+" Boots")); i++;
		BronzeBoots = 			(new ItemTFCArmor(Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 3, 50,0).setUnlocalizedName(Names[i]+" Boots")); i++;
		CopperBoots = 			(new ItemTFCArmor(Armor.CopperPlate, proxy.getArmorRenderID("copper"), 3, 50,0).setUnlocalizedName(Names[i]+" Boots")); i++;
		WroughtIronBoots = 		(new ItemTFCArmor(Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 3, 50,0).setUnlocalizedName(Names[i]+" Boots")); i++;
		RedSteelBoots = 		(new ItemTFCArmor(Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 3, 50,0).setUnlocalizedName(Names[i]+" Boots")); i++;
		SteelBoots = 			(new ItemTFCArmor(Armor.SteelPlate, proxy.getArmorRenderID("steel"), 3, 50,0).setUnlocalizedName(Names[i]+" Boots"));

		i = 0;
		BismuthBronzeUnfinishedGreaves = 	(new ItemUnfinishedArmor().setMetal("Bismuth Bronze", 2).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		BlackBronzeUnfinishedGreaves = 		(new ItemUnfinishedArmor().setMetal("Black Bronze", 2).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		BlackSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor().setMetal("Black Steel", 2).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		BlueSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor().setMetal("Blue Steel", 2).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		BronzeUnfinishedGreaves = 			(new ItemUnfinishedArmor().setMetal("Bronze", 2).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		CopperUnfinishedGreaves = 			(new ItemUnfinishedArmor().setMetal("Copper", 2).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		WroughtIronUnfinishedGreaves = 		(new ItemUnfinishedArmor().setMetal("Wrought Iron", 2).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		RedSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor().setMetal("Red Steel", 2).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		SteelUnfinishedGreaves = 			(new ItemUnfinishedArmor().setMetal("Steel", 2).setUnlocalizedName(Names[i]+" Unfinished Greaves"));

		i = 0;
		BismuthBronzeGreaves = 	(new ItemTFCArmor(Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 2, 50,1).setUnlocalizedName(Names[i]+" Greaves")); i++;
		BlackBronzeGreaves = 	(new ItemTFCArmor(Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 2, 50,1).setUnlocalizedName(Names[i]+" Greaves")); i++;
		BlackSteelGreaves = 	(new ItemTFCArmor(Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 2, 50,1).setUnlocalizedName(Names[i]+" Greaves")); i++;
		BlueSteelGreaves = 		(new ItemTFCArmor(Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 2, 50,1).setUnlocalizedName(Names[i]+" Greaves")); i++;
		BronzeGreaves = 		(new ItemTFCArmor(Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 2, 50,1).setUnlocalizedName(Names[i]+" Greaves")); i++;
		CopperGreaves = 		(new ItemTFCArmor(Armor.CopperPlate, proxy.getArmorRenderID("copper"), 2, 50,1).setUnlocalizedName(Names[i]+" Greaves")); i++;
		WroughtIronGreaves =	(new ItemTFCArmor(Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 2, 50,1).setUnlocalizedName(Names[i]+" Greaves")); i++;
		RedSteelGreaves = 		(new ItemTFCArmor(Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 2, 50,1).setUnlocalizedName(Names[i]+" Greaves")); i++;
		SteelGreaves = 			(new ItemTFCArmor(Armor.SteelPlate, proxy.getArmorRenderID("steel"), 2, 50,1).setUnlocalizedName(Names[i]+" Greaves"));

		i = 0;
		BismuthBronzeUnfinishedChestplate = (new ItemUnfinishedArmor().setMetal("Bismuth Bronze", 1).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		BlackBronzeUnfinishedChestplate = 	(new ItemUnfinishedArmor().setMetal("Black Bronze", 1).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		BlackSteelUnfinishedChestplate = 	(new ItemUnfinishedArmor().setMetal("Black Steel", 1).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		BlueSteelUnfinishedChestplate = 	(new ItemUnfinishedArmor().setMetal("Blue Steel", 1).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		BronzeUnfinishedChestplate = 		(new ItemUnfinishedArmor().setMetal("Bronze", 1).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		CopperUnfinishedChestplate = 		(new ItemUnfinishedArmor().setMetal("Copper", 1).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		WroughtIronUnfinishedChestplate = 	(new ItemUnfinishedArmor().setMetal("Wrought Iron", 1).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		RedSteelUnfinishedChestplate = 		(new ItemUnfinishedArmor().setMetal("Red Steel", 1).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		SteelUnfinishedChestplate = 		(new ItemUnfinishedArmor().setMetal("Steel", 1).setUnlocalizedName(Names[i]+" Unfinished Chestplate"));

		i = 0;
		BismuthBronzeChestplate =	(new ItemTFCArmor(Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 1, 50,2).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		BlackBronzeChestplate = 	(new ItemTFCArmor(Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 1, 50,2).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		BlackSteelChestplate = 		(new ItemTFCArmor(Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 1, 50,2).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		BlueSteelChestplate = 		(new ItemTFCArmor(Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 1, 50,2).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		BronzeChestplate = 			(new ItemTFCArmor(Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 1, 50,2).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		CopperChestplate = 			(new ItemTFCArmor(Armor.CopperPlate, proxy.getArmorRenderID("copper"), 1, 50,2).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		WroughtIronChestplate = 	(new ItemTFCArmor(Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 1, 50,2).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		RedSteelChestplate = 		(new ItemTFCArmor(Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 1, 50,2).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		SteelChestplate = 			(new ItemTFCArmor(Armor.SteelPlate, proxy.getArmorRenderID("steel"), 1, 50,2).setUnlocalizedName(Names[i]+" Chestplate"));

		i = 0;
		BismuthBronzeUnfinishedHelmet = 	(new ItemUnfinishedArmor().setMetal("Bismuth Bronze", 0).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		BlackBronzeUnfinishedHelmet = 		(new ItemUnfinishedArmor().setMetal("Black Bronze", 0).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		BlackSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor().setMetal("Black Steel", 0).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		BlueSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor().setMetal("Blue Steel", 0).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		BronzeUnfinishedHelmet = 			(new ItemUnfinishedArmor().setMetal("Bronze", 0).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		CopperUnfinishedHelmet = 			(new ItemUnfinishedArmor().setMetal("Copper", 0).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		WroughtIronUnfinishedHelmet = 		(new ItemUnfinishedArmor().setMetal("Wrought Iron", 0).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		RedSteelUnfinishedHelmet = 			(new ItemUnfinishedArmor().setMetal("Red Steel", 0).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		SteelUnfinishedHelmet = 			(new ItemUnfinishedArmor().setMetal("Steel", 0).setUnlocalizedName(Names[i]+" Unfinished Helmet"));

		i = 0;
		BismuthBronzeHelmet = 	(new ItemTFCArmor(Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 0, 50,3).setUnlocalizedName(Names[i]+" Helmet")); i++;
		BlackBronzeHelmet = 	(new ItemTFCArmor(Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 0, 50,3).setUnlocalizedName(Names[i]+" Helmet")); i++;
		BlackSteelHelmet = 		(new ItemTFCArmor(Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 0, 50,3).setUnlocalizedName(Names[i]+" Helmet")); i++;
		BlueSteelHelmet = 		(new ItemTFCArmor(Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 0, 50,3).setUnlocalizedName(Names[i]+" Helmet")); i++;
		BronzeHelmet = 			(new ItemTFCArmor(Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 0, 50,3).setUnlocalizedName(Names[i]+" Helmet")); i++;
		CopperHelmet = 			(new ItemTFCArmor(Armor.CopperPlate, proxy.getArmorRenderID("copper"), 0, 50,3).setUnlocalizedName(Names[i]+" Helmet")); i++;
		WroughtIronHelmet = 	(new ItemTFCArmor(Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 0, 50,3).setUnlocalizedName(Names[i]+" Helmet")); i++;
		RedSteelHelmet = 		(new ItemTFCArmor(Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 0, 50,3).setUnlocalizedName(Names[i]+" Helmet")); i++;
		SteelHelmet = 			(new ItemTFCArmor(Armor.SteelPlate, proxy.getArmorRenderID("steel"), 0, 50,3).setUnlocalizedName(Names[i]+" Helmet"));

		LeatherHelmet = new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 0, ArmorMaterial.CLOTH, 100,3).setUnlocalizedName("helmetCloth").setTextureName("leather_helmet");
		LeatherChestplate = new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 1, ArmorMaterial.CLOTH, 100,2).setUnlocalizedName("chestplateCloth").setTextureName("leather_chestplate");
		LeatherLeggings = new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 2, ArmorMaterial.CLOTH, 100,1).setUnlocalizedName("leggingsCloth").setTextureName("leather_leggings");
		LeatherBoots = new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 3, ArmorMaterial.CLOTH, 100,0).setUnlocalizedName("bootsCloth").setTextureName("leather_boots");

		Quiver = new ItemQuiver().setUnlocalizedName("Quiver");
	}

	public static void registerFurniceFuel()
	{
		//1 sec = 20 burn time value
		TFCFuelHandler.registerFuel(BlueSteelBucketLava, 20000);
		TFCFuelHandler.registerFuel(SinglePlank, 400);
		TFCFuelHandler.registerFuel(WoodenBucketEmpty, 300);
		TFCFuelHandler.registerFuel(FireStarter, 100);
		TFCFuelHandler.registerFuel(Logs, 800);
		TFCFuelHandler.registerFuel(SluiceItem, 300);
		TFCFuelHandler.registerFuel(Rope, 50);
		TFCFuelHandler.registerFuel(Arrow, 20);
		TFCFuelHandler.registerFuel(Bow, 100);
		TFCFuelHandler.registerFuel(FishingRod, 100);
		TFCFuelHandler.registerFuel(Stick, 100);
		TFCFuelHandler.registerFuel(Coal, 1600);
		TFCFuelHandler.registerFuel(WoolCloth, 20);
		TFCFuelHandler.registerFuel(SilkCloth, 20);
		TFCFuelHandler.registerFuel(BurlapCloth, 20);
		TFCFuelHandler.registerFuel(Straw, 20);

		for(int l = 0; l < Recipes.Doors.length; l++)
			TFCFuelHandler.registerFuel(Recipes.Doors[l], 300);

		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.WoodSupportV), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.WoodSupportV2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.WoodSupportH), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.WoodSupportH2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Fence), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Fence2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.FenceGate), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.FenceGate2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Chest), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.StrawHideBed), 200);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Thatch), 200);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Planks), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Planks2), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Barrel), 300);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Torch), 100);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Sapling), 100);
		TFCFuelHandler.registerFuel(Item.getItemFromBlock(TFCBlocks.Sapling2), 100);
	}
}
