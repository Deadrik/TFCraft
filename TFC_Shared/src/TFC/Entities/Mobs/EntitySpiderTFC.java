package TFC.Entities.Mobs;

import net.minecraft.entity.Entity;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.world.World;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFC_MobDamage;

public class EntitySpiderTFC extends EntitySpider implements ICausesDamage
{
    public EntitySpiderTFC(World par1World)
    {
        super(par1World);
        this.texture = "/mob/spider.png";
        this.setSize(0.5F, 0.3F);
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
    @Override
	public int getAttackStrength(Entity par1Entity)
    {
        return TFC_MobDamage.SpiderDamage;
    }
    
    @Override
	public EnumDamageType GetDamageType() {
		return EnumDamageType.PIERCING;
	}

}
