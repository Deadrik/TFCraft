package TFC.Core.Player;

import io.netty.buffer.ByteBuf;

import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraftforge.common.MinecraftForge;
import TFC.TerraFirmaCraft;
import TFC.API.SkillsManager;
import TFC.API.Events.GetSkillMultiplierEvent;
import TFC.API.Events.PlayerSkillEvent;
import TFC.Handlers.Network.AbstractPacket;
import TFC.Handlers.Network.PlayerUpdatePacket;
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
			AbstractPacket pkt = new PlayerUpdatePacket(player, (byte) 1, skillName, i);
			TerraFirmaCraft.packetPipeline.sendTo(pkt, (EntityPlayerMP) player);
			//TerraFirmaCraft.proxy.sendCustomPacketToPlayer((EntityPlayerMP)player, getStatusPacket(skillName, i));
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
			for(Object n : skillCompound.func_150296_c().toArray())
			{
				NBTTagInt nbtf = (NBTTagInt)n;
				setSkill((String)n, nbtf.func_150287_d());
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

//	public static Packet getStatusPacket(String s, int skill)
//	{
//		ByteArrayOutputStream bos=new ByteArrayOutputStream(40);
//		DataOutputStream dos=new DataOutputStream(bos);
//		try
//		{
//			//The packet type sent determines who is expected to process this packet, the client or the server.
//			dos.writeByte(PacketHandler.Packet_Player_Status);
//			dos.writeByte(1);
//			dos.writeUTF(s);
//			dos.writeInt(skill);
//		}
//		catch (IOException e)
//		{
//		}
//		return PacketHandler.getPacket(bos);
//	}
}
