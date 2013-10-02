package TFC;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemColored;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.EnumHelper;
import TFC.API.Armor;
import TFC.API.Metal;
import TFC.API.TFCOptions;
import TFC.API.Constant.Global;
import TFC.API.Constant.TFCItemID;
import TFC.API.Enums.EnumDamageType;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Recipes;
import TFC.Core.TFCTabs;
import TFC.Core.Metal.Alloy;
import TFC.Core.Metal.AlloyManager;
import TFC.Core.Metal.MetalRegistry;
import TFC.Food.ItemMeal;
import TFC.Food.ItemTerraFood;
import TFC.Items.ItemAlcohol;
import TFC.Items.ItemBloom;
import TFC.Items.ItemBlueprint;
import TFC.Items.ItemClay;
import TFC.Items.ItemCustomMinecart;
import TFC.Items.ItemCustomPotion;
import TFC.Items.ItemCustomSeeds;
import TFC.Items.ItemDyeCustom;
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
	public static Item MealMoveSpeed;
	public static Item MealDigSpeed;
	public static Item MealDamageBoost;
	public static Item MealJump;
	public static Item MealDamageResist;
	public static Item MealFireResist;
	public static Item MealWaterBreathing;
	public static Item MealNightVision;

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


	public static EnumToolMaterial IgInToolMaterial;
	public static EnumToolMaterial SedToolMaterial;
	public static EnumToolMaterial IgExToolMaterial;
	public static EnumToolMaterial MMToolMaterial;

	public static EnumToolMaterial BismuthBronzeToolMaterial;
	public static EnumToolMaterial BlackBronzeToolMaterial;
	public static EnumToolMaterial BlackSteelToolMaterial;
	public static EnumToolMaterial BlueSteelToolMaterial;
	public static EnumToolMaterial BronzeToolMaterial;
	public static EnumToolMaterial CopperToolMaterial;
	public static EnumToolMaterial IronToolMaterial;
	public static EnumToolMaterial RedSteelToolMaterial;
	public static EnumToolMaterial SteelToolMaterial;


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
		Item.itemsList[Item.coal.itemID] = null; Item.itemsList[Item.coal.itemID] = (new TFC.Items.ItemCoal(7)).setUnlocalizedName("coal");
		Item.itemsList[Item.stick.itemID] = null; Item.itemsList[Item.stick.itemID] = new ItemStick(24).setFull3D().setUnlocalizedName("stick");
		Item.itemsList[Item.leather.itemID] = null; Item.itemsList[Item.leather.itemID] = new ItemTerra(Item.leather.itemID).setFull3D().setUnlocalizedName("leather");
		Item.itemsList[Block.vine.blockID] = new ItemColored(Block.vine.blockID - 256, false);
		Item.itemsList[Item.egg.itemID] = new ItemTerra(88).setSize(EnumSize.SMALL).setUnlocalizedName("egg").setTextureName("egg");

		minecartCrate = (new ItemCustomMinecart(TFCItemID.minecartCrate, 1)).setUnlocalizedName("minecartChest").setTextureName("minecart_chest");

		Item.itemsList[Item.bow.itemID] = null; Item.itemsList[Item.bow.itemID] = (new ItemCustomBow(5)).setUnlocalizedName("bow").setTextureName("bow");
		Item.itemsList[Item.porkRaw.itemID] = null; Item.itemsList[Item.porkRaw.itemID] = new ItemTerra(63).setUnlocalizedName("porkchopRaw");
		Item.itemsList[Item.porkCooked.itemID] = null; Item.itemsList[Item.porkCooked.itemID] = new ItemTerraFood(64, 35, 0.8F, true, 38).setFolder("").setUnlocalizedName("porkchopCooked");
		Item.itemsList[Item.fishRaw.itemID] = null; Item.itemsList[Item.fishRaw.itemID] = new ItemTerra(93).setUnlocalizedName("fishRaw");
		Item.itemsList[Item.fishCooked.itemID] = null; Item.itemsList[Item.fishCooked.itemID] = new ItemTerraFood(94, 30, 0.6F, true, 39).setFolder("").setUnlocalizedName("fishCooked");
		Item.itemsList[Item.beefRaw.itemID] = null; Item.itemsList[Item.beefRaw.itemID] = new ItemTerra(107).setUnlocalizedName("beefRaw");
		Item.itemsList[Item.beefCooked.itemID] = null; Item.itemsList[Item.beefCooked.itemID] = new ItemTerraFood(108, 40, 0.8F, true, 40).setFolder("").setUnlocalizedName("beefCooked");
		Item.itemsList[Item.chickenRaw.itemID] = null; Item.itemsList[Item.chickenRaw.itemID] = new ItemTerra(109).setUnlocalizedName("chickenRaw");
		Item.itemsList[Item.chickenCooked.itemID] = null; Item.itemsList[Item.chickenCooked.itemID] = new ItemTerraFood(110, 35, 0.6F, true, 41).setFolder("").setUnlocalizedName("chickenCooked");
		//Item.itemsList[41+256] = null; Item.itemsList[41+256] = (new ItemTerraFood(41, 25, 0.6F, false, 42)).setFolder("").setUnlocalizedName("bread");
		Item.itemsList[Item.dyePowder.itemID] = null; Item.itemsList[Item.dyePowder.itemID] = new ItemDyeCustom(95).setUnlocalizedName("dyePowder").setTextureName("dye_powder");
		Item.itemsList[Item.potion.itemID] = null; Item.itemsList[Item.potion.itemID] = (new ItemCustomPotion(117)).setUnlocalizedName("potion").setTextureName("potion");

		Item.itemsList[Block.tallGrass.blockID] = null; Item.itemsList[Block.tallGrass.blockID] = (new ItemColored(Block.tallGrass.blockID - 256, true)).setBlockNames(new String[] {"shrub", "grass", "fern"});

		GoldPan = new ItemGoldPan(TFCItemID.GoldPan).setUnlocalizedName("GoldPan");
		SluiceItem = new ItemSluice(TFCItemID.SluiceItem).setFolder("devices/").setUnlocalizedName("SluiceItem");

		ProPickBismuthBronze = new ItemProPick(TFCItemID.ProPickBismuthBronze).setUnlocalizedName("Bismuth Bronze ProPick").setMaxDamage(BismuthBronzeUses);
		ProPickBlackBronze = new ItemProPick(TFCItemID.ProPickBlackBronze).setUnlocalizedName("Black Bronze ProPick").setMaxDamage(BlackBronzeUses);
		ProPickBlackSteel = new ItemProPick(TFCItemID.ProPickBlackSteel).setUnlocalizedName("Black Steel ProPick").setMaxDamage(BlackSteelUses);
		ProPickBlueSteel = new ItemProPick(TFCItemID.ProPickBlueSteel).setUnlocalizedName("Blue Steel ProPick").setMaxDamage(BlueSteelUses);
		ProPickBronze = new ItemProPick(TFCItemID.ProPickBronze).setUnlocalizedName("Bronze ProPick").setMaxDamage(BronzeUses);
		ProPickCopper = new ItemProPick(TFCItemID.ProPickCopper).setUnlocalizedName("Copper ProPick").setMaxDamage(CopperUses);
		ProPickIron = new ItemProPick(TFCItemID.ProPickWroughtIron).setUnlocalizedName("Wrought Iron ProPick").setMaxDamage(WroughtIronUses);
		ProPickRedSteel = new ItemProPick(TFCItemID.ProPickRedSteel).setUnlocalizedName("Red Steel ProPick").setMaxDamage(RedSteelUses);
		ProPickSteel = new ItemProPick(TFCItemID.ProPickSteel).setUnlocalizedName("Steel ProPick").setMaxDamage(SteelUses);

		BismuthIngot = new ItemIngot(TFCItemID.BismuthIngot).setUnlocalizedName("Bismuth Ingot");
		BismuthBronzeIngot = new ItemIngot(TFCItemID.BismuthBronzeIngot).setUnlocalizedName("Bismuth Bronze Ingot");
		BlackBronzeIngot = new ItemIngot(TFCItemID.BlackBronzeIngot).setUnlocalizedName("Black Bronze Ingot");
		BlackSteelIngot = new ItemIngot(TFCItemID.BlackSteelIngot).setUnlocalizedName("Black Steel Ingot");
		BlueSteelIngot = new ItemIngot(TFCItemID.BlueSteelIngot).setUnlocalizedName("Blue Steel Ingot");
		BrassIngot = new ItemIngot(TFCItemID.BrassIngot).setUnlocalizedName("Brass Ingot");
		BronzeIngot = new ItemIngot(TFCItemID.BronzeIngot).setUnlocalizedName("Bronze Ingot");
		CopperIngot = new ItemIngot(TFCItemID.CopperIngot).setUnlocalizedName("Copper Ingot");
		GoldIngot = new ItemIngot(TFCItemID.GoldIngot).setUnlocalizedName("Gold Ingot");
		WroughtIronIngot = new ItemIngot(TFCItemID.WroughtIronIngot).setUnlocalizedName("Wrought Iron Ingot");
		LeadIngot = new ItemIngot(TFCItemID.LeadIngot).setUnlocalizedName("Lead Ingot");
		NickelIngot = new ItemIngot(TFCItemID.NickelIngot).setUnlocalizedName("Nickel Ingot");
		PigIronIngot = new ItemIngot(TFCItemID.PigIronIngot).setUnlocalizedName("Pig Iron Ingot");
		PlatinumIngot = new ItemIngot(TFCItemID.PlatinumIngot).setUnlocalizedName("Platinum Ingot");
		RedSteelIngot = new ItemIngot(TFCItemID.RedSteelIngot).setUnlocalizedName("Red Steel Ingot");
		RoseGoldIngot = new ItemIngot(TFCItemID.RoseGoldIngot).setUnlocalizedName("Rose Gold Ingot");
		SilverIngot = new ItemIngot(TFCItemID.SilverIngot).setUnlocalizedName("Silver Ingot");
		SteelIngot = new ItemIngot(TFCItemID.SteelIngot).setUnlocalizedName("Steel Ingot");
		SterlingSilverIngot = new ItemIngot(TFCItemID.SterlingSilverIngot).setUnlocalizedName("Sterling Silver Ingot");
		TinIngot = new ItemIngot(TFCItemID.TinIngot).setUnlocalizedName("Tin Ingot");
		ZincIngot = new ItemIngot(TFCItemID.ZincIngot).setUnlocalizedName("Zinc Ingot");

		BismuthIngot2x = ((ItemIngot)new ItemIngot(TFCItemID.BismuthIngot2x).setUnlocalizedName("Bismuth Double Ingot")).setSize(EnumSize.LARGE).setMetal("Bismuth", 200);
		BismuthBronzeIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.BismuthBronzeIngot2x).setUnlocalizedName("Bismuth Bronze Double Ingot")).setSize(EnumSize.LARGE).setMetal("Bismuth Bronze", 200);
		BlackBronzeIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.BlackBronzeIngot2x).setUnlocalizedName("Black Bronze Double Ingot")).setSize(EnumSize.LARGE).setMetal("Black Bronze", 200);
		BlackSteelIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.BlackSteelIngot2x).setUnlocalizedName("Black Steel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Black Steel", 200);
		BlueSteelIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.BlueSteelIngot2x).setUnlocalizedName("Blue Steel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Blue Steel", 200);
		BrassIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.BrassIngot2x).setUnlocalizedName("Brass Double Ingot")).setSize(EnumSize.LARGE).setMetal("Brass", 200);
		BronzeIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.BronzeIngot2x).setUnlocalizedName("Bronze Double Ingot")).setSize(EnumSize.LARGE).setMetal("Bronze", 200);
		CopperIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.CopperIngot2x).setUnlocalizedName("Copper Double Ingot")).setSize(EnumSize.LARGE).setMetal("Copper", 200);
		GoldIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.GoldIngot2x).setUnlocalizedName("Gold Double Ingot")).setSize(EnumSize.LARGE).setMetal("Gold", 200);
		WroughtIronIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.WroughtIronIngot2x).setUnlocalizedName("Wrought Iron Double Ingot")).setSize(EnumSize.LARGE).setMetal("Wrought Iron", 200);
		LeadIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.LeadIngot2x).setUnlocalizedName("Lead Double Ingot")).setSize(EnumSize.LARGE).setMetal("Lead", 200);
		NickelIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.NickelIngot2x).setUnlocalizedName("Nickel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Nickel", 200);
		PigIronIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.PigIronIngot2x).setUnlocalizedName("Pig Iron Double Ingot")).setSize(EnumSize.LARGE).setMetal("Pig Iron", 200);
		PlatinumIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.PlatinumIngot2x).setUnlocalizedName("Platinum Double Ingot")).setSize(EnumSize.LARGE).setMetal("Platinum", 200);
		RedSteelIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.RedSteelIngot2x).setUnlocalizedName("Red Steel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Red Steel", 200);
		RoseGoldIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.RoseGoldIngot2x).setUnlocalizedName("Rose Gold Double Ingot")).setSize(EnumSize.LARGE).setMetal("Rose Gold", 200);
		SilverIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.SilverIngot2x).setUnlocalizedName("Silver Double Ingot")).setSize(EnumSize.LARGE).setMetal("Silver", 200);
		SteelIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.SteelIngot2x).setUnlocalizedName("Steel Double Ingot")).setSize(EnumSize.LARGE).setMetal("Steel", 200);
		SterlingSilverIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.SterlingSilverIngot2x).setUnlocalizedName("Sterling Silver Double Ingot")).setSize(EnumSize.LARGE).setMetal("Sterling Silver", 200);
		TinIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.TinIngot2x).setUnlocalizedName("Tin Double Ingot")).setSize(EnumSize.LARGE).setMetal("Tin", 200);
		ZincIngot2x  = ((ItemIngot)new ItemIngot(TFCItemID.ZincIngot2x).setUnlocalizedName("Zinc Double Ingot")).setSize(EnumSize.LARGE).setMetal("Zinc", 200);

		GemRuby = new ItemGem(TFCItemID.GemRuby).setUnlocalizedName("Ruby");
		GemSapphire = new ItemGem(TFCItemID.GemSapphire).setUnlocalizedName("Sapphire");
		GemEmerald = new ItemGem(TFCItemID.GemEmerald).setUnlocalizedName("Emerald");
		GemTopaz = new ItemGem(TFCItemID.GemTopaz).setUnlocalizedName("Topaz");
		GemTourmaline = new ItemGem(TFCItemID.GemTourmaline).setUnlocalizedName("Tourmaline");
		GemJade = new ItemGem(TFCItemID.GemJade).setUnlocalizedName("Jade");
		GemBeryl = new ItemGem(TFCItemID.GemBeryl).setUnlocalizedName("Beryl");
		GemAgate = new ItemGem(TFCItemID.GemAgate).setUnlocalizedName("Agate");
		GemOpal = new ItemGem(TFCItemID.GemOpal).setUnlocalizedName("Opal");
		GemGarnet = new ItemGem(TFCItemID.GemGarnet).setUnlocalizedName("Garnet");
		GemJasper = new ItemGem(TFCItemID.GemJasper).setUnlocalizedName("Jasper");
		GemAmethyst = new ItemGem(TFCItemID.GemAmethyst).setUnlocalizedName("Amethyst");
		GemDiamond = new ItemGem(TFCItemID.GemDiamond).setUnlocalizedName("Diamond");

		//Tools
		IgInShovel = new ItemCustomShovel(TFCItemID.IgInShovel,IgInToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(IgInStoneUses);
		IgInAxe = new ItemCustomAxe(TFCItemID.IgInAxe,IgInToolMaterial, 75).setUnlocalizedName("Stone Axe").setMaxDamage(IgInStoneUses);
		IgInHoe = new ItemCustomHoe(TFCItemID.IgInHoe,IgInToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(IgInStoneUses);

		SedShovel= new ItemCustomShovel(TFCItemID.SedShovel,SedToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(SedStoneUses);
		SedAxe = new ItemCustomAxe(TFCItemID.SedAxe,SedToolMaterial, 75).setUnlocalizedName("Stone Axe").setMaxDamage(SedStoneUses);
		SedHoe = new ItemCustomHoe(TFCItemID.SedHoe,SedToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(SedStoneUses);

		IgExShovel= new ItemCustomShovel(TFCItemID.IgExShovel,IgExToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(IgExStoneUses);
		IgExAxe = new ItemCustomAxe(TFCItemID.IgExAxe,IgExToolMaterial, 75).setUnlocalizedName("Stone Axe").setMaxDamage(IgExStoneUses);
		IgExHoe = new ItemCustomHoe(TFCItemID.IgExHoe,IgExToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(IgExStoneUses);

		MMShovel = new ItemCustomShovel(TFCItemID.MMShovel,MMToolMaterial).setUnlocalizedName("Stone Shovel").setMaxDamage(MMStoneUses);
		MMAxe = new ItemCustomAxe(TFCItemID.MMAxe,MMToolMaterial, 75).setUnlocalizedName("Stone Axe").setMaxDamage(MMStoneUses);
		MMHoe = new ItemCustomHoe(TFCItemID.MMHoe,MMToolMaterial).setUnlocalizedName("Stone Hoe").setMaxDamage(MMStoneUses);

		BismuthBronzePick = new ItemCustomPickaxe(TFCItemID.BismuthBronzePick,BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Pick").setMaxDamage(BismuthBronzeUses);
		BismuthBronzeShovel = new ItemCustomShovel(TFCItemID.BismuthBronzeShovel,BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Shovel").setMaxDamage(BismuthBronzeUses);
		BismuthBronzeAxe = new ItemCustomAxe(TFCItemID.BismuthBronzeAxe,BismuthBronzeToolMaterial, 150).setUnlocalizedName("Bismuth Bronze Axe").setMaxDamage(BismuthBronzeUses);
		BismuthBronzeHoe = new ItemCustomHoe(TFCItemID.BismuthBronzeHoe,BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Hoe").setMaxDamage(BismuthBronzeUses);

		BlackBronzePick = new ItemCustomPickaxe(TFCItemID.BlackBronzePick,BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Pick").setMaxDamage(BlackBronzeUses);
		BlackBronzeShovel = new ItemCustomShovel(TFCItemID.BlackBronzeShovel,BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Shovel").setMaxDamage(BlackBronzeUses);
		BlackBronzeAxe = new ItemCustomAxe(TFCItemID.BlackBronzeAxe,BlackBronzeToolMaterial, 170).setUnlocalizedName("Black Bronze Axe").setMaxDamage(BlackBronzeUses);
		BlackBronzeHoe = new ItemCustomHoe(TFCItemID.BlackBronzeHoe,BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Hoe").setMaxDamage(BlackBronzeUses);

		BlackSteelPick = new ItemCustomPickaxe(TFCItemID.BlackSteelPick,BlackSteelToolMaterial).setUnlocalizedName("Black Steel Pick").setMaxDamage(BlackSteelUses);
		BlackSteelShovel = new ItemCustomShovel(TFCItemID.BlackSteelShovel,BlackSteelToolMaterial).setUnlocalizedName("Black Steel Shovel").setMaxDamage(BlackSteelUses);
		BlackSteelAxe = new ItemCustomAxe(TFCItemID.BlackSteelAxe,BlackSteelToolMaterial, 245).setUnlocalizedName("Black Steel Axe").setMaxDamage(BlackSteelUses);
		BlackSteelHoe = new ItemCustomHoe(TFCItemID.BlackSteelHoe,BlackSteelToolMaterial).setUnlocalizedName("Black Steel Hoe").setMaxDamage(BlackSteelUses);

		BlueSteelPick = new ItemCustomPickaxe(TFCItemID.BlueSteelPick,BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Pick").setMaxDamage(BlueSteelUses);
		BlueSteelShovel = new ItemCustomShovel(TFCItemID.BlueSteelShovel,BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Shovel").setMaxDamage(BlueSteelUses);
		BlueSteelAxe = new ItemCustomAxe(TFCItemID.BlueSteelAxe,BlueSteelToolMaterial, 270).setUnlocalizedName("Blue Steel Axe").setMaxDamage(BlueSteelUses);
		BlueSteelHoe = new ItemCustomHoe(TFCItemID.BlueSteelHoe,BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Hoe").setMaxDamage(BlueSteelUses);

		BronzePick = new ItemCustomPickaxe(TFCItemID.BronzePick,BronzeToolMaterial).setUnlocalizedName("Bronze Pick").setMaxDamage(BronzeUses);
		BronzeShovel = new ItemCustomShovel(TFCItemID.BronzeShovel,BronzeToolMaterial).setUnlocalizedName("Bronze Shovel").setMaxDamage(BronzeUses);
		BronzeAxe = new ItemCustomAxe(TFCItemID.BronzeAxe,BronzeToolMaterial, 160).setUnlocalizedName("Bronze Axe").setMaxDamage(BronzeUses);
		BronzeHoe = new ItemCustomHoe(TFCItemID.BronzeHoe,BronzeToolMaterial).setUnlocalizedName("Bronze Hoe").setMaxDamage(BronzeUses);

		CopperPick = new ItemCustomPickaxe(TFCItemID.CopperPick,CopperToolMaterial).setUnlocalizedName("Copper Pick").setMaxDamage(CopperUses);
		CopperShovel = new ItemCustomShovel(TFCItemID.CopperShovel,CopperToolMaterial).setUnlocalizedName("Copper Shovel").setMaxDamage(CopperUses);
		CopperAxe = new ItemCustomAxe(TFCItemID.CopperAxe,CopperToolMaterial, 115).setUnlocalizedName("Copper Axe").setMaxDamage(CopperUses);
		CopperHoe = new ItemCustomHoe(TFCItemID.CopperHoe,CopperToolMaterial).setUnlocalizedName("Copper Hoe").setMaxDamage(CopperUses);

		WroughtIronPick = new ItemCustomPickaxe(TFCItemID.WroughtIronPick,IronToolMaterial).setUnlocalizedName("Wrought Iron Pick").setMaxDamage(WroughtIronUses);
		WroughtIronShovel = new ItemCustomShovel(TFCItemID.WroughtIronShovel,IronToolMaterial).setUnlocalizedName("Wrought Iron Shovel").setMaxDamage(WroughtIronUses);
		WroughtIronAxe = new ItemCustomAxe(TFCItemID.WroughtIronAxe,IronToolMaterial, 185).setUnlocalizedName("Wrought Iron Axe").setMaxDamage(WroughtIronUses);
		WroughtIronHoe = new ItemCustomHoe(TFCItemID.WroughtIronHoe,IronToolMaterial).setUnlocalizedName("Wrought Iron Hoe").setMaxDamage(WroughtIronUses);;

		RedSteelPick = new ItemCustomPickaxe(TFCItemID.RedSteelPick,RedSteelToolMaterial).setUnlocalizedName("Red Steel Pick").setMaxDamage(RedSteelUses);
		RedSteelShovel = new ItemCustomShovel(TFCItemID.RedSteelShovel,RedSteelToolMaterial).setUnlocalizedName("Red Steel Shovel").setMaxDamage(RedSteelUses);
		RedSteelAxe = new ItemCustomAxe(TFCItemID.RedSteelAxe,RedSteelToolMaterial, 270).setUnlocalizedName("Red Steel Axe").setMaxDamage(RedSteelUses);
		RedSteelHoe = new ItemCustomHoe(TFCItemID.RedSteelHoe,RedSteelToolMaterial).setUnlocalizedName("Red Steel Hoe").setMaxDamage(RedSteelUses);

		SteelPick = new ItemCustomPickaxe(TFCItemID.SteelPick,SteelToolMaterial).setUnlocalizedName("Steel Pick").setMaxDamage(SteelUses);
		SteelShovel = new ItemCustomShovel(TFCItemID.SteelShovel,SteelToolMaterial).setUnlocalizedName("Steel Shovel").setMaxDamage(SteelUses);
		SteelAxe = new ItemCustomAxe(TFCItemID.SteelAxe,SteelToolMaterial, 210).setUnlocalizedName("Steel Axe").setMaxDamage(SteelUses);
		SteelHoe = new ItemCustomHoe(TFCItemID.SteelHoe,SteelToolMaterial).setUnlocalizedName("Steel Hoe").setMaxDamage(SteelUses);

		//chisels
		BismuthBronzeChisel = new ItemChisel(TFCItemID.BismuthBronzeChisel,BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Chisel").setMaxDamage(BismuthBronzeUses);
		BlackBronzeChisel = new ItemChisel(TFCItemID.BlackBronzeChisel,BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Chisel").setMaxDamage(BlackBronzeUses);
		BlackSteelChisel = new ItemChisel(TFCItemID.BlackSteelChisel,BlackSteelToolMaterial).setUnlocalizedName("Black Steel Chisel").setMaxDamage(BlackSteelUses);
		BlueSteelChisel = new ItemChisel(TFCItemID.BlueSteelChisel,BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Chisel").setMaxDamage(BlueSteelUses);
		BronzeChisel = new ItemChisel(TFCItemID.BronzeChisel,BronzeToolMaterial).setUnlocalizedName("Bronze Chisel").setMaxDamage(BronzeUses);
		CopperChisel = new ItemChisel(TFCItemID.CopperChisel,CopperToolMaterial).setUnlocalizedName("Copper Chisel").setMaxDamage(CopperUses);
		WroughtIronChisel = new ItemChisel(TFCItemID.WroughtIronChisel,IronToolMaterial).setUnlocalizedName("Wrought Iron Chisel").setMaxDamage(WroughtIronUses);
		RedSteelChisel = new ItemChisel(TFCItemID.RedSteelChisel,RedSteelToolMaterial).setUnlocalizedName("Red Steel Chisel").setMaxDamage(RedSteelUses);
		SteelChisel = new ItemChisel(TFCItemID.SteelChisel,SteelToolMaterial).setUnlocalizedName("Steel Chisel").setMaxDamage(SteelUses);

		BismuthBronzeSword = new ItemCustomSword(TFCItemID.BismuthBronzeSword,BismuthBronzeToolMaterial, 	210).setUnlocalizedName("Bismuth Bronze Sword").setMaxDamage(BismuthBronzeUses);
		BlackBronzeSword = new ItemCustomSword(TFCItemID.BlackBronzeSword,BlackBronzeToolMaterial, 		230).setUnlocalizedName("Black Bronze Sword").setMaxDamage(BlackBronzeUses);
		BlackSteelSword = new ItemCustomSword(TFCItemID.BlackSteelSword,BlackSteelToolMaterial, 			270).setUnlocalizedName("Black Steel Sword").setMaxDamage(BlackSteelUses);
		BlueSteelSword = new ItemCustomSword(TFCItemID.BlueSteelSword,BlueSteelToolMaterial,				315).setUnlocalizedName("Blue Steel Sword").setMaxDamage(BlueSteelUses);
		BronzeSword = new ItemCustomSword(TFCItemID.BronzeSword,BronzeToolMaterial,						220).setUnlocalizedName("Bronze Sword").setMaxDamage(BronzeUses);
		CopperSword = new ItemCustomSword(TFCItemID.CopperSword,CopperToolMaterial, 						165).setUnlocalizedName("Copper Sword").setMaxDamage(CopperUses);
		WroughtIronSword = new ItemCustomSword(TFCItemID.WroughtIronSword,IronToolMaterial,				240).setUnlocalizedName("Wrought Iron Sword").setMaxDamage(WroughtIronUses);
		RedSteelSword = new ItemCustomSword(TFCItemID.RedSteelSword,RedSteelToolMaterial,					305).setUnlocalizedName("Red Steel Sword").setMaxDamage(RedSteelUses);
		SteelSword = new ItemCustomSword(TFCItemID.SteelSword,SteelToolMaterial,							265).setUnlocalizedName("Steel Sword").setMaxDamage(SteelUses);

		BismuthBronzeMace = new ItemCustomSword(TFCItemID.BismuthBronzeMace,BismuthBronzeToolMaterial, 	210,EnumDamageType.CRUSHING).setUnlocalizedName("Bismuth Bronze Mace").setMaxDamage(BismuthBronzeUses);
		BlackBronzeMace = new ItemCustomSword(TFCItemID.BlackBronzeMace,BlackBronzeToolMaterial, 			230,EnumDamageType.CRUSHING).setUnlocalizedName("Black Bronze Mace").setMaxDamage(BlackBronzeUses);
		BlackSteelMace = new ItemCustomSword(TFCItemID.BlackSteelMace,BlackSteelToolMaterial, 				270,EnumDamageType.CRUSHING).setUnlocalizedName("Black Steel Mace").setMaxDamage(BlackSteelUses);
		BlueSteelMace = new ItemCustomSword(TFCItemID.BlueSteelMace,BlueSteelToolMaterial, 				315,EnumDamageType.CRUSHING).setUnlocalizedName("Blue Steel Mace").setMaxDamage(BlueSteelUses);
		BronzeMace = new ItemCustomSword(TFCItemID.BronzeMace,BronzeToolMaterial, 							220,EnumDamageType.CRUSHING).setUnlocalizedName("Bronze Mace").setMaxDamage(BronzeUses);
		CopperMace = new ItemCustomSword(TFCItemID.CopperMace,CopperToolMaterial, 							165,EnumDamageType.CRUSHING).setUnlocalizedName("Copper Mace").setMaxDamage(CopperUses);
		WroughtIronMace = new ItemCustomSword(TFCItemID.WroughtIronMace,IronToolMaterial, 					240,EnumDamageType.CRUSHING).setUnlocalizedName("Wrought Iron Mace").setMaxDamage(WroughtIronUses);
		RedSteelMace = new ItemCustomSword(TFCItemID.RedSteelMace,RedSteelToolMaterial, 					305,EnumDamageType.CRUSHING).setUnlocalizedName("Red Steel Mace").setMaxDamage(RedSteelUses);
		SteelMace = new ItemCustomSword(TFCItemID.SteelMace,SteelToolMaterial, 							265,EnumDamageType.CRUSHING).setUnlocalizedName("Steel Mace").setMaxDamage(SteelUses);

		BismuthBronzeSaw = new ItemCustomSaw(TFCItemID.BismuthBronzeSaw,BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Saw").setMaxDamage(BismuthBronzeUses);
		BlackBronzeSaw = new ItemCustomSaw(TFCItemID.BlackBronzeSaw,BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Saw").setMaxDamage(BlackBronzeUses);
		BlackSteelSaw = new ItemCustomSaw(TFCItemID.BlackSteelSaw,BlackSteelToolMaterial).setUnlocalizedName("Black Steel Saw").setMaxDamage(BlackSteelUses);
		BlueSteelSaw = new ItemCustomSaw(TFCItemID.BlueSteelSaw,BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Saw").setMaxDamage(BlueSteelUses);
		BronzeSaw = new ItemCustomSaw(TFCItemID.BronzeSaw,BronzeToolMaterial).setUnlocalizedName("Bronze Saw").setMaxDamage(BronzeUses);
		CopperSaw = new ItemCustomSaw(TFCItemID.CopperSaw,CopperToolMaterial).setUnlocalizedName("Copper Saw").setMaxDamage(CopperUses);
		WroughtIronSaw = new ItemCustomSaw(TFCItemID.WroughtIronSaw,IronToolMaterial).setUnlocalizedName("Wrought Iron Saw").setMaxDamage(WroughtIronUses);
		RedSteelSaw = new ItemCustomSaw(TFCItemID.RedSteelSaw,RedSteelToolMaterial).setUnlocalizedName("Red Steel Saw").setMaxDamage(RedSteelUses);
		SteelSaw = new ItemCustomSaw(TFCItemID.SteelSaw,SteelToolMaterial).setUnlocalizedName("Steel Saw").setMaxDamage(SteelUses);

		HCBlackSteelIngot = new ItemIngot(TFCItemID.HCBlackSteelIngot, false).setUnlocalizedName("HC Black Steel Ingot");
		WeakBlueSteelIngot = new ItemIngot(TFCItemID.WeakBlueSteelIngot, false).setUnlocalizedName("Weak Blue Steel Ingot");
		WeakRedSteelIngot = new ItemIngot(TFCItemID.WeakRedSteelIngot, false).setUnlocalizedName("Weak Red Steel Ingot");
		WeakSteelIngot = new ItemIngot(TFCItemID.WeakSteelIngot, false).setUnlocalizedName("Weak Steel Ingot");
		HCBlueSteelIngot = new ItemIngot(TFCItemID.HCBlueSteelIngot, false).setUnlocalizedName("HC Blue Steel Ingot");
		HCRedSteelIngot = new ItemIngot(TFCItemID.HCRedSteelIngot, false).setUnlocalizedName("HC Red Steel Ingot");
		HCSteelIngot = new ItemIngot(TFCItemID.HCSteelIngot, false).setUnlocalizedName("HC Steel Ingot");

		OreChunk = new ItemOre(TFCItemID.OreChunk).setFolder("ore/").setUnlocalizedName("Ore");
		Logs = new ItemLogs(TFCItemID.Logs).setUnlocalizedName("Log");

		//javelins
		IgInStoneJavelin = new ItemJavelin(TFCItemID.IgInJavelin,IgInToolMaterial, 45, 30).setUnlocalizedName("Stone Javelin");
		SedStoneJavelin = new ItemJavelin(TFCItemID.SedJavelin,SedToolMaterial, 45, 30).setUnlocalizedName("Stone Javelin");
		IgExStoneJavelin = new ItemJavelin(TFCItemID.IgExJavelin,IgExToolMaterial, 45, 30).setUnlocalizedName("Stone Javelin");
		MMStoneJavelin = new ItemJavelin(TFCItemID.MMJavelin,MMToolMaterial, 45, 30).setUnlocalizedName("Stone Javelin");        
		CopperJavelin = new ItemJavelin(TFCItemID.CopperJavelin,CopperToolMaterial, 65, 50).setUnlocalizedName("Copper Javelin");
		BismuthBronzeJavelin = new ItemJavelin(TFCItemID.BismuthBronzeJavelin, BismuthBronzeToolMaterial, 90, 75).setUnlocalizedName("Bismuth Bronze Javelin");
		BronzeJavelin = new ItemJavelin(TFCItemID.BronzeJavelin,BronzeToolMaterial, 100, 85).setUnlocalizedName("Bronze Javelin");
		BlackBronzeJavelin = new ItemJavelin(TFCItemID.BlackBronzeJavelin, BlackBronzeToolMaterial, 95, 80).setUnlocalizedName("Black Bronze Javelin");
		WroughtIronJavelin = new ItemJavelin(TFCItemID.WroughtIronJavelin,IronToolMaterial, 135, 120).setUnlocalizedName("Wrought Iron Javelin");
		SteelJavelin = new ItemJavelin(TFCItemID.SteelJavelin,SteelToolMaterial, 170, 155).setUnlocalizedName("Steel Javelin");
		BlackSteelJavelin = new ItemJavelin(TFCItemID.BlackSteelJavelin,BlackSteelToolMaterial, 205, 190).setUnlocalizedName("Black Steel Javelin");
		BlueSteelJavelin = new ItemJavelin(TFCItemID.BlueSteelJavelin,BlueSteelToolMaterial, 240, 225).setUnlocalizedName("Blue Steel Javelin");
		RedSteelJavelin = new ItemJavelin(TFCItemID.RedSteelJavelin,RedSteelToolMaterial, 240, 225).setUnlocalizedName("Red Steel Javelin");

		//javelin heads
		IgInStoneJavelinHead = new ItemMiscToolHead(TFCItemID.IgInJavelinHead).setUnlocalizedName("Stone Javelin Head");
		SedStoneJavelinHead = new ItemMiscToolHead(TFCItemID.SedJavelinHead).setUnlocalizedName("Stone Javelin Head");
		IgExStoneJavelinHead = new ItemMiscToolHead(TFCItemID.IgExJavelinHead).setUnlocalizedName("Stone Javelin Head");
		MMStoneJavelinHead = new ItemMiscToolHead(TFCItemID.MMJavelinHead).setUnlocalizedName("Stone Javelin Head");
		CopperJavelinHead = new ItemMiscToolHead(TFCItemID.CopperJavelinHead).setUnlocalizedName("Copper Javelin Head");
		BismuthBronzeJavelinHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeJavelinHead).setUnlocalizedName("Bismuth Bronze Javelin Head");
		BronzeJavelinHead = new ItemMiscToolHead(TFCItemID.BronzeJavelinHead).setUnlocalizedName("Bronze Javelin Head");
		BlackBronzeJavelinHead = new ItemMiscToolHead(TFCItemID.BlackBronzeJavelinHead).setUnlocalizedName("Black Bronze Javelin Head");
		WroughtIronJavelinHead = new ItemMiscToolHead(TFCItemID.WroughtIronJavelinHead).setUnlocalizedName("Wrought Iron Javelin Head");
		SteelJavelinHead = new ItemMiscToolHead(TFCItemID.SteelJavelinHead).setUnlocalizedName("Steel Javelin Head");
		BlackSteelJavelinHead = new ItemMiscToolHead(TFCItemID.BlackSteelJavelinHead).setUnlocalizedName("Black Steel Javelin Head");
		BlueSteelJavelinHead = new ItemMiscToolHead(TFCItemID.BlueSteelJavelinHead).setUnlocalizedName("Blue Steel Javelin Head");
		RedSteelJavelinHead = new ItemMiscToolHead(TFCItemID.RedSteelJavelinHead).setUnlocalizedName("Red Steel Javelin Head");

		BismuthUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedBismuth).setUnlocalizedName("Bismuth Unshaped");
		BismuthBronzeUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedBismuthBronze).setUnlocalizedName("Bismuth Bronze Unshaped");
		BlackBronzeUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedBlackBronze).setUnlocalizedName("Black Bronze Unshaped");
		BlackSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedBlackSteel).setUnlocalizedName("Black Steel Unshaped");
		BlueSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedBlueSteel).setUnlocalizedName("Blue Steel Unshaped");
		BrassUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedBrass).setUnlocalizedName("Brass Unshaped");
		BronzeUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedBronze).setUnlocalizedName("Bronze Unshaped");
		CopperUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedCopper).setUnlocalizedName("Copper Unshaped");
		GoldUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedGold).setUnlocalizedName("Gold Unshaped");
		WroughtIronUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedIron).setUnlocalizedName("Wrought Iron Unshaped");
		LeadUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedLead).setUnlocalizedName("Lead Unshaped");
		NickelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedNickel).setUnlocalizedName("Nickel Unshaped");
		PigIronUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedPigIron).setUnlocalizedName("Pig Iron Unshaped");
		PlatinumUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedPlatinum).setUnlocalizedName("Platinum Unshaped");
		RedSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedRedSteel).setUnlocalizedName("Red Steel Unshaped");
		RoseGoldUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedRoseGold).setUnlocalizedName("Rose Gold Unshaped");
		SilverUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedSilver).setUnlocalizedName("Silver Unshaped");
		SteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedSteel).setUnlocalizedName("Steel Unshaped");
		SterlingSilverUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedSterlingSilver).setUnlocalizedName("Sterling Silver Unshaped");
		TinUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedTin).setUnlocalizedName("Tin Unshaped");
		ZincUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedZinc).setUnlocalizedName("Zinc Unshaped");

		//Hammers
		StoneHammer = new ItemHammer(TFCItemID.StoneHammer,TFCItems.IgInToolMaterial).setUnlocalizedName("Stone Hammer").setMaxDamage(TFCItems.IgInStoneUses);
		BismuthBronzeHammer = new ItemHammer(TFCItemID.BismuthBronzeHammer,TFCItems.BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Hammer").setMaxDamage(TFCItems.BismuthBronzeUses);
		BlackBronzeHammer = new ItemHammer(TFCItemID.BlackBronzeHammer,TFCItems.BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Hammer").setMaxDamage(TFCItems.BlackBronzeUses);
		BlackSteelHammer = new ItemHammer(TFCItemID.BlackSteelHammer,TFCItems.BlackSteelToolMaterial).setUnlocalizedName("Black Steel Hammer").setMaxDamage(TFCItems.BlackSteelUses);
		BlueSteelHammer = new ItemHammer(TFCItemID.BlueSteelHammer,TFCItems.BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Hammer").setMaxDamage(TFCItems.BlueSteelUses);
		BronzeHammer = new ItemHammer(TFCItemID.BronzeHammer,TFCItems.BronzeToolMaterial).setUnlocalizedName("Bronze Hammer").setMaxDamage(TFCItems.BronzeUses);
		CopperHammer = new ItemHammer(TFCItemID.CopperHammer,TFCItems.CopperToolMaterial).setUnlocalizedName("Copper Hammer").setMaxDamage(TFCItems.CopperUses);
		WroughtIronHammer = new ItemHammer(TFCItemID.WroughtIronHammer,TFCItems.IronToolMaterial).setUnlocalizedName("Wrought Iron Hammer").setMaxDamage(TFCItems.WroughtIronUses);
		RedSteelHammer = new ItemHammer(TFCItemID.RedSteelHammer,TFCItems.RedSteelToolMaterial).setUnlocalizedName("Red Steel Hammer").setMaxDamage(TFCItems.RedSteelUses);
		SteelHammer = new ItemHammer(TFCItemID.SteelHammer,TFCItems.SteelToolMaterial).setUnlocalizedName("Steel Hammer").setMaxDamage(TFCItems.SteelUses);

		Ink = new ItemTerra(TFCItemID.Ink).setUnlocalizedName("Ink");
		FireStarter = new ItemFirestarter(TFCItemID.FireStarter).setFolder("tools/").setUnlocalizedName("Firestarter");

		//Tool heads
		BismuthBronzePickaxeHead = new ItemMiscToolHead(TFCItemID.BismuthBronzePickaxeHead).setUnlocalizedName("Bismuth Bronze Pick Head");
		BlackBronzePickaxeHead = new ItemMiscToolHead(TFCItemID.BlackBronzePickaxeHead).setUnlocalizedName("Black Bronze Pick Head");
		BlackSteelPickaxeHead = new ItemMiscToolHead(TFCItemID.BlackSteelPickaxeHead).setUnlocalizedName("Black Steel Pick Head");
		BlueSteelPickaxeHead = new ItemMiscToolHead(TFCItemID.BlueSteelPickaxeHead).setUnlocalizedName("Blue Steel Pick Head");
		BronzePickaxeHead = new ItemMiscToolHead(TFCItemID.BronzePickaxeHead).setUnlocalizedName("Bronze Pick Head");
		CopperPickaxeHead = new ItemMiscToolHead(TFCItemID.CopperPickaxeHead).setUnlocalizedName("Copper Pick Head");
		WroughtIronPickaxeHead = new ItemMiscToolHead(TFCItemID.WroughtIronPickaxeHead).setUnlocalizedName("Wrought Iron Pick Head");
		RedSteelPickaxeHead = new ItemMiscToolHead(TFCItemID.RedSteelPickaxeHead).setUnlocalizedName("Red Steel Pick Head");
		SteelPickaxeHead = new ItemMiscToolHead(TFCItemID.SteelPickaxeHead).setUnlocalizedName("Steel Pick Head");

		BismuthBronzeShovelHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeShovelHead).setUnlocalizedName("Bismuth Bronze Shovel Head");
		BlackBronzeShovelHead = new ItemMiscToolHead(TFCItemID.BlackBronzeShovelHead).setUnlocalizedName("Black Bronze Shovel Head");
		BlackSteelShovelHead = new ItemMiscToolHead(TFCItemID.BlackSteelShovelHead).setUnlocalizedName("Black Steel Shovel Head");
		BlueSteelShovelHead = new ItemMiscToolHead(TFCItemID.BlueSteelShovelHead).setUnlocalizedName("Blue Steel Shovel Head");
		BronzeShovelHead = new ItemMiscToolHead(TFCItemID.BronzeShovelHead).setUnlocalizedName("Bronze Shovel Head");
		CopperShovelHead = new ItemMiscToolHead(TFCItemID.CopperShovelHead).setUnlocalizedName("Copper Shovel Head");
		WroughtIronShovelHead = new ItemMiscToolHead(TFCItemID.WroughtIronShovelHead).setUnlocalizedName("Wrought Iron Shovel Head");
		RedSteelShovelHead = new ItemMiscToolHead(TFCItemID.RedSteelShovelHead).setUnlocalizedName("Red Steel Shovel Head");
		SteelShovelHead = new ItemMiscToolHead(TFCItemID.SteelShovelHead).setUnlocalizedName("Steel Shovel Head");

		BismuthBronzeHoeHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeHoeHead).setUnlocalizedName("Bismuth Bronze Hoe Head");
		BlackBronzeHoeHead = new ItemMiscToolHead(TFCItemID.BlackBronzeHoeHead).setUnlocalizedName("Black Bronze Hoe Head");
		BlackSteelHoeHead = new ItemMiscToolHead(TFCItemID.BlackSteelHoeHead).setUnlocalizedName("Black Steel Hoe Head");
		BlueSteelHoeHead = new ItemMiscToolHead(TFCItemID.BlueSteelHoeHead).setUnlocalizedName("Blue Steel Hoe Head");
		BronzeHoeHead = new ItemMiscToolHead(TFCItemID.BronzeHoeHead).setUnlocalizedName("Bronze Hoe Head");
		CopperHoeHead = new ItemMiscToolHead(TFCItemID.CopperHoeHead).setUnlocalizedName("Copper Hoe Head");
		WroughtIronHoeHead = new ItemMiscToolHead(TFCItemID.WroughtIronHoeHead).setUnlocalizedName("Wrought Iron Hoe Head");
		RedSteelHoeHead = new ItemMiscToolHead(TFCItemID.RedSteelHoeHead).setUnlocalizedName("Red Steel Hoe Head");
		SteelHoeHead = new ItemMiscToolHead(TFCItemID.SteelHoeHead).setUnlocalizedName("Steel Hoe Head");

		BismuthBronzeAxeHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeAxeHead).setUnlocalizedName("Bismuth Bronze Axe Head");
		BlackBronzeAxeHead = new ItemMiscToolHead(TFCItemID.BlackBronzeAxeHead).setUnlocalizedName("Black Bronze Axe Head");
		BlackSteelAxeHead = new ItemMiscToolHead(TFCItemID.BlackSteelAxeHead).setUnlocalizedName("Black Steel Axe Head");
		BlueSteelAxeHead = new ItemMiscToolHead(TFCItemID.BlueSteelAxeHead).setUnlocalizedName("Blue Steel Axe Head");
		BronzeAxeHead = new ItemMiscToolHead(TFCItemID.BronzeAxeHead).setUnlocalizedName("Bronze Axe Head");
		CopperAxeHead = new ItemMiscToolHead(TFCItemID.CopperAxeHead).setUnlocalizedName("Copper Axe Head");
		WroughtIronAxeHead = new ItemMiscToolHead(TFCItemID.WroughtIronAxeHead).setUnlocalizedName("Wrought Iron Axe Head");
		RedSteelAxeHead = new ItemMiscToolHead(TFCItemID.RedSteelAxeHead).setUnlocalizedName("Red Steel Axe Head");
		SteelAxeHead = new ItemMiscToolHead(TFCItemID.SteelAxeHead).setUnlocalizedName("Steel Axe Head");

		BismuthBronzeHammerHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeHammerHead).setUnlocalizedName("Bismuth Bronze Hammer Head");
		BlackBronzeHammerHead = new ItemMiscToolHead(TFCItemID.BlackBronzeHammerHead).setUnlocalizedName("Black Bronze Hammer Head");
		BlackSteelHammerHead = new ItemMiscToolHead(TFCItemID.BlackSteelHammerHead).setUnlocalizedName("Black Steel Hammer Head");
		BlueSteelHammerHead = new ItemMiscToolHead(TFCItemID.BlueSteelHammerHead).setUnlocalizedName("Blue Steel Hammer Head");
		BronzeHammerHead = new ItemMiscToolHead(TFCItemID.BronzeHammerHead).setUnlocalizedName("Bronze Hammer Head");
		CopperHammerHead = new ItemMiscToolHead(TFCItemID.CopperHammerHead).setUnlocalizedName("Copper Hammer Head");
		WroughtIronHammerHead = new ItemMiscToolHead(TFCItemID.WroughtIronHammerHead).setUnlocalizedName("Wrought Iron Hammer Head");
		RedSteelHammerHead = new ItemMiscToolHead(TFCItemID.RedSteelHammerHead).setUnlocalizedName("Red Steel Hammer Head");
		SteelHammerHead = new ItemMiscToolHead(TFCItemID.SteelHammerHead).setUnlocalizedName("Steel Hammer Head");

		//chisel heads
		BismuthBronzeChiselHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeChiselHead).setUnlocalizedName("Bismuth Bronze Chisel Head");
		BlackBronzeChiselHead = new ItemMiscToolHead(TFCItemID.BlackBronzeChiselHead).setUnlocalizedName("Black Bronze Chisel Head");
		BlackSteelChiselHead = new ItemMiscToolHead(TFCItemID.BlackSteelChiselHead).setUnlocalizedName("Black Steel Chisel Head");
		BlueSteelChiselHead = new ItemMiscToolHead(TFCItemID.BlueSteelChiselHead).setUnlocalizedName("Blue Steel Chisel Head");
		BronzeChiselHead = new ItemMiscToolHead(TFCItemID.BronzeChiselHead).setUnlocalizedName("Bronze Chisel Head");
		CopperChiselHead = new ItemMiscToolHead(TFCItemID.CopperChiselHead).setUnlocalizedName("Copper Chisel Head");
		WroughtIronChiselHead = new ItemMiscToolHead(TFCItemID.WroughtIronChiselHead).setUnlocalizedName("Wrought Iron Chisel Head");
		RedSteelChiselHead = new ItemMiscToolHead(TFCItemID.RedSteelChiselHead).setUnlocalizedName("Red Steel Chisel Head");
		SteelChiselHead = new ItemMiscToolHead(TFCItemID.SteelChiselHead).setUnlocalizedName("Steel Chisel Head");

		BismuthBronzeSwordHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeSwordHead).setUnlocalizedName("Bismuth Bronze Sword Blade");
		BlackBronzeSwordHead = new ItemMiscToolHead(TFCItemID.BlackBronzeSwordHead).setUnlocalizedName("Black Bronze Sword Blade");
		BlackSteelSwordHead = new ItemMiscToolHead(TFCItemID.BlackSteelSwordHead).setUnlocalizedName("Black Steel Sword Blade");
		BlueSteelSwordHead = new ItemMiscToolHead(TFCItemID.BlueSteelSwordHead).setUnlocalizedName("Blue Steel Sword Blade");
		BronzeSwordHead = new ItemMiscToolHead(TFCItemID.BronzeSwordHead).setUnlocalizedName("Bronze Sword Blade");
		CopperSwordHead = new ItemMiscToolHead(TFCItemID.CopperSwordHead).setUnlocalizedName("Copper Sword Blade");
		WroughtIronSwordHead = new ItemMiscToolHead(TFCItemID.WroughtIronSwordHead).setUnlocalizedName("Wrought Iron Sword Blade");
		RedSteelSwordHead = new ItemMiscToolHead(TFCItemID.RedSteelSwordHead).setUnlocalizedName("Red Steel Sword Blade");
		SteelSwordHead = new ItemMiscToolHead(TFCItemID.SteelSwordHead).setUnlocalizedName("Steel Sword Blade");

		BismuthBronzeMaceHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeMaceHead).setUnlocalizedName("Bismuth Bronze Mace Head");
		BlackBronzeMaceHead = new ItemMiscToolHead(TFCItemID.BlackBronzeMaceHead).setUnlocalizedName("Black Bronze Mace Head");
		BlackSteelMaceHead = new ItemMiscToolHead(TFCItemID.BlackSteelMaceHead).setUnlocalizedName("Black Steel Mace Head");
		BlueSteelMaceHead = new ItemMiscToolHead(TFCItemID.BlueSteelMaceHead).setUnlocalizedName("Blue Steel Mace Head");
		BronzeMaceHead = new ItemMiscToolHead(TFCItemID.BronzeMaceHead).setUnlocalizedName("Bronze Mace Head");
		CopperMaceHead = new ItemMiscToolHead(TFCItemID.CopperMaceHead).setUnlocalizedName("Copper Mace Head");
		WroughtIronMaceHead = new ItemMiscToolHead(TFCItemID.WroughtIronMaceHead).setUnlocalizedName("Wrought Iron Mace Head");
		RedSteelMaceHead = new ItemMiscToolHead(TFCItemID.RedSteelMaceHead).setUnlocalizedName("Red Steel Mace Head");
		SteelMaceHead = new ItemMiscToolHead(TFCItemID.SteelMaceHead).setUnlocalizedName("Steel Mace Head");

		BismuthBronzeSawHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeSawHead).setUnlocalizedName("Bismuth Bronze Saw Blade");
		BlackBronzeSawHead = new ItemMiscToolHead(TFCItemID.BlackBronzeSawHead).setUnlocalizedName("Black Bronze Saw Blade");
		BlackSteelSawHead = new ItemMiscToolHead(TFCItemID.BlackSteelSawHead).setUnlocalizedName("Black Steel Saw Blade");
		BlueSteelSawHead = new ItemMiscToolHead(TFCItemID.BlueSteelSawHead).setUnlocalizedName("Blue Steel Saw Blade");
		BronzeSawHead = new ItemMiscToolHead(TFCItemID.BronzeSawHead).setUnlocalizedName("Bronze Saw Blade");
		CopperSawHead = new ItemMiscToolHead(TFCItemID.CopperSawHead).setUnlocalizedName("Copper Saw Blade");
		WroughtIronSawHead = new ItemMiscToolHead(TFCItemID.WroughtIronSawHead).setUnlocalizedName("Wrought Iron Saw Blade");
		RedSteelSawHead = new ItemMiscToolHead(TFCItemID.RedSteelSawHead).setUnlocalizedName("Red Steel Saw Blade");
		SteelSawHead = new ItemMiscToolHead(TFCItemID.SteelSawHead).setUnlocalizedName("Steel Saw Blade");

		HCBlackSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedHCBlackSteel).setUnlocalizedName("HC Black Steel Unshaped");
		WeakBlueSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedWeakBlueSteel).setUnlocalizedName("Weak Blue Steel Unshaped");
		HCBlueSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedHCBlueSteel).setUnlocalizedName("HC Blue Steel Unshaped");
		WeakRedSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedWeakRedSteel).setUnlocalizedName("Weak Red Steel Unshaped");
		HCRedSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedHCRedSteel).setUnlocalizedName("HC Red Steel Unshaped");
		WeakSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedWeakSteel).setUnlocalizedName("Weak Steel Unshaped");
		HCSteelUnshaped = new ItemMeltedMetal(TFCItemID.UnshapedHCSteel).setUnlocalizedName("HC Steel Unshaped");
		Coke = (new ItemTerra(TFCItemID.Coke).setUnlocalizedName("Coke"));

		BismuthBronzeProPickHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeProPickHead).setUnlocalizedName("Bismuth Bronze ProPick Head");
		BlackBronzeProPickHead = new ItemMiscToolHead(TFCItemID.BlackBronzeProPickHead).setUnlocalizedName("Black Bronze ProPick Head");
		BlackSteelProPickHead = new ItemMiscToolHead(TFCItemID.BlackSteelProPickHead).setUnlocalizedName("Black Steel ProPick Head");
		BlueSteelProPickHead = new ItemMiscToolHead(TFCItemID.BlueSteelProPickHead).setUnlocalizedName("Blue Steel ProPick Head");
		BronzeProPickHead = new ItemMiscToolHead(TFCItemID.BronzeProPickHead).setUnlocalizedName("Bronze ProPick Head");
		CopperProPickHead = new ItemMiscToolHead(TFCItemID.CopperProPickHead).setUnlocalizedName("Copper ProPick Head");
		WroughtIronProPickHead = new ItemMiscToolHead(TFCItemID.WroughtIronProPickHead).setUnlocalizedName("Wrought Iron ProPick Head");
		RedSteelProPickHead = new ItemMiscToolHead(TFCItemID.RedSteelProPickHead).setUnlocalizedName("Red Steel ProPick Head");
		SteelProPickHead = new ItemMiscToolHead(TFCItemID.SteelProPickHead).setUnlocalizedName("Steel ProPick Head");

		Powder = new ItemTerra(TFCItemID.Flux).setMetaNames(new String[]
				{"Flux", "Kaolinite Powder", "Graphite Powder", "Sulfur Powder", "Saltpeter Powder"}).setUnlocalizedName("Powder");

		/**
		 * Scythe
		 * */
		BismuthBronzeScythe = new ItemCustomScythe(TFCItemID.BismuthBronzeScythe,BismuthBronzeToolMaterial).setUnlocalizedName("Bismuth Bronze Scythe").setMaxDamage(BismuthBronzeUses);
		BlackBronzeScythe = new ItemCustomScythe(TFCItemID.BlackBronzeScythe,BlackBronzeToolMaterial).setUnlocalizedName("Black Bronze Scythe").setMaxDamage(BlackBronzeUses);
		BlackSteelScythe = new ItemCustomScythe(TFCItemID.BlackSteelScythe,BlackSteelToolMaterial).setUnlocalizedName("Black Steel Scythe").setMaxDamage(BlackSteelUses);
		BlueSteelScythe = new ItemCustomScythe(TFCItemID.BlueSteelScythe,BlueSteelToolMaterial).setUnlocalizedName("Blue Steel Scythe").setMaxDamage(BlueSteelUses);
		BronzeScythe = new ItemCustomScythe(TFCItemID.BronzeScythe,BronzeToolMaterial).setUnlocalizedName("Bronze Scythe").setMaxDamage(BronzeUses);
		CopperScythe = new ItemCustomScythe(TFCItemID.CopperScythe,CopperToolMaterial).setUnlocalizedName("Copper Scythe").setMaxDamage(CopperUses);
		WroughtIronScythe = new ItemCustomScythe(TFCItemID.WroughtIronScythe,IronToolMaterial).setUnlocalizedName("Wrought Iron Scythe").setMaxDamage(WroughtIronUses);
		RedSteelScythe = new ItemCustomScythe(TFCItemID.RedSteelScythe,RedSteelToolMaterial).setUnlocalizedName("Red Steel Scythe").setMaxDamage(RedSteelUses);
		SteelScythe = new ItemCustomScythe(TFCItemID.SteelScythe,SteelToolMaterial).setUnlocalizedName("Steel Scythe").setMaxDamage(SteelUses);

		BismuthBronzeScytheHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeScytheHead).setUnlocalizedName("Bismuth Bronze Scythe Blade");
		BlackBronzeScytheHead = new ItemMiscToolHead(TFCItemID.BlackBronzeScytheHead).setUnlocalizedName("Black Bronze Scythe Blade");
		BlackSteelScytheHead = new ItemMiscToolHead(TFCItemID.BlackSteelScytheHead).setUnlocalizedName("Black Steel Scythe Blade");
		BlueSteelScytheHead = new ItemMiscToolHead(TFCItemID.BlueSteelScytheHead).setUnlocalizedName("Blue Steel Scythe Blade");
		BronzeScytheHead = new ItemMiscToolHead(TFCItemID.BronzeScytheHead).setUnlocalizedName("Bronze Scythe Blade");
		CopperScytheHead = new ItemMiscToolHead(TFCItemID.CopperScytheHead).setUnlocalizedName("Copper Scythe Blade");
		WroughtIronScytheHead = new ItemMiscToolHead(TFCItemID.WroughtIronScytheHead).setUnlocalizedName("Wrought Iron Scythe Blade");
		RedSteelScytheHead = new ItemMiscToolHead(TFCItemID.RedSteelScytheHead).setUnlocalizedName("Red Steel Scythe Blade");
		SteelScytheHead = new ItemMiscToolHead(TFCItemID.SteelScytheHead).setUnlocalizedName("Steel Scythe Blade");

		WoodenBucketEmpty = (new ItemCustomBucket(TFCItemID.WoodenBucketEmpty, 0)).setUnlocalizedName("Wooden Bucket Empty");
		WoodenBucketWater = (new ItemCustomBucket(TFCItemID.WoodenBucketWater, 1)).setUnlocalizedName("Wooden Bucket Water").setContainerItem(WoodenBucketEmpty);
		WoodenBucketMilk = (new ItemCustomBucketMilk(TFCItemID.WoodenBucketMilk)).setUnlocalizedName("Wooden Bucket Milk").setContainerItem(WoodenBucketEmpty);

		BismuthBronzeKnifeHead = new ItemMiscToolHead(TFCItemID.BismuthBronzeKnifeHead).setUnlocalizedName("Bismuth Bronze Knife Blade");
		BlackBronzeKnifeHead = new ItemMiscToolHead(TFCItemID.BlackBronzeKnifeHead).setUnlocalizedName("Black Bronze Knife Blade");
		BlackSteelKnifeHead = new ItemMiscToolHead(TFCItemID.BlackSteelKnifeHead).setUnlocalizedName("Black Steel Knife Blade");
		BlueSteelKnifeHead = new ItemMiscToolHead(TFCItemID.BlueSteelKnifeHead).setUnlocalizedName("Blue Steel Knife Blade");
		BronzeKnifeHead = new ItemMiscToolHead(TFCItemID.BronzeKnifeHead).setUnlocalizedName("Bronze Knife Blade");
		CopperKnifeHead = new ItemMiscToolHead(TFCItemID.CopperKnifeHead).setUnlocalizedName("Copper Knife Blade");
		WroughtIronKnifeHead = new ItemMiscToolHead(TFCItemID.WroughtIronKnifeHead).setUnlocalizedName("Wrought Iron Knife Blade");
		RedSteelKnifeHead = new ItemMiscToolHead(TFCItemID.RedSteelKnifeHead).setUnlocalizedName("Red Steel Knife Blade");
		SteelKnifeHead = new ItemMiscToolHead(TFCItemID.SteelKnifeHead).setUnlocalizedName("Steel Knife Blade");

		BismuthBronzeKnife = new ItemCustomKnife(TFCItemID.BismuthBronzeKnife,BismuthBronzeToolMaterial, 	155).setUnlocalizedName("Bismuth Bronze Knife").setMaxDamage(BismuthBronzeUses);
		BlackBronzeKnife = new ItemCustomKnife(TFCItemID.BlackBronzeKnife,BlackBronzeToolMaterial, 		165).setUnlocalizedName("Black Bronze Knife").setMaxDamage(BlackBronzeUses);
		BlackSteelKnife = new ItemCustomKnife(TFCItemID.BlackSteelKnife,BlackSteelToolMaterial, 			205).setUnlocalizedName("Black Steel Knife").setMaxDamage(BlackSteelUses);
		BlueSteelKnife = new ItemCustomKnife(TFCItemID.BlueSteelKnife,BlueSteelToolMaterial, 				250).setUnlocalizedName("Blue Steel Knife").setMaxDamage(BlueSteelUses);
		BronzeKnife = new ItemCustomKnife(TFCItemID.BronzeKnife,BronzeToolMaterial, 						150).setUnlocalizedName("Bronze Knife").setMaxDamage(BronzeUses);
		CopperKnife = new ItemCustomKnife(TFCItemID.CopperKnife,CopperToolMaterial, 						100).setUnlocalizedName("Copper Knife").setMaxDamage(CopperUses);
		WroughtIronKnife = new ItemCustomKnife(TFCItemID.WroughtIronKnife,IronToolMaterial, 				175).setUnlocalizedName("Wrought Iron Knife").setMaxDamage(WroughtIronUses);
		RedSteelKnife = new ItemCustomKnife(TFCItemID.RedSteelKnife,RedSteelToolMaterial, 					250).setUnlocalizedName("Red Steel Knife").setMaxDamage(RedSteelUses);
		SteelKnife = new ItemCustomKnife(TFCItemID.SteelKnife,SteelToolMaterial,							200).setUnlocalizedName("Steel Knife").setMaxDamage(SteelUses);

		FlatRock = (new ItemFlatGeneric(TFCItemID.FlatRock).setFolder("rocks/flatrocks/").setMetaNames(Global.STONE_ALL).setUnlocalizedName("FlatRock"));
		LooseRock = (new ItemLooseRock(TFCItemID.LooseRock).setSpecialCraftingType(FlatRock).setFolder("rocks/").setMetaNames(Global.STONE_ALL).setUnlocalizedName("LooseRock"));

		IgInStoneShovelHead = new ItemMiscToolHead(TFCItemID.IgInStoneShovelHead).setUnlocalizedName("Stone Shovel Head");
		SedStoneShovelHead = new ItemMiscToolHead(TFCItemID.SedStoneShovelHead).setUnlocalizedName("Stone Shovel Head");
		IgExStoneShovelHead = new ItemMiscToolHead(TFCItemID.IgExStoneShovelHead).setUnlocalizedName("Stone Shovel Head");
		MMStoneShovelHead = new ItemMiscToolHead(TFCItemID.MMStoneShovelHead).setUnlocalizedName("Stone Shovel Head");

		IgInStoneAxeHead = new ItemMiscToolHead(TFCItemID.IgInStoneAxeHead).setUnlocalizedName("Stone Axe Head");
		SedStoneAxeHead = new ItemMiscToolHead(TFCItemID.SedStoneAxeHead).setUnlocalizedName("Stone Axe Head");
		IgExStoneAxeHead = new ItemMiscToolHead(TFCItemID.IgExStoneAxeHead).setUnlocalizedName("Stone Axe Head");
		MMStoneAxeHead = new ItemMiscToolHead(TFCItemID.MMStoneAxeHead).setUnlocalizedName("Stone Axe Head");

		IgInStoneHoeHead = new ItemMiscToolHead(TFCItemID.IgInStoneHoeHead).setUnlocalizedName("Stone Hoe Head");
		SedStoneHoeHead = new ItemMiscToolHead(TFCItemID.SedStoneHoeHead).setUnlocalizedName("Stone Hoe Head");
		IgExStoneHoeHead = new ItemMiscToolHead(TFCItemID.IgExStoneHoeHead).setUnlocalizedName("Stone Hoe Head");
		MMStoneHoeHead = new ItemMiscToolHead(TFCItemID.MMStoneHoeHead).setUnlocalizedName("Stone Hoe Head");

		StoneKnifeHead = new ItemMiscToolHead(TFCItemID.StoneKnifeHead).setUnlocalizedName("Stone Knife Blade");
		StoneHammerHead = new ItemMiscToolHead(TFCItemID.StoneHammerHead).setUnlocalizedName("Stone Hammer Head");

		StoneKnife = new ItemCustomKnife(TFCItemID.StoneKnife,IgExToolMaterial, 75).setUnlocalizedName("Stone Knife").setMaxDamage(IgExStoneUses);
		SmallOreChunk = new ItemOreSmall(TFCItemID.SmallOreChunk).setUnlocalizedName("Small Ore");
		SinglePlank = new ItemPlank(TFCItemID.SinglePlank).setUnlocalizedName("SinglePlank");

		RedSteelBucketEmpty = (new ItemCustomRedSteelBucket(TFCItemID.RedSteelBucketEmpty, 0)).setUnlocalizedName("Red Steel Bucket Empty");
		RedSteelBucketWater = (new ItemCustomRedSteelBucket(TFCItemID.RedSteelBucketWater, Block.waterMoving.blockID)).setUnlocalizedName("Red Steel Bucket Water").setContainerItem(RedSteelBucketEmpty);

		BlueSteelBucketEmpty = (new ItemCustomBlueSteelBucket(TFCItemID.BlueSteelBucketEmpty, 0)).setUnlocalizedName("Blue Steel Bucket Empty");
		BlueSteelBucketLava = (new ItemCustomBlueSteelBucket(TFCItemID.BlueSteelBucketLava, Block.lavaMoving.blockID)).setUnlocalizedName("Blue Steel Bucket Lava").setContainerItem(BlueSteelBucketEmpty);

		Quern = ((ItemTerra) new ItemTerra(TFCItemID.Quern).setUnlocalizedName("Quern").setMaxDamage(250)).setSize(EnumSize.MEDIUM).setWeight(EnumWeight.HEAVY);
		FlintSteel = new ItemFlintSteel(TFCItemID.FlintSteel).setUnlocalizedName("flintAndSteel").setMaxDamage(200).setTextureName("flint_and_steel");

		DoorOak = new ItemWoodDoor(TFCItemID.DoorOak, 0).setUnlocalizedName("Oak Door");
		DoorAspen = new ItemWoodDoor(TFCItemID.DoorAspen, 1).setUnlocalizedName("Aspen Door");
		DoorBirch = new ItemWoodDoor(TFCItemID.DoorBirch, 2).setUnlocalizedName("Birch Door");
		DoorChestnut = new ItemWoodDoor(TFCItemID.DoorChestnut, 3).setUnlocalizedName("Chestnut Door");
		DoorDouglasFir = new ItemWoodDoor(TFCItemID.DoorDouglasFir, 4).setUnlocalizedName("Douglas Fir Door");
		DoorHickory = new ItemWoodDoor(TFCItemID.DoorHickory, 5).setUnlocalizedName("Hickory Door");
		DoorMaple = new ItemWoodDoor(TFCItemID.DoorMaple, 6).setUnlocalizedName("Maple Door");
		DoorAsh = new ItemWoodDoor(TFCItemID.DoorAsh, 7).setUnlocalizedName("Ash Door");
		DoorPine = new ItemWoodDoor(TFCItemID.DoorPine, 8).setUnlocalizedName("Pine Door");
		DoorSequoia = new ItemWoodDoor(TFCItemID.DoorSequoia, 9).setUnlocalizedName("Sequoia Door");
		DoorSpruce = new ItemWoodDoor(TFCItemID.DoorSpruce, 10).setUnlocalizedName("Spruce Door");
		DoorSycamore = new ItemWoodDoor(TFCItemID.DoorSycamore, 11).setUnlocalizedName("Sycamore Door");
		DoorWhiteCedar = new ItemWoodDoor(TFCItemID.DoorWhiteCedar, 12).setUnlocalizedName("White Cedar Door");
		DoorWhiteElm = new ItemWoodDoor(TFCItemID.DoorWhiteElm, 13).setUnlocalizedName("White Elm Door");
		DoorWillow = new ItemWoodDoor(TFCItemID.DoorWillow, 14).setUnlocalizedName("Willow Door");
		DoorKapok = new ItemWoodDoor(TFCItemID.DoorKapok, 15).setUnlocalizedName("Kapok Door");

		Beer = new ItemAlcohol(TFCItemID.Beer).setUnlocalizedName("Beer").setCreativeTab(CreativeTabs.tabFood);
		Cider = new ItemAlcohol(TFCItemID.Cider).setUnlocalizedName("Cider").setCreativeTab(CreativeTabs.tabFood);
		Rum = new ItemAlcohol(TFCItemID.Rum).setUnlocalizedName("Rum").setCreativeTab(CreativeTabs.tabFood);
		RyeWhiskey = new ItemAlcohol(TFCItemID.RyeWhiskey).setUnlocalizedName("RyeWhiskey").setCreativeTab(CreativeTabs.tabFood);
		Sake = new ItemAlcohol(TFCItemID.Sake).setUnlocalizedName("Sake").setCreativeTab(CreativeTabs.tabFood);
		Vodka = new ItemAlcohol(TFCItemID.Vodka).setUnlocalizedName("Vodka").setCreativeTab(CreativeTabs.tabFood);
		Whiskey = new ItemAlcohol(TFCItemID.Whiskey).setUnlocalizedName("Whiskey").setCreativeTab(CreativeTabs.tabFood);

		Blueprint = new ItemBlueprint(TFCItemID.Blueprint);
		writabeBookTFC = new ItemWritableBookTFC(TFCItemID.WritableBookTFC,"Fix Me I'm Broken").setUnlocalizedName("book");
		WoolYarn = new ItemTerra(TFCItemID.WoolYarn).setUnlocalizedName("WoolYarn");
		Wool = new ItemTerra(TFCItemID.Wool).setUnlocalizedName("Wool").setCreativeTab(TFCTabs.TFCMaterials);
		WoolCloth = new ItemTerra(TFCItemID.WoolCloth).setUnlocalizedName("WoolCloth").setCreativeTab(TFCTabs.TFCMaterials);
		Spindle = new ItemSpindle(TFCItemID.Spindle).setUnlocalizedName("Spindle").setCreativeTab(TFCTabs.TFCMaterials);

		SpindleHead = new ItemPotteryBase(TFCItemID.SpindleHead).setMetaNames(new String[]{"Clay Spindle", "Spindle Head"}).setUnlocalizedName("Spindle").setCreativeTab(TFCTabs.TFCMaterials);
		StoneBrick = (new ItemStoneBrick(TFCItemID.ItemStoneBrick2).setFolder("tools/").setUnlocalizedName("ItemStoneBrick"));
		Mortar = new ItemTerra(TFCItemID.Mortar).setFolder("tools/").setUnlocalizedName("Mortar").setCreativeTab(TFCTabs.TFCMaterials);
		Limewater = new ItemCustomBucket(TFCItemID.Limewater,2).setFolder("tools/").setUnlocalizedName("Lime Water").setContainerItem(WoodenBucketEmpty).setCreativeTab(TFCTabs.TFCMaterials);
		Vinegar = new ItemCustomBucket(TFCItemID.Vinegar,2).setFolder("food/").setUnlocalizedName("Vinegar").setContainerItem(WoodenBucketEmpty).setCreativeTab(TFCTabs.TFCMaterials);
		Hide = new ItemTerra(TFCItemID.Hide).setFolder("tools/").setUnlocalizedName("Hide").setCreativeTab(TFCTabs.TFCMaterials);
		SoakedHide = new ItemTerra(TFCItemID.SoakedHide).setFolder("tools/").setUnlocalizedName("Soaked Hide").setCreativeTab(TFCTabs.TFCMaterials);
		ScrapedHide = new ItemTerra(TFCItemID.ScrapedHide).setFolder("tools/").setUnlocalizedName("Scraped Hide").setCreativeTab(TFCTabs.TFCMaterials);
		PrepHide = new ItemTerra(TFCItemID.PrepHide).setFolder("tools/").setFolder("tools/").setUnlocalizedName("Prep Hide").setCreativeTab(TFCTabs.TFCMaterials);

		SheepSkin = new ItemTerra(TFCItemID.SheepSkin).setFolder("tools/").setUnlocalizedName("Sheep Skin").setCreativeTab(TFCTabs.TFCMaterials);
		muttonRaw = new ItemTerra(TFCItemID.muttonRaw).setFolder("food/").setUnlocalizedName("Mutton Raw");
		muttonCooked =  new ItemTerraFood(TFCItemID.muttonCooked, 40, 0.8F, true, 48).setUnlocalizedName("Mutton Cooked");
		FlatLeather = (new ItemFlatGeneric(TFCItemID.FlatLeather2).setFolder("tools/").setUnlocalizedName("Flat Leather"));
		TerraLeather = new ItemLeather(TFCItemID.TFCLeather).setSpecialCraftingType(FlatLeather).setFolder("tools/").setUnlocalizedName("TFC Leather");

		Straw = new ItemTerra(TFCItemID.Straw).setFolder("plants/").setUnlocalizedName("Straw");
		FlatClay = (new ItemFlatGeneric(TFCItemID.FlatClay).setFolder("pottery/").setMetaNames(new String[]{"clay flat light", "clay flat dark", "clay flat fire", "clay flat dark fire"}).setUnlocalizedName(""));

		PotteryJug = new ItemPotteryJug(TFCItemID.PotteryJug).setUnlocalizedName("Jug");
		PotterySmallVessel = new ItemPotterySmallVessel(TFCItemID.PotterySmallVessel).setUnlocalizedName("Small Vessel");
		PotteryLargeVessel = new ItemPotteryLargeVessel(TFCItemID.PotteryLargeVessel).setUnlocalizedName("Large Vessel");
		PotteryPot = new ItemPotteryPot(TFCItemID.PotteryPot).setUnlocalizedName("Pot");
		CeramicMold = new ItemPotteryBase(TFCItemID.CeramicMold).setMetaNames(new String[]{"Clay Mold","Ceramic Mold"}).setUnlocalizedName("Mold");
		Item.itemsList[Item.clay.itemID] = null; Item.itemsList[Item.clay.itemID] = (new ItemClay(Item.clay.itemID).setSpecialCraftingType(FlatClay, new ItemStack(FlatClay, 1, 1))).setMetaNames(new String[]{"Clay", "Fire Clay"}).setUnlocalizedName("clay");
		ClayMoldAxe = new ItemPotteryMold(TFCItemID.ClayMoldAxe).setMetaNames(new String[]{"Clay Mold Axe","Ceramic Mold Axe",
				"Ceramic Mold Axe Copper","Ceramic Mold Axe Bronze","Ceramic Mold Axe Bismuth Bronze","Ceramic Mold Axe Black Bronze"}).setUnlocalizedName("Axe Mold");
		ClayMoldChisel = new ItemPotteryMold(TFCItemID.ClayMoldChisel).setMetaNames(new String[]{"Clay Mold Chisel","Ceramic Mold Chisel",
				"Ceramic Mold Chisel Copper","Ceramic Mold Chisel Bronze","Ceramic Mold Chisel Bismuth Bronze","Ceramic Mold Chisel Black Bronze"}).setUnlocalizedName("Chisel Mold");
		ClayMoldHammer = new ItemPotteryMold(TFCItemID.ClayMoldHammer).setMetaNames(new String[]{"Clay Mold Hammer","Ceramic Mold Hammer",
				"Ceramic Mold Hammer Copper","Ceramic Mold Hammer Bronze","Ceramic Mold Hammer Bismuth Bronze","Ceramic Mold Hammer Black Bronze"}).setUnlocalizedName("Hammer Mold");
		ClayMoldHoe = new ItemPotteryMold(TFCItemID.ClayMoldHoe).setMetaNames(new String[]{"Clay Mold Hoe","Ceramic Mold Hoe",
				"Ceramic Mold Hoe Copper","Ceramic Mold Hoe Bronze","Ceramic Mold Hoe Bismuth Bronze","Ceramic Mold Hoe Black Bronze"}).setUnlocalizedName("Hoe Mold");
		ClayMoldKnife = new ItemPotteryMold(TFCItemID.ClayMoldKnife).setMetaNames(new String[]{"Clay Mold Knife","Ceramic Mold Knife",
				"Ceramic Mold Knife Copper","Ceramic Mold Knife Bronze","Ceramic Mold Knife Bismuth Bronze","Ceramic Mold Knife Black Bronze"}).setUnlocalizedName("Knife Mold");
		ClayMoldMace = new ItemPotteryMold(TFCItemID.ClayMoldMace).setMetaNames(new String[]{"Clay Mold Mace","Ceramic Mold Mace",
				"Ceramic Mold Mace Copper","Ceramic Mold Mace Bronze","Ceramic Mold Mace Bismuth Bronze","Ceramic Mold Mace Black Bronze"}).setUnlocalizedName("Mace Mold");
		ClayMoldPick = new ItemPotteryMold(TFCItemID.ClayMoldPick).setMetaNames(new String[]{"Clay Mold Pick","Ceramic Mold Pick",
				"Ceramic Mold Pick Copper","Ceramic Mold Pick Bronze","Ceramic Mold Pick Bismuth Bronze","Ceramic Mold Pick Black Bronze"}).setUnlocalizedName("Pick Mold");
		ClayMoldProPick = new ItemPotteryMold(TFCItemID.ClayMoldProPick).setMetaNames(new String[]{"Clay Mold ProPick","Ceramic Mold ProPick",
				"Ceramic Mold ProPick Copper","Ceramic Mold ProPick Bronze","Ceramic Mold ProPick Bismuth Bronze","Ceramic Mold ProPick Black Bronze"}).setUnlocalizedName("ProPick Mold");
		ClayMoldSaw = new ItemPotteryMold(TFCItemID.ClayMoldSaw).setMetaNames(new String[]{"Clay Mold Saw","Ceramic Mold Saw",
				"Ceramic Mold Saw Copper","Ceramic Mold Saw Bronze","Ceramic Mold Saw Bismuth Bronze","Ceramic Mold Saw Black Bronze"}).setUnlocalizedName("Saw Mold");
		ClayMoldScythe = new ItemPotteryMold(TFCItemID.ClayMoldScythe).setMetaNames(new String[]{"Clay Mold Scythe","Ceramic Mold Scythe",
				"Ceramic Mold Scythe Copper","Ceramic Mold Scythe Bronze","Ceramic Mold Scythe Bismuth Bronze","Ceramic Mold Scythe Black Bronze"}).setUnlocalizedName("Scythe Mold");
		ClayMoldShovel = new ItemPotteryMold(TFCItemID.ClayMoldShovel).setMetaNames(new String[]{"Clay Mold Shovel","Ceramic Mold Shovel",
				"Ceramic Mold Shovel Copper","Ceramic Mold Shovel Bronze","Ceramic Mold Shovel Bismuth Bronze","Ceramic Mold Shovel Black Bronze"}).setUnlocalizedName("Shovel Mold");
		ClayMoldSword = new ItemPotteryMold(TFCItemID.ClayMoldSword).setMetaNames(new String[]{"Clay Mold Sword","Ceramic Mold Sword",
				"Ceramic Mold Sword Copper","Ceramic Mold Sword Bronze","Ceramic Mold Sword Bismuth Bronze","Ceramic Mold Sword Black Bronze"}).setUnlocalizedName("Sword Mold");
		ClayMoldJavelin = new ItemPotteryMold(TFCItemID.ClayMoldJavelin).setMetaNames(new String[]{"Clay Mold Javelin","Ceramic Mold Javelin",
				"Ceramic Mold Javelin Copper","Ceramic Mold Javelin Bronze","Ceramic Mold Javelin Bismuth Bronze","Ceramic Mold Javelin Black Bronze"}).setUnlocalizedName("Javelin Mold");

		TuyereCopper = new ItemTuyere(TFCItemID.TuyereCopper, 40, 0).setUnlocalizedName("Copper Tuyere");
		TuyereBronze = new ItemTuyere(TFCItemID.TuyereBronze, 80, 1).setUnlocalizedName("Bronze Tuyere");
		TuyereBlackBronze = new ItemTuyere(TFCItemID.TuyereBlackBronze, 80, 1).setUnlocalizedName("Black Bronze Tuyere");
		TuyereBismuthBronze = new ItemTuyere(TFCItemID.TuyereBismuthBronze, 80, 1).setUnlocalizedName("Bismuth Bronze Tuyere");
		TuyereWroughtIron = new ItemTuyere(TFCItemID.TuyereWroughtIron, 120, 2).setUnlocalizedName("Wrought Iron Tuyere");
		TuyereSteel = new ItemTuyere(TFCItemID.TuyereSteel, 180, 3).setUnlocalizedName("Steel Tuyere");
		TuyereBlackSteel = new ItemTuyere(TFCItemID.TuyereBlackSteel, 260, 4).setUnlocalizedName("Black Steel Tuyere");
		TuyereRedSteel = new ItemTuyere(TFCItemID.TuyereRedSteel, 400, 5).setUnlocalizedName("Red Steel Tuyere");
		TuyereBlueSteel = new ItemTuyere(TFCItemID.TuyereBlueSteel, 500, 6).setUnlocalizedName("Blue Steel Tuyere");

		Bloom = new ItemBloom(TFCItemID.Bloom).setFolder("ingots/").setUnlocalizedName("Iron Bloom");
		RawBloom = new ItemBloom(TFCItemID.RawBloom).setFolder("ingots/").setUnlocalizedName("Raw Iron Bloom");

		UnknownIngot = new ItemIngot(TFCItemID.UnknownIngot).setUnlocalizedName("Unknown Ingot");
		UnknownUnshaped = new ItemMeltedMetal(TFCItemID.UnknownUnshaped).setUnlocalizedName("Unknown Unshaped");

		// Plans
		PickaxeHeadPlan = new ItemPlan(TFCItemID.PickaxeHeadPlan).setUnlocalizedName("PickaxeHeadPlan");
		ShovelHeadPlan = new ItemPlan(TFCItemID.ShovelHeadPlan).setUnlocalizedName("ShovelHeadPlan");
		HoeHeadPlan = new ItemPlan(TFCItemID.HoeHeadPlan).setUnlocalizedName("HoeHeadPlan");
		AxeHeadPlan = new ItemPlan(TFCItemID.AxeHeadPlan).setUnlocalizedName("AxeHeadPlan");
		HammerHeadPlan = new ItemPlan(TFCItemID.HammerHeadPlan).setUnlocalizedName("HammerHeadPlan");
		ChiselHeadPlan = new ItemPlan(TFCItemID.ChiselHeadPlan).setUnlocalizedName("ChiselHeadPlan");
		SwordBladePlan = new ItemPlan(TFCItemID.SwordBladePlan).setUnlocalizedName("SwordBladePlan");
		MaceHeadPlan = new ItemPlan(TFCItemID.MaceHeadPlan).setUnlocalizedName("MaceHeadPlan");
		SawBladePlan = new ItemPlan(TFCItemID.SawBladePlan).setUnlocalizedName("SawBladePlan");
		ProPickHeadPlan = new ItemPlan(TFCItemID.ProPickHeadPlan).setUnlocalizedName("ProPickHeadPlan");
		HelmetPlan = new ItemPlan(TFCItemID.HelmetPlan).setUnlocalizedName("HelmetPlan");
		ChestplatePlan = new ItemPlan(TFCItemID.ChestplatePlan).setUnlocalizedName("ChestplatePlan");
		GreavesPlan = new ItemPlan(TFCItemID.GreavesPlan).setUnlocalizedName("GreavesPlan");
		BootsPlan = new ItemPlan(TFCItemID.BootsPlan).setUnlocalizedName("BootsPlan");
		ScythePlan = new ItemPlan(TFCItemID.ScythePlan).setUnlocalizedName("ScythePlan");
		KnifePlan = new ItemPlan(TFCItemID.KnifePlan).setUnlocalizedName("KnifePlan");
		BucketPlan = new ItemPlan(TFCItemID.BucketPlan).setUnlocalizedName("BucketPlan");
		JavelinHeadPlan = new ItemPlan(TFCItemID.JavelinHeadPlan).setUnlocalizedName("JavelinHeadPlan");

		// Food related items
		FruitTreeSapling1 = new ItemFruitTreeSapling(TFCItemID.FruitTreeSapling1, 0).setUnlocalizedName("FruitSapling1");
		FruitTreeSapling2 = new ItemFruitTreeSapling(TFCItemID.FruitTreeSapling2, 8).setUnlocalizedName("FruitSapling2");
		RedApple = new ItemTerraFood(TFCItemID.RedApple, 15, 0.1F, false, 2).setUnlocalizedName("Red Apple");
		Banana = new ItemTerraFood(TFCItemID.Banana, 10, 0.1F, false, 3).setUnlocalizedName("Banana");
		Orange = new ItemTerraFood(TFCItemID.Orange, 10, 0.1F, false, 4).setUnlocalizedName("Orange");
		GreenApple = new ItemTerraFood(TFCItemID.GreenApple, 15, 0.1F, false, 5).setUnlocalizedName("Green Apple");
		Lemon = new ItemTerraFood(TFCItemID.Lemon, 10, 0.03F, false, 6).setUnlocalizedName("Lemon");
		Olive = new ItemTerraFood(TFCItemID.Olive, 10, 0.05F, false, 7).setUnlocalizedName("Olive");
		Cherry = new ItemTerraFood(TFCItemID.Cherry, 10, 0.03F, false, 8).setUnlocalizedName("Cherry");
		Peach = new ItemTerraFood(TFCItemID.Peach, 12, 0.1F, false, 9).setUnlocalizedName("Peach");
		Plum = new ItemTerraFood(TFCItemID.Plum, 10, 0.1F, false, 10).setUnlocalizedName("Plum");
		EggCooked = new ItemTerraFood(TFCItemID.EggCooked, 25, 0.4F, false, 11).setUnlocalizedName("Egg Cooked");

		WheatGrain = new ItemTerraFood(TFCItemID.WheatGrain, 1, 0.4F, false, 12).setUnlocalizedName("Wheat Grain");
		BarleyGrain = new ItemTerraFood(TFCItemID.BarleyGrain, 1, 0.4F, false, 14).setUnlocalizedName("Barley Grain");
		OatGrain = new ItemTerraFood(TFCItemID.OatGrain, 1, 0.4F, false, 16).setUnlocalizedName("Oat Grain");
		RyeGrain = new ItemTerraFood(TFCItemID.RyeGrain, 1, 0.4F, false, 18).setUnlocalizedName("Rye Grain");
		RiceGrain = new ItemTerraFood(TFCItemID.RiceGrain, 1, 0.4F, false, 20).setUnlocalizedName("Rice Grain");
		MaizeEar = new ItemTerraFood(TFCItemID.MaizeEar, 10, 0.4F, false, 22).setUnlocalizedName("Maize Ear");
		Tomato = new ItemTerraFood(TFCItemID.Tomato, 15, 0.4F, false, 24).setUnlocalizedName("Tomato");
		Potato = new ItemTerraFood(TFCItemID.Potato, 22, 0.4F, false, 25).setUnlocalizedName("Potato");
		Onion = new ItemTerraFood(TFCItemID.Onion, 10, 0.4F, false, 27).setUnlocalizedName(TFCOptions.iDontLikeOnions?"Rutabaga":"Onion");
		Cabbage = new ItemTerraFood(TFCItemID.Cabbage, 20, 0.4F, false, 28).setUnlocalizedName("Cabbage");
		Garlic = new ItemTerraFood(TFCItemID.Garlic, 10, 0.4F, false, 29).setUnlocalizedName("Garlic");
		Carrot = new ItemTerraFood(TFCItemID.Carrot, 5, 0.4F, false, 30).setUnlocalizedName("Carrot");
		Sugarcane = new ItemTerra(TFCItemID.Sugarcane).setFolder("plants/").setUnlocalizedName("Sugarcane");
		Hemp = new ItemTerra(TFCItemID.Hemp).setFolder("plants/").setUnlocalizedName("Hemp");
		Soybean = new ItemTerraFood(TFCItemID.Soybean, 10, 0.4F, false, 31).setUnlocalizedName("Soybeans");
		Greenbeans = new ItemTerraFood(TFCItemID.Greenbeans, 10, 0.4F, false, 32).setUnlocalizedName("Greenbeans");
		GreenBellPepper = new ItemTerraFood(TFCItemID.GreenBellPepper, 10, 0.4F, false, 34).setUnlocalizedName("Green Bell Pepper");
		YellowBellPepper = new ItemTerraFood(TFCItemID.YellowBellPepper, 10, 0.4F, false, 35).setUnlocalizedName("Yellow Bell Pepper");
		RedBellPepper = new ItemTerraFood(TFCItemID.RedBellPepper, 10, 0.4F, false, 36).setUnlocalizedName("Red Bell Pepper");
		Squash = new ItemTerraFood(TFCItemID.Squash, 12, 0.4F, false, 37).setUnlocalizedName("Squash");
		Cheese = new ItemTerraFood(TFCItemID.Cheese, 20, 0.6F, false, 50).setUnlocalizedName("Cheese");

		WheatWhole = new ItemTerra(TFCItemID.WheatWhole).setFolder("food/").setUnlocalizedName("Wheat Whole");
		BarleyWhole = new ItemTerra(TFCItemID.BarleyWhole).setFolder("food/").setUnlocalizedName("Barley Whole");
		OatWhole = new ItemTerra(TFCItemID.OatWhole).setFolder("food/").setUnlocalizedName("Oat Whole");
		RyeWhole = new ItemTerra(TFCItemID.RyeWhole).setFolder("food/").setUnlocalizedName("Rye Whole");
		RiceWhole = new ItemTerra(TFCItemID.RiceWhole).setFolder("food/").setUnlocalizedName("Rice Whole");

		MealGeneric = new ItemMeal(TFCItemID.MealGeneric, 0).setUnlocalizedName("MealGeneric");
		MealMoveSpeed = new ItemMeal(TFCItemID.MealMoveSpeed, 1).setPotionEffect(new PotionEffect(Potion.moveSpeed.id,8000,1)).setUnlocalizedName("MealGeneric");
		MealDigSpeed = new ItemMeal(TFCItemID.MealDigSpeed, 2).setPotionEffect(new PotionEffect(Potion.digSpeed.id,8000,1)).setUnlocalizedName("MealGeneric");
		MealDamageBoost = new ItemMeal(TFCItemID.MealDamageBoost, 3).setPotionEffect(new PotionEffect(Potion.damageBoost.id,4000,1)).setUnlocalizedName("MealGeneric");
		MealJump = new ItemMeal(TFCItemID.MealJump, 4).setPotionEffect(new PotionEffect(Potion.jump.id,8000,1)).setUnlocalizedName("MealGeneric");
		MealDamageResist = new ItemMeal(TFCItemID.MealDamageResist, 5).setPotionEffect(new PotionEffect(Potion.resistance.id,8000,1)).setUnlocalizedName("MealGeneric");
		MealFireResist = new ItemMeal(TFCItemID.MealFireResist, 6).setPotionEffect(new PotionEffect(Potion.fireResistance.id,8000,1)).setUnlocalizedName("MealGeneric");
		MealWaterBreathing = new ItemMeal(TFCItemID.MealWaterBreathing, 7).setPotionEffect(new PotionEffect(Potion.waterBreathing.id,8000,1)).setUnlocalizedName("MealGeneric");
		MealNightVision = new ItemMeal(TFCItemID.MealNightVision, 8).setPotionEffect(new PotionEffect(Potion.nightVision.id,4000,1)).setUnlocalizedName("MealGeneric");

		WheatGround = new ItemTerra(TFCItemID.WheatGround).setFolder("food/").setUnlocalizedName("Wheat Ground");
		BarleyGround = new ItemTerra(TFCItemID.BarleyGround).setFolder("food/").setUnlocalizedName("Barley Ground");
		OatGround = new ItemTerra(TFCItemID.OatGround).setFolder("food/").setUnlocalizedName("Oat Ground");
		RyeGround = new ItemTerra(TFCItemID.RyeGround).setFolder("food/").setUnlocalizedName("Rye Ground");
		RiceGround = new ItemTerra(TFCItemID.RiceGround).setFolder("food/").setUnlocalizedName("Rice Ground");
		CornmealGround = new ItemTerra(TFCItemID.CornmealGround).setFolder("food/").setUnlocalizedName("Cornmeal Ground");

		WheatDough = new ItemTerraFood(TFCItemID.WheatDough, 1, 0.0F, false, 0).setUnlocalizedName("Wheat Dough");
		BarleyDough = new ItemTerraFood(TFCItemID.BarleyDough, 1, 0.0F, false, 0).setUnlocalizedName("Barley Dough");
		OatDough = new ItemTerraFood(TFCItemID.OatDough, 1, 0.0F, false, 0).setUnlocalizedName("Oat Dough");
		RyeDough = new ItemTerraFood(TFCItemID.RyeDough, 1, 0.0F, false, 0).setUnlocalizedName("Rye Dough");
		RiceDough = new ItemTerraFood(TFCItemID.RiceDough, 1, 0.0F, false, 0).setUnlocalizedName("Rice Dough");
		CornmealDough = new ItemTerraFood(TFCItemID.CornmealDough, 1, 0.0F, false, 0).setUnlocalizedName("Cornmeal Dough");

		WheatBread = new ItemTerraFood(TFCItemID.WheatBread, 25, 0.6F, false, 42).setUnlocalizedName("Wheat Bread");
		BarleyBread = new ItemTerraFood(TFCItemID.BarleyBread, 25, 0.6F, false, 43).setUnlocalizedName("Barley Bread");
		OatBread = new ItemTerraFood(TFCItemID.OatBread, 25, 0.6F, false, 44).setUnlocalizedName("Oat Bread");
		RyeBread = new ItemTerraFood(TFCItemID.RyeBread, 25, 0.6F, false, 45).setUnlocalizedName("Rye Bread");
		RiceBread = new ItemTerraFood(TFCItemID.RiceBread, 25, 0.6F, false, 46).setUnlocalizedName("Rice Bread");
		CornBread = new ItemTerraFood(TFCItemID.CornBread, 25, 0.6F, false, 47).setUnlocalizedName("Corn Bread");

		CalamariRaw = new ItemTerra(TFCItemID.CalamariRaw).setFolder("").setUnlocalizedName("Calamari Raw");
		CalamariCooked = new ItemTerraFood(TFCItemID.CalamariCooked, 29, 0.6F, true, 49).setFolder("").setUnlocalizedName("Calamari Cooked");

		SeedsWheat = new ItemCustomSeeds(TFCItemID.SeedsWheat,0).setUnlocalizedName("Seeds Wheat");
		SeedsBarley = new ItemCustomSeeds(TFCItemID.SeedsBarley,5).setUnlocalizedName("Seeds Barley");
		SeedsRye = new ItemCustomSeeds(TFCItemID.SeedsRye,7).setUnlocalizedName("Seeds Rye");
		SeedsOat = new ItemCustomSeeds(TFCItemID.SeedsOat,9).setUnlocalizedName("Seeds Oat");
		SeedsRice = new ItemCustomSeeds(TFCItemID.SeedsRice,11).setUnlocalizedName("Seeds Rice");
		SeedsMaize = new ItemCustomSeeds(TFCItemID.SeedsMaize,2).setUnlocalizedName("Seeds Maize");
		SeedsPotato = new ItemCustomSeeds(TFCItemID.SeedsPotato,13).setUnlocalizedName("Seeds Potato");
		SeedsOnion = new ItemCustomSeeds(TFCItemID.SeedsOnion,15).setUnlocalizedName(TFCOptions.iDontLikeOnions?"Seeds Rutabaga":"Seeds Onion");
		SeedsCabbage = new ItemCustomSeeds(TFCItemID.SeedsCabbage,16).setUnlocalizedName("Seeds Cabbage");
		SeedsGarlic = new ItemCustomSeeds(TFCItemID.SeedsGarlic,17).setUnlocalizedName("Seeds Garlic");
		SeedsCarrot = new ItemCustomSeeds(TFCItemID.SeedsCarrot,18).setUnlocalizedName("Seeds Carrot");
		SeedsSugarcane = new ItemCustomSeeds(TFCItemID.SeedsSugarcane,21).setUnlocalizedName("Seeds Sugarcane");
		SeedsHemp = new ItemCustomSeeds(TFCItemID.SeedsHemp,22).setUnlocalizedName("Seeds Hemp");
		SeedsTomato = new ItemCustomSeeds(TFCItemID.SeedsTomato,4).setUnlocalizedName("Seeds Tomato");
		SeedsYellowBellPepper = new ItemCustomSeeds(TFCItemID.SeedsYellowBellPepper,19).setUnlocalizedName("Seeds Yellow Bell Pepper");
		SeedsRedBellPepper = new ItemCustomSeeds(TFCItemID.SeedsRedBellPepper,20).setUnlocalizedName("Seeds Red Bell Pepper");
		SeedsSoybean = new ItemCustomSeeds(TFCItemID.SeedsSoybean,21).setUnlocalizedName("Seeds Soybean");
		SeedsGreenbean = new ItemCustomSeeds(TFCItemID.SeedsGreenbean,22).setUnlocalizedName("Seeds Greenbean");
		SeedsSquash = new ItemCustomSeeds(TFCItemID.SeedsSquash,23).setUnlocalizedName("Seeds Squash");

		/**Armor Crafting related items*/
		SetupArmor();

		Recipes.Doors = new Item[]{DoorOak, DoorAspen, DoorBirch, DoorChestnut, DoorDouglasFir, 
				DoorHickory, DoorMaple, DoorAsh, DoorPine, DoorSequoia, DoorSpruce, DoorSycamore, 
				DoorWhiteCedar, DoorWhiteElm, DoorWillow, DoorKapok};

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
				TFCItems.BlackSteelUnshaped,TFCItems.BlueSteelUnshaped,TFCItems.BrassUnshaped,TFCItems.BronzeUnshaped,
				TFCItems.CopperUnshaped,TFCItems.GoldUnshaped,
				TFCItems.WroughtIronUnshaped,TFCItems.LeadUnshaped,TFCItems.NickelUnshaped,TFCItems.PigIronUnshaped,
				TFCItems.PlatinumUnshaped,TFCItems.RedSteelUnshaped,TFCItems.RoseGoldUnshaped,TFCItems.SilverUnshaped,
				TFCItems.SteelUnshaped,TFCItems.SterlingSilverUnshaped,
				TFCItems.TinUnshaped,TFCItems.ZincUnshaped, TFCItems.HCSteelUnshaped, TFCItems.WeakSteelUnshaped,
				TFCItems.HCBlackSteelUnshaped, TFCItems.HCBlueSteelUnshaped, TFCItems.HCRedSteelUnshaped, 
				TFCItems.WeakBlueSteelUnshaped, TFCItems.WeakRedSteelUnshaped};

		Recipes.Hammers  = new Item[]{TFCItems.StoneHammer,TFCItems.BismuthBronzeHammer,TFCItems.BlackBronzeHammer,
				TFCItems.BlackSteelHammer,TFCItems.BlueSteelHammer,TFCItems.BronzeHammer,TFCItems.CopperHammer,
				TFCItems.WroughtIronHammer,TFCItems.RedSteelHammer,TFCItems.SteelHammer};

		Recipes.Scythes = new Item[]{TFCItems.BismuthBronzeScythe,TFCItems.BlackBronzeScythe,
				TFCItems.BlackSteelScythe,TFCItems.BlueSteelScythe,TFCItems.BronzeScythe,TFCItems.CopperScythe,
				TFCItems.WroughtIronScythe,TFCItems.RedSteelScythe,TFCItems.SteelScythe};

		Recipes.Spindle = new Item[]{TFCItems.Spindle};

		Recipes.Gems  = new Item[]{TFCItems.GemAgate, TFCItems.GemAmethyst, TFCItems.GemBeryl, TFCItems.GemDiamond, TFCItems.GemEmerald, TFCItems.GemGarnet, 
				TFCItems.GemJade, TFCItems.GemJasper, TFCItems.GemOpal,TFCItems.GemRuby,TFCItems.GemSapphire,TFCItems.GemTopaz,TFCItems.GemTourmaline};

		Meals = new Item[]{MealMoveSpeed, MealDigSpeed, MealDamageBoost, MealJump, MealDamageResist, 
				MealFireResist, MealWaterBreathing, MealNightVision};

		((TFCTabs)TFCTabs.TFCTools).setTabIconItemIndex(TFCItems.SteelHammer.itemID);
		((TFCTabs)TFCTabs.TFCMaterials).setTabIconItemIndex(TFCItems.LeadIngot.itemID);
		((TFCTabs)TFCTabs.TFCUnfinished).setTabIconItemIndex(TFCItems.SteelHammerHead.itemID);
		((TFCTabs)TFCTabs.TFCArmor).setTabIconItemIndex(TFCItems.SteelHelmet.itemID);      
		((TFCTabs)TFCTabs.TFCMisc).setTabIconItemIndex(TFCItems.Spindle.itemID);  
		((TFCTabs)TFCTabs.TFCPottery).setTabIconItemStack(new ItemStack(PotteryJug, 1, 1));  
		((TFCTabs)TFCTabs.TFCWeapons).setTabIconItemStack(new ItemStack(SteelSword, 1));  

		registerMetals();

		System.out.println(new StringBuilder().append("[TFC] Done Loading Items").toString());
	}

	private static void registerMetals() 
	{
		Global.BISMUTH = new Metal("Bismuth", BismuthUnshaped.itemID, BismuthIngot.itemID);
		Global.BISMUTHBRONZE = new Metal("Bismuth Bronze", BismuthBronzeUnshaped.itemID, BismuthBronzeIngot.itemID);
		Global.BLACKBRONZE = new Metal("Black Bronze", BlackBronzeUnshaped.itemID, BlackBronzeIngot.itemID); 
		Global.BLACKSTEEL = new Metal("Black Steel", BlackSteelUnshaped.itemID, BlackSteelIngot.itemID); 
		Global.BLUESTEEL = new Metal("Blue Steel", BlueSteelUnshaped.itemID, BlueSteelIngot.itemID); 
		Global.BRASS = new Metal("Brass", BrassUnshaped.itemID, BrassIngot.itemID); 
		Global.BRONZE = new Metal("Bronze", BronzeUnshaped.itemID, BronzeIngot.itemID);
		Global.COPPER = new Metal("Copper", CopperUnshaped.itemID, CopperIngot.itemID);
		Global.GOLD = new Metal("Gold", GoldUnshaped.itemID, GoldIngot.itemID); 
		Global.WROUGHTIRON = new Metal("Wrought Iron", WroughtIronUnshaped.itemID, WroughtIronIngot.itemID); 
		Global.LEAD = new Metal("Lead", LeadUnshaped.itemID, LeadIngot.itemID); 
		Global.NICKEL = new Metal("Nickel", NickelUnshaped.itemID, NickelIngot.itemID); 
		Global.PIGIRON = new Metal("Pig Iron", PigIronUnshaped.itemID, PigIronIngot.itemID); 
		Global.PLATINUM = new Metal("Platinum", PlatinumUnshaped.itemID, PlatinumIngot.itemID); 
		Global.REDSTEEL = new Metal("Red Steel", RedSteelUnshaped.itemID, RedSteelIngot.itemID); 
		Global.ROSEGOLD = new Metal("Rose Gold", RoseGoldUnshaped.itemID, RoseGoldIngot.itemID); 
		Global.SILVER = new Metal("Silver", SilverUnshaped.itemID, SilverIngot.itemID); 
		Global.STEEL = new Metal("Steel", SteelUnshaped.itemID, SteelIngot.itemID); 
		Global.STERLINGSILVER = new Metal("Sterling Silver", SterlingSilverUnshaped.itemID, SterlingSilverIngot.itemID); 
		Global.TIN = new Metal("Tin", TinUnshaped.itemID, TinIngot.itemID);
		Global.ZINC = new Metal("Zinc", ZincUnshaped.itemID, ZincIngot.itemID);
		Global.WEAKSTEEL = new Metal("Weak Steel", WeakSteelUnshaped.itemID, WeakSteelIngot.itemID); 
		Global.HCBLACKSTEEL = new Metal("HC Black Steel", HCBlackSteelUnshaped.itemID, HCBlackSteelIngot.itemID); 
		Global.WEAKREDSTEEL = new Metal("Weak Red Steel", WeakRedSteelUnshaped.itemID, WeakRedSteelIngot.itemID); 
		Global.HCREDSTEEL = new Metal("HC Red Steel", HCRedSteelUnshaped.itemID, HCRedSteelIngot.itemID); 
		Global.WEAKBLUESTEEL = new Metal("Weak Blue Steel", WeakBlueSteelUnshaped.itemID, WeakBlueSteelIngot.itemID);
		Global.HCBLUESTEEL = new Metal("HC Blue Steel", HCBlueSteelUnshaped.itemID, HCBlueSteelIngot.itemID); 
		Global.UNKNOWN = new Metal("Unknown", UnknownUnshaped.itemID, UnknownIngot.itemID); 

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

		Alloy Bronze = new Alloy(Global.BRONZE, Alloy.EnumTier.TierI);
		Bronze.addIngred(Global.COPPER, 88, 92);
		Bronze.addIngred(Global.TIN, 8, 12);
		AlloyManager.instance.addAlloy(Bronze);

		Alloy Brass = new Alloy(Global.BRASS, Alloy.EnumTier.TierI);
		Brass.addIngred(Global.COPPER, 88, 92);
		Brass.addIngred(Global.ZINC, 8, 12);
		AlloyManager.instance.addAlloy(Brass);

		Alloy RoseGold = new Alloy(Global.ROSEGOLD, Alloy.EnumTier.TierI);
		RoseGold.addIngred(Global.GOLD, 70, 85);
		RoseGold.addIngred(Global.COPPER, 15, 30);
		AlloyManager.instance.addAlloy(RoseGold);

		Alloy BlackBronze = new Alloy(Global.BLACKBRONZE, Alloy.EnumTier.TierI);
		BlackBronze.addIngred(Global.GOLD, 10, 25);
		BlackBronze.addIngred(Global.COPPER, 50, 70);
		BlackBronze.addIngred(Global.SILVER, 10, 25);
		AlloyManager.instance.addAlloy(BlackBronze);

		Alloy BismuthBronze = new Alloy(Global.BISMUTHBRONZE, Alloy.EnumTier.TierI);
		BismuthBronze.addIngred(Global.ZINC, 20, 30);
		BismuthBronze.addIngred(Global.COPPER, 50, 70);
		BismuthBronze.addIngred(Global.BISMUTH, 10, 20);
		AlloyManager.instance.addAlloy(BismuthBronze);

		Alloy SterlingSilver = new Alloy(Global.STERLINGSILVER, Alloy.EnumTier.TierI);
		SterlingSilver.addIngred(Global.SILVER, 60, 80);
		SterlingSilver.addIngred(Global.COPPER, 20, 40);
		AlloyManager.instance.addAlloy(SterlingSilver);

		Alloy WeakSteel = new Alloy(Global.WEAKSTEEL, Alloy.EnumTier.TierIII);
		WeakSteel.addIngred(Global.STEEL, 50, 70);
		WeakSteel.addIngred(Global.NICKEL, 15, 25);
		WeakSteel.addIngred(Global.BLACKBRONZE, 15, 25);
		AlloyManager.instance.addAlloy(WeakSteel);

		Alloy WeakRedSteel = new Alloy(Global.WEAKREDSTEEL, Alloy.EnumTier.TierIII);
		WeakRedSteel.addIngred(Global.BLACKSTEEL, 50, 60);
		WeakRedSteel.addIngred(Global.ROSEGOLD, 10, 15);
		WeakRedSteel.addIngred(Global.BRASS, 10, 15);
		WeakRedSteel.addIngred(Global.STEEL, 20, 25);
		AlloyManager.instance.addAlloy(WeakRedSteel);

		Alloy WeakBlueSteel = new Alloy(Global.WEAKBLUESTEEL, Alloy.EnumTier.TierIII);
		WeakBlueSteel.addIngred(Global.BLACKSTEEL, 50, 60);
		WeakBlueSteel.addIngred(Global.BISMUTHBRONZE, 10, 15);
		WeakBlueSteel.addIngred(Global.STERLINGSILVER, 10, 15);
		WeakBlueSteel.addIngred(Global.STEEL, 20, 25);
		AlloyManager.instance.addAlloy(WeakBlueSteel);
	}

	public static void SetupArmor()
	{
		String[] Names = {"Bismuth Bronze", "Black Bronze", "Black Steel", "Blue Steel", "Bronze", "Copper", "Wrought Iron", "Red Steel", "Steel"};
		String[] NamesNSO = {"Brass", "Gold", "Lead", "Nickel", "Pig Iron", "Platinum", "Silver", "Sterling Silver"};
		CommonProxy proxy = TerraFirmaCraft.proxy;

		TFCItems.BismuthSheet = 		(new ItemMetalSheet(TFCItemID.BismuthSheet).setUnlocalizedName("Bismuth Sheet"));
		TFCItems.BismuthBronzeSheet = 	(new ItemMetalSheet(TFCItemID.BismuthBronzeSheet).setUnlocalizedName("Bismuth Bronze Sheet"));
		TFCItems.BlackBronzeSheet = 	(new ItemMetalSheet(TFCItemID.BlackBronzeSheet).setUnlocalizedName("Black Bronze Sheet"));
		TFCItems.BlackSteelSheet = 		(new ItemMetalSheet(TFCItemID.BlackSteelSheet).setUnlocalizedName("Black Steel Sheet"));
		TFCItems.BlueSteelSheet = 		(new ItemMetalSheet(TFCItemID.BlueSteelSheet).setUnlocalizedName("Blue Steel Sheet"));
		TFCItems.BronzeSheet = 			(new ItemMetalSheet(TFCItemID.BronzeSheet).setUnlocalizedName("Bronze Sheet"));
		TFCItems.CopperSheet = 			(new ItemMetalSheet(TFCItemID.CopperSheet).setUnlocalizedName("Copper Sheet"));
		TFCItems.WroughtIronSheet = 	(new ItemMetalSheet(TFCItemID.WroughtIronSheet).setUnlocalizedName("Wrought Iron Sheet"));
		TFCItems.RedSteelSheet = 		(new ItemMetalSheet(TFCItemID.RedSteelSheet).setUnlocalizedName("Red Steel Sheet"));
		TFCItems.RoseGoldSheet = 		(new ItemMetalSheet(TFCItemID.RoseGoldSheet).setUnlocalizedName("Rose Gold Sheet"));
		TFCItems.SteelSheet = 			(new ItemMetalSheet(TFCItemID.SteelSheet).setUnlocalizedName("Steel Sheet"));
		TFCItems.TinSheet = 			(new ItemMetalSheet(TFCItemID.TinSheet).setUnlocalizedName("Tin Sheet"));
		TFCItems.ZincSheet = 			(new ItemMetalSheet(TFCItemID.ZincSheet).setUnlocalizedName("Zinc Sheet"));

		TFCItems.BismuthSheet2x = 		(new ItemMetalSheet2x(TFCItemID.BismuthSheet2x).setUnlocalizedName("Bismuth Double Sheet"));
		TFCItems.BismuthBronzeSheet2x = (new ItemMetalSheet2x(TFCItemID.BismuthBronzeSheet2x).setUnlocalizedName("Bismuth Bronze Double Sheet"));
		TFCItems.BlackBronzeSheet2x = 	(new ItemMetalSheet2x(TFCItemID.BlackBronzeSheet2x).setUnlocalizedName("Black Bronze Double Sheet"));
		TFCItems.BlackSteelSheet2x = 	(new ItemMetalSheet2x(TFCItemID.BlackSteelSheet2x).setUnlocalizedName("Black Steel Double Sheet"));
		TFCItems.BlueSteelSheet2x = 	(new ItemMetalSheet2x(TFCItemID.BlueSteelSheet2x).setUnlocalizedName("Blue Steel Double Sheet"));
		TFCItems.BronzeSheet2x = 		(new ItemMetalSheet2x(TFCItemID.BronzeSheet2x).setUnlocalizedName("Bronze Double Sheet"));
		TFCItems.CopperSheet2x = 		(new ItemMetalSheet2x(TFCItemID.CopperSheet2x).setUnlocalizedName("Copper Double Sheet"));
		TFCItems.WroughtIronSheet2x = 	(new ItemMetalSheet2x(TFCItemID.WroughtIronSheet2x).setUnlocalizedName("Wrought Iron Double Sheet"));
		TFCItems.RedSteelSheet2x = 		(new ItemMetalSheet2x(TFCItemID.RedSteelSheet2x).setUnlocalizedName("Red Steel Double Sheet"));
		TFCItems.RoseGoldSheet2x = 		(new ItemMetalSheet2x(TFCItemID.RoseGoldSheet2x).setUnlocalizedName("Rose Gold Double Sheet"));
		TFCItems.SteelSheet2x = 		(new ItemMetalSheet2x(TFCItemID.SteelSheet2x).setUnlocalizedName("Steel Double Sheet"));
		TFCItems.TinSheet2x = 			(new ItemMetalSheet2x(TFCItemID.TinSheet2x).setUnlocalizedName("Tin Double Sheet"));
		TFCItems.ZincSheet2x = 			(new ItemMetalSheet2x(TFCItemID.ZincSheet2x).setUnlocalizedName("Zinc Double Sheet"));

		int i = 0;
		TFCItems.BismuthBronzeUnfinishedBoots = 	(new ItemUnfinishedArmor(TFCItemID.BismuthBronzeUnfinishedBoots).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		TFCItems.BlackBronzeUnfinishedBoots = 		(new ItemUnfinishedArmor(TFCItemID.BlackBronzeUnfinishedBoots).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		TFCItems.BlackSteelUnfinishedBoots = 		(new ItemUnfinishedArmor(TFCItemID.BlackSteelUnfinishedBoots).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		TFCItems.BlueSteelUnfinishedBoots = 		(new ItemUnfinishedArmor(TFCItemID.BlueSteelUnfinishedBoots).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		TFCItems.BronzeUnfinishedBoots = 			(new ItemUnfinishedArmor(TFCItemID.BronzeUnfinishedBoots).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		TFCItems.CopperUnfinishedBoots = 			(new ItemUnfinishedArmor(TFCItemID.CopperUnfinishedBoots).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		TFCItems.WroughtIronUnfinishedBoots = 		(new ItemUnfinishedArmor(TFCItemID.WroughtIronUnfinishedBoots).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		TFCItems.RedSteelUnfinishedBoots = 			(new ItemUnfinishedArmor(TFCItemID.RedSteelUnfinishedBoots).setUnlocalizedName(Names[i]+" Unfinished Boots")); i++;
		TFCItems.SteelUnfinishedBoots = 			(new ItemUnfinishedArmor(TFCItemID.SteelUnfinishedBoots).setUnlocalizedName(Names[i]+" Unfinished Boots"));

		i = 0;
		TFCItems.BismuthBronzeBoots = 	(new ItemTFCArmor(TFCItemID.BismuthBronzeBoots, Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 3).setUnlocalizedName(Names[i]+" Boots")); i++;
		TFCItems.BlackBronzeBoots = 	(new ItemTFCArmor(TFCItemID.BlackBronzeBoots, Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 3).setUnlocalizedName(Names[i]+" Boots")); i++;
		TFCItems.BlackSteelBoots = 		(new ItemTFCArmor(TFCItemID.BlackSteelBoots, Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 3).setUnlocalizedName(Names[i]+" Boots")); i++;
		TFCItems.BlueSteelBoots = 		(new ItemTFCArmor(TFCItemID.BlueSteelBoots, Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 3).setUnlocalizedName(Names[i]+" Boots")); i++;
		TFCItems.BronzeBoots = 			(new ItemTFCArmor(TFCItemID.BronzeBoots, Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 3).setUnlocalizedName(Names[i]+" Boots")); i++;
		TFCItems.CopperBoots = 			(new ItemTFCArmor(TFCItemID.CopperBoots, Armor.CopperPlate, proxy.getArmorRenderID("copper"), 3).setUnlocalizedName(Names[i]+" Boots")); i++;
		TFCItems.WroughtIronBoots = 	(new ItemTFCArmor(TFCItemID.WroughtIronBoots, Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 3).setUnlocalizedName(Names[i]+" Boots")); i++;
		TFCItems.RedSteelBoots = 		(new ItemTFCArmor(TFCItemID.RedSteelBoots, Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 3).setUnlocalizedName(Names[i]+" Boots")); i++;
		TFCItems.SteelBoots = 			(new ItemTFCArmor(TFCItemID.SteelBoots, Armor.SteelPlate, proxy.getArmorRenderID("steel"), 3).setUnlocalizedName(Names[i]+" Boots"));

		i = 0;
		TFCItems.BismuthBronzeUnfinishedGreaves = 	(new ItemUnfinishedArmor(TFCItemID.BismuthBronzeUnfinishedGreaves).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		TFCItems.BlackBronzeUnfinishedGreaves = 	(new ItemUnfinishedArmor(TFCItemID.BlackBronzeUnfinishedGreaves).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		TFCItems.BlackSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor(TFCItemID.BlackSteelUnfinishedGreaves).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		TFCItems.BlueSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor(TFCItemID.BlueSteelUnfinishedGreaves).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		TFCItems.BronzeUnfinishedGreaves = 			(new ItemUnfinishedArmor(TFCItemID.BronzeUnfinishedGreaves).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		TFCItems.CopperUnfinishedGreaves = 			(new ItemUnfinishedArmor(TFCItemID.CopperUnfinishedGreaves).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		TFCItems.WroughtIronUnfinishedGreaves = 	(new ItemUnfinishedArmor(TFCItemID.WroughtIronUnfinishedGreaves).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		TFCItems.RedSteelUnfinishedGreaves = 		(new ItemUnfinishedArmor(TFCItemID.RedSteelUnfinishedGreaves).setUnlocalizedName(Names[i]+" Unfinished Greaves")); i++;
		TFCItems.SteelUnfinishedGreaves = 			(new ItemUnfinishedArmor(TFCItemID.SteelUnfinishedGreaves).setUnlocalizedName(Names[i]+" Unfinished Greaves"));

		i = 0;
		TFCItems.BismuthBronzeGreaves = 	(new ItemTFCArmor(TFCItemID.BismuthBronzeGreaves, Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 2).setUnlocalizedName(Names[i]+" Greaves")); i++;
		TFCItems.BlackBronzeGreaves = 		(new ItemTFCArmor(TFCItemID.BlackBronzeGreaves, Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 2).setUnlocalizedName(Names[i]+" Greaves")); i++;
		TFCItems.BlackSteelGreaves = 		(new ItemTFCArmor(TFCItemID.BlackSteelGreaves, Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 2).setUnlocalizedName(Names[i]+" Greaves")); i++;
		TFCItems.BlueSteelGreaves = 		(new ItemTFCArmor(TFCItemID.BlueSteelGreaves, Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 2).setUnlocalizedName(Names[i]+" Greaves")); i++;
		TFCItems.BronzeGreaves = 			(new ItemTFCArmor(TFCItemID.BronzeGreaves, Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 2).setUnlocalizedName(Names[i]+" Greaves")); i++;
		TFCItems.CopperGreaves = 			(new ItemTFCArmor(TFCItemID.CopperGreaves, Armor.CopperPlate, proxy.getArmorRenderID("copper"), 2).setUnlocalizedName(Names[i]+" Greaves")); i++;
		TFCItems.WroughtIronGreaves =		(new ItemTFCArmor(TFCItemID.WroughtIronGreaves, Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 2).setUnlocalizedName(Names[i]+" Greaves")); i++;
		TFCItems.RedSteelGreaves = 			(new ItemTFCArmor(TFCItemID.RedSteelGreaves, Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 2).setUnlocalizedName(Names[i]+" Greaves")); i++;
		TFCItems.SteelGreaves = 			(new ItemTFCArmor(TFCItemID.SteelGreaves, Armor.SteelPlate, proxy.getArmorRenderID("steel"), 2).setUnlocalizedName(Names[i]+" Greaves"));

		i = 0;
		TFCItems.BismuthBronzeUnfinishedChestplate = 	(new ItemUnfinishedArmor(TFCItemID.BismuthBronzeUnfinishedChestplate).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		TFCItems.BlackBronzeUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFCItemID.BlackBronzeUnfinishedChestplate).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		TFCItems.BlackSteelUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFCItemID.BlackSteelUnfinishedChestplate).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		TFCItems.BlueSteelUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFCItemID.BlueSteelUnfinishedChestplate).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		TFCItems.BronzeUnfinishedChestplate = 			(new ItemUnfinishedArmor(TFCItemID.BronzeUnfinishedChestplate).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		TFCItems.CopperUnfinishedChestplate = 			(new ItemUnfinishedArmor(TFCItemID.CopperUnfinishedChestplate).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		TFCItems.WroughtIronUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFCItemID.WroughtIronUnfinishedChestplate).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		TFCItems.RedSteelUnfinishedChestplate = 		(new ItemUnfinishedArmor(TFCItemID.RedSteelUnfinishedChestplate).setUnlocalizedName(Names[i]+" Unfinished Chestplate")); i++;
		TFCItems.SteelUnfinishedChestplate = 			(new ItemUnfinishedArmor(TFCItemID.SteelUnfinishedChestplate).setUnlocalizedName(Names[i]+" Unfinished Chestplate"));

		i = 0;
		TFCItems.BismuthBronzeChestplate =	(new ItemTFCArmor(TFCItemID.BismuthBronzeChestplate, Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 1).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		TFCItems.BlackBronzeChestplate = 	(new ItemTFCArmor(TFCItemID.BlackBronzeChestplate, Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 1).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		TFCItems.BlackSteelChestplate = 	(new ItemTFCArmor(TFCItemID.BlackSteelChestplate, Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 1).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		TFCItems.BlueSteelChestplate = 		(new ItemTFCArmor(TFCItemID.BlueSteelChestplate, Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 1).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		TFCItems.BronzeChestplate = 		(new ItemTFCArmor(TFCItemID.BronzeChestplate, Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 1).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		TFCItems.CopperChestplate = 		(new ItemTFCArmor(TFCItemID.CopperChestplate, Armor.CopperPlate, proxy.getArmorRenderID("copper"), 1).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		TFCItems.WroughtIronChestplate = 	(new ItemTFCArmor(TFCItemID.WroughtIronChestplate, Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 1).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		TFCItems.RedSteelChestplate = 		(new ItemTFCArmor(TFCItemID.RedSteelChestplate, Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 1).setUnlocalizedName(Names[i]+" Chestplate")); i++;
		TFCItems.SteelChestplate = 			(new ItemTFCArmor(TFCItemID.SteelChestplate, Armor.SteelPlate, proxy.getArmorRenderID("steel"), 1).setUnlocalizedName(Names[i]+" Chestplate"));

		i = 0;
		TFCItems.BismuthBronzeUnfinishedHelmet = 	(new ItemUnfinishedArmor(TFCItemID.BismuthBronzeUnfinishedHelmet).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		TFCItems.BlackBronzeUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFCItemID.BlackBronzeUnfinishedHelmet).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		TFCItems.BlackSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFCItemID.BlackSteelUnfinishedHelmet).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		TFCItems.BlueSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFCItemID.BlueSteelUnfinishedHelmet).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		TFCItems.BronzeUnfinishedHelmet = 			(new ItemUnfinishedArmor(TFCItemID.BronzeUnfinishedHelmet).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		TFCItems.CopperUnfinishedHelmet = 			(new ItemUnfinishedArmor(TFCItemID.CopperUnfinishedHelmet).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		TFCItems.WroughtIronUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFCItemID.WroughtIronUnfinishedHelmet).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		TFCItems.RedSteelUnfinishedHelmet = 		(new ItemUnfinishedArmor(TFCItemID.RedSteelUnfinishedHelmet).setUnlocalizedName(Names[i]+" Unfinished Helmet")); i++;
		TFCItems.SteelUnfinishedHelmet = 			(new ItemUnfinishedArmor(TFCItemID.SteelUnfinishedHelmet).setUnlocalizedName(Names[i]+" Unfinished Helmet"));

		i = 0;
		TFCItems.BismuthBronzeHelmet = 	(new ItemTFCArmor(TFCItemID.BismuthBronzeHelmet, Armor.BismuthBronzePlate, proxy.getArmorRenderID("bismuthbronze"), 0).setUnlocalizedName(Names[i]+" Helmet")); i++;
		TFCItems.BlackBronzeHelmet = 	(new ItemTFCArmor(TFCItemID.BlackBronzeHelmet, Armor.BlackBronzePlate, proxy.getArmorRenderID("blackbronze"), 0).setUnlocalizedName(Names[i]+" Helmet")); i++;
		TFCItems.BlackSteelHelmet = 	(new ItemTFCArmor(TFCItemID.BlackSteelHelmet, Armor.BlackSteelPlate, proxy.getArmorRenderID("blacksteel"), 0).setUnlocalizedName(Names[i]+" Helmet")); i++;
		TFCItems.BlueSteelHelmet = 		(new ItemTFCArmor(TFCItemID.BlueSteelHelmet, Armor.BlueSteelPlate, proxy.getArmorRenderID("bluesteel"), 0).setUnlocalizedName(Names[i]+" Helmet")); i++;
		TFCItems.BronzeHelmet = 		(new ItemTFCArmor(TFCItemID.BronzeHelmet, Armor.BronzePlate, proxy.getArmorRenderID("bronze"), 0).setUnlocalizedName(Names[i]+" Helmet")); i++;
		TFCItems.CopperHelmet = 		(new ItemTFCArmor(TFCItemID.CopperHelmet, Armor.CopperPlate, proxy.getArmorRenderID("copper"), 0).setUnlocalizedName(Names[i]+" Helmet")); i++;
		TFCItems.WroughtIronHelmet = 	(new ItemTFCArmor(TFCItemID.WroughtIronHelmet, Armor.WroughtIronPlate, proxy.getArmorRenderID("wroughtiron"), 0).setUnlocalizedName(Names[i]+" Helmet")); i++;
		TFCItems.RedSteelHelmet = 		(new ItemTFCArmor(TFCItemID.RedSteelHelmet, Armor.RedSteelPlate, proxy.getArmorRenderID("redsteel"), 0).setUnlocalizedName(Names[i]+" Helmet")); i++;
		TFCItems.SteelHelmet = 			(new ItemTFCArmor(TFCItemID.SteelHelmet, Armor.SteelPlate, proxy.getArmorRenderID("steel"), 0).setUnlocalizedName(Names[i]+" Helmet")); 

		i = 0;
		TFCItems.BrassSheet = 			new ItemMetalSheet(TFCItemID.BrassSheet).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.GoldSheet = 			new ItemMetalSheet(TFCItemID.GoldSheet).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.LeadSheet = 			new ItemMetalSheet(TFCItemID.LeadSheet).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.NickelSheet = 			new ItemMetalSheet(TFCItemID.NickelSheet).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.PigIronSheet = 		new ItemMetalSheet(TFCItemID.PigIronSheet).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.PlatinumSheet = 		new ItemMetalSheet(TFCItemID.PlatinumSheet).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.SilverSheet = 			new ItemMetalSheet(TFCItemID.SilverSheet).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		TFCItems.SterlingSilverSheet = 	new ItemMetalSheet(TFCItemID.SterlingSilverSheet).setUnlocalizedName(NamesNSO[i++]+" Sheet");

		i = 0;
		TFCItems.BrassSheet2x = 			new ItemMetalSheet2x(TFCItemID.BrassSheet2x).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.GoldSheet2x = 				new ItemMetalSheet2x(TFCItemID.GoldSheet2x).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.LeadSheet2x = 				new ItemMetalSheet2x(TFCItemID.LeadSheet2x).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.NickelSheet2x = 			new ItemMetalSheet2x(TFCItemID.NickelSheet2x).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.PigIronSheet2x = 			new ItemMetalSheet2x(TFCItemID.PigIronSheet2x).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.PlatinumSheet2x = 			new ItemMetalSheet2x(TFCItemID.PlatinumSheet2x).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.SilverSheet2x = 			new ItemMetalSheet2x(TFCItemID.SilverSheet2x).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		TFCItems.SterlingSilverSheet2x = 	new ItemMetalSheet2x(TFCItemID.SterlingSilverSheet2x).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");

		Item.itemsList[Item.helmetLeather.itemID] = null; Item.itemsList[Item.helmetLeather.itemID] = new ItemTFCArmor(Item.helmetLeather.itemID, Armor.Leather, proxy.getArmorRenderID("leather"), 0, EnumArmorMaterial.CLOTH).setUnlocalizedName("helmetCloth").setTextureName("leather_helmet");
		Item.itemsList[Item.plateLeather.itemID] = null; Item.itemsList[Item.plateLeather.itemID] = new ItemTFCArmor(Item.plateLeather.itemID, Armor.Leather, proxy.getArmorRenderID("leather"), 1, EnumArmorMaterial.CLOTH).setUnlocalizedName("chestplateCloth").setTextureName("leather_chestplate");
		Item.itemsList[Item.legsLeather.itemID] = null; Item.itemsList[Item.legsLeather.itemID] = new ItemTFCArmor(Item.legsLeather.itemID, Armor.Leather, proxy.getArmorRenderID("leather"), 2, EnumArmorMaterial.CLOTH).setUnlocalizedName("leggingsCloth").setTextureName("leather_leggings");
		Item.itemsList[Item.bootsLeather.itemID] = null; Item.itemsList[Item.bootsLeather.itemID] = new ItemTFCArmor(Item.bootsLeather.itemID, Armor.Leather, proxy.getArmorRenderID("leather"), 3, EnumArmorMaterial.CLOTH).setUnlocalizedName("bootsCloth").setTextureName("leather_boots");
	}

	public static Item[] Meals;
}
