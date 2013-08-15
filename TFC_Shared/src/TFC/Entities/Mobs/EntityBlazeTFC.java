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
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(TFC_MobData.BlazeDamage);
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(TFC_MobData.BlazeHealth);//MaxHealth
	}
}
