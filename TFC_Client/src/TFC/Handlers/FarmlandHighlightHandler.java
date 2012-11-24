package TFC.Handlers;

import org.lwjgl.opengl.GL11;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.ItemCustomHoe;
import TFC.TileEntities.TileEntityFarmland;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.Block;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.Tessellator;
import net.minecraft.src.Vec3;
import net.minecraft.src.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class FarmlandHighlightHandler{

	@ForgeSubscribe
	public void DrawBlockHighlightEvent(DrawBlockHighlightEvent evt) 
	{
		World world = evt.player.worldObj;		
		if(evt.currentItem != null && evt.currentItem.getItem() instanceof ItemCustomHoe)
		{
			int id = world.getBlockId(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
			int crop = 0;
			if(id == Block.crops.blockID)
			{
				id = TFCBlocks.tilledSoil.blockID;
				crop = 1;
			}

			if(id == TFCBlocks.tilledSoil.blockID || id == TFCBlocks.tilledSoil2.blockID)
			{
				double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * (double)evt.partialTicks;
				double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * (double)evt.partialTicks;
				double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * (double)evt.partialTicks;

				TileEntityFarmland te = (TileEntityFarmland) world.getBlockTileEntity(evt.target.blockX, evt.target.blockY - crop, evt.target.blockZ);
				te.requestNutrientData();

				float timeMultiplier = (float)TFC_Time.daysInYear / 360f;
				int soilMax = (int) (25000 * timeMultiplier);

				//Setup GL for the depthbox
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4ub((byte)237, (byte)28, (byte)36, (byte)200);
				GL11.glDisable(GL11.GL_CULL_FACE);
				//GL11.glLineWidth(6.0F);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(false);

				double offset = 0;
				double nutrient = 1.02 + ((double)te.nutrients[0] / (double)soilMax)*0.5;

				drawFace(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
				
				offset = 0.3333;
				nutrient = 1.02 + ((double)te.nutrients[1] / (double)soilMax)*0.5;
				GL11.glColor4ub((byte)242, (byte)101, (byte)34, (byte)200);
				drawFace(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
				
				offset = 0.6666;
				nutrient = 1.02 + ((double)te.nutrients[2] / (double)soilMax)*0.5;
				GL11.glColor4ub((byte)247, (byte)148, (byte)29, (byte)200);
				drawFace(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				GL11.glEnable(GL11.GL_CULL_FACE);
				
				
				/**
				 * Draw the outliens around the boxes
				 * */
				
				GL11.glColor4f(0.1F, 0.1F, 0.1F, 1.0F);
				GL11.glLineWidth(3.0F);
				GL11.glDepthMask(false);
				
				offset = 0;
				nutrient = 1.02 + ((double)te.nutrients[0] / (double)soilMax)*0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
				
				offset = 0.3333;
				nutrient = 1.02 + ((double)te.nutrients[1] / (double)soilMax)*0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
				
				offset = 0.6666;
				nutrient = 1.02 + ((double)te.nutrients[2] / (double)soilMax)*0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
			}
		}
	}

	void drawFace(AxisAlignedBB par1AxisAlignedBB)
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

	void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
	{
		Tessellator var2 = Tessellator.instance;
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(3);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.draw();
		var2.startDrawing(GL11.GL_LINES);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.minZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.maxX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.minY, par1AxisAlignedBB.maxZ);
		var2.addVertex(par1AxisAlignedBB.minX, par1AxisAlignedBB.maxY, par1AxisAlignedBB.maxZ);
		var2.draw();
	}

}
