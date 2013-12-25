package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.packet.Packet;
import TFC.TerraFirmaCraft;
import TFC.Handlers.PacketHandler;

public class TileEntityFenceGate extends NetworkTileEntity {

	private boolean open = false;
	private byte direction = 0;

	public void setOpen(boolean value){
		open = value;
		TerraFirmaCraft.proxy.sendCustomPacket(createUpdatePacket());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public void setDirection(byte value){
		direction = value;
		TerraFirmaCraft.proxy.sendCustomPacket(createUpdatePacket());
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public boolean getOpen(){
		return open;
	}

	public byte getDirection(){
		return direction;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound) {
		super.readFromNBT(nbttagcompound);
		open = nbttagcompound.getBoolean("open");
		direction = nbttagcompound.getByte("dir");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound) {
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setBoolean("open", open);
		nbttagcompound.setByte("dir", direction);
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		this.open = inStream.readBoolean();
		this.direction = inStream.readByte();
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
			dos.writeBoolean(open);
			dos.writeByte(direction);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream) throws IOException 
	{

	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		outStream.writeBoolean(open);
		outStream.writeByte(direction);
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		open = inStream.readBoolean();
		direction = inStream.readByte();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}
}
