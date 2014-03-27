package TFC.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;
import net.minecraft.tileentity.TileEntity;

public class InitBlockPacket extends AbstractPacket
{
	private int x;
	private int y;
	private int z;
	private NBTTagCompound nbtData;
	
	public InitBlockPacket(int x, int y, int z, NBTTagCompound data)
	{
		this.x = x;
		this.y = y;
		this.z = z;
		this.nbtData = data;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
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
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
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

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		TileEntity te = player.worldObj.getTileEntity(x, y, z);
		if (te != null)
			te.readFromNBT(nbtData);
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		TileEntity te = player.worldObj.getTileEntity(x, y, z);
		if (te != null)
			te.readFromNBT(nbtData);
	}

}
