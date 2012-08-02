package net.minecraft.src.ic2.api;

/**
 * BaseSeeds are data containers indicating which crop with which stats is meant to be planted.
 * Used in conjunction with CropCard.registerBaseSeed
 * @author Alblaka
 *
 */
public class BaseSeed {
	public int id;
	public int size;
	public int statGrowth;
	public int statGain;
	public int statResistance;
	public int stackSize;
	
	public BaseSeed(int id, int size, int statGrowth, int statGain, int statResistance, int stackSize) {
		super();
		this.id = id;
		this.size = size;
		this.statGrowth = statGrowth;
		this.statGain = statGain;
		this.statResistance = statResistance;
		this.stackSize = stackSize;
	}
}
