package TFC.Entities;

import net.minecraft.src.*;

public class EntityAIEatGrassTFC extends EntityAIEatGrass
{
    private EntityLiving theEntity;
    private World theWorld;

    /** A decrementing tick used for the sheep's head offset and animation. */
    int eatGrassTick = 0;

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
            return this.theWorld.getBlockId(var1, var2, var3) == Block.tallGrass.blockID && this.theWorld.getBlockMetadata(var1, var2, var3) == 1 ? true : (this.theWorld.getBlockId(var1, var2 - 1, var3) == Block.grass.blockID ||
                    this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraGrass.blockID || this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraGrass2.blockID || 
                    this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraPeatGrass.blockID ||
                    this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraClayGrass.blockID || this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraClayGrass2.blockID);
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
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraGrass.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.terraGrass.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.terraDirt.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraGrass2.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.terraGrass2.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.terraDirt2.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraClayGrass.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.terraClayGrass.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.terraDirt.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraClayGrass2.blockID)
            {
                int meta = this.theWorld.getBlockMetadata(var1, var2 - 1, var3);
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.terraClayGrass2.blockID);
                this.theWorld.setBlockAndMetadataWithNotify(var1, var2 - 1, var3, TFCBlocks.terraDirt2.blockID, meta);
                this.theEntity.eatGrassBonus();
            }
            else if (this.theWorld.getBlockId(var1, var2 - 1, var3) == TFCBlocks.terraPeatGrass.blockID)
            {
                this.theWorld.playAuxSFX(2001, var1, var2 - 1, var3, TFCBlocks.terraPeatGrass.blockID);
                this.theWorld.setBlockWithNotify(var1, var2 - 1, var3, TFCBlocks.terraPeat.blockID);
                this.theEntity.eatGrassBonus();
            }
        }
    }
}
