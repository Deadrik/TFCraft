package com.bioxx.tfc.Render;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityPolarBear;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Entities.IAnimal;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;
public class RenderPolarBear extends RenderLiving
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/PolarBear.png");

	//private float scale = 1.1f;
	//private ModelBear modelbear;

	public RenderPolarBear (ModelBase par1ModelBase, float par2)
	{
		super (par1ModelBase, par2);
		//modelbear = (ModelBear) par1ModelBase;
	}


	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback (EntityLivingBase par1EntityLiving, float par2)
	{
		preRenderScale ((EntityPolarBear) par1EntityLiving, par2);
	}

	protected void preRenderScale (EntityPolarBear par1EntityBear, float par2)
	{
		GL11.glScalef(0.3f + par1EntityBear.getSizeMod(), 0.3f + par1EntityBear.getSizeMod(), 0.3f + par1EntityBear.getSizeMod());
	}


	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float handleRotationFloat (EntityLivingBase par1EntityLiving, float par2)
	{
		return 1.0f;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.shadowSize = 0.35f + (TFC_Core.getPercentGrown((IAnimal)par1Entity)*0.35f);
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}


	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		return TEXTURE;
	}
}
