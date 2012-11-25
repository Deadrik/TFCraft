package TFC.Entities.Mobs;

import TFC.*;
import TFC.Core.TFC_MobDamage;
import net.minecraft.src.Entity;
import net.minecraft.src.EntitySpider;
import net.minecraft.src.World;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class EntitySpiderTFC extends EntitySpider
{
    public EntitySpiderTFC(World par1World)
    {
        super(par1World);
        this.texture = "/mob/spider.png";
        this.setSize(1.4F, 0.9F);
        this.moveSpeed = 0.8F;
    }

    @Override
    public int getMaxHealth()
    {
        return 800;
    }
    
    /**
     * Returns the amount of damage a mob should deal.
     */
    public int getAttackStrength(Entity par1Entity)
    {
        return TFC_MobDamage.SpiderDamage;
    }

}
