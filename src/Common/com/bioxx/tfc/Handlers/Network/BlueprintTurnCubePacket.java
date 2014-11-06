package com.bioxx.tfc.Handlers.Network;

import com.bioxx.tfc.TileEntities.TEDetailed;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.PacketBuffer;

public class BlueprintTurnCubePacket extends AbstractPacket {
	private int x_a, y_a, z_a;

	public BlueprintTurnCubePacket() { x_a = y_a = z_a = 0; }

	public BlueprintTurnCubePacket(int x_angle, int y_angle, int z_angle) {
		x_a = x_angle;
		y_a = y_angle;
		z_a = z_angle;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		PacketBuffer pb = new PacketBuffer(buffer);
		pb.writeInt(x_a);
		pb.writeInt(y_a);
		pb.writeInt(z_a);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer) {
		PacketBuffer pb = new PacketBuffer(buffer);
		x_a = pb.readInt();
		y_a = pb.readInt();
		z_a = pb.readInt();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		ItemStack stack = player.inventory.getCurrentItem();
		byte[] bytes = stack.stackTagCompound.getByteArray("data");
		byte[] turned_bytes = TEDetailed.turnCube(bytes, x_a, y_a, z_a);
		stack.stackTagCompound.setByteArray("data", turned_bytes);
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		ItemStack stack = player.inventory.getCurrentItem();
		byte[] bytes = stack.stackTagCompound.getByteArray("data");
		byte[] turned_bytes = TEDetailed.turnCube(bytes, x_a, y_a, z_a);
		stack.stackTagCompound.setByteArray("data", turned_bytes);
	}
}
