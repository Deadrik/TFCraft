package com.bioxx.tfc.Blocks.Devices;

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
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.Tools.ItemFirestarter;
import com.bioxx.tfc.TileEntities.TEFirepit;
import com.bioxx.tfc.api.TFCBlocks;

public class BlockFirepit extends BlockTerraContainer
{
	private IIcon textureOn;
	private IIcon textureOff;

	public BlockFirepit()
	{
		super(Material.ground);
		this.setBlockBounds(0, 0, 0, 1, 0.1f, 1);
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote)
		{
			ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
			if (equippedItem != null)
			{
				Item item = entityplayer.getCurrentEquippedItem().getItem();
				if (item instanceof ItemFirestarter || item instanceof ItemFlintAndSteel)
				{
					if ((TEFirepit) world.getTileEntity(x, y, z) != null)
					{
						TEFirepit te = (TEFirepit) world.getTileEntity(x, y, z);
						if (te.fireTemp < 210 && te.fireItemStacks[5] != null)
						{
							te.fireTemp = 300;
							if (item instanceof ItemFlintAndSteel)
							{
								Random rand = new Random();
								world.playSoundEffect(x + 0.5D, y + 0.5D, z + 0.5D, "fire.ignite", 1.0F, rand.nextFloat() * 0.4F + 0.8F);
							}
							equippedItem.damageItem(1, entityplayer);
							world.setBlockMetadataWithNotify(x, y, z, 1, 3);
							return true;
						}
					}
				}
			}

			if ((TEFirepit) world.getTileEntity(x, y, z) != null)
				entityplayer.openGui(TerraFirmaCraft.instance, 20, world, x, y, z);
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
	public int getRenderType()
	{
		return TFCBlocks.firepitRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return 0;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block block)
	{
		if (!TFC_Core.isTopFaceSolid(world, x, y - 1, z))
		{
			((TEFirepit)world.getTileEntity(x, y, z)).ejectContents();
			world.setBlockToAir(x, y, z);
			return;
		}
	}

	@Override
	public void randomDisplayTick(World world, int x, int y, int z, Random rand)
	{
		int meta = world.getBlockMetadata(x, y, z);
		if (meta >= 1)
		{
			if (rand.nextInt(24) == 0)
				world.playSoundEffect(x, y, z, "fire.fire", 0.4F + (rand.nextFloat() / 2), 0.7F + rand.nextFloat());

			float f = x + 0.5F;
			float f1 = y + 0.1F + rand.nextFloat() * 6F / 16F;
			float f2 = z + 0.5F;
			//float f3 = 0.52F;
			float f4 = rand.nextFloat() * 0.6F;
			float f5 = rand.nextFloat() * -0.6F;
			float f6 = rand.nextFloat() * -0.6F;
			world.spawnParticle("smoke", f + f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f + f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f + f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			if (((TEFirepit)world.getTileEntity(x, y, z)).fireTemp > 550)
			{
				world.spawnParticle("flame", f + f5 + 0.3F , f1, f2 + f6 + 0.2F, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f + f4 - 0.3F , f1, f2 + f6 + 0.1F, 0.0D, 0.0D, 0.0D);
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
			return 10;
		else
			return 15;
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
		eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int x, int y, int z, Explosion exp)
	{
		eject(world, x, y, z);
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int x, int y, int z, int meta)
	{
		eject(world, x, y, z);
	}

	//public void onBlockRemoval(World world, int x, int y, int z) {Eject(world, x, y, z);}

	public void eject(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TEFirepit)
		{
			TEFirepit te = (TEFirepit)world.getTileEntity(x, y, z);
			te.ejectContents();
			world.removeTileEntity(x, y, z);
		}
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int meta)
	{
		return new TEFirepit();
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		textureOn = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Firepit On");
		textureOff = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "devices/Firepit Off");
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z)
	{
		return null;
	}

	/**
	 * Displays a flat icon image for an ItemStack containing the block, instead of a render. Using primarily for WAILA HUD.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return Reference.MOD_ID + ":" + "devices/firepit";
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
			// Two ticks of fire damage will deal 100 HP of damage.
			entity.setFire(2);
		}
    }
}
