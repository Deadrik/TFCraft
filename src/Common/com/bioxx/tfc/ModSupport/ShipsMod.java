package com.bioxx.tfc.ModSupport;

import cpw.mods.fml.common.Optional.Method;
import cuchaz.ships.core.ShipIntermediary;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.world.World;

public class ShipsMod implements IShipsMod {

	public ShipsMod() {
		// TODO Auto-generated constructor stub
	}

	@Method(modid="cuchaz.ships")
	public World getShipsWorld(World world, InventoryPlayer inventory) {
		return ShipIntermediary.translateWorld(world, inventory);
	}

}
