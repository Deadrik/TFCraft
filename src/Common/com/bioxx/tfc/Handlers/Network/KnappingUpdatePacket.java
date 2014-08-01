package com.bioxx.tfc.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Containers.ContainerSpecialCrafting;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class KnappingUpdatePacket implements IMessage
{
	private byte index;

	public KnappingUpdatePacket(){}

	public KnappingUpdatePacket(int i)
	{
		index = (byte) i;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeByte(index);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		index = buffer.readByte();
	}

	public static class ServerHandler implements IMessageHandler<KnappingUpdatePacket, IMessage> {

    @Override
    public IMessage onMessage(KnappingUpdatePacket message, MessageContext ctx) {
      EntityPlayer player = TerraFirmaCraft.proxy.getPlayerFromMessageContext(ctx);
      if(player.openContainer != null && player.openContainer instanceof ContainerSpecialCrafting)
      {
        ((ContainerSpecialCrafting)player.openContainer).craftMatrix.setInventorySlotContents(message.index, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).specialCraftingTypeAlternate);
        ((ContainerSpecialCrafting)player.openContainer).onCraftMatrixChanged(((ContainerSpecialCrafting)player.openContainer).craftMatrix);
      }
      return null;
    }
	}

}
