package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityBlaze;
import net.minecraft.world.World;
import TFC.Core.TFC_MobData;

public class EntityBlazeTFC extends EntityBlaze
{
	public EntityBlazeTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(TFC_MobData.BlazeDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(TFC_MobData.BlazeHealth);//MaxHealth
	}
}
