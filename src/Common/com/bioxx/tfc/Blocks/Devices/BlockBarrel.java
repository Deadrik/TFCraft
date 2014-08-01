package com.bioxx.tfc.Blocks.Devices;

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
import net.minecraftforge.fluids.FluidContainerRegistry;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Items.ItemBarrels;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBarrel extends BlockTerraContainer
{
	private final Random random = new Random();
	private String[] woodNames;
	protected static IIcon blockIcon;

	public BlockBarrel()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFCDevices);
		this.setBlockBounds(0.1f, 0, 0.1f, 0.9f, 1, 0.9f);
		woodNames = Global.WOOD_ALL;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		BlockBarrel.blockIcon = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/BarrelHoop");
		TFC_Textures.GuiSolidStorage = iconRegisterer.registerIcon(Reference.ModID + ":" + "button_barrel_solid");
		TFC_Textures.GuiLiquidStorage = iconRegisterer.registerIcon(Reference.ModID + ":" + "button_barrel_liquid");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		//When doing inventory item render, we increase the side by 10 so that we can draw the hoops instead of planks
		if(side >= 10)
		{
			side-=10;
			if(side == 0 || side == 1)
				return TFC_Textures.InvisibleTexture;
			else
				return BlockBarrel.blockIcon;
		}
		if(meta<16)
			return TFCBlocks.Planks.getIcon(side, meta);
		return TFCBlocks.Planks2.getIcon(side, meta-16);


	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		if(side == 0 || side == 1)
			return TFC_Textures.InvisibleTexture;
		else
			return BlockBarrel.blockIcon;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List) 
	{
		for(int i = 0; i < woodNames.length; i++)
			par3List.add(new ItemStack(this, 1, i));
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
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
		TEBarrel teb = null;
		TileEntity te = world.getTileEntity(i, j, k);
		if (te != null && is.hasTagCompound() && te instanceof TEBarrel)
		{
			teb = (TEBarrel) te;
			teb.readFromItemNBT(is.getTagCompound());
			world.markBlockForUpdate(i, j, k);
		}
	}

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
			j = s.substring(s.indexOf("l", s.length())) == ((ItemBarrels)(TFCItems.Barrel)).MetaNames[i] ? i : 0;
		return new ItemStack(TFCItems.Barrel, 1, j);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (world.isBlockIndirectlyGettingPowered(x, y, z))
		{
			TEBarrel TE = (TEBarrel)world.getTileEntity(x, y, z);
			if(TE.getGunPowderCount() >= 256 && TE.getSealed())
			{
				BarrelEntity BE = new BarrelEntity(world, x, y, z);
				world.spawnEntityInWorld(BE);
				world.playSoundAtEntity(BE, "random.fuse", 1.0F, 1.0F);
				//float f = 16.0F;
				//EI.worldObj.createExplosion(EI, EI.posX, EI.posY, EI.posZ, f, true);
				//world.setBlockToAir(x, y, z);
			}
		}
	}

	/**
	 * Called whenever the block is removed.
	 */
	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		TEBarrel te = (TEBarrel)world.getTileEntity(x, y, z);

		if (te != null)
		{
			if(te.getSealed())
			{
				ItemStack is = new ItemStack(Item.getItemFromBlock(block), 1, te.barrelType);
				NBTTagCompound nbt = writeBarrelToNBT(te);
				is.setTagCompound(nbt);
				EntityItem ei = new EntityItem(world,x,y,z,is);
				world.spawnEntityInWorld(ei);

				for(int s = 0; s < te.getSizeInventory(); ++s)
					te.setInventorySlotContents(s, null);
			}
			else
			{
				ItemStack is = new ItemStack(Item.getItemFromBlock(block), 1, te.barrelType);
				EntityItem ei = new EntityItem(world,x,y,z,is);
				world.spawnEntityInWorld(ei);
			}
		}
		super.breakBlock(world, x, y, z, block, meta);
	}

	@Override
	protected void dropBlockAsItem(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
	{
	}

	public NBTTagCompound writeBarrelToNBT(TEBarrel te)
	{
		NBTTagCompound nbt = new NBTTagCompound();

		NBTTagCompound fluidNBT = new NBTTagCompound();
		if(te.fluid != null)
			te.fluid.writeToNBT(fluidNBT);
		nbt.setTag("fluidNBT", fluidNBT);
		nbt.setInteger("barrelType", te.barrelType);
		nbt.setBoolean("sealed", te.getSealed());

		NBTTagList nbttaglist = new NBTTagList();
		for(int i = 0; i < te.storage.length; i++)
		{
			if(te.storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				te.storage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Items", nbttaglist);

		return nbt;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		if (world.isRemote)
		{
			world.markBlockForUpdate(x, y, z);
			return true;
		}
		else
		{
			if (player.isSneaking())
			{
				return false;
			}

			if(world.getTileEntity(x, y, z) != null)
			{
				TEBarrel te = (TEBarrel)(world.getTileEntity(x, y, z));
				if(te.getGunPowderCount() >= 256 && te.getSealed())
				{
					List<Entity> list = world.getEntitiesWithinAABB(BarrelEntity.class, AxisAlignedBB.getBoundingBox(x, y, z, x + 1, y + 1, z + 1));
					for(Entity entity : list)
					{
						entity.setDead();
					}
					return true;
				}

				if (!te.getSealed()) {
					ItemStack equippedItem = player.getCurrentEquippedItem();
					if(FluidContainerRegistry.isFilledContainer(equippedItem) && !te.getSealed())
					{
						ItemStack is = te.addLiquid(player.getCurrentEquippedItem());
						player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
						return true;
					}
					else if(FluidContainerRegistry.isEmptyContainer(equippedItem))
					{
						ItemStack is = te.removeLiquid(player.getCurrentEquippedItem());
						player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
						return true;
					}
					else if ((equippedItem != null && equippedItem.getItem() == TFCItems.PotteryJug && equippedItem.getItemDamage() == 1)) {
						ItemStack is = te.removeLiquid(equippedItem);
						player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
						return true;
					}
				}

				if(te.getInvCount() == 0)
					player.openGui(TerraFirmaCraft.instance, 35, world, x, y, z);
				else
					player.openGui(TerraFirmaCraft.instance, 36, world, x, y, z);
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
		return new TEBarrel();
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
		public void onUpdate()
		{
			fuse--;
			world.playSoundAtEntity(this, "random.fuse", 1.0F, 1.0F);
			if(fuse == 0)
				explode();
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
}
