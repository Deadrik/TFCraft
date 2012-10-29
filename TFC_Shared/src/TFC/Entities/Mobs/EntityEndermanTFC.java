package TFC.Entities.Mobs;

import TFC.*;
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

    public int getMaxHealth()
    {
        return 2000;
    }


    public int func_82193_c(Entity par1Entity)
    {
        return 350;
    }

}
