package TFC.Entities.AI;

import TFC.API.Entities.IAnimal;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityAIAttackTFC extends  EntityAINearestAttackableTarget{
	private IAnimal theAnimal;

    public EntityAIAttackTFC(IAnimal par1EntityTameable, Class par2Class, int par3, boolean par4)
    {
        super((EntityCreature)par1EntityTameable, par2Class, par3, par4);
        this.theAnimal = par1EntityTameable;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return  super.shouldExecute();
    }

}
