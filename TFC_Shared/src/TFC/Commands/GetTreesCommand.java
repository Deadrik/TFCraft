package TFC.Commands;

import TFC.Core.EnumTree;
import TFC.Core.Manager;
import TFC.Core.TFC_Climate;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.CommandBase;
import net.minecraft.src.EntityPlayerMP;
import net.minecraft.src.ICommandSender;
import net.minecraft.src.PlayerNotFoundException;
import net.minecraft.src.WrongUsageException;

public class GetTreesCommand extends CommandBase{

	@Override
	public String getCommandName() {
		return "gt";
	}

	@Override
	public void processCommand(ICommandSender sender, String[] params) 
	{

		MinecraftServer var3 = MinecraftServer.getServer();
		EntityPlayerMP var4;

		var4 = (EntityPlayerMP)getCommandSenderAsPlayer(sender);

		int t0ID = TFC_Climate.getTreeLayer((int)var4.posX, (int)var4.posY, (int)var4.posZ, 0);
		int t1ID = TFC_Climate.getTreeLayer((int)var4.posX, (int)var4.posY, (int)var4.posZ, 1);
		int t2ID = TFC_Climate.getTreeLayer((int)var4.posX, (int)var4.posY, (int)var4.posZ, 2);

		String t0 = EnumTree.values()[t0ID].name();
		String t1 = EnumTree.values()[t1ID].name();
		String t2 = EnumTree.values()[t2ID].name();

		throw new PlayerNotFoundException("Tree 0: "+ t0 + "   Tree 1: "+ t1 + "   Tree 2: "+ t2);

	}

}
