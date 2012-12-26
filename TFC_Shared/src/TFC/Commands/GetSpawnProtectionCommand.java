package TFC.Commands;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Climate;
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

public class GetSpawnProtectionCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "gsp";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{
		if(sender.canCommandSenderUseCommand(0, sender.getCommandSenderName()))
		{
            MinecraftServer var3 = MinecraftServer.getServer();
            EntityPlayerMP var4;
            
             var4 = (EntityPlayerMP)getCommandSenderAsPlayer(sender);
             
             int x = (int)var4.posX >> 4;
             int z = (int)var4.posZ >> 4;
             
             ChunkData d = (ChunkData) ChunkDataManager.chunkmap.get(x+","+z);

             if(d != null)
            	 throw new PlayerNotFoundException("SP: " + d.getSpawnProtectionWithUpdate());
             else
            	 throw new PlayerNotFoundException("Unable to find ChunkData for "+x+","+z);
		}
		
	}

}
