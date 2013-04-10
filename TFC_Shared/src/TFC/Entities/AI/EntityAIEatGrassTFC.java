package TFC.Entities.AI;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.ai.EntityAIEatGrass;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Core.TFC_Core;
import TFC.Entities.EntityAnimalTFC;

public class EntityAIEatGrassTFC extends EntityAIEatGrass
{
    private final EntityLiving theEntity;
    private final World theWorld;

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
    @Override
	public boolean shouldExecute()
    {
        if (((EntityAnimalTFC)theEntity).hunger > 160000 || theEntity.getRNG().nextInt(1000)!=0)
        {
            return false;
        }
        else
        {
            int var1 = MathHelper.floor_double(this.theEntity.posX);
            int var2 = MathHelper.floor_double(this.theEntity.posY);
            int var3 = MathHelper.floor_double(this.theEntity.posZ);
            eatGrassTick = 40;
            return this.theWorld.getBlockId(var1, var2, var3) == Block.tallGrass.blockID && this.theWorld.getBlockMetadata(var1, var2, var3) == 1 ? true : TFC_Core.isGrass(theWorld.getBlockId(var1, var2-1, var3) );
        }
    }

    /**
     * Updates the task
     */
    @Override
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
                this.theWorld.setBlock(var1, var2, var3, 0);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID)
            {
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, Block.grass.blockID);
                this.theWorld.setBlock(var1, var2 - 1, var3, Block.dirt.blockID);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.Grass.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.Grass.blockID);
                this.theWorld.setBlock(var1, var2 - 1, var3, TFCBlocks.Dirt.blockID, meta,2);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.Grass2.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.Grass2.blockID);
                this.theWorld.setBlock(var1, var2 - 1, var3, TFCBlocks.Dirt2.blockID, meta,2);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.DryGrass.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.DryGrass.blockID);
                this.theWorld.setBlock(var1, var2 - 1, var3, TFCBlocks.Dirt.blockID, meta,2);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.DryGrass2.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.DryGrass2.blockID);
                this.theWorld.setBlock(var1, var2 - 1, var3, TFCBlocks.Dirt2.blockID, meta,2);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.ClayGrass.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.ClayGrass.blockID);
                this.theWorld.setBlock(var1, var2 - 1, var3, TFCBlocks.Dirt.blockID, meta,2);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.ClayGrass2.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.ClayGrass2.blockID);
                this.theWorld.setBlock(var1, var2 - 1, var3, TFCBlocks.Dirt2.blockID, meta,2);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.PeatGrass.blockID)
            {
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.PeatGrass.blockID);
                this.theWorld.setBlock(var1, var2 - 1, var3, TFCBlocks.Peat.blockID);
                this.theEntity.eatGrassBonus();
            }
        }
    }
}
