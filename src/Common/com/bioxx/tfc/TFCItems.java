package com.bioxx.tfc;

import java.util.ArrayList;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.Item.ToolMaterial;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.common.util.EnumHelper;

import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.Metal.Alloy;
import com.bioxx.tfc.Core.Metal.AlloyManager;
import com.bioxx.tfc.Core.Metal.MetalRegistry;
import com.bioxx.tfc.Food.ItemEgg;
import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Food.ItemSalad;
import com.bioxx.tfc.Food.ItemSandwich;
import com.bioxx.tfc.Food.ItemSoup;
import com.bioxx.tfc.Items.ItemAlcohol;
import com.bioxx.tfc.Items.ItemArrow;
import com.bioxx.tfc.Items.ItemBloom;
import com.bioxx.tfc.Items.ItemBlueprint;
import com.bioxx.tfc.Items.ItemClay;
import com.bioxx.tfc.Items.ItemCoal;
import com.bioxx.tfc.Items.ItemCustomLeash;
import com.bioxx.tfc.Items.ItemCustomMinecart;
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
import com.bioxx.tfc.Items.ItemBlocks.ItemWoodDoor;
import com.bioxx.tfc.Items.Pottery.ItemPotteryBase;
import com.bioxx.tfc.Items.Pottery.ItemPotteryJug;
import com.bioxx.tfc.Items.Pottery.ItemPotteryMold;
import com.bioxx.tfc.Items.Pottery.ItemPotteryPot;
import com.bioxx.tfc.Items.Pottery.ItemPotterySmallVessel;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemCustomAxe;
import com.bioxx.tfc.Items.Tools.ItemCustomBlueSteelBucket;
import com.bioxx.tfc.Items.Tools.ItemCustomBow;
import com.bioxx.tfc.Items.Tools.ItemCustomBucket;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.Items.Tools.ItemCustomFishingRod;
import com.bioxx.tfc.Items.Tools.ItemCustomHoe;
import com.bioxx.tfc.Items.Tools.ItemCustomKnife;
import com.bioxx.tfc.Items.Tools.ItemCustomPickaxe;
import com.bioxx.tfc.Items.Tools.ItemCustomRedSteelBucket;
import com.bioxx.tfc.Items.Tools.ItemCustomSaw;
import com.bioxx.tfc.Items.Tools.ItemCustomScythe;
import com.bioxx.tfc.Items.Tools.ItemCustomShovel;
import com.bioxx.tfc.Items.Tools.ItemCustomSword;
import com.bioxx.tfc.Items.Tools.ItemFirestarter;
import com.bioxx.tfc.Items.Tools.ItemFlintSteel;
import com.bioxx.tfc.Items.Tools.ItemGoldPan;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.Items.Tools.ItemJavelin;
import com.bioxx.tfc.Items.Tools.ItemMiscToolHead;
import com.bioxx.tfc.Items.Tools.ItemProPick;
import com.bioxx.tfc.Items.Tools.ItemSpindle;
import com.bioxx.tfc.Items.Tools.ItemWritableBookTFC;
import com.bioxx.tfc.api.Armor;
import com.bioxx.tfc.api.Metal;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumDamageType;
import com.bioxx.tfc.api.Enums.EnumFoodGroup;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import cpw.mods.fml.common.registry.GameRegistry;

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

	public static Item Coal;
	public static Item OreChunk;
	public static Item SmallOreChunk;
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
	public static Item FishingRod;
	public static Item Bow;

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
	public static Item Dye;

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

	public static Item LeatherChestplate;
	public static Item BismuthBronzeChestplate;
	public static Item BlackBronzeChestplate;
	public static Item BlackSteelChestplate;
	public static Item BlueSteelChestplate;
	public static Item BronzeChestplate;
	public static Item CopperChestplate;
	public static Item WroughtIronChestplate;
	public static Item RedSteelChestplate;
	public static Item SteelChestplate;

	public static Item LeatherLeggings;
	public static Item BismuthBronzeGreaves;
	public static Item BlackBronzeGreaves;
	public static Item BlackSteelGreaves;
	public static Item BlueSteelGreaves;
	public static Item BronzeGreaves;
	public static Item CopperGreaves;
	public static Item WroughtIronGreaves;
	public static Item RedSteelGreaves;
	public static Item SteelGreaves;

	public static Item LeatherBoots;
	public static Item BismuthBronzeBoots;
	public static Item BlackBronzeBoots;
	public static Item BlackSteelBoots;
	public static Item BlueSteelBoots;
	public static Item BronzeBoots;
	public static Item CopperBoots;
	public static Item WroughtIronBoots;
	public static Item RedSteelBoots;
	public static Item SteelBoots;

	public static Item LeatherHelmet;
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
	public static Item WoodenBucketSaltWater;
	public static Item WoodenBucketMilk;

	/**Food Related Items and Blocks*/
	public static Item SeedsWheat;
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
	public static Item SeedsJute;

	public static Item FruitTreeSapling;

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

	public static Item muttonRaw;
	public static Item muttonCooked;
	public static Item calamariRaw;
	public static Item calamariCooked;
	public static Item venisonRaw;
	public static Item venisonCooked;
	public static Item horseMeatRaw;
	public static Item horseMeatCooked;
	public static Item porkchopRaw;
	public static Item porkchopCooked;
	public static Item fishRaw;
	public static Item fishCooked;
	public static Item beefRaw;
	public static Item beefCooked;
	public static Item chickenRaw;
	public static Item chickenCooked;

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
	public static Item SinglePlank;

	public static Item minecartEmpty;
	public static Item minecartCrate;

	public static Item RedSteelBucketEmpty;
	public static Item RedSteelBucketWater;
	public static Item RedSteelBucketSaltWater;

	public static Item BlueSteelBucketEmpty;
	public static Item BlueSteelBucketLava;

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
	public static Item Vinegar;
	public static Item Hide;
	public static Item SoakedHide;
	public static Item ScrapedHide;
	public static Item PrepHide;
	public static Item SheepSkin;
	public static Item Leather;
	public static Item FlatLeather;

	public static Item Beer;
	public static Item Cider;
	public static Item Rum;
	public static Item RyeWhiskey;
	public static Item Sake;
	public static Item Vodka;
	public static Item Whiskey;

	public static Item GlassBottle;
	public static Item Potion;

	public static Item ClayBall;
	public static Item PotteryJug;
	public static Item PotteryPot;
	public static Item PotterySmallVessel;
	public static Item KilnRack;
	public static Item Straw;
	public static Item FlatClay;
	public static Item FireBrick;
	public static Item Stick;
	public static Item Arrow;
	public static Item Leash;

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
	public static Item Reeds;

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
	public static Item MetalLock;
	public static Item MudBrick;
	public static Item Sandwich;
	public static Item Soup;
	public static Item Stew;
	public static Item Salad;
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

		FishingRod = new ItemCustomFishingRod().setUnlocalizedName("fishingRod").setTextureName("fishing_rod");
		Coal = new ItemCoal().setUnlocalizedName("coal");
		Stick = new ItemStick().setFull3D().setUnlocalizedName("stick");
		Bow = new ItemCustomBow().setUnlocalizedName("bow").setTextureName("bow");
		Arrow = new ItemArrow().setUnlocalizedName("arrow").setCreativeTab(TFCTabs.TFCWeapons);
		Dye = new ItemDyeCustom().setUnlocalizedName("dyePowder").setTextureName("dye_powder").setCreativeTab(TFCTabs.TFCMaterials);
		GlassBottle = new ItemGlassBottle().setUnlocalizedName("Glass Bottle");
		Potion = new ItemCustomPotion().setUnlocalizedName("potion").setTextureName("potion");
		Leash = new ItemCustomLeash().setUnlocalizedName("Rope").setCreativeTab(TFCTabs.TFCTools);

		minecartCrate = new ItemCustomMinecart(1).setUnlocalizedName("minecartChest").setTextureName("minecart_chest");
		GoldPan = new ItemGoldPan().setUnlocalizedName("GoldPan");
		SluiceItem = new ItemSluice().setFolder("devices/").setUnlocalizedName("SluiceItem").setCreativeTab(TFCTabs.TFCDevices);

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

		StoneKnife = new ItemCustomKnife(IgExToolMaterial, 40).setUnlocalizedName("Stone Knife").setMaxDamage(IgExStoneUses);
		SinglePlank = new ItemPlank().setUnlocalizedName("SinglePlank");

		RedSteelBucketEmpty = (new ItemCustomRedSteelBucket(Blocks.air)).setUnlocalizedName("Red Steel Bucket Empty");
		RedSteelBucketWater = (new ItemCustomRedSteelBucket(TFCBlocks.FreshWater)).setUnlocalizedName("Red Steel Bucket Water").setContainerItem(RedSteelBucketEmpty);
		RedSteelBucketSaltWater = (new ItemCustomRedSteelBucket(TFCBlocks.SaltWater)).setUnlocalizedName("Red Steel Bucket Salt Water").setContainerItem(RedSteelBucketEmpty);

		BlueSteelBucketEmpty = (new ItemCustomBlueSteelBucket(Blocks.air)).setUnlocalizedName("Blue Steel Bucket Empty");
		BlueSteelBucketLava = (new ItemCustomBlueSteelBucket(TFCBlocks.Lava)).setUnlocalizedName("Blue Steel Bucket Lava").setContainerItem(BlueSteelBucketEmpty);

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

		Blueprint = new ItemBlueprint();
		writabeBookTFC = new ItemWritableBookTFC("Fix Me I'm Broken").setUnlocalizedName("book");
		WoolYarn = new ItemTerra().setUnlocalizedName("WoolYarn").setCreativeTab(TFCTabs.TFCMaterials);
		Wool = new ItemTerra().setUnlocalizedName("Wool").setCreativeTab(TFCTabs.TFCMaterials);
		WoolCloth = new ItemTerra().setUnlocalizedName("WoolCloth").setCreativeTab(TFCTabs.TFCMaterials);
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
		Reeds = new ItemReeds().setUnlocalizedName("Reeds").setCreativeTab(CreativeTabs.tabMaterials).setTextureName("reeds");
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
		porkchopRaw = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false).setDecayRate(3.0f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("porkchopRaw");
		porkchopCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 65).setDecayRate(1.8f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("porkchopCooked");
		fishRaw = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, true).setDecayRate(3.0f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("fishRaw");
		fishCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 50).setDecayRate(2.0f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("fishCooked");
		beefRaw = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 50, false, false).setDecayRate(3.0f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("beefRaw");
		beefCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 75).setDecayRate(1.8f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("beefCooked");
		chickenRaw = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false).setDecayRate(3.0f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("chickenRaw");
		chickenCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 60).setDecayRate(1.8f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("chickenCooked");
		Soybean = new ItemFoodTFC(EnumFoodGroup.Protein, 10, 0, 0, 0, 40, true).setUnlocalizedName("Soybeans");
		EggCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 25).setDecayRate(3.0f).setUnlocalizedName("Egg Cooked");
		calamariRaw = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 20, 0, 35, false, false).setCanSmoke().setSmokeAbsorbMultiplier(1F).setDecayRate(4.0f).setUnlocalizedName("Calamari Raw");
		calamariCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 20, 0, 40).setCanSmoke().setSmokeAbsorbMultiplier(1F).setDecayRate(2.0f).setUnlocalizedName("Calamari Cooked");
		muttonRaw = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false).setDecayRate(3.0f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Mutton Raw");
		muttonCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 60).setDecayRate(1.8f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Mutton Cooked");
		venisonRaw = new ItemFoodTFC(EnumFoodGroup.Protein, 5, 0, 0, 0, 50, false, false).setDecayRate(3.0f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Venison");
		venisonCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 5, 0, 0, 0, 70).setDecayRate(1.8f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("VenisonCooked");
		horseMeatRaw = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 40, false, false).setDecayRate(3.0f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("HorseMeat");
		horseMeatCooked = new ItemFoodTFC(EnumFoodGroup.Protein, 0, 0, 0, 0, 65).setDecayRate(1.8f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("HorseMeatCooked");

		//Dairy
		Cheese = new ItemFoodTFC(EnumFoodGroup.Dairy, 0, 10, 20, 0, 35).setDecayRate(0.5f).setCanSmoke().setSmokeAbsorbMultiplier(1F).setUnlocalizedName("Cheese");

		//Grains
		WheatGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setUnlocalizedName("Wheat Grain");
		BarleyGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 10, 20).setUnlocalizedName("Barley Grain");
		OatGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setUnlocalizedName("Oat Grain");
		RyeGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 15, 0, 5, 20).setUnlocalizedName("Rye Grain");
		RiceGrain = new ItemFoodTFC(EnumFoodGroup.Grain, 10, 0, 0, 5, 20).setUnlocalizedName("Rice Grain");
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
		Cabbage = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 50, true).setUnlocalizedName("Cabbage");
		Garlic = new ItemFoodTFC(EnumFoodGroup.Vegetable, 0, 0, 0, 10, 20, true).setUnlocalizedName("Garlic");
		Carrot = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setUnlocalizedName("Carrot");
		Greenbeans = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setUnlocalizedName("Greenbeans");
		GreenBellPepper = new ItemFoodTFC(EnumFoodGroup.Vegetable, 10, 0, 0, 0, 20, true).setUnlocalizedName("Green Bell Pepper");
		YellowBellPepper = new ItemFoodTFC(EnumFoodGroup.Vegetable, 15, 0, 0, 0, 20, true).setUnlocalizedName("Yellow Bell Pepper");
		RedBellPepper = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setUnlocalizedName("Red Bell Pepper");
		Squash = new ItemFoodTFC(EnumFoodGroup.Vegetable, 20, 0, 0, 0, 20, true).setUnlocalizedName("Squash");

		//Fruit are in the foodID range of 50,000
		RedApple = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 5, 0, 0, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[0]);
		Banana = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 5, 0, 0, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[1]);
		Orange = new ItemFoodTFC(EnumFoodGroup.Fruit, 50, 30, 0, 10, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[2]);
		GreenApple = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 40, 0, 40, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[3]);
		Lemon = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 80, 0, 10, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[4]);
		Olive = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 0, 50, 20, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[5]);
		Cherry = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 5, 0, 0, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[6]);
		Peach = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 10, 0, 0, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[7]);
		Plum = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 15, 0, 0, 0, true).setDecayRate(2.0f).setUnlocalizedName(Global.FRUIT_META_NAMES[8]);

		WintergreenBerry = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 0, 0, 20, 0).setDecayRate(2.0f).setUnlocalizedName("Wintergreen Berry");
		Blueberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 20, 0, 0, 0).setDecayRate(2.0f).setUnlocalizedName("Blueberry");
		Raspberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 45, 15, 0, 5, 0).setDecayRate(2.0f).setUnlocalizedName("Raspberry");
		Strawberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 60, 5, 0, 0, 0).setDecayRate(2.0f).setUnlocalizedName("Strawberry");
		Blackberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 30, 0, 0, 0).setDecayRate(2.0f).setUnlocalizedName("Blackberry");
		Bunchberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 5, 0, 0, 0).setDecayRate(2.0f).setUnlocalizedName("Bunchberry");
		Cranberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 30, 5, 0, 75, 0).setDecayRate(2.0f).setUnlocalizedName("Cranberry");
		Snowberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 10, 0, 0, 90, 0).setDecayRate(2.0f).setUnlocalizedName("Snowberry");
		Elderberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 40, 0, 10, 0).setDecayRate(2.0f).setUnlocalizedName("Elderberry");
		Gooseberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 20, 40, 0, 0, 0).setDecayRate(2.0f).setUnlocalizedName("Gooseberry");
		Cloudberry = new ItemFoodTFC(EnumFoodGroup.Fruit, 40, 40, 0, 30, 0).setDecayRate(2.0f).setUnlocalizedName("Cloudberry");

		Sandwich = new ItemSandwich().setUnlocalizedName("Sandwich");
		Salad = new ItemSalad().setUnlocalizedName("Salad");
		Soup = new ItemSoup().setUnlocalizedName("Soup");

		Sugarcane = new ItemTerra().setFolder("plants/").setUnlocalizedName("Sugarcane");
		Hemp = new ItemTerra().setFolder("plants/").setUnlocalizedName("Hemp");

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
		SeedsJute = new ItemCustomSeeds(24).setUnlocalizedName("Seeds Jute");

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
		WroughtIronSheet = 		(((ItemMetalSheet) new ItemMetalSheet(19).setUnlocalizedName("Wrought Iron Sheet")).setMetal("Wrought Iron", 200));
		RedSteelSheet = 		(((ItemMetalSheet) new ItemMetalSheet(13).setUnlocalizedName("Red Steel Sheet")).setMetal("Red Steel", 200));
		RoseGoldSheet = 		(((ItemMetalSheet) new ItemMetalSheet(14).setUnlocalizedName("Rose Gold Sheet")).setMetal("Rose Gold", 200));
		SteelSheet = 			(((ItemMetalSheet) new ItemMetalSheet(16).setUnlocalizedName("Steel Sheet")).setMetal("Steel", 200));
		TinSheet = 				(((ItemMetalSheet) new ItemMetalSheet(18).setUnlocalizedName("Tin Sheet")).setMetal("Tin", 200));
		ZincSheet = 			(((ItemMetalSheet) new ItemMetalSheet(20).setUnlocalizedName("Zinc Sheet")).setMetal("Zinc", 200));

		BismuthSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(0).setUnlocalizedName("Bismuth Double Sheet")).setMetal("Bismuth", 400));
		BismuthBronzeSheet2x = 	(((ItemMetalSheet2x) new ItemMetalSheet2x(1).setUnlocalizedName("Bismuth Bronze Double Sheet")).setMetal("Bismuth Bronze", 400));
		BlackBronzeSheet2x = 	(((ItemMetalSheet2x) new ItemMetalSheet2x(2).setUnlocalizedName("Black Bronze Double Sheet")).setMetal("Black Bronze", 400));
		BlackSteelSheet2x = 	(((ItemMetalSheet2x) new ItemMetalSheet2x(3).setUnlocalizedName("Black Steel Double Sheet")).setMetal("Black Steel", 400));
		BlueSteelSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(4).setUnlocalizedName("Blue Steel Double Sheet")).setMetal("Blue Steel", 400));
		BronzeSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(6).setUnlocalizedName("Bronze Double Sheet")).setMetal("Bronze", 400));
		CopperSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(7).setUnlocalizedName("Copper Double Sheet")).setMetal("Copper", 400));
		WroughtIronSheet2x = 	(((ItemMetalSheet2x) new ItemMetalSheet2x(19).setUnlocalizedName("Wrought Iron Double Sheet")).setMetal("Wrought Iron", 400));
		RedSteelSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(13).setUnlocalizedName("Red Steel Double Sheet")).setMetal("Red Steel", 400));
		RoseGoldSheet2x = 		(((ItemMetalSheet2x) new ItemMetalSheet2x(14).setUnlocalizedName("Rose Gold Double Sheet")).setMetal("Rose Gold", 400));
		SteelSheet2x = 			(((ItemMetalSheet2x) new ItemMetalSheet2x(16).setUnlocalizedName("Steel Double Sheet")).setMetal("Steel", 400));
		TinSheet2x = 			(((ItemMetalSheet2x) new ItemMetalSheet2x(18).setUnlocalizedName("Tin Double Sheet")).setMetal("Tin", 400));
		ZincSheet2x = 			(((ItemMetalSheet2x) new ItemMetalSheet2x(20).setUnlocalizedName("Zinc Double Sheet")).setMetal("Zinc", 400));

		i = 0;
		BrassSheet = 			new ItemMetalSheet(5).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		GoldSheet = 			new ItemMetalSheet(8).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		LeadSheet = 			new ItemMetalSheet(9).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		NickelSheet = 			new ItemMetalSheet(10).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		PigIronSheet = 			new ItemMetalSheet(11).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		PlatinumSheet = 		new ItemMetalSheet(12).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		SilverSheet = 			new ItemMetalSheet(15).setUnlocalizedName(NamesNSO[i++]+" Sheet");
		SterlingSilverSheet = 	new ItemMetalSheet(17).setUnlocalizedName(NamesNSO[i++]+" Sheet");

		i = 0;
		BrassSheet2x = 			new ItemMetalSheet2x(5).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		GoldSheet2x = 			new ItemMetalSheet2x(8).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		LeadSheet2x = 			new ItemMetalSheet2x(9).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		NickelSheet2x = 		new ItemMetalSheet2x(10).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		PigIronSheet2x = 		new ItemMetalSheet2x(11).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		PlatinumSheet2x = 		new ItemMetalSheet2x(12).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		SilverSheet2x = 		new ItemMetalSheet2x(15).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");
		SterlingSilverSheet2x = new ItemMetalSheet2x(17).setUnlocalizedName(NamesNSO[i++]+" Double Sheet");

		i = 0;
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

		LeatherHelmet = new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 0, ArmorMaterial.CLOTH, 100,3).setUnlocalizedName("helmetCloth").setTextureName("leather_helmet");
		LeatherChestplate = new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 1, ArmorMaterial.CLOTH, 100,2).setUnlocalizedName("chestplateCloth").setTextureName("leather_chestplate");
		LeatherLeggings = new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 2, ArmorMaterial.CLOTH, 100,1).setUnlocalizedName("leggingsCloth").setTextureName("leather_leggings");
		LeatherBoots = new ItemTFCArmor(Armor.Leather, proxy.getArmorRenderID("leather"), 3, ArmorMaterial.CLOTH, 100,0).setUnlocalizedName("bootsCloth").setTextureName("leather_boots");

		Quiver = new ItemQuiver(Armor.LeatherQuiver, proxy.getArmorRenderID("leather"), 4, ArmorMaterial.IRON, 0,-1).setUnlocalizedName("Quiver");
	}

	public static Item[] Meals;

	public static void registerItems()
	{
		System.out.println(new StringBuilder().append("[TFC] Registering Items").toString());

		GameRegistry.registerItem(GoldPan, GoldPan.getUnlocalizedName());
		GameRegistry.registerItem(SluiceItem, SluiceItem.getUnlocalizedName());

		GameRegistry.registerItem(ProPickBismuthBronze, ProPickBismuthBronze.getUnlocalizedName());
		GameRegistry.registerItem(ProPickBlackBronze, ProPickBlackBronze.getUnlocalizedName());
		GameRegistry.registerItem(ProPickBlackSteel, ProPickBlackSteel.getUnlocalizedName());
		GameRegistry.registerItem(ProPickBlueSteel, ProPickBlueSteel.getUnlocalizedName());
		GameRegistry.registerItem(ProPickBronze, ProPickBronze.getUnlocalizedName());
		GameRegistry.registerItem(ProPickCopper, ProPickCopper.getUnlocalizedName());
		GameRegistry.registerItem(ProPickIron, ProPickIron.getUnlocalizedName());
		GameRegistry.registerItem(ProPickRedSteel, ProPickRedSteel.getUnlocalizedName());
		GameRegistry.registerItem(ProPickSteel, ProPickSteel.getUnlocalizedName());

		GameRegistry.registerItem(BismuthIngot, BismuthIngot.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeIngot, BismuthBronzeIngot.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeIngot, BlackBronzeIngot.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelIngot, BlackSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelIngot, BlueSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(BrassIngot, BrassIngot.getUnlocalizedName());
		GameRegistry.registerItem(BronzeIngot, BronzeIngot.getUnlocalizedName());
		GameRegistry.registerItem(CopperIngot, CopperIngot.getUnlocalizedName());
		GameRegistry.registerItem(GoldIngot, GoldIngot.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronIngot, WroughtIronIngot.getUnlocalizedName());
		GameRegistry.registerItem(LeadIngot, LeadIngot.getUnlocalizedName());
		GameRegistry.registerItem(NickelIngot, NickelIngot.getUnlocalizedName());
		GameRegistry.registerItem(PigIronIngot, PigIronIngot.getUnlocalizedName());
		GameRegistry.registerItem(PlatinumIngot, PlatinumIngot.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelIngot, RedSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(RoseGoldIngot, RoseGoldIngot.getUnlocalizedName());
		GameRegistry.registerItem(SilverIngot, SilverIngot.getUnlocalizedName());
		GameRegistry.registerItem(SteelIngot, SteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(SterlingSilverIngot, SterlingSilverIngot.getUnlocalizedName());
		GameRegistry.registerItem(TinIngot, TinIngot.getUnlocalizedName());
		GameRegistry.registerItem(ZincIngot, ZincIngot.getUnlocalizedName());

		GameRegistry.registerItem(BismuthIngot2x, BismuthIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeIngot2x, BismuthBronzeIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeIngot2x, BlackBronzeIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelIngot2x, BlackSteelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelIngot2x, BlueSteelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(BrassIngot2x, BrassIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(BronzeIngot2x, BronzeIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(CopperIngot2x, CopperIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(GoldIngot2x, GoldIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronIngot2x, WroughtIronIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(LeadIngot2x, LeadIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(NickelIngot2x, NickelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(PigIronIngot2x, PigIronIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(PlatinumIngot2x, PlatinumIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelIngot2x, RedSteelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(RoseGoldIngot2x, RoseGoldIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(SilverIngot2x, SilverIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(SteelIngot2x, SteelIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(SterlingSilverIngot2x, SterlingSilverIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(TinIngot2x, TinIngot2x.getUnlocalizedName());
		GameRegistry.registerItem(ZincIngot2x, ZincIngot2x.getUnlocalizedName());

		GameRegistry.registerItem(GemRuby, GemRuby.getUnlocalizedName());
		GameRegistry.registerItem(GemSapphire, GemSapphire.getUnlocalizedName());
		GameRegistry.registerItem(GemEmerald, GemEmerald.getUnlocalizedName());
		GameRegistry.registerItem(GemTopaz, GemTopaz.getUnlocalizedName());
		GameRegistry.registerItem(GemTourmaline, GemTourmaline.getUnlocalizedName());
		GameRegistry.registerItem(GemJade, GemJade.getUnlocalizedName());
		GameRegistry.registerItem(GemBeryl, GemBeryl.getUnlocalizedName());
		GameRegistry.registerItem(GemAgate, GemAgate.getUnlocalizedName());
		GameRegistry.registerItem(GemOpal, GemOpal.getUnlocalizedName());
		GameRegistry.registerItem(GemGarnet, GemGarnet.getUnlocalizedName());
		GameRegistry.registerItem(GemJasper, GemJasper.getUnlocalizedName());
		GameRegistry.registerItem(GemAmethyst, GemAmethyst.getUnlocalizedName());
		GameRegistry.registerItem(GemDiamond, GemDiamond.getUnlocalizedName());

		GameRegistry.registerItem(IgInShovel, IgInShovel.getUnlocalizedName());
		GameRegistry.registerItem(IgInAxe, IgInAxe.getUnlocalizedName());
		GameRegistry.registerItem(IgInHoe, IgInHoe.getUnlocalizedName());
		GameRegistry.registerItem(SedShovel, SedShovel.getUnlocalizedName());
		GameRegistry.registerItem(SedAxe, SedAxe.getUnlocalizedName());
		GameRegistry.registerItem(SedHoe, SedHoe.getUnlocalizedName());
		GameRegistry.registerItem(IgExShovel, IgExShovel.getUnlocalizedName());
		GameRegistry.registerItem(IgExAxe, IgExAxe.getUnlocalizedName());
		GameRegistry.registerItem(IgExHoe, IgExHoe.getUnlocalizedName());
		GameRegistry.registerItem(MMShovel, MMShovel.getUnlocalizedName());
		GameRegistry.registerItem(MMAxe, MMAxe.getUnlocalizedName());
		GameRegistry.registerItem(MMHoe, MMHoe.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzePick, BismuthBronzePick.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeShovel, BismuthBronzeShovel.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeAxe, BismuthBronzeAxe.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeHoe, BismuthBronzeHoe.getUnlocalizedName());

		GameRegistry.registerItem(BlackBronzePick, BlackBronzePick.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeShovel, BlackBronzeShovel.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeAxe, BlackBronzeAxe.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeHoe, BlackBronzeHoe.getUnlocalizedName());

		GameRegistry.registerItem(BlackSteelPick, BlackSteelPick.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelShovel, BlackSteelShovel.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelAxe, BlackSteelAxe.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelHoe, BlackSteelHoe.getUnlocalizedName());

		GameRegistry.registerItem(BlueSteelPick, BlueSteelPick.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelShovel, BlueSteelShovel.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelAxe, BlueSteelAxe.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelHoe, BlueSteelHoe.getUnlocalizedName());

		GameRegistry.registerItem(BronzePick, BronzePick.getUnlocalizedName());
		GameRegistry.registerItem(BronzeShovel, BronzeShovel.getUnlocalizedName());
		GameRegistry.registerItem(BronzeAxe, BronzeAxe.getUnlocalizedName());
		GameRegistry.registerItem(BronzeHoe, BronzeHoe.getUnlocalizedName());

		GameRegistry.registerItem(CopperPick, CopperPick.getUnlocalizedName());
		GameRegistry.registerItem(CopperShovel, CopperShovel.getUnlocalizedName());
		GameRegistry.registerItem(CopperAxe, CopperAxe.getUnlocalizedName());
		GameRegistry.registerItem(CopperHoe, CopperHoe.getUnlocalizedName());

		GameRegistry.registerItem(WroughtIronPick, WroughtIronPick.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronShovel, WroughtIronShovel.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronAxe, WroughtIronAxe.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronHoe, WroughtIronHoe.getUnlocalizedName());

		GameRegistry.registerItem(RedSteelPick, RedSteelPick.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelShovel, RedSteelShovel.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelAxe, RedSteelAxe.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelHoe, RedSteelHoe.getUnlocalizedName());

		GameRegistry.registerItem(SteelPick, SteelPick.getUnlocalizedName());
		GameRegistry.registerItem(SteelShovel, SteelShovel.getUnlocalizedName());
		GameRegistry.registerItem(SteelAxe, SteelAxe.getUnlocalizedName());
		GameRegistry.registerItem(SteelHoe, SteelHoe.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeChisel, BismuthBronzeChisel.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeChisel, BlackBronzeChisel.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelChisel, BlackSteelChisel.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelChisel, BlueSteelChisel.getUnlocalizedName());
		GameRegistry.registerItem(BronzeChisel, BronzeChisel.getUnlocalizedName());
		GameRegistry.registerItem(CopperChisel, CopperChisel.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronChisel, WroughtIronChisel.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelChisel, RedSteelChisel.getUnlocalizedName());
		GameRegistry.registerItem(SteelChisel, SteelChisel.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeSword, BismuthBronzeSword.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeSword, BlackBronzeSword.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelSword, BlackSteelSword.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelSword, BlueSteelSword.getUnlocalizedName());
		GameRegistry.registerItem(BronzeSword, BronzeSword.getUnlocalizedName());
		GameRegistry.registerItem(CopperSword, CopperSword.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronSword, WroughtIronSword.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelSword, RedSteelSword.getUnlocalizedName());
		GameRegistry.registerItem(SteelSword, SteelSword.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeMace, BismuthBronzeMace.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeMace, BlackBronzeMace.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelMace, BlackSteelMace.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelMace, BlueSteelMace.getUnlocalizedName());
		GameRegistry.registerItem(BronzeMace, BronzeMace.getUnlocalizedName());
		GameRegistry.registerItem(CopperMace, CopperMace.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronMace, WroughtIronMace.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelMace, RedSteelMace.getUnlocalizedName());
		GameRegistry.registerItem(SteelMace, SteelMace.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeSaw, BismuthBronzeSaw.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeSaw, BlackBronzeSaw.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelSaw, BlackSteelSaw.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelSaw, BlueSteelSaw.getUnlocalizedName());
		GameRegistry.registerItem(BronzeSaw, BronzeSaw.getUnlocalizedName());
		GameRegistry.registerItem(CopperSaw, CopperSaw.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronSaw, WroughtIronSaw.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelSaw, RedSteelSaw.getUnlocalizedName());
		GameRegistry.registerItem(SteelSaw, SteelSaw.getUnlocalizedName());

		GameRegistry.registerItem(HCBlackSteelIngot, HCBlackSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(WeakBlueSteelIngot, WeakBlueSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(WeakRedSteelIngot, WeakRedSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(WeakSteelIngot, WeakSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(HCBlueSteelIngot, HCBlueSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(HCRedSteelIngot, HCRedSteelIngot.getUnlocalizedName());
		GameRegistry.registerItem(HCSteelIngot, HCSteelIngot.getUnlocalizedName());

		GameRegistry.registerItem(OreChunk, OreChunk.getUnlocalizedName());
		GameRegistry.registerItem(SmallOreChunk, SmallOreChunk.getUnlocalizedName());
		GameRegistry.registerItem(Logs, Logs.getUnlocalizedName());

		GameRegistry.registerItem(IgInStoneJavelin, IgInStoneJavelin.getUnlocalizedName());
		GameRegistry.registerItem(SedStoneJavelin, SedStoneJavelin.getUnlocalizedName());
		GameRegistry.registerItem(IgExStoneJavelin, IgExStoneJavelin.getUnlocalizedName());
		GameRegistry.registerItem(MMStoneJavelin, MMStoneJavelin.getUnlocalizedName());
		GameRegistry.registerItem(CopperJavelin, CopperJavelin.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeJavelin, BismuthBronzeJavelin.getUnlocalizedName());
		GameRegistry.registerItem(BronzeJavelin, BronzeJavelin.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeJavelin, BlackBronzeJavelin.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronJavelin, WroughtIronJavelin.getUnlocalizedName());
		GameRegistry.registerItem(SteelJavelin, SteelJavelin.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelJavelin, BlackSteelJavelin.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelJavelin, BlueSteelJavelin.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelJavelin, RedSteelJavelin.getUnlocalizedName());

		GameRegistry.registerItem(IgInStoneJavelinHead, IgInStoneJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(SedStoneJavelinHead, SedStoneJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(IgExStoneJavelinHead, IgExStoneJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(MMStoneJavelinHead, MMStoneJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperJavelinHead, CopperJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeJavelinHead, BismuthBronzeJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeJavelinHead, BronzeJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeJavelinHead, BlackBronzeJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronJavelinHead, WroughtIronJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelJavelinHead, SteelJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelJavelinHead, BlackSteelJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelJavelinHead, BlueSteelJavelinHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelJavelinHead, RedSteelJavelinHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthUnshaped, BismuthUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeUnshaped, BismuthBronzeUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeUnshaped, BlackBronzeUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelUnshaped, BlackSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelUnshaped, BlueSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(BrassUnshaped, BrassUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(BronzeUnshaped, BronzeUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(CopperUnshaped, CopperUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(GoldUnshaped, GoldUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronUnshaped, WroughtIronUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(LeadUnshaped, LeadUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(NickelUnshaped, NickelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(PigIronUnshaped, PigIronUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(PlatinumUnshaped, PlatinumUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelUnshaped, RedSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(RoseGoldUnshaped, RoseGoldUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(SilverUnshaped, SilverUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(SteelUnshaped, SteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(SterlingSilverUnshaped, SterlingSilverUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(TinUnshaped, TinUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(ZincUnshaped, ZincUnshaped.getUnlocalizedName());

		GameRegistry.registerItem(StoneHammer, StoneHammer.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeHammer, BismuthBronzeHammer.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeHammer, BlackBronzeHammer.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelHammer, BlackSteelHammer.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelHammer, BlueSteelHammer.getUnlocalizedName());
		GameRegistry.registerItem(BronzeHammer, BronzeHammer.getUnlocalizedName());
		GameRegistry.registerItem(CopperHammer, CopperHammer.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronHammer, WroughtIronHammer.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelHammer, RedSteelHammer.getUnlocalizedName());
		GameRegistry.registerItem(SteelHammer, SteelHammer.getUnlocalizedName());

		GameRegistry.registerItem(Ink, Ink.getUnlocalizedName());
		GameRegistry.registerItem(FireStarter, FireStarter.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzePickaxeHead, BismuthBronzePickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzePickaxeHead, BlackBronzePickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelPickaxeHead, BlackSteelPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelPickaxeHead, BlueSteelPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzePickaxeHead, BronzePickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperPickaxeHead, CopperPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronPickaxeHead, WroughtIronPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelPickaxeHead, RedSteelPickaxeHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelPickaxeHead, SteelPickaxeHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeShovelHead, BismuthBronzeShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeShovelHead, BlackBronzeShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelShovelHead, BlackSteelShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelShovelHead, BlueSteelShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeShovelHead, BronzeShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperShovelHead, CopperShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronShovelHead, WroughtIronShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelShovelHead, RedSteelShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelShovelHead, SteelShovelHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeHoeHead, BismuthBronzeHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeHoeHead, BlackBronzeHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelHoeHead, BlackSteelHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelHoeHead, BlueSteelHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeHoeHead, BronzeHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperHoeHead, CopperHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronHoeHead, WroughtIronHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelHoeHead, RedSteelHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelHoeHead, SteelHoeHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeAxeHead, BismuthBronzeAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeAxeHead, BlackBronzeAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelAxeHead, BlackSteelAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelAxeHead, BlueSteelAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeAxeHead, BronzeAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperAxeHead, CopperAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronAxeHead, WroughtIronAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelAxeHead, RedSteelAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelAxeHead, SteelAxeHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeHammerHead, BismuthBronzeHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeHammerHead, BlackBronzeHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelHammerHead, BlackSteelHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelHammerHead, BlueSteelHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeHammerHead, BronzeHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperHammerHead, CopperHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronHammerHead, WroughtIronHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelHammerHead, RedSteelHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelHammerHead, SteelHammerHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeChiselHead, BismuthBronzeChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeChiselHead, BlackBronzeChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelChiselHead, BlackSteelChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelChiselHead, BlueSteelChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeChiselHead, BronzeChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperChiselHead, CopperChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronChiselHead, WroughtIronChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelChiselHead, RedSteelChiselHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelChiselHead, SteelChiselHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeSwordHead, BismuthBronzeSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeSwordHead, BlackBronzeSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelSwordHead, BlackSteelSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelSwordHead, BlueSteelSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeSwordHead, BronzeSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperSwordHead, CopperSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronSwordHead, WroughtIronSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelSwordHead, RedSteelSwordHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelSwordHead, SteelSwordHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeMaceHead, BismuthBronzeMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeMaceHead, BlackBronzeMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelMaceHead, BlackSteelMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelMaceHead, BlueSteelMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeMaceHead, BronzeMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperMaceHead, CopperMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronMaceHead, WroughtIronMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelMaceHead, RedSteelMaceHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelMaceHead, SteelMaceHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeSawHead, BismuthBronzeSawHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeSawHead, BlackBronzeSawHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelSawHead, BlackSteelSawHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelSawHead, BlueSteelSawHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeSawHead, BronzeSawHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperSawHead, CopperSawHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronSawHead, WroughtIronSawHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelSawHead, RedSteelSawHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelSawHead, SteelSawHead.getUnlocalizedName());

		GameRegistry.registerItem(HCBlackSteelUnshaped, HCBlackSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(WeakBlueSteelUnshaped, WeakBlueSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(HCBlueSteelUnshaped, HCBlueSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(WeakRedSteelUnshaped, WeakRedSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(HCRedSteelUnshaped, HCRedSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(WeakSteelUnshaped, WeakSteelUnshaped.getUnlocalizedName());
		GameRegistry.registerItem(HCSteelUnshaped, HCSteelUnshaped.getUnlocalizedName());

		GameRegistry.registerItem(Coke, Coke.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeProPickHead, BismuthBronzeProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeProPickHead, BlackBronzeProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelProPickHead, BlackSteelProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelProPickHead, BlueSteelProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeProPickHead, BronzeProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperProPickHead, CopperProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronProPickHead, WroughtIronProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelProPickHead, RedSteelProPickHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelProPickHead, SteelProPickHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeScythe, BismuthBronzeScythe.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeScythe, BlackBronzeScythe.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelScythe, BlackSteelScythe.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelScythe, BlueSteelScythe.getUnlocalizedName());
		GameRegistry.registerItem(BronzeScythe, BronzeScythe.getUnlocalizedName());
		GameRegistry.registerItem(CopperScythe, CopperScythe.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronScythe, WroughtIronScythe.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelScythe, RedSteelScythe.getUnlocalizedName());
		GameRegistry.registerItem(SteelScythe, SteelScythe.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeScytheHead, BismuthBronzeScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeScytheHead, BlackBronzeScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelScytheHead, BlackSteelScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelScytheHead, BlueSteelScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeScytheHead, BronzeScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperScytheHead, CopperScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronScytheHead, WroughtIronScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelScytheHead, RedSteelScytheHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelScytheHead, SteelScytheHead.getUnlocalizedName());

		GameRegistry.registerItem(WoodenBucketEmpty, WoodenBucketEmpty.getUnlocalizedName());
		GameRegistry.registerItem(WoodenBucketWater, WoodenBucketWater.getUnlocalizedName());
		GameRegistry.registerItem(WoodenBucketSaltWater, WoodenBucketSaltWater.getUnlocalizedName());
		GameRegistry.registerItem(WoodenBucketMilk, WoodenBucketMilk.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeKnifeHead, BismuthBronzeKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeKnifeHead, BlackBronzeKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelKnifeHead, BlackSteelKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelKnifeHead, BlueSteelKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(BronzeKnifeHead, BronzeKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(CopperKnifeHead, CopperKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronKnifeHead, WroughtIronKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelKnifeHead, RedSteelKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(SteelKnifeHead, SteelKnifeHead.getUnlocalizedName());

		GameRegistry.registerItem(BismuthBronzeKnife, BismuthBronzeKnife.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeKnife, BlackBronzeKnife.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelKnife, BlackSteelKnife.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelKnife, BlueSteelKnife.getUnlocalizedName());
		GameRegistry.registerItem(BronzeKnife, BronzeKnife.getUnlocalizedName());
		GameRegistry.registerItem(CopperKnife, CopperKnife.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronKnife, WroughtIronKnife.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelKnife, RedSteelKnife.getUnlocalizedName());
		GameRegistry.registerItem(SteelKnife, SteelKnife.getUnlocalizedName());

		GameRegistry.registerItem(FlatRock, FlatRock.getUnlocalizedName());
		GameRegistry.registerItem(LooseRock, LooseRock.getUnlocalizedName());

		GameRegistry.registerItem(IgInStoneShovelHead, IgInStoneShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(SedStoneShovelHead, SedStoneShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(IgExStoneShovelHead, IgExStoneShovelHead.getUnlocalizedName());
		GameRegistry.registerItem(MMStoneShovelHead, MMStoneShovelHead.getUnlocalizedName());

		GameRegistry.registerItem(IgInStoneAxeHead, IgInStoneAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(SedStoneAxeHead, SedStoneAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(IgExStoneAxeHead, IgExStoneAxeHead.getUnlocalizedName());
		GameRegistry.registerItem(MMStoneAxeHead, MMStoneAxeHead.getUnlocalizedName());

		GameRegistry.registerItem(IgInStoneHoeHead, IgInStoneHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(SedStoneHoeHead, SedStoneHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(IgExStoneHoeHead, IgExStoneHoeHead.getUnlocalizedName());
		GameRegistry.registerItem(MMStoneHoeHead, MMStoneHoeHead.getUnlocalizedName());

		GameRegistry.registerItem(StoneKnifeHead, StoneKnifeHead.getUnlocalizedName());
		GameRegistry.registerItem(StoneHammerHead, StoneHammerHead.getUnlocalizedName());
		GameRegistry.registerItem(StoneKnife, StoneKnife.getUnlocalizedName());

		GameRegistry.registerItem(SinglePlank, SinglePlank.getUnlocalizedName());

		GameRegistry.registerItem(RedSteelBucketEmpty, RedSteelBucketEmpty.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelBucketWater, RedSteelBucketWater.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelBucketSaltWater, RedSteelBucketSaltWater.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelBucketEmpty, BlueSteelBucketEmpty.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelBucketLava, BlueSteelBucketLava.getUnlocalizedName());

		GameRegistry.registerItem(Quern, Quern.getUnlocalizedName());
		GameRegistry.registerItem(FlintSteel, FlintSteel.getUnlocalizedName());

		GameRegistry.registerItem(DoorOak, DoorOak.getUnlocalizedName());
		GameRegistry.registerItem(DoorAspen, DoorAspen.getUnlocalizedName());
		GameRegistry.registerItem(DoorBirch, DoorBirch.getUnlocalizedName());
		GameRegistry.registerItem(DoorChestnut, DoorChestnut.getUnlocalizedName());
		GameRegistry.registerItem(DoorDouglasFir, DoorDouglasFir.getUnlocalizedName());
		GameRegistry.registerItem(DoorHickory, DoorHickory.getUnlocalizedName());
		GameRegistry.registerItem(DoorMaple, DoorMaple.getUnlocalizedName());
		GameRegistry.registerItem(DoorAsh, DoorAsh.getUnlocalizedName());
		GameRegistry.registerItem(DoorPine, DoorPine.getUnlocalizedName());
		GameRegistry.registerItem(DoorSequoia, DoorSequoia.getUnlocalizedName());
		GameRegistry.registerItem(DoorSpruce, DoorSpruce.getUnlocalizedName());
		GameRegistry.registerItem(DoorSycamore, DoorSycamore.getUnlocalizedName());
		GameRegistry.registerItem(DoorWhiteCedar, DoorWhiteCedar.getUnlocalizedName());
		GameRegistry.registerItem(DoorWhiteElm, DoorWhiteElm.getUnlocalizedName());
		GameRegistry.registerItem(DoorWillow, DoorWillow.getUnlocalizedName());
		GameRegistry.registerItem(DoorKapok, DoorKapok.getUnlocalizedName());
		GameRegistry.registerItem(DoorAcacia, DoorAcacia.getUnlocalizedName());

		GameRegistry.registerItem(GlassBottle, GlassBottle.getUnlocalizedName());
		GameRegistry.registerItem(Beer, Beer.getUnlocalizedName());
		GameRegistry.registerItem(Cider, Cider.getUnlocalizedName());
		GameRegistry.registerItem(Rum, Rum.getUnlocalizedName());
		GameRegistry.registerItem(RyeWhiskey, RyeWhiskey.getUnlocalizedName());
		GameRegistry.registerItem(Sake, Sake.getUnlocalizedName());
		GameRegistry.registerItem(Vodka, Vodka.getUnlocalizedName());
		GameRegistry.registerItem(Whiskey, Whiskey.getUnlocalizedName());

		GameRegistry.registerItem(Blueprint, Blueprint.getUnlocalizedName());
		GameRegistry.registerItem(writabeBookTFC, writabeBookTFC.getUnlocalizedName());
		GameRegistry.registerItem(WoolYarn, WoolYarn.getUnlocalizedName());
		GameRegistry.registerItem(Wool, Wool.getUnlocalizedName());
		GameRegistry.registerItem(WoolCloth, WoolCloth.getUnlocalizedName());
		GameRegistry.registerItem(Spindle, Spindle.getUnlocalizedName());
		GameRegistry.registerItem(SpindleHead, SpindleHead.getUnlocalizedName());
		GameRegistry.registerItem(StoneBrick , StoneBrick.getUnlocalizedName());

		GameRegistry.registerItem(Mortar , Mortar.getUnlocalizedName());
		GameRegistry.registerItem(Vinegar , Vinegar.getUnlocalizedName());

		GameRegistry.registerItem(Hide , Hide.getUnlocalizedName());
		GameRegistry.registerItem(SoakedHide , SoakedHide.getUnlocalizedName());
		GameRegistry.registerItem(ScrapedHide , ScrapedHide.getUnlocalizedName());
		GameRegistry.registerItem(PrepHide , PrepHide.getUnlocalizedName());
		GameRegistry.registerItem(SheepSkin , SheepSkin.getUnlocalizedName());
		GameRegistry.registerItem(FlatLeather , FlatLeather.getUnlocalizedName());
		GameRegistry.registerItem(Leather , Leather.getUnlocalizedName());
		GameRegistry.registerItem(Straw , Straw.getUnlocalizedName());
		GameRegistry.registerItem(FlatClay , FlatClay.getUnlocalizedName());

		GameRegistry.registerItem(PotteryJug , PotteryJug.getUnlocalizedName());
		GameRegistry.registerItem(PotterySmallVessel , PotterySmallVessel.getUnlocalizedName());
		GameRegistry.registerItem(PotteryPot , PotteryPot.getUnlocalizedName());

		GameRegistry.registerItem(CeramicMold , CeramicMold.getUnlocalizedName());
		GameRegistry.registerItem(FireBrick , FireBrick.getUnlocalizedName());

		GameRegistry.registerItem(ClayMoldAxe , ClayMoldAxe.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldChisel , ClayMoldChisel.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldHammer , ClayMoldHammer.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldHoe , ClayMoldHoe.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldKnife , ClayMoldKnife.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldMace , ClayMoldMace.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldPick , ClayMoldPick.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldProPick , ClayMoldProPick.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldSaw , ClayMoldSaw.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldScythe , ClayMoldScythe.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldShovel , ClayMoldShovel.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldSword , ClayMoldSword.getUnlocalizedName());
		GameRegistry.registerItem(ClayMoldJavelin , ClayMoldJavelin.getUnlocalizedName());

		GameRegistry.registerItem(TuyereCopper , TuyereCopper.getUnlocalizedName());
		GameRegistry.registerItem(TuyereBronze , TuyereBronze.getUnlocalizedName());
		GameRegistry.registerItem(TuyereBlackBronze , TuyereBlackBronze.getUnlocalizedName());
		GameRegistry.registerItem(TuyereBismuthBronze , TuyereBismuthBronze.getUnlocalizedName());
		GameRegistry.registerItem(TuyereWroughtIron , TuyereWroughtIron.getUnlocalizedName());
		GameRegistry.registerItem(TuyereSteel , TuyereSteel.getUnlocalizedName());
		GameRegistry.registerItem(TuyereBlackSteel , TuyereBlackSteel.getUnlocalizedName());
		GameRegistry.registerItem(TuyereRedSteel , TuyereRedSteel.getUnlocalizedName());
		GameRegistry.registerItem(TuyereBlueSteel , TuyereBlueSteel.getUnlocalizedName());

		GameRegistry.registerItem(Bloom , Bloom.getUnlocalizedName());
		GameRegistry.registerItem(RawBloom , RawBloom.getUnlocalizedName());

		GameRegistry.registerItem(UnknownIngot , UnknownIngot.getUnlocalizedName());
		GameRegistry.registerItem(UnknownUnshaped , UnknownUnshaped.getUnlocalizedName());

		GameRegistry.registerItem(Jute , Jute.getUnlocalizedName());
		GameRegistry.registerItem(JuteFibre , JuteFibre.getUnlocalizedName());
		GameRegistry.registerItem(Reeds , Reeds.getUnlocalizedName());

		GameRegistry.registerItem(FishingRod, FishingRod.getUnlocalizedName());
		GameRegistry.registerItem(Coal, Coal.getUnlocalizedName());
		GameRegistry.registerItem(Stick, Stick.getUnlocalizedName());
		GameRegistry.registerItem(Bow, Bow.getUnlocalizedName());
		GameRegistry.registerItem(Arrow, Arrow.getUnlocalizedName());
		GameRegistry.registerItem(Dye, Dye.getUnlocalizedName());
		GameRegistry.registerItem(Leash, Leash.getUnlocalizedName());
		GameRegistry.registerItem(ClayBall, ClayBall.getUnlocalizedName());
		GameRegistry.registerItem(Powder, Powder.getUnlocalizedName());
		GameRegistry.registerItem(Fertilizer, Fertilizer.getUnlocalizedName());


		System.out.println(new StringBuilder().append("[TFC] Registering Food").toString());
		GameRegistry.registerItem(FruitTreeSapling, FruitTreeSapling.getUnlocalizedName());
		GameRegistry.registerItem(RedApple, RedApple.getUnlocalizedName());
		GameRegistry.registerItem(Banana, Banana.getUnlocalizedName());
		GameRegistry.registerItem(Orange, Orange.getUnlocalizedName());
		GameRegistry.registerItem(GreenApple, GreenApple.getUnlocalizedName());
		GameRegistry.registerItem(Lemon, Lemon.getUnlocalizedName());
		GameRegistry.registerItem(Olive, Olive.getUnlocalizedName());
		GameRegistry.registerItem(Cherry, Cherry.getUnlocalizedName());
		GameRegistry.registerItem(Peach, Peach.getUnlocalizedName());
		GameRegistry.registerItem(Plum, Plum.getUnlocalizedName());
		GameRegistry.registerItem(Egg, Egg.getUnlocalizedName());
		GameRegistry.registerItem(EggCooked, EggCooked.getUnlocalizedName());
		GameRegistry.registerItem(WheatGrain, WheatGrain.getUnlocalizedName());
		GameRegistry.registerItem(BarleyGrain, BarleyGrain.getUnlocalizedName());
		GameRegistry.registerItem(OatGrain, OatGrain.getUnlocalizedName());
		GameRegistry.registerItem(RyeGrain, RyeGrain.getUnlocalizedName());
		GameRegistry.registerItem(RiceGrain, RiceGrain.getUnlocalizedName());
		GameRegistry.registerItem(MaizeEar, MaizeEar.getUnlocalizedName());
		GameRegistry.registerItem(Tomato, Tomato.getUnlocalizedName());
		GameRegistry.registerItem(Potato, Potato.getUnlocalizedName());
		GameRegistry.registerItem(Onion, Onion.getUnlocalizedName());
		GameRegistry.registerItem(Cabbage, Cabbage.getUnlocalizedName());
		GameRegistry.registerItem(Garlic, Garlic.getUnlocalizedName());
		GameRegistry.registerItem(Carrot, Carrot.getUnlocalizedName());
		GameRegistry.registerItem(Sugarcane, Sugarcane.getUnlocalizedName());
		GameRegistry.registerItem(Hemp, Hemp.getUnlocalizedName());
		GameRegistry.registerItem(Soybean, Soybean.getUnlocalizedName());
		GameRegistry.registerItem(Greenbeans, Greenbeans.getUnlocalizedName());
		GameRegistry.registerItem(GreenBellPepper, GreenBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(YellowBellPepper, YellowBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(RedBellPepper, RedBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(Squash, Squash.getUnlocalizedName());
		GameRegistry.registerItem(Cheese, Cheese.getUnlocalizedName());
		GameRegistry.registerItem(WheatWhole, WheatWhole.getUnlocalizedName());
		GameRegistry.registerItem(BarleyWhole, BarleyWhole.getUnlocalizedName());
		GameRegistry.registerItem(OatWhole, OatWhole.getUnlocalizedName());
		GameRegistry.registerItem(RyeWhole, RyeWhole.getUnlocalizedName());
		GameRegistry.registerItem(RiceWhole, RiceWhole.getUnlocalizedName());
		GameRegistry.registerItem(WheatGround, WheatGround.getUnlocalizedName());
		GameRegistry.registerItem(BarleyGround, BarleyGround.getUnlocalizedName());
		GameRegistry.registerItem(OatGround, OatGround.getUnlocalizedName());
		GameRegistry.registerItem(RyeGround, RyeGround.getUnlocalizedName());
		GameRegistry.registerItem(RiceGround, RiceGround.getUnlocalizedName());
		GameRegistry.registerItem(CornmealGround, CornmealGround.getUnlocalizedName());
		GameRegistry.registerItem(WheatDough, WheatDough.getUnlocalizedName());
		GameRegistry.registerItem(BarleyDough, BarleyDough.getUnlocalizedName());
		GameRegistry.registerItem(OatDough, OatDough.getUnlocalizedName());
		GameRegistry.registerItem(RyeDough, RyeDough.getUnlocalizedName());
		GameRegistry.registerItem(RiceDough, RiceDough.getUnlocalizedName());
		GameRegistry.registerItem(CornmealDough, CornmealDough.getUnlocalizedName());
		GameRegistry.registerItem(WheatBread, WheatBread.getUnlocalizedName());
		GameRegistry.registerItem(BarleyBread, BarleyBread.getUnlocalizedName());
		GameRegistry.registerItem(OatBread, OatBread.getUnlocalizedName());
		GameRegistry.registerItem(RyeBread, RyeBread.getUnlocalizedName());
		GameRegistry.registerItem(RiceBread, RiceBread.getUnlocalizedName());
		GameRegistry.registerItem(CornBread, CornBread.getUnlocalizedName());
		GameRegistry.registerItem(calamariRaw, calamariRaw.getUnlocalizedName());
		GameRegistry.registerItem(calamariCooked, calamariCooked.getUnlocalizedName());
		GameRegistry.registerItem(SeedsWheat, SeedsWheat.getUnlocalizedName());
		GameRegistry.registerItem(SeedsBarley, SeedsBarley.getUnlocalizedName());
		GameRegistry.registerItem(SeedsRye, SeedsRye.getUnlocalizedName());
		GameRegistry.registerItem(SeedsOat, SeedsOat.getUnlocalizedName());
		GameRegistry.registerItem(SeedsRice, SeedsRice.getUnlocalizedName());
		GameRegistry.registerItem(SeedsMaize, SeedsMaize.getUnlocalizedName());
		GameRegistry.registerItem(SeedsPotato, SeedsPotato.getUnlocalizedName());
		GameRegistry.registerItem(SeedsOnion, SeedsOnion.getUnlocalizedName());
		GameRegistry.registerItem(SeedsCabbage, SeedsCabbage.getUnlocalizedName());
		GameRegistry.registerItem(SeedsGarlic, SeedsGarlic.getUnlocalizedName());
		GameRegistry.registerItem(SeedsCarrot, SeedsCarrot.getUnlocalizedName());
		GameRegistry.registerItem(SeedsSugarcane, SeedsSugarcane.getUnlocalizedName());
		GameRegistry.registerItem(SeedsHemp, SeedsHemp.getUnlocalizedName());
		GameRegistry.registerItem(SeedsTomato, SeedsTomato.getUnlocalizedName());
		GameRegistry.registerItem(SeedsYellowBellPepper, SeedsYellowBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(SeedsRedBellPepper, SeedsRedBellPepper.getUnlocalizedName());
		GameRegistry.registerItem(SeedsSoybean, SeedsSoybean.getUnlocalizedName());
		GameRegistry.registerItem(SeedsGreenbean, SeedsGreenbean.getUnlocalizedName());
		GameRegistry.registerItem(SeedsSquash, SeedsSquash.getUnlocalizedName());
		GameRegistry.registerItem(SeedsJute, SeedsJute.getUnlocalizedName());
		GameRegistry.registerItem(muttonRaw, muttonRaw.getUnlocalizedName());
		GameRegistry.registerItem(muttonCooked, muttonCooked.getUnlocalizedName());
		GameRegistry.registerItem(venisonRaw, venisonRaw.getUnlocalizedName());
		GameRegistry.registerItem(venisonCooked, venisonCooked.getUnlocalizedName());
		GameRegistry.registerItem(horseMeatRaw, horseMeatRaw.getUnlocalizedName());
		GameRegistry.registerItem(horseMeatCooked, horseMeatCooked.getUnlocalizedName());
		GameRegistry.registerItem(porkchopRaw, porkchopRaw.getUnlocalizedName());
		GameRegistry.registerItem(porkchopCooked, porkchopCooked.getUnlocalizedName());
		GameRegistry.registerItem(fishRaw, fishRaw.getUnlocalizedName());
		GameRegistry.registerItem(fishCooked, fishCooked.getUnlocalizedName());
		GameRegistry.registerItem(beefRaw, beefRaw.getUnlocalizedName());
		GameRegistry.registerItem(beefCooked, beefCooked.getUnlocalizedName());
		GameRegistry.registerItem(chickenRaw, chickenRaw.getUnlocalizedName());
		GameRegistry.registerItem(chickenCooked, chickenCooked.getUnlocalizedName());

		GameRegistry.registerItem(WintergreenBerry, WintergreenBerry.getUnlocalizedName());
		GameRegistry.registerItem(Blueberry, Blueberry.getUnlocalizedName());
		GameRegistry.registerItem(Raspberry, Raspberry.getUnlocalizedName());
		GameRegistry.registerItem(Strawberry, Strawberry.getUnlocalizedName());
		GameRegistry.registerItem(Blackberry, Blackberry.getUnlocalizedName());
		GameRegistry.registerItem(Bunchberry, Bunchberry.getUnlocalizedName());
		GameRegistry.registerItem(Cranberry, Cranberry.getUnlocalizedName());
		GameRegistry.registerItem(Snowberry, Snowberry.getUnlocalizedName());
		GameRegistry.registerItem(Elderberry, Elderberry.getUnlocalizedName());
		GameRegistry.registerItem(Gooseberry, Gooseberry.getUnlocalizedName());
		GameRegistry.registerItem(Cloudberry, Cloudberry.getUnlocalizedName());
		GameRegistry.registerItem(WintergreenLeaf, WintergreenLeaf.getUnlocalizedName());
		GameRegistry.registerItem(BlueberryLeaf, BlueberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(RaspberryLeaf, RaspberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(StrawberryLeaf, StrawberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(BlackberryLeaf, BlackberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(BunchberryLeaf, BunchberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(CranberryLeaf, CranberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(SnowberryLeaf, SnowberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(ElderberryLeaf, ElderberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(GooseberryLeaf, GooseberryLeaf.getUnlocalizedName());
		GameRegistry.registerItem(CloudberryLeaf, CloudberryLeaf.getUnlocalizedName());

		System.out.println(new StringBuilder().append("[TFC] Registering Armor").toString());
		GameRegistry.registerItem(BismuthSheet, BismuthSheet.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeSheet, BismuthBronzeSheet.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeSheet, BlackBronzeSheet.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelSheet, BlackSteelSheet.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelSheet, BlueSteelSheet.getUnlocalizedName());
		GameRegistry.registerItem(BronzeSheet, BronzeSheet.getUnlocalizedName());
		GameRegistry.registerItem(CopperSheet, CopperSheet.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronSheet, WroughtIronSheet.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelSheet, RedSteelSheet.getUnlocalizedName());
		GameRegistry.registerItem(RoseGoldSheet, RoseGoldSheet.getUnlocalizedName());
		GameRegistry.registerItem(SteelSheet, SteelSheet.getUnlocalizedName());
		GameRegistry.registerItem(TinSheet, TinSheet.getUnlocalizedName());
		GameRegistry.registerItem(ZincSheet, ZincSheet.getUnlocalizedName());
		GameRegistry.registerItem(BismuthSheet2x, BismuthSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeSheet2x, BismuthBronzeSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeSheet2x, BlackBronzeSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelSheet2x, BlackSteelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelSheet2x, BlueSteelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(BronzeSheet2x, BronzeSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(CopperSheet2x, CopperSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronSheet2x, WroughtIronSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelSheet2x, RedSteelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(RoseGoldSheet2x, RoseGoldSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(SteelSheet2x, SteelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(TinSheet2x, TinSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(ZincSheet2x, ZincSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(BrassSheet, BrassSheet.getUnlocalizedName());
		GameRegistry.registerItem(GoldSheet, GoldSheet.getUnlocalizedName());
		GameRegistry.registerItem(LeadSheet, LeadSheet.getUnlocalizedName());
		GameRegistry.registerItem(NickelSheet, NickelSheet.getUnlocalizedName());
		GameRegistry.registerItem(PigIronSheet, PigIronSheet.getUnlocalizedName());
		GameRegistry.registerItem(PlatinumSheet, PlatinumSheet.getUnlocalizedName());
		GameRegistry.registerItem(SilverSheet, SilverSheet.getUnlocalizedName());
		GameRegistry.registerItem(SterlingSilverSheet, SterlingSilverSheet.getUnlocalizedName());
		GameRegistry.registerItem(BrassSheet2x, BrassSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(GoldSheet2x, GoldSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(LeadSheet2x, LeadSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(NickelSheet2x, NickelSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(PigIronSheet2x, PigIronSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(PlatinumSheet2x, PlatinumSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(SilverSheet2x, SilverSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(SterlingSilverSheet2x, SterlingSilverSheet2x.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeUnfinishedBoots, BismuthBronzeUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeUnfinishedBoots, BlackBronzeUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelUnfinishedBoots, BlackSteelUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelUnfinishedBoots, BlueSteelUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(BronzeUnfinishedBoots, BronzeUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(CopperUnfinishedBoots, CopperUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronUnfinishedBoots, WroughtIronUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelUnfinishedBoots, RedSteelUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(SteelUnfinishedBoots, SteelUnfinishedBoots.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeBoots, BismuthBronzeBoots.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeBoots, BlackBronzeBoots.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelBoots, BlackSteelBoots.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelBoots, BlueSteelBoots.getUnlocalizedName());
		GameRegistry.registerItem(BronzeBoots, BronzeBoots.getUnlocalizedName());
		GameRegistry.registerItem(CopperBoots, CopperBoots.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronBoots, WroughtIronBoots.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelBoots, RedSteelBoots.getUnlocalizedName());
		GameRegistry.registerItem(SteelBoots, SteelBoots.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeUnfinishedGreaves, BismuthBronzeUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeUnfinishedGreaves, BlackBronzeUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelUnfinishedGreaves, BlackSteelUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelUnfinishedGreaves, BlueSteelUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BronzeUnfinishedGreaves, BronzeUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(CopperUnfinishedGreaves, CopperUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronUnfinishedGreaves, WroughtIronUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelUnfinishedGreaves, RedSteelUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(SteelUnfinishedGreaves, SteelUnfinishedGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeGreaves, BismuthBronzeGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeGreaves, BlackBronzeGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelGreaves, BlackSteelGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelGreaves, BlueSteelGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BronzeGreaves, BronzeGreaves.getUnlocalizedName());
		GameRegistry.registerItem(CopperGreaves, CopperGreaves.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronGreaves, WroughtIronGreaves.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelGreaves, RedSteelGreaves.getUnlocalizedName());
		GameRegistry.registerItem(SteelGreaves, SteelGreaves.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeUnfinishedChestplate, BismuthBronzeUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeUnfinishedChestplate, BlackBronzeUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelUnfinishedChestplate, BlackSteelUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelUnfinishedChestplate, BlueSteelUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BronzeUnfinishedChestplate, BronzeUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(CopperUnfinishedChestplate, CopperUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronUnfinishedChestplate, WroughtIronUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelUnfinishedChestplate, RedSteelUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(SteelUnfinishedChestplate, SteelUnfinishedChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeChestplate, BismuthBronzeChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeChestplate, BlackBronzeChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelChestplate, BlackSteelChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelChestplate, BlueSteelChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BronzeChestplate, BronzeChestplate.getUnlocalizedName());
		GameRegistry.registerItem(CopperChestplate, CopperChestplate.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronChestplate, WroughtIronChestplate.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelChestplate, RedSteelChestplate.getUnlocalizedName());
		GameRegistry.registerItem(SteelChestplate, SteelChestplate.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeUnfinishedHelmet, BismuthBronzeUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeUnfinishedHelmet, BlackBronzeUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelUnfinishedHelmet, BlackSteelUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelUnfinishedHelmet, BlueSteelUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(BronzeUnfinishedHelmet, BronzeUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(CopperUnfinishedHelmet, CopperUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronUnfinishedHelmet, WroughtIronUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelUnfinishedHelmet, RedSteelUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(SteelUnfinishedHelmet, SteelUnfinishedHelmet.getUnlocalizedName());
		GameRegistry.registerItem(BismuthBronzeHelmet, BismuthBronzeHelmet.getUnlocalizedName());
		GameRegistry.registerItem(BlackBronzeHelmet, BlackBronzeHelmet.getUnlocalizedName());
		GameRegistry.registerItem(BlackSteelHelmet, BlackSteelHelmet.getUnlocalizedName());
		GameRegistry.registerItem(BlueSteelHelmet, BlueSteelHelmet.getUnlocalizedName());
		GameRegistry.registerItem(BronzeHelmet, BronzeHelmet.getUnlocalizedName());
		GameRegistry.registerItem(CopperHelmet, CopperHelmet.getUnlocalizedName());
		GameRegistry.registerItem(WroughtIronHelmet, WroughtIronHelmet.getUnlocalizedName());
		GameRegistry.registerItem(RedSteelHelmet, RedSteelHelmet.getUnlocalizedName());
		GameRegistry.registerItem(SteelHelmet, SteelHelmet.getUnlocalizedName());

		GameRegistry.registerItem(LeatherHelmet, LeatherHelmet.getUnlocalizedName());
		GameRegistry.registerItem(LeatherChestplate, LeatherChestplate.getUnlocalizedName());
		GameRegistry.registerItem(LeatherLeggings, LeatherLeggings.getUnlocalizedName());
		GameRegistry.registerItem(LeatherBoots, LeatherBoots.getUnlocalizedName());

		GameRegistry.registerItem(Quiver, Quiver.getUnlocalizedName());
		GameRegistry.registerItem(MudBrick, MudBrick.getUnlocalizedName());
		GameRegistry.registerItem(Sandwich, Sandwich.getUnlocalizedName());
		GameRegistry.registerItem(Salad, Salad.getUnlocalizedName());
		GameRegistry.registerItem(Soup, Soup.getUnlocalizedName());

		System.out.println(new StringBuilder().append("[TFC] All Items Registered").toString());
	}
}
