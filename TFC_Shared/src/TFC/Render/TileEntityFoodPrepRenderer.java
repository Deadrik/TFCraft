package TFC.Render;

import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;

import org.lwjgl.opengl.GL11;

import TFC.TileEntities.TileEntityFoodPrep;

import com.google.common.primitives.SignedBytes;

public class TileEntityFoodPrepRenderer extends TileEntitySpecialRenderer
{
	private RenderBlocks renderBlocks;
	private RenderItem itemRenderer;
	private ModelRenderer modelRenderer;

	public TileEntityFoodPrepRenderer()
	{
		renderBlocks = new RenderBlocks();
		itemRenderer = new RenderItem() {
			@Override
			public byte getMiniBlockCount(ItemStack stack) {
				return SignedBytes.saturatedCast(Math.min(stack.stackSize / 32, 15) + 1);
			}
			@Override
			public byte getMiniItemCount(ItemStack stack) {
				return SignedBytes.saturatedCast(Math.min(stack.stackSize / 32, 7) + 1);
			}
			@Override
			public boolean shouldBob() {
				return false;
			}
			@Override
			public boolean shouldSpreadItems() {
				return false;
			}
		};
		itemRenderer.setRenderManager(RenderManager.instance);
	}

	/**
	 * Renders the TileEntity for the chest at a position.
	 */
	 public void renderAt(TileEntityFoodPrep te, double d, double d1, double d2, float f)
	{
		 if (te.worldObj == null)
		 {

		 }
		 else
		 {
			 EntityItem customitem = new EntityItem(tileEntityRenderer.worldObj);			
			 customitem.hoverStart = 0f;
			 float blockScale = 1.0F;
			 float timeD = (float) (360.0 * (System.currentTimeMillis() & 0x3FFFL) / 0x3FFFL);

			 if (te.getStackInSlot(0) != null)
			 {
				 GL11.glPushMatrix(); //start
				 GL11.glTranslatef((float)d + 0.25F, (float)d1 + 0F, (float)d2 + 0.25F);
				 GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				 GL11.glScalef(blockScale, blockScale, blockScale);
				 customitem.setEntityItemStack(te.getStackInSlot(0));
				 itemRenderer.doRenderItem(customitem, 0, 0, 0, 0, 0);
				 GL11.glPopMatrix(); //end
			 }
			 if (te.getStackInSlot(1) != null)
			 {
				 GL11.glPushMatrix(); //start
				 GL11.glTranslatef((float)d + 0.75F, (float)d1 + 0F, (float)d2 + 0.25F);
				 GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				 GL11.glScalef(blockScale, blockScale, blockScale);
				 customitem.setEntityItemStack(te.getStackInSlot(1));
				 itemRenderer.doRenderItem(customitem, 0, 0, 0, 0, 0);
				 GL11.glPopMatrix(); //end
			 }
			 if (te.getStackInSlot(2) != null)
			 {
				 GL11.glPushMatrix(); //start
				 GL11.glTranslatef((float)d + 0.25F, (float)d1 + 0F, (float)d2 + 0.75F);
				 GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				 GL11.glScalef(blockScale, blockScale, blockScale);
				 customitem.setEntityItemStack(te.getStackInSlot(2));
				 itemRenderer.doRenderItem(customitem, 0, 0, 0, 0, 0);
				 GL11.glPopMatrix(); //end
			 }
			 if (te.getStackInSlot(3) != null)
			 {
				 GL11.glPushMatrix(); //start
				 GL11.glTranslatef((float)d + 0.75F, (float)d1 + 0F, (float)d2 + 0.75F);
				 GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				 GL11.glScalef(blockScale, blockScale, blockScale);
				 customitem.setEntityItemStack(te.getStackInSlot(3));
				 itemRenderer.doRenderItem(customitem, 0, 0, 0, 0, 0);
				 GL11.glPopMatrix(); //end
			 }
			 if (te.getStackInSlot(5) != null)
			 {
				 GL11.glPushMatrix(); //start
				 GL11.glTranslatef((float)d + 0.50F, (float)d1 + 0F, (float)d2 + 0.50F);
				 GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
				 GL11.glScalef(blockScale, blockScale, blockScale);
				 customitem.setEntityItemStack(te.getStackInSlot(5));
				 itemRenderer.doRenderItem(customitem, 0, 0, 0, 0, 0);
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
