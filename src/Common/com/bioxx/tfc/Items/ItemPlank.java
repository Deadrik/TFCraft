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
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TileEntityWoodConstruct;
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
		setCreativeTab(TFCTabs.TFCMaterials);
		this.MetaNames = Global.WOOD_ALL.clone();
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.MEDIUM);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		boolean isConstruct = world.getBlock(i, j, k) == TFCBlocks.WoodConstruct;
		int offset = !isConstruct ? 1 : 0;
		boolean isAir = world.isAirBlock(i, j, k);

		if(!world.isRemote)
		{
			int d = TileEntityWoodConstruct.PlankDetailLevel;
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
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.isAirBlock(i, j-offset, k)))
					world.setBlock(i, j-1, k, TFCBlocks.WoodConstruct);

				TileEntity tile = world.getTileEntity(i, j-offset, k);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct)))
					return false;

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set(dd+(x+(z*d)));
				te.woodTypes[dd+(x+(z*d))] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", dd+(x+(z*d)));
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 1)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.isAirBlock(i, j+offset, k))) {
					world.setBlock(i, j+1, k, TFCBlocks.WoodConstruct);
				}

				TileEntity tile = world.getTileEntity(i, j+offset, k);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set(dd+(x+(z*d)));
				te.woodTypes[dd+(x+(z*d))] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", dd+(x+(z*d)));
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 2)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.isAirBlock(i, j, k-offset))) {
					world.setBlock(i, j, k-1, TFCBlocks.WoodConstruct);
				}

				TileEntity tile = world.getTileEntity(i, j, k-offset);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set(dd2+(x+(y*d)));
				te.woodTypes[dd2+(x+(y*d))] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", dd2+(x+(z*d)));
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 3)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.isAirBlock(i, j, k+offset))) {
					world.setBlock(i, j, k+1, TFCBlocks.WoodConstruct);
				}

				TileEntity tile = world.getTileEntity(i, j, k+offset);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set(dd2+(x+(y*d)));
				te.woodTypes[dd2+(x+(y*d))] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", dd2+(x+(z*d)));
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 4)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.isAirBlock(i-offset, j, k))) {
					world.setBlock(i-1, j, k, TFCBlocks.WoodConstruct);
				}

				TileEntity tile = world.getTileEntity(i-offset, j, k);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set((y+(z*d)));
				te.woodTypes[(y+(z*d))] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", (y+(z*d)));
				nbt.setByte("meta", (byte) is.getItemDamage());
				te.broadcastPacketInRange(te.createDataPacket(nbt));
			}
			else if(side == 5)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.isAirBlock(i+offset, j, k))) {
					world.setBlock(i+1, j, k, TFCBlocks.WoodConstruct);
				}

				TileEntity tile = world.getTileEntity(i+offset, j, k);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set((y+(z*d)));
				te.woodTypes[(y+(z*d))] = (byte) is.getItemDamage();

				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setInteger("index", (y+(z*d)));
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

	IIcon[] icons = new IIcon[Global.WOOD_ALL.length];
	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/"+Global.WOOD_ALL[i]+" Plank");
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
