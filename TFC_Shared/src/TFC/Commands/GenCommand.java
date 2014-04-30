package TFC.Commands;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayerMP;
import TFC.TFCBlocks;
import TFC.API.TFCOptions;
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
		}
		else
			player.addChatMessage("Command must be /gen fissure water or /gen fissure empty");
	}

	@Override
	public String getCommandUsage(ICommandSender icommandsender) 
	{
		return null;
	}

}
