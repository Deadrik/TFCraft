package TFC.Core;

import java.util.ArrayList;
import java.util.Random;

import cpw.mods.fml.relauncher.ReflectionHelper;

import TFC.TileEntities.TileEntityPartial;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.Biomes.BiomeGenHillsEdgeTFC;
import TFC.WorldGen.Biomes.BiomeGenRiverTFC;
import TFC.WorldGen.Generators.WorldGenClayPit;
import TFC.WorldGen.Generators.WorldGenCustomFlowers;
import TFC.WorldGen.Generators.WorldGenCustomFruitTree;
import TFC.WorldGen.Generators.WorldGenCustomFruitTree2;
import TFC.WorldGen.Generators.WorldGenCustomTallGrass;
import TFC.WorldGen.Generators.WorldGenLooseRocks;
import TFC.WorldGen.Generators.WorldGenMinableTFC;
import TFC.WorldGen.Generators.WorldGenPeatPit;
import net.minecraft.src.*;
import net.minecraftforge.common.MinecraftForge;

public class TFC_Core
{
	public enum Direction{PosX,PosZ,NegX,NegZ,None,PosXPosZ,PosXNegZ,NegXPosZ,NegXNegZ,NegY,PosY}

	public static Item[] Axes;

	public static Item[] Chisels;

	public static Item[] Saws;

	public static Item[] Knives;

	static Boolean isBlockAboveSolid(IBlockAccess blockAccess, int i, int j, int k)
	{
		if(TerraFirmaCraft.proxy.getCurrentWorld().isBlockOpaqueCube(i, j+1, k)) {
			return true;
		}

		return false;
	}

