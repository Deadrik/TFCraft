package com.bioxx.tfc.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderChicken;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityPheasantTFC;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;
public class RenderPheasantTFC extends RenderChicken
{
	private static final ResourceLocation FEMALE_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/PheasantF.png");
	private static final ResourceLocation MALE_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/PheasantM.png");
	private static final ResourceLocation CHICK_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/pheasant_chick.png");

	public RenderPheasantTFC(ModelBase par1ModelBase, float par2)
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
			return CHICK_TEXTURE;
		}
		else if(entity.getGender() == GenderEnum.MALE){
			return MALE_TEXTURE;
		}
		else{
			return FEMALE_TEXTURE;
		}
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		float scale = ((EntityPheasantTFC) par1EntityLivingBase).getSizeMod() / 3 + 0.5f;
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(90, 0, 1, 0);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		return getTexture((IAnimal)entity);
	}
}
