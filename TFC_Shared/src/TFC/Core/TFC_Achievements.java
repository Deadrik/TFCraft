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
	public static Achievement achPickaxe;
	public static Achievement achStoneAge;
	public static Achievement achCopperAge;
	public static Achievement achBronzeAge;
	public static Achievement achIronAge;
	public static Achievement achLimonite;
	public static Achievement achSaw;
	

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

		achLooseRock = createAchievement("TFC.achLooseRock",-2,0,new ItemStack(TFCItems.LooseRock,1,0), null, "Definitely a rock", "Pick up a rock");

		achSmallOre = createAchievement("TFC.achSmallOre",1,0,new ItemStack(TFCItems.SmallOreChunk,1,0), achLooseRock, "No stone unturned", "Find an ore nugget");

		achWildVegetable = createAchievement("TFC.achWildVegetable",-2,-3,new ItemStack(TFCItems.Onion),null,"Gatherer","Find a wild vegetable");

		achRutabaga = createAchievement("TFC.achRutabaga",-1,-5,new ItemStack(TFCItems.Onion,1,1),achWildVegetable,"Stupid onions...","Find a rutabaga");

		achQuern = createAchievement("TFC.achQuern",2,-6, new ItemStack(TFCBlocks.Quern,1,0),achWildVegetable,"The Grind","Use a quern");
		
		achStoneAge = createAchievement("TFC.achStoneAge",0,-1, new ItemStack(TFCItems.IgInStoneAxeHead,1,0),achLooseRock,"Paleolithic!","Knap a stone tool to enter the Stone Age");
		
		achCopperAge = createAchievement("TFC.achCopperAge",2,1, new ItemStack(TFCItems.CopperAxeHead,1,0),achSmallOre,"Chalcolithic!","Cast something in metal to enter the Copper Age");
		
		achSaw = createAchievement("TFC.achSaw",1,2, new ItemStack(TFCItems.CopperSaw,1,0),achCopperAge,"Carpenter","Craft a saw");
		
		achAnvil = createAchievement("TFC.achAnvil",3,3, new ItemStack(TFCItems.StoneHammer,1,2),achStoneAge,"Hammer Time","Make a stone anvil");
		
		achPickaxe = createAchievement("TFC.achPickaxe",2,-1, new ItemStack(TFCItems.CopperPick,1,0),achCopperAge,"Time to Mine! (finally)","Make a pickaxe");
		
		achDiamond = createAchievement("TFC.achDiamond",5,-1,new ItemStack(TFCItems.GemDiamond,1,4),achPickaxe,"DIAM- oh wait, no","Find a diamond");
		
		achLimonite = createAchievement("TFC.achLimonite",3,-2, new ItemStack(TFCItems.OreChunk,1,11),achPickaxe, "LI-MO-NIIITE!", "Mine some limonite");
		
		achBronzeAge = createAchievement("TFC.achBronzeAge",4,1, new ItemStack(TFCBlocks.Anvil,1,2),achCopperAge,"The Bronze Age", "Craft a bronze anvil to enter the Bronze Age");
		
		achIronAge = createAchievement("TFC.achIronAge",6,2, new ItemStack(TFCItems.Bloom,1,0),achBronzeAge,"The Iron Age", "Mine an iron bloom to enter the Iron Age");
		
		achievementsTFC = new Achievement[achlist.size()];
		achievementsTFC = achlist.toArray(achievementsTFC);

		pageBiome = new AchievementPage("TerraFirmaCraft", achievementsTFC);

		AchievementPage.registerAchievementPage(pageBiome);
	}

	private static Achievement createAchievement(String name, int posX, int posY, ItemStack is, Achievement preReq, String IGN,String desc){
		boolean fail = true;
		Achievement a = null;
		while(fail){
			try{
				fail = false;
				a = (new Achievement(achievementIndex++, name,posX,posY,is,preReq)).registerAchievement();
			}
			catch(RuntimeException re){
				fail = true;
			}
		}
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
