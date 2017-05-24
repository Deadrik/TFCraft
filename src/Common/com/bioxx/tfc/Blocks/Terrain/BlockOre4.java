package com.bioxx.tfc.Blocks.Terrain;

import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.TFCItems;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.Random;

public class BlockOre4 extends BlockOre
{
    public BlockOre4(Material material)
    {
        super(material);
        blockNames = Global.ORE_MINERAL2;
    }

    @Override
    public int damageDropped(int dmg)
    {
        if (dmg== 0 || dmg == 1) //coal
            return 0;
        return dmg + Global.ORE_MINERAL.length;
    }

    @Override
    public int quantityDropped(int meta, int fortune, Random random)
    {
        if (meta == 0 || meta == 1) // coal
            return 1 + random.nextInt(2);
        return 1;
    }

    @Override
    public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
    {
        if(!world.isRemote)
        {
            boolean dropOres = false;

            if(player != null)
            {
                player.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
                player.addExhaustion(0.075F);
                dropOres = player.canHarvestBlock(this);
            }
            if (player == null || dropOres)
            {
                int meta = world.getBlockMetadata(x, y, z);
                boolean isCoal = meta == 0 || meta == 1;
                Random random = new Random();

                ItemStack itemstack = null;

                if(isCoal)
                    itemstack = new ItemStack(TFCItems.coal, 1 + world.rand.nextInt(2));
                else
                    itemstack = new ItemStack(TFCItems.oreMineralChunk, 1 , damageDropped(meta));

                if (itemstack != null)
                    dropBlockAsItem(world, x, y, z, itemstack);
            }

        }
        return world.setBlockToAir(x, y, z);
    }

    @Override
    public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
    {
        ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

        int count = quantityDropped(metadata, fortune, world.rand);
        for (int i = 0; i < count; i++)
        {
            ItemStack itemstack;
            if (metadata == 0 || metadata  == 1)
                itemstack = new ItemStack(TFCItems.coal);
            else
                itemstack = new ItemStack(TFCItems.oreMineralChunk, 1, damageDropped(metadata));

            ret.add(itemstack);
        }
        return ret;
    }

    @Override
    public void onBlockExploded(World world, int x, int y, int z, Explosion exp)
    {
        if(!world.isRemote) {
            Random random = new Random();
            ItemStack itemstack;
            int dmg = world.getBlockMetadata(x, y, z);
            int meta = dmg + Global.ORE_METAL.length + Global.ORE_METAL2.length;

            if (dmg == 0 || dmg == 1)
                itemstack = new ItemStack(TFCItems.coal, 1 + random.nextInt(2));
            else
                itemstack = new ItemStack(TFCItems.oreMineralChunk, meta);

            dropBlockAsItem(world, x, y, z, itemstack);
            onBlockDestroyedByExplosion(world, x, y, z, exp);
        }
    }
}
