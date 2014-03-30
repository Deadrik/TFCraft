package TFC;

import java.util.ArrayList;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import TFC.API.Armor;
import TFC.API.Metal;
import TFC.API.TFCOptions;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumDamageType;
import TFC.API.Enums.EnumFoodGroup;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Recipes;
import TFC.Core.TFCTabs;
import TFC.Core.Metal.Alloy;
import TFC.Core.Metal.AlloyManager;
import TFC.Core.Metal.MetalRegistry;
import TFC.Food.ItemEgg;
import TFC.Food.ItemFoodTFC;
import TFC.Food.ItemMeal;
import TFC.Food.ItemRawFood;
import TFC.Food.ItemRawFoodDough;
import TFC.Items.ItemAlcohol;
import TFC.Items.ItemArrow;
import TFC.Items.ItemBloom;
import TFC.Items.ItemBlueprint;
import TFC.Items.ItemClay;
import TFC.Items.ItemCustomLeash;
import TFC.Items.ItemCustomMinecart;
import TFC.Items.ItemCustomPotion;
import TFC.Items.ItemCustomSeeds;
import TFC.Items.ItemDyeCustom;
import TFC.Items.ItemFertilizer;
import TFC.Items.ItemFlatGeneric;
import TFC.Items.ItemFruitTreeSapling;
import TFC.Items.ItemGem;
import TFC.Items.ItemIngot;
import TFC.Items.ItemLeather;
import TFC.Items.ItemLogs;
import TFC.Items.ItemLooseRock;
import TFC.Items.ItemMeltedMetal;
import TFC.Items.ItemMetalSheet;
import TFC.Items.ItemMetalSheet2x;
import TFC.Items.ItemOre;
import TFC.Items.ItemOreSmall;
import TFC.Items.ItemPlank;
import TFC.Items.ItemQuiver;
import TFC.Items.ItemRawHide;
import TFC.Items.ItemSluice;
import TFC.Items.ItemStick;
import TFC.Items.ItemStoneBrick;
import TFC.Items.ItemTFCArmor;
import TFC.Items.ItemTerra;
import TFC.Items.ItemTuyere;
import TFC.Items.ItemUnfinishedArmor;
import TFC.Items.ItemBlocks.ItemWoodDoor;
import TFC.Items.Pottery.ItemPotteryBase;
import TFC.Items.Pottery.ItemPotteryJug;
import TFC.Items.Pottery.ItemPotteryLargeVessel;
import TFC.Items.Pottery.ItemPotteryMold;
import TFC.Items.Pottery.ItemPotteryPot;
import TFC.Items.Pottery.ItemPotterySmallVessel;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemCustomAxe;
import TFC.Items.Tools.ItemCustomBlueSteelBucket;
import TFC.Items.Tools.ItemCustomBow;
import TFC.Items.Tools.ItemCustomBucket;
import TFC.Items.Tools.ItemCustomBucketMilk;
import TFC.Items.Tools.ItemCustomHoe;
import TFC.Items.Tools.ItemCustomKnife;
import TFC.Items.Tools.ItemCustomPickaxe;
import TFC.Items.Tools.ItemCustomRedSteelBucket;
import TFC.Items.Tools.ItemCustomSaw;
import TFC.Items.Tools.ItemCustomScythe;
import TFC.Items.Tools.ItemCustomShovel;
import TFC.Items.Tools.ItemCustomSword;
import TFC.Items.Tools.ItemFirestarter;
import TFC.Items.Tools.ItemFlintSteel;
import TFC.Items.Tools.ItemGoldPan;
import TFC.Items.Tools.ItemHammer;
import TFC.Items.Tools.ItemJavelin;
import TFC.Items.Tools.ItemMiscToolHead;
import TFC.Items.Tools.ItemPlan;
import TFC.Items.Tools.ItemProPick;
import TFC.Items.Tools.ItemSpindle;
import TFC.Items.Tools.ItemWritableBookTFC;

public class TFCItems
{
	public static Item GemRuby;
	public static Item GemSapphire;
	public static Item GemEmerald;
	public static Item GemTopaz;
	public static Item GemGarnet;
	public static Item GemOpal;
	public static Item GemAmethyst;
	public static Item GemJasper;
	public static Item GemBeryl;
	public static Item GemTourmaline;
	public static Item GemJade;
	
	public static Item GemAgate;
	public static Item GemDiamond;

	public static Item BismuthIngot;
	public static Item BismuthBronzeIngot;
	public static Item BlackBronzeIngot;
	public static Item BlackSteelIngot;
	public static Item HCBlackSteelIngot;
	public static Item BlueSteelIngot;
	public static Item WeakBlueSteelIngot;
	public static Item HCBlueSteelIngot;
	public static Item BrassIngot;
	public static Item BronzeIngot;
	public static Item CopperIngot;
	public static Item GoldIngot;
	public static Item WroughtIronIngot;
	public static Item LeadIngot;
	public static Item NickelIngot;
	public static Item PigIronIngot;
	public static Item PlatinumIngot;
	public static Item RedSteelIngot;
	public static Item WeakRedSteelIngot;
	public static Item HCRedSteelIngot;
	public static Item RoseGoldIngot;
	public static Item SilverIngot;
	public static Item SteelIngot;
	public static Item WeakSteelIngot;
	public static Item HCSteelIngot;
	public static Item SterlingSilverIngot;
	public static Item TinIngot;
	public static Item ZincIngot;

	public static Item BismuthIngot2x;
	public static Item BismuthBronzeIngot2x;
	public static Item BlackBronzeIngot2x;
	public static Item BlackSteelIngot2x;
	public static Item BlueSteelIngot2x;
	public static Item BrassIngot2x;
	public static Item BronzeIngot2x;
	public static Item CopperIngot2x;
	public static Item GoldIngot2x;
	public static Item WroughtIronIngot2x;
	public static Item LeadIngot2x;
	public static Item NickelIngot2x;
	public static Item PigIronIngot2x;
	public static Item PlatinumIngot2x;
	public static Item RedSteelIngot2x;
	public static Item RoseGoldIngot2x;
	public static Item SilverIngot2x;
	public static Item SteelIngot2x;
	public static Item SterlingSilverIngot2x;
	public static Item TinIngot2x;
	public static Item ZincIngot2x;


	public static Item IgInShovel;
	public static Item IgInAxe;
	public static Item IgInHoe;

	public static Item SedShovel;
	public static Item SedAxe;
	public static Item SedHoe;

	public static Item IgExShovel;
	public static Item IgExAxe;
	public static Item IgExHoe;

	public static Item MMShovel;
	public static Item MMAxe;
	public static Item MMHoe;

	public static Item BismuthBronzePick;
	public static Item BismuthBronzeShovel;
	public static Item BismuthBronzeAxe;
	public static Item BismuthBronzeHoe;
	public static Item BlackBronzePick;
	public static Item BlackBronzeShovel;
	public static Item BlackBronzeAxe;
	public static Item BlackBronzeHoe;
	public static Item BlackSteelPick;
	public static Item BlackSteelShovel;
	public static Item BlackSteelAxe;
	public static Item BlackSteelHoe;
	public static Item BlueSteelPick;
	public static Item BlueSteelShovel;
	public static Item BlueSteelAxe;
	public static Item BlueSteelHoe;
	public static Item BronzePick;
	public static Item BronzeShovel;
	public static Item BronzeAxe;
	public static Item BronzeHoe;
	public static Item CopperPick;
	public static Item CopperShovel;
	public static Item CopperAxe;
	public static Item CopperHoe;
	public static Item WroughtIronPick;
	public static Item WroughtIronShovel;
	public static Item WroughtIronAxe;
	public static Item WroughtIronHoe;
	public static Item RedSteelPick;
	public static Item RedSteelShovel;
	public static Item RedSteelAxe;
	public static Item RedSteelHoe;
	public static Item SteelPick;
	public static Item SteelShovel;
	public static Item SteelAxe;
	public static Item SteelHoe;


	public static Item BismuthBronzeChisel;
	public static Item BlackBronzeChisel;
	public static Item BlackSteelChisel;
	public static Item BlueSteelChisel;
	public static Item BronzeChisel;
	public static Item CopperChisel;
	public static Item WroughtIronChisel;
	public static Item RedSteelChisel;
	public static Item SteelChisel;

	public static Item BismuthBronzeSword;
	public static Item BlackBronzeSword;
	public static Item BlackSteelSword;
	public static Item BlueSteelSword;
	public static Item BronzeSword;
	public static Item CopperSword;
	public static Item WroughtIronSword;
	public static Item RedSteelSword;
	public static Item SteelSword;

	public static Item IgInStoneMace;
	public static Item IgExStoneMace;
	public static Item SedStoneMace;
	public static Item MMStoneMace;
	public static Item BismuthBronzeMace;
	public static Item BlackBronzeMace;
	public static Item BlackSteelMace;
	public static Item BlueSteelMace;
	public static Item BronzeMace;
	public static Item CopperMace;
	public static Item WroughtIronMace;
	public static Item RedSteelMace;
	public static Item SteelMace;

	public static Item BismuthBronzeSaw;
	public static Item BlackBronzeSaw;
	public static Item BlackSteelSaw;
	public static Item BlueSteelSaw;
	public static Item BronzeSaw;
	public static Item CopperSaw;
	public static Item WroughtIronSaw;
	public static Item RedSteelSaw;
	public static Item SteelSaw;

