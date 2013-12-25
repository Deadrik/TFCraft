package TFC.Blocks.Devices;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntitySluice;

public class BlockSluice extends BlockContainer
{
	public static final int headBlockToFootBlockMap[][] = {
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

	public BlockSluice(int i)
	{
		super(i, Material.wood);
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
		TileEntity te = world.getBlockTileEntity(i, j, k);

		if(world.isRemote)
		{
			return true;
		} else
		{
			if(!isBlockFootOfBed(meta))
			{
				if((TileEntitySluice)world.getBlockTileEntity(i, j, k)!=null)
				{
					TileEntitySluice tileentitysluice;
					tileentitysluice = (TileEntitySluice)world.getBlockTileEntity(i, j, k);
					ItemStack is =entityplayer.getCurrentEquippedItem();
					if(is!= null && is.itemID == TFCItems.GoldPan.itemID && is.getItemDamage() != 0)
					{
						tileentitysluice.soilAmount+=7;
						tileentitysluice.soilType = (byte) is.getItemDamage();
						if(tileentitysluice.soilAmount > 50) {
							tileentitysluice.soilAmount = 50;
						}
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
			}
			return true;
		}
	}

	@Override
	public Icon getIcon(int i, int j)
	{
		if(j == 4) {
			return Block.blocksList[Block.waterMoving.blockID].getIcon(i, 0);
		} else {
			return Block.planks.getIcon(i, 0);
		}
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
	public int idDropped(int i, Random random, int j)
	{
		if(!isBlockFootOfBed(i)) {
			return TFCItems.SluiceItem.itemID;
		} else {
			return 0;
		}
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
		world.setBlockMetadataWithNotify(i, j, k, l, 3);
		if(world.getBlockId(i, j, k) == this.blockID)
		{
			world.setBlock(i + byte0, j, k + byte1, this.blockID, l + 8, 3);
		}

		//Minecraft mc = ModLoader.getMinecraftInstance();
		//mc.ingameGUI.addChatMessage("Dir = "+(new StringBuilder()).append(l).toString());
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int i, int j, int k) 
	{
		int meta = par1IBlockAccess.getBlockMetadata(i, j, k);
		if(this.isBlockFootOfBed(meta)) {
			setBlockBounds(0,0,0,1,0.5f,1);
		} else {
			setBlockBounds(0,0,0,1,1,1);
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		if(this.isBlockFootOfBed(meta)) {
			return AxisAlignedBB.getBoundingBox(i, j, k, i+1, j+0.5f, k+1);
		}
		return AxisAlignedBB.getBoundingBox(i, j, k, i + 1, j + 1, k + 1);
	}

	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		int var5 = par1World.getBlockId(par2, par3, par4);

		int dir = getDirectionFromMetadata(par1World.getBlockMetadata(par2, par3, par4));
		int[] offset = headBlockToFootBlockMap[dir];

		boolean stay = (canStay(par1World, par2,par3,par4,false,dir) && 
				canStay(par1World, par2+offset[0],par3,par4+offset[1],true,dir)) && 
				(var5 == 0 || blocksList[var5].blockMaterial.isReplaceable());

		return stay;
	}

	public boolean canPlace(World world, int i, int j, int k,int dir)
	{
		int var5 = world.getBlockId(i, j, k);

		int[] offset = headBlockToFootBlockMap[dir];

		boolean stay = (canStay(world, i,j,k,false,dir) && 
				canStay(world, i+offset[0],j,k+offset[1],true,dir)) && 
				(var5 == 0 || blocksList[var5].blockMaterial.isReplaceable());

		return stay;
	}

	private boolean canStay(World world, int i, int j, int k, boolean foot, int dir)
	{
		int l = dir;
		if(l == 0)//+z
		{
			if(!foot && 
					(!world.isBlockNormalCube(i+1, j, k) || 
							!world.isBlockNormalCube(i-1, j, k) || 
							!world.isBlockNormalCube(i, j, k-1) || 
							!world.isBlockNormalCube(i, j-1, k)  || 
							world.isBlockNormalCube(i, j+2, k))) {
				return false;
			} else if(foot && 
					(!world.isBlockNormalCube(i+1, j, k) || 
							!world.isBlockNormalCube(i-1, j, k) || 
							!world.isBlockNormalCube(i, j-1, k)  || 
							world.isBlockNormalCube(i, j+2, k))) {
				return false;
			}
		}
		if(l == 1)//-x
		{
			if(!foot && 
					(!world.isBlockNormalCube(i, j, k+1) || 
							!world.isBlockNormalCube(i, j, k-1) || 
							!world.isBlockNormalCube(i+1, j, k)  ||
							!world.isBlockNormalCube(i, j-1, k)  || 
							world.isBlockNormalCube(i, j+2, k))) {
				return false;
			} else if(foot && 
					(!world.isBlockNormalCube(i, j, k+1) || 
							!world.isBlockNormalCube(i, j, k-1) || 
							!world.isBlockNormalCube(i, j-1, k)  || 
							world.isBlockNormalCube(i, j+2, k))) {
				return false;
			}
		}
		if(l == 2)//-z
		{
			if(!foot && 
					(!world.isBlockNormalCube(i+1, j, k) || 
							!world.isBlockNormalCube(i-1, j, k) || 
							!world.isBlockNormalCube(i, j, k+1) || 
							!world.isBlockNormalCube(i, j-1, k)  || 
							world.isBlockNormalCube(i, j+2, k))) {
				return false;
			} else if(foot && 
					(!world.isBlockNormalCube(i+1, j, k) || 
							!world.isBlockNormalCube(i-1, j, k) || 
							!world.isBlockNormalCube(i, j-1, k)  || 
							world.isBlockNormalCube(i, j+2, k))) {
				return false;
			}
		}
		if(l == 3)//+x
		{
			if(!foot && 
					(!world.isBlockNormalCube(i, j, k+1) || 
							!world.isBlockNormalCube(i, j, k-1) || 
							!world.isBlockNormalCube(i-1, j, k)  ||
							!world.isBlockNormalCube(i, j-1, k)  || 
							world.isBlockNormalCube(i, j+2, k))) {
				return false;
			} else if(foot && 
					(!world.isBlockNormalCube(i, j, k+1) || 
							!world.isBlockNormalCube(i, j, k-1) || 
							!world.isBlockNormalCube(i, j-1, k)  || 
							world.isBlockNormalCube(i, j+2, k))) {
				return false;
			}
		}
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
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		//Minecraft mc = ModLoader.getMinecraftInstance();
		int i1 = world.getBlockMetadata(i, j, k);
		int j1 = getDirectionFromMetadata(i1);

		if(isBlockFootOfBed(i1))
		{
			if(world.getBlockId(i - headBlockToFootBlockMap[j1][0], j, k - headBlockToFootBlockMap[j1][1]) != blockID || !canStay(world, i, j, k,true,j1))
			{
				world.setBlock(i, j, k, 0);
			}
		}
		else
		{
			if(world.getBlockId(i + headBlockToFootBlockMap[j1][0], j, k + headBlockToFootBlockMap[j1][1]) != blockID || !canStay(world, i, j, k,false,j1))
			{
				world.setBlock(i, j, k, 0);
				if(!world.isRemote)
				{
					dropBlockAsItem(world, i, j, k, i1, 0);
				}
			}
		}
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntitySluice();
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{

	}
}
