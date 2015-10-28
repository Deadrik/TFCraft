package com.bioxx.tfc.Blocks.Devices;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.TileEntities.TELeatherRack;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Tools.IKnife;

public class BlockLeatherRack extends BlockTerraContainer
{
	public IIcon scrapedTex;

	public BlockLeatherRack()
	{
		super(Material.wood);
		this.setCreativeTab(null); // Removed from creative menu to help prevent the block being spawned in.
		this.setBlockBounds(0, 0, 0, 1, 0.001f, 1); // Very flat, but still has a hitbox that can be highlighted with the cursor.
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if (!world.isRemote && world.getTileEntity(x, y, z) instanceof TELeatherRack) // Instanceof check to avoid TE casting crashes.
		{
			TELeatherRack te = (TELeatherRack) world.getTileEntity(x, y, z);
			ItemStack equipped = entityplayer.getCurrentEquippedItem();
			
			if (te.workedArea != -1 && equipped != null && equipped.getItem() instanceof IKnife) // The player is trying to scrape a piece off the hide.
			{
				int coord = (int) Math.floor(hitX / 0.25f) + (int) Math.floor(hitZ / 0.25f) * 4; // Get location of spot being scraped.
				if (((te.workedArea >> coord) & 1) == 0) // If the area hasn't been scraped yet
				{
					te.workArea(coord); // Scrape the spot
					NBTTagCompound nbt = new NBTTagCompound();
					nbt.setShort("workedArea", te.workedArea); // Save the scraped spot to NBT
					te.broadcastPacketInRange(te.createDataPacket(nbt));
					equipped.damageItem(1, entityplayer); // Damage the knife
				}
			}
			else // Any other situation where the block is clicked, but the player is not properly trying to scrape a hide.
			{
				world.setBlockToAir(x, y, z); // Spawns any existing hides from the block as entities, and destroys the block and TE.
			}
		}

		if (!canBlockStay(world, x, y, z)) // For when players attempt to scrape a leather rack that should not exist (cheated in, no log underneath, etc).
		{
			world.setBlockToAir(x, y, z);
		}

		return false; // Always return false because a block is not being placed? Not sure if this is correct logic. -Kitty
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		if (world.getTileEntity(x, y, z) instanceof TELeatherRack)
		{
			TELeatherRack te = (TELeatherRack) world.getTileEntity(x, y, z);
			if (te.leatherItem == null) // Leather racks that have no hide should not exist.
			{
				return false;
			}
		}

		return world.getBlock(x, y - 1, z).getMaterial() == Material.wood; // Leather racks without a log underneath should not exist.
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, Block par5)
	{
		super.onNeighborBlockChange(world, i, j, k, par5);
		if(!canBlockStay(world,i,j,k))
			world.setBlock(i, j, k, Blocks.air, 0, 2);
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		if (!world.isRemote && world.getTileEntity(i, j, k) instanceof TELeatherRack) // Instanceof check to avoid TE casting crashes.
		{
			TELeatherRack te = (TELeatherRack)world.getTileEntity(i, j, k);
			if (te.leatherItem != null) // Only spawn a hide if the leather rack has a hide stored in it.
			{
				EntityItem ei = new EntityItem(world, i, j, k, te.leatherItem);
				ei.motionX = 0;
				ei.motionZ = 0;
				world.spawnEntityInWorld(ei);
			}
		}
	}

	@Override
	public Item getItemDropped(int metadata, Random rand, int fortune)
	{
		return null; // The block itself has no item to drop, the hide stored in the leather rack is dropped/spawned elsewhere.
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess w, int x, int y, int z)
	{
		return false;
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
		return TFCBlocks.leatherRackRenderId;
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		this.blockIcon = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "Soaked Hide");
		scrapedTex = iconRegisterer.registerIcon(Reference.MOD_ID + ":" + "Scraped Hide");
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess access, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public TileEntity createNewTileEntity(World world, int var2)
	{
		return new TELeatherRack();
	}
}
