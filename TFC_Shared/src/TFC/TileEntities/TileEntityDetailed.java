package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

import TFC.*;
import TFC.Blocks.BlockDetailed;
import TFC.Core.TFC_Time;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Handlers.PacketHandler;
import TFC.Items.ItemOre;
import net.minecraft.src.Material;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;

public class TileEntityDetailed extends NetworkTileEntity
{
	public short TypeID = -1;
	public byte MetaID = 0;
	public BitSet data;

	public static final byte Packet_Update = 0;
	public static final byte Packet_Activate = 1;
	
	protected byte packetType = -1;

	public TileEntityDetailed()
	{
		data = new BitSet(512);
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}
	
	public int getType()
	{
		return TypeID;
	}
	
	public int getMeta()
	{
		return MetaID;
	}

	public boolean getBlockExists(int x, int y, int z)
	{
		return data.get((x * 8 + z)*8 + y);
	}
	
	public void setBlock(int x, int y, int z)
	{
		data.set((x * 8 + z)*8 + y);
	}
	
	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		MetaID = par1NBTTagCompound.getByte("metaID");
		TypeID = par1NBTTagCompound.getShort("typeID");
		data = new BitSet(512);
		data.or(fromByteArray(par1NBTTagCompound.getByteArray("data"), 512));
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("typeID", (short) TypeID);
		par1NBTTagCompound.setByte("metaID", MetaID);
		par1NBTTagCompound.setByteArray("data", toByteArray(data));
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		packetType = inStream.readByte();
		if(packetType == this.Packet_Update)
		{
			int index = inStream.readInt();
			data.flip(index);
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException 
	{
		outStream.writeShort(TypeID);
		outStream.writeByte(MetaID);
		byte[] b = toByteArray(data);
		outStream.writeInt(b.length);
		outStream.write(b);		
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		TypeID = inStream.readShort();
		MetaID = inStream.readByte();

		int length = inStream.readInt();
		byte[] b = new byte[length];
		inStream.readFully(b);
		data.or(fromByteArray(b, 512));

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream) throws IOException 
	{
		packetType = inStream.readByte();
		if(packetType == this.Packet_Activate)
		{
			PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer).ChiselMode = inStream.readByte();
			((BlockDetailed)TFCBlocks.Detailed).xSelected = inStream.readByte();
			((BlockDetailed)TFCBlocks.Detailed).ySelected = inStream.readByte();
			((BlockDetailed)TFCBlocks.Detailed).zSelected = inStream.readByte();
			
			((BlockDetailed)TFCBlocks.Detailed).onBlockActivatedServer(worldObj, xCoord, yCoord, zCoord, this.entityplayer, 0, 0, 0, 0);
			
			((BlockDetailed)TFCBlocks.Detailed).xSelected = -10;
			((BlockDetailed)TFCBlocks.Detailed).ySelected = -10;
			((BlockDetailed)TFCBlocks.Detailed).zSelected = -10;
		}

	}

	public Packet createUpdatePacket(int index)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream outStream = new DataOutputStream(bos);	
		try {
			outStream.writeByte(PacketHandler.Packet_Data_Client);
			outStream.writeInt(xCoord);
			outStream.writeInt(yCoord);
			outStream.writeInt(zCoord);
			outStream.writeByte(this.Packet_Update);
			outStream.writeInt(index);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	
	public Packet createActivatePacket(int xSelected, int ySelected, int zSelected)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream outStream = new DataOutputStream(bos);	
		try {
			outStream.writeByte(PacketHandler.Packet_Data_Server);
			outStream.writeInt(xCoord);
			outStream.writeInt(yCoord);
			outStream.writeInt(zCoord);
			outStream.writeByte(this.Packet_Activate);
			outStream.writeByte(PlayerManagerTFC.getInstance().getClientPlayer().ChiselMode);
			outStream.writeByte(xSelected);
			outStream.writeByte(ySelected);
			outStream.writeByte(zSelected);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	public static BitSet fromByteArray(byte[] bytes, int size) {
		BitSet bits = new BitSet(size);
		for (int i = 0; i < bytes.length * 8; i++) {
			if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0) {
				bits.set(i);
			}
		}
		return bits;
	}

	public static byte[] toByteArray(BitSet bits) {
		byte[] bytes = new byte[bits.length()/8+1];
		for (int i=0; i<bits.length(); i++) {
			if (bits.get(i)) {
				bytes[bytes.length-i/8-1] |= 1<<(i%8);
			}
		}
		return bytes;
	}

}
