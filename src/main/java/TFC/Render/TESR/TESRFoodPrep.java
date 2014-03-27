package TFC.Render.TESR;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import TFC.TileEntities.TileEntityFoodPrep;

public class TESRFoodPrep extends TESRBase
{

	public TESRFoodPrep()
	{
		super();
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	public void renderAt(TileEntityFoodPrep te, double d, double d1, double d2, float f)
	{
		if (te.getWorldObj() == null)
		{
		}
		else
		{
			EntityItem customitem = new EntityItem(field_147501_a.field_147550_f); //tileEntityRenderer.worldObj
			customitem.hoverStart = 0f;
			float blockScale = 1.0F;
			float timeD = (float) (360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

			if (te.getStackInSlot(0) != null)
			{
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.25F, (float)d1 + 0.1F, (float)d2 + 0.25F);
				GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(blockScale, blockScale, blockScale);
				customitem.setEntityItemStack(te.getStackInSlot(0));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix(); //end
			}
			if (te.getStackInSlot(1) != null)
			{
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.75F, (float)d1 + 0.1F, (float)d2 + 0.25F);
				GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(blockScale, blockScale, blockScale);
				customitem.setEntityItemStack(te.getStackInSlot(1));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix(); //end
			}
			if (te.getStackInSlot(2) != null)
			{
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.25F, (float)d1 + 0.1F, (float)d2 + 0.75F);
				GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(blockScale, blockScale, blockScale);
				customitem.setEntityItemStack(te.getStackInSlot(2));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix(); //end
			}
			if (te.getStackInSlot(3) != null)
			{
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.75F, (float)d1 + 0.1F, (float)d2 + 0.75F);
				GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(blockScale, blockScale, blockScale);
				customitem.setEntityItemStack(te.getStackInSlot(3));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix(); //end
			}
			if (te.getStackInSlot(5) != null)
			{
				GL11.glPushMatrix(); //start
				GL11.glTranslatef((float)d + 0.50F, (float)d1 + 0.1F, (float)d2 + 0.50F);
				GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				GL11.glScalef(blockScale, blockScale, blockScale);
				customitem.setEntityItemStack(te.getStackInSlot(5));
				itemRenderer.doRender(customitem, 0, 0, 0, 0, 0);
				GL11.glPopMatrix(); //end
			}
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TileEntityFoodPrep)par1TileEntity, par2, par4, par6, par8);
	}
}
