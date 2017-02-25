package com.bioxx.tfc.Blocks;

import com.bioxx.tfc.Blocks.Terrain.BlockCollapsible;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class BlockFuel extends BlockTerra {

    public BlockFuel() {
        super(Material.ground);
        this.setCreativeTab(TFCTabs.TFC_MATERIALS);
        this.setTickRandomly(true);

    }

    @Override
    public void registerBlockIcons(IIconRegister registerer)
    {
        this.blockIcon = registerer.registerIcon(Reference.MOD_ID + ":" + "Coke Block");
    }

    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta) {
        boolean isHammer = false;
        ItemStack equip = entityplayer.getCurrentEquippedItem();
        if (equip != null)
        {
            int[] equipIDs = OreDictionary.getOreIDs(equip);
            for (int id : equipIDs)
            {
                String name = OreDictionary.getOreName(id);
                if (name.startsWith("itemHammer"))
                {
                    isHammer = true;
                    break;
                }
            }
            if (isHammer)
            {
                EntityItem item = new EntityItem(world, x + 0.5, y + 0.5, z + 0.5, new ItemStack(TFCItems.coal, 8 + world.rand.nextInt(6), 2));
                world.spawnEntityInWorld(item);
            }
        }
        else
        {
            world.setBlock(x, y, z, this, meta, 0x2);
        }
    }
    @Override
    public void onBlockAdded(World world, int x, int y, int z)
    {
        world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
    }

    @Override
    public int tickRate(World world)
    {
        return 3;
    }

    @Override
    public void updateTick(World world, int i, int j, int k, Random random)
    {
        if (!world.isRemote && world.doChunksNearChunkExist(i, j, k, 1) && !BlockCollapsible.isNearSupport(world, i, j, k, 4, 0))
        {
            int meta = world.getBlockMetadata(i, j, k);

            boolean canFallOneBelow = BlockCollapsible.canFallBelow(world, i, j-1, k);
            byte count = 0;
            List<Integer> sides = new ArrayList<Integer>();

            if(world.isAirBlock(i+1, j, k))
            {
                count++;
                if(BlockCollapsible.canFallBelow(world, i+1, j-1, k))
                    sides.add(0);
            }
            if(world.isAirBlock(i, j, k+1))
            {
                count++;
                if(BlockCollapsible.canFallBelow(world, i, j-1, k+1))
                    sides.add(1);
            }
            if(world.isAirBlock(i-1, j, k))
            {
                count++;
                if(BlockCollapsible.canFallBelow(world, i-1, j-1, k))
                    sides.add(2);
            }
            if(world.isAirBlock(i, j, k-1))
            {
                count++;
                if(BlockCollapsible.canFallBelow(world, i, j-1, k-1))
                    sides.add(3);
            }

            if (!canFallOneBelow && count > 2 && !sides.isEmpty())
            {
                switch (sides.get(random.nextInt(sides.size())))
                {
                    case 0:
                    {
                        world.setBlockToAir(i, j, k);
                        world.setBlock(i+1, j, k, this, meta, 0x2);
                        BlockCollapsible.tryToFall(world, i + 1, j, k, this);
                        break;
                    }
                    case 1:
                    {
                        world.setBlockToAir(i, j, k);
                        world.setBlock(i, j, k+1, this, meta, 0x2);
                        BlockCollapsible.tryToFall(world, i, j, k + 1, this);
                        break;
                    }
                    case 2:
                    {
                        world.setBlockToAir(i, j, k);
                        world.setBlock(i-1, j, k, this, meta, 0x2);
                        BlockCollapsible.tryToFall(world, i - 1, j, k, this);
                        break;
                    }
                    case 3:
                    {
                        world.setBlockToAir(i, j, k);
                        world.setBlock(i, j, k-1, this, meta, 0x2);
                        BlockCollapsible.tryToFall(world, i, j, k - 1, this);
                        break;
                    }
                }
            }
            else if(canFallOneBelow)
            {
                BlockCollapsible.tryToFall(world, i, j, k, this);
            }
        }
    }

    @Override
    public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
    {
        if (!world.isRemote)
        {
            BlockCollapsible.tryToFall(world, x, y, z, this);
            world.scheduleBlockUpdate(x, y, z, this, tickRate(world));
        }
    }
}