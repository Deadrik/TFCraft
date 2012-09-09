package TFC.Handlers;

import org.lwjgl.opengl.GL11;

import TFC.Core.TFC_Core;
import TFC.Items.ItemChisel;
import TFC.Render.Blocks.RenderDetailed;
import TFC.TileEntities.TileEntityDetailed;
import TFC.TileEntities.TileEntityPartial;
import net.minecraft.src.*;
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
			if(ItemChisel.mode == 3 && (id == TFCBlocks.StoneDetailed.blockID || TFC_Core.isRawStone(id) || TFC_Core.isSmoothStone(id)))
			{				
				//				double subX = evt.target.hitVec.xCoord - evt.target.blockX - 0.08;
				//				double subY = evt.target.hitVec.yCoord - evt.target.blockY - 0.08;
				//				double subZ = evt.target.hitVec.zCoord - evt.target.blockZ - 0.08;

				double hitX = (evt.target.hitVec.xCoord - evt.target.blockX);
				double hitY = (evt.target.hitVec.yCoord - evt.target.blockY);
				double hitZ = (evt.target.hitVec.zCoord - evt.target.blockZ);
				TileEntityDetailed te = (TileEntityDetailed) world.getBlockTileEntity(evt.target.blockX,evt.target.blockY,evt.target.blockZ);


				double subX = (double)((int)((hitX)*10))/10;
				double subY = (double)((int)((hitY)*10))/10;
				double subZ = (double)((int)((hitZ)*10))/10;

				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.4F);
				GL11.glLineWidth(6.0F);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(true);

				double minX = evt.target.blockX + subX;
				double minY = evt.target.blockY + subY;
				double minZ = evt.target.blockZ + subZ;

				double maxX = minX + 0.1;
				double maxY = minY + 0.1;
				double maxZ = minZ + 0.1;

				double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * (double)evt.partialTicks;
				double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * (double)evt.partialTicks;
				double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * (double)evt.partialTicks;

				//AxisAlignedBB AABB = Block.blocksList[id].getSelectedBoundingBoxFromPool(world, evt.target.blockX,evt.target.blockY,evt.target.blockZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12);
				//drawOutlinedBoundingBox(AABB);
				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(minX,minY,minZ,maxX,maxY,maxZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_BLEND);

			}
			if(ItemChisel.mode == 5 && (id == TFCBlocks.stoneSlabs.blockID))
			{
				double hitX = (evt.target.hitVec.xCoord - evt.target.blockX);
				double hitY = (evt.target.hitVec.yCoord - evt.target.blockY);
				double hitZ = (evt.target.hitVec.zCoord - evt.target.blockZ);

				int extraX = 0;
				int extraY = 0;
				int extraZ = 0;
				int extraX2 = 1;
				int extraY2 = 1;
				int extraZ2 = 1;

				TileEntityPartial tep = (TileEntityPartial) world.getBlockTileEntity(evt.target.blockX,evt.target.blockY,evt.target.blockZ);
				if(tep != null)
				{
					extraX = (int) ((tep.extraData) & 0xf);
					extraY = (int) ((tep.extraData >> 4) & 0xf);
					extraZ = (int) ((tep.extraData >> 8) & 0xf);
					extraX2 = 10 - (int) ((tep.extraData >> 12) & 0xf);
					extraY2 = 10 - (int) ((tep.extraData >> 16) & 0xf);
					extraZ2 = 10 - (int) ((tep.extraData >> 20) & 0xf);
				}

				double minX = evt.target.blockX + (0.1 * extraX);
				double minY = evt.target.blockY + (0.1 * extraY);
				double minZ = evt.target.blockZ + (0.1 * extraZ);

				double maxX = evt.target.blockX + (1 - (0.1 * extraX2));
				double maxY = evt.target.blockY + (1 - (0.1 * extraY2));
				double maxZ = evt.target.blockZ + (1 - (0.1 * extraZ2));

				if(evt.target.sideHit == 2)
					minX = maxX - 0.1;
				else if(evt.target.sideHit == 3)
					maxX = minX + 0.1;

				if(evt.target.sideHit == 1)
					minY = maxY - 0.1;
				else if(evt.target.sideHit == 0)
					maxY = minY + 0.1;

				if(evt.target.sideHit == 4)
					minZ = maxZ - 0.1;
				else if(evt.target.sideHit == 5)
					maxZ = minZ + 0.1;

				double var8 = evt.player.lastTickPosX + (evt.player.posX - evt.player.lastTickPosX) * (double)evt.partialTicks;
				double var10 = evt.player.lastTickPosY + (evt.player.posY - evt.player.lastTickPosY) * (double)evt.partialTicks;
				double var12 = evt.player.lastTickPosZ + (evt.player.posZ - evt.player.lastTickPosZ) * (double)evt.partialTicks;

				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
				GL11.glColor4f(1.0F, 0.0F, 0.0F, 0.4F);
				GL11.glLineWidth(6.0F);
				GL11.glDisable(GL11.GL_TEXTURE_2D);
				GL11.glDepthMask(true);

				drawOutlinedBoundingBox(AxisAlignedBB.getAABBPool().addOrModifyAABBInPool(minX,minY,minZ,maxX,maxY,maxZ).expand(0.002F, 0.002F, 0.002F).getOffsetBoundingBox(-var8, -var10, -var12));

				GL11.glDepthMask(true);
				GL11.glEnable(GL11.GL_TEXTURE_2D);
				GL11.glDisable(GL11.GL_BLEND);
			}
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
