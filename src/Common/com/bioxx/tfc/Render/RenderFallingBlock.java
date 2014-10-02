package com.bioxx.tfc.Render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Entities.EntityFallingBlockTFC;

public class RenderFallingBlock extends Render
{
	private final RenderBlocks field_147920_a = new RenderBlocks();

	public RenderFallingBlock()
	{
		this.shadowSize = 0.5f;
	}
	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	public void doRender(EntityFallingBlockTFC entity, double x, double y, double z, float p_76986_8_, float p_76986_9_)
	{
		World world = entity.getWorld();
		Block block = entity.getBlock();
		int i = MathHelper.floor_double(entity.posX);
		int j = MathHelper.floor_double(entity.posY);
		int k = MathHelper.floor_double(entity.posZ);

		if (block != null && block != world.getBlock(i, j, k))
		{
			GL11.glPushMatrix();
			GL11.glTranslatef((float)x, (float)y, (float)z);
			this.bindEntityTexture(entity);
			GL11.glDisable(GL11.GL_LIGHTING);
			Tessellator tessellator;

			this.field_147920_a.setRenderBoundsFromBlock(block);
			this.field_147920_a.renderBlockSandFalling(block, world, i, j, k, entity.blockMeta);

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityFallingBlockTFC p_110775_1_)
	{
		return TextureMap.locationBlocksTexture;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(Entity p_110775_1_)
	{
		return this.getEntityTexture((EntityFallingBlockTFC)p_110775_1_);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity p_76986_1_, double p_76986_2_, double p_76986_4_, double p_76986_6_, float p_76986_8_, float p_76986_9_)
	{
		this.doRender((EntityFallingBlockTFC)p_76986_1_, p_76986_2_, p_76986_4_, p_76986_6_, p_76986_8_, p_76986_9_);
	}
}
