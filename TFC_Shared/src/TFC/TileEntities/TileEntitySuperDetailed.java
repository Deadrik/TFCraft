package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.BitSet;

import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.Packet;
import TFC.TFCBlocks;
import TFC.Blocks.BlockDetailed;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Handlers.PacketHandler;

public class TileEntitySuperDetailed extends TileEntityDetailed
{
	public int[] blockIndex = {-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1,-1};
	public BitSet superData = new BitSet(2048);
	
	public static final byte Packet_Update_Index = 2;
	
	/**
	 * Reads a tile entity from NBT.
	 */
	public void readFromNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.readFromNBT(par1NBTTagCompound);
		superData = new BitSet(2048);
		superData.or(fromByteArray(par1NBTTagCompound.getByteArray("superData"), 2048));
		blockIndex = par1NBTTagCompound.getIntArray("index");
		
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	public void writeToNBT(NBTTagCompound par1NBTTagCompound)
	{
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setByteArray("superData", toByteArray(superData));
		par1NBTTagCompound.setIntArray("index", blockIndex);		
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{
		packetType = inStream.readByte();
		if(packetType == this.Packet_Update)
		{
			int index = inStream.readInt();
			data.flip(index);
		}
		else if(packetType == this.Packet_Update_Index)
		{
			blockIndex[inStream.readInt()] = inStream.readByte();
		}
		
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException 
	{
		super.createInitPacket(outStream);		
		byte[] b = toByteArray(superData);
		outStream.writeInt(b.length);
		outStream.write(b);		
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		super.handleInitPacket(inStream);

		int length = inStream.readInt();
		byte[] b = new byte[length];
		inStream.readFully(b);
		superData.or(fromByteArray(b, 2048));

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream) throws IOException 
	{
		//super.handleDataPacketServer(inStream);
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

	public Packet createUpdatePacket(int index, byte ind)
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
			outStream.writeByte(ind);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	
	public Packet createUpdateIndexPacket(int index, short data)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream outStream = new DataOutputStream(bos);	
		try {
			outStream.writeByte(PacketHandler.Packet_Data_Client);
			outStream.writeInt(xCoord);
			outStream.writeInt(yCoord);
			outStream.writeInt(zCoord);
			outStream.writeByte(this.Packet_Update_Index);
			outStream.writeInt(index);
			outStream.writeShort(data);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	
	public Packet createSuperActivatePacket(int xSelected, int ySelected, int zSelected, short indexData)
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
			outStream.writeShort(indexData);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	
	public int getMetaFromIndex(int i)
	{
		return blockIndex[i] & 0xf;
	}
	
	public int getIdFromIndex(int i)
	{
		return blockIndex[i] >> 4;
	}
	
	public int getMeta(int i)
	{
		return i & 0xf;
	}
	
	public int getId(int i)
	{
		return i >> 4;
	}
	
	public int setMeta(int i, int meta)
	{
		int t = getId(i);
		return (t << 4) + meta;
	}
	
	public int setId(int i, int id)
	{
		int meta = getMeta(i);
		return (id << 4) + meta;
	}
	
	public int setIdAndMeta(int id, int meta)
	{
		int i = 0;
		i = setId(i,id);
		i = setMeta(i,meta);
		return i;
	}

	public byte getIndex(int x, int y, int z)
	{
		int index = ((x * 8 + z) * 8 + y) * 4;
		BitSet bs = superData.get(index, index+3);
		byte[] bsa = bs.toByteArray();
		byte out = bsa.length > 0 ? bsa[0] : 0;
		return out;
	}
	
	public byte getBlockIndex(int i)
	{
		int index = i * 4;
		BitSet bs = superData.get(index, index+3);
		byte[] bsa = bs.toByteArray();
		byte out = bsa.length > 0 ? bsa[0] : 0;
		return out;
	}
	
	public void setIndex(int x, int y, int z, byte i)
	{
		int index = ((x * 8 + z) * 8 + y) * 4;
		boolean b1 = (i & 0x1) > 0;
		boolean b2 = ((i>>1) & 0x1) > 0;
		boolean b3 = ((i>>2) & 0x1) > 0;
		boolean b4 = ((i>>3) & 0x1) > 0;
		
		superData.clear(index, index+3);
		superData.set(index, b1);
		superData.set(index+1, b2);
		superData.set(index+2, b3);
		superData.set(index+3, b4);
	}
	
	public byte createIndex(int x, int y, int z, short i)
	{
		byte emptyIndex = -1;
		
		for(byte j = 0; j < 16; j++)
		{
			if(blockIndex[j] == i)
				return j;
			if(emptyIndex == -1)
				emptyIndex = j;
		}
		
		if(emptyIndex != -1)
		{
			blockIndex[emptyIndex] = i;
			broadcastPacketInRange(createUpdateIndexPacket(emptyIndex, i));
		}

		return emptyIndex;
	}
	
	public void setBlock(int x, int y, int z, int index)
	{
		superData.set((x * 8 + z)*8 + y);
	}
}
