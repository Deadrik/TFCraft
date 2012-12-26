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

public class EntityAITargetNonTamedTFC extends EntityAINearestAttackableTarget
{
    private EntityTameableTFC field_48390_g;

    public EntityAITargetNonTamedTFC(EntityTameableTFC par1EntityTameable, Class par2Class, float par3, int par4, boolean par5)
    {
	super(par1EntityTameable, par2Class, par3, par4, par5);
	field_48390_g = par1EntityTameable;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
	if (field_48390_g.isTamed()||field_48390_g.hunger > 20000)
	{
	    return false;
	}
	else
	{
	    return super.shouldExecute();
	}
    }
}
