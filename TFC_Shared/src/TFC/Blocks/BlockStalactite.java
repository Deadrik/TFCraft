package TFC.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Textures;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockStalactite extends BlockTerra
{
	Random R;

	public BlockStalactite(int par1)
	{
		super(par1);
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if ((world.isBlockNormalCube(i, j+1, k) || world.isBlockNormalCube(i, j+2, k)) && random.nextInt(80) == 0)
		{
			float f = i + 0.5F;
			float f1 = j-0.08f;
			float f2 = k + 0.5F;
			float f3 = 0.52F;

			float f4 = random.nextFloat() * 0.6F;
			float f5 = random.nextFloat() * -0.6F;
			float f6 = random.nextFloat() * -0.6F;
			world.spawnParticle("dripWater", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k)
	{
		boolean isStalac = isStalactite(access.getBlockMetadata(i, j, k));
		boolean isStalag = isStalagmite(access.getBlockMetadata(i, j, k));
		int style = access.getBlockMetadata(i, j, k) & 7;
		float f = 0.125F;
		R = new Random(i+(i*k));
		if(isStalac)
		{
			float height = TFC_Core.isRawStone(access.getBlockId(i, j+1, k)) ? 1 : TFC_Core.isRawStone(access.getBlockId(i, j+2, k)) ? 2 : 3;
			f = 0.1f + R.nextFloat()*0.025f;
			float width = height * f;
			if(height == 3)
				height = 0.5f+R.nextFloat()*0.5f;
			else height = 1;
			setBlockBounds(width, 1-height, width, 1f-width, 1, 1F-width);
		}
		else if(isStalag)
		{
			float height = TFC_Core.isRawStone(access.getBlockId(i, j-1, k)) ? 1 : TFC_Core.isRawStone(access.getBlockId(i, j-2, k)) ? 2 : 3;
			f = 0.1f + R.nextFloat()*0.025f;
			float width = height * f;
			if(height == 3)
				height = 0.5f+R.nextFloat()*0.5f;
			else height = 1;
			setBlockBounds(width, 0.0F, width, 1f-width, height, 1F-width);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		boolean isStalac = isStalactite(world.getBlockMetadata(i, j, k));
		boolean isStalag = isStalagmite(world.getBlockMetadata(i, j, k));
		int style = world.getBlockMetadata(i, j, k) & 7;
		float f = 0.125F;
		R = new Random(i+(i*k));
		if(isStalac)
		{
			float height = TFC_Core.isRawStone(world.getBlockId(i, j+1, k)) ? 1 : TFC_Core.isRawStone(world.getBlockId(i, j+2, k)) ? 2 : 3;
			f = 0.1f + R.nextFloat()*0.025f;
			float width = height * f;
			if(height == 3)
				height = 0.5f+R.nextFloat()*0.5f;
			else height = 1;
			setBlockBounds(width, 1-height, width, 1f-width, 1, 1F-width);

			return AxisAlignedBB.getAABBPool().getAABB(i + width, j - height, k + width, i + 1-width, j+1, k + 1 - width);
		}
		else if(isStalag)
		{
			float height = TFC_Core.isRawStone(world.getBlockId(i, j-1, k)) ? 1 : TFC_Core.isRawStone(world.getBlockId(i, j-2, k)) ? 2 : 3;
			f = 0.1f + R.nextFloat()*0.025f;
			float width = height * f;
			if(height == 3)
				height = 0.5f+R.nextFloat()*0.5f;
			else height = 1;
			setBlockBounds(width, 0.0F, width, 1f-width, height, 1F-width);
			return AxisAlignedBB.getAABBPool().getAABB(i+ width, j, k + width, i + 1-width, j+height, k + 1 - width);
		}

		return AxisAlignedBB.getAABBPool().getAABB(i + this.minX, j + this.minY, k + this.minZ, i + this.maxX, j+this.maxY, k + this.maxZ);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int i, int j, int k, int meta)
	{
		boolean isStalac = isStalactite(access.getBlockMetadata(i, j, k));
		boolean isStalag = isStalagmite(access.getBlockMetadata(i, j, k));
		if(isStalac)
		{
			if(TFC_Core.isRawStone(access.getBlockId(i, j+1, k)))
			{
				return Block.blocksList[access.getBlockId(i, j+1, k)].getIcon(0,access.getBlockMetadata(i, j+1, k));
			}
			else if(TFC_Core.isRawStone(access.getBlockId(i, j+2, k)))
			{
				return Block.blocksList[access.getBlockId(i, j+2, k)].getIcon(0,access.getBlockMetadata(i, j+2, k));
			}
			else if(TFC_Core.isRawStone(access.getBlockId(i, j+3, k)))
			{
				return Block.blocksList[access.getBlockId(i, j+3, k)].getIcon(0,access.getBlockMetadata(i, j+3, k));
			}
		}
		else if(isStalag)
		{
			if(TFC_Core.isRawStone(access.getBlockId(i, j-1, k)))
			{
				return Block.blocksList[access.getBlockId(i, j-1, k)].getIcon(0,access.getBlockMetadata(i, j-1, k));
			}
			else if(TFC_Core.isRawStone(access.getBlockId(i, j-2, k)))
			{
				return Block.blocksList[access.getBlockId(i, j-2, k)].getIcon(0,access.getBlockMetadata(i, j-2, k));
			}
			else if(TFC_Core.isRawStone(access.getBlockId(i, j-3, k)))
			{
				return Block.blocksList[access.getBlockId(i, j-3, k)].getIcon(0,access.getBlockMetadata(i, j-3, k));
			}
		}
		return TFC_Textures.InvisibleTexture;
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		boolean isStalac = isStalactite(world.getBlockMetadata(i, j, k));
		boolean isStalag = isStalagmite(world.getBlockMetadata(i, j, k));
		if(isStalac)
		{
			if(TFC_Core.isRawStone(world.getBlockId(i, j+1, k)) || TFC_Core.isRawStone(world.getBlockId(i, j+2, k)) ||
					TFC_Core.isRawStone(world.getBlockId(i, j+3, k)))
			{
				return true;
			}
		}
		else if(isStalag)
		{
			if(TFC_Core.isRawStone(world.getBlockId(i, j-1, k)) || TFC_Core.isRawStone(world.getBlockId(i, j-2, k)) ||
					TFC_Core.isRawStone(world.getBlockId(i, j-3, k)))
			{
				return true;
			}
		}
		return false;
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return 0;
	}

	/**
	 * @return if bit 4 is flipped then this is a stalagmite (Bottom Formation)
	 */
	public boolean isStalagmite(int meta)
	{
		return (meta & 8) > 0;
	}

	/**
	 * @return if bit 4 is not flipped then this is a stalactite (Top Formation)
	 */
	public boolean isStalactite(int meta)
	{
		return (meta & 8) == 0;
	}

	@Override
	public boolean canDropFromExplosion(Explosion ex)
	{
		return false;
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
}
