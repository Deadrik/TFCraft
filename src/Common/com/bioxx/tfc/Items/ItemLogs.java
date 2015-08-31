package com.bioxx.tfc.Items;

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

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TELogPile;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemLogs extends ItemTerra
{
	public ItemLogs()
	{
		super();
		setMaxDamage(0);
		setHasSubtypes(true);
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.metaNames = Global.WOOD_ALL.clone();
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

	private boolean createPile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, int l)
	{
		TELogPile te = null;
		if(world.isAirBlock(x, y, z) && isValid(world, x, y, z))
		{
			world.setBlock(x, y, z, TFCBlocks.logPile, l, 3);
			te = (TELogPile)world.getTileEntity(x, y, z);
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
		if(world.isSideSolid(i, j-1, k, ForgeDirection.UP))
		{
			TileEntity te = world.getTileEntity(i, j-1, k);

			if (te instanceof TELogPile)
			{
				TELogPile lp = (TELogPile)te;

				if (lp.storage[0] == null || lp.storage[0].stackSize < 4)
				{
					return false;
				}
				if (lp.storage[1] == null || lp.storage[1].stackSize < 4)
				{
					return false;
				}
				if (lp.storage[2] == null || lp.storage[2].stackSize < 4)
				{
					return false;
				}
				if (lp.storage[3] == null || lp.storage[3].stackSize < 4)
				{
					return false;
				}
			}
			return true;
		}
		return false;
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		if(meta < icons.length)
			return icons[meta];
		return icons[0];
	}

	private IIcon[] icons = new IIcon[Global.WOOD_ALL.length];
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/"+Global.WOOD_ALL[i]+" Log");
		}
	}


	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if (entityplayer.isSneaking() && (world.getBlock(x, y, z) != TFCBlocks.logPile || side != 1 && side != 0))
			{
				int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
				if (side == 0)
					--y;
				else if (side == 1)
					++y;
				else if (side == 2)
					--z;
				else if (side == 3)
					++z;
				else if (side == 4)
					--x;
				else if (side == 5)
					++x;
				if(world.canPlaceEntityOnSide(TFCBlocks.logPile, x, y, z, false, side, entityplayer, itemstack))
					if (createPile(itemstack, entityplayer, world, x, y, z, side, dir)) 
					{
						itemstack.stackSize = itemstack.stackSize-1;
						playSound(world, x, y, z);
					}
				return true;
			}
			else if(world.getBlock(x, y, z) == TFCBlocks.logPile)
			{
				TELogPile te = (TELogPile)world.getTileEntity(x, y, z);
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
					} else if(te.storage[3] != null && te.contentsMatch(3,itemstack)) {
						te.injectContents(3,1);
					} else if(te.storage[3] == null) {
						te.addContents(3, new ItemStack(this,1, itemstack.getItemDamage()));
					} else
					{
						int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;
						if (side == 0)
							--y;
						else if (side == 1)
							++y;
						else if (side == 2)
							--z;
						else if (side == 3)
							++z;
						else if (side == 4)
							--x;
						else if (side == 5)
							++x;
						if (!createPile(itemstack, entityplayer, world, x, y, z, side, dir)) {
							return true;
						}

					}
					playSound(world, x, y, z);
					itemstack.stackSize = itemstack.stackSize-1;
					return true;
				}

			}
			else
			{
				int m = itemstack.getItemDamage();
				Block block = m>15?TFCBlocks.woodVert2:TFCBlocks.woodVert;

				if(side == 0 && block.canPlaceBlockAt(world, x, y-1, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y-1, z, false, side, null, itemstack))
				{
					world.setBlock(x, y-1, z, block, m,0x2);
					itemstack.stackSize = itemstack.stackSize-1;
					playSound(world, x, y, z);
				}
				else if(side == 1 && block.canPlaceBlockAt(world, x, y+1, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y+1, z, false, side, null, itemstack))
				{
					world.setBlock(x, y+1, z, block, m,0x2);
					itemstack.stackSize = itemstack.stackSize-1;
					playSound(world, x, y, z);
				}
				else if(side == 2 && block.canPlaceBlockAt(world, x, y, z-1) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y, z-1, false, side, null, itemstack))
				{
					setSide(world, itemstack, m, side, x, y, z-1);
				}
				else if(side == 3 && block.canPlaceBlockAt(world, x, y, z+1) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x, y, z+1, false, side, null, itemstack))
				{
					setSide(world, itemstack, m, side, x, y, z+1);
				}
				else if(side == 4 && block.canPlaceBlockAt(world, x-1, y, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x-1, y, z, false, side, null, itemstack))
				{
					setSide(world, itemstack, m, side, x-1, y, z);
				}
				else if(side == 5 && block.canPlaceBlockAt(world, x+1, y, z) && world.canPlaceEntityOnSide(TFCBlocks.woodVert, x+1, y, z, false, side, null, itemstack))
				{
					setSide(world, itemstack, m, side, x+1, y, z);
				}
				return true;
			}
		}
		return false;
	}

	public void setSide(World world, ItemStack itemstack, int m, int side, int x, int y, int z)
	{
		// don't call this function with side==0 or side==1, it won't do anything

		int meta = m % 8;
		Block log = TFCBlocks.woodHoriz;
		switch (m/8) {
		case 1:
			log = TFCBlocks.woodHoriz2;
			break;
		case 2:
			log = TFCBlocks.woodHoriz3;
			break;
		case 3:
			//log = TFCBlocks.WoodHoriz4;
			break;
		}

		if (side == 2 || side == 3) {
			world.setBlock(x, y, z, log, meta, 0x2);
			itemstack.stackSize = itemstack.stackSize-1;
			playSound(world, x, y, z);
		}
		else if (side == 4 || side == 5) {
			world.setBlock(x, y, z, log, meta | 8, 0x2);
			itemstack.stackSize = itemstack.stackSize-1;
			playSound(world, x, y, z);
		}
	}

	private void playSound(World world, int x, int y, int z)
	{
		world.playSoundEffect(x + 0.5, y + 0.5, z + 0.5, TFCBlocks.logNatural.stepSound.func_150496_b(), (TFCBlocks.logNatural.stepSound.getVolume() + 1.0F) / 2.0F, TFCBlocks.logNatural.stepSound.getPitch() * 0.8F);
	}
}
