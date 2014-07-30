package com.bioxx.tfc.Handlers.Network;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class KeyPressPacket implements IMessage
{
	private int type;
	private static long keyTimer = 0; // not sure what this is for??

	public KeyPressPacket(){}

	public KeyPressPacket(int t)
	{
		type = t;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeInt(type);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		type = buffer.readInt();
	}

	public static class ServerHandler implements IMessageHandler<KeyPressPacket, IMessage> {
  
  	@Override
    public IMessage onMessage(KeyPressPacket message, MessageContext ctx)
  	{
  	  EntityPlayer player = TerraFirmaCraft.proxy.getPlayerFromMessageContext(ctx);
  		if(keyTimer + 1 < TFC_Time.getTotalTicks())
  		{
  			keyTimer = TFC_Time.getTotalTicks();
  			if (message.type == 0)//ChiselMode
  			{
  				PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
  				if(pi != null)
  					pi.switchChiselMode();
  			}
  		}
  		return null;
  	}
	}
}
