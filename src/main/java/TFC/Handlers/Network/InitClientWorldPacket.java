package TFC.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import TFC.API.TFCOptions;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Core.Player.FoodStatsTFC;
import TFC.Core.Player.PlayerInventory;
import cpw.mods.fml.common.network.ByteBufUtils;

public class InitClientWorldPacket extends AbstractPacket
{
	private EntityPlayer player;
	private World world;
	private long seed;
	private FoodStatsTFC foodstats;
	private boolean craftingTable = false;
	
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
		buffer.writeFloat(foodstats.foodLevel);
		buffer.writeFloat(foodstats.waterLevel);
		buffer.writeInt(TFCOptions.HealthGainRate);
		buffer.writeInt(TFCOptions.HealthGainCap);
		if(player.getEntityData().hasKey("craftingTable"))
			craftingTable = true;
		buffer.writeBoolean(craftingTable);
		TFC_Core.getSkillStats(player).toOutBuffer(buffer);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		seed = buffer.readLong();
		TFC_Time.daysInYear = buffer.readInt();
		foodstats.foodLevel = buffer.readFloat();
		foodstats.waterLevel = buffer.readFloat();
		TFCOptions.HealthGainRate = buffer.readInt();
		TFCOptions.HealthGainCap = buffer.readInt();
		craftingTable = buffer.readBoolean();
		int i = buffer.readInt();
		for(int l = 0; l < i; ++l)
			TFC_Core.getSkillStats(player).setSkillSave(ByteBufUtils.readUTF8String(buffer), buffer.readInt());
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		TFC_Core.setPlayerFoodStats(player, foodstats);
		if(craftingTable)
		{
			player.getEntityData().setBoolean("craftingTable", craftingTable);
			PlayerInventory.upgradePlayerCrafting(player);
		}
		TFC_Core.SetupWorld(world, seed);
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}

}
