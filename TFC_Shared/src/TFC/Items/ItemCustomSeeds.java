package TFC.Items;

import TFC.*;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.TileEntities.TileEntityCrop;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.ItemStack;
import net.minecraft.src.World;

public class ItemCustomSeeds extends ItemTerra
{
	/**
	 * The type of block this seed turns into (wheat or pumpkin stems for instance)
	 */
	private int cropId;

	/** BlockID of the block the seeds can be planted on. */
	private int soilBlockID;
	private int soilBlockID2;

	public ItemCustomSeeds(int par1, int cropId)
	{
		super(par1);
		this.soilBlockID = TFCBlocks.tilledSoil.blockID;
		this.soilBlockID2 = TFCBlocks.tilledSoil2.blockID;
		this.cropId = cropId;
		this.setCreativeTab(CreativeTabs.tabFood);
	}
	
	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.TINY;
	}
	
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}
	
	@Override
    public String getTextureFile()
    {
        return "/bioxx/FoodSprites.png";
    }

	/**
	 * Callback for item usage. If the item does something special on right clicking, he will have one of those. Return
	 * True if something happen and false if it don't. This is for ITEMS, not BLOCKS !
	 */
	@Override
	public boolean onItemUse(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		if (side != 1 || world.isRemote)
		{
			return false;
		}
		else if (player.canPlayerEdit(x, y, z, side, stack) && player.canPlayerEdit(x, y + 1, z, side, stack))
		{
			int var8 = world.getBlockId(x, y, z);

			if ((var8 == this.soilBlockID || var8 == this.soilBlockID2) && world.isAirBlock(x, y+1, z))
			{
				CropIndex crop = CropManager.getInstance().getCropFromId(cropId);
				
				if(crop.needsSunlight && !world.canBlockSeeTheSky(x, y+1, z))
					return false;
				
				world.setBlockWithNotify(x, y+1, z, Block.crops.blockID);
				TileEntityCrop te = ((TileEntityCrop)world.getBlockTileEntity(x, y+1, z));
				te.cropId = cropId;
				te.broadcastPacketInRange(te.createCropUpdatePacket());
				world.markBlockForUpdate(x, y, z);
				--stack.stackSize;
				return true;
			}
			else
			{
				return false;
			}
		}
		else
		{
			return false;
		}
	}
}
