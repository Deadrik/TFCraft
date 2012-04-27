package net.minecraft.src.TFC_Core;

import java.util.ArrayList;
import java.util.Random;
import net.minecraft.src.*;

public class TFC_Core
{
	public enum Direction{PosX,PosZ,NegX,NegZ,None,PosXPosZ,PosXNegZ,NegXPosZ,NegXNegZ,NegY,PosY}

	public static Item[] Axes = {mod_TFC_Core.terraSedAxe,mod_TFC_Core.terraIgInAxe,mod_TFC_Core.terraIgExAxe,mod_TFC_Core.terraMMAxe,
		mod_TFC_Core.terraBismuthAxe,mod_TFC_Core.terraBismuthBronzeAxe,mod_TFC_Core.terraBlackBronzeAxe,
		mod_TFC_Core.terraBlackSteelAxe,mod_TFC_Core.terraBlueSteelAxe,mod_TFC_Core.terraBronzeAxe,mod_TFC_Core.terraCopperAxe,
		mod_TFC_Core.terraWroughtIronAxe,mod_TFC_Core.terraRedSteelAxe,mod_TFC_Core.terraRoseGoldAxe,mod_TFC_Core.terraSteelAxe,
		mod_TFC_Core.terraTinAxe,mod_TFC_Core.terraZincAxe,
		mod_TFC_Core.boneSedAxe,mod_TFC_Core.boneIgInAxe,mod_TFC_Core.boneIgExAxe,mod_TFC_Core.boneMMAxe,};

	public static Item[] Chisels = {mod_TFC_Core.BismuthChisel,mod_TFC_Core.BismuthBronzeChisel,mod_TFC_Core.BlackBronzeChisel,
		mod_TFC_Core.BlackSteelChisel,mod_TFC_Core.BlueSteelChisel,mod_TFC_Core.BronzeChisel,mod_TFC_Core.CopperChisel,
		mod_TFC_Core.WroughtIronChisel,mod_TFC_Core.RedSteelChisel,mod_TFC_Core.RoseGoldChisel,mod_TFC_Core.SteelChisel,
		mod_TFC_Core.TinChisel,mod_TFC_Core.ZincChisel};


