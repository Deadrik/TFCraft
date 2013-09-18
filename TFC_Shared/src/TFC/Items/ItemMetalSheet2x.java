package TFC.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemMetalSheet2x extends ItemMetalSheet
{
	public ItemMetalSheet2x(int i) 
	{
		super(i);
		setMaxDamage(0);
		this.setCreativeTab(TFCTabs.TFCMaterials);
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.MEDIUM);
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int dir = MathHelper.floor_double(entityplayer.rotationYaw * 4F / 360F + 0.5D) & 3;

		}
		return false;
	}
}
