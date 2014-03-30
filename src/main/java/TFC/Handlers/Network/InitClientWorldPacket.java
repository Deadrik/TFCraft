package TFC.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.DataInputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import TFC.API.TFCOptions;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Player.SkillStats;

public class InitClientWorldPacket extends AbstractPacket
{
	private EntityPlayer player;
	private World world;
	private long seed;
	private FoodStatsTFC foodstats;
	private boolean craftingTable = false;
	private DataInputStream dis;
	private SkillStats playerSkills;
	private int daysInYear, HGRate, HGCap;
	
	public InitClientWorldPacket(EntityPlayer P)
	{
		player = P;
		world = P.worldObj;
		foodstats = TFC_Core.getPlayerFoodStats(P);
		foodstats.resetTimers();
		foodstats.writeNBT(P.getEntityData());
	}
	
	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeLong(world.getSeed());
		buffer.writeInt(TFC_Time.daysInYear);
		buffer.writeFloat(foodstats.stomachLevel);
		buffer.writeFloat(foodstats.waterLevel);
		buffer.writeInt(TFCOptions.HealthGainRate);
		buffer.writeInt(TFCOptions.HealthGainCap);
		if(player.getEntityData().hasKey("craftingTable"))
			craftingTable = true;
		buffer.writeBoolean(craftingTable);
//		TFC_Core.getSkillStats(player).toOutBuffer(buffer);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		seed = buffer.readLong();
		daysInYear = buffer.readInt();
		foodstats.stomachLevel = buffer.readFloat();
		foodstats.waterLevel = buffer.readFloat();
		HGRate = buffer.readInt();
		HGCap = buffer.readInt();
		craftingTable = buffer.readBoolean();
//		dis = new DataInputStream(new ByteArrayInputStream(buffer.array()));
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		TFC_Core.setPlayerFoodStats(player, foodstats);
		TFC_Time.daysInYear = this.daysInYear;
		TFCOptions.HealthGainRate = HGRate;
		TFCOptions.HealthGainCap = HGCap;
		if(craftingTable)
		{
			player.getEntityData().setBoolean("craftingTable", craftingTable);
			PlayerInventory.upgradePlayerCrafting(player);
		}
		TFC_Core.SetupWorld(world, seed);
//		try
//		{
//			while(dis.available() > 0)
//				playerSkills.setSkillSave(dis.readUTF(), dis.readInt());
//		}
//		catch (IOException e)
//		{
//			e.printStackTrace();
//		}
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}

}
