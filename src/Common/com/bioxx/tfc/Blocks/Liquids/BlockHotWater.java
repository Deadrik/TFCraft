package com.bioxx.tfc.Blocks.Liquids;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fluids.Fluid;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Effects.GasFX;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockHotWater extends BlockCustomLiquid
{
	public BlockHotWater(Fluid fluid)
	{
		super(fluid, Material.water);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return 0x1f5099;
	}

	@Override
	protected Block getInverseBlock() 
	{
		return TFCBlocks.HotWaterStationary;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if(world.isAirBlock(i-1, j, k) || world.isAirBlock(i+1, j, k) || 
				world.isAirBlock(i, j, k-1) || world.isAirBlock(i, j, k+1) || 
				world.isAirBlock(i, j+1, k))
			;
		else return;

		double f = (double)i + 0.5F;
		double f1 = (double)j + 1f;
		double f2 = (double)k + 0.5F;

		double f4 = random.nextFloat() * -0.1F;
		double f5 = random.nextFloat() * -0.1F;
		double f6 = random.nextFloat() * -0.1F;

		Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, f,f1,f2,f4,f5,f6));
		f4 = random.nextFloat() * -0.1F;
		f5 = random.nextFloat() * -0.1F;
		f6 = random.nextFloat() * -0.1F;
		Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, f,f1,f2,f4,f5,f6));
		f4 = random.nextFloat() * -0.1F;
		f5 = random.nextFloat() * -0.1F;
		f6 = random.nextFloat() * -0.1F;
		Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, f,f1,f2,f4,f5,f6));
		f4 = random.nextFloat() * -0.1F;
		f5 = random.nextFloat() * -0.1F;
		f6 = random.nextFloat() * -0.1F;
		Minecraft.getMinecraft().effectRenderer.addEffect(new GasFX(world, f,f1,f2,f4,f5,f6));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerBlockIcons(IIconRegister registerer)
	{
		icons = new IIcon[]{registerer.registerIcon(Reference.ModID+":water_still"),registerer.registerIcon(Reference.ModID+":water_still"), registerer.registerIcon(Reference.ModID+":water_flow")};
		this.getFluid().setIcons(registerer.registerIcon(Reference.ModID+":water_still"), registerer.registerIcon(Reference.ModID+":water_flow"));
		TFC_Textures.GasFXIcon = registerer.registerIcon(Reference.ModID + ":" + "Steam");
		TFC_Textures.GuiInventory = registerer.registerIcon(Reference.ModID + ":" + "button_inv");
		TFC_Textures.GuiSkills = registerer.registerIcon(Reference.ModID + ":" + "button_skills");
		TFC_Textures.GuiCalendar = registerer.registerIcon(Reference.ModID + ":" + "button_calendar");
		TFC_Textures.GuiHealth = registerer.registerIcon(Reference.ModID + ":" + "button_health");
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		if (entity instanceof EntityLivingBase)
		{
			EntityLivingBase e = ((EntityLivingBase) entity);
			if(world.rand.nextInt(25) == 0 && e.getHealth() < e.getMaxHealth())
			{
				float diff = e.getMaxHealth() - e.getHealth();
				e.heal(Math.max(diff * 0.001f, 0.0001f));
			}
		}
	}
}
