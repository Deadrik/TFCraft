package TFC.Core;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.event.entity.EntityEvent;
import TFC.TFCItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class TFC_Achievements
{
	public static Achievement achLooseRock;
	public static Achievement achSmallOre;
	public static Achievement achWildVegetable;
	public static Achievement achRutabaga;
	public static Achievement achDiamond;
	public static AchievementPage pageBiome;
	static Achievement[] achievementsTFC;
	
	public TFC_Achievements()
	{
		init();
	}
	
	public static void init()
	{
		achLooseRock = (new Achievement("achievement.TFC.achLooseRock", "TFC.achLooseRock", 0, 0, new ItemStack(TFCItems.LooseRock, 1, 0), (Achievement)null)).registerStat();
		achSmallOre = (new Achievement("achievement.TFC.achSmallOre", "TFC.achSmallOre", 2, 0, new ItemStack(TFCItems.SmallOreChunk, 1, 0), achLooseRock)).registerStat();
		achWildVegetable = (new Achievement("achievement.TFC.achWildVegetable", "TFC.achWildVegetable", 0, -5, TFCItems.Onion, (Achievement)null)).registerStat();
		achRutabaga = (new Achievement("achievement.TFC.achRutabaga", "TFC.achRutabaga", -1, -5, new ItemStack(TFCItems.Onion,1,1), achWildVegetable)).registerStat();
		achDiamond = (new Achievement("achievement.TFC.achDiamond", "TFC.achDiamond", 0, 5, new ItemStack(TFCItems.GemDiamond,1,4), achDiamond)).registerStat();

		achievementsTFC = new Achievement[] {achLooseRock,achSmallOre, achWildVegetable, achRutabaga, achDiamond};
		pageBiome = new AchievementPage("TerraFirmaCraft", achievementsTFC);
		AchievementPage.registerAchievementPage(pageBiome);
	}

	@SubscribeEvent
	public void chunkEntered(EntityEvent.EnteringChunk event)
	{
	}

}
