package TFC.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.Constant.Global;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.TFCTabs;
import TFC.TileEntities.TileEntityWoodConstruct;

public class ItemPlank extends ItemTerra
{
	public ItemPlank(int id) 
	{
		super(id);
		this.hasSubtypes = true;
		this.setMaxDamage(0);
		setCreativeTab(TFCTabs.TFCMaterials);
		this.MetaNames = Global.WOOD_ALL;
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
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i, j-offset, k) == 0)) {
					world.setBlock(i, j-1, k, TFCBlocks.WoodConstruct.blockID);
				}

				TileEntity tile = world.getBlockTileEntity(i, j-offset, k);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set(dd+(x+(z*d)));
				te.woodTypes[dd+(x+(z*d))] = (byte) is.getItemDamage();

				te.broadcastPacketInRange(te.createUpdatePacket(dd+(x+(z*d)), (byte) is.getItemDamage()));
			}
			else if(side == 1)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i, j+offset, k) == 0)) {
					world.setBlock(i, j+1, k, TFCBlocks.WoodConstruct.blockID);
				}

				TileEntity tile = world.getBlockTileEntity(i, j+offset, k);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set(dd+(x+(z*d)));
				te.woodTypes[dd+(x+(z*d))] = (byte) is.getItemDamage();

				te.broadcastPacketInRange(te.createUpdatePacket(dd+(x+(z*d)), (byte) is.getItemDamage()));
			}
			else if(side == 2)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i, j, k-offset) == 0)) {
					world.setBlock(i, j, k-1, TFCBlocks.WoodConstruct.blockID);
				}

				TileEntity tile = world.getBlockTileEntity(i, j, k-offset);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set(dd2+(x+(y*d)));
				te.woodTypes[dd2+(x+(y*d))] = (byte) is.getItemDamage();

				te.broadcastPacketInRange(te.createUpdatePacket(dd2+(x+(y*d)), (byte) is.getItemDamage()));
			}
			else if(side == 3)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i, j, k+offset) == 0)) {
					world.setBlock(i, j, k+1, TFCBlocks.WoodConstruct.blockID);
				}

				TileEntity tile = world.getBlockTileEntity(i, j, k+offset);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set(dd2+(x+(y*d)));
				te.woodTypes[dd2+(x+(y*d))] = (byte) is.getItemDamage();

				te.broadcastPacketInRange(te.createUpdatePacket(dd2+(x+(y*d)), (byte) is.getItemDamage()));
			}
			else if(side == 4)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i-offset, j, k) == 0)) {
					world.setBlock(i-1, j, k, TFCBlocks.WoodConstruct.blockID);
				}

				TileEntity tile = world.getBlockTileEntity(i-offset, j, k);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set((y+(z*d)));
				te.woodTypes[(y+(z*d))] = (byte) is.getItemDamage();

				te.broadcastPacketInRange(te.createUpdatePacket((y+(z*d)), (byte) is.getItemDamage()));
			}
			else if(side == 5)
			{
				if((!isConstruct && isAir) || (isConstruct && isEdge && world.getBlockId(i+offset, j, k) == 0)) {
					world.setBlock(i+1, j, k, TFCBlocks.WoodConstruct.blockID);
				}

				TileEntity tile = world.getBlockTileEntity(i+offset, j, k);
				if((tile == null) || (!(tile instanceof TileEntityWoodConstruct))) {
					return false;
				}

				TileEntityWoodConstruct te = (TileEntityWoodConstruct)tile;
				te.data.set((y+(z*d)));
				te.woodTypes[(y+(z*d))] = (byte) is.getItemDamage();

				te.broadcastPacketInRange(te.createUpdatePacket((y+(z*d)), (byte) is.getItemDamage()));
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

	Icon[] icons = new Icon[Global.WOOD_ALL.length];
	@Override
	public void registerIcons(IconRegister registerer)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "wood/"+Global.WOOD_ALL[i]+" Plank");
		}
	}

	@Override
	public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < Global.WOOD_ALL.length; i++) {
			list.add(new ItemStack(this,1,i));
		}
	}
}
