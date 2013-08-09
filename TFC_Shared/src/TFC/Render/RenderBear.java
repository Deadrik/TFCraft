package TFC.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Entities.Mobs.EntityBear;
import TFC.Render.Models.ModelBear;
public class RenderBear extends RenderLiving
{
	private static final ResourceLocation Texture = new ResourceLocation(Reference.ModID, "mob/Bear.png");
	private float scale = 1.1f;
	private ModelBear modelbear;

	public RenderBear (ModelBase par1ModelBase, float par2)
	{
		super (par1ModelBase, par2);
		modelbear = (ModelBear) par1ModelBase;
	}


	public void renderBear (EntityBear par1EntityBear, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving (par1EntityBear, par2, par4, par6, par8, par9);
		scale = 0.5F + par1EntityBear.size_mod;
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback (EntityLivingBase par1EntityLiving, float par2)
	{
		preRenderScale ((EntityBear) par1EntityLiving, par2);
	}

	protected void preRenderScale (EntityBear par1EntityBear, float par2)
	{
		GL11.glScalef (0.5F + par1EntityBear.size_mod, 0.5F + par1EntityBear.size_mod, 0.5F + par1EntityBear.size_mod);
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
	public void doRenderLiving (EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		renderBear ((EntityBear) par1EntityLiving, par2, par4, par6, par8, par9);
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
		renderBear ((EntityBear) par1Entity, par2, par4, par6, par8, par9);
	}


	@Override
	protected ResourceLocation func_110775_a(Entity entity) {
		// TODO Auto-generated method stub
		return Texture;
	}
}
