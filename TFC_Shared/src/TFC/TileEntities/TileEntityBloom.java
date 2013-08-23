package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.nbt.NBTTagCompound;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TileEntityBloom extends NetworkTileEntity
{
	public int size;
	public TileEntityBloom()
	{
		size = 0;
	}

	public void setSize(int iron){
		size = iron;
	}

	@Override
	public void writeToNBT(NBTTagCompound nbttagcompound)
	{
		super.writeToNBT(nbttagcompound);
		nbttagcompound.setInteger("size", size);
	}

	@Override
	public void readFromNBT(NBTTagCompound nbttagcompound)
	{
		super.readFromNBT(nbttagcompound);
		size = nbttagcompound.getInteger("size");
	}

	@Override
	public void handleDataPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void handleDataPacketServer(DataInputStream inStream)
			throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	public void createInitPacket(DataOutputStream outStream) throws IOException {
		// TODO Auto-generated method stub

	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleInitPacket(DataInputStream inStream) throws IOException {
		// TODO Auto-generated method stub

	}
}
