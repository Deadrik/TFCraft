package com.bioxx.tfc.Blocks.Devices;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.Tools.ItemFirestarter;
import com.bioxx.tfc.TileEntities.TEForge;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockForge extends BlockTerraContainer
{
	private IIcon textureOn;
	private IIcon textureOff;

	public BlockForge()
	{
		super(Material.sand);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 0.9F, 1F);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && world.getTileEntity(i, j, k) instanceof TEForge)
		{
			ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
			TEForge tef = (TEForge) world.getTileEntity(i, j, k);
			if (equippedItem != null)
			{
				Item item = equippedItem.getItem();
				if (item instanceof ItemFirestarter || item instanceof ItemFlintAndSteel)
				{
					if (!tef.isSmokeStackValid)
					{
						TFC_Core.sendInfoMessage(entityplayer, new ChatComponentTranslation("gui.forge.badChimney"));
						return true;
					}
					else if (tef.fireTemp <= 0 && tef.fireItemStacks[7] != null)
					{
						tef.fireTemp = 10;
						tef.fuelBurnTemp = 20;
						tef.fuelTimeLeft = 10;
						if (item instanceof ItemFlintAndSteel)
						{
							Random rand = new Random();
							world.playSoundEffect(i + 0.5D, j + 0.5D, k + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
						}
						equippedItem.damageItem(1, entityplayer);
						world.setBlockMetadataWithNotify(i, j, k, 2, 3);
						return true;
					}
				}
			}

			if (tef.isSmokeStackValid)
			{
				entityplayer.openGui(TerraFirmaCraft.instance, 23, world, i, j, k);
			}
			else
			{
				TFC_Core.sendInfoMessage(entityplayer, new ChatComponentTranslation("gui.forge.badChimney"));
			}
		}

		return true;
	}

	@Override
	public IIcon getIcon(int i, int j)
	{
		if(j > 0)
			return textureOn;
		return textureOff;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		textureOn = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Forge On");
		textureOff = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Forge Off");
	}

	public boolean getIsFireLit(int i)
	{
		return (i & 1) != 0;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.forgeRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if(!world.isRemote)
		{
			if (!TFC_Core.isSurroundedSolid(world, x, y, z) || !TFC_Core.isSurroundedStone(world, x, y, z))
			{
				((TEForge)world.getTileEntity(x, y, z)).ejectContents();
				world.setBlockToAir(x, y, z);
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getDrops(World world, int x, int y, int z, int metadata, int fortune)
	{
		return new ArrayList<ItemStack>();
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if (this == TFCBlocks.forge)
		{
			return;
		}
		else
		{
			float f = i + 0.5F;
			float f1 = j + 0.9F + random.nextFloat() * 6F / 16F;
			float f2 = k + 0.5F;
			//float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F;
			float f5 = random.nextFloat() * -0.6F;
			float f6 = random.nextFloat() * -0.6F;
			world.spawnParticle("smoke", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);

			if (((TEForge)world.getTileEntity(i, j, k)).fireTemp > 550)
			{
				world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f6 + 0.2F, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f+f4 - 0.3F , f1, f2 + f6 + 0.1F, 0.0D, 0.0D, 0.0D);
			}
		}
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0)
			return 0;
		else if(meta == 1)
			return 15;
		else
			return 10;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, Block block, int meta)
	{
		if(!world.isRemote)
		{
			if (world.getTileEntity(x, y, z) instanceof TEForge)
			{
				((TEForge) world.getTileEntity(x, y, z)).removeSmoke();
			}
		}

		super.breakBlock(world, x, y, z, block, meta);
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
	}

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEForge();
	}

	/**
	 * Displays a flat icon image for an ItemStack containing the block, instead of a render. Using primarily for WAILA HUD.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return Reference.MOD_ID + ":" + "devices/forge";
	}

	/**
	 * Triggered whenever an entity collides with this block (enters into the block). Args: world, x, y, z, entity
	 */
	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		// We'll be nice and do EntityLivingBase so it won't burn up dropped items.
		if (world.getBlockMetadata(x, y, z) >= 1 && !entity.isImmuneToFire() && entity instanceof EntityLivingBase)
		{
			// Five ticks of fire damage will deal 250 HP of damage.
			entity.setFire(5);
		}
	}
}
