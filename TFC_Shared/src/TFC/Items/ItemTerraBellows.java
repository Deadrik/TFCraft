package TFC.Items;

import TFC.Core.Helper;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC;
import net.minecraft.src.forge.ITextureProvider;

public class ItemTerraBellows extends Item implements ITextureProvider
{
	public ItemTerraBellows(int i) 
	{
		super(i);
		maxStackSize = 1;
	}

	@Override
	public int getMetadata(int i) 
	{		
		return i;
	}

	@Override
	public String getTextureFile()
	{
		return "/bioxx/terratools.png";
	}

	public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer)
	{
		if(!world.isRemote)
		{
			MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
			if(objectMouseOver == null) {
				return itemstack;
			}		
			int x = objectMouseOver.blockX;
			int y = objectMouseOver.blockY;
			int z = objectMouseOver.blockZ;
			int side = objectMouseOver.sideHit;

			int l = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;
			if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
					world.getBlockId(x, y+1, z) == 0)
			{
				world.setBlockAndMetadataWithNotify( x, y+1, z, mod_TFC.terraBellows.blockID, l);
				return new ItemStack(this,0);
			}
		}

		return itemstack;
	}
}
