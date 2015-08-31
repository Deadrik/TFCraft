package com.bioxx.tfc.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayer;

import com.bioxx.tfc.Containers.ContainerSpecialCrafting;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;

public class KnappingUpdatePacket extends AbstractPacket
{
	private byte index;

	public KnappingUpdatePacket(){}

	public KnappingUpdatePacket(int i)
	{
		index = (byte) i;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeByte(index);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		index = buffer.readByte();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		if(player.openContainer != null && player.openContainer instanceof ContainerSpecialCrafting)
		{
			((ContainerSpecialCrafting)player.openContainer).craftMatrix.setInventorySlotContents(index, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).specialCraftingTypeAlternate);
			((ContainerSpecialCrafting)player.openContainer).onCraftMatrixChanged(((ContainerSpecialCrafting)player.openContainer).craftMatrix);
		}
	}

}
