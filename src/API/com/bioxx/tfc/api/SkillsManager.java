package com.bioxx.tfc.api;

import java.util.ArrayList;
import java.util.List;

public class SkillsManager 
{
	private List<Skill> skillsArray = new ArrayList<Skill>();

	public static SkillsManager instance = new SkillsManager();

	public SkillsManager()
	{

	}

	public void registerSkill(String name, int rate)
	{
		skillsArray.add(new Skill(name, rate));
	}

	public List<Skill> getSkillsArray()
	{
		return this.skillsArray;
	}

	public Skill getSkill(String name)
	{
		for(Skill s : skillsArray)
			if(s.skillName.equalsIgnoreCase(name))
				return s;
		return null;
	}

	public class Skill
	{
		public String skillName;
		public int skillRate;
		public Skill(String n, int r)
		{
			skillName = n;
			skillRate = r;
		}
	}
}
