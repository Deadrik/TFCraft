package TFC.Items;

import java.util.List;

import TFC.TFCBlocks;
import TFC.Blocks.BlockCollapsable;
import TFC.Core.Helper;
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

public class ItemStoneBrick extends ItemTerra
{
	public ItemStoneBrick(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		this.MetaNames = new String[]{"Granite", "Diorite", "Gabbro", 
				"Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", 
				"Chalk", "Rhyolite", "Basalt", "Andesite", "Dacite", 
				"Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};
	}
	public ItemStoneBrick(int id, String tex) 
	{
		super(id);
	}

	/*int[][] map = 
		{   {0,-1,0},
			{0,1,0},
			{0,0,-1},
			{0,0,1},
			{-1,0,0},
			{1,0,0},
		};*/

	/*@Override
	public boolean onItemUse(ItemStack itemstack, EntityPlayer entityplayer, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
	{
		MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
		if(objectMouseOver == null) {
			return false;
		}

		int dir = MathHelper.floor_double((double)(entityplayer.rotationYaw * 4F / 360F) + 0.5D) & 3;

		double xCoord = x + map[side][0];
		double yCoord = y + map[side][1];
		double zCoord = z + map[side][2];

		if((entityplayer.posX > xCoord+1  || entityplayer.posX < xCoord) || 
				(entityplayer.posZ > zCoord+1 || entityplayer.posZ < zCoord) || 
				(entityplayer.posY > yCoord+1.63 || entityplayer.posY < yCoord-1))
		{
			if(!world.isRemote)
			{
				if(world.getBlockId(x, y, z) == Block.snow.blockID || BlockCollapsable.canFallBelow(world, x, y, z))
				{
					if(     (itemstack.getItemDamage() < 3 && world.setBlockAndMetadata(x, y, z, TFCBlocks.StoneIgInCobble.blockID, itemstack.getItemDamage())) || 
							(itemstack.getItemDamage() < 13 && world.setBlockAndMetadata(x, y, z, TFCBlocks.StoneSedCobble.blockID, itemstack.getItemDamage() - 3))|| 
							(itemstack.getItemDamage() < 17 && world.setBlockAndMetadata(x, y, z, TFCBlocks.StoneIgExCobble.blockID, itemstack.getItemDamage() - 13))|| 
							(itemstack.getItemDamage() < 23 && world.setBlockAndMetadata(x, y, z, TFCBlocks.StoneMMCobble.blockID, itemstack.getItemDamage() - 17)))
					{
						if(!world.isRemote)
						{
							world.markBlockForUpdate(x, y, z);
							itemstack.stackSize = itemstack.stackSize-1;   
						}
						return true; 
					}
				}
				else if((world.getBlockId(x + map[side][0], y + map[side][1], z + map[side][2]) == 0 || 
						world.getBlockId(x + map[side][0], y + map[side][1], z + map[side][2]) == Block.snow.blockID  || BlockCollapsable.canFallBelow(world, x, y, z)))
				{
					if((itemstack.getItemDamage() < 3 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.StoneIgInCobble.blockID, itemstack.getItemDamage())) || 
					(itemstack.getItemDamage() < 13 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.StoneSedCobble.blockID, itemstack.getItemDamage() - 3))|| 
					(itemstack.getItemDamage() < 17 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.StoneIgExCobble.blockID, itemstack.getItemDamage() - 13))|| 
					(itemstack.getItemDamage() < 23 && world.setBlockAndMetadata(x + map[side][0], y + map[side][1], z + map[side][2], TFCBlocks.StoneMMCobble.blockID, itemstack.getItemDamage() - 17)))
					{
						world.markBlockForUpdate(x + map[side][0], y + map[side][1], z + map[side][2]);
						itemstack.stackSize = itemstack.stackSize-1;   
						return true; 
					}
				}
			}

		}

		return false;
	}*/

	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) 
	{

	}

	@Override
	public Icon getIconFromDamage(int meta)
	{
		return icons[meta];
	}
	
	Icon[] icons = new Icon[23];
	@Override
	public void registerIcon(IconRegister registerer)
    {
		for(int i = 0; i < 23; i++)
			registerer.func_94245_a("/rocks/"+MetaNames[i]+" Brick");
    }

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 23; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
