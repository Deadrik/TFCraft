
package TFC.Render.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

public class ModelUngulate extends ModelBase
{
	//fields
	ModelRenderer Body1;
	ModelRenderer Neck;
	ModelRenderer Haunch;
	ModelRenderer UnderSide;
	ModelRenderer Throat;
	ModelRenderer Spine;
	ModelRenderer LeftThigh;
	ModelRenderer RightThigh;
	ModelRenderer LeftCalf;
	ModelRenderer RightCalf;
	ModelRenderer LeftAnkle;
	ModelRenderer RightAnkle;
	ModelRenderer LeftFoot;
	ModelRenderer RightFoot;
	ModelRenderer LeftHoofBack;
	ModelRenderer RightHoofBack;
	ModelRenderer LeftShoulder;
	ModelRenderer RightShoulder;
	ModelRenderer LeftArm;
	ModelRenderer RightArm;
	ModelRenderer LeftWrist;
	ModelRenderer RightWrist;
	ModelRenderer RightHoofFront;
	ModelRenderer LeftHoofFront;

	public ModelUngulate()
	{
		textureWidth = 64;
		textureHeight = 32;

		Body1 = new ModelRenderer(this, 0, 0);
		Body1.addBox(-3F, -3F, -6F, 6, 8, 16);
		Body1.setRotationPoint(0F, 7F, 0F);
		Body1.setTextureSize(64, 32);
		Body1.mirror = true;
		setRotation(Body1, -0.1047198F, 0F, 0F);
		Neck = new ModelRenderer(this, 0, 0);
		Neck.addBox(-2F, -1F, -11F, 4, 5, 6);
		Neck.setRotationPoint(0F, 0F, 0F);
		Neck.setTextureSize(64, 32);
		Neck.mirror = true;
		setRotation(Neck, -0.3490659F, 0F, 0F);
		Haunch = new ModelRenderer(this, 0, 0);
		Haunch.addBox(-3.5F, -3F, 0F, 7, 8, 9);
		Haunch.setRotationPoint(0F, 0F, 4F);
		Haunch.setTextureSize(64, 32);
		Haunch.mirror = true;
		setRotation(Haunch, 0.0872665F, 0F, 0F);
		UnderSide = new ModelRenderer(this, 0, 0);
		UnderSide.addBox(-3F, 1.5F, 2F, 6, 6, 4);
		UnderSide.setRotationPoint(0F, 0F, 0F);
		UnderSide.setTextureSize(64, 32);
		UnderSide.mirror = true;
		setRotation(UnderSide, 0.5235988F, 0F, 0F);
		Throat = new ModelRenderer(this, 0, 0);
		Throat.addBox(-1.5F, 5F, -10F, 3, 2, 8);
		Throat.setRotationPoint(0F, 0F, 0F);
		Throat.setTextureSize(64, 32);
		Throat.mirror = true;
		setRotation(Throat, -0.6108652F, 0F, 0F);
		Spine = new ModelRenderer(this, 0, 0);
		Spine.addBox(-1.5F, -4F, -9F, 3, 1, 22);
		Spine.setRotationPoint(0F, 0F, 0F);
		Spine.setTextureSize(64, 32);
		Spine.mirror = true;
		setRotation(Spine, -0.0349066F, 0F, 0F);
		LeftThigh = new ModelRenderer(this, 0, 0);
		LeftThigh.addBox(-1F, -2F, -4F, 2, 7, 4);
		LeftThigh.setRotationPoint(3F, 2F, 8F);
		LeftThigh.setTextureSize(64, 32);
		LeftThigh.mirror = true;
		setRotation(LeftThigh, -0.52359877559829887307710723054658F, 0F, 0F);
		RightThigh = new ModelRenderer(this, 0, 0);
		RightThigh.addBox(-1F, -2F, -4F, 2, 7, 4);
		RightThigh.setRotationPoint(-3F, 2F, 8F);
		RightThigh.setTextureSize(64, 32);
		RightThigh.mirror = true;
		setRotation(RightThigh, -0.52359877559829887307710723054658F, 0F, 0F);
		LeftCalf = new ModelRenderer(this, 0, 0);
		LeftCalf.addBox(-0.6F, 0F, 0F, 2, 4, 3);
		LeftCalf.setRotationPoint(0F, 5F, -3F);
		LeftCalf.setTextureSize(64, 32);
		LeftCalf.mirror = true;
		setRotation(LeftCalf, (3*0.2617994F)+0.52359877559829887307710723054658F, 0F, 0F);
		RightCalf = new ModelRenderer(this, 0, 0);
		RightCalf.addBox(-1.3F, 0F, 0F, 2, 4, 3);
		RightCalf.setRotationPoint(0F, 5F, -3F);
		RightCalf.setTextureSize(64, 32);
		RightCalf.mirror = true;
		setRotation(RightCalf, (3*0.2617994F)+0.52359877559829887307710723054658F, 0F, 0F);
		LeftAnkle = new ModelRenderer(this, 0, 0);
		LeftAnkle.addBox(-0.6F, 2F, 0.2F, 2, 5, 2);
		LeftAnkle.setRotationPoint(0F, 0F, 0F);
		LeftAnkle.setTextureSize(64, 32);
		LeftAnkle.mirror = true;
		setRotation(LeftAnkle, 0F, 0F, 0F);
		RightAnkle = new ModelRenderer(this, 0, 0);
		RightAnkle.addBox(-1.3F, 2F, 0.2F, 2, 5, 2);
		RightAnkle.setRotationPoint(0F, 0F, 0F);
		RightAnkle.setTextureSize(64, 32);
		RightAnkle.mirror = true;
		setRotation(RightAnkle, 0F, 0F, 0F);
		LeftFoot = new ModelRenderer(this, 0, 0);
		LeftFoot.addBox(-0.6F, 0.8F, -0.3F, 2, 6, 2);
		LeftFoot.setRotationPoint(0F, 5F, 1.5F);
		LeftFoot.setTextureSize(64, 32);
		LeftFoot.mirror = true;
		setRotation(LeftFoot, 3*-0.2617994F, 0F, 0F);
		RightFoot = new ModelRenderer(this, 0, 0);
		RightFoot.addBox(-1.3F, 0.8F, -0.3F, 2, 6, 2);
		RightFoot.setRotationPoint(0F, 5F, 1.5F);
		RightFoot.setTextureSize(64, 32);
		RightFoot.mirror = true;
		setRotation(RightFoot, 3*-0.2617994F, 0F, 0F);
		LeftHoofBack = new ModelRenderer(this, 0, 0);
		LeftHoofBack.addBox(-0.6F, 0.5F, -0.5F, 2, 1, 2);
		LeftHoofBack.setRotationPoint(0F, 6F, -0.5F);
		LeftHoofBack.setTextureSize(64, 32);
		LeftHoofBack.mirror = true;
		setRotation(LeftHoofBack, 0F, 0F, 0F);
		RightHoofBack = new ModelRenderer(this, 0, 0);
		RightHoofBack.addBox(-1.3F, 0.5F, -0.5F, 2, 1, 2);
		RightHoofBack.setRotationPoint(0F, 6F, -0.5F);
		RightHoofBack.setTextureSize(64, 32);
		RightHoofBack.mirror = true;
		setRotation(RightHoofBack, 0F, 0F, 0F);
		LeftShoulder = new ModelRenderer(this, 0, 0);
		LeftShoulder.addBox(-1.5F, -1F, -2F, 2, 5, 3);
		LeftShoulder.setRotationPoint(3F, 3F, -4F);
		LeftShoulder.setTextureSize(64, 32);
		LeftShoulder.mirror = true;
		setRotation(LeftShoulder, 2*0.52359877559829887307710723054658F+0.1047198F, 0F, 0F);
		RightShoulder = new ModelRenderer(this, 0, 0);
		RightShoulder.addBox(-0.5F, -1F, -2F, 2, 5, 3);
		RightShoulder.setRotationPoint(-3F, 3F, -4F);
		RightShoulder.setTextureSize(64, 32);
		RightShoulder.mirror = true;
		setRotation(RightShoulder, 2*0.52359877559829887307710723054658F+0.1047198F, 0F, 0F);
		LeftArm = new ModelRenderer(this, 0, 0);
		LeftArm.addBox(-1F, 0.8F, 0F, 2, 5, 2);
		LeftArm.setRotationPoint(-0.4F, 2F, 0F);
		LeftArm.setTextureSize(64, 32);
		LeftArm.mirror = true;
		setRotation(LeftArm, -2*0.52359877559829887307710723054658F, 0F, 0F);
		RightArm = new ModelRenderer(this, 0, 0);
		RightArm.addBox(-1F, 0.8F, 0F, 2, 5, 2);
		RightArm.setRotationPoint(0.4F, 2F, 0F);
		RightArm.setTextureSize(64, 32);
		RightArm.mirror = true;
		setRotation(RightArm, -2*0.52359877559829887307710723054658F, 0F, 0F);
		LeftWrist = new ModelRenderer(this, 0, 0);
		LeftWrist.addBox(-1F, 0.5F, 0F, 2, 5, 2);
		LeftWrist.setRotationPoint(0F, 5F, 0F);
		LeftWrist.setTextureSize(64, 32);
		LeftWrist.mirror = true;
		setRotation(LeftWrist, 0F, 0F, 0F);
		RightWrist = new ModelRenderer(this, 0, 0);
		RightWrist.addBox(-1F, 0.5F, 0F, 2, 5, 2);
		RightWrist.setRotationPoint(0F, 5F, 0F);
		RightWrist.setTextureSize(64, 32);
		RightWrist.mirror = true;
		setRotation(RightWrist, 0F, 0F, 0F);
		LeftHoofFront = new ModelRenderer(this, 0, 0);
		LeftHoofFront.addBox(-1F, 0.5F, -0.5F, 2, 1, 2);
		LeftHoofFront.setRotationPoint(0F, 5F, 0F);
		LeftHoofFront.setTextureSize(64, 32);
		LeftHoofFront.mirror = true;
		setRotation(LeftHoofFront, 0F, 0F, 0F);
		RightHoofFront = new ModelRenderer(this, 0, 0);
		RightHoofFront.addBox(-1F, 0.5F, -0.5F, 2, 1, 2);
		RightHoofFront.setRotationPoint(0F, 5F, 0F);
		RightHoofFront.setTextureSize(64, 32);
		RightHoofFront.mirror = true;
		setRotation(RightHoofFront, 0F, 0F, 0F);

		Body1.addChild(Neck);
		Neck.addChild(Throat);
		Body1.addChild(Haunch);
		//Body1.addChild(Spine);
		Body1.addChild(LeftShoulder);
		Body1.addChild(RightShoulder);
		LeftShoulder.addChild(LeftArm);
		LeftArm.addChild(LeftWrist);
		LeftWrist.addChild(LeftHoofFront);
		RightShoulder.addChild(RightArm);
		RightArm.addChild(RightWrist);
		RightWrist.addChild(RightHoofFront);
		Body1.addChild(UnderSide);
		Haunch.addChild(LeftThigh);
		Haunch.addChild(RightThigh);
		LeftThigh.addChild(LeftCalf);
		RightThigh.addChild(RightCalf);
		LeftCalf.addChild(LeftAnkle);
		RightCalf.addChild(RightAnkle);
		LeftAnkle.addChild(LeftFoot);
		RightAnkle.addChild(RightFoot);
		LeftFoot.addChild(LeftHoofBack);
		RightFoot.addChild(RightHoofBack);
	}

	@Override
	public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
	{
		super.render(entity, f, f1, f2, f3, f4, f5);
		setRotationAngles(f, f1, f2, f3, f4, f5);
		Body1.render(f5);
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
		float FRL = par1 + (float)(Math.PI*0.75F);
		float FLL = par1 + (float)(Math.PI*2.25F);
		float BRL = par1;
		float BLL = par1;
		float speedMod = Math.min(Math.abs(Math.max(Math.abs(par2),0.001F)),1F)*(par2!=0?par2/Math.abs(par2):1);
		float rightArmInitAngle =  -2*0.52359877559829887307710723054658F;
		float leftArmInitAngle =  -2*0.52359877559829887307710723054658F;
		float leftThighInitAngle = -0.52359877559829887307710723054658F;
		float rightThighInitAngle = -0.52359877559829887307710723054658F;
		float rightCalfInitAngle = (3*0.2617994F)+0.52359877559829887307710723054658F;
		float leftCalfInitAngle = (3*0.2617994F)+0.52359877559829887307710723054658F;
		float rightFootInitAngle = 3*-0.2617994F;
		float leftFootInitAngle = 3*-0.2617994F;
		RightWrist.rotateAngleX = Math.max(0,-MathHelper.cos(FRL*0.6662F)*1.8F*speedMod);
		//LeftFoot.rotateAngleX = Math.max(0,-MathHelper.cos(FRL*0.6662F)*1.8F*speedMod)+leftFootInitAngle;
		LeftWrist.rotateAngleX = Math.max(0,-MathHelper.cos(FLL*0.6662F)*1.8F*speedMod);
		//RightFoot.rotateAngleX = Math.max(0,-MathHelper.cos(FLL*0.6662F)*1.8F*speedMod)+rightFootInitAngle;
		//RightArm.rotateAngleX = MathHelper.sin(FRL*0.6662F)*1.4F*par2 + rightArmInitAngle;
		RightShoulder.rotateAngleX = MathHelper.sin(FRL*0.6662F)*1.4F*speedMod - rightArmInitAngle;
		LeftShoulder.rotateAngleX = MathHelper.sin(FLL*0.6662F)*1.4F*speedMod - leftArmInitAngle;
		if(MathHelper.cos(FRL*0.6662F+(float)Math.PI/2F)*1.4F*speedMod > 0){
			RightArm.rotateAngleX = MathHelper.cos(FRL*0.6662F+(float)Math.PI/2F)*1.4F*speedMod + rightArmInitAngle;
			LeftCalf.rotateAngleX = MathHelper.cos(FRL*0.6662F+(float)Math.PI/2F)*0.7F*(MathHelper.sin(FLL*0.6662F)*speedMod>0?1.5F:1)*speedMod + leftCalfInitAngle;
		}
		if(MathHelper.cos(FLL*0.6662F + (float)Math.PI/2F)*1.4F*speedMod  > 0){
			LeftArm.rotateAngleX = MathHelper.cos(FLL*0.6662F+(float)Math.PI/2F)*1.4F*speedMod + leftArmInitAngle;
			RightCalf.rotateAngleX = MathHelper.cos(FLL*0.6662F+(float)Math.PI/2F)*0.7F*(MathHelper.sin(FRL*0.6662F)*speedMod>0?1.5F:1)*speedMod + rightCalfInitAngle;

		}
		RightThigh.rotateAngleX = MathHelper.sin(FLL*0.6662F)*0.7F*speedMod + rightThighInitAngle;
		RightFoot.rotateAngleX = MathHelper.sin(FLL*0.6662F)*0.7F*(MathHelper.sin(FRL*0.6662F)*speedMod>0?2:1)*speedMod + rightFootInitAngle;
		LeftThigh.rotateAngleX = MathHelper.sin(FRL*0.6662F)*0.7F*speedMod +leftThighInitAngle;
		LeftFoot.rotateAngleX = MathHelper.sin(FRL * 0.6662F)*0.7F*(MathHelper.sin(FLL*0.6662F)*speedMod>0?2:1)*speedMod + rightFootInitAngle;
		/*
	  else if(MathHelper.cos(FRL*0.6662F)*1.4F*par2 + rightArmInitAngle < RightArm.rotateAngleX){
		  //System.out.println("Check2");
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
