package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import TFC.TFCBlocks;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBarrel  implements ISimpleBlockRenderingHandler 
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k,
			Block block, int modelId, RenderBlocks renderer) 
	{
			IBlockAccess blockAccess = renderer.blockAccess;
			renderer.setRenderBounds(0.15F, 0.1F, 0.15F, 0.85F, 0.9F, 0.85F);
			renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
			
			renderer.setRenderBounds(0.1F, 0F, 0.15F, 0.15F, 1F, 0.85F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
			rotate(renderer, 0);
			renderer.renderStandardBlock(block, i, j, k);
			
			renderer.setRenderBounds(0.85F, 0F, 0.15F, 0.9F, 1F, 0.85F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
			rotate(renderer, 0);
			renderer.renderStandardBlock(block, i, j, k);
			
			renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.9F, 1F, 0.15F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
			rotate(renderer, 0);
			renderer.renderStandardBlock(block, i, j, k);
			
			renderer.setRenderBounds(0.1F, 0F, 0.85F, 0.9F, 1F, 0.9F);
			rotate(renderer, 1);
			renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
			rotate(renderer, 0);
			renderer.renderStandardBlock(block, i, j, k);
			
			return true;
	}
	
	public void rotate(RenderBlocks renderer, int i)
	{
		renderer.uvRotateEast = i;
		renderer.uvRotateWest = i;
		renderer.uvRotateNorth = i;
		renderer.uvRotateSouth = i;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		
		renderer.setRenderBounds(0.15F, 0.2F, 0.15F, 0.85F, 0.8F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);
		
		renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.15F, 1F, 0.9F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);
		
		renderer.setRenderBounds(0.85F, 0F, 0.1F, 0.9F, 1F, 0.9F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);
		
		renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.15F, 1F, 0.9F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);
		
		renderer.setRenderBounds(0.85F, 0F, 0.1F, 0.9F, 1F, 0.9F);
		rotate(renderer, 1);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);
	}
	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return true;
	}
	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return 0;
	}
	
	public static void renderInvBlock(Block block, int meta, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderBottomFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(0, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(1, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(2, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(3, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(4, meta));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getBlockTextureFromSideAndMetadata(5, meta));
		var14.draw();
	}
}
