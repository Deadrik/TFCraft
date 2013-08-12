package TFC.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderWolf;
import net.minecraft.entity.Entity;
import TFC.API.Entities.IAnimal;
import TFC.Core.TFC_Core;

public class RenderWolfTFC extends RenderWolf
{
	public RenderWolfTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par2ModelBase, par3);
		this.setRenderPassModel(par2ModelBase);
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
}
