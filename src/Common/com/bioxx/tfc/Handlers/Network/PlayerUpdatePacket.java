package com.bioxx.tfc.Handlers.Network;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;

import cpw.mods.fml.common.network.ByteBufUtils;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.Player.FoodStatsTFC;
import com.bioxx.tfc.Core.Player.PlayerInventory;
import com.bioxx.tfc.Core.Player.SkillStats;

public class PlayerUpdatePacket extends AbstractPacket
{
	private byte flag;
	private float stomachLevel;
	private float waterLevel;
	private long soberTime;
	private float nutrFruit;
	private float nutrVeg;
	private float nutrGrain;
	private float nutrProtein;
	private float nutrDairy;
	private SkillStats playerSkills;
	private String skillName;
	private int skillLevel;
	private boolean craftingTable;
	private Map<String, Integer> skillMap = new HashMap<String, Integer>();

	public PlayerUpdatePacket() {}

	public PlayerUpdatePacket(EntityPlayer p, int f)
	{
		this.flag = (byte)f;
		if(this.flag == 0)
		{
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(p);
			this.stomachLevel = fs.stomachLevel;
			this.waterLevel = fs.waterLevel;
			this.soberTime = fs.soberTime;
			this.nutrFruit = fs.nutrFruit;
			this.nutrVeg = fs.nutrVeg;
			this.nutrGrain = fs.nutrGrain;
			this.nutrProtein = fs.nutrProtein;
			this.nutrDairy = fs.nutrDairy;
		}
		else if(this.flag == 2)
		{
			this.craftingTable = p.getEntityData().getBoolean("craftingTable");
		}
		else if(this.flag == 3)
		{
			this.playerSkills = TFC_Core.getSkillStats(p);
		}
		/*else if(this.flag == 4)
		{
			// flag 4 -> Send a request to the server for the skills data.
		}*/
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
	public void encodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		buffer.writeByte(this.flag);
		if(this.flag == 0)
		{
			buffer.writeFloat(this.stomachLevel);
			buffer.writeFloat(this.waterLevel);
			buffer.writeLong(this.soberTime);
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
		/*else if(this.flag == 4)
		{
			// flag is all we need
		}*/
	}

	@Override
	public void decodeInto(ChannelHandlerContext ctx, ByteBuf buffer)
	{
		this.flag = buffer.readByte();
		if(this.flag == 0)
		{
			this.stomachLevel = buffer.readFloat();
			this.waterLevel = buffer.readFloat();
			this.soberTime = buffer.readLong();
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
		/*else if(this.flag == 4)
		{
			// flag is all we need
		}*/
	}

	@Override
	public void handleClientSide(EntityPlayer player)
	{
		if(this.flag == 0)
		{
			FoodStatsTFC fs = TFC_Core.getPlayerFoodStats(player);
			fs.stomachLevel = this.stomachLevel;
			fs.waterLevel = this.waterLevel;
			fs.soberTime = this.soberTime;
			fs.nutrFruit = this.nutrFruit;
			fs.nutrVeg = this.nutrVeg;
			fs.nutrGrain = this.nutrGrain;
			fs.nutrProtein = this.nutrProtein;
			fs.nutrDairy = this.nutrDairy;
			TFC_Core.setPlayerFoodStats(player, fs);
		}
		else if(this.flag == 1)
		{
			this.playerSkills = TFC_Core.getSkillStats(player);
			this.playerSkills.setSkillSave(skillName, skillLevel);
		}
		else if(this.flag == 2)
		{
			if(this.craftingTable && !player.getEntityData().hasKey("craftingTable"))
			{
				player.getEntityData().setBoolean("craftingTable", this.craftingTable);
				PlayerInventory.upgradePlayerCrafting(player);
			}
		}
		else if(this.flag == 3)
		{
			this.playerSkills = TFC_Core.getSkillStats(player);
			for(String skill : skillMap.keySet())
			{
				playerSkills.setSkillSave(skill, skillMap.get(skill));
			}
			skillMap.clear();
		}
		/*else if(this.flag == 4)
		{
			//NOOP on client
		}*/
	}

	@Override
	public void handleServerSide(EntityPlayer player)
	{
		if(this.flag == 4)
		{
			AbstractPacket pkt = new PlayerUpdatePacket(player, 3);
			TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) player);
		}
	}

}
