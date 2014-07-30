package com.bioxx.tfc.Handlers.Network;

import com.bioxx.tfc.TerraFirmaCraft;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class ItemRenamePacket implements IMessage
{
	private String name;

	public ItemRenamePacket(){}

	public ItemRenamePacket(String S)
	{
		name = S;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		ByteBufUtils.writeUTF8String(buffer, name);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		name = ByteBufUtils.readUTF8String(buffer);
	}

	public static class ClientHandler implements IMessageHandler<ItemRenamePacket, IMessage> {

    @Override
    public IMessage onMessage(ItemRenamePacket message, MessageContext ctx) {
      EntityPlayer player = TerraFirmaCraft.proxy.getPlayerFromMessageContext(ctx);
      player.inventory.getCurrentItem().stackTagCompound.setString("Name", message.name);
      return null;
    }
	}
}
