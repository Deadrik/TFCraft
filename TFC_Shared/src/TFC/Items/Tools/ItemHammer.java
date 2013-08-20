package TFC.Items.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.TileEntities.TileEntityAnvil;

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
	
	@Override
	public EnumWeight getWeight()
	{
		return EnumWeight.MEDIUM;
	}
	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.SMALL;
	}
}