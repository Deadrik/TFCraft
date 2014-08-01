package com.bioxx.tfc.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.TileEntities.NetworkTileEntity;

import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class DataBlockPacket implements IMessage
{
	private int x;
	private int y;
	private int z;
	private NBTTagCompound nbtData;

	public DataBlockPacket() {}

	public DataBlockPacket(int x, int y, int z, NBTTagCompound data)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.nbtData = data;
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		PacketBuffer pb = new PacketBuffer(buffer);
		pb.writeInt(x);
		pb.writeShort(y);
		pb.writeInt(z);
		try
		{
			pb.writeNBTTagCompoundToBuffer(nbtData);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		PacketBuffer pb = new PacketBuffer(buffer);
		x = pb.readInt();
		y = pb.readShort();
		z = pb.readInt();
		try
		{
			nbtData = pb.readNBTTagCompoundFromBuffer();
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	public static class UniversalHandler implements IMessageHandler<DataBlockPacket, IMessage> {

    @Override
    public IMessage onMessage(DataBlockPacket message, MessageContext ctx) {
      EntityPlayer player = TerraFirmaCraft.proxy.getPlayerFromMessageContext(ctx);
      NetworkTileEntity te = (NetworkTileEntity)player.worldObj.getTileEntity(message.x, message.y, message.z);
      if (te != null)
      {
        te.entityplayer = player;
        te.handleDataPacket(message.nbtData);
      }
      return null;
    }
	}
}
