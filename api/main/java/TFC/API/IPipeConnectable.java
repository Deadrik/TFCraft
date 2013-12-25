package TFC.API;

import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public interface IPipeConnectable {
	//is able to connect to (for most cases, is the origin of the block facing the block that asks)
	public boolean canConnectTo(IBlockAccess world, int desiredFace, int x, int y, int z);
	//executes code based for having power / steam added
	/**
	 *@param world the world
	 *@param fedFace For metadata: 0 = top origin, 5 = bottom origin, 1 = left, 4 = right, 2 = front, 3 = back, for MATH REASONS. 
	 * 
	*/
	public boolean feed(IBlockAccess world, int fedFace, int x, int y, int z, boolean isLiquid);
	//Certain blocks won't take input / connect to pipes if there are other attachments to said pipe. 0 means doesn't care, 1 means otherwise
	public int hasToBeOnlyOption();
	
	/**
	 * gives us the ability to use the last bit in the meta
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public int getSide(IBlockAccess world, int x, int y, int z);
	/**
	 * gives us the non-side related metadata. Only used in toggleable pipes, nothing else should have it equal 1.
	 * @param world
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public int getFinalBit(IBlockAccess world, int x, int y, int z);
}
