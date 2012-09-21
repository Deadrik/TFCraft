package TFC.Items;

import java.util.List;

import TFC.Core.Helper;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.TileEntities.TileEntityTerraLogPile;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.TFCBlocks;
import net.minecraft.src.World;

public class ItemLogs extends ItemTerra
{
	public static String[] blockNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
		"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};


	public ItemLogs(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
	
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 16; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	private boolean CreatePile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y,
			int z, int side, int l) {
		TileEntityTerraLogPile te = null;
		if(side == 0 && world.getBlockId(x, y-1, z) == 0)
		{
			world.setBlockAndMetadataWithNotify( x, y-1, z, TFCBlocks.LogPile.blockID, l);
			if(world.isRemote)
				world.markBlockNeedsUpdate(x, y-1, z);
			te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y-1, z);
		}
		else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
		{
			world.setBlockAndMetadataWithNotify( x, y+1, z, TFCBlocks.LogPile.blockID, l);
			if(world.isRemote)
				world.markBlockNeedsUpdate(x, y+1, z);
			te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y+1, z);
		}
		else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
		{
			world.setBlockAndMetadataWithNotify( x, y, z-1, TFCBlocks.LogPile.blockID, l);
			if(world.isRemote)
				world.markBlockNeedsUpdate(x, y, z-1);
			te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y, z-1);
		}
		else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
		{
			world.setBlockAndMetadataWithNotify( x, y, z+1, TFCBlocks.LogPile.blockID, l);
			if(world.isRemote)
				world.markBlockNeedsUpdate(x, y, z+1);
			te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y, z+1);
		}
		else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
		{
			world.setBlockAndMetadataWithNotify( x-1, y, z, TFCBlocks.LogPile.blockID, l);
			if(world.isRemote)
				world.markBlockNeedsUpdate(x-1, y, z);
			te = (TileEntityTerraLogPile)world.getBlockTileEntity(x-1, y, z);
		}
		else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
		{
			world.setBlockAndMetadataWithNotify( x+1, y, z, TFCBlocks.LogPile.blockID, l);
			if(world.isRemote)
				world.markBlockNeedsUpdate(x+1, y, z);
			te = (TileEntityTerraLogPile)world.getBlockTileEntity(x+1, y, z);
		}
		else
		{
			return false;
		}

		if(te != null)
		{
			te.storage[0] = new ItemStack(this,1,itemstack.getItemDamage());
			if(entityplayer.capabilities.isCreativeMode)
			{
				te.storage[0] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[1] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[2] = new ItemStack(this,4,itemstack.getItemDamage());
				te.storage[3] = new ItemStack(this,4,itemstack.getItemDamage());
			}
		}

		return true;
	}

	public int getIconFromDamage(int par1)
	{
		return this.iconIndex+par1;
	}

	@Override
	public String getItemNameIS(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemName()).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
		return s;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terrasprites2.png";
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int dir = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;

			if(entityplayer.isSneaking() && (world.getBlockId(x, y, z) != TFCBlocks.LogPile.blockID || (side != 1 && side != 0)))
			{
				if(CreatePile(itemstack, entityplayer, world, x, y, z, side, dir))
					itemstack.stackSize = itemstack.stackSize-1;
				return true;
			}
			else if(world.getBlockId(x, y, z) == TFCBlocks.LogPile.blockID)
			{
				TileEntityTerraLogPile te = (TileEntityTerraLogPile)world.getBlockTileEntity(x, y, z);
				if(te != null)
				{
					if(te.storage[0] != null && te.contentsMatch(0,itemstack)) {
						te.injectContents(0,1);
					} else if(te.storage[0] == null) {
						te.addContents(0, new ItemStack(this,1, itemstack.getItemDamage()));
					} else if(te.storage[1] != null && te.contentsMatch(1,itemstack)) {
						te.injectContents(1,1);
					} else if(te.storage[1] == null) {
						te.addContents(1, new ItemStack(this,1, itemstack.getItemDamage()));
					} else if(te.storage[2] != null && te.contentsMatch(2,itemstack)) {
						te.injectContents(2,1);
					} else if(te.storage[2] == null) {
						te.addContents(2, new ItemStack(this,1, itemstack.getItemDamage()));
					} else if(te.storage[3] != null  && te.contentsMatch(3,itemstack)) {
						te.injectContents(3,1);
					} else if(te.storage[3] == null) {
						te.addContents(3, new ItemStack(this,1, itemstack.getItemDamage()));
					} else
					{
						if(CreatePile(itemstack, entityplayer, world, x, y, z, side, dir))						
							itemstack.stackSize = itemstack.stackSize-1;
						
						return true;
						
					}
					itemstack.stackSize = itemstack.stackSize-1;
					return true;
				}

			}
			else
			{
				int m = itemstack.getItemDamage();
				if(side == 1)
				{
					world.setBlockAndMetadata(x, y+1, z, TFCBlocks.WoodVert.blockID, m);
					itemstack.stackSize = itemstack.stackSize-1;
				}
				else if(side == 0 && world.getBlockId(x, y-1, z) == 0)
				{
					world.setBlockAndMetadata(x, y-1, z, TFCBlocks.WoodVert.blockID, m);
					itemstack.stackSize = itemstack.stackSize-1;
				}
				else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, -1);
				}
				else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, 1);
				}
				else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
				{
					setSide(world, itemstack, m, dir, x, y, z, -1, 0, 0);
				}
				else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
				{
					setSide(world, itemstack, m, dir, x, y, z, 1, 0, 0);
				}
				return true;
			}
		}
		return false;
	}
	
	public void setSide(World world, ItemStack itemstack, int m, int dir, int x, int y, int z, int i, int j, int k)
	{
		if(m < 8)
		{
			if(dir == 0 || dir == 2)
				world.setBlockAndMetadata(x+i, y+j, z+k, TFCBlocks.WoodHoriz.blockID, m);
			else
				world.setBlockAndMetadata(x+i, y+j, z+k, TFCBlocks.WoodHoriz.blockID, m | 8);
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if(m >= 8)
		{
			if(dir == 0 || dir == 2)
				world.setBlockAndMetadata(x+i, y+j, z+k, TFCBlocks.WoodHoriz2.blockID, m-8);
			else
				world.setBlockAndMetadata(x+i, y+j, z+k, TFCBlocks.WoodHoriz2.blockID, m-8 | 8);
			itemstack.stackSize = itemstack.stackSize-1;
		}
	}

}
