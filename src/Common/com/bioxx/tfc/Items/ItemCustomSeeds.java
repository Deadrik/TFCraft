package com.bioxx.tfc.Items;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Food.CropIndex;
import com.bioxx.tfc.Food.CropManager;
import com.bioxx.tfc.TileEntities.TECrop;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemCustomSeeds extends ItemTerra
{
	/**
	 * The type of block this seed turns into (wheat or pumpkin stems for instance)
	 */
	private int cropId;

	public ItemCustomSeeds(int cropId)
	{
		super();
		this.cropId = cropId;
		this.setCreativeTab(TFCTabs.TFCFoods);
		this.setFolder("food/");
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.TINY);
	}

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if (side != 1 || world.isRemote)
			return false;
		else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
		{
			Block var8 = world.getBlock(x, y, z);
			if ((var8 == TFCBlocks.tilledSoil || var8 == TFCBlocks.tilledSoil2) && world.isAirBlock(x, y + 1, z))
			{
				CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
				if(crop.needsSunlight && !world.canBlockSeeTheSky(x, y + 1, z))
					return false;

				world.setBlock(x, y + 1, z, TFCBlocks.Crops);
				//world.setBlock(x, y + 2, z, Block.crops.blockID, 8, 0x2);
				TECrop te = (TECrop) world.getTileEntity(x, y + 1, z);
				te.cropId = cropId;
				world.markBlockForUpdate(te.xCoord, te.yCoord, te.zCoord);
				//te.broadcastPacketInRange(te.createCropUpdatePacket());
				world.markBlockForUpdate(x, y, z);
				--stack.stackSize;
				return true;
			}
			else
				return false;
		}
		else
			return false;
	}
}
