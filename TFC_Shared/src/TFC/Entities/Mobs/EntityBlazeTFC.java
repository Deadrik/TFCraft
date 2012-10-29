package TFC.Entities.Mobs;

import TFC.*;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityBlaze;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class EntityBlazeTFC extends EntityBlaze
{


    public EntityBlazeTFC(World par1World)
    {
        super(par1World);
    }

    @Override
    public int getMaxHealth()
    {
        return 1000;
    }

    @Override
    public int func_82193_c(Entity par1Entity)
    {
        return 60;
    }
}
