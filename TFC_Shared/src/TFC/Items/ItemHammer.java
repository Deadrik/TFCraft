package TFC.Items;

import java.util.List;

import TFC.Core.Helper;
import TFC.Core.TFCSettings;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.WorldGen.TFCBiome;
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
		if(TFCSettings.enableDebugMode)
			arraylist.add("Damage: "+is.getItemDamage());
	}

	@Override
	public boolean onBlockStartBreak(ItemStack itemstack, int x, int y, int z, EntityPlayer player) 
	{
		int id = 0;
		int id2 = player.worldObj.getBlockId(x, y, z);
		int meta = 0;
		int meta2 = player.worldObj.getBlockMetadata(x, y, z);
		TFCBiome biome = (TFCBiome) player.worldObj.getBiomeGenForCoords(x, z);
		if(y < biome.Layer3)
		{
			id = biome.Layer3Type; meta = biome.Layer3Meta;
		}
		else if(y < biome.Layer2)
		{
			id = biome.Layer2Type; meta = biome.Layer2Meta;
		}
		else
		{
			id = biome.Layer1Type; meta = biome.Layer1Meta;
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