
package TFC.Render;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelUngulate extends ModelBaseTFC
{
  //fields
    ModelRenderer Body1;
    ModelRenderer Neck;
    ModelRenderer Haunch;
    ModelRenderer UnderSide;
    ModelRenderer Throat;
    ModelRenderer Spine;
    ModelRenderer LeftThigh;
    ModelRenderer RightThing;
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
      Body1.addBox(-3F, -3F, -6F, 6, 8, 12);
      Body1.setRotationPoint(0F, 6F, 0F);
      Body1.setTextureSize(64, 32);
      Body1.mirror = true;
      setRotation(Body1, -0.1047198F, 0F, 0F);
      Neck = new ModelRenderer(this, 0, 0);
      Neck.addBox(-2F, -1F, -11F, 4, 5, 6);
      Neck.setRotationPoint(0F, 6F, 0F);
      Neck.setTextureSize(64, 32);
      Neck.mirror = true;
      setRotation(Neck, -0.3490659F, 0F, 0F);
      Haunch = new ModelRenderer(this, 0, 0);
      Haunch.addBox(-3.5F, -3F, 0F, 7, 8, 9);
      Haunch.setRotationPoint(0F, 6F, 0F);
      Haunch.setTextureSize(64, 32);
      Haunch.mirror = true;
      setRotation(Haunch, 0.0872665F, 0F, 0F);
      UnderSide = new ModelRenderer(this, 0, 0);
      UnderSide.addBox(-3F, 1.5F, 2F, 6, 6, 4);
      UnderSide.setRotationPoint(0F, 6F, 0F);
      UnderSide.setTextureSize(64, 32);
      UnderSide.mirror = true;
      setRotation(UnderSide, 0.5235988F, 0F, 0F);
      Throat = new ModelRenderer(this, 0, 0);
      Throat.addBox(-1.5F, 5F, -10F, 3, 2, 8);
      Throat.setRotationPoint(0F, 6F, 0F);
      Throat.setTextureSize(64, 32);
      Throat.mirror = true;
      setRotation(Throat, -0.6108652F, 0F, 0F);
      Spine = new ModelRenderer(this, 0, 0);
      Spine.addBox(-1.5F, -4F, -9F, 3, 1, 18);
      Spine.setRotationPoint(0F, 6F, 0F);
      Spine.setTextureSize(64, 32);
      Spine.mirror = true;
      setRotation(Spine, -0.0349066F, 0F, 0F);
      LeftThigh = new ModelRenderer(this, 0, 0);
      LeftThigh.addBox(-1F, -2F, -3F, 2, 7, 4);
      LeftThigh.setRotationPoint(3F, 6F, 6F);
      LeftThigh.setTextureSize(64, 32);
      LeftThigh.mirror = true;
      setRotation(LeftThigh, -0.0174533F, 0F, -0.1745329F);
      RightThing = new ModelRenderer(this, 0, 0);
      RightThing.addBox(-1F, -2F, -3F, 2, 7, 4);
      RightThing.setRotationPoint(-3F, 6F, 6F);
      RightThing.setTextureSize(64, 32);
      RightThing.mirror = true;
      setRotation(RightThing, -0.0174533F, 0F, 0.1745329F);
      LeftCalf = new ModelRenderer(this, 0, 0);
      LeftCalf.addBox(-0.1F, 0F, 0F, 2, 4, 3);
      LeftCalf.setRotationPoint(3F, 10F, 3F);
      LeftCalf.setTextureSize(64, 32);
      LeftCalf.mirror = true;
      setRotation(LeftCalf, 0.2617994F, 0F, 0F);
      RightCalf = new ModelRenderer(this, 0, 0);
      RightCalf.addBox(0.1F, 0F, 0F, 2, 4, 3);
      RightCalf.setRotationPoint(-5F, 10F, 3F);
      RightCalf.setTextureSize(64, 32);
      RightCalf.mirror = true;
      setRotation(RightCalf, 0.2617994F, 0F, 0F);
      LeftAnkle = new ModelRenderer(this, 0, 0);
      LeftAnkle.addBox(-0.3F, 2F, 0.2F, 2, 4, 2);
      LeftAnkle.setRotationPoint(3F, 10F, 3F);
      LeftAnkle.setTextureSize(64, 32);
      LeftAnkle.mirror = true;
      setRotation(LeftAnkle, 0.2617994F, 0F, 0F);
      RightAnkle = new ModelRenderer(this, 0, 0);
      RightAnkle.addBox(-1.7F, 2F, 0.2F, 2, 4, 2);
      RightAnkle.setRotationPoint(-3F, 10F, 3F);
      RightAnkle.setTextureSize(64, 32);
      RightAnkle.mirror = true;
      setRotation(RightAnkle, 0.2617994F, 0F, 0F);
      LeftFoot = new ModelRenderer(this, 0, 0);
      LeftFoot.addBox(-0.3F, -0.2F, -0.3F, 2, 3, 2);
      LeftFoot.setRotationPoint(3F, 15F, 5F);
      LeftFoot.setTextureSize(64, 32);
      LeftFoot.mirror = true;
      setRotation(LeftFoot, -0.2617994F, 0F, 0F);
      RightFoot = new ModelRenderer(this, 0, 0);
      RightFoot.addBox(-1.7F, -0.2F, -0.3F, 2, 3, 2);
      RightFoot.setRotationPoint(-3F, 15F, 5F);
      RightFoot.setTextureSize(64, 32);
      RightFoot.mirror = true;
      setRotation(RightFoot, -0.2617994F, 0F, 0F);
      LeftHoofBack = new ModelRenderer(this, 0, 0);
      LeftHoofBack.addBox(-0.3F, 0.5F, -0.5F, 2, 1, 2);
      LeftHoofBack.setRotationPoint(3F, 17F, 4F);
      LeftHoofBack.setTextureSize(64, 32);
      LeftHoofBack.mirror = true;
      setRotation(LeftHoofBack, 0F, 0F, 0F);
      RightHoofBack = new ModelRenderer(this, 0, 0);
      RightHoofBack.addBox(-1.7F, 0.5F, -0.5F, 2, 1, 2);
      RightHoofBack.setRotationPoint(-3F, 17F, 4F);
      RightHoofBack.setTextureSize(64, 32);
      RightHoofBack.mirror = true;
      setRotation(RightHoofBack, 0F, 0F, 0F);
      LeftShoulder = new ModelRenderer(this, 0, 0);
      LeftShoulder.addBox(-1.5F, -1F, -2F, 2, 6, 3);
      LeftShoulder.setRotationPoint(3F, 6F, -5F);
      LeftShoulder.setTextureSize(64, 32);
      LeftShoulder.mirror = true;
      setRotation(LeftShoulder, 0.3490659F, 0F, -0.1047198F);
      RightShoulder = new ModelRenderer(this, 0, 0);
      RightShoulder.addBox(-0.5F, -1F, -2F, 2, 6, 3);
      RightShoulder.setRotationPoint(-3F, 6F, -5F);
      RightShoulder.setTextureSize(64, 32);
      RightShoulder.mirror = true;
      setRotation(RightShoulder, 0.3490659F, 0F, 0.1047198F);
      LeftArm = new ModelRenderer(this, 0, 0);
      LeftArm.addBox(-1F, 1.5F, -1F, 2, 4, 2);
      LeftArm.setRotationPoint(3F, 9F, -4F);
      LeftArm.setTextureSize(64, 32);
      LeftArm.mirror = true;
      setRotation(LeftArm, 0F, 0F, 0F);
      RightArm = new ModelRenderer(this, 0, 0);
      RightArm.addBox(-1F, 1.5F, -1F, 2, 4, 2);
      RightArm.setRotationPoint(-3F, 9F, -4F);
      RightArm.setTextureSize(64, 32);
      RightArm.mirror = true;
      setRotation(RightArm, 0F, 0F, 0F);
      LeftWrist = new ModelRenderer(this, 0, 0);
      LeftWrist.addBox(-1F, 0.5F, 0F, 2, 3, 2);
      LeftWrist.setRotationPoint(3F, 14F, -5F);
      LeftWrist.setTextureSize(64, 32);
      LeftWrist.mirror = true;
      setRotation(LeftWrist, 0F, 0F, 0F);
      RightWrist = new ModelRenderer(this, 0, 0);
      RightWrist.addBox(-1F, 0.5F, 0F, 2, 3, 2);
      RightWrist.setRotationPoint(-3F, 14F, -5F);
      RightWrist.setTextureSize(64, 32);
      RightWrist.mirror = true;
      setRotation(RightWrist, 0F, 0F, 0F);
      RightHoofFront = new ModelRenderer(this, 0, 0);
      RightHoofFront.addBox(-1F, 3F, -0.5F, 2, 1, 2);
      RightHoofFront.setRotationPoint(3F, 14F, -5F);
      RightHoofFront.setTextureSize(64, 32);
      RightHoofFront.mirror = true;
      setRotation(RightHoofFront, 0F, 0F, 0F);
      LeftHoofFront = new ModelRenderer(this, 0, 0);
      LeftHoofFront.addBox(-1F, 0.5F, -0.5F, 2, 1, 2);
      LeftHoofFront.setRotationPoint(-3F, 17F, -5F);
      LeftHoofFront.setTextureSize(64, 32);
      LeftHoofFront.mirror = true;
      setRotation(LeftHoofFront, 0F, 0F, 0F);
  }
  
  @Override
public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Body1.render(f5);
    Neck.render(f5);
    Haunch.render(f5);
    UnderSide.render(f5);
    Throat.render(f5);
    Spine.render(f5);
    LeftThigh.render(f5);
    RightThing.render(f5);
    LeftCalf.render(f5);
    RightCalf.render(f5);
    LeftAnkle.render(f5);
    RightAnkle.render(f5);
    LeftFoot.render(f5);
    RightFoot.render(f5);
    LeftHoofBack.render(f5);
    RightHoofBack.render(f5);
    LeftShoulder.render(f5);
    RightShoulder.render(f5);
    LeftArm.render(f5);
    RightArm.render(f5);
    LeftWrist.render(f5);
    RightWrist.render(f5);
    LeftHoofFront.render(f5);
    LeftHoofFront.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    
  }

}
