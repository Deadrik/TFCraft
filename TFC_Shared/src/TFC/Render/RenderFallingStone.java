package TFC.Render;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Entities.EntityFallingStone;

public class RenderFallingStone extends Render
{
    private RenderBlocks renderBlocks;

    
    public RenderFallingStone()
    {
        renderBlocks = new RenderBlocks();
        shadowSize = 0.5F;

    }

    public void doRenderFallingStone(EntityFallingStone entity, double d, double d1, double d2,
            float f, float f1)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        String path = Block.blocksList[entity.blockID].getBlockTextureFromSideAndMetadata(0, entity.metaID).getIconName();
        loadTexture("textures/blocks/"+path);

        World world = entity.getWorld();
        GL11.glDisable(2896 /*GL_LIGHTING*/);

        TFC_CoreRender.renderBlockFallingSand(Block.blocksList[entity.blockID], entity.metaID, world, MathHelper.floor_double(entity.posX), MathHelper.floor_double(entity.posY), MathHelper.floor_double(entity.posZ));

        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glPopMatrix();
    }

    @Override
	public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1)
    {
        doRenderFallingStone((EntityFallingStone)entity, d, d1, d2, f, f1);
    }
}
