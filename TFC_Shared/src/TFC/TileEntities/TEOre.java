package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import TFC.TerraFirmaCraft;

public class TEOre extends NetworkTileEntity 
{
	public int baseBlockID = -1000;
	public int baseBlockMeta = -1000;
	public byte extraData = 0;

	public TEOre()
	{
		this.shouldSendInitData = true;
	}

	@Override
	public void validate()
	{
		super.validate();

		if(worldObj.isRemote && this.shouldSendInitData && (extraData & 8) != 0)
			try {
				requestInitialization();
			} catch (IOException e) {}
	}

	@Override
	public boolean canUpdate()
	{
		return false;
	}

	public void setVisible()
	{
		if((extraData & 8) == 0 || this.yCoord > 130)
			extraData += 8;
		try
		{
			TerraFirmaCraft.proxy.sendCustomPacket(sendInitPacket());
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound nbt) 
	{
		super.readFromNBT(nbt);
		baseBlockID = nbt.getInteger("baseBlockID");
		baseBlockMeta = nbt.getInteger("baseBlockMeta");
		extraData = nbt.getByte("grade");
	}

	@Override
	public void writeToNBT(NBTTagCompound nbt) 
	{
		super.writeToNBT(nbt);
		nbt.setInteger("baseBlockID", baseBlockID);
		nbt.setInteger("baseBlockMeta", baseBlockMeta);
		nbt.setByte("grade", extraData);
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException 
	{

	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream) throws IOException 
	{

	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException 
	{
		outStream.writeInt(baseBlockID);
		outStream.writeInt(baseBlockMeta);
	}

	@Override
	public void handleInitPacket(DataInputStream inStream) throws IOException 
	{
		baseBlockID = inStream.readInt();
		baseBlockMeta = inStream.readInt();
		worldObj.markBlockForRenderUpdate(xCoord, yCoord, zCoord);
	}
}
