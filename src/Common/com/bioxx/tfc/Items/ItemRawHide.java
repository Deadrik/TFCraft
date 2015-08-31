package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.Flora.BlockLogHoriz;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.TileEntities.TELeatherRack;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemRawHide extends ItemLooseRock
{
	public ItemRawHide()
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFC_MATERIALS);
		this.metaNames = new String[]{"small","medium","large"};
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.MEDIUM);
	}


	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			if(itemstack.getItem() == TFCItems.hide && itemstack.getItemDamage() >= 2){
				int d = (int) ((45 + (entityplayer.rotationYaw % 360 + 360f) % 360) / 90) % 4; //direction
				int x2 = x + (d == 1 ? -1 : d == 3 ? 1 : 0); // the x-coord of the second block
				int z2 = z + (d == 2 ? -1 : d == 0 ? 1 : 0);
				if(world.getBlock(x, y, z)==TFCBlocks.thatch && side == 1 && world.getBlock(x2,y,z2)==TFCBlocks.thatch
						&& world.isAirBlock(x, y+1, z) && world.isAirBlock(x2, y+1, z2)){
					world.func_147480_a/*destroyBlock*/(x, y, z, false);
					world.func_147480_a/*destroyBlock*/(x2, y, z2, false);
					world.setBlock(x, y, z, TFCBlocks.strawHideBed, d, 2);
					world.setBlock(x2, y, z2, TFCBlocks.strawHideBed, d+8, 2);
					itemstack.stackSize--;
				}
			}
			else if(itemstack.getItem() == TFCItems.soakedHide && side == ForgeDirection.UP.ordinal() )
			{
				if(world.getBlock(x, y, z) instanceof BlockLogHoriz && world.isAirBlock( x, y + 1, z ) && world.setBlock(x, y+1, z, TFCBlocks.leatherRack))
				{
					TELeatherRack te = (TELeatherRack)world.getTileEntity(x, y+1, z);
					te.setLeather(itemstack);
				}

			}
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
		return itemstack;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5)
	{
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return this.itemIcon;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public void getSubItems(Item item, CreativeTabs tabs, List list)
	{
		list.add(new ItemStack(this, 1, 0));
		list.add(new ItemStack(this, 1, 1));
		list.add(new ItemStack(this, 1, 2));
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
	{
		// Blank to override ItemLooseRock's help tooltip.
	}
}
