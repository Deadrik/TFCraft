package net.minecraft.src.TFC_Core.General;

public class HeatRaw
{
    public float specificHeat;
    public float meltTemp;
    public float boilTemp;
    
    public HeatRaw(float sh, float melt, float boil)
    {
        specificHeat = sh;
        meltTemp = melt;
        boilTemp = boil;
    }
    
    public HeatRaw(float sh, float melt)
    {
        specificHeat = sh;
        meltTemp = melt;
        boilTemp = 100000;
    }
}
