package com.bioxx.tfc.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.Item;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.util.ForgeDirection;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.Core.CollisionRayTraceStandard;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEMetalSheet;
import com.bioxx.tfc.api.Interfaces.ICustomCollision;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class BlockMetalSheet extends BlockTerraContainer implements ICustomCollision
{
	public IIcon[] icons;
	public String[] metalNames = {"Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel","Brass","Bronze",
			"Copper","Gold","Lead","Nickel","Pig Iron","Platinum","Red Steel","Rose Gold","Silver","Steel",
			"Sterling Silver","Tin","Wrought Iron","Zinc"};
	public BlockMetalSheet()
	{
		super(Material.iron);
		icons = new IIcon[21];
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public Item getItemDropped(int par1, Random par2Random, int par3)
	{
		return Item.getItemById(0);
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		//TODO: Make the block hardness depend on the panels in this block.
		return this.blockHardness;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		TEMetalSheet te = (TEMetalSheet)world.getTileEntity(i, j, k);
		int stack = 0;
		if(te.TopExists()) stack++;
		if(te.BottomExists()) stack++;
		if(te.NorthExists()) stack++;
		if(te.SouthExists()) stack++;
		if(te.EastExists()) stack++;
		if(te.WestExists()) stack++;
		te.sheetStack.stackSize = stack;
		EntityItem ei = new EntityItem(world, i, j, k, te.sheetStack);
		world.spawnEntityInWorld(ei);
	}

	@Override
	public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex) 
	{
		TEMetalSheet te = (TEMetalSheet)world.getTileEntity(i, j, k);
		te.clearSides();
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.metalsheetRenderId;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void registerBlockIcons(IIconRegister registerer)
	{
		for(int i = 0; i < icons.length; i++)
			icons[i] = registerer.registerIcon(Reference.ModID + ":" + "metal/"+metalNames[i]);

		TFC_Textures.SheetBismuth = icons[0];
		TFC_Textures.SheetBismuthBronze = icons[1];
		TFC_Textures.SheetBlackBronze = icons[2];
		TFC_Textures.SheetBlackSteel = icons[3];
		TFC_Textures.SheetBlueSteel = icons[4];
		TFC_Textures.SheetBrass = icons[5];
		TFC_Textures.SheetBronze = icons[6];
		TFC_Textures.SheetCopper = icons[7];
		TFC_Textures.SheetGold = icons[8];
		TFC_Textures.SheetLead = icons[9];
		TFC_Textures.SheetNickel = icons[10];
		TFC_Textures.SheetPigIron = icons[11];
		TFC_Textures.SheetPlatinum = icons[12];
		TFC_Textures.SheetRedSteel = icons[13];
		TFC_Textures.SheetRoseGold = icons[14];
		TFC_Textures.SheetSilver = icons[15];
		TFC_Textures.SheetSteel = icons[16];
		TFC_Textures.SheetSterlingSilver = icons[17];
		TFC_Textures.SheetTin = icons[18];
		TFC_Textures.SheetWroughtIron = icons[19];
		TFC_Textures.SheetZinc = icons[20];
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEMetalSheet();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(IBlockAccess access, int i, int j, int k, int meta)
	{
		TEMetalSheet te = (TEMetalSheet) access.getTileEntity(i, j, k);
		if(te!= null)
			return icons[te.metalID];
		else
			return icons[19];
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, List list)
	{
		TEMetalSheet te = (TEMetalSheet)world.getTileEntity(i, j, k);
		double f0 = 0.0625;
		double f1 = 0.9375;
		double yMax = 1;
		double yMin = 0;

		if(te.TopExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0, f1, 0.0, 1.0, 1.0, 1.0), 0});
		if(te.BottomExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0, 0, 0.0, 1.0, f0, 1.0), 1});
		if(te.NorthExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0, yMin, 0, 1, yMax, f0), 2});
		if(te.SouthExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0, yMin, f1, 1, yMax, 1), 3});
		if(te.EastExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0, yMin, 0, f0, yMax, 1), 4});
		if(te.WestExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(f1, yMin, 0, 1, yMax, 1), 5});
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity)
	{
		ArrayList<Object[]> l = new ArrayList<Object[]>();
		addCollisionBoxesToList(world,i,j,k,l);
		for(Object[] o : l)
		{
			AxisAlignedBB a = (AxisAlignedBB)o[0];
			if (a != null && aabb.intersectsWith(a))
				list.add(a);
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		return CollisionRayTraceStandard.collisionRayTrace(this, world, x, y, z, player, view);
	}

	@Override
	public boolean isBlockSolid(IBlockAccess world, int x, int y, int z, int side)
	{
		TEMetalSheet te = (TEMetalSheet)world.getTileEntity(x, y, z);
		switch(side)
		{
		case 0:return te.BottomExists();
		case 1:return te.TopExists();
		case 2:return te.NorthExists();
		case 3:return te.SouthExists();
		case 4:return te.EastExists();
		case 5:return te.WestExists();
		default: return false;
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		TEMetalSheet te = (TEMetalSheet)world.getTileEntity(x, y, z);
		switch(side)
		{
		case DOWN:return te.BottomExists();
		case UP:return te.TopExists();
		case NORTH:return te.NorthExists();
		case SOUTH:return te.SouthExists();
		case EAST:return te.EastExists();
		case WEST:return te.WestExists();
		default: return false;
		}
	}

	/*@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int x, int y, int z)
	{
		if(!world.isRemote)
		{
			Vec3 posVec = player.getPosition(1.0F);
			Vec3 lookVec = player.getLookVec();
			Vec3 vec32 = posVec.addVector(lookVec.xCoord * 5, lookVec.yCoord * 5, lookVec.zCoord * 5f);
			MovingObjectPosition mop = world.clip(posVec, vec32);
			if(mop != null)
			{
				Object[] o = (Object[])mop.hitInfo;
				if(o != null && o.length > 1)
				{
					TEMetalSheet te = (TEMetalSheet)world.getBlockTileEntity(x, y, z);
					te.toggleBySide((Integer)o[1], false);
					return true;
				}	
			}

			return world.setBlockToAir(x, y, z);
		}
		return false;
	}*/
}
