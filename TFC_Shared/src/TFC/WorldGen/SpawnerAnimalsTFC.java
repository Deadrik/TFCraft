package TFC.WorldGen;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Random;

import TFC.Chunkdata.ChunkData;
import TFC.Chunkdata.ChunkDataManager;
import TFC.Entities.Mobs.*;
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
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingSpawnEvent;

public class SpawnerAnimalsTFC
{
	/** The 17x17 area around the player where mobs can spawn */
	public static HashMap eligibleChunksForSpawning = new HashMap();

    /** An array of entity classes that spawn at night. */
    public static Class[] nightSpawnEntities = new Class[] {EntitySpiderTFC.class, EntityZombieTFC.class, EntitySkeletonTFC.class};

    /**
     * Given a chunk, find a random position in it.
     */
    public static ChunkPosition getRandomSpawningPointInChunk(World par0World, int par1, int par2)
    {
        Chunk var3 = par0World.getChunkFromChunkCoords(par1, par2);
        int var4 = par1 * 16 + par0World.rand.nextInt(16);
        int var5 = par2 * 16 + par0World.rand.nextInt(16);
        int var6 = par0World.rand.nextInt(var3 == null ? par0World.getActualHeight() : var3.getTopFilledSegment() + 16 - 1);
        return new ChunkPosition(var4, var6, var5);
    }
    
