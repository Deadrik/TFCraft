package TFC.WorldGen.Generators;

import java.util.Random;

import TFC.*;
import TFC.Blocks.BlockCrop;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Time;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.TileEntities.TileEntityCrop;

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
import net.minecraft.world.gen.feature.WorldGenerator;

public class WorldGenGrowCrops extends WorldGenerator
{
	/** The ID of the plant block used in this plant generator. */
	private int cropBlockId;

	public WorldGenGrowCrops(int par1)
	{
		this.cropBlockId = par1;
	}

	@Override
	public boolean generate(World world, Random par2Random, int x, int y, int z)
	{
		int i = x - 5 + par2Random.nextInt(10);
		int k = z - 5 + par2Random.nextInt(10);
		int j = world.getHeightValue(x, z)+1;

		CropIndex crop = CropManager.getInstance().getCropFromId(cropBlockId);
		if(crop != null)
		{
			float temp = TFC_Climate.getHeightAdjustedTemp(i, j, k);
			float growth =  Math.min(crop.numGrowthStages-par2Random.nextInt(3), crop.numGrowthStages);

			if(temp > crop.minAliveTemp)
			{
				if (world.isAirBlock(i, j, k) && ((BlockCrop)Block.blocksList[Block.crops.blockID]).canBlockStay(world, i, j, k))
				{
					world.setBlockAndMetadataWithNotify(i, j, k, Block.crops.blockID, 0, 2);
					TileEntityCrop te = (TileEntityCrop)world.getBlockTileEntity(i, j, k);
					te.cropId = cropBlockId;
					te.growth = growth;
					return true;
				}
			}
		}
		return false;
	}
}
