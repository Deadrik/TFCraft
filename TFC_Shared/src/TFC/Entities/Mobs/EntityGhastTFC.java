package TFC.Entities.Mobs;

import TFC.*;
import net.minecraft.src.EntityGhast;
import net.minecraft.src.World;

public class EntityGhastTFC extends EntityGhast
{
    public EntityGhastTFC(World par1World)
    {
        super(par1World);

    }
    
    @Override
    public int getMaxHealth()
    {
        return 10;
    }
}
