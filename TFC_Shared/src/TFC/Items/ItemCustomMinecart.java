package TFC.Items;

import java.util.List;

import net.minecraft.block.BlockRail;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import TFC.API.Enums.EnumSize;
import TFC.Entities.EntityCustomMinecart;

public class ItemCustomMinecart extends ItemTerra
{
	public int minecartType;

	public ItemCustomMinecart(int par1, int par2)
	{
		super(par1);
		this.maxStackSize = 1;
		this.minecartType = par2;
		this.setCreativeTab(CreativeTabs.tabTransport);
	}

	@Override
	public void registerIcons(IconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon("minecart_chest");
	}

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		int var11 = world.getBlockId(x, y, z);

		if (BlockRail.isRailBlock(var11))
		{
			if (!world.isRemote)
			{
				world.spawnEntityInWorld(new EntityCustomMinecart(world, x + 0.5F, y + 0.5F, z + 0.5F));
			}

			--itemstack.stackSize;
			return true;
		}
		else
		{
			return false;
		}
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List par3List)
	{
		par3List.add(new ItemStack(par1, 1, 0));
	}

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.MEDIUM;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}
}
