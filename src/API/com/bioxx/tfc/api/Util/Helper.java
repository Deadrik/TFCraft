package com.bioxx.tfc.api.Util;

import java.lang.reflect.Field;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Interfaces.ISize;


public class Helper {

	public static MovingObjectPosition getMouseOverObject(EntityLivingBase player, World world )
	{
		return getMovingObjectPositionFromPlayer(world, player, true);
	}

	public static double getReachDistance(World par1World, EntityLivingBase entity, boolean par3)
	{
		double var21 = 1; /*ModLoader.getMinecraftInstance().playerController.getBlockReachDistance()*/
		if(entity.getHeldItem()!=null && (entity.getHeldItem().getItem()) instanceof ISize){
			var21 *= ((ISize)(entity.getHeldItem().getItem())).getReach(null).multiplier;
		}

		else{
			var21 *= EnumItemReach.SHORT.multiplier;
		}
		return var21;
	}

	/**
	 * @return Returns an integer equal to the byte value of all chars in the passed string added together.
	 */
	public static int stringToInt(String s)
	{
		int result = 0;
		for(char c : s.toCharArray())
		{
			result += (byte)c;
		}
		return result;
	}

	public static MovingObjectPosition getMovingObjectPositionFromPlayer(World world, EntityLivingBase entity, boolean scanFluids)
	{
		return getMovingObjectPositionFromPlayer(world, entity, scanFluids, 4);
	}

	public static MovingObjectPosition getMovingObjectPositionFromPlayer(World world, EntityLivingBase entity, boolean scanFluids, int reach)
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

		Vec3 var23 = var13.addVector(var18 * reach, var17 * reach, var20 * reach);
		return world.rayTraceBlocks(var13, var23, scanFluids);
	}

	/**
	 * Returns the value of the first parameter, clamped to be within the lower and upper limits given by the second and
	 * third parameters
	 */
	public static float clampFloat(float par0, float par1, float par2)
	{
		return par0 < par1 ? par1 : par0 > par2 ? par2 : par0;
	}

	public static float roundNumber(float input, float rounding)
	{
		int o = (int)(input * rounding);
		return o / rounding;
	}

	private static boolean usesSRG(Object obj, String srgName)
	{
		Field[] fields = obj.getClass().getFields();
		for(Field f : fields)
		{
			if(f.getName().equals(srgName))
				return true;
		}
		return false;
	}

	public static int getInteger(Object obj, String srgName, String obfName, String deobfName, boolean useDeobf)
	{
		Field f = null;
		try 
		{
			if(!useDeobf)
				f = obj.getClass().getDeclaredField(deobfName);
			else if(usesSRG(obj, srgName))
				f = obj.getClass().getDeclaredField(srgName);
			else
				f = obj.getClass().getDeclaredField(obfName);
			f.setAccessible(true);
			return (Integer) f.get(obj);
		} catch (NoSuchFieldException e) {
			TerraFirmaCraft.LOG.catching(e);
		} catch (SecurityException e) {
			TerraFirmaCraft.LOG.catching(e);
		} catch (IllegalArgumentException e) {
			TerraFirmaCraft.LOG.catching(e);
		} catch (IllegalAccessException e) {
			TerraFirmaCraft.LOG.catching(e);
		}
		return 0; 		
	}

	public static boolean getBoolean(Object obj, String srgName, String obfName, String deobfName, boolean useDeobf)
	{
		Field f = null;
		try 
		{
			if(!useDeobf)
				f = obj.getClass().getDeclaredField(deobfName);
			else if(usesSRG(obj, srgName))
				f = obj.getClass().getDeclaredField(srgName);
			else
				f = obj.getClass().getDeclaredField(obfName);
			f.setAccessible(true);
			return (Boolean) f.get(obj);
		} catch (NoSuchFieldException e) {
			TerraFirmaCraft.LOG.catching(e);
		} catch (SecurityException e) {
			TerraFirmaCraft.LOG.catching(e);
		} catch (IllegalArgumentException e) {
			TerraFirmaCraft.LOG.catching(e);
		} catch (IllegalAccessException e) {
			TerraFirmaCraft.LOG.catching(e);
		}
		return false; 		
	}

	public static Object getObject(Object obj, String srgName, String obfName, String deobfName, boolean useDeobf)
	{
		Field f = null;
		try 
		{
			if(!useDeobf)
				f = obj.getClass().getDeclaredField(deobfName);
			else if(usesSRG(obj, srgName))
				f = obj.getClass().getDeclaredField(srgName);
			else
				f = obj.getClass().getDeclaredField(obfName);
			f.setAccessible(true);
			return f.get(obj);
		} catch (NoSuchFieldException e) {
			TerraFirmaCraft.LOG.catching(e);
		} catch (SecurityException e) {
			TerraFirmaCraft.LOG.catching(e);
		} catch (IllegalArgumentException e) {
			TerraFirmaCraft.LOG.catching(e);
		} catch (IllegalAccessException e) {
			TerraFirmaCraft.LOG.catching(e);
		}
		return null; 		
	}
}
