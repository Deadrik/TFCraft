package com.bioxx.tfc.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Enums.EnumSize;

public class ItemBellows extends ItemTerraBlock
{
	public ItemBellows(Block par1)
	{
		super(par1);
		this.setCreativeTab(TFCTabs.TFC_TOOLS);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int l = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
			if(side == 1 && world.getBlock(x, y, z).isNormalCube() && world.getBlock(x, y, z).isOpaqueCube() && world.isAirBlock(x, y+1, z))
			{
				world.setBlock( x, y+1, z, TFCBlocks.bellows, l, 0x2);
				player.inventory.mainInventory[player.inventory.currentItem].stackSize--;
				return true;
			}
		}
		return false;
	}

	@Override
	public EnumSize getSize(ItemStack is) {
		return EnumSize.HUGE;
	}
}
