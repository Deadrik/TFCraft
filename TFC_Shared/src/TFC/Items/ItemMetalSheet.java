package TFC.Items;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemMetalSheet extends ItemTerra
{
	public ItemMetalSheet(int i) 
	{
		super(i);
		setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		setFolder("ingots/");
		this.setWeight(EnumWeight.MEDIUM);
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
