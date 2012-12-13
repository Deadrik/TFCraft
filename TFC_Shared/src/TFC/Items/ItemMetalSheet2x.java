package TFC.Items;

import java.util.List;

import TFC.TFCBlocks;
import TFC.Core.Helper;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.TileEntities.TileEntityTerraLogPile;
import net.minecraft.src.Block;
import net.minecraft.src.CreativeTabs;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemStack;
import net.minecraft.src.MathHelper;
import net.minecraft.src.MovingObjectPosition;
import net.minecraft.src.World;

public class ItemMetalSheet2x extends ItemMetalSheet
{
	public ItemMetalSheet2x(int i) 
	{
		super(i);
		setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
	
	@Override
	public EnumWeight getWeight() {
		return EnumWeight.MEDIUM;
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int dir = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;

		}
		return false;
	}
}
