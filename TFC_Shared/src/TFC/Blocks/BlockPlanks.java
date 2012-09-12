package TFC.Blocks;

import java.util.List;

import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public class BlockPlanks extends Block
{
	public BlockPlanks(int i, Material material) 
	{
		super(i, Material.wood);
		this.setCreativeTab(CreativeTabs.tabBlock);
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
	protected int damageDropped(int j) 
	{
		return j;
	}

	@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return j+176;
	}

	public float getHardness(int md)
	{
		switch(md)
		{
		case 5:
		{
			return 3.5F;
		}
		case 6:
		{
			return 3.0F;
		}
		case 0:
		{
			return 3.5F;
		}
		case 11:
		{
			return 2.5F;
		}
		case 13:
		{
			return 2.5F;
		}
		default:
		{
			return 2.0F;
		}
		}
	}

	@Override
	public String getTextureFile() 
	{
		return "/bioxx/terrablocks.png";
	}

	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{


		super.harvestBlock(world, entityplayer, i, j, k, l);
	}

}
