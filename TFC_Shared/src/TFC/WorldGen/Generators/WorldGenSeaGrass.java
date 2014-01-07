package TFC.WorldGen.Generators;

import java.util.Random;
import TFC.*;
import TFC.Blocks.Flora.BlockTallSeaGrass;
import TFC.Core.TFC_Core;
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

public class WorldGenSeaGrass extends WorldGenerator
{
	/** The ID of the plant block used in this plant generator. */
	private int plantBlockId;
	private boolean isSwamp;

	public WorldGenSeaGrass(int par1,boolean isSwamp)
	{
		this.plantBlockId = par1;
		this.isSwamp = isSwamp;
	}

	public boolean generate(World par1World, Random par2Random, int x, int y, int z)
	{
		int n = isSwamp?25:10;
		for (int var6 = 0; var6 < n; ++var6)
		{
			int var7 = x + (par2Random.nextInt(8) + par2Random.nextInt(4)) - (par2Random.nextInt(3) + par2Random.nextInt(2));
			int var8 = y + par2Random.nextInt(4) - par2Random.nextInt(4);
			int var9 = z + (par2Random.nextInt(8) + par2Random.nextInt(4)) - (par2Random.nextInt(3) + par2Random.nextInt(2));

			if (par1World.isAirBlock(var7, var8, var9)){
				//How far underwater are we going
				int depthCounter = 0;
				//Effectively makes sea grass grow less frequently as depth increases beyond 6 m.
				boolean randomTooDeepFlag = false;
				//travel down until a solid surface is reached
				while(var8 > 0 && TFC_Core.isWater(par1World.getBlockId(var7, --var8, var9)) && !randomTooDeepFlag){
					depthCounter++;
					if(depthCounter >= 6){
						//If depthCounter reaches 11, automatically prevents plants from growing
						randomTooDeepFlag = (par2Random.nextInt(12 - depthCounter)==0);
					}
				}
				var8++;
				if(!randomTooDeepFlag && depthCounter >0&&((BlockTallSeaGrass)Block.blocksList[this.plantBlockId]).canBlockStay(par1World, var7, var8, var9))
				{
					par1World.setBlock(var7, var8, var9, this.plantBlockId,0,1);
					//Gravelly areas will spawn fewer plants
					if(TFC_Core.isGravel(par1World.getBlockId(var7, var8-1, var9))){
						n--;
					}
				}
			}
		}

		return true;
	}
}
