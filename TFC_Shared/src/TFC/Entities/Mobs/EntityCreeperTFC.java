package TFC.Entities.Mobs;

import TFC.*;
import net.minecraft.src.EntityCreeper;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class EntityCreeperTFC extends EntityCreeper
{

    public EntityCreeperTFC(World par1World)
    {
        super(par1World);
    }

    public int getMaxHealth()
    {
        return 500;
    }
    
}
