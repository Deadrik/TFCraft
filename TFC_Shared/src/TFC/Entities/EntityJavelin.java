package TFC.Entities;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.world.World;
import TFC.API.ICausesDamage;
import TFC.API.Enums.EnumDamageType;

public class EntityJavelin extends EntityProjectileTFC implements ICausesDamage
{
	public EntityJavelin(World par1World)
	{
		super(par1World);
	}

	public EntityJavelin(World par1World, double i, double j, double k)
	{
		super(par1World, i , j, k);
	}

	public EntityJavelin(World world, EntityLivingBase shooter, EntityLivingBase victim, float force, float forceVariation, int itemID)
	{
		super(world, shooter, victim, force, forceVariation, itemID);
	}

	public EntityJavelin(World par1World, EntityLivingBase shooter, float force, int itemID)
	{
		super(par1World, shooter, force, itemID);
	}

	@Override
	public EnumDamageType GetDamageType() {
		// TODO Auto-generated method stub
		return EnumDamageType.PIERCING;
	}
}