package TFC.Core;

import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.event.entity.EntityEvent;
import TFC.TFCItems;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.registry.LanguageRegistry;

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
		achLooseRock = (new Achievement(1000, "TFC.achLooseRock", 0, 0, new ItemStack(TFCItems.LooseRock, 1, 0), null)).registerAchievement();
		addAchievementDesc("TFC.achLooseRock","Definitely a rock","Pick up a rock");
		
		achSmallOre = (new Achievement(1001, "TFC.achSmallOre", 2, 0, new ItemStack(TFCItems.SmallOreChunk, 1, 0), achLooseRock)).registerAchievement();
		addAchievementDesc("TFC.achSmallOre","No stone unturned","Find an ore nugget");
		
		achWildVegetable = (new Achievement(1002, "TFC.achWildVegetable", 0, -5, TFCItems.Onion, null)).registerAchievement();
		addAchievementDesc("TFC.achWildVegetable","Gatherer","Find a wild vegetable");
		
		achRutabaga = (new Achievement(1003, "TFC.achRutabaga", -1, -5, new ItemStack(TFCItems.Onion,1,1), achWildVegetable)).registerAchievement();
		addAchievementDesc("TFC.achRutabaga","Stupid onions...","Find a rutabaga");
		
		achDiamond = (new Achievement(1004, "TFC.achDiamond", 0, 5, new ItemStack(TFCItems.GemDiamond,1,4), achDiamond)).registerAchievement();
		addAchievementDesc("TFC.achDiamond","DIAM- oh wait, no","Find a diamond");
		
		achievementsTFC = new Achievement[] {achLooseRock,achSmallOre, achWildVegetable, achRutabaga, achDiamond};

		pageBiome = new AchievementPage("TerraFirmaCraft", achievementsTFC);

		AchievementPage.registerAchievementPage(pageBiome);
	}
	
	@SubscribeEvent
	public void chunkEntered(EntityEvent.EnteringChunk event)
	{
	}
	
	private static void addAchievementDesc(String ach, String name, String desc)
	{
		LanguageRegistry.instance().addStringLocalization("achievement." + ach, "en_US", name);
		LanguageRegistry.instance().addStringLocalization("achievement." + ach + ".desc", "en_US", desc);
	}
}
