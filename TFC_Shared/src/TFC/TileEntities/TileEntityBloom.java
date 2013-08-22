package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

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
		
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void handleInitPacket(DataInputStream inStream) throws IOException
	{
		
	}
}
