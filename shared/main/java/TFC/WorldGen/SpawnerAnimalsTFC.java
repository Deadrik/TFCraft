package TFC.WorldGen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingData;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.WeightedRandom;
import net.minecraft.world.ChunkCoordIntPair;
import net.minecraft.world.ChunkPosition;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraft.world.biome.SpawnListEntry;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.event.Event.Result;
import net.minecraftforge.event.ForgeEventFactory;

public final class SpawnerAnimalsTFC
{
	/** The 17x17 area around the player where mobs can spawn */
	private HashMap eligibleChunksForSpawning = new HashMap();

	/**
	 * Given a chunk, find a random position in it.
	 */
	protected static ChunkPosition getRandomSpawningPointInChunk(World par0World, int par1, int par2)
	{
		Chunk chunk = par0World.getChunkFromChunkCoords(par1, par2);
		int k = par1 * 16 + par0World.rand.nextInt(16);
		int l = par2 * 16 + par0World.rand.nextInt(16);
		int i1 = par0World.rand.nextInt(chunk == null ? par0World.getActualHeight() : chunk.getTopFilledSegment() + 16 - 1);
		return new ChunkPosition(k, i1, l);
	}

	/**
	 * adds all chunks within the spawn radius of the players to eligibleChunksForSpawning. pars: the world,
	 * hostileCreatures, passiveCreatures. returns number of eligible chunks.
	 */
	public int findChunksForSpawning(WorldServer par1WorldServer, boolean par2, boolean par3, boolean par4)
	{
		if (!par2 && !par3)
		{
			return 0;
		}
		else
		{
			this.eligibleChunksForSpawning.clear();
			int i;
			int j;

			for (i = 0; i < par1WorldServer.playerEntities.size(); ++i)
			{
				EntityPlayer entityplayer = (EntityPlayer)par1WorldServer.playerEntities.get(i);
				int k = MathHelper.floor_double(entityplayer.posX / 16.0D);
				j = MathHelper.floor_double(entityplayer.posZ / 16.0D);
				byte b0 = 8;

				for (int l = -b0; l <= b0; ++l)
				{
					for (int i1 = -b0; i1 <= b0; ++i1)
					{
						boolean flag3 = l == -b0 || l == b0 || i1 == -b0 || i1 == b0;
						ChunkCoordIntPair chunkcoordintpair = new ChunkCoordIntPair(l + k, i1 + j);

						if (!flag3)
						{
							this.eligibleChunksForSpawning.put(chunkcoordintpair, Boolean.valueOf(false));
						}
						else if (!this.eligibleChunksForSpawning.containsKey(chunkcoordintpair))
						{
							this.eligibleChunksForSpawning.put(chunkcoordintpair, Boolean.valueOf(true));
						}
					}
				}
			}

			i = 0;
			ChunkCoordinates chunkcoordinates = par1WorldServer.getSpawnPoint();
			EnumCreatureType[] aenumcreaturetype = EnumCreatureType.values();
			j = aenumcreaturetype.length;

			for (int j1 = 0; j1 < j; ++j1)
			{
				EnumCreatureType enumcreaturetype = aenumcreaturetype[j1];

				if ((!enumcreaturetype.getPeacefulCreature() || par3) && (enumcreaturetype.getPeacefulCreature() || par2) && (!enumcreaturetype.getAnimal() || par4) && par1WorldServer.countEntities(enumcreaturetype, true) <= enumcreaturetype.getMaxNumberOfCreature() * this.eligibleChunksForSpawning.size() / 256)
				{
					Iterator iterator = this.eligibleChunksForSpawning.keySet().iterator();
					ArrayList<ChunkCoordIntPair> tmp = new ArrayList(eligibleChunksForSpawning.keySet());
					Collections.shuffle(tmp);
					iterator = tmp.iterator();
					label110:

						while (iterator.hasNext())
						{
							ChunkCoordIntPair chunkcoordintpair1 = (ChunkCoordIntPair)iterator.next();

							if (!((Boolean)this.eligibleChunksForSpawning.get(chunkcoordintpair1)).booleanValue())
							{
								ChunkPosition chunkposition = getRandomSpawningPointInChunk(par1WorldServer, chunkcoordintpair1.chunkXPos, chunkcoordintpair1.chunkZPos);
								int k1 = chunkposition.x;
								int l1 = chunkposition.y;
								int i2 = chunkposition.z;

								if (!par1WorldServer.isBlockNormalCube(k1, l1, i2) && par1WorldServer.getBlockMaterial(k1, l1, i2) == enumcreaturetype.getCreatureMaterial())
								{
									int j2 = 0;
									int k2 = 0;

									while (k2 < 3)
									{
										int l2 = k1;
										int i3 = l1;
										int j3 = i2;
										byte b1 = 6;
										SpawnListEntry spawnlistentry = null;
										EntityLivingData entitylivingdata = null;
										int k3 = 0;

										while (true)
										{
											if (k3 < 4)
											{
												label103:
												{
												l2 += par1WorldServer.rand.nextInt(b1) - par1WorldServer.rand.nextInt(b1);
												i3 += par1WorldServer.rand.nextInt(1) - par1WorldServer.rand.nextInt(1);
												j3 += par1WorldServer.rand.nextInt(b1) - par1WorldServer.rand.nextInt(b1);

												if (canCreatureTypeSpawnAtLocation(enumcreaturetype, par1WorldServer, l2, i3, j3))
												{
													float f = l2 + 0.5F;
													float f1 = i3;
													float f2 = j3 + 0.5F;

													if (par1WorldServer.getClosestPlayer(f, f1, f2, 24.0D) == null)
													{
														float f3 = f - chunkcoordinates.posX;
														float f4 = f1 - chunkcoordinates.posY;
														float f5 = f2 - chunkcoordinates.posZ;
														float f6 = f3 * f3 + f4 * f4 + f5 * f5;

														if (f6 >= 576.0F)
														{
															if (spawnlistentry == null)
															{
																spawnlistentry = par1WorldServer.spawnRandomCreature(enumcreaturetype, l2, i3, j3);

																if (spawnlistentry == null)
																{
																	break label103;
																}
															}

															EntityLiving entityliving;

															try
															{
																entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par1WorldServer});
															}
															catch (Exception exception)
															{
																exception.printStackTrace();
																return i;
															}

															entityliving.setLocationAndAngles(f, f1, f2, par1WorldServer.rand.nextFloat() * 360.0F, 0.0F);

															Result canSpawn = ForgeEventFactory.canEntitySpawn(entityliving, par1WorldServer, f, f1, f2);
															if (canSpawn == Result.ALLOW || (canSpawn == Result.DEFAULT && entityliving.getCanSpawnHere()))
															{
																++j2;
																par1WorldServer.spawnEntityInWorld(entityliving);
																if (!ForgeEventFactory.doSpecialSpawn(entityliving, par1WorldServer, f, f1, f2))
																{
																	entitylivingdata = entityliving.onSpawnWithEgg(entitylivingdata);
																}

																if (j2 >= ForgeEventFactory.getMaxSpawnPackSize(entityliving))
																{
																	continue label110;
																}
															}

															i += j2;
														}
													}
												}

												++k3;
												continue;
												}
											}

											++k2;
											break;
										}
									}
								}
							}
						}
				}
			}

