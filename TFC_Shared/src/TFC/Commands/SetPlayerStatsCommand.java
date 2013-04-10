package TFC.Commands;

import java.util.List;

import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.command.PlayerNotFoundException;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.server.MinecraftServer;
import TFC.Core.Player.TFC_PlayerServer;
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
            EntityPlayerMP var5;
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
            var5 = var4;     
            if(params.length == 4){
            	try{
            	var5  = func_82359_c(sender, params[0]);
            	}catch(PlayerNotFoundException e){
            		throw new PlayerNotFoundException("Unkown Player");
            	}
            }
            if(var5 == null)throw new PlayerNotFoundException("Invalid");
            TFC_PlayerServer var6 = TFC_PlayerServer.getFromEntityPlayer(var5);
            FoodStatsTFC fs = var6.getFoodStatsTFC();
            var6.getPlayer().setHealthField((int)((values[0]/100d)*var6.getMaxHealth()));
            fs.setFoodLevel((int)values[1]);
            fs.waterLevel = ((int)((values[2]/100d)*fs.getMaxWater(var6.getPlayer())));
            throw new PlayerNotFoundException(values[0]+" "+values[1]+" "+values[2]);
		
	}

}