    /**
     * adds all chunks within the spawn radius of the players to eligibleChunksForSpawning. pars: the world,
     * hostileCreatures, passiveCreatures. returns number of eligible chunks.
     */
    public static int findChunksForSpawning(WorldServer par0WorldServer, boolean par1, boolean par2)
    {
        if (!par1 && !par2)
        {
            return 0;
        }
        else
        {
            eligibleChunksForSpawning.clear();
            int var3;
            int var6;

            for (var3 = 0; var3 < par0WorldServer.playerEntities.size(); ++var3)
            {
                EntityPlayer var4 = (EntityPlayer)par0WorldServer.playerEntities.get(var3);
                int var5 = MathHelper.floor_double(var4.posX / 16.0D);
                var6 = MathHelper.floor_double(var4.posZ / 16.0D);
                byte var7 = 8;

                for (int var8 = -var7; var8 <= var7; ++var8)
                {
                    for (int var9 = -var7; var9 <= var7; ++var9)
                    {
                        boolean var10 = var8 == -var7 || var8 == var7 || var9 == -var7 || var9 == var7;
                        ChunkCoordIntPair var11 = new ChunkCoordIntPair(var8 + var5, var9 + var6);

                        if (!var10)
                        {
                            eligibleChunksForSpawning.put(var11, Boolean.valueOf(false));
                        }
                        else if (!eligibleChunksForSpawning.containsKey(var11))
                        {
                            eligibleChunksForSpawning.put(var11, Boolean.valueOf(true));
                        }
                    }
                }
            }

            var3 = 0;
            ChunkCoordinates var31 = par0WorldServer.getSpawnPoint();
            EnumCreatureType[] var32 = EnumCreatureType.values();
            var6 = var32.length;

            for (int var33 = 0; var33 < var6; ++var33)
            {
                EnumCreatureType var34 = var32[var33];

                if ((!var34.getPeacefulCreature() || par2) && (var34.getPeacefulCreature() || par1) && par0WorldServer.countEntities(var34.getCreatureClass()) <= var34.getMaxNumberOfCreature() * eligibleChunksForSpawning.size() / 256)
                {
                    Iterator var35 = eligibleChunksForSpawning.keySet().iterator();
                    ArrayList<ChunkCoordIntPair> tmp = new ArrayList(eligibleChunksForSpawning.keySet());
                    Collections.shuffle(tmp);
                    var35 = tmp.iterator();
                    label108:

                    while (var35.hasNext())
                    {
                        ChunkCoordIntPair var37 = (ChunkCoordIntPair)var35.next();

                        if (!((Boolean)eligibleChunksForSpawning.get(var37)).booleanValue())
                        {
                            ChunkPosition var36 = getRandomSpawningPointInChunk(par0WorldServer, var37.chunkXPos, var37.chunkZPos);
                            int var12 = var36.x;
                            int var13 = var36.y;
                            int var14 = var36.z;

                            if (!par0WorldServer.isBlockNormalCube(var12, var13, var14) && par0WorldServer.getBlockMaterial(var12, var13, var14) == var34.getCreatureMaterial())
                            {
                                int var15 = 0;
                                int var16 = 0;

                                while (var16 < 3)
                                {
                                    int var17 = var12;
                                    int var18 = var13;
                                    int var19 = var14;
                                    byte var20 = 6;
                                    SpawnListEntry var21 = null;
                                    int var22 = 0;

                                    while (true)
                                    {
                                        if (var22 < 4)
                                        {
                                            label101:
                                            {
                                                var17 += par0WorldServer.rand.nextInt(var20) - par0WorldServer.rand.nextInt(var20);
                                                var18 += par0WorldServer.rand.nextInt(1) - par0WorldServer.rand.nextInt(1);
                                                var19 += par0WorldServer.rand.nextInt(var20) - par0WorldServer.rand.nextInt(var20);

                                                if (canCreatureTypeSpawnAtLocation(var34, par0WorldServer, var17, var18, var19))
                                                {
                                                    float var23 = (float)var17 + 0.5F;
                                                    float var24 = (float)var18;
                                                    float var25 = (float)var19 + 0.5F;

                                                    if (par0WorldServer.getClosestPlayer((double)var23, (double)var24, (double)var25, 24.0D) == null)
                                                    {
                                                        float var26 = var23 - (float)var31.posX;
                                                        float var27 = var24 - (float)var31.posY;
                                                        float var28 = var25 - (float)var31.posZ;
                                                        float var29 = var26 * var26 + var27 * var27 + var28 * var28;

                                                        if (var29 >= 576.0F)
                                                        {
                                                            if (var21 == null)
                                                            {
                                                                var21 = par0WorldServer.spawnRandomCreature(var34, var17, var18, var19);

                                                                if (var21 == null)
                                                                {
                                                                    break label101;
                                                                }
                                                            }

                                                            EntityLiving var38;

                                                            try
                                                            {
                                                                var38 = (EntityLiving)var21.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par0WorldServer});
                                                            }
                                                            catch (Exception var30)
                                                            {
                                                                var30.printStackTrace();
                                                                return var3;
                                                            }

                                                            var38.setLocationAndAngles((double)var23, (double)var24, (double)var25, par0WorldServer.rand.nextFloat() * 360.0F, 0.0F);

                                                            int x = var17 >> 4;
                                							int z = var19 >> 4;
                                                            
                                                            ChunkData data = (ChunkData) ChunkDataManager.chunkmap.get(x + "," + z);
                                                            
                                                            if (var38.getCanSpawnHere() && (data == null || data.getSpawnProtectionWithUpdate() <= 0))
                                                            {
                                                                ++var15;
                                                                par0WorldServer.spawnEntityInWorld(var38);
                                                                creatureSpecificInit(var38, par0WorldServer, var23, var24, var25);

                                                                if (var15 >= var38.getMaxSpawnedInChunk())
                                                                {
                                                                    continue label108;
                                                                }
                                                            }

                                                            var3 += var15;
                                                        }
                                                    }
                                                }

                                                ++var22;
                                                continue;
                                            }
                                        }

                                        ++var16;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                }
            }

            return var3;
        }
    }

    /**
     * Returns whether or not the specified creature type can spawn at the specified location.
     */
    public static boolean canCreatureTypeSpawnAtLocation(EnumCreatureType par0EnumCreatureType, World par1World, int par2, int par3, int par4)
    {
        if (par0EnumCreatureType.getCreatureMaterial() == Material.water)
        {
            return par1World.getBlockMaterial(par2, par3, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
        }
        else if (!par1World.doesBlockHaveSolidTopSurface(par2, par3 - 1, par4))
        {
            return false;
        }
        else
        {
            int var5 = par1World.getBlockId(par2, par3 - 1, par4);
            boolean spawnBlock = (Block.blocksList[var5] != null && Block.blocksList[var5].canCreatureSpawn(par0EnumCreatureType, par1World, par2, par3 - 1, par4));
            return spawnBlock && var5 != Block.bedrock.blockID && !par1World.isBlockNormalCube(par2, par3, par4) && !par1World.getBlockMaterial(par2, par3, par4).isLiquid() && !par1World.isBlockNormalCube(par2, par3 + 1, par4);
        }
    }

    /**
     * determines if a skeleton spawns on a spider, and if a sheep is a different color
     */
    public static void creatureSpecificInit(EntityLiving par0EntityLiving, World par1World, float par2, float par3, float par4)
    {
    	if (MinecraftForge.EVENT_BUS.post(new LivingSpawnEvent(par0EntityLiving, par1World, par2, par3, par4)))
        {
            return;
        }

        par0EntityLiving.initCreature();
    }

    /**
     * Called during chunk generation to spawn initial creatures.
     */
    public static void performWorldGenSpawning(World par0World, BiomeGenBase par1BiomeGenBase, int par2, int par3, int par4, int par5, Random par6Random)
    {
        List var7 = par1BiomeGenBase.getSpawnableList(EnumCreatureType.creature);

        if (!var7.isEmpty())
        {
            while (par6Random.nextFloat() < par1BiomeGenBase.getSpawningChance())
            {
                SpawnListEntry var8 = (SpawnListEntry)WeightedRandom.getRandomItem(par0World.rand, var7);
                int var9 = var8.minGroupCount + par6Random.nextInt(1 + var8.maxGroupCount - var8.minGroupCount);
                int var10 = par2 + par6Random.nextInt(par4);
                int var11 = par3 + par6Random.nextInt(par5);
                int var12 = var10;
                int var13 = var11;

                for (int var14 = 0; var14 < var9; ++var14)
                {
                    boolean var15 = false;

                    for (int var16 = 0; !var15 && var16 < 4; ++var16)
                    {
                        int var17 = par0World.getTopSolidOrLiquidBlock(var10, var11);

                        if (canCreatureTypeSpawnAtLocation(EnumCreatureType.creature, par0World, var10, var17, var11))
                        {
                            float var18 = (float)var10 + 0.5F;
                            float var19 = (float)var17;
                            float var20 = (float)var11 + 0.5F;
                            EntityLiving var21;

                            try
                            {
                                var21 = (EntityLiving)var8.entityClass.getConstructor(new Class[] {World.class}).newInstance(new Object[] {par0World});
                            }
                            catch (Exception var23)
                            {
                                var23.printStackTrace();
                                continue;
                            }

                            var21.setLocationAndAngles((double)var18, (double)var19, (double)var20, par6Random.nextFloat() * 360.0F, 0.0F);
                            par0World.spawnEntityInWorld(var21);
                            creatureSpecificInit(var21, par0World, var18, var19, var20);
                            var15 = true;
                        }

                        var10 += par6Random.nextInt(5) - par6Random.nextInt(5);

                        for (var11 += par6Random.nextInt(5) - par6Random.nextInt(5); var10 < par2 || var10 >= par2 + par4 || var11 < par3 || var11 >= par3 + par4; var11 = var13 + par6Random.nextInt(5) - par6Random.nextInt(5))
                        {
                            var10 = var12 + par6Random.nextInt(5) - par6Random.nextInt(5);
                        }
                    }
                }
            }
        }
    }
}
