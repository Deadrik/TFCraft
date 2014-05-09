package TFC.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.gen.feature.WorldGenerator;
import TFC.TFCBlocks;
import TFC.API.TFCOptions;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.Generators.WorldGenFissure;

public class GenCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "gen";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		if(!TFCOptions.enableDebugMode)
			return;

		EntityPlayerMP player;

		player = getCommandSenderAsPlayer(sender);

		if(params.length == 2)
		{
			if(params[0].equals("fissure"))
			{
				WorldGenFissure gen = null;
				if(params[1].equals("water"))
				{
					gen = new WorldGenFissure(TFCBlocks.HotWaterStill);
					player.addChatMessage("Generating Hot Springs");
				}
				else
				{
					gen = new WorldGenFissure(null);
					player.addChatMessage("Generating Fissure");
				}
				gen.generate(sender.getEntityWorld(), sender.getEntityWorld().rand, (int)player.posX, (int)player.posY-1, (int)player.posZ);
			}
			else if (params[0].equalsIgnoreCase("tree"))
			{
				int i = getTree(params[1]);

				if (i != -1)
				{
					player.addChatMessage("Generating Small " + params[1] + " Tree");
					WorldGenerator treeGen = TFCBiome.getTreeGen(i, false);
					if (!treeGen.generate(sender.getEntityWorld(), sender.getEntityWorld().rand, (int) player.posX, (int) player.posY, (int) player.posZ))
						player.addChatMessage("Generation Failed");
				}
				else
					player.addChatMessage("Invalid Tree");
			}
		}
		else if (params.length == 3 && (params[0].equalsIgnoreCase("tree") && params[2].equalsIgnoreCase("big")))
		{
			int i = getTree(params[1]);

			if (i != -1)
			{
				player.addChatMessage("Generating Big " + params[1] + " Tree");
				WorldGenerator treeGen = TFCBiome.getTreeGen(i, false);
				if (!treeGen.generate(sender.getEntityWorld(), sender.getEntityWorld().rand, (int) player.posX, (int) player.posY, (int) player.posZ))
					player.addChatMessage("Generation Failed");
			}
			else
				player.addChatMessage("Invalid Tree");
		}
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return null;
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
