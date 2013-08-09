package TFC.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;
import TFC.Reference;
import TFC.API.Entities.IAnimal.GenderEnum;
import TFC.Entities.Mobs.EntityCowTFC;

public class RenderCowTFC extends RenderCow
{
	private static final ResourceLocation CowTex = new ResourceLocation("textures/entity/cow/cow.png");
	private static final ResourceLocation BullTex = new ResourceLocation(Reference.ModID, "mob/cow.png");

	public RenderCowTFC(ModelBase par1ModelBase, float par2)
	{
		super(par1ModelBase, par2);
	}

	public void renderCow(EntityCowTFC par1EntityCow, double par2, double par4, double par6, float par8, float par9)
	{
		super.doRenderLiving(par1EntityCow, par2, par4, par6, par8, par9);
	}

	@Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
	{
		this.renderCow((EntityCowTFC)par1EntityLiving, par2, par4, par6, par8, par9);
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
		this.renderCow((EntityCowTFC)par1Entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getTexture(EntityCowTFC entity)
	{
		if(entity.getGender() == GenderEnum.MALE) {
			return BullTex;
		} else {
			return CowTex;
		}
	}

	@Override
	protected ResourceLocation func_110775_a(Entity par1Entity)
	{
		return this.getTexture((EntityCowTFC)par1Entity);
	}
}
