package TFC.Core;

import java.util.ArrayList;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.event.entity.EntityEvent;
import TFC.TFCBlocks;
import TFC.TFCItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TFC_Achievements
{
	public static Achievement achLooseRock;
	public static Achievement achSmallOre;
	public static Achievement achWildVegetable;
	public static Achievement achRutabaga;
	public static Achievement achDiamond;
	public static Achievement achAnvil;
	public static Achievement achQuern;

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

		achLooseRock = createAchievement("achievement.TFC.achLooseRock", "TFC.achLooseRock", 0, 0, new ItemStack(TFCItems.LooseRock, 1, 0), (Achievement)null);
		achSmallOre = createAchievement("achievement.TFC.achSmallOre", "TFC.achSmallOre", 2, 0, new ItemStack(TFCItems.SmallOreChunk, 1, 0), achLooseRock);
		achWildVegetable = createAchievement("achievement.TFC.achWildVegetable", "TFC.achWildVegetable", 0, -5, new ItemStack(TFCItems.Onion, 1, 1), (Achievement)null);
		achRutabaga = createAchievement("achievement.TFC.achRutabaga", "TFC.achRutabaga", -1, -5, new ItemStack(TFCItems.Onion, 1, 1), achWildVegetable);
		achDiamond = createAchievement("achievement.TFC.achDiamond", "TFC.achDiamond", 0, 5, new ItemStack(TFCItems.GemDiamond, 1, 4), achDiamond);
		achAnvil = createAchievement("achievement.TFC.achAnvil", "TFC.achAnvil", 2, -2, new ItemStack(TFCBlocks.Anvil, 1, 1), (Achievement)null);

		achievementsTFC = new Achievement[achlist.size()];
		achievementsTFC = achlist.toArray(achievementsTFC);
		pageBiome = new AchievementPage("TerraFirmaCraft", achievementsTFC);
		AchievementPage.registerAchievementPage(pageBiome);
	}

	private static Achievement createAchievement(String id, String name, int posX, int posY, ItemStack is, Achievement preReq)
	{
		Achievement a = (new Achievement(id, name,posX,posY,is,preReq)).registerStat();
		achlist.add(a);
		return a;
	}

	@SubscribeEvent
	public void chunkEntered(EntityEvent.EnteringChunk event)
	{
	}

}
