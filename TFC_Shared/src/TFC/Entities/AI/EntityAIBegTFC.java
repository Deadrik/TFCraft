package TFC.Entities.AI;

import TFC.*;
import TFC.Entities.Mobs.EntityWolfTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class EntityAIBegTFC extends EntityAIBase
{
    private EntityWolfTFC theWolf;
    private EntityPlayer field_48348_b;
    private World field_48349_c;
    private float field_48346_d;
    private int field_48347_e;

    public EntityAIBegTFC(EntityWolfTFC par1EntityWolf, float par2)
    {
        this.theWolf = par1EntityWolf;
        this.field_48349_c = par1EntityWolf.worldObj;
        this.field_48346_d = par2;
        this.setMutexBits(2);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        this.field_48348_b = this.field_48349_c.getClosestPlayerToEntity(this.theWolf, (double)this.field_48346_d);
        return this.field_48348_b == null ? false : this.func_48345_a(this.field_48348_b);
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean continueExecuting()
    {
        return !this.field_48348_b.isEntityAlive() ? false : (this.theWolf.getDistanceSqToEntity(this.field_48348_b) > (double)(this.field_48346_d * this.field_48346_d) ? false : this.field_48347_e > 0 && this.func_48345_a(this.field_48348_b));
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
    	this.theWolf.setLooksWithInterest(true);
        this.field_48347_e = 40 + this.theWolf.getRNG().nextInt(40);
    }

    /**
     * Resets the task
     */
    public void resetTask()
    {
        this.theWolf.setLooksWithInterest(false);
        this.field_48348_b = null;
    }

    /**
     * Updates the task
     */
    public void updateTask()
    {
        this.theWolf.getLookHelper().setLookPosition(this.field_48348_b.posX, this.field_48348_b.posY + (double)this.field_48348_b.getEyeHeight(), this.field_48348_b.posZ, 10.0F, (float)this.theWolf.getVerticalFaceSpeed());
        --this.field_48347_e;
    }

  
    private boolean func_48345_a(EntityPlayer par1EntityPlayer)
    {
        ItemStack var2 = par1EntityPlayer.inventory.getCurrentItem();
        return var2 == null ? false : (!this.theWolf.isTamed() && var2.itemID == Item.bone.itemID ? true : this.theWolf.isBreedingItem(var2));
    }
}
