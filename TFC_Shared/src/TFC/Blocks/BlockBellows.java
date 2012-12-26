package TFC.Blocks;

import java.util.Random;

import TFC.*;
import TFC.TileEntities.TileEntityFireEntity;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
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

public class BlockBellows extends BlockTerra
{
	private int meta;
	private int xCoord;
	private int yCoord;
	private int zCoord;

	public static final int blockMap[][] = {
		{
			0, 1
		}, {
			-1, 0
		}, {
			0, -1
		}, {
			1, 0
		}
	};

	public static int getDirectionFromMetadata(int i)
	{
		return i & 3;
	}

	public BlockBellows(int i, Material material) 
	{
		super(i, material);
	}

	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;

		if(world.isRemote)
		{
			return true;
		} 
		else
		{
			GiveAir(world,i, j, k);
			return true;
		}
	}

	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		if(i == 0)//bottom
		{
			if(j == 0)
			{
				return 82;
			}
			else if(j == 1)
			{
				return 83;
			}
			else if(j == 2)
			{
				return 85;
			}
			else if(j == 3)
			{
				return 84;
			}
		}
		else if(i == 1)//top
		{
			if(j == 0)
			{
				return 82;
			}
			else if(j == 1)
			{
				return 83;
			}
			else if(j == 2)
			{
				return 85;
			}
			else if(j == 3)
			{
				return 84;
			}
		}
		else if(i == 2)//-z
		{
			if(j == 0)
			{
				return 0;
			}
			else if(j == 1)//-z
			{
				return 84;
			}
			else if(j == 2)
			{
				return 0;
			}
			else if(j == 3)//-z
			{
				return 83;
			}
		}
		else if(i == 3)//-z
		{
			if(j == 0)
			{
				return 0;
			}
			else if(j == 1)//-z
			{
				return 83;
			}
			else if(j == 2)
			{
				return 0;
			}
			else if(j == 3)//-z
			{
				return 84;
			}
		}
		else if(i == 4)//+x
		{
			if(j == 0)
			{
				return 84;
			}
			else if(j == 1)//-z
			{
				return 0;
			}
			else if(j == 2)
			{
				return 83;
			}
			else if(j == 3)//-z
			{
				return 0;
			}
		}
		else if(i == 5)//-z
		{
			if(j == 0)
			{
				return 83;
			}
			else if(j == 1)//-z
			{
				return 0;
			}
			else if(j == 2)
			{
				return 84;
			}
			else if(j == 3)//-z
			{
				return 0;
			}
		}
		else
		{
			return 83;
		}

		return 82;
	}

	public int getRenderType()
	{
		return TFCBlocks.BellowsRenderId;
	}

	public void GiveAir(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);

		int x = blockMap[meta][0];
		int z = blockMap[meta][1];

		Random random = new Random();
		float f = (float)i +x+ 0.5F;
		float f1 = (float)j + 0.1F + random.nextFloat() * 6F / 16F;
		float f2 = (float)k +z+ 0.5F;
		float f3 = 0.82F;
		float f4 = random.nextFloat() * 0.6F;
		float f5 = random.nextFloat() * -0.6F;
		float f6 = random.nextFloat() * -0.6F;
		world.spawnParticle("smoke", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);

		TileEntity te = world.getBlockTileEntity(i+x, j, k+z);
		TileEntity te2 = world.getBlockTileEntity(i+x, j-1, k+z);
		TileEntityFireEntity tileentityfirepit = null;;
		if(te != null && te instanceof TileEntityFireEntity)
		{
			tileentityfirepit = (TileEntityFireEntity)te;
		}
		else if(te2 != null && te2 instanceof TileEntityFireEntity)
		{
			tileentityfirepit = (TileEntityFireEntity)te2;
		}
		
		if(tileentityfirepit != null)
		{
			tileentityfirepit.receiveAirFromBellows();
		}
	}
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BellowsItem, 1));
	}

	public boolean isOpaqueCube()
	{
		return false;
	}

	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
		byte byte0 = 0;
		byte byte1 = 0;
		if(l == 0)//+z
		{
			byte1 = 1;
		}
		if(l == 1)//-x
		{
			byte0 = -1;
		}
		if(l == 2)//-z
		{
			byte1 = -1;
		}
		if(l == 3)//+x
		{
			byte0 = 1;
		}
		world.setBlockMetadataWithNotify(i, j, k, l);

	}

	public void powerBlock(World world, int i, int j, int k, int par5, int par6)
	{
		GiveAir(world,i, j, k);
	}

	public boolean renderAsNormalBlock()
	{
		return false;
	}
}
