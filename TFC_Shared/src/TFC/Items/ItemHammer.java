package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Core.Helper;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityAnvil;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

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
					world.setBlock(x, y, z, TFCBlocks.Anvil.blockID);
					TileEntityAnvil te = (TileEntityAnvil) world.getBlockTileEntity(x, y, z);
					if(te != null)
					{
						te.stonePair[0] = id2;
						te.stonePair[1] = meta2;
						te.validate();
					}
					world.markBlockForUpdate(x, y, z);
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