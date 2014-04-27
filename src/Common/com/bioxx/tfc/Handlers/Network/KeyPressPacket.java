package com.bioxx.tfc.Handlers.Network;

import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

public class KeyPressPacket extends AbstractPacket
{
	private int type;
	private static long keyTimer = 0; // not sure what this is for??

	public KeyPressPacket(){}

	public KeyPressPacket(int t)
	{
		type = t;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeInt(type);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		type = buffer.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		if(keyTimer + 1 < TFC_Time.getTotalTicks())
		{
			keyTimer = TFC_Time.getTotalTicks();
			if(type == 0)//ChiselMode
			{
				PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
				if(pi != null)
					pi.switchChiselMode();
			}
		}
	}

}
