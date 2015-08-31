package com.bioxx.tfc.TileEntities;

import java.util.BitSet;

import net.minecraft.nbt.NBTTagCompound;

import com.bioxx.tfc.Blocks.BlockDetailed;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.api.TFCBlocks;

public class TEDetailed extends NetworkTileEntity {
	public short typeID = -1;
	public byte metaID;
	public BitSet data;
	public static final byte PACKET_UPDATE = 0;
	public static final byte PACKET_ACTIVATE = 1;
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
		return typeID;
	}

	public int getMeta() {
		return metaID;
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
		byte[] ba = toByteArray(data);
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
		metaID = nbttc.getByte("metaID");
		typeID = nbttc.getShort("typeID");
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
		par1NBTTagCompound.setShort("typeID", typeID);
		par1NBTTagCompound.setByte("metaID", metaID);
		par1NBTTagCompound.setByteArray("data", toByteArray(data));
		par1NBTTagCompound.setByteArray("quads", toByteArray(quads));
	}

	@Override
	public void handleInitPacket(NBTTagCompound nbt) {
		typeID = nbt.getShort("typeID");
		metaID = nbt.getByte("metaID");
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
		if (packetType == TEDetailed.PACKET_UPDATE) {
			int index = nbt.getInteger("index");
			data.set(index, false);

			for (int subX = 0; subX < 8; subX++) {
				for (int subZ = 0; subZ < 8; subZ++) {
					for (int subY = 0; subY < 8; subY++) {
						if (!getBlockExists(subX, subY, subZ))
							setQuad(subX, subY, subZ);
					}
				}
			}

			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		} else if (packetType == TEDetailed.PACKET_ACTIVATE && !worldObj.isRemote) {
			PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(entityplayer).chiselMode = nbt.getByte("chiselMode");
			((BlockDetailed) TFCBlocks.detailed).xSelected = nbt.getByte("xSelected");
			((BlockDetailed) TFCBlocks.detailed).ySelected = nbt.getByte("ySelected");
			((BlockDetailed) TFCBlocks.detailed).zSelected = nbt.getByte("zSelected");

			((BlockDetailed) TFCBlocks.detailed).onBlockActivatedServer(worldObj, xCoord, yCoord, zCoord, this.entityplayer, 0, 0, 0, 0);

			((BlockDetailed) TFCBlocks.detailed).xSelected = -10;
			((BlockDetailed) TFCBlocks.detailed).ySelected = -10;
			((BlockDetailed) TFCBlocks.detailed).zSelected = -10;
		}

	}

	@Override
	public void createDataNBT(NBTTagCompound nbt) {
		packetType = nbt.getByte("packetType");
		/*if (packetType == TEDetailed.Packet_Update) {
			//The data for this is already set in BlockDetailed onBlockActivatedServer()
		} else */if (packetType == TEDetailed.PACKET_ACTIVATE)
		{
			nbt.setByte("chiselMode", PlayerManagerTFC.getInstance().getClientPlayer().chiselMode);
			/*We've already added the xSelected, ySelected, and zSelected bytes to the nbt in the
			 * BlockDetailed onBlockActivated().*/
		}
	}

	@Override
	public void createInitNBT(NBTTagCompound nbt) {
		nbt.setShort("typeID", typeID);
		nbt.setByte("metaID", metaID);
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

	public static BitSet turnCube(byte[] bytes, int xAngle, int yAngle, int zAngle) {
		if (xAngle == 0 && yAngle == 0 && zAngle == 0)
			return fromByteArray(bytes, 512);

		BitSet data = fromByteArray(bytes, 512);
		BitSet turnedData = new BitSet(512);

		int xCoord, yCoord, zCoord;
		for (int x = 0; x < 8; ++x)
			for (int z = 0; z < 8; ++z)
				for (int y = 0; y < 8; ++y) {
					xCoord = x; yCoord = y; zCoord = z;

					// X:
					for (int i = 0; i < xAngle; i += 90)
					{
						int buf = yCoord;
						yCoord = 7 - zCoord;
						zCoord = buf;
					}
					// Z:
					for (int i = 0; i < zAngle; i += 90)
					{
						int buf = xCoord;
						xCoord = 7 - yCoord;
						yCoord = buf;
					}
					// Y:
					for (int i = 0; i < yAngle; i += 90)
					{
						int buf = zCoord;
						zCoord = 7 - xCoord;
						xCoord = buf;
					}

					int srcI = (x * 8 + z) * 8 + y;
					int resI = (xCoord * 8 + zCoord) * 8 + yCoord;
					turnedData.set(resI, data.get(srcI));
				}

		return turnedData;
	}
}