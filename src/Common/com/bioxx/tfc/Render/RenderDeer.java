package com.bioxx.tfc.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Entities.Mobs.EntityDeer;
import com.bioxx.tfc.api.Entities.IAnimal;
public class RenderDeer extends RenderLiving
{
	private static final ResourceLocation DEER_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/deer.png");
	private static final ResourceLocation FAWN_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/deer_fawn.png");

	//private float scale = 1.0f;
	//private ModelDeer modeldeer;

	public RenderDeer (ModelBase par1ModelBase, float par2)
	{
		super (par1ModelBase, par2);
		//modeldeer = (ModelDeer) par1ModelBase;
	}

	public void renderDeer(EntityDeer par1EntityDeer, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRender(par1EntityDeer, par2, par4, par6, par8, par9);
		//scale = par1EntityDeer.getSize();
	}

	/*protected void func_25006_b (EntityDeer entitydeer, float f)
	{
	}*/

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback (EntityLivingBase par1EntityLiving, float par2)
	{
		preRenderScale ((EntityDeer) par1EntityLiving, par2);
		//func_25006_b ((EntityDeer) par1EntityLiving, par2);
	}

	protected void preRenderScale (EntityDeer par1EntityDeer, float par2)
	{
		GL11.glScalef(par1EntityDeer.getSizeMod() - 0.3f, par1EntityDeer.getSizeMod() - 0.3f, par1EntityDeer.getSizeMod() - 0.3f);
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float handleRotationFloat (EntityLivingBase par1EntityLiving, float par2)
	{
		return 1.0f;
	}

	@Override
	public void doRender(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderDeer ((EntityDeer) par1EntityLiving, par2, par4, par6, par8, par9);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender (Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		renderDeer ((EntityDeer) par1Entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getTexture(IAnimal entity)
	{
		if(!entity.isAdult())
		{
			return FAWN_TEXTURE;
		}
		else
		{
			return DEER_TEXTURE;
		}
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return getTexture((IAnimal)entity);
	}
}
