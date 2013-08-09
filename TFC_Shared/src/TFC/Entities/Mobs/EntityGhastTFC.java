package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityGhast;
import net.minecraft.world.World;

public class EntityGhastTFC extends EntityGhast
{
	public EntityGhastTFC(World par1World)
	{
		super(par1World);

	}

	@Override
	protected void func_110147_ax()
	{
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111267_a).func_111128_a(1000);//MaxHealth
	}
}
