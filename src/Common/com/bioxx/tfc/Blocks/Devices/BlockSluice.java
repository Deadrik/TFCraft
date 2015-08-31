package com.bioxx.tfc.Blocks.Devices;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.TileEntities.TESluice;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCFluids;
import com.bioxx.tfc.api.TFCItems;

public class BlockSluice extends BlockContainer
{
	public static final int HEAD_FOOT_BLOCKMAP[][] = { {0, 1}, {-1, 0}, {0, -1}, {1, 0} };

	public BlockSluice()
	{
		super(Material.wood);
		needsRandomTick = true;
		//		entity = new TileEntityTerraSluice();
		//		entity.canUpdate();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		int meta = world.getBlockMetadata(i, j, k);
		//int xCoord = i;
		//int yCoord = j;
		//int zCoord = k;
		//Minecraft mc = ModLoader.getMinecraftInstance();
		//TileEntity te = world.getTileEntity(i, j, k);

		if(world.isRemote)
			return true;
		else
		{
			if(!isBlockFootOfBed(meta))
				if((TESluice)world.getTileEntity(i, j, k)!=null)
				{
					TESluice tileentitysluice;
					tileentitysluice = (TESluice)world.getTileEntity(i, j, k);
					ItemStack is =entityplayer.getCurrentEquippedItem();
					if(is != null && is.getItem() == TFCItems.goldPan && is.getItemDamage() != 0)
					{
						tileentitysluice.soilAmount+=7;
						tileentitysluice.soilType = (byte) is.getItemDamage();
						if(tileentitysluice.soilAmount > 50)
							tileentitysluice.soilAmount = 50;
						is.setItemDamage(0);
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, is);
						/*The line below was uncommented before the smp inclusion. If this section isn't working it's because the above line never worked properly.
						 * Apparently I was doing the same thing twice, so I'm not sure if one of these wasn't working.*/
						//mc.thePlayer.inventory.setInventorySlotContents(mc.thePlayer.inventory.currentItem, new ItemStack(mod_TFC_Core.terraGoldPan,1,0));
						return true;
					}
					entityplayer.openGui(TerraFirmaCraft.instance, 25, world, i, j, k);
					//ModLoader.openGUI(entityplayer, new GuiTerraSluice(entityplayer.inventory, tileentitysluice));
				}
			return true;
		}
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if((meta & 4) != 0 && side == 1)
			return TFCFluids.SALTWATER.getFlowingIcon();
		else
			return TFCBlocks.woodSupportH.getIcon(side, 8);
	}

	@Override
	@SideOnly(Side.CLIENT)
	/**
	 * Returns a integer with hex for 0xrrggbb with this color multiplied against the blocks color. Note only called
	 * when first determining what to render.
	 */
	public int colorMultiplier(IBlockAccess world, int x, int y, int z)
	{
		if ((world.getBlockMetadata(x, y, z) & 4) == 0)
			return 16777215;
		else
			return 0x354d35;
	}

	public static int getDirectionFromMetadata(int i)
	{
		return i & 3;
	}

	public static boolean isBlockFootOfBed(int i)
	{
		return (i & 8) != 0;
	}

	public boolean getIsRecievingWater(int i)
	{
		return (i & 4) != 0;
	}

	public String getItemNameIS(ItemStack itemstack) 
	{
		return "Sluice";
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.sluiceRenderId;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		if(!isBlockFootOfBed(i))
			return TFCItems.sluiceItem;
		else
			return null;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		byte byte0 = 0;
		byte byte1 = 0;
		if(l == 0)
			byte1 = 1;
		if(l == 1)
			byte0 = -1;
		if(l == 2)
			byte1 = -1;
		if(l == 3)
			byte0 = 1;
		world.setBlockMetadataWithNotify(i, j, k, l, 3);
		if(world.getBlock(i, j, k) == this)
			world.setBlock(i + byte0, j, k + byte1, this, l + 8, 3);

		//Minecraft mc = ModLoader.getMinecraftInstance();
		//mc.ingameGUI.addChatMessage("Dir = "+(new StringBuilder()).append(l).toString());
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k) 
	{
		int meta = par1IBlockAccess.getBlockMetadata(i, j, k);
		if(BlockSluice.isBlockFootOfBed(meta))
			setBlockBounds(0,0,0,1,0.5f,1);
		else
			setBlockBounds(0,0,0,1,1,1);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		if(BlockSluice.isBlockFootOfBed(meta))
			return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.5f, k+1);
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	}

	@Override
	public boolean canPlaceBlockAt(World world, int x, int y, int z)
	{
		Block block = world.getBlock(x, y, z);

		int dir = getDirectionFromMetadata(world.getBlockMetadata(x, y, z));
		int[] offset = HEAD_FOOT_BLOCKMAP[dir];

		boolean stay = canStay(world, x, y, z, false, dir) && canStay(world, x + offset[0], y, z + offset[1], true, dir) && 
				(block.isAir(world, x, y, z) || block.getMaterial().isReplaceable());

		return stay;
	}

	public boolean canPlace(World world, int i, int j, int k,int dir)
	{
		int[] offset = HEAD_FOOT_BLOCKMAP[dir];
		Block topBlock = world.getBlock(i, j, k);
		Block footBlock = world.getBlock(i+offset[0],j,k+offset[1]);
		boolean stay = canStay(world, i, j, k, false, dir) && canStay(world, i + offset[0], j, k + offset[1], true, dir) && 
				(topBlock.isAir(world, i, j, k) || topBlock.getMaterial().isReplaceable()) &&
				(footBlock.isAir(world, i+offset[0],j,k+offset[1]) || footBlock.getMaterial().isReplaceable());

		return stay;
	}

	private boolean canStay(World world, int i, int j, int k, boolean foot, int dir)
	{
		int l = dir;
		if(l == 0)
			if(!foot && 
					(!world.getBlock(i+1, j, k).isNormalCube() || 
							!world.getBlock(i-1, j, k).isNormalCube() || 
							!world.getBlock(i, j, k-1).isNormalCube() || 
							!world.getBlock(i, j-1, k) .isNormalCube() || 
							world.getBlock(i, j+2, k).isNormalCube()))
				return false;
			else if(foot && 
					(!world.getBlock(i+1, j, k).isNormalCube() || 
							!world.getBlock(i-1, j, k).isNormalCube() || 
							!world.getBlock(i, j-1, k) .isNormalCube() || 
							world.getBlock(i, j+2, k).isNormalCube()))
				return false;
		if(l == 1)
			if(!foot && 
					(!world.getBlock(i, j, k+1).isNormalCube() || 
							!world.getBlock(i, j, k-1).isNormalCube() || 
							!world.getBlock(i+1, j, k).isNormalCube() ||
							!world.getBlock(i, j-1, k).isNormalCube() || 
							world.getBlock(i, j+2, k).isNormalCube()))
				return false;
			else if(foot && 
					(!world.getBlock(i, j, k+1).isNormalCube() || 
							!world.getBlock(i, j, k-1).isNormalCube() || 
							!world.getBlock(i, j-1, k) .isNormalCube() || 
							world.getBlock(i, j+2, k).isNormalCube()))
				return false;
		if(l == 2)
			if(!foot && 
					(!world.getBlock(i+1, j, k).isNormalCube() || 
							!world.getBlock(i-1, j, k).isNormalCube() || 
							!world.getBlock(i, j, k+1).isNormalCube() || 
							!world.getBlock(i, j-1, k) .isNormalCube() || 
							world.getBlock(i, j+2, k).isNormalCube()))
				return false;
			else if(foot && 
					(!world.getBlock(i+1, j, k).isNormalCube() || 
							!world.getBlock(i-1, j, k).isNormalCube() || 
							!world.getBlock(i, j-1, k) .isNormalCube() || 
							world.getBlock(i, j+2, k).isNormalCube()))
				return false;
		if(l == 3)
			if(!foot && 
					(!world.getBlock(i, j, k+1).isNormalCube() || 
							!world.getBlock(i, j, k-1).isNormalCube() || 
							!world.getBlock(i-1, j, k).isNormalCube() ||
							!world.getBlock(i, j-1, k) .isNormalCube() || 
							world.getBlock(i, j+2, k).isNormalCube()))
				return false;
			else if(foot && 
					(!world.getBlock(i, j, k+1).isNormalCube() || 
							!world.getBlock(i, j, k-1).isNormalCube() || 
							!world.getBlock(i, j-1, k) .isNormalCube() || 
							world.getBlock(i, j+2, k).isNormalCube()))
				return false;
		return true;
	}

	//	@Override
	//	public void onBlockRemoval(World world, int i, int j, int k)
	//	{
	//		if((world.getBlockMetadata(i, j, k) & 8) == 0)
	//		{
	//			world.setBlock(i, j, k, 0);
	//		}
	//	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block block)
	{
		//Minecraft mc = ModLoader.getMinecraftInstance();
		int i1 = world.getBlockMetadata(i, j, k);
		int j1 = getDirectionFromMetadata(i1);

		if(isBlockFootOfBed(i1))
		{
			if(world.getBlock(i - HEAD_FOOT_BLOCKMAP[j1][0], j, k - HEAD_FOOT_BLOCKMAP[j1][1]) != this || !canStay(world, i, j, k,true,j1))
				world.setBlockToAir(i, j, k);
		}
		else if(world.getBlock(i + HEAD_FOOT_BLOCKMAP[j1][0], j, k + HEAD_FOOT_BLOCKMAP[j1][1]) != this || !canStay(world, i, j, k,false,j1))
		{
			world.setBlockToAir(i, j, k);
			if(!world.isRemote)
				dropBlockAsItem(world, i, j, k, i1, 0);
		}
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2) 
	{
		return new TESluice();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return new ItemStack(TFCItems.sluiceItem);
	}
}
