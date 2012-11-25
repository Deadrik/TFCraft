package TFC.Entities.Mobs;

import TFC.*;
import TFC.Core.TFC_MobDamage;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityEnderman;
import net.minecraft.src.World;

public class EntityEndermanTFC extends EntityEnderman
{
    public static boolean[] carriableBlocks = new boolean[256];


    public EntityEndermanTFC(World par1World)
    {
        super(par1World);

    }
    
    @Override
    public int getMaxHealth()
    {
        return 2000;
    }

    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        return TFC_MobDamage.EndermanDamage;
    }

}
