package TFC.Core;

import java.util.ArrayList;

import TFC.TFCBlocks;
import TFC.TFCItems;
import cpw.mods.fml.common.registry.LanguageRegistry;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;
import net.minecraft.stats.StatList;
import net.minecraftforge.common.AchievementPage;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent;

public class TFC_Achievements {

	public static Achievement achLooseRock;
	public static Achievement achSmallOre;
	public static Achievement achWildVegetable;
	public static Achievement achRutabaga;
	public static Achievement achDiamond;
	public static Achievement achAnvil;
	public static Achievement achQuern;
	
	private static int achievementIndex = 1000;
	private static ArrayList<Achievement> achlist;
	
	public static AchievementPage pageBiome;
	
	static Achievement[] achievementsTFC;
	
	public TFC_Achievements(){
		init();
	}
	
	public static void init()
	{
		achlist = new ArrayList<Achievement>();
		
		achLooseRock = createAchievement("TFC.achLooseRock",0,0,new ItemStack(TFCItems.LooseRock,1,0), null, "Definitely a rock", "Pick up a rock");
		
		achSmallOre = createAchievement("TFC.achSmallOre",2,0,new ItemStack(TFCItems.SmallOreChunk,1,0), achLooseRock, "No stone unturned", "Find an ore nugget");
				
		achWildVegetable = createAchievement("TFC.achWildVegetable",0,-5,new ItemStack(TFCItems.Onion),null,"Gatherer","Find a wild vegetable");
		
		achRutabaga = createAchievement("TFC.achRutabaga",-1,-5,new ItemStack(TFCItems.Onion,1,1),achWildVegetable,"Stupid onions...","Find a rutabaga");
		
		achDiamond = createAchievement("TFC.achDiamond",0,5,new ItemStack(TFCItems.GemDiamond,1,4),null,"DIAM- oh wait, no","Find a diamond");
		
		achAnvil = createAchievement("TFC.achAnvil",2,-2, new ItemStack(TFCBlocks.Anvil,1,1),null,"Hammer Time","Make an anvil");
		
		achievementsTFC = new Achievement[achlist.size()];
		achievementsTFC = achlist.toArray(achievementsTFC);

		pageBiome = new AchievementPage("TerraFirmaCraft", achievementsTFC);

		AchievementPage.registerAchievementPage(pageBiome);
	}
	
	private static Achievement createAchievement(String name, int posX, int posY, ItemStack is, Achievement preReq, String IGN,String desc){
		Achievement a = (new Achievement(achievementIndex++, name,posX,posY,is,preReq)).registerAchievement();
		addAchievementDesc(name,IGN, desc);
		achlist.add(a);
		return a;
	}
	
	@ForgeSubscribe
	public void chunkEntered(EntityEvent.EnteringChunk event)
	{
	}
	
	private static void addAchievementDesc(String ach, String name, String desc)
	{
		LanguageRegistry.instance().addStringLocalization("achievement." + ach, "en_US", name);
		LanguageRegistry.instance().addStringLocalization("achievement." + ach + ".desc", "en_US", desc);
	}
}
