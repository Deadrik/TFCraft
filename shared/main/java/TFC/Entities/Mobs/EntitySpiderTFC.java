package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.world.World;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFC_MobData;

public class EntitySpiderTFC extends EntitySpider implements ICausesDamage
{
	public EntitySpiderTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(TFC_MobData.SpiderDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(TFC_MobData.SpiderHealth);//MaxHealth
	}

	@Override
	public EnumDamageType GetDamageType() {
		return EnumDamageType.PIERCING;
	}

}
