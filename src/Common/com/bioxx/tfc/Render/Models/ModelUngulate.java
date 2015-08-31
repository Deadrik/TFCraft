
package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelUngulate extends ModelBase
{
	//fields
	private ModelRenderer body1;
	private ModelRenderer neck;
	private ModelRenderer haunch;
	private ModelRenderer underSide;
	private ModelRenderer throat;
	private ModelRenderer spine;
	private ModelRenderer leftThigh;
	private ModelRenderer rightThigh;
	private ModelRenderer leftCalf;
	private ModelRenderer rightCalf;
	private ModelRenderer leftAnkle;
	private ModelRenderer rightAnkle;
	private ModelRenderer leftFoot;
	private ModelRenderer rightFoot;
	private ModelRenderer leftHoofBack;
	private ModelRenderer rightHoofBack;
	private ModelRenderer leftShoulder;
	private ModelRenderer rightShoulder;
	private ModelRenderer leftArm;
	private ModelRenderer rightArm;
	private ModelRenderer leftWrist;
	private ModelRenderer rightWrist;
	private ModelRenderer rightHoofFront;
	private ModelRenderer leftHoofFront;

	public ModelUngulate()
	{
		textureWidth = 64;
		textureHeight = 32;

		body1 = new ModelRenderer(this, 0, 0);
		body1.addBox(-3F, -3F, -6F, 6, 8, 16);
		body1.setRotationPoint(0F, 7F, 0F);
		body1.setTextureSize(64, 32);
		body1.mirror = true;
		setRotation(body1, -0.1047198F, 0F, 0F);
		neck = new ModelRenderer(this, 0, 0);
		neck.addBox(-2F, -1F, -11F, 4, 5, 6);
		neck.setRotationPoint(0F, 0F, 0F);
		neck.setTextureSize(64, 32);
		neck.mirror = true;
		setRotation(neck, -0.3490659F, 0F, 0F);
		haunch = new ModelRenderer(this, 0, 0);
		haunch.addBox(-3.5F, -3F, 0F, 7, 8, 9);
		haunch.setRotationPoint(0F, 0F, 4F);
		haunch.setTextureSize(64, 32);
		haunch.mirror = true;
		setRotation(haunch, 0.0872665F, 0F, 0F);
		underSide = new ModelRenderer(this, 0, 0);
		underSide.addBox(-3F, 1.5F, 2F, 6, 6, 4);
		underSide.setRotationPoint(0F, 0F, 0F);
		underSide.setTextureSize(64, 32);
		underSide.mirror = true;
		setRotation(underSide, 0.5235988F, 0F, 0F);
		throat = new ModelRenderer(this, 0, 0);
		throat.addBox(-1.5F, 5F, -10F, 3, 2, 8);
		throat.setRotationPoint(0F, 0F, 0F);
		throat.setTextureSize(64, 32);
		throat.mirror = true;
		setRotation(throat, -0.6108652F, 0F, 0F);
		spine = new ModelRenderer(this, 0, 0);
		spine.addBox(-1.5F, -4F, -9F, 3, 1, 22);
		spine.setRotationPoint(0F, 0F, 0F);
		spine.setTextureSize(64, 32);
		spine.mirror = true;
		setRotation(spine, -0.0349066F, 0F, 0F);
		leftThigh = new ModelRenderer(this, 0, 0);
		leftThigh.addBox(-1F, -2F, -4F, 2, 7, 4);
		leftThigh.setRotationPoint(3F, 2F, 8F);
		leftThigh.setTextureSize(64, 32);
		leftThigh.mirror = true;
		setRotation(leftThigh, -0.52359877559829887307710723054658F, 0F, 0F);
		rightThigh = new ModelRenderer(this, 0, 0);
		rightThigh.addBox(-1F, -2F, -4F, 2, 7, 4);
		rightThigh.setRotationPoint(-3F, 2F, 8F);
		rightThigh.setTextureSize(64, 32);
		rightThigh.mirror = true;
		setRotation(rightThigh, -0.52359877559829887307710723054658F, 0F, 0F);
		leftCalf = new ModelRenderer(this, 0, 0);
		leftCalf.addBox(-0.6F, 0F, 0F, 2, 4, 3);
		leftCalf.setRotationPoint(0F, 5F, -3F);
		leftCalf.setTextureSize(64, 32);
		leftCalf.mirror = true;
		setRotation(leftCalf, 3 * 0.2617994F + 0.52359877559829887307710723054658F, 0F, 0F);
		rightCalf = new ModelRenderer(this, 0, 0);
		rightCalf.addBox(-1.3F, 0F, 0F, 2, 4, 3);
		rightCalf.setRotationPoint(0F, 5F, -3F);
		rightCalf.setTextureSize(64, 32);
		rightCalf.mirror = true;
		setRotation(rightCalf, 3 * 0.2617994F + 0.52359877559829887307710723054658F, 0F, 0F);
		leftAnkle = new ModelRenderer(this, 0, 0);
		leftAnkle.addBox(-0.6F, 2F, 0.2F, 2, 5, 2);
		leftAnkle.setRotationPoint(0F, 0F, 0F);
		leftAnkle.setTextureSize(64, 32);
		leftAnkle.mirror = true;
		setRotation(leftAnkle, 0F, 0F, 0F);
		rightAnkle = new ModelRenderer(this, 0, 0);
		rightAnkle.addBox(-1.3F, 2F, 0.2F, 2, 5, 2);
		rightAnkle.setRotationPoint(0F, 0F, 0F);
		rightAnkle.setTextureSize(64, 32);
		rightAnkle.mirror = true;
		setRotation(rightAnkle, 0F, 0F, 0F);
		leftFoot = new ModelRenderer(this, 0, 0);
		leftFoot.addBox(-0.6F, 0.8F, -0.3F, 2, 6, 2);
		leftFoot.setRotationPoint(0F, 5F, 1.5F);
		leftFoot.setTextureSize(64, 32);
		leftFoot.mirror = true;
		setRotation(leftFoot, 3*-0.2617994F, 0F, 0F);
		rightFoot = new ModelRenderer(this, 0, 0);
		rightFoot.addBox(-1.3F, 0.8F, -0.3F, 2, 6, 2);
		rightFoot.setRotationPoint(0F, 5F, 1.5F);
		rightFoot.setTextureSize(64, 32);
		rightFoot.mirror = true;
		setRotation(rightFoot, 3*-0.2617994F, 0F, 0F);
		leftHoofBack = new ModelRenderer(this, 0, 0);
		leftHoofBack.addBox(-0.6F, 0.5F, -0.5F, 2, 1, 2);
		leftHoofBack.setRotationPoint(0F, 6F, -0.5F);
		leftHoofBack.setTextureSize(64, 32);
		leftHoofBack.mirror = true;
		setRotation(leftHoofBack, 0F, 0F, 0F);
		rightHoofBack = new ModelRenderer(this, 0, 0);
		rightHoofBack.addBox(-1.3F, 0.5F, -0.5F, 2, 1, 2);
		rightHoofBack.setRotationPoint(0F, 6F, -0.5F);
		rightHoofBack.setTextureSize(64, 32);
		rightHoofBack.mirror = true;
		setRotation(rightHoofBack, 0F, 0F, 0F);
		leftShoulder = new ModelRenderer(this, 0, 0);
		leftShoulder.addBox(-1.5F, -1F, -2F, 2, 5, 3);
		leftShoulder.setRotationPoint(3F, 3F, -4F);
		leftShoulder.setTextureSize(64, 32);
		leftShoulder.mirror = true;
		setRotation(leftShoulder, 2*0.52359877559829887307710723054658F+0.1047198F, 0F, 0F);
		rightShoulder = new ModelRenderer(this, 0, 0);
		rightShoulder.addBox(-0.5F, -1F, -2F, 2, 5, 3);
		rightShoulder.setRotationPoint(-3F, 3F, -4F);
		rightShoulder.setTextureSize(64, 32);
		rightShoulder.mirror = true;
		setRotation(rightShoulder, 2*0.52359877559829887307710723054658F+0.1047198F, 0F, 0F);
		leftArm = new ModelRenderer(this, 0, 0);
		leftArm.addBox(-1F, 0.8F, 0F, 2, 5, 2);
		leftArm.setRotationPoint(-0.4F, 2F, 0F);
		leftArm.setTextureSize(64, 32);
		leftArm.mirror = true;
		setRotation(leftArm, -2*0.52359877559829887307710723054658F, 0F, 0F);
		rightArm = new ModelRenderer(this, 0, 0);
		rightArm.addBox(-1F, 0.8F, 0F, 2, 5, 2);
		rightArm.setRotationPoint(0.4F, 2F, 0F);
		rightArm.setTextureSize(64, 32);
		rightArm.mirror = true;
		setRotation(rightArm, -2*0.52359877559829887307710723054658F, 0F, 0F);
		leftWrist = new ModelRenderer(this, 0, 0);
		leftWrist.addBox(-1F, 0.5F, 0F, 2, 5, 2);
		leftWrist.setRotationPoint(0F, 5F, 0F);
		leftWrist.setTextureSize(64, 32);
		leftWrist.mirror = true;
		setRotation(leftWrist, 0F, 0F, 0F);
		rightWrist = new ModelRenderer(this, 0, 0);
		rightWrist.addBox(-1F, 0.5F, 0F, 2, 5, 2);
		rightWrist.setRotationPoint(0F, 5F, 0F);
		rightWrist.setTextureSize(64, 32);
		rightWrist.mirror = true;
		setRotation(rightWrist, 0F, 0F, 0F);
		leftHoofFront = new ModelRenderer(this, 0, 0);
		leftHoofFront.addBox(-1F, 0.5F, -0.5F, 2, 1, 2);
		leftHoofFront.setRotationPoint(0F, 5F, 0F);
		leftHoofFront.setTextureSize(64, 32);
		leftHoofFront.mirror = true;
		setRotation(leftHoofFront, 0F, 0F, 0F);
		rightHoofFront = new ModelRenderer(this, 0, 0);
		rightHoofFront.addBox(-1F, 0.5F, -0.5F, 2, 1, 2);
		rightHoofFront.setRotationPoint(0F, 5F, 0F);
		rightHoofFront.setTextureSize(64, 32);
		rightHoofFront.mirror = true;
		setRotation(rightHoofFront, 0F, 0F, 0F);

		body1.addChild(neck);
		neck.addChild(throat);
		body1.addChild(haunch);
		//Body1.addChild(Spine);
		body1.addChild(leftShoulder);
		body1.addChild(rightShoulder);
		leftShoulder.addChild(leftArm);
		leftArm.addChild(leftWrist);
		leftWrist.addChild(leftHoofFront);
		rightShoulder.addChild(rightArm);
		rightArm.addChild(rightWrist);
		rightWrist.addChild(rightHoofFront);
		body1.addChild(underSide);
		haunch.addChild(leftThigh);
		haunch.addChild(rightThigh);
		leftThigh.addChild(leftCalf);
		rightThigh.addChild(rightCalf);
		leftCalf.addChild(leftAnkle);
		rightCalf.addChild(rightAnkle);
		leftAnkle.addChild(leftFoot);
		rightAnkle.addChild(rightFoot);
		leftFoot.addChild(leftHoofBack);
		rightFoot.addChild(rightHoofBack);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		body1.render(f5);
		//Neck.render(f5);
		//Haunch.render(f5);
		//UnderSide.render(f5);
		//Throat.render(f5);
		//Spine.render(f5);
		//LeftThigh.render(f5);
		//RightThigh.render(f5);
		//LeftCalf.render(f5);
		//RightCalf.render(f5);
		//LeftAnkle.render(f5);
		//RightAnkle.render(f5);
		//LeftFoot.render(f5);
		//RightFoot.render(f5);
		//LeftHoofBack.render(f5);
		//RightHoofBack.render(f5);
		//LeftShoulder.render(f5);
		//RightShoulder.render(f5);
		//LeftArm.render(f5);
		//RightArm.render(f5);
		//LeftWrist.render(f5);
		//RightWrist.render(f5);
		//LeftHoofFront.render(f5);
		//LeftHoofFront.render(f5);
	}

	private void setRotation(ModelRenderer model, float x, float y, float z)
	{
		model.rotateAngleX = x;
		model.rotateAngleY = y;
		model.rotateAngleZ = z;
	}

	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
	{
		float frontRightLeg = par1 + (float)(Math.PI*0.75F);
		float frontLeftLeg = par1 + (float)(Math.PI*2.25F);
		//float BRL = par1;
		//float BLL = par1;
		float speedMod = Math.min(Math.abs(Math.max(Math.abs(par2),0.001F)),1F)*(par2!=0?par2/Math.abs(par2):1);
		float rightArmInitAngle =  -2*0.52359877559829887307710723054658F;
		float leftArmInitAngle =  -2*0.52359877559829887307710723054658F;
		float leftThighInitAngle = -0.52359877559829887307710723054658F;
		float rightThighInitAngle = -0.52359877559829887307710723054658F;
		float rightCalfInitAngle = 3 * 0.2617994F + 0.52359877559829887307710723054658F;
		float leftCalfInitAngle = 3 * 0.2617994F + 0.52359877559829887307710723054658F;
		float rightFootInitAngle = 3*-0.2617994F;
		//float leftFootInitAngle = 3*-0.2617994F;
		rightWrist.rotateAngleX = Math.max(0,-MathHelper.cos(frontRightLeg*0.6662F)*1.8F*speedMod);
		//LeftFoot.rotateAngleX = Math.max(0,-MathHelper.cos(FRL*0.6662F)*1.8F*speedMod)+leftFootInitAngle;
		leftWrist.rotateAngleX = Math.max(0,-MathHelper.cos(frontLeftLeg*0.6662F)*1.8F*speedMod);
		//RightFoot.rotateAngleX = Math.max(0,-MathHelper.cos(FLL*0.6662F)*1.8F*speedMod)+rightFootInitAngle;
		//RightArm.rotateAngleX = MathHelper.sin(FRL*0.6662F)*1.4F*par2 + rightArmInitAngle;
		rightShoulder.rotateAngleX = MathHelper.sin(frontRightLeg*0.6662F)*1.4F*speedMod - rightArmInitAngle;
		leftShoulder.rotateAngleX = MathHelper.sin(frontLeftLeg*0.6662F)*1.4F*speedMod - leftArmInitAngle;
		if(MathHelper.cos(frontRightLeg*0.6662F+(float)Math.PI/2F)*1.4F*speedMod > 0){
			rightArm.rotateAngleX = MathHelper.cos(frontRightLeg*0.6662F+(float)Math.PI/2F)*1.4F*speedMod + rightArmInitAngle;
			leftCalf.rotateAngleX = MathHelper.cos(frontRightLeg*0.6662F+(float)Math.PI/2F)*0.7F*(MathHelper.sin(frontLeftLeg*0.6662F)*speedMod>0?1.5F:1)*speedMod + leftCalfInitAngle;
		}
		if(MathHelper.cos(frontLeftLeg*0.6662F + (float)Math.PI/2F)*1.4F*speedMod  > 0){
			leftArm.rotateAngleX = MathHelper.cos(frontLeftLeg*0.6662F+(float)Math.PI/2F)*1.4F*speedMod + leftArmInitAngle;
			rightCalf.rotateAngleX = MathHelper.cos(frontLeftLeg*0.6662F+(float)Math.PI/2F)*0.7F*(MathHelper.sin(frontRightLeg*0.6662F)*speedMod>0?1.5F:1)*speedMod + rightCalfInitAngle;

		}
		rightThigh.rotateAngleX = MathHelper.sin(frontLeftLeg*0.6662F)*0.7F*speedMod + rightThighInitAngle;
		rightFoot.rotateAngleX = MathHelper.sin(frontLeftLeg*0.6662F)*0.7F*(MathHelper.sin(frontRightLeg*0.6662F)*speedMod>0?2:1)*speedMod + rightFootInitAngle;
		leftThigh.rotateAngleX = MathHelper.sin(frontRightLeg*0.6662F)*0.7F*speedMod +leftThighInitAngle;
		leftFoot.rotateAngleX = MathHelper.sin(frontRightLeg * 0.6662F)*0.7F*(MathHelper.sin(frontLeftLeg*0.6662F)*speedMod>0?2:1)*speedMod + rightFootInitAngle;
		/*
	  else if(MathHelper.cos(FRL*0.6662F)*1.4F*par2 + rightArmInitAngle < RightArm.rotateAngleX){
		  //TerraFirmaCraft.log.info("Check2");
		  RightArm.rotateAngleX = MathHelper.cos(FRL*0.6662F)*1.4F*par2+ rightArmInitAngle;
	  }
	  if(2*MathHelper.cos(FLL*0.6662F)*1.4F*par2 + leftArmInitAngle >= LeftArm.rotateAngleX){ 

		  if(MathHelper.cos(FLL*0.6662F)*1.4F*par2 <= 0)
			  LeftArm.rotateAngleX = 2*MathHelper.cos(FLL*0.6662F)*1.4F*par2 + leftArmInitAngle;

	  }
	  else if(MathHelper.cos(FLL*0.6662F)*1.4F*par2 + leftArmInitAngle > LeftArm.rotateAngleX)
		  LeftArm.rotateAngleX = MathHelper.cos(FLL*0.6662F)*1.4F*par2 + leftArmInitAngle;
		 */
		/*
      this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
      this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
      this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
      this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;*/
	}

}
