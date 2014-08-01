package com.bioxx.tfc.Handlers.Network;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import java.io.DataInputStream;
import java.util.HashMap;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Core.Player.SkillStats;
import com.bioxx.tfc.api.TFCOptions;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class InitClientWorldPacket implements IMessage
{
	private long seed;
	private float stomachLevel;
	private float waterLevel;
	private float nutrFruit;
	private float nutrVeg;
	private float nutrGrain;
	private float nutrProtein;
	private float nutrDairy;
	private boolean craftingTable = false;
	private SkillStats playerSkills;
	private int daysInYear, HGRate, HGCap;
	private HashMap<String, Integer> skillMap = new HashMap<String, Integer>();

	public InitClientWorldPacket() {}

	public InitClientWorldPacket(EntityPlayer P)
	{
		this.seed = P.worldObj.getSeed();
		// Make sure to update time before loading food stats!
		TFC_Time.UpdateTime(P.worldObj);
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(P);
		fs.resetTimers();
		fs.writeNBT(P.getEntityData());
		this.stomachLevel = fs.stomachLevel;
		this.waterLevel = fs.waterLevel;
		this.nutrFruit = fs.nutrFruit;
		this.nutrVeg = fs.nutrVeg;
		this.nutrGrain = fs.nutrGrain;
		this.nutrProtein = fs.nutrProtein;
		this.nutrDairy = fs.nutrDairy;
		this.daysInYear = TFC_Time.daysInYear;
		this.HGRate = TFCOptions.HealthGainRate;
		this.HGCap = TFCOptions.HealthGainCap;
		if(P.getEntityData().hasKey("craftingTable"))
			this.craftingTable = true;
		this.playerSkills = TFC_Core.getSkillStats(P);
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeLong(this.seed);
		buffer.writeInt(this.daysInYear);
		buffer.writeFloat(this.stomachLevel);
		buffer.writeFloat(this.waterLevel);
		buffer.writeFloat(this.nutrFruit);
		buffer.writeFloat(this.nutrVeg);
		buffer.writeFloat(this.nutrGrain);
		buffer.writeFloat(this.nutrProtein);
		buffer.writeFloat(this.nutrDairy);
		buffer.writeInt(this.HGRate);
		buffer.writeInt(this.HGCap);
		buffer.writeBoolean(this.craftingTable);
		this.playerSkills.toOutBuffer(buffer);
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.seed = buffer.readLong();
		this.daysInYear = buffer.readInt();
		this.stomachLevel = buffer.readFloat();
		this.waterLevel = buffer.readFloat();
		this.nutrFruit = buffer.readFloat();
		this.nutrVeg = buffer.readFloat();
		this.nutrGrain = buffer.readFloat();
		this.nutrProtein = buffer.readFloat();
		this.nutrDairy = buffer.readFloat();
		this.HGRate = buffer.readInt();
		this.HGCap = buffer.readInt();
		this.craftingTable = buffer.readBoolean();

		this.skillMap.clear();
		String name;
		int lvl;
		int size = buffer.readInt();
		for(int l = 0; l < size; l++)
		{
			name = ByteBufUtils.readUTF8String(buffer);
			lvl = buffer.readInt();
			this.skillMap.put(name, lvl);
		}
	}

	public static class ClientHandler implements IMessageHandler<InitClientWorldPacket, IMessage> {

		@Override
		public IMessage onMessage(InitClientWorldPacket message, MessageContext ctx) {
			// *** client side only here ***
			EntityPlayer player = TerraFirmaCraft.proxy.getPlayerFromMessageContext(ctx);

			// a client-side version of the playerinfo
			PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromUUID(player.getUniqueID().toString());
			if (pi == null) {
				pi = new PlayerInfo( player.getDisplayName(), player.getUniqueID());
				PlayerManagerTFC.getInstance().Players.add(pi);
			}

			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			fs.stomachLevel = message.stomachLevel;
			fs.waterLevel = message.waterLevel;
			fs.nutrFruit = message.nutrFruit;
			fs.nutrVeg = message.nutrVeg;
			fs.nutrProtein = message.nutrProtein;
			fs.nutrDairy = message.nutrDairy;
			TFC_Core.setPlayerFoodStats(player, fs);

			TFC_Time.daysInYear = message.daysInYear;
			TFCOptions.HealthGainRate = message.HGRate;
			TFCOptions.HealthGainCap = message.HGCap;
			if(message.craftingTable)
			{
				player.getEntityData().setBoolean("craftingTable", message.craftingTable);
				PlayerInventory.upgradePlayerCrafting(player);
			}
			TFC_Core.SetupWorld(player.worldObj, message.seed);

			SkillStats playerSkills = TFC_Core.getSkillStats(player);
			for(String skill : message.skillMap.keySet())
			{
				playerSkills.setSkillSave(skill, message.skillMap.get(skill));
			}
			TFC_Core.setSkillStats(player, playerSkills);
			return null;
		}
	}

}
