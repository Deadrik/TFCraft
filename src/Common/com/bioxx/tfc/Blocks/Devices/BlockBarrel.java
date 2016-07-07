package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import net.minecraftforge.fluids.FluidContainerRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidContainerItem;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.Entities.EntityBarrel;
import com.bioxx.tfc.Items.ItemBlocks.ItemBarrels;
import com.bioxx.tfc.Items.ItemBlocks.ItemLargeVessel;
import com.bioxx.tfc.Items.Tools.ItemCustomBucketMilk;
import com.bioxx.tfc.TileEntities.TEBarrel;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFCOptions;
import com.bioxx.tfc.api.Constant.Global;

public class BlockBarrel extends BlockTerraContainer
{
	private String[] woodNames;

	public BlockBarrel()
	{
		super(Material.wood);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
		this.setBlockBounds(0.1f, 0, 0.1f, 0.9f, 1, 0.9f);
		woodNames = Global.WOOD_ALL;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "wood/BarrelHoop");
		TFC_Textures.guiSolidStorage = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "button_barrel_solid");
		TFC_Textures.guiLiquidStorage = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "button_barrel_liquid");
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		//When doing inventory item render, we increase the side by 10 so that we can draw the hoops instead of planks
		if(side >= 10)
		{
			side-=10;
			if(side == 0 || side == 1)
				return TFC_Textures.invisibleTexture;
			else
				return blockIcon;
		}
		if(meta<16)
			return TFCBlocks.planks.getIcon(side, meta);
		return TFCBlocks.planks2.getIcon(side, meta-16);


	}

	@Override
	public IIcon getIcon(IBlockAccess access, int x, int y, int z, int side)
	{
		if(side == 0 || side == 1)
			return TFC_Textures.invisibleTexture;
		else
			return blockIcon;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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

	@Override
	public void onBlockPreDestroy(World world, int x, int y, int z, int meta) 
	{
		if (!world.isRemote)
		{
			TEBarrel te = (TEBarrel)world.getTileEntity(x, y, z);
			if (te != null && te.shouldDropItem && world.getGameRules().getGameRuleBooleanValue("doTileDrops"))
			{
				if(te.getSealed())
				{
					ItemStack is = new ItemStack(Item.getItemFromBlock(this), 1, te.barrelType);
					NBTTagCompound nbt = writeBarrelToNBT(te);
					is.setTagCompound(nbt);
					EntityItem ei = new EntityItem(world,x,y,z,is);
					world.spawnEntityInWorld(ei);

					te.fluid = null; //Drain Liquid before we clear out the inventory to prevent dupes.

					for(int s = 0; s < te.getSizeInventory(); ++s)
						te.setInventorySlotContents(s, null);
				}
				else
				{
					ItemStack is = new ItemStack(Item.getItemFromBlock(this), 1, te.barrelType);
					EntityItem ei = new EntityItem(world,x,y,z,is);
					world.spawnEntityInWorld(ei);
				}
			}
		}
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		// Order is reverse of the vanilla method. I'm assuming this is so the TE stuff works? -Kitty
		onBlockDestroyedByExplosion(world, x, y, z, explosion);
		world.setBlockToAir(x, y, z);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion exp)
	{
		if (world.getTileEntity(x, y, z) instanceof TEBarrel)
		{
			TEBarrel te = (TEBarrel) world.getTileEntity(x, y, z);
			// Only barrels, no large vessels. Requires at least 12 gunpowder for minimum blast strength of 1.
			if (this == TFCBlocks.barrel && te != null && te.getGunPowderCount() >= 12 && te.getSealed())
			{
				spawnPowderKeg(world, x, y, z, te, true);
				return; // Don't call super if we're going to explode so the barrel is properly destroyed.
			}
		}

		super.onBlockDestroyedByExplosion(world, x, y, z, exp);
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
	public boolean canDropFromExplosion(Explosion exp)
	{

		return true;
	}

	@Override
	protected ItemStack createStackedBlock(int par1)
	{
		int j = 0;
		String s = this.getUnlocalizedName();
		for(int i = 0; i < woodNames.length;i++)
			j = s.substring(s.indexOf('l', s.length())) == ((ItemBarrels) (TFCItems.barrel)).metaNames[i] ? i : 0;
		return new ItemStack(TFCItems.barrel, 1, j);
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (world.getTileEntity(x, y, z) instanceof TEBarrel)
		{
			TEBarrel te = (TEBarrel) world.getTileEntity(x, y, z);
			// Only barrels, no large vessels. Requires at least 12 gunpowder for minimum blast strength of 1.
			if (this == TFCBlocks.barrel && te != null && te.getGunPowderCount() >= 12 && te.getSealed())
			{
				boolean fireNearby = false;
				if (world.getBlock(x - 1, y, z) instanceof BlockFire)
					fireNearby = true;
				if (world.getBlock(x + 1, y, z) instanceof BlockFire)
					fireNearby = true;
				if (world.getBlock(x, y, z - 1) instanceof BlockFire)
					fireNearby = true;
				if (world.getBlock(x, y, z + 1) instanceof BlockFire)
					fireNearby = true;

				if (fireNearby || world.isBlockIndirectlyGettingPowered(x, y, z))
				{
					spawnPowderKeg(world, x, y, z, te, false);
					world.setBlockToAir(x, y, z);
				}
			}
		}
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
		nbt.setInteger("SealTime", te.sealtime);
		nbt.setBoolean("Sealed", te.getSealed());

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
		if(nbttaglist.tagCount() > 0)
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

			if (world.getTileEntity(x, y, z) instanceof TEBarrel)
			{
				TEBarrel te = (TEBarrel) world.getTileEntity(x, y, z);

				if (this == TFCBlocks.barrel && te.getSealed() && te.getGunPowderCount() >= 12
						&& player.getCurrentEquippedItem() != null && player.getCurrentEquippedItem().getItem() instanceof ItemFlintAndSteel)
				{
					spawnPowderKeg(world, x, y, z, te, false);
					world.setBlockToAir(x, y, z);
					return true;
				}

				if(!handleInteraction(player, te))
				{
					if(te.getFluidLevel() > 0 || te.getInvCount() == 0)
						player.openGui(TerraFirmaCraft.instance, 35, world, x, y, z);
					else
						player.openGui(TerraFirmaCraft.instance, 36, world, x, y, z);
					return true;
				}
				else return true;
			}
		}
		return false;
	}

	protected boolean handleInteraction(EntityPlayer player, TEBarrel te) 
	{
		if (!te.getSealed() && te.getInvCount() <= 1 && !te.getWorldObj().isRemote) 
		{
			ItemStack equippedItem = player.getCurrentEquippedItem();
			if(equippedItem == null)
				return false;

			if((FluidContainerRegistry.isFilledContainer(equippedItem)
					|| equippedItem.getItem() instanceof IFluidContainerItem && ((IFluidContainerItem)equippedItem.getItem()).getFluid(equippedItem) != null)
					&& !te.getSealed())
			{
				ItemStack tmp = equippedItem.copy();
				tmp.stackSize = 1;
				ItemStack is = te.addLiquid(tmp);

				// If we cannot add the liquid to the barrel, open the interface.
				if (ItemStack.areItemStacksEqual(equippedItem, is))
				{
					return false;
				}

				equippedItem.stackSize--;

				if (equippedItem.stackSize == 0)
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

				if ( equippedItem.stackSize == 0 && ( is.getMaxStackSize() == 1 || ! player.inventory.hasItemStack(is) ) ) // put buckets in the slot you used them from.
					player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
				else
				{
					if (!player.inventory.addItemStackToInventory(is))
						player.dropPlayerItemWithRandomChoice(is, false); // if the new item dosn't fit, drop it.
				}

				if ( player.inventoryContainer != null )
				{
					// for some reason the players inventory won't update without this.
					player.inventoryContainer.detectAndSendChanges();
				}

				return true;
			}
			else if(FluidContainerRegistry.isEmptyContainer(equippedItem) || equippedItem.getItem() instanceof IFluidContainerItem)
			{
				ItemStack tmp = equippedItem.copy();
				tmp.stackSize = 1;
				ItemStack is = te.removeLiquid(tmp);

				// If we cannot remove the liquid from the barrel, open the interface.
				if (ItemStack.areItemStacksEqual(equippedItem, is))
				{
					return false;
				}

				if (is.getItem() == TFCItems.woodenBucketMilk)
				{
					ItemCustomBucketMilk.createTag(is, 20f);
				}

				equippedItem.stackSize--;

				if (equippedItem.stackSize == 0)
					player.inventory.setInventorySlotContents(player.inventory.currentItem, null);

				if ( equippedItem.stackSize == 0 && ( is.getMaxStackSize() == 1 || ! player.inventory.hasItemStack(is) ) ) // put buckets in the slot you used them from.
					player.inventory.setInventorySlotContents(player.inventory.currentItem, is);
				else
				{
					if (!player.inventory.addItemStackToInventory(is))
						player.dropPlayerItemWithRandomChoice(is, false); // if the new item dosn't fit, drop it.
				}

				if ( player.inventoryContainer != null )
				{
					// for some reason the players inventory won't update without this.
					player.inventoryContainer.detectAndSendChanges();
				}

				return true;
			}
			else if(equippedItem.getItem() instanceof ItemBarrels || equippedItem.getItem() instanceof ItemLargeVessel)
			{
				ItemStack is = equippedItem.copy();
				is.stackSize = 1;
				if(equippedItem.hasTagCompound())
				{
					if(equippedItem.getTagCompound().hasKey("fluidNBT") && !equippedItem.getTagCompound().hasKey("Items") && te.getFluidLevel() < te.getMaxLiquid())
					{
						FluidStack fs = FluidStack.loadFluidStackFromNBT(equippedItem.getTagCompound().getCompoundTag("fluidNBT"));
						if (te.addLiquid(fs))
						{
							if (fs.amount == 0)
							{
								equippedItem.getTagCompound().removeTag("Sealed");
								equippedItem.getTagCompound().removeTag("fluidNBT");
								if (equippedItem.getTagCompound().hasNoTags())
									equippedItem.setTagCompound(null);
							}
							else
							{
								fs.writeToNBT(equippedItem.getTagCompound().getCompoundTag("fluidNBT"));
							}
							return true;
						}
					}
				}
				else
				{
					if(te.getFluidStack() != null)
					{
						NBTTagCompound nbt = new NBTTagCompound();
						if(is.getItem() instanceof ItemBarrels)
						{
							nbt.setTag("fluidNBT",te.getFluidStack().writeToNBT(new NBTTagCompound()));
							nbt.setBoolean("Sealed", true);
							is.setTagCompound(nbt);
							te.actionEmpty();
							equippedItem.stackSize--;
							TFC_Core.giveItemToPlayer(is, player);
						}
						else if(is.getItem() instanceof ItemLargeVessel)
						{
							if(is.getItemDamage() == 0)
								return false;

							FluidStack fs = te.getFluidStack().copy();
							if(fs.amount > 5000)
							{
								fs.amount = 5000;
								te.drainLiquid(5000);
							}
							else
							{
								te.actionEmpty();
							}
							nbt.setTag("fluidNBT", fs.writeToNBT(new NBTTagCompound()));
							nbt.setBoolean("Sealed", true);
							is.setTagCompound(nbt);
							equippedItem.stackSize--;
							TFC_Core.giveItemToPlayer(is, player);
						}
						return true;
					}
				}
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

	/**
	 * Get the block's damage value (for use with pick block).
	 */
	@Override
	public int getDamageValue(World world, int x, int y, int z)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if (te instanceof TEBarrel)
			return ((TEBarrel)te).barrelType;
		return 0;
	}

	/**
	 * This returns a complete list of items dropped from this block.
	 */
	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();

		int damageValue = getDamageValue(world, x, y, z);
		ret.add(new ItemStack(this, 1, damageValue));

		return ret;
	}

	public void spawnPowderKeg(World world, int x, int y, int z, TEBarrel te, boolean shortFuse)
	{
		if (TFCOptions.enablePowderKegs)
		{
			ItemStack is = new ItemStack(this, 1, te.barrelType);
			NBTTagCompound nbt = writeBarrelToNBT(te);
			is.setTagCompound(nbt);
			EntityBarrel entity = new EntityBarrel(world, x, y, z, is, te.getGunPowderCount());
			te.clearInventory();
			te.shouldDropItem = false;
			if (shortFuse)
			{
				entity.setFuse(1);
				world.spawnEntityInWorld(entity);
			}
			else
			{
				world.spawnEntityInWorld(entity);
				world.playSoundAtEntity(entity, "game.tnt.primed", 1.0F, 1.0F);
			}
		}
	}
}
