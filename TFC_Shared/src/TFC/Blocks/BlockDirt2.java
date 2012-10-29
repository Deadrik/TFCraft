package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import TFC.*;
import TFC.Core.TFC_Sounds;
import TFC.Entities.EntityFallingDirt;

import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class BlockDirt2 extends BlockDirt
{	
    public BlockDirt2(int i, int j, Block Farm)
    {
        super(i, j, Farm);
        this.setCreativeTab(CreativeTabs.tabBlock);
    }

    @Override
    public int damageDropped(int i) {
        return i;
    }
    
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < 7; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }
    
    private void tryToFall(World world, int i, int j, int k)
    {
        if(!world.isRemote)
        {
            int meta = world.getBlockMetadata(i, j, k);
            if (!BlockCollapsable.isNearSupport(world, i, j, k) && BlockCollapsable.canFallBelow(world, i, j - 1, k) && j >= 0)
            {
                byte byte0 = 32;
                if (!world.checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0))
                {
                    world.setBlockWithNotify(i, j, k, 0);
                    for (; BlockCollapsable.canFallBelow(world, i, j - 1, k) && j > 0; j--) { }
                    if (j > 0)
                    {
                        world.setBlockAndMetadataWithNotify(i, j, k, blockID, meta);
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
}
