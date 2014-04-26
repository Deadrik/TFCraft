package TFC.Render.TESR;

import java.util.Random;

import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import TFC.TileEntities.TEWorldItem;

public class TESRWorldItem extends TESRBase
{
	public TESRWorldItem()
	{
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TEWorldItem te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() != null)
		{
			int dir = te.getWorldObj().getBlockMetadata(te.xCoord, te.yCoord, te.zCoord);
			Random R = new Random((te.xCoord + te.zCoord)*te.xCoord);
			EntityItem customitem = new EntityItem(field_147501_a.field_147550_f); //tileEntityRenderer.worldObj
			customitem.hoverStart = 0f;
			float blockScale = 1.0F;
			float timeD = (float) (360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

			if (te.storage[0] != null)
			{
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.5f, (float)d1 + 0.05f, (float)d2 + 0.5f);
				if (RenderManager.instance.options.fancyGraphics)
				{
					GL11.glRotatef(90, 1.0f, 0.0F, 0.0F);
					GL11.glRotatef(R.nextFloat()*360, 0.0f, 0.0F, 1.0F);
				}
				GL11.glScalef(blockScale, blockScale, blockScale);
				customitem.setEntityItemStack(te.storage[0]);
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix(); //end
			}
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TEWorldItem)par1TileEntity, par2, par4, par6, par8);
	}
}
