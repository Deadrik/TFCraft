package TFC.Render.Models;

import net.minecraft.client.model.ModelQuadruped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import TFC.API.Entities.IAnimal;
import TFC.API.Entities.IAnimal.GenderEnum;
public class ModelCowTFC extends ModelQuadruped
{
	public ModelRenderer udders;
	ModelRenderer horn1;
	ModelRenderer horn2;
	ModelRenderer horn1b;
	ModelRenderer horn2b;
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
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7);

		float age = 0;
		long tempAge = 0;
		int sex = 0;
		/*if (par1Entity instanceof EntityAnimalTFC)
		{
			tempAge = Math.min(TFC_Time.getTotalTicks()-((EntityAnimalTFC)par1Entity).adultTime,0);
			if(tempAge < 0){
				age = (-1F)*tempAge / (((EntityAnimalTFC)par1Entity).adultAge * TFC_Settings.dayLength);
				//System.out.print("completed; "+age+", "+tempAge);
			}
			sex = ((EntityAnimalTFC)par1Entity).sex;
		}*/
		float aa =  1;//2F - (1.0F - age);
		GL11.glPushMatrix ();
		float ab = (float)Math.sqrt(1.0F / aa);
		GL11.glScalef(ab, ab, ab);
		GL11.glTranslatef (0.0F, 32F * par7 * age/aa,2F*par7*age/ab);
		if(aa>1.5F){
			horn1.isHidden = true;//rotateAngleX = (float)Math.PI;
			horn2.isHidden = true;//rotateAngleX = -(float)Math.PI;
			if(aa>1.25F){
				horn1b.isHidden = true;
				horn2b.isHidden = true;
			}
		}
		if(((IAnimal)par1Entity).getGender()==GenderEnum.MALE){
			udders.isHidden = true;
		}
		else{
			horn1b.isHidden = true;
			horn2b.isHidden = true;
		}
		head.render(par7);
		GL11.glPopMatrix();
		GL11.glPushMatrix();
		GL11.glScalef(1.0F / aa, 1.0F / aa, 1.0F / aa);
		GL11.glTranslatef(0.0F, 24F * par7 * age, 0.0F);
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
		this.body.rotateAngleX = ((float)Math.PI / 2F);
		this.udders.rotateAngleX = ((float)Math.PI / 2F);
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
