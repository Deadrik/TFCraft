package com.bioxx.tfc.TileEntities;

import java.util.BitSet;

import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.BlockDetailed;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;

public class TEDetailed extends NetworkTileEntity {
	public short TypeID = -1;
	public byte MetaID = 0;
	public BitSet data;
	public static final byte Packet_Update = 0;
	public static final byte Packet_Activate = 1;
	protected byte packetType = -1;
	private BitSet quads;

	public TEDetailed() {
		data = new BitSet(512);
		quads = new BitSet(8);
	}

	@Override
	public boolean canUpdate() {
		return false;
	}

	public int getType() {
		return TypeID;
	}

	public int getMeta() {
		return MetaID;
	}

	public boolean getBlockExists(int x, int y, int z) {
		return data.get((x * 8 + z) * 8 + y);
	}

	public void setBlock(int x, int y, int z) {
		data.set((x * 8 + z) * 8 + y);
	}

	public void setQuad(int x, int y, int z) {
		int x1 = x >= 4 ? 1 : 0;
		int y1 = y >= 4 ? 1 : 0;
		int z1 = z >= 4 ? 1 : 0;
		int index = (x1 * 2 + z1) * 2 + y1;
		quads.set(index);
	}

	public void clearQuad(int x, int y, int z) {
		int x1 = x >= 4 ? 1 : 0;
		int y1 = y >= 4 ? 1 : 0;
		int z1 = z >= 4 ? 1 : 0;
		int index = (x1 * 2 + z1) * 2 + y1;
		quads.clear(index);
	}

	public boolean isQuadSolid(int x, int y, int z) {
		return !quads.get((x * 2 + z) * 2 + y);
	}

	public boolean isBlockEmpty() {
		byte[] ba = data.toByteArray();
		for (byte b : ba) {
			if (b != -1)
				return false;
		}
		return true;
	}

	/**
	 * Reads a tile entity from NBT.
	 */
	@Override
	public void readFromNBT(NBTTagCompound nbttc) {
		super.readFromNBT(nbttc);
		MetaID = nbttc.getByte("metaID");
		TypeID = nbttc.getShort("typeID");
		data = new BitSet(512);
		data.or(fromByteArray(nbttc.getByteArray("data"), 512));
		quads.or(fromByteArray(nbttc.getByteArray("quads"), 8));
	}

	/**
	 * Writes a tile entity to NBT.
	 */
	@Override
	public void writeToNBT(NBTTagCompound par1NBTTagCompound) {
		super.writeToNBT(par1NBTTagCompound);
		par1NBTTagCompound.setShort("typeID", TypeID);
		par1NBTTagCompound.setByte("metaID", MetaID);
		par1NBTTagCompound.setByteArray("data", toByteArray(data));
		par1NBTTagCompound.setByteArray("quads", toByteArray(quads));
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		TypeID = nbt.getShort("typeID");
		MetaID = nbt.getByte("metaID");
		data = new BitSet(512);
		data.or(fromByteArray(nbt.getByteArray("data"), 512));

		for (int subX = 0; subX < 8; subX++) {
			for (int subZ = 0; subZ < 8; subZ++) {
				for (int subY = 0; subY < 8; subY++) {
					if (!getBlockExists(subX, subY, subZ))
						setQuad(subX, subY, subZ);
				}
			}
		}

		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	@Override
	public void handleDataPacket(NBTTagCompound nbt) {
		packetType = nbt.getByte("packetType");
		if (packetType == TEDetailed.Packet_Update) {
			int index = nbt.getInteger("index");
			data.flip(index);

			for (int subX = 0; subX < 8; subX++) {
				for (int subZ = 0; subZ < 8; subZ++) {
					for (int subY = 0; subY < 8; subY++) {
						if (!getBlockExists(subX, subY, subZ))
							setQuad(subX, subY, subZ);
					}
				}
			}

			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		} else if (packetType == TEDetailed.Packet_Activate && !worldObj.isRemote) {
			PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer).ChiselMode = nbt.getByte("chiselMode");
			((BlockDetailed) TFCBlocks.Detailed).xSelected = nbt.getByte("xSelected");
			((BlockDetailed) TFCBlocks.Detailed).ySelected = nbt.getByte("ySelected");
			((BlockDetailed) TFCBlocks.Detailed).zSelected = nbt.getByte("zSelected");

			((BlockDetailed) TFCBlocks.Detailed).onBlockActivatedServer(worldObj, xCoord, yCoord, zCoord, this.entityplayer, 0, 0, 0, 0);

			((BlockDetailed) TFCBlocks.Detailed).xSelected = -10;
			((BlockDetailed) TFCBlocks.Detailed).ySelected = -10;
			((BlockDetailed) TFCBlocks.Detailed).zSelected = -10;
		}

	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
		packetType = nbt.getByte("packetType");
		if (packetType == TEDetailed.Packet_Update) {
			/*The data for this is already set in BlockDetailed onBlockActivatedServer()*/
		} else if (packetType == TEDetailed.Packet_Activate) {
			nbt.setByte("chiselMode", PlayerManagerTFC.getInstance().getClientPlayer().ChiselMode);
			/*We've already added the xSelected, ySelected, and zSelected bytes to the nbt in the
			 * BlockDetailed onBlockActivated().*/
		}
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setShort("typeID", TypeID);
		nbt.setByte("metaID", MetaID);
		nbt.setByteArray("data", toByteArray(data));
	}

	public static BitSet fromByteArray(byte[] bytes, int size) {
		BitSet bits = new BitSet(size);
		for (int i = 0; i < bytes.length * 8; i++) {
			if ((bytes[bytes.length - i / 8 - 1] & (1 << (i % 8))) > 0)
				bits.set(i);
		}
		return bits;
	}

	public static byte[] toByteArray(BitSet bits) {
		byte[] bytes = new byte[bits.length() / 8 + 1];
		for (int i = 0; i < bits.length(); i++) {
			if (bits.get(i))
				bytes[bytes.length - i / 8 - 1] |= 1 << (i % 8);
		}
		return bytes;
	}

	public static BitSet turnCube(byte[] bytes, int x_angle, int y_angle, int z_angle) {
		if (x_angle == 0 && y_angle == 0 && z_angle == 0)
			return fromByteArray(bytes, 512);

		BitSet data = fromByteArray(bytes, 512);
		BitSet turned_data = new BitSet(512);

		int _x, _y, _z;
		for (int x = 0; x < 8; ++x)
			for (int z = 0; z < 8; ++z)
				for (int y = 0; y < 8; ++y) {
					_x = x; _y = y; _z = z;

					// X:
					for (int i = 0; i < x_angle; i += 90)
					{
						int buf = _y;
						_y = 7 - _z;
						_z = buf;
					}
					// Z:
					for (int i = 0; i < z_angle; i += 90)
					{
						int buf = _x;
						_x = 7 - _y;
						_y = buf;
					}
					// Y:
					for (int i = 0; i < y_angle; i += 90)
					{
						int buf = _z;
						_z = 7 - _x;
						_x = buf;
					}

					int src_i = (x * 8 + z) * 8 + y;
					int res_i = (_x * 8 + _z) * 8 + _y;
					turned_data.set(res_i, data.get(src_i));
				}

		return turned_data;
	}
}