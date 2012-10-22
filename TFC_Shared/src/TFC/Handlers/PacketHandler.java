package TFC.Handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.lang.reflect.Proxy;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

import TFC.Core.TFC_Time;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.TFC_PlayerClient;
import TFC.Core.Player.TFC_PlayerServer;
import TFC.GUI.GuiHUD;
import TFC.TileEntities.NetworkTileEntity;
import TFC.TileEntities.TileEntityCrop;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.TileEntities.TileEntityBloomery;
import TFC.TileEntities.TileEntityTerraFirepit;
import TFC.TileEntities.TileEntityTerraLogPile;

import net.minecraft.server.MinecraftServer;
import net.minecraft.src.*;

public class PacketHandler implements IPacketHandler, IConnectionHandler {

	public static final byte Packet_Init_Block_Client = 0;
	public static final byte Packet_Init_Block_Server = 1;
	public static final byte Packet_Keypress_Server = 2;
	public static final byte Packet_Init_World_Client = 3;
	public static final byte Packet_Data_Client = 4;
	public static final byte Packet_Data_Server = 5;
	public static final byte Packet_Player_Status = 6;

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			NetworkManager manager, Packet1Login login) 
	{
		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(clientHandler.getPlayer().username, manager));
		ModLoader.getMinecraftInstance().ingameGUI = new GuiHUD(ModLoader.getMinecraftInstance());
	}


	@Override
	public void playerLoggedIn(Player p, NetHandler netHandler,NetworkManager manager)
	{
		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(((EntityPlayer)p).username, manager));

		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		EntityPlayer player = (EntityPlayer)p;
		TFC_PlayerServer playerserver = ((TFC.Core.Player.TFC_PlayerServer)((EntityPlayerMP)player).getServerPlayerBase("TFC Player Server"));
		World world= player.worldObj;

		if(!world.isRemote)
		{
			try
			{
				dos.writeByte(Packet_Init_World_Client);
				dos.writeLong(world.getSeed());
				dos.writeLong(TFC_Time.dayLength);
				dos.writeInt(TFC_Time.daysInYear);
				dos.writeInt(playerserver.getFoodStatsTFC().foodLevel);
				dos.writeInt(playerserver.getFoodStatsTFC().waterLevel);
			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			Packet250CustomPayload pkt=new Packet250CustomPayload();
			pkt.channel="TerraFirmaCraft";
			pkt.data=bos.toByteArray();
			pkt.length=bos.size();
			pkt.isChunkDataPacket=false;

			((NetServerHandler)netHandler).sendPacketToPlayer(pkt);
		}
	}

	@Override
	public void onPacketData(NetworkManager manager,
			Packet250CustomPayload packet, Player p)
	{
		DataInputStream dis=new DataInputStream(new ByteArrayInputStream(packet.data));
		
		byte type = 0;
		int x = 0;
		int y = 0;
		int z = 0;
		try {
			type = dis.readByte();

			EntityPlayer player = (EntityPlayer)p;
			TFC_PlayerClient playerClient = null;
			TFC_PlayerServer playerServer = null;;
			World world= player.worldObj;
			if(world.isRemote)
			{
				playerClient = (TFC_PlayerClient)((EntityPlayerSP)player).getPlayerBase("TFC Player Client");
			}
			else
			{	
				playerServer = (TFC_PlayerServer)((EntityPlayerMP)player).getServerPlayerBase("TFC Player Server");
			}

			if(type == Packet_Init_Block_Client)//Client recieves the init packet from the server and assigns the data
			{

				try
				{
				x = dis.readInt();
				y = dis.readInt();
				z = dis.readInt();

				if(world != null)
				{
					NetworkTileEntity te = (NetworkTileEntity) world.getBlockTileEntity(x, y, z);
					if(te!= null)
						te.handleInitPacket(dis);
				}
				}catch(Exception e)
				{
					System.out.println("PacketHandler error in Packet Type: " + type + ", "+x + ", "+y + ", "+z);
				}
			}
			else if(type == Packet_Init_Block_Server)//Server builds the init packet and sends it to the client.
			{

				x = dis.readInt();
				y = dis.readInt();
				z = dis.readInt();

				ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
				DataOutputStream dos=new DataOutputStream(bos);
				NetworkTileEntity te = (NetworkTileEntity) world.getBlockTileEntity(x, y, z);
				if(te != null)
				{

					dos.writeByte(Packet_Init_Block_Client);
					dos.writeInt(x);
					dos.writeInt(y);
					dos.writeInt(z);
					te.createInitPacket(dos);

					Packet250CustomPayload pkt=new Packet250CustomPayload();
					pkt.channel="TerraFirmaCraft";
					pkt.data=bos.toByteArray();
					pkt.length=bos.size();
					pkt.isChunkDataPacket=false;

					TerraFirmaCraft.proxy.sendCustomPacketToPlayer((EntityPlayerMP) player, pkt);
				}
			}
			else if(type == Packet_Keypress_Server)
			{
				byte change;
				if(keyTimer + 1 < TFC_Time.getTotalTicks())
				{
					keyTimer = TFC_Time.getTotalTicks();
					try 
					{
						change = dis.readByte();
						if(change == 0)//ChiselMode
						{
							PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);

							if(pi!=null) pi.switchChiselMode();
						}
					} 
					catch (IOException e) 
					{
						return;
					} 
				}
			}
			else if(type == Packet_Init_World_Client)
			{
				if(world.isRemote)
				{
					long seed = 0;
					try 
					{
						seed = dis.readLong();
						TFC_Time.dayLength = dis.readLong();
						TFC_Time.daysInYear = dis.readInt();
						playerClient.getFoodStatsTFC().foodLevel = dis.readInt();
						playerClient.getFoodStatsTFC().waterLevel = dis.readInt();

					} catch (IOException e) 
					{
						// IMPOSSIBLE?
					}
					TFC_Core.SetupWorld(world, seed);
				}
			}
			else if(type == Packet_Data_Client)
			{
				if(world.isRemote)
				{
					x = dis.readInt();
					y = dis.readInt();
					z = dis.readInt();

					NetworkTileEntity te = (NetworkTileEntity) world.getBlockTileEntity(x, y, z);
					if(te!= null)
						te.handleDataPacket(dis);
				}
			}
			else if(type == Packet_Data_Server)
			{
				if(!world.isRemote)
				{
					x = dis.readInt();
					y = dis.readInt();
					z = dis.readInt();

					NetworkTileEntity te = (NetworkTileEntity) world.getBlockTileEntity(x, y, z);
					if(te!= null)
						te.handleDataPacketServer(dis);
				}
			}
			else if (type == Packet_Player_Status)
			{
				if(world.isRemote)
				{
					try 
					{
						playerClient.getFoodStatsTFC().foodLevel = dis.readInt();
						playerClient.getFoodStatsTFC().waterLevel = dis.readInt();

					} catch (IOException e) {}
				}
			}
		} catch (Exception e) 
		{
			return;
		}
	}
	static long keyTimer = 0;

	@SideOnly(Side.CLIENT)
	public static void sendKeyPress(int type) //0 = chiselmode
	{
		if (!ModLoader.getMinecraftInstance().theWorld.isRemote) return;
		else 
		{
			try
			{
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				Packet250CustomPayload pkt = new Packet250CustomPayload();

				dos.writeByte(Packet_Keypress_Server);
				dos.writeByte(type);
				dos.close();

				pkt.channel="TerraFirmaCraft";
				pkt.data=bos.toByteArray();
				pkt.length=bos.size();
				pkt.isChunkDataPacket=false;

				TerraFirmaCraft.proxy.sendCustomPacket(pkt);
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			NetworkManager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, NetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, NetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionClosed(NetworkManager manager) 
	{
		PlayerInfo PI = new PlayerInfo("", manager);
		for(int i = 0; i < PlayerManagerTFC.getInstance().Players.size() && PI != null; i++)
		{
			if(PlayerManagerTFC.getInstance().Players.get(i).networkManager == manager)
			{
				System.out.println("PlayerManager Successfully removed player " + PlayerManagerTFC.getInstance().Players.get(i).Name);
				PlayerManagerTFC.getInstance().Players.remove(i);
			}  
		}
		
		if(TerraFirmaCraft.proxy.isRemote())
			manager.closeConnections();
		else
			manager.serverShutdown();
	}
}
