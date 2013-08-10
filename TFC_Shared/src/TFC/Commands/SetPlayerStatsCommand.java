package TFC.Commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import TFC.Core.TFC_Core;
import TFC.Food.FoodStatsTFC;

public class SetPlayerStatsCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "sps";
	}

	@Override
	public List addTabCompletionOptions(ICommandSender par1ICommandSender, String[] par2ArrayOfStr)
    {
        return getListOfStringsMatchingLastWord(par2ArrayOfStr, MinecraftServer.getServer().getAllUsernames());
    }
	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{

            MinecraftServer var3 = MinecraftServer.getServer();
            EntityPlayerMP var4;
            EntityPlayerMP player;
            double[] values = new double[3];
            var4 = getCommandSenderAsPlayer(sender);
            if(params.length == 4 || params.length == 3){
            	for(int i = 0;i<3;i++){
            		try{
            			values[i] = Double.parseDouble(params[i+(params.length-3)]);
            		}catch(NumberFormatException e){
            			throw new PlayerNotFoundException("Invalid");
            		}
            		if(values[i]<0||values[i]>100){
            			throw new PlayerNotFoundException("Invalid");
            		}
            	}
            }
            player = var4;     
            if(params.length == 4){
            	try{
            	player  = func_82359_c(sender, params[0]);
            	}catch(PlayerNotFoundException e){
            		throw new PlayerNotFoundException("Unkown Player");
            	}
            }
            if(player == null)throw new PlayerNotFoundException("Invalid");
            FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
            player.setEntityHealth((int)((values[0]/100d)*player.getMaxHealth()));
            fs.setFoodLevel((int)values[1]);
            fs.waterLevel = ((int)((values[2]/100d)*fs.getMaxWater(player)));
            throw new PlayerNotFoundException(values[0]+" "+values[1]+" "+values[2]);
		
	}

}
