package TFC.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.API.Entities.IAnimal;
import TFC.API.Entities.IAnimal.GenderEnum;
import TFC.Core.TFC_Core;
import TFC.Entities.Mobs.EntityChickenTFC;
public class RenderChickenTFC extends RenderChicken
{
	private static final ResourceLocation ChickenTexture = new ResourceLocation("textures/entity/chicken.png");
	private static final ResourceLocation RoosterTexture = new ResourceLocation(Reference.ModID, "mob/rooster.png");
	private static final ResourceLocation ChickTexture = new ResourceLocation(Reference.ModID, "mob/chick.png");

	public RenderChickenTFC(ModelBase par1ModelBase, float par2)
	{
		super(par1ModelBase, par2);
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
		this.shadowSize = 0.15f + (TFC_Core.getPercentGrown((IAnimal)par1Entity)*0.15f);
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getTexture(IAnimal entity)
	{
		float percent = TFC_Core.getPercentGrown(entity);

		if(percent < 0.65f){
			return ChickTexture;
		}
		else if(entity.getGender() == GenderEnum.MALE){
			return RoosterTexture;
		}
		else{
			return ChickenTexture;
		}
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		float scale = (((EntityChickenTFC)par1EntityLivingBase).size_mod/3)+0.5f;
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		return getTexture((IAnimal)entity);
	}
}
