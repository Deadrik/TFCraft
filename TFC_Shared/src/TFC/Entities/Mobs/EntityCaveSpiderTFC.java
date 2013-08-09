package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntitySpider;
import net.minecraft.world.World;
import TFC.Core.TFC_MobDamage;

public class EntityCaveSpiderTFC extends EntitySpider
{
	public EntityCaveSpiderTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(TFC_MobDamage.CaveSpiderDamage);
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(600);//MaxHealth
	}
}
