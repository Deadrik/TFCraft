package com.bioxx.tfc.Render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
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
	public void doRender(EntityFallingBlockTFC entity, double x, double y, double z, float f, float f1)
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

			this.field_147920_a.setRenderBoundsFromBlock(block);
			this.field_147920_a.renderBlockSandFalling(block, world, i, j, k, entity.blockMeta);

			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glPopMatrix();
		}
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(EntityFallingBlockTFC entity)
	{
		return TextureMap.locationBlocksTexture;
	}

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return this.getEntityTexture((EntityFallingBlockTFC)entity);
	}

	/**
	 * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
	 * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
	 * (Render<T extends Entity) and this method has signature public void func_76986_a(T entity, double d, double d1,
	 * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
	 */
	@Override
	public void doRender(Entity entity, double x, double y, double z, float f, float f1)
	{
		this.doRender((EntityFallingBlockTFC)entity, x, y, z, f, f1);
	}
}
