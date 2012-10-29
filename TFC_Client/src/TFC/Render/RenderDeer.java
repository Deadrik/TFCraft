package TFC.Render;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.ModelBase;

import org.lwjgl.opengl.GL11;

import TFC.Entities.Mobs.EntityDeer;
public class RenderDeer extends RenderLivingTFC
{
    private float scale = 1.0f;
    private ModelDeer modeldeer;

    public RenderDeer (ModelBaseTFC par1ModelBase, float par2)
    {
	super (par1ModelBase, par2);
	modeldeer = (ModelDeer) par1ModelBase;
    }


    public void renderDeer (EntityDeer par1EntityDeer, double par2, double par4, double par6, float par8, float par9)
    {
	super.doRenderLiving (par1EntityDeer, par2, par4, par6, par8, par9);
	scale = 0.5F + par1EntityDeer.size_mod;
    }


    protected void func_25006_b (EntityDeer entitydeer, float f)
    {
    }


    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback (EntityLiving par1EntityLiving, float par2)
    {
	preRenderScale ((EntityDeer) par1EntityLiving, par2);
	func_25006_b ((EntityDeer) par1EntityLiving, par2);
    }

    protected void preRenderScale (EntityDeer par1EntityDeer, float par2)
    {
	GL11.glScalef (par1EntityDeer.size_mod,par1EntityDeer.size_mod,par1EntityDeer.size_mod);
    }


    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat (EntityLiving par1EntityLiving, float par2)
    {
	return 1.0f;
    }


    public void doRenderLiving (EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
	renderDeer ((EntityDeer) par1EntityLiving, par2, par4, par6, par8, par9);
    }


    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender (Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
	renderDeer ((EntityDeer) par1Entity, par2, par4, par6, par8, par9);
    }
}