	public static Item[] Saws = {mod_TFC_Core.BismuthSaw,mod_TFC_Core.BismuthBronzeSaw,mod_TFC_Core.BlackBronzeSaw,
		mod_TFC_Core.BlackSteelSaw,mod_TFC_Core.BlueSteelSaw,mod_TFC_Core.BronzeSaw,mod_TFC_Core.CopperSaw,
		mod_TFC_Core.WroughtIronSaw,mod_TFC_Core.RedSteelSaw,mod_TFC_Core.RoseGoldSaw,mod_TFC_Core.SteelSaw,
		mod_TFC_Core.TinSaw,mod_TFC_Core.ZincSaw};
	public static void CreateLocalization()
	{
		String[] Names = {"Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel","Brass","Bronze","Copper","Gold"
				,"Wrought Iron","Lead","Nickel","Pig Iron","Platinum","Red Steel","Rose Gold","Silver", "Steel", "Sterling Silver",
				"Tin", "Zinc"};
		/*==================================================================
		 * TFC Core Localization
		 *==================================================================*/
		ModLoader.addLocalization("tile.IgInRock.Granite.name", "Granite");
		ModLoader.addLocalization("tile.IgInRock.Diorite.name", "Diorite");
		ModLoader.addLocalization("tile.IgInRock.Gabbro.name", "Gabbro");
		ModLoader.addLocalization("tile.IgExRock.Rhyolite.name", "Rhyolite");
		ModLoader.addLocalization("tile.IgExRock.Basalt.name", "Basalt");
		ModLoader.addLocalization("tile.IgExRock.Andesite.name", "Andesite");
		ModLoader.addLocalization("tile.IgExRock.Dacite.name", "Dacite");
		ModLoader.addLocalization("tile.SedRock.Siltstone.name", "Siltstone");
		ModLoader.addLocalization("tile.SedRock.Mudstone.name", "Mudstone");
		ModLoader.addLocalization("tile.SedRock.Shale.name", "Shale");
		ModLoader.addLocalization("tile.SedRock.Claystone.name", "Claystone");
		ModLoader.addLocalization("tile.SedRock.Rock Salt.name", "Rock Salt");
		ModLoader.addLocalization("tile.SedRock.Limestone.name", "Limestone");
		ModLoader.addLocalization("tile.SedRock.Conglomerate.name", "Conglomerate");
		ModLoader.addLocalization("tile.SedRock.Dolomite.name", "Dolomite");
		ModLoader.addLocalization("tile.SedRock.Chert.name", "Chert");
		ModLoader.addLocalization("tile.SedRock.Chalk.name", "Chalk");
		ModLoader.addLocalization("tile.MMRock.Quartzite.name", "Quartzite");
		ModLoader.addLocalization("tile.MMRock.Slate.name", "Slate");
		ModLoader.addLocalization("tile.MMRock.Phyllite.name", "Phyllite");
		ModLoader.addLocalization("tile.MMRock.Schist.name", "Schist");
		ModLoader.addLocalization("tile.MMRock.Gneiss.name", "Gneiss");
		ModLoader.addLocalization("tile.MMRock.Marble.name", "Marble");
		ModLoader.addLocalization("tile.Ore.Native Copper.name", "Native Copper");
		ModLoader.addLocalization("tile.Ore.Native Gold.name", "Native Gold");
		ModLoader.addLocalization("tile.Ore.Native Platinum.name", "Native Platinum");
		ModLoader.addLocalization("tile.Ore.Hematite.name", "Hematite");
		ModLoader.addLocalization("tile.Ore.Native Silver.name", "Native Silver");
		ModLoader.addLocalization("tile.Ore.Cassiterite.name", "Cassiterite");
		ModLoader.addLocalization("tile.Ore.Galena.name", "Galena");
		ModLoader.addLocalization("tile.Ore.Bismuthinite.name", "Bismuthinite");
		ModLoader.addLocalization("tile.Ore.Garnierite.name", "Garnierite");
		ModLoader.addLocalization("tile.Ore.Malachite.name", "Malachite");
		ModLoader.addLocalization("tile.Ore.Magnetite.name", "Magnetite");
		ModLoader.addLocalization("tile.Ore.Limonite.name", "Limonite");
		ModLoader.addLocalization("tile.Ore.Sphalerite.name", "Sphalerite");
		ModLoader.addLocalization("tile.Ore.Tetrahedrite.name", "Tetrahedrite");
		ModLoader.addLocalization("tile.Ore.Bituminous Coal.name", "Bituminous Coal");
		ModLoader.addLocalization("tile.Ore.Lignite.name", "Lignite");
		ModLoader.addLocalization("tile.Ore.Kaolinite.name", "Kaolinite");
		ModLoader.addLocalization("tile.Ore.Gypsum.name", "Gypsum");
		ModLoader.addLocalization("tile.Ore.Satinspar.name", "Satinspar");
		ModLoader.addLocalization("tile.Ore.Selenite.name", "Selenite");
		ModLoader.addLocalization("tile.Ore.Graphite.name", "Graphite");
		ModLoader.addLocalization("tile.Ore.Kimberlite.name", "Kimberlite");
		ModLoader.addLocalization("tile.Ore.Petrified Wood.name", "Petrified Wood");
		ModLoader.addLocalization("tile.Ore.Sulfur.name", "Sulfur");
		ModLoader.addLocalization("tile.Ore.Jet.name", "Jet");
		ModLoader.addLocalization("tile.Ore.Microcline.name", "Microcline");
		ModLoader.addLocalization("tile.Ore.Pitchblende.name", "Pitchblende");
		ModLoader.addLocalization("tile.Ore.Cinnabar.name", "Cinnabar");
		ModLoader.addLocalization("tile.Ore.Cryolite.name", "Cryolite");
		ModLoader.addLocalization("tile.Ore.Saltpeter.name", "Saltpeter");
		ModLoader.addLocalization("tile.Ore.Serpentine.name", "Serpentine");
		ModLoader.addLocalization("tile.Ore.Sylvite.name", "Sylvite");
		ModLoader.addLocalization("tile.Ore.Borax.name", "Borax");
		ModLoader.addLocalization("tile.Ore.Olivine.name", "Olivine");
		ModLoader.addLocalization("tile.Ore.LapisLazuli.name", "Lapis Lazuli");

		ModLoader.addLocalization("item.Ore.Native Copper.name", "Native Copper");
		ModLoader.addLocalization("item.Ore.Native Gold.name", "Native Gold");
		ModLoader.addLocalization("item.Ore.Native Platinum.name", "Native Platinum");
		ModLoader.addLocalization("item.Ore.Hematite.name", "Hematite");
		ModLoader.addLocalization("item.Ore.Native Silver.name", "Native Silver");
		ModLoader.addLocalization("item.Ore.Cassiterite.name", "Cassiterite");
		ModLoader.addLocalization("item.Ore.Galena.name", "Galena");
		ModLoader.addLocalization("item.Ore.Bismuthinite.name", "Bismuthinite");
		ModLoader.addLocalization("item.Ore.Garnierite.name", "Garnierite");
		ModLoader.addLocalization("item.Ore.Malachite.name", "Malachite");
		ModLoader.addLocalization("item.Ore.Magnetite.name", "Magnetite");
		ModLoader.addLocalization("item.Ore.Limonite.name", "Limonite");
		ModLoader.addLocalization("item.Ore.Sphalerite.name", "Sphalerite");
		ModLoader.addLocalization("item.Ore.Tetrahedrite.name", "Tetrahedrite");
		ModLoader.addLocalization("item.Ore.Bituminous Coal.name", "Bituminous Coal");
		ModLoader.addLocalization("item.Ore.Lignite.name", "Lignite");
		ModLoader.addLocalization("item.Ore.Kaolinite.name", "Kaolinite");
		ModLoader.addLocalization("item.Ore.Gypsum.name", "Gypsum");
		ModLoader.addLocalization("item.Ore.Satinspar.name", "Satinspar");
		ModLoader.addLocalization("item.Ore.Selenite.name", "Selenite");
		ModLoader.addLocalization("item.Ore.Graphite.name", "Graphite");
		ModLoader.addLocalization("item.Ore.Kimberlite.name", "Kimberlite");
		ModLoader.addLocalization("item.Ore.Petrified Wood.name", "Petrified Wood");
		ModLoader.addLocalization("item.Ore.Sulfur.name", "Sulfur");
		ModLoader.addLocalization("item.Ore.Jet.name", "Jet");
		ModLoader.addLocalization("item.Ore.Microcline.name", "Microcline");
		ModLoader.addLocalization("item.Ore.Pitchblende.name", "Pitchblende");
		ModLoader.addLocalization("item.Ore.Cinnabar.name", "Cinnabar");
		ModLoader.addLocalization("item.Ore.Cryolite.name", "Cryolite");
		ModLoader.addLocalization("item.Ore.Saltpeter.name", "Saltpeter");
		ModLoader.addLocalization("item.Ore.Serpentine.name", "Serpentine");
		ModLoader.addLocalization("item.Ore.Sylvite.name", "Sylvite");
		ModLoader.addLocalization("item.Ore.Borax.name", "Borax");
		ModLoader.addLocalization("item.Ore.Olivine.name", "Olivine");
		ModLoader.addLocalization("item.Ore.LapisLazuli.name", "Lapis Lazuli");
		//Cobble
		ModLoader.addLocalization("tile.IgInRockCobble.Granite.name", "Granite Cobblestone");
		ModLoader.addLocalization("tile.IgInRockCobble.Diorite.name", "Diorite Cobblestone");
		ModLoader.addLocalization("tile.IgInRockCobble.Gabbro.name", "Gabbro Cobblestone");
		ModLoader.addLocalization("tile.IgExRockCobble.Rhyolite.name", "Rhyolite Cobblestone");
		ModLoader.addLocalization("tile.IgExRockCobble.Basalt.name", "Basalt Cobblestone");
		ModLoader.addLocalization("tile.IgExRockCobble.Andesite.name", "Andesite Cobblestone");
		ModLoader.addLocalization("tile.IgExRockCobble.Dacite.name", "Dacite Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Siltstone.name", "Siltstone Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Mudstone.name", "Mudstone Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Shale.name", "Shale Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Claystone.name", "Claystone Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Rock Salt.name", "Rock Salt Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Limestone.name", "Limestone Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Conglomerate.name", "Conglomerate Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Dolomite.name", "Dolomite Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Chert.name", "Chert Cobblestone");
		ModLoader.addLocalization("tile.SedRockCobble.Chalk.name", "Chalk Cobblestone");
		ModLoader.addLocalization("tile.MMRockCobble.Quartzite.name", "Quartzite Cobblestone");
		ModLoader.addLocalization("tile.MMRockCobble.Slate.name", "Slate Cobblestone");
		ModLoader.addLocalization("tile.MMRockCobble.Phyllite.name", "Phyllite Cobblestone");
		ModLoader.addLocalization("tile.MMRockCobble.Schist.name", "Schist Cobblestone");
		ModLoader.addLocalization("tile.MMRockCobble.Gneiss.name", "Gneiss Cobblestone");
		ModLoader.addLocalization("tile.MMRockCobble.Marble.name", "Marble Cobblestone");
		//Smooth Stone
		ModLoader.addLocalization("tile.IgInRockSmooth.Granite.name", "Smooth Granite");
		ModLoader.addLocalization("tile.IgInRockSmooth.Diorite.name", "Smooth Diorite");
		ModLoader.addLocalization("tile.IgInRockSmooth.Gabbro.name", "Smooth Gabbro");
		ModLoader.addLocalization("tile.IgExRockSmooth.Rhyolite.name", "Smooth Rhyolite");
		ModLoader.addLocalization("tile.IgExRockSmooth.Basalt.name", "Smooth Basalt");
		ModLoader.addLocalization("tile.IgExRockSmooth.Andesite.name", "Smooth Andesite");
		ModLoader.addLocalization("tile.IgExRockSmooth.Dacite.name", "Smooth Dacite");
		ModLoader.addLocalization("tile.SedRockSmooth.Siltstone.name", "Smooth Siltstone");
		ModLoader.addLocalization("tile.SedRockSmooth.Mudstone.name", "Smooth Mudstone");
		ModLoader.addLocalization("tile.SedRockSmooth.Shale.name", "Smooth Shale");
		ModLoader.addLocalization("tile.SedRockSmooth.Claystone.name", "Smooth Claystone");
		ModLoader.addLocalization("tile.SedRockSmooth.Rock Salt.name", "Smooth Rock Salt");
		ModLoader.addLocalization("tile.SedRockSmooth.Limestone.name", "Smooth Limestone");
		ModLoader.addLocalization("tile.SedRockSmooth.Conglomerate.name", "Smooth Conglomerate");
		ModLoader.addLocalization("tile.SedRockSmooth.Dolomite.name", "Smooth Dolomite");
		ModLoader.addLocalization("tile.SedRockSmooth.Chert.name", "Smooth Chert");
		ModLoader.addLocalization("tile.SedRockSmooth.Chalk.name", "Smooth Chalk");
		ModLoader.addLocalization("tile.MMRockSmooth.Quartzite.name", "Smooth Quartzite");
		ModLoader.addLocalization("tile.MMRockSmooth.Slate.name", "Smooth Slate");
		ModLoader.addLocalization("tile.MMRockSmooth.Phyllite.name", "Smooth Phyllite");
		ModLoader.addLocalization("tile.MMRockSmooth.Schist.name", "Smooth Schist");
		ModLoader.addLocalization("tile.MMRockSmooth.Gneiss.name", "Smooth Gneiss");
		ModLoader.addLocalization("tile.MMRockSmooth.Marble.name", "Smooth Marble");
		//Brick
		ModLoader.addLocalization("tile.IgInRockBrick.Granite.name", "Granite Brick");
		ModLoader.addLocalization("tile.IgInRockBrick.Diorite.name", "Diorite Brick");
		ModLoader.addLocalization("tile.IgInRockBrick.Gabbro.name", "Gabbro Brick");
		ModLoader.addLocalization("tile.IgExRockBrick.Rhyolite.name", "Rhyolite Brick");
		ModLoader.addLocalization("tile.IgExRockBrick.Basalt.name", "Basalt Brick");
		ModLoader.addLocalization("tile.IgExRockBrick.Andesite.name", "Andesite Brick");
		ModLoader.addLocalization("tile.IgExRockBrick.Dacite.name", "Dacite Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Siltstone.name", "Siltstone Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Mudstone.name", "Mudstone Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Shale.name", "Shale Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Claystone.name", "Claystone Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Rock Salt.name", "Rock Salt Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Limestone.name", "Limestone Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Conglomerate.name", "Conglomerate Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Dolomite.name", "Dolomite Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Chert.name", "Chert Brick");
		ModLoader.addLocalization("tile.SedRockBrick.Chalk.name", "Chalk Brick");
		ModLoader.addLocalization("tile.MMRockBrick.Quartzite.name", "Quartzite Brick");
		ModLoader.addLocalization("tile.MMRockBrick.Slate.name", "Slate Brick");
		ModLoader.addLocalization("tile.MMRockBrick.Phyllite.name", "Phyllite Brick");
		ModLoader.addLocalization("tile.MMRockBrick.Schist.name", "Schist Brick");
		ModLoader.addLocalization("tile.MMRockBrick.Gneiss.name", "Gneiss Brick");
		ModLoader.addLocalization("tile.MMRockBrick.Marble.name", "Marble Brick");
		//Cobble Stairs
		ModLoader.addLocalization("tile.IgInRockCobbleStairs.Granite.name", "Granite Cobblestone Stairs");
		ModLoader.addLocalization("tile.IgInRockCobbleStairs.Diorite.name", "Diorite Cobblestone Stairs");
		ModLoader.addLocalization("tile.IgInRockCobbleStairs.Gabbro.name", "Gabbro Cobblestone Stairs");
		ModLoader.addLocalization("tile.IgExRockCobbleStairs.Rhyolite.name", "Rhyolite Cobblestone Stairs");
		ModLoader.addLocalization("tile.IgExRockCobbleStairs.Basalt.name", "Basalt Cobblestone Stairs");
		ModLoader.addLocalization("tile.IgExRockCobbleStairs.Andesite.name", "Andesite Cobblestone Stairs");
		ModLoader.addLocalization("tile.IgExRockCobbleStairs.Dacite.name", "Dacite Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Siltstone.name", "Siltstone Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Mudstone.name", "Mudstone Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Shale.name", "Shale Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Claystone.name", "Claystone Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Rock Salt.name", "Rock Salt Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Limestone.name", "Limestone Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Conglomerate.name", "Conglomerate Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Dolomite.name", "Dolomite Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Chert.name", "Chert Cobblestone Stairs");
		ModLoader.addLocalization("tile.SedRockCobbleStairs.Chalk.name", "Chalk Cobblestone Stairs");
		ModLoader.addLocalization("tile.MMRockCobbleStairs.Quartzite.name", "Quartzite Cobblestone Stairs");
		ModLoader.addLocalization("tile.MMRockCobbleStairs.Slate.name", "Slate Cobblestone Stairs");
		ModLoader.addLocalization("tile.MMRockCobbleStairs.Phyllite.name", "Phyllite Cobblestone Stairs");
		ModLoader.addLocalization("tile.MMRockCobbleStairs.Schist.name", "Schist Cobblestone Stairs");
		ModLoader.addLocalization("tile.MMRockCobbleStairs.Gneiss.name", "Gneiss Cobblestone Stairs");
		ModLoader.addLocalization("tile.MMRockCobbleStairs.Marble.name", "Marble Cobblestone Stairs");

		//Dirt
		ModLoader.addLocalization("tile.dirt.Granite.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Diorite.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Gabbro.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Rhyolite.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Basalt.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Andesite.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Dacite.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Siltstone.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Mudstone.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Shale.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Claystone.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Rock Salt.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Limestone.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Conglomerate.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Dolomite.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Chert.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Chalk.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Quartzite.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Slate.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Phyllite.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Schist.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Gneiss.name", "Dirt");
		ModLoader.addLocalization("tile.dirt.Marble.name", "Dirt");
		//Clay
		ModLoader.addLocalization("tile.clay.Granite.name", "Clay");
		ModLoader.addLocalization("tile.clay.Diorite.name", "Clay");
		ModLoader.addLocalization("tile.clay.Gabbro.name", "Clay");
		ModLoader.addLocalization("tile.clay.Rhyolite.name", "Clay");
		ModLoader.addLocalization("tile.clay.Basalt.name", "Clay");
		ModLoader.addLocalization("tile.clay.Andesite.name", "Clay");
		ModLoader.addLocalization("tile.clay.Dacite.name", "Clay");
		ModLoader.addLocalization("tile.clay.Siltstone.name", "Clay");
		ModLoader.addLocalization("tile.clay.Mudstone.name", "Clay");
		ModLoader.addLocalization("tile.clay.Shale.name", "Clay");
		ModLoader.addLocalization("tile.clay.Claystone.name", "Clay");
		ModLoader.addLocalization("tile.clay.Rock Salt.name", "Clay");
		ModLoader.addLocalization("tile.clay.Limestone.name", "Clay");
		ModLoader.addLocalization("tile.clay.Conglomerate.name", "Clay");
		ModLoader.addLocalization("tile.clay.Dolomite.name", "Clay");
		ModLoader.addLocalization("tile.clay.Chert.name", "Clay");
		ModLoader.addLocalization("tile.clay.Chalk.name", "Clay");
		ModLoader.addLocalization("tile.clay.Quartzite.name", "Clay");
		ModLoader.addLocalization("tile.clay.Slate.name", "Clay");
		ModLoader.addLocalization("tile.clay.Phyllite.name", "Clay");
		ModLoader.addLocalization("tile.clay.Schist.name", "Clay");
		ModLoader.addLocalization("tile.clay.Gneiss.name", "Clay");
		ModLoader.addLocalization("tile.clay.Marble.name", "Clay");
		//Grass
		ModLoader.addLocalization("tile.Grass.Granite.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Diorite.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Gabbro.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Rhyolite.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Basalt.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Andesite.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Dacite.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Siltstone.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Mudstone.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Shale.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Claystone.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Rock Salt.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Limestone.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Conglomerate.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Dolomite.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Chert.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Chalk.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Quartzite.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Slate.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Phyllite.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Schist.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Gneiss.name", "Grass");
		ModLoader.addLocalization("tile.Grass.Marble.name", "Grass");
		//clay grass
		ModLoader.addLocalization("tile.ClayGrass.Granite.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Diorite.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Gabbro.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Rhyolite.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Basalt.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Andesite.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Dacite.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Siltstone.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Mudstone.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Shale.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Claystone.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Rock Salt.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Limestone.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Conglomerate.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Dolomite.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Chert.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Chalk.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Quartzite.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Slate.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Phyllite.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Schist.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Gneiss.name", "Clay Grass");
		ModLoader.addLocalization("tile.ClayGrass.Marble.name", "Clay Grass");
		//peat
		ModLoader.addLocalization("tile.peat.name", "Peat");
		ModLoader.addLocalization("tile.PeatGrass.name", "Peat Grass");

		//Wood
		String[] WoodNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
				"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
		for(int i= 0; i < WoodNames.length; i++)
		{
			ModLoader.addLocalization("tile.log."+WoodNames[i]+".name", WoodNames[i]);
			ModLoader.addLocalization("tile.leaves."+WoodNames[i]+".name", WoodNames[i] + " Leaves");
			ModLoader.addLocalization("tile.sapling."+WoodNames[i]+".name", WoodNames[i] + " Sapling");
			ModLoader.addLocalization("tile.wood."+WoodNames[i]+".name", WoodNames[i] + " Planks");
			ModLoader.addLocalization("item.terraWoodSupportItemV."+WoodNames[i]+".name","Vertical " + WoodNames[i] + " Support Beam");
			ModLoader.addLocalization("item.terraWoodSupportItemH."+WoodNames[i]+".name","Horizontal " + WoodNames[i] + " Support Beam");
			ModLoader.addLocalization("item.Log."+WoodNames[i]+".name", WoodNames[i]);
		}

		//Gems
		String[] GemNames = {"Ruby","Emerald","Topaz","Sapphire","Opal","Agate",
				"Jade","Garnet","Amethyst","Beryl","Jasper","Tourmaline","Diamond"};

		for(int i= 0; i < GemNames.length; i++)
		{
			ModLoader.addLocalization("item."+GemNames[i]+".Chipped.name", "Chipped "+GemNames[i]);
			ModLoader.addLocalization("item."+GemNames[i]+".Flawed.name", "Flawed "+GemNames[i]);
			ModLoader.addLocalization("item."+GemNames[i]+".Normal.name",  GemNames[i]);
			ModLoader.addLocalization("item."+GemNames[i]+".Flawless.name", "Flawless "+GemNames[i]);
			ModLoader.addLocalization("item."+GemNames[i]+".Exquisite.name", "Exquisite "+GemNames[i]);
		}

		for(int i= 0; i < Names.length; i++)
		{
			ModLoader.addLocalization("item.terra"+Names[i].replace(" ", "")+"Ingot.name", Names[i] + " Ingot");
		}
		for(int i= 0; i < Names.length; i++)
		{
			ModLoader.addLocalization("item.terra"+Names[i].replace(" ", "")+"Ingot2x.name", Names[i] + " Ingot 2x");
		}


		ModLoader.addLocalization("item.SulfurPowder.name", "Sulfur Powder");
		ModLoader.addLocalization("item.SaltpeterPowder.name", "Saltpeter");
		ModLoader.addLocalization("item.flintPaxel.name", "Flint Tool");

		ModLoader.addLocalization("item.IgIn Stone Pick.name", "Stone Pickaxe");
		ModLoader.addLocalization("item.IgIn Stone Shovel.name", "Stone Shovel");
		ModLoader.addLocalization("item.IgIn Stone Axe.name", "Stone Axe");
		ModLoader.addLocalization("item.IgIn Stone Hoe.name", "Stone Hoe");
		ModLoader.addLocalization("item.IgIn Stone Sword.name", "Stone Sword");
		ModLoader.addLocalization("item.IgIn Stone Mace.name", "Stone Mace");
		ModLoader.addLocalization("item.Sed Stone Pick.name", "Stone Pickaxe");
		ModLoader.addLocalization("item.Sed Stone Shovel.name", "Stone Shovel");
		ModLoader.addLocalization("item.Sed Stone Axe.name", "Stone Axe");
		ModLoader.addLocalization("item.Sed Stone Hoe.name", "Stone Hoe");
		ModLoader.addLocalization("item.Sed Stone Sword.name", "Stone Sword");
		ModLoader.addLocalization("item.Sed Stone Mace.name", "Stone Mace");
		ModLoader.addLocalization("item.IgEx Stone Pick.name", "Stone Pickaxe");
		ModLoader.addLocalization("item.IgEx Stone Shovel.name", "Stone Shovel");
		ModLoader.addLocalization("item.IgEx Stone Axe.name", "Stone Axe");
		ModLoader.addLocalization("item.IgEx Stone Hoe.name", "Stone Hoe");
		ModLoader.addLocalization("item.IgEx Stone Sword.name", "Stone Sword");
		ModLoader.addLocalization("item.IgEx Stone Mace.name", "Stone Mace");
		ModLoader.addLocalization("item.MM Stone Pick.name", "Stone Pickaxe");
		ModLoader.addLocalization("item.MM Stone Shovel.name", "Stone Shovel");
		ModLoader.addLocalization("item.MM Stone Axe.name", "Stone Axe");
		ModLoader.addLocalization("item.MM Stone Hoe.name", "Stone Hoe");
		ModLoader.addLocalization("item.MM Stone Sword.name", "Stone Sword");
		ModLoader.addLocalization("item.MM Stone Mace.name", "Stone Mace");

		ModLoader.addLocalization("item.Bone IgIn Stone Pick.name", "Stone Pickaxe");
		ModLoader.addLocalization("item.Bone IgIn Stone Shovel.name", "Stone Shovel");
		ModLoader.addLocalization("item.Bone IgIn Stone Axe.name", "Stone Axe");
		ModLoader.addLocalization("item.Bone IgIn Stone Hoe.name", "Stone Hoe");

		ModLoader.addLocalization("item.Bone Sed Stone Pick.name", "Stone Pickaxe");
		ModLoader.addLocalization("item.Bone Sed Stone Shovel.name", "Stone Shovel");
		ModLoader.addLocalization("item.Bone Sed Stone Axe.name", "Stone Axe");
		ModLoader.addLocalization("item.Bone Sed Stone Hoe.name", "Stone Hoe");

		ModLoader.addLocalization("item.Bone IgEx Stone Pick.name", "Stone Pickaxe");
		ModLoader.addLocalization("item.Bone IgEx Stone Shovel.name", "Stone Shovel");
		ModLoader.addLocalization("item.Bone IgEx Stone Axe.name", "Stone Axe");
		ModLoader.addLocalization("item.Bone IgEx Stone Hoe.name", "Stone Hoe");

		ModLoader.addLocalization("item.Bone MM Stone Pick.name", "Stone Pickaxe");
		ModLoader.addLocalization("item.Bone MM Stone Shovel.name", "Stone Shovel");
		ModLoader.addLocalization("item.Bone MM Stone Axe.name", "Stone Axe");
		ModLoader.addLocalization("item.Bone MM Stone Hoe.name", "Stone Hoe");

		ModLoader.addLocalization("item.Stone Hammer.name", "Stone Hammer");

		String[] ToolNames = {"Stone","Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel", "Bronze", "Copper"
				,"Wrought Iron","Red Steel","Rose Gold", "Steel", "Tin", "Zinc"};
		for(int i= 0; i < ToolNames.length; i++)
		{
			ModLoader.addLocalization("item."+ToolNames[i]+" Pick.name", ToolNames[i] + " Pickaxe");
			ModLoader.addLocalization("item."+ToolNames[i]+" Shovel.name", ToolNames[i] + " Shovel");
			ModLoader.addLocalization("item."+ToolNames[i]+" Axe.name", ToolNames[i] + " Axe");
			ModLoader.addLocalization("item."+ToolNames[i]+" Hoe.name", ToolNames[i] + " Hoe");
			ModLoader.addLocalization("item."+ToolNames[i]+" Hammer.name", ToolNames[i] + " Hammer");
			ModLoader.addLocalization("item."+ToolNames[i]+" Sword.name", ToolNames[i] + " Sword");
			ModLoader.addLocalization("item."+ToolNames[i]+" Mace.name", ToolNames[i] + " Mace");
			ModLoader.addLocalization("item."+ToolNames[i]+" Chisel.name", ToolNames[i] + " Chisel");
			ModLoader.addLocalization("item."+ToolNames[i]+" Saw.name", ToolNames[i] + " Saw");
		}

		ModLoader.addLocalization("item.javelin.name", "Javelin");

		ModLoader.addLocalization("item.SeedsWheat.name", "Wheat Seeds");

		/*==================================================================
		 * TFC Mining Localization
		 *==================================================================*/
		ModLoader.addLocalization("item.SluiceItem.name", "Sluice");

		//TFC_Mining
		ModLoader.addLocalization("item.GoldPan.GoldPan.name", "Gold Pan - Empty");
		ModLoader.addLocalization("item.GoldPan.GoldPanSand.name", "Gold Pan - Sand");
		ModLoader.addLocalization("item.GoldPan.GoldPanGravel.name", "Gold Pan - Gravel");
		ModLoader.addLocalization("item.GoldPan.GoldPanClay.name", "Gold Pan - Clay");
		ModLoader.addLocalization("item.GoldPan.GoldPanDirt.name", "Gold Pan - Dirt");
		//Prospecting picks
		ModLoader.addLocalization("item.StoneProPick.name", "Stone Prospector's Pick");
		for(int i= 0; i < ToolNames.length; i++)
		{
			ModLoader.addLocalization("item."+ToolNames[i].replace(" ", "")+"ProPick.name", ToolNames[i] + " Prospector's Pick");
		}

		/*==================================================================
		 * TFC Game Localization
		 *==================================================================*/
		ModLoader.addLocalization("item.terraFireStarter.name", "Firestarter");
		ModLoader.addLocalization("item.terraSlag.name", "Slag");
		ModLoader.addLocalization("tile.terraAnvil.name", "Anvil");
		ModLoader.addLocalization("item.terraStoneAnvilItem.name", "Stone Anvil");
		ModLoader.addLocalization("item.terraCopperAnvilItem.name", "Copper Anvil");
		ModLoader.addLocalization("item.terraBronzeAnvilItem.name", "Bronze Anvil");
		ModLoader.addLocalization("item.terraWroughtIronAnvilItem.name", "Wrought Iron Anvil");
		ModLoader.addLocalization("item.terraSteelAnvilItem.name", "Steel Anvil");
		ModLoader.addLocalization("item.terraBlackSteelAnvilItem.name", "Black Steel  Anvil");
		ModLoader.addLocalization("item.terraBlueSteelAnvilItem.name", "Blue Steel Anvil");
		ModLoader.addLocalization("item.terraRedSteelAnvilItem.name", "Red Steel Anvil");
		ModLoader.addLocalization("item.terraBellowsItem.name", "Bellows");
		ModLoader.addLocalization("tile.terraBellows.name", "Bellows");
		ModLoader.addLocalization("tile.terraScribe.name", "Scribing Table");
		ModLoader.addLocalization("item.terraInk.name", "Marking");
		ModLoader.addLocalization("item.terraClayMold.name", "Clay Mold");
		ModLoader.addLocalization("item.terraFiredClayMold.name", "Ceramic Mold");
		ModLoader.addLocalization("tile.terraForge.name", "Forge");
		ModLoader.addLocalization("tile.terraBloomery.name", "Bloomery");
		ModLoader.addLocalization("tile.terraMetallurgy.name", "Metallurgy Table");

		ModLoader.addLocalization("item.PickaxeHeadPlan.name", "Plan: Pickaxe Head");
		ModLoader.addLocalization("item.ShovelHeadPlan.name", "Plan: Shovel Head");
		ModLoader.addLocalization("item.HoeHeadPlan.name", "Plan: Hoe Head");
		ModLoader.addLocalization("item.AxeHeadPlan.name", "Plan: Axe Head");
		ModLoader.addLocalization("item.HammerHeadPlan.name", "Plan: Hammer Head");
		ModLoader.addLocalization("item.ChiselHeadPlan.name", "Plan: Chisel Head");
		ModLoader.addLocalization("item.SwordBladePlan.name", "Plan: Sword Blade");
		ModLoader.addLocalization("item.MaceHeadPlan.name", "Plan: Mace Head");
		ModLoader.addLocalization("item.SawBladePlan.name", "Plan: Saw Head");
		ModLoader.addLocalization("item.ProPickHeadPlan.name", "Plan: Prospecter's Pick Head");
		for(int i= 0; i < ToolNames.length; i++)
		{
			ModLoader.addLocalization("item."+ToolNames[i]+" Pickaxe Head.name", ToolNames[i] + " Pickaxe Head");
			ModLoader.addLocalization("item."+ToolNames[i]+" Shovel Head.name", ToolNames[i] + " Shovel Head");
			ModLoader.addLocalization("item."+ToolNames[i]+" Axe Head.name", ToolNames[i] + " Axe Head");
			ModLoader.addLocalization("item."+ToolNames[i]+" Hoe Head.name", ToolNames[i] + " Hoe Head");
			ModLoader.addLocalization("item."+ToolNames[i]+" Hammer Head.name", ToolNames[i] + " Hammer Head");
			ModLoader.addLocalization("item."+ToolNames[i]+" Sword Blade.name", ToolNames[i] + " Sword Blade");
			ModLoader.addLocalization("item."+ToolNames[i]+" Mace Head.name", ToolNames[i] + " Mace Head");
			ModLoader.addLocalization("item."+ToolNames[i]+" Chisel Head.name", ToolNames[i] + " Chisel Head");
			ModLoader.addLocalization("item."+ToolNames[i]+" Saw Blade.name", ToolNames[i] + " Saw Blade");
			ModLoader.addLocalization("item."+ToolNames[i]+" ProPick Head.name", ToolNames[i] + " Prospector's Pick Head");
		}

		//meltedmetal
		for(int i= 0; i < Names.length; i++)
		{
			ModLoader.addLocalization("item.terraMelted"+Names[i].replace(" ", "")+".name", "Unshaped "+Names[i]);
		}

		ModLoader.addLocalization("item.terraMeltedHCBlackSteel.name", "Unshaped High Carbon Black Steel");
		ModLoader.addLocalization("item.terraMeltedHCBlueSteel.name", "Unshaped High Carbon Blue Steel");
		ModLoader.addLocalization("item.terraMeltedHCRedSteel.name", "Unshaped High Carbon Red Steel");
		ModLoader.addLocalization("item.terraMeltedHCSteel.name", "Unshaped High Carbon Steel");
		ModLoader.addLocalization("item.terraMeltedWeakSteel.name", "Unshaped Weak Steel");
		ModLoader.addLocalization("item.terraMeltedWeakBlueSteel.name", "Unshaped Weak Blue Steel");
		ModLoader.addLocalization("item.terraMeltedWeakRedSteel.name", "Unshaped Weak Red Steel");

		ModLoader.addLocalization("item.terraHCBlackSteelIngot.name", "High Carbon Black Steel Ingot");
		ModLoader.addLocalization("item.terraHCBlueSteelIngot.name", "High Carbon Blue Steel Ingot");
		ModLoader.addLocalization("item.terraHCRedSteelIngot.name", "High Carbon Red Steel Ingot");
		ModLoader.addLocalization("item.terraHCSteelIngot.name", "High Carbon Steel Ingot");
		ModLoader.addLocalization("item.terraWeakSteelIngot.name", "Weak Steel Ingot");
		ModLoader.addLocalization("item.terraWeakBlueSteelIngot.name", "Weak Blue Steel Ingot");
		ModLoader.addLocalization("item.terraWeakRedSteelIngot.name", "Weak Red Steel Ingot");

		ModLoader.addLocalization("item.coke.name", "Coke");
		ModLoader.addLocalization("item.flux.name", "Flux");

	}
	private static void createOre(int i, int j, int[] Layers, int rarity, int veinSize, 
			int veinAmount, int height, int diameter, int vDensity, int hDensity,World world, Random rand, int chunkX, int chunkZ)
	{
		for(int n = 0; n < Layers.length/2;)
		{
			new WorldGenMinableTFC(i, j,Layers[n],Layers[n+1],rarity,veinSize,veinAmount,height,diameter,vDensity,hDensity).generate(
					world, rand, chunkX, chunkZ);
			n+=2;
		}
	}
	public static void CreateTreeLimb(World world, int i, int j, int k, int meta, Direction dir, Random R)
	{
		if(dir == Direction.PosX)
		{
			if(world.getBlockId(i+1, j, k) == Block.leaves.blockID)
			{
				world.setBlockAndMetadata(i+1, j, k, Block.wood.blockID, meta);
				SurroundWithLeaves(world,i+1,j,k,meta,R);
				if(world.getBlockId(i+2, j, k) == Block.leaves.blockID)
				{
					world.setBlockAndMetadata(i+2, j, k, Block.wood.blockID, meta);
					SurroundWithLeaves(world,i+2,j,k,meta,R);
					if(world.getBlockId(i+3, j, k) == Block.leaves.blockID && R.nextInt(10) == 0)
					{
						world.setBlockAndMetadata(i+3, j, k, Block.wood.blockID, meta);
						SurroundWithLeaves(world,i+3,j,k,meta,R);
						if(world.getBlockId(i+4, j-1, k) != Block.leaves.blockID && R.nextInt(10) == 0)
						{
							world.setBlockAndMetadata(i+4, j-1, k, Block.wood.blockID, meta);
							SurroundWithLeaves(world,i+4,j-1,k,meta,R);
						}
					}
				}
			}
		}
		if(dir == Direction.NegX)
		{
			if(world.getBlockId(i-1, j, k) == Block.leaves.blockID)
			{
				world.setBlockAndMetadata(i-1, j, k, Block.wood.blockID, meta);
				SurroundWithLeaves(world,i-1,j,k,meta,R);
				if(world.getBlockId(i-2, j, k) == Block.leaves.blockID)
				{
					world.setBlockAndMetadata(i-2, j, k, Block.wood.blockID, meta);
					SurroundWithLeaves(world,i-2,j,k,meta,R);
					if(world.getBlockId(i-3, j, k) == Block.leaves.blockID && R.nextInt(10) == 0)
					{
						world.setBlockAndMetadata(i-3, j, k, Block.wood.blockID, meta);
						SurroundWithLeaves(world,i-3,j,k,meta,R);
						if(world.getBlockId(i-4, j-1, k) == Block.leaves.blockID && R.nextInt(10) == 0)
						{
							world.setBlockAndMetadata(i-4, j-1, k, Block.wood.blockID, meta);
							SurroundWithLeaves(world,i-4,j-1,k,meta,R);
						}
					}
				}
			}
		}
		if(dir == Direction.PosZ)
		{
			if(world.getBlockId(i, j, k+1) == Block.leaves.blockID)
			{
				world.setBlockAndMetadata(i, j, k+1, Block.wood.blockID, meta);
				SurroundWithLeaves(world,i,j,k+1,meta,R);
				if(world.getBlockId(i, j, k+2) == Block.leaves.blockID)
				{
					world.setBlockAndMetadata(i, j, k+2, Block.wood.blockID, meta);
					SurroundWithLeaves(world,i,j,k+2,meta,R);
					if(world.getBlockId(i, j, k+3) == Block.leaves.blockID && R.nextInt(10) == 0)
					{
						world.setBlockAndMetadata(i, j, k+3, Block.wood.blockID, meta);
						SurroundWithLeaves(world,i,j,k+3,meta,R);
						if(world.getBlockId(i, j-1, k+4) == Block.leaves.blockID && R.nextInt(10) == 0)
						{
							world.setBlockAndMetadata(i, j-1, k+4, Block.wood.blockID, meta);
							SurroundWithLeaves(world,i,j-1,k+4,meta,R);
						}
					}
				}
			}


		}
		if(dir == Direction.NegZ)
		{
			if(world.getBlockId(i, j, k-1) == Block.leaves.blockID)
			{
				world.setBlockAndMetadata(i, j, k-1, Block.wood.blockID, meta);
				SurroundWithLeaves(world,i,j,k-1,meta,R);
				if(world.getBlockId(i, j, k-2) == Block.leaves.blockID)
				{
					world.setBlockAndMetadata(i, j, k-2, Block.wood.blockID, meta);
					SurroundWithLeaves(world,i,j,k-2,meta,R);
					if(world.getBlockId(i, j, k-3) == Block.leaves.blockID && R.nextInt(10) == 0)
					{
						world.setBlockAndMetadata(i, j, k-3, Block.wood.blockID, meta);
						SurroundWithLeaves(world,i,j,k-3,meta,R);
						if(world.getBlockId(i, j-1, k-4) == Block.leaves.blockID && R.nextInt(10) == 0)
						{
							world.setBlockAndMetadata(i, j-1, k-4, Block.wood.blockID, meta);
							SurroundWithLeaves(world,i,j-1,k-4,meta,R);
						}
					}
				}
			}	
		}
	}

