package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelStand extends ModelBiped {

	public ModelStand()
	{
		super(0.0F);
	}

	public ModelStand(float par1)
	{
		super(par1, 0.0F, 64, 32);
	}

	public ModelStand(float par1, float par2, int par3, int par4)
	{
		super(par1,par2,par3,par4);
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity entity)
	{
		this.bipedHeadwear.rotateAngleY = 0;
		this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
		this.bipedRightArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float) Math.PI) * 2.0F * par2 * 0.5F + 0.001F;
		this.bipedLeftArm.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F + 0.001F;
		this.bipedRightArm.rotateAngleZ = 0.00F;
		this.bipedLeftArm.rotateAngleZ = 0.00F;
		this.bipedRightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2 + 0.001F;
		this.bipedLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.bipedRightLeg.rotateAngleY = 0.0F;
		this.bipedLeftLeg.rotateAngleY = 0.0F;

		if (this.heldItemLeft != 0)
		{
			this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F) * this.heldItemLeft;
		}

		if (this.heldItemRight != 0)
		{
			this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float) Math.PI / 10F) * this.heldItemRight;
		}

		this.bipedRightArm.rotateAngleY = 0.0F;
		this.bipedLeftArm.rotateAngleY = 0.0F;
		//float f6;
		//float f7;

		if (this.onGround > -9990.0F)
		{
			//f6 = this.onGround;
			this.bipedBody.rotateAngleY = 0;
			this.bipedRightArm.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedRightArm.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
			this.bipedLeftArm.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;

			this.bipedRightLeg.rotationPointZ = MathHelper.sin(this.bipedBody.rotateAngleY) * 1.9F;
			this.bipedRightLeg.rotationPointX = -MathHelper.cos(this.bipedBody.rotateAngleY) * 1.9F;
			this.bipedLeftLeg.rotationPointZ = -MathHelper.sin(this.bipedBody.rotateAngleY) * 1.9F;
			this.bipedLeftLeg.rotationPointX = MathHelper.cos(this.bipedBody.rotateAngleY) * 1.9F;

			this.bipedRightLeg.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftLeg.rotateAngleY += this.bipedBody.rotateAngleY;

			this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY;
			this.bipedLeftArm.rotateAngleY += this.bipedBody.rotateAngleY;
			//this.bipedLeftArm.rotateAngleX += this.bipedBody.rotateAngleY;
		}

		this.bipedBody.rotateAngleX = 0.0F;
		this.bipedRightLeg.rotationPointY = 12.0F;
		this.bipedLeftLeg.rotationPointY = 12.0F;
		this.bipedHead.rotationPointY = 0.0F;
		this.bipedHeadwear.rotationPointY = 0.0F;

		this.bipedHead.rotateAngleY = 0;
		this.bipedHeadwear.rotateAngleY = 0;
		this.bipedLeftLeg.rotateAngleX+=0.01f;
		this.bipedRightLeg.rotateAngleX+=0.01f;

	}
}
