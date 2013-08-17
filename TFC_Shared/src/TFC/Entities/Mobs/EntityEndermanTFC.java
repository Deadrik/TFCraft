package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.util.MathHelper;
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

	/**
	 * Moves Spawning Underground
	 */

	public boolean getCanSpawnHere()
    {
		int i = MathHelper.floor_double(this.posY);
        return super.getCanSpawnHere() && i < 144;
    }
	
	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111264_e).func_111128_a(TFC_MobData.EndermanDamage);
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(TFC_MobData.EndermanHealth);//MaxHealth
	}

	@Override
	public EnumDamageType GetDamageType() {
		// TODO Auto-generated method stub
		return EnumDamageType.GENERIC;
	}

}
