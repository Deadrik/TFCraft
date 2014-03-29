package TFC.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import TFC.Core.TFC_Time;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;

public class KeyPressPacket extends AbstractPacket
{
	private int type;
	private static long keyTimer = 0;
	
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
