package TFC.API;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public interface INetworkTE 
{
	/**
	 * The X/Y/Z has already been read from the input stream. Now read out the custom data that you needed to send across. Read by the client only.
	 * @param inStream
	 */
	public abstract void handleDataPacket(DataInputStream inStream) throws IOException;
	
	/**
	 * The X/Y/Z has already been read from the input stream. Now read out the custom data that you needed to send across. Read by the server only.
	 * @param inStream
	 * @param player
	 */
	public abstract void handleDataPacketServer(DataInputStream inStream, EntityPlayer player) throws IOException;
	
	/**
	 * The X/Y/Z has already been read from the input stream. Now read out the custom data that you needed to send across. Read by the server only.
	 * @param inStream
	 */
	public abstract void handleDataPacketServer(DataInputStream inStream) throws IOException;

	/**
	 * To make it easy the x/y/z should already be added by the packet handler. Add whatever variables 
	 * are needed to be synced for initialization to the DataOutputStream
	 * @param outStream - DataOutputStream supplied by the packethandler for creating a new packet 
	 * to send to the client.
	 * @throws IOException 
	 */
	public abstract void createInitPacket(DataOutputStream outStream) throws IOException;

	/**
	 * The X/Y/Z has already been read from the input stream. Now read out the initialization data that you needed to send across.
	 * @param inStream
	 */
	@SideOnly(Side.CLIENT)
	public abstract void handleInitPacket(DataInputStream inStream) throws IOException;
}
