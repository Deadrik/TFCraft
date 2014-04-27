package com.bioxx.tfc.Core.Player;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.PlayerUpdatePacket;
import com.bioxx.tfc.api.SkillsManager;
import com.bioxx.tfc.api.Events.GetSkillMultiplierEvent;
import com.bioxx.tfc.api.Events.PlayerSkillEvent;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.MinecraftForge;
import cpw.mods.fml.common.network.ByteBufUtils;

public class SkillStats
{
	private HashMap skillsMap;
	private EntityPlayer player;

	public SkillStats(EntityPlayer p)
	{
		player = p;
		skillsMap = new HashMap<String, Integer>();
		for(String s : SkillsManager.instance.getSkillsArray())
		{
			setSkill(s, 0);
		}
	}

	public Object[] getSkillsKeysArray()
	{
		return skillsMap.keySet().toArray();
	}

	public void setSkill(String skillName, int amount)
	{
		skillsMap.put(skillName, amount);
	}

	public void setSkillSave(String skillName, int amount)
	{
		skillsMap.put(skillName, amount);
		writeNBT(player.getEntityData());
	}

	public void increaseSkill(String skillName, int amount)
	{
		PlayerSkillEvent.Increase event = new PlayerSkillEvent.Increase(this.player, skillName, amount);
		if(!MinecraftForge.EVENT_BUS.post(event))
		{
			if(skillsMap.containsKey(skillName))
			{
				//First get what the skill level currently is
				int i = (Integer) skillsMap.get(skillName);
				//The put method replaces the old identical entry.
				skillsMap.put(skillName, i+amount);
			}
			else
			{
				skillsMap.put(skillName, amount);
			}
		}

		int i = (Integer) skillsMap.get(skillName);
		if(player instanceof EntityPlayerMP)
		{
			AbstractPacket pkt = new PlayerUpdatePacket(1, skillName, i);
			TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) player);
		}
		writeNBT(player.getEntityData());
	}

	public int getSkill(String skillName)
	{
		if(skillsMap.containsKey(skillName))
			return (Integer) skillsMap.get(skillName);
		else
			return 0;
	}

	public float getSkillMultiplier(String skillName)
	{
		int skill = getSkill(skillName);
		float mult = getSkillMult(skill);
		GetSkillMultiplierEvent event = new GetSkillMultiplierEvent(player, skillName, mult);
		MinecraftForge.EVENT_BUS.post(event);
		return event.skillResult;
	}

	public static float getSkillMult(int skill)
	{
		return 1 - (100f / (100f + skill));
	}

	public void readNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("skillCompound"))
		{
			NBTTagCompound skillCompound = nbt.getCompoundTag("skillCompound");
			for(Object n : skillCompound.func_150296_c())
			{
				String skill = (String) n;
				setSkill(skill, skillCompound.getInteger(skill));
			}
		}
	}

	/**
	 * Writes food stats to an NBT object.
	 */
	public void writeNBT(NBTTagCompound nbt)
	{
		NBTTagCompound skillCompound = new NBTTagCompound();
		Object[] keys = skillsMap.keySet().toArray();
		for(Object o : keys)
		{
			String k = (String)o;
			int f = (Integer) skillsMap.get(k);
			skillCompound.setInteger(k, f);
		}
		nbt.setTag("skillCompound", skillCompound);
	}

	public void toOutBuffer(ByteBuf buffer)
	{
		Object[] keys = skillsMap.keySet().toArray();
		buffer.writeInt(keys.length);
		for(Object o : keys)
		{
			String k = (String)o;
			int f = (Integer) skillsMap.get(k);
			ByteBufUtils.writeUTF8String(buffer, k);
			buffer.writeInt(f);
		}
	}
}
