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
    HITNOTLAST("Hit Not Last", 0, 1, 2);
    
    public static final CraftingRule rules[] = new CraftingRule[]{HITANY, HITLAST, HITSECONDFROMLAST, HITTHIRDFROMLAST, HITLASTTWO, HITLASTTHREE, HITNOTLAST};
    
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
        if(Action == action)
            if(position >= Min && position <= Max)
                return true;
        
        return false;
    }
}
