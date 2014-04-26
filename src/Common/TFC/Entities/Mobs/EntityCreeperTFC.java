package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.world.World;
import TFC.API.IInnateArmor;

public class EntityCreeperTFC extends EntityCreeper implements IInnateArmor
{
	public EntityCreeperTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setBaseValue(500);//MaxHealth
	}

	@Override
	public int GetCrushArmor() {
		return 1000;
	}
	@Override
	public int GetSlashArmor() {
		return 0;
	}
	@Override
	public int GetPierceArmor() {
		return -335;
	}

}
