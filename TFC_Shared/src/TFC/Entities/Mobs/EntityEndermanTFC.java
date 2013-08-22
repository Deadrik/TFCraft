package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.World;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFC_MobData;

public class EntityEndermanTFC extends EntityEnderman implements ICausesDamage
{
	public static boolean[] carriableBlocks = new boolean[256];


	public EntityEndermanTFC(World par1World)
	{
		super(par1World);

	}

	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(TFC_MobData.EndermanDamage); //Damage
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(TFC_MobData.EndermanHealth); //MaxHealth
	}

	@Override
	public EnumDamageType GetDamageType() 
	{
		return EnumDamageType.GENERIC;
	}

}
