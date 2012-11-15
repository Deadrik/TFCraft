package TFC.Entities.AI;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Entities.EntityAnimalTFC;
import net.minecraft.src.Block;
import net.minecraft.src.EntityAIEatGrass;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.MathHelper;
import net.minecraft.src.World;

public class EntityAIEatGrassTFC extends EntityAIEatGrass
{
    private EntityLiving theEntity;
    private World theWorld;

    /** A decrementing tick used for the sheep's head offset and animation. */
    public int eatGrassTick = 0;

    public EntityAIEatGrassTFC(EntityLiving par1EntityLiving)
    {
        super(par1EntityLiving);
        this.theEntity = par1EntityLiving;
        this.theWorld = par1EntityLiving.worldObj;
        this.setMutexBits(7);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (((EntityAnimalTFC)theEntity).hunger > 160000 || theEntity.getRNG().nextInt(20)!=0)
        {
            return false;
        }
        else
        {
            int var1 = MathHelper.floor_double(this.theEntity.posX);
            int var2 = MathHelper.floor_double(this.theEntity.posY);
            int var3 = MathHelper.floor_double(this.theEntity.posZ);
            eatGrassTick = 5;
            return this.theWorld.getBlockId(var1, var2, var3) == Block.tallGrass.blockID && this.theWorld.getBlockMetadata(var1, var2, var3) == 1 ? true : TFC_Core.isGrass(theWorld.getBlockId(var1, var2-1, var3) );
        }
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.eatGrassTick = Math.max(0, this.eatGrassTick - 1);

        if (this.eatGrassTick == 4)
        {
            int var1 = MathHelper.floor_double(this.theEntity.posX);
            int var2 = MathHelper.floor_double(this.theEntity.posY);
            int var3 = MathHelper.floor_double(this.theEntity.posZ);

            if (this.theWorld.getBlockId(var1, var2, var3) == Block.tallGrass.blockID)
            {
                this.theWorld.playAuxSFX(2001, var1, var2, var3, Block.tallGrass.blockID + 4096);
                this.theWorld.setBlockWithNotify(var1, var2, var3, 0);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID)
            {
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, Block.grass.blockID);
                this.theWorld.setBlockWithNotify(var1, var2 - 1, var3, Block.dirt.blockID);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.Grass.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.Grass.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.Dirt.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.Grass2.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.Grass2.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.Dirt2.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.DryGrass.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.DryGrass.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.Dirt.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.DryGrass2.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.DryGrass2.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.Dirt2.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.ClayGrass.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.ClayGrass.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.Dirt.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.ClayGrass2.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.ClayGrass2.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.Dirt2.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.PeatGrass.blockID)
            {
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.PeatGrass.blockID);
                this.theWorld.setBlockWithNotify(var1, var2 - 1, var3, TFCBlocks.Peat.blockID);
                this.theEntity.eatGrassBonus();
            }
        }
    }
}
