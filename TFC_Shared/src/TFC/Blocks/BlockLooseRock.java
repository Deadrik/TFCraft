package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Textures;
import TFC.TileEntities.TileEntityCrop;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
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

public class BlockLooseRock extends BlockTerra
{
	public BlockLooseRock(int par1) 
	{
		super(par1, Material.wood);
		//this.setBlockBounds(0.20F, 0.00F, 0.2F, 0.8F, 0.25F, 0.8F);
	}
	@Override
	public int getRenderType()
	{
		return TFCBlocks.looseRockRenderId;
	}
	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int i, int j, int k)
    {
        return true;
    }
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int xCoord, int yCoord, int zCoord, int l)
	{		
	    TFCBiome biome = (TFCBiome) world.getBiomeGenForCoords(xCoord, zCoord);
	    
	    DataLayer rockLayer = ((TFCWorldChunkManager)world.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
	    
	    ArrayList coreSample = new ArrayList<Item>();
	    ArrayList coreSampleStacks = new ArrayList<ItemStack>();
	    
	    for(int x = -15; x <= 15; x++)
        {
            for(int z = -15; z <= 15; z++)
            {
                for(int y = yCoord; y > yCoord-35; y--)
                {
                    if(world.getBlockId(xCoord+x, y, zCoord+z) == TFCBlocks.Ore.blockID)
                    {
                        int m = world.getBlockMetadata(xCoord+x, y, zCoord+z);
                        if(!coreSample.contains(BlockOre.getDroppedItem(m)))
                        {
                            //coreSample.add(BlockTerraOre.getItemNameDamage(((BlockTerraOre)mod_TFC_Core.terraOre).damageDropped(meta)));
                            if(m!= 14 && m != 15)
                            {
                                coreSample.add(BlockOre.getDroppedItem(m));
                                coreSampleStacks.add(new ItemStack(BlockOre.getDroppedItem(m), 1, m));
                            }
                        }
                    }
                }
            }
        }
	    Random R = new Random();
	    
	    if(!coreSampleStacks.isEmpty() && R.nextInt(3) == 0)
	    {
	        dropBlockAsItem_do(world, xCoord, yCoord, zCoord,(ItemStack)coreSampleStacks.toArray()[R.nextInt(coreSampleStacks.toArray().length)]);
	    }
	    else
	        dropBlockAsItem_do(world, xCoord, yCoord, zCoord, new ItemStack(TFCItems.LooseRock, 1, TFC_Core.getItemMetaFromStone(rockLayer.data1, rockLayer.data2)));
	    
		//super.harvestBlock(world, entityplayer, i, j, k, l);
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int par5)
	{
		if (world.getBlockId(i, j-1, k) == 0)
		{
		    this.harvestBlock(world,null,i,j,k,par5);
			world.setBlockWithNotify(i, j, k, 0);
			return;
		}
		if (!Block.blocksList[world.getBlockId(i, j-1, k)].isOpaqueCube())
		{
		    this.harvestBlock(world,null,i,j,k,par5);
			world.setBlockWithNotify(i, j, k, 0);
			return;
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
	
	@Override
    public String getTextureFile()
    {
		return TFC_Textures.RockSheet;
    }
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return null;
    }

    @Override
    public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int i, int j, int k)
    {
        return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.25, k+1);
    }

}
