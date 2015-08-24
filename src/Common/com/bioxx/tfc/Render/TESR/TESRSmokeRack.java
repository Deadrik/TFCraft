package com.bioxx.tfc.Render.TESR;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.TileEntities.TESmokeRack;

public class TESRSmokeRack extends TESRBase
{

	public TESRSmokeRack()
	{
		super();
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TESmokeRack te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() != null)
		{
			EntityItem customitem = new EntityItem(field_147501_a.field_147550_f); //tileEntityRenderer.worldObj
			customitem.hoverStart = 0f;
			float blockScale = 1.0F;
			int meta = te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord, te.zCoord);
			float offsetX = 0;
			float offsetZ = 0;
			if((meta & 1) == 0)
				offsetZ = 0.25f;
			else 
				offsetX = 0.25f;
			if (te.getStackInSlot(0) != null)
			{
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.5F-offsetX, (float)d1 + 0F, (float)d2 + 0.5F-offsetZ);

				if((meta & 1) == 0)
					GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
				else
					GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);

				GL11.glScalef(blockScale, blockScale, blockScale);
				customitem.setEntityItemStack(te.getStackInSlot(0));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix(); //end
			}
			if (te.getStackInSlot(1) != null)
			{
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.5F+offsetX, (float)d1 + 0F, (float)d2 + 0.5F + offsetZ);

				if((meta & 1) == 0)
					GL11.glRotatef(90, 0.0F, 1.0F, 0.0F);
				else
					GL11.glRotatef(0, 0.0F, 1.0F, 0.0F);

				GL11.glScalef(blockScale, blockScale, blockScale);
				customitem.setEntityItemStack(te.getStackInSlot(1));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix(); //end
			}
		}
	}

	public void drawItem(TESmokeRack te, int index, double minX, double maxX, double minZ, double maxZ)
	{
		if (te.storage[index] != null /*&& te.storage[index].getItem() instanceof Item*/) //That will always ben an instanceof Item, perhaps this is supposed to check somethign else?
		{
			float minU = te.storage[index].getIconIndex().getMinU();
			float maxU = te.storage[index].getIconIndex().getMaxU();
			float minV = te.storage[index].getIconIndex().getMinV();
			float maxV = te.storage[index].getIconIndex().getMaxV();
			Tessellator tessellator = Tessellator.instance;
			tessellator.startDrawingQuads();
			tessellator.setNormal(0.0F, 1.0F, 0.0F);
			tessellator.addVertexWithUV(minX, 0.0F, maxZ, minU, maxV);
			tessellator.addVertexWithUV(maxX, 0.0F, maxZ, maxU, maxV);
			tessellator.addVertexWithUV(maxX, 0.0F, minZ, maxU, minV);
			tessellator.addVertexWithUV(minX, 0.0F, minZ, minU, minV);
			tessellator.draw();
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TESmokeRack)par1TileEntity, par2, par4, par6, par8);
	}
}
