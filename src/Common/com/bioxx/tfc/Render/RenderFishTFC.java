package com.bioxx.tfc.Render;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityFishTFC;

@SideOnly(Side.CLIENT)
public class RenderFishTFC extends RenderLiving
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Reference.MOD_ID, "textures/mob/Bass.png");

	//private float scale = 1f;
	//private ModelBass modelbass;

	public RenderFishTFC (ModelBase par1ModelBase, float par2)
	{
		super (par1ModelBase, par2);
		//modelbass = (ModelBass) par1ModelBase;
	}


	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback (EntityLivingBase par1EntityLiving, float par2)
	{
		preRenderScale ((EntityFishTFC) par1EntityLiving, par2);
		Block blockBelow = par1EntityLiving.worldObj.getBlock((int)par1EntityLiving.posX,(int)par1EntityLiving.posY-1,(int)par1EntityLiving.posZ);
		
		//float pitch = ((EntityFishTFC) par1EntityLiving).currentRenderPitch;
		//GL11.glRotatef(pitch, 1, 0, 0);
		if (!par1EntityLiving.isInWater() && !TFC_Core.isWater(blockBelow))
		{
			GL11.glRotatef(((EntityFishTFC) par1EntityLiving).currentRenderRoll, 0.0F, 0.0F, 1.0F);
			GL11.glTranslatef(-0.15f, 1.5f, -0.8f);
		}
		else{
			((EntityFishTFC) par1EntityLiving).currentRenderRoll = 0;
			GL11.glTranslatef(0f, 1.2F, -0.5f);
		}
	}
	
	@Override
	protected void rotateCorpse(EntityLivingBase par1EntityLivingBase, float par2, float par3, float par4)
    {
		float temp = ((EntityFishTFC)par1EntityLivingBase).currentRenderYaw;
        GL11.glRotatef(temp, 0.0F, 1.0F, 0.0F);
        
        if (par1EntityLivingBase.deathTime > 0)
        {
			float f3 = (par1EntityLivingBase.deathTime + par4 - 1.0F) / 20.0F * 1.6F;
            f3 = MathHelper.sqrt_float(f3);

            if (f3 > 1.0F)
            {
                f3 = 1.0F;
            }

            //GL11.glRotatef(f3 * this.getDeathMaxRotation(par1EntityLivingBase), 0.0F, 0.0F, 1.0F);
        }
    }

	protected void preRenderScale (EntityFishTFC par1EntityBass, float par2)
	{
		GL11.glScalef (0.4f,0.4f, 0.4f);
	}


	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float handleRotationFloat (EntityLivingBase par1EntityLiving, float par2)
	{
		return 1.0f;
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
	{
		this.shadowSize = 0.35f;
		super.doRender(par1Entity, par2, par4, par6, par8, par9);
	}


	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO Auto-generated method stub
		return TEXTURE;
	}
}
