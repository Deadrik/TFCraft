package com.bioxx.tfc.Blocks.Devices;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEAnvil;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Crafting.AnvilReq;

public class BlockAnvil extends BlockTerraContainer
{
	private IIcon[] textureMapTop;
	private IIcon[] textureMapSide;
	private IIcon stoneAnvilIcon;
	private int anvilId;

	public BlockAnvil()
	{
		super(Material.iron);
		this.setCreativeTab(TFCTabs.TFC_DEVICES);
	}

	public BlockAnvil(int offset)
	{
		this();
		anvilId = offset;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		if(this == TFCBlocks.anvil)
		{
			for(int i = 1; i < 8; i++)
				par3List.add(new ItemStack(this, 1, i));
		}

		if(this == TFCBlocks.anvil2)
		{
			for(int i = 0; i < 3; i++)
				par3List.add(new ItemStack(this, 1, i));
		}
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg & 7;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(world.isRemote)
		{
			return true;
		}
		else
		{
			if((TEAnvil)world.getTileEntity(i, j, k)!=null)
			{
				/*TEAnvil TEAnvil;
				TEAnvil = (TEAnvil)world.getTileEntity(i, j, k);
				ItemStack is = entityplayer.getCurrentEquippedItem();*/
				entityplayer.openGui(TerraFirmaCraft.instance, 21, world, i, j, k);
			}
			return true;
		}
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		int meta = par1World.getBlockMetadata(par2, par3, par4);
		int direction = getDirectionFromMetadata(meta);
		TileEntity te = par1World.getTileEntity(par2, par3, par4);

		if (te instanceof TEAnvil)
		{
			TEAnvil teAnvil = (TEAnvil) te;
			if (teAnvil.anvilTier != AnvilReq.STONE.Tier || this == TFCBlocks.anvil2)
			{
				if(direction == 0) 
					return AxisAlignedBB.getBoundingBox(par2 + 0.2, (double)par3 + 0, (double)par4 + 0, par2 + 0.8, par3 + 0.6, (double)par4 + 1);
				else
					return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, par4 + 0.2, (double)par2 + 1, par3 + 0.6, par4 + 0.8);
			}
			else
			{
				return AxisAlignedBB.getBoundingBox((double)par2 + 0, (double)par3 + 0, (double)par4 + 0, (double)par2 + 1, (double)par3 + 1, (double)par4 + 1);
			}
		}
		return null;
	}

	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		int direction = getDirectionFromMetadata(meta);
		TEAnvil te = (TEAnvil)world.getTileEntity(x, y, z);

		if(te.anvilTier != AnvilReq.STONE.Tier)
		{
			if(direction == 0)
			{
				this.setBlockBounds(0.2f, 0, 0, 0.8f, 0.6f, 1);
				return AxisAlignedBB.getBoundingBox(x + 0.2, (double)y + 0, (double)z + 0, x + 0.8, y + 0.6, (double)z + 1);
			}
			else
			{
				this.setBlockBounds(0, 0, 0.2f, 1, 0.6f, 0.8f);
				return AxisAlignedBB.getBoundingBox((double)x + 0, (double)y + 0, z + 0.2, (double)x + 1, y + 0.6, z + 0.8);
			}
		}
		else
		{
			this.setBlockBounds(0, 0, 0, 1, 0.9F, 1);
			return AxisAlignedBB.getBoundingBox((double)x + 0, (double)y + 0, (double)z + 0, (double)x + 1, (double)y + 0.9F, (double)z + 1);
		}
	}

	/**
	 * Updates the blocks bounds based on its current state. Args: world, x, y, z
	 */
	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess bAccess, int x, int y, int z)
	{
		int meta = bAccess.getBlockMetadata(x, y, z);
		int direction = getDirectionFromMetadata(meta);
		TEAnvil te = (TEAnvil)bAccess.getTileEntity(x, y, z);

		if(te.anvilTier != AnvilReq.STONE.Tier)
		{
			if(direction == 0)
				this.setBlockBounds(0.2f, 0, 0, 0.8f, 0.6f, 1);
			else
				this.setBlockBounds(0, 0, 0.2f, 1, 0.6f, 0.8f);
		}
		else
		{
			this.setBlockBounds(0, 0, 0, 1, 0.9F, 1);
		}
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		int meta = getAnvilTypeFromMeta(j);

		if (j == 0 && this == TFCBlocks.anvil)
		{
			return stoneAnvilIcon;
		}
		else
		{
			if(i == 0 || i == 1)
				return textureMapTop[meta];
			else
				return textureMapSide[meta];
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.anvilRenderId;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{
		int type = BlockAnvil.getAnvilTypeFromMeta(l);
		if(this == TFCBlocks.anvil)
		{
			if(type == 0)
				return;
		}
		super.harvestBlock(world, entityplayer, i, j, k, type);
	}

	@Override
	public void dropBlockAsItemWithChance(World par1World, int x, int y, int z, int meta, float par6, int par7)
	{
		if (!par1World.isRemote)
			this.dropBlockAsItem(par1World, x, y, z, new ItemStack(this, 1, meta));
	}

	@Override
	protected void dropBlockAsItem(World par1World, int par2, int par3, int par4, ItemStack is)
	{
		if (!par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
		{
			if(is.getItemDamage() == 0 && this == TFCBlocks.anvil)
				return;
			float f = 0.7F;
			double d0 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d1 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			double d2 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5D;
			EntityItem entityitem = new EntityItem(par1World, par2 + d0, par3 + d1, par4 + d2, is);
			entityitem.delayBeforeCanPickup = 10;
			par1World.spawnEntityInWorld(entityitem);
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
		int meta = world.getBlockMetadata(i, j, k);
		int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
		byte byte0 = 0;
		if(l == 0)//+z
			byte0 = 8;
		if(l == 1)//-x
			byte0 = 0;
		if(l == 2)//-z
			byte0 = 8;
		if(l == 3)//+x
			byte0 = 0;
		byte0 += meta;

		world.setBlockMetadataWithNotify(i, j, k, byte0, 3);

		TEAnvil te = (TEAnvil)world.getTileEntity(i, j, k);
		if(this == TFCBlocks.anvil)
			te.anvilTier = AnvilReq.getReqFromInt(meta).Tier;
		else if(this == TFCBlocks.anvil2)
			te.anvilTier = AnvilReq.getReqFromInt2(meta).Tier;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int metadata)
	{
		TEAnvil var5 = (TEAnvil)world.getTileEntity(x, y, z);

		if (var5 != null)
		{
			for (int var6 = 0; var6 < var5.getSizeInventory(); ++var6)
			{
				ItemStack var7 = var5.getStackInSlot(var6);

				if (var7 != null)
				{
					float var8 = world.rand.nextFloat() * 0.8F + 0.1F;
					float var9 = world.rand.nextFloat() * 0.8F + 0.1F;
					EntityItem var12;

					for (float var10 = world.rand.nextFloat() * 0.8F + 0.1F; var7.stackSize > 0; world.spawnEntityInWorld(var12))
					{
						int var11 = world.rand.nextInt(21) + 10;

						if (var11 > var7.stackSize)
							var11 = var7.stackSize;
						var7.stackSize -= var11;
						var12 = new EntityItem(world, x + var8, y + var9, z + var10, new ItemStack(var7.getItem(), var11, var7.getItemDamage()));
						float var13 = 0.05F;
						var12.motionX = (float)world.rand.nextGaussian() * var13;
						var12.motionY = (float)world.rand.nextGaussian() * var13 + 0.2F;
						var12.motionZ = (float)world.rand.nextGaussian() * var13;
						if (var7.hasTagCompound())
							var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, block, metadata);
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	public static int getAnvilTypeFromMeta(int j)
	{
		int l = 7;
		return j & l;
	}

	public static int getDirectionFromMetadata(int i)
	{
		int d = i >> 3;
			if (d == 1)
				return 1;
			else
				return 0;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEAnvil();
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		textureMapTop = new IIcon[anvilId == 0 ? 8 : 3];
		textureMapSide = new IIcon[anvilId == 0 ? 8 : 3];
		for(int i = (anvilId == 0 ? 1 : 0); i < (anvilId == 0 ? 8 : 3); i++)
		{
			textureMapTop[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Anvil_" + (i+anvilId) + "_Top");
			textureMapSide[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "devices/Anvil_" + (i+anvilId) + "_Side");
		}

		stoneAnvilIcon = registerer.registerIcon(Reference.MOD_ID + ":" + "rocks/" + "Gabbro Raw");

		TFC_Textures.anvilHit = registerer.registerIcon(Reference.MOD_ID + ":" + "Anvil Hit");
		TFC_Textures.anvilHitHeavy = registerer.registerIcon(Reference.MOD_ID + ":" + "Anvil Hit Heavy");
		TFC_Textures.anvilHitMedium = registerer.registerIcon(Reference.MOD_ID + ":" + "Anvil Hit Medium");
		TFC_Textures.anvilHitLight = registerer.registerIcon(Reference.MOD_ID + ":" + "Anvil Hit Light");
		TFC_Textures.anvilDraw = registerer.registerIcon(Reference.MOD_ID + ":" + "Anvil Draw");
		TFC_Textures.anvilPunch = registerer.registerIcon(Reference.MOD_ID + ":" + "Anvil Punch");
		TFC_Textures.anvilBend = registerer.registerIcon(Reference.MOD_ID + ":" + "Anvil Bend");
		TFC_Textures.anvilUpset = registerer.registerIcon(Reference.MOD_ID + ":" + "Anvil Upset");
		TFC_Textures.anvilShrink = registerer.registerIcon(Reference.MOD_ID + ":" + "Anvil Shrink");
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return world.getBlock(x, y, z) == this;
	}
}
