package TFC.Render;

import TFC.Entities.EntityChickenTFC;
import net.minecraft.src.*;

public class RenderChickenTFC extends RenderLivingTFC
{
    public RenderChickenTFC(ModelBase par1ModelBase, float par2)
    {
        super((ModelBaseTFC) par1ModelBase, par2);
    }

    public void renderChicken(EntityChickenTFC par1EntityChicken, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntityChicken, par2, par4, par6, par8, par9);
    }

    protected float getWingRotation(EntityChickenTFC par1EntityChicken, float par2)
    {
        float var3 = par1EntityChicken.field_756_e + (par1EntityChicken.field_752_b - par1EntityChicken.field_756_e) * par2;
        float var4 = par1EntityChicken.field_757_d + (par1EntityChicken.destPos - par1EntityChicken.field_757_d) * par2;
        return (MathHelper.sin(var3) + 1.0F) * var4;
    }

    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat(EntityLiving par1EntityLiving, float par2)
    {
        return this.getWingRotation((EntityChickenTFC)par1EntityLiving, par2);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderChicken((EntityChickenTFC)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderChicken((EntityChickenTFC)par1Entity, par2, par4, par6, par8, par9);
    }
}
