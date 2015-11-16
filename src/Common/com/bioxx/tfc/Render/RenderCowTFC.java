package com.bioxx.tfc.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderCow;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityCowTFC;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;

public class RenderCowTFC extends RenderCow
{
	private static final ResourceLocation COW_TEXTURE = new ResourceLocation("textures/entity/cow/cow.png");
	private static final ResourceLocation BULL_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/bull.png");

	public RenderCowTFC(ModelBase par1ModelBase, float par2)
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
		this.shadowSize = 0.35f + (TFC_Core.getPercentGrown((IAnimal)par1Entity)*0.35f);
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}

	protected ResourceLocation getTexture(EntityCowTFC entity)
	{
		if(entity.getGender() == GenderEnum.MALE) {
			return BULL_TEXTURE;
		} else {
			return COW_TEXTURE;
		}
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		float scale = ((EntityCowTFC) par1EntityLivingBase).getSizeMod();
		GL11.glScalef(scale, scale, scale);
	}



	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getTexture((EntityCowTFC)par1Entity);
	}
}
