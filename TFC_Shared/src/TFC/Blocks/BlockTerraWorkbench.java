package TFC.Blocks;

import TFC.TileEntities.TileEntityTerraWorkbench;
import net.minecraft.src.*;

public class BlockTerraWorkbench extends BlockContainer
{
	private Class EntityClass;

	public BlockTerraWorkbench(int i, Class tClass)
	{
		super(i, Material.wood);
		blockIndexInTexture = 59;
		EntityClass = tClass;
	}

	public boolean blockActivated(World world, int i, int j, int k, EntityPlayer entityplayer)
	{
		if (world.isRemote)
		{
			return true;
		}
		else
		{
			if((TileEntityTerraWorkbench)world.getBlockTileEntity(i, j, k)!=null)
			{
				TileEntityTerraWorkbench tileentityanvil;
				tileentityanvil = (TileEntityTerraWorkbench)world.getBlockTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();

				//ModLoader.openGUI(entityplayer, new GuiTerraWorkbench(entityplayer.inventory, tileentityanvil,world, i, j, k));
				entityplayer.openGui(TerraFirmaCraft.instance, 1, world, i, j, k);
			}
			return true;
		}
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
