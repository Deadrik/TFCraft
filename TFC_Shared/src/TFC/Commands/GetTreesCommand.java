package TFC.Commands;

import TFC.Core.TFC_Climate;
import TFC.Enums.EnumTree;
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
