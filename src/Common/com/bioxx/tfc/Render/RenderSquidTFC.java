package com.bioxx.tfc.Render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderSquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntitySquid;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Entities.Mobs.EntitySquidTFC;

@SideOnly(Side.CLIENT)
public class RenderSquidTFC extends RenderSquid
{
	public RenderSquidTFC(ModelBase par1ModelBase, float par2)
	{
		super(par1ModelBase, par2);
	}

	/**
	 * Rotates the Squid's corpse.
	 */
	@Override
	protected void rotateCorpse(EntitySquid par1EntitySquid, float par2, float par3, float par4)
	{
		float f3 = par1EntitySquid.prevSquidPitch + (par1EntitySquid.squidPitch - par1EntitySquid.prevSquidPitch) * par4;
		float f4 = par1EntitySquid.prevSquidYaw + (par1EntitySquid.squidYaw - par1EntitySquid.prevSquidYaw) * par4;
		GL11.glTranslatef(0.0F, 0.5F, 0.0F);
		GL11.glRotatef(180.0F - par3, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(f3, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(f4, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(0.0F, -1.2F, 0.0F);
	}

	@Override
	protected float handleRotationFloat(EntitySquid par1EntitySquid, float par2)
	{
		return par1EntitySquid.lastTentacleAngle + (par1EntitySquid.tentacleAngle - par1EntitySquid.lastTentacleAngle) * par2;
	}

	@Override
	protected void preRenderCallback (EntityLivingBase par1EntityLiving, float par2)
	{
		preRenderScale ((EntitySquidTFC) par1EntityLiving, par2);
	}

	protected void preRenderScale (EntitySquidTFC par1EntitySquid, float par2)
	{
		GL11.glScalef (0.5f,0.5f,0.5f);
	}

	/**
	 * Defines what float the third param in setRotationAngles of ModelBase is
	 */
	@Override
	protected float handleRotationFloat(EntityLivingBase par1EntityLiving, float par2)
	{
		return this.handleRotationFloat((EntitySquid)par1EntityLiving, par2);
	}

	@Override
	protected void rotateCorpse(EntityLivingBase par1EntityLiving, float par2, float par3, float par4)
	{
		this.rotateCorpse((EntitySquid)par1EntityLiving, par2, par3, par4);
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
		this.doRender((EntitySquid)par1Entity, par2, par4, par6, par8, par9);
	}
}
