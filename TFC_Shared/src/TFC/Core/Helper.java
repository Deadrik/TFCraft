package TFC.Core;

import java.lang.reflect.Field;

import net.minecraft.src.ModLoader;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
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
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;


public class Helper {

	public static MovingObjectPosition getMouseOverObject(EntityLivingBase player, World world )
	{
		return getMovingObjectPositionFromPlayer(world, player, true);
	}

	public static MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityLivingBase entity, boolean par3)
	{
		float var4 = 1.0F;
		float var5 = entity.prevRotationPitch + (entity.rotationPitch - entity.prevRotationPitch) * var4;
		float var6 = entity.prevRotationYaw + (entity.rotationYaw - entity.prevRotationYaw) * var4;
		double var7 = entity.prevPosX + (entity.posX - entity.prevPosX) * (double)var4;
		double var9 = entity.prevPosY + (entity.posY - entity.prevPosY) * (double)var4 + 1.62D - (double)entity.yOffset;
		double var11 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * (double)var4;
		Vec3 var13 = Vec3.createVectorHelper(var7, var9, var11);
		float var14 = MathHelper.cos(-var6 * 0.017453292F - (float)Math.PI);
		float var15 = MathHelper.sin(-var6 * 0.017453292F - (float)Math.PI);
		float var16 = -MathHelper.cos(-var5 * 0.017453292F);
		float var17 = MathHelper.sin(-var5 * 0.017453292F);
		float var18 = var15 * var16;
		float var20 = var14 * var16;
		double var21 = 4; /*ModLoader.getMinecraftInstance().playerController.getBlockReachDistance()*/
		Vec3 var23 = var13.addVector((double)var18 * var21, (double)var17 * var21, (double)var20 * var21);
		MovingObjectPosition var24 = par1World.rayTraceBlocks_do_do(var13, var23, par3, !par3);
		return var24;
	}
	
	/**
     * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
     * third parameters
     */
    public static float clamp_float(float par0, float par1, float par2)
    {
        return par0 < par1 ? par1 : (par0 > par2 ? par2 : par0);
    }
    
    private static Field field_modifiers = null;
    public static void setPrivateValue(Class var0, Object var1, int var2, Object var3) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
            Field var4 = var0.getDeclaredFields()[var2];
            var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);

            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }

            var4.set(var1, var3);
        }
        catch (IllegalAccessException var6)
        {
            System.out.println("setPrivateValue Error:" + var6.getMessage());
        }
    }

    public static void setPrivateValue(Class var0, Object var1, String var2, Object var3) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
            Field var4 = var0.getDeclaredField(var2);
            int var5 = field_modifiers.getInt(var4);

            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }

            var4.setAccessible(true);
            var4.set(var1, var3);
        }
        catch (IllegalAccessException var6)
        {
            System.out.println("setPrivateValue Error:" + var6.getMessage());
        }
    }

}
