package com.bioxx.tfc.Blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.TFCOptions;

import static net.minecraftforge.common.util.ForgeDirection.UP;

public abstract class BlockTerra extends Block
{
	protected BlockTerra()
	{
		super(Material.rock);
	}

	protected BlockTerra(Material material)
	{
		super(material);
	}

	@Override
	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLivingBase entityliving, ItemStack is)
	{
		//TODO: Debug Message should go here if debug is toggled on
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			TerraFirmaCraft.LOG.info("Meta=" + (new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)  
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			TerraFirmaCraft.LOG.info("Meta = " + (new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
		return false;
	}

	public void onBlockPlacedBy(World world, int x, int y, int z, EntityLiving entityliving)
	{
		onBlockPlacedBy(world, x, y, z, entityliving, null);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer player, int x, int y, int z, int meta)
	{
		super.harvestBlock(world, player, x, y, z, meta);
		TFC_Core.addPlayerExhaustion(player, 0.001f);
	}

	@Override
	public boolean canSustainPlant(IBlockAccess world, int x, int y, int z, ForgeDirection direction, IPlantable plantable)
	{
		Block plant = plantable.getPlant(world, x, y + 1, z);
		if (plant == Blocks.cactus && this == Blocks.cactus)
		{
			return true;
		}

		if (plant == Blocks.reeds && this == Blocks.reeds)
		{
			return true;
		}

		EnumPlantType plantType = plantable.getPlantType(world, x, y + 1, z);
		switch (plantType)
		{
		case Cave:   return isSideSolid(world, x, y, z, UP);
		case Plains: return TFC_Core.isSoil(this) || TFC_Core.isFarmland(this);
		case Water:  return world.getBlock(x, y, z).getMaterial() == Material.water && world.getBlockMetadata(x, y, z) == 0;
		case Beach:
			boolean isBeach = TFC_Core.isSand(this) || TFC_Core.isGravel(this);
			boolean hasWater = world.getBlock(x - 1, y, z    ).getMaterial() == Material.water ||
					world.getBlock(x + 1, y, z    ).getMaterial() == Material.water ||
					world.getBlock(x,     y, z - 1).getMaterial() == Material.water ||
					world.getBlock(x,     y, z + 1).getMaterial() == Material.water;
			return isBeach && hasWater;
		default: return false;
		}
	}
}
