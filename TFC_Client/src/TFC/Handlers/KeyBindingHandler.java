package TFC.Handlers;

import java.util.EnumSet;

import TFC.TerraFirmaCraft;
import TFC.Core.KeyBindings;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.GUI.GuiCalendar;
import TFC.Items.*;

import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.KeyBinding;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;

public class KeyBindingHandler extends KeyBindingRegistry.KeyHandler
{
	KeyBinding Key_Calendar = new KeyBinding("Key_Calendar", 49);
	KeyBinding Key_ToolMode = new KeyBinding("Key_ToolMode", 50);
	KeyBinding Key_ChiselDepthIncrease = new KeyBinding("Key_ChiselDepthIncrease", 13);
	KeyBinding Key_ChiselDepthDecrease = new KeyBinding("Key_ChiselDepthDecrease", 12);

	private long keyTime = 0;

	public KeyBindingHandler() 
	{
		super(KeyBindings.gatherKeyBindings(), KeyBindings.gatherIsRepeating());
	}

	@Override
	public String getLabel() {
		return "TerraFirmaCraft KeyBindingHandler";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding bind,
			boolean tickEnd, boolean isRepeat) {


	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding bind, boolean tickEnd) {
		if(tickEnd)
		{
			EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;
			if (bind.keyDescription == Key_Calendar.keyDescription && FMLClientHandler.instance().getClient().inGameHasFocus && FMLClientHandler.instance().getClient().currentScreen == null)
			{
				player.openGui(TerraFirmaCraft.instance, 27, player.worldObj, 0, 0, 0);
			}
			else if (bind.keyDescription == Key_Calendar.keyDescription && FMLClientHandler.instance().getClient().currentScreen instanceof GuiCalendar)
			{
				player.closeScreen();
			}
			else if (bind.keyDescription == Key_ToolMode.keyDescription && FMLClientHandler.instance().getClient().inGameHasFocus && FMLClientHandler.instance().getClient().thePlayer.getCurrentEquippedItem() != null 
					 && FMLClientHandler.instance().getClient().currentScreen == null)
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
				if(player.getCurrentEquippedItem().getItem() instanceof ItemChisel)
				{
					pi.switchChiselMode();
					//String type = pi.ChiselMode == 0 ? "Smoothing" : pi.ChiselMode == 1 ? "Creating Stairs" : pi.ChiselMode == 3 ? "Detailing" : pi.ChiselMode == 4 ? "Finishing" :"Creating Slabs";
					//player.addChatMessage(type);

					PacketHandler.sendKeyPress(0);
				}
				else if(player.getCurrentEquippedItem().getItem() instanceof ItemCustomHoe)
				{
					pi.switchHoeMode();

					//PacketHandler.sendKeyPress(3);
				}
			}
			else if (bind.keyDescription == Key_ChiselDepthIncrease.keyDescription && FMLClientHandler.instance().getClient().inGameHasFocus && FMLClientHandler.instance().getClient().thePlayer.getCurrentEquippedItem() != null 
					&& player.getCurrentEquippedItem().getItem() instanceof ItemChisel && FMLClientHandler.instance().getClient().currentScreen == null)
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
				pi.switchIncreaseDetailZoom();

				if(player.worldObj.isRemote)
				{
					PacketHandler.sendKeyPress(1);
				}
			}
			else if (bind.keyDescription == Key_ChiselDepthDecrease.keyDescription && FMLClientHandler.instance().getClient().inGameHasFocus && FMLClientHandler.instance().getClient().thePlayer.getCurrentEquippedItem() != null 
					&& player.getCurrentEquippedItem().getItem() instanceof ItemChisel && FMLClientHandler.instance().getClient().currentScreen == null)
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
				pi.switchDecreaseDetailZoom();

				if(player.worldObj.isRemote)
				{
					PacketHandler.sendKeyPress(2);
				}
			}
		}
	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.WORLD, TickType.WORLDLOAD, TickType.CLIENT, TickType.PLAYER);
	}

}
