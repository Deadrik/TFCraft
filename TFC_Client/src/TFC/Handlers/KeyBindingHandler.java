package TFC.Handlers;

import java.util.EnumSet;

import TFC.Core.KeyBindings;
import TFC.GUI.GuiCalendar;
import TFC.Items.ItemChisel;

import net.minecraft.src.EntityClientPlayerMP;
import net.minecraft.src.KeyBinding;
import net.minecraft.src.TerraFirmaCraft;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.TickType;

public class KeyBindingHandler extends KeyBindingRegistry.KeyHandler
{
	KeyBinding Key_Calendar = new KeyBinding("Key_Calendar", 49);
	KeyBinding Key_ToolMode = new KeyBinding("Key_ToolMode", 50);

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
					&& player.getCurrentEquippedItem().getItem() instanceof ItemChisel && FMLClientHandler.instance().getClient().currentScreen == null)
			{
				ItemChisel.mode = ItemChisel.mode == 0 ? 1 : ItemChisel.mode == 1 ? 2 /*: ItemChisel.mode == 2 ? 3*/ : 0;
				String type = ItemChisel.mode == 0 ? "Smoothing" : ItemChisel.mode == 1 ? "Creating Stairs" /*: ItemChisel.mode == 3 ? "Detailing"*/ : "Creating Slabs";
				player.addChatMessage(type);

				if(player.worldObj.isRemote)
				{
					PacketHandler.sendKeyPress(0);
				}
			}
		}

	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		// TODO Auto-generated method stub

	}

	@Override
	public EnumSet<TickType> ticks() {
		// TODO Auto-generated method stub
		return EnumSet.of(TickType.WORLD, TickType.WORLDLOAD, TickType.CLIENT, TickType.PLAYER);
	}

}
