package TFC.Entities;

import TFC.Blocks.BlockCollapsable;
import TFC.Blocks.BlockTerraIgInCobble;
import net.minecraft.src.*;

public class EntityFallingStone extends Entity
{
    public int blockID;
    public int metaID;
    public int fallTime;
    public int pTime;

    public EntityFallingStone(World world)
    {
        super(world);
        fallTime = 0;
        metaID = 0;
    }

    public EntityFallingStone(World world, double d, double d1, double d2,
            int i, int meta, int processTime)
    {
        super(world);
        fallTime = 0;
        blockID = i;
        metaID = meta;
        preventEntitySpawning = true;
        setSize(0.98F, 0.98F);
        yOffset = height / 2.0F;
        setPosition(d, d1, d2);
        motionX = 0.0D;
        motionY = 0.0D;
        motionZ = 0.0D;
        prevPosX = d;
        prevPosY = d1;
        prevPosZ = d2;
        pTime = processTime;
        this.dataWatcher.addObject(18, Integer.valueOf(blockID));
    }

    public boolean canBeCollidedWith()
    {
        return !isDead;
    }

    protected boolean canTriggerWalking()
    {
        return false;
    }

    protected void entityInit()
    {
    }

    public float getShadowSize()
    {
        return 0.0F;
    }

    public World getWorld()
    {
        return worldObj;
    }

    public void onUpdate()
    {
        if(pTime == 0)
        {
            if (blockID == 0)
            {
                setDead();
                return;
            }
            prevPosX = posX;
            prevPosY = posY;
            prevPosZ = posZ;
            fallTime++;
            motionY -= 0.039999999105930328D;
            moveEntity(motionX, motionY, motionZ);
            motionX *= 0.98000001907348633D;
            motionY *= 0.98000001907348633D;
            motionZ *= 0.98000001907348633D;
            int i = MathHelper.floor_double(posX);
            int j = MathHelper.floor_double(posY);
            int k = MathHelper.floor_double(posZ);
            if (fallTime == 1 /*&& worldObj.getBlockId(i, j, k) == blockID*/)
            {
                worldObj.setBlockWithNotify(i, j, k, 0);
            }
            else if (!worldObj.isRemote && fallTime == 1)
            {
                //setDead();
            }
            if (onGround || j == 1)
            {
                motionX *= 0.69999998807907104D;
                motionZ *= 0.69999998807907104D;
                motionY *= -0.5D;
                if (worldObj.getBlockId(i, j, k) != Block.pistonMoving.blockID)
                {
                    setDead();
                    if ((!worldObj.canPlaceEntityOnSide(blockID, i, j, k, true, 1, this) || BlockCollapsable.canFallBelow(worldObj, i, j - 1, k) || !worldObj.setBlockAndMetadataWithNotify(i, j, k, blockID, metaID)) && !worldObj.isRemote)
                    {
                        ItemStack itemstack = new ItemStack(blockID,1,metaID);
                        EntityItem entityitem = new EntityItem(worldObj, posX, posY+0.5, posZ, itemstack);
                        entityitem.delayBeforeCanPickup = 10;
                        worldObj.spawnEntityInWorld(entityitem);
                    }
                }
            }
            else if (fallTime > 100 && !worldObj.isRemote)
            {
                ItemStack itemstack = new ItemStack(blockID,1,metaID);
                EntityItem entityitem = new EntityItem(worldObj, posX, posY+0.5, posZ, itemstack);
                entityitem.delayBeforeCanPickup = 10;
                worldObj.spawnEntityInWorld(entityitem);
                setDead();
            }
        }
        if(pTime > 0) {
            pTime--;
        }
    }

    protected void readEntityFromNBT(NBTTagCompound nbttagcompound)
    {
        blockID = nbttagcompound.getByte("Tile") & 0xff;
    }

    protected void writeEntityToNBT(NBTTagCompound nbttagcompound)
    {
        nbttagcompound.setByte("Tile", (byte)blockID);
    }

    public void onCollideWithPlayer(EntityPlayer par1EntityPlayer) 
    {
        double var2 = par1EntityPlayer.posX - this.posX;
        double var4 = par1EntityPlayer.posY - this.posY;
        double var6 = par1EntityPlayer.posZ - this.posZ;
        double d =  (double)MathHelper.sqrt_double(var2 * var2 + var4 * var4 + var6 * var6);
        if(d < 1)
        {
            if(par1EntityPlayer.inventory.armorInventory[3] != null)
                par1EntityPlayer.attackEntityFrom(DamageSource.cactus, 2);
            else
                par1EntityPlayer.attackEntityFrom(DamageSource.cactus, 4);
        }
    }
}
