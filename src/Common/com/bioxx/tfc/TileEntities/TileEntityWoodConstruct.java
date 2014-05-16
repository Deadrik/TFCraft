package com.bioxx.tfc.TileEntities;

import java.util.BitSet;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

import com.bioxx.tfc.TFCItems;

public class TileEntityWoodConstruct extends TileEntity
{
	public byte[] woodTypes = new byte[192];
	public BitSet data;
	public static int PlankDetailLevel = 8;

	//This should not be stored and are calculated at runtime on the client only for faster subsequent rendering operations
	public boolean[] solidCheck = new boolean[24];

	public TileEntityWoodConstruct()
	{
		data = new BitSet(192);
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	public void ejectContents()
	{
		for(int i = 0; i < 192; i++)
		{
			if(data.get(i))
			{
				data.clear(i);
				ItemStack stack = new ItemStack(TFCItems.SinglePlank, 1, woodTypes[i]);
				EntityItem e = new EntityItem(worldObj, xCoord, yCoord, zCoord, stack);
				e.delayBeforeCanPickup = 5;
				worldObj.spawnEntityInWorld(e);
			}
		}
	}

	public static BitSet fromByteArray(byte[] bytes)
	{
		BitSet bits = new BitSet(192);
		for (int i = 0; i < bytes.length * 8; i++)
		{
			if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0)
				bits.set(i);
		}
		return bits;
	}

	public static byte[] toByteArray(BitSet bits)
	{
		byte[] bytes = new byte[bits.length() / 8 + 1];
		for (int i=0; i < bits.length(); i++)
		{
			if (bits.get(i))
				bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
		}
		return bytes;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbttc)
	{
		super.readFromNBT(nbttc);
		woodTypes = nbttc.getByteArray("woodTypes");
		data = new BitSet(192);
		data.or(fromByteArray(nbttc.getByteArray("data")));
		//prefabID = par1NBTTagCompound.getInteger("prefabID");
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound nbttc)
	{
		super.writeToNBT(nbttc);
		nbttc.setByteArray("woodTypes", woodTypes);
		nbttc.setByteArray("data", toByteArray(data));
		//par1NBTTagCompound.setInteger("prefabID", prefabID);
	}

	@Override
	public Packet getDescriptionPacket()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return new S35PacketUpdateTileEntity(xCoord, yCoord, zCoord, 1, nbt);
	}

	@Override
	public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity pkt)
	{
		readFromNBT(pkt.func_148857_g());
		updateClient();
	}

	private void updateClient()
	{
		int dd = PlankDetailLevel * PlankDetailLevel;

		for(int layer = 0; layer < 24;layer++)
		{
			this.solidCheck[layer] = true;
			int type = -1;
			for(int index = 0; index < 8 && solidCheck[layer] == true;index++)
			{
				if(!data.get((layer * 8) + index) || (type != -1 && woodTypes[(layer * 8) + index] != type))
					solidCheck[layer] = false;
				type = woodTypes[(layer * 8) + index];
			}
		}
	}




//TODO

//
//
//	@Override
//	public void handleDataPacket(DataInputStream inStream) throws IOException 
//	{
//		int index = inStream.readInt();
//		byte meta = inStream.readByte();
//
//		this.data.flip(index);
//		woodTypes[index] = meta;
//		updateClient();
//		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
//	}
//
//	@Override
//	public void createInitPacket(DataOutputStream outStream) throws IOException 
//	{
//		outStream.write(woodTypes);
//		byte[] b = toByteArray(data);
//		outStream.writeInt(b.length);
//		outStream.write(b);		
//	}
//
//	@Override
//	public void handleInitPacket(DataInputStream inStream) throws IOException 
//	{
//		inStream.readFully(woodTypes, 0, 192);
//
//		int length = inStream.readInt();
//		byte[] b = new byte[length];
//		inStream.readFully(b);
//		data.or(fromByteArray(b));
//		updateClient();
//		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
//	}
//
//	@Override
//	public void handleDataPacketServer(DataInputStream inStream)
//			throws IOException {
//
//
//	}
//
//	public Packet createUpdatePacket(int index, byte meta)
//	{
//		ByteArrayOutputStream bos = new ByteArrayOutputStream(255);
//		DataOutputStream outStream = new DataOutputStream(bos);	
//		try {
//			outStream.writeByte(PacketHandler.Packet_Data_Block_Client);
//			outStream.writeInt(xCoord);
//			outStream.writeInt(yCoord);
//			outStream.writeInt(zCoord);
//			outStream.writeInt(index);
//			outStream.writeByte(meta);
//		} catch (IOException e) {
//		}
//		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
//	}

}
