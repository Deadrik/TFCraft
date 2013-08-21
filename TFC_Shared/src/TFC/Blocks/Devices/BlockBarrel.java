package TFC.Blocks.Devices;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Textures;
import TFC.Items.ItemBarrels;
import TFC.TileEntities.NetworkTileEntity;
import TFC.TileEntities.TileEntityBarrel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBarrel extends BlockTerraContainer
{
	private final Random random = new Random();

	public BlockBarrel(int par1)
	{
		super(par1, Material.wood);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setBlockBounds(0.1f, 0, 0.1f, 0.9f, 1, 0.9f);
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/BarrelHoop");
	}

	@Override
	public Icon getIcon(int side, int meta)
	{
		if(side == 0 || side == 1) 
		{
			return TFC_Textures.InvisibleTexture;
		} 
		else 
		{
			return this.blockIcon;
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < 16; i++) {
			par3List.add(new ItemStack(this, 1, i));
		}
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
	public void onBlockDestroyedByExplosion(World par1World, int par2, int par3, int par4, Explosion par5Explosion) {


	}
	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLiving, ItemStack is)
	{

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
		for(int i = 0; i < ((ItemBarrels)(TFCItems.Barrel)).MetaNames.length;i++){
			j = s.substring(s.indexOf("l",s.length()))==((ItemBarrels)(TFCItems.Barrel)).MetaNames[i]?i:0;
		}


		return new ItemStack(TFCItems.Barrel, 1, j);
	}

	public class BarrelEntity extends Entity{
		public int fuse;
		public BarrelEntity(World par1World)
		{
			super(par1World);
			this.fuse = 15;
			this.preventEntitySpawning = true;
			this.setSize(0.98F, 0.98F);
			this.yOffset = this.height / 2.0F;
		}

		public BarrelEntity(World par1World, double par2, double par4, double par6)
		{
			this(par1World);
			this.setPosition(par2, par4, par6);
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
			if(fuse == 0){
				explode();
			}
			worldObj.spawnParticle("smoke", posX, posY + 0.5D, posZ, new Random().nextFloat(), 1.0D, new Random().nextFloat());
		}
		private void explode()
		{
			float f = 16.0F;
			this.worldObj.createExplosion(this, this.posX, this.posY, this.posZ, f, true);
			setDead();
		}
		@Override
		protected void entityInit() {
			// TODO Auto-generated method stub

		}
		@Override
		protected void readEntityFromNBT(NBTTagCompound par1NBTTagCompound) {
			this.fuse = par1NBTTagCompound.getByte("Fuse");

		}
		@Override
		protected void writeEntityToNBT(NBTTagCompound par1NBTTagCompound) {
			par1NBTTagCompound.setByte("Fuse", (byte)this.fuse);

		}
	}

	@Override
	public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
	{
		if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
		{
			TileEntityBarrel TE = (TileEntityBarrel)par1World.getBlockTileEntity(par2,par3,par4);
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
	public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		TileEntityBarrel var5 = (TileEntityBarrel)par1World.getBlockTileEntity(par2, par3, par4);

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
		}

		super.breakBlock(par1World, par2, par3, par4, par5, par6);
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		//Random R = new Random();
		//dropBlockAsItem_do(world, i, j, k, new ItemStack(idDropped(0,R,l), 1, l+13));

		//super.harvestBlock(world, entityplayer, i, j, k, l);
		dropBlockAsItem_do(world, i, j, k, new ItemStack(this, 1, l));
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			((NetworkTileEntity)world.getBlockTileEntity(x,y,z)).validate();
			return true;
		}
		else
		{
			if(world.getBlockTileEntity(x, y, z) != null){
				TileEntityBarrel TeBarrel = (TileEntityBarrel)(world.getBlockTileEntity(x, y, z));
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
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityBarrel();
	}

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
}
