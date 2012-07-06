package TFC.Entities;

import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EntityWaterMob;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.MathHelper;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.*;

public class EntitySquidTFC extends EntitySquid
{
    public EntitySquidTFC(World par1World)
    {
        super(par1World);
    }

    /**
     * Checks if the entity's current position is a valid location to spawn this entity.
     */
    public boolean getCanSpawnHere()
    {
        return this.posY > 128.0D && this.posY < 145.0D && this.worldObj.checkIfAABBIsClear(this.boundingBox);
    }
}
