package TFC.Entities.AI;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.API.Entities.IAnimal.GenderEnum;
import TFC.TileEntities.TileEntityNestBox;
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
    private int sitableBlockX;

    /** Y Coordinate of a nearby sitable block */
    private int sitableBlockY;

    /** Z Coordinate of a nearby sitable block */
    private int sitableBlockZ;

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
        }else{
        	return false;
        }
        
            Vec3 vec3 = this.findPossibleShelter();

            if (vec3 == null)
            {
                return false;
            }
            else
            {
                this.shelterX = vec3.xCoord;
                this.shelterY = vec3.yCoord;
                this.shelterZ = vec3.zCoord;
                return true;
            }
        
    }

    public boolean continueExecuting()
    {
        return this.currentTick <= this.maxSittingTicks && this.field_75402_d <= 60 && this.isSittableBlock(this.theCreature.worldObj, this.sitableBlockX, this.sitableBlockY, this.sitableBlockZ);
    }
    
    protected boolean getNearbySitableBlockDistance()
    {
        int i = (int)this.theCreature.posY;
        double d0 = 2.147483647E9D;

        for (int j = (int)this.theCreature.posX - 8; (double)j < this.theCreature.posX + 8.0D; ++j)
        {
            for (int k = (int)this.theCreature.posZ - 8; (double)k < this.theCreature.posZ + 8.0D; ++k)
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

    private Vec3 findPossibleShelter()
    {
        Random random = this.theCreature.getRNG();

        for (int i = 0; i < 100; ++i)
        {
            int j = MathHelper.floor_double(this.theCreature.posX + (double)random.nextInt(20) - 10.0D);
            int k = MathHelper.floor_double(this.theCreature.boundingBox.minY + (double)random.nextInt(6) - 3.0D);
            int l = MathHelper.floor_double(this.theCreature.posZ + (double)random.nextInt(20) - 10.0D);

            if (this.theWorld.getBlockId(j, k, l) == TFCBlocks.NestBox.blockID && this.theCreature.getBlockPathWeight(j, k, l) < 0.0F)
            {
                return this.theWorld.getWorldVec3Pool().getVecFromPool((double)j, (double)k, (double)l);
            }
        }

        return null;
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
            TileEntityNestBox tileentitynest = (TileEntityNestBox)par1World.getBlockTileEntity(par2, par3, par4);

            if (!tileentitynest.hasBird())
            {
                return true;
            }
        }

        return false;
    }
}
