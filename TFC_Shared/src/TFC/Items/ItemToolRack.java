package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.TileEntities.TileEntityToolRack;

public class ItemToolRack extends ItemTerra
{	
	public ItemToolRack(int id) {
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.MetaNames = new String[]{"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
				"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
		this.setFolder("devices/toolrack/");
	}

	@Override
	public void updateIcons(IconRegister registerer)
	{
		for(int i = 0; i < 16; i++)
			this.iconIndex = registerer.registerIcon(textureFolder+MetaNames[i]);
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.HUGE;
	}

	@Override
	public EnumWeight getWeight() {
		return EnumWeight.LIGHT;
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int dir = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
		if(!world.isRemote && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z))
		{
			int id = TFCBlocks.ToolRack.blockID;
			if(side == 2 && world.getBlockId(x, y, z-1) == 0)
			{
				world.setBlock(x, y, z-1, id);
				world.markBlockForUpdate(x, y, z-1);
				if(world.getBlockTileEntity(x, y, z-1) != null)
				{
					TileEntityToolRack te = (TileEntityToolRack)world.getBlockTileEntity(x, y, z-1);
					te.woodType = (byte) itemstack.getItemDamage();
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
				player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
			}
			else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
			{
				world.setBlock(x, y, z+1, id, 2, 0x2);

				if(world.getBlockTileEntity(x, y, z+1) != null)
				{
					TileEntityToolRack te = (TileEntityToolRack)world.getBlockTileEntity(x, y, z+1);
					te.woodType = (byte) itemstack.getItemDamage();
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
				player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
			}
			else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
			{
				world.setBlock(x-1, y, z, id, 3, 0x2);

				if(world.getBlockTileEntity(x-1, y, z) != null)
				{
					TileEntityToolRack te = (TileEntityToolRack)world.getBlockTileEntity(x-1, y, z);
					te.woodType = (byte) itemstack.getItemDamage();
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
				player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
			}
			else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
			{
				world.setBlock(x+1, y, z, id, 1, 0x2);

				if(world.getBlockTileEntity(x+1, y, z) != null)
				{
					TileEntityToolRack te = (TileEntityToolRack)world.getBlockTileEntity(x+1, y, z);
					te.woodType = (byte) itemstack.getItemDamage();
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
				player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
			}
			return true;
		}

		return false;
	}



}
