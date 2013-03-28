package TFC.Items;

import java.util.List;

import TFC.TFCBlocks;
import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.TileEntities.TileEntityWoodConstruct;
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

public class ItemPlank extends ItemTerra
{
	public ItemPlank(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		this.setCreativeTab(CreativeTabs.tabMaterials);
		MetaNames = new String[]{"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
				"Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
	}

	@Override
	public boolean onItemUse(ItemStack is, EntityPlayer player, World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ)
	{
		boolean isConstruct = world.getBlockId(i, j, k) == TFCBlocks.WoodConstruct.blockID;
		int offset = !isConstruct ? 1 : 0;
		boolean isAir = world.getBlockId(i, j, k) == 0;

		if(!world.isRemote)
		{
			int d = TileEntityWoodConstruct.PlankDetailLevel;
			int dd = d*d;
			int dd2 = dd*2;

			float div = 1f / d;

			int x = (int) (hitX / div);
			int y = (int) (hitY / div);
			int z = (int) (hitZ / div);

			hitX = Math.round(hitX*100)/100.0f;
			hitY = Math.round(hitY*100)/100.0f;
			hitZ = Math.round(hitZ*100)/100.0f;

			boolean isEdge = false;

			if(hitX == 0 || hitX == 1 || hitY == 0 || hitY == 1 || hitZ == 0 || hitZ == 1)
			{
				isEdge = true;
				isConstruct = true;
				offset = 1;
			}

			if(side == 0)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i, j-offset, k) == 0))
					world.setBlock(i, j-1, k, TFCBlocks.WoodConstruct.blockID);

				TileEntity tile = world.getBlockTileEntity(i, j-offset, k);
				if(tile!= null)
				{
					TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
					te.data.set(dd+(x+(z*d)));
					te.woodTypes[dd+(x+(z*d))] = (byte) is.getItemDamage();

					te.broadcastPacketInRange(te.createUpdatePacket(dd+(x+(z*d)), (byte) is.getItemDamage()));
				}
			}
			else if(side == 1)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i, j+offset, k) == 0))
					world.setBlock(i, j+1, k, TFCBlocks.WoodConstruct.blockID);

				TileEntity tile = world.getBlockTileEntity(i, j+offset, k);
				if(tile!= null)
				{
					TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
					te.data.set(dd+(x+(z*d)));
					te.woodTypes[dd+(x+(z*d))] = (byte) is.getItemDamage();

					te.broadcastPacketInRange(te.createUpdatePacket(dd+(x+(z*d)), (byte) is.getItemDamage()));
				}
			}
			else if(side == 2)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i, j, k-offset) == 0))
					world.setBlock(i, j, k-1, TFCBlocks.WoodConstruct.blockID);

				TileEntity tile = world.getBlockTileEntity(i, j, k-offset);
				if(tile!= null)
				{
					TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
					te.data.set(dd2+(x+(y*d)));
					te.woodTypes[dd2+(x+(y*d))] = (byte) is.getItemDamage();

					te.broadcastPacketInRange(te.createUpdatePacket(dd2+(x+(y*d)), (byte) is.getItemDamage()));
				}
			}
			else if(side == 3)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i, j, k+offset) == 0))
					world.setBlock(i, j, k+1, TFCBlocks.WoodConstruct.blockID);

				TileEntity tile = world.getBlockTileEntity(i, j, k+offset);
				if(tile!= null)
				{
					TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
					te.data.set(dd2+(x+(y*d)));
					te.woodTypes[dd2+(x+(y*d))] = (byte) is.getItemDamage();

					te.broadcastPacketInRange(te.createUpdatePacket(dd2+(x+(y*d)), (byte) is.getItemDamage()));
				}
			}
			else if(side == 4)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i-offset, j, k) == 0))
					world.setBlock(i-1, j, k, TFCBlocks.WoodConstruct.blockID);

				TileEntity tile = world.getBlockTileEntity(i-offset, j, k);
				if(tile!= null)
				{
					TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
					te.data.set((y+(z*d)));
					te.woodTypes[(y+(z*d))] = (byte) is.getItemDamage();

					te.broadcastPacketInRange(te.createUpdatePacket((y+(z*d)), (byte) is.getItemDamage()));
				}
			}
			else if(side == 5)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i+offset, j, k) == 0))
					world.setBlock(i+1, j, k, TFCBlocks.WoodConstruct.blockID);

				TileEntity tile = world.getBlockTileEntity(i+offset, j, k);
				if(tile!= null)
				{
					TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
					te.data.set((y+(z*d)));
					te.woodTypes[(y+(z*d))] = (byte) is.getItemDamage();

					te.broadcastPacketInRange(te.createUpdatePacket((y+(z*d)), (byte) is.getItemDamage()));
				}
			}
			is.stackSize--;
			return true;
		}
		//world.markBlockForRenderUpdate(i, j, k);
		return false;
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}
	
	@Override
	public int getMetadata(int i) 
	{       
		return i;
	}
	
	@Override
	public Icon getIconFromDamage(int meta)
	{        
		return icons[meta];
	}
	
	Icon[] icons = new Icon[16];
	@Override
	public void updateIcons(IconRegister registerer)
    {
		for(int i = 0; i < 16; i++)
			registerer.registerIcon("wood/"+MetaNames[i]+" Plank");
    }

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
