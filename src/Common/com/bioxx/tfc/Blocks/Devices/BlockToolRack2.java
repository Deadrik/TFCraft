package com.bioxx.tfc.Blocks.Devices;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TileEntities.TileEntityToolRack;
import com.bioxx.tfc.api.Constant.Global;

public class BlockToolRack2 extends BlockToolRack
{
	public BlockToolRack2()
	{
		super();
		this.woodNames = new String[Global.WOOD_ALL.length - 16];
		System.arraycopy(Global.WOOD_ALL, 16, this.woodNames, 0, Global.WOOD_ALL.length - 16);
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			// tile entity should still be valid at this point, so get the wood type and drop the rack
			TileEntity te = world.getTileEntity(x, y, z);
			if((te != null) && (te instanceof TileEntityToolRack))
			{
				TileEntityToolRack rack = (TileEntityToolRack) te;
				dropBlockAsItem(world, x, y, z, new ItemStack(TFCBlocks.ToolRack2, 1, rack.woodType));
			}
		}
		return world.setBlockToAir(x, y, z);
	}

	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.WoodSupportH2;
	}

}
