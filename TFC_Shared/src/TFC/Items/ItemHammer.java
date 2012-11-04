package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Core.Helper;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.EnumToolMaterial;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemHammer extends ItemTerraTool
{
	public ItemHammer(int i, EnumToolMaterial e)
	{
		super(i, 0, e, new Block[] {});
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if(!world.isRemote)
		{
			int id2 = player.worldObj.getBlockId(x, y, z);
			int meta2 = player.worldObj.getBlockMetadata(x, y, z);

			if(id2 == TFCBlocks.StoneIgEx.blockID || id2 == TFCBlocks.StoneIgIn.blockID)
			{
				if(side == 1)
				{
					world.setBlockWithNotify(x, y, z, TFCBlocks.Anvil.blockID);
					TileEntityTerraAnvil te = (TileEntityTerraAnvil) world.getBlockTileEntity(x, y, z);
					if(te != null)
					{
						te.stonePair[0] = id2;
						te.stonePair[1] = meta2;
						te.validate();
					}
					world.markBlockNeedsUpdate(x, y, z);
					return true;
				}
			}
		}
		else
		{
			return false;
		}
		return false;
	}
	
	public boolean onBlockDestroyed(ItemStack stack, EntityPlayer player, World world, int i, int j, int k, int side, EntityLiving entity)
    {
        return false;
    }
}