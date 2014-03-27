package TFC.Items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.TileEntities.TileEntityLogPile;

public class ItemLogs extends ItemTerra
{
	public ItemLogs() 
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(TFCTabs.TFCMaterials);
		this.MetaNames = Global.WOOD_ALL.clone();
		this.setWeight(EnumWeight.MEDIUM);
		this.setSize(EnumSize.MEDIUM);
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	private boolean CreatePile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y,
			int z, int side, int l) {
		TileEntityLogPile te = null;
		if(side == 0 && world.isAirBlock(x, y-1, z) && isValid(world, x, y-1, z))
		{
			world.setBlock( x, y-1, z, TFCBlocks.LogPile, l, 3);
			te = (TileEntityLogPile)world.getTileEntity(x, y-1, z);
		}
		else if(side == 1 && world.isAirBlock(x, y+1, z) && isValid(world, x, y+1, z))
		{
			world.setBlock( x, y+1, z, TFCBlocks.LogPile, l, 3);
			te = (TileEntityLogPile)world.getTileEntity(x, y+1, z);
		}
		else if(side == 2 && world.isAirBlock(x, y, z-1) && isValid(world, x, y, z-1))
		{
			world.setBlock( x, y, z-1, TFCBlocks.LogPile, l, 3);
			te = (TileEntityLogPile)world.getTileEntity(x, y, z-1);
		}
		else if(side == 3 && world.isAirBlock(x, y, z+1) && isValid(world, x, y, z+1))
		{
			world.setBlock( x, y, z+1, TFCBlocks.LogPile, l, 3);
			te = (TileEntityLogPile)world.getTileEntity(x, y, z+1);
		}
		else if(side == 4 && world.isAirBlock(x-1, y, z) && isValid(world, x-1, y, z))
		{
			world.setBlock( x-1, y, z, TFCBlocks.LogPile, l, 3);
			te = (TileEntityLogPile)world.getTileEntity(x-1, y, z);
		}
		else if(side == 5 && world.isAirBlock(x+1, y, z) && isValid(world, x+1, y, z))
		{
			world.setBlock( x+1, y, z, TFCBlocks.LogPile, l, 3);
			te = (TileEntityLogPile)world.getTileEntity(x+1, y, z);
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

	public boolean isValid(World world, int i, int j, int k)
	{
		if(world.getBlock(i, j-1, k)==TFCBlocks.Firepit ||world.getBlock(i, j-1, k) == TFCBlocks.Pottery){
			return true;
		}
		else if(world.isSideSolid(i, j-1, k, ForgeDirection.UP))
		{
			TileEntity te = world.getTileEntity(i, j-1, k);

			if (te instanceof TileEntityLogPile)
			{
				TileEntityLogPile lp = (TileEntityLogPile)te;

				if(lp != null)
				{
					if(lp.storage[0] == null || lp.storage[0].stackSize < 4) {
						return false;
					}
					if(lp.storage[1] == null || lp.storage[1].stackSize < 4) {
						return false;
					}
					if(lp.storage[2] == null || lp.storage[2].stackSize < 4) {
						return false;
					}
					if(lp.storage[3] == null || lp.storage[3].stackSize < 4) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{        
		return icons[meta];
	}

	IIcon[] icons = new IIcon[Global.WOOD_ALL.length];
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/"+Global.WOOD_ALL[i]+" Log");
		}
	}


	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;

			if(entityplayer.isSneaking() && (world.getBlock(x, y, z) != TFCBlocks.LogPile || (side != 1 && side != 0)))
			{
				if(CreatePile(itemstack, entityplayer, world, x, y, z, side, dir)) {
					itemstack.stackSize = itemstack.stackSize-1;
				}
				return true;
			}
			else if(world.getBlock(x, y, z) == TFCBlocks.LogPile)
			{
				TileEntityLogPile te = (TileEntityLogPile)world.getTileEntity(x, y, z);
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
						if(CreatePile(itemstack, entityplayer, world, x, y, z, side, dir)) {
							itemstack.stackSize = itemstack.stackSize-1;
						}

						return true;

					}
					itemstack.stackSize = itemstack.stackSize-1;
					return true;
				}

			}
			else
			{
				int m = itemstack.getItemDamage();
				Block block = m>15?TFCBlocks.WoodVert2:TFCBlocks.WoodVert;
				if(side == 1)
				{
					world.setBlock(x, y+1, z, block, m,0x2);
					itemstack.stackSize = itemstack.stackSize-1;
				}
				else if(side == 0 && world.isAirBlock(x, y-1, z))
				{
					world.setBlock(x, y-1, z, block, m,0x2);
					itemstack.stackSize = itemstack.stackSize-1;
				}
				else if(side == 2 && world.isAirBlock(x, y, z-1))
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, -1);
				}
				else if(side == 3 && world.isAirBlock(x, y, z+1))
				{
					setSide(world, itemstack, m, dir, x, y, z, 0, 0, 1);
				}
				else if(side == 4 && world.isAirBlock(x-1, y, z))
				{
					setSide(world, itemstack, m, dir, x, y, z, -1, 0, 0);
				}
				else if(side == 5 && world.isAirBlock(x+1, y, z))
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
			if(dir == 0 || dir == 2) {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.WoodHoriz, m, 0x2);
			} else {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.WoodHoriz, m | 8, 0x2);
			}
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if(m >= 8 && m<16)
		{
			if(dir == 0 || dir == 2) {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.WoodHoriz2, m-8, 0x2);
			} else {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.WoodHoriz2, m-8 | 8, 0x2);
			}
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if(m < 24)
		{
			m-=16;
			if(dir == 0 || dir == 2) {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.WoodHoriz3, m, 0x2);
			} else {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.WoodHoriz3, m | 8, 0x2);
			}
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if(m >= 24 && m<32)
		{
			m-=16;
			if(dir == 0 || dir == 2) {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.WoodHoriz4, m-8, 0x2);
			} else {
				world.setBlock(x+i, y+j, z+k, TFCBlocks.WoodHoriz4, m-8 | 8, 0x2);
			}
			itemstack.stackSize = itemstack.stackSize-1;
		}
	}

}
