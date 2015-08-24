package com.bioxx.tfc.Core;

import java.util.ArrayList;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;

import net.minecraftforge.common.AchievementPage;

import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;

public class TFC_Achievements
{
	public static Achievement achLooseRock;
	public static Achievement achSmallOre;
	public static Achievement achWildVegetable;
	public static Achievement achRutabaga;
	public static Achievement achDiamond;
	public static Achievement achAnvil;
	public static Achievement achQuern;
	public static Achievement achPickaxe;
	public static Achievement achStoneAge;
	public static Achievement achCopperAge;
	public static Achievement achBronzeAge;
	public static Achievement achIronAge;
	public static Achievement achLimonite;
	public static Achievement achSaw;
	public static Achievement achPokeCreeper;
	public static Achievement achTwoKnives;
	public static Achievement achBlastFurnace;
	public static Achievement achFireClay;
	public static Achievement achCrucible;
	public static Achievement achUnknown;
	public static Achievement achBlackSteel;
	public static Achievement achBlueSteel;
	public static Achievement achRedSteel;
	public static Achievement achBlueBucket;
	public static Achievement achRedBucket;

	private static ArrayList<Achievement> achlist;
	public static AchievementPage pageBiome;
	static Achievement[] achievementsTFC;
	
	public TFC_Achievements()
	{
		init();
	}
	
	public static void init()
	{
		achlist = new ArrayList<Achievement>();

		achLooseRock = createAchievement("achievement.achLooseRock", "achLooseRock", 0, 0, new ItemStack(TFCItems.LooseRock), null);
		achSmallOre = createAchievement("achievement.achSmallOre", "achSmallOre", 2, 0, new ItemStack(TFCItems.SmallOreChunk), achLooseRock);
		achWildVegetable = createAchievement("achievement.achWildVegetable", "achWildVegetable", -3, -1, new ItemStack(TFCItems.Onion), null);
		achRutabaga = createAchievement("achievement.achRutabaga", "achRutabaga", -3, 1, new ItemStack(TFCItems.Onion, 1, 1), achWildVegetable);
		achStoneAge = createAchievement("achievement.achStoneAge", "achStoneAge", -1, -1, new ItemStack(TFCItems.IgInStoneAxeHead), achLooseRock);
		achTwoKnives = createAchievement("achievement.achTwoKnives", "achTwoKnives", -1, -3, new ItemStack(TFCItems.StoneKnife), achStoneAge);
		achCopperAge = createAchievement("achievement.achCopperAge", "achCopperAge", 3, -1, new ItemStack(TFCItems.CopperAxeHead), achSmallOre);
		achSaw = createAchievement("achievement.achSaw", "achSaw", 5, -1, new ItemStack(TFCItems.CopperSaw), achCopperAge);
		achAnvil = createAchievement("achievement.achAnvil", "achAnvil", 1, 2, new ItemStack(TFCItems.StoneHammer, 1, 2), achStoneAge);
		achPickaxe = createAchievement("achievement.achPickaxe", "achPickaxe", 3, -3, new ItemStack(TFCItems.CopperPick), achCopperAge);
		achQuern = createAchievement("achievement.achQuern", "achQuern", 3, -5, new ItemStack(TFCBlocks.Quern), achPickaxe);
		achDiamond = createAchievement("achievement.achDiamond", "achDiamond", 1, -3, new ItemStack(TFCItems.GemDiamond, 1, 4), achPickaxe);
		achLimonite = createAchievement("achievement.achLimonite", "achLimonite", 5, -3, new ItemStack(TFCItems.OreChunk, 1, 11), achPickaxe);
		achBronzeAge = createAchievement("achievement.achBronzeAge", "achBronzeAge", 4, 1, new ItemStack(TFCBlocks.Anvil, 1, 2), achCopperAge);
		achIronAge = createAchievement("achievement.achIronAge", "achIronAge", 5, 3, new ItemStack(TFCItems.RawBloom), achBronzeAge);
		achPokeCreeper = createAchievement("achievement.achPokeCreeper", "achPokeCreeper", -3, -3, new ItemStack(Items.skull, 1, 4), null);
		achBlastFurnace = createAchievement("achievement.achBlastFurnace", "achBlastFurnace", 6, 1, new ItemStack(TFCBlocks.BlastFurnace), achIronAge);
		achFireClay = createAchievement("achievement.achFireClay", "achFireClay", 5, -5, new ItemStack(TFCItems.ClayBall, 1, 1), achQuern);
		achCrucible = createAchievement("achievement.achCrucible", "achCrucible", 7, -5, new ItemStack(TFCBlocks.Crucible), achFireClay);
		achUnknown = createAchievement("achievement.achUnknown", "achUnknown", 7, -3, new ItemStack(TFCItems.UnknownIngot), achCrucible);
		achBlackSteel = createAchievement("achievement.achBlackSteel", "achBlackSteel", 7, 0, new ItemStack(TFCItems.BlackSteelIngot), achBlastFurnace);
		achBlueSteel = createAchievement("achievement.achBlueSteel", "achBlueSteel", 8, 1, new ItemStack(TFCItems.BlueSteelIngot), achBlackSteel);
		achRedSteel = createAchievement("achievement.achRedSteel", "achRedSteel", 8, -1, new ItemStack(TFCItems.RedSteelIngot), achBlackSteel);
		achBlueBucket = createAchievement("achievement.achBlueBucket", "achBlueBucket", 10, 1, new ItemStack(TFCItems.BlueSteelBucketEmpty), achBlueSteel);
		achRedBucket = createAchievement("achievement.achRedBucket", "achRedBucket", 10, -1, new ItemStack(TFCItems.RedSteelBucketEmpty), achRedSteel);
		
		achievementsTFC = new Achievement[achlist.size()];
		achievementsTFC = achlist.toArray(achievementsTFC);
		pageBiome = new AchievementPage("TerraFirmaCraft", achievementsTFC);
		AchievementPage.registerAchievementPage(pageBiome);
	}

	private static Achievement createAchievement(String id, String name, int posX, int posY, ItemStack is, Achievement preReq)
	{
		Achievement a = new Achievement(id, name, posX, posY, is, preReq).registerStat();
		achlist.add(a);
		return a;
	}

}
