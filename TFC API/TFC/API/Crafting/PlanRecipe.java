package TFC.API.Crafting;

import net.minecraft.util.Icon;
import TFC.API.Enums.RuleEnum;

public class PlanRecipe 
{
	public RuleEnum[] rules;
	public Icon icon;

	public PlanRecipe(RuleEnum[] r, Icon i)
	{
		rules = r;
		icon = i;
	}

	public PlanRecipe(RuleEnum[] r)
	{
		rules = r;
	}
}
