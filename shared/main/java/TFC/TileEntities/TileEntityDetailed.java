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
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class TileEntityDetailed extends NetworkTileEntity
{
	public short TypeID = -1;
	public byte MetaID = 0;
	public BitSet data;

	public static final byte Packet_Update = 0;
	public static final byte Packet_Activate = 1;
	
	protected byte packetType = -1;
	
	private BitSet quads;

	public TileEntityDetailed()
	{
		data = new BitSet(512);
		quads = new BitSet(8);
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
	
	public void setQuad(int x, int y, int z)
	{
		int x1 = x >= 4 ? 1 : 0;
		int y1 = y >= 4 ? 1 : 0;
		int z1 = z >= 4 ? 1 : 0;
		
		int index = (x1 * 2 + z1) * 2 + y1;
		quads.set(index);
	}
	
	public void clearQuad(int x, int y, int z)
	{
		int x1 = x >= 4 ? 1 : 0;
		int y1 = y >= 4 ? 1 : 0;
		int z1 = z >= 4 ? 1 : 0;
		
		int index = (x1 * 2 + z1) * 2 + y1;
		quads.clear(index);
	}
	
	public boolean isQuadSolid(int x, int y, int z)
	{
		return !quads.get((x * 2 + z)*2 + y);
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
		quads.or(fromByteArray(par1NBTTagCompound.getByteArray("quads"), 8));
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
		par1NBTTagCompound.setByteArray("quads", toByteArray(quads));
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
			
			for(int subX = 0; subX < 8; subX++)
			{
				for(int subZ = 0; subZ < 8; subZ++)
				{
					for(int subY = 0; subY < 8; subY++)
					{
						if(!getBlockExists(subX, subY, subZ))
							setQuad(subX, subY, subZ);
					}
				}
			}
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
		BitSet bs = fromByteArray(b, length);
		data.or(bs);

		for(int subX = 0; subX < 8; subX++)
		{
			for(int subZ = 0; subZ < 8; subZ++)
			{
				for(int subY = 0; subY < 8; subY++)
				{
					if(!getBlockExists(subX, subY, subZ))
						setQuad(subX, subY, subZ);
				}
			}
		}
		
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
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
	
	public Packet createFullPacket()
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream outStream = new DataOutputStream(bos);	
		try {
			outStream.writeByte(PacketHandler.Packet_Init_Block_Client);
			outStream.writeInt(xCoord);
			outStream.writeInt(yCoord);
			outStream.writeInt(zCoord);
			outStream.writeShort(TypeID);
			outStream.writeByte(MetaID);
			byte[] b = toByteArray(data);
			outStream.writeInt(b.length);
			outStream.write(b);	
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	public Packet createUpdatePacket(int index)
	{
		ByteArrayOutputStream bos = new ByteArrayOutputStream(140);
		DataOutputStream outStream = new DataOutputStream(bos);	
		try {
			outStream.writeByte(PacketHandler.Packet_Data_Block_Client);
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
			outStream.writeByte(PacketHandler.Packet_Data_Block_Server);
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
