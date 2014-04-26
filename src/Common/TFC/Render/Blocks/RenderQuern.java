package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import TFC.TFCBlocks;
import TFC.TileEntities.TileEntityQuern;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderQuern implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderblocks)
	{
		if (modelId == TFCBlocks.quernRenderId)
		{
			IBlockAccess blockAccess = renderblocks.blockAccess;
			TileEntityQuern te = (TileEntityQuern)blockAccess.getTileEntity(i, j, k);
			if(te != null)
			{
				if(te.hasQuern)
				{
					renderblocks.setRenderBounds(0.0F, 0F, 0.0F, 1F, 0.825F, 1F);
					renderblocks.renderStandardBlock(block, i, j, k);
					renderblocks.overrideBlockTexture = TFCBlocks.Planks.getIcon(0, 0);
					float pos = te.rotatetimer * 0.035F;

					if(te.rotation == 0) {
						renderblocks.setRenderBounds(0.8F, 0.8, 0.8F-pos, 0.9F, 1, 0.9F-pos);
						renderblocks.renderStandardBlock(block, i, j, k);
					}
					else if(te.rotation == 1) {
						renderblocks.setRenderBounds(0.8F-pos, 0.8, 0.1F, 0.9F-pos, 1, 0.2F);
						renderblocks.renderStandardBlock(block, i, j, k);
					}
					else if(te.rotation == 2) {
						renderblocks.setRenderBounds(0.1F, 0.8, 0.1F+pos, 0.2F, 1, 0.2F+pos);
						renderblocks.renderStandardBlock(block, i, j, k);
					}
					else if(te.rotation == 3) {
						renderblocks.setRenderBounds(0.1F+pos, 0.8, 0.8F, 0.2F+pos, 1, 0.9F);
						renderblocks.renderStandardBlock(block, i, j, k);
					}
				}
				else {
					renderblocks.setRenderBounds(0.0F, 0, 0.0F, 1F, 0.625, 1F);
					renderblocks.renderStandardBlock(block, i, j, k);
				}
			}
			renderblocks.clearOverrideBlockTexture();
			return true;
		}
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		renderer.setRenderBounds(0, 0, 0, 1, 0.625, 1);
		Tessellator var14 = Tessellator.instance;
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 1.0F, 0.0F);
		renderer.renderFaceYPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, 1));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, -1.0F);
		renderer.renderFaceXNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(0.0F, 0.0F, 1.0F);
		renderer.renderFaceXPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(-1.0F, 0.0F, 0.0F);
		renderer.renderFaceZNeg(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, 0));
		var14.draw();
		var14.startDrawingQuads();
		var14.setNormal(1.0F, 0.0F, 0.0F);
		renderer.renderFaceZPos(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, 0));
		var14.draw();
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return true;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}
}
