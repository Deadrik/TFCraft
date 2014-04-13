package TFC.Items;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TileEntities.TileEntityFruitTreeWood;

public class ItemFruitTreeSapling extends ItemTerra
{
	int offset;
	IIcon[] icons;

	public ItemFruitTreeSapling(int off)
	{
		super();
		offset = off;
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabFood);
		this.MetaNames = new String[]{"Red Apple","Banana","Orange","Green Apple","Lemon","Olive","Cherry","Peach","Plum"};
		icons = new IIcon[MetaNames.length];
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		int meta = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
		if(side == 1 && world.getBlock(x, y, z).isNormalCube() && world.getBlock(x, y, z).isOpaqueCube() && 
				(world.getBlock(x, y, z).getMaterial() == Material.grass || world.getBlock(x, y, z).getMaterial() == Material.ground) &&
				world.isAirBlock(x, y + 1, z) && !world.isRemote)
		{

			world.setBlock(x, y + 1, z, TFCBlocks.fruitTreeWood, stack.getItemDamage() + offset, 0x2);

			((TileEntityFruitTreeWood)world.getTileEntity(x, y+1, z)).setTrunk(true);
			((TileEntityFruitTreeWood)world.getTileEntity(x, y+1, z)).setHeight(0);
			((TileEntityFruitTreeWood)world.getTileEntity(x, y+1, z)).setBirth();

			stack.stackSize = stack.stackSize - 1;
			return true;
		}

		return false;
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		if(this == TFCItems.FruitTreeSapling1)
		{
			for(int i = 0; i < 8; i++) 
			{
				list.add(new ItemStack(this, 1, i));
			}
		}
		else
		{
			list.add(new ItemStack(this, 1, 0));
		}
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		for(int i = 0; i < MetaNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/"+MetaNames[i]+" Sapling");
	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{
		return icons[meta];
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
	}
}