	public static Item OreChunk;
	public static Item Logs;
	public static Item Barrel;

	// javelins
	public static Item IgInStoneJavelin;
	public static Item SedStoneJavelin;
	public static Item IgExStoneJavelin;
	public static Item MMStoneJavelin;
	public static Item CopperJavelin;
	public static Item BismuthBronzeJavelin;
	public static Item BronzeJavelin;
	public static Item BlackBronzeJavelin;
	public static Item WroughtIronJavelin;
	public static Item SteelJavelin;
	public static Item BlackSteelJavelin;
	public static Item BlueSteelJavelin;
	public static Item RedSteelJavelin;

	// javelin heads
	public static Item IgInStoneJavelinHead;
	public static Item SedStoneJavelinHead;
	public static Item IgExStoneJavelinHead;
	public static Item MMStoneJavelinHead;
	public static Item CopperJavelinHead;
	public static Item BismuthBronzeJavelinHead;
	public static Item BronzeJavelinHead;
	public static Item BlackBronzeJavelinHead;
	public static Item WroughtIronJavelinHead;
	public static Item SteelJavelinHead;
	public static Item BlackSteelJavelinHead;
	public static Item BlueSteelJavelinHead;
	public static Item RedSteelJavelinHead;

	public static Item BismuthBronzeScythe;
	public static Item BlackBronzeScythe;
	public static Item BlackSteelScythe;
	public static Item BlueSteelScythe;
	public static Item BronzeScythe;
	public static Item CopperScythe;
	public static Item WroughtIronScythe;
	public static Item RedSteelScythe;
	public static Item SteelScythe;

	public static Item BismuthBronzeKnife;
	public static Item BlackBronzeKnife;
	public static Item BlackSteelKnife;
	public static Item BlueSteelKnife;
	public static Item BronzeKnife;
	public static Item CopperKnife;
	public static Item WroughtIronKnife;
	public static Item RedSteelKnife;
	public static Item SteelKnife;

	public static Item FireStarter;

	public static Item StoneHammer;
	public static Item BismuthBronzeHammer;
	public static Item BlackBronzeHammer;
	public static Item BlackSteelHammer;
	public static Item BlueSteelHammer;
	public static Item BronzeHammer;
	public static Item CopperHammer;
	public static Item WroughtIronHammer;
	public static Item RedSteelHammer;
	public static Item SteelHammer;

	public static Item BismuthUnshaped;
	public static Item BismuthBronzeUnshaped;
	public static Item BlackBronzeUnshaped;
	public static Item BlackSteelUnshaped;
	public static Item HCBlackSteelUnshaped;
	public static Item BlueSteelUnshaped;
	public static Item WeakBlueSteelUnshaped;
	public static Item HCBlueSteelUnshaped;
	public static Item BrassUnshaped;
	public static Item BronzeUnshaped;
	public static Item CopperUnshaped;
	public static Item GoldUnshaped;
	public static Item WroughtIronUnshaped;
	public static Item LeadUnshaped;
	public static Item NickelUnshaped;
	public static Item PigIronUnshaped;
	public static Item PlatinumUnshaped;
	public static Item RedSteelUnshaped;
	public static Item WeakRedSteelUnshaped;
	public static Item HCRedSteelUnshaped;
	public static Item RoseGoldUnshaped;
	public static Item SilverUnshaped;
	public static Item SteelUnshaped;
	public static Item WeakSteelUnshaped;
	public static Item HCSteelUnshaped;
	public static Item SterlingSilverUnshaped;
	public static Item TinUnshaped;
	public static Item ZincUnshaped;
	public static Item CeramicMold;
	public static Item Ink;

	//Plans
	public static Item PickaxeHeadPlan;
	public static Item ShovelHeadPlan;
	public static Item HoeHeadPlan;
	public static Item AxeHeadPlan;
	public static Item HammerHeadPlan;
	public static Item ChiselHeadPlan;
	public static Item SwordBladePlan;
	public static Item MaceHeadPlan;
	public static Item SawBladePlan;
	public static Item ProPickHeadPlan;
	public static Item HelmetPlan;
	public static Item ChestplatePlan;
	public static Item GreavesPlan;
	public static Item BootsPlan;
	public static Item ScythePlan;
	public static Item KnifePlan;
	public static Item BucketPlan;
	public static Item JavelinHeadPlan;

	//Tool Heads
	public static Item BismuthBronzePickaxeHead;
	public static Item BlackBronzePickaxeHead;
	public static Item BlackSteelPickaxeHead;
	public static Item BlueSteelPickaxeHead;
	public static Item BronzePickaxeHead;
	public static Item CopperPickaxeHead;
	public static Item WroughtIronPickaxeHead;
	public static Item RedSteelPickaxeHead;
	public static Item SteelPickaxeHead;

	public static Item BismuthBronzeShovelHead;
	public static Item BlackBronzeShovelHead;
	public static Item BlackSteelShovelHead;
	public static Item BlueSteelShovelHead;
	public static Item BronzeShovelHead;
	public static Item CopperShovelHead;
	public static Item WroughtIronShovelHead;
	public static Item RedSteelShovelHead;
	public static Item SilverShovelHead;
	public static Item SteelShovelHead;

	public static Item BismuthBronzeHoeHead;
	public static Item BlackBronzeHoeHead;
	public static Item BlackSteelHoeHead;
	public static Item BlueSteelHoeHead;
	public static Item BronzeHoeHead;
	public static Item CopperHoeHead;
	public static Item WroughtIronHoeHead;
	public static Item RedSteelHoeHead;
	public static Item SteelHoeHead;

	public static Item BismuthBronzeAxeHead;
	public static Item BlackBronzeAxeHead;
	public static Item BlackSteelAxeHead;
	public static Item BlueSteelAxeHead;
	public static Item BronzeAxeHead;
	public static Item CopperAxeHead;
	public static Item WroughtIronAxeHead;
	public static Item RedSteelAxeHead;
	public static Item SteelAxeHead;

	public static Item BismuthBronzeHammerHead;
	public static Item BlackBronzeHammerHead;
	public static Item BlackSteelHammerHead;
	public static Item BlueSteelHammerHead;
	public static Item BronzeHammerHead;
	public static Item CopperHammerHead;
	public static Item WroughtIronHammerHead;
	public static Item RedSteelHammerHead;
	public static Item SteelHammerHead;

	public static Item BismuthBronzeChiselHead;
	public static Item BlackBronzeChiselHead;
	public static Item BlackSteelChiselHead;
	public static Item BlueSteelChiselHead;
	public static Item BronzeChiselHead;
	public static Item CopperChiselHead;
	public static Item WroughtIronChiselHead;
	public static Item RedSteelChiselHead;
	public static Item SteelChiselHead;

	public static Item BismuthBronzeSwordHead;
	public static Item BlackBronzeSwordHead;
	public static Item BlackSteelSwordHead;
	public static Item BlueSteelSwordHead;
	public static Item BronzeSwordHead;
	public static Item CopperSwordHead;
	public static Item WroughtIronSwordHead;
	public static Item RedSteelSwordHead;
	public static Item SteelSwordHead;

	public static Item BismuthBronzeMaceHead;
	public static Item BlackBronzeMaceHead;
	public static Item BlackSteelMaceHead;
	public static Item BlueSteelMaceHead;
	public static Item BronzeMaceHead;
	public static Item CopperMaceHead;
	public static Item WroughtIronMaceHead;
	public static Item RedSteelMaceHead;
	public static Item SteelMaceHead;

	public static Item BismuthBronzeSawHead;
	public static Item BlackBronzeSawHead;
	public static Item BlackSteelSawHead;
	public static Item BlueSteelSawHead;
	public static Item BronzeSawHead;
	public static Item CopperSawHead;
	public static Item WroughtIronSawHead;
	public static Item RedSteelSawHead;
	public static Item SteelSawHead;

	public static Item BismuthBronzeProPickHead;
	public static Item BlackBronzeProPickHead;
	public static Item BlackSteelProPickHead;
	public static Item BlueSteelProPickHead;
	public static Item BronzeProPickHead;
	public static Item CopperProPickHead;
	public static Item WroughtIronProPickHead;
	public static Item RedSteelProPickHead;
	public static Item SteelProPickHead;

	public static Item BismuthBronzeScytheHead;
	public static Item BlackBronzeScytheHead;
	public static Item BlackSteelScytheHead;
	public static Item BlueSteelScytheHead;
	public static Item BronzeScytheHead;
	public static Item CopperScytheHead;
	public static Item WroughtIronScytheHead;
	public static Item RedSteelScytheHead;
	public static Item SteelScytheHead;

	public static Item BismuthBronzeKnifeHead;
	public static Item BlackBronzeKnifeHead;
	public static Item BlackSteelKnifeHead;
	public static Item BlueSteelKnifeHead;
	public static Item BronzeKnifeHead;
	public static Item CopperKnifeHead;
	public static Item WroughtIronKnifeHead;
	public static Item RedSteelKnifeHead;
	public static Item SteelKnifeHead;

	public static Item Coke;
	public static Item Powder;

	//Formerly TFC_Mining
	public static Item GoldPan;
	public static Item SluiceItem;

	public static Item ProPickStone;
	public static Item ProPickBismuthBronze;
	public static Item ProPickBlackBronze;
	public static Item ProPickBlackSteel;
	public static Item ProPickBlueSteel;
	public static Item ProPickBronze;
	public static Item ProPickCopper;
	public static Item ProPickIron;
	public static Item ProPickRedSteel;
	public static Item ProPickSteel;

