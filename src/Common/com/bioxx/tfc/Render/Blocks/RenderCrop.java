package com.bioxx.tfc.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Blocks.BlockCrop;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TEFarmland;

public class RenderCrop 
{
	public static boolean render(Block block, int x, int y, int z, RenderBlocks renderblocks)
	{
		IBlockAccess blockaccess = renderblocks.blockAccess;
		TECrop cropTE = (TECrop)blockaccess.getTileEntity(x, y, z);

		if(cropTE != null)
			CropManager.getInstance().getCropFromId(cropTE.cropId);
		else
			return false;

		Tessellator var9 = Tessellator.instance;
		var9.setBrightness(block.getMixedBrightnessForBlock(blockaccess, x, y, z));
		switch(cropTE.cropId)
		{
		case 0://Wheat
		{
			renderBlockCropsImpl(block, x, y, z, renderblocks, 0.5, 1.0);
			break;
		}
		case 1://Corn
		{
			renderBlockCropsImpl(block, x, y, z, renderblocks, 1.0, 2.0);
			break;
		}
		case 2://Tomatoes
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.9, 2.0);
			break;
		}
		case 3://Barley
		{
			renderBlockCropsImpl(block, x, y, z, renderblocks, 0.5, 1.0);
			break;
		}
		case 4://Rye
		{
			renderBlockCropsImpl(block, x, y, z, renderblocks, 0.5, 1.0);
			break;
		}
		case 5://Oat
		{
			renderBlockCropsImpl(block, x, y, z, renderblocks, 0.5, 1.0);
			break;
		}
		case 6://Rice
		{
			renderBlockCropsImpl(block, x, y, z, renderblocks, 0.5, 1.0);
			break;
		}
		case 7://Potato
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 8://Onion
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 9://Cabbage
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 10://Garlic
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 11://Carrots
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 12://Yellow Bell
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 13://Red Bell
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 14://Soybean
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 15://Greenbean
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 16://Squash
		{
			drawCrossedSquares(block, x, y, z, renderblocks, 0.45, 1.0);
			break;
		}
		case 17://Jute
		{
			renderBlockCropsImpl(block, x, y, z, renderblocks, 0.8, 2.0);
			break;
		}
		case 18://Sugarcane
		{
			renderBlockCropsImpl(block, x, y, z, renderblocks, 1.0, 2.0);
			break;
		}
		default:
		{
			renderblocks.renderBlockCrops(block, x, y, z);
			break;
		}
		}
		TileEntity te = blockaccess.getTileEntity(x, y-1, z);
		TEFarmland tef = null;
		if (te instanceof TEFarmland)
			tef = (TEFarmland) te;
		if(tef != null && tef.isInfested)
		{

			Tessellator tessellator = Tessellator.instance;
			//tessellator.startDrawingQuads();
			//tessellator.setNormal(0.0F, 1.0F, 0.0F);
			tessellator.addVertexWithUV(x+0, y+0.001, z+1, ((BlockCrop)block).iconInfest.getMinU(), ((BlockCrop)block).iconInfest.getMaxV());
			tessellator.addVertexWithUV(x+1, y+0.001, z+1, ((BlockCrop)block).iconInfest.getMaxU(), ((BlockCrop)block).iconInfest.getMaxV());
			tessellator.addVertexWithUV(x+1, y+0.001, z+0, ((BlockCrop)block).iconInfest.getMaxU(), ((BlockCrop)block).iconInfest.getMinV());
			tessellator.addVertexWithUV(x+0, y+0.001, z+0, ((BlockCrop)block).iconInfest.getMinU(), ((BlockCrop)block).iconInfest.getMinV());
			//tessellator.draw();

		}
		return true;
	}

	private static void renderBlockCropsImpl(Block block, double i, double j, double k, RenderBlocks renderblocks, double width, double height)
	{
		Tessellator tess = Tessellator.instance;
		GL11.glColor3f(1, 1, 1);
		int brightness = block.getMixedBrightnessForBlock(renderblocks.blockAccess, (int)i, (int)j, (int)k);
		tess.setBrightness(brightness);
		tess.setColorOpaque_F(1, 1, 1);

		IIcon icon = block.getIcon(renderblocks.blockAccess, (int)i, (int)j, (int)k, renderblocks.blockAccess.getBlockMetadata((int)i, (int)j, (int)k));
		if (renderblocks.hasOverrideBlockTexture())
			icon = renderblocks.overrideBlockTexture;

		if(icon != null)
		{
			if(((int)i & 1) > 0)
			{
				k+=0.0001;
			}
			if(((int)k & 1) > 0)
			{
				i+=0.0001;
			}

			double minU = icon.getMinU();
			double maxU = icon.getMaxU();
			double minV = icon.getMinV();
			double maxV = icon.getMaxV();
			double minX = i + 0.25D;
			double maxX = i + 0.75D;
			double minZ = k + 0.5D - width;
			double maxZ = k + 0.5D + width;
			double y = j;

			tess.addVertexWithUV(minX, y+height, minZ, minU, minV);
			tess.addVertexWithUV(minX, y, minZ, minU, maxV);
			tess.addVertexWithUV(minX, y, maxZ, maxU, maxV);
			tess.addVertexWithUV(minX, y+height, maxZ, maxU, minV);
			tess.addVertexWithUV(minX, y+height, maxZ, minU, minV);
			tess.addVertexWithUV(minX, y, maxZ, minU, maxV);
			tess.addVertexWithUV(minX, y, minZ, maxU, maxV);
			tess.addVertexWithUV(minX, y+height, minZ, maxU, minV);
			tess.addVertexWithUV(maxX, y+height, maxZ, minU, minV);
			tess.addVertexWithUV(maxX, y, maxZ, minU, maxV);
			tess.addVertexWithUV(maxX, y, minZ, maxU, maxV);
			tess.addVertexWithUV(maxX, y+height, minZ, maxU, minV);
			tess.addVertexWithUV(maxX, y+height, minZ, minU, minV);
			tess.addVertexWithUV(maxX, y, minZ, minU, maxV);
			tess.addVertexWithUV(maxX, y, maxZ, maxU, maxV);
			tess.addVertexWithUV(maxX, y+height, maxZ, maxU, minV);
			minX = i + 0.5D - width;
			maxX = i + 0.5D + width;
			minZ = k + 0.5D - 0.25D;
			maxZ = k + 0.5D + 0.25D;
			tess.addVertexWithUV(minX, y+height, minZ, minU, minV);
			tess.addVertexWithUV(minX, y, minZ, minU, maxV);
			tess.addVertexWithUV(maxX, y, minZ, maxU, maxV);
			tess.addVertexWithUV(maxX, y+height, minZ, maxU, minV);
			tess.addVertexWithUV(maxX, y+height, minZ, minU, minV);
			tess.addVertexWithUV(maxX, y, minZ, minU, maxV);
			tess.addVertexWithUV(minX, y, minZ, maxU, maxV);
			tess.addVertexWithUV(minX, y+height, minZ, maxU, minV);
			tess.addVertexWithUV(maxX, y+height, maxZ, minU, minV);
			tess.addVertexWithUV(maxX, y, maxZ, minU, maxV);
			tess.addVertexWithUV(minX, y, maxZ, maxU, maxV);
			tess.addVertexWithUV(minX, y+height, maxZ, maxU, minV);
			tess.addVertexWithUV(minX, y+height, maxZ, minU, minV);
			tess.addVertexWithUV(minX, y, maxZ, minU, maxV);
			tess.addVertexWithUV(maxX, y, maxZ, maxU, maxV);
			tess.addVertexWithUV(maxX, y+height, maxZ, maxU, minV);
		}
	}

	private static void drawCrossedSquares(Block block, double x, double y, double z, RenderBlocks renderblocks, double width, double height)
	{
		Tessellator tess = Tessellator.instance;
		GL11.glColor3f(1, 1, 1);

		int brightness = block.getMixedBrightnessForBlock(renderblocks.blockAccess, (int)x, (int)y, (int)z);
		tess.setBrightness(brightness);
		tess.setColorOpaque_F(1, 1, 1);

		IIcon icon = block.getIcon(renderblocks.blockAccess, (int)x, (int)y, (int)z, renderblocks.blockAccess.getBlockMetadata((int)x, (int)y, (int)z));
		if (renderblocks.hasOverrideBlockTexture())
			icon = renderblocks.overrideBlockTexture;

		double minU = icon.getMinU();
		double maxU = icon.getMaxU();
		double minV = icon.getMinV();
		double maxV = icon.getMaxV();

		double minX = x + 0.5D - width;
		double maxX = x + 0.5D + width;
		double minZ = z + 0.5D - width;
		double maxZ = z + 0.5D + width;

		tess.addVertexWithUV(minX, y + height, minZ, minU, minV);
		tess.addVertexWithUV(minX, y + 0.0D, minZ, minU, maxV);
		tess.addVertexWithUV(maxX, y + 0.0D, maxZ, maxU, maxV);
		tess.addVertexWithUV(maxX, y + height, maxZ, maxU, minV);

		tess.addVertexWithUV(maxX, y + height, maxZ, minU, minV);
		tess.addVertexWithUV(maxX, y + 0.0D, maxZ, minU, maxV);
		tess.addVertexWithUV(minX, y + 0.0D, minZ, maxU, maxV);
		tess.addVertexWithUV(minX, y + height, minZ, maxU, minV);

		tess.addVertexWithUV(minX, y + height, maxZ, minU, minV);
		tess.addVertexWithUV(minX, y + 0.0D, maxZ, minU, maxV);
		tess.addVertexWithUV(maxX, y + 0.0D, minZ, maxU, maxV);
		tess.addVertexWithUV(maxX, y + height, minZ, maxU, minV);

		tess.addVertexWithUV(maxX, y + height, minZ, minU, minV);
		tess.addVertexWithUV(maxX, y + 0.0D, minZ, minU, maxV);
		tess.addVertexWithUV(minX, y + 0.0D, maxZ, maxU, maxV);
		tess.addVertexWithUV(minX, y + height, maxZ, maxU, minV);
	}
}
