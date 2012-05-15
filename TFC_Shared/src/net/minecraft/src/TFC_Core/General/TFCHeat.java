package net.minecraft.src.TFC_Core.General;

import java.util.Collection;
import java.util.Hashtable;
import java.util.Iterator;
import net.minecraft.src.Block;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTBase;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagFloat;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Game;

public class TFCHeat 
{
	public static Hashtable ItemHeatData;
	static
	{
		ItemHeatData = new Hashtable();


		//----------------------------------------Specific Heat---Boiling Point C---Melt Temp Item 1---Item 1---Melt Temp Item 2---Item 2---

		//ItemHeatData.put("tile.stonebrick", new Object[]{(float)0.75F,100000F,(float)1000F, new ItemStack(Block.stone,1)});

		ItemHeatData.put("item.Ore.Native Copper", new Object[]{(float)0.67F,100000F,(float)1084, new ItemStack(mod_TFC_Game.UnshapedCopper,1,50)});
		ItemHeatData.put("item.Ore.Native Gold", new Object[]{(float)0.77F,100000F,(float)1063, new ItemStack(mod_TFC_Game.UnshapedGold,1,50)});
		ItemHeatData.put("item.Ore.Native Platinum", new Object[]{(float)0.82F,100000F,(float)1770, new ItemStack(mod_TFC_Game.UnshapedPlatinum,1,50)});
		ItemHeatData.put("item.Ore.Hematite", new Object[]{(float)0.64F,100000F,(float)1536, new ItemStack(mod_TFC_Game.UnshapedPigIron,1,50)});
		ItemHeatData.put("item.Ore.Native Silver", new Object[]{(float)0.72F,100000F,(float)961, new ItemStack(mod_TFC_Game.UnshapedSilver,1,50)});
		ItemHeatData.put("item.Ore.Cassiterite", new Object[]{(float)0.69F,100000F,(float)232, new ItemStack(mod_TFC_Game.UnshapedTin,1,50)});
		ItemHeatData.put("item.Ore.Galena", new Object[]{(float)0.77F,100000F,(float)327.5, new ItemStack(mod_TFC_Game.UnshapedLead,1,80), 
				(float)961, new ItemStack(mod_TFC_Game.UnshapedSilver,1,80)});
		ItemHeatData.put("item.Ore.Bismuthinite", new Object[]{(float)0.75F,100000F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,1,50)});
		ItemHeatData.put("item.Ore.Garnierite", new Object[]{(float)0.65F,100000F,(float)1453, new ItemStack(mod_TFC_Game.UnshapedNickel,1,50)});
		ItemHeatData.put("item.Ore.Malachite", new Object[]{(float)0.67F,100000F,(float)1084, new ItemStack(mod_TFC_Game.UnshapedCopper,1,50)});
		ItemHeatData.put("item.Ore.Magnetite", new Object[]{(float)0.64F,100000F,(float)1536, new ItemStack(mod_TFC_Game.UnshapedPigIron,1,50)});

		ItemHeatData.put("item.Ore.Limonite", new Object[]{(float)0.64F,100000F,(float)1536, new ItemStack(mod_TFC_Game.UnshapedPigIron,1,50)});

		ItemHeatData.put("item.Ore.Sphalerite", new Object[]{(float)0.66F,100000F,(float)419.5, new ItemStack(mod_TFC_Game.UnshapedZinc,1,50)});

		ItemHeatData.put("item.Ore.Tetrahedrite", new Object[]{(float)0.67F,100000F,(float)961, new ItemStack(mod_TFC_Game.UnshapedSilver,1,90),
				(float)1084, new ItemStack(mod_TFC_Game.UnshapedCopper,1,75)});

