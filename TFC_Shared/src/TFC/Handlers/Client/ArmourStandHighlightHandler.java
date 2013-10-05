package TFC.Handlers.Client;

import org.lwjgl.opengl.GL11;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.Tools.ItemChisel;
import TFC.Render.Blocks.RenderDetailed;
import TFC.TileEntities.TEStand;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.Tessellator;
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
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;
import net.minecraftforge.client.event.DrawBlockHighlightEvent;
import net.minecraftforge.event.ForgeSubscribe;

public class ArmourStandHighlightHandler{
	AxisAlignedBB boxToRender;
	@ForgeSubscribe
	public void DrawBlockHighlightEvent(DrawBlockHighlightEvent evt) 
	{
		World world = evt.player.worldObj;
		if(evt.currentItem == null && world.getBlockId(evt.target.blockX,evt.target.blockY,evt.target.blockZ) == TFCBlocks.ArmourStand.blockID
			&&	(Math.sqrt(evt.target.hitVec.squareDistanceTo(evt.player.posX, evt.player.posY, evt.player.posZ))) < 2.5)
		{
			PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();

			EntityPlayer player = evt.player;



			Vec3 vectorTerm = player.getLookVec();
			vectorTerm.xCoord*=0.5;
			vectorTerm.yCoord*=0.5;
			vectorTerm.zCoord*=0.5;
			vectorTerm = vectorTerm.addVector(evt.target.hitVec.xCoord, evt.target.hitVec.yCoord, evt.target.hitVec.zCoord);			

			TEStand stand = ((TEStand)world.getBlockTileEntity(evt.target.blockX,evt.target.blockY, evt.target.blockZ));

			boolean isTop = stand.isTop;

			if(isTop){
				stand = (TEStand) world.getBlockTileEntity(evt.target.blockX, evt.target.blockY-1, evt.target.blockZ);
			}
			boolean NS = stand.yaw == 0 || stand.yaw == 180;

			double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * (double)evt.partialTicks;
			double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * (double)evt.partialTicks;
			double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * (double)evt.partialTicks;


			//Head box
			AxisAlignedBB head = AxisAlignedBB.getBoundingBox(
					evt.target.blockX+0.5 - (0.3*1), evt.target.blockY+(1.35)+(isTop?-1:0), evt.target.blockZ+0.5 - (0.3*1),
					evt.target.blockX+0.5 + (0.3*1), evt.target.blockY+(1.95+(isTop?-1:0)), evt.target.blockZ+0.5 + (0.3*1));


			//Body box
			AxisAlignedBB body = AxisAlignedBB.getBoundingBox(
					evt.target.blockX+0.5 - (0.55*1), evt.target.blockY+(0.75)+(isTop?-1:0), evt.target.blockZ+0.5 - (0.17*1),
					evt.target.blockX+0.5 + (0.55*1), evt.target.blockY+(1.5)+(isTop?-1:0), evt.target.blockZ+0.5 + (0.17*1));
			if(!NS){
				body = AxisAlignedBB.getBoundingBox(
						evt.target.blockX+0.5 - (0.17*1), evt.target.blockY+(0.75)+(isTop?-1:0), evt.target.blockZ+0.5 - (0.55*1),
						evt.target.blockX+0.5 + (0.17*1), evt.target.blockY+(1.5)+(isTop?-1:0), evt.target.blockZ+0.5 + (0.55*1));
			}


			//Legs box
			AxisAlignedBB legs = AxisAlignedBB.getBoundingBox(
					evt.target.blockX+0.5 - (0.35*0.9), evt.target.blockY+(1.8-(1.6))+(isTop?-1:0), evt.target.blockZ+0.5 - (0.155*0.9),
					evt.target.blockX+0.5 + (0.35*0.9), evt.target.blockY+(1.8-(1*0.9))+(isTop?-1:0), evt.target.blockZ+0.5 + (0.155*0.9));
			if(!NS){
				legs = AxisAlignedBB.getBoundingBox(
						evt.target.blockX+0.5 - (0.155*0.9), evt.target.blockY+(1.8-(1.6))+(isTop?-1:0), evt.target.blockZ+0.5 - (0.35*0.9),
						evt.target.blockX+0.5 + (0.155*0.9), evt.target.blockY+(1.8-(1*0.9))+(isTop?-1:0), evt.target.blockZ+0.5 + (0.35*0.9));
			}


			//Feet box
			AxisAlignedBB feet = AxisAlignedBB.getBoundingBox(
					evt.target.blockX+0.5 - (0.35*1), evt.target.blockY+0.15+(isTop?-1:0), evt.target.blockZ+0.5 - (0.17*1),
					evt.target.blockX+0.5 + (0.35*1), evt.target.blockY+(1.8-(1.4))+(isTop?-1:0), evt.target.blockZ+0.5 + (0.17*1));
			if(!NS){
				feet = AxisAlignedBB.getBoundingBox(
						evt.target.blockX+0.5 - (0.17*1), evt.target.blockY+0.15+(isTop?-1:0), evt.target.blockZ+0.5 - (0.35*1),
						evt.target.blockX+0.5 + (0.17*1), evt.target.blockY+(1.8-(1.4))+(isTop?-1:0), evt.target.blockZ+0.5 + (0.35*1));
			}


			if(head.isVecInside(vectorTerm) && stand.items[4]!=null){
				boxToRender = head;
				TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(4));
			}
			else if(body.isVecInside(vectorTerm) && stand.items[3]!=null){
				boxToRender = body;
				TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(3));
			}
			else if(feet.isVecInside(vectorTerm)&&stand.items[1]!=null){
				boxToRender = feet;
				TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(1));
			}
			else if(legs.isVecInside(vectorTerm)&&stand.items[2]!=null){
				boxToRender = legs;
				TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(2));
			}
			else{
				TerraFirmaCraft.proxy.sendCustomPacket(stand.createHighlightPacket(-1));
			}

			if(boxToRender != null){
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
