package TFC.TileEntities;

import java.io.DataInputStream;
import java.io.DataOutputStream;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import net.minecraft.src.Packet;
import net.minecraft.src.TileEntity;

public abstract class NetworkTileEntity extends TileEntity {

	/**
	 * The X/Y/Z has already been read from the input stream. Now read out the custom data that you needed to send across.
	 * @param inStream
	 */
	@SideOnly(Side.CLIENT)
	public abstract void handleDataPacket(DataInputStream inStream);

	/**
	 * To make it easy the x/y/z should already be added by the packet handler. Add whatever variables 
	 * are needed to be synced for initialization to the DataOutputStream
	 * @param outStream - DataOutputStream supplied by the packethandler for creating a new packet 
	 * to send to the client.
	 */
	@SideOnly(Side.SERVER)
	public abstract void createInitPacket(DataOutputStream outStream);

	/**
	 * The X/Y/Z has already been read from the input stream. Now read out the initialization data that you needed to send across.
	 * @param inStream
	 */
	@SideOnly(Side.CLIENT)
	public abstract void handleInitPacket(DataInputStream inStream);

	/**
	 * Use this method to request initialization data from the server.
	 */
	@SideOnly(Side.CLIENT)
	public abstract void initialize();

	@Override
	public void validate()
	{
		super.validate();
		
		if(worldObj.isRemote)
			initialize();    	
	}
}
