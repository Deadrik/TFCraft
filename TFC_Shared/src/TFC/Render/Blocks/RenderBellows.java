package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import TFC.TFCBlocks;
import TFC.Blocks.Devices.BlockBellows;
import TFC.TileEntities.TileEntityBellows;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderBellows implements ISimpleBlockRenderingHandler {

	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer) {
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z, Block block, int modelId, RenderBlocks renderer) {
		if(modelId == TFCBlocks.BellowsRenderId) {
			IBlockAccess blockAccess = renderer.blockAccess;
			int meta = blockAccess.getBlockMetadata(x, y, z);
			int direction = ((BlockBellows)block).getDirectionFromMetadata(meta);
			TileEntityBellows te = (TileEntityBellows)blockAccess.getBlockTileEntity(x, y, z);
			float pos = te.blowTimer * 0.1F;

			if(te != null) {
				boolean breaking = false;
				if(renderer.overrideBlockTexture != null) {
					breaking = true;
				}
				if(direction == 0)
				{
					//forward
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsFront;
					renderer.setRenderBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					//mid
					if(!breaking) renderer.clearOverrideBlockTexture();
					renderer.setRenderBounds(0.1F, 0.1F, 0.05F+pos, 0.9F, 0.9F, 0.95F);
					renderer.renderStandardBlock(block, x, y, z);
					//back
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsBack;
					renderer.setRenderBounds(0.0F, 0.0F, pos, 1.0F, 1.0F, 0.1F+pos);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.clearOverrideBlockTexture();
				}
				else if(direction == 1)
				{
					//forward
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsFront;
					renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					//mid
					if(!breaking) renderer.clearOverrideBlockTexture();
					renderer.setRenderBounds(0.1F, 0.1F, 0.1F, 0.9F-pos, 0.9F, 0.9F);
					renderer.renderStandardBlock(block, x, y, z);
					//back
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsBack;
					renderer.setRenderBounds(0.9F-pos, 0.0F, 0.0F, 1.0F-pos, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.clearOverrideBlockTexture();
				}
				else if(direction == 2)
				{
					//forward
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsFront;
					renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
					renderer.renderStandardBlock(block, x, y, z);
					//mid
					if(!breaking) renderer.clearOverrideBlockTexture();
					renderer.setRenderBounds(0.1F, 0.1F, 0.05F, 0.9F, 0.9F, 0.95F-pos);
					renderer.renderStandardBlock(block, x, y, z);
					//back
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsBack;
					renderer.setRenderBounds(0.0F, 0.0F, 0.9F-pos, 1.0F, 1.0F, 1.0F-pos);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.clearOverrideBlockTexture();
				}
				else if(direction == 3)
				{
					//forward
					if(!breaking)  renderer.overrideBlockTexture = BlockBellows.BellowsFront;
					renderer.setRenderBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					//mid
					if(!breaking) renderer.clearOverrideBlockTexture();
					renderer.setRenderBounds(0.1F+pos, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
					renderer.renderStandardBlock(block, x, y, z);
					//back
					if(!breaking) renderer.overrideBlockTexture = BlockBellows.BellowsBack;
					renderer.setRenderBounds(pos, 0.0F, 0.0F, 0.1F+pos, 1.0F, 1.0F);
					renderer.renderStandardBlock(block, x, y, z);
					renderer.clearOverrideBlockTexture();
				}
				renderer.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			}
		}
		return true;
	}

	@Override
	public int getRenderId() {
		return 0;
	}
}
