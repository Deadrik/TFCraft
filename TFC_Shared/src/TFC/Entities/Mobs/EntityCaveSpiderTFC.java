package TFC.Entities.Mobs;

import TFC.*;
import net.minecraft.src.Entity;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class EntityCaveSpiderTFC extends EntitySpider
{
    public EntityCaveSpiderTFC(World par1World)
    {
        super(par1World);

    }
    @Override
    public int getAttackStrength(Entity par1Entity)
    {
        return 100;
    }
    @Override
    public int getMaxHealth()
    {
        return 600;
    }

    @Override
    public void initCreature() {}
}
