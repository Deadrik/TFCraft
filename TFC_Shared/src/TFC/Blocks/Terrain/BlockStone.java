package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import TFC.TFCItems;
import TFC.Core.TFC_Core;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

public class BlockStone extends BlockCollapsable
{
    public BlockStone(int i, Material material, int id) {
        super(i, material, id);
    }
    
	protected String[] names;
    public Icon[] icons;
	

    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < names.length; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }

    /*
     * Mapping from metadata value to damage value
     */
    @Override
    public int damageDropped(int i) {
        return i;
    }

    @Override
	public Icon getIcon(int i, int j) 
	{
		return icons[j];
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
    {
		for(int i = 0; i < names.length; i++)
		{
			icons[i] = iconRegisterer.registerIcon("rocks/"+names[i]+" Raw");
		}
    }


	@Override
	public int idDropped(int i, Random random, int j)
	{
		return TFCItems.LooseRock.itemID;
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex) 
	{
		if(!world.isRemote)
		{
			//super.onBlockDestroyedByExplosion(world, i, j, k, ex);
			Random random = new Random();

			ItemStack is = null;

			is = TFC_Core.RandomGem(random, 0);

			if(is != null)
			{
				EntityItem item = new EntityItem(world, i, j, k, is);
				world.spawnEntityInWorld(item);
			}
		}
	}

    @Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
    {
        DropCarvedStone(world, i, j, k);
    }
}
