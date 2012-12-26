package TFC.Commands;

import TFC.TFCBlocks;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Enums.EnumTree;
import TFC.Items.ItemLooseRock;
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

public class GetRocksCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "gr";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{

		MinecraftServer var3 = MinecraftServer.getServer();
		EntityPlayerMP var4;

		var4 = (EntityPlayerMP)getCommandSenderAsPlayer(sender);

		int[] t0 = TFC_Climate.getRockLayer((int)var4.posX, (int)var4.posY, (int)var4.posZ, 0);
		int[] t1 = TFC_Climate.getRockLayer((int)var4.posX, (int)var4.posY, (int)var4.posZ, 1);
		int[] t2 = TFC_Climate.getRockLayer((int)var4.posX, (int)var4.posY, (int)var4.posZ, 2);
		
		t0[1] = getSoilMetaFromStone(t0[0], t0[1]);
		t1[1] = getSoilMetaFromStone(t1[0], t1[1]);
		t2[1] = getSoilMetaFromStone(t2[0], t2[1]);

		String t0s = ItemLooseRock.blockNames[t0[1]];
		String t1s = ItemLooseRock.blockNames[t1[1]];
		String t2s = ItemLooseRock.blockNames[t2[1]];
		
		String ore1 = "Empty";
		String ore2 = "Empty";
		String ore3 = "Empty";
		
		if(ChunkDataManager.getData((int)var4.posX >> 4, (int)var4.posZ >> 4).oreList1.size() > 0)
			ore1 = ChunkDataManager.getData((int)var4.posX >> 4, (int)var4.posZ >> 4).oreList1.get(0);
		if(ChunkDataManager.getData((int)var4.posX >> 4, (int)var4.posZ >> 4).oreList2.size() > 0)
			ore2 = ChunkDataManager.getData((int)var4.posX >> 4, (int)var4.posZ >> 4).oreList2.get(0);
		if(ChunkDataManager.getData((int)var4.posX >> 4, (int)var4.posZ >> 4).oreList3.size() > 0)
			ore3 = ChunkDataManager.getData((int)var4.posX >> 4, (int)var4.posZ >> 4).oreList3.get(0);
		
		throw new PlayerNotFoundException("Rock Layer 1: "+ t0s + "   Rock Layer 2: "+ t1s + "   Rock Layer 3: "+ t2s + 
				"   Ore1: " + ore1+ 
				"   Ore2: " + ore2+ 
				"   Ore3: " + ore3);

	}
	
	public static int getSoilMetaFromStone(int inType, int inMeta)
	{
		if(inType == TFCBlocks.StoneIgIn.blockID)
			return inMeta;
		else if(inType == TFCBlocks.StoneSed.blockID)
			return inMeta+3;
		else if(inType == TFCBlocks.StoneIgEx.blockID)
		{
			return inMeta+13;
		}
		else
			return inMeta+17;
	}

}
