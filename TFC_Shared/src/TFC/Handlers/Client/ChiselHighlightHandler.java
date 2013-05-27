package TFC.Handlers.Client;

import org.lwjgl.opengl.GL11;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.Tools.ItemChisel;
import TFC.Render.Blocks.RenderDetailed;
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

public class ChiselHighlightHandler{

	@ForgeSubscribe
	public void DrawBlockHighlightEvent(DrawBlockHighlightEvent evt) 
	{
		World world = evt.player.worldObj;
		if(evt.currentItem != null && evt.currentItem.getItem() instanceof ItemChisel)
		{
			int id = world.getBlockId(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
			PlayerInfo pi = PlayerManagerTFC.getInstance().getClientPlayer();
			//double depth = (double)pi.ChiselDetailZoom/8D;
			if(pi.ChiselMode == 3 && id == TFCBlocks.Detailed.blockID)
			{				
				//Get the hit location in local box coords
				double hitX = Math.round((evt.target.hitVec.xCoord - evt.target.blockX)*100)/100.0d;
				double hitY = Math.round((evt.target.hitVec.yCoord - evt.target.blockY)*100)/100.0d;
				double hitZ = Math.round((evt.target.hitVec.zCoord - evt.target.blockZ)*100)/100.0d;

				//get the targeted sub block coords
				double subX = (double)((int)((hitX)*8))/8;
				double subY = (double)((int)((hitY)*8))/8;
				double subZ = (double)((int)((hitZ)*8))/8;
				

				if(evt.target.sideHit == 1)
				{
					subY -= 0.125;
				}
				else if(evt.target.sideHit == 5)
				{
					subX -= 0.125;
				}
				else if(evt.target.sideHit == 3)
				{
					subZ -= 0.125;
				}

				//create the box size
				double minX = evt.target.blockX + subX;
				double minY = evt.target.blockY + subY;
				double minZ = evt.target.blockZ + subZ;
				double maxX = minX + 0.125;
				double maxY = minY + 0.125;
				double maxZ = minZ + 0.125;

				double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * (double)evt.partialTicks;
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
				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().getAABB(minX,minY,minZ,maxX,maxY,maxZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_BLEND);

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
//				TileEntityPartial tep = (TileEntityPartial) world.getBlockTileEntity(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
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
