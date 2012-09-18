package TFC.Blocks;

import TFC.Enums.EnumSize;
import TFC.Items.ISize;
import TFC.TileEntities.TileEntityTerraWorkbench;
import net.minecraft.src.*;

public class BlockWorkbench extends BlockContainer
{
	public BlockWorkbench(int i)
	{
		super(i, Material.wood);
		blockIndexInTexture = 59;
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if (!world.isRemote)
		{
			entityplayer.openGui(TerraFirmaCraft.instance, 1, world, i, j, k);
		}
		return true;
	}

	public int getBlockTextureFromSide(int i)
	{
		if (i == 1)
		{
			return blockIndexInTexture - 16;
		}
		if (i == 0)
		{
			return Block.planks.getBlockTextureFromSide(0);
		}
		if (i == 2 || i == 4)
		{
			return blockIndexInTexture + 1;
		}
		else
		{
			return blockIndexInTexture;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityTerraWorkbench();
	}
}
