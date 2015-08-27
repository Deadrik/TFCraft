package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.model.ModelSheep2;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Entities.IAnimal;
import com.bioxx.tfc.api.Entities.IAnimal.GenderEnum;
public class ModelSheep2TFC extends ModelSheep2
{
	private ModelRenderer horn1;
	private ModelRenderer horn2;
	private ModelRenderer horn1b;
	private ModelRenderer horn2b;
	
	public ModelSheep2TFC()
	{
		super();
		
		horn1 = new ModelRenderer(this,28,2);
		horn1.addBox(0F, 0F, 0F, 2, 4, 2, 0F);
		horn1.setRotationPoint(0F, -10F, 0F);
		horn1.rotateAngleZ = (float)-Math.PI/6;
		horn1.rotateAngleX = (float)-Math.PI/6;
		horn1.rotateAngleY = (float)-Math.PI/3;
		horn1.setRotationPoint(-5F, -6F, -1F);
		
		horn1b = new ModelRenderer(this,38,4);
		horn1b.addBox(0.5F, 1F, 0.5F, 1, 3, 1, 0.25F);
		horn1b.setRotationPoint(0F, -2F, 4F);
		horn1b.rotateAngleX = (float)-Math.PI/3;
		
		horn2 = new ModelRenderer(this,28,2);
		horn2.addBox(0F, 0F, 0F, 2, 4, 2, 0F);
		horn2.setRotationPoint(0F, -10F, 0F);
		horn2.rotateAngleZ = (float)Math.PI/6;
		horn2.rotateAngleX = (float)-Math.PI/6;
		horn2.rotateAngleY = (float)Math.PI/3;
		horn2.setRotationPoint(4F, -6.5F, 0.75F);
		
		horn2b = new ModelRenderer(this,38,4);
		horn2b.addBox(0.5F, 1F, 0.5F, 1, 3, 1, 0.25F);
		horn2b.setRotationPoint(0F, -2F, 4F);
		horn2b.rotateAngleX = (float)-Math.PI/3;
			
		horn1.addChild(horn1b);
		horn2.addChild(horn2b);
		
		head.addChild(horn1);
		head.addChild(horn2);
	}

	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);

		float percent = TFC_Core.getPercentGrown((IAnimal)entity);
		float ageScale = 2.0F-percent;
		float ageHeadScale = (float)Math.pow(1/ageScale,0.66);
		//float offset = 1.4f - percent;

		/*if(((IAnimal)entity).isAdult()) {
			offset = 0;
		}*/
		
		GL11.glPushMatrix ();

		GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
		GL11.glScalef(ageHeadScale, ageHeadScale, ageHeadScale);
		GL11.glTranslatef (0.0F, (ageScale-1)*-0.125f,0.1875f-(0.1875f*percent));

		
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

		if(((IAnimal)entity).getGender()==GenderEnum.FEMALE)
		{
			horn1.isHidden = true;
			horn2.isHidden = true;
		}
		this.head.render(par7);
		//this.horn1.render(par7);
		//this.horn2.render(par7);
		
		GL11.glPopMatrix();
		GL11.glPushMatrix ();
		GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
		GL11.glScalef(1/ageScale, 1/ageScale, 1/ageScale);

		this.body.render(par7);
		this.leg1.render(par7);
		this.leg2.render(par7);
		this.leg3.render(par7);
		this.leg4.render(par7);
		horn1.isHidden = false;
		horn1b.isHidden = false;
		horn2.isHidden = false;
		horn2b.isHidden = false;
		GL11.glPopMatrix();
	}
}
