package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import TFC.*;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.Recipes;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import TFC.TileEntities.TileEntityPartial;
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

public class BlockLogVert extends BlockTerra
{
    public BlockLogVert(int par1)
    {
        super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabDecorations);
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {		
        //we need to make sure teh palyer has the correct tool out
        boolean isAxeorSaw = false;
        boolean isHammer = false;
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
            for(int cnt = 0; cnt < Recipes.Hammers.length && !isAxeorSaw; cnt++)
            {
                if(equip.getItem() == Recipes.Hammers[cnt])
                {
                	isHammer = true;
                }
            }
        }
        if(isAxeorSaw)
        {
        	super.harvestBlock(world, entityplayer, i, j, k, l);
        }
        else if(isHammer)
        {
        	EntityItem item = new EntityItem(world, i+0.5, j+0.5, k+0.5, new ItemStack(Item.stick, 1+world.rand.nextInt(3)));
        	world.spawnEntityInWorld(item);
        }
        else
        {
            world.setBlockAndMetadataWithNotify(i, j, k, blockID, l, 3);
        }
    }

    @Override
    public int damageDropped(int j) {
        return j;
    }	
    
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return TFCItems.Logs.itemID;
    }

    @Override
    public Icon getBlockTextureFromSideAndMetadata(int i, int j) 
    {
    	if (i == 1)
		{
			return BlockLogNatural.innerIcons[j];
		}
		if (i == 0)
		{
			return BlockLogNatural.innerIcons[j];
		}
		return BlockLogNatural.sideIcons[j];
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
