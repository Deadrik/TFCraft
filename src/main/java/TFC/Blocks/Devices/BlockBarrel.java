package TFC.Blocks.Devices;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.API.IMultipleBlock;
import TFC.API.IPipeConnectable;
import TFC.API.Constant.Global;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Textures;
import TFC.Items.ItemBarrels;
import TFC.TileEntities.TileEntityBarrel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBarrel extends BlockTerraContainer implements IMultipleBlock, IPipeConnectable
{
	private final Random random = new Random();
	String[] woodNames;
	
	public BlockBarrel()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFCDevices);
		this.setBlockBounds(0.1f, 0, 0.1f, 0.9f, 1, 0.9f);
		woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0,16);
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/BarrelHoop");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(side == 0 || side == 1)
			return TFC_Textures.InvisibleTexture;
		else
			return this.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < woodNames.length; i++)
			par3List.add(new ItemStack(this, 1, i));
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
	public int getRenderType()
	{
		return TFCBlocks.barrelRenderId;
	}

	/**
	 * Called whenever the block is added into the world. Args: world, x, y, z
	 */
	@Override
	public void onBlockAdded(World par1World, int par2, int par3, int par4)
	{
		super.onBlockAdded(par1World, par2, par3, par4);
	}

	@Override
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion)
	{
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase player, ItemStack is)
	{
		super.onBlockPlacedBy(world, i, j, k, player, is);
		TileEntityBarrel teb = null;
		TileEntity te = world.getTileEntity(i, j, k);
		if (te != null && is.hasTagCompound() && te instanceof TileEntityBarrel)
		{
			teb = (TileEntityBarrel) te;
			teb.readFromItemNBT(is.getTagCompound());
			world.markBlockForUpdate(i, j, k);
			//TerraFirmaCraft.proxy.sendCustomPacket(teb.createUpdatePacket());
		}
	}

	/**
	 * Returns the block texture based on the side being looked at.  Args: side
	 */
	/*@Override
	public Icon getBlockTextureFromSideAndMetadata(int par1)
	{
		return par1 == 1 ? this.blockIndexInTexture - 1 : (par1 == 0 ? this.blockIndexInTexture - 1 : (par1 == 3 ? this.blockIndexInTexture + 1 : this.blockIndexInTexture));
	}*/

	/**
	 * Checks to see if its valid to put this block at the specified coordinates. Args: world, x, y, z
	 */
	@Override
	public boolean canPlaceBlockAt(World par1World, int par2, int par3, int par4)
	{
		return true;
	}

	@Override
	protected ItemStack createStackedBlock(int par1)
	{
		int j = 0;
		String s = this.getUnlocalizedName();
		for(int i = 0; i < woodNames.length;i++)
			j = s.substring(s.indexOf("l",s.length()))==((ItemBarrels)(TFCItems.Barrel)).MetaNames[i]?i:0;
		return new ItemStack(TFCItems.Barrel, 1, j);
	}

	public class BarrelEntity extends Entity
	{
		public int fuse;
		World world;
		public BarrelEntity(World par1World)
		{
			super(par1World);
			this.fuse = 60;
			this.preventEntitySpawning = true;
			this.setSize(0.98F, 0.98F);
			this.yOffset = this.height / 2.0F;
		}

		public BarrelEntity(World par1World, double par2, double par4, double par6)
		{
			this(par1World);
			this.setPosition(par2, par4, par6);
			world = par1World;
			float f = (float)(Math.random() * Math.PI * 2.0D);
			this.motionX = -((float)Math.sin(f)) * 0.02F;
			this.motionY = 0.20000000298023224D;
			this.motionZ = -((float)Math.cos(f)) * 0.02F;
			this.prevPosX = par2;
			this.prevPosY = par4;
			this.prevPosZ = par6;
		}

		@Override
		public void onUpdate(){
			fuse--;
			world.playSoundAtEntity(this, "random.fuse", 1.0F, 1.0F);
			if(fuse == 0){
				explode();
			}
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, new Random().nextFloat(), 1.0D, new Random().nextFloat());
		}

		private void explode()
		{
			float f = 64.0F;
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
			setDead();
		}

		@Override
		protected void entityInit()
		{
		}

		@Override
		protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound)
		{
			this.fuse = par1NBTTagCompound.getByte("Fuse");
		}

		@Override
		protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound)
		{
			par1NBTTagCompound.setByte("Fuse", (byte)this.fuse);
		}
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, Block block)
	{
		if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
		{
			TileEntityBarrel TE = (TileEntityBarrel)par1World.getTileEntity(par2,par3,par4);
			if(TE.liquidLevel == 256 && TE.Type == 4 && !TE.getSealed()){
				TE.setSealed();
				BarrelEntity BE = new BarrelEntity(par1World,par2,par3,par4);
				par1World.spawnEntityInWorld(BE);
				par1World.playSoundAtEntity(BE, "random.fuse", 1.0F, 1.0F);
				//float f = 16.0F;
				//EI.worldObj.createExplosion(EI, EI.posX, EI.posY, EI.posZ, f, true);
				//par1World.setBlockToAir(par2, par3, par4);
			}
		}
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void breakBlock(World world, int i, int j, int k, Block block, int par6)
	{
		TileEntityBarrel te = (TileEntityBarrel)world.getTileEntity(i, j, k);

		if (te != null)
		{
			ItemStack is = new ItemStack(Item.getItemFromBlock(block), 1,par6);
			NBTTagCompound nbt = writeBarrelToNBT(te);
			is.setTagCompound(nbt);
			EntityItem ei = new EntityItem(world,i,j,k,is);
			world.spawnEntityInWorld(ei);

			for(int s = 0; s < te.getSizeInventory(); ++s)
				te.setInventorySlotContents(s, null);
		}
		super.breakBlock(world, i, j, k, block, par6);
	}

	@Override
	protected void dropBlockAsItem(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
	{
	}

	public NBTTagCompound writeBarrelToNBT(TileEntityBarrel te)
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setInteger("liqLev", te.liquidLevel);
		nbt.setInteger("type", te.Type);
		nbt.setBoolean("sealed", te.getSealed());

		NBTTagList nbttaglist = new NBTTagList();
		nbttaglist = new NBTTagList();
		NBTTagCompound nbttagcompound1 = new NBTTagCompound();
		if(te.getStackInSlot(0)!=null){
			nbttagcompound1.setByte("Slot", (byte)0);
			te.getStackInSlot(0).writeToNBT(nbttagcompound1);
			nbttaglist.appendTag(nbttagcompound1);
		}

		nbttagcompound1 = new NBTTagCompound();
		if(te.getStackInSlot(1)!=null){
			nbttagcompound1.setByte("Slot", (byte)1);
			te.getStackInSlot(1).writeToNBT(nbttagcompound1);
			nbttaglist.appendTag(nbttagcompound1);
		}

		nbt.setTag("Items", nbttaglist);

		return nbt;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			//((NetworkTileEntity)world.getTileEntity(x,y,z)).validate();
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else
		{

			if(world.getTileEntity(x, y, z) != null){
				TileEntityBarrel TeBarrel = (TileEntityBarrel)(world.getTileEntity(x, y, z));
				if(TeBarrel.liquidLevel == 256 && TeBarrel.Type == 4 && TeBarrel.getSealed()){
					List<Entity> list = world.getEntitiesWithinAABB(BarrelEntity.class, AxisAlignedBB.getBoundingBox(x,y,z,x+1,y+1,z+1));
					for(Entity entity : list){
						entity.setDead();
					}
					TeBarrel.setUnsealed("killing fuse");
					return true;
				}
				if (TeBarrel.getSealed()||entityplayer.isSneaking()){
					return false;
				}
				entityplayer.openGui(TerraFirmaCraft.instance, 35, world, x, y, z);
				return true;
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TileEntityBarrel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	public Block getBlockTypeForRender()
	{
		return TFCBlocks.Planks;
	}

	@Override
	public boolean canConnectTo(IBlockAccess world, int desiredFace, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean feed(IBlockAccess world, int fedFace, int x, int y, int z,boolean isLiquid)
	{
		TileEntityBarrel te = ((TileEntityBarrel)(world.getTileEntity(x, y, z)));
		if(te != null && te.liquidLevel < 256)
		{
			te.liquidLevel+=4;
			te.Type = 1;
			te.updateGui();
			return true;
		}
		return false;
	}

	@Override
	public int hasToBeOnlyOption()
	{
		return 0;
	}

	@Override
	public int getSide(IBlockAccess world, int x, int y, int z)
	{
		return -1;
	}

	@Override
	public int getFinalBit(IBlockAccess world, int x, int y, int z)
	{
		return 0;
	}
}
