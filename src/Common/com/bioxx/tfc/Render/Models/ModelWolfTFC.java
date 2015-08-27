package com.bioxx.tfc.Render.Models;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityWolfTFC;
import com.bioxx.tfc.api.Entities.IAnimal;

public class ModelWolfTFC extends ModelBase
{
	/** main box for the wolf head */
	public ModelRenderer wolfHeadMain;

	/** The wolf's body */
	public ModelRenderer wolfBody;

	/** Wolf'se first leg */
	public ModelRenderer wolfLeg1;

	/** Wolf's second leg */
	public ModelRenderer wolfLeg2;

	/** Wolf's third leg */
	public ModelRenderer wolfLeg3;

	/** Wolf's fourth leg */
	public ModelRenderer wolfLeg4;

	/** The wolf's tail */
	private ModelRenderer wolfTail;

	/** The wolf's mane */
	private ModelRenderer wolfMane;

	public ModelWolfTFC()
	{
		float f = 0.0F;
		float f1 = 13.5F;
		this.wolfHeadMain = new ModelRenderer(this, 0, 0);
		this.wolfHeadMain.addBox(-3.0F, -3.0F, -2.0F, 6, 6, 4, f);
		this.wolfHeadMain.setRotationPoint(-1.0F, f1, -7.0F);
		this.wolfBody = new ModelRenderer(this, 18, 14);
		this.wolfBody.addBox(-4.0F, -2.0F, -3.0F, 6, 9, 6, f);
		this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
		this.wolfMane = new ModelRenderer(this, 21, 0);
		this.wolfMane.addBox(-4.0F, -3.0F, -3.0F, 8, 6, 7, f);
		this.wolfMane.setRotationPoint(-1.0F, 14.0F, 2.0F);
		this.wolfLeg1 = new ModelRenderer(this, 0, 18);
		this.wolfLeg1.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
		this.wolfLeg2 = new ModelRenderer(this, 0, 18);
		this.wolfLeg2.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
		this.wolfLeg3 = new ModelRenderer(this, 0, 18);
		this.wolfLeg3.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
		this.wolfLeg4 = new ModelRenderer(this, 0, 18);
		this.wolfLeg4.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
		this.wolfTail = new ModelRenderer(this, 9, 18);
		this.wolfTail.addBox(-1.0F, 0.0F, -1.0F, 2, 8, 2, f);
		this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
		this.wolfHeadMain.setTextureOffset(16, 14).addBox(-3.0F, -5.0F, 0.0F, 2, 2, 1, f);
		this.wolfHeadMain.setTextureOffset(16, 14).addBox(1.0F, -5.0F, 0.0F, 2, 2, 1, f);
		this.wolfHeadMain.setTextureOffset(0, 10).addBox(-1.5F, 0.0F, -5.0F, 3, 3, 4, f);
	}

	/**
	 * Sets the models various rotation angles then renders the model.
	 */
	@Override
	public void render(Entity entity, float par2, float par3, float par4, float par5, float par6, float par7)
	{
		super.render(entity, par2, par3, par4, par5, par6, par7);
		this.setRotationAngles(par2, par3, par4, par5, par6, par7, entity);

		float percent = TFC_Core.getPercentGrown((IAnimal)entity);
		float ageScale = 2.0F-percent;
		//float offset = 1.4f - percent;

		if(entity instanceof IAnimal)
		{
			GL11.glPushMatrix();
			GL11.glTranslatef(0.0F, 0.75f - (0.75f * percent), 0f);
			GL11.glScalef(1/ageScale, 1/ageScale, 1/ageScale);    
			this.wolfHeadMain.renderWithRotation(par7);
			this.wolfBody.render(par7);
			this.wolfLeg1.render(par7);
			this.wolfLeg2.render(par7);
			this.wolfLeg3.render(par7);
			this.wolfLeg4.render(par7);
			this.wolfTail.renderWithRotation(par7);
			this.wolfMane.render(par7);
			GL11.glPopMatrix ();
		}
	}

