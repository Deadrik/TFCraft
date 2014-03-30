package TFC.API.Crafting;

import net.minecraft.util.IIcon;
import TFC.API.Enums.RuleEnum;

public class PlanRecipe 
{
	public RuleEnum[] rules;
	public IIcon icon;

	public PlanRecipe(RuleEnum[] r, IIcon i)
	{
		rules = r;
		icon = i;
	}

	public PlanRecipe(RuleEnum[] r)
	{
		rules = r;
	}
}
