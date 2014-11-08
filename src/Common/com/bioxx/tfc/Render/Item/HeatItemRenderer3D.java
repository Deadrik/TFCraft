package com.bioxx.tfc.Render.Item;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Food.ItemFoodTFC;
import com.bioxx.tfc.Items.ItemBloom;
import com.bioxx.tfc.Items.ItemIngot;
import com.bioxx.tfc.Items.ItemMetalSheet;
import com.bioxx.tfc.Items.ItemUnfinishedArmor;
import com.bioxx.tfc.Items.ItemBlocks.ItemSoil;
import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.Render.Item.HeatItemRenderer.HeatItemDetails;
import com.bioxx.tfc.api.Food;
import com.bioxx.tfc.api.HeatRegistry;
import com.bioxx.tfc.api.TFC_ItemHeat;
import com.bioxx.tfc.api.Interfaces.IFood;

public class HeatItemRenderer3D implements IItemRenderer
{
	private Tessellator tessellator = Tessellator.instance;
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) 
	{
		switch (type) 
		{ 
			case INVENTORY: 
				return true; 
			default: 
				return false; 
		} 
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item, ItemRendererHelper helper) 
	{	
		switch (type) 
		{ 
			case INVENTORY: 
				return (helper == ItemRendererHelper.INVENTORY_BLOCK);
			default: 
				return false; 
		} 
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) 
	{
		if(type != ItemRenderType.INVENTORY)
		{
			System.out.println("HeatItemRenderer.renderItem called with wrong render type: " + type.toString());
			return;
		}

		tessellator.startDrawingQuads();
		
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		
		IIcon icon;
		
		// Renders the given texture to the east (x-positive) face of the block.
		icon = item.getItem().getIconFromDamage(5);
		tessellator.setNormal(1.0F, 0.0F, 0.0F);
	    tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV()); 
	    tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV()); 
	    tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMinV()); 
	    tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV()); 
		
	    // Renders the given texture to the west (x-negative) face of the block.
		icon = item.getItem().getIconFromDamage(4);
		tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV()); 
		tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV()); 
		
		// Renders the given texture to the south (z-positive) face of the block.
		icon = item.getItem().getIconFromDamage(3); 
		tessellator.setNormal(0.0F, 0.0F, 1.0F); 
		tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMaxV()); 
		tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMaxV()); 

		// Renders the given texture to the north (z-negative) face of the block.
		icon = item.getItem().getIconFromDamage(2); 
		tessellator.setNormal(0.0F, 0.0F, -1.0F); 
		tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV()); 
		tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV()); 
		
		// Renders the given texture to the top face of the block.
		icon = item.getItem().getIconFromDamage(1); 
		tessellator.setNormal(0.0F, 1.0F, 0.0F); 
		tessellator.addVertexWithUV(1.0, 1.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV()); 
		tessellator.addVertexWithUV(1.0, 1.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(0.0, 1.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(0.0, 1.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV()); 
		
		// Renders the given texture to the bottom face of the block.
		icon = item.getItem().getIconFromDamage(0); 
		tessellator.setNormal(0.0F, -1.0F, 0.0F); 
		tessellator.addVertexWithUV(0.0, 0.0, 1.0, (double)icon.getMaxU(), (double)icon.getMaxV()); 
		tessellator.addVertexWithUV(0.0, 0.0, 0.0, (double)icon.getMaxU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(1.0, 0.0, 0.0, (double)icon.getMinU(), (double)icon.getMinV()); 
		tessellator.addVertexWithUV(1.0, 0.0, 1.0, (double)icon.getMinU(), (double)icon.getMaxV()); 
		 
		tessellator.draw(); 
				
		GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
		GL11.glTranslatef(-0.7F, 1.55F, 0.5F);
		
		renderHeatBar(type, item);
	}

	private void renderHeatBar(ItemRenderType type, ItemStack item)
	{
		if(type != ItemRenderType.INVENTORY) return;

		GL11.glDisable(GL11.GL_TEXTURE_2D);

		HeatItemDetails details = new HeatItemDetails(item);
		if (details.hasTemp && details.range > 0)
		{
			renderQuadWithNormal(0.0, 0.10, 1.0, 0.12, 0x000000, 0.0F, 0.0F, -1.0F);

			int tempValue = details.range > 0 ? 2 : 0;
			tempValue += (2 * details.subRange);
			
			if (tempValue < 0) tempValue = 0;
			if (tempValue > 10) tempValue = 10;
			renderQuadWithNormal(0.0, 0.10, tempValue * 0.1, 0.12, details.color, 0.0F, 0.0F, -1.0F);

			if (details.isWorkable || details.isWeldable || details.isInDanger)
			{
				renderQuadWithNormal(1.1, 0.10, 0.3, 0.12, 0x000000, 0.0F, 0.0F, -1.0F);

				if (details.isWorkable)
					renderQuadWithNormal(1.1, 0.10, 0.1, 0.12, 0x00ff00, 0.0F, 0.0F, -1.0F);
				if (details.isWeldable)
					renderQuadWithNormal(1.2, 0.10, 0.1, 0.12, 0xffaa00, 0.0F, 0.0F, -1.0F);
				if (details.isInDanger)
					renderQuadWithNormal(1.3, 0.10, 0.1, 0.12, 0xff0000, 0.0F, 0.0F, -1.0F);					
			}
		}

		GL11.glEnable(GL11.GL_TEXTURE_2D);
}

	private void renderQuadWithNormal(double x, double y, double sizeX, double sizeY, int color, float xNormal, float yNormal, float zNormal)
	{
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_I(color);
		tessellator.setNormal(xNormal, yNormal, zNormal);
		tessellator.addVertex((double)(x + sizeX), (double)(y + 0),     1.0D);
		tessellator.addVertex((double)(x + sizeX), (double)(y + sizeY), 1.0D);
		tessellator.addVertex((double)(x + 0),     (double)(y + sizeY), 1.0D);
		tessellator.addVertex((double)(x + 0),     (double)(y + 0),     1.0D);
		tessellator.draw();
	}
}
