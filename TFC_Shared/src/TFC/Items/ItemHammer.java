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
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) 
	{
		int id = 0;
		int id2 = player.worldObj.getBlockId(x, y, z);
		int meta = 0;
		int meta2 = player.worldObj.getBlockMetadata(x, y, z);
		DataLayer rockLayer1 = ((TFCWorldChunkManager)player.worldObj.getWorldChunkManager()).getRockLayerAt(x, z, 0);
        DataLayer rockLayer2 = ((TFCWorldChunkManager)player.worldObj.getWorldChunkManager()).getRockLayerAt(x, z, 1);
        DataLayer rockLayer3 = ((TFCWorldChunkManager)player.worldObj.getWorldChunkManager()).getRockLayerAt(x, z, 2);

		if(y < TerraFirmaCraft.RockLayer3Height)
		{
			id = rockLayer3.data1; meta = rockLayer3.data2;
		}
		else if(y < TerraFirmaCraft.RockLayer2Height)
		{
			id = rockLayer2.data1; meta = rockLayer2.data2;
		}
		else
		{
			id = rockLayer1.data1; meta = rockLayer1.data2;
		}

		if(TFC_Core.isRawStone(player.worldObj, x, y, z) && 
				id2 == id && 
				meta2 == meta)
		{
			MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(player, player.worldObj);
			if(objectMouseOver == null) {
				return false;
			}       
			int side = objectMouseOver.sideHit;
			if(side == 1)
			{
				player.worldObj.setBlock(x, y, z, TFCBlocks.terraAnvil.blockID);
				TFCBlocks.terraAnvil.onBlockActivated(player.worldObj, x, y, z, player,1,0,0,0);
				return false;
			}
		}

		return true;
	}
}