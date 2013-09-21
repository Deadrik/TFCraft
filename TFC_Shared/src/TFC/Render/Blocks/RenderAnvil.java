package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.world.IBlockAccess;
import TFC.TFCBlocks;
import TFC.API.Crafting.AnvilReq;
import TFC.Blocks.Devices.BlockAnvil;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityAnvil;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderAnvil implements ISimpleBlockRenderingHandler{

	public static boolean renderAnvil(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		int meta = blockAccess.getBlockMetadata(i, j, k);
		int direction = ((BlockAnvil)block).getDirectionFromMetadata(meta);
		renderblocks.renderAllFaces = true;

		boolean breaking = false;
		if(renderblocks.overrideBlockTexture != null)
		{
			breaking = true;
		}

		TileEntityAnvil te = (TileEntityAnvil)blockAccess.getBlockTileEntity(i, j, k);

		if(te.AnvilTier != AnvilReq.STONE.Tier)
		{
			if(direction == 0)//x
			{
				//top
				renderblocks.setRenderBounds(0.3F, 0.4F, 0.1F, 0.7F, 0.6F, 0.9F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//core
				renderblocks.setRenderBounds(0.35F, 0.0F, 0.15F, 0.65F, 0.4F, 0.85F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//feet
				renderblocks.setRenderBounds(0.25F, 0.0F, 0.1F, 0.75F, 0.2F, 0.90F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.20F, 0.0F, 0.0F, 0.80F, 0.1F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//block.setBlockBounds(0.2F, 0.0F, 0.0F, 0.80F, 0.6F, 1.0F);
			}
			else if(direction == 1)//z
			{
				//top
				renderblocks.setRenderBounds(0.1F, 0.4F, 0.3F, 0.9F, 0.6F, 0.7F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//core
				renderblocks.setRenderBounds(0.15F, 0.0F, 0.35F, 0.85F, 0.4F, 0.65F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//feet
				renderblocks.setRenderBounds(0.1F, 0.0F, 0.25F, 0.90F, 0.2F, 0.75F);
				renderblocks.renderStandardBlock(block, i, j, k);
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.20F, 1.00F, 0.1F, 0.80F);
				renderblocks.renderStandardBlock(block, i, j, k);

				//block.setBlockBounds(0.0F, 0.0F, 0.20F, 1.0F, 0.6F, 0.8F);
			}	
		}
		else
		{
			if(Block.blocksList[te.stonePair[0]] != null)
			{
				if(!breaking)
				{
					//ForgeHooksClient.bindTexture(TFC_Textures.RockSheet, ModLoader.getMinecraftInstance().renderEngine.getTexture(TFC_Textures.RockSheet));
					renderblocks.overrideBlockTexture = Block.blocksList[te.stonePair[0]].getIcon(0, te.stonePair[1]);
				}
				renderblocks.setRenderBounds(0.0F, 0.0F, 0.00F, 1.0F, 0.9F, 1.0F);
				renderblocks.renderStandardBlock(block, i, j, k);
				if(te.anvilItemStacks[0] != null && !breaking)
				{

					//					block.setBlockBounds(0.0F, 0.9F, 0.0F, 1F, 0.901F, 1F);
					//					renderblocks.renderStandardBlock(block, i, j, k);
					Tessellator tessellator = Tessellator.instance;
					int state = tessellator.drawMode;
					tessellator.draw();
					tessellator.startDrawingQuads();
					TFC_Core.bindTexture(TextureMap.locationItemsTexture);
					renderblocks.overrideBlockTexture = te.anvilItemStacks[0].getIconIndex();
					tessellator.setBrightness(block.getMixedBrightnessForBlock(blockAccess, i, j, k));
					tessellator.setColorRGBA_F(1, 1, 1, 1);

					double minX = renderblocks.overrideBlockTexture.getMinU();
					double maxX = renderblocks.overrideBlockTexture.getMaxU();
					double minZ = renderblocks.overrideBlockTexture.getMinV();
					double maxZ = renderblocks.overrideBlockTexture.getMaxV();

					tessellator.addTranslation(0.5f, 0f, 0.5f);

					tessellator.addVertexWithUV(i, j + 0.901, k + 0.4, minX, maxZ);
					tessellator.addVertexWithUV(i + 0.4, j + 0.901, k + 0.4, maxX, maxZ);
					tessellator.addVertexWithUV(i + 0.4, j + 0.901, k, maxX, minZ);
					tessellator.addVertexWithUV(i, j + 0.901, k, minX, minZ);

					tessellator.addTranslation(-0.5f, 0f, -0.5f);
					tessellator.draw();				
					tessellator.startDrawing(state);

				}
				TFC_Core.bindTexture(TextureMap.locationBlocksTexture);
				renderblocks.clearOverrideBlockTexture();
			}
		}
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelId,
			RenderBlocks renderer) 
	{
		if(modelId == TFCBlocks.AnvilRenderId)
		{
			renderer.setRenderBounds(0.3F, 0.4F, 0.1F, 0.7F, 0.6F, 0.9F);
			renderInvBlock(block, metadata, renderer);

			//core
			renderer.setRenderBounds(0.35F, 0.0F, 0.15F, 0.65F, 0.4F, 0.85F);
			renderInvBlock(block, metadata, renderer);

			//feet
			renderer.setRenderBounds(0.25F, 0.0F, 0.1F, 0.75F, 0.2F, 0.90F);
			renderInvBlock(block, metadata, renderer);
			renderer.setRenderBounds(0.20F, 0.0F, 0.0F, 0.80F, 0.1F, 1.0F);
			renderInvBlock(block, metadata, renderer);
		}
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) 
	{
		if(modelId == TFCBlocks.AnvilRenderId)
		{
			return renderAnvil(block,x,y,z,renderer);
		}
		return false;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return 0;
	}

	public static void renderInvBlock(Block block, int m, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		int meta = m;
		if(meta >=8)
		{
			meta-=8;
		}
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, meta));
		var14.draw();
	}
}

