package com.bioxx.tfc.Blocks.Vanilla;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFlowerPot;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFlowerPot;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.TFCBlocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomFlowerPot extends BlockFlowerPot
{

	public BlockCustomFlowerPot()
	{
		super();
	}

	/**
	 * Called upon block activation (right click on the block.)
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer player, int side, float hitX, float hitY, float hitZ)
	{
		ItemStack itemstack = player.inventory.getCurrentItem();

		if (itemstack != null)
		{			
			TileEntityFlowerPot teFlowerPot = this.getTileEntity(world, x, y, z);

			if (teFlowerPot != null)
			{
				if (teFlowerPot.getFlowerPotItem() != null)
				{
					return false; // There is already something in the flower pot.
				}
				
				Item item = itemstack.getItem();
				int meta = itemstack.getItemDamage();
				
				if (this.validItem(item, meta))
				{
					teFlowerPot.func_145964_a(item, meta); // Set Item and Data
					teFlowerPot.markDirty();

					if (!world.setBlockMetadataWithNotify(x, y, z, meta, 2))
					{
						world.markBlockForUpdate(x, y, z);
					}

					if (!player.capabilities.isCreativeMode && --itemstack.stackSize <= 0)
					{
						player.inventory.setInventorySlotContents(player.inventory.currentItem, (ItemStack) null);
					}

					return true;					
				}
				else
				{
					return false; // Invalid Item
				}
			}
			else
			{
				return false; // Null flower pot tile entity
			}
		}
		else
		{
			return false; // Empty hand
		}
	}

	private boolean validItem(Item item, int meta)
	{
		if (item instanceof ItemBlock)
		{
			Block block = Block.getBlockFromItem(item);
			
			// TFC Blocks
			if (block == TFCBlocks.Cactus || block == TFCBlocks.Flora || block == TFCBlocks.Flowers || block == TFCBlocks.Flowers2 || 
					block == TFCBlocks.Fungi || block == TFCBlocks.Sapling || block == TFCBlocks.Sapling2 || block == TFCBlocks.TallGrass && meta == 1 /*Fern*/)
				return true;
			
			// Vanilla Blocks
			if (block == Blocks.yellow_flower || block == Blocks.red_flower || block == Blocks.cactus || block == Blocks.brown_mushroom || 
					block == Blocks.red_mushroom || block == Blocks.sapling || block == Blocks.deadbush  || block == Blocks.tallgrass && meta == 2 /*Fern*/)
				return true;
		}
		// Currently not possible to render non-ItemBlocks in the flower pot. -Kitty
		/*else if (item == TFCItems.FruitTreeSapling)
			return true;*/
		return false;
	}

	private TileEntityFlowerPot getTileEntity(World world, int x, int y, int z)
	{
		TileEntity tileentity = world.getTileEntity(x, y, z);
		return tileentity != null && tileentity instanceof TileEntityFlowerPot ? (TileEntityFlowerPot) tileentity : null;
	}

	/**
	 * Displays a flat icon image for an ItemStack containing the block, instead of a render.
	 */
	@Override
	@SideOnly(Side.CLIENT)
	public String getItemIconName()
	{
		return Reference.ModID + ":" + "flower_pot";
	}

	@Override
	public Item getItemDropped(int p_149650_1_, Random p_149650_2_, int p_149650_3_)
	{
		return Item.getItemFromBlock(TFCBlocks.FlowerPot);
	}

	@Override
	public ItemStack getPickBlock(MovingObjectPosition target, World world, int x, int y, int z, EntityPlayer player)
	{
		TileEntityFlowerPot teFlowerPot = this.getTileEntity(world, x, y, z);

		if (teFlowerPot != null)
		{
			return new ItemStack(teFlowerPot.getFlowerPotItem(), 1, teFlowerPot.getFlowerPotData());
		}
		return new ItemStack(TFCBlocks.FlowerPot);
	}

	/**
	 * The type of render function that is called for this block
	 */
	@Override
	public int getRenderType()
	{
		return TFCBlocks.flowerPotRenderId;
	}
}
