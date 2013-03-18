package TFC.Render;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.FontRenderer;
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

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import TFC.*;
import TFC.Entities.EntityAnimalTFC;

public class RenderLivingTFC extends Render
{
    protected ModelBaseTFC mainModel;

    /** The model to be used during the render passes. */
    protected ModelBaseTFC renderPassModel;

    public RenderLivingTFC (ModelBaseTFC par1ModelBase, float par2)
    {
	mainModel = par1ModelBase;
	shadowSize = par2;
    }


    /**
     * Sets the model to be used in the current render pass (the first render pass is done after the primary model is
     * rendered) Args: model
     */
    public void setRenderPassModel (ModelBaseTFC par1ModelBase)
    {
	renderPassModel = par1ModelBase;
    }


    private float func_48418_a (float par1, float par2, float par3)
    {
	float f;

	for (f = par2 - par1 ; f < -180F ; f += 360F)
	{
	}

	for (; f >= 180F ; f -= 360F)
	{
	}

	return par1 + par3 * f;
    }


    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_CULL_FACE);
        this.mainModel.onGround = this.renderSwingProgress(par1EntityLiving, par9);

        if (this.renderPassModel != null)
        {
            this.renderPassModel.onGround = this.mainModel.onGround;
        }

        this.mainModel.isRiding = par1EntityLiving.isRiding();

        if (this.renderPassModel != null)
        {
            this.renderPassModel.isRiding = this.mainModel.isRiding;
        }

        this.mainModel.isChild = par1EntityLiving.isChild();

        if (this.renderPassModel != null)
        {
            this.renderPassModel.isChild = this.mainModel.isChild;
        }

        try
        {
            float var10 = this.func_48418_a(par1EntityLiving.prevRenderYawOffset, par1EntityLiving.renderYawOffset, par9);
            float var11 = this.func_48418_a(par1EntityLiving.prevRotationYawHead, par1EntityLiving.rotationYawHead, par9);
            float var12 = par1EntityLiving.prevRotationPitch + (par1EntityLiving.rotationPitch - par1EntityLiving.prevRotationPitch) * par9;
            this.renderLivingAt(par1EntityLiving, par2, par4, par6);
            float var13 = this.handleRotationFloat(par1EntityLiving, par9);
            this.rotateCorpse(par1EntityLiving, var13, var10, par9);
            float var14 = 0.0625F;
            GL11.glEnable(GL12.GL_RESCALE_NORMAL);
            GL11.glScalef(-1.0F, -1.0F, 1.0F);
            this.preRenderCallback(par1EntityLiving, par9);
            GL11.glTranslatef(0.0F, -24.0F * var14 - 0.0078125F, 0.0F);
            float var15 = par1EntityLiving.prevLegYaw + (par1EntityLiving.legYaw - par1EntityLiving.prevLegYaw) * par9;
            float var16 = par1EntityLiving.legSwing - par1EntityLiving.legYaw * (1.0F - par9);

            if (par1EntityLiving.isChild())
            {
                var16 *= 3.0F;
            }

            if (var15 > 1.0F)
            {
                var15 = 1.0F;
            }

            GL11.glEnable(GL11.GL_ALPHA_TEST);
            this.mainModel.setLivingAnimations(par1EntityLiving, var16, var15, par9);
            this.renderModel(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
            float var19;
            int var18;
            float var20;
            float var22;

            for (int var17 = 0; var17 < 4; ++var17)
            {
                var18 = this.shouldRenderPass(par1EntityLiving, var17, par9);

                if (var18 > 0)
                {
                    this.renderPassModel.setLivingAnimations(par1EntityLiving, var16, var15, par9);
                    this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);

                    if (var18 == 15)
                    {
                        var19 = (float)par1EntityLiving.ticksExisted + par9;
                        this.loadTexture("%blur%/misc/glint.png");
                        GL11.glEnable(GL11.GL_BLEND);
                        var20 = 0.5F;
                        GL11.glColor4f(var20, var20, var20, 1.0F);
                        GL11.glDepthFunc(GL11.GL_EQUAL);
                        GL11.glDepthMask(false);

                        for (int var21 = 0; var21 < 2; ++var21)
                        {
                            GL11.glDisable(GL11.GL_LIGHTING);
                            var22 = 0.76F;
                            GL11.glColor4f(0.5F * var22, 0.25F * var22, 0.8F * var22, 1.0F);
                            GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
                            GL11.glMatrixMode(GL11.GL_TEXTURE);
                            GL11.glLoadIdentity();
                            float var23 = var19 * (0.001F + (float)var21 * 0.003F) * 20.0F;
                            float var24 = 0.33333334F;
                            GL11.glScalef(var24, var24, var24);
                            GL11.glRotatef(30.0F - (float)var21 * 60.0F, 0.0F, 0.0F, 1.0F);
                            GL11.glTranslatef(0.0F, var23, 0.0F);
                            GL11.glMatrixMode(GL11.GL_MODELVIEW);
                            this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                        }

                        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                        GL11.glMatrixMode(GL11.GL_TEXTURE);
                        GL11.glDepthMask(true);
                        GL11.glLoadIdentity();
                        GL11.glMatrixMode(GL11.GL_MODELVIEW);
                        GL11.glEnable(GL11.GL_LIGHTING);
                        GL11.glDisable(GL11.GL_BLEND);
                        GL11.glDepthFunc(GL11.GL_LEQUAL);
                    }

                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_ALPHA_TEST);
                }
            }

            this.renderEquippedItems(par1EntityLiving, par9);
            float var26 = par1EntityLiving.getBrightness(par9);
            var18 = this.getColorMultiplier(par1EntityLiving, var26, par9);
            OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
            GL11.glDisable(GL11.GL_TEXTURE_2D);
            OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);

            if ((var18 >> 24 & 255) > 0 || par1EntityLiving.hurtTime > 0 || par1EntityLiving.deathTime > 0)
            {
                GL11.glDisable(GL11.GL_TEXTURE_2D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_BLEND);
                GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                GL11.glDepthFunc(GL11.GL_EQUAL);

                if (par1EntityLiving.hurtTime > 0 || par1EntityLiving.deathTime > 0)
                {
                    GL11.glColor4f(var26, 0.0F, 0.0F, 0.4F);
                    this.mainModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);

                    for (int var27 = 0; var27 < 4; ++var27)
                    {
                        if (this.inheritRenderPass(par1EntityLiving, var27, par9) >= 0)
                        {
                            GL11.glColor4f(var26, 0.0F, 0.0F, 0.4F);
                            this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                        }
                    }
                }

                if ((var18 >> 24 & 255) > 0)
                {
                    var19 = (float)(var18 >> 16 & 255) / 255.0F;
                    var20 = (float)(var18 >> 8 & 255) / 255.0F;
                    float var29 = (float)(var18 & 255) / 255.0F;
                    var22 = (float)(var18 >> 24 & 255) / 255.0F;
                    GL11.glColor4f(var19, var20, var29, var22);
                    this.mainModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);

                    for (int var28 = 0; var28 < 4; ++var28)
                    {
                        if (this.inheritRenderPass(par1EntityLiving, var28, par9) >= 0)
                        {
                            GL11.glColor4f(var19, var20, var29, var22);
                            this.renderPassModel.render(par1EntityLiving, var16, var15, var13, var11 - var10, var12, var14);
                        }
                    }
                }

                GL11.glDepthFunc(GL11.GL_LEQUAL);
                GL11.glDisable(GL11.GL_BLEND);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glEnable(GL11.GL_TEXTURE_2D);
            }

            GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        }
        catch (Exception var25)
        {
            var25.printStackTrace();
        }

        OpenGlHelper.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        OpenGlHelper.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GL11.glEnable(GL11.GL_CULL_FACE);
        GL11.glPopMatrix();
        this.passSpecialRender(par1EntityLiving, par2, par4, par6);
    }


    /**
     * Renders the model in RenderLiving
     */
    protected void renderModel (EntityLiving par1EntityLiving, float par2, float par3, float par4, float par5, float par6, float par7)
    {
	loadDownloadableImageTexture (par1EntityLiving.skinUrl, par1EntityLiving.getTexture ());
	mainModel.render (par1EntityLiving, par2, par3, par4, par5, par6, par7);
    }


    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    protected void renderLivingAt (EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
	GL11.glTranslatef ((float) par2, (float) par4, (float) par6);
    }


    protected void rotateCorpse (EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
	GL11.glRotatef (180F - par3, 0.0F, 1.0F, 0.0F);

	if (par1EntityLiving.deathTime > 0)
	{
	    float f = ((((float) par1EntityLiving.deathTime + par4) - 1.0F) / 20F) * 1.6F;
	    f = MathHelper.sqrt_float (f);

	    if (f > 1.0F)
	    {
		f = 1.0F;
	    }

	    GL11.glRotatef (f * getDeathMaxRotation (par1EntityLiving), 0.0F, 0.0F, 1.0F);
	}
    }


    protected float renderSwingProgress (EntityLiving par1EntityLiving, float par2)
    {
	return par1EntityLiving.getSwingProgress (par2);
    }


    /**
     * Defines what float the third param in setRotationAngles of ModelBase is
     */
    protected float handleRotationFloat (EntityLiving par1EntityLiving, float par2)
    {
	return (float) par1EntityLiving.ticksExisted + par2;
    }


    protected void renderEquippedItems (EntityLiving entityliving, float f)
    {
    }


    protected int inheritRenderPass (EntityLiving par1EntityLiving, int par2, float par3)
    {
	return shouldRenderPass (par1EntityLiving, par2, par3);
    }


    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass (EntityLiving par1EntityLiving, int par2, float par3)
    {
	return -1;
    }


    protected float getDeathMaxRotation (EntityLiving par1EntityLiving)
    {
	return 90F;
    }


    /**
     * Returns an ARGB int color back. Args: entityLiving, lightBrightness, partialTickTime
     */
    protected int getColorMultiplier (EntityLiving par1EntityLiving, float par2, float par3)
    {
	return 0;
    }


    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    protected void preRenderCallback (EntityLiving entityliving, float f)
    {
    }


    /**
     * Passes the specialRender and renders it
     */
    protected void passSpecialRender (EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
	if (!Minecraft.isDebugInfoEnabled ())
	    ;
    }


    /**
     * Draws the debug or playername text above a living
     */
    protected void renderLivingLabel (EntityLiving par1EntityLiving, String par2Str, double par3, double par5, double par7, int par9)
    {
	float f = par1EntityLiving.getDistanceToEntity (renderManager.livingPlayer);

	if (f > (float) par9)
	{
	    return;
	}

	FontRenderer fontrenderer = getFontRendererFromRenderManager ();
	float f1 = 1.6F;
	float f2 = 0.01666667F * f1;
	GL11.glPushMatrix ();
	GL11.glTranslatef ((float) par3 + 0.0F, (float) par5 + 2.3F, (float) par7);
	GL11.glNormal3f (0.0F, 1.0F, 0.0F);
	GL11.glRotatef (-renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
	GL11.glRotatef (renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
	GL11.glScalef (-f2, -f2, f2);
	GL11.glDisable (GL11.GL_LIGHTING);
	GL11.glDepthMask (false);
	GL11.glDisable (GL11.GL_DEPTH_TEST);
	GL11.glEnable (GL11.GL_BLEND);
	GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
	Tessellator tessellator = Tessellator.instance;
	byte byte0 = 0;

	if (par2Str.equals ("deadmau5"))
	{
	    byte0 = -10;
	}

	GL11.glDisable (GL11.GL_TEXTURE_2D);
	tessellator.startDrawingQuads ();
	int i = fontrenderer.getStringWidth (par2Str) / 2;
	tessellator.setColorRGBA_F (0.0F, 0.0F, 0.0F, 0.25F);
	tessellator.addVertex (-i - 1, -1 + byte0, 0.0D);
	tessellator.addVertex (-i - 1, 8 + byte0, 0.0D);
	tessellator.addVertex (i + 1, 8 + byte0, 0.0D);
	tessellator.addVertex (i + 1, -1 + byte0, 0.0D);
	tessellator.draw ();
	GL11.glEnable (GL11.GL_TEXTURE_2D);
	fontrenderer.drawString (par2Str, -fontrenderer.getStringWidth (par2Str) / 2, byte0, 0x20ffffff);
	GL11.glEnable (GL11.GL_DEPTH_TEST);
	GL11.glDepthMask (true);
	fontrenderer.drawString (par2Str, -fontrenderer.getStringWidth (par2Str) / 2, byte0, -1);
	GL11.glEnable (GL11.GL_LIGHTING);
	GL11.glDisable (GL11.GL_BLEND);
	GL11.glColor4f (1.0F, 1.0F, 1.0F, 1.0F);
	GL11.glPopMatrix ();
    }


    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender (Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
	doRenderLiving ((EntityLiving) par1Entity, par2, par4, par6, par8, par9);
    }
}
