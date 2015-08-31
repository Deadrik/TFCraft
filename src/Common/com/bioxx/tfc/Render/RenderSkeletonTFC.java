package com.bioxx.tfc.Render;

import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Entities.Mobs.EntitySkeletonTFC;
import com.bioxx.tfc.Items.ItemQuiver;
import com.bioxx.tfc.Render.Models.ModelSkeletonTFC;
import com.bioxx.tfc.api.TFCItems;

@SideOnly(Side.CLIENT)
public class RenderSkeletonTFC extends RenderBiped
{
	private static final ResourceLocation SKELETON_TEXTURE = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation WITHER_TEXTURE = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
	public static final RenderQuiver QUIVER_RENDER = new RenderQuiver();
	public static ItemStack quiver = new ItemStack(TFCItems.quiver,1,1);
	public static ItemStack ammo = ((ItemQuiver)TFCItems.quiver).addItem(quiver, new ItemStack(TFCItems.arrow,16,0));

	public RenderSkeletonTFC()
	{
		super(new ModelSkeletonTFC(), 0.5F);
	}

	protected void scaleRender(EntitySkeletonTFC par1EntitySkeleton, float par2)
	{
		if (par1EntitySkeleton.getSkeletonType() == 1)
			GL11.glScalef(1.2F, 1.2F, 1.2F);
	}

	@Override
	protected void func_82422_c()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}

	protected ResourceLocation getTextureLocation(EntitySkeletonTFC par1EntitySkeleton)
	{
		return par1EntitySkeleton.getSkeletonType() == 1 ? WITHER_TEXTURE : SKELETON_TEXTURE;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
	{
		return this.getTextureLocation((EntitySkeletonTFC)par1EntityLiving);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{

		QUIVER_RENDER.render(par1EntityLivingBase,quiver);
		this.scaleRender((EntitySkeletonTFC)par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.getTextureLocation((EntitySkeletonTFC)par1Entity);
	}
}
