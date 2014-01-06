package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import TFC.TerraFirmaCraft;
import TFC.Handlers.PacketHandler;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.packet.Packet;
import net.minecraft.tileentity.TileEntity;

public class TESeaWeed extends NetworkTileEntity {
	private int type = -1;

	public TESeaWeed(){
	}

	public void setType(int type){	
			if(this.type == -1){
				this.type = type;
			}
	}

	public int getType(){
		if(!this.worldObj.isRemote){
			return this.type;
		}
		else{
			//validate();
			return this.type;
		}
	}
	
	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		type = nbttagcompound.getInteger("type");
	}
	
	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("type", type);
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		type = inStream.readInt();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}

	public Packet createUpdatePacket()
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);	
		try {
			dos.writeByte(PacketHandler.Packet_Data_Block_Client);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			dos.writeInt(type);
		} catch (IOException e) {
		}
		return this.setupCustomPacketData(bos.toByteArray(), bos.size());
	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		type = inStream.readInt();
	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		outStream.writeInt(type);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		type = inStream.readInt();
		worldObj.markBlockForUpdate(xCoord, yCoord, zCoord);
	}




}
