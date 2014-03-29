package TFC.Blocks.Terrain;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.item.EntityFallingBlock;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class BlockDirt2 extends BlockDirt
{
	public BlockDirt2(int texOff, Block Farm)
	{
		super(texOff, Farm);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		for(int i = 0; i < 5; i++)
			par3List.add(new ItemStack(par1, 1, i));
	}

	private void tryToFall(World world, int i, int j, int k)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			if (!BlockCollapsable.isNearSupport(world, i, j, k, 4, 0) && BlockCollapsable.canFallBelow(world, i, j - 1, k) && j >= 0)
			{
				byte byte0 = 32;
				if (!world.checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0))
				{
					world.setBlockToAir(i, j, k);
					for (; BlockCollapsable.canFallBelow(world, i, j - 1, k) && j > 0; j--) { }
					if (j > 0)
						world.setBlock(i, j, k, this, meta, 0x2);
				}
				else
				{
					EntityFallingBlock ent = new EntityFallingBlock(world, i + 0.5F, j + 0.5F, k + 0.5F, this, meta);
					world.spawnEntityInWorld(ent);
					Random R = new Random(i*j+k);
					world.playSoundAtEntity(ent, "dirt.slide.short", 1.0F, 0.8F + (R.nextFloat()/2));
				}
			}
		}
	}
}
