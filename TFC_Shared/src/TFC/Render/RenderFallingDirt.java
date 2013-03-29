package TFC.Render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Entities.EntityFallingDirt;

public class RenderFallingDirt extends Render
{
    private RenderBlocks renderBlocks;

    public RenderFallingDirt()
    {
        renderBlocks = new RenderBlocks();
        shadowSize = 0.5F;
    }

    public void doRenderFallingStone(EntityFallingDirt entity, double d, double d1, double d2,
            float f, float f1)
    {
    	Block block = Block.blocksList[entity.blockID];
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        String path = block.getBlockTextureFromSideAndMetadata(0, entity.metaID).getIconName();
        loadTexture("/terrain.png");

        World world = entity.getWorld();
        GL11.glDisable(2896 /*GL_LIGHTING*/);

        //TFC_CoreRender.renderBlockFallingSand(Block.blocksList[entity.blockID], entity.metaID, world, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));
        renderBlocks.setRenderBoundsFromBlock(block);
        renderBlocks.renderBlockSandFalling(block, world, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ), entity.metaID);
        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glPopMatrix();
    }

    @Override
	public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1)
    {
        doRenderFallingStone((EntityFallingDirt)entity, d, d1, d2, f, f1);
    }
}
