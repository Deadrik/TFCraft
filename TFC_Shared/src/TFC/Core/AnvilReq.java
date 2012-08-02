package TFC.Core;

public enum AnvilReq
{
    STONE("Stone", 0),
    COPPER("Copper", 1),
    BRONZE("Bronze", 2),
    WROUGHTIRON("Wrought Iron", 3),
    STEEL("Steel", 4),
    BLACKSTEEL("Black Steel", 5),
    REDSTEEL("Red Steel", 6),
    BLUESTEEL("Blue Steel", 6),
    BISMUTHBRONZE("Bismuth Bronze", 2),
    BLACKBRONZE("Black Bronze", 2),
    ROSEGOLD("Rose Gold", 2);
    
    public static final AnvilReq rules[] = new AnvilReq[]{STONE, COPPER, BRONZE, WROUGHTIRON, STEEL, BLACKSTEEL, REDSTEEL, BLUESTEEL, BISMUTHBRONZE, BLACKBRONZE, ROSEGOLD};
    
    public final int Tier;

    public final String Name;
    
    AnvilReq(String n, int tier)
    {
        Name = n;
        Tier = tier;
    }
    
    public boolean matches(int tier)
    {
        if(tier >= Tier)
           return true;
        
        return false;
    }
    public boolean matches(AnvilReq A)
    {
        if(A.Tier >= Tier)
           return true;
        
        return false;
    }
    public static boolean matches(int i, int j)
    {
        if(j >= i)
           return true;
        
        return false;
    }
}
