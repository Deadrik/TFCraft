package TFC.Entities.AI;

import java.util.List;

import TFC.API.Entities.IAnimal;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.RandomPositionGenerator;
import net.minecraft.util.Vec3;

public class EntityAIPanicTFC extends EntityAIBase
{
    private EntityCreature theEntityCreature;
    private boolean alertHerd;
    private double speed;
    private double randPosX;
    private double randPosY;
    private double randPosZ;

    public EntityAIPanicTFC(EntityCreature par1EntityCreature, double par2, boolean par3)
    {
        this.theEntityCreature = par1EntityCreature;
        this.speed = par2;
        this.alertHerd = par3;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (this.theEntityCreature.getAITarget() == null && !this.theEntityCreature.isBurning() && 
        		((this.theEntityCreature instanceof IAnimal && ((IAnimal)this.theEntityCreature).getAttackedVec() == null)||
        				!(this.theEntityCreature instanceof IAnimal)))
        {
            return false;
        }
        else
        {
        	Vec3 attackedVec = this.theEntityCreature instanceof IAnimal?((IAnimal)this.theEntityCreature).getAttackedVec() : null;
        	System.out.println(attackedVec != null);
            Vec3 vec3 = RandomPositionGenerator.findRandomTarget(this.theEntityCreature, 5, 4);
            if(attackedVec != null){
            	if(this.theEntityCreature instanceof IAnimal){
            		attackedVec = updateAttackVec((IAnimal)this.theEntityCreature,attackedVec);
            	}
            	vec3 = RandomPositionGenerator.findRandomTargetBlockAwayFrom(this.theEntityCreature, 5, 4, attackedVec);
            }
            if (vec3 == null)
            {
                return false;
            }
            else
            {
                this.randPosX = vec3.xCoord;
                this.randPosY = vec3.yCoord;
                this.randPosZ = vec3.zCoord;
                if(alertHerd && this.theEntityCreature instanceof IAnimal){
                	List list = this.theEntityCreature.worldObj.getEntitiesWithinAABB(this.theEntityCreature.getClass(), this.theEntityCreature.boundingBox.expand(8, 8, 8));
                	for(Object entity : list){
                		System.out.println(entity);
                		((IAnimal)entity).setAttackedVec(attackedVec);
                	}
                }
                return true;
            }
        }
    }
    
    public Vec3 updateAttackVec(IAnimal theCreature, Vec3 attackedVec){
    	if(theCreature.getFearSource() != null && (this.theEntityCreature.getPosition(1).distanceTo(attackedVec)>this.theEntityCreature.getDistanceToEntity(theCreature.getFearSource()))){
    		Vec3 newVec = Vec3.fakePool.getVecFromPool(theCreature.getFearSource().posX, theCreature.getFearSource().posY, theCreature.getFearSource().posZ);
    		theCreature.setAttackedVec(newVec);
    		return newVec;
    	}
    	return attackedVec;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.theEntityCreature.getNavigator().tryMoveToXYZ(this.randPosX, this.randPosY, this.randPosZ, this.speed);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.theEntityCreature.getNavigator().noPath();
    }
}
