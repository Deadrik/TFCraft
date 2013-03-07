package TFC.Blocks;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Core.AnvilReq;
import TFC.TileEntities.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
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
import net.minecraft.world.chunk.*;

public class BlockIngotPile extends BlockTerraContainer
{
	private int meta;
	public int damage;
	public int stack;
	private int xCoord;
	private int yCoord;
	private int zCoord;
	private World world;
	private int ingotpileId = TFCBlocks.IngotPileRenderId;
	public TileEntity TE;
	public boolean replacing = false;


	private Random random = new Random();
	public BlockIngotPile(int i,int tex)
	{
		super(i, Material.iron);
		this.blockIndexInTexture = tex;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		System.out.println("shifty");
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;

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
					System.out.println("no TE: "+i+", "+j+", "+k);
					world.setBlock(i, j, k, 0);
					return false;
				}
				if (!entityplayer.isSneaking() && tileentityingotpile.getStackInSlot(0)!=null){
					if (tileentityingotpile.getStackInSlot(0).stackSize > 0){
						System.out.println("before: "+stack);
						tileentityingotpile.injectContents(0,-1);
						System.out.println("after: "+stack);
					}
					world.spawnEntityInWorld(new EntityItem(world,tileentityingotpile.xCoord,
							tileentityingotpile.yCoord+1,tileentityingotpile.zCoord,new ItemStack(tileentityingotpile.getStackInSlot(0).getItem(),1,tileentityingotpile.getStackInSlot(0).getItemDamage())));
					if(tileentityingotpile.getStackInSlot(0).stackSize < 1){
						world.setBlock(i, j, k, 0);
					}
				}
				damage = tileentityingotpile.getStackInSlot(0).getItem().shiftedIndex - 16028 - 256;
				//tileentityingotpile.setType(damage);
				stack = tileentityingotpile.getStackInSlot(0).stackSize;
				ItemStack is = entityplayer.getCurrentEquippedItem();

			}

			return true;
		}
	}
	@Override
	public void onBlockEventReceived(World world,int i,int j,int k,int event,int param){
		if(event ==0){
			TileEntityIngotPile te = (TileEntityIngotPile)world.getBlockTileEntity(i, j, k);
			TE = (TileEntityIngotPile)world.getBlockTileEntity(i, j, k);
		}
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		super.onBlockAdded(world, i, j, k);
		this.world = Minecraft.getMinecraft().theWorld;
		TE = world.getBlockTileEntity(i,j,k);
		int x = i;
		int y = j;
		int z = k;
		if(Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z)!=null && world.getBlockTileEntity(x,y,z)!=null){
			//((TileEntityIngotPile)Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z)).setType(((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).getType());
			//((TileEntityIngotPile)Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z)).storage[0].stackSize=(((TileEntityIngotPile)world.getBlockTileEntity(x,y,z)).storage[0].stackSize);
		}
		
		if (((TileEntityIngotPile)world.getBlockTileEntity(i,j,k)).getType() != -1){
			//damage = ((EntityPlayer)entityliving).getItemInUse().getItem().shiftedIndex;
			//stack = 1;
			damage = ((TileEntityIngotPile)world.getBlockTileEntity(i,j,k)).getType();
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

		if (te.getStackInSlot(0)!=null){

			return AxisAlignedBB.getBoundingBox((double)par2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + ((te.getStackInSlot(0).stackSize + 7)/8)*0.125, (double)par4 + 1);
		}
		//else
		//{

		return AxisAlignedBB.getBoundingBox((double)par2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + 0.25, (double)par4 + 1);
		//}
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
		int meta = par1World.getBlockMetadata(par2, par3, par4);
        int direction = getDirectionFromMetadata(meta);
        TileEntityIngotPile te = (TileEntityIngotPile)par1World.getBlockTileEntity(par2, par3, par4);

		if (te.getStackInSlot(0)!=null){
            return AxisAlignedBB.getBoundingBox((double)par2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + ((te.getStackInSlot(0).stackSize + 7)/8)*0.125, (double)par4 + 1);
		}
		else
			return AxisAlignedBB.getBoundingBox((double)par2, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + 0.25, (double)par4 + 1);
    }
	
	@Override
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
		}*/
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

	public int getDamage(World world,TileEntityIngotPile tt)
	{
		if(tt != null)
		{
			TileEntityIngotPile Te = ((TileEntityIngotPile)world.getBlockTileEntity(tt.xCoord, tt.yCoord, tt.zCoord));
			return Te != null ? Te.getType() != -1 ? Te.getType() : 0 : 0;
		}
		return 0;
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
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving)
	{
		super.onBlockPlacedBy(world,i,j,k,entityliving);
		meta = world.getBlockMetadata(i, j, k);
		xCoord = i;
		yCoord = j;
		zCoord = k;
		int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
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
		world.setBlockMetadataWithNotify(i, j, k, byte0);
		//damage = ((EntityPlayer)entityliving).getItemInUse().getItem().shiftedIndex;
		//stack = 1;
		damage = ((TileEntityIngotPile)world.getBlockTileEntity(i,j,k)).type;
		//damage = ((TileEntityIngotPile)(world.getBlockTileEntity(i,j,k))).getStackInSlot(0).getItem().shiftedIndex - 16028 - 256;
		//this.createTileEntity(world, meta);
	}
	@Override
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityIngotPile var5 = (TileEntityIngotPile)par1World.getBlockTileEntity(par2, par3, par4);
		if(!replacing){
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
							var12 = new EntityItem(par1World, (double)((float)par2 + var8), (double)((float)par3 + var9), (double)((float)par4 + var10), new ItemStack(var7.itemID, var11, var7.getItemDamage()));
							float var13 = 0.05F;
							var12.motionX = (double)((float)this.random.nextGaussian() * var13);
							var12.motionY = (double)((float)this.random.nextGaussian() * var13 + 0.2F);
							var12.motionZ = (double)((float)this.random.nextGaussian() * var13);

							if (var7.hasTagCompound())
							{
								var12.func_92014_d().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
							}
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
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		TileEntityIngotPile tE = new TileEntityIngotPile();
		return tE;
	}
}
