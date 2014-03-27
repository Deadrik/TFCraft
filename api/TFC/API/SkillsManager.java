package TFC.API;

import java.util.ArrayList;

public class SkillsManager 
{
	private ArrayList<String> skillsArray = new ArrayList<String>();

	public static SkillsManager instance = new SkillsManager();

	public SkillsManager()
	{

	}

	public void registerSkill(String name)
	{
		skillsArray.add(name);
	}

	public ArrayList<String> getSkillsArray()
	{
		return this.skillsArray;
	}

}
