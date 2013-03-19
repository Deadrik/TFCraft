package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import TFC.*;
import TFC.Core.TFC_Sounds;
import TFC.Entities.EntityFallingDirt;
import TFC.Entities.EntityFallingStone;
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

public class BlockSand extends BlockTerra2
{
    public BlockSand(int i)
    {
        super(i, Material.sand);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < 15; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }
    
    public static boolean canFallBelow(World world, int i, int j, int k)
    {
        int l = world.getBlockId(i, j, k);
        if (l == 0)
        {
            return true;
        }
        if (l == Block.fire.blockID)
        {
            return true;
        }
        Material material = Block.blocksList[l].blockMaterial;
        if (material == Material.water)
        {
            return true;
        }
        return material == Material.lava;
    }

    /**
     * Retrieves the block texture to use based on the display side. Args: iBlockAccess, x, y, z, side
     */
    @Override
    public Icon getBlockTexture(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
    {
        return icons[par1IBlockAccess.getBlockMetadata(par2, par3, par4)];
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return icons[par2];
    }
    
    Icon[] icons = new Icon[16];

	@Override
    public void registerIcon(IconRegister registerer)
    {
		for(int i = 0; i < 16; i++)
		{
			icons[i] = registerer.func_94245_a("sand/Sand"+i);
		}
    }

    @Override
    public void onBlockAdded(World world, int i, int j, int k)
    {
        world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
    }

    private void tryToFall(World world, int i, int j, int k)
    {
        if(!world.isRemote)
        {
            int meta = world.getBlockMetadata(i, j, k);
            if (canFallBelow(world, i, j - 1, k) && j >= 0)
            {
                byte byte0 = 32;
                if (!world.checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0))
                {
                    world.setBlock(i, j, k, 0);
                    for (; canFallBelow(world, i, j - 1, k) && j > 0; j--) { }
                    if (j > 0)
                    {
                        world.setBlockAndMetadataWithNotify(i, j, k, blockID, meta,3);
                    }
                }
                else
                {
                    EntityFallingDirt ent = new EntityFallingDirt(world, (float)i + 0.5F, (float)j + 0.5F, (float)k + 0.5F, blockID, meta, 0);
                    world.spawnEntityInWorld(ent);
                    Random R = new Random(i*j+k);
                    world.playSoundAtEntity(ent, TFC_Sounds.FALLININGDIRTSHORT, 1.0F, 0.8F + (R.nextFloat()/2));
                }
            }
        }
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			boolean PosXAir = false;
			boolean NegXAir = false;
			boolean PosZAir = false;
			boolean NegZAir = false;
			boolean PosXAir2 = false;
			boolean NegXAir2 = false;
			boolean PosZAir2 = false;
			boolean NegZAir2 = false;
			byte count = 0;
			List sides = new ArrayList<Integer>();

			if(world.getBlockId(i+1, j, k) == 0)
			{
				count++;
				if(world.getBlockId(i+1, j-1, k) == 0)
					//PosXAir = true;
					sides.add(0);
			}
			if(world.getBlockId(i, j, k+1) == 0)
			{
				count++;
				if(world.getBlockId(i, j-1, k+1) == 0)
					//PosZAir = true;
					sides.add(1);
			}
			if(world.getBlockId(i-1, j, k) == 0)
			{
				count++;
				if(world.getBlockId(i-1, j-1, k) == 0)
					//NegXAir = true;
					sides.add(2);
			}
			if(world.getBlockId(i, j, k-1) == 0)
			{
				count++;
				if(world.getBlockId(i, j-1, k-1) == 0)
					//NegZAir = true; 
					sides.add(3);
			}

			if((random.nextInt(5) == 0 || count > 2) && sides.size() >= 1)
			{
				switch((Integer)sides.get(random.nextInt(sides.size())))
				{
				case 0:
				{
					world.setBlockAndMetadataWithNotify(i+1, j, k, blockID, meta, 3);
					tryToFall(world, i+1, j, k);
					world.setBlock(i, j, k, 0);
					break;
				}
				case 1:
				{
					world.setBlockAndMetadataWithNotify(i, j, k+1, blockID,meta, 3);
					tryToFall(world, i, j, k+1);
					world.setBlock(i, j, k, 0);
					break;
				}
				case 2:
				{
					world.setBlockAndMetadataWithNotify(i-1, j, k, blockID,meta, 3);
					tryToFall(world, i-1, j, k);
					world.setBlock(i, j, k, 0);
					break;
				}
				case 3:
				{
					world.setBlockAndMetadataWithNotify(i, j, k-1, blockID,meta, 3);
					tryToFall(world, i, j, k-1);
					world.setBlock(i, j, k, 0);
					break;
				}
				}

			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int id)
	{
		if(!world.isRemote)
		{
			if(world.getBlockId(i, j-1, k) == 0)
			{
				int meta = world.getBlockMetadata(i, j, k);
				world.setBlockAndMetadataWithNotify(i, j-1, k, blockID, meta, 3);
				world.setBlock(i, j, k, 0);

			}
			world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
		}
	}
    

}
