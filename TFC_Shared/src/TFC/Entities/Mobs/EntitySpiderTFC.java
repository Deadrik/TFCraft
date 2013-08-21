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
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(TFC_MobData.SpiderDamage);
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(TFC_MobData.SpiderHealth);//MaxHealth
	}

	@Override
	public EnumDamageType GetDamageType() {
		return EnumDamageType.PIERCING;
	}

}
