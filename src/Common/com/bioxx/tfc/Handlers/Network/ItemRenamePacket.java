package com.bioxx.tfc.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.network.ByteBufUtils;

public class ItemRenamePacket extends AbstractPacket
{
	private String name;

	public ItemRenamePacket(){}

	public ItemRenamePacket(String s)
	{
		name = s;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, name);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		name = ByteBufUtils.readUTF8String(buffer);
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		player.inventory.getCurrentItem().stackTagCompound.setString("ItemName", name);
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		player.inventory.getCurrentItem().stackTagCompound.setString("ItemName", name);
	}

}
