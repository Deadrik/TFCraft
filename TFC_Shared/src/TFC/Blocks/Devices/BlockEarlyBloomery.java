package TFC.Blocks.Devices;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.ICustomCollision;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.CollisionRayTraceStandard;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.TileEntities.TileEntityEarlyBloomery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockEarlyBloomery extends BlockTerraContainer implements ICustomCollision
{
	Icon textureOn;
	Icon textureOff;

	public static final int bloomeryToStackMap[][] = { { 0, 1 }, { -1, 0 }, { 0, -1 }, { 1, 0 } };
	public static final int sidesMap[][] = { { 1, 0 }, { 0, 1 }, { 1, 0 }, { 0, 1 } };
	//North = 2 = -z
	//South = 0 = +z
	//West = 1 = -x
	//East = 3 = = +x

	public BlockEarlyBloomery(int i)
	{
		super(i, Material.rock);
		this.setCreativeTab(TFCTabs.TFCDevices);
		setBlockBounds(0.0F, 0.0F, 0.0f, 0.0f, 0.0F, 0.0F);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z) & 4;
		if (meta == 0)
		{
			return 0;
		}
		else
		{
			return 15;
		}

	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if (!canBlockStay(world, i, j, k))
		{
			world.setBlockToAir(i, j, k);
			world.spawnEntityInWorld(new EntityItem(world, i, j, k, new ItemStack(this, 1)));
		}
		else if ((TileEntityEarlyBloomery) world.getBlockTileEntity(i, j, k) != null)
		{
			TileEntityEarlyBloomery te = (TileEntityEarlyBloomery) world.getBlockTileEntity(i, j, k);
			ItemStack is = entityplayer.getCurrentEquippedItem();

			if (is != null && (is.getItem() == TFCItems.FireStarter || is.getItem() == TFCItems.FlintSteel))
			{
				if (te.canLight())
				{
					entityplayer.getCurrentEquippedItem().damageItem(1, entityplayer);
				}
			}
			else
			{
				world.playAuxSFXAtEntity(entityplayer, 1003, i, j, k, 0);
				if (isOpen(world.getBlockMetadata(i, j, k)))
					world.setBlockMetadataWithNotify(i, j, k, world.getBlockMetadata(i, j, k) - 8, 3);
				else
					world.setBlockMetadataWithNotify(i, j, k, world.getBlockMetadata(i, j, k) + 8, 3);
			}
		}
		return true;
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		int id = world.getBlockId(i, j, k);
		if(id == 0)
			return true;

		boolean flipped = false;

		int dir = world.getBlockMetadata(i, j, k) & 3;
		TileEntityEarlyBloomery te = (TileEntityEarlyBloomery) world.getBlockTileEntity(i, j, k);

		if(te!= null)
			flipped = te.isFlipped;

		if(checkStack(world, i, j, k, dir))
		{
			if (checkVertical(world, i,j,k, flipped)) 
			{
				if(checkHorizontal(world, i,j,k, flipped))
					return true; 
			}
			else if(te != null && !flipped)
			{
				this.tryFlip(world, i, j, k);
				flipped = te.isFlipped;
				if (checkVertical(world, i,j,k, flipped)) 
				{
					if(checkHorizontal(world, i,j,k, flipped))
						return true; 
				}
			}
		}
		return false;
	}

	public boolean checkStack(World world, int i, int j, int k, int dir)
	{
		int[] map = bloomeryToStackMap[dir];
		int centerX = i + map[0];
		int centerZ = k + map[1];
		if (isNorthStackValid(world, centerX, j, centerZ-1) || (centerX == i && centerZ-1 == k))
		{
			if (isSouthStackValid(world, centerX, j, centerZ+1) || (centerX == i && centerZ+1 == k))
			{
				if (isEastStackValid(world, centerX-1, j, centerZ) || (centerX-1 == i && centerZ == k))
				{
					if (isWestStackValid(world, centerX+1, j, centerZ) || (centerX+1 == i && centerZ == k)) 
					{ 
						return true; 
					}
				}
			}
		}
		return false;
	}

	private boolean isNorthStackValid(World world, int i, int j, int k)
	{
		if (((world.getBlockMaterial(i, j, k) == Material.rock || world.getBlockMaterial(i, j, k) == Material.iron) && world.isBlockNormalCube(i, j, k)) || 
				TFC_Core.isSouthFaceSolid(world, i, j, k))//Since its the North Block, we need to make sure the South side facing the stack is solid
		{
			return true;
		}
		return false;
	}

	private boolean isSouthStackValid(World world, int i, int j, int k)
	{
		if (((world.getBlockMaterial(i, j, k) == Material.rock || world.getBlockMaterial(i, j, k) == Material.iron) && world.isBlockNormalCube(i, j, k)) || 
				TFC_Core.isNorthFaceSolid(world, i, j, k))//Since its the South Block, we need to make sure the North side facing the stack is solid
		{
			return true;
		}
		return false;
	}

	private boolean isEastStackValid(World world, int i, int j, int k)
	{
		if (((world.getBlockMaterial(i, j, k) == Material.rock || world.getBlockMaterial(i, j, k) == Material.iron) && world.isBlockNormalCube(i, j, k)) || 
				TFC_Core.isWestFaceSolid(world, i, j, k))//Since its the East Block, we need to make sure the West side facing the stack is solid
		{
			return true;
		}
		return false;
	}

	private boolean isWestStackValid(World world, int i, int j, int k)
	{
		if (((world.getBlockMaterial(i, j, k) == Material.rock || world.getBlockMaterial(i, j, k) == Material.iron) && world.isBlockNormalCube(i, j, k)) || 
				TFC_Core.isEastFaceSolid(world, i, j, k))//Since its the West Block, we need to make sure the East side facing the stack is solid
		{
			return true;
		}
		return false;
	}

	private boolean checkHorizontal(World world, int i, int j, int k, boolean flip)
	{
		int dir = world.getBlockMetadata(i, j, k) & 3;

		if(flip)
			dir = flipDir(dir);

		int[] map = sidesMap[dir];

		boolean l = false;
		boolean r = false;
		if((world.getBlockMaterial(i-map[0], j, k-map[1]) == Material.rock || world.getBlockMaterial(i-map[0], j, k-map[1]) == Material.iron) && world.isBlockOpaqueCube(i-map[0], j, k-map[1]))
			l = true;

		if (!l && world.getBlockId(i-map[0], j, k-map[1]) == TFCBlocks.Detailed.blockID || world.getBlockId(i-map[0], j, k-map[1]) == TFCBlocks.stoneSlabs.blockID)
		{
			switch(dir)
			{
			case 0:
				if(TFC_Core.isNorthFaceSolid(world, i-map[0], j, k-map[1]) && TFC_Core.isEastFaceSolid(world, i-map[0], j, k-map[1]))
					l=true;
				break;
			case 1:
				if(TFC_Core.isEastFaceSolid(world, i-map[0], j, k-map[1]) && TFC_Core.isSouthFaceSolid(world, i-map[0], j, k-map[1]))
					l=true;
				break;
			case 2:
				if(TFC_Core.isSouthFaceSolid(world, i-map[0], j, k-map[1]) && TFC_Core.isEastFaceSolid(world, i-map[0], j, k-map[1]))
					l=true;
				break;
			case 3:
				if(TFC_Core.isWestFaceSolid(world, i-map[0], j, k-map[1]) && TFC_Core.isSouthFaceSolid(world, i-map[0], j, k-map[1]))
					l=true;
				break;
			}

			if(!TFC_Core.isBottomFaceSolid(world, i-map[0], j, k-map[1]))
				l = false;
			if(!TFC_Core.isTopFaceSolid(world, i-map[0], j, k-map[1]))
				l = false;
		}

		if((world.getBlockMaterial(i+map[0], j, k+map[1]) == Material.rock || world.getBlockMaterial(i+map[0], j, k+map[1]) == Material.iron) && world.isBlockOpaqueCube(i+map[0], j, k+map[1]))
			r = true;

		if (!r && world.getBlockId(i+map[0], j, k+map[1]) == TFCBlocks.Detailed.blockID || world.getBlockId(i+map[0], j, k+map[1]) == TFCBlocks.stoneSlabs.blockID)
		{
			switch(dir)
			{
			case 0:
				if(TFC_Core.isNorthFaceSolid(world, i+map[0], j, k+map[1]) && TFC_Core.isWestFaceSolid(world, i+map[0], j, k+map[1]))
					r=true;
				break;
			case 1:
				if(TFC_Core.isEastFaceSolid(world, i+map[0], j, k+map[1]) && TFC_Core.isNorthFaceSolid(world, i+map[0], j, k+map[1]))
					r=true;
				break;
			case 2:
				if(TFC_Core.isSouthFaceSolid(world, i+map[0], j, k+map[1]) && TFC_Core.isWestFaceSolid(world, i+map[0], j, k+map[1]))
					r=true;
				break;
			case 3:
				if(TFC_Core.isWestFaceSolid(world, i+map[0], j, k+map[1]) && TFC_Core.isNorthFaceSolid(world, i+map[0], j, k+map[1]))
					r=true;
				break;
			}
		}

		if(!TFC_Core.isBottomFaceSolid(world, i+map[0], j, k+map[1]))
			r = false;
		if(!TFC_Core.isTopFaceSolid(world, i+map[0], j, k+map[1]))
			r = false;

		if(l && r)
			return true;

		return false;

	}

	private boolean checkVertical(World world, int i, int j, int k, boolean flip)
	{
		int dir = world.getBlockMetadata(i, j, k) & 3;

		if(flip)
			dir = flipDir(dir);

		boolean b = false;
		boolean t = false;
		if((world.getBlockMaterial(i, j - 1, k) == Material.rock || world.getBlockMaterial(i, j - 1, k) == Material.iron) && world.isBlockOpaqueCube(i, j - 1, k))
			b = true;

		if (!b && world.getBlockId(i, j - 1, k) == TFCBlocks.Detailed.blockID || world.getBlockId(i, j - 1, k) == TFCBlocks.stoneSlabs.blockID)
		{
			switch(dir)
			{
			case 0:
				if(TFC_Core.isNorthFaceSolid(world, i, j-1, k) && TFC_Core.isEastFaceSolid(world, i, j-1, k) && TFC_Core.isWestFaceSolid(world, i, j-1, k))
					b=true;
				break;
			case 1:
				if(TFC_Core.isEastFaceSolid(world, i, j-1, k) && TFC_Core.isNorthFaceSolid(world, i, j-1, k) && TFC_Core.isSouthFaceSolid(world, i, j-1, k))
					b=true;
				break;
			case 2:
				if(TFC_Core.isSouthFaceSolid(world, i, j-1, k) && TFC_Core.isEastFaceSolid(world, i, j-1, k) && TFC_Core.isWestFaceSolid(world, i, j-1, k))
					b=true;
				break;
			case 3:
				if(TFC_Core.isWestFaceSolid(world, i, j-1, k) && TFC_Core.isNorthFaceSolid(world, i, j-1, k) && TFC_Core.isSouthFaceSolid(world, i, j-1, k))
					b=true;
				break;
			}

			if(!TFC_Core.isTopFaceSolid(world, i, j-1, k))
				b = false;
		}

		if((world.getBlockMaterial(i, j + 1, k) == Material.rock || world.getBlockMaterial(i, j + 1, k) == Material.iron) && world.isBlockOpaqueCube(i, j + 1, k))
			t = true;

		if (!t && world.getBlockId(i, j + 1, k) == TFCBlocks.Detailed.blockID || world.getBlockId(i, j + 1, k) == TFCBlocks.stoneSlabs.blockID)
		{
			switch(dir)
			{
			case 0:
				if(TFC_Core.isNorthFaceSolid(world, i, j+1, k) && TFC_Core.isEastFaceSolid(world, i, j+1, k) && TFC_Core.isWestFaceSolid(world, i, j+1, k))
					t=true;
				break;
			case 1:
				if(TFC_Core.isEastFaceSolid(world, i, j+1, k) && TFC_Core.isNorthFaceSolid(world, i, j+1, k) && TFC_Core.isSouthFaceSolid(world, i, j+1, k))
					t=true;
				break;
			case 2:
				if(TFC_Core.isSouthFaceSolid(world, i, j+1, k) && TFC_Core.isEastFaceSolid(world, i, j+1, k) && TFC_Core.isWestFaceSolid(world, i, j+1, k))
					t=true;
				break;
			case 3:
				if(TFC_Core.isWestFaceSolid(world, i, j+1, k) && TFC_Core.isNorthFaceSolid(world, i, j+1, k) && TFC_Core.isSouthFaceSolid(world, i, j+1, k))
					t=true;
				break;
			}

			if(!TFC_Core.isBottomFaceSolid(world, i, j+1, k) || !TFC_Core.isTopFaceSolid(world, i, j+1, k))
				t = false;
		}

		if(b && t)
			return true;

		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return canBlockStay(world,i,j,k);
	}

	@Override
	public Icon getIcon(int i, int j)
	{
		int lit = (j & 4) == 4 ? 1 : 0;

		if (!isOpen(j))
		{
			if (lit == 1)
				return textureOn;
		}
		return textureOff;
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		textureOn = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Bloomery On");
		textureOff = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Bloomery Off");
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack par6ItemStack)
	{
		if (!world.isRemote)
		{
			int dir = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
			//l = flipDir(l);
			world.setBlockMetadataWithNotify(i, j, k, dir, 0x2);
			if (!canBlockStay(world, i, j, k))
			{
				dropBlockAsItem_do(world, i, j, k, new ItemStack(this, 1));
			}
		}
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int i, int j, int k)
	{
		clearStack(world,i,j,k);
		return true;
	}

	public void clearStack(World world, int i, int j, int k)
	{
		if (!world.isRemote)
		{
			world.setBlockToAir(i, j, k);
			int meta = world.getBlockMetadata(i, j, k);
			int[] dir = bloomeryToStackMap[meta & 3];
			if (world.getBlockId(i + dir[0], j, k + dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlockToAir(i + dir[0], j, k + dir[1]);
			}
			if (world.getBlockId(i + dir[0], j + 1, k + dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlockToAir(i + dir[0], j + 1, k + dir[1]);
			}
			if (world.getBlockId(i + dir[0], j + 2, k + dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlockToAir(i + dir[0], j + 2, k + dir[1]);
			}
			if (world.getBlockId(i + dir[0], j + 3, k + dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlockToAir(i + dir[0], j + 3, k + dir[1]);
			}
		}
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if (!canBlockStay(world, i, j, k))
		{
			if (!tryFlip(world, i, j, k))
			{
				world.setBlockToAir(i, j, k);
				dropBlockAsItem_do(world, i, j, k, new ItemStack(this, 1));
			}
			else if (!canBlockStay(world, i, j, k))
			{
				world.setBlockToAir(i, j, k);
				dropBlockAsItem_do(world, i, j, k, new ItemStack(this, 1));
			}
		}
	}
	public static int flipDir(int dir)
	{
		int out = 0;
		if (dir - 2 >= 0)
			out = dir - 2;
		else if (dir + 2 < 4)
			out = dir + 2;
		return out;
	}

	private boolean tryFlip(World world, int i, int j, int k)
	{
		TileEntityEarlyBloomery te = (TileEntityEarlyBloomery)world.getBlockTileEntity(i, j, k);
		te.swapFlipped();
		if(!canBlockStay(world,i,j,k))
			return false;

		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		// TODO Auto-generated method stub
		return new TileEntityEarlyBloomery();
	}

	/*@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k)
	{
		int meta = access.getBlockMetadata(i, j, k) & 3;
		float f = 0.125F;
		if (!isOpen(access.getBlockMetadata(i, j, k)))
		{
			if ((meta & 3) == 0)
			{
				setBlockBounds(0.0F, 0.0F, 0.0f, 1.0f, 1.0F, f);
			}

			if ((meta & 3) == 1)
			{
				setBlockBounds(1.0f - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0f);
			}

			if ((meta & 3) == 2)
			{
				setBlockBounds(0.0f, 0.0F, 1.0f - f, 1.0F, 1.0F, 1.0F);
			}

			if ((meta & 3) == 3)
			{
				setBlockBounds(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F);
			}
		}
		else
			setBlockBounds(0.0F, 0.0F, 0.0f, 0.0f, 0.0F, 0.0F);
	}*/

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return null;
	}

	public static boolean isOpen(int par0)
	{
		return (par0 & 8) != 0;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.bloomeryRenderId;
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

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, List list)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int dir = meta & 3;
		TileEntityEarlyBloomery te = (TileEntityEarlyBloomery)world.getBlockTileEntity(i, j, k);
		if(te.isFlipped)
			dir = flipDir(dir);
		float f = 0.125F;

		if (!BlockEarlyBloomery.isOpen(meta))
		{
			if (dir == 0)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0f, 1.0f, 1.0F, f)});
			}
			else if (dir == 1)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(1.0f - f, 0.0F, 0.0F, 1.0F, 1.0F, 1.0f)});
			}
			else if (dir == 2)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0f, 0.0F, 1.0f - f, 1.0F, 1.0F, 1.0F)});
			}
			else if (dir == 3)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, f, 1.0F, 1.0F)});
			}
		}
		else
		{
			if (dir == 0)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0f, f, 1.0F, 0.5F)});
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(1 - f, 0.0F, 0.0f, 1, 1.0F, 0.5F)});
			}
			else if (dir == 1)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.5F, 0.0F, 0, 1.0F, 1.0F, f)});
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.5F, 0.0F, 1-f, 1.0F, 1.0F, 1)});
			}
			else if (dir == 2)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.5f, f, 1.0F, 1.0F)});
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(1 - f, 0.0F, 0.5f, 1, 1.0F, 1.0F)});
			}
			else if (dir == 3)
			{
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 0.0F, 0.5F, 1.0F, f)});
				list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0F, 0.0F, 1-f, 0.5F, 1.0F, 1.0)});
			}
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		return CollisionRayTraceStandard.collisionRayTrace(this, world, x, y, z, player, view);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	protected void dropBlockAsItem_do(World world, int x, int y, int z, ItemStack is)
	{
		if (!world.isRemote && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
		{
			clearStack(world, x, y, z);
			EntityItem ei = new EntityItem(world, x+0.5, y+0.5, z+0.5, is);
			ei.motionX = 0;
			ei.motionY = 0;
			ei.motionZ = 0;
			ei.delayBeforeCanPickup = 10;
			world.spawnEntityInWorld(ei);
		}
	}
}
