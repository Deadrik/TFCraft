package TFC.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderPig;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.util.ResourceLocation;
import TFC.Entities.Mobs.EntityPigTFC;

public class RenderPigTFC extends RenderPig
{
	private static final ResourceLocation field_110888_a = new ResourceLocation("textures/entity/pig/pig_saddle.png");
	private static final ResourceLocation field_110887_f = new ResourceLocation("textures/entity/pig/pig.png");

	public RenderPigTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase,par2ModelBase, par3);
	}

	protected int renderSaddledPig(EntityPigTFC par1EntityPig, int par2, float par3)
	{
		if (par2 == 0 && par1EntityPig.getSaddled())
		{
			this.func_110776_a(field_110888_a);
			return 1;
		}
		else
		{
			return -1;
		}
	}

	public void func_40286_a(EntityPigTFC par1EntityPig, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityPig, par2, par4, par6, par8, par9);
	}

	/**
	 * Queries whether should render the specified pass or not.
	 */
	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		return this.renderSaddledPig((EntityPigTFC)par1EntityLivingBase, par2, par3);
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		this.func_40286_a((EntityPigTFC)par1EntityLiving, par2, par4, par6, par8, par9);
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
		this.func_40286_a((EntityPigTFC)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return this.func_110886_a((EntityPig)par1Entity);
	}
}
