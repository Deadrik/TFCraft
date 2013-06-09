package TFC.API.Enums;

public enum CraftingRuleEnum
{
    ANY("Any", -1, 0, 2),
    HITANY("Hit, Any", 0, 0, 2),
    HITLAST("Hit, Last", 0, 0, 0),
    HITSECONDFROMLAST("Hit, Second From Last", 0, 1, 1),
    HITTHIRDFROMLAST("Hit, Third From Last", 0, 2, 2),
    HITLASTTWO("Hit, Last Two", 0, 0, 1),
    HITLASTTHREE("Hit, Last Three", 0, 0, 2),
    HITNOTLAST("Hit, Not Last", 0, 1, 2),
    PUNCHANY("Punch, Any", 3, 0, 2),
    PUNCHLAST("Punch, Last", 3, 0, 0),
    PUNCHSECONDFROMLAST("Punch, Second From Last", 3, 1, 1),
    PUNCHTHIRDFROMLAST("Punch, Third From Last", 3, 2, 2),
    PUNCHLASTTWO("Punch, Last Two", 3, 0, 1),
    PUNCHLASTTHREE("Punch, Last Three", 3, 0, 2),
    PUNCHNOTLAST("Punch, Not Last", 3, 1, 2),
    SHRINKANY("Shrink, Any", 6, 0, 2),
    SHRINKLAST("Shrink, Last", 6, 0, 0),
    SHRINKSECONDFROMLAST("Shrink, Second From Last", 6, 1, 1),
    SHRINKTHIRDFROMLAST("Shrink, Third From Last", 6, 2, 2),
    SHRINKLASTTWO("Shrink, Last Two", 6, 0, 1),
    SHRINKLASTTHREE("Shrink, Last Three", 6, 0, 2),
    SHRINKNOTLAST("Shrink, Not Last", 6, 1, 2),
    /*QUENCHANY("Quench, Any", 2, 0, 2),
    QUENCHLAST("Quench, Last", 2, 0, 0),
    QUENCHSECONDFROMLAST("Quench, Second From Last", 2, 1, 1),
    QUENCHTHIRDFROMLAST("Quench, Third From Last", 2, 2, 2),
    QUENCHLASTTWO("Quench, Last Two", 2, 0, 1),
    QUENCHLASTTHREE("Quench, Last Three", 2, 0, 2),
    QUENCHNOTLAST("Quench, Not Last", 2, 1, 2),*/
    DRAWANY("Draw, Any", 1, 0, 2),
    DRAWLAST("Draw, Last", 1, 0, 0),
    DRAWSECONDFROMLAST("Draw, Second From Last", 1, 1, 1),
    DRAWTHIRDFROMLAST("Draw, Third From Last", 1, 2, 2),
    DRAWLASTTWO("Draw, Last Two", 1, 0, 1),
    DRAWLASTTHREE("Draw, Last Three", 1, 0, 2),
    DRAWNOTLAST("Draw, Not Last", 1, 1, 2),
    UPSETANY("Upset, Any", 5, 0, 2),
    UPSETLAST("Upset, Last", 5, 0, 0),
    UPSETSECONDFROMLAST("Upset, Second From Last", 5, 1, 1),
    UPSETTHIRDFROMLAST("Upset, Third From Last", 5, 2, 2),
    UPSETLASTTWO("Upset, Last Two From Last", 5, 0, 1),
    UPSETLASTTHREE("Upset, Last Three", 5, 0, 2),
    UPSETNOTLAST("Upset, Not Last", 5, 1, 2),
    BENDANY("Bend, Any", 4, 0, 2),
    BENDLAST("Bend, Last", 4, 0, 0),
    BENDSECONDFROMLAST("Bend, Second From Last", 4, 1, 1),
    BENDTHIRDFROMLAST("Bend, Third From Last", 4, 2, 2),
    BENDLASTTWO("Bend, Last Two", 4, 0, 1),
    BENDLASTTHREE("Bend, Last Three", 4, 0, 2),
    BENDNOTLAST("Bend, Not Last", 4, 1, 2);
    
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
        if((Action == action || Action == -1))
            if(position >= Min && position <= Max)
                return true;
        
        return false;
    }
    
    public boolean matches(CraftingRuleEnum R, int position)
    {
        if((Action == R.Action || Action == this.ANY.Action))
            if(position >= Min && position <= Max)
                return true;
        
        return false;
    }
    
    public boolean matches(int[] actions, int position)
    {
        for(int i = Min; i <= Max; i++)
        {
            if((Action == actions[i] || Action == -1))
                return true;
        }
        
        return false;
    }
}
