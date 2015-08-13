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
	private static final ResourceLocation field_110862_k = new ResourceLocation("textures/entity/skeleton/skeleton.png");
	private static final ResourceLocation field_110861_l = new ResourceLocation("textures/entity/skeleton/wither_skeleton.png");
	public static final RenderQuiver renderQuiver = new RenderQuiver();
	public static ItemStack quiver = new ItemStack(TFCItems.Quiver,1,1);
	public static ItemStack ammo = ((ItemQuiver)TFCItems.Quiver).addItem(quiver, new ItemStack(TFCItems.Arrow,16,0));

	public RenderSkeletonTFC()
	{
		super(new ModelSkeletonTFC(), 0.5F);
	}

	protected void func_82438_a(EntitySkeletonTFC par1EntitySkeleton, float par2)
	{
		if (par1EntitySkeleton.getSkeletonType() == 1)
			GL11.glScalef(1.2F, 1.2F, 1.2F);
	}

	@Override
	protected void func_82422_c()
	{
		GL11.glTranslatef(0.09375F, 0.1875F, 0.0F);
	}

	protected ResourceLocation func_110860_a(EntitySkeletonTFC par1EntitySkeleton)
	{
		return par1EntitySkeleton.getSkeletonType() == 1 ? field_110861_l : field_110862_k;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLiving par1EntityLiving)
	{
		return this.func_110860_a((EntitySkeletonTFC)par1EntityLiving);
	}

	/**
	 * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
	 * entityLiving, partialTickTime
	 */
	@Override
	protected void preRenderCallback(EntityLivingBase par1EntityLivingBase, float par2)
	{

		renderQuiver.render(par1EntityLivingBase,quiver);
		this.func_82438_a((EntitySkeletonTFC)par1EntityLivingBase, par2);
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity par1Entity)
	{
		return this.func_110860_a((EntitySkeletonTFC)par1Entity);
	}
}
