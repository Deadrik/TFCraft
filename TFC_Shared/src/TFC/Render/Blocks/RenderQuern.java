package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import TFC.TFCBlocks;
import TFC.Blocks.Devices.BlockToolRack;
import TFC.Render.TFC_CoreRender;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityQuern;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.src.ModLoader;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraftforge.client.ForgeHooksClient;

public class RenderQuern  implements ISimpleBlockRenderingHandler 
{
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int i, int j, int k,
			Block block, int modelId, RenderBlocks renderblocks) 
	{
		if (modelId == TFCBlocks.quernRenderId)
		{
			IBlockAccess blockAccess = renderblocks.blockAccess;
			TileEntityQuern te = (TileEntityQuern)blockAccess.getBlockTileEntity(i, j, k);
			if(te != null)
			{
				if(te.hasQuern)
				{
					renderblocks.setRenderBounds(0.0F, 0F, 0.0F, 1F, 0.825F, 1F);
					renderblocks.renderStandardBlock(block, i, j, k);

					renderblocks.overrideBlockTexture = Block.blocksList[5].getIcon(0, 0);
					if(te.rotation == 0)
					{
						renderblocks.setRenderBounds(0.8F, 0.8, 0.8F, 0.9F, 1, 0.9F);
						renderblocks.renderStandardBlock(block, i, j, k);
					}
					else if(te.rotation == 1)
					{
						renderblocks.setRenderBounds(0.8F, 0.8, 0.1F, 0.9F, 1, 0.2F);
						renderblocks.renderStandardBlock(block, i, j, k);
					}
					else if(te.rotation == 2)
					{
						renderblocks.setRenderBounds(0.1F, 0.8, 0.1F, 0.2F, 1, 0.2F);
						renderblocks.renderStandardBlock(block, i, j, k);
					}
					else if(te.rotation == 3)
					{
						renderblocks.setRenderBounds(0.1F, 0.8, 0.8F, 0.2F, 1, 0.9F);
						renderblocks.renderStandardBlock(block, i, j, k);
					}

				}
				else
				{
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
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		
		renderer.setRenderBounds(0, 0, 0, 1, 0.625, 1);
		
		Tessellator var14 = Tessellator.instance;
		var14.startDrawingQuads();
        var14.setNormal(0.0F, 1.0F, 0.0F);
        renderer.renderTopFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(1, 1));
        var14.draw();
        var14.startDrawingQuads();
        var14.setNormal(0.0F, 0.0F, -1.0F);
        renderer.renderEastFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(2, 0));
        var14.draw();
        var14.startDrawingQuads();
        var14.setNormal(0.0F, 0.0F, 1.0F);
        renderer.renderWestFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(3, 0));
        var14.draw();
        var14.startDrawingQuads();
        var14.setNormal(-1.0F, 0.0F, 0.0F);
        renderer.renderNorthFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(4, 0));
        var14.draw();
        var14.startDrawingQuads();
        var14.setNormal(1.0F, 0.0F, 0.0F);
        renderer.renderSouthFace(block, 0.0D, 0.0D, 0.0D, block.getIcon(5, 0));
        var14.draw();
		
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
}
