package TFC.Commands;

import TFC.Core.Manager;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Enums.EnumTree;
import TFC.Items.ItemLooseRock;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.PlayerNotFoundException;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.WrongUsageException;

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

		throw new PlayerNotFoundException("Rock Layer 1: "+ t0s + "   Rock Layer 2: "+ t1s + "   Rock Layer 3: "+ t2s);

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
