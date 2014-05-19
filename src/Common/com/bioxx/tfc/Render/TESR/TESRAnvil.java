package com.bioxx.tfc.Render.TESR;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Blocks.Devices.BlockAnvil;
import com.bioxx.tfc.TileEntities.TEAnvil;

public class TESRAnvil extends TESRBase
{
	public TESRAnvil()
	{
		super();
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TEAnvil te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() != null)
		{
			int dir = BlockAnvil.getDirectionFromMetadata(te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord, te.zCoord));

			EntityItem customitem = new EntityItem(field_147501_a.field_147550_f); //tileEntityRenderer.worldObj
			customitem.hoverStart = 0f;
			float blockScale = 1.0F;

			drawItem(te, d, d1, d2, dir, customitem, blockScale, TEAnvil.HAMMER_SLOT);
			drawItem(te, d, d1, d2, dir, customitem, blockScale, TEAnvil.INPUT1_SLOT);
			//drawItem(te, d, d1, d2, dir, customitem, blockScale, te.FLUX_SLOT);
		}
	}

	private void drawItem(TEAnvil te, double d, double d1, double d2, int dir, EntityItem customitem, float blockScale, int i)
	{
		if (te.getStackInSlot(i) != null)
		{
			float[] pos = getLocation(dir, i, Block.getBlockById(te.stonePair[0]) != null);
			if(Block.getBlockById(te.stonePair[0]) != Blocks.air)
				pos[1] += 0.3f;
			GL11.glPushMatrix(); //start
			{
				GL11.glTranslatef((float)d + pos[0], (float)d1 + pos[1], (float)d2 + pos[2]);
				if (RenderManager.instance.options.fancyGraphics)
					GL11.glRotatef(pos[3], pos[4], pos[5], pos[6]);
				GL11.glScalef(pos[7], pos[8], pos[9]);
				customitem.setEntityItemStack(te.getStackInSlot(i));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
			}
			GL11.glPopMatrix(); //end
		}
	}

	public float[] getLocation(int dir, int slot, boolean isStone)
	{
		float[] out = new float[10];
		out[7] = 1f;
		out[8] = 1f;
		out[9] = 1f;

		if(dir == 0)
		{
			out[3] = 90f;
			out[4] = 1f;
			out[5] = 0f;
			out[6] = 0f;
			if(slot == TEAnvil.HAMMER_SLOT)
			{
				out[0] = 0.55f;
				out[1] = 0.61f;
				out[2] = 0.45f;
			}
			else if(slot == TEAnvil.INPUT1_SLOT)
			{
				out[0] = 0.55f;
				out[1] = 0.61f;
				out[2] = 0.05f;

				out[7] = 0.8f;
				out[8] = 0.8f;
				out[9] = 0.8f;
			}
		}
		else if(dir == 1)
		{
			out[3] = 90f;
			out[4] = 1f;
			out[5] = 0f;
			out[6] = 0f;
			if(slot == TEAnvil.HAMMER_SLOT)
			{
				out[0] = 0.25f;
				out[1] = 0.61f;
				out[2] = 0.25f;
			}
			else if(slot == TEAnvil.INPUT1_SLOT)
			{
				out[0] = 0.75f;
				out[1] = 0.61f;
				out[2] = 0.25f;

				out[7] = 0.8f;
				out[8] = 0.8f;
				out[9] = 0.8f;
			}
		}
		return out;
	}

	@Override
	public void renderTileEntityAt(TileEntity te, double xDis, double yDis, double zDis, float f)
	{
		this.renderAt((TEAnvil)te, xDis, yDis, zDis, f);
	}
}
