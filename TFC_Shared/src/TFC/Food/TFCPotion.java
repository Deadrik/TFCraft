package TFC.Food;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;

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
	
	public static void Setup()
	{
		bleed = (new TFCPotion(20, true, 0xFF0000)).setPotionName("effect.bleed").setIconIndex(4, 0).setEffectiveness(0.25D);
	}

}
