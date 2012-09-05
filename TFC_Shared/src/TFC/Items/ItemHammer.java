package TFC.Items;

import java.util.List;

import TFC.Core.Helper;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import net.minecraft.src.*;

public class ItemHammer extends ItemTool
{
	public ItemHammer(int i, EnumToolMaterial e)
	{
		super(i, 0, e, new Block[] {});
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}

	public void addInformation(ItemStack is, List arraylist) 
	{
		if(TFC_Settings.enableDebugMode)
			arraylist.add("Damage: "+is.getItemDamage());
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if(!world.isRemote)
		{
			int id2 = player.worldObj.getBlockId(x, y, z);
			int meta2 = player.worldObj.getBlockMetadata(x, y, z);

			if(TFC_Core.isRawStone(player.worldObj, x, y, z))
			{
				if(side == 1)
				{
					world.setBlockWithNotify(x, y, z, TFCBlocks.terraAnvil.blockID);
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
}