package TFC.API;

import java.util.List;

import net.minecraft.world.World;

public interface ICustomCollision
{
	public void addCollisionBoxesToList(World world, int i, int j, int k, List boxlist);
}
