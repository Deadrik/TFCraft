package com.bioxx.tfc.Handlers.Network;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Core.Player.SkillStats;

import cpw.mods.fml.common.network.ByteBufUtils;
import cpw.mods.fml.common.network.simpleimpl.IMessage;
import cpw.mods.fml.common.network.simpleimpl.IMessageHandler;
import cpw.mods.fml.common.network.simpleimpl.MessageContext;

public class PlayerUpdatePacket implements IMessage
{
	private byte flag;
	private float stomachLevel;
	private float waterLevel;
	private float nutrFruit;
	private float nutrVeg;
	private float nutrGrain;
	private float nutrProtein;
	private float nutrDairy;
	private SkillStats playerSkills;
	private String skillName;
	private int skillLevel;
	private boolean craftingTable = false;
	private HashMap<String, Integer> skillMap = new HashMap<String, Integer>();

	public PlayerUpdatePacket() {}

	public PlayerUpdatePacket(EntityPlayer P, int f)
	{
		this.flag = (byte)f;
		if(this.flag == 0)
		{
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(P);
			this.stomachLevel = fs.stomachLevel;
			this.waterLevel = fs.waterLevel;
			this.nutrFruit = fs.nutrFruit;
			this.nutrVeg = fs.nutrVeg;
			this.nutrGrain = fs.nutrGrain;
			this.nutrProtein = fs.nutrProtein;
			this.nutrDairy = fs.nutrDairy;
		}
		else if(this.flag == 2)
		{
			this.craftingTable = P.getEntityData().getBoolean("craftingTable");
		}
		else if(this.flag == 3)
		{
			this.playerSkills = TFC_Core.getSkillStats(P);
		}
		else if(this.flag == 4)
		{
			// flag 4 -> Send a request to the server for the skills data.
		}
	}

	public PlayerUpdatePacket(int f, String name, int lvl)
	{
		this.flag = (byte)f;
		if(this.flag == 1)
		{
			this.skillName = name;
			this.skillLevel = lvl;
		}
	}

	@Override
	public void toBytes(ByteBuf buffer)
	{
		buffer.writeByte(this.flag);
		if(this.flag == 0)
		{
			buffer.writeFloat(this.stomachLevel);
			buffer.writeFloat(this.waterLevel);
			buffer.writeFloat(this.nutrFruit);
			buffer.writeFloat(this.nutrVeg);
			buffer.writeFloat(this.nutrGrain);
			buffer.writeFloat(this.nutrProtein);
			buffer.writeFloat(this.nutrDairy);
		}
		else if(this.flag == 1)
		{
			ByteBufUtils.writeUTF8String(buffer, this.skillName);
			buffer.writeInt(this.skillLevel);
		}
		else if(this.flag == 2)
		{
			buffer.writeBoolean(this.craftingTable);
		}
		else if(this.flag == 3)
		{
			this.playerSkills.toOutBuffer(buffer);
		}
		else if(this.flag == 4)
		{
			// flag is all we need
		}
	}

	@Override
	public void fromBytes(ByteBuf buffer)
	{
		this.flag = buffer.readByte();
		if(this.flag == 0)
		{
			this.stomachLevel = buffer.readFloat();
			this.waterLevel = buffer.readFloat();
			this.nutrFruit = buffer.readFloat();
			this.nutrVeg = buffer.readFloat();
			this.nutrGrain = buffer.readFloat();
			this.nutrProtein = buffer.readFloat();
			this.nutrDairy = buffer.readFloat();
		}
		else if(this.flag == 1)
		{
			this.skillName = ByteBufUtils.readUTF8String(buffer);
			this.skillLevel = buffer.readInt();
		}
		else if(this.flag == 2)
		{
			this.craftingTable = buffer.readBoolean();
		}
		else if(this.flag == 3)
		{
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
		else if(this.flag == 4)
		{
			// flag is all we need
		}
	}

	public static class ServerHandler implements IMessageHandler<PlayerUpdatePacket, IMessage> {

		@Override
		public IMessage onMessage(PlayerUpdatePacket message, MessageContext ctx) {

			EntityPlayer player = TerraFirmaCraft.proxy.getPlayerFromMessageContext(ctx);

			if (message.flag == 4) {
				// return to client with id=3
				return new PlayerUpdatePacket(player, 3);
			}
			return null;
		}
	}

	public static class ClientHandler implements IMessageHandler<PlayerUpdatePacket, IMessage> {

		@Override
		public IMessage onMessage(PlayerUpdatePacket message, MessageContext ctx) {

			EntityPlayer player = TerraFirmaCraft.proxy.getPlayerFromMessageContext(ctx);

			if (message.flag == 0) {
				FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
				fs.stomachLevel = message.stomachLevel;
				fs.waterLevel = message.waterLevel;
				fs.nutrFruit = message.nutrFruit;
				fs.nutrVeg = message.nutrVeg;
				fs.nutrGrain = message.nutrGrain;
				fs.nutrProtein = message.nutrProtein;
				fs.nutrDairy = message.nutrDairy;
				TFC_Core.setPlayerFoodStats(player, fs);
			}
			else if (message.flag == 1) {
				message.playerSkills = TFC_Core.getSkillStats(player);
				message.playerSkills.setSkillSave(message.skillName, message.skillLevel);
			}
			else if (message.flag == 2) {
				if (message.craftingTable && !player.getEntityData().hasKey("craftingTable")) {
					player.getEntityData().setBoolean("craftingTable", message.craftingTable);
					PlayerInventory.upgradePlayerCrafting(player);
				}
			}
			else if (message.flag == 3) {
				message.playerSkills = TFC_Core.getSkillStats(player);
				for (String skill : message.skillMap.keySet()) {
					message.playerSkills.setSkillSave(skill, message.skillMap.get(skill));
				}
				message.skillMap.clear();
			}
			return null;
		}
	}
}
