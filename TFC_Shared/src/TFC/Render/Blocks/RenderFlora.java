package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Icon;

public class RenderFlora
{
	public static boolean render(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		int meta = renderer.blockAccess.getBlockMetadata(x, y, z);

		switch(meta)
		{
		case 0:
			renderer.renderCrossedSquares(block, x, y, z);
			break;
		case 1:
			renderCatTails(block,x,y,z,renderer);
			break;
		}

		return true;
	}

	public static void renderCatTails(Block block, int x, int y, int z, RenderBlocks renderer)
	{
		Tessellator tessellator = Tessellator.instance;
		int meta = renderer.blockAccess.getBlockMetadata(x, y, z);
        Icon icon = block.getBlockTextureFromSideAndMetadata(0, meta);
        
        double minU = icon.getMinU();
        double minV = icon.getMinV();
        double maxU = icon.getMaxU();
        double maxV = icon.getMaxV();
        
        double minX = x + 0.5D - 0.25D;
        double maxX = x + 0.5D + 0.25D;
        double minZ = z + 0.5D - 0.75D;
        double maxZ = z + 0.5D + 0.75D;
        
        tessellator.addVertexWithUV(minX, y + 1.0D, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, y - 1.0D, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, y - 1.0D, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, y + 1.0D, maxZ, maxU, minV);
        tessellator.addVertexWithUV(minX, y + 1.0D, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, y - 1.0D, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, y - 1.0D, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, y + 1.0D, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, y + 1.0D, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, y - 1.0D, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, y - 1.0D, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, y + 1.0D, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, y + 1.0D, minZ, minU, minV);
        tessellator.addVertexWithUV(maxX, y - 1.0D, minZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, y - 1.0D, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, y + 1.0D, maxZ, maxU, minV);
        minX = x + 0.5D - 0.75D;
        maxX = x + 0.5D + 0.75D;
        minZ = z + 0.5D - 0.25D;
        maxZ = z + 0.5D + 0.25D;
        tessellator.addVertexWithUV(minX, y + 1.0D, minZ, minU, minV);
        tessellator.addVertexWithUV(minX, y - 1.0D, minZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, y - 1.0D, minZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, y + 1.0D, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, y + 1.0D, minZ, minU, minV);
        tessellator.addVertexWithUV(maxX, y - 1.0D, minZ, minU, maxV);
        tessellator.addVertexWithUV(minX, y - 1.0D, minZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, y + 1.0D, minZ, maxU, minV);
        tessellator.addVertexWithUV(maxX, y + 1.0D, maxZ, minU, minV);
        tessellator.addVertexWithUV(maxX, y - 1.0D, maxZ, minU, maxV);
        tessellator.addVertexWithUV(minX, y - 1.0D, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(minX, y + 1.0D, maxZ, maxU, minV);
        tessellator.addVertexWithUV(minX, y + 1.0D, maxZ, minU, minV);
        tessellator.addVertexWithUV(minX, y - 1.0D, maxZ, minU, maxV);
        tessellator.addVertexWithUV(maxX, y - 1.0D, maxZ, maxU, maxV);
        tessellator.addVertexWithUV(maxX, y + 1.0D, maxZ, maxU, minV);
	}
}
