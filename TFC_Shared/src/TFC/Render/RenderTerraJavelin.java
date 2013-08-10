package TFC.Render;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import TFC.Reference;
import TFC.Entities.EntityTerraJavelin;

public class RenderTerraJavelin extends Render
{
	public void render(EntityTerraJavelin entity, double x, double y, double z, float par8, float par9)
	{
		func_110777_b(entity);
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x, (float)y, (float)z);
		GL11.glRotatef(entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * par9 - 90.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * par9, 0.0F, 0.0F, 1.0F);
		Tessellator var10 = Tessellator.instance;
		byte var11 = 0;
		float var12 = 0.0F;
		float var13 = 0.5F;
		float var14 = (0 + var11 * 10) / 32.0F;
		float var15 = (5 + var11 * 10) / 32.0F;
		float var20 = 0.05625F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		float var21 = entity.arrowShake - par9;

		if (var21 > 0.0F)
		{
			float var22 = -MathHelper.sin(var21 * 3.0F) * var21;
			GL11.glRotatef(var22, 0.0F, 0.0F, 1.0F);
		}

		GL11.glRotatef(45.0F, 1.0F, 0.0F, 0.0F);
		GL11.glScalef(var20, var20, var20);
		GL11.glTranslatef(-4.0F, 0.0F, 0.0F);

		for (int var23 = 0; var23 < 4; ++var23)
		{
			GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
			GL11.glNormal3f(0.0F, 0.0F, var20);
			var10.startDrawingQuads();
			var10.addVertexWithUV(-45.0D, -2.0D, 0.0D, var12, var14);
			var10.addVertexWithUV(8.0D, -2.0D, 0.0D, var13, var14);
			var10.addVertexWithUV(8.0D, 2.0D, 0.0D, var13, var15);
			var10.addVertexWithUV(-45.0D, 2.0D, 0.0D, var12, var15);
			var10.draw();
		}

		GL11.glDisable(GL12.GL_RESCALE_NORMAL);
		GL11.glPopMatrix();
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
		this.render((EntityTerraJavelin)par1Entity, par2, par4, par6, par8, par9);
	}

	@Override
	protected ResourceLocation func_110775_a(Entity entity) 
	{
		return new ResourceLocation(Reference.ModID, "mob/javelin.png");
	}
}