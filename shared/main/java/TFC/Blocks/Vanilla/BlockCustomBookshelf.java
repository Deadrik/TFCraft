package TFC.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.util.Icon;
import TFC.TFCBlocks;
import TFC.Blocks.BlockTerra;

public class BlockCustomBookshelf extends BlockTerra {

	public BlockCustomBookshelf(int par1) 
	{
		super(par1, Material.wood);
        this.setCreativeTab(CreativeTabs.tabBlock);
	}
	
	@Override
	public Icon getIcon(int par1, int par2)
    {
        return par1 != 1 && par1 != 0 ? super.getIcon(par1, par2) : TFCBlocks.Planks.getBlockTextureFromSide(par1);
    }
	
	@Override
    public int quantityDropped(Random par1Random)
    {
        return 3;
    }

	@Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return Item.book.itemID;
    }
}
