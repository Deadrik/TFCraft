package TFC.Blocks.Devices;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.BlockTerra;
import TFC.TileEntities.TileEntityFireEntity;

public class BlockBellows extends BlockTerra
{
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

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
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

	@Override
	public Icon getIcon(int i, int j)
	{
		if(i == 0)//bottom
		{
			if(j == 0)
			{
				return Sides[0];
			}
			else if(j == 1)
			{
				return Sides[1];
			}
			else if(j == 2)
			{
				return Sides[3];
			}
			else if(j == 3)
			{
				return Sides[2];
			}
		}
		else if(i == 1)//top
		{
			if(j == 0)
			{
				return Sides[0];
			}
			else if(j == 1)
			{
				return Sides[1];
			}
			else if(j == 2)
			{
				return Sides[3];
			}
			else if(j == 3)
			{
				return Sides[2];
			}
		}
		else if(i == 2)//-z
		{
			if(j == 0)
			{
				return BellowsBack;
			}
			else if(j == 1)//-z
			{
				return Sides[2];
			}
			else if(j == 2)
			{
				return BellowsBack;
			}
			else if(j == 3)//-z
			{
				return Sides[1];
			}
		}
		else if(i == 3)//-z
		{
			if(j == 0)
			{
				return BellowsBack;
			}
			else if(j == 1)//-z
			{
				return Sides[1];
			}
			else if(j == 2)
			{
				return BellowsBack;
			}
			else if(j == 3)//-z
			{
				return Sides[2];
			}
		}
		else if(i == 4)//+x
		{
			if(j == 0)
			{
				return Sides[2];
			}
			else if(j == 1)//-z
			{
				return BellowsBack;
			}
			else if(j == 2)
			{
				return Sides[1];
			}
			else if(j == 3)//-z
			{
				return BellowsBack;
			}
		}
		else if(i == 5)//-z
		{
			if(j == 0)
			{
				return Sides[1];
			}
			else if(j == 1)//-z
			{
				return BellowsBack;
			}
			else if(j == 2)
			{
				return Sides[2];
			}
			else if(j == 3)//-z
			{
				return BellowsBack;
			}
		}
		else
		{
			return Sides[1];
		}

		return Sides[0];
	}
	
	public static Icon[] Sides = new Icon[4];
	public static Icon BellowsFront;
	public static Icon BellowsBack;

	@Override
	public void registerIcons(IconRegister registerer)
	{
		Sides[0] = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows82");
		Sides[1] = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows83");
		Sides[2] = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows84");
		Sides[3] = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows85");
		BellowsFront = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows Front");
		BellowsBack = registerer.registerIcon(Reference.ModID + ":" + "devices/Bellows Back");
	}

	@Override
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
		float f1 = j + 0.1F + random.nextFloat() * 6F / 16F;
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
	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		
		dropBlockAsItem_do(world, i, j, k, new ItemStack(TFCItems.BellowsItem, 1));
	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack is)
	{
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		world.setBlockMetadataWithNotify(i, j, k, l, 0x2);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}
}
