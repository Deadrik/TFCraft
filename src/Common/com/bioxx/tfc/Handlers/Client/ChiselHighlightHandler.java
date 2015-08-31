package com.bioxx.tfc.Handlers.Client;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Core.Player.PlayerManagerTFC;
import com.bioxx.tfc.Items.Tools.ItemChisel;
import com.bioxx.tfc.Items.Tools.ItemHammer;
import com.bioxx.tfc.api.Tools.ChiselManager;

public class ChiselHighlightHandler
{
	@SubscribeEvent
	public void drawBlockHighlightEvent(DrawBlockHighlightEvent evt)
	{
		EntityPlayer player = evt.player;
		World world = player.worldObj;

		if (evt.currentItem != null && evt.currentItem.getItem() instanceof ItemChisel)
		{
			boolean hasHammer = false;
			for (int i = 0; i < 9; i++)
			{
				if (player.inventory.mainInventory[i] != null && player.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				{
					hasHammer = true;
					break;
				}
			}
			
			if (hasHammer)
			{
				MovingObjectPosition target = evt.target;
				Block id = world.getBlock(target.blockX, target.blockY, target.blockZ);
				int mode = PlayerManagerTFC.getInstance().getClientPlayer().chiselMode;
				//double depth = (double)pi.ChiselDetailZoom/8D;

				if (mode > -1)
				{
					//Get the hit location in local box coords
					double hitX = Math.round((target.hitVec.xCoord - target.blockX) * 100) / 100.0d;
					double hitY = Math.round((target.hitVec.yCoord - target.blockY) * 100) / 100.0d;
					double hitZ = Math.round((target.hitVec.zCoord - target.blockZ) * 100) / 100.0d;

					ChiselManager.getInstance().setDivision(mode, target.sideHit);
					int divX = ChiselManager.getInstance().getDivX(mode, id);
					int divY = ChiselManager.getInstance().getDivY(mode, id);
					int divZ = ChiselManager.getInstance().getDivZ(mode, id);

					if (divX > 0)
					{

						//get the targeted sub block coords
						double subX = (double) ((int) ((hitX) * divX)) / divX;
						double subY = (double) ((int) ((hitY) * divY)) / divY;
						double subZ = (double) ((int) ((hitZ) * divZ)) / divZ;

						switch (target.sideHit)
						{
						case 1:
							subY -= 1d / divY;
							if (hitX == 1)
								subX -= 1d / divX;
							if (hitZ == 1)
								subZ -= 1d / divZ;
							break;
						case 3:
							subZ -= 1d / divZ;
							if (hitX == 1)
								subX -= 1d / divX;
							if (hitY == 1)
								subY -= 1d / divY;
							break;
						case 5:
							subX -= 1d / divX;
							if (hitY == 1)
								subY -= 1d / divY;
							if (hitZ == 1)
								subZ -= 1d / divZ;
							break;
						}

						//create the box size
						double minX = target.blockX + subX;
						double minY = target.blockY + subY;
						double minZ = target.blockZ + subZ;
						double maxX = minX + (1d / divX);
						double maxY = minY + (1d / divY);
						double maxZ = minZ + (1d / divZ);

						double var8 = player.lastTickPosX + (player.posX - player.lastTickPosX) * evt.partialTicks;
						double var10 = player.lastTickPosY + (player.posY - player.lastTickPosY) * evt.partialTicks;
						double var12 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * evt.partialTicks;

						//Setup GL for the depthbox
						GL11.glEnable(GL11.GL_BLEND);
						GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE);
						GL11.glDisable(GL11.GL_TEXTURE_2D);
						GL11.glDepthMask(false);
						//drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(target.blockX+depth,target.blockY+depth,target.blockZ+depth,target.blockX+1-depth,target.blockY+1-depth,target.blockZ+1-depth).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
						//Setup the GL stuff for the outline
						GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
						GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.4F);
						GL11.glLineWidth(4.0F);
						GL11.glDepthMask(true);
						//Draw the mini Box
						drawOutlinedBoundingBox(AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

						GL11.glDepthMask(true);
						GL11.glEnable(GL11.GL_TEXTURE_2D);
						GL11.glDisable(GL11.GL_BLEND);
					}
				}
				/*else if (pi.ChiselMode == 3 && (id == TFCBlocks.stoneSlabs.blockID))
				{
					double hitX = (target.hitVec.xCoord - target.blockX);
					double hitY = (target.hitVec.yCoord - target.blockY);
					double hitZ = (target.hitVec.zCoord - target.blockZ);

					int extraX = 0;
					int extraY = 0;
					int extraZ = 0;
					int extraX2 = 1;
					int extraY2 = 1;
					int extraZ2 = 1;
								
					TileEntityPartial tep = (TileEntityPartial) world.getTileEntity(target.blockX, target.blockY, target.blockZ);
					if (tep != null)
					{
						extraX = (int) ((tep.extraData) & 0xf);
						extraY = (int) ((tep.extraData >> 4) & 0xf);
						extraZ = (int) ((tep.extraData >> 8) & 0xf);
						extraX2 = 10 - (int) ((tep.extraData >> 12) & 0xf);
						extraY2 = 10 - (int) ((tep.extraData >> 16) & 0xf);
						extraZ2 = 10 - (int) ((tep.extraData >> 20) & 0xf);
					}

					double minX = target.blockX + (0.125 * extraX);
					double minY = target.blockY + (0.125 * extraY);
					double minZ = target.blockZ + (0.125 * extraZ);

					double maxX = target.blockX + (1 - (0.125 * extraX2));
					double maxY = target.blockY + (1 - (0.125 * extraY2));
					double maxZ = target.blockZ + (1 - (0.125 * extraZ2));
								
					if (target.sideHit == 2)
						minX = maxX - 0.125;
					else if (target.sideHit == 3)
						maxX = minX + 0.125;
								
					if (target.sideHit == 1)
						minY = maxY - 0.125;
					else if (target.sideHit == 0)
						maxY = minY + 0.125;
								
					if (target.sideHit == 4)
						minZ = maxZ - 0.125;
					else if (target.sideHit == 5)
						maxZ = minZ + 0.125;
								
					double var8 = player.lastTickPosX + (player.posX - player.lastTickPosX) * (double) evt.partialTicks;
					double var10 = player.lastTickPosY + (player.posY - player.lastTickPosY) * (double) evt.partialTicks;
					double var12 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * (double) evt.partialTicks;
								
					GL11.glEnable(GL11.GL_BLEND);
					GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
					GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.4F);
					GL11.glLineWidth(6.0F);
					GL11.glDisable(GL11.GL_TEXTURE_2D);
					GL11.glDepthMask(true);

					drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(minX, minY, minZ, maxX, maxY, maxZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));
								
					GL11.glDepthMask(true);
					GL11.glEnable(GL11.GL_TEXTURE_2D);
					GL11.glDisable(GL11.GL_BLEND);
				}*/
			}
		}
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
