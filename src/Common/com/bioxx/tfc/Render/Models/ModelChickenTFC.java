package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;

public class ModelChickenTFC extends ModelBase
{
	public ModelRenderer head;
	public ModelRenderer body;
	public ModelRenderer rightLeg;
	public ModelRenderer leftLeg;
	public ModelRenderer rightWing;
	public ModelRenderer leftWing;
	public ModelRenderer bill;
	public ModelRenderer chin;
	public ModelRenderer[] tails;
	public ModelRenderer crown;

	public ModelChickenTFC()
	{
		byte var1 = 16;
		this.head = new ModelRenderer(this, 0, 0);
		this.head.addBox(-2.0F, -6.0F, -2.0F, 4, 6, 3, 0.0F);
		this.head.setRotationPoint(0.0F, -1 + var1, -4.0F);
		this.bill = new ModelRenderer(this, 14, 0);
		this.bill.addBox(-2.0F, -4.0F, -4.0F, 4, 2, 2, 0.0F);
		this.bill.setRotationPoint(0.0F, -1 + var1, -4.0F);
		this.chin = new ModelRenderer(this, 14, 4);
		this.chin.addBox(-1.0F, -2.0F, -3.0F, 2, 2, 2, 0.0F);
		this.chin.setRotationPoint(0.0F, -1 + var1, -4.0F);
		this.body = new ModelRenderer(this, 0, 9);
		this.body.addBox(-3.0F, -4.0F, -3.0F, 6, 8, 6, 0.0F);
		this.body.setRotationPoint(0.0F, var1, 0.0F);
		this.rightLeg = new ModelRenderer(this, 26, 0);
		this.rightLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		this.rightLeg.setRotationPoint(-2.0F, 3 + var1, 1.0F);
		this.leftLeg = new ModelRenderer(this, 26, 0);
		this.leftLeg.addBox(-1.0F, 0.0F, -3.0F, 3, 5, 3);
		this.leftLeg.setRotationPoint(1.0F, 3 + var1, 1.0F);
		this.rightWing = new ModelRenderer(this, 24, 13);
		this.rightWing.addBox(0.0F, 0.0F, -3.0F, 1, 4, 6);
		this.rightWing.setRotationPoint(-4.0F, -3 + var1, 0.0F);
		this.leftWing = new ModelRenderer(this, 24, 13);
		this.leftWing.addBox(-1.0F, 0.0F, -3.0F, 1, 4, 6);
		this.leftWing.setRotationPoint(4.0F, -3 + var1, 0.0F);

		crown = new ModelRenderer(this,0,23);
		crown.addBox(0, -7, -3, 0, 4, 5);
		crown.setRotationPoint(0.0F, -3 + var1, -1.0F);


		tails = new ModelRenderer[32];
		for(int i = 0; i <32;i++){
			tails[i] = new ModelRenderer(this,45,0);
			tails[i].addBox(0,16,0,3,21,0,0);
			tails[i].setRotationPoint(0, 32, 2);
		}
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7);
		float percent = Math.max(TFC_Core.getPercentGrown((IAnimal)entity),0);
		float ageScale = 2.0F-percent;
		//float offset = 1.4f - percent;

		GL11.glPushMatrix ();

		GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
		GL11.glScalef(1/ageScale, 1/ageScale, 1/ageScale);

		if(percent >= 0.75 && ((IAnimal)entity).getGender() == GenderEnum.MALE){
			crown.isHidden = false;
			this.body.rotateAngleX = (float) Math.PI / 4F;
			this.rightWing.rotateAngleX = (float) Math.PI / 4F;
			rightWing.setRotationPoint(-4.0F,13.5f, -2.5F);
			this.leftWing.rotateAngleX = (float) Math.PI / 4F;
			leftWing.setRotationPoint(4.0F,13.5f, -2.5F);
			this.head.setRotationPoint(0.0F, 13, -1.0F);
			this.bill.setRotationPoint(0.0F, 13, -1.0F);
			this.chin.setRotationPoint(0.0F, 13, -1.0F);
		}
		this.head.render(par7);
		this.bill.render(par7);
		this.chin.render(par7);

		GL11.glPushMatrix();
		GL11.glScalef(0.75f, 0.75f, 0.75f);
		this.crown.render(par7);
		GL11.glPopMatrix();
		GL11.glPopMatrix();

		GL11.glPushMatrix();
		GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
		GL11.glScalef(1/ageScale, 1/ageScale, 1/ageScale);
		this.body.render(par7);
		this.rightLeg.render(par7);
		this.leftLeg.render(par7);
		this.rightWing.render(par7);
		this.leftWing.render(par7);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(0.25F / percent, 0.5F / percent, 0.25F / percent);
		for(int i = 0;i<32;i++){
			tails[i].render(par7);
		}
		GL11.glPopMatrix();
	}

	/**
	 * Sets the models various rotation angles.
	 */
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
	{
		this.head.rotateAngleX = -(par5 / (180F / (float)Math.PI));
		this.head.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.crown.rotateAngleX = -(par5 / (180F / (float)Math.PI));
		this.crown.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.bill.rotateAngleX = this.head.rotateAngleX;
		this.bill.rotateAngleY = this.head.rotateAngleY;
		this.chin.rotateAngleX = this.head.rotateAngleX;
		this.chin.rotateAngleY = this.head.rotateAngleY;
		this.body.rotateAngleX = (float) Math.PI / 2F;
		/*this.tail1.rotateAngleX = ((float)Math.PI*3 / 4F);
        this.tail1.rotateAngleZ = ((float)Math.PI / 8F);
        this.tail2.rotateAngleX = ((float)Math.PI*3 / 4F);
        this.tail2.rotateAngleZ = ((float)Math.PI / -8F);
        this.tail3.rotateAngleX = ((float)Math.PI*3 / 4F);
        this.tail3.rotateAngleZ = ((float)Math.PI / 12F);
        this.tail4.rotateAngleX = ((float)Math.PI*3 / 4F);
        this.tail4.rotateAngleZ = ((float)Math.PI / -12F);*/
		crown.isHidden = true;

		for(int i = 0;i<32;i++){
			tails[i].rotateAngleX = (float) Math.PI * 3 / 5F + (float) ((i % 3) * Math.PI / 32) * (i % 2 != 0 ? 1 : -1);
			tails[i].rotateAngleZ = (float) Math.PI / -2 + (float) Math.PI * i / 31;
		}
		this.rightLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.leftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.rightWing.rotateAngleZ = par3;
		this.leftWing.rotateAngleZ = -par3;
		this.rightWing.rotateAngleX = 0;//((float)Math.PI / 4F);
		this.leftWing.rotateAngleX = 0;//((float)Math.PI / 4F);
		this.rightWing.setRotationPoint(-4.0F, 13, 0.0F);
		this.leftWing.setRotationPoint(4.0F, 13, 0.0F);
	}
}
