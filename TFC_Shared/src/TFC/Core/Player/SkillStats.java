package TFC.Core.Player;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagInt;

public class SkillStats 
{
	private HashMap skillsMap;

	public SkillStats()
	{
		skillsMap = new HashMap<String, Integer>();
		setSkill("General_Smithing", 0);
		setSkill("Toolsmith", 0);
		setSkill("Weaponsmith", 0);
		setSkill("Armorsmith", 0);
	}

	public void setSkill(String skillName, int amount)
	{
		skillsMap.put(skillName, amount);
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
}