		ItemHeatData.put("item.terraBismuthIngot", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,1)});
		ItemHeatData.put("item.UnshapedBismuth", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,1)});
		ItemHeatData.put("item.terraBismuthIngot2x", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
		ItemHeatData.put("item.Bismuth"+"Sheet", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
		ItemHeatData.put("item.Bismuth"+"Sheet2x", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
		ItemHeatData.put("item.Bismuth"+"UnfinishedHelmet", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
		ItemHeatData.put("item.Bismuth"+"UnfinishedHelmet2", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
		ItemHeatData.put("item.Bismuth"+"UnfinishedChestplate", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
        ItemHeatData.put("item.Bismuth"+"UnfinishedChestplate2", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
        ItemHeatData.put("item.Bismuth"+"UnfinishedGreaves", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
        ItemHeatData.put("item.Bismuth"+"UnfinishedGreaves2", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
        ItemHeatData.put("item.Bismuth"+"UnfinishedBoots", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});
        ItemHeatData.put("item.Bismuth"+"UnfinishedBoots2", new Object[]{(float)0.75F,(float)1564F,(float)271.4, new ItemStack(mod_TFC_Game.UnshapedBismuth,2)});

		ItemHeatData.put("item.terraBismuthBronzeIngot", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,1)});
		ItemHeatData.put("item.UnshapedBismuthBronze", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,1)});
		ItemHeatData.put("item.terraBismuthBronzeIngot2x", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
		ItemHeatData.put("item.BismuthBronze"+"Sheet", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
		ItemHeatData.put("item.BismuthBronze"+"Sheet2x", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
		ItemHeatData.put("item.BismuthBronze"+"UnfinishedHelmet", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
		ItemHeatData.put("item.BismuthBronze"+"UnfinishedHelmet2", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
		ItemHeatData.put("item.BismuthBronze"+"UnfinishedChestplate", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
        ItemHeatData.put("item.BismuthBronze"+"UnfinishedChestplate2", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
        ItemHeatData.put("item.BismuthBronze"+"UnfinishedGreaves", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
        ItemHeatData.put("item.BismuthBronze"+"UnfinishedGreaves2", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
        ItemHeatData.put("item.BismuthBronze"+"UnfinishedBoots", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});
        ItemHeatData.put("item.BismuthBronze"+"UnfinishedBoots2", new Object[]{(float)0.65F,(float)1941F,(float)985, new ItemStack(mod_TFC_Game.UnshapedBismuthBronze,2)});

		ItemHeatData.put("item.terraBlackBronzeIngot", new Object[]{(float)0.70F, (float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,1)});
		ItemHeatData.put("item.UnshapedBlackBronze", new Object[]{(float)0.70F, (float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,1)});
		ItemHeatData.put("item.terraBlackBronzeIngot2x", new Object[]{(float)0.70F, (float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
		ItemHeatData.put("item.BlackBronze"+"Sheet", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
        ItemHeatData.put("item.BlackBronze"+"Sheet2x", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
		ItemHeatData.put("item.BlackBronze"+"UnfinishedHelmet", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
		ItemHeatData.put("item.BlackBronze"+"UnfinishedHelmet2", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
		ItemHeatData.put("item.BlackBronze"+"UnfinishedChestplate", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
        ItemHeatData.put("item.BlackBronze"+"UnfinishedChestplate2", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
        ItemHeatData.put("item.BlackBronze"+"UnfinishedGreaves", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
        ItemHeatData.put("item.BlackBronze"+"UnfinishedGreaves2", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
        ItemHeatData.put("item.BlackBronze"+"UnfinishedBoots", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});
        ItemHeatData.put("item.BlackBronze"+"UnfinishedBoots2", new Object[]{(float)0.70F,(float)2219,(float)1070, new ItemStack(mod_TFC_Game.UnshapedBlackBronze,2)});

        float sh = 0.63F; float boil = 2726; float melt = 1485;
		ItemHeatData.put("item.terraBlackSteelIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,1)});
		ItemHeatData.put("item.UnshapedBlackSteel", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,1)});
		ItemHeatData.put("item.terraBlackSteelIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
		ItemHeatData.put("item.BlackSteel"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
        ItemHeatData.put("item.BlackSteel"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
		ItemHeatData.put("item.BlackSteel"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
        ItemHeatData.put("item.BlackSteel"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
        ItemHeatData.put("item.BlackSteel"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
        ItemHeatData.put("item.BlackSteel"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
        ItemHeatData.put("item.BlackSteel"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
        ItemHeatData.put("item.BlackSteel"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
        ItemHeatData.put("item.BlackSteel"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});
        ItemHeatData.put("item.BlackSteel"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlackSteel,2)});

        sh = 0.60F; boil = 3460; melt = 1680;
		ItemHeatData.put("item.terraBlueSteelIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,1)});
		ItemHeatData.put("item.UnshapedBlueSteel", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,1)});
		ItemHeatData.put("item.terraBlueSteelIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
		ItemHeatData.put("item.BlueSteel"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
        ItemHeatData.put("item.BlueSteel"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
        ItemHeatData.put("item.BlueSteel"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
        ItemHeatData.put("item.BlueSteel"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
        ItemHeatData.put("item.BlueSteel"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
        ItemHeatData.put("item.BlueSteel"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
        ItemHeatData.put("item.BlueSteel"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
        ItemHeatData.put("item.BlueSteel"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
        ItemHeatData.put("item.BlueSteel"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});
        ItemHeatData.put("item.BlueSteel"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBlueSteel,2)});

		sh = 0.65F; boil = 1760; melt = 930;
		ItemHeatData.put("item.terraBrassIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBrass,1)});
		ItemHeatData.put("item.UnshapedBrass", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBrass,1)});
		ItemHeatData.put("item.terraBrassIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBrass,2)});

		sh = 0.65F; boil = 2380; melt = 950;
		ItemHeatData.put("item.terraBronzeIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,1)});
		ItemHeatData.put("item.UnshapedBronze", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,1)});
		ItemHeatData.put("item.terraBronzeIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
		ItemHeatData.put("item.Bronze"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
        ItemHeatData.put("item.Bronze"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
        ItemHeatData.put("item.Bronze"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
        ItemHeatData.put("item.Bronze"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
        ItemHeatData.put("item.Bronze"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
        ItemHeatData.put("item.Bronze"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
        ItemHeatData.put("item.Bronze"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
        ItemHeatData.put("item.Bronze"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
        ItemHeatData.put("item.Bronze"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});
        ItemHeatData.put("item.Bronze"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedBronze,2)});

		sh = 0.67F; boil = 1760; melt = 1084;
		ItemHeatData.put("item.terraCopperIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,1)});
		ItemHeatData.put("item.UnshapedCopper", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,1)});
		ItemHeatData.put("item.terraCopperIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
		ItemHeatData.put("item.Copper"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
        ItemHeatData.put("item.Copper"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
        ItemHeatData.put("item.Copper"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
        ItemHeatData.put("item.Copper"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
        ItemHeatData.put("item.Copper"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
        ItemHeatData.put("item.Copper"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
        ItemHeatData.put("item.Copper"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
        ItemHeatData.put("item.Copper"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
        ItemHeatData.put("item.Copper"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});
        ItemHeatData.put("item.Copper"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedCopper,2)});

		sh = 0.77F; boil = 1760; melt = 1063;
		ItemHeatData.put("item.terraGoldIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedGold,1)});
		ItemHeatData.put("item.UnshapedGold", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedGold,1)});
		ItemHeatData.put("item.terraGoldIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedGold,2)});

		sh = 0.64F; boil = 1760; melt = 1536;
		ItemHeatData.put("item.terraWroughtIronIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,1)});
		ItemHeatData.put("item.UnshapedWroughtIron", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,1)});
		ItemHeatData.put("item.terraWroughtIronIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
		ItemHeatData.put("item.WroughtIron"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
        ItemHeatData.put("item.WroughtIron"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
        ItemHeatData.put("item.WroughtIron"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
        ItemHeatData.put("item.WroughtIron"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
        ItemHeatData.put("item.WroughtIron"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
        ItemHeatData.put("item.WroughtIron"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
        ItemHeatData.put("item.WroughtIron"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
        ItemHeatData.put("item.WroughtIron"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
        ItemHeatData.put("item.WroughtIron"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});
        ItemHeatData.put("item.WroughtIron"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedWroughtIron,2)});

		sh = 0.82F; boil = 1750; melt = 327;
		ItemHeatData.put("item.terraLeadIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedLead,1)});
		ItemHeatData.put("item.UnshapedLead", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedLead,1)});
		ItemHeatData.put("item.terraLeadIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedLead,2)});

		sh = 0.56F; boil = 2732; melt = 930;
		ItemHeatData.put("item.terraNickelIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedNickel,1)});
		ItemHeatData.put("item.UnshapedNickel", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedNickel,1)});
		ItemHeatData.put("item.terraNickelIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedNickel,2)});

		sh = 0.59F; boil = 3150; melt = 1500;
		ItemHeatData.put("item.terraPigIronIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedPigIron,1)});
		ItemHeatData.put("item.UnshapedPigIron", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedPigIron,1)});
		ItemHeatData.put("item.terraPigIronIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedPigIron,2)});

		sh = 0.82F; boil = 1760; melt = 1770;
		ItemHeatData.put("item.terraPlatinumIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedPlatinum,1)});
		ItemHeatData.put("item.UnshapedPlatinum", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedPlatinum,1)});
		ItemHeatData.put("item.terraPlatinumIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedPlatinum,2)});

		sh = 0.60F; boil = 3589; melt = 1770;
		ItemHeatData.put("item.terraRedSteelIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,1)});
		ItemHeatData.put("item.UnshapedRedSteel", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,1)});
		ItemHeatData.put("item.terraRedSteelIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
		ItemHeatData.put("item.RedSteel"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
        ItemHeatData.put("item.RedSteel"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
        ItemHeatData.put("item.RedSteel"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
        ItemHeatData.put("item.RedSteel"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
        ItemHeatData.put("item.RedSteel"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
        ItemHeatData.put("item.RedSteel"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
        ItemHeatData.put("item.RedSteel"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
        ItemHeatData.put("item.RedSteel"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
        ItemHeatData.put("item.RedSteel"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});
        ItemHeatData.put("item.RedSteel"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRedSteel,2)});

		sh = 0.69F; boil = 2650; melt = 960;
		ItemHeatData.put("item.terraRoseGoldIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,1)});
		ItemHeatData.put("item.UnshapedRoseGold", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,1)});
		ItemHeatData.put("item.terraRoseGoldIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
		ItemHeatData.put("item.RoseGold"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
        ItemHeatData.put("item.RoseGold"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
        ItemHeatData.put("item.RoseGold"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
        ItemHeatData.put("item.RoseGold"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
        ItemHeatData.put("item.RoseGold"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
        ItemHeatData.put("item.RoseGold"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
        ItemHeatData.put("item.RoseGold"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
        ItemHeatData.put("item.RoseGold"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
        ItemHeatData.put("item.RoseGold"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});
        ItemHeatData.put("item.RoseGold"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedRoseGold,2)});

		sh = 0.72F; boil = 2212; melt = 961;
		ItemHeatData.put("item.terraSilverIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedSilver,1)});
		ItemHeatData.put("item.UnshapedSilver", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedSilver,1)});
		ItemHeatData.put("item.terraSilverIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedSilver,2)});

		sh = 0.63F; boil = 3500; melt = 1540;
		ItemHeatData.put("item.terraSteelIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,1)});
		ItemHeatData.put("item.UnshapedSteel", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,1)});
		ItemHeatData.put("item.terraSteelIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
		ItemHeatData.put("item.Steel"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
        ItemHeatData.put("item.Steel"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
        ItemHeatData.put("item.Steel"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
        ItemHeatData.put("item.Steel"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
        ItemHeatData.put("item.Steel"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
        ItemHeatData.put("item.Steel"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
        ItemHeatData.put("item.Steel"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
        ItemHeatData.put("item.Steel"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
        ItemHeatData.put("item.Steel"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});
        ItemHeatData.put("item.Steel"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedSteel,2)});

		sh = 0.72F; boil = 2212; melt = 893;
		ItemHeatData.put("item.terraSterlingSilverIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedSterlingSilver,1)});
		ItemHeatData.put("item.UnshapedSterlingSilver", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedSterlingSilver,1)});
		ItemHeatData.put("item.terraSterlingSilverIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedSterlingSilver,2)});

		sh = 0.69F; boil = 2600; melt = 232;
		ItemHeatData.put("item.terraTinIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,1)});
		ItemHeatData.put("item.UnshapedTin", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,1)});
		ItemHeatData.put("item.terraTinIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
		ItemHeatData.put("item.Tin"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
        ItemHeatData.put("item.Tin"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
        ItemHeatData.put("item.Tin"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
        ItemHeatData.put("item.Tin"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
        ItemHeatData.put("item.Tin"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
        ItemHeatData.put("item.Tin"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
        ItemHeatData.put("item.Tin"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
        ItemHeatData.put("item.Tin"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
        ItemHeatData.put("item.Tin"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});
        ItemHeatData.put("item.Tin"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedTin,2)});

		sh = 0.66F; boil = 907; melt = 420;
		ItemHeatData.put("item.terraZincIngot", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,1)});
		ItemHeatData.put("item.UnshapedZinc", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,1)});
		ItemHeatData.put("item.terraZincIngot2x", new Object[]{sh, boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
		ItemHeatData.put("item.Zinc"+"Sheet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
        ItemHeatData.put("item.Zinc"+"Sheet2x", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
        ItemHeatData.put("item.Zinc"+"UnfinishedHelmet", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
        ItemHeatData.put("item.Zinc"+"UnfinishedHelmet2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
        ItemHeatData.put("item.Zinc"+"UnfinishedChestplate", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
        ItemHeatData.put("item.Zinc"+"UnfinishedChestplate2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
        ItemHeatData.put("item.Zinc"+"UnfinishedGreaves", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
        ItemHeatData.put("item.Zinc"+"UnfinishedGreaves2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
        ItemHeatData.put("item.Zinc"+"UnfinishedBoots", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});
        ItemHeatData.put("item.Zinc"+"UnfinishedBoots2", new Object[]{sh,boil, melt, new ItemStack(mod_TFC_Game.UnshapedZinc,2)});

		ItemHeatData.put("item.terraHCSteelIngot", new Object[]{(float)0.63F,(float)3500,(float)1540, new ItemStack(mod_TFC_Game.UnshapedHCSteel,1)});
		ItemHeatData.put("item.terraWeakSteelIngot", new Object[]{(float)0.63F,(float)3500,(float)1540, new ItemStack(mod_TFC_Game.UnshapedWeakSteel,1)});
		ItemHeatData.put("item.terraHCBlueSteelIngot", new Object[]{(float)0.60F,(float)3460,(float)1680, new ItemStack(mod_TFC_Game.UnshapedHCBlueSteel,1)});
		ItemHeatData.put("item.terraWeakBlueSteelIngot", new Object[]{(float)0.60F,(float)3460,(float)1680, new ItemStack(mod_TFC_Game.UnshapedWeakBlueSteel,1)});
		ItemHeatData.put("item.terraHCBlackSteelIngot", new Object[]{(float)0.63F, (float)3426.85,(float)1485, new ItemStack(mod_TFC_Game.UnshapedHCBlackSteel,1)});
		ItemHeatData.put("item.terraHCRedSteelIngot", new Object[]{(float)0.60F,(float)3589,(float)1770, new ItemStack(mod_TFC_Game.UnshapedHCRedSteel,1)});
		ItemHeatData.put("item.terraWeakRedSteelIngot", new Object[]{(float)0.60F,(float)3589,(float)1770, new ItemStack(mod_TFC_Game.UnshapedWeakRedSteel,1)});

		ItemHeatData.put("item.UnshapedHCSteel", new Object[]{(float)0.63F,(float)3500,(float)1540, new ItemStack(mod_TFC_Game.UnshapedHCSteel,1)});
		ItemHeatData.put("item.UnshapedWeakSteel", new Object[]{(float)0.63F,(float)3500,(float)1540, new ItemStack(mod_TFC_Game.UnshapedWeakSteel,1)});
		ItemHeatData.put("item.UnshapedHCBlueSteel", new Object[]{(float)0.60F,(float)3460,(float)1680, new ItemStack(mod_TFC_Game.UnshapedHCBlueSteel,1)});
		ItemHeatData.put("item.UnshapedWeakBlueSteel", new Object[]{(float)0.60F,(float)3460,(float)1680, new ItemStack(mod_TFC_Game.UnshapedWeakBlueSteel,1)});
		ItemHeatData.put("item.UnshapedHCBlackSteel", new Object[]{(float)0.63F, (float)3426.85,(float)1485, new ItemStack(mod_TFC_Game.UnshapedHCBlackSteel,1)});
		ItemHeatData.put("item.UnshapedHCRedSteel", new Object[]{(float)0.60F,(float)3589,(float)1770, new ItemStack(mod_TFC_Game.UnshapedHCRedSteel,1)});
		ItemHeatData.put("item.UnshapedWeakRedSteel", new Object[]{(float)0.60F,(float)3589,(float)1770, new ItemStack(mod_TFC_Game.UnshapedWeakRedSteel,1)});

		//Clay stuff
		ItemHeatData.put("item.terraClayMold", new Object[]{(float)1.40F,100000F,(float)515.5, new ItemStack(mod_TFC_Game.terraCeramicMold,1)});
		ItemHeatData.put("tile.sand", new Object[]{(float)0.75F,100000F,(float)825.5, new ItemStack(Block.glass,1)});

		//Food
		ItemHeatData.put("item.porkchopRaw", new Object[]{(float)0.85F,385F,(float)130.5, new ItemStack(Item.porkCooked,1)});
		ItemHeatData.put("item.beefRaw", new Object[]{(float)0.85F,425F,(float)135.5, new ItemStack(Item.beefCooked,1)});
		ItemHeatData.put("item.chickenRaw", new Object[]{(float)0.85F,425F,(float)120.5, new ItemStack(Item.chickenCooked,1)});
		ItemHeatData.put("item.fishRaw", new Object[]{(float)0.85F,425F,(float)120.5, new ItemStack(Item.fishCooked,1)});

		//Torches
		ItemHeatData.put("item.stick", new Object[]{(float)13.0F,800F,(float)210.5, new ItemStack(Block.torchWood,2)});
		
	}
	public static Boolean canRemoveTag(Object tag, String key, Class c)
	{
		if(tag.getClass() == c)
		{
			if (((NBTBase)c.cast(tag)).getName() == key)
			{
				return true;
			}
		}
		return false;
	}

	public static String getHeatColor(float temp, float meltTemp)
	{
		String phrase = "";
		if(TFCSettings.BlacksmithModeHeatScale)
		{
			if(temp < 80)
			{
				phrase = "Warm";
				if(temp>(80 * 0.2)) phrase = phrase + "*";
				if(temp>(80 * 0.4)) phrase = phrase + "*";
				if(temp>(80 * 0.6)) phrase = phrase + "*";
				if(temp>(80 * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 80 && temp < 210)
			{
				phrase = "Hot";
				if(temp>80+((210-80) * 0.2)) phrase = phrase + "*";
                if(temp>80+((210-80) * 0.4)) phrase = phrase + "*";
                if(temp>80+((210-80) * 0.6)) phrase = phrase + "*";
                if(temp>80+((210-80) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 210 &&  temp < 480)
			{
				phrase = "Very Hot";
				if(temp>210+((480-210) * 0.2)) phrase = phrase + "*";
                if(temp>210+((480-210) * 0.4)) phrase = phrase + "*";
                if(temp>210+((480-210) * 0.6)) phrase = phrase + "*";
                if(temp>210+((480-210) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 480 &&  temp < 580)
			{
				phrase = "\2474Faint Red";
				if(temp>480+((580-480) * 0.2)) phrase = phrase + "*";
                if(temp>480+((580-480) * 0.4)) phrase = phrase + "*";
                if(temp>480+((580-480) * 0.6)) phrase = phrase + "*";
                if(temp>480+((580-480) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 580 &&  temp < 730)
			{
				phrase = "\2474Dark Red";
				if(temp>580+((730-580) * 0.2)) phrase = phrase + "*";
                if(temp>580+((730-580) * 0.4)) phrase = phrase + "*";
                if(temp>580+((730-580) * 0.6)) phrase = phrase + "*";
                if(temp>580+((730-580) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 730 &&  temp < 930)
			{
				phrase = "\247cBright Red";
				if(temp>730+((930-730) * 0.2)) phrase = phrase + "*";
                if(temp>730+((930-730) * 0.4)) phrase = phrase + "*";
                if(temp>730+((930-730) * 0.6)) phrase = phrase + "*";
                if(temp>730+((930-730) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 930 &&  temp < 1100)
			{
				phrase = "\2476Orange";
				if(temp>930+((1100-930) * 0.2)) phrase = phrase + "*";
                if(temp>930+((1100-930) * 0.4)) phrase = phrase + "*";
                if(temp>930+((1100-930) * 0.6)) phrase = phrase + "*";
                if(temp>930+((1100-930) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 1100 &&  temp < 1300)
			{
				phrase = "\247eYellow";
				if(temp>1100+((1300-1100) * 0.2)) phrase = phrase + "*";
                if(temp>1100+((1300-1100) * 0.4)) phrase = phrase + "*";
                if(temp>1100+((1300-1100) * 0.6)) phrase = phrase + "*";
                if(temp>1100+((1300-1100) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 1300 &&  temp < 1400)
			{
				phrase = "\247eYellow White";
				if(temp>1300+((1400-1300) * 0.2)) phrase = phrase + "*";
                if(temp>1300+((1400-1300) * 0.4)) phrase = phrase + "*";
                if(temp>1300+((1400-1300) * 0.6)) phrase = phrase + "*";
                if(temp>1300+((1400-1300) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 1400 &&  temp < 1500)
			{
				phrase = "\247fWhite";
				if(temp>1400+((1500-1400) * 0.2)) phrase = phrase + "*";
                if(temp>1400+((1500-1400) * 0.4)) phrase = phrase + "*";
                if(temp>1400+((1500-1400) * 0.6)) phrase = phrase + "*";
                if(temp>1400+((1500-1400) * 0.8)) phrase = phrase + "*";
			}
			else if(temp >= 1500)
			{
				phrase = "\247fBrilliant White";
			}

			if(temp > meltTemp) {
				phrase = phrase + "\247f - Liquid";
			}
		}
		else
		{
			if(temp < meltTemp)
			{
				if(temp < meltTemp*0.1F)
				{
					return "Warm";
				}
				else if(temp >= meltTemp*0.1F && temp < meltTemp*0.3F)
				{
					return "\2474Hot";
				}
				else if(temp >= meltTemp*0.3F && temp < meltTemp*0.5F)
				{
					return "\247cRed";
				}
				else if(temp >= meltTemp*0.5F && temp < meltTemp*0.6F)
				{
					return "\2476Orange";
				}
				else if(temp >= meltTemp*0.6F && temp < meltTemp*0.7F)
				{
					return "\247eYellow";
				}
				else if(temp >= meltTemp*0.7F && temp < meltTemp*0.8F)
				{
					return "\247eYellow White";
				}
				else if(temp >= meltTemp*0.8F && temp < meltTemp*0.9F)
				{
					return "\247fWhite";
				}
				else if(temp >= meltTemp*0.9F && temp < meltTemp)
				{
					return "\247fBrilliant White";
				}
			}

			return "\247fBrilliant White: Liquid";
		}

		return phrase;
	}

	public static String getHeatColorFood(float temp, float meltTemp)
	{
		if(temp < meltTemp)
		{
			if(temp < meltTemp*0.1F)
			{
				return "Cold";
			}
			else if(temp >= meltTemp*0.1F && temp < meltTemp*0.4F)
			{
				return "\2474Warming";
			}
			else if(temp >= meltTemp*0.4F && temp < meltTemp*0.8F)
			{
				return "\2474Hot";
			}
			else
			{
				return "\2474Very Hot";
			}
		}

		return "Clear an output slot.";
	}

	public static String getHeatColorTorch(float temp, float meltTemp)
	{
		if(temp < meltTemp)
		{
			if(temp > 0 && temp < meltTemp*0.8F)
			{
				return "Catching Fire";
			}
			else if(temp >= meltTemp*0.8F)
			{
				return "\2474Lit";
			}
		}

		return "Clear an output slot.";
	}

	public static Boolean getIsBoiling(ItemStack is)
	{
		Object[] meltData = (Object[])TFCHeat.ItemHeatData.get(is.getItem().getItemNameIS(is));
		if(meltData != null && is != null)
		{
			if(is != null && is.hasTagCompound())
			{
				float temp = is.getTagCompound().getFloat("temperature");
				return temp >= (Float)meltData[1];
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	public static float getMeltingPoint(ItemStack is)
	{		
		if(ItemHeatData!=null)
		{
			String name = is.getItem().getItemNameIS(is);
			Object[] data = (Object[])ItemHeatData.get(name);
			if(data != null)
			{
				return (Float) data[2];
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}
	public static float getMeltingPoint2(ItemStack is)
	{		
		if(ItemHeatData!=null)
		{
			String name = is.getItem().getItemNameIS(is);
			Object[] data = (Object[])ItemHeatData.get(name);
			if(data != null)
			{
				return (Float) data[4];
			} else {
				return -1;
			}
		} else {
			return -1;
		}
	}

	public static float getSpecificHeat(ItemStack is)
	{		
		if(ItemHeatData!=null)
		{
			String name = is.getItem().getItemNameIS(is);
			Object[] data = (Object[])ItemHeatData.get(name);
			if(data != null)
			{
				return (Float) data[0];
			} else {
				return 0.7F;
			}
		} else {
			return 0.7F;
		}
	}

	public static float getTempDecrease(ItemStack is)
	{
		return 0.2F * getSpecificHeat(is);
	}

	public static float GetTemperature(ItemStack is)
	{
		if(is != null)
		{
			if(is.hasTagCompound() && is.getTagCompound().hasKey("temperature"))
			{
				return is.getTagCompound().getFloat("temperature");
			}
			else 
			{
				return 0F;
			}
		} else {
			return 0F;
		}

	}

	public static float getTempIncrease(ItemStack is, float itemTemp, float fireTemp)
	{
		return 0.35F * getSpecificHeat(is);
	}

	public static void HandleContainerHeat(World world, ItemStack[] inventory, int xCoord, int yCoord, int zCoord)
	{
		float ambient = world.getWorldChunkManager().getBiomeGenAt(xCoord, zCoord).getFloatTemperature();
		ambient = ambient / 2.0F;//Normalize the value to between 0 and 1
		ambient = 62 * ambient-20;

		for(int i = 0; i < inventory.length; i++)
		{
			if(inventory[i] != null && inventory[i].hasTagCompound())
			{
				if(inventory[i].stackSize <= 0) {
					inventory[i].stackSize = 0;
				}

				NBTTagCompound comp = inventory[i].getTagCompound();
				if(comp.hasKey("temperature"))
				{
					float temp = comp.getFloat("temperature");
					if(temp > ambient)
					{
						temp -= TFCHeat.getTempDecrease(inventory[i]);
						comp.setFloat("temperature",temp);
					}
					inventory[i].setTagCompound(comp);
					if(temp <= ambient)
					{
						Collection C = comp.getTags();
						Iterator itr = C.iterator();
						while(itr.hasNext())
						{
							Object tag = itr.next();
							if(canRemoveTag(tag, "temperature", NBTTagFloat.class))
							{
								itr.remove();
								break;
							}
						}
					}
					if(comp.getTags().size() == 0)
					{
						inventory[i].stackTagCompound = null;
					}
				}
			}
		}
	}

	public static Boolean SetTemperature(ItemStack is, float Temp)
	{
		if(is != null)
		{
			if(is.hasTagCompound())
			{
				is.getTagCompound().setFloat("temperature", Temp);
			}
			else 
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setFloat("temperature", Temp);
				is.setTagCompound(nbt);
			}
		} else {
			return false;
		}

		return true;
	}
}
