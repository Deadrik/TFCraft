package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TEWoodConstruct;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemPlank extends ItemTerra
{
	public ItemPlank() 
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.metaNames = Global.WOOD_ALL.clone();
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.MEDIUM);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		boolean isConstruct = world.getBlock(i, j, k) == TFCBlocks.woodConstruct;
		int offset = !isConstruct ? 1 : 0;
		boolean isAir = world.isAirBlock(i, j, k);

		if(!world.isRemote)
		{
			int d = TEWoodConstruct.plankDetailLevel;
			int dd = d*d;
			int dd2 = dd*2;

			float div = 1f / d;

			int x = (int) (hitX / div);
			int y = (int) (hitY / div);
			int z = (int) (hitZ / div);

			hitX = Math.round(hitX*100)/100.0f;
			hitY = Math.round(hitY*100)/100.0f;
			hitZ = Math.round(hitZ*100)/100.0f;

			boolean isEdge = false;

			if(hitX == 0 || hitX == 1 || hitY == 0 || hitY == 1 || hitZ == 0 || hitZ == 1)
			{
				isEdge = true;
				isConstruct = true;
				offset = 1;
			}

			if(side == 0)
			{
				if (!isConstruct && isAir ||
					isConstruct && isEdge && world.isAirBlock(i, j - offset, k))
					world.setBlock(i, j-1, k, TFCBlocks.woodConstruct);

				TileEntity tile = world.getTileEntity(i, j-offset, k);
				if (!(tile instanceof TEWoodConstruct))
					return false;
				int index = dd+(x+(z*d));
				TEWoodConstruct te = (TEWoodConstruct)tile;
				te.data.set(index);
				if (index < te.woodTypes.length)
					te.woodTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 1)
			{
				if (!isConstruct && isAir ||
					isConstruct && isEdge && world.isAirBlock(i, j + offset, k))
				{
					world.setBlock(i, j+1, k, TFCBlocks.woodConstruct);
				}

				TileEntity tile = world.getTileEntity(i, j+offset, k);
				if (!(tile instanceof TEWoodConstruct))
				{
					return false;
				}
				int index = dd+(x+(z*d));
				TEWoodConstruct te = (TEWoodConstruct)tile;
				te.data.set(index);
				te.woodTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 2)
			{
				if (!isConstruct && isAir ||
					isConstruct && isEdge && world.isAirBlock(i, j, k - offset))
				{
					world.setBlock(i, j, k-1, TFCBlocks.woodConstruct);
				}

				TileEntity tile = world.getTileEntity(i, j, k-offset);
				if (!(tile instanceof TEWoodConstruct))
				{
					return false;
				}
				int index = dd2+(x+(y*d));
				TEWoodConstruct te = (TEWoodConstruct)tile;
				te.data.set(index);
				te.woodTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 3)
			{
				if (!isConstruct && isAir ||
					isConstruct && isEdge && world.isAirBlock(i, j, k + offset))
				{
					world.setBlock(i, j, k+1, TFCBlocks.woodConstruct);
				}

				TileEntity tile = world.getTileEntity(i, j, k+offset);
				if (!(tile instanceof TEWoodConstruct))
				{
					return false;
				}
				int index = dd2+(x+(y*d));
				TEWoodConstruct te = (TEWoodConstruct)tile;
				te.data.set(index);
				te.woodTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 4)
			{
				if (!isConstruct && isAir ||
					isConstruct && isEdge && world.isAirBlock(i - offset, j, k))
				{
					world.setBlock(i-1, j, k, TFCBlocks.woodConstruct);
				}

				TileEntity tile = world.getTileEntity(i-offset, j, k);
				if (!(tile instanceof TEWoodConstruct))
				{
					return false;
				}
				int index = (y+(z*d));
				TEWoodConstruct te = (TEWoodConstruct)tile;
				te.data.set(index);
				te.woodTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 5)
			{
				if (!isConstruct && isAir ||
					isConstruct && isEdge && world.isAirBlock(i + offset, j, k))
				{
					world.setBlock(i+1, j, k, TFCBlocks.woodConstruct);
				}

				TileEntity tile = world.getTileEntity(i+offset, j, k);
				if (!(tile instanceof TEWoodConstruct))
				{
					return false;
				}
				int index = (y+(z*d));
				TEWoodConstruct te = (TEWoodConstruct)tile;
				te.data.set(index);
				te.woodTypes[index] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", index);
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			is.stackSize--;
			return true;
		}
		return false;
	}

	@Override
	public int getMetadata(int i) 
	{
		return i;
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	private IIcon[] icons = new IIcon[Global.WOOD_ALL.length];
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/"+Global.WOOD_ALL[i]+" Plank");
		}
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
