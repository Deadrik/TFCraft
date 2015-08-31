package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Constant.Global;

public class ItemStoneBrick extends ItemTerra
{
	private IIcon[] icons;
	public ItemStoneBrick() 
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.metaNames = Global.STONE_ALL;
		this.icons = new IIcon[metaNames.length];
	}
	public ItemStoneBrick(int id, String tex) 
	{
		super();
	}

	/*int[][] map = 
		{   {0,-1,0},
			{0,1,0},
			{0,0,-1},
			{0,0,1},
			{-1,0,0},
			{1,0,0},
		};*/

	/*@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
		if(objectMouseOver == null) {
			return false;
		}

		int dir = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;

		double xCoord = x + map[side][0];
		double yCoord = y + map[side][1];
		double zCoord = z + map[side][2];

		if((entityplayer.posX > xCoord+1  || entityplayer.posX < xCoord) || 
				(entityplayer.posZ > zCoord+1 || entityplayer.posZ < zCoord) || 
				(entityplayer.posY > yCoord+1.63 || entityplayer.posY < yCoord-1))
		{
			if(!world.isRemote)
			{
				if(world.getBlockId(x, y, z) == Block.snow.blockID || BlockCollapsable.canFallBelow(world, x, y, z))
				{
					if(     (itemstack.getItemDamage() < 3 && world.setBlockAndMetadata(x, y, z, TFCBlocks.StoneIgInCobble.blockID, itemstack.getItemDamage())) || 
							(itemstack.getItemDamage() < 13 && world.setBlockAndMetadata(x, y, z, TFCBlocks.StoneSedCobble.blockID, itemstack.getItemDamage() - 3))|| 
							(itemstack.getItemDamage() < 17 && world.setBlockAndMetadata(x, y, z, TFCBlocks.StoneIgExCobble.blockID, itemstack.getItemDamage() - 13))|| 
							(itemstack.getItemDamage() < 23 && world.setBlockAndMetadata(x, y, z, TFCBlocks.StoneMMCobble.blockID, itemstack.getItemDamage() - 17)))
					{
						if(!world.isRemote)
						{
							world.markBlockForUpdate(x, y, z);
							itemstack.stackSize = itemstack.stackSize-1;   
						}
						return true; 
					}
				}
				else if((world.getBlockId(x + map[side][0], y + map[side][1], z + map[side][2]) == 0 || 
						world.getBlockId(x + map[side][0], y + map[side][1], z + map[side][2]) == Block.snow.blockID  || BlockCollapsable.canFallBelow(world, x, y, z)))
				{
					if((itemstack.getItemDamage() < 3 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.StoneIgInCobble.blockID, itemstack.getItemDamage())) || 
					(itemstack.getItemDamage() < 13 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.StoneSedCobble.blockID, itemstack.getItemDamage() - 3))|| 
					(itemstack.getItemDamage() < 17 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.StoneIgExCobble.blockID, itemstack.getItemDamage() - 13))|| 
					(itemstack.getItemDamage() < 23 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.StoneMMCobble.blockID, itemstack.getItemDamage() - 17)))
					{
						world.markBlockForUpdate(x + map[side][0], y + map[side][1], z + map[side][2]);
						itemstack.stackSize = itemstack.stackSize-1;   
						return true; 
					}
				}
			}

		}

		return false;
	}*/


	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{

	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < metaNames.length; i++) {
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "rocks/"+metaNames[i]+" Brick");
		}
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < metaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
