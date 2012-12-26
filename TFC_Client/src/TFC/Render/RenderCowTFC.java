package TFC.Render;

import org.lwjgl.opengl.GL11;

import TFC.Entities.Mobs.EntityCowTFC;
import TFC.Entities.Mobs.EntityPigTFC;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
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

public class RenderCowTFC extends RenderLivingTFC
{
    public RenderCowTFC(ModelBase par1ModelBase, float par2)
    {
        super((ModelBaseTFC)par1ModelBase, par2);
    }

    public void renderCow(EntityCowTFC par1EntityCow, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntityCow, par2, par4, par6, par8, par9);
    }
    
    protected void preRenderCallback (EntityLiving par1EntityLiving, float par2)
    {
	preRenderScale ((EntityCowTFC) par1EntityLiving, par2);
    }

    protected void preRenderScale (EntityCowTFC par1EntityCow, float par2)
    {
	GL11.glScalef (par1EntityCow.size_mod,par1EntityCow.size_mod,par1EntityCow.size_mod);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderCow((EntityCowTFC)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderCow((EntityCowTFC)par1Entity, par2, par4, par6, par8, par9);
    }
}