	public static ItemStack RandomGem(Random random, int rockType)
	{
		ItemStack is = null;
		if(random.nextInt(500) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.terraGemAgate,1,0));
			items.add(new ItemStack(TFCItems.terraGemAmethyst,1,0));
			items.add(new ItemStack(TFCItems.terraGemBeryl,1,0));
			items.add(new ItemStack(TFCItems.terraGemEmerald,1,0));
			items.add(new ItemStack(TFCItems.terraGemGarnet,1,0));
			items.add(new ItemStack(TFCItems.terraGemJade,1,0));
			items.add(new ItemStack(TFCItems.terraGemJasper,1,0));
			items.add(new ItemStack(TFCItems.terraGemOpal,1,0));
			items.add(new ItemStack(TFCItems.terraGemRuby,1,0));
			items.add(new ItemStack(TFCItems.terraGemSapphire,1,0));
			items.add(new ItemStack(TFCItems.terraGemTourmaline,1,0));
			items.add(new ItemStack(TFCItems.terraGemTopaz,1,0));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

		}
		else if(random.nextInt(1000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.terraGemAgate,1,1));
			items.add(new ItemStack(TFCItems.terraGemAmethyst,1,1));
			items.add(new ItemStack(TFCItems.terraGemBeryl,1,1));
			items.add(new ItemStack(TFCItems.terraGemEmerald,1,1));
			items.add(new ItemStack(TFCItems.terraGemGarnet,1,1));
			items.add(new ItemStack(TFCItems.terraGemJade,1,1));
			items.add(new ItemStack(TFCItems.terraGemJasper,1,1));
			items.add(new ItemStack(TFCItems.terraGemOpal,1,1));
			items.add(new ItemStack(TFCItems.terraGemRuby,1,1));
			items.add(new ItemStack(TFCItems.terraGemSapphire,1,1));
			items.add(new ItemStack(TFCItems.terraGemTourmaline,1,1));
			items.add(new ItemStack(TFCItems.terraGemTopaz,1,1));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(2000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.terraGemAgate,1,2));
			items.add(new ItemStack(TFCItems.terraGemAmethyst,1,2));
			items.add(new ItemStack(TFCItems.terraGemBeryl,1,2));
			items.add(new ItemStack(TFCItems.terraGemEmerald,1,2));
			items.add(new ItemStack(TFCItems.terraGemGarnet,1,2));
			items.add(new ItemStack(TFCItems.terraGemJade,1,2));
			items.add(new ItemStack(TFCItems.terraGemJasper,1,2));
			items.add(new ItemStack(TFCItems.terraGemOpal,1,2));
			items.add(new ItemStack(TFCItems.terraGemRuby,1,2));
			items.add(new ItemStack(TFCItems.terraGemSapphire,1,2));
			items.add(new ItemStack(TFCItems.terraGemTourmaline,1,2));
			items.add(new ItemStack(TFCItems.terraGemTopaz,1,2));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(4000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.terraGemAgate,1,3));
			items.add(new ItemStack(TFCItems.terraGemAmethyst,1,3));
			items.add(new ItemStack(TFCItems.terraGemBeryl,1,3));
			items.add(new ItemStack(TFCItems.terraGemEmerald,1,3));
			items.add(new ItemStack(TFCItems.terraGemGarnet,1,3));
			items.add(new ItemStack(TFCItems.terraGemJade,1,3));
			items.add(new ItemStack(TFCItems.terraGemJasper,1,3));
			items.add(new ItemStack(TFCItems.terraGemOpal,1,3));
			items.add(new ItemStack(TFCItems.terraGemRuby,1,3));
			items.add(new ItemStack(TFCItems.terraGemSapphire,1,3));
			items.add(new ItemStack(TFCItems.terraGemTourmaline,1,3));
			items.add(new ItemStack(TFCItems.terraGemTopaz,1,3));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(8000) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(TFCItems.terraGemAgate,1,4));
			items.add(new ItemStack(TFCItems.terraGemAmethyst,1,4));
			items.add(new ItemStack(TFCItems.terraGemBeryl,1,4));
			items.add(new ItemStack(TFCItems.terraGemEmerald,1,4));
			items.add(new ItemStack(TFCItems.terraGemGarnet,1,4));
			items.add(new ItemStack(TFCItems.terraGemJade,1,4));
			items.add(new ItemStack(TFCItems.terraGemJasper,1,4));
			items.add(new ItemStack(TFCItems.terraGemOpal,1,4));
			items.add(new ItemStack(TFCItems.terraGemRuby,1,4));
			items.add(new ItemStack(TFCItems.terraGemSapphire,1,4));
			items.add(new ItemStack(TFCItems.terraGemTourmaline,1,4));
			items.add(new ItemStack(TFCItems.terraGemTopaz,1,4));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

		}
		return is;
	}

	public static void RegisterRecipes()
	{
		Item[] Ingots = {TFCItems.BismuthIngot, TFCItems.BismuthBronzeIngot,TFCItems.BlackBronzeIngot,
				TFCItems.BlackSteelIngot,TFCItems.BlueSteelIngot,TFCItems.BrassIngot,TFCItems.BronzeIngot,
				TFCItems.BronzeIngot,TFCItems.CopperIngot,TFCItems.GoldIngot,TFCItems.WroughtIronIngot,TFCItems.LeadIngot
				,TFCItems.NickelIngot,TFCItems.PigIronIngot,TFCItems.PlatinumIngot,TFCItems.RedSteelIngot,
				TFCItems.RoseGoldIngot,TFCItems.SilverIngot,TFCItems.SteelIngot,TFCItems.SterlingSilverIngot
				,TFCItems.TinIngot,TFCItems.ZincIngot};


		//jimmynator's javelin
		ModLoader.addRecipe(new ItemStack(TFCItems.Javelin, 1), new Object[] { 
			"1","2", Character.valueOf('1'), new ItemStack(TFCItems.LooseRock, 1, -1),Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addShapelessRecipe(new ItemStack(Item.arrow, 8), new Object[] { 
			new ItemStack(TFCItems.LooseRock, 1, -1), new ItemStack(Item.stick,1,-1),
			new ItemStack(Item.feather,1,-1)});

		//stone picks

		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSedPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraMMPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		//stone shovels
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSedShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraMMShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		//stone axes
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSedAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraMMAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		//stone hoes
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSedHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraMMHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		//bone pick
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSedPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraMMPick, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStonePickaxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		//bone shovels
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSedShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraMMShovel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneShovelHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		//bone axes
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSedAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraMMAxe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneAxeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		//bone hoes
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgInHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgInStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSedHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.SedStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraIgExHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.IgExStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraMMHoe, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.MMStoneHoeHead,Character.valueOf('2'), new ItemStack(Item.bone,1,-1)});

		ModLoader.addRecipe(new ItemStack(TFCItems.StoneHammer, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.StoneHammerHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

		ModLoader.addRecipe(new ItemStack(TFCItems.StoneKnife, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), TFCItems.StoneKnifeHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

		//        ModLoader.addRecipe(new ItemStack(TFCItems.terraProPickStone, 1, 0), new Object[] { 
		//            "1","2", Character.valueOf('1'), TFCItems.StoneProPickHead,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

		//        ModLoader.addRecipe(new ItemStack(TFCItems.FlintPaxel, 1, 0), new Object[] { 
		//            "1","2", Character.valueOf('1'), Item.flint,Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});

		ModLoader.addRecipe(new ItemStack(TFCItems.WoodenBucketEmpty, 1), new Object[] { "w w","w w"," w ", Character.valueOf('w'), new ItemStack(TFCItems.SinglePlank, 1, -1) });

		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < Axes.length; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 4, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(Axes[j], 1, -1)});
				ModLoader.addRecipe(new ItemStack(TFCItems.terraWoodSupportItemV, 8, i), new Object[] { "A2"," 2", Character.valueOf('2'), new ItemStack(TFCItems.Logs,1,i), Character.valueOf('A'), new ItemStack(Axes[j], 1, -1)});
				ModLoader.addRecipe(new ItemStack(TFCItems.terraWoodSupportItemH, 8, i), new Object[] { "A ","22", Character.valueOf('2'), new ItemStack(TFCItems.Logs,1,i), Character.valueOf('A'), new ItemStack(Axes[j], 1, -1)});
			}
			for(int j = 0; j < Saws.length; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 12, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(Saws[j], 1, -1)});
			}

			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SinglePlank, 4, i), new Object[] {new ItemStack(TFCItems.Logs, 1, i), new ItemStack(TFCItems.FlintPaxel, 1, -1)});
			ModLoader.addRecipe(new ItemStack(Block.planks.blockID, 1, i), new Object[] {"11","11", Character.valueOf('1'), new ItemStack(TFCItems.SinglePlank, 1, i)});
		}

		for(int j = 0; j < Knives.length; j++)
		{
			ModLoader.addRecipe(new ItemStack(Item.bowlEmpty, 4, 0), new Object[] { 
				"2","1", Character.valueOf('1'),new ItemStack(TFCItems.Logs,1,-1), Character.valueOf('2'),new ItemStack(Knives[j], 1, -1)});

			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WheatGrain, 4), new Object[] {new ItemStack(TFCItems.WheatWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.RyeGrain, 4), new Object[] {new ItemStack(TFCItems.RyeWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.BarleyGrain, 4), new Object[] {new ItemStack(TFCItems.BarleyWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.OatGrain, 4), new Object[] {new ItemStack(TFCItems.OatWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.RiceGrain, 4), new Object[] {new ItemStack(TFCItems.RiceWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildWheatGrain, 4), new Object[] {new ItemStack(TFCItems.WildWheatWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildRyeGrain, 4), new Object[] {new ItemStack(TFCItems.WildRyeWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildBarleyGrain, 4), new Object[] {new ItemStack(TFCItems.WildBarleyWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildOatGrain, 4), new Object[] {new ItemStack(TFCItems.WildOatWhole, 1),new ItemStack(Knives[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.WildRiceGrain, 4), new Object[] {new ItemStack(TFCItems.WildRiceWhole, 1),new ItemStack(Knives[j], 1, -1)});
		}

		//Chest
		ModLoader.addRecipe(new ItemStack(Block.chest, 1), new Object[] { "###","# #","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank, 1, -1)});

		//Red Stone		
		ModLoader.addRecipe(new ItemStack(Item.redstone, 8), new Object[] { 
			"1", Character.valueOf('1'),new ItemStack(TFCItems.OreChunk, 1, 28)});
		//Lapis Lazuli	
		ModLoader.addShapelessRecipe(new ItemStack(Item.dyePowder, 4,4), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 34)});

		//knapping
		for(int i = 0; i < 23; i++)
		{
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.LooseRock, 1, i), new Object[] {new ItemStack(TFCItems.LooseRock, 1, i), new ItemStack(TFCItems.LooseRock, 1, i)});
		}

		for(int i = 0; i < 13; i++)
		{			
			for(int j = 0; j < 3; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCBlocks.terraStoneIgInBrick,1,j), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 3; j < 13; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCBlocks.terraStoneSedBrick,1,j-3), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 13; j < 17; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCBlocks.terraStoneIgExBrick,1,j-13), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 17; j < 23; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(TFCBlocks.terraStoneMMBrick,1,j-17), 
						new Object[] {new ItemStack(TFCItems.LooseRock,1,j),new ItemStack(Chisels[i],1,-1)});
			}
		}

		//Gold Pan
		ModLoader.addRecipe(new ItemStack(TFCItems.terraGoldPan, 1, 0), new Object[] { 
			"1", Character.valueOf('1'),Item.bowlEmpty});
		//Sluice
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSluiceItem, 1), new Object[] { 
			"  1"," 12","122", Character.valueOf('1'),new ItemStack(Item.stick,1,-1), Character.valueOf('2'),new ItemStack(TFCItems.SinglePlank,1,-1)});

		for(int j = 0; j < TFCItems.Hammers.length; j++)
		{
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 8), new ItemStack(TFCItems.Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 12), new ItemStack(TFCItems.Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 22), new ItemStack(TFCItems.Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 2), new Object[] {new ItemStack(TFCItems.LooseRock, 1, 10), new ItemStack(TFCItems.Hammers[j], 1, -1)});
			ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 6), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 32), new ItemStack(TFCItems.Hammers[j], 1, -1)});
		}

		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.Flux, 4), new Object[] {new ItemStack(TFCItems.OreChunk, 1, 32), new ItemStack(TFCItems.StoneHammer, 1, -1)});

		ModLoader.addRecipe(new ItemStack(Item.redstone, 8, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(TFCItems.OreChunk,1,27)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraInk, 16, 0), new Object[] { "2", Character.valueOf('2'), new ItemStack(Item.dyePowder,1,0)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraFireStarter, 1, 0), new Object[] { "2 "," 2", Character.valueOf('2'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraBellowsItem, 1, 0), new Object[] { "###","???","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1), Character.valueOf('?'), Item.leather});
		//ModLoader.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgIn});
		//ModLoader.addRecipe(new ItemStack(TFCItems.terraStoneAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), mod_TFC_Core.terraStoneIgEx});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraCopperAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.CopperIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BronzeIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraWroughtIronAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.WroughtIronIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.SteelIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraBlueSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlueSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraRedSteelAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RedSteelIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraRoseGoldAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.RoseGoldIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraBismuthBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BismuthBronzeIngot2x});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraBlackBronzeAnvilItem, 1, 0), new Object[] { "###"," # ","###", Character.valueOf('#'), TFCItems.BlackBronzeIngot2x});

		ModLoader.addRecipe(new ItemStack(TFCBlocks.terraScribe, 1), new Object[] { " L ","#P#","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1),
			Character.valueOf('P'), Item.paper,Character.valueOf('L'), Item.feather});
		ModLoader.addRecipe(new ItemStack(TFCItems.terraClayMold, 4), new Object[] { "# #","###", Character.valueOf('#'), Item.clay});

		ModLoader.addRecipe(new ItemStack(TFCBlocks.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), TFCBlocks.terraStoneIgEx});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), TFCBlocks.terraStoneIgIn});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), TFCBlocks.terraStoneSed});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.terraMetalTable, 1), new Object[] { "P P","PPP","PPP", Character.valueOf('P'), TFCBlocks.terraStoneMM});

		ModLoader.addRecipe(new ItemStack(TFCBlocks.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), TFCBlocks.terraStoneIgExBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), TFCBlocks.terraStoneIgInBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), TFCBlocks.terraStoneSedBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});
		ModLoader.addRecipe(new ItemStack(TFCBlocks.terraBloomery, 1), new Object[] { "PPP","PKP","PPP", Character.valueOf('P'), TFCBlocks.terraStoneMMBrick, Character.valueOf('K'), new ItemStack(Item.coal,1,1)});

		ModLoader.addRecipe(new ItemStack(Block.rail, 64), new Object[] { "PsP","PsP", Character.valueOf('P'), TFCItems.WroughtIronIngot, Character.valueOf('s'), new ItemStack(Item.stick,1,-1)});
		ModLoader.addRecipe(new ItemStack(Block.railPowered, 64), new Object[] { " r ","PsP","PsP", Character.valueOf('P'), TFCItems.GoldIngot, Character.valueOf('s'), new ItemStack(Item.stick,1,-1), Character.valueOf('r'), Item.redstone});
		ModLoader.addRecipe(new ItemStack(Item.minecartEmpty, 1), new Object[] { "P P","PPP", Character.valueOf('P'), TFCItems.WroughtIronSheet});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.minecartCrate, 1), new Object[] { new ItemStack(Block.chest), new ItemStack(Item.minecartEmpty)});
		ModLoader.addRecipe(new ItemStack(Item.shears, 1), new Object[] { "P "," P", Character.valueOf('P'), TFCItems.WroughtIronIngot});
		ModLoader.addRecipe(new ItemStack(Block.lever, 1), new Object[] { "P","H", Character.valueOf('P'), new ItemStack(Item.stick,1,-1), Character.valueOf('H'), new ItemStack(TFCItems.LooseRock,1,-1)});
		ModLoader.addRecipe(new ItemStack(Item.doorWood, 1, 0), new Object[] { "##","##","##", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1)});
		ModLoader.addRecipe(new ItemStack(Block.trapdoor, 1, 0), new Object[] { "###","###", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1)});
		ModLoader.addRecipe(new ItemStack(Block.signPost, 1, 0), new Object[] { "###","###"," 1 ", Character.valueOf('#'), new ItemStack(TFCItems.SinglePlank,1,-1),Character.valueOf('1'), new ItemStack(Item.stick,1,-1)});

		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildWheat, 1), new Object[] {new ItemStack(TFCItems.WildWheatGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildRye, 1), new Object[] {new ItemStack(TFCItems.WildRyeGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildBarley, 1), new Object[] {new ItemStack(TFCItems.WildBarleyGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildOat, 1), new Object[] {new ItemStack(TFCItems.WildOatGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWildRice, 1), new Object[] {new ItemStack(TFCItems.WildRiceGrain, 1)});

		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsWheat, 1), new Object[] {new ItemStack(TFCItems.WheatGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsRye, 1), new Object[] {new ItemStack(TFCItems.RyeGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsBarley, 1), new Object[] {new ItemStack(TFCItems.BarleyGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsOat, 1), new Object[] {new ItemStack(TFCItems.OatGrain, 1)});
		ModLoader.addShapelessRecipe(new ItemStack(TFCItems.SeedsRice, 1), new Object[] {new ItemStack(TFCItems.RiceGrain, 1)});

		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.WheatGrain});
		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.RyeGrain});
		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.BarleyGrain});
		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.OatGrain});
		ModLoader.addRecipe(new ItemStack(Item.bread, 1), new Object[] { "PPP", Character.valueOf('P'), TFCItems.RiceGrain});

		VanillaRecipes();
	}

	private static void VanillaRecipes()
	{
		if(TFC_Settings.enableVanillaDiamondRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.diamond, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.terraGemDiamond,1,2)});
			ModLoader.addRecipe(new ItemStack(Item.diamond, 2), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.terraGemDiamond,1,3)});
			ModLoader.addRecipe(new ItemStack(Item.diamond, 3), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.terraGemDiamond,1,4)});
		}
		if(TFC_Settings.enableVanillaIronRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.ingotIron, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.WroughtIronIngot,1)});

		}
		if(TFC_Settings.enableVanillaGoldRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.ingotGold, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(TFCItems.GoldIngot,1)});
		}
		if(TFC_Settings.enableVanillaRecipes == true)
		{
			//Terrastone to Cobblestone
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.terraStoneSedCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.terraStoneIgInCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.terraStoneIgExCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),TFCBlocks.terraStoneMMCobble});
		}

		if(TFC_Settings.enableVanillaRecipes == true)
		{
			//Conversion to vanilla tools for recipes in other mods
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgInPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgExPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraSedPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraMMPick});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgInShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgExShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraSedShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraMMShovel});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgInHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgExHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraSedHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraMMHoe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgInAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraIgExAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraSedAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), TFCItems.terraMMAxe});
		}
	}

	public static void SurroundWithLeaves(World world, int i, int j, int k, int meta, Random R)
	{
		for (int y = 2; y >= -2; y--)
		{
			for (int x = 2; x >= -2; x--)
			{
				for (int z = 2; z >= -2; z--)
				{
					if(world.getBlockId(i+x, j+y, k+z) == 0) {
						world.setBlockAndMetadata(i+x, j+y, k+z, Block.leaves.blockID, meta);
					}
				}
			}
		}
	}

	public static void SetupWorld(World world)
	{
		long seed = world.getSeed();
		Random R = new Random(seed);
		world.provider.registerWorld(world);
		TFC_Game.registerAnvilRecipes(R, world);
	}

	public static void SetupWorld(World w, long seed)
	{
		World world = w;
		try
		{
			//ReflectionHelper.setPrivateValue(WorldInfo.class, w.getWorldInfo(), "randomSeed", seed);
			ReflectionHelper.setPrivateValue(WorldInfo.class, w.getWorldInfo(), seed, 0);
			SetupWorld(w);
		}
		catch(Exception ex)
		{

		}

	}

	public static boolean isRawStone(World world,int x, int y, int z)
	{
		int id = world.getBlockId(x, y, z);
		return id == TFCBlocks.terraStoneIgEx.blockID || id == TFCBlocks.terraStoneIgIn.blockID || 
				id == TFCBlocks.terraStoneSed.blockID || id == TFCBlocks.terraStoneMM.blockID;
	}

	public static boolean isRawStone(int id)
	{
		return id == TFCBlocks.terraStoneIgEx.blockID || id == TFCBlocks.terraStoneIgIn.blockID || 
				id == TFCBlocks.terraStoneSed.blockID || id == TFCBlocks.terraStoneMM.blockID;
	}

	public static boolean isDirt(int id)
	{
		return (id == TFCBlocks.terraDirt.blockID || id == TFCBlocks.terraDirt2.blockID);
	}

	public static boolean isGrass(int id)
	{
		return (id == TFCBlocks.terraGrass.blockID || id == TFCBlocks.terraGrass2.blockID ||
				id == TFCBlocks.terraClayGrass.blockID || id == TFCBlocks.terraClayGrass2.blockID ||
				id == TFCBlocks.terraPeatGrass.blockID || id == TFCBlocks.DryGrass.blockID || id == TFCBlocks.DryGrass2.blockID);

	}
	
	public static boolean isDryGrass(int id)
	{
		return (id == TFCBlocks.DryGrass.blockID || id == TFCBlocks.DryGrass2.blockID);

	}

	public static boolean isClay(int id)
	{
		if(id == TFCBlocks.terraClay.blockID || id == TFCBlocks.terraClay2.blockID)
			return true;
		return false;
	}

	public static boolean isSand(int id)
	{
		if(id == TFCBlocks.Sand.blockID || id == TFCBlocks.Sand2.blockID)
			return true;
		return false;
	}

	public static boolean isPeat(int id)
	{
		if(id == TFCBlocks.terraPeat.blockID)
			return true;
		return false;
	}

	public static boolean isWater(int id)
	{
		if(id == Block.waterMoving.blockID || id == Block.waterMoving.blockID || id == TFCBlocks.finiteWater.blockID)
			return true;
		return false;
	}

	public static boolean isSoil(int id)
	{
		return isGrass(id) || isDirt(id) || isClay(id);
	}

	public static int getSoilMetaFromStone(int inType, int inMeta)
	{
		if(inType == TFCBlocks.terraStoneIgIn.blockID)
			return inMeta;
		else if(inType == TFCBlocks.terraStoneSed.blockID)
			return inMeta+3;
		else if(inType == TFCBlocks.terraStoneIgEx.blockID)
		{
			if(inMeta == 3)
				return 0;
			return inMeta+13;
		}
		else
			return inMeta+1;
	}

	public static int getTypeForGrassWithRain(int inMeta, float rain)
	{
		if(rain >= 500)
			return getTypeForGrass(inMeta);
		return getTypeForDryGrass(inMeta);

	}

	public static int getTypeForGrass(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.terraGrass.blockID;
		return TFCBlocks.terraGrass2.blockID;
	}

	public static int getTypeForDryGrass(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.DryGrass.blockID;
		return TFCBlocks.DryGrass2.blockID;
	}

	public static int getTypeForClayGrass(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.terraClayGrass.blockID;
		return TFCBlocks.terraClayGrass2.blockID;
	}

	public static int getTypeForDirt(int inMeta)
	{
		if(inMeta < 16)
			return TFCBlocks.terraDirt.blockID;
		return TFCBlocks.terraDirt2.blockID;
	}

	public static int getTypeForClay(int inMeta)
	{
		if(inMeta < 16)

			return TFCBlocks.terraClay.blockID;
		return TFCBlocks.terraClay2.blockID;

	}

	public static int getTypeForSand(int inMeta)
	{
		if(inMeta < 16)

			return TFCBlocks.Sand.blockID;
		return TFCBlocks.Sand2.blockID;

	}

	public static int getRockLayerFromHeight( int y)
	{
		if(y <= TerraFirmaCraft.RockLayer3Height)
			return 2;
		else if(y <= TerraFirmaCraft.RockLayer2Height)
			return 1;
		else
			return 0;
	}


}
