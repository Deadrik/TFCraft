package TFC.Blocks.Flora;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.BlockTerra;
import TFC.Core.Recipes;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogVert extends BlockTerra
{
    public BlockLogVert(int par1)
    {
        super(par1, Material.wood);
        //this.setCreativeTab(CreativeTabs.tabDecorations);
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
            world.setBlock(i, j, k, blockID, l, 0x2);
        }
    }

    @Override
    public int damageDropped(int j) {
    	if(blockID == TFCBlocks.WoodVert2.blockID){
    		j+=16;
    	}
        return j;
    }	
    
    @Override
    public int idDropped(int i, Random random, int j)
    {
        return TFCItems.Logs.itemID;
    }

    @Override
    public Icon getIcon(int i, int j) 
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
    
    @Override
	public void registerIcons(IconRegister iconRegisterer)
    {
    	
    }
}
