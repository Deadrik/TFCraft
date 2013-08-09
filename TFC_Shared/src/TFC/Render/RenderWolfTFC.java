package TFC.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.util.ResourceLocation;
import TFC.Entities.Mobs.EntityWolfTFC;

public class RenderWolfTFC extends RenderWolf
{
	private static final ResourceLocation field_110917_a = new ResourceLocation("textures/entity/wolf/wolf.png");
	private static final ResourceLocation field_110915_f = new ResourceLocation("textures/entity/wolf/wolf_tame.png");
	private static final ResourceLocation field_110916_g = new ResourceLocation("textures/entity/wolf/wolf_angry.png");
	private static final ResourceLocation field_110918_h = new ResourceLocation("textures/entity/wolf/wolf_collar.png");

	public RenderWolfTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par2ModelBase, par3);
		this.setRenderPassModel(par2ModelBase);
	}

	public void renderWolf(EntityWolfTFC par1EntityWolf, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityWolf, par2, par4, par6, par8, par9);
	}

	protected float getTailRotation(EntityWolfTFC par1EntityWolf, float par2)
	{
		return par1EntityWolf.getTailRotation();
	}

	protected void func_25006_b(EntityWolfTFC par1EntityWolf, float par2) {}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLiving, float par2)
	{
		this.func_25006_b((EntityWolfTFC)par1EntityLiving, par2);
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float handleRotationFloat(EntityLivingBase par1EntityLiving, float par2)
	{
		return this.getTailRotation((EntityWolfTFC)par1EntityLiving, par2);
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderWolf((EntityWolfTFC)par1EntityLiving, par2, par4, par6, par8, par9);
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
		this.renderWolf((EntityWolfTFC)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation func_110914_a(EntityWolf par1EntityWolf)
	{
		return par1EntityWolf.isTamed() ? field_110915_f : (par1EntityWolf.isAngry() ? field_110916_g : field_110917_a);
	}

	@Override
	protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return this.func_110914_a((EntityWolf)par1Entity);
	}
}
