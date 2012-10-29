package TFC.Render;

import org.lwjgl.opengl.GL11;

import TFC.*;
import TFC.Entities.Mobs.EntityBear;
import TFC.Entities.Mobs.EntityPigTFC;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;

public class RenderPigTFC extends RenderLivingTFC
{
    public RenderPigTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
    {
        super((ModelBaseTFC) par1ModelBase, par3);
        this.setRenderPassModel((ModelBaseTFC) par2ModelBase);
    }

    protected int renderSaddledPig(EntityPigTFC par1EntityPig, int par2, float par3)
    {
        this.loadTexture("/mob/saddle.png");
        return par2 == 0 && par1EntityPig.getSaddled() ? 1 : -1;
    }

    public void func_40286_a(EntityPigTFC par1EntityPig, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntityPig, par2, par4, par6, par8, par9);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.renderSaddledPig((EntityPigTFC)par1EntityLiving, par2, par3);
    }
    
    protected void preRenderCallback (EntityLiving par1EntityLiving, float par2)
    {
	preRenderScale ((EntityPigTFC) par1EntityLiving, par2);
    }

    protected void preRenderScale (EntityPigTFC par1EntityPig, float par2)
    {
	GL11.glScalef (par1EntityPig.size_mod,par1EntityPig.size_mod,par1EntityPig.size_mod);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.func_40286_a((EntityPigTFC)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.func_40286_a((EntityPigTFC)par1Entity, par2, par4, par6, par8, par9);
    }
}