	public static void Generate(World world, Random rand, int chunkX, int chunkZ)
	{
		//============Copper
		createOre(mod_TFC_Core.terraOre.blockID, 7,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,Block.sandStone.blockID,-1},//IgEx and Sandstone, veins
				/*rarity*/10,/*veinSize*/20,/*veinAmt*/25,/*height*/128,/*diameter*/40,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Gold
		createOre(mod_TFC_Core.terraOre.blockID, 8,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneIgIn.blockID,-1},//Ig veins
				/*rarity*/20,/*veinSize*/10,/*veinAmt*/15,/*height*/128,/*diameter*/15,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Hematite
		createOre(mod_TFC_Core.terraOre.blockID, 10,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1},//IgEx veins
				/*rarity*/10,/*veinSize*/20,/*veinAmt*/12,/*height*/128,/*diameter*/40,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Silver
		createOre(mod_TFC_Core.terraOre.blockID, 11,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0,mod_TFC_Core.terraStoneMM.blockID,4},//granite and gneiss, veins
				/*rarity*/10,/*veinSize*/10,/*veinAmt*/15,/*height*/128,/*diameter*/15,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Cassiterite
		createOre(mod_TFC_Core.terraOre.blockID, 12,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//Granite Veins
				/*rarity*/10,/*veinSize*/10,/*veinAmt*/15,/*height*/128,/*diameter*/15,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Galena
		createOre(mod_TFC_Core.terraOre.blockID, 13,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneMM.blockID,-1,
				mod_TFC_Core.terraStoneIgIn.blockID,0,mod_TFC_Core.terraStoneSed.blockID,5},//igex, mm, granite, limestone as veins
				/*rarity*/15,/*veinSize*/10,/*veinAmt*/25,/*height*/128,/*diameter*/30,/*vDensity*/60,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Bismuthinite
		createOre(mod_TFC_Core.terraOre.blockID, 14,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//Granite Veins
				/*rarity*/8,/*veinSize*/8,/*veinAmt*/15,/*height*/128,/*diameter*/60,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Garnierite
		createOre(mod_TFC_Core.terraOre.blockID, 15,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,2},//Gabbro Veins
				/*rarity*/18,/*veinSize*/10,/*veinAmt*/25,/*height*/128,/*diameter*/40,/*vDensity*/60,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Malachite
		createOre(mod_TFC_Core.terraOre.blockID, 0,new int[]{mod_TFC_Core.terraStoneSed.blockID,5,mod_TFC_Core.terraStoneMM.blockID,5},//limestone and marble veins
				/*rarity*/5,/*veinSize*/10,/*veinAmt*/15,/*height*/128,/*diameter*/20,/*vDensity*/30,/*hDensity*/20,         world, rand, chunkX, chunkZ);

		//============Magnetite
		createOre(mod_TFC_Core.terraOre.blockID, 1,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, Large Cluster
				/*rarity*/25,/*veinSize*/30,/*veinAmt*/6,/*height*/128,/*diameter*/40,/*vDensity*/80,/*hDensity*/80,         world, rand, chunkX, chunkZ);

		//============Limonite
		createOre(mod_TFC_Core.terraOre.blockID, 2,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, Large Cluster
				/*rarity*/25,/*veinSize*/10,/*veinAmt*/20,/*height*/128,/*diameter*/25,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Sphalerite
		createOre(mod_TFC_Core.terraOre.blockID, 3,new int[]{mod_TFC_Core.terraStoneMM.blockID,-1},//mm, veins
				/*rarity*/10,/*veinSize*/10,/*veinAmt*/8,/*height*/128,/*diameter*/15,/*vDensity*/60,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Tetrahedrite
		createOre(mod_TFC_Core.terraOre.blockID, 4,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneMM.blockID,-1,
				mod_TFC_Core.terraStoneIgIn.blockID,-1,mod_TFC_Core.terraStoneSed.blockID,-1},//everything, veins
				/*rarity*/40,/*veinSize*/25,/*veinAmt*/15,/*height*/128,/*diameter*/40,/*vDensity*/30,/*hDensity*/30,         world, rand, chunkX, chunkZ);

		//============Bituminous Coal
		createOre(mod_TFC_Core.terraOre.blockID, 5,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, veins
				/*rarity*/18,/*veinSize*/28,/*veinAmt*/6,/*height*/128,/*diameter*/22,/*vDensity*/60,/*hDensity*/70,         world, rand, chunkX, chunkZ);

		//============Lignite
		createOre(mod_TFC_Core.terraOre.blockID, 6,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, veins
				/*rarity*/18,/*veinSize*/28,/*veinAmt*/6,/*height*/128,/*diameter*/25,/*vDensity*/70,/*hDensity*/30,         world, rand, chunkX, chunkZ);

		//============Kaolinite
		createOre(mod_TFC_Core.terraOre2.blockID, 7,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, large clusters
				/*rarity*/30,/*veinSize*/60,/*veinAmt*/2,/*height*/128,/*diameter*/40,/*vDensity*/50,/*hDensity*/90,         world, rand, chunkX, chunkZ);

		//============Gypsum
		createOre(mod_TFC_Core.terraOre2.blockID, 8,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sedimentary, large clusters
				/*rarity*/30,/*veinSize*/60,/*veinAmt*/2,/*height*/128,/*diameter*/40,/*vDensity*/50,/*hDensity*/90,         world, rand, chunkX, chunkZ);

		//============Satinspar
		createOre(mod_TFC_Core.terraOre2.blockID, 9,new int[]{mod_TFC_Core.terraOre2.blockID,8},//gypsum, small clusters
				/*rarity*/4,/*veinSize*/6,/*veinAmt*/10,/*height*/128,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Selenite
		createOre(mod_TFC_Core.terraOre2.blockID, 10,new int[]{mod_TFC_Core.terraOre2.blockID,8},//gypsum, small clusters
				/*rarity*/4,/*veinSize*/6,/*veinAmt*/10,/*height*/128,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Graphite
		createOre(mod_TFC_Core.terraOre2.blockID, 11,new int[]{mod_TFC_Core.terraStoneMM.blockID,4,mod_TFC_Core.terraStoneMM.blockID,0,
				mod_TFC_Core.terraStoneMM.blockID,5, mod_TFC_Core.terraStoneMM.blockID,3},//gneiss, quartzite, marble, schist, small clusters
				/*rarity*/4,/*veinSize*/6,/*veinAmt*/14,/*height*/128,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Kimberlite
		createOre(mod_TFC_Core.terraOre2.blockID, 12,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,2},//Gabbro, large clusters
				/*rarity*/30,/*veinSize*/40,/*veinAmt*/10,/*height*/128,/*diameter*/40,/*vDensity*/40,/*hDensity*/90,         world, rand, chunkX, chunkZ);

		//============Petrified Wood
		createOre(mod_TFC_Core.terraOre2.blockID, 13,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, small clusters 
				/*rarity*/40,/*veinSize*/10,/*veinAmt*/5,/*height*/60,/*diameter*/20,/*vDensity*/80,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Sulfur
		//		createOre(mod_TFCraft.terraOre.blockID, 14,new int[]{mod_TFCraft.terraStoneIgEx.blockID,-1,mod_TFCraft.terraOre2.blockID,8},//igex, gypsum small clusters
		//				/*rarity*/4,/*veinSize*/6,/*veinAmt*/10,/*height*/128,/*diameter*/40,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Jet
		createOre(mod_TFC_Core.terraOre2.blockID, 15,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//Sedimentary, med clusters 
				/*rarity*/100,/*veinSize*/30,/*veinAmt*/10,/*height*/128,/*diameter*/40,/*vDensity*/60,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Microcline
		createOre(mod_TFC_Core.terraOre2.blockID, 0,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, large clusters 
				/*rarity*/15,/*veinSize*/64,/*veinAmt*/2,/*height*/128,/*diameter*/20,/*vDensity*/80,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Pitchblende
		createOre(mod_TFC_Core.terraOre2.blockID, 1,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, small clusters 
				/*rarity*/20,/*veinSize*/4,/*veinAmt*/10,/*height*/128,/*diameter*/20,/*vDensity*/80,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Cinnabar
		createOre(mod_TFC_Core.terraOre2.blockID, 2,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneSed.blockID,2,
				mod_TFC_Core.terraStoneMM.blockID,0},//igex, shale, quartzite small clusters
				/*rarity*/5,/*veinSize*/6,/*veinAmt*/30,/*height*/128,/*diameter*/20,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Cryolite
		createOre(mod_TFC_Core.terraOre2.blockID, 3,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,0},//granite, small clusters 
				/*rarity*/20,/*veinSize*/4,/*veinAmt*/10,/*height*/128,/*diameter*/20,/*vDensity*/80,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Saltpeter
		createOre(mod_TFC_Core.terraOre2.blockID, 4,new int[]{mod_TFC_Core.terraStoneSed.blockID,-1},//sed, small clusters 
				/*rarity*/20,/*veinSize*/4,/*veinAmt*/10,/*height*/128,/*diameter*/20,/*vDensity*/80,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Olivine(Out of Order) must come before serpentine
		createOre(mod_TFC_Core.terraOre3.blockID, 8,new int[]{mod_TFC_Core.terraStoneIgIn.blockID,2},//gabbro, large clusters 
				/*rarity*/10,/*veinSize*/30,/*veinAmt*/4,/*height*/128,/*diameter*/20,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Serpentine
		createOre(mod_TFC_Core.terraOre2.blockID, 5,new int[]{mod_TFC_Core.terraOre3.blockID,8},//Olivine, small clusters 
				/*rarity*/10,/*veinSize*/5,/*veinAmt*/8,/*height*/128,/*diameter*/20,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Sylvite
		createOre(mod_TFC_Core.terraOre2.blockID, 6,new int[]{mod_TFC_Core.terraStoneSed.blockID,4},//Rock Salt, large clusters 
				/*rarity*/10,/*veinSize*/40,/*veinAmt*/4,/*height*/128,/*diameter*/20,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);

		//============Borax
		createOre(mod_TFC_Core.terraOre3.blockID, 7,new int[]{mod_TFC_Core.terraStoneSed.blockID,4},//Rock Salt, large clusters 
				/*rarity*/10,/*veinSize*/30,/*veinAmt*/4,/*height*/128,/*diameter*/20,/*vDensity*/50,/*hDensity*/60,         world, rand, chunkX, chunkZ);
		createOre(mod_TFC_Core.terraOre3.blockID, 7,new int[]{mod_TFC_Core.terraOre2.blockID,8},//Gypsum, small clusters 
				/*rarity*/10,/*veinSize*/12,/*veinAmt*/8,/*height*/128,/*diameter*/20,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);
		//============Lapis Lazuli
		createOre(mod_TFC_Core.terraOre3.blockID, 9,new int[]{mod_TFC_Core.terraStoneMM.blockID,5},//Marble, small clusters 
				/*rarity*/14,/*veinSize*/8,/*veinAmt*/8,/*height*/128,/*diameter*/30,/*vDensity*/40,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Platinum -- (out of order) must follow magnetite and olivine
		createOre(mod_TFC_Core.terraOre.blockID, 9,new int[]{mod_TFC_Core.terraOre.blockID,1,mod_TFC_Core.terraOre3.blockID,8},//magnetite, veins
				/*rarity*/8,/*veinSize*/5,/*veinAmt*/10,/*height*/128,/*diameter*/15,/*vDensity*/60,/*hDensity*/40,         world, rand, chunkX, chunkZ);

		//============Gravel
		createOre(Block.gravel.blockID, 0,new int[]{mod_TFC_Core.terraStoneIgEx.blockID,-1,mod_TFC_Core.terraStoneMM.blockID,-1,
				mod_TFC_Core.terraStoneIgIn.blockID,-1,mod_TFC_Core.terraStoneSed.blockID,-1,mod_TFC_Core.terraDirt.blockID,-1,
				mod_TFC_Core.terraDirt2.blockID,-1,mod_TFC_Core.terraGrass.blockID,-1,mod_TFC_Core.terraGrass2.blockID,-1},//Everywhere, Clusters
				/*rarity*/2,/*veinSize*/40,/*veinAmt*/5,/*height*/128,/*diameter*/40,/*vDensity*/80,/*hDensity*/60,         world, rand, chunkX, chunkZ);
	}

	public static void GenerateLooseRocks(World currentWorld, Random randomGenerator, int chunk_X, int chunk_Z)
	{
		BiomeGenBase biome = currentWorld.getBiomeGenForCoords(chunk_X*16, chunk_Z*16);


		for (int var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).looseRocksPerChunk; var2++)
		{
			int var7 = chunk_X + randomGenerator.nextInt(16) + 8;
			int var3 = chunk_Z + randomGenerator.nextInt(16) + 8;

			new WorldGenLooseRocks().generate(currentWorld, randomGenerator, var7, currentWorld.getTopSolidOrLiquidBlock(var7, var3)-1, var3);

		}

	}

	public static void GeneratePits(World world, Random rand, int chunkX, int chunkZ)
	{
		for (int var1 = 0; var1 < 1; ++var1)
		{
			int var2 = chunkX + rand.nextInt(16) + 8;
			int var3 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenClayPit(16, world.getBiomeGenForCoords(var2, var3)).generate(world, rand, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
		}

		for (int var1 = 0; var1 < 1; ++var1)
		{
			int var2 = chunkX + rand.nextInt(16) + 8;
			int var3 = chunkZ + rand.nextInt(16) + 8;
			new WorldGenPeatPit(24, world.getBiomeGenForCoords(var2, var3)).generate(world, rand, var2, world.getTopSolidOrLiquidBlock(var2, var3), var3);
		}
	}

	public static void GeneratePlants(World world, Random rand, int chunk_X, int chunk_Z)
	{
		BiomeGenBase biome = world.getBiomeGenForCoords(chunk_X*16, chunk_Z*16);
		WorldGenCustomFlowers plantYellowGen = new WorldGenCustomFlowers(Block.plantYellow.blockID);
		WorldGenCustomFlowers plantRedGen = new WorldGenCustomFlowers(Block.plantRed.blockID);
		WorldGenCustomFlowers mushroomBrownGen = new WorldGenCustomFlowers(Block.mushroomBrown.blockID);
		WorldGenCustomFlowers mushroomRedGen = new WorldGenCustomFlowers(Block.mushroomRed.blockID);

		int var2;
		int var3;
		int var4;
		int var7;

		for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).flowersPerChunk; ++var2)
		{
			var3 = chunk_X + rand.nextInt(16) + 8;
			var4 = rand.nextInt(128);
			var7 = chunk_Z + rand.nextInt(16) + 8;

			plantYellowGen.generate(world, rand, var3, var4, var7);

			if (rand.nextInt(4) == 0)
			{
				var3 = chunk_X + rand.nextInt(16) + 8;
				var4 = rand.nextInt(128);
				var7 = chunk_Z + rand.nextInt(16) + 8;
				plantRedGen.generate(world, rand, var3, var4, var7);
			}
		}

		for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).grassPerChunk; ++var2)
		{
			var3 = chunk_X + rand.nextInt(16) + 8;
			var4 = rand.nextInt(128);
			var7 = chunk_Z + rand.nextInt(16) + 8;
			WorldGenerator var6 = new WorldGenCustomTallGrass(Block.tallGrass.blockID, 1);
			var6.generate(world, rand, var3, var4, var7);
		}

		for (var2 = 0; var2 < ((BiomeDecoratorTFC)biome.biomeDecorator).mushroomsPerChunk; ++var2)
		{
			if (rand.nextInt(4) == 0)
			{
				var3 = chunk_X + rand.nextInt(16) + 8;
				var4 = chunk_Z + rand.nextInt(16) + 8;
				var7 = world.getHeightValue(var3, var4);
				mushroomBrownGen.generate(world, rand, var3, var7, var4);
			}

			if (rand.nextInt(8) == 0)
			{
				var3 = chunk_X + rand.nextInt(16) + 8;
				var4 = chunk_Z + rand.nextInt(16) + 8;
				var7 = rand.nextInt(128);
				mushroomRedGen.generate(world, rand, var3, var7, var4);
			}
		}

		if (rand.nextInt(4) == 0)
		{
			var2 = chunk_X + rand.nextInt(16) + 8;
			var3 = rand.nextInt(128);
			var4 = chunk_Z + rand.nextInt(16) + 8;
			mushroomBrownGen.generate(world, rand, var2, var3, var4);
		}

		if (rand.nextInt(8) == 0)
		{
			var2 = chunk_X + rand.nextInt(16) + 8;
			var3 = rand.nextInt(128);
			var4 = chunk_Z + rand.nextInt(16) + 8;
			mushroomRedGen.generate(world, rand, var2, var3, var4);
		}
	}

	static Boolean isBlockAboveSolid(IBlockAccess blockAccess, int i, int j, int k)
	{
		if(((World) blockAccess).isBlockOpaqueCube(i, j+1, k)) {
			return true;
		}

		return false;
	}

	public static ItemStack RandomGem(Random random, int rockType)
	{
		ItemStack is = null;
		if(random.nextInt(250) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,0));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,0));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

		}
		else if(random.nextInt(625) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,1));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,1));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(1250) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,2));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,2));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(1875) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,3));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,3));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];
		}
		else if(random.nextInt(2500) == 0)
		{
			ArrayList items = new ArrayList<ItemStack>();
			items.add(new ItemStack(mod_TFC_Core.terraGemAgate,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemAmethyst,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemBeryl,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemEmerald,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemGarnet,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemJade,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemJasper,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemOpal,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemRuby,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemSapphire,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemTourmaline,1,4));
			items.add(new ItemStack(mod_TFC_Core.terraGemTopaz,1,4));

			is = (ItemStack)items.toArray()[random.nextInt(items.toArray().length)];

		}
		return is;
	}

	public static void RegisterRecipes()
	{
		RegisterTools();


		/** Axe Recipes */
		for(int i = 0; i < 16; i++)
		{
			for(int j = 0; j < Axes.length; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(Block.planks, 1, i), new Object[] {new ItemStack(mod_TFC_Core.Logs, 1, i), new ItemStack(Axes[j], 1, -1)});
				ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWoodSupportItemV, 4, i), new Object[] { "A2"," 2", Character.valueOf('2'), new ItemStack(mod_TFC_Core.Logs,1,i), Character.valueOf('A'), new ItemStack(Axes[j], 1, -1)});
				ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWoodSupportItemH, 4, i), new Object[] { "A ","22", Character.valueOf('2'), new ItemStack(mod_TFC_Core.Logs,1,i), Character.valueOf('A'), new ItemStack(Axes[j], 1, -1)});
			}
			for(int j = 0; j < Saws.length; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(Block.planks, 3, i), new Object[] {new ItemStack(mod_TFC_Core.Logs, 1, i), new ItemStack(Saws[j], 1, -1)});
			}
			ModLoader.addShapelessRecipe(new ItemStack(Block.planks, 1, i), new Object[] {new ItemStack(mod_TFC_Core.Logs, 1, i), new ItemStack(mod_TFC_Core.FlintPaxel, 1, -1)});
		}

		//Red Stone		
		ModLoader.addRecipe(new ItemStack(Item.redstone, 8), new Object[] { 
			"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.OreChunk, 1, 28)});
		//Lapis Lazuli	
		ModLoader.addShapelessRecipe(new ItemStack(Item.dyePowder, 4,4), new Object[] {new ItemStack(mod_TFC_Core.OreChunk, 1, 35)});


		for(int i = 0; i < 13; i++)
		{			
			for(int j = 0; j < 3; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneIgInBrick,1,j), 
						new Object[] {new ItemStack(mod_TFC_Core.terraStoneIgInCobble,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 0; j < 13; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneSedBrick,1,j), 
						new Object[] {new ItemStack(mod_TFC_Core.terraStoneSedCobble,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 13; j < 17; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneIgExBrick,1,j), 
						new Object[] {new ItemStack(mod_TFC_Core.terraStoneIgExCobble,1,j),new ItemStack(Chisels[i],1,-1)});
			}

			for(int j = 17; j < 23; j++)
			{
				ModLoader.addShapelessRecipe(new ItemStack(mod_TFC_Core.terraStoneMMBrick,1,j), 
						new Object[] {new ItemStack(mod_TFC_Core.terraStoneMMCobble,1,j),new ItemStack(Chisels[i],1,-1)});
			}
		}

		if(TFCSettings.enableVanillaDiamondRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.diamond, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.terraGemDiamond,1,2)});
			ModLoader.addRecipe(new ItemStack(Item.diamond, 2), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.terraGemDiamond,1,3)});
			ModLoader.addRecipe(new ItemStack(Item.diamond, 3), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.terraGemDiamond,1,4)});
		}
		if(TFCSettings.enableVanillaIronRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.ingotIron, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.terraWroughtIronIngot,1)});

		}
		if(TFCSettings.enableVanillaGoldRecipe == true)
		{
			ModLoader.addRecipe(new ItemStack(Item.ingotGold, 1), new Object[] {"1", Character.valueOf('1'),new ItemStack(mod_TFC_Core.terraGoldIngot,1)});
		}
		if(TFCSettings.enableVanillaRecipes == true)
		{
			//Terrastone to Cobblestone
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneSedCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneIgInCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneIgExCobble});
			ModLoader.addRecipe(new ItemStack(Block.cobblestone, 1), new Object[] {"1", Character.valueOf('1'),mod_TFC_Core.terraStoneMMCobble});
		}

		if(TFCSettings.enableVanillaFurnaceRecipes  == true)
		{
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgIn.blockID, new ItemStack(Block.stone));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgEx.blockID, new ItemStack(Block.stone));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneSed.blockID,  new ItemStack(Block.stone));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneMM.blockID,   new ItemStack(Block.stone));

			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgInCobble.blockID, new ItemStack(mod_TFC_Core.terraStoneIgIn));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneIgExCobble.blockID, new ItemStack(mod_TFC_Core.terraStoneIgEx));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneSedCobble.blockID,  new ItemStack(mod_TFC_Core.terraStoneSed));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraStoneMMCobble.blockID,   new ItemStack(mod_TFC_Core.terraStoneMM));

			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 23,new ItemStack(mod_TFC_Core.terraCopperIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 32,new ItemStack(mod_TFC_Core.terraCopperIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 36,new ItemStack(mod_TFC_Core.terraCopperIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 35,new ItemStack(mod_TFC_Core.terraZincIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 34,new ItemStack(mod_TFC_Core.terraWroughtIronIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 33,new ItemStack(mod_TFC_Core.terraWroughtIronIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 26,new ItemStack(mod_TFC_Core.terraWroughtIronIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 25,new ItemStack(mod_TFC_Core.terraPlatinumIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 24,new ItemStack(mod_TFC_Core.terraGoldIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 31,new ItemStack(mod_TFC_Core.terraNickelIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 29,new ItemStack(mod_TFC_Core.terraSilverIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 27,new ItemStack(mod_TFC_Core.terraSilverIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 30,new ItemStack(mod_TFC_Core.terraBismuthIngot));
			FurnaceRecipes.smelting().addSmelting(mod_TFC_Core.terraOre.blockID, 28,new ItemStack(mod_TFC_Core.terraTinIngot));
		}
	}

	@SuppressWarnings("unused")
	public static void RegisterTools()
	{
		Item[] Ingots = {mod_TFC_Core.terraBismuthIngot, mod_TFC_Core.terraBismuthBronzeIngot,mod_TFC_Core.terraBlackBronzeIngot,
				mod_TFC_Core.terraBlackSteelIngot,mod_TFC_Core.terraBlueSteelIngot,mod_TFC_Core.terraBrassIngot,mod_TFC_Core.terraBronzeIngot,
				mod_TFC_Core.terraBronzeIngot,mod_TFC_Core.terraCopperIngot,mod_TFC_Core.terraGoldIngot,mod_TFC_Core.terraWroughtIronIngot,mod_TFC_Core.terraLeadIngot
				,mod_TFC_Core.terraNickelIngot,mod_TFC_Core.terraPigIronIngot,mod_TFC_Core.terraPlatinumIngot,mod_TFC_Core.terraRedSteelIngot,
				mod_TFC_Core.terraRoseGoldIngot,mod_TFC_Core.terraSilverIngot,mod_TFC_Core.terraSteelIngot,mod_TFC_Core.terraSterlingSilverIngot
				,mod_TFC_Core.terraTinIngot,mod_TFC_Core.terraZincIngot};

		if(TFCSettings.enableVanillaRecipes == true)
		{
			//Conversion to vanilla tools for recipes in other mods
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgInPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgExPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraSedPick});
			ModLoader.addRecipe(new ItemStack(Item.pickaxeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraMMPick});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgInShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgExShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraSedShovel});
			ModLoader.addRecipe(new ItemStack(Item.shovelStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraMMShovel});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgInHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgExHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraSedHoe});
			ModLoader.addRecipe(new ItemStack(Item.hoeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraMMHoe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgInAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraIgExAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraSedAxe});
			ModLoader.addRecipe(new ItemStack(Item.axeStone, 1, 0), new Object[] { "2","2", Character.valueOf('2'), mod_TFC_Core.terraMMAxe});

			for (int i = 0; i < 22 && false; i++)
			{
				Object[] pickaxeRecipe = new Object[] { "111"," 2 "," 2 ", Character.valueOf('1'), Ingots[i],Character.valueOf('2'), Item.stick};
				Object[] shovelRecipe = new Object[] { "1 ","2 ","2 ", Character.valueOf('1'), Ingots[i],Character.valueOf('2'), Item.stick};
				Object[] hoeRecipe = new Object[] { "11"," 2"," 2", Character.valueOf('1'), Ingots[i],Character.valueOf('2'), Item.stick};
				Object[] axeRecipe = new Object[] { "11 ","12 "," 2 ", Character.valueOf('1'), Ingots[i],Character.valueOf('2'), Item.stick};

				if (Ingots[i].getItemName().equals("ingotBismuth"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBismuthBronze"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzePick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBismuthBronzeAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBlackBronze"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzePick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackBronzeAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBlackSteel"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlackSteelAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBlueSteel"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBlueSteelAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotBronze"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzePick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraBronzeAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotCopper"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraCopperAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotGold"))
				{

				}
				if (Ingots[i].getItemName().equals("terraWroughtIronIngot"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraWroughtIronAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotNickel"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotPigIron"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotPlatinum"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotRedSteel"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRedSteelAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotRoseGold"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraRoseGoldAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotSilver"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotSteel"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSteelAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotSterlingSilver"))
				{

				}
				if (Ingots[i].getItemName().equals("ingotTin"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraTinAxe, 1, 0), axeRecipe);
				}
				if (Ingots[i].getItemName().equals("ingotZinc"))
				{
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincPick, 1, 0), pickaxeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincShovel, 1, 0), shovelRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincHoe, 1, 0), hoeRecipe);
					ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraZincAxe, 1, 0), axeRecipe);
				}
			}
		}
		//javelin
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.Javelin, 1, 0), new Object[] { 
			"1","2","2", Character.valueOf('1'), Item.flint,Character.valueOf('2'), Item.stick});

		//stone picks
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgInPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgExPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSedPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMMPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});
		//stone shovels
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgInShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgExShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSedShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMMShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});
		//stone hoes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgInHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgExHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSedHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMMHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});
		//stone axes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgInAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraIgExAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraSedAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.stick});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.terraMMAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.stick});

		//the bone versions
		//stone picks
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgInPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgExPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneSedPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneMMPick, 1, 0), new Object[] { 
			"111"," 2 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.bone});
		//stone shovels
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgInShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgExShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneSedShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneMMShovel, 1, 0), new Object[] { 
			"1 ","2 ","2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.bone});
		//stone hoes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgInHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgExHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneSedHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneMMHoe, 1, 0), new Object[] { 
			"11"," 2"," 2", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.bone});
		//stone axes
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgInAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgInCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneIgExAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneIgExCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneSedAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneSedCobble,Character.valueOf('2'), Item.bone});
		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.boneMMAxe, 1, 0), new Object[] { 
			"11 ","12 "," 2 ", Character.valueOf('1'), mod_TFC_Core.terraStoneMMCobble,Character.valueOf('2'), Item.bone});

		ModLoader.addRecipe(new ItemStack(mod_TFC_Core.FlintPaxel, 1, 0), new Object[] { 
			"1","2", Character.valueOf('1'), Item.flint,Character.valueOf('2'), Item.stick});



	}

	/**This is the old version that would be run after the world had already 
	 * generated vanilla style. This is no longer used due to lag.*/
	public static void replaceBlocksForBiome(int par1, int par2, byte[] abyte0, BiomeGenBase[] par4ArrayOfBiomeGenBase, Chunk chunk, Random rand)
	{
		for (int xCoord = 0; xCoord < 16; ++xCoord)
		{
			for (int zCoord = 0; zCoord < 16; ++zCoord)
			{
				BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[zCoord + xCoord * 16];
				float var11 = biomegenbase.getFloatTemperature();

				byte var14 = (byte)biomegenbase.topBlock;
				byte var15 = (byte)biomegenbase.fillerBlock;

				for (int height = 127; height >= 0; --height)
				{
					int arrayCoord = (zCoord * 16 + xCoord) * 128 + height;

					if (height == 0)
					{
						abyte0[arrayCoord] = (byte)Block.bedrock.blockID;
					}

					if(chunk.getBlockID(xCoord, height, zCoord) == Block.grass.blockID)
					{
						chunk.setBlockIDWithMetadata(xCoord, height, zCoord,biomegenbase.GrassID, biomegenbase.TopSoilMetaID);
					}
					else if(chunk.getBlockID(xCoord, height, zCoord) == Block.dirt.blockID)
					{
						chunk.setBlockIDWithMetadata(xCoord, height, zCoord,biomegenbase.DirtID, biomegenbase.TopSoilMetaID);
					}
					else if (chunk.getBlockID(xCoord, height, zCoord) == Block.stone.blockID)
					{
						if(height <= biomegenbase.Layer3)
						{
							chunk.setBlockIDWithMetadata(xCoord, height, zCoord, biomegenbase.Layer3Type, biomegenbase.Layer3Meta);
							if(height == biomegenbase.Layer3)
							{
								if(rand.nextBoolean())
								{
									chunk.setBlockIDWithMetadata(xCoord, height+1, zCoord, biomegenbase.Layer3Type, biomegenbase.Layer3Meta);
									if(rand.nextBoolean())
									{
										chunk.setBlockIDWithMetadata(xCoord, height+2, zCoord, biomegenbase.Layer3Type, biomegenbase.Layer3Meta);
										if(rand.nextBoolean())
										{
											chunk.setBlockIDWithMetadata(xCoord, height+3, zCoord, biomegenbase.Layer3Type, biomegenbase.Layer3Meta);
										}
									}
								}
							}
						}
						else if(height <= biomegenbase.Layer2 && height > biomegenbase.Layer3)
						{
							chunk.setBlockIDWithMetadata(xCoord, height, zCoord, biomegenbase.Layer2Type, biomegenbase.Layer2Meta);
							if(height == biomegenbase.Layer2)
							{
								if(rand.nextBoolean())
								{
									chunk.setBlockIDWithMetadata(xCoord, height+1, zCoord, biomegenbase.Layer2Type, biomegenbase.Layer2Meta);
									if(rand.nextBoolean())
									{
										chunk.setBlockIDWithMetadata(xCoord, height+2, zCoord, biomegenbase.Layer2Type, biomegenbase.Layer2Meta);
										if(rand.nextBoolean())
										{
											chunk.setBlockIDWithMetadata(xCoord, height+3, zCoord, biomegenbase.Layer2Type, biomegenbase.Layer2Meta);
										}
									}
								}
							}
						}
						else if(height <= biomegenbase.Layer1 && height > biomegenbase.Layer2)
						{
							chunk.setBlockIDWithMetadata(xCoord, height, zCoord, biomegenbase.Layer1Type, biomegenbase.Layer1Meta);                          
							if(height == biomegenbase.Layer1)
							{
								if(rand.nextBoolean())
								{
									chunk.setBlockIDWithMetadata(xCoord, height+1, zCoord, biomegenbase.Layer1Type, biomegenbase.Layer1Meta);
									if(rand.nextBoolean())
									{
										chunk.setBlockIDWithMetadata(xCoord, height+2, zCoord, biomegenbase.Layer1Type, biomegenbase.Layer1Meta);
										if(rand.nextBoolean())
										{
											chunk.setBlockIDWithMetadata(xCoord, height+3, zCoord, biomegenbase.Layer1Type, biomegenbase.Layer1Meta);
										}
									}
								}
							}
						}
						else
						{
							chunk.setBlockIDWithMetadata(xCoord, height, zCoord, biomegenbase.SurfaceType, biomegenbase.SurfaceMeta);
						}
					}
				}
			}
		}
	};

	/**This version performs all the intial block placement in the initial loop, eliminating
	 * a major source of chunkloader lag.*/
	public static void replaceBlocksForBiome(int par1, int par2, byte[] blockArray, byte[] metaArray, BiomeGenBase[] par4ArrayOfBiomeGenBase, 
			double[] stoneNoise, NoiseGeneratorOctaves noiseGen4, Random rand)
	{
		byte var5 = 63;
		double var6 = 0.03125D;
		stoneNoise = noiseGen4.generateNoiseOctaves(stoneNoise, par1 * 16, par2 * 16, 0, 16, 16, 1, var6 * 2.0D, var6 * 2.0D, var6 * 2.0D);

		for (int xCoord = 0; xCoord < 16; ++xCoord)
		{
			for (int zCoord = 0; zCoord < 16; ++zCoord)
			{
				BiomeGenBase biomegenbase = par4ArrayOfBiomeGenBase[zCoord + xCoord * 16];
				float var11 = biomegenbase.getFloatTemperature();
				int var12 = (int)(stoneNoise[xCoord + zCoord * 16] / 3.0D + 3.0D + rand.nextDouble() * 0.25D);
				int var13 = -1;
				int var14 = biomegenbase.GrassID;
				int var15 = biomegenbase.DirtID;

				for (int height = 127; height >= 0; --height)
				{
					int var17 = (zCoord * 16 + xCoord) * 128 + height;

					int var10 = height >> 4;
		metaArray[var17] = 0;

		if (height <= 1)
		{
			blockArray[var17] = (byte) Block.bedrock.blockID;
		}
		else
		{
			int var18 = blockArray[var17];
			if (var18 == 0)
			{
				var13 = -1;
			}
			else if (var18 == Block.stone.blockID)
			{
				if(height <= biomegenbase.Layer3)
				{
					blockArray[var17] = (byte) biomegenbase.Layer3Type; 
					metaArray[var17] = (byte)  biomegenbase.Layer3Meta;
					if(height == biomegenbase.Layer3)
					{
						if(rand.nextBoolean())
						{
							blockArray[var17+1] = (byte) biomegenbase.Layer3Type; 
							metaArray[var17+1] = (byte)  biomegenbase.Layer3Meta;
							if(rand.nextBoolean())
							{
								blockArray[var17+2] = (byte) biomegenbase.Layer3Type; 
								metaArray[var17+2] = (byte)  biomegenbase.Layer3Meta;
								if(rand.nextBoolean())
								{
									blockArray[var17+3] = (byte) biomegenbase.Layer3Type; 
									metaArray[var17+3] = (byte)  biomegenbase.Layer3Meta;
								}
							}
						}
					}
				}
				else if(height <= biomegenbase.Layer2 && height > biomegenbase.Layer3)
				{
					blockArray[var17] = (byte) biomegenbase.Layer2Type; 
					metaArray[var17] = (byte)  biomegenbase.Layer2Meta;
					if(height == biomegenbase.Layer2)
					{
						if(rand.nextBoolean())
						{
							blockArray[var17+1] = (byte) biomegenbase.Layer2Type; 
							metaArray[var17+1] = (byte)  biomegenbase.Layer2Meta;
							if(rand.nextBoolean())
							{
								blockArray[var17+2] = (byte) biomegenbase.Layer2Type; 
								metaArray[var17+2] = (byte)  biomegenbase.Layer2Meta;
								if(rand.nextBoolean())
								{
									blockArray[var17+3] = (byte) biomegenbase.Layer2Type; 
									metaArray[var17+3] = (byte)  biomegenbase.Layer2Meta;
								}
							}
						}
					}
				}
				else if(height <= biomegenbase.Layer1 && height > biomegenbase.Layer2)
				{
					blockArray[var17] = (byte) biomegenbase.Layer1Type; 
					metaArray[var17] = (byte)  biomegenbase.Layer1Meta;    
					if(height == biomegenbase.Layer3)
					{
						if(rand.nextBoolean())
						{
							blockArray[var17+1] = (byte) biomegenbase.Layer1Type; 
							metaArray[var17+1] = (byte)  biomegenbase.Layer1Meta;
							if(rand.nextBoolean())
							{
								blockArray[var17+2] = (byte) biomegenbase.Layer1Type; 
								metaArray[var17+2] = (byte)  biomegenbase.Layer1Meta;
								if(rand.nextBoolean())
								{
									blockArray[var17+3] = (byte) biomegenbase.Layer1Type; 
									metaArray[var17+3] = (byte)  biomegenbase.Layer1Meta;
								}
							}
						}
					}
				}
				else
				{
					blockArray[var17] = (byte) biomegenbase.SurfaceType; 
					metaArray[var17] = (byte) biomegenbase.SurfaceMeta;
				}
				if (var13 == -1)
				{
					if (var12 <= 0)
					{
						var14 = 0;

					}
					else if (height >= var5 - 4 && height <= var5 + 1)
					{
						var14 = biomegenbase.GrassID;
						var15 =  biomegenbase.DirtID;
					}

					if (height < var5 && var14 == 0)
					{
						if (var11 < 0.15F)
						{
							var14 = Block.ice.blockID;
						}
						else
						{
							var14 = Block.waterStill.blockID;
						}
					}

					var13 = var12;

					if (height >= var5 - 1)
					{
						blockArray[var17] = (byte) var14;
					}
					else
					{
						blockArray[var17] = (byte) var15;
						metaArray[var17] = (byte) biomegenbase.SurfaceMeta;
					}
				}
				else if (var13 > 0)
				{
					--var13;
					blockArray[var17] = (byte) var15;
					metaArray[var17] = (byte) biomegenbase.SurfaceMeta;

					if (var13 == 0 && var15 == Block.sand.blockID)
					{
						var13 = rand.nextInt(4);
						var15 = Block.sandStone.blockID;
					}
				}
			}
		}
				}
			}
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
}
