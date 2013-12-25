package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDirectional;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.world.IBlockAccess;
import TFC.TFCBlocks;
import TFC.API.IMultipleBlock;
import TFC.Blocks.Vanilla.BlockCustomFenceGate;
import TFC.TileEntities.TileEntityFenceGate;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderFenceGate  implements ISimpleBlockRenderingHandler 
{
	static float pixel3 = 3f/16f;
	static float pixel5 = 5f/16f;
	static float pixel12 = 12f/16f;
	static float pixel14 = 14f/16f;

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int par2, int par3, int par4,
			Block block, int modelId, RenderBlocks renderer)
	{
		Block par1BlockFenceGate = ((IMultipleBlock)block).getBlockTypeForRender();
		boolean flag = true;
		int l = ((TileEntityFenceGate)renderer.blockAccess.getBlockTileEntity(par2, par3, par4)).getDirection();
		boolean flag1 = ((TileEntityFenceGate)renderer.blockAccess.getBlockTileEntity(par2, par3, par4)).getOpen();
		int i1 = BlockDirectional.getDirection(l);
		float f = 0.375F;
		float f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = 0.3125F;
		float f5 = 1.0F;

		if ((i1 == 2 || i1 == 0) && renderer.blockAccess.getBlockId(par2 - 1, par3, par4) == Block.cobblestoneWall.blockID && renderer.blockAccess.getBlockId(par2 + 1, par3, par4) == Block.cobblestoneWall.blockID || (i1 == 3 || i1 == 1) && renderer.blockAccess.getBlockId(par2, par3, par4 - 1) == Block.cobblestoneWall.blockID && renderer.blockAccess.getBlockId(par2, par3, par4 + 1) == Block.cobblestoneWall.blockID)
		{
			f -= 0.1875F;
			f1 -= 0.1875F;
			f2 -= 0.1875F;
			f3 -= 0.1875F;
			f4 -= 0.1875F;
			f5 -= 0.1875F;
		}

		renderer.renderAllFaces = true;
		float f6;
		float f7;
		float f8;
		float f9;

		if (i1 != 3 && i1 != 1)
		{
			f6 = 0.0F;
			f8 = 0.125F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds((double)f6, (double)f4, (double)f7, (double)f8, (double)f5, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f6 = 0.875F;
			f8 = 1.0F;
			renderer.setRenderBounds((double)f6, (double)f4, (double)f7, (double)f8, (double)f5, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
		}
		else
		{
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.0F;
			f9 = 0.125F;
			renderer.setRenderBounds((double)f6, (double)f4, (double)f7, (double)f8, (double)f5, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f7 = 0.875F;
			f9 = 1.0F;
			renderer.setRenderBounds((double)f6, (double)f4, (double)f7, (double)f8, (double)f5, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.uvRotateTop = 0;
		}

		if (flag1)
		{
			if (i1 == 2 || i1 == 0)
			{
				renderer.uvRotateTop = 1;
			}

			float f10;
			float f11;
			float f12;

			if (i1 == 3)
			{
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				f10 = 0.5625F;
				f12 = 0.8125F;
				f11 = 0.9375F;
				renderer.setRenderBounds(0.8125D, (double)f, 0.0D, 0.9375D, (double)f3, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.8125D, (double)f, 0.875D, 0.9375D, (double)f3, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.5625D, (double)f, 0.0D, 0.8125D, (double)f1, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.5625D, (double)f, 0.875D, 0.8125D, (double)f1, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.5625D, (double)f2, 0.0D, 0.8125D, (double)f3, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.5625D, (double)f2, 0.875D, 0.8125D, (double)f3, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			}
			else if (i1 == 1)
			{
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				f10 = 0.0625F;
				f12 = 0.1875F;
				f11 = 0.4375F;
				renderer.setRenderBounds(0.0625D, (double)f, 0.0D, 0.1875D, (double)f3, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0625D, (double)f, 0.875D, 0.1875D, (double)f3, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.1875D, (double)f, 0.0D, 0.4375D, (double)f1, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.1875D, (double)f, 0.875D, 0.4375D, (double)f1, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.1875D, (double)f2, 0.0D, 0.4375D, (double)f3, 0.125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.1875D, (double)f2, 0.875D, 0.4375D, (double)f3, 1.0D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			}
			else if (i1 == 0)
			{
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				f10 = 0.5625F;
				f12 = 0.8125F;
				f11 = 0.9375F;
				renderer.setRenderBounds(0.0D, (double)f, 0.8125D, 0.125D, (double)f3, 0.9375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, (double)f, 0.8125D, 1.0D, (double)f3, 0.9375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0D, (double)f, 0.5625D, 0.125D, (double)f1, 0.8125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, (double)f, 0.5625D, 1.0D, (double)f1, 0.8125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0D, (double)f2, 0.5625D, 0.125D, (double)f3, 0.8125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, (double)f2, 0.5625D, 1.0D, (double)f3, 0.8125D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			}
			else if (i1 == 2)
			{
				f6 = 0.0F;
				f8 = 0.125F;
				f7 = 0.875F;
				f9 = 1.0F;
				f10 = 0.0625F;
				f12 = 0.1875F;
				f11 = 0.4375F;
				renderer.setRenderBounds(0.0D, (double)f, 0.0625D, 0.125D, (double)f3, 0.1875D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, (double)f, 0.0625D, 1.0D, (double)f3, 0.1875D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0D, (double)f, 0.1875D, 0.125D, (double)f1, 0.4375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, (double)f, 0.1875D, 1.0D, (double)f1, 0.4375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.0D, (double)f2, 0.1875D, 0.125D, (double)f3, 0.4375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
				renderer.setRenderBounds(0.875D, (double)f2, 0.1875D, 1.0D, (double)f3, 0.4375D);
				renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			}
		}
		else if (i1 != 3 && i1 != 1)
		{
			f6 = 0.375F;
			f8 = 0.5F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f3, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f6 = 0.5F;
			f8 = 0.625F;
			renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f3, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f6 = 0.625F;
			f8 = 0.875F;
			renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f1, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.setRenderBounds((double)f6, (double)f2, (double)f7, (double)f8, (double)f3, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f6 = 0.125F;
			f8 = 0.375F;
			renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f1, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.setRenderBounds((double)f6, (double)f2, (double)f7, (double)f8, (double)f3, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
		}
		else
		{
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.375F;
			f9 = 0.5F;
			renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f3, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f7 = 0.5F;
			f9 = 0.625F;
			renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f3, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f7 = 0.625F;
			f9 = 0.875F;
			renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f1, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.setRenderBounds((double)f6, (double)f2, (double)f7, (double)f8, (double)f3, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			f7 = 0.125F;
			f9 = 0.375F;
			renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f1, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
			renderer.setRenderBounds((double)f6, (double)f2, (double)f7, (double)f8, (double)f3, (double)f9);
			renderer.renderStandardBlock(par1BlockFenceGate, par2, par3, par4);
		}

		renderer.renderAllFaces = false;
		renderer.uvRotateTop = 0;
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
		return flag;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {

		int l = 1;
		boolean flag1 = false;
		int i1 = BlockDirectional.getDirection(l);

		float f = 0.375F;
		float f1 = 0.5625F;
		float f2 = 0.75F;
		float f3 = 0.9375F;
		float f4 = 0.3125F;
		float f5 = 1.0F;
		float f6;
		float f7;
		float f8;
		float f9;

		if (i1 != 3 && i1 != 1)
		{
			f6 = 0.0F;
			f8 = 0.125F;
			f7 = 0.4375F;
			f9 = 0.5625F;
			renderer.setRenderBounds((double)f6, (double)f4, (double)f7, (double)f8, (double)f5, (double)f9);
			renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
			f6 = 0.875F;
			f8 = 1.0F;
			renderer.setRenderBounds((double)f6, (double)f4, (double)f7, (double)f8, (double)f5, (double)f9);
			renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		}
		else
		{
			renderer.uvRotateTop = 1;
			f6 = 0.4375F;
			f8 = 0.5625F;
			f7 = 0.0F;
			f9 = 0.125F;
			renderer.setRenderBounds((double)f6, (double)f4, (double)f7, (double)f8, (double)f5, (double)f9);
			renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
			f7 = 0.875F;
			f9 = 1.0F;
			renderer.setRenderBounds((double)f6, (double)f4, (double)f7, (double)f8, (double)f5, (double)f9);
			renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
			renderer.uvRotateTop = 0;
		}



		renderer.uvRotateTop = 1;
		f6 = 0.4375F;
		f8 = 0.5625F;
		f7 = 0.375F;
		f9 = 0.5F;
		renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f3, (double)f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		f7 = 0.5F;
		f9 = 0.625F;
		renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f3, (double)f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		f7 = 0.625F;
		f9 = 0.875F;
		renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f1, (double)f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		renderer.setRenderBounds((double)f6, (double)f2, (double)f7, (double)f8, (double)f3, (double)f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		f7 = 0.125F;
		f9 = 0.375F;
		renderer.setRenderBounds((double)f6, (double)f, (double)f7, (double)f8, (double)f1, (double)f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);
		renderer.setRenderBounds((double)f6, (double)f2, (double)f7, (double)f8, (double)f3, (double)f9);
		renderInvBlock2(((IMultipleBlock)block).getBlockTypeForRender(), metadata, renderer);


		renderer.renderAllFaces = false;
		renderer.uvRotateTop = 0;
		renderer.setRenderBounds(0.0D, 0.0D, 0.0D, 1.0D, 1.0D, 1.0D);
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

	public static void renderInvBlock2(Block block, int m, RenderBlocks renderer)
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
