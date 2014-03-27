package TFC.Handlers.Client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import org.lwjgl.opengl.GL11;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.TFCOptions;
import TFC.Core.TFC_Time;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.Items.Tools.ItemCustomHoe;
import TFC.TileEntities.TECrop;
import TFC.TileEntities.TileEntityFarmland;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class FarmlandHighlightHandler{

	@SubscribeEvent
	public void DrawBlockHighlightEvent(DrawBlockHighlightEvent evt)
	{
		World world = evt.player.worldObj;
		double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * evt.partialTicks;
		double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * evt.partialTicks;
		double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * evt.partialTicks;

		boolean isMetalHoe = false;

		if(evt.currentItem != null &&
				evt.currentItem.getItem() != TFCItems.IgInHoe &&
				evt.currentItem.getItem() != TFCItems.IgExHoe &&
				evt.currentItem.getItem() != TFCItems.SedHoe &&
				evt.currentItem.getItem() != TFCItems.MMHoe)
			isMetalHoe = true;

		if(evt.currentItem != null && evt.currentItem.getItem() instanceof ItemCustomHoe && isMetalHoe && PlayerManagerTFC.getInstance().getClientPlayer().hoeMode == 1)
		{
			Block id = world.getBlock(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
			int crop = 0;
			if(id == Blocks.wheat && (
					world.getBlock(evt.target.blockX, evt.target.blockY-1, evt.target.blockZ) == TFCBlocks.tilledSoil ||
					world.getBlock(evt.target.blockX, evt.target.blockY-1, evt.target.blockZ) == TFCBlocks.tilledSoil2))
			{
				id = TFCBlocks.tilledSoil;
				crop = 1;
			}

			if(id == TFCBlocks.tilledSoil || id == TFCBlocks.tilledSoil2)
			{
				TileEntityFarmland te = (TileEntityFarmland) world.getTileEntity(evt.target.blockX, evt.target.blockY - crop, evt.target.blockZ);
				te.requestNutrientData();

				float timeMultiplier = TFC_Time.daysInYear / 360f;
				int soilMax = (int) (25000 * timeMultiplier);

				//Setup GL for the depthbox
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

				GL11.glDisable(GL11.GL_CULL_FACE);
				//GL11.glLineWidth(6.0F);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(false);

				double offset = 0;
				double fertilizer = 1.02 + ((double)te.nutrients[3] / (double)soilMax)*0.5;
				GL11.glColor4ub(TFCOptions.cropFertilizerColor[0], TFCOptions.cropFertilizerColor[1], TFCOptions.cropFertilizerColor[2], TFCOptions.cropFertilizerColor[3]);
				drawBox(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + 1,
						evt.target.blockY + fertilizer - crop, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				double nutrient = 1.02 + ((double)te.nutrients[0] / (double)soilMax)*0.5;
				GL11.glColor4ub(TFCOptions.cropNutrientAColor[0], TFCOptions.cropNutrientAColor[1], TFCOptions.cropNutrientAColor[2], TFCOptions.cropNutrientAColor[3]);
				drawBox(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer-1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer-1.02, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.3333;
				nutrient = 1.02 + ((double)te.nutrients[1] / (double)soilMax)*0.5;
				GL11.glColor4ub(TFCOptions.cropNutrientBColor[0], TFCOptions.cropNutrientBColor[1], TFCOptions.cropNutrientBColor[2], TFCOptions.cropNutrientBColor[3]);
				drawBox(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer-1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer-1.02, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.6666;
				nutrient = 1.02 + ((double)te.nutrients[2] / (double)soilMax)*0.5;
				GL11.glColor4ub(TFCOptions.cropNutrientCColor[0], TFCOptions.cropNutrientCColor[1], TFCOptions.cropNutrientCColor[2], TFCOptions.cropNutrientCColor[3]);
				drawBox(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer-1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer-1.02, 
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

				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + 1,
						evt.target.blockY + fertilizer - crop, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				nutrient = 1.02 + ((double)te.nutrients[0] / (double)soilMax)*0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer-1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer-1.02, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.3333;
				nutrient = 1.02 + ((double)te.nutrients[1] / (double)soilMax)*0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer-1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer-1.02, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.6666;
				nutrient = 1.02 + ((double)te.nutrients[2] / (double)soilMax)*0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer-1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer-1.02, 
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
			}
		}
		else if(evt.currentItem != null && evt.currentItem.getItem() instanceof ItemCustomHoe && 
				PlayerManagerTFC.getInstance().getClientPlayer().hoeMode == 2)
		{
			Block id = world.getBlock(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
			int crop = 0;
			if(id == Blocks.wheat && (
					world.getBlock(evt.target.blockX,evt.target.blockY-1,evt.target.blockZ) == TFCBlocks.tilledSoil ||
					world.getBlock(evt.target.blockX,evt.target.blockY-1,evt.target.blockZ) == TFCBlocks.tilledSoil2))
			{
				id = TFCBlocks.tilledSoil;
				crop = 1;
			}

			if(id == TFCBlocks.tilledSoil || id == TFCBlocks.tilledSoil2)
			{
				boolean water = TFC.Blocks.BlockFarmland.isWaterNearby(world, evt.target.blockX, evt.target.blockY-crop, evt.target.blockZ);

				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				if(water)
					GL11.glColor4ub((byte)14, (byte)23, (byte)212, (byte)200);
				else
					GL11.glColor4ub((byte)0, (byte)0, (byte)0, (byte)200);
				GL11.glDisable(GL11.GL_CULL_FACE);
				//GL11.glLineWidth(6.0F);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(false);

				drawFace(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX+1,
						evt.target.blockY + 1.02 - crop, 
						evt.target.blockZ+1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				GL11.glEnable(GL11.GL_CULL_FACE);
			}
		}
		else if(evt.currentItem != null && evt.currentItem.getItem() instanceof ItemCustomHoe && 
				PlayerManagerTFC.getInstance().getClientPlayer().hoeMode == 3)
		{
			Block id = world.getBlock(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
			if(id == Blocks.wheat && (
					world.getBlock(evt.target.blockX,evt.target.blockY-1,evt.target.blockZ) == TFCBlocks.tilledSoil ||
					world.getBlock(evt.target.blockX,evt.target.blockY-1,evt.target.blockZ) == TFCBlocks.tilledSoil2))
			{
				TECrop te = (TECrop) world.getTileEntity(evt.target.blockX, evt.target.blockY, evt.target.blockZ);
				CropIndex index = CropManager.getInstance().getCropFromId(te.cropId);
				boolean fullyGrown = te.growth >= index.numGrowthStages;

				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				if(fullyGrown)
					GL11.glColor4ub((byte)64, (byte)200, (byte)37, (byte)200);
				else
					GL11.glColor4ub((byte)200, (byte)37, (byte)37, (byte)200);
				GL11.glDisable(GL11.GL_CULL_FACE);
				//GL11.glLineWidth(6.0F);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(false);

				drawFace(AxisAlignedBB.getAABBPool().getAABB(
						evt.target.blockX,
						evt.target.blockY + 0.01,
						evt.target.blockZ,
						evt.target.blockX+1,
						evt.target.blockY + 0.02, 
						evt.target.blockZ+1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				GL11.glEnable(GL11.GL_CULL_FACE);
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
	}

	void drawBox(AxisAlignedBB par1AxisAlignedBB)
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
