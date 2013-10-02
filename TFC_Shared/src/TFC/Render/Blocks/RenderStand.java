package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import TFC.TFCBlocks;
import TFC.TileEntities.TEStand;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderStand  implements ISimpleBlockRenderingHandler 
{
	static float pixel3 = 3f/16f;
	static float pixel5 = 5f/16f;
	static float pixel12 = 12f/16f;
	static float pixel14 = 14f/16f;
	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k,
			Block block, int modelId, RenderBlocks renderer) 
	{
			IBlockAccess blockAccess = renderer.blockAccess;
			
			TEStand te = (TEStand)(world.getBlockTileEntity(i, j, k));
			
			int l = (int)(((te.yaw%360) / 90)%2);
			//Arms of the Stand
			if(l == 0){
				renderer.setRenderBounds(0.1F,1.35F,0.44F,0.9F,1.45F,0.56F);
				renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
				
				//Main posts of the stand
				renderer.setRenderBounds(0.35F, 0.201F, 0.45F, 0.45F, 1.35F, 0.55F);
				renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
				
				renderer.setRenderBounds(0.55F, 0.201F, 0.45F, 0.65F, 1.35F, 0.55F);
				renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
			}
			else{
				renderer.setRenderBounds(0.44F,1.35F,0.1F,0.56F,1.45F,0.9F);
				renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
				
				//Main posts of the stand
				renderer.setRenderBounds(0.45F, 0.201F, 0.35F, 0.55F, 1.35F, 0.45F);
				renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
				
				renderer.setRenderBounds(0.45F, 0.201F, 0.55F, 0.55F, 1.35F, 0.65F);
				renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
			}
			
			
			//Base of the stand
			renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.9F, 0.2F, 0.9F);
			renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
			
			renderer.setRenderBounds(0.45F, 1.45F, 0.45F,0.55F,1.9F,0.55F);
			renderer.renderStandardBlock(TFCBlocks.Planks, i, j, k);
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

		//Arms of the Stand
		renderer.setRenderBounds(0.44F,1.45F,0.2F,0.56F,1.55F,0.8F);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);
		
		renderer.setRenderBounds(0.45F, 0.201F, 0.35F, 0.55F, 1.35F, 0.45F);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);
		
		renderer.setRenderBounds(0.45F, 0.201F, 0.55F, 0.55F, 1.35F, 0.65F);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);
		
		//Base of the stand
		renderer.setRenderBounds(0.1F, 0F, 0.1F, 0.9F, 0.2F, 0.9F);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);

		//Main post of the stand
		renderer.setRenderBounds(0.45F, 1.45F, 0.45F,0.55F,1.9F,0.55F);
		renderInvBlock(TFCBlocks.Planks, metadata, renderer);
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