	/**Armor Crafting related items*/
	public static Item BismuthSheet;
	public static Item BismuthBronzeSheet;
	public static Item BlackBronzeSheet;
	public static Item BlackSteelSheet;
	public static Item BlueSteelSheet;
	public static Item BronzeSheet;
	public static Item CopperSheet;
	public static Item WroughtIronSheet;
	public static Item RedSteelSheet;
	public static Item RoseGoldSheet;
	public static Item SteelSheet;
	public static Item TinSheet;
	public static Item ZincSheet;

	public static Item BrassSheet;
	public static Item GoldSheet;
	public static Item LeadSheet;
	public static Item NickelSheet;
	public static Item PigIronSheet;
	public static Item PlatinumSheet;
	public static Item SilverSheet;
	public static Item SterlingSilverSheet;

	public static Item BismuthSheet2x;
	public static Item BismuthBronzeSheet2x;
	public static Item BlackBronzeSheet2x;
	public static Item BlackSteelSheet2x;
	public static Item BlueSteelSheet2x;
	public static Item BronzeSheet2x;
	public static Item CopperSheet2x;
	public static Item WroughtIronSheet2x;
	public static Item RedSteelSheet2x;
	public static Item RoseGoldSheet2x;
	public static Item SteelSheet2x;
	public static Item TinSheet2x;
	public static Item ZincSheet2x;

	public static Item BrassSheet2x;
	public static Item GoldSheet2x;
	public static Item LeadSheet2x;
	public static Item NickelSheet2x;
	public static Item PigIronSheet2x;
	public static Item PlatinumSheet2x;
	public static Item SilverSheet2x;
	public static Item SterlingSilverSheet2x;

	public static Item BismuthBronzeUnfinishedChestplate;
	public static Item BlackBronzeUnfinishedChestplate;
	public static Item BlackSteelUnfinishedChestplate;
	public static Item BlueSteelUnfinishedChestplate;
	public static Item BronzeUnfinishedChestplate;
	public static Item CopperUnfinishedChestplate;
	public static Item WroughtIronUnfinishedChestplate;
	public static Item RedSteelUnfinishedChestplate;
	public static Item SteelUnfinishedChestplate;

	public static Item BismuthBronzeUnfinishedGreaves;
	public static Item BlackBronzeUnfinishedGreaves;
	public static Item BlackSteelUnfinishedGreaves;
	public static Item BlueSteelUnfinishedGreaves;
	public static Item BronzeUnfinishedGreaves;
	public static Item CopperUnfinishedGreaves;
	public static Item WroughtIronUnfinishedGreaves;
	public static Item RedSteelUnfinishedGreaves;
	public static Item SteelUnfinishedGreaves;

	public static Item BismuthBronzeUnfinishedBoots;
	public static Item BlackBronzeUnfinishedBoots;
	public static Item BlackSteelUnfinishedBoots;
	public static Item BlueSteelUnfinishedBoots;
	public static Item BronzeUnfinishedBoots;
	public static Item CopperUnfinishedBoots;
	public static Item WroughtIronUnfinishedBoots;
	public static Item RedSteelUnfinishedBoots;
	public static Item SteelUnfinishedBoots;

	public static Item BismuthBronzeUnfinishedHelmet;
	public static Item BlackBronzeUnfinishedHelmet;
	public static Item BlackSteelUnfinishedHelmet;
	public static Item BlueSteelUnfinishedHelmet;
	public static Item BronzeUnfinishedHelmet;
	public static Item CopperUnfinishedHelmet;
	public static Item WroughtIronUnfinishedHelmet;
	public static Item RedSteelUnfinishedHelmet;
	public static Item SteelUnfinishedHelmet;

	public static Item BismuthBronzeChestplate;
	public static Item BlackBronzeChestplate;
	public static Item BlackSteelChestplate;
	public static Item BlueSteelChestplate;
	public static Item BronzeChestplate;
	public static Item CopperChestplate;
	public static Item WroughtIronChestplate;
	public static Item RedSteelChestplate;
	public static Item SteelChestplate;

	public static Item BismuthBronzeGreaves;
	public static Item BlackBronzeGreaves;
	public static Item BlackSteelGreaves;
	public static Item BlueSteelGreaves;
	public static Item BronzeGreaves;
	public static Item CopperGreaves;
	public static Item WroughtIronGreaves;
	public static Item RedSteelGreaves;
	public static Item SteelGreaves;

	public static Item BismuthBronzeBoots;
	public static Item BlackBronzeBoots;
	public static Item BlackSteelBoots;
	public static Item BlueSteelBoots;
	public static Item BronzeBoots;
	public static Item CopperBoots;
	public static Item WroughtIronBoots;
	public static Item RedSteelBoots;
	public static Item SteelBoots;

	public static Item BismuthBronzeHelmet;
	public static Item BlackBronzeHelmet;
	public static Item BlackSteelHelmet;
	public static Item BlueSteelHelmet;
	public static Item BronzeHelmet;
	public static Item CopperHelmet;
	public static Item WroughtIronHelmet;
	public static Item RedSteelHelmet;
	public static Item SteelHelmet;

	public static Item WoodenBucketEmpty;
	public static Item WoodenBucketWater;
	public static Item WoodenBucketMilk;

	/**Food Related Items and Blocks*/
	public static Item SeedsWheat;
	public static Item SeedsMelon;
	public static Item SeedsPumpkin;
	public static Item SeedsMaize;
	public static Item SeedsTomato;
	public static Item SeedsBarley;
	public static Item SeedsRye;
	public static Item SeedsOat;
	public static Item SeedsRice;
	public static Item SeedsPotato;
	public static Item SeedsOnion;
	public static Item SeedsCabbage;
	public static Item SeedsGarlic;
	public static Item SeedsCarrot;
	public static Item SeedsSugarcane;
	public static Item SeedsHemp;
	public static Item SeedsYellowBellPepper;
	public static Item SeedsRedBellPepper;
	public static Item SeedsSoybean;
	public static Item SeedsGreenbean;
	public static Item SeedsSquash;

	public static Item FruitTreeSapling1;
	public static Item FruitTreeSapling2;

	public static Item RedApple;
	public static Item GreenApple;
	public static Item Banana;
	public static Item Orange;
	public static Item Lemon;
	public static Item Olive;
	public static Item Cherry;
	public static Item Peach;
	public static Item Plum;
	public static Item Egg;
	public static Item EggCooked;
	public static Item Cheese;

	public static Item WheatGrain;
	public static Item BarleyGrain;
	public static Item OatGrain;
	public static Item RyeGrain;
	public static Item RiceGrain;
	public static Item MaizeEar;
	public static Item Tomato;
	public static Item Potato;
	public static Item Onion;
	public static Item Cabbage;
	public static Item Garlic;
	public static Item Carrot;
	public static Item Sugarcane;
	public static Item Hemp;
	public static Item Soybean;
	public static Item Greenbeans;
	public static Item GreenBellPepper;
	public static Item YellowBellPepper;
	public static Item RedBellPepper;
	public static Item Squash;

	public static Item WheatGround;
	public static Item BarleyGround;
	public static Item OatGround;
	public static Item RyeGround;
	public static Item RiceGround;
	public static Item CornmealGround;

	public static Item WheatDough;
	public static Item BarleyDough;
	public static Item OatDough;
	public static Item RyeDough;
	public static Item RiceDough;
	public static Item CornmealDough;

	public static Item WheatBread;
	public static Item BarleyBread;
	public static Item OatBread;
	public static Item RyeBread;
	public static Item RiceBread;
	public static Item CornBread;

	public static Item WheatWhole;
	public static Item BarleyWhole;
	public static Item OatWhole;
	public static Item RyeWhole;
	public static Item RiceWhole;

	public static Item venisonRaw;
	public static Item venisonCooked;

	public static Item LooseRock;
	public static Item FlatRock;

	public static Item IgInStoneShovelHead;
	public static Item SedStoneShovelHead;
	public static Item IgExStoneShovelHead;
	public static Item MMStoneShovelHead;
	public static Item IgInStoneAxeHead;
	public static Item SedStoneAxeHead;
	public static Item IgExStoneAxeHead;
	public static Item MMStoneAxeHead;
	public static Item IgInStoneHoeHead;
	public static Item SedStoneHoeHead;
	public static Item IgExStoneHoeHead;
	public static Item MMStoneHoeHead;

	public static Item StoneKnife;
	public static Item StoneKnifeHead;
	public static Item StoneHammerHead;
	public static Item SmallOreChunk;
	public static Item SinglePlank;

	public static Item minecartEmpty;
	public static Item minecartCrate;

	public static Item RedSteelBucketEmpty;
	public static Item RedSteelBucketWater;

	public static Item BlueSteelBucketEmpty;
	public static Item BlueSteelBucketLava;

	public static Item MealGeneric;

	public static Item Quern;
	public static Item FlintSteel;

	public static Item DoorOak;
	public static Item DoorAspen;
	public static Item DoorBirch;
	public static Item DoorChestnut;
	public static Item DoorDouglasFir;
	public static Item DoorHickory;
	public static Item DoorMaple;
	public static Item DoorAsh;
	public static Item DoorPine;
	public static Item DoorSequoia;
	public static Item DoorSpruce;
	public static Item DoorSycamore;
	public static Item DoorWhiteCedar;
	public static Item DoorWhiteElm;
	public static Item DoorWillow;
	public static Item DoorKapok;
	public static Item DoorAcacia;

