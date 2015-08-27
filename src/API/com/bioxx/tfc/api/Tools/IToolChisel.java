package com.bioxx.tfc.api.Tools;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public interface IToolChisel 
{
	/***
	 * Called from the block that is being chiseled
	 * @param world
	 * @param player
	 * @param x
	 * @param y
	 * @param z
	 * @param block
	 * @param meta
	 * @param side
	 * @param hitX
	 * @param hitY
	 * @param hitZ
	 * @return true if the chisel has handled the event
	 */
	boolean onUsed(World world, EntityPlayer player, int x, int y, int z, Block block, int meta, int side, float hitX, float hitY, float hitZ);
	
	
	/***
	 * Called to make sure that the chisel can be used
	 * @param player
	 * @param x coordinate of the block
	 * @param y	coordinate of the block
	 * @param z coordinate of the block
	 * @return true if chiseling is allowed
	 */
	boolean canChisel(EntityPlayer player, int x, int y, int z);
}
