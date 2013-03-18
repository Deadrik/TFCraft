
package TFC.Render.Blocks;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
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
import TFC.Blocks.BlockIngotPile;
import TFC.Core.AnvilReq;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Textures;
import TFC.TileEntities.TileEntityIngotPile;
import TFC.WorldGen.DataLayer;

public class RenderIngotPile {


	public static boolean renderIngotPile(Block block, int i, int j, int k, RenderBlocks renderblocks)
	{
		IBlockAccess blockAccess = renderblocks.blockAccess;

		int meta = blockAccess.getBlockMetadata(i, j, k);
		int direction = ((BlockIngotPile)block).getDirectionFromMetadata(meta);
		renderblocks.renderAllFaces = true;

		boolean breaking = false;
		if(renderblocks.overrideBlockTexture >= 240)
		{
			breaking = true;
		}

		TileEntityIngotPile te = (TileEntityIngotPile)blockAccess.getBlockTileEntity(i, j, k);
		System.out.println("called!");
		//block.setBlockBounds(0.2F, 0.0F, 0.0F, 0.80F, 0.6F, 1.0F);
		if(te.getStackInSlot(0)!=null){
			for (int n = 0; n < te.getStackInSlot(0).stackSize;n++){
				//for (int n = 0; n < 31;n++){
				//for (int n = 0; n < ((BlockIngotPile)block).stack;n++){
				int m = (n+8)/8;
				float x = (n %4)*0.25f;
				float y = (m -1)*0.25f;
				float z = 0;
				if (n%8 >=4){
					z = 0.5f;
				}
				if (m %2 == 1){
					/*renderblocks.setRenderMinMax(0.0F + x, 0.0F + y, 0.0F + z, 0.25F + x, 0.0625F + y, 0.5F + z);
					renderblocks.renderStandardBlock(block, i, j, k);

					renderblocks.setRenderMinMax(0.03125F + x, 0.0625F + y, 0.03125F + z, 0.21875F + x, 0.25F + y, 0.46875F + z);
					renderblocks.renderStandardBlock(block, i, j, k);*/
					drawBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(
						i + x,
						j + y,
						k + z,
						i + x + 0.25f,
						j + y + 0.0625f, 
						k + z + 0.5f
						));
				}
				else{
					/*renderblocks.setRenderMinMax(0.0F + z, 0.0F + y, 0.0F + x, 0.5F + z, 0.0625F + y, 0.25F +x);
					renderblocks.renderStandardBlock(block, i, j, k);

					renderblocks.setRenderMinMax(0.03125F + z, 0.0625F + y,  0.03125F + x, 0.46875F + z , 0.25F + y,0.21875F + x);
					renderblocks.renderStandardBlock(block, i, j, k);*/
					drawBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(
							i + x,
							j + y,
							k + z,
							i + x + 0.25f,
							j + y + 0.0625f, 
							k + z + 0.5f
							));
				}
			}
		}
		return true;
	}
	
	static void drawBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;

		//Top
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//-x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();

		//+x
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();

		//-z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();
		
		//+z
		var2.startDrawing(GL11.GL_QUADS);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}
}

