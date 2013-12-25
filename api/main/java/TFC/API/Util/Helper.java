package TFC.API.Util;

import java.lang.reflect.Field;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;


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
		double var7 = entity.prevPosX + (entity.posX - entity.prevPosX) * var4;
		double var9 = entity.prevPosY + (entity.posY - entity.prevPosY) * var4 + 1.62D - entity.yOffset;
		double var11 = entity.prevPosZ + (entity.posZ - entity.prevPosZ) * var4;
		Vec3 var13 = Vec3.createVectorHelper(var7, var9, var11);
		float var14 = MathHelper.cos(-var6 * 0.017453292F - (float)Math.PI);
		float var15 = MathHelper.sin(-var6 * 0.017453292F - (float)Math.PI);
		float var16 = -MathHelper.cos(-var5 * 0.017453292F);
		float var17 = MathHelper.sin(-var5 * 0.017453292F);
		float var18 = var15 * var16;
		float var20 = var14 * var16;
		double var21 = 4; /*ModLoader.getMinecraftInstance().playerController.getBlockReachDistance()*/
		Vec3 var23 = var13.addVector(var18 * var21, var17 * var21, var20 * var21);
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
	public static void setPrivateValue(Class var0, Object var1, int var2, int var3) throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field var4 = var0.getDeclaredFields()[var2];
			var4.setAccessible(true);
			var4.set(var1, var3);
		}
		catch (IllegalAccessException var6)
		{
			System.out.println("setPrivateValue Error:" + var6.getMessage());
		}
	}

	public static void setPrivateValue(Class var0, Object var1, int var2, float var3) throws IllegalArgumentException, SecurityException, NoSuchFieldException
	{
		try
		{
			Field var4 = var0.getDeclaredFields()[var2];
			var4.setAccessible(true);
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
