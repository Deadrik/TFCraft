package com.bioxx.tfc.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.gen.feature.WorldGenerator;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.WorldGen.TFCBiome;
import com.bioxx.tfc.WorldGen.Generators.WorldGenFissure;
import com.bioxx.tfc.WorldGen.Generators.Trees.WorldGenCustomFruitTree;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCOptions;

public class GenCommand extends CommandBase
{
	@Override
	public String getCommandName()
	{
		return "gen";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params)
	{
		EntityPlayerMP player = getCommandSenderAsPlayer(sender);

		if(!TFCOptions.enableDebugMode)
		{
			TFC_Core.sendInfoMessage(player, new ChatComponentText("Debug Mode Required"));
			return;
		}

		if (params.length == 1)
		{
			if (params[0].equalsIgnoreCase("fruittree"))
			{
				TFC_Core.sendInfoMessage(player, new ChatComponentText("Generating Fruit Tree"));
				WorldGenerator fruitGen = new WorldGenCustomFruitTree(false, TFCBlocks.fruitTreeLeaves, 0);

				if (!fruitGen.generate(sender.getEntityWorld(), sender.getEntityWorld().rand, (int) player.posX, (int) player.posY, (int) player.posZ))
					TFC_Core.sendInfoMessage(player, new ChatComponentText("Generation Failed"));
			}
		}
		else if (params.length == 2)
		{
			if(params[0].equals("fissure"))
			{
				WorldGenFissure gen = null;
				if(params[1].equals("water"))
				{
					gen = new WorldGenFissure(TFCBlocks.FreshWater);
					gen.checkStability = false;
					TFC_Core.sendInfoMessage(player, new ChatComponentText("Generating Water"));
				}
				else if(params[1].equals("hotwater"))
				{
					gen = new WorldGenFissure(TFCBlocks.HotWater);
					gen.checkStability = false;
					TFC_Core.sendInfoMessage(player, new ChatComponentText("Generating Hot Springs"));
				}
				else
				{
					gen = new WorldGenFissure(null);
					gen.checkStability = false;
					TFC_Core.sendInfoMessage(player, new ChatComponentText("Generating Fissure"));
				}
				gen.generate(sender.getEntityWorld(), sender.getEntityWorld().rand, (int)player.posX, (int)player.posY - 1, (int)player.posZ);
			}
			else if (params[0].equalsIgnoreCase("tree"))
			{
				int i = getTree(params[1]);

				if (i != -1)
				{
					TFC_Core.sendInfoMessage(player, new ChatComponentText("Generating Small " + params[1] + " Tree"));
					WorldGenerator treeGen = TFCBiome.getTreeGen(i, false);
					if (!treeGen.generate(sender.getEntityWorld(), sender.getEntityWorld().rand, (int) player.posX, (int) player.posY, (int) player.posZ))
						TFC_Core.sendInfoMessage(player, new ChatComponentText("Generation Failed"));
				}
				else
					TFC_Core.sendInfoMessage(player, new ChatComponentText("Invalid Tree"));
			}
		}
		else if (params.length == 3 && (params[0].equalsIgnoreCase("tree") && params[2].equalsIgnoreCase("big")))
		{
			int i = getTree(params[1]);

			if (i != -1)
			{
				TFC_Core.sendInfoMessage(player, new ChatComponentText("Generating Big " + params[1] + " Tree"));
				WorldGenerator treeGen = TFCBiome.getTreeGen(i, false);
				if (!treeGen.generate(sender.getEntityWorld(), sender.getEntityWorld().rand, (int) player.posX, (int) player.posY, (int) player.posZ))
					TFC_Core.sendInfoMessage(player, new ChatComponentText("Generation Failed"));
			}
			else
				TFC_Core.sendInfoMessage(player, new ChatComponentText("Invalid Tree"));
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender)
	{
		return "";
	}

	public int getTree(String tree)
	{
		if (tree.equalsIgnoreCase("oak"))
			return 0;
		else if (tree.equalsIgnoreCase("aspen"))
			return 1;
		else if (tree.equalsIgnoreCase("birch"))
			return 2;
		else if (tree.equalsIgnoreCase("chestnut"))
			return 3;
		else if (tree.equalsIgnoreCase("douglasfir"))
			return 4;
		else if (tree.equalsIgnoreCase("hickory"))
			return 5;
		else if (tree.equalsIgnoreCase("maple"))
			return 6;
		else if (tree.equalsIgnoreCase("ash"))
			return 7;
		else if (tree.equalsIgnoreCase("pine"))
			return 8;
		else if (tree.equalsIgnoreCase("sequoia"))
			return 9;
		else if (tree.equalsIgnoreCase("spruce"))
			return 10;
		else if (tree.equalsIgnoreCase("sycamore"))
			return 11;
		else if (tree.equalsIgnoreCase("whitecedar"))
			return 12;
		else if (tree.equalsIgnoreCase("whiteelm"))
			return 13;
		else if (tree.equalsIgnoreCase("willow"))
			return 14;
		else if (tree.equalsIgnoreCase("kapok"))
			return 15;
		else if (tree.equalsIgnoreCase("acacia"))
			return 16;
		else
			return -1;
	}
}
