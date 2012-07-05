package net.minecraft.src.TFC_Core;

import org.lwjgl.opengl.GL11;

import net.minecraft.src.*;
import net.minecraft.src.TFC_Core.Blocks.BlockPartial;
import net.minecraft.src.forge.IHighlightHandler;

public class RenderHighlight implements IHighlightHandler
{

    @Override
    public boolean onBlockHighlight(RenderGlobal render, EntityPlayer player,
            MovingObjectPosition target, int i, ItemStack currentItem,
            float partialTicks)
    {
        World world = player.worldObj;
        
        if(render.damagePartialTime > 0F)
        {
            if(Block.blocksList[world.getBlockId(target.blockX, target.blockY, target.blockZ)] instanceof BlockPartial)
            {
                drawBlockBreaking(render, player, target, i, currentItem, partialTicks);
                render.drawSelectionBox(player, target, i, currentItem, partialTicks);
                return true;
            }
            return false;
        }
        return false;
    }
    
    public void drawBlockBreaking(RenderGlobal render, EntityPlayer par1EntityPlayer, MovingObjectPosition par2MovingObjectPosition, int par3, ItemStack par4ItemStack, float par5)
    {
        Tessellator var6 = Tessellator.instance;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_ALPHA_TEST);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, (MathHelper.sin((float)System.currentTimeMillis() / 100.0F) * 0.2F + 0.4F) * 0.5F);
        int var8;

        if (par3 == 0)
        {
            if (render.damagePartialTime > 0.0F)
            {
                GL11.glBlendFunc(GL11.GL_DST_COLOR, GL11.GL_SRC_COLOR);
                int var7 = render.renderEngine.getTexture("/terrain.png");
                GL11.glBindTexture(GL11.GL_TEXTURE_2D, var7);
                GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
                GL11.glPushMatrix();
                var8 = render.worldObj.getBlockId(par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ);
                Block var9 = var8 > 0 ? Block.blocksList[var8] : null;
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glPolygonOffset(-3.0F, -3.0F);
                GL11.glEnable(GL11.GL_POLYGON_OFFSET_FILL);
                double var10 = par1EntityPlayer.lastTickPosX + (par1EntityPlayer.posX - par1EntityPlayer.lastTickPosX) * (double)par5;
                double var12 = par1EntityPlayer.lastTickPosY + (par1EntityPlayer.posY - par1EntityPlayer.lastTickPosY) * (double)par5;
                double var14 = par1EntityPlayer.lastTickPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.lastTickPosZ) * (double)par5;

                if (var9 == null)   
                {
                    var9 = Block.stone;
                }

                GL11.glEnable(GL11.GL_ALPHA_TEST);
                var6.startDrawingQuads();
                var6.setTranslation(-var10, -var12, -var14);
                var6.disableColor();
                render.globalRenderBlocks.renderBlockUsingTexture(var9, par2MovingObjectPosition.blockX, par2MovingObjectPosition.blockY, par2MovingObjectPosition.blockZ, 240 + (int)(render.damagePartialTime * 10.0F));
                var6.draw();
                var6.setTranslation(0.0D, 0.0D, 0.0D);
                GL11.glDisable(GL11.GL_ALPHA_TEST);
                GL11.glPolygonOffset(0.0F, 0.0F);
                GL11.glDisable(GL11.GL_POLYGON_OFFSET_FILL);
                GL11.glEnable(GL11.GL_ALPHA_TEST);
                GL11.glDepthMask(true);
                GL11.glPopMatrix();
            }
        }
        else if (par4ItemStack != null)
        {
            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
            float var16 = MathHelper.sin((float)System.currentTimeMillis() / 100.0F) * 0.2F + 0.8F;
            GL11.glColor4f(var16, var16, var16, MathHelper.sin((float)System.currentTimeMillis() / 200.0F) * 0.2F + 0.5F);
            var8 = render.renderEngine.getTexture("/terrain.png");
            GL11.glBindTexture(GL11.GL_TEXTURE_2D, var8);
            int var17 = par2MovingObjectPosition.blockX;
            int var18 = par2MovingObjectPosition.blockY;
            int var11 = par2MovingObjectPosition.blockZ;

            if (par2MovingObjectPosition.sideHit == 0)
            {
                --var18;
            }

            if (par2MovingObjectPosition.sideHit == 1)
            {
                ++var18;
            }

            if (par2MovingObjectPosition.sideHit == 2)
            {
                --var11;
            }

            if (par2MovingObjectPosition.sideHit == 3)
            {
                ++var11;
            }

            if (par2MovingObjectPosition.sideHit == 4)
            {
                --var17;
            }

            if (par2MovingObjectPosition.sideHit == 5)
            {
                ++var17;
            }
        }

        GL11.glDisable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_ALPHA_TEST);
    }

}