			return i;
		}
	}

	/**
	 * Returns whether or not the specified creature type can spawn at the specified location.
	 */
	public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType par0EnumCreatureType, World par1World, int par2, int par3, int par4)
	{
		if (par0EnumCreatureType.getCreatureMaterial() == Material.water)
		{
			return par1World.getBlockMaterial(par2, par3, par4).isLiquid() && par1World.getBlockMaterial(par2, par3 - 1, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
		}
		else if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4))
		{
			return false;
		}
		else
		{
			int l = par1World.getBlockId(par2, par3 - 1, par4);
			boolean spawnBlock = (Block.blocksList[l] != null && Block.blocksList[l].canCreatureSpawn(par0EnumCreatureType, par1World, par2, par3 - 1, par4));
			return spawnBlock && l != Block.bedrock.blockID && !par1World.isBlockNormalCube(par2, par3, par4) && !par1World.getBlockMaterial(par2, par3, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
		}
	}

	/**
	 * Called during chunk generation to spawn initial creatures.
	 */
	public static void performWorldGenSpawning(World par0World, BiomeGenBase par1BiomeGenBase, int par2, int par3, int par4, int par5, Random par6Random)
	{
		List list = TFCChunkProviderGenerate.getCreatureSpawnsByChunk(par0World,par1BiomeGenBase, par2, par3);//par1BiomeGenBase.getSpawnableList(EnumCreatureType.creature);

		if (!list.isEmpty())
		{
			while (par6Random.nextFloat() < par1BiomeGenBase.getSpawningChance())
			{
				SpawnListEntry spawnlistentry = (SpawnListEntry)WeightedRandom.getRandomItem(par0World.rand, list);
				EntityLivingData entitylivingdata = null;
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
						int l2 = par0World.getTopSolidOrLiquidBlock(j1, k1);

						if (canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, par0World, j1, l2, k1))
						{
							float f = j1 + 0.5F;
							float f1 = l2;
							float f2 = k1 + 0.5F;
							EntityLiving entityliving;

							try
							{
								entityliving = (EntityLiving)spawnlistentry.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par0World});
							}
							catch (Exception exception)
							{
								exception.printStackTrace();
								continue;
							}

							entityliving.setLocationAndAngles(f, f1, f2, par6Random.nextFloat() * 360.0F, 0.0F);
							par0World.spawnEntityInWorld(entityliving);
							entitylivingdata = entityliving.onSpawnWithEgg(entitylivingdata);
							flag = true;
						}

						j1 += par6Random.nextInt(5) - par6Random.nextInt(5);

						for (k1 += par6Random.nextInt(5) - par6Random.nextInt(5); j1 < par2 || j1 >= par2 + par4 || k1 < par3 || k1 >= par3 + par4; k1 = i2 + par6Random.nextInt(5) - par6Random.nextInt(5))
						{
							j1 = l1 + par6Random.nextInt(5) - par6Random.nextInt(5);
						}
					}
				}
			}
		}
	}
}
