package TFC.Render;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import org.lwjgl.opengl.GL11;
import TFC.*;
import TFC.Core.TFC_Textures;
import TFC.Entities.EntityFallingStone;

public class RenderFallingStone extends Render
{
    private RenderBlocks renderBlocks;

    
    public RenderFallingStone()
    {
        renderBlocks = new RenderBlocks();
        shadowSize = 0.5F;

    }

    public void doRenderFallingStone(EntityFallingStone entityfallingstone, double d, double d1, double d2,
            float f, float f1)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef((float)d, (float)d1, (float)d2);
        loadTexture(TFC_Textures.RockSheet);

        World world = entityfallingstone.getWorld();
        GL11.glDisable(2896 /*GL_LIGHTING*/);

        TFC_CoreRender.renderBlockFallingSand(Block.blocksList[entityfallingstone.blockID], entityfallingstone.metaID, world, MathHelper.floor_double(entityfallingstone.posX), MathHelper.floor_double(entityfallingstone.posY), MathHelper.floor_double(entityfallingstone.posZ));

        GL11.glEnable(2896 /*GL_LIGHTING*/);
        GL11.glPopMatrix();
    }

    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1)
    {
        doRenderFallingStone((EntityFallingStone)entity, d, d1, d2, f, f1);
    }
}
