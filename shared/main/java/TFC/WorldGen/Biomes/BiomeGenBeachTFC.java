package TFC.WorldGen.Biomes;

import TFC.WorldGen.BiomeDecoratorTFC;
import TFC.WorldGen.TFCBiome;
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

public class BiomeGenBeachTFC extends TFCBiome
{
	public BiomeGenBeachTFC(int i)
	{
		super(i);
		spawnableCreatureList.clear();
		topBlock = (byte)Block.sand.blockID;
		fillerBlock = (byte)Block.sand.blockID;
		((BiomeDecoratorTFC)this.theBiomeDecorator).treesPerChunk = -999;
		((BiomeDecoratorTFC)this.theBiomeDecorator).deadBushPerChunk = 0;
		((BiomeDecoratorTFC)this.theBiomeDecorator).reedsPerChunk = 0;
		((BiomeDecoratorTFC)this.theBiomeDecorator).cactiPerChunk = 0;
	}
}
