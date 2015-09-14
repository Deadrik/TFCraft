package com.bioxx.tfc.Handlers.Client;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;

import org.lwjgl.input.Keyboard;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockDetailed;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.KeyPressPacket;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemCustomHoe;

public class KeyBindingHandler
{
	//public static KeyBinding Key_Calendar = new KeyBinding("key.Calendar", Keyboard.KEY_N/*49*/, Reference.ModName);
	public static KeyBinding keyToolMode = new KeyBinding("key.ToolMode", Keyboard.KEY_M/*50*/, Reference.MOD_NAME);
	public static KeyBinding keyLockTool = new KeyBinding("key.LockTool", Keyboard.KEY_L/*38*/, Reference.MOD_NAME);

	@SubscribeEvent
	public void onKeyInput(InputEvent.KeyInputEvent event)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
		EntityClientPlayerMP player = FMLClientHandler.instance().getClient().thePlayer;

		if(FMLClientHandler.instance().getClient().inGameHasFocus &&
				FMLClientHandler.instance().getClient().thePlayer.getCurrentEquippedItem() != null &&
				FMLClientHandler.instance().getClient().currentScreen == null)
		{
			if(keyToolMode.isPressed())
			{
				if(player.getCurrentEquippedItem().getItem() instanceof ItemChisel)
				{
					pi.switchChiselMode();
					//Let's send the actual ChiselMode so the server/client does not
					//come out of sync.
					AbstractPacket pkt = new KeyPressPacket(pi.chiselMode);
					TerraFirmaCraft.PACKET_PIPELINE.sendToServer(pkt);
				}
				else if(player.getCurrentEquippedItem().getItem() instanceof ItemCustomHoe)
				{
					pi.switchHoeMode(player);
				}
			}
			else if (keyLockTool.isPressed() && pi != null)
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
