package TFC.Core.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.HashMap;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.Handlers.PacketHandler;

public class SkillStats 
{
	private HashMap skillsMap;
	private EntityPlayer player;

	public SkillStats(EntityPlayer p)
	{
		player = p;
		skillsMap = new HashMap<String, Integer>();
		setSkill("General_Smithing", 0);
		setSkill("Toolsmith", 0);
		setSkill("Weaponsmith", 0);
		setSkill("Armorsmith", 0);
	}

	public void init()
	{

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
		if(skillsMap.containsKey(skillName))
		{
			//First get what the skill level currently is
			int i = (Integer) skillsMap.get(skillName);
			//Remove the old skill and add the new skill level. 
			//The put method replaces the old identical entry.
			skillsMap.put(skillName, i+amount);
		}
		else
			skillsMap.put(skillName, amount);

		int i = (Integer) skillsMap.get(skillName);
		TerraFirmaCraft.proxy.sendCustomPacketToPlayer((EntityPlayerMP)player, getStatusPacket(skillName, i));
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
		return getSkillMult(skill);
	}

	public static float getSkillMult(int skill)
	{
		return 1-(100f/(100f+skill));
	}

	public void readNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("skillCompound"))
		{
			NBTTagCompound skillCompound = nbt.getCompoundTag("skillCompound");
			for(Object n : skillCompound.getTags())
			{
				NBTTagInt nbtf = (NBTTagInt)n;
				setSkill(nbtf.getName(), nbtf.data);
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

		nbt.setCompoundTag("skillCompound", skillCompound);
	}

	public void toOutStream(DataOutputStream dos)
	{
		Object[] keys = skillsMap.keySet().toArray();
		for(Object o : keys)
		{
			String k = (String)o;
			int f = (Integer) skillsMap.get(k);
			try {
				dos.writeUTF(k);
				dos.writeInt(f);
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	}

	public static Packet getStatusPacket(String s, int skill)
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(40);
		DataOutputStream dos=new DataOutputStream(bos);
		Packet250CustomPayload pkt=new Packet250CustomPayload();
		try 
		{
			//The packet type sent determines who is expected to process this packet, the client or the server.
			dos.writeByte(PacketHandler.Packet_Player_Status);
			dos.writeByte(1);
			dos.writeUTF(s);
			dos.writeInt(skill);

			pkt.channel=Reference.ModChannel;
			pkt.data = bos.toByteArray();
			pkt.length= pkt.data.length;
			pkt.isChunkDataPacket=false;
		} 
		catch (IOException e) 
		{

		}
		return pkt;
	}
}
