package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import TFC.TFCBlocks;
import TFC.API.IMultipleBlock;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBarrel  implements ISimpleBlockRenderingHandler 
{
	static float pixel3 = 3f/16f;
	static float pixel5 = 5f/16f;
	static float pixel12 = 12f/16f;
	static float pixel14 = 14f/16f;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k,
			Block block, int modelId, RenderBlocks renderer) 
	{
		Block blockToRender;
		blockToRender = ((IMultipleBlock)block).getBlockTypeForRender();

		IBlockAccess blockAccess = renderer.blockAccess;
		renderer.setRenderBounds(0.15F, 0.1F, 0.15F, 0.85F, 0.9F, 0.85F);
		renderer.renderStandardBlock(blockToRender, i, j, k);

		renderer.setRenderBounds(0.1F, 0F, 0.15F, 0.15F, 1F, 0.85F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(blockToRender, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);

		renderer.setRenderBounds(0.85F, 0F, 0.15F, 0.9F, 1F, 0.85F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(blockToRender, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);

		renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.9F, 1F, 0.15F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(blockToRender, i, j, k);
		rotate(renderer, 0);
		renderer.renderStandardBlock(block, i, j, k);

		renderer.setRenderBounds(0.1F, 0F, 0.85F, 0.9F, 1F, 0.9F);
		rotate(renderer, 1);
		renderer.renderStandardBlock(blockToRender, i, j, k);
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
		Block blockToRender;
		blockToRender = ((IMultipleBlock)block).getBlockTypeForRender();

		renderer.setRenderBounds(0.15F, 0.2F, 0.15F, 0.85F, 0.8F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(blockToRender, metadata, renderer);

		renderer.setRenderBounds(0.1F, 0F, 0.15F, 0.15F, 1F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(blockToRender, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(0.85F, 0F, 0.15F, 0.9F, 1F, 0.85F);
		rotate(renderer, 1);
		renderInvBlock(blockToRender, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.9F, 1F, 0.15F);
		rotate(renderer, 1);
		renderInvBlock(blockToRender, metadata, renderer);
		rotate(renderer, 0);
		renderInvBlock(block, metadata, renderer);

		renderer.setRenderBounds(0.1F, 0F, 0.85F, 0.9F, 1F, 0.9F);
		rotate(renderer, 1);
		renderInvBlock(blockToRender, metadata, renderer);
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

	public static void renderInvBlock(Block block, int m, RenderBlocks renderer)
	{
		Tessellator var14 = Tessellator.instance;
		GL11.glTranslatef(-0.5F, -0.5F, -0.5F);
		var14.startDrawingQuads();
		var14.setNormal(0.0F, -1.0F, 0.0F);
		renderer.renderFaceYNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(0, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, m));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, m));
		var14.draw();
		GL11.glTranslatef(0.5F, 0.5F, 0.5F);
	}
}