	@Override
	public void setLivingAnimations(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
	{
		EntityWolfTFC entitywolf = (EntityWolfTFC) par1EntityLivingBase;

		if (entitywolf.isAngry())
		{
			this.wolfTail.rotateAngleY = 0.0F;
		}
		else
		{
			this.wolfTail.rotateAngleY = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
		}
		if (entitywolf.isSitting())
		{
			this.wolfMane.setRotationPoint(-1.0F, 16.0F, -3.0F);
			this.wolfMane.rotateAngleX = (float) Math.PI * 2F / 5F;
			this.wolfMane.rotateAngleY = 0.0F;
			this.wolfBody.setRotationPoint(0.0F, 18.0F, 0.0F);
			this.wolfBody.rotateAngleX = (float) Math.PI / 4F;
			this.wolfTail.setRotationPoint(-1.0F, 21.0F, 6.0F);
			this.wolfLeg1.setRotationPoint(-2.5F, 22.0F, 2.0F);
			this.wolfLeg1.rotateAngleX = (float) Math.PI * 3F / 2F;
			this.wolfLeg2.setRotationPoint(0.5F, 22.0F, 2.0F);
			this.wolfLeg2.rotateAngleX = (float) Math.PI * 3F / 2F;
			this.wolfLeg3.rotateAngleX = 5.811947F;
			this.wolfLeg3.setRotationPoint(-2.49F, 17.0F, -4.0F);
			this.wolfLeg4.rotateAngleX = 5.811947F;
			this.wolfLeg4.setRotationPoint(0.51F, 17.0F, -4.0F);
		}
		else
		{
			this.wolfBody.setRotationPoint(0.0F, 14.0F, 2.0F);
			this.wolfBody.rotateAngleX = (float) Math.PI / 2F;
			this.wolfMane.setRotationPoint(-1.0F, 14.0F, -3.0F);
			this.wolfMane.rotateAngleX = this.wolfBody.rotateAngleX;
			this.wolfTail.setRotationPoint(-1.0F, 12.0F, 8.0F);
			this.wolfLeg1.setRotationPoint(-2.5F, 16.0F, 7.0F);
			this.wolfLeg2.setRotationPoint(0.5F, 16.0F, 7.0F);
			this.wolfLeg3.setRotationPoint(-2.5F, 16.0F, -4.0F);
			this.wolfLeg4.setRotationPoint(0.5F, 16.0F, -4.0F);
			this.wolfLeg1.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
			this.wolfLeg2.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
			this.wolfLeg3.rotateAngleX = MathHelper.cos(par2 * 0.6662F + (float)Math.PI) * 1.4F * par3;
			this.wolfLeg4.rotateAngleX = MathHelper.cos(par2 * 0.6662F) * 1.4F * par3;
		}

		this.wolfHeadMain.rotateAngleZ = entitywolf.getInterestedAngle(par4) + entitywolf.getShakeAngle(par4, 0.0F);
		this.wolfMane.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.08F);
		this.wolfBody.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.16F);
		this.wolfTail.rotateAngleZ = entitywolf.getShakeAngle(par4, -0.2F);
		wolfTail.rotateAngleY = 0.5f * (1 - (1 / (entitywolf.getHappyTicks() + 1f))) * MathHelper.sin((float) (Math.PI * entitywolf.getHappyTicks() / 5F));
	}
	
	@Override
	public void setRotationAngles(float par1, float par2, float par3, float par4, float par5, float par6, Entity par7Entity)
    {
        super.setRotationAngles(par1, par2, par3, par4, par5, par6, par7Entity);
        this.wolfHeadMain.rotateAngleX = par5 / (180F / (float)Math.PI);
        this.wolfHeadMain.rotateAngleY = par4 / (180F / (float)Math.PI);
		if (par7Entity instanceof EntityWolfTFC && ((EntityWolfTFC) par7Entity).getHappyTicks() > 0)
			this.wolfTail.rotateAngleX = (float) (Math.PI / 4f) * 2.5F;
		else
			this.wolfTail.rotateAngleX = par3;
    }
}
