package net.minecraft.src.TFC_Core.General;

public enum CraftingRule
{
    ANY("Any", -1, 0, 2),
    HITANY("Hit Any", 0, 0, 2),
    HITLAST("Hit Last", 0, 0, 0),
    HITSECONDFROMLAST("Hit Second", 0, 1, 1),
    HITTHIRDFROMLAST("Hit Third", 0, 2, 2),
    HITLASTTWO("Hit Last Two", 0, 0, 1),
    HITLASTTHREE("Hit Last Three", 0, 0, 2),
    HITNOTLAST("Hit Not Last", 0, 1, 2),
    PUNCHANY("Punch Any", 0, 0, 2),
    PUNCHLAST("Punch Last", 0, 0, 0),
    PUNCHSECONDFROMLAST("Punch Second", 0, 1, 1),
    PUNCHTHIRDFROMLAST("Punch Third", 0, 2, 2),
    PUNCHLASTTWO("Punch Last Two", 0, 0, 1),
    PUNCHLASTTHREE("Punch Last Three", 0, 0, 2),
    PUNCHNOTLAST("Punch Not Last", 0, 1, 2),
    SHRINKANY("Shrink Any", 0, 0, 2),
    SHRINKLAST("Shrink Last", 0, 0, 0),
    SHRINKSECONDFROMLAST("Shrink Second", 0, 1, 1),
    SHRINKTHIRDFROMLAST("Shrink Third", 0, 2, 2),
    SHRINKLASTTWO("Shrink Last Two", 0, 0, 1),
    SHRINKLASTTHREE("Shrink Last Three", 0, 0, 2),
    SHRINKNOTLAST("Shrink Not Last", 0, 1, 2),
    QUENCHANY("Quench Any", 0, 0, 2),
    QUENCHLAST("Quench Last", 0, 0, 0),
    QUENCHSECONDFROMLAST("Quench Second", 0, 1, 1),
    QUENCHTHIRDFROMLAST("Quench Third", 0, 2, 2),
    QUENCHLASTTWO("Quench Last Two", 0, 0, 1),
    QUENCHLASTTHREE("Quench Last Three", 0, 0, 2),
    QUENCHNOTLAST("Quench Not Last", 0, 1, 2),
    DRAWANY("Draw Any", 0, 0, 2),
    DRAWLAST("Draw Last", 0, 0, 0),
    DRAWSECONDFROMLAST("Draw Second", 0, 1, 1),
    DRAWTHIRDFROMLAST("Draw Third", 0, 2, 2),
    DRAWLASTTWO("Draw Last Two", 0, 0, 1),
    DRAWLASTTHREE("Draw Last Three", 0, 0, 2),
    DRAWNOTLAST("Draw Not Last", 0, 1, 2),
    UPSETANY("Upset Any", 0, 0, 2),
    UPSETLAST("Upset Last", 0, 0, 0),
    UPSETSECONDFROMLAST("Upset Second", 0, 1, 1),
    UPSETTHIRDFROMLAST("Upset Third", 0, 2, 2),
    UPSETLASTTWO("Upset Last Two", 0, 0, 1),
    UPSETLASTTHREE("Upset Last Three", 0, 0, 2),
    UPSETNOTLAST("Upset Not Last", 0, 1, 2),
    BENDANY("Bend Any", 0, 0, 2),
    BENDLAST("Bend Last", 0, 0, 0),
    BENDSECONDFROMLAST("Bend Second", 0, 1, 1),
    BENDTHIRDFROMLAST("Bend Third", 0, 2, 2),
    BENDLASTTWO("Bend Last Two", 0, 0, 1),
    BENDLASTTHREE("Bend Last Three", 0, 0, 2),
    BENDNOTLAST("Bend Not Last", 0, 1, 2);
    
    //public static final CraftingRule rules[] = new CraftingRule[]{ANY, HITANY, HITLAST, HITSECONDFROMLAST, HITTHIRDFROMLAST, HITLASTTWO, HITLASTTHREE, HITNOTLAST};
    
    public final int Min;
    public final int Max;
    public final int Action;
    public final String Name;
    
    CraftingRule(String n, int action, int min, int max)
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
    
    public boolean matches(CraftingRule R, int position)
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
