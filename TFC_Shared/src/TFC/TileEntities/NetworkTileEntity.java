package TFC.TileEntities;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import TFC.TerraFirmaCraft;
import TFC.Handlers.PacketHandler;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public abstract class NetworkTileEntity extends TileEntity {

	public boolean shouldSendInitData = true;
	public EntityPlayer entityplayer;
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
	public void handleDataPacketServer(DataInputStream inStream, EntityPlayer player) throws IOException
	{
		entityplayer = player;
		handleDataPacketServer(inStream);
	}
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

	/**
	 * This method sends a packet to the server requesting initialization data for the client. Client Only.
	 */
	public void requestInitialization() throws IOException
	{
		if (this.worldObj.isRemote)
        {
			TerraFirmaCraft.proxy.sendCustomPacket(needsInitPacket());
        }
	}

	@Override
	public void validate()
	{
		super.validate();
		
		if(worldObj.isRemote && this.shouldSendInitData)
		{
			try {
				requestInitialization();
			} catch (IOException e) {}  
		}
	}
	
	/**
	 * Client Side Only.
	 * @return A packet that is sent to the server when the client needs initialization.
	 */
	private Packet needsInitPacket() throws IOException
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		int x=xCoord;
		int y=yCoord;
		int z=zCoord;
		try 
		{
			dos.writeByte(PacketHandler.Packet_Init_Block_Server);
			dos.writeInt(x);
			dos.writeInt(y);
			dos.writeInt(z);
		} 
		catch (IOException e) 
		{

		}
		return setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	
	/**
	 * Constructs a packet. Only called by the server.
	 * @return A packet to be sent to the client that is requesting initialization of the block.
	 */
	private Packet sendInitPacket() throws IOException
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		int x=xCoord;
		int y=yCoord;
		int z=zCoord;
		try 
		{
			//The packet type sent determines who is expected to process this packet, the client or the server.
			dos.writeByte(PacketHandler.Packet_Init_Block_Server);
			dos.writeInt(xCoord);
			dos.writeInt(yCoord);
			dos.writeInt(zCoord);
			createInitPacket(dos);
		} 
		catch (IOException e) 
		{

		}
		return setupCustomPacketData(bos.toByteArray(), bos.size());
	}
	
	/**
	 * 
	 * @param data A byte array contining the data to be sent
	 * @param length the size of the packet.
	 * @return Creates a Packet250CustomPayload
	 */
	public Packet setupCustomPacketData(byte[] data, int length)
	{
		Packet250CustomPayload pkt=new Packet250CustomPayload();
		pkt.channel="TerraFirmaCraft";
		pkt.data = data;
		pkt.length= length;
		pkt.isChunkDataPacket=true;
		return pkt;
	}
	
	/**
	 * Sends a packet to all players within 160 meters of the block. This should include all players that have the block loaded themselves.
	 * @param packet The packet containing custom data that is to be sent.
	 */
	public void broadcastPacketInRange(Packet packet)
	{
		TerraFirmaCraft.proxy.sendCustomPacketToPlayersInRange(xCoord, yCoord, zCoord, packet, 160);
	}
}
