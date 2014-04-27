package com.bioxx.tfc.Blocks.Devices;

import java.util.Random;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.TileEntities.TileEntitySluice;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSluice extends BlockContainer
{
	public static final int headBlockToFootBlockMap[][] = { {0, 1}, {-1, 0}, {0, -1}, {1, 0} };

	public BlockSluice()
	{
		super(Material.wood);
		needsRandomTick = true;
		//		entity = new TileEntityTerraSluice();
		//		entity.canUpdate();
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		//Minecraft mc = ModLoader.getMinecraftInstance();
		TileEntity te = world.getTileEntity(i, j, k);

		if(world.isRemote)
			return true;
		else
		{
			if(!isBlockFootOfBed(meta))
				if((TileEntitySluice)world.getTileEntity(i, j, k)!=null)
				{
					TileEntitySluice tileentitysluice;
					tileentitysluice = (TileEntitySluice)world.getTileEntity(i, j, k);
					ItemStack is =entityplayer.getCurrentEquippedItem();
					if(is != null && is.getItem() == TFCItems.GoldPan && is.getItemDamage() != 0)
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
	public IIcon getIcon(int i, int j)
	{
		if(j == 4)
			return TFCBlocks.SaltWaterFlowing.getIcon(i, 0);
		else
			return TFCBlocks.Planks.getIcon(i, 0);
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
		String s = "Sluice";
		return s;
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
			return TFCItems.SluiceItem;
		else
			return null;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
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
		if(this.isBlockFootOfBed(meta))
			setBlockBounds(0,0,0,1,0.5f,1);
		else
			setBlockBounds(0,0,0,1,1,1);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		if(this.isBlockFootOfBed(meta))
			return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.5f, k+1);
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		Block var5 = par1World.getBlock(par2, par3, par4);

		int dir = getDirectionFromMetadata(par1World.getBlockMetadata(par2, par3, par4));
		int[] offset = headBlockToFootBlockMap[dir];

		boolean stay = (canStay(par1World, par2,par3,par4,false,dir) && 
				canStay(par1World, par2+offset[0],par3,par4+offset[1],true,dir)) && 
				(var5 == Blocks.air || var5.getMaterial().isReplaceable());

		return stay;
	}

	public boolean canPlace(World world, int i, int j, int k,int dir)
	{
		Block var5 = world.getBlock(i, j, k);

		int[] offset = headBlockToFootBlockMap[dir];

		boolean stay = (canStay(world, i,j,k,false,dir) && 
				canStay(world, i+offset[0],j,k+offset[1],true,dir)) && 
				(var5 == Blocks.air || var5.getMaterial().isReplaceable());

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
			if(world.getBlock(i - headBlockToFootBlockMap[j1][0], j, k - headBlockToFootBlockMap[j1][1]) != this || !canStay(world, i, j, k,true,j1))
				world.setBlockToAir(i, j, k);
		}
		else if(world.getBlock(i + headBlockToFootBlockMap[j1][0], j, k + headBlockToFootBlockMap[j1][1]) != this || !canStay(world, i, j, k,false,j1))
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
		return new TileEntitySluice();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
	}
}
