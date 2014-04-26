package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.world.IBlockAccess;
import TFC.TFCBlocks;
import TFC.TileEntities.TileEntityWoodConstruct;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderWoodConstruct implements ISimpleBlockRenderingHandler
{
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderer)
	{
		renderOld(i, j, k, block, renderer);
		return true;
	}

	private void renderOld(int i, int j, int k, Block block, RenderBlocks renderer)
	{
		TileEntityWoodConstruct te = (TileEntityWoodConstruct) renderer.blockAccess.getTileEntity(i, j, k);
		int md = renderer.blockAccess.getBlockMetadata(i, j, k);

		int d = te.PlankDetailLevel;
		int dd = te.PlankDetailLevel * te.PlankDetailLevel;
		int dd2 = dd*2;

		float div = 1f / d;

		boolean breaking = false;
		if(renderer.overrideBlockTexture != null)
			breaking = true;

		for(int index = 0; index < dd; index++)
		{
			if(te.data.get(index))
			{
				float minX = 0;
				float maxX = 1;
				float minY = div * (index & 7);
				float maxY = minY + div;
				float minZ = div * (index >> 3);
				float maxZ = minZ + div;

				if(!breaking)
					renderer.overrideBlockTexture = TFCBlocks.WoodConstruct.getIcon(0, te.woodTypes[index]);
				renderer.uvRotateTop = 3;
				renderer.uvRotateBottom = 3;
				renderer.setRenderBounds(minX-0.001d, minY-0.001d, minZ-0.001d, maxX+0.001d, maxY+0.001d, maxZ+0.001d);
				renderer.renderStandardBlock(block, i, j, k);
			}
		}
		//Fix the rotations
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		for(int index = 0; index < dd; index++)
		{
			if(te.data.get(index + dd))
			{
				float minX = div * (index & 7);
				float maxX = minX + div;
				float minY = 0;
				float maxY = 1;
				float minZ = div * (index >> 3);
				float maxZ = minZ + div;

				if(!breaking)
					renderer.overrideBlockTexture = TFCBlocks.WoodConstruct.getIcon(0, te.woodTypes[index+dd]);
				renderer.uvRotateNorth = 1;
				renderer.uvRotateSouth = 1;
				renderer.uvRotateEast = 1;
				renderer.uvRotateWest = 1;
				renderer.setRenderBounds(minX, minY, minZ, maxX, maxY, maxZ);
				renderer.renderStandardBlock(block, i, j, k);
			}
		}

		//Fix the rotations
		renderer.uvRotateNorth = 0;
		renderer.uvRotateSouth = 0;
		renderer.uvRotateEast = 0;
		renderer.uvRotateWest = 0;

		for(int z = 0; z < dd; z++)
		{
			if(te.data.get(z+dd2))
			{
				float minX = div * (z & 7);
				float maxX = minX + div;
				float minY = div * (z >> 3);
				float maxY = minY + div;
				float minZ = 0;
				float maxZ = 1;

				if(!breaking)
					renderer.overrideBlockTexture = TFCBlocks.WoodConstruct.getIcon(0, te.woodTypes[z+dd2]);
				renderer.uvRotateTop = 1;
				renderer.uvRotateBottom = 1;
				renderer.setRenderBounds(minX+0.001d, minY+0.001d, minZ+0.001d, maxX-0.001d, maxY-0.001d, maxZ-0.001d);
				renderer.renderStandardBlockWithColorMultiplier(block, i, j, k, 1, 1, 1);
			}
		}
		renderer.uvRotateTop = 0;
		renderer.uvRotateBottom = 0;
		renderer.clearOverrideBlockTexture();
	}

	@Override
	public boolean shouldRender3DInInventory(int modelId)
	{
		return false;
	}

	@Override
	public int getRenderId()
	{
		return 0;
	}
}
