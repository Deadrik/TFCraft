package TFC.Entities.AI;

import java.util.Iterator;
import java.util.List;

import TFC.*;
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

public class EntityAIHurtByTargetTFC extends EntityAIHurtByTarget
{
    boolean field_48395_a;
    EntityLiving entity;

    public EntityAIHurtByTargetTFC(EntityLiving par1EntityLiving, boolean par2)
    {
        super(par1EntityLiving, par2);
        this.field_48395_a = par2;
        this.setMutexBits(1);
        entity = par1EntityLiving;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {    	
        return (!(entity.isChild()) && /*this.func_48376_a(this.taskOwner.getAITarget()*/entity.getAITarget() != null);
    }
    public void startExecuting()
    {       
        super.startExecuting();
    }
}
