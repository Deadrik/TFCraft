package com.bioxx.tfc.Handlers.Network;

import io.netty.buffer.ByteBuf;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;

public class TestPacket implements IMessage
{
	String msg;

	public TestPacket() {}

	public TestPacket(String S)
	{
		msg = S;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, msg);
		
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		msg = ByteBufUtils.readUTF8String(buffer);
	}
}
