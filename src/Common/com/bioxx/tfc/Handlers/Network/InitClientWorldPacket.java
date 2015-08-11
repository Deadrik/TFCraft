package com.bioxx.tfc.Handlers.Network;

import java.util.HashMap;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.entity.player.EntityPlayer;
import cpw.mods.fml.common.network.ByteBufUtils;

import com.bioxx.tfc.Core.Player.*;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
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
	private boolean craftingTable = false;
	private SkillStats playerSkills;
	private int daysInYear, HGRate, HGCap;
	private HashMap<String, Integer> skillMap = new HashMap<String, Integer>();
	private byte chiselMode;

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
		this.soberTime = fs.soberTime;
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
		this.chiselMode = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(P).ChiselMode;
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
		buffer.writeInt(this.HGRate);
		buffer.writeInt(this.HGCap);
		buffer.writeBoolean(this.craftingTable);
		this.playerSkills.toOutBuffer(buffer);
		buffer.writeByte(this.chiselMode);
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

		this.chiselMode = buffer.readByte();
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
		TFCOptions.HealthGainRate = this.HGRate;
		TFCOptions.HealthGainCap = this.HGCap;
		if(this.craftingTable)
		{
			player.getEntityData().setBoolean("craftingTable", this.craftingTable);
			PlayerInventory.upgradePlayerCrafting(player);
		}
		TFC_Core.SetupWorld(player.worldObj, this.seed);

		this.playerSkills = TFC_Core.getSkillStats(player);
		for(String skill : skillMap.keySet())
		{
			playerSkills.setSkillSave(skill, skillMap.get(skill));
		}
		skillMap.clear();

		PlayerManagerTFC.getInstance().Players.add(new PlayerInfo(
				player.getDisplayName(),
				player.getUniqueID()));

		PlayerManagerTFC.getInstance().getClientPlayer().setChiselMode(this.chiselMode);


	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}

}
