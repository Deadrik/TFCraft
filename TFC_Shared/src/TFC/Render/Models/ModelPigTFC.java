package TFC.Render.Models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Time;
import TFC.Entities.EntityAnimalTFC;

public class ModelPigTFC extends ModelQuadrupedTFC
{
	public ModelPigTFC()
	{
		this(0.0F);
	}
	ModelRenderer tusk1;
	ModelRenderer tusk2;
	ModelRenderer snout;

	public ModelPigTFC(float par1)
	{
		super(6, par1);
		tusk1 = new ModelRenderer(this,32,0);
		tusk1.addBox(0F, 0F, 0F, 1, 2, 1, 0F);
		tusk1.setRotationPoint(-3f,0.5f,-9f);
		tusk1.rotateAngleX = (float)Math.PI/12;

		tusk2 = new ModelRenderer(this,32,0);
		tusk2.addBox(0F, 0F, 0F, 1, 2, 1, 0F);
		tusk2.setRotationPoint(2f,0.5f,-9f);
		tusk2.rotateAngleX = (float)Math.PI/12;

		snout = new ModelRenderer(this,0,26);
		snout.addBox(-2.0F, 0.0F, -10.0F, 4, 3, 3, par1);
		snout.addChild(tusk1);
		snout.addChild(tusk2);
		this.head.addChild(snout);
		this.field_40331_g = 4.0F;
	}
	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7);
		if(par1Entity instanceof EntityAnimalTFC){
			float tempAge;

			tempAge = Math.min(TFC_Time.getTotalTicks()-((EntityAnimalTFC)par1Entity).adultTime,0);
			if(tempAge <= 0){
				age = (-1F)*tempAge / (((EntityAnimalTFC)par1Entity).adultAge * TFC_Settings.dayLength);
				if(((EntityAnimalTFC)par1Entity).sex==0){
					if((1-age) > 0.75){
						tusk1.isHidden = false;
						tusk2.isHidden = false;
					}
				}
				float aa =  2F - (1.0F - age);
				GL11.glPushMatrix ();
				float ab = (float)Math.sqrt(1.0F / aa);
				GL11.glScalef(ab, ab, ab);
				GL11.glTranslatef (0.0F, 24F * par7 * age/aa,2F*par7*age/ab);            
				head.render(par7);
				GL11.glPopMatrix();
				GL11.glPushMatrix();
				GL11.glScalef(1.0F / aa, 1.0F / aa, 1.0F / aa);
				GL11.glTranslatef(0.0F, 24F * par7 * age, 0.0F);
				body.render(par7);
				leg1.render(par7);
				leg2.render(par7);
				leg3.render(par7);
				leg4.render(par7);
				GL11.glPopMatrix();

			}
		}
	}
	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6)
	{
		//super.setRotationAngles(par1, par2, par3, par4, par5, par6);
		tusk1.isHidden = true;
		tusk2.isHidden = true;
		this.head.rotateAngleX = par5 / (180F / (float)Math.PI);
		this.head.rotateAngleY = par4 / (180F / (float)Math.PI);
		this.body.rotateAngleX = ((float)Math.PI / 2F);
		this.leg1.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
		this.leg2.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.leg3.rotateAngleX = MathHelper.cos(par1 * 0.6662F + (float)Math.PI) * 1.4F * par2;
		this.leg4.rotateAngleX = MathHelper.cos(par1 * 0.6662F) * 1.4F * par2;
	}
}
