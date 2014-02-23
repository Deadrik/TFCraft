package TFC.Handlers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.MemoryConnection;
import net.minecraft.network.NetLoginHandler;
import net.minecraft.network.NetServerHandler;
import net.minecraft.network.packet.NetHandler;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet1Login;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.INetworkTE;
import TFC.API.TFCOptions;
import TFC.Containers.ContainerSpecialCrafting;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Core.Player.SkillStats;
import TFC.Items.Tools.ItemWritableBookTFC;
import cpw.mods.fml.common.network.IConnectionHandler;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class PacketHandler implements IPacketHandler, IConnectionHandler {

	/**
	 * Packets that are affixed with _Server are processed server side and 
	 * _Client is processed on the client side. Therefore when the client wishes 
	 * to request something from the server, _Server packets should be sent from the client.
	 */
	public static final byte Packet_Init_Block_Client = 0;
	public static final byte Packet_Init_Block_Server = 1;
	public static final byte Packet_Keypress_Server = 2;
	public static final byte Packet_Init_World_Client = 3;
	public static final byte Packet_Data_Block_Client = 4;
	public static final byte Packet_Data_Block_Server = 5;
	public static final byte Packet_Player_Status = 6;
	public static final byte Packet_Rename_Item = 7;
	public static final byte Packet_Book_Sign = 8;
	public static final byte Packet_Update_Knapping = 9;
	public static final byte Packet_Update_Skills_Client = 10;
	public static final byte Packet_Update_Skills_Server = 11;

	private static long keyTimer = 0;

	@Override
	public void clientLoggedIn(NetHandler clientHandler,
			INetworkManager manager, Packet1Login login) 
	{
		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(clientHandler.getPlayer().username, manager));
		TerraFirmaCraft.proxy.onClientLogin();
	}


	@Override
	public void playerLoggedIn(Player p, NetHandler netHandler, INetworkManager manager)
	{
		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(((EntityPlayer)p).username, manager));

		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		EntityPlayerMP player = (EntityPlayerMP)p;
		FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
		foodstats.resetTimers();
		foodstats.writeNBT(player.getEntityData());

		World world= player.worldObj;

		if(!world.isRemote)
		{

			try
			{
				dos.writeByte(Packet_Init_World_Client);
				dos.writeLong(world.getSeed());
				dos.writeInt(TFC_Time.daysInYear);

				dos.writeFloat(foodstats.stomachLevel);
				dos.writeFloat(foodstats.waterLevel);	
				dos.writeFloat(foodstats.nutrFruit);
				dos.writeFloat(foodstats.nutrVeg);
				dos.writeFloat(foodstats.nutrGrain);
				dos.writeFloat(foodstats.nutrProtein);
				dos.writeFloat(foodstats.nutrDairy);

				dos.writeInt(TFCOptions.HealthGainRate);
				dos.writeInt(TFCOptions.HealthGainCap);

				if(player.getEntityData().hasKey("craftingTable"))
					dos.writeBoolean(true);
				else dos.writeBoolean(false);

				TFC_Core.getSkillStats(player).toOutStream(dos);

			} 
			catch (IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			((NetServerHandler)netHandler).sendPacketToPlayer(getPacket(bos));
		}
	}

	@Override
	public void onPacketData(INetworkManager manager,
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
			World world= player.worldObj;

			if(type == Packet_Init_Block_Client)
				try
			{
					x = dis.readInt();
					y = dis.readInt();
					z = dis.readInt();

					if(world != null && world.isRemote)
					{
						INetworkTE te = (INetworkTE) world.getBlockTileEntity(x, y, z);
						if(te!= null)
							te.handleInitPacket(dis);
					}
			}catch(Exception e)
			{
				System.out.println("PacketHandler error in Packet Type: " + type + ", "+x + ", "+y + ", "+z);
			}
			else if(type == Packet_Init_Block_Server)//Server builds the init packet and sends it to the client.
			{

				x = dis.readInt();
				y = dis.readInt();
				z = dis.readInt();

				ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
				DataOutputStream dos=new DataOutputStream(bos);
				INetworkTE te = (INetworkTE) world.getBlockTileEntity(x, y, z);
				if(te != null)
				{

					dos.writeByte(Packet_Init_Block_Client);
					dos.writeInt(x);
					dos.writeInt(y);
					dos.writeInt(z);
					te.createInitPacket(dos);

					TerraFirmaCraft.proxy.sendCustomPacketToPlayer((EntityPlayerMP) player, getPacket(bos));
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

							if(pi!=null)
								pi.switchChiselMode();
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
						TFC_Time.daysInYear = dis.readInt();
						FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
						foodstats.stomachLevel = dis.readFloat();
						foodstats.waterLevel = dis.readFloat();
						foodstats.nutrFruit = dis.readFloat();
						foodstats.nutrVeg = dis.readFloat();
						foodstats.nutrGrain = dis.readFloat();
						foodstats.nutrProtein = dis.readFloat();
						foodstats.nutrDairy = dis.readFloat();
						TFC_Core.setPlayerFoodStats(player, foodstats);
						TFCOptions.HealthGainRate = dis.readInt();
						TFCOptions.HealthGainCap = dis.readInt();
						boolean craftingTable = dis.readBoolean();
						if(craftingTable)
						{
							player.getEntityData().setBoolean("craftingTable", craftingTable);
							PlayerInventory.upgradePlayerCrafting(player);
						}
						SkillStats skills = TFC_Core.getSkillStats(player);
						while(dis.available() > 0)
							skills.setSkillSave(dis.readUTF(), dis.readInt());


					} catch (IOException e) 
					{
						// IMPOSSIBLE?
					}
					TFC_Core.SetupWorld(world, seed);
				}
			}
			else if(type == Packet_Data_Block_Client)
			{
				if(world.isRemote)
				{
					x = dis.readInt();
					y = dis.readInt();
					z = dis.readInt();

					INetworkTE te = (INetworkTE) world.getBlockTileEntity(x, y, z);
					if(te!= null)
						te.handleDataPacket(dis);
				}
			}
			else if(type == Packet_Data_Block_Server)
			{
				if(!world.isRemote)
				{
					x = dis.readInt();
					y = dis.readInt();
					z = dis.readInt();

					INetworkTE te = (INetworkTE) world.getBlockTileEntity(x, y, z);
					if(te!= null)
						te.handleDataPacketServer(dis, player);
				}
			}
			else if (type == Packet_Player_Status)
			{
				if(world.isRemote)
					try 
				{
						byte flag = dis.readByte();
						if(flag == 0)
						{
							FoodStatsTFC foodstats = TFC_Core.getPlayerFoodStats(player);
							foodstats.stomachLevel = dis.readFloat();
							foodstats.waterLevel = dis.readFloat();
							foodstats.nutrFruit = dis.readFloat();
							foodstats.nutrVeg = dis.readFloat();
							foodstats.nutrGrain = dis.readFloat();
							foodstats.nutrProtein = dis.readFloat();
							foodstats.nutrDairy = dis.readFloat();
							TFC_Core.setPlayerFoodStats(player, foodstats);
						}
						else if(flag == 1)
						{
							SkillStats skills = TFC_Core.getSkillStats(player);
							skills.setSkillSave(dis.readUTF(), dis.readInt());
							skills = TFC_Core.getSkillStats(player);
						}

				} catch (IOException e) {}
			}
			else if(type == Packet_Rename_Item)
			{
				if(!world.isRemote)
				{
					String s = dis.readUTF();
					player.inventory.getCurrentItem().stackTagCompound.setString("Name", s);
				}
			}
			else if(type == Packet_Book_Sign){
				DataInputStream var2;
				ItemStack var3;
				ItemStack var4;

				/*if (true)
				{
					try
					{
						var2 = new DataInputStream(new ByteArrayInputStream(packet.data));
						var3 = Packet.readItemStack(var2);

						if (!ItemWritableBook.validBookTagPages(var3.getTagCompound()))
						{
							throw new IOException("Invalid book tag!");
						}

						var4 = player.inventory.getCurrentItem();

						if (var3 != null && var3.itemID == Item.writableBook.shiftedIndex && var3.itemID == var4.itemID)
						{
							var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages"));
						}
					}
					catch (Exception var12)
					{
						var12.printStackTrace();
					}
				}
				else */if (true)
					try
				{
						var2 = dis;//new DataInputStream(new ByteArrayInputStream(packet.data));
						var3 = Packet.readItemStack(var2);

						if (!ItemWritableBookTFC.validBookTagContents(var3.getTagCompound()))
							throw new IOException("Invalid book tag!");

						var4 = new ItemStack(0,1,0);//player.inventory.getCurrentItem();
						System.out.println("Tags: "+var3.getTagCompound().getTags());
						System.out.println(var3);
						if (var3 != null && var3.itemID == TFCItems.writabeBookTFC.itemID)
						{
							var4.setTagInfo("author", new NBTTagString("author", player.username));
							var4.setTagInfo("title", new NBTTagString("title", var3.getTagCompound().getString("title")));
							var4.setTagInfo("pages", var3.getTagCompound().getTagList("pages"));
							var4.itemID = TFCItems.writabeBookTFC.itemID;
						}
						//player.setCurrentItemOrArmor(0, var4);
						player.inventory.setInventorySlotContents(player.inventory.currentItem, var4);
				}
				catch (Exception var11)
				{
					var11.printStackTrace();
				}

			}
			else if (type == Packet_Update_Knapping)
			{
				if(!world.isRemote)
					if(player.openContainer != null && player.openContainer instanceof ContainerSpecialCrafting)
					{
						byte index = dis.readByte();
						((ContainerSpecialCrafting)player.openContainer).craftMatrix.setInventorySlotContents(index, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).specialCraftingTypeAlternate);
						((ContainerSpecialCrafting)player.openContainer).onCraftMatrixChanged(((ContainerSpecialCrafting)player.openContainer).craftMatrix);
					}
			}
			else if (type == Packet_Update_Skills_Client)
				process_Packet_Update_Skills_Client(dis, player);
			else if (type == Packet_Update_Skills_Server)
				process_Packet_Update_Skills_Server(player);

		} catch (Exception e) 
		{
			return;
		}
	}

	public void process_Packet_Update_Skills_Client(DataInputStream dis, EntityPlayer player)
	{
		SkillStats skills = TFC_Core.getSkillStats(player);
		try 
		{
			boolean craftingTable = dis.readBoolean();
			if(craftingTable && !player.getEntityData().hasKey("craftingTable"))
			{
				player.getEntityData().setBoolean("craftingTable", craftingTable);
				PlayerInventory.upgradePlayerCrafting(player);
			}

			while(dis.available() > 0)
				skills.setSkillSave(dis.readUTF(), dis.readInt());
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}
	}

	public void process_Packet_Update_Skills_Server(EntityPlayer player)
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);
		try 
		{
			dos.writeByte(Packet_Update_Skills_Client);
			dos.writeBoolean(player.getEntityData().hasKey("craftingTable"));
			TFC_Core.getSkillStats(player).toOutStream(dos);
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
		}

		TerraFirmaCraft.proxy.sendCustomPacketToPlayer((EntityPlayerMP) player, getPacket(bos));
	}

	@SideOnly(Side.CLIENT)
	public static void sendKeyPress(int type) //0 = chiselmode
	{
		if (!ModLoader.getMinecraftInstance().theWorld.isRemote)
			return;
		else
			try
		{
				ByteArrayOutputStream bos = new ByteArrayOutputStream();
				DataOutputStream dos = new DataOutputStream(bos);
				Packet250CustomPayload pkt = new Packet250CustomPayload();

				dos.writeByte(Packet_Keypress_Server);
				dos.writeByte(type);
				dos.close();

				pkt.channel=Reference.ModChannel;
				pkt.data=bos.toByteArray();
				pkt.length=bos.size();
				pkt.isChunkDataPacket=false;

				TerraFirmaCraft.proxy.sendCustomPacket(pkt);
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public String connectionReceived(NetLoginHandler netHandler,
			INetworkManager manager) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void connectionOpened(NetHandler netClientHandler, String server,
			int port, INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionOpened(NetHandler netClientHandler,
			MinecraftServer server, INetworkManager manager) {
		// TODO Auto-generated method stub

	}

	@Override
	public void connectionClosed(INetworkManager manager) 
	{
		PlayerInfo playerToRemove = null;

		List<PlayerInfo> players = PlayerManagerTFC.getInstance().Players;
		for(int i = 0; i < players.size(); i++)
		{
			playerToRemove = players.get(i);
			if(playerToRemove.networkManager == manager)
			{
				System.out.println("PlayerManager Successfully removed player " + playerToRemove.Name);
				players.remove(i);
			}  
		}

		// Preventive cleanup of integrated server PlayerInfo instance in the case it doesn't call connectionClosed
		if(playerToRemove != null && manager instanceof MemoryConnection)
			for(int i = 0; i < players.size(); i++)
				if(playerToRemove.Name == players.get(i).Name)
				{
					System.out.println("PlayerManager Successfully removed player " + players.get(i).Name);
					players.remove(i);
				}

		//		if(TerraFirmaCraft.proxy.isRemote())
		//			manager.closeConnections();
		//		else
		//			manager.serverShutdown();
	}

	public static Packet250CustomPayload getPacket(ByteArrayOutputStream bos)
	{
		Packet250CustomPayload pkt=new Packet250CustomPayload();
		pkt.channel=Reference.ModChannel;
		pkt.data=bos.toByteArray();
		pkt.length=bos.size();
		pkt.isChunkDataPacket=false;

		return pkt;
	}
}
