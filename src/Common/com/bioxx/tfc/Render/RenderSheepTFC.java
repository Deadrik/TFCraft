package com.bioxx.tfc.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSheep;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntitySheepTFC;
import com.bioxx.tfc.api.Entities.IAnimal;

public class RenderSheepTFC extends RenderSheep
{
	private static final ResourceLocation SHEEP_FUR_TEXTURE = new ResourceLocation("textures/entity/sheep/sheep_fur.png");
	private static final ResourceLocation SHEEP_TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/sheep.png");

	public RenderSheepTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
	{
		super(par1ModelBase, par2ModelBase, par3);
		this.setRenderPassModel(par2ModelBase);
	}

	protected int setWoolColorAndRender(EntitySheepTFC par1EntitySheep, int par2, float par3)
	{
		if (par2 == 0 && !par1EntitySheep.getSheared())
		{
			this.bindTexture(SHEEP_FUR_TEXTURE);
			float var4 = 1.0F;
			int var5 = par1EntitySheep.getFleeceColor();
			GL11.glColor3f(var4 * EntitySheepTFC.FLEECE_COLOR_TABLE[var5][0], var4 * EntitySheepTFC.FLEECE_COLOR_TABLE[var5][1], var4 * EntitySheepTFC.FLEECE_COLOR_TABLE[var5][2]);
			return 1;
		}
		else
		{
			return -1;
		}
	}

	protected ResourceLocation getTexture(EntitySheep par1EntitySheep)
	{
		return SHEEP_TEXTURE;
	}

	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{
		float scale = ((EntitySheepTFC) par1EntityLivingBase).getSizeMod() / 2 + 0.5f;
		GL11.glScalef(scale, scale, scale);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getTexture((EntitySheep)par1Entity);
	}

	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.shadowSize = 0.35f + (TFC_Core.getPercentGrown((IAnimal)par1Entity)*0.35f);
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}
}
