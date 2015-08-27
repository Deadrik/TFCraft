package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ModelSquidTFC extends ModelBase
{
	/** The squid's body */
	private ModelRenderer squidBody;

	/** The squid's tentacles */
	private ModelRenderer[] squidTentacles = new ModelRenderer[8];

	public ModelSquidTFC()
	{
		byte b0 = -16;
		this.squidBody = new ModelRenderer(this, 0, 0);
		this.squidBody.addBox(-6.0F, -8.0F, -6.0F, 12, 16, 12);
		this.squidBody.rotationPointY += 24 + b0;

		for (int i = 0; i < this.squidTentacles.length; ++i)
		{
			this.squidTentacles[i] = new ModelRenderer(this, 48, 0);
			double d0 = i * Math.PI * 2.0D / this.squidTentacles.length;
			float f = (float)Math.cos(d0) * 5.0F;
			float f1 = (float)Math.sin(d0) * 5.0F;
			this.squidTentacles[i].addBox(-1.0F, 0.0F, -1.0F, 2, 18, 2);
			this.squidTentacles[i].rotationPointX = f;
			this.squidTentacles[i].rotationPointZ = f1;
			this.squidTentacles[i].rotationPointY = 31 + b0;
			d0 = i * Math.PI * -2.0D / this.squidTentacles.length + Math.PI / 2D;
			this.squidTentacles[i].rotateAngleY = (float)d0;
		}
	}

	/**
	 * Sets the model's various rotation angles. For bipeds, par1 and par2 are used for animating the movement of arms
	 * and legs, where par1 represents the time(so that arms and legs swing back and forth) and par2 represents how
	 * "far" arms and legs can swing at most.
	 */
	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
	{
		ModelRenderer[] amodelrenderer = this.squidTentacles;
		int i = amodelrenderer.length;

		for (int j = 0; j < i; ++j)
		{
			ModelRenderer modelrenderer = amodelrenderer[j];
			modelrenderer.rotateAngleX = par3;
		}
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, par1Entity);
		this.squidBody.render(par7);

		for (int i = 0; i < this.squidTentacles.length; ++i)
		{
			this.squidTentacles[i].render(par7);
		}
	}
}
