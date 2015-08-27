package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelSheep1;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Entities.IAnimal;
public class ModelSheep1TFC extends ModelSheep1
{
	
	/*private ModelRenderer horn1;
	private ModelRenderer horn2;
	private ModelRenderer horn3;
	private ModelRenderer horn4;
	private ModelRenderer horn5;
	private ModelRenderer horn6;
	private ModelRenderer horn1b;
	private ModelRenderer horn2b;
	private ModelRenderer horn3b;
	private ModelRenderer horn4b;
	private ModelRenderer horn5b;
	private ModelRenderer horn6b;
	
	private ModelRenderer ear;
	private ModelRenderer earb;*/
	public ModelSheep1TFC()
	{
		super();
		/*
		horn1 = new ModelRenderer(this,28,14);
		horn1.addBox(0F, 0F, 0F, 2, 3, 2, 0);
		horn1.setRotationPoint(0F, -10F, 0F);
		horn1.rotateAngleX = (float)(-28f*Math.PI/180f);
		horn1.rotateAngleY = (float)(70f*Math.PI/180f);
		horn1.rotateAngleZ = (float)(155f*Math.PI/180f);
		
		horn2 = new ModelRenderer(this,28,14);
		horn2.addBox(0F, 0F, 0F, 2, 2, 2, 0);
		horn2.setRotationPoint(0F, -3F, 0F);
		horn2.rotateAngleX = (float)(-10f*Math.PI/180f);
		horn2.rotateAngleY = (float)(-110f*Math.PI/180f);
		horn2.rotateAngleZ = (float)(-160f*Math.PI/180f);
		
		horn3 = new ModelRenderer(this,28,14);
		horn3.addBox(0F, 0F, 0F, 2, 2, 2, 0);
		horn3.setRotationPoint(0F, -2F, 0F);
		horn3.rotateAngleX = (float)(-52f*Math.PI/180f);
		horn3.rotateAngleY = (float)((-20f)*Math.PI/180f);
		horn3.rotateAngleZ = (float)((5f)*Math.PI/180f);
		
		horn4 = new ModelRenderer(this,28,14);
		horn4.addBox(0F, 0F, 0F, 2, 2, 2, 0);
		horn4.setRotationPoint(0F, -2F, 0F);
		horn4.rotateAngleX = (float)(-76f*Math.PI/180f);
		horn4.rotateAngleY = (float)(240f*Math.PI/180f);
		horn4.rotateAngleZ = (float)(50f*Math.PI/180f);
		
		horn5 = new ModelRenderer(this,28,14);
		horn5.addBox(0F, 0F, 0F, 2, 2, 1, 0);
		horn5.setRotationPoint(0F, -2F, 0F);
		horn5.rotateAngleX = (float)(90f*Math.PI/180f);
		horn5.rotateAngleY = (float)(-90f*Math.PI/180f);
		horn5.rotateAngleZ = (float)(28f*Math.PI/180f);
		
		horn6 = new ModelRenderer(this,28,14);
		horn6.addBox(0F, 0F, 0F, 2, 1, 1, 0);
		horn6.setRotationPoint(0F, -2F, 0F);
		horn6.rotateAngleX = (float)(90f*Math.PI/180f);
		//horn6.rotateAngleY = (float)(0f*Math.PI/180f);
		horn6.rotateAngleZ = (float)(-213f*Math.PI/180f);
		
		horn5.addChild(horn6);
		horn4.addChild(horn5);
		horn3.addChild(horn4);
		horn2.addChild(horn3);
		horn1.addChild(horn2);
		head.addChild(horn1);
		
		float locHornX1 = -3;
		float locHornY1 = 4;
		float locHornZ1 = -3;
		
		ear = new ModelRenderer(this,28,14);
		ear.addBox(0F, 0F, 0F, 3, 2, 1, -0.2f);
		ear.setRotationPoint(-3.5f + locHornX1,locHornY1 -8F,locHornZ1 + 0.5F);
		
		horn1 = new ModelRenderer(this,28,14);
		horn1.addBox(0F, 0F, 0F, 2, 3, 2, 0);
		horn1.rotateAngleX = (float)(-20f*Math.PI/180f);
		horn1.rotateAngleY = (float)(-20f*Math.PI/180f);
		horn1.rotateAngleZ = (float)(0f*Math.PI/180f);
		horn1.setRotationPoint(0F + locHornX1,locHornY1 -10F,locHornZ1 + 0F);
		
		horn2 = new ModelRenderer(this,28,14);
		horn2.addBox(0F, 0F, 0F, 2, 2, 2, 0);
		horn2.rotateAngleX = (float)(38f*Math.PI/180f);
		horn2.rotateAngleY = (float)(-40f*Math.PI/180f);
		horn2.rotateAngleZ = (float)(0f*Math.PI/180f);
		horn2.setRotationPoint(0F + locHornX1,locHornY1 -10F,locHornZ1 + 0F);
		
		horn3 = new ModelRenderer(this,28,14);
		horn3.addBox(0F, 0F, 0F, 2, 2, 2, 0);
		horn3.rotateAngleX = (float)(-14f*Math.PI/180f);
		horn3.rotateAngleY = (float)((-60f)*Math.PI/180f);
		horn3.rotateAngleZ = (float)((0f)*Math.PI/180f);
		horn3.setRotationPoint(-1F + locHornX1,locHornY1 -11F,locHornZ1 + 1F);
		
		horn4 = new ModelRenderer(this,28,14);
		horn4.addBox(0F, 0F, 0F, 2, 2, 2, 0);
		horn4.rotateAngleX = (float)(-90f*Math.PI/180f);
		horn4.rotateAngleY = (float)(0f*Math.PI/180f);
		horn4.rotateAngleZ = (float)(50f*Math.PI/180f);
		horn4.setRotationPoint(-2.5F + locHornX1,locHornY1 -10.5F,locHornZ1 + 3.7F);
		
		horn5 = new ModelRenderer(this,28,14);
		horn5.addBox(0F, 0F, 0F, 2, 2, 1, 0);		
		horn5.rotateAngleX = (float)(78f*Math.PI/180f);
		horn5.rotateAngleY = (float)(0f*Math.PI/180f);
		horn5.rotateAngleZ = (float)(90f*Math.PI/180f);
		horn5.setRotationPoint(-4F + locHornX1,locHornY1 -9F,locHornZ1 + 1.5F);
		
		horn6 = new ModelRenderer(this,28,14);
		horn6.addBox(0F, 0F, 0F, 1, 2, 1, 0);		
		horn6.rotateAngleX = (float)(-55f*Math.PI/180f);
		horn6.rotateAngleY = (float)(0f*Math.PI/180f);
		horn6.rotateAngleZ = (float)(0f*Math.PI/180f);
		horn6.setRotationPoint(-4F + locHornX1,locHornY1 -7.5F,locHornZ1 + 2.5F);
		
		head.addChild(horn6);
		head.addChild(horn5);
		head.addChild(horn4);
		head.addChild(horn3);
		head.addChild(horn2);
		head.addChild(horn1);
		head.addChild(ear);
		
		locHornX1 = 5;
		locHornY1 = 4;
		locHornZ1 = -3;
		
		earb = new ModelRenderer(this,28,14);
		earb.addBox(0F, 0F, 0F, 3, 2, 1, -0.2f);
		earb.setRotationPoint(-3.5f + locHornX1,locHornY1 -8F,locHornZ1 + 0.5F);
		
		horn1b = new ModelRenderer(this,28,14);
		horn1b.addBox(0F, 0F, 0F, 2, 3, 2, 0);
		horn1b.setRotationPoint(0F,-2F,0F);
		horn1b.rotateAngleX = (float)(-20f*Math.PI/180f);
		horn1b.rotateAngleY = -(float)(-20f*Math.PI/180f);
		horn1b.rotateAngleZ = -(float)(0f*Math.PI/180f);
		horn1b.setRotationPoint(0F + locHornX1,locHornY1 -10F,locHornZ1 + 0F);
		
		horn2b = new ModelRenderer(this,28,14);
		horn2b.addBox(0F, 0F, 0F, 2, 2, 2, 0);
		horn2b.setRotationPoint(-1F,0F,-1F);
		horn2b.rotateAngleX = -(float)(38f*Math.PI/180f);
		horn2b.rotateAngleY = (float)(-40f*Math.PI/180f);
		horn2b.rotateAngleZ = -(float)(0f*Math.PI/180f);
		horn2b.setRotationPoint(0F + locHornX1,locHornY1 -10F,locHornZ1 + 0F);
		
		horn3b = new ModelRenderer(this,28,14);
		horn3b.addBox(0F, 0F, 0F, 2, 2, 2, 0);
		horn3b.setRotationPoint(-1F,0F,-1F);
		horn3b.rotateAngleX = (float)(-14f*Math.PI/180f);
		horn3b.rotateAngleY = -(float)((-60f)*Math.PI/180f);
		horn3b.rotateAngleZ = -(float)((0f)*Math.PI/180f);
		horn3b.setRotationPoint(1F + locHornX1,locHornY1 -11F,locHornZ1 + 1F);
		
		horn4b = new ModelRenderer(this,28,14);
		horn4b.addBox(0F, 0F, 0F, 2, 2, 2, 0);
		horn4b.setRotationPoint(-1F,0F,-1F);
		horn4b.rotateAngleX = (float)(-90f*Math.PI/180f);
		horn4b.rotateAngleY = -(float)(0f*Math.PI/180f);
		horn4b.rotateAngleZ = -(float)(50f*Math.PI/180f);
		horn4b.setRotationPoint(1.5F + locHornX1,locHornY1 -10.5F,locHornZ1 + 3.7F);
		
		horn5b = new ModelRenderer(this,28,14);
		horn5b.addBox(0F, 0F, 0F, 2, 2, 1, 0);
		horn5b.setRotationPoint(-1F,0F,-0.5F);
		horn5b.rotateAngleX = (float)(78f*Math.PI/180f);
		horn5b.rotateAngleY = -(float)(0f*Math.PI/180f);
		horn5b.rotateAngleZ = -(float)(90f*Math.PI/180f);
		horn5b.setRotationPoint(4F + locHornX1,locHornY1 -9F,locHornZ1 + 1.5F);
		
		horn6b = new ModelRenderer(this,28,14);
		horn6b.addBox(0F, 0F, 0F, 1, 2, 1, 0);
		horn6b.setRotationPoint(-0.5F,0F,-0.5F);
		horn6b.rotateAngleX = (float)(-55f*Math.PI/180f);
		horn6b.rotateAngleY = -(float)(0f*Math.PI/180f);
		horn6b.rotateAngleZ = -(float)(0f*Math.PI/180f);
		horn6b.setRotationPoint(4F + locHornX1,locHornY1 -7.5F,locHornZ1 + 2.5F);*/
		
		//head.addChild(horn6b);
		//head.addChild(horn5b);
		//head.addChild(horn4b);
		//head.addChild(horn3b);
		//head.addChild(horn2b);
		//head.addChild(horn1b);
		//head.addChild(earb);
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

		
		this.head.render(par7);
		
		GL11.glPopMatrix();
		GL11.glPushMatrix ();
		GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
		GL11.glScalef(1/ageScale, 1/ageScale, 1/ageScale);
		this.body.render(par7);
		this.leg1.render(par7);
		this.leg2.render(par7);
		this.leg3.render(par7);
		this.leg4.render(par7);
		GL11.glPopMatrix();
	}
}
