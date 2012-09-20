package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

import TFC.Core.ColorizerGrassTFC;
import TFC.Core.Recipes;
import TFC.Core.TFCItems;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.TileEntities.TileEntityPartial;

import net.minecraft.src.*;

public class BlockLogVert extends BlockTerra
{
    public BlockLogVert(int par1)
    {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {		
        //we need to make sure teh palyer has the correct tool out
        boolean isAxeorSaw = false;
        ItemStack equip = entityplayer.getCurrentEquippedItem();
        if(equip!=null)
        {
            for(int cnt = 0; cnt < Recipes.Axes.length && !isAxeorSaw; cnt++)
            {
                if(equip.getItem() == Recipes.Axes[cnt])
                {
                    isAxeorSaw = true;
                }
            }
            for(int cnt = 0; cnt < Recipes.Saws.length && !isAxeorSaw; cnt++)
            {
                if(equip.getItem() == Recipes.Saws[cnt])
                {
                    isAxeorSaw = true;
                }
            }
            if(!isAxeorSaw && equip.getItem() == TFCItems.FlintPaxel)
            {
                isAxeorSaw = true;
            }
        }
        if(isAxeorSaw)
        {
        	super.harvestBlock(world, entityplayer, i, j, k, l);
        }
        else
        {
            world.setBlockAndMetadata(i, j, k, blockID, l);
        }
    }

    @Override
    protected int damageDropped(int j) {
        return j;
    }	
    
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return TFCItems.Logs.shiftedIndex;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) 
    {
        if (i == 1)
        {
            return j+144;
        }
        if (i == 0)
        {
            return j+144;
        }
        return j+128;
    }

    @Override
    public String getTextureFile() 
    {
        return "/bioxx/terrablocks.png";
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
