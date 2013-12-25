package TFC.Render.Models;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
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
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        //this.bipedHead.rotateAngleY = par4 / (180F / (float)Math.PI);
		float yaw = (float) -(((180 + par7Entity.rotationYaw)/180)*Math.PI);
		
        //this.bipedHead.rotateAngleX = par5 / (180F / (float)Math.PI);
        this.bipedHeadwear.rotateAngleY = this.bipedHead.rotateAngleY;
        this.bipedHeadwear.rotateAngleX = this.bipedHead.rotateAngleX;
        this.bipedRightArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 2.0F * par2 * 0.5F)+0.001F;
        this.bipedLeftArm.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F)+0.001F;
        this.bipedRightArm.rotateAngleZ = 0.00F;
        this.bipedLeftArm.rotateAngleZ = 0.00F;
        this.bipedRightLeg.rotateAngleX = (MathHelper.cos(par1 * 0.6662F) * 1.4F * par2)+0.001F;
        this.bipedLeftLeg.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
        this.bipedRightLeg.rotateAngleY = 0.0F;
        this.bipedLeftLeg.rotateAngleY = 0.0F;

        if (this.isRiding)
        {
            this.bipedRightArm.rotateAngleX += -((float)Math.PI / 5F);
            this.bipedLeftArm.rotateAngleX += -((float)Math.PI / 5F);
            this.bipedRightLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            this.bipedLeftLeg.rotateAngleX = -((float)Math.PI * 2F / 5F);
            this.bipedRightLeg.rotateAngleY = ((float)Math.PI / 10F);
            this.bipedLeftLeg.rotateAngleY = -((float)Math.PI / 10F);
        }

        if (this.heldItemLeft != 0)
        {
            this.bipedLeftArm.rotateAngleX = this.bipedLeftArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemLeft;
        }

        if (this.heldItemRight != 0)
        {
            this.bipedRightArm.rotateAngleX = this.bipedRightArm.rotateAngleX * 0.5F - ((float)Math.PI / 10F) * (float)this.heldItemRight;
        }

        this.bipedRightArm.rotateAngleY = 0.0F;
        this.bipedLeftArm.rotateAngleY = 0.0F;
        float f6;
        float f7;

        if (this.onGround > -9990.0F)
        {
            f6 = this.onGround;
            this.bipedBody.rotateAngleY = yaw;//MathHelper.sin(MathHelper.sqrt_float(f6) * (float)Math.PI * 2.0F) * 0.2F;
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
            f6 = 1.0F - this.onGround;
            f6 *= f6;
            f6 *= f6;
            f6 = 1.0F - f6;
            f7 = MathHelper.sin(f6 * (float)Math.PI);
            float f8 = MathHelper.sin(this.onGround * (float)Math.PI) * -(this.bipedHead.rotateAngleX - 0.7F) * 0.75F;
            //this.bipedRightArm.rotateAngleX = (float)((double)this.bipedRightArm.rotateAngleX - ((double)f7 * 1.2D + (double)f8));
            //this.bipedRightArm.rotateAngleY += this.bipedBody.rotateAngleY * 2.0F;
            //this.bipedRightArm.rotateAngleZ = MathHelper.sin(this.onGround * (float)Math.PI) * -0.4F;
        }

        if (this.isSneak)
        {
            this.bipedBody.rotateAngleX = 0.5F;
            this.bipedRightArm.rotateAngleX += 0.4F;
            this.bipedLeftArm.rotateAngleX += 0.4F;
            this.bipedRightLeg.rotationPointZ = 4.0F;
            this.bipedLeftLeg.rotationPointZ = 4.0F;
            this.bipedRightLeg.rotationPointY = 9.0F;
            this.bipedLeftLeg.rotationPointY = 9.0F;
            this.bipedHead.rotationPointY = 1.0F;
            this.bipedHeadwear.rotationPointY = 1.0F;
        }
        else
        {
            this.bipedBody.rotateAngleX = 0.0F;
            //this.bipedRightLeg.rotationPointZ = 0.1F;
            //this.bipedLeftLeg.rotationPointZ = 0.1F;
            this.bipedRightLeg.rotationPointY = 12.0F;
            this.bipedLeftLeg.rotationPointY = 12.0F;
            this.bipedHead.rotationPointY = 0.0F;
            this.bipedHeadwear.rotationPointY = 0.0F;
        }

        //this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        //this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
        //this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
        //this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;

        if (this.aimedBow)
        {
            f6 = 0.0F;
            f7 = 0.0F;
            this.bipedRightArm.rotateAngleZ = 0.0F;
            this.bipedLeftArm.rotateAngleZ = 0.0F;
            this.bipedRightArm.rotateAngleY = -(0.1F - f6 * 0.6F) + this.bipedHead.rotateAngleY;
            this.bipedLeftArm.rotateAngleY = 0.1F - f6 * 0.6F + this.bipedHead.rotateAngleY + 0.4F;
            this.bipedRightArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
            this.bipedLeftArm.rotateAngleX = -((float)Math.PI / 2F) + this.bipedHead.rotateAngleX;
            this.bipedRightArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            this.bipedLeftArm.rotateAngleX -= f6 * 1.2F - f7 * 0.4F;
            this.bipedRightArm.rotateAngleZ += MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
            this.bipedLeftArm.rotateAngleZ -= MathHelper.cos(par3 * 0.09F) * 0.05F + 0.05F;
            this.bipedRightArm.rotateAngleX += MathHelper.sin(par3 * 0.067F) * 0.05F;
            this.bipedLeftArm.rotateAngleX -= MathHelper.sin(par3 * 0.067F) * 0.05F;
        }
        //this.bipedBody.rotateAngleY = yaw;
        this.bipedHead.rotateAngleY = yaw;
        this.bipedHeadwear.rotateAngleY = yaw;
        //this.bipedLeftArm.rotateAngleY = yaw;
        //this.bipedRightArm.rotateAngleY = yaw;
        //this.bipedLeftLeg.rotateAngleY = yaw;
        //this.bipedRightLeg.rotateAngleY = yaw;
        /*this.bipedBody.setRotationPoint(8, 0, -8);
        this.bipedHead.setRotationPoint(8, 0, -8);
        this.bipedHeadwear.setRotationPoint(8, 0, -8);
        this.bipedRightArm.setRotationPoint(8, 0, -8);
        this.bipedLeftArm.setRotationPoint(8, 0, -8);
        this.bipedRightLeg.setRotationPoint(8, 12, -8);
        this.bipedLeftLeg.setRotationPoint(8, 12, -8);
        
        this.bipedRightArm.rotationPointZ += MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
        this.bipedRightArm.rotationPointX += -MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
        this.bipedLeftArm.rotationPointZ += -MathHelper.sin(this.bipedBody.rotateAngleY) * 5.0F;
        this.bipedLeftArm.rotationPointX += MathHelper.cos(this.bipedBody.rotateAngleY) * 5.0F;
        
        this.bipedRightLeg.rotationPointZ += MathHelper.sin(this.bipedBody.rotateAngleY) * 1.9F;
        this.bipedRightLeg.rotationPointX += -MathHelper.cos(this.bipedBody.rotateAngleY) * 1.9F;
        this.bipedLeftLeg.rotationPointZ += -MathHelper.sin(this.bipedBody.rotateAngleY) * 1.9F;
        this.bipedLeftLeg.rotationPointX += MathHelper.cos(this.bipedBody.rotateAngleY) * 1.9F;*/
    }
}