	public static Item Blueprint;
	public static Item writabeBookTFC;
	public static Item WoolYarn;
	public static Item Wool;
	public static Item WoolCloth;
	public static Item Spindle;

	public static Item SpindleHead;
	public static Item StoneBrick;
	public static Item Mortar;
	public static Item Limewater;
	public static Item Vinegar;
	public static Item Hide;
	public static Item SoakedHide;
	public static Item ScrapedHide;
	public static Item PrepHide;
	public static Item SheepSkin;
	public static Item TerraLeather;
	public static Item muttonRaw;
	public static Item muttonCooked;
	public static Item CalamariRaw;
	public static Item CalamariCooked;
	public static Item FlatLeather;
	public static Item Beer;
	public static Item Cider;
	public static Item Rum;
	public static Item RyeWhiskey;
	public static Item Sake;
	public static Item Vodka;
	public static Item Whiskey;

	public static Item PotteryJug;
	public static Item PotteryPot;
	public static Item PotterySmallVessel;
	public static Item PotteryLargeVessel;
	public static Item KilnRack;
	public static Item Straw;
	public static Item FlatClay;

	public static Item ClayMoldAxe;
	public static Item ClayMoldChisel;
	public static Item ClayMoldHammer;
	public static Item ClayMoldHoe;
	public static Item ClayMoldKnife;
	public static Item ClayMoldMace;
	public static Item ClayMoldPick;
	public static Item ClayMoldProPick;
	public static Item ClayMoldSaw;
	public static Item ClayMoldScythe;
	public static Item ClayMoldShovel;
	public static Item ClayMoldSword;
	public static Item ClayMoldJavelin;

	public static Item TuyereCopper;
	public static Item TuyereBronze;
	public static Item TuyereBlackBronze;
	public static Item TuyereBismuthBronze;
	public static Item TuyereWroughtIron;
	public static Item TuyereSteel;
	public static Item TuyereBlackSteel;
	public static Item TuyereBlueSteel;
	public static Item TuyereRedSteel;

	public static Item Bloom;
	public static Item RawBloom;
	public static Item UnknownIngot;
	public static Item UnknownUnshaped;

	public static Item Quiver;

	public static Item Jute;
	public static Item JuteFibre;

	public static Item WintergreenBerry;
	public static Item Blueberry;
	public static Item Raspberry;
	public static Item Strawberry;
	public static Item Blackberry;
	public static Item Bunchberry;
	public static Item Cranberry;
	public static Item Snowberry;
	public static Item Elderberry;
	public static Item Gooseberry;
	public static Item Cloudberry;
	public static Item WintergreenLeaf;
	public static Item BlueberryLeaf;
	public static Item RaspberryLeaf;
	public static Item StrawberryLeaf;
	public static Item BlackberryLeaf;
	public static Item BunchberryLeaf;
	public static Item CranberryLeaf;
	public static Item SnowberryLeaf;
	public static Item ElderberryLeaf;
	public static Item GooseberryLeaf;
	public static Item CloudberryLeaf;
	public static Item Fertilizer;

	/**
	 * Item Uses Setup
	 * */
	public static int IgInStoneUses = 60;
	public static int IgExStoneUses = 70;
	public static int SedStoneUses = 50;
	public static int MMStoneUses = 55;

	//Tier 1
	public static int CopperUses = 600;
	//Tier 2
	public static int BronzeUses = 1300;
	public static int BismuthBronzeUses = 1200;
	public static int BlackBronzeUses = 1460;
	//Tier 3
	public static int WroughtIronUses = 2200;
	//Tier 4
	public static int SteelUses = 3300;
	//Tier 5
	public static int BlackSteelUses = 4200;
	//Tier 6
	public static int BlueSteelUses = 6500;
	public static int RedSteelUses = 6500;


	//Tier 1
	public static float CopperEff = 8;
	//Tier 2
	public static float BronzeEff = 11;
	public static float BismuthBronzeEff = 10;
	public static float BlackBronzeEff = 9;
	//Tier 3
	public static float WroughtIronEff = 12;
	//Tier 4
	public static float SteelEff = 14;
	//Tier 5
	public static float BlackSteelEff = 16;
	//Tier 6
	public static float BlueSteelEff = 18;
	public static float RedSteelEff = 18;

	public static ToolMaterial IgInToolMaterial;
	public static ToolMaterial SedToolMaterial;
	public static ToolMaterial IgExToolMaterial;
	public static ToolMaterial MMToolMaterial;

	public static ToolMaterial BismuthBronzeToolMaterial;
	public static ToolMaterial BlackBronzeToolMaterial;
	public static ToolMaterial BlackSteelToolMaterial;
	public static ToolMaterial BlueSteelToolMaterial;
	public static ToolMaterial BronzeToolMaterial;
	public static ToolMaterial CopperToolMaterial;
	public static ToolMaterial IronToolMaterial;
	public static ToolMaterial RedSteelToolMaterial;
	public static ToolMaterial SteelToolMaterial;

	public static ArrayList<Item> FoodList;

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

