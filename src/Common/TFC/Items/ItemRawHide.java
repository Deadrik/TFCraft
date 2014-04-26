package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;

public class ItemRawHide extends ItemLooseRock
{
	public ItemRawHide()
	{
		super();
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFCMaterials);
		this.MetaNames = new String[]{"small","medium","large"};
		this.setWeight(EnumWeight.LIGHT);
		this.setSize(EnumSize.MEDIUM);
	}


	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote && itemstack.getItem() == TFCItems.Hide && itemstack.getItemDamage() >= 2){
			int d = (int)((45 + ((entityplayer.rotationYaw % 360)+360f)%360)/90)%4; //direction
			int x2 = x+(d==1?-1:(d==3?1:0)); // the x-coord of the second block
			int z2 = z+(d==2?-1:(d==0?1:0));
			if(world.getBlock(x, y, z)==TFCBlocks.Thatch && side == 1 && world.getBlock(x2,y,z2)==TFCBlocks.Thatch
					&& world.isAirBlock(x, y+1, z) && world.isAirBlock(x2, y+1, z2)){
				world.func_147480_a/*destroyBlock*/(x, y, z, false);
				world.func_147480_a/*destroyBlock*/(x2, y, z2, false);
				world.setBlock(x, y, z, TFCBlocks.StrawHideBed, d, 2);
				world.setBlock(x2, y, z2, TFCBlocks.StrawHideBed, d+8, 2);
				itemstack.stackSize--;
			}
		}
		return true;
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemstack, World par2World, EntityPlayer entityplayer)
	{
		return itemstack;
	}

	@Override
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
	{
		if (TFC_Core.showExtraInformation() && is.getItem() == TFCItems.Hide) 
		{
			arraylist.add(StatCollector.translateToLocal("gui.Help"));
			arraylist.add(StatCollector.translateToLocal("gui.RawHide.Inst0"));
		}
		else
		{
			arraylist.add(StatCollector.translateToLocal("gui.ShowHelp"));
		}
	}


	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{

	}

	@Override
	public IIcon getIconFromDamage(int meta)
	{        
		return this.itemIcon;
	}


	@Override
	public void registerIcons(IIconRegister registerer)
	{
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + textureFolder + this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public void getSubItems(Item par1, CreativeTabs par2CreativeTabs, List list)
	{
		list.add(new ItemStack(this,1,0));
		list.add(new ItemStack(this,1,1));
		list.add(new ItemStack(this,1,2));
	}
}
