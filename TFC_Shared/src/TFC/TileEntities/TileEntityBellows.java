package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import TFC.Handlers.PacketHandler;

public class TileEntityBellows extends NetworkTileEntity {

	public boolean shouldBlow = false;
	public int blowTimer = 0;
	public int blowDirection = 0;

	@Override
	public void updateEntity() {
		if(shouldBlow) {
			if(blowDirection == 0) {
				blowTimer++;
				if(blowTimer == 5) { blowDirection = 1; }
			} else {
				blowTimer--;
				if(blowTimer == -3) {
					blowDirection = 0;
					shouldBlow = false;
				}
			}
			worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		shouldBlow = nbttagcompound.getBoolean("shouldBlow");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("shouldBlow", shouldBlow);
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		this.shouldBlow = inStream.readBoolean();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
	
	public Packet createUpdatePacket() {
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeBoolean(shouldBlow);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream) throws IOException {
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException {
	}
	
}
