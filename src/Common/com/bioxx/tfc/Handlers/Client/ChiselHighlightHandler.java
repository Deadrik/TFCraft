package com.bioxx.tfc.Handlers.Client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.World;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Blocks.Terrain.BlockCobble;
import com.bioxx.tfc.Blocks.Terrain.BlockSmooth;
import com.bioxx.tfc.Blocks.Terrain.BlockStone;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.Tools.ItemChisel;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

public class ChiselHighlightHandler
{
	@SubscribeEvent
	public void DrawBlockHighlightEvent(DrawBlockHighlightEvent evt)
	{
		World world = evt.player.worldObj;
		if(evt.currentItem != null && evt.currentItem.getItem() instanceof ItemChisel)
		{
			Block id = world.getBlock(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
			PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
			//double depth = (double)pi.ChiselDetailZoom/8D;
			
			//isChiselable
			boolean isChiselable = id == TFCBlocks.Planks
								|| id instanceof BlockCobble
								|| id instanceof BlockStone
								|| id instanceof BlockSmooth;

			if( pi.ChiselMode > -1 )
			{
				//Get the hit location in local box coords
				double hitX = Math.round((evt.target.hitVec.xCoord - evt.target.blockX)*100)/100.0d;
				double hitY = Math.round((evt.target.hitVec.yCoord - evt.target.blockY)*100)/100.0d;
				double hitZ = Math.round((evt.target.hitVec.zCoord - evt.target.blockZ)*100)/100.0d;
					
				int divX = 0, divY = 0, divZ = 0;

				if( pi.ChiselMode == 0 && id instanceof BlockStone )
				{
					divX = divY = divZ = 1;
				}
				else if( pi.ChiselMode == 1 && ( id == TFCBlocks.stoneStairs || isChiselable ) )
				{
						divX = divY = divZ = 2;
				}
				else if( pi.ChiselMode == 2 && ( id == TFCBlocks.stoneSlabs || isChiselable ) )
				{
						   if(evt.target.sideHit == 5 || evt.target.sideHit == 4) {
						divY = divZ = 1;
						divX = 8;
					} else if(evt.target.sideHit == 1 || evt.target.sideHit == 0) {
						divX = divZ = 1;
						divY = 8;
					} else if(evt.target.sideHit == 3 || evt.target.sideHit == 2) {
						divY = divX = 1;
						divZ = 8;
					}
				}
				else if( pi.ChiselMode == 3 && ( id == TFCBlocks.Detailed || isChiselable ) )
				{
					divX = divY = divZ = 8;
				}

				if( divX > 0 ) {
	
					//get the targeted sub block coords
					double subX = (double)((int)((hitX)*divX))/divX;
					double subY = (double)((int)((hitY)*divY))/divY;
					double subZ = (double)((int)((hitZ)*divZ))/divZ;

					switch(evt.target.sideHit) {
						case 1 :
										  subY -= (1d/divY);
							if(hitX == 1) subX -= (1d/divX);
							if(hitZ == 1) subZ -= (1d/divZ);
						break;
						case 3 :
										  subZ -= (1d/divZ);
							if(hitX == 1) subX -= (1d/divX);
							if(hitY == 1) subY -= (1d/divY);
						break;
						case 5 :
										  subX -= (1d/divX);
							if(hitY == 1) subY -= (1d/divY);
							if(hitZ == 1) subZ -= (1d/divZ);
						break;
					}
	
					//create the box size
					double minX = evt.target.blockX + subX;
					double minY = evt.target.blockY + subY;
					double minZ = evt.target.blockZ + subZ;
					double maxX = minX + (1d/divX);
					double maxY = minY + (1d/divY);
					double maxZ = minZ + (1d/divZ);
	
					double var8  = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * (double)evt.partialTicks;
					double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * (double)evt.partialTicks;
					double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * (double)evt.partialTicks;
	
					//Setup GL for the depthbox
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glDepthMask(false);
					//drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(evt.target.blockX+depth,evt.target.blockY+depth,evt.target.blockZ+depth,evt.target.blockX+1-depth,evt.target.blockY+1-depth,evt.target.blockZ+1-depth).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
					//Setup the GL stuff for the outline
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.4F);
					GL11.glLineWidth(4.0F);
					GL11.glDepthMask(true);
					//Draw the mini Box
					drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(minX,minY,minZ,maxX,maxY,maxZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
	
					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDisable(GL11.GL_BLEND);
				}

			}
//			else if(pi.ChiselMode == 3 && (id == TFCBlocks.stoneSlabs.blockID))
//			{
//				double hitX = (evt.target.hitVec.xCoord - evt.target.blockX);
//				double hitY = (evt.target.hitVec.yCoord - evt.target.blockY);
//				double hitZ = (evt.target.hitVec.zCoord - evt.target.blockZ);
//
//				int extraX = 0;
//				int extraY = 0;
//				int extraZ = 0;
//				int extraX2 = 1;
//				int extraY2 = 1;
//				int extraZ2 = 1;
//
//				TileEntityPartial tep = (TileEntityPartial) world.getTileEntity(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
//				if(tep != null)
//				{
//					extraX = (int) ((tep.extraData) & 0xf);
//					extraY = (int) ((tep.extraData >> 4) & 0xf);
//					extraZ = (int) ((tep.extraData >> 8) & 0xf);
//					extraX2 = 10 - (int) ((tep.extraData >> 12) & 0xf);
//					extraY2 = 10 - (int) ((tep.extraData >> 16) & 0xf);
//					extraZ2 = 10 - (int) ((tep.extraData >> 20) & 0xf);
//				}
//
//				double minX = evt.target.blockX + (0.125 * extraX);
//				double minY = evt.target.blockY + (0.125 * extraY);
//				double minZ = evt.target.blockZ + (0.125 * extraZ);
//
//				double maxX = evt.target.blockX + (1 - (0.125 * extraX2));
//				double maxY = evt.target.blockY + (1 - (0.125 * extraY2));
//				double maxZ = evt.target.blockZ + (1 - (0.125 * extraZ2));
//
//				if(evt.target.sideHit == 2)
//					minX = maxX - 0.125;
//				else if(evt.target.sideHit == 3)
//					maxX = minX + 0.125;
//
//				if(evt.target.sideHit == 1)
//					minY = maxY - 0.125;
//				else if(evt.target.sideHit == 0)
//					maxY = minY + 0.125;
//
//				if(evt.target.sideHit == 4)
//					minZ = maxZ - 0.125;
//				else if(evt.target.sideHit == 5)
//					maxZ = minZ + 0.125;
//
//				double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * (double)evt.partialTicks;
//				double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * (double)evt.partialTicks;
//				double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * (double)evt.partialTicks;
//
//				GL11.glEnable(GL11.GL_BLEND);
//				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
//				GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.4F);
//				GL11.glLineWidth(6.0F);
//				GL11.glDisable(GL11.GL_TEXTURE_2D);
//				GL11.glDepthMask(true);
//
//				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(minX,minY,minZ,maxX,maxY,maxZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
//
//				GL11.glDepthMask(true);
//				GL11.glEnable(GL11.GL_TEXTURE_2D);
//				GL11.glDisable(GL11.GL_BLEND);
//			}
		}
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
		var2.startDrawing(1);
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
