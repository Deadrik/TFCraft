package TFC.Items;

import java.util.List;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.TileEntities.TileEntityFruitTreeWood;

public class ItemFruitTreeSapling extends ItemTerra
{
	int offset;
	Icon[] icons;
	public ItemFruitTreeSapling(int id, int off)
	{
		super(id);
		offset = off;
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabFood);
		this.MetaNames = new String[]{"Red Apple","Banana","Orange","Green Apple","Lemon","Olive","Cherry","Peach","Plum"};
		icons = new Icon[MetaNames.length];
	}

	@Override
	public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ) 
	{
		int meta = MathHelper.floor_double(player.rotationYaw * 4F / 360F + 0.5D) & 3;
		if(side == 1 && world.isBlockNormalCube(x, y, z) && world.isBlockOpaqueCube(x, y, z) && 
				(world.getBlockMaterial(x, y, z) == Material.grass || world.getBlockMaterial(x, y, z) == Material.ground) &&
				world.getBlockId(x, y+1, z) == 0 && !world.isRemote)
		{

			world.setBlock(x, y+1, z, TFCBlocks.fruitTreeWood.blockID, stack.getItemDamage()+offset, 0x2);

			((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setTrunk(true);
			((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setHeight(0);
			((TileEntityFruitTreeWood)world.getBlockTileEntity(x, y+1, z)).setBirth();

			stack.stackSize = stack.stackSize-1;
			return true;
		}

		return false;
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		if(this.itemID == TFCItems.FruitTreeSapling1.itemID)
		{
			for(int i = 0; i < 8; i++) 
			{
				list.add(new ItemStack(this,1,i));
			}
		}
		else
		{
			list.add(new ItemStack(this,1,0+offset));
		}
	}


	@Override
	public void registerIcons(IconRegister registerer)
	{
		for(int i = 0; i < MetaNames.length; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/fruit trees/"+MetaNames[i]+" Sapling");
	}

	@Override
	public Icon getIconFromDamage(int meta)
	{        
		return icons[meta+offset];
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
	}
}