		//Replace any vanilla Items here
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.coal), "coal", new TFC.Items.ItemCoal().setUnlocalizedName("coal"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.stick), "stick", new ItemStick().setFull3D().setUnlocalizedName("stick"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.leather), "leather", new ItemTerra().setFull3D().setUnlocalizedName("leather"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.bow), "bow", new ItemCustomBow().setUnlocalizedName("bow").setTextureName("bow"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.arrow), "arrow", new ItemArrow().setUnlocalizedName("arrow"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.dye), "dye", new ItemDyeCustom().setUnlocalizedName("dyePowder").setTextureName("dye_powder"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.potionitem), "potionitem", new ItemCustomPotion().setUnlocalizedName("potion").setTextureName("potion"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.lead), "lead", new ItemCustomLeash().setUnlocalizedName("Rope"));

		Item.itemRegistry.addObject(Block.getIdFromBlock(Blocks.tallgrass), "tallgrass", (new ItemColored(Blocks.tallgrass, true)).func_150943_a(new String[] {"shrub", "grass", "fern"}));
		Item.itemRegistry.addObject(Block.getIdFromBlock(Blocks.vine), "vine", new ItemColored(Blocks.vine, false));

		minecartCrate = new ItemCustomMinecart(1).setUnlocalizedName("minecartChest").setTextureName("minecart_chest");
		GoldPan = new ItemGoldPan().setUnlocalizedName("GoldPan");
		SluiceItem = new ItemSluice().setFolder("devices/").setUnlocalizedName("SluiceItem");

		ProPickBismuthBronze = new ItemProPick().setUnlocalizedName("Bismuth Bronze ProPick").setMaxDamage(BismuthBronzeUses);
		ProPickBlackBronze = new ItemProPick().setUnlocalizedName("Black Bronze ProPick").setMaxDamage(BlackBronzeUses);
		ProPickBlackSteel = new ItemProPick().setUnlocalizedName("Black Steel ProPick").setMaxDamage(BlackSteelUses);
		ProPickBlueSteel = new ItemProPick().setUnlocalizedName("Blue Steel ProPick").setMaxDamage(BlueSteelUses);
		ProPickBronze = new ItemProPick().setUnlocalizedName("Bronze ProPick").setMaxDamage(BronzeUses);
		ProPickCopper = new ItemProPick().setUnlocalizedName("Copper ProPick").setMaxDamage(CopperUses);
		ProPickIron = new ItemProPick().setUnlocalizedName("Wrought Iron ProPick").setMaxDamage(WroughtIronUses);
		ProPickRedSteel = new ItemProPick().setUnlocalizedName("Red Steel ProPick").setMaxDamage(RedSteelUses);
		ProPickSteel = new ItemProPick().setUnlocalizedName("Steel ProPick").setMaxDamage(SteelUses);

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
		IgInShovel = new ItemCustomShovel(IgInToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(IgInStoneUses);
		IgInAxe = new ItemCustomAxe(IgInToolMaterial, 60).setUnlocalizedName("Stone Axe").setMaxDamage(IgInStoneUses);
		IgInHoe = new ItemCustomHoe(IgInToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(IgInStoneUses);

		SedShovel= new ItemCustomShovel(SedToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(SedStoneUses);
		SedAxe = new ItemCustomAxe(SedToolMaterial, 60).setUnlocalizedName("Stone Axe").setMaxDamage(SedStoneUses);
		SedHoe = new ItemCustomHoe(SedToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(SedStoneUses);

		IgExShovel= new ItemCustomShovel(IgExToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(IgExStoneUses);
		IgExAxe = new ItemCustomAxe(IgExToolMaterial, 60).setUnlocalizedName("Stone Axe").setMaxDamage(IgExStoneUses);
		IgExHoe = new ItemCustomHoe(IgExToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(IgExStoneUses);

		MMShovel = new ItemCustomShovel(MMToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(MMStoneUses);
		MMAxe = new ItemCustomAxe(MMToolMaterial, 60).setUnlocalizedName("Stone Axe").setMaxDamage(MMStoneUses);
		MMHoe = new ItemCustomHoe(MMToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(MMStoneUses);

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
		Logs = new ItemLogs().setUnlocalizedName("Log");

		//javelins
		IgInStoneJavelin = new ItemJavelin(IgInToolMaterial, 60).setUnlocalizedName("Stone Javelin");
		SedStoneJavelin = new ItemJavelin(SedToolMaterial, 60).setUnlocalizedName("Stone Javelin");
		IgExStoneJavelin = new ItemJavelin(IgExToolMaterial, 60).setUnlocalizedName("Stone Javelin");
		MMStoneJavelin = new ItemJavelin(MMToolMaterial, 60).setUnlocalizedName("Stone Javelin");
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
		IgInStoneJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Stone Javelin Head");
		SedStoneJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Stone Javelin Head");
		IgExStoneJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Stone Javelin Head");
		MMStoneJavelinHead = new ItemMiscToolHead().setUnlocalizedName("Stone Javelin Head");
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
		StoneHammer = new ItemHammer(IgInToolMaterial).setUnlocalizedName("Stone Hammer").setMaxDamage(IgInStoneUses);
		BismuthBronzeHammer = new ItemHammer(BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Hammer").setMaxDamage(BismuthBronzeUses);
		BlackBronzeHammer = new ItemHammer(BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Hammer").setMaxDamage(BlackBronzeUses);
		BlackSteelHammer = new ItemHammer(BlackSteelToolMaterial).setUnlocalizedName("Black Steel Hammer").setMaxDamage(BlackSteelUses);
		BlueSteelHammer = new ItemHammer(BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Hammer").setMaxDamage(BlueSteelUses);
		BronzeHammer = new ItemHammer(BronzeToolMaterial).setUnlocalizedName("Bronze Hammer").setMaxDamage(BronzeUses);
		CopperHammer = new ItemHammer(CopperToolMaterial).setUnlocalizedName("Copper Hammer").setMaxDamage(CopperUses);
		WroughtIronHammer = new ItemHammer(IronToolMaterial).setUnlocalizedName("Wrought Iron Hammer").setMaxDamage(WroughtIronUses);
		RedSteelHammer = new ItemHammer(RedSteelToolMaterial).setUnlocalizedName("Red Steel Hammer").setMaxDamage(RedSteelUses);
		SteelHammer = new ItemHammer(SteelToolMaterial).setUnlocalizedName("Steel Hammer").setMaxDamage(SteelUses);

		Ink = new ItemTerra().setUnlocalizedName("Ink");
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
		Coke = (new ItemTerra().setUnlocalizedName("Coke"));

		BismuthBronzeProPickHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze ProPick Head");
		BlackBronzeProPickHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze ProPick Head");
		BlackSteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel ProPick Head");
		BlueSteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel ProPick Head");
		BronzeProPickHead = new ItemMiscToolHead().setUnlocalizedName("Bronze ProPick Head");
		CopperProPickHead = new ItemMiscToolHead().setUnlocalizedName("Copper ProPick Head");
		WroughtIronProPickHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron ProPick Head");
		RedSteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel ProPick Head");
		SteelProPickHead = new ItemMiscToolHead().setUnlocalizedName("Steel ProPick Head");

		Powder = new ItemTerra().setMetaNames(Global.POWDER).setUnlocalizedName("Powder");

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

		WoodenBucketEmpty = (new ItemCustomBucket(0)).setUnlocalizedName("Wooden Bucket Empty");
		WoodenBucketWater = (new ItemCustomBucket(1)).setUnlocalizedName("Wooden Bucket Water").setContainerItem(WoodenBucketEmpty);
		WoodenBucketMilk = (new ItemCustomBucketMilk()).setUnlocalizedName("Wooden Bucket Milk").setContainerItem(WoodenBucketEmpty);

		BismuthBronzeKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Bismuth Bronze Knife Blade");
		BlackBronzeKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Black Bronze Knife Blade");
		BlackSteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Black Steel Knife Blade");
		BlueSteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Blue Steel Knife Blade");
		BronzeKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Bronze Knife Blade");
		CopperKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Copper Knife Blade");
		WroughtIronKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Wrought Iron Knife Blade");
		RedSteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Red Steel Knife Blade");
		SteelKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Steel Knife Blade");

		BismuthBronzeKnife = new ItemCustomKnife(BismuthBronzeToolMaterial, 155).setUnlocalizedName("Bismuth Bronze Knife").setMaxDamage(BismuthBronzeUses);
		BlackBronzeKnife = new ItemCustomKnife(BlackBronzeToolMaterial, 	165).setUnlocalizedName("Black Bronze Knife").setMaxDamage(BlackBronzeUses);
		BlackSteelKnife = new ItemCustomKnife(BlackSteelToolMaterial, 		205).setUnlocalizedName("Black Steel Knife").setMaxDamage(BlackSteelUses);
		BlueSteelKnife = new ItemCustomKnife(BlueSteelToolMaterial, 		250).setUnlocalizedName("Blue Steel Knife").setMaxDamage(BlueSteelUses);
		BronzeKnife = new ItemCustomKnife(BronzeToolMaterial, 				150).setUnlocalizedName("Bronze Knife").setMaxDamage(BronzeUses);
		CopperKnife = new ItemCustomKnife(CopperToolMaterial, 				100).setUnlocalizedName("Copper Knife").setMaxDamage(CopperUses);
		WroughtIronKnife = new ItemCustomKnife(IronToolMaterial, 			175).setUnlocalizedName("Wrought Iron Knife").setMaxDamage(WroughtIronUses);
		RedSteelKnife = new ItemCustomKnife(RedSteelToolMaterial, 			250).setUnlocalizedName("Red Steel Knife").setMaxDamage(RedSteelUses);
		SteelKnife = new ItemCustomKnife(SteelToolMaterial,					200).setUnlocalizedName("Steel Knife").setMaxDamage(SteelUses);

		FlatRock = (new ItemFlatGeneric().setFolder("rocks/flatrocks/").setMetaNames(Global.STONE_ALL).setUnlocalizedName("FlatRock"));
		LooseRock = (new ItemLooseRock().setSpecialCraftingType(FlatRock).setFolder("rocks/").setMetaNames(Global.STONE_ALL).setUnlocalizedName("LooseRock"));

		IgInStoneShovelHead = new ItemMiscToolHead().setUnlocalizedName("Stone Shovel Head");
		SedStoneShovelHead = new ItemMiscToolHead().setUnlocalizedName("Stone Shovel Head");
		IgExStoneShovelHead = new ItemMiscToolHead().setUnlocalizedName("Stone Shovel Head");
		MMStoneShovelHead = new ItemMiscToolHead().setUnlocalizedName("Stone Shovel Head");

		IgInStoneAxeHead = new ItemMiscToolHead().setUnlocalizedName("Stone Axe Head");
		SedStoneAxeHead = new ItemMiscToolHead().setUnlocalizedName("Stone Axe Head");
		IgExStoneAxeHead = new ItemMiscToolHead().setUnlocalizedName("Stone Axe Head");
		MMStoneAxeHead = new ItemMiscToolHead().setUnlocalizedName("Stone Axe Head");

		IgInStoneHoeHead = new ItemMiscToolHead().setUnlocalizedName("Stone Hoe Head");
		SedStoneHoeHead = new ItemMiscToolHead().setUnlocalizedName("Stone Hoe Head");
		IgExStoneHoeHead = new ItemMiscToolHead().setUnlocalizedName("Stone Hoe Head");
		MMStoneHoeHead = new ItemMiscToolHead().setUnlocalizedName("Stone Hoe Head");

		StoneKnifeHead = new ItemMiscToolHead().setUnlocalizedName("Stone Knife Blade");
		StoneHammerHead = new ItemMiscToolHead().setUnlocalizedName("Stone Hammer Head");

		StoneKnife = new ItemCustomKnife(IgExToolMaterial, 75).setUnlocalizedName("Stone Knife").setMaxDamage(IgExStoneUses);
		SmallOreChunk = new ItemOreSmall().setUnlocalizedName("Small Ore");
		SinglePlank = new ItemPlank().setUnlocalizedName("SinglePlank");

		RedSteelBucketEmpty = (new ItemCustomRedSteelBucket(Blocks.air)).setUnlocalizedName("Red Steel Bucket Empty");
		RedSteelBucketWater = (new ItemCustomRedSteelBucket(Blocks.flowing_water)).setUnlocalizedName("Red Steel Bucket Water").setContainerItem(RedSteelBucketEmpty);

		BlueSteelBucketEmpty = (new ItemCustomBlueSteelBucket(Blocks.air)).setUnlocalizedName("Blue Steel Bucket Empty");
		BlueSteelBucketLava = (new ItemCustomBlueSteelBucket(Blocks.flowing_lava)).setUnlocalizedName("Blue Steel Bucket Lava").setContainerItem(BlueSteelBucketEmpty);

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

		Beer = new ItemAlcohol().setUnlocalizedName("Beer").setCreativeTab(CreativeTabs.tabFood);
		Cider = new ItemAlcohol().setUnlocalizedName("Cider").setCreativeTab(CreativeTabs.tabFood);
		Rum = new ItemAlcohol().setUnlocalizedName("Rum").setCreativeTab(CreativeTabs.tabFood);
		RyeWhiskey = new ItemAlcohol().setUnlocalizedName("RyeWhiskey").setCreativeTab(CreativeTabs.tabFood);
		Sake = new ItemAlcohol().setUnlocalizedName("Sake").setCreativeTab(CreativeTabs.tabFood);
		Vodka = new ItemAlcohol().setUnlocalizedName("Vodka").setCreativeTab(CreativeTabs.tabFood);
		Whiskey = new ItemAlcohol().setUnlocalizedName("Whiskey").setCreativeTab(CreativeTabs.tabFood);

		Blueprint = new ItemBlueprint();
		writabeBookTFC = new ItemWritableBookTFC("Fix Me I'm Broken").setUnlocalizedName("book");
		WoolYarn = new ItemTerra().setUnlocalizedName("WoolYarn");
		Wool = new ItemTerra().setUnlocalizedName("Wool").setCreativeTab(TFCTabs.TFCMaterials);
		WoolCloth = new ItemTerra().setUnlocalizedName("WoolCloth").setCreativeTab(TFCTabs.TFCMaterials);
		Spindle = new ItemSpindle().setUnlocalizedName("Spindle").setCreativeTab(TFCTabs.TFCMaterials);

		SpindleHead = new ItemPotteryBase().setMetaNames(new String[]{"Clay Spindle", "Spindle Head"}).setUnlocalizedName("Spindle").setCreativeTab(TFCTabs.TFCMaterials);
		StoneBrick = (new ItemStoneBrick().setFolder("tools/").setUnlocalizedName("ItemStoneBrick"));
		Mortar = new ItemTerra().setFolder("tools/").setUnlocalizedName("Mortar").setCreativeTab(TFCTabs.TFCMaterials);
		Limewater = new ItemCustomBucket(2).setFolder("tools/").setUnlocalizedName("Lime Water").setContainerItem(WoodenBucketEmpty).setCreativeTab(TFCTabs.TFCMaterials);
		Vinegar = new ItemCustomBucket(12).setFolder("food/").setUnlocalizedName("Vinegar").setContainerItem(WoodenBucketEmpty).setCreativeTab(TFCTabs.TFCMaterials);
		Hide = new ItemRawHide().setFolder("tools/").setUnlocalizedName("Hide").setCreativeTab(TFCTabs.TFCMaterials);
		SoakedHide = new ItemTerra().setFolder("tools/").setUnlocalizedName("Soaked Hide").setCreativeTab(TFCTabs.TFCMaterials);
		ScrapedHide = new ItemTerra().setFolder("tools/").setUnlocalizedName("Scraped Hide").setCreativeTab(TFCTabs.TFCMaterials);
		PrepHide = new ItemTerra().setFolder("tools/").setFolder("tools/").setUnlocalizedName("Prep Hide").setCreativeTab(TFCTabs.TFCMaterials);

		SheepSkin = new ItemTerra().setFolder("tools/").setUnlocalizedName("Sheep Skin").setCreativeTab(TFCTabs.TFCMaterials);
		FlatLeather = (new ItemFlatGeneric().setFolder("tools/").setUnlocalizedName("Flat Leather"));
		TerraLeather = new ItemLeather().setSpecialCraftingType(FlatLeather).setFolder("tools/").setUnlocalizedName("TFC Leather");

		Straw = new ItemTerra().setFolder("plants/").setUnlocalizedName("Straw");
		FlatClay = (new ItemFlatGeneric().setFolder("pottery/").setMetaNames(new String[]{"clay flat light", "clay flat dark", "clay flat fire", "clay flat dark fire"}).setUnlocalizedName(""));

		PotteryJug = new ItemPotteryJug().setUnlocalizedName("Jug");
		PotterySmallVessel = new ItemPotterySmallVessel().setUnlocalizedName("Small Vessel");
		PotteryLargeVessel = new ItemPotteryLargeVessel().setUnlocalizedName("Large Vessel");
		PotteryPot = new ItemPotteryPot().setUnlocalizedName("Pot");
		CeramicMold = new ItemPotteryBase().setMetaNames(new String[]{"Clay Mold","Ceramic Mold"}).setUnlocalizedName("Mold");
		
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.clay_ball), "clay", new ItemClay().setSpecialCraftingType(FlatClay, new ItemStack(FlatClay, 1, 1)).setMetaNames(new String[]{"Clay", "Fire Clay"}).setUnlocalizedName("clay"));
		
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

		Jute = new ItemTerra().setFolder("plants/").setUnlocalizedName("Jute");
		JuteFibre = new ItemTerra().setFolder("plants/").setUnlocalizedName("Jute Fibre");

		// Plans
		PickaxeHeadPlan = new ItemPlan().setUnlocalizedName("PickaxeHeadPlan");
		ShovelHeadPlan = new ItemPlan().setUnlocalizedName("ShovelHeadPlan");
		HoeHeadPlan = new ItemPlan().setUnlocalizedName("HoeHeadPlan");
		AxeHeadPlan = new ItemPlan().setUnlocalizedName("AxeHeadPlan");
		HammerHeadPlan = new ItemPlan().setUnlocalizedName("HammerHeadPlan");
		ChiselHeadPlan = new ItemPlan().setUnlocalizedName("ChiselHeadPlan");
		SwordBladePlan = new ItemPlan().setUnlocalizedName("SwordBladePlan");
		MaceHeadPlan = new ItemPlan().setUnlocalizedName("MaceHeadPlan");
		SawBladePlan = new ItemPlan().setUnlocalizedName("SawBladePlan");
		ProPickHeadPlan = new ItemPlan().setUnlocalizedName("ProPickHeadPlan");
		HelmetPlan = new ItemPlan().setUnlocalizedName("HelmetPlan");
		ChestplatePlan = new ItemPlan().setUnlocalizedName("ChestplatePlan");
		GreavesPlan = new ItemPlan().setUnlocalizedName("GreavesPlan");
		BootsPlan = new ItemPlan().setUnlocalizedName("BootsPlan");
		ScythePlan = new ItemPlan().setUnlocalizedName("ScythePlan");
		KnifePlan = new ItemPlan().setUnlocalizedName("KnifePlan");
		BucketPlan = new ItemPlan().setUnlocalizedName("BucketPlan");
		JavelinHeadPlan = new ItemPlan().setUnlocalizedName("JavelinHeadPlan");

		// Food related items
		SetupFood();

		Fertilizer = new ItemFertilizer().setUnlocalizedName("Fertilizer");

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

		((TFCTabs)TFCTabs.TFCTools).setTabIconItem(SteelHammer);
		((TFCTabs)TFCTabs.TFCMaterials).setTabIconItem(LeadIngot);
		((TFCTabs)TFCTabs.TFCUnfinished).setTabIconItem(SteelHammerHead);
		((TFCTabs)TFCTabs.TFCArmor).setTabIconItem(SteelHelmet);
		((TFCTabs)TFCTabs.TFCMisc).setTabIconItem(Spindle);
		((TFCTabs)TFCTabs.TFCPottery).setTabIconItemStack(new ItemStack(PotteryJug, 1, 1));
		((TFCTabs)TFCTabs.TFCWeapons).setTabIconItemStack(new ItemStack(SteelSword, 1));

		registerMetals();

		System.out.println(new StringBuilder().append("[TFC] Done Loading Items").toString());
	}

	/**
	 * 
	 */
	private static void SetupFood()
	{
		FoodList = new ArrayList<Item>();

		Item.itemRegistry.addObject(Item.getIdFromItem(Items.egg), "egg", new ItemEgg().setSize(EnumSize.SMALL).setUnlocalizedName("egg").setTextureName("egg"));
		Egg = Items.egg;
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.porkchop), "porkchop", new ItemRawFood(-1, EnumFoodGroup.Protein, false, false).setUnlocalizedName("porkchopRaw"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.cooked_porkchop), "cooked_porkchop", new ItemFoodTFC(38, EnumFoodGroup.Protein).setUnlocalizedName("porkchopCooked"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.fish), "fish", new ItemRawFood(-1, EnumFoodGroup.Protein, false, true).setUnlocalizedName("fishRaw"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.cooked_fished), "cooked_fished", new ItemFoodTFC(39, EnumFoodGroup.Protein).setUnlocalizedName("fishCooked"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.beef), "beef", new ItemRawFood(-1, EnumFoodGroup.Protein, false, false).setUnlocalizedName("beefRaw"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.cooked_beef), "cooked_beef", new ItemFoodTFC(40, EnumFoodGroup.Protein).setUnlocalizedName("beefCooked"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.chicken), "chicken", new ItemRawFood(-1, EnumFoodGroup.Protein, false, false).setUnlocalizedName("chickenRaw"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.cooked_chicken), "cooked_chicken", new ItemFoodTFC(41, EnumFoodGroup.Protein).setUnlocalizedName("chickenCooked"));

		FruitTreeSapling1 = new ItemFruitTreeSapling(0).setUnlocalizedName("FruitSapling1");
		FruitTreeSapling2 = new ItemFruitTreeSapling(8).setUnlocalizedName("FruitSapling2");
		RedApple = new ItemRawFood(2, EnumFoodGroup.Fruit, true).setUnlocalizedName("Red Apple");
		Banana = new ItemRawFood(3, EnumFoodGroup.Fruit, true).setUnlocalizedName("Banana");
		Orange = new ItemRawFood(4, EnumFoodGroup.Fruit, true).setUnlocalizedName("Orange");
		GreenApple = new ItemRawFood(5, EnumFoodGroup.Fruit, true).setUnlocalizedName("Green Apple");
		Lemon = new ItemRawFood(6, EnumFoodGroup.Fruit, true).setUnlocalizedName("Lemon");
		Olive = new ItemRawFood(7, EnumFoodGroup.Fruit, true).setUnlocalizedName("Olive");
		Cherry = new ItemRawFood(8, EnumFoodGroup.Fruit, true).setUnlocalizedName("Cherry");
		Peach = new ItemRawFood(9, EnumFoodGroup.Fruit, true).setUnlocalizedName("Peach");
		Plum = new ItemRawFood(10, EnumFoodGroup.Fruit, true).setUnlocalizedName("Plum");
		EggCooked = new ItemFoodTFC(11, EnumFoodGroup.Protein).setUnlocalizedName("Egg Cooked");

		WheatGrain = new ItemRawFood(12, EnumFoodGroup.Grain).setUnlocalizedName("Wheat Grain");
		BarleyGrain = new ItemRawFood(14, EnumFoodGroup.Grain).setUnlocalizedName("Barley Grain");
		OatGrain = new ItemRawFood(16, EnumFoodGroup.Grain).setUnlocalizedName("Oat Grain");
		RyeGrain = new ItemRawFood(18, EnumFoodGroup.Grain).setUnlocalizedName("Rye Grain");
		RiceGrain = new ItemRawFood(20, EnumFoodGroup.Grain).setUnlocalizedName("Rice Grain");
		MaizeEar = new ItemRawFood(22, EnumFoodGroup.Grain).setUnlocalizedName("Maize Ear");

		Tomato = new ItemRawFood(24, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Tomato");
		Potato = new ItemRawFood(25, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Potato");
		Onion = new ItemRawFood(27, EnumFoodGroup.Vegetable, true).setUnlocalizedName(TFCOptions.iDontLikeOnions?"Rutabaga":"Onion");
		Cabbage = new ItemRawFood(28, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Cabbage");
		Garlic = new ItemRawFood(29, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Garlic");
		Carrot = new ItemRawFood(30, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Carrot");
		Sugarcane = new ItemTerra().setFolder("plants/").setUnlocalizedName("Sugarcane");
		Hemp = new ItemTerra().setFolder("plants/").setUnlocalizedName("Hemp");
		Soybean = new ItemRawFood(31, EnumFoodGroup.Protein, true).setUnlocalizedName("Soybeans");
		Greenbeans = new ItemRawFood(32, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Greenbeans");
		GreenBellPepper = new ItemRawFood(34, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Green Bell Pepper");
		YellowBellPepper = new ItemRawFood(35, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Yellow Bell Pepper");
		RedBellPepper = new ItemRawFood(36, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Red Bell Pepper");
		Squash = new ItemRawFood(37, EnumFoodGroup.Vegetable, true).setUnlocalizedName("Squash");
		Cheese = new ItemFoodTFC(26, EnumFoodGroup.Dairy).setUnlocalizedName("Cheese");

		WheatWhole = new ItemTerra().setFolder("food/").setUnlocalizedName("Wheat Whole");
		BarleyWhole = new ItemTerra().setFolder("food/").setUnlocalizedName("Barley Whole");
		OatWhole = new ItemTerra().setFolder("food/").setUnlocalizedName("Oat Whole");
		RyeWhole = new ItemTerra().setFolder("food/").setUnlocalizedName("Rye Whole");
		RiceWhole = new ItemTerra().setFolder("food/").setUnlocalizedName("Rice Whole");

		MealGeneric = new ItemMeal().setUnlocalizedName("MealGeneric");

		WheatGround = new ItemRawFood(-1, EnumFoodGroup.Grain, false, false).setFolder("food/").setUnlocalizedName("Wheat Ground");
		BarleyGround = new ItemRawFood(-1, EnumFoodGroup.Grain, false, false).setFolder("food/").setUnlocalizedName("Barley Ground");
		OatGround = new ItemRawFood(-1, EnumFoodGroup.Grain, false, false).setFolder("food/").setUnlocalizedName("Oat Ground");
		RyeGround = new ItemRawFood(-1, EnumFoodGroup.Grain, false, false).setFolder("food/").setUnlocalizedName("Rye Ground");
		RiceGround = new ItemRawFood(-1, EnumFoodGroup.Grain, false, false).setFolder("food/").setUnlocalizedName("Rice Ground");
		CornmealGround = new ItemRawFood(-1, EnumFoodGroup.Grain, false, false).setFolder("food/").setUnlocalizedName("Cornmeal Ground");

		WheatDough = new ItemRawFoodDough(61, EnumFoodGroup.Grain).setUnlocalizedName("Wheat Dough");
		BarleyDough = new ItemRawFoodDough(62, EnumFoodGroup.Grain).setUnlocalizedName("Barley Dough");
		OatDough = new ItemRawFoodDough(63, EnumFoodGroup.Grain).setUnlocalizedName("Oat Dough");
		RyeDough = new ItemRawFoodDough(64, EnumFoodGroup.Grain).setUnlocalizedName("Rye Dough");
		RiceDough = new ItemRawFoodDough(65, EnumFoodGroup.Grain).setUnlocalizedName("Rice Dough");
		CornmealDough = new ItemRawFoodDough(66, EnumFoodGroup.Grain).setUnlocalizedName("Cornmeal Dough");

		WheatBread = new ItemFoodTFC(42, EnumFoodGroup.Grain).setUnlocalizedName("Wheat Bread");
		BarleyBread = new ItemFoodTFC(43, EnumFoodGroup.Grain).setUnlocalizedName("Barley Bread");
		OatBread = new ItemFoodTFC(44, EnumFoodGroup.Grain).setUnlocalizedName("Oat Bread");
		RyeBread = new ItemFoodTFC(45, EnumFoodGroup.Grain).setUnlocalizedName("Rye Bread");
		RiceBread = new ItemFoodTFC(46, EnumFoodGroup.Grain).setUnlocalizedName("Rice Bread");
		CornBread = new ItemFoodTFC(47, EnumFoodGroup.Grain).setUnlocalizedName("Corn Bread");

		CalamariRaw = new ItemRawFood(-1, EnumFoodGroup.Protein, false, false).setUnlocalizedName("Calamari Raw");
		CalamariCooked = new ItemFoodTFC(49, EnumFoodGroup.Protein).setUnlocalizedName("Calamari Cooked");

		SeedsWheat = new ItemCustomSeeds(0).setUnlocalizedName("Seeds Wheat");
		SeedsBarley = new ItemCustomSeeds(5).setUnlocalizedName("Seeds Barley");
		SeedsRye = new ItemCustomSeeds(7).setUnlocalizedName("Seeds Rye");
		SeedsOat = new ItemCustomSeeds(9).setUnlocalizedName("Seeds Oat");
		SeedsRice = new ItemCustomSeeds(11).setUnlocalizedName("Seeds Rice");
		SeedsMaize = new ItemCustomSeeds(2).setUnlocalizedName("Seeds Maize");
		SeedsPotato = new ItemCustomSeeds(13).setUnlocalizedName("Seeds Potato");
		SeedsOnion = new ItemCustomSeeds(15).setUnlocalizedName(TFCOptions.iDontLikeOnions?"Seeds Rutabaga":"Seeds Onion");
		SeedsCabbage = new ItemCustomSeeds(16).setUnlocalizedName("Seeds Cabbage");
		SeedsGarlic = new ItemCustomSeeds(17).setUnlocalizedName("Seeds Garlic");
		SeedsCarrot = new ItemCustomSeeds(18).setUnlocalizedName("Seeds Carrot");
		SeedsSugarcane = new ItemCustomSeeds(21).setUnlocalizedName("Seeds Sugarcane");
		SeedsHemp = new ItemCustomSeeds(22).setUnlocalizedName("Seeds Hemp");
		SeedsTomato = new ItemCustomSeeds(4).setUnlocalizedName("Seeds Tomato");
		SeedsYellowBellPepper = new ItemCustomSeeds(19).setUnlocalizedName("Seeds Yellow Bell Pepper");
		SeedsRedBellPepper = new ItemCustomSeeds(20).setUnlocalizedName("Seeds Red Bell Pepper");
		SeedsSoybean = new ItemCustomSeeds(21).setUnlocalizedName("Seeds Soybean");
		SeedsGreenbean = new ItemCustomSeeds(22).setUnlocalizedName("Seeds Greenbean");
		SeedsSquash = new ItemCustomSeeds(23).setUnlocalizedName("Seeds Squash");

		muttonRaw = new ItemRawFood(-1, EnumFoodGroup.Protein, false, false).setUnlocalizedName("Mutton Raw");
		muttonCooked =  new ItemFoodTFC(48, EnumFoodGroup.Protein).setUnlocalizedName("Mutton Cooked");
		venisonRaw = new ItemRawFood(-1, EnumFoodGroup.Protein, false, false).setUnlocalizedName("Venison");
		venisonCooked =  new ItemFoodTFC(49, EnumFoodGroup.Protein).setUnlocalizedName("VenisonCooked");

		WintergreenBerry =  new ItemFoodTFC(50, EnumFoodGroup.Fruit).setUnlocalizedName("Wintergreen Berry");
		Blueberry =  new ItemFoodTFC(51, EnumFoodGroup.Fruit).setUnlocalizedName("Blueberry");
		Raspberry =  new ItemFoodTFC(52, EnumFoodGroup.Fruit).setUnlocalizedName("Raspberry");
		Strawberry =  new ItemFoodTFC(53, EnumFoodGroup.Fruit).setUnlocalizedName("Strawberry");
		Blackberry =  new ItemFoodTFC(54, EnumFoodGroup.Fruit).setUnlocalizedName("Blackberry");
		Bunchberry =  new ItemFoodTFC(55, EnumFoodGroup.Fruit).setUnlocalizedName("Bunchberry");
		Cranberry =  new ItemFoodTFC(56, EnumFoodGroup.Fruit).setUnlocalizedName("Cranberry");
		Snowberry =  new ItemFoodTFC(57, EnumFoodGroup.Fruit).setUnlocalizedName("Snowberry");	
		Elderberry =  new ItemFoodTFC(58, EnumFoodGroup.Fruit).setUnlocalizedName("Elderberry");
		Gooseberry =  new ItemFoodTFC(59, EnumFoodGroup.Fruit).setUnlocalizedName("Gooseberry");
		Cloudberry =  new ItemFoodTFC(60, EnumFoodGroup.Fruit).setUnlocalizedName("Cloudberry");

		//mushroom is a food now, with foodID 61
		//pumpkin is a food now, id = 61
		//melon is a food, not currently obtainable. id = 62. See ItemFoodBlock
		WintergreenLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Wintergreen Leaf");	
		BlueberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Blueberry Leaf");
		RaspberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Raspberry Leaf");
		StrawberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Strawberry Leaf");
		BlackberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Blackberry Leaf");
		BunchberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Bunchberry Leaf");
		CranberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Cranberry Leaf");
		SnowberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Snowberry Leaf");
		ElderberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Elderberry Leaf");
		GooseberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Gooseberry Leaf");
		CloudberryLeaf =  new ItemTerra().setFolder("plants/").setUnlocalizedName("Cloudberry Leaf");
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
		Global.UNKNOWN = new Metal("Unknown", UnknownUnshaped, UnknownIngot);

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

		BismuthSheet = 			(new ItemMetalSheet().setUnlocalizedName("Bismuth Sheet"));
		BismuthBronzeSheet = 	(new ItemMetalSheet().setUnlocalizedName("Bismuth Bronze Sheet"));
		BlackBronzeSheet = 		(new ItemMetalSheet().setUnlocalizedName("Black Bronze Sheet"));
		BlackSteelSheet = 		(new ItemMetalSheet().setUnlocalizedName("Black Steel Sheet"));
		BlueSteelSheet = 		(new ItemMetalSheet().setUnlocalizedName("Blue Steel Sheet"));
		BronzeSheet = 			(new ItemMetalSheet().setUnlocalizedName("Bronze Sheet"));
		CopperSheet = 			(new ItemMetalSheet().setUnlocalizedName("Copper Sheet"));
		WroughtIronSheet = 		(new ItemMetalSheet().setUnlocalizedName("Wrought Iron Sheet"));
		RedSteelSheet = 		(new ItemMetalSheet().setUnlocalizedName("Red Steel Sheet"));
		RoseGoldSheet = 		(new ItemMetalSheet().setUnlocalizedName("Rose Gold Sheet"));
		SteelSheet = 			(new ItemMetalSheet().setUnlocalizedName("Steel Sheet"));
		TinSheet = 				(new ItemMetalSheet().setUnlocalizedName("Tin Sheet"));
		ZincSheet = 			(new ItemMetalSheet().setUnlocalizedName("Zinc Sheet"));

		BismuthSheet2x = 		(new ItemMetalSheet2x().setUnlocalizedName("Bismuth Double Sheet"));
		BismuthBronzeSheet2x = 	(new ItemMetalSheet2x().setUnlocalizedName("Bismuth Bronze Double Sheet"));
		BlackBronzeSheet2x = 	(new ItemMetalSheet2x().setUnlocalizedName("Black Bronze Double Sheet"));
		BlackSteelSheet2x = 	(new ItemMetalSheet2x().setUnlocalizedName("Black Steel Double Sheet"));
		BlueSteelSheet2x = 		(new ItemMetalSheet2x().setUnlocalizedName("Blue Steel Double Sheet"));
		BronzeSheet2x = 		(new ItemMetalSheet2x().setUnlocalizedName("Bronze Double Sheet"));
		CopperSheet2x = 		(new ItemMetalSheet2x().setUnlocalizedName("Copper Double Sheet"));
		WroughtIronSheet2x = 	(new ItemMetalSheet2x().setUnlocalizedName("Wrought Iron Double Sheet"));
		RedSteelSheet2x = 		(new ItemMetalSheet2x().setUnlocalizedName("Red Steel Double Sheet"));
		RoseGoldSheet2x = 		(new ItemMetalSheet2x().setUnlocalizedName("Rose Gold Double Sheet"));
		SteelSheet2x = 			(new ItemMetalSheet2x().setUnlocalizedName("Steel Double Sheet"));
		TinSheet2x = 			(new ItemMetalSheet2x().setUnlocalizedName("Tin Double Sheet"));
		ZincSheet2x = 			(new ItemMetalSheet2x().setUnlocalizedName("Zinc Double Sheet"));

		BismuthBronzeUnfinishedBoots = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		BlackBronzeUnfinishedBoots = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		BlackSteelUnfinishedBoots = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		BlueSteelUnfinishedBoots = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		BronzeUnfinishedBoots = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		CopperUnfinishedBoots = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		WroughtIronUnfinishedBoots = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		RedSteelUnfinishedBoots = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		SteelUnfinishedBoots = 			(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Boots"));

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
		BismuthBronzeUnfinishedGreaves = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		BlackBronzeUnfinishedGreaves = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		BlackSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		BlueSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		BronzeUnfinishedGreaves = 			(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		CopperUnfinishedGreaves = 			(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		WroughtIronUnfinishedGreaves = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		RedSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		SteelUnfinishedGreaves = 			(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Greaves"));

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
		BismuthBronzeUnfinishedChestplate = (new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		BlackBronzeUnfinishedChestplate = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		BlackSteelUnfinishedChestplate = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		BlueSteelUnfinishedChestplate = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		BronzeUnfinishedChestplate = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		CopperUnfinishedChestplate = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		WroughtIronUnfinishedChestplate = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		RedSteelUnfinishedChestplate = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		SteelUnfinishedChestplate = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Chestplate"));

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
		BismuthBronzeUnfinishedHelmet = 	(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		BlackBronzeUnfinishedHelmet = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		BlackSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		BlueSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		BronzeUnfinishedHelmet = 			(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		CopperUnfinishedHelmet = 			(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		WroughtIronUnfinishedHelmet = 		(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		RedSteelUnfinishedHelmet = 			(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		SteelUnfinishedHelmet = 			(new ItemUnfinishedArmor().setUnlocalizedName(Names[i]+" Unfinished Helmet"));

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

		i = 0;
		BrassSheet = 			new ItemMetalSheet().setUnlocalizedName(NamesNSO[i++]+" Sheet");
		GoldSheet = 			new ItemMetalSheet().setUnlocalizedName(NamesNSO[i++]+" Sheet");
		LeadSheet = 			new ItemMetalSheet().setUnlocalizedName(NamesNSO[i++]+" Sheet");
		NickelSheet = 			new ItemMetalSheet().setUnlocalizedName(NamesNSO[i++]+" Sheet");
		PigIronSheet = 			new ItemMetalSheet().setUnlocalizedName(NamesNSO[i++]+" Sheet");
		PlatinumSheet = 		new ItemMetalSheet().setUnlocalizedName(NamesNSO[i++]+" Sheet");
		SilverSheet = 			new ItemMetalSheet().setUnlocalizedName(NamesNSO[i++]+" Sheet");
		SterlingSilverSheet = 	new ItemMetalSheet().setUnlocalizedName(NamesNSO[i++]+" Sheet");

		i = 0;
		BrassSheet2x = 			new ItemMetalSheet2x().setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		GoldSheet2x = 			new ItemMetalSheet2x().setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		LeadSheet2x = 			new ItemMetalSheet2x().setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		NickelSheet2x = 		new ItemMetalSheet2x().setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		PigIronSheet2x = 		new ItemMetalSheet2x().setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		PlatinumSheet2x = 		new ItemMetalSheet2x().setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		SilverSheet2x = 		new ItemMetalSheet2x().setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		SterlingSilverSheet2x = new ItemMetalSheet2x().setUnlocalizedName(NamesNSO[i++]+" Double Sheet");

		Item.itemRegistry.addObject(Item.getIdFromItem(Items.leather_helmet), "leather_helmet", new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 0, ArmorMaterial.CLOTH, 100,3).setUnlocalizedName("helmetCloth").setTextureName("leather_helmet"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.leather_helmet), "leather_chestplate", new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 1, ArmorMaterial.CLOTH, 100,2).setUnlocalizedName("chestplateCloth").setTextureName("leather_chestplate"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.leather_helmet), "leather_leggings", new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 2, ArmorMaterial.CLOTH, 100,1).setUnlocalizedName("leggingsCloth").setTextureName("leather_leggings"));
		Item.itemRegistry.addObject(Item.getIdFromItem(Items.leather_helmet), "leather_boots", new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 3, ArmorMaterial.CLOTH, 100,0).setUnlocalizedName("bootsCloth").setTextureName("leather_boots"));
		
		Quiver = new ItemQuiver(Armor.LeatherQuiver, proxy.getArmorRenderID("leather"), 4, ArmorMaterial.IRON, 0,-1).setUnlocalizedName("Quiver");
	}

	public static Item[] Meals;
}
