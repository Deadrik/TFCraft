package TFC.Entities.AI;

import TFC.*;
import TFC.Entities.EntityTameableTFC;
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

public class EntityAIOwnerHurtTargetTFC extends EntityAITarget
{
    EntityTameableTFC field_48392_a;
    EntityLiving field_48391_b;

    public EntityAIOwnerHurtTargetTFC(EntityTameableTFC par1EntityTameable)
    {
        super(par1EntityTameable, 32.0F, false);
        this.field_48392_a = par1EntityTameable;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        if (!this.field_48392_a.isTamed())
        {
            return false;
        }
        else
        {
            EntityLiving var1 = this.field_48392_a.getOwner();

            if (var1 == null)
            {
                return false;
            }
            else
            {
                this.field_48391_b = var1.getLastAttackingEntity();
                return this.isSuitableTarget(field_48391_b, false);
            }
        }
    }

    /**
     * Execute a one shot task or start executing a continuous task
     */
    public void startExecuting()
    {
        this.taskOwner.setAttackTarget(this.field_48391_b);
        super.startExecuting();
    }
}
