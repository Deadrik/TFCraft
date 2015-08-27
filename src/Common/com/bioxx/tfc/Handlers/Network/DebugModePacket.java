package com.bioxx.tfc.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayer;

import com.bioxx.tfc.api.TFCOptions;

public class DebugModePacket extends AbstractPacket
{
	private boolean debugMode;

	public DebugModePacket() {}
	
	public DebugModePacket(EntityPlayer p)
	{
		this.debugMode = TFCOptions.enableDebugMode;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeBoolean(this.debugMode);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.debugMode = buffer.readBoolean();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		TFCOptions.enableDebugMode = this.debugMode;
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}

}
