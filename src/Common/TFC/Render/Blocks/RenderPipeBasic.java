package TFC.Render.Blocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.world.IBlockAccess;

import org.lwjgl.opengl.GL11;

import TFC.TFCBlocks;
import TFC.API.IPipeConnectable;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderPipeBasic  implements ISimpleBlockRenderingHandler
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k, Block block, int modelId, RenderBlocks renderer)
	{
			IBlockAccess blockAccess = renderer.blockAccess;
			
			Block blockToRender = TFCBlocks.SteamPipe;
			Block blockToRenderb = TFCBlocks.SteamPipeValve;//To use with connector
			
			int l = ((IPipeConnectable)TFCBlocks.SteamPipe).getSide(blockAccess,i, j, k);
			int toggle = ((IPipeConnectable)TFCBlocks.SteamPipe).getFinalBit(world, i, j, k);
			boolean isValve = world.getBlock(i, j, k) == TFCBlocks.SteamPipeValve;
			//Origin is from top
			if(l == 0)
			{
				renderer.setRenderBounds(0.25F,0.8F,0.25F,0.75F,1F,0.75F);
				renderer.renderStandardBlock(blockToRenderb, i, j, k);
				if(isValve)
				{
					renderer.setRenderBounds(0.21F,0.87F,0.475F,0.25F,0.92F,0.525F);
					renderer.renderStandardBlock(blockToRender, i, j, k);
					if(toggle == 0)
					{
						renderer.setRenderBounds(0.16F,0.7F,0.465F,0.21F,1F,0.535F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
					else
					{
						renderer.setRenderBounds(0.16F,0.86F,0.305F,0.21F,0.92F,0.605F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
				}
				
				renderer.setRenderBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.8F, 0.7F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			//Origin is from bottom
			else if(l == 5)
			{
				renderer.setRenderBounds(0.25F,0F,0.25F,0.75F,0.2F,0.75F);
				renderer.renderStandardBlock(blockToRenderb, i, j, k);
				
				if(isValve)
				{
					renderer.setRenderBounds(0.21F,1-0.92F/*1-0.87F*/,0.475F,0.25F,1-0.87F/*1-0.92F*/,0.525F);
					renderer.renderStandardBlock(blockToRender, i, j, k);
					if(toggle == 0)
					{
						renderer.setRenderBounds(0.16F,1-1F/*1-0.7F*/,0.465F,0.21F,1-0.7F/*1-1F*/,0.535F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
					else
					{
						renderer.setRenderBounds(0.16F,1-0.92F/*1-0.86F*/,0.305F,0.21F,1-0.86F/*1-0.92F*/,0.605F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
				}
				
				renderer.setRenderBounds(0.3F, 0.2F, 0.3F, 0.7F, 0.7F, 0.7F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			//Origin is from left
			else if(l == 1)
			{
				renderer.setRenderBounds(0F,0.25F,0.25F,0.2F,0.75F,0.75F);
				renderer.renderStandardBlock(blockToRenderb, i, j, k);
				
				if(isValve)
				{
					renderer.setRenderBounds(0.08F,0.475F,0.21F,1-0.87F,0.525F,0.25F);
					renderer.renderStandardBlock(blockToRender, i, j, k);
					if(toggle == 0)
					{
						renderer.setRenderBounds(0F,0.465F,0.16F,1F - 0.7,0.535F,0.21F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
					else
					{
						renderer.setRenderBounds(0.08F,0.305F,0.16F,1-0.86F,0.605F,0.21F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
				}
				
				renderer.setRenderBounds(0.2F, 0.3F, 0.3F, 0.7F, 0.7F, 0.7F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			//Origin is from right
			else if(l == 4)
			{
				renderer.setRenderBounds(0.8F,0.25F,0.25F,1F,0.75F,0.75F);
				renderer.renderStandardBlock(blockToRenderb, i, j, k);
				
				if(isValve)
				{
					renderer.setRenderBounds(0.87F,0.475F,0.21F,0.92F,0.525F,0.25F);
					renderer.renderStandardBlock(blockToRender, i, j, k);
					if(toggle == 0)
					{
						renderer.setRenderBounds(0.7F,0.465F,0.16F,1F,0.535F,0.21F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
					else
					{
						renderer.setRenderBounds(0.86F,0.305F,0.16F,0.92F,0.605F,0.21F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
				}
				
				renderer.setRenderBounds(0.3F, 0.3F, 0.3F, 0.8F, 0.7F, 0.7F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			//Origin is from front
			else if(l == 2)
			{
				renderer.setRenderBounds(0.25F,0.25F,0F,0.75F,0.75F,0.2F);
				renderer.renderStandardBlock(blockToRenderb, i, j, k);
				
				if(isValve)
				{
					renderer.setRenderBounds(0.21F,0.475F,0.08F,0.25F,0.525F,0.13F);
					renderer.renderStandardBlock(blockToRender, i, j, k);
					if(toggle == 0)
					{
						renderer.setRenderBounds(0.16F,0.465F,0F,0.21F,0.535F,1F - 0.7);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
					else
					{
						renderer.setRenderBounds(0.16F,0.305F,0.08F,0.21F,0.605F,1-0.86F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
				}
				
				renderer.setRenderBounds(0.3F, 0.3F, 0.2F, 0.7F, 0.7F, 0.7F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			//Origin is from back
			else if(l == 3)
			{
				renderer.setRenderBounds(0.25F,0.25F,0.8F,0.75F,0.75F,1F);
				renderer.renderStandardBlock(blockToRenderb, i, j, k);
				
				if(isValve)
				{
					renderer.setRenderBounds(0.21F,0.475F,0.87F,0.25F,0.525F,0.92F);
					renderer.renderStandardBlock(blockToRender, i, j, k);
					if(toggle == 0)
					{
						renderer.setRenderBounds(0.16F,0.465F,0.7F,0.21F,0.535F,1F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
					else
					{
						renderer.setRenderBounds(0.16F,0.305F,0.86F,0.21F,0.605F,0.92F);
						renderer.renderStandardBlock(Blocks.redstone_block, i, j, k);
					}
				}
				
				renderer.setRenderBounds(0.3F, 0.3F, 0.3F, 0.7F, 0.7F, 0.8F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			
			boolean[] flags = new boolean[6];
			Block[] blocks = new Block[6];
			int index = -1;
			blocks[0] = blockAccess.getBlock(i, j+1, k);
			blocks[1] = blockAccess.getBlock(i-1, j, k);
			blocks[2] = blockAccess.getBlock(i, j, k-1);
			blocks[3] = blockAccess.getBlock(i, j, k+1);
			blocks[4] = blockAccess.getBlock(i+1, j, k);
			blocks[5] = blockAccess.getBlock(i, j-1, k);
			flags[++index] = ((index != l) && blocks[index] instanceof IPipeConnectable && ((IPipeConnectable)blocks[index]).canConnectTo(world, Math.abs(index - 5), i, j+1, k));
			flags[++index] = ((index != l) && blocks[index] instanceof IPipeConnectable && ((IPipeConnectable)blocks[index]).canConnectTo(world, Math.abs(index - 5), i-1, j, k));
			flags[++index] = ((index != l) && blocks[index] instanceof IPipeConnectable && ((IPipeConnectable)blocks[index]).canConnectTo(world, Math.abs(index - 5), i, j, k-1));
			flags[++index] = ((index != l) && blocks[index] instanceof IPipeConnectable && ((IPipeConnectable)blocks[index]).canConnectTo(world, Math.abs(index - 5), i, j, k+1));
			flags[++index] = ((index != l) && blocks[index] instanceof IPipeConnectable && ((IPipeConnectable)blocks[index]).canConnectTo(world, Math.abs(index - 5), i+1, j, k));
			flags[++index] = ((index != l) && blocks[index] instanceof IPipeConnectable && ((IPipeConnectable)blocks[index]).canConnectTo(world, Math.abs(index - 5), i, j-1, k));
			
			int c = -1,counter = 0;
			while(++c < 6 && counter < 2){
				if(flags[c])counter++;
			}
			for(c = 0; c < 6 && counter > 2; c++){
				flags[c] = flags[c] && ((IPipeConnectable)blocks[c]).hasToBeOnlyOption() == 0;
			}
			if(flags[0]){
				renderer.setRenderBounds(0.3F, 0.7F, 0.3F, 0.7F, 1F, 0.7F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			if(flags[1]){
				renderer.setRenderBounds(0F, 0.3F, 0.3F, 0.3F, 0.7F, 0.7F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			if(flags[2]){
				renderer.setRenderBounds(0.3F, 0.3F, 0F, 0.7F, 0.7F, 0.3F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			if(flags[3]){
				renderer.setRenderBounds(0.3F, 0.3F, 0.7F, 0.7F, 0.7F, 1F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			if(flags[4]){
				renderer.setRenderBounds(0.7F, 0.3F, 0.3F, 1F, 0.7F, 0.7F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
			if(flags[5]){
				renderer.setRenderBounds(0.3F, 0F, 0.3F, 0.7F, 0.3F, 0.7F);
				renderer.renderStandardBlock(blockToRender, i, j, k);
			}
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
	public void renderInventoryBlock(Block block, int metadata, int modelID, RenderBlocks renderer)
	{
		Block blockToRender = TFCBlocks.Dirt;
		
		renderer.setRenderBounds(0F,0.25F,0.25F,0.2F,0.75F,0.75F);
		renderInvBlock(blockToRender, metadata, renderer);
		
		renderer.setRenderBounds(0.2F, 0.3F, 0.3F, 1F, 0.7F, 0.7F);
		renderInvBlock(blockToRender, metadata, renderer);
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
