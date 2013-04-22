package TFC.Handlers;

import java.util.EnumSet;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import TFC.TerraFirmaCraft;
import TFC.Core.TFC_Time;
import TFC.GUI.GuiInventoryTFC;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler
{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{

	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.contains(TickType.PLAYER))
		{
			EntityPlayer player = (EntityPlayer)tickData[0];
			World world = player.worldObj;

			if(FMLClientHandler.instance().getClient().currentScreen instanceof GuiInventory &&
					!(FMLClientHandler.instance().getClient().currentScreen instanceof GuiInventoryTFC))
			{
				player.openGui(TerraFirmaCraft.instance, 31, player.worldObj, 0, 0, 0);
			}
			//Allow the client to increment time
			TFC_Time.UpdateTime(world);
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.PLAYER, TickType.RENDER);
	}

	@Override
	public String getLabel() {
		// TODO Auto-generated method stub
		return "TFC Client";
	}

}
