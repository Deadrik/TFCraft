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

import TFC.TFCItems;
import TFC.Render.Models.ModelUrn;
import TFC.TileEntities.TileEntityPottery;

import com.google.common.primitives.SignedBytes;

public class TileEntityPotteryRenderer extends TileEntitySpecialRenderer
{
	private RenderBlocks renderBlocks;
	private ModelUrn urnModel;
    private RenderItem itemRenderer;
    private ModelRenderer modelRenderer;

    public TileEntityPotteryRenderer()
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
                return true;
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
	public void renderAt(TileEntityPottery te, double d, double d1, double d2, float f)
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
			for(int i = 0; i < 4; i++)
			{
				float offsetX = 0.25f; 
				float offsetY = 0.1f; 
				float offsetZ = 0.25f;
				if(i == 1)
				{
					offsetX = 0.75f; 
					offsetZ = 0.25f;
				}
				else if(i == 2)
				{
					offsetX = 0.25f; 
					offsetZ = 0.75f;
				}
				else if(i == 3)
				{
					offsetX = 0.75f; 
					offsetZ = 0.75f;
				}
				
				if (te.getStackInSlot(i) != null && te.getStackInSlot(i).itemID != TFCItems.PotteryLargeVessel.itemID)
				{
					GL11.glPushMatrix(); //start
					GL11.glTranslatef((float)d + offsetX, (float)d1 + offsetY, (float)d2 + offsetZ);
					if (RenderManager.instance.options.fancyGraphics)
					{
						GL11.glRotatef(timeD, 0.0F, 1.0F, 0.0F);
					}
					GL11.glScalef(blockScale, blockScale, blockScale);
					customitem.setEntityItemStack(te.getStackInSlot(i));
					itemRenderer.doRenderItem(customitem, 0, 0, 0, 0, 0);
					GL11.glPopMatrix(); //end
					
				}
				/**
				 * Commented out until Large Vessels are implemented
				 */
				/*
				else if (te.getStackInSlot(i) != null && te.getStackInSlot(i).itemID == TFCItems.PotteryLargeVessel.itemID)
				{
					GL11.glPushMatrix(); //start
					GL11.glTranslatef((float)d, (float)d1, (float)d2);
					urnModel.render(Tessellator.instance, 1);
					GL11.glPopMatrix(); //end
				}*/
			}
			
		}
	}

	@Override
	public void renderTileEntityAt(TileEntity par1TileEntity, double par2, double par4, double par6, float par8)
	{
		this.renderAt((TileEntityPottery)par1TileEntity, par2, par4, par6, par8);
	}
}
