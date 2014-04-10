package TFC.Entities.Mobs;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.world.World;
import TFC.API.ICausesDamage;
import TFC.API.IInnateArmor;
import TFC.API.Enums.EnumDamageType;
import TFC.Core.TFC_MobData;

public class EntityEndermanTFC extends EntityEnderman implements ICausesDamage, IInnateArmor
{
	public static boolean[] carriableBlocks = new boolean[256];

	public EntityEndermanTFC(World par1World)
	{
		super(par1World);
	}

	@Override
	protected void applyEntityAttributes()
	{
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.attackDamage).setAttribute(TFC_MobData.EndermanDamage);
		this.getEntityAttribute(SharedMonsterAttributes.maxHealth).setAttribute(TFC_MobData.EndermanHealth);//MaxHealth
	}

	@Override
	public EnumDamageType GetDamageType() {
		// TODO Auto-generated method stub
		return EnumDamageType.GENERIC;
	}

	@Override
	public int GetCrushArmor() {
		return -335;
	}
	@Override
	public int GetSlashArmor() {
		return -335;
	}
	@Override
	public int GetPierceArmor() {
		return -335;
	}

}
