package com.bioxx.tfc.Handlers.Network;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayer;

import cpw.mods.fml.common.network.ByteBufUtils;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.*;
import com.bioxx.tfc.api.TFCOptions;

public class InitClientWorldPacket extends AbstractPacket
{
	private long seed;
	private long soberTime;
	private float stomachLevel;
	private float waterLevel;
	private float nutrFruit;
	private float nutrVeg;
	private float nutrGrain;
	private float nutrProtein;
	private float nutrDairy;
	private boolean craftingTable;
	private boolean debugMode;
	private SkillStats playerSkills;
	private int daysInYear, healthGainRate, healthGainCap;
	private Map<String, Integer> skillMap = new HashMap<String, Integer>();
	private byte chiselMode;

	public InitClientWorldPacket() {}

	public InitClientWorldPacket(EntityPlayer p)
	{
		this.seed = p.worldObj.getSeed();
		// Make sure to update time before loading food stats!
		TFC_Time.updateTime(p.worldObj);
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(p);
		fs.resetTimers();
		fs.writeNBT(p.getEntityData());
		this.stomachLevel = fs.stomachLevel;
		this.waterLevel = fs.waterLevel;
		this.soberTime = fs.soberTime;
		this.nutrFruit = fs.nutrFruit;
		this.nutrVeg = fs.nutrVeg;
		this.nutrGrain = fs.nutrGrain;
		this.nutrProtein = fs.nutrProtein;
		this.nutrDairy = fs.nutrDairy;
		this.daysInYear = TFCOptions.yearLength;
		this.healthGainRate = TFCOptions.healthGainRate;
		this.healthGainCap = TFCOptions.healthGainCap;
		this.debugMode = TFCOptions.enableDebugMode;

		if(p.getEntityData().hasKey("craftingTable"))
			this.craftingTable = true;
		this.playerSkills = TFC_Core.getSkillStats(p);
		this.chiselMode = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(p).chiselMode;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeLong(this.seed);
		buffer.writeInt(this.daysInYear);
		buffer.writeFloat(this.stomachLevel);
		buffer.writeFloat(this.waterLevel);
		buffer.writeLong(this.soberTime);
		buffer.writeFloat(this.nutrFruit);
		buffer.writeFloat(this.nutrVeg);
		buffer.writeFloat(this.nutrGrain);
		buffer.writeFloat(this.nutrProtein);
		buffer.writeFloat(this.nutrDairy);
		buffer.writeInt(this.healthGainRate);
		buffer.writeInt(this.healthGainCap);
		buffer.writeBoolean(this.craftingTable);
		this.playerSkills.toOutBuffer(buffer);
		buffer.writeByte(this.chiselMode);
		buffer.writeBoolean(this.debugMode);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.seed = buffer.readLong();
		this.daysInYear = buffer.readInt();
		this.stomachLevel = buffer.readFloat();
		this.waterLevel = buffer.readFloat();
		this.soberTime = buffer.readLong();
		this.nutrFruit = buffer.readFloat();
		this.nutrVeg = buffer.readFloat();
		this.nutrGrain = buffer.readFloat();
		this.nutrProtein = buffer.readFloat();
		this.nutrDairy = buffer.readFloat();
		this.healthGainRate = buffer.readInt();
		this.healthGainCap = buffer.readInt();
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

		this.chiselMode = buffer.readByte();
		this.debugMode = buffer.readBoolean();
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
		fs.stomachLevel = this.stomachLevel;
		fs.waterLevel = this.waterLevel;
		fs.soberTime = this.soberTime;
		fs.nutrFruit = this.nutrFruit;
		fs.nutrVeg = this.nutrVeg;
		fs.nutrProtein = this.nutrProtein;
		fs.nutrDairy = this.nutrDairy;
		TFC_Core.setPlayerFoodStats(player, fs);

		TFC_Time.setYearLength(this.daysInYear);
		TFCOptions.healthGainRate = this.healthGainRate;
		TFCOptions.healthGainCap = this.healthGainCap;
		TFCOptions.enableDebugMode = this.debugMode;
		if(this.craftingTable)
		{
			player.getEntityData().setBoolean("craftingTable", this.craftingTable);
			PlayerInventory.upgradePlayerCrafting(player);
		}
		TFC_Core.setupWorld(player.worldObj, this.seed);

		this.playerSkills = TFC_Core.getSkillStats(player);
		for(String skill : skillMap.keySet())
		{
			playerSkills.setSkillSave(skill, skillMap.get(skill));
		}
		skillMap.clear();

		PlayerManagerTFC.getInstance().players.add(new PlayerInfo(
				player.getCommandSenderName(),
				player.getUniqueID()));

		PlayerManagerTFC.getInstance().getClientPlayer().setChiselMode(this.chiselMode);


	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}

}
