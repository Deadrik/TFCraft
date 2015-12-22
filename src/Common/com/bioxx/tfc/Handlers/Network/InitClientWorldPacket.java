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
	private SkillStats playerSkills;
	private Map<String, Integer> skillMap = new HashMap<String, Integer>();
	private byte chiselMode;

	// Config Options
	private boolean debugMode;
	private int daysInYear, healthGainRate, healthGainCap, maxProtectionMonths, protectionGain, protectionBuffer,
			smallOreUnits, poorOreUnits, normalOreUnits, richOreUnits, torchBurnTime, oilLampFuelMult;
	private float pitKilnBurnTime, bloomeryBurnTime, charcoalPitBurnTime, saplingTimerMultiplier, animalTimeMultiplier;

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

		// Sync config options between server and client
		this.debugMode = TFCOptions.enableDebugMode;
		this.daysInYear = TFCOptions.yearLength;
		this.healthGainRate = TFCOptions.healthGainRate;
		this.healthGainCap = TFCOptions.healthGainCap;
		this.maxProtectionMonths = TFCOptions.maxProtectionMonths;
		this.protectionGain = TFCOptions.protectionGain;
		this.protectionBuffer = TFCOptions.protectionBuffer;
		this.smallOreUnits = TFCOptions.smallOreUnits;
		this.poorOreUnits = TFCOptions.poorOreUnits;
		this.normalOreUnits = TFCOptions.normalOreUnits;
		this.richOreUnits = TFCOptions.richOreUnits;
		this.pitKilnBurnTime = TFCOptions.pitKilnBurnTime;
		this.bloomeryBurnTime = TFCOptions.bloomeryBurnTime;
		this.charcoalPitBurnTime = TFCOptions.charcoalPitBurnTime;
		this.torchBurnTime = TFCOptions.torchBurnTime;
		this.saplingTimerMultiplier = TFCOptions.saplingTimerMultiplier;
		this.oilLampFuelMult = TFCOptions.oilLampFuelMult;
		this.animalTimeMultiplier = TFCOptions.animalTimeMultiplier;


		if(p.getEntityData().hasKey("craftingTable"))
			this.craftingTable = true;
		this.playerSkills = TFC_Core.getSkillStats(p);
		this.chiselMode = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(p).chiselMode;
	}

	@Override
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeLong(this.seed);
		buffer.writeFloat(this.stomachLevel);
		buffer.writeFloat(this.waterLevel);
		buffer.writeLong(this.soberTime);
		buffer.writeFloat(this.nutrFruit);
		buffer.writeFloat(this.nutrVeg);
		buffer.writeFloat(this.nutrGrain);
		buffer.writeFloat(this.nutrProtein);
		buffer.writeFloat(this.nutrDairy);
		buffer.writeBoolean(this.craftingTable);
		this.playerSkills.toOutBuffer(buffer);
		buffer.writeByte(this.chiselMode);

		// Config Options
		buffer.writeBoolean(this.debugMode);
		buffer.writeInt(this.daysInYear);
		buffer.writeInt(this.healthGainRate);
		buffer.writeInt(this.healthGainCap);
		buffer.writeInt(this.maxProtectionMonths);
		buffer.writeInt(this.protectionGain);
		buffer.writeInt(this.protectionBuffer);
		buffer.writeInt(this.smallOreUnits);
		buffer.writeInt(this.poorOreUnits);
		buffer.writeInt(this.normalOreUnits);
		buffer.writeInt(this.richOreUnits);
		buffer.writeInt(this.torchBurnTime);
		buffer.writeInt(this.oilLampFuelMult);
		buffer.writeFloat(this.pitKilnBurnTime);
		buffer.writeFloat(this.bloomeryBurnTime);
		buffer.writeFloat(this.charcoalPitBurnTime);
		buffer.writeFloat(this.saplingTimerMultiplier);
		buffer.writeFloat(this.animalTimeMultiplier);
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.seed = buffer.readLong();
		this.stomachLevel = buffer.readFloat();
		this.waterLevel = buffer.readFloat();
		this.soberTime = buffer.readLong();
		this.nutrFruit = buffer.readFloat();
		this.nutrVeg = buffer.readFloat();
		this.nutrGrain = buffer.readFloat();
		this.nutrProtein = buffer.readFloat();
		this.nutrDairy = buffer.readFloat();
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

		// Config Options
		this.debugMode = buffer.readBoolean();
		this.daysInYear = buffer.readInt();
		this.healthGainRate = buffer.readInt();
		this.healthGainCap = buffer.readInt();
		this.maxProtectionMonths = buffer.readInt();
		this.protectionGain = buffer.readInt();
		this.protectionBuffer = buffer.readInt();
		this.smallOreUnits = buffer.readInt();
		this.poorOreUnits = buffer.readInt();
		this.normalOreUnits = buffer.readInt();
		this.richOreUnits = buffer.readInt();
		this.torchBurnTime = buffer.readInt();
		this.oilLampFuelMult = buffer.readInt();
		this.pitKilnBurnTime = buffer.readFloat();
		this.bloomeryBurnTime = buffer.readFloat();
		this.charcoalPitBurnTime = buffer.readFloat();
		this.saplingTimerMultiplier = buffer.readFloat();
		this.animalTimeMultiplier = buffer.readFloat();
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

		// Sync Config Options
		TFCOptions.enableDebugMode = this.debugMode;
		TFC_Time.setYearLength(this.daysInYear);
		TFCOptions.healthGainRate = this.healthGainRate;
		TFCOptions.healthGainCap = this.healthGainCap;
		TFCOptions.maxProtectionMonths = this.maxProtectionMonths;
		TFCOptions.protectionGain = this.protectionGain;
		TFCOptions.protectionBuffer = this.protectionBuffer;
		TFCOptions.smallOreUnits = this.smallOreUnits;
		TFCOptions.poorOreUnits = this.poorOreUnits;
		TFCOptions.normalOreUnits = this.normalOreUnits;
		TFCOptions.richOreUnits = this.richOreUnits;
		TFCOptions.torchBurnTime = this.torchBurnTime;
		TFCOptions.oilLampFuelMult = this.oilLampFuelMult;
		TFCOptions.pitKilnBurnTime = this.pitKilnBurnTime;
		TFCOptions.bloomeryBurnTime = this.bloomeryBurnTime;
		TFCOptions.charcoalPitBurnTime = this.charcoalPitBurnTime;
		TFCOptions.saplingTimerMultiplier = this.saplingTimerMultiplier;
		TFCOptions.animalTimeMultiplier = this.animalTimeMultiplier;
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
	}

}
