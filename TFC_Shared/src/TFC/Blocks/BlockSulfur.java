package TFC.Blocks;

import java.util.Random;

import TFC.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class BlockSulfur extends BlockTerra
{
	Icon[] icons = new Icon[4];
	public BlockSulfur(int i, Material material)
	{
		super(i, material);
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int j) 
	{
		return icons[j];
	}
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		return null;
	}
	
	@Override
    public void registerIcon(IconRegister registerer)
    {
		for(int i = 0; i < 4; i++)
		{
			icons[i] = registerer.func_94245_a("ore/Sulfur"+i);
		}
    }
	@Override
	public int getRenderType()
	{
		return TFCBlocks.sulfurRenderId;
	}
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//super.harvestBlock(world, entityplayer, i, j, k, l);
	    dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.SulfurPowder, quantityDropped(new Random())));
	}
	@Override
	public int idDropped(int i, Random random, int j)
	{
		return TFCItems.SulfurPowder.itemID;
	}
	@Override
	public boolean isBlockNormalCube(World world, int i, int j, int k)
	{
		return false;
	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		int num = 0;
		if(world.isBlockNormalCube(i, j, k+1))
		{
			num++;
		}
		if(world.isBlockNormalCube(i, j, k-1))
		{
			num++;
		}
		if(world.isBlockNormalCube(i+1, j, k))
		{
			num++;
		}
		if(world.isBlockNormalCube(i-1, j, k))
		{
			num++;
		}
		if(world.isBlockNormalCube(i, j+1, k))
		{
			num++;
		}
		if(world.isBlockNormalCube(i, j-1, k))
		{
			num++;
		}
		if(num == 0)
		{
			world.setBlock(i, j, k, 0);
			this.dropBlockAsItem(world, i, j, k, l, 0);
		}
	}

	@Override
	public int quantityDropped(Random random)
	{
		return 1 + random.nextInt(5);
	}
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess iblockaccess, int i, int j, int k)
	{
		int num = 0;
		if(iblockaccess.isBlockNormalCube(i, j, k+1))
		{
			setBlockBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i, j, k-1))
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i+1, j, k))
		{
			setBlockBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i-1, j, k))
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i, j+1, k))
		{
			setBlockBounds(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
			num++;
		}
		if(iblockaccess.isBlockNormalCube(i, j-1, k))
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
			num++;
		}
		if(num > 1)
		{
			setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		}
	}

}
