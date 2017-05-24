package com.bioxx.tfc.Render;


import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;

import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Entities.Mobs.EntityOcelotTFC;
import com.bioxx.tfc.Reference;


@SideOnly(Side.CLIENT)
public class RenderOcelotTFC extends RenderLiving
{
	private static final ResourceLocation ocelotTextures = new ResourceLocation(Reference.MOD_ID , "textures/mob/cat/ocelot.png");
	private static final ResourceLocation blackOcelotTextures = new ResourceLocation(Reference.MOD_ID, "textures/mob/cat/black.png");
    private static final ResourceLocation redOcelotTextures = new ResourceLocation(Reference.MOD_ID, "textures/mob/cat/red.png");
    private static final ResourceLocation siameseOcelotTextures = new ResourceLocation(Reference.MOD_ID , "textures/mob/cat/siamese.png");
    private static final ResourceLocation herosiamOcelotTextures = new ResourceLocation(Reference.MOD_ID , "textures/mob/cat/hero_siam.png");
    private static final ResourceLocation red_cat_tigerOcelotTextures = new ResourceLocation(Reference.MOD_ID , "textures/mob/cat/red_cat_tiger.png");
    private static final ResourceLocation cute_catOcelotTextures = new ResourceLocation(Reference.MOD_ID , "textures/mob/cat/cute_cat.png");
    private static final ResourceLocation calicoOcelotTextures = new ResourceLocation(Reference.MOD_ID , "textures/mob/cat/calico.png");
    
    
    


    public RenderOcelotTFC(ModelBase p_i1264_1_, float p_i1264_2_)
    {
        super(p_i1264_1_, p_i1264_2_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityOcelotTFC p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        super.doRender((EntityLiving)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(EntityOcelotTFC p_110775_1_)
    {
        switch (p_110775_1_.getTameSkin())
        {
            case 0:
            default:
                return ocelotTextures;
            case 1:
                return blackOcelotTextures;
            case 2:
                return redOcelotTextures;
            case 3:
                return siameseOcelotTextures;
            case 4:
            	return herosiamOcelotTextures;
            case 5:
            	return red_cat_tigerOcelotTextures;
            case 6:
            	return calicoOcelotTextures;
            	
        }
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityOcelotTFC p_77041_1_, float p_77041_2_)
    {
        super.preRenderCallback(p_77041_1_, p_77041_2_);

        if (p_77041_1_.isTamed())
        {
            GL11.glScalef(0.8F, 0.8F, 0.8F);
        }
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLiving p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityOcelotTFC)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback(EntityLivingBase p_77041_1_, float p_77041_2_)
    {
        this.preRenderCallback((EntityOcelotTFC)p_77041_1_, p_77041_2_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(EntityLivingBase p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityOcelotTFC)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }

    /**
     * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
     */
    protected ResourceLocation getEntityTexture(Entity p_110775_1_)
    {
        return this.getEntityTexture((EntityOcelotTFC)p_110775_1_);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
    {
        this.doRender((EntityOcelotTFC)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
    }
}
