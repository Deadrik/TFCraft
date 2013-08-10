package TFC.Render;

import org.lwjgl.opengl.GL11;

import TFC.Entities.Mobs.EntityCowTFC;
import TFC.Entities.Mobs.EntitySheepTFC;
import TFC.Render.Models.ModelBaseTFC;
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

public class RenderSheepTFC extends RenderLivingTFC
{
    public RenderSheepTFC(ModelBase par1ModelBase, ModelBase par2ModelBase, float par3)
    {
        super((ModelBaseTFC)par1ModelBase, par3);
        this.setRenderPassModel((ModelBaseTFC) par2ModelBase);
    }

    protected int setWoolColorAndRender(EntitySheepTFC par1EntitySheep, int par2, float par3)
    {
        if (par2 == 0 && !par1EntitySheep.getSheared())
        {
            this.loadTexture("/mob/sheep_fur.png");
            float var4 = 1.0F;
            int var5 = par1EntitySheep.getFleeceColor();
            GL11.glColor3f(var4 * EntitySheepTFC.fleeceColorTable[var5][0], var4 * EntitySheepTFC.fleeceColorTable[var5][1], var4 * EntitySheepTFC.fleeceColorTable[var5][2]);
            return 1;
        }
        else
        {
            return -1;
        }
    }

    public void doRenderSheep(EntitySheepTFC par1EntitySheep, double par2, double par4, double par6, float par8, float par9)
    {
        super.doRenderLiving(par1EntitySheep, par2, par4, par6, par8, par9);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.setWoolColorAndRender((EntitySheepTFC)par1EntityLiving, par2, par3);
    }

    public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderSheep((EntitySheepTFC)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.doRenderSheep((EntitySheepTFC)par1Entity, par2, par4, par6, par8, par9);
    }
}
