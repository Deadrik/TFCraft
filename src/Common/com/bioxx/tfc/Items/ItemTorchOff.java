package com.bioxx.tfc.Items;

import static net.minecraftforge.common.util.ForgeDirection.EAST;
import static net.minecraftforge.common.util.ForgeDirection.NORTH;
import static net.minecraftforge.common.util.ForgeDirection.SOUTH;
import static net.minecraftforge.common.util.ForgeDirection.WEST;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.world.World;

public class ItemTorchOff extends ItemTerra
{
	IIcon offIcon;
	public ItemTorchOff()
	{
		super();
		setMaxDamage(0);
		this.setMaxStackSize(32);
		this.setCreativeTab(TFCTabs.TFCDecoration);
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int[][] map = {{0,-1,0},{0,1,0},{0,0,-1},{0,0,1},{-1,0,0},{1,0,0}};
		
		if( !world.isRemote )
		{
			if(world.isAirBlock(x + map[side][0], y + map[side][1], z + map[side][2]))
			{
				int meta = 13;
				
				System.out.println(side +
						" " + world.isSideSolid(x, y, z, NORTH, true) +
						" " + world.isSideSolid(x, y, z, SOUTH, true) +
						" " + world.isSideSolid(x, y, z, WEST, true) +
						" " + world.isSideSolid(x, y, z, EAST, true));
				
				if (side == 2 && world.isSideSolid(x, y, z, NORTH, true)) meta = 12;
				if (side == 3 && world.isSideSolid(x, y, z, SOUTH, true)) meta = 11;
				if (side == 4 && world.isSideSolid(x, y, z, WEST, true)) meta = 10;
				if (side == 5 && world.isSideSolid(x, y, z, EAST, true)) meta = 9;
				
				world.setBlock(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.Torch, meta, 0x2);
				is.stackSize--;
			}
		}
		
		return false;
	}

	@Override
	public EnumSize getSize(ItemStack is) 
	{
		return EnumSize.TINY;
	}
	@Override
	public EnumWeight getWeight(ItemStack is) 
	{
		return EnumWeight.LIGHT;
	}
}
