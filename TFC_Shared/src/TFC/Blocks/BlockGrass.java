package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TFC.*;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Textures;
import TFC.TileEntities.TileEntityPartial;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.Generators.WorldGenGrowTrees;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
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

public class BlockGrass extends net.minecraft.block.BlockGrass
{
	protected int textureOffset = 0;
	
	Icon GrassTopTexture;
	Icon[] DirtTexture = new Icon[23];
	
	public BlockGrass(int par1)
    {
        super(par1);
        this.setTickRandomly(true);
    }
	
    public BlockGrass(int par1, int texOff)
    {
        this(par1);
        textureOffset = texOff;
    }
    
    @Override
    public int damageDropped(int i) {
        return i;
    }
    
    @Override
    public void registerIcons(IconRegister registerer)
    {
		for(int i = 0; i < 23; i++)
		{
			DirtTexture[i] = registerer.registerIcon("soil/Dirt"+i);
		}
		GrassTopTexture = registerer.registerIcon("GrassTop");
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public Icon getBlockTextureFromSideAndMetadata(int side, int meta)
    {
        return side == 1 ? GrassTopTexture : DirtTexture[meta+textureOffset];
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    @Override
    public Icon getBlockTexture(IBlockAccess access, int xCoord, int yCoord, int zCoord, int side)
    {
    	Block blk = Block.blocksList[TFC_Core.getTypeForDirt(access.getBlockMetadata(xCoord, yCoord, zCoord) + textureOffset)];
    	
        if (side == 1)//top
        {
            return GrassTopTexture;
        }
        else if (side == 0)//bottom
        {
            return DirtTexture[access.getBlockMetadata(xCoord, yCoord, zCoord) + textureOffset];
        }
        else if (side == 2)//-Z
        {
            if(TFC_Settings.enableBetterGrass == true && access.getBlockMaterial(xCoord, yCoord-1, zCoord-1) == Material.grass)
                return GrassTopTexture;
        }
        else if (side == 3)//+Z
        {
            if(TFC_Settings.enableBetterGrass == true && access.getBlockMaterial(xCoord, yCoord-1, zCoord+1) == Material.grass)
                return GrassTopTexture;
        }
        else if (side == 4)//-X
        {
            if(TFC_Settings.enableBetterGrass == true && access.getBlockMaterial(xCoord-1, yCoord-1, zCoord) == Material.grass)
                return GrassTopTexture;
        }
        else if (side == 5)//+X
        {
            if(TFC_Settings.enableBetterGrass == true && access.getBlockMaterial(xCoord+1, yCoord-1, zCoord) == Material.grass)
                return GrassTopTexture;
        }
        
        return DirtTexture[access.getBlockMetadata(xCoord, yCoord, zCoord) + textureOffset];
    }


    /**
     * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
     * when first determining what to render.
     */
    @Override
    public int colorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        return TerraFirmaCraft.proxy.grassColorMultiplier(par1IBlockAccess, par2, par3, par4);
    }

    @Override
    public int getBlockColor()
    {
        double var1 = 0.5D;
        double var3 = 1.0D;
        return ColorizerGrassTFC.getGrassColor(var1, var3);
    }

    /**
     * Returns the color this block should be rendered. Used by leaves.
     */
    @Override
    public int getRenderColor(int par1)
    {
        return this.getBlockColor();
    }

    /**
     * Ticks the block if it's been scheduled
     */
    @Override
    public void updateTick(World world, int i, int j, int k, Random rand)
    {
        if (!world.isRemote)
        {
        	if(world.getBlockId(i, j+1, k)==Block.snow.blockID){
        		world.setBlock(i, j, k, TFC_Core.getTypeForDryGrass(world.getBlockMetadata(i, j, k)), world.getBlockMetadata(i, j, k), 0x2);
        	}
        	else if (world.getBlockLightValue(i, j + 1, k) < 4 && Block.lightOpacity[world.getBlockId(i, j + 1, k)] > 2)
            {
                world.setBlock(i, j, k, TFC_Core.getTypeForDirt(world.getBlockMetadata(i, j, k)), world.getBlockMetadata(i, j, k), 0x2);
            }
            else if (world.getBlockLightValue(i, j + 1, k) >= 9)
            {            	
                for (int var6 = 0; var6 < 4; ++var6)
                {
                    int x = i + rand.nextInt(3) - 1;
                    int y = j + rand.nextInt(5) - 3;
                    int z = k + rand.nextInt(3) - 1;

                    float rain = TFC_Climate.getRainfall(x, y + 1, z);
                    
                    int id = world.getBlockId(x, y, z);
                    int meta = world.getBlockMetadata(x, y, z);

                    if (TFC_Core.isDirt(id) && rand.nextInt(10) == 0 &&
                    		world.getBlockLightValue(x, y + 1, z) >= 4 && world.getBlockMaterial(x, y + 1, z) != Material.water)
                    {
                        world.setBlock(x, y, z, TFC_Core.getTypeForGrassWithRain(meta, rain), meta, 0x2);
                    }
                    else if (TFC_Core.isClay(id) && world.getBlockLightValue(x, y + 1, z) >= 4 && rand.nextInt(10) == 0 && world.getBlockMaterial(x, y + 1, z) != Material.water)
                    {
                        world.setBlock(x, y, z, TFC_Core.getTypeForClayGrass(meta), meta, 0x2);
                    }
                    else if (TFC_Core.isPeat(id) && world.getBlockLightValue(x, y + 1, z) >= 4 && rand.nextInt(10) == 0 && world.getBlockMaterial(x, y + 1, z) != Material.water)
                    {
                        world.setBlock(x, y, z, TFCBlocks.PeatGrass.blockID);
                    }
                }
                
                float rain = TFC_Climate.getRainfall(i, j + 1, k);
                int id = world.getBlockId(i, j, k);
                
                if (TFC_Core.isGrass(id) && !TFC_Core.isDryGrass(id) && world.getBlockLightValue(i, j + 1, k) >= 4 && 
                		world.getBlockMaterial(i, j + 1, k) != Material.water && world.getBlockId(i, j + 1, k) == 0)
                {
                	if(rand.nextInt((int) ((16800-rain)/4)) == 0)
                	{
                		world.setBlock(i, j + 1, k, Block.tallGrass.blockID, 1, 0x2);
                	}
                	else if(rand.nextInt(10000) == 0)
                	{
                		new WorldGenGrowTrees().generate(world, rand, i, j, k);
                	}
                }
                
                boolean nearWater = false;
                
                for(int y = 0; y < 2 && !nearWater; y++)
                {
                	for(int x = -4; x < 5 && !nearWater; x++)
                    {
                		for(int z = -4; z < 5 && !nearWater; z++)
                        {
                        	if(world.getBlockMaterial(i+x, j-y, k+z) == Material.water)
                        	{
                        		nearWater = true;
                        	}
                        }
                    }
                }
                
                int[] rock1 = TFC_Climate.getRockLayer(i, j, k, 0);
                if(TFC_Core.isGrass(id) && !TFC_Core.isDryGrass(id) && !nearWater && rain < 500)
                {
                	int meta = TFC_Core.getSoilMetaFromStone(rock1[0], rock1[1]);
                	world.setBlock(i, j, k, TFC_Core.getTypeForGrass(meta), meta, 0x2);
                }
                else if(TFC_Core.isGrass(id) && TFC_Core.isDryGrass(id) && (nearWater||rain>=500)&&world.getBlockId(i, j+1, k)!=Block.snow.blockID)
                {
                	int meta = TFC_Core.getSoilMetaFromStone(rock1[0], rock1[1]);
                	world.setBlock(i, j, k, TFC_Core.getTypeForGrass(meta), meta, 0x2);
                }
            }
            
//            if(!(this.blockID >= 2080 && this.blockID < 2088))
//            {
//            	boolean hasBeenSet = false;
//            	int meta = world.getBlockMetadata(i, j, k);
//            	for(int x = i-1; x <= i+1 && !hasBeenSet; x++)
//            	{
//            		for(int z = k-1; z <= k+1 && !hasBeenSet; z++)
//                	{
//            			if(!world.isBlockNormalCube(x, j, z))
//            			{
//            				hasBeenSet = true;
//            				world.setBlockAndMetadataWithNotify(i, j, k, TFC_Core.getTypeForRaisedGrass(meta), meta);
//            			}
//                	}
//            	}
//            }
            
            world.markBlockForUpdate(i, j, k);
        }
    }

    @Override
    public void onEntityWalking(World world, int i, int j, int k, Entity par5Entity) 
    {
        if (!world.isRemote)
        {
            Random R = new Random();
            if(!BlockCollapsable.isNearSupport(world, i, j, k) && BlockDirt.canFallBelow(world, i, j - 1, k) && R.nextInt(10) == 0)
            {
                int meta = world.getBlockMetadata(i, j, k);
                world.setBlock(i, j, k, TFC_Core.getTypeForDirt(meta), meta, 0x2);
            }
        }
    }

    /**
     * Returns the ID of the items to drop on destruction.
     */
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Block.blocksList[TFC_Core.getTypeForDirt(par1)].idDropped(par1, par2Random, par3);
    }

    @Override
    public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        if(!world.blockExists(i, j-1, k))
        {
            int meta = world.getBlockMetadata(i, j, k);
            world.setBlock(i, j, k, TFC_Core.getTypeForDirt(meta), meta, 0x2);
        }
    }
}
