package TFC.Entities.AI;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.API.Entities.IAnimal.GenderEnum;
import TFC.TileEntities.TENestBox;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBed;
import net.minecraft.entity.EntityCreature;
import TFC.Entities.Mobs.EntityChickenTFC;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityAIFindNest extends EntityAIBase
{
    private EntityCreature theCreature;
    private double shelterX;
    private double shelterY;
    private double shelterZ;
    private int currentTick;
    private double movementSpeed;
    private World theWorld;
    private int field_75402_d;
    private final double field_75404_b;
    private int maxSittingTicks;
    
    /** X Coordinate of a nearby sitable block */
    private int sitableBlockX = -1;

    /** Y Coordinate of a nearby sitable block */
    private int sitableBlockY =-1;

    /** Z Coordinate of a nearby sitable block */
    private int sitableBlockZ =-1;

    public EntityAIFindNest(EntityAnimal par1EntityAnimal, double par2)
    {
        this.theCreature = par1EntityAnimal;
        this.movementSpeed = par2;
        this.field_75404_b = par2;
        this.theWorld = par1EntityAnimal.worldObj;
        this.setMutexBits(5);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        
        if(this.theWorld.getBlockId((int)theCreature.posX, (int)theCreature.posY,(int)theCreature.posZ) != TFCBlocks.NestBox.blockID&&
        		this.theWorld.getBlockId((int)theCreature.posX, (int)theCreature.posY-1,(int)theCreature.posZ) != TFCBlocks.NestBox.blockID
        		 && this.getNearbySitableBlockDistance() &&
        		 ((EntityChickenTFC)theCreature).getGender()==GenderEnum.FEMALE){
        	return true;
        }else{
        	return false;
        }
        
    }

    public boolean continueExecuting()
    {
    	if(this.theCreature.getDistanceSq((double)sitableBlockX+0.5, (double)sitableBlockY, (double)sitableBlockZ+0.5)<0.2){
    		this.theCreature.getNavigator().clearPathEntity();
    	}
        return this.currentTick <= this.maxSittingTicks && this.field_75402_d <= 60 && this.isSittableBlock(this.theCreature.worldObj, this.sitableBlockX, this.sitableBlockY, this.sitableBlockZ);
    }
    
    protected boolean getNearbySitableBlockDistance()
    {
        int i = (int)this.theCreature.posY;
        double d0 = 2.147483647E9D;

        for (int j = (int)this.theCreature.posX - 16; (double)j < this.theCreature.posX + 16.0D; ++j)
        {
            for (int k = (int)this.theCreature.posZ - 16; (double)k < this.theCreature.posZ + 16.0D; ++k)
            {
                if (this.isSittableBlock(this.theCreature.worldObj, j, i, k) && this.theCreature.worldObj.isAirBlock(j, i + 1, k))
                {
                    double d1 = this.theCreature.getDistanceSq((double)j, (double)i, (double)k);

                    if (d1 < d0)
                    {
                        this.sitableBlockX = j;
                        this.sitableBlockY = i;
                        this.sitableBlockZ = k;
                        d0 = d1;
                    }
                }
            }
        }

        return d0 < 2.147483647E9D;
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.theCreature.getNavigator().tryMoveToXYZ((double)((float)this.sitableBlockX) + 0.5D, (double)(this.sitableBlockY + 1), (double)((float)this.sitableBlockZ) + 0.5D, this.field_75404_b);
        this.currentTick = 0;
        this.field_75402_d = 0;
        this.maxSittingTicks = this.theCreature.getRNG().nextInt(this.theCreature.getRNG().nextInt(1200) + 1200) + 1200;
    }
    
    public void updateTask()
    {
        ++this.currentTick;
        

        if (this.theCreature.getDistanceSq((double)this.sitableBlockX, (double)(this.sitableBlockY + 1), (double)this.sitableBlockZ) > 1.0D)
        {
            this.theCreature.getNavigator().tryMoveToXYZ((double)((float)this.sitableBlockX) + 0.5D, (double)(this.sitableBlockY + 1), (double)((float)this.sitableBlockZ) + 0.5D, this.field_75404_b);
            ++this.field_75402_d;
        }
        else
        {
            --this.field_75402_d;
        }
    }
    
    /**
     * Determines whether the creature wants to sit on the block at given coordinate
     */
    protected boolean isSittableBlock(World par1World, int par2, int par3, int par4)
    {
        int l = par1World.getBlockId(par2, par3, par4);
        int i1 = par1World.getBlockMetadata(par2, par3, par4);

        if (l == TFCBlocks.NestBox.blockID)
        {
            TENestBox tileentitynest = (TENestBox)par1World.getBlockTileEntity(par2, par3, par4);

            if (!tileentitynest.hasBird()||(this.theCreature.getDistanceSq((double)sitableBlockX+0.5, (double)sitableBlockY, (double)sitableBlockZ+0.5)<1))
            {
                return true;
            }
        }

        return false;
    }
}
