package com.bioxx.tfc.api.Crafting;

import com.bioxx.tfc.api.Enums.RuleEnum;

import net.minecraft.util.IIcon;

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
