package com.bioxx.tfc.Render.Item;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

public class BowItemRenderer implements IItemRenderer
{

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		if(type == ItemRenderType.EQUIPPED)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) {
		return false;
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack itemstack, Object... data) 
	{
		Item item = itemstack.getItem();
		if (false)
		{

		}
		EntityLivingBase entity = (EntityLivingBase)data[1];
		TextureManager texturemanager = Minecraft.getMinecraft().getTextureManager();
		//Reset old rotations
		float scale = 0.375F;
		/*GL11.glTranslatef(-0.25F, -0.1875F, 0.1875F);
		GL11.glScalef(scale, scale, scale);
		GL11.glRotatef(-20.0F, 0.0F, 0.0F, 1.0F);
		GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);*/

		//New rotations
		scale = 0.625F;
		GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
		GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(scale, -scale, scale);
		GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
		GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);

		/*int i = itemstack.getItem().getColorFromItemStack(itemstack, 0);
		float f4 = (float)(i >> 16 & 255) / 255.0F;
		float f5 = (float)(i >> 8 & 255) / 255.0F;
		f2 = (float)(i & 255) / 255.0F;
		GL11.glColor4f(f4, f5, f2, 1.0F);
		renderIcon(0, 0, itemstack.getItem().getIconIndex(itemstack), 16, 16);*/

		IIcon iicon = entity.getItemIcon(itemstack, 0);

		if (iicon == null)
		{
			GL11.glPopMatrix();
			return;
		}

		texturemanager.bindTexture(texturemanager.getResourceLocation(itemstack.getItemSpriteNumber()));
		TextureUtil.func_152777_a(false, false, 1.0F);
		Tessellator tessellator = Tessellator.instance;
		float f = iicon.getMinU();
		float f1 = iicon.getMaxU();
		float f2 = iicon.getMinV();
		float f3 = iicon.getMaxV();
		float f4 = 0.0F;
		float f5 = 0.3F;
		GL11.glEnable(GL12.GL_RESCALE_NORMAL);
		GL11.glTranslatef(-f4, -f5, 0.0F);
		float f6 = 1.5F;
		GL11.glScalef(f6, f6, f6);
		GL11.glRotatef(50.0F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(335.0F, 0.0F, 0.0F, 1.0F);
		GL11.glTranslatef(-0.9375F, -0.0625F, 0.0F);
		ItemRenderer.renderItemIn2D(tessellator, f1, f2, f, f3, iicon.getIconWidth(), iicon.getIconHeight(), 0.0625F);

		//RenderManager.instance.itemRenderer.renderItem((EntityLivingBase)data[1], itemstack, 0);
	}

	public static void renderIcon(int x, int y, IIcon icon, int sizeX, int sizeY)
	{
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + sizeY), (double)0, (double)icon.getMinU(), (double)icon.getMaxV());
		tessellator.addVertexWithUV((double)(x + sizeX), (double)(y + sizeY), (double)0, (double)icon.getMaxU(), (double)icon.getMaxV());
		tessellator.addVertexWithUV((double)(x + sizeX), (double)(y + 0), (double)0, (double)icon.getMaxU(), (double)icon.getMinV());
		tessellator.addVertexWithUV((double)(x + 0), (double)(y + 0), (double)0, (double)icon.getMinU(), (double)icon.getMinV());
		tessellator.draw();
	}

	private static void renderQuad(double x, double y, double sizeX, double sizeY, int color)
	{
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		Tessellator tess = Tessellator.instance;
		tess.startDrawingQuads();
		tess.setColorOpaque_I(color);
		tess.addVertex((double)(x + 0), (double)(y + 0), 0.0D);
		tess.addVertex((double)(x + 0), (double)(y + sizeY), 0.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + sizeY), 0.0D);
		tess.addVertex((double)(x + sizeX), (double)(y + 0), 0.0D);
		tess.draw();
		GL11.glEnable(GL11.GL_TEXTURE_2D);
	}

}
