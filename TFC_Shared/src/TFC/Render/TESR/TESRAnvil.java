package TFC.Render.TESR;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import TFC.Blocks.Devices.BlockAnvil;
import TFC.TileEntities.TEAnvil;

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
		if (te.worldObj != null)
		{
			int dir = BlockAnvil.getDirectionFromMetadata(te.worldObj.getBlockMetadata(te.xCoord, te.yCoord, te.zCoord));

			EntityItem customitem = new EntityItem(tileEntityRenderer.worldObj);			
			customitem.hoverStart = 0f;
			float blockScale = 1.0F;

			drawItem(te, d, d1, d2, dir, customitem, blockScale, te.HAMMER_SLOT);
			drawItem(te, d, d1, d2, dir, customitem, blockScale, te.INPUT1_SLOT);
			//drawItem(te, d, d1, d2, dir, customitem, blockScale, te.FLUX_SLOT);
		}
	}

	private void drawItem(TEAnvil te, double d, double d1, double d2,
			int dir, EntityItem customitem, float blockScale, int i)
	{
		if (te.getStackInSlot(i) != null)
		{
			float[] pos = getLocation(dir, i, Block.blocksList[te.stonePair[0]] != null);
			if(Block.blocksList[te.stonePair[0]] != null)
			{
				pos[1] += 0.3f;
			}
			GL11.glPushMatrix(); //start
			GL11.glTranslatef((float)d + pos[0], (float)d1 + pos[1], (float)d2 + pos[2]);
			if (RenderManager.instance.options.fancyGraphics)
			{
				GL11.glRotatef(pos[3], pos[4], pos[5], pos[6]);
			}
			GL11.glScalef(pos[7], pos[8], pos[9]);
			customitem.setEntityItemStack(te.getStackInSlot(i));
			itemRenderer.doRenderItem(customitem, 0, 0, 0, 0, 0);
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
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TEAnvil)par1TileEntity, par2, par4, par6, par8);
	}
}
