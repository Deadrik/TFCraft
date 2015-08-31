package com.bioxx.tfc.Render.TESR;

import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelBox;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.TileEntities.TEHopper;
import com.bioxx.tfc.api.TFCBlocks;

public class TESRHopper extends TESRBase
{
	//private ModelPress press = new ModelPress();
	public TESRHopper()
	{
		super();


	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TEHopper te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() != null)
		{
			if(te.pressBlock != null)
			{
				float sink = -0.34f + (te.pressCooldown/20/800f)*0.25f;
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.5F, (float)d1 + 1.33F, (float)d2 + 0.5F); //starting location
				GL11.glScalef(0.75f, 0.75f, 0.75f);
				GL11.glTranslatef(0,sink,0); //
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				GL11.glDisable(GL11.GL_LIGHTING);
				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				TESRBase.renderBlocks.setRenderBoundsFromBlock(TFCBlocks.stoneIgInSmooth);
				renderPress(Block.getBlockFromItem(te.pressBlock.getItem()), te.getWorldObj(), (int)d, (int)d1, (int)d2, te.pressBlock.getItemDamage());
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glPopMatrix(); //end
			}
		}
	}

	public void renderPress(Block block, World world, int x, int y, int z, int meta)
	{
		float f = 0.5F;
		float f1 = 1.0F;
		float f2 = 0.8F;
		float f3 = 0.6F;
		Tessellator tessellator = Tessellator.instance;
		tessellator.startDrawingQuads();
		tessellator.setColorOpaque_F(f, f, f);
		renderBlocks.renderFaceYNeg(block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(block, 0, meta));
		tessellator.setColorOpaque_F(f1, f1, f1);
		renderBlocks.renderFaceYPos(block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(block, 1, meta));
		tessellator.setColorOpaque_F(f2, f2, f2);
		renderBlocks.renderFaceZNeg(block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(block, 2, meta));
		tessellator.setColorOpaque_F(f2, f2, f2);
		renderBlocks.renderFaceZPos(block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(block, 3, meta));
		tessellator.setColorOpaque_F(f3, f3, f3);
		renderBlocks.renderFaceXNeg(block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(block, 4, meta));
		tessellator.setColorOpaque_F(f3, f3, f3);
		renderBlocks.renderFaceXPos(block, -0.5D, -0.5D, -0.5D, renderBlocks.getBlockIconFromSideAndMetadata(block, 5, meta));
		tessellator.draw();
	}



	@Override
	public void renderTileEntityAt(TileEntity te, double xDis, double yDis, double zDis, float f)
	{
		this.renderAt((TEHopper)te, xDis, yDis, zDis, f);
	}

	private class ModelPress extends ModelBase
	{
		private ModelBox pressModel;
		private ModelRenderer renderer;

		public ModelPress()
		{
			renderer = new ModelRenderer(this);
			//ModelRenderer renderer, int textureOffsetX, int textureOffsetY,float originX, float originY, float originZ, int width, int height, int depth,float scale
			pressModel = new ModelBox(renderer, 0, 0, 0.5F, 0, 0.5F, 15, 4, 7, 1);
			renderer.cubeList.add(pressModel);
		}

		@Override
		public void render(Entity entity, float x, float y, float z, float maxX, float maxY, float maxZ) 
		{
			renderer = new ModelRenderer(this);

			pressModel = new ModelBox(renderer, 0, 0, 2F, 16, 2F, 12, 12, 12, 0f);
			renderer.cubeList.add(pressModel);


			this.renderer.render(0.0625f);
		}
	}
}
