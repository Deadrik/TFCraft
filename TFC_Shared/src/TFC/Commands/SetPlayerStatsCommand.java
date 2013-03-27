package TFC.Commands;

import java.util.List;

import TFC.Core.TFC_Climate;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.TFC_PlayerServer;
import TFC.Food.FoodStatsTFC;
import net.minecraft.server.MinecraftServer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.command.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class SetPlayerStatsCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "sps";
	}

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
            var4 = (EntityPlayerMP)getCommandSenderAsPlayer(sender);
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
            fs.waterLevel = ((int)((values[0]/100d)*fs.getMaxWater(var6.getPlayer())));
            throw new PlayerNotFoundException(values[0]+" "+values[1]+" "+values[2]);
		
	}

}
