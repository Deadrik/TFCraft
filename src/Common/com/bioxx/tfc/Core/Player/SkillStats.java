package com.bioxx.tfc.Core.Player;

import java.util.HashMap;
import java.util.Map;

import io.netty.buffer.ByteBuf;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;

import net.minecraftforge.common.MinecraftForge;

import cpw.mods.fml.common.network.ByteBufUtils;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.PlayerUpdatePacket;
import com.bioxx.tfc.api.SkillsManager;
import com.bioxx.tfc.api.SkillsManager.Skill;
import com.bioxx.tfc.api.Events.GetSkillMultiplierEvent;
import com.bioxx.tfc.api.Events.PlayerSkillEvent;

public class SkillStats
{
	private Map<Skill, Integer> skillsMap;
	private EntityPlayer player;

	public SkillStats(EntityPlayer p)
	{
		player = p;
		skillsMap = new HashMap<Skill, Integer>();
		for(Skill s : SkillsManager.instance.getSkillsArray())
		{
			setSkill(s.skillName, 0);
		}
	}

	public Object[] getSkillsKeysArray()
	{
		return skillsMap.keySet().toArray();
	}

	public void setSkill(String skillName, int amount)
	{
		Skill sk = SkillsManager.instance.getSkill(skillName);
		if(sk != null)
			skillsMap.put(sk, amount);
	}

	public void setSkillSave(String skillName, int amount)
	{
		Skill sk = SkillsManager.instance.getSkill(skillName);
		if(sk != null)
			skillsMap.put(sk, amount);
		writeNBT(player.getEntityData());
	}

	public void increaseSkill(String skillName, int amount)
	{
		Skill sk = SkillsManager.instance.getSkill(skillName);
		PlayerSkillEvent.Increase event = new PlayerSkillEvent.Increase(this.player, skillName, amount);
		if(!MinecraftForge.EVENT_BUS.post(event))
		{
			if(skillsMap.containsKey(sk))
			{
				//First get what the skill level currently is
				int i = skillsMap.get(sk);
				//The put method replaces the old identical entry.
				skillsMap.put(sk, i+amount);
			}
			else
			{
				skillsMap.put(sk, amount);
			}
		}

		int i = skillsMap.get(sk);
		if(player instanceof EntityPlayerMP)
		{
			AbstractPacket pkt = new PlayerUpdatePacket(1, skillName, i);
			TerraFirmaCraft.PACKET_PIPELINE.sendTo(pkt, (EntityPlayerMP) player);
		}
		writeNBT(player.getEntityData());
	}

	public int getSkillRaw(String skillName)
	{
		Skill sk = SkillsManager.instance.getSkill(skillName);
		if(skillsMap.containsKey(sk))
			return skillsMap.get(sk);
		else
			return 0;
	}

	public SkillRank getSkillRank(String skillName)
	{
		float raw = getSkillMultiplier(skillName);
		if(raw < 0.25)
		{
			return SkillRank.Novice;
		}
		else if(raw < 0.5)
		{
			return SkillRank.Adept;
		}
		else if(raw < 0.75)
		{
			return SkillRank.Expert;
		}
		else
		{
			return SkillRank.Master;
		}
	}

	public float getPercToNextRank(String skillName)
	{
		float raw = getSkillMultiplier(skillName);
		if(raw < 0.25)
		{
			return raw/0.25f;
		}
		else if(raw < 0.5)
		{
			return (raw-0.25f)/0.25f;
		}
		else if(raw < 0.75)
		{
			return (raw-0.5f)/0.25f;
		}
		else
		{
			return (raw-0.75f)/0.25f;
		}
	}

	public float getSkillMultiplier(String skillName)
	{
		int skill = getSkillRaw(skillName);
		Skill sk = SkillsManager.instance.getSkill(skillName);
		float mult = getSkillMult(skill, sk.skillRate);
		GetSkillMultiplierEvent event = new GetSkillMultiplierEvent(player, skillName, mult);
		MinecraftForge.EVENT_BUS.post(event);
		return event.skillResult;
	}

	private float getSkillMult(int skill, float rate)
	{
		return 1 - (rate / (rate + skill));
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
			Skill k = (Skill)o;
			int f = skillsMap.get(k);
			skillCompound.setInteger(k.skillName, f);
		}
		nbt.setTag("skillCompound", skillCompound);
	}

	public void toOutBuffer(ByteBuf buffer)
	{
		Object[] keys = skillsMap.keySet().toArray();
		buffer.writeInt(keys.length);
		for(Object o : keys)
		{
			Skill k = (Skill)o;
			int f = skillsMap.get(k);
			ByteBufUtils.writeUTF8String(buffer, k.skillName);
			buffer.writeInt(f);
		}
	}

	public enum SkillRank
	{
		Novice("gui.skill.novice"), Adept("gui.skill.adept"), Expert("gui.skill.expert"), Master("gui.skill.master");

		String name;
		private SkillRank(String local)
		{
			name = local;
		}

		public String getUnlocalizedName()
		{
			return name;
		}

		public String getLocalizedName()
		{
			return TFC_Core.translate(name);
		}
	}


}
