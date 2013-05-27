package TFC.Handlers.Client;

import java.util.EnumSet;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockDetailed;
import TFC.Core.KeyBindings;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.GUI.GuiCalendar;
import TFC.Handlers.PacketHandler;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemCustomHoe;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;

public class KeyBindingHandler extends KeyBindingRegistry.KeyHandler
{
	KeyBinding Key_Calendar = new KeyBinding("Key_Calendar", 49);
	KeyBinding Key_ToolMode = new KeyBinding("Key_ToolMode", 50);
	KeyBinding Key_LockTool = new KeyBinding("Key_LockTool", 38);

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
			PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();

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
				if(player.getCurrentEquippedItem().getItem() instanceof ItemChisel)
				{
					pi.switchChiselMode();
					PacketHandler.sendKeyPress(0);
				}
				else if(player.getCurrentEquippedItem().getItem() instanceof ItemCustomHoe)
				{
					pi.switchHoeMode();
				}
			}
			else if (bind.keyDescription == Key_LockTool.keyDescription && FMLClientHandler.instance().getClient().inGameHasFocus && FMLClientHandler.instance().getClient().thePlayer.getCurrentEquippedItem() != null 
					 && FMLClientHandler.instance().getClient().currentScreen == null)
			{
				if(player.getCurrentEquippedItem().getItem() instanceof ItemChisel)
				{
					if(pi.lockX == -9999999)
					{
						pi.lockX = BlockDetailed.lockX;
						pi.lockY = BlockDetailed.lockY;
						pi.lockZ = BlockDetailed.lockZ;
					}
					else
					{
						pi.lockX = -9999999;
						pi.lockY = -9999999;
						pi.lockZ = -9999999;
					}
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
