package TFC.Entities.Mobs;

import net.minecraft.entity.passive.EntitySquid;
import net.minecraft.world.World;

public class EntitySquidTFC extends EntitySquid
{
    public EntitySquidTFC(World par1World)
    {
        super(par1World);
        this.setSize(0.75F, 0.75F);
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    @Override
    public boolean getCanSpawnHere()
    {
        return this.posY > 128.0D && this.posY < 145.0D && this.worldObj.checkNoEntityCollision(this.boundingBox);
    }
    
    @Override
    public int getMaxHealth()
    {
        return 400;
    }
}
