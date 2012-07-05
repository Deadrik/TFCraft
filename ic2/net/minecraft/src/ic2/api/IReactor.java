package net.minecraft.src.ic2.api;

import net.minecraft.src.ChunkCoordinates;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

/**
 * Interface implemented by the nuclear reactor tile entity.
 */
public interface IReactor {
	/**
	 * Get the reactor's position in the world.
	 * 
	 * @return Position of the reactor
	 */
	public ChunkCoordinates getPosition();
	
	/**
	 * Get the reactor's corresponding world.
	 * 
	 * @return The reactor's world
	 */
	public World getWorld();
	
	/**
	 * Get the reactor's heat.
	 * 
	 * @return The reactor's heat
	 */
	public int getHeat();
	
	/**
	 * Set the reactor's heat.
	 * 
	 * @param heat reactor heat
	 */
	public void setHeat(int heat);
	
	/**
	 * Increase the reactor's heat.
	 * 
	 * Use negative values to decrease.
	 * 
	 * @param amount amount of heat to add
	 * @return The reactor's heat after adding the specified amount
	 */
	public int addHeat(int amount);
	
	/**
	 * Get the item at the specified grid coordinates.
	 * 
	 * @param x X position of the item
	 * @param y Y position of the item
	 * @return The item or null if there is no item
	 */
	public ItemStack getItemAt(int x, int y);
	
	/**
	 * Set the item at the specified grid coordinates.
	 * 
	 * @param x X position of the item
	 * @param y Y position of the item
	 * @param item The item to set.
	 */
	public void setItemAt(int x, int y, ItemStack item);
	
	/**
	 * Explode the reactor.
	 */
	public void explode();
}
