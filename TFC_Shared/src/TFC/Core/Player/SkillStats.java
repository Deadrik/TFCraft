package TFC.Core.Player;

import java.util.HashMap;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;

public class SkillStats 
{
	private HashMap skillsMap;

	public SkillStats()
	{
		skillsMap = new HashMap<String, Float>();
		setSkill("General_Smithing", 1.00f);
		setSkill("Toolsmith", 1.00f);
		setSkill("Weaponsmith", 1.00f);
		setSkill("Armorsmith", 1.00f);
	}

	public void setSkill(String skillName, float amount)
	{
		skillsMap.put(skillName, amount);
	}

	public void increaseSkill(String skillName)
	{
		if(skillsMap.containsKey(skillName))
		{
			//First get what the skill level currently is
			Float i = (Float) skillsMap.get(skillName);
			//Do a calculation and increase the skill level
			float amount = 0;
			//Remove the old skill and add the new skill level. 
			//The put method replaces the old identical entry.
			skillsMap.put(skillName, i+amount);
		}
	}

	public float getSkill(String skillName, float amount)
	{
		if(skillsMap.containsKey(skillName))
			return (Float) skillsMap.get(skillName);
		else
			return 0;
	}

	public void readNBT(NBTTagCompound nbt)
	{
		if (nbt.hasKey("skillCompound"))
		{
			NBTTagCompound skillCompound = nbt.getCompoundTag("skillCompound");
			for(Object n : skillCompound.getTags())
			{
				NBTTagFloat nbtf = (NBTTagFloat)n;
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
			float f = (Float) skillsMap.get(k);
			skillCompound.setFloat(k, f);
		}

		nbt.setCompoundTag("skillCompound", skillCompound);
	}
}
