package TFC.Render;

import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

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


    public void doRenderLiving (EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
	GL11.glPushMatrix ();
	GL11.glDisable (GL11.GL_CULL_FACE);
	mainModel.onGround = renderSwingProgress (par1EntityLiving, par9);

	if (renderPassModel != null)
	{
	    renderPassModel.onGround = mainModel.onGround;
	}

	mainModel.isRiding = par1EntityLiving.isRiding ();

	if (renderPassModel != null)
	{
	    renderPassModel.isRiding = mainModel.isRiding;
	}

	mainModel.isChild = par1EntityLiving.isChild ();
	if (par1EntityLiving instanceof EntityAnimalTFC){
		mainModel.sex = ((EntityAnimalTFC)par1EntityLiving).sex;
	}	
	    mainModel.age = ((float) (((EntityAgeable) par1EntityLiving).getGrowingAge ()) / -24000F) / (float) ((EntityAnimalTFC)par1EntityLiving).adultAge;	

	if (renderPassModel != null)
	{
	    renderPassModel.isChild = mainModel.isChild;
	    renderPassModel.age = mainModel.age;
	}

	try
	{
	    float f = func_48418_a (par1EntityLiving.prevRenderYawOffset, par1EntityLiving.renderYawOffset, par9);
	    float f1 = func_48418_a (par1EntityLiving.prevRotationYawHead, par1EntityLiving.rotationYawHead, par9);
	    float f2 = par1EntityLiving.prevRotationPitch + (par1EntityLiving.rotationPitch - par1EntityLiving.prevRotationPitch) * par9;
	    renderLivingAt (par1EntityLiving, par2, par4, par6);
	    float f3 = handleRotationFloat (par1EntityLiving, par9);
	    rotateCorpse (par1EntityLiving, f3, f, par9);
	    float f4 = 0.0625F;
	    GL11.glEnable (GL12.GL_RESCALE_NORMAL);
	    GL11.glScalef (-1F, -1F, 1.0F);
	    preRenderCallback (par1EntityLiving, par9);
	    GL11.glTranslatef (0.0F, -24F * f4 - 0.0078125F, 0.0F);
	    float f5 = par1EntityLiving.field_705_Q + (par1EntityLiving.field_704_R - par1EntityLiving.field_705_Q) * par9;
	    float f6 = par1EntityLiving.field_703_S - par1EntityLiving.field_704_R * (1.0F - par9);

	    if (par1EntityLiving.isChild ())
	    {
		f6 *= 1F + 2F*mainModel.age;
	    }

	    if (f5 > 1.0F)
	    {
		f5 = 1.0F;
	    }

	    GL11.glEnable (GL11.GL_ALPHA_TEST);
	    mainModel.setLivingAnimations (par1EntityLiving, f6, f5, par9);
	    renderModel (par1EntityLiving, f6, f5, f3, f1 - f, f2, f4);

	    for (int i = 0 ; i < 4 ; i++)
	    {
		int j = shouldRenderPass (par1EntityLiving, i, par9);

		if (j <= 0)
		{
		    continue;
		}

		renderPassModel.setLivingAnimations (par1EntityLiving, f6, f5, par9);
		renderPassModel.render (par1EntityLiving, f6, f5, f3, f1 - f, f2, f4);

		if (j == 15)
		{
		    float f8 = (float) par1EntityLiving.ticksExisted + par9;
		    loadTexture ("%blur%/misc/glint.png");
		    GL11.glEnable (GL11.GL_BLEND);
		    float f10 = 0.5F;
		    GL11.glColor4f (f10, f10, f10, 1.0F);
		    GL11.glDepthFunc (GL11.GL_EQUAL);
		    GL11.glDepthMask (false);

		    for (int i1 = 0 ; i1 < 2 ; i1++)
		    {
			GL11.glDisable (GL11.GL_LIGHTING);
			float f13 = 0.76F;
			GL11.glColor4f (0.5F * f13, 0.25F * f13, 0.8F * f13, 1.0F);
			GL11.glBlendFunc (GL11.GL_SRC_COLOR, GL11.GL_ONE);
			GL11.glMatrixMode (GL11.GL_TEXTURE);
			GL11.glLoadIdentity ();
			float f15 = f8 * (0.001F + (float) i1 * 0.003F) * 20F;
			float f16 = 0.3333333F;
			GL11.glScalef (f16, f16, f16);
			GL11.glRotatef (30F - (float) i1 * 60F, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef (0.0F, f15, 0.0F);
			GL11.glMatrixMode (GL11.GL_MODELVIEW);
			renderPassModel.render (par1EntityLiving, f6, f5, f3, f1 - f, f2, f4);
		    }

		    GL11.glColor4f (1.0F, 1.0F, 1.0F, 1.0F);
		    GL11.glMatrixMode (GL11.GL_TEXTURE);
		    GL11.glDepthMask (true);
		    GL11.glLoadIdentity ();
		    GL11.glMatrixMode (GL11.GL_MODELVIEW);
		    GL11.glEnable (GL11.GL_LIGHTING);
		    GL11.glDisable (GL11.GL_BLEND);
		    GL11.glDepthFunc (GL11.GL_LEQUAL);
		}

		GL11.glDisable (GL11.GL_BLEND);
		GL11.glEnable (GL11.GL_ALPHA_TEST);
	    }

	    renderEquippedItems (par1EntityLiving, par9);
	    float f7 = par1EntityLiving.getBrightness (par9);
	    int k = getColorMultiplier (par1EntityLiving, f7, par9);
	    OpenGlHelper.setActiveTexture (OpenGlHelper.lightmapTexUnit);
	    GL11.glDisable (GL11.GL_TEXTURE_2D);
	    OpenGlHelper.setActiveTexture (OpenGlHelper.defaultTexUnit);

	    if ((k >> 24 & 0xff) > 0 || par1EntityLiving.hurtTime > 0 || par1EntityLiving.deathTime > 0)
	    {
		GL11.glDisable (GL11.GL_TEXTURE_2D);
		GL11.glDisable (GL11.GL_ALPHA_TEST);
		GL11.glEnable (GL11.GL_BLEND);
		GL11.glBlendFunc (GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		GL11.glDepthFunc (GL11.GL_EQUAL);

		if (par1EntityLiving.hurtTime > 0 || par1EntityLiving.deathTime > 0)
		{
		    GL11.glColor4f (f7, 0.0F, 0.0F, 0.4F);
		    mainModel.render (par1EntityLiving, f6, f5, f3, f1 - f, f2, f4);

		    for (int l = 0 ; l < 4 ; l++)
		    {
			if (inheritRenderPass (par1EntityLiving, l, par9) >= 0)
			{
			    GL11.glColor4f (f7, 0.0F, 0.0F, 0.4F);
			    renderPassModel.render (par1EntityLiving, f6, f5, f3, f1 - f, f2, f4);
			}
		    }
		}

		if ((k >> 24 & 0xff) > 0)
		{
		    float f9 = (float) (k >> 16 & 0xff) / 255F;
		    float f11 = (float) (k >> 8 & 0xff) / 255F;
		    float f12 = (float) (k & 0xff) / 255F;
		    float f14 = (float) (k >> 24 & 0xff) / 255F;
		    GL11.glColor4f (f9, f11, f12, f14);
		    mainModel.render (par1EntityLiving, f6, f5, f3, f1 - f, f2, f4);

		    for (int j1 = 0 ; j1 < 4 ; j1++)
		    {
			if (inheritRenderPass (par1EntityLiving, j1, par9) >= 0)
			{
			    GL11.glColor4f (f9, f11, f12, f14);
			    renderPassModel.render (par1EntityLiving, f6, f5, f3, f1 - f, f2, f4);
			}
		    }
		}

		GL11.glDepthFunc (GL11.GL_LEQUAL);
		GL11.glDisable (GL11.GL_BLEND);
		GL11.glEnable (GL11.GL_ALPHA_TEST);
		GL11.glEnable (GL11.GL_TEXTURE_2D);
	    }

	    GL11.glDisable (GL12.GL_RESCALE_NORMAL);
	}
	catch (Exception exception)
	{
	    exception.printStackTrace ();
	}

	OpenGlHelper.setActiveTexture (OpenGlHelper.lightmapTexUnit);
	GL11.glEnable (GL11.GL_TEXTURE_2D);
	OpenGlHelper.setActiveTexture (OpenGlHelper.defaultTexUnit);
	GL11.glEnable (GL11.GL_CULL_FACE);
	GL11.glPopMatrix ();
	passSpecialRender (par1EntityLiving, par2, par4, par6);
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
