package com.bioxx.tfc.ModSupport;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public interface IShipsMod {

	public World getShipsWorld(World world, InventoryPlayer inventory);
}
