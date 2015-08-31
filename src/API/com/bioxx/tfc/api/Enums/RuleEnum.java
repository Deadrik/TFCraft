package com.bioxx.tfc.api.Enums;


public enum RuleEnum
{
	ANY("gui.Anvil.Rule1", -1, 0, 2),
	HITANY("gui.Anvil.Rule2", 0, 0, 2),
	HITLAST("gui.Anvil.Rule3", 0, 0, 0),
	HITSECONDFROMLAST("gui.Anvil.Rule4", 0, 1, 1),
	HITTHIRDFROMLAST("gui.Anvil.Rule5", 0, 2, 2),
	HITLASTTWO("gui.Anvil.Rule6", 0, 0, 1),
	HITNOTLAST("gui.Anvil.Rule8", 0, 1, 2),
	PUNCHANY("gui.Anvil.Rule9", 3, 0, 2),
	PUNCHLAST("gui.Anvil.Rule10", 3, 0, 0),
	PUNCHSECONDFROMLAST("gui.Anvil.Rule11", 3, 1, 1),
	PUNCHTHIRDFROMLAST("gui.Anvil.Rule12", 3, 2, 2),
	PUNCHLASTTWO("gui.Anvil.Rule13", 3, 0, 1),
	PUNCHNOTLAST("gui.Anvil.Rule15", 3, 1, 2),
	SHRINKANY("gui.Anvil.Rule16", 6, 0, 2),
	SHRINKLAST("gui.Anvil.Rule17", 6, 0, 0),
	SHRINKSECONDFROMLAST("gui.Anvil.Rule18", 6, 1, 1),
	SHRINKTHIRDFROMLAST("gui.Anvil.Rule19", 6, 2, 2),
	SHRINKLASTTWO("gui.Anvil.Rule20", 6, 0, 1),
	SHRINKNOTLAST("gui.Anvil.Rule22", 6, 1, 2),
	DRAWANY("gui.Anvil.Rule23", 1, 0, 2),
	DRAWLAST("gui.Anvil.Rule24", 1, 0, 0),
	DRAWSECONDFROMLAST("gui.Anvil.Rule25", 1, 1, 1),
	DRAWTHIRDFROMLAST("gui.Anvil.Rule26", 1, 2, 2),
	DRAWLASTTWO("gui.Anvil.Rule27", 1, 0, 1),
	DRAWNOTLAST("gui.Anvil.Rule29", 1, 1, 2),
	UPSETANY("gui.Anvil.Rule30", 5, 0, 2),
	UPSETLAST("gui.Anvil.Rule31", 5, 0, 0),
	UPSETSECONDFROMLAST("gui.Anvil.Rule32", 5, 1, 1),
	UPSETTHIRDFROMLAST("gui.Anvil.Rule33", 5, 2, 2),
	UPSETLASTTWO("gui.Anvil.Rule34", 5, 0, 1),
	UPSETNOTLAST("gui.Anvil.Rule36", 5, 1, 2),
	BENDANY("gui.Anvil.Rule37", 4, 0, 2),
	BENDLAST("gui.Anvil.Rule38", 4, 0, 0),
	BENDSECONDFROMLAST("gui.Anvil.Rule39", 4, 1, 1),
	BENDTHIRDFROMLAST("gui.Anvil.Rule40", 4, 2, 2),
	BENDLASTTWO("gui.Anvil.Rule41", 4, 0, 1),
	BENDNOTLAST("gui.Anvil.Rule43", 4, 1, 2);

	//public static final CraftingRule rules[] = new CraftingRule[]{ANY, HITANY, HITLAST, HITSECONDFROMLAST, HITTHIRDFROMLAST, HITLASTTWO, HITLASTTHREE, HITNOTLAST};

	public final int Min;
	public final int Max;
	public final int Action;
	public final String Name;

	RuleEnum(String n, int action, int min, int max)
	{
		Name = n;
		Min = min;
		Max = max;
		Action = action;
	}

	public boolean matches(int action, int position)
	{
		if (Action == action || Action == -1)
		{
			if(position >= Min && position <= Max) {
				return true;
			}
		}

		return false;
	}

	public boolean matches(RuleEnum rule, int position)
	{
		if (Action == rule.Action || Action == RuleEnum.ANY.Action)
		{
			if(position >= Min && position <= Max) {
				return true;
			}
		}

		return false;
	}

	public boolean matches(int[] actions, int position)
	{
		for(int i = Min; i <= Max; i++)
		{
			if (Action == actions[i] || Action == -1)
			{
				return true;
			}
		}

		return false;
	}
}
