package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;
public class ModelCowTFC extends ModelQuadruped
{
	public ModelRenderer udders;
	private ModelRenderer horn1;
	private ModelRenderer horn2;
	private ModelRenderer horn1b;
	private ModelRenderer horn2b;
	public ModelCowTFC()
	{
		super(12, 0.0F);
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-4.0F, -4.0F, -6.0F, 8, 8, 6, 0.0F);
		this.head.setRotationPoint(0.0F, 4.0F, -8.0F);

		horn1 = new ModelRenderer(this,22,0);
		horn1.addBox(0F, 0F, 0F, 1, 3, 1, 0.15F);
		horn1.setRotationPoint(-5.5F, -2.5F, -2F);
		horn1.rotateAngleZ = (float)-Math.PI/2;

		horn1b = new ModelRenderer(this,22,0);
		horn1b.addBox(0,-2.1f,-0.5f,1,3,1,0F);
		horn1b.setRotationPoint(0f, 0f, 0f);
		horn1b.rotateAngleX = (float)Math.PI/3f;
		horn1b.rotateAngleY = (float)-Math.PI/12f;
		horn1.addChild(horn1b);

		this.head.addChild(horn1);
		horn2 = new ModelRenderer(this,22,0);
		horn2.addBox(0F, -3F, 0F, 1, 3, 1, 0.15F);
		horn2.setRotationPoint(5.5F, -2.5F, -2F);
		horn2.rotateAngleZ = (float)-Math.PI/2;

		horn2b = new ModelRenderer(this,22,0);
		horn2b.addBox(0f, -0.8F, -0.5f, 1, 3, 1,0F);
		horn2b.setRotationPoint(0F, 0F, 0F);
		horn2b.rotateAngleX = (float)-Math.PI/3F;
		horn2b.rotateAngleY = (float)-Math.PI/12F;
		horn2.addChild(horn2b);

		this.head.addChild(horn2);
		this.body = new ModelRenderer(this, 18, 4);
		this.body.addBox(-6.0F, -10.0F, -7.0F, 12, 18, 10, 0.0F);
		this.body.setRotationPoint(0.0F, 5.0F, 2.0F);
		this.udders = new ModelRenderer(this, 18,4);
		this.udders.setRotationPoint(0.0F, 5.0F, 2.0F);
		this.udders.setTextureOffset(52, 0).addBox(-2.0F, 2.0F, -8.0F, 4, 6, 1);

		--this.leg1.rotationPointX;
		++this.leg2.rotationPointX;
		this.leg1.rotationPointZ += 0.0F;
		this.leg2.rotationPointZ += 0.0F;
		--this.leg3.rotationPointX;
		++this.leg4.rotationPointX;
		--this.leg3.rotationPointZ;
		--this.leg4.rotationPointZ;
		this.field_78151_h += 2.0F;
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7);

		float percent = TFC_Core.getPercentGrown((IAnimal)entity);
		
		float ageScale = 2.0F-percent;
		float ageHeadScale = (float)Math.pow(1/ageScale,0.66);
		//float offset = 1.4f - percent;
		GL11.glPushMatrix ();

		GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
		GL11.glScalef(ageHeadScale, ageHeadScale, ageHeadScale);
		GL11.glTranslatef (0.0F, 0,0.1875f-(0.1875f*percent));
		if(percent < 0.5)
		{
			horn1.isHidden = true;//rotateAngleX = (float)Math.PI;
			horn2.isHidden = true;//rotateAngleX = -(float)Math.PI;
			if(percent < 0.75)
			{
				horn1b.isHidden = true;
				horn2b.isHidden = true;
			}
		}

		if(((IAnimal)entity).getGender()==GenderEnum.MALE)
		{
			udders.isHidden = true;
		}
		else
		{
			horn1b.isHidden = true;
			horn2b.isHidden = true;
		}
		head.render(par7);
		GL11.glPopMatrix();
		GL11.glPushMatrix ();
		GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
		GL11.glScalef(1/ageScale, 1/ageScale, 1/ageScale);
		
		body.render(par7);			
		udders.render(par7);
		leg1.render(par7);
		leg2.render(par7);
		leg3.render(par7);
		leg4.render(par7);
		GL11.glPopMatrix();
	}

	/**
	 * Sets the models various rotation angles.
	 */
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
	{
		this.head.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.head.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.body.rotateAngleX = (float) Math.PI / 2F;
		this.udders.rotateAngleX = (float) Math.PI / 2F;
		this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		horn1.rotateAngleX = 0F;
		horn2.rotateAngleX = 0F;
		horn1.isHidden = false;
		horn1b.isHidden = false;
		horn2.isHidden = false;
		horn2b.isHidden = false;
		udders.isHidden = false;
	}
}
