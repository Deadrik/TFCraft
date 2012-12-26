package TFC.WorldGen.Biomes;

import java.util.Random;
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
import TFC.Entities.*;
import TFC.Entities.Mobs.EntityBear;
import TFC.Entities.Mobs.EntityDeer;
import TFC.Entities.Mobs.EntityWolfTFC;
import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;


// Referenced classes of package net.minecraft.src:
//            BiomeGenBase, SpawnListEntry, EntityWolf, BiomeDecorator, 
//            WorldGenerator

public class BiomeGenTaigaTFC extends TFCBiome
{
	int treeCommon1 = -1;
	Boolean treeCommon1Size;
	int treeCommon2 = -1;
	Boolean treeCommon2Size;
	int treeUncommon = -1;
	Boolean treeUncommonSize;
	int treeRare = -1;
	Boolean treeRareSize;

	public BiomeGenTaigaTFC(int i)
	{
		super(i);
		spawnableCreatureList.add(new SpawnListEntry(EntityWolfTFC.class, 6, 1, 5));
        spawnableCreatureList.add(new SpawnListEntry(EntityBear.class, 6, 1, 2));
	spawnableCreatureList.add(new SpawnListEntry(EntityDeer.class, 10, 3, 8));
        ((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = 10;
		((BiomeDecoratorTFC)this.theBiomeDecorator).grassPerChunk = 1;
		treeCommon1 = 0;
		treeCommon2 = 0;
		treeUncommon = 0;
		treeRare = 0;
		setMinMaxHeight(0.2F, 0.4F);
		((BiomeDecoratorTFC)this.theBiomeDecorator).looseRocksPerChunk = 4;
        ((BiomeDecoratorTFC)this.theBiomeDecorator).looseRocksChancePerChunk = 90;
	}

	public WorldGenerator getRandomWorldGenForTrees(Random random, World world)
	{
		
		int rand = random.nextInt(100);
		if(rand < 40) {
			return getTreeGen(treeCommon1,treeCommon1Size);
		} else if(rand < 80) {
			return getTreeGen(treeCommon2,treeCommon2Size);
		} else if(rand < 95) {
			return getTreeGen(treeUncommon,treeUncommonSize);
		} else {
			return getTreeGen(treeRare,treeRareSize);
		}
	}
	
	public void SetupTrees(World world, Random R)
    {
	    WorldGenerator[] ConiferGenList = {
	            worldGenRedwoodShortTrees,
                worldGenWhiteCedarTallTrees,
                worldGenPineShortTrees,
                worldGenSpruceShortTrees,
                worldGenOakShortTrees,
                worldGenBirchShortTrees,
                worldGenAshTallTrees,
                worldGenWhiteElmTallTrees,
                worldGenOakTallTrees,
                worldGenBirchTallTrees,
                worldGenPineTallTrees,
                worldGenAspenTallTrees,
                worldGenAspenShortTrees};

	   while(treeCommon1 == 0 || treeCommon2 == 0)
	   {
            treeCommon1 = R.nextInt(ConiferGenList.length);
            treeCommon1Size = R.nextBoolean();
            treeCommon2 = R.nextInt(ConiferGenList.length);
            treeCommon2Size = R.nextBoolean();
            treeUncommon = R.nextInt(ConiferGenList.length);
            treeUncommonSize = R.nextBoolean();
            treeRare = R.nextInt(ConiferGenList.length);
            treeRareSize = R.nextBoolean();
	   }

    }
	
	protected float getMonthTemp(int month)
    {
        switch(month)
        {
            case 11:
                return -1F;
            case 0:
                return 0F;
            case 1:
                return 0.25F;
            case 2:
                return 0.8F;
            case 3:
                return 0.75F; 
            case 4:
                return 1F;
            case 5:
                return 0.75F;
            case 6:
                return 0.5F;
            case 7:
                return 0.25F;
            case 8:
                return 0F;
            case 9:
                return -1F;
            case 10:
                return -2F;
            default:
                return 1F;
        }
    }
	
}
