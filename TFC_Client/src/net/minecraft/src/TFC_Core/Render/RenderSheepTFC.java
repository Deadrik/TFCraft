package net.minecraft.src.TFC_Core.Render;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.Entity;
import net.minecraft.src.EntityCow;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntitySheep;
import net.minecraft.src.ModelBase;
import net.minecraft.src.RenderLiving;
import net.minecraft.src.TFC_Core.Custom.EntityCowTFC;
import net.minecraft.src.TFC_Core.Custom.EntitySheepTFC;

public class RenderSheepTFC extends RenderLivingTFC
{
    public RenderSheepTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
    {
        super((ModelBaseTFC)par1ModelBase, par3);
        this.setRenderPassModel((ModelBaseTFC) par2ModelBase);
    }

    protected int setWoolColorAndRender(EntitySheepTFC par1EntitySheep, int par2, float par3)
    {
        if (par2 == 0 && !par1EntitySheep.getSheared())
        {
            this.loadTexture("/mob/sheep_fur.png");
            float var4 = 1.0F;
            int var5 = par1EntitySheep.getFleeceColor();
            GL11.glColor3f(var4 * EntitySheepTFC.fleeceColorTable[var5][0], var4 * EntitySheepTFC.fleeceColorTable[var5][1], var4 * EntitySheepTFC.fleeceColorTable[var5][2]);
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public void doRenderSheep(EntitySheepTFC par1EntitySheep, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntitySheep, par2, par4, par6, par8, par9);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.setWoolColorAndRender((EntitySheepTFC)par1EntityLiving, par2, par3);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderSheep((EntitySheepTFC)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderSheep((EntitySheepTFC)par1Entity, par2, par4, par6, par8, par9);
    }
}
