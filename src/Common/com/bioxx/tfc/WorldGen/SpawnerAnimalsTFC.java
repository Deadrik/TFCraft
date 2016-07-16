package com.bioxx.tfc.WorldGen;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.init.Blocks;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase.SpawnListEntry;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Chunkdata.ChunkData;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Entities.Mobs.EntityFishTFC;
import com.bioxx.tfc.Entities.Mobs.EntityOcelotTFC;

public final class SpawnerAnimalsTFC
{
	/**
	 * Returns whether or not the specified creature type can spawn at the specified location.
	 */
	public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType par0EnumCreatureType, World par1World, int par2, int par3, int par4)
	{
		if (par0EnumCreatureType.getCreatureMaterial() == Material.water)
		{
			return par1World.getBlock(par2, par3, par4).getMaterial().isLiquid() &&
					par1World.getBlock(par2, par3 - 1, par4).getMaterial().isLiquid() &&
					!par1World.getBlock(par2, par3 + 1, par4).isNormalCube();
		}
		else if (!World.doesBlockHaveSolidTopSurface(par1World, par2, par3 - 1, par4))
		{
			return false;
		}
		else
		{
			Block b = par1World.getBlock(par2, par3 - 1, par4);
			boolean spawnBlock = b != null && b.canCreatureSpawn(par0EnumCreatureType, par1World, par2, par3 - 1, par4);
			return spawnBlock && b != Blocks.bedrock &&
					!par1World.getBlock(par2, par3, par4).isNormalCube() &&
					!par1World.getBlock(par2, par3, par4).getMaterial().isLiquid() &&
					!par1World.getBlock(par2, par3 + 1, par4).isNormalCube();
		}
	}

	/**
	 * Called during chunk generation to spawn initial creatures.
	 */
	public static void performWorldGenSpawning(World world, TFCBiome biome, int par2, int par3, int par4, int par5, Random par6Random)
	{
		List list = TFCChunkProviderGenerate.getCreatureSpawnsByChunk(world, biome, par2, par3);//par1BiomeGenBase.getSpawnableList(EnumCreatureType.creature);
		if (!list.isEmpty())
		{
			while (par6Random.nextFloat() < biome.getSpawningChance())
			{
				SpawnListEntry spawnlistentry = (SpawnListEntry)WeightedRandom.getRandomItem(world.rand, list);
				IEntityLivingData entitylivingdata = null;
				int i1 = spawnlistentry.minGroupCount + par6Random.nextInt(1 + spawnlistentry.maxGroupCount - spawnlistentry.minGroupCount);
				int j1 = par2 + par6Random.nextInt(par4);
				int k1 = par3 + par6Random.nextInt(par5);
				int l1 = j1;
				int i2 = k1;

				for (int j2 = 0; j2 < i1; ++j2)
				{
					boolean flag = false;
					for (int k2 = 0; !flag && k2 < 4; ++k2)
					{
						int l2 = world.getTopSolidOrLiquidBlock(j1, k1);
						if (canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, world, j1, l2, k1))
						{
							EntityLiving entityliving;
							try
							{
								entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {world});
							}
							catch (Exception exception)
							{
								TerraFirmaCraft.LOG.catching(exception);
								continue;
							}
							if(entityliving instanceof EntityFishTFC){
								if(entityliving.getRNG().nextInt((int)ChunkData.FISH_POP_MAX) > TFC_Core.getCDM(world).getFishPop(j1 >> 4, k1 >> 4)){
									return;
								}
							}

							float f = j1 + 0.5F;
							float f1 = l2;
							float f2 = k1 + 0.5F;
							entityliving.setLocationAndAngles(f, f1, f2, par6Random.nextFloat() * 360.0F, 0.0F);
							if(entityliving instanceof EntityOcelotTFC){
								if(entityliving.getCanSpawnHere()){
									world.spawnEntityInWorld(entityliving);
									entitylivingdata = entityliving.onSpawnWithEgg(entitylivingdata);
									flag = true;
								}
							}
							else
							{
								world.spawnEntityInWorld(entityliving);
								entitylivingdata = entityliving.onSpawnWithEgg(entitylivingdata);
								flag = true;	
							}

							
						}

						j1 += par6Random.nextInt(5) - par6Random.nextInt(5);
						for (k1 += par6Random.nextInt(5) - par6Random.nextInt(5);
								j1 < par2 || j1 >= par2 + par4 || k1 < par3 || k1 >= par3 + par4;
								k1 = i2 + par6Random.nextInt(5) - par6Random.nextInt(5))
						{
							j1 = l1 + par6Random.nextInt(5) - par6Random.nextInt(5);
						}
					}
				}
			}
		}
	}
}
