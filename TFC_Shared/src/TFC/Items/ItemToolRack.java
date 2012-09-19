package TFC.Items;

import java.util.List;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.World;
import net.minecraftforge.common.ForgeDirection;
import TFC.Core.AnvilReq;
import TFC.Enums.EnumSize;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.TileEntities.TileEntityToolRack;
import cpw.mods.fml.common.Side;
import cpw.mods.fml.common.asm.SideOnly;

public class ItemToolRack extends ItemTerraBlock
{
	public ItemToolRack(int id) {
		super(id);
		this.setIconIndex(16);
		this.hasSubtypes = true;
        this.setMaxDamage(0);
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.HUGE;
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}
	@Override
	public int getIconFromDamage(int i)
    {
		return 16+i;
    }

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites.png";
	}

	public boolean tryPlaceIntoWorld(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int dir = MathHelper.floor_double((double)(player.rotationYaw * 4F / 360F) + 0.5D) & 3;
		if(!world.isRemote && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z))
		{
			int id = TFCBlocks.ToolRack.blockID;
			if(side == 2 && world.getBlockId(x, y, z-1) == 0)
			{
				world.setBlockAndMetadataWithNotify(x, y, z-1, id, 0);
				world.markBlockNeedsUpdate(x, y, z-1);
				if(world.getBlockTileEntity(x, y, z-1) != null)
				{
					TileEntityToolRack te = (TileEntityToolRack)world.getBlockTileEntity(x, y, z-1);
					te.woodType = (byte) itemstack.getItemDamage();
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
			else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
			{
				world.setBlockAndMetadataWithNotify(x, y, z+1, id, 2);
				world.markBlockNeedsUpdate(x, y, z+1);
				if(world.getBlockTileEntity(x, y, z+1) != null)
				{
					TileEntityToolRack te = (TileEntityToolRack)world.getBlockTileEntity(x, y, z+1);
					te.woodType = (byte) itemstack.getItemDamage();
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
			else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
			{
				world.setBlockAndMetadataWithNotify(x-1, y, z, id, 3);
				world.markBlockNeedsUpdate(x-1, y, z);
				if(world.getBlockTileEntity(x-1, y, z) != null)
				{
					TileEntityToolRack te = (TileEntityToolRack)world.getBlockTileEntity(x-1, y, z);
					te.woodType = (byte) itemstack.getItemDamage();
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
			else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
			{
				world.setBlockAndMetadataWithNotify(x+1, y, z, id, 1);
				world.markBlockNeedsUpdate(x+1, y, z);
				if(world.getBlockTileEntity(x+1, y, z) != null)
				{
					TileEntityToolRack te = (TileEntityToolRack)world.getBlockTileEntity(x+1, y, z);
					te.woodType = (byte) itemstack.getItemDamage();
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
				player.inventory.mainInventory[player.inventory.currentItem] = null;
			}
			return true;
		}

		return false;
	}
	
	

}
