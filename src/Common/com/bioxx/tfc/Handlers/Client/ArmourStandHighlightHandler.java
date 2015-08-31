package com.bioxx.tfc.Handlers.Client;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import net.minecraftforge.client.event.DrawBlockHighlightEvent;

import cpw.mods.fml.common.eventhandler.SubscribeEvent;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TileEntities.TEStand;
import com.bioxx.tfc.api.TFCBlocks;

public class ArmourStandHighlightHandler
{
	private AxisAlignedBB boxToRender;
	@SubscribeEvent
	public void drawBlockHighlightEvent(DrawBlockHighlightEvent evt)
	{
		World world = evt.player.worldObj;
		if(evt.currentItem == null && TFCBlocks.isArmourStand(world.getBlock(evt.target.blockX,evt.target.blockY,evt.target.blockZ))
				&& (Math.sqrt(evt.target.hitVec.squareDistanceTo(evt.player.posX, evt.player.posY, evt.player.posZ))) < 2.5)
		{
			//PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
			EntityPlayer player = evt.player;
			Vec3 vectorTerm = player.getLookVec();
			vectorTerm.xCoord*=0.5;
			vectorTerm.yCoord*=0.5;
			vectorTerm.zCoord*=0.5;
			vectorTerm = vectorTerm.addVector(evt.target.hitVec.xCoord, evt.target.hitVec.yCoord, evt.target.hitVec.zCoord);
			TEStand stand = ((TEStand)world.getTileEntity(evt.target.blockX,evt.target.blockY, evt.target.blockZ));
			boolean isTop = stand.isTop;

			if(isTop)
				stand = (TEStand) world.getTileEntity(evt.target.blockX, evt.target.blockY-1, evt.target.blockZ);
			boolean ns = stand.yaw%360 == 0 || stand.yaw%360  == 180 ;
			double var8 = player.lastTickPosX + (player.posX - player.lastTickPosX) * evt.partialTicks;
			double var10 = player.lastTickPosY + (player.posY - player.lastTickPosY) * evt.partialTicks;
			double var12 = player.lastTickPosZ + (player.posZ - player.lastTickPosZ) * evt.partialTicks;

			//Head box
			AxisAlignedBB head = AxisAlignedBB.getBoundingBox(
					evt.target.blockX+0.5 - (0.3*1), evt.target.blockY+(1.35)+(isTop?-1:0), evt.target.blockZ+0.5 - (0.3*1),
					evt.target.blockX+0.5 + (0.3*1), evt.target.blockY+(1.95+(isTop?-1:0)), evt.target.blockZ+0.5 + (0.3*1));
			//Body box
			AxisAlignedBB body = AxisAlignedBB.getBoundingBox(
					evt.target.blockX+0.5 - (0.55*1), evt.target.blockY+(0.75)+(isTop?-1:0), evt.target.blockZ+0.5 - (0.17*1),
					evt.target.blockX+0.5 + (0.55*1), evt.target.blockY+(1.5)+(isTop?-1:0), evt.target.blockZ+0.5 + (0.17*1));
			if(!ns)
				body = AxisAlignedBB.getBoundingBox(
						evt.target.blockX+0.5 - (0.17*1), evt.target.blockY+(0.75)+(isTop?-1:0), evt.target.blockZ+0.5 - (0.55*1),
						evt.target.blockX+0.5 + (0.17*1), evt.target.blockY+(1.5)+(isTop?-1:0), evt.target.blockZ+0.5 + (0.55*1));
			//Legs box
			AxisAlignedBB legs = AxisAlignedBB.getBoundingBox(
					evt.target.blockX+0.5 - (0.35*0.9), evt.target.blockY+(1.8-(1.6))+(isTop?-1:0), evt.target.blockZ+0.5 - (0.155*0.9),
					evt.target.blockX+0.5 + (0.35*0.9), evt.target.blockY+(1.8-(1*0.9))+(isTop?-1:0), evt.target.blockZ+0.5 + (0.155*0.9));
			if(!ns)
				legs = AxisAlignedBB.getBoundingBox(
						evt.target.blockX+0.5 - (0.155*0.9), evt.target.blockY+(1.8-(1.6))+(isTop?-1:0), evt.target.blockZ+0.5 - (0.35*0.9),
						evt.target.blockX+0.5 + (0.155*0.9), evt.target.blockY+(1.8-(1*0.9))+(isTop?-1:0), evt.target.blockZ+0.5 + (0.35*0.9));
			//Feet box
			AxisAlignedBB feet = AxisAlignedBB.getBoundingBox(
					evt.target.blockX+0.5 - (0.35*1), evt.target.blockY+0.15+(isTop?-1:0), evt.target.blockZ+0.5 - (0.17*1),
					evt.target.blockX+0.5 + (0.35*1), evt.target.blockY+(1.8-(1.4))+(isTop?-1:0), evt.target.blockZ+0.5 + (0.17*1));
			if(!ns)
				feet = AxisAlignedBB.getBoundingBox(
						evt.target.blockX+0.5 - (0.17*1), evt.target.blockY+0.15+(isTop?-1:0), evt.target.blockZ+0.5 - (0.35*1),
						evt.target.blockX+0.5 + (0.17*1), evt.target.blockY+(1.8-(1.4))+(isTop?-1:0), evt.target.blockZ+0.5 + (0.35*1));

			Vec3 unit = vectorTerm.normalize();
			unit = player.getLookVec();
			if(isVecInsideBox(head,player,unit,var8,var10,var12)&& stand.items[4]!=null)
			{
				boxToRender = head;
				//TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(4));
			}
			else if(isVecInsideBox(body,player,unit,var8,var10,var12)&& stand.items[3]!=null)
			{
				boxToRender = body;
				//TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(3));
			}
			else if(isVecInsideBox(legs,player,unit,var8,var10,var12)&&stand.items[2]!=null)
			{
				boxToRender = legs;
				//TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(2));
			}
			else if(isVecInsideBox(feet,player,unit,var8,var10,var12)&&stand.items[1]!=null)
			{
				boxToRender = feet;
				//TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(1));
			}
			/*else
			{
				//TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(-1));
			}*/

			if(boxToRender != null)
			{
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
				drawOutlinedBoundingBox(boxToRender.expand(0.02F, 0.02F, 0.02F).getOffsetBoundingBox(-var8, -var10, -var12));
				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_BLEND);
			}
			boxToRender = null;
		}
	}

	public boolean isVecInsideBox(AxisAlignedBB aabb, EntityPlayer player, Vec3 unit, double xOffset, double yOffset, double zOffset)
	{
		unit = player.getLookVec();
		aabb.minY+=0.1;
		aabb.maxY+=0.1;
		Vec3 playerVec = Vec3.createVectorHelper(player.posX, player.posY + player.eyeHeight, player.posZ);
		//Vec3 negPlayerVec = Vec3.createVectorHelper(-player.lastTickPosX, -(player.lastTickPosY + player.eyeHeight), -player.lastTickPosZ);
		//Vec3 zeroVec = Vec3.createVectorHelper(0,0,0);
		Vec3 distBlockxyz = playerVec.subtract(Vec3.createVectorHelper(aabb.minX, aabb.minY, aabb.minZ)).normalize();
		Vec3 distBlockXYZ = playerVec.subtract(Vec3.createVectorHelper(aabb.maxX, aabb.maxY, aabb.maxZ)).normalize();

		Vec3 distBlockxyZ = playerVec.subtract(Vec3.createVectorHelper(aabb.minX, aabb.minY, aabb.maxZ)).normalize();
		Vec3 distBlockXYz = playerVec.subtract(Vec3.createVectorHelper(aabb.maxX, aabb.maxY, aabb.minZ)).normalize();

		Vec3 distBlockxYZ = playerVec.subtract(Vec3.createVectorHelper(aabb.minX, aabb.maxY, aabb.maxZ)).normalize();
		Vec3 distBlockXyz = playerVec.subtract(Vec3.createVectorHelper(aabb.maxX, aabb.minY, aabb.minZ)).normalize();

		Vec3 distBlockxYz = playerVec.subtract(Vec3.createVectorHelper(aabb.minX, aabb.maxY, aabb.minZ)).normalize();
		Vec3 distBlockXyZ = playerVec.subtract(Vec3.createVectorHelper(aabb.maxX, aabb.minY, aabb.maxZ)).normalize();
			//To ensure that the unit vector is actually within the space, we project the vectors onto it, and make the total length that length
			double currentLongestProj = 0;
			currentLongestProj = Math.max(currentLongestProj, unit.dotProduct(distBlockxyz));
			currentLongestProj = Math.max(currentLongestProj, unit.dotProduct(distBlockXYZ));
			currentLongestProj = Math.max(currentLongestProj, unit.dotProduct(distBlockXyz));
			currentLongestProj = Math.max(currentLongestProj, unit.dotProduct(distBlockxYZ));
			currentLongestProj = Math.max(currentLongestProj, unit.dotProduct(distBlockxYz));
			currentLongestProj = Math.max(currentLongestProj, unit.dotProduct(distBlockXyZ));
			currentLongestProj = Math.max(currentLongestProj, unit.dotProduct(distBlockxyZ));
			currentLongestProj = Math.max(currentLongestProj, unit.dotProduct(distBlockXYz));

			unit.xCoord*=currentLongestProj*0.99;unit.yCoord*=currentLongestProj*0.99;unit.zCoord*=currentLongestProj*0.99;
		//print(unit);
		//print(distBlockxyz);
		//print(distBlockXYZ);
		//print(distBlockXyz);
		//print(distBlockxYZ);
		//print(distBlockxYz);
		//print(distBlockXyZ);
		//print(distBlockxyZ);
		//print(distBlockXYz);
		boolean insideBoxX = unit.xCoord >= distBlockxyz.xCoord && unit.xCoord <= distBlockXYZ.xCoord ||
								unit.xCoord >= distBlockxyZ.xCoord && unit.xCoord <= distBlockXYz.xCoord ||
								unit.xCoord >= distBlockxYZ.xCoord && unit.xCoord <= distBlockXyz.xCoord ||
								unit.xCoord >= distBlockxYz.xCoord && unit.xCoord <= distBlockXyZ.xCoord;

		boolean insideBoxY = unit.yCoord >= distBlockxyz.yCoord && unit.yCoord <= distBlockXYZ.yCoord ||
								unit.yCoord >= distBlockxyZ.yCoord && unit.yCoord <= distBlockXYz.yCoord ||
								unit.yCoord >= distBlockXyz.yCoord && unit.yCoord <= distBlockxYZ.yCoord ||
								unit.yCoord >= distBlockXyZ.yCoord && unit.yCoord <= distBlockxYz.yCoord;

		boolean insideBoxZ = unit.zCoord >= distBlockxyz.zCoord && unit.zCoord <= distBlockXYZ.zCoord ||
								unit.zCoord >= distBlockXYz.zCoord && unit.zCoord <= distBlockxyZ.zCoord ||
								unit.zCoord >= distBlockXyz.zCoord && unit.zCoord <= distBlockxYZ.zCoord ||
								unit.zCoord >= distBlockxYz.zCoord && unit.zCoord <= distBlockXyZ.zCoord;
		//print(playerVec);
		//print(playerVec.addVector(unit.xCoord, unit.yCoord, unit.zCoord));
		/*GL11.glEnable(GL11.GL_BLEND);
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
		GL11.glDisable(GL11.GL_LIGHTING);
		drawLine(zeroVec,unit.addVector(-(xOffset - player.lastTickPosX), -(yOffset - player.lastTickPosY), -(zOffset - player.lastTickPosZ)));
		drawLine(zeroVec,distBlockxyz);
		drawLine(zeroVec,distBlockXYZ);
		drawLine(zeroVec,distBlockXyz);
		drawLine(zeroVec,distBlockxYZ);
		drawLine(zeroVec,distBlockxYz);
		drawLine(zeroVec,distBlockXyZ);
		drawLine(zeroVec,distBlockxyZ);
		drawLine(zeroVec,distBlockXYz);
		GL11.glDepthMask(true);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glDisable(GL11.GL_BLEND);*/
		//print(unit);
		//print(distBlockxyz);
		//print(distBlockXYZ);
		aabb.minY-=0.1;
		aabb.maxY-=0.1;
		return insideBoxX && insideBoxY && insideBoxZ;
	}

	public void drawLine(Vec3 origin, Vec3 vector)
	{
		Tessellator var2 = Tessellator.instance;
		var2.startDrawing(3);
		//var2.addVertex(origin.xCoord, origin.yCoord, origin.zCoord);
		//var2.addVertex(origin.xCoord+vector.xCoord, origin.yCoord+vector.yCoord, origin.zCoord+vector.zCoord);
		var2.addVertex(origin.xCoord,origin.yCoord,origin.zCoord);
		var2.addVertex(vector.xCoord, vector.yCoord, vector.zCoord);
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
