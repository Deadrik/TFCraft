package com.bioxx.tfc.Handlers.Client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;

import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Time;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Core.Player.SkillStats.SkillRank;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropIndexPepper;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.Items.Tools.ItemCustomHoe;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.TileEntities.TEFarmland;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

public class FarmlandHighlightHandler
{
	@SubscribeEvent
	public void drawBlockHighlightEvent(DrawBlockHighlightEvent evt)
	{
		World world = evt.player.worldObj;
		double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * evt.partialTicks;
		double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * evt.partialTicks;
		double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * evt.partialTicks;

		boolean isMetalHoe = false;

		if(evt.currentItem != null &&
				evt.currentItem.getItem() != TFCItems.igInHoe &&
				evt.currentItem.getItem() != TFCItems.igExHoe &&
				evt.currentItem.getItem() != TFCItems.sedHoe &&
				evt.currentItem.getItem() != TFCItems.mMHoe)
		{
			isMetalHoe = true;
		}

		PlayerManagerTFC manager = PlayerManagerTFC.getInstance();
		PlayerInfo playerInfo = manager != null ? manager.getClientPlayer() : null;
		int hoeMode = playerInfo != null ? playerInfo.hoeMode : -1;

		if (evt.currentItem != null && evt.currentItem.getItem() instanceof ItemCustomHoe && isMetalHoe && hoeMode == 1)
		{
			if (TFC_Core.getSkillStats(evt.player) != null)
			{
				SkillRank sr = TFC_Core.getSkillStats(evt.player).getSkillRank(Global.SKILL_AGRICULTURE);
				if (sr != SkillRank.Expert && sr != SkillRank.Master)
					return;
			}

			Block b = world.getBlock(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
			int crop = 0;
			if(b == TFCBlocks.crops && (
					world.getBlock(evt.target.blockX, evt.target.blockY - 1, evt.target.blockZ) == TFCBlocks.tilledSoil ||
					world.getBlock(evt.target.blockX, evt.target.blockY - 1, evt.target.blockZ) == TFCBlocks.tilledSoil2))
			{
				b = TFCBlocks.tilledSoil;
				crop = 1;
			}

			if(b == TFCBlocks.tilledSoil || b == TFCBlocks.tilledSoil2)
			{
				TEFarmland te = (TEFarmland) world.getTileEntity(evt.target.blockX, evt.target.blockY - crop, evt.target.blockZ);
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
				drawBox(AxisAlignedBB.getBoundingBox(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + 1,
						evt.target.blockY + fertilizer - crop,
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				double nutrient = 1.02 + ((double)te.nutrients[0] / (double)soilMax) * 0.5;
				GL11.glColor4ub(TFCOptions.cropNutrientAColor[0], TFCOptions.cropNutrientAColor[1], TFCOptions.cropNutrientAColor[2], TFCOptions.cropNutrientAColor[3]);
				drawBox(AxisAlignedBB.getBoundingBox(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer - 1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer - 1.02,
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.3333;
				nutrient = 1.02 + ((double)te.nutrients[1] / (double)soilMax) * 0.5;
				GL11.glColor4ub(TFCOptions.cropNutrientBColor[0], TFCOptions.cropNutrientBColor[1], TFCOptions.cropNutrientBColor[2], TFCOptions.cropNutrientBColor[3]);
				drawBox(AxisAlignedBB.getBoundingBox(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer - 1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer - 1.02,
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.6666;
				nutrient = 1.02 + ((double)te.nutrients[2] / (double)soilMax) * 0.5;
				GL11.glColor4ub(TFCOptions.cropNutrientCColor[0], TFCOptions.cropNutrientCColor[1], TFCOptions.cropNutrientCColor[2], TFCOptions.cropNutrientCColor[3]);
				drawBox(AxisAlignedBB.getBoundingBox(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer - 1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer - 1.02,
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

				drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop,
						evt.target.blockZ,
						evt.target.blockX + 1,
						evt.target.blockY + fertilizer - crop,
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				nutrient = 1.02 + ((double)te.nutrients[0] / (double)soilMax) * 0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer - 1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer - 1.02,
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.3333;
				nutrient = 1.02 + ((double)te.nutrients[1] / (double)soilMax) * 0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer - 1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer - 1.02,
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				offset = 0.6666;
				nutrient = 1.02 + ((double)te.nutrients[2] / (double)soilMax) * 0.5;
				drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(
						evt.target.blockX + offset,
						evt.target.blockY + 1.01 - crop + fertilizer - 1.02,
						evt.target.blockZ,
						evt.target.blockX + offset + 0.3333,
						evt.target.blockY + nutrient - crop + fertilizer - 1.02,
						evt.target.blockZ + 1
						).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
			}
		}
		else if (evt.currentItem != null && evt.currentItem.getItem() instanceof ItemCustomHoe && hoeMode == 2)
		{
			Block b = world.getBlock(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
			int crop = 0;
			if(b == TFCBlocks.crops && (
					world.getBlock(evt.target.blockX,evt.target.blockY-1,evt.target.blockZ) == TFCBlocks.tilledSoil ||
					world.getBlock(evt.target.blockX,evt.target.blockY-1,evt.target.blockZ) == TFCBlocks.tilledSoil2))
			{
				b = TFCBlocks.tilledSoil;
				crop = 1;
			}

			if(b == TFCBlocks.tilledSoil || b == TFCBlocks.tilledSoil2)
			{
				boolean water = com.bioxx.tfc.Blocks.BlockFarmland.isFreshWaterNearby(world, evt.target.blockX, evt.target.blockY-crop, evt.target.blockZ);

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

				drawFace(AxisAlignedBB.getBoundingBox(
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
		else if (evt.currentItem != null && evt.currentItem.getItem() instanceof ItemCustomHoe && hoeMode == 3)
		{
			Block b = world.getBlock(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
			if(b == TFCBlocks.crops && (
					world.getBlock(evt.target.blockX,evt.target.blockY-1,evt.target.blockZ) == TFCBlocks.tilledSoil ||
					world.getBlock(evt.target.blockX,evt.target.blockY-1,evt.target.blockZ) == TFCBlocks.tilledSoil2))
			{
				TECrop te = (TECrop) world.getTileEntity(evt.target.blockX, evt.target.blockY, evt.target.blockZ);
				CropIndex index = CropManager.getInstance().getCropFromId(te.cropId);
				boolean fullyGrown = index instanceof CropIndexPepper ? te.growth >= index.numGrowthStages - 1 : te.growth >= index.numGrowthStages;

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

				drawFace(AxisAlignedBB.getBoundingBox(
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

	public void drawFace(AxisAlignedBB par1AxisAlignedBB)
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

	public void drawBox(AxisAlignedBB par1AxisAlignedBB)
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

	public void drawOutlinedBoundingBox(AxisAlignedBB par1AxisAlignedBB)
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
