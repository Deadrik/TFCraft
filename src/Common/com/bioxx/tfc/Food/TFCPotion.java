package com.bioxx.tfc.Food;

import net.minecraft.potion.Potion;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class TFCPotion extends Potion
{
    public static Potion bleed;
    public static Potion nausea;
    
    /** The name of the Potion. */
    private String name = "";

    /** The index for the icon displayed when the potion effect is active. */
    private int statusIconIndex = -1;
	
	public TFCPotion(int par1, boolean par2, int par3) 
	{
		super(par1, par2, par3);
	}
	
	@Override
	public TFCPotion setIconIndex(int par1, int par2)
    {
		this.statusIconIndex = par1 + par2 * 8;
        return this;
    }
	
	@Override
	public TFCPotion setPotionName(String par1Str)
    {
        this.name = par1Str;
        return this;
    }
	
	@Override
	@SideOnly(Side.CLIENT)

    /**
     * Returns true if the potion has a associated status icon to display in then inventory when active.
     */
    public boolean hasStatusIcon()
    {
        return this.statusIconIndex >= 0;
    }
	
	@Override
	public String getName()
    {
        return this.name;
    }
	
	@SideOnly(Side.CLIENT)
	@Override
	public int getStatusIconIndex()
    {
        return this.statusIconIndex;
    }
	
	public static void setup()
	{
		bleed = new TFCPotion(20, true, 0xFF0000).setPotionName("effect.bleed").setIconIndex(4, 0).setEffectiveness(0.25D);
	}

}
