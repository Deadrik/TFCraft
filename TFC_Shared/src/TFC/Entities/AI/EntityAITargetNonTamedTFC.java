package TFC.Entities.AI;

import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import TFC.Entities.EntityTameableTFC;

public class EntityAITargetNonTamedTFC extends EntityAINearestAttackableTarget
{
    private EntityTameableTFC field_48390_g;

    public EntityAITargetNonTamedTFC(EntityTameableTFC par1EntityTameable, Class par2Class, float par3, int par4, boolean par5)
    {
	super(par1EntityTameable, par2Class, par3, par4, par5);
	field_48390_g = par1EntityTameable;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
	public boolean shouldExecute()
    {
	if (field_48390_g.isTamed())
	{
	    return false;
	}
	else
	{
	    return super.shouldExecute();
	}
    }
}
