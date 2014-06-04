package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

import com.bioxx.tfc.Items.ItemBlocks.ItemTerraBlock;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemBarrels extends ItemTerraBlock
{
	private int type = 0;
	public ItemBarrels(Block par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.MetaNames = Global.WOOD_ALL;
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		return EnumWeight.HEAVY;
	}

	public void readFromItemNBT(NBTTagCompound nbt, List arraylist)
	{
		if(nbt != null && nbt.hasKey("type"))
		{
			type = nbt.getInteger("type");
			arraylist.add(EnumChatFormatting.YELLOW + TEBarrel.getType(type));
		}
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		readFromItemNBT(is.getTagCompound(), arraylist);
	}

	@Override
	public boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ, int metadata)
	{

		if (!world.setBlock(x, y, z, field_150939_a, metadata&15, 3))
		{
			return false;
		}

		if (world.getBlock(x, y, z) == field_150939_a)
		{
			field_150939_a.onBlockPlacedBy(world, x, y, z, player, stack);
			field_150939_a.onPostBlockPlaced(world, x, y, z, 0);

			TEBarrel te = (TEBarrel) world.getTileEntity(x, y, z);
			te.barrelType = metadata;

		}

		return true;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
