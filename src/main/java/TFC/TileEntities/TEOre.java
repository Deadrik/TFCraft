package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;

public class TEOre extends TileEntity
{
	public int baseBlockID = -1000;
	public int baseBlockMeta = -1000;
	public byte grade = 0;

	public TEOre()
	{
//		this.shouldSendInitData = true;
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt)
	{
		super.readFromNBT(nbt);
		baseBlockID = nbt.getInteger("baseBlockID");
		baseBlockMeta = nbt.getInteger("baseBlockMeta");
		grade = nbt.getByte("grade");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setInteger("baseBlockID", baseBlockID);
		nbt.setInteger("baseBlockMeta", baseBlockMeta);
		nbt.setByte("grade", grade);
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
	}

//	@Override
//	public void createInitPacket(DataOutputStream outStream) throws IOException 
//	{
//		outStream.writeInt(baseBlockID);
//		outStream.writeInt(baseBlockMeta);
//	}
//
//	@Override
//	public void handleInitPacket(DataInputStream inStream) throws IOException 
//	{
//		baseBlockID = inStream.readInt();
//		baseBlockMeta = inStream.readInt();
//		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
//	}
}
