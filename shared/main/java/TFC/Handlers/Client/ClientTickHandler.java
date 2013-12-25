package TFC.Handlers.Client;

import java.util.EnumSet;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;

import TFC.Containers.ContainerTFC;
import TFC.Core.TFC_Time;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

public class ClientTickHandler implements ITickHandler
{

	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.contains(TickType.PLAYER))
		{
			EntityPlayer player = (EntityPlayer)tickData[0];
			World world = player.worldObj;

			if(Minecraft.getMinecraft().currentScreen instanceof GuiContainer && 
					((GuiContainer)Minecraft.getMinecraft().currentScreen).inventorySlots instanceof ContainerTFC)
			{
				ContainerTFC cont = (ContainerTFC)((GuiContainer)Minecraft.getMinecraft().currentScreen).inventorySlots;
				int bagNum = cont.bagsSlotNum;
				if(Keyboard.isKeyDown(bagNum+2))
				{
					player.closeScreen();
				}
			}
		}
	}

	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) 
	{
		if(type.contains(TickType.PLAYER))
		{
			EntityPlayer player = (EntityPlayer)tickData[0];
			World world = player.worldObj;

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
