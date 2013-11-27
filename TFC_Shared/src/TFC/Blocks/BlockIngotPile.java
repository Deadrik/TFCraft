package TFC.Blocks;

import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.TileEntities.NetworkTileEntity;
import TFC.TileEntities.TileEntityIngotPile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockIngotPile extends BlockTerraContainer
{
	private Random random = new Random();
	public BlockIngotPile(int i)
	{
		super(i, Material.iron);
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{

	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;
		if(equippedItem != null)
		{
			itemid = entityplayer.getCurrentEquippedItem().itemID;
		} else {
			itemid = 0;
		}

		if(world.isRemote)
		{
			((NetworkTileEntity)world.getBlockTileEntity(i,j,k)).validate();
			return true;
		} 
		else
		{

			if((TileEntityIngotPile)world.getBlockTileEntity(i, j, k)!=null)
			{

				TileEntityIngotPile tileentityingotpile;
				tileentityingotpile = (TileEntityIngotPile)world.getBlockTileEntity(i, j, k);

				if (tileentityingotpile.getStackInSlot(0)==null){
					world.setBlock(i, j, k, 0);
					return false;
				}
				if (!entityplayer.isSneaking() && tileentityingotpile.getStackInSlot(0)!=null){
					if (tileentityingotpile.getStackInSlot(0).stackSize > 0){
						tileentityingotpile.injectContents(0,-1);
					}
					world.spawnEntityInWorld(new EntityItem(world,tileentityingotpile.xCoord,
							tileentityingotpile.yCoord+1,tileentityingotpile.zCoord,new ItemStack(tileentityingotpile.getStackInSlot(0).getItem(),1,tileentityingotpile.getStackInSlot(0).getItemDamage())));
					world.notifyBlockOfNeighborChange(i, j+1, k, this.blockID);
					
					if (tileentityingotpile.getStackInSlot(0).stackSize < 1) {
						world.setBlockToAir(i, j, k);
					}
					tileentityingotpile.broadcastPacketInRange(tileentityingotpile.createUpdatePacket());
				}
				//damage = tileentityingotpile.getStackInSlot(0).getItem().itemID - 16028 - 256;
				//stack = tileentityingotpile.getStackInSlot(0).stackSize;
				ItemStack is = entityplayer.getCurrentEquippedItem();

			}
			return true;
		}
	}
	
	public void combineIngotsDown(World world, int i, int j, int k)
	{
		TileEntityIngotPile teip = (TileEntityIngotPile)world.getBlockTileEntity(i, j, k);
		TileEntityIngotPile teipBottom = (TileEntityIngotPile)world.getBlockTileEntity(i, j-1, k);
		
		int bottomSize = teipBottom.getStackInSlot(0).stackSize;
		int topSize = teip.getStackInSlot(0).stackSize;
		
		if(bottomSize < 64)
		{
			bottomSize = bottomSize + topSize;
			int m2 = 0;
			if(bottomSize > 64)
			{
				m2 = bottomSize - 64;
				bottomSize = 64;
			}
			teipBottom.storage[0] = 
					new ItemStack(teipBottom.storage[0].getItem(),
							bottomSize,
							teipBottom.storage[0].getItemDamage());

			if(m2 > 0)
			{
				teip.injectContents(0, m2-topSize);
				world.notifyBlockOfNeighborChange(i, j+1, k, blockID);
				teip.broadcastPacketInRange(teip.createUpdatePacket());
			}
			else
				world.setBlockToAir(i, j, k);
		}
	}

	public void combineIngotsUp(World world, int i, int j, int k)
	{
		TileEntityIngotPile teip = (TileEntityIngotPile)world.getBlockTileEntity(i, j+1, k);
		TileEntityIngotPile teipBottom = (TileEntityIngotPile)world.getBlockTileEntity(i, j, k);
		
		int bottomSize = teipBottom.getStackInSlot(0).stackSize;
		int topSize = teip.getStackInSlot(0).stackSize;
		
		if(bottomSize < 64)
		{
			bottomSize = bottomSize + topSize;
			int m2 = 0;
			if(bottomSize > 64)
			{
				m2 = bottomSize - 64;
				bottomSize = 64;
			}
			teipBottom.storage[0] = 
					new ItemStack(teipBottom.storage[0].getItem(),
							bottomSize,
							teipBottom.storage[0].getItemDamage());

			if(m2 > 0)
			{
				teip.injectContents(0, m2-topSize);
				world.notifyBlockOfNeighborChange(i, j+2, k, blockID);
				teip.broadcastPacketInRange(teip.createUpdatePacket());
			}
			else
				world.setBlockToAir(i, j+1, k);
		}
	}
	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		int direction = getDirectionFromMetadata(meta);
		TileEntityIngotPile te = (TileEntityIngotPile)par1World.getBlockTileEntity(par2, par3, par4);

		if (te != null && te.getStackInSlot(0) != null)
		{

			return AxisAlignedBB.getBoundingBox(par2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, par3 + ((te.getStackInSlot(0).stackSize + 7)/8)*0.125, (double)par4 + 1);
		}
		//else
		//{

		return AxisAlignedBB.getBoundingBox(par2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, par3 + 0.25, (double)par4 + 1);
		//}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		int direction = getDirectionFromMetadata(meta);
		TileEntityIngotPile te = (TileEntityIngotPile)par1World.getBlockTileEntity(par2, par3, par4);

		if (te.getStackInSlot(0)!=null){
			return AxisAlignedBB.getBoundingBox(par2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, par3 + ((te.getStackInSlot(0).stackSize + 7)/8)*0.125, (double)par4 + 1);
		} else {
			return AxisAlignedBB.getBoundingBox(par2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, par3 + 0.25, (double)par4 + 1);
		}
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
		int direction = getDirectionFromMetadata(meta);
		TileEntityIngotPile te = (TileEntityIngotPile)par1IBlockAccess.getBlockTileEntity(par2, par3, par4);

		if (te.getStackInSlot(0)!=null){
			this.setBlockBounds(0f, 0f, 0f, 1f, (float) (((te.getStackInSlot(0).stackSize + 7)/8)*0.125), 1f);
		} else {
			this.setBlockBounds(0f, 0f, 0f, 0f, 0.25f, 0f);
		}

	}
	/*@Override
	public int getBlockTextureFromSideAndMetadata(int i, int j)
	{
		int meta = getAnvilTypeFromMeta(j);
		int offset = meta;

		return blockIndexInTexture + getDamage(world,(TileEntityIngotPile)TE);
		/*if(i %2 == 0) {
			return blockIndexInTexture+ 1 + offset;
		} else if(i == 1) {
			return blockIndexInTexture+ 1 + offset;
		} else if(i == 2) {
			return blockIndexInTexture + offset;
		} else if(i == 3) {
			return blockIndexInTexture + offset;
		} else if(i == 4) {
			return blockIndexInTexture + offset;
		} else {
			return blockIndexInTexture + offset;
		}
	}*/

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addBlockHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
		return true;
	}

	@Override
	public int getRenderType()
	{
		return 22;//TFCBlocks.IngotPileRenderId;//ingotpileId;
	}

	public int getStack(World world,TileEntityIngotPile tt){
		TileEntityIngotPile Te = ((TileEntityIngotPile)world.getBlockTileEntity(tt.xCoord, tt.yCoord, tt.zCoord));

		return Te != null ? Te.getStackInSlot(0) != null ? Te.getStackInSlot(0).stackSize : 0 : 0;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{		

	}
	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is)
	{
		super.onBlockPlacedBy(world,i,j,k,entityliving, is);
		int meta = world.getBlockMetadata(i, j, k);

		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		byte byte0 = 0;
		if(l == 0)//+z
		{
			byte0 = 8;
		}
		if(l == 1)//-x
		{
			byte0 = 0;
		}
		if(l == 2)//-z
		{
			byte0 = 8;
		}
		if(l == 3)//+x
		{
			byte0 = 0;
		}
		byte0 += meta;
		world.setBlockMetadataWithNotify(i, j, k, byte0, 0x2);;
	}
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityIngotPile var5 = (TileEntityIngotPile)par1World.getBlockTileEntity(par2, par3, par4);
		if (var5 != null)
		{
			for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
			{
				ItemStack var7 = var5.getStackInSlot(var6);

				if (var7 != null)
				{
					float var8 = this.random.nextFloat() * 0.8F + 0.1F;
					float var9 = this.random.nextFloat() * 0.8F + 0.1F;
					EntityItem var12;

					for (float var10 = this.random.nextFloat() * 0.8F + 0.1F; var7.stackSize > 0; par1World.spawnEntityInWorld(var12))
					{
						int var11 = this.random.nextInt(21) + 10;

						if (var11 > var7.stackSize)
						{
							var11 = var7.stackSize;
						}

						var7.stackSize -= var11;
						var12 = new EntityItem(par1World, par2 + var8, par3 + var9, par4 + var10, new ItemStack(var7.itemID, var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (float)this.random.nextGaussian() * var13;
						var12.motionY = (float)this.random.nextGaussian() * var13 + 0.2F;
						var12.motionZ = (float)this.random.nextGaussian() * var13;

						if (var7.hasTagCompound())
						{
							var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
						}
					}
				}
			}
			super.breakBlock(par1World, par2, par3, par4, par5, par6);}
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public static int getAnvilTypeFromMeta(int j)
	{
		int l = 7;
		int k = j & l;
		return k;
	}

	public static int getDirectionFromMetadata(int i)
	{
		int d = i >> 3;

		if (d == 1) {
			return 1;
		} else {
			return 0;
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1) 
	{
		return new TileEntityIngotPile();
	}
	
	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int id)
	{
		if(!world.isRemote)
		{
			if(!world.isBlockOpaqueCube(i, j-1, k))
			{
				if(world.getBlockId(i, j-1, k) == this.blockID && 
						((TileEntityIngotPile)world.getBlockTileEntity(i, j, k)).storage[0].itemID == 
						((TileEntityIngotPile)world.getBlockTileEntity(i, j-1, k)).storage[0].itemID)
				{
					combineIngotsDown(world, i, j, k);
				}
				else if(world.getBlockId(i, j+1, k) == this.blockID && 
						((TileEntityIngotPile)world.getBlockTileEntity(i, j, k)).storage[0].itemID == 
						((TileEntityIngotPile)world.getBlockTileEntity(i, j+1, k)).storage[0].itemID)
				{
					combineIngotsUp(world, i, j, k);
				}
				else
				{
					((TileEntityIngotPile)world.getBlockTileEntity(i, j, k)).ejectContents();
					world.setBlock(i, j, k, 0);
					return;
				}
			}

		}
	}
}
