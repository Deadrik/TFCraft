package TFC.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.IIcon;
import TFC.TFCBlocks;
import TFC.Blocks.BlockTerra;

public class BlockCustomBookshelf extends BlockTerra
{
	public BlockCustomBookshelf()
	{
		super(Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public IIcon getIcon(int par1, int par2)
	{
		return par1 != 1 && par1 != 0 ? super.getIcon(par1, par2) : TFCBlocks.Planks.getBlockTextureFromSide(par1);
	}

	@Override
	public int quantityDropped(Random par1Random)
	{
		return 3;
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Items.book;
	}
}
