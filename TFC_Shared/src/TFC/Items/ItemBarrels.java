package TFC.Items;

import java.util.List;

import TFC.TFCBlocks;
import TFC.Core.Helper;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.TileEntities.TileEntityBarrel;
import TFC.TileEntities.TileEntityTerraLogPile;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class ItemBarrels extends ItemTerra
{
	public static String[] blockNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
		"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};


	public ItemBarrels(int i) 
	{
		super(i);
		setMaxDamage(0);
		setHasSubtypes(true);
		this.setCreativeTab(CreativeTabs.tabMaterials);
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.LARGE;
	}
	
	@Override
	public EnumWeight getWeight() {
		return EnumWeight.HEAVY;
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < blockNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}

	private boolean CreatePile(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y,
			int z, int side) {
		TileEntityBarrel te = null;

		if(side == 0 && world.getBlockId(x, y-1, z) == 0)
		{
			world.setBlock(x, y-1, z, te.getBarrels()[itemstack.getItemDamage()]);
			if(world.isRemote)
				world.markBlockForUpdate(x, y-1, z);
			
		}
		else if(side == 1 && world.getBlockId(x, y+1, z) == 0)
		{
			world.setBlock(x, y+1, z, te.getBarrels()[itemstack.getItemDamage()]);
			if(world.isRemote)
				world.markBlockForUpdate(x, y+1, z);
			
		}
		else if(side == 2 && world.getBlockId(x, y, z-1) == 0)
		{
			world.setBlock(x, y, z-1, te.getBarrels()[itemstack.getItemDamage()]);
			if(world.isRemote)
				world.markBlockForUpdate(x, y, z-1);
			
		}
		else if(side == 3 && world.getBlockId(x, y, z+1) == 0)
		{
			world.setBlock(x, y, z+1, te.getBarrels()[itemstack.getItemDamage()]);
			if(world.isRemote)
				world.markBlockForUpdate(x, y, z+1);
			
		}
		else if(side == 4 && world.getBlockId(x-1, y, z) == 0)
		{
			world.setBlock(x-1, y, z, te.getBarrels()[itemstack.getItemDamage()]);
			if(world.isRemote)
				world.markBlockForUpdate(x-1, y, z);
			
		}
		else if(side == 5 && world.getBlockId(x+1, y, z) == 0)
		{
			world.setBlock(x+1, y, z, te.getBarrels()[itemstack.getItemDamage()]);
			if(world.isRemote)
				world.markBlockForUpdate(x+1, y, z);
			
		}
		else
		{
			return false;
		}
		itemstack.stackSize--;
		return true;
	}
	
	

	@Override
	public Icon getIconFromDamage(int meta)
	{        
		return icons[meta];
	}
	
	Icon[] icons = new Icon[16];
	
	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemDisplayName(itemstack)).append(".").append(blockNames[itemstack.getItemDamage()]).toString();
		return s;
	}

	@Override
	public void registerIcon(IconRegister registerer)
    {
		for(int i = 0; i < 16; i++)
			registerer.func_94245_a("tools/Barrel_"+i);
    }

	@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			CreatePile(itemstack,entityplayer,world,x,y,z,side);
		}
		return false;
	}
	
	public void setSide(World world, ItemStack itemstack, int m, int dir, int x, int y, int z, int i, int j, int k)
	{
		if(m < 8)
		{
			if(dir == 0 || dir == 2)
				world.setBlockAndMetadataWithNotify(x+i, y+j, z+k, TFCBlocks.WoodHoriz.blockID, m,3);
			else
				world.setBlockAndMetadataWithNotify(x+i, y+j, z+k, TFCBlocks.WoodHoriz.blockID, m | 8,3);
			itemstack.stackSize = itemstack.stackSize-1;
		}
		else if(m >= 8)
		{
			if(dir == 0 || dir == 2)
				world.setBlockAndMetadataWithNotify(x+i, y+j, z+k, TFCBlocks.WoodHoriz2.blockID, m-8,3);
			else
				world.setBlockAndMetadataWithNotify(x+i, y+j, z+k, TFCBlocks.WoodHoriz2.blockID, m-8 | 8,3);
			itemstack.stackSize = itemstack.stackSize-1;
		}
	}

}
