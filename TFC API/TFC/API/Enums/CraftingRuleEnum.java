package TFC.API.Enums;

import TFC.API.Util.StringUtil;

public enum CraftingRuleEnum
{
	ANY(StringUtil.localize("gui.Anvil.Rule1"), -1, 0, 2),
	HITANY(StringUtil.localize("gui.Anvil.Rule2"), 0, 0, 2),
	HITLAST(StringUtil.localize("gui.Anvil.Rule3"), 0, 0, 0),
	HITSECONDFROMLAST(StringUtil.localize("gui.Anvil.Rule4"), 0, 1, 1),
	HITTHIRDFROMLAST(StringUtil.localize("gui.Anvil.Rule5"), 0, 2, 2),
	HITLASTTWO(StringUtil.localize("gui.Anvil.Rule6"), 0, 0, 1),
	HITNOTLAST(StringUtil.localize("gui.Anvil.Rule8"), 0, 1, 2),
	PUNCHANY(StringUtil.localize("gui.Anvil.Rule9"), 3, 0, 2),
	PUNCHLAST(StringUtil.localize("gui.Anvil.Rule10"), 3, 0, 0),
	PUNCHSECONDFROMLAST(StringUtil.localize("gui.Anvil.Rule11"), 3, 1, 1),
	PUNCHTHIRDFROMLAST(StringUtil.localize("gui.Anvil.Rule12"), 3, 2, 2),
	PUNCHLASTTWO(StringUtil.localize("gui.Anvil.Rule13"), 3, 0, 1),
	PUNCHNOTLAST(StringUtil.localize("gui.Anvil.Rule15"), 3, 1, 2),
	SHRINKANY(StringUtil.localize("gui.Anvil.Rule16"), 6, 0, 2),
	SHRINKLAST(StringUtil.localize("gui.Anvil.Rule17"), 6, 0, 0),
	SHRINKSECONDFROMLAST(StringUtil.localize("gui.Anvil.Rule18"), 6, 1, 1),
	SHRINKTHIRDFROMLAST(StringUtil.localize("gui.Anvil.Rule19"), 6, 2, 2),
	SHRINKLASTTWO(StringUtil.localize("gui.Anvil.Rule20"), 6, 0, 1),
	SHRINKNOTLAST(StringUtil.localize("gui.Anvil.Rule22"), 6, 1, 2),
	/*QUENCHANY("Quench, Any", 2, 0, 2),
    QUENCHLAST("Quench, Last", 2, 0, 0),
    QUENCHSECONDFROMLAST("Quench, Second From Last", 2, 1, 1),
    QUENCHTHIRDFROMLAST("Quench, Third From Last", 2, 2, 2),
    QUENCHLASTTWO("Quench, Last Two", 2, 0, 1),
    QUENCHLASTTHREE("Quench, Last Three", 2, 0, 2),
    QUENCHNOTLAST("Quench, Not Last", 2, 1, 2),*/
	DRAWANY(StringUtil.localize("gui.Anvil.Rule23"), 1, 0, 2),
	DRAWLAST(StringUtil.localize("gui.Anvil.Rule24"), 1, 0, 0),
	DRAWSECONDFROMLAST(StringUtil.localize("gui.Anvil.Rule25"), 1, 1, 1),
	DRAWTHIRDFROMLAST(StringUtil.localize("gui.Anvil.Rule26"), 1, 2, 2),
	DRAWLASTTWO(StringUtil.localize("gui.Anvil.Rule27"), 1, 0, 1),
	DRAWNOTLAST(StringUtil.localize("gui.Anvil.Rule29"), 1, 1, 2),
	UPSETANY(StringUtil.localize("gui.Anvil.Rule30"), 5, 0, 2),
	UPSETLAST(StringUtil.localize("gui.Anvil.Rule31"), 5, 0, 0),
	UPSETSECONDFROMLAST(StringUtil.localize("gui.Anvil.Rule32"), 5, 1, 1),
	UPSETTHIRDFROMLAST(StringUtil.localize("gui.Anvil.Rule33"), 5, 2, 2),
	UPSETLASTTWO(StringUtil.localize("gui.Anvil.Rule34"), 5, 0, 1),
	UPSETNOTLAST(StringUtil.localize("gui.Anvil.Rule36"), 5, 1, 2),
	BENDANY(StringUtil.localize("gui.Anvil.Rule37"), 4, 0, 2),
	BENDLAST(StringUtil.localize("gui.Anvil.Rule38"), 4, 0, 0),
	BENDSECONDFROMLAST(StringUtil.localize("gui.Anvil.Rule39"), 4, 1, 1),
	BENDTHIRDFROMLAST(StringUtil.localize("gui.Anvil.Rule40"), 4, 2, 2),
	BENDLASTTWO(StringUtil.localize("gui.Anvil.Rule41"), 4, 0, 1),
	BENDNOTLAST(StringUtil.localize("gui.Anvil.Rule43"), 4, 1, 2);

	//public static final CraftingRule rules[] = new CraftingRule[]{ANY, HITANY, HITLAST, HITSECONDFROMLAST, HITTHIRDFROMLAST, HITLASTTWO, HITLASTTHREE, HITNOTLAST};

	public final int Min;
	public final int Max;
	public final int Action;
	public final String Name;

	CraftingRuleEnum(String n, int action, int min, int max)
	{
		Name = n;
		Min = min;
		Max = max;
		Action = action;
	}

	public boolean matches(int action, int position)
	{
		if((Action == action || Action == -1)) {
			if(position >= Min && position <= Max) {
				return true;
			}
		}

		return false;
	}

	public boolean matches(CraftingRuleEnum R, int position)
	{
		if((Action == R.Action || Action == this.ANY.Action)) {
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
			if((Action == actions[i] || Action == -1)) {
				return true;
			}
		}

		return false;
	}
}
