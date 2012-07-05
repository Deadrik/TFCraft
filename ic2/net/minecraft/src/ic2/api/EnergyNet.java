package net.minecraft.src.ic2.api;

import net.minecraft.src.TileEntity;
import net.minecraft.src.World;

/**
 * Provides access to the energy network.
 */
public final class EnergyNet {
	/**
	 * Gets the EnergyNet instance for the specified world.
	 * 
	 * @param world world
	 * @return EnergyNet instance for the world
	 */
	public static EnergyNet getForWorld(World world) {
		try {
			return new EnergyNet(Class.forName(getPackage() + ".common.EnergyNet").getMethod("getForWorld", World.class).invoke(null, world));
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	private EnergyNet(Object energyNetInstance) {
		this.energyNetInstance = energyNetInstance;
	}
	
	/**
	 * Add a tile entity to the energy network.
	 * The tile entity has to be valid and initialized.
	 *
	 * @param addedTileEntity tile entity to add
	 */
	public void addTileEntity(TileEntity addedTileEntity) {
		try {
			Class.forName(getPackage() + ".common.EnergyNet").getMethod("addTileEntity", TileEntity.class).invoke(energyNetInstance, addedTileEntity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Removes a tile entity from the energy network.
	 * The tile entity has to be still valid.
	 *
	 * @param removedTileEntity tile entity to remove
	 */
	public void removeTileEntity(TileEntity removedTileEntity) {
		try {
			Class.forName(getPackage() + ".common.EnergyNet").getMethod("removeTileEntity", TileEntity.class).invoke(energyNetInstance, removedTileEntity);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Emit energy from an energy source to the energy network.
	 * 
	 * @param energySource energy source to emit energy from
	 * @param amount amount of energy to emit in EU
	 * @return Leftover (unused) power  
	 */
	public int emitEnergyFrom(IEnergySource energySource, int amount) {
		try {
			return ((Integer) Class.forName(getPackage() + ".common.EnergyNet").getMethod("emitEnergyFrom", IEnergySource.class, Integer.TYPE).invoke(energyNetInstance, energySource, amount)).intValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Get the amount of energy currently being conducted by a conductor.
	 * Call this twice with a delay to get the average conducted power by doing (call2 - call1) / 2.
	 *
	 * @param tileEntity conductor
	 */
	public long getTotalEnergyConducted(TileEntity tileEntity) {
		try {
			return ((Long) Class.forName(getPackage() + ".common.EnergyNet").getMethod("getTotalEnergyConducted", TileEntity.class).invoke(energyNetInstance, tileEntity)).longValue();
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * Get the base IC2 package name, used internally.
	 * 
	 * @return IC2 package name, if unable to be determined defaults to ic2
	 */
	private static String getPackage() {
		Package pkg = EnergyNet.class.getPackage();
		if (pkg != null) return pkg.getName().substring(0, pkg.getName().lastIndexOf('.'));
		else return "ic2";
	}
	
	Object energyNetInstance;
}

