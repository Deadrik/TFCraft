package com.bioxx.tfc.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.particle.EffectRenderer;
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

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.CollisionRayTraceStandard;
import com.bioxx.tfc.Core.TFC_Textures;
import com.bioxx.tfc.TileEntities.TEMetalSheet;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.Interfaces.ICustomCollision;

public class BlockMetalSheet extends BlockTerraContainer implements ICustomCollision
{
	public IIcon[] icons;
	public String[] metalNames = {"Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel","Brass","Bronze",
			"Copper","Gold","Wrought Iron","Lead","Nickel","Pig Iron","Platinum","Red Steel","Rose Gold","Silver","Steel",
			"Sterling Silver","Tin","Zinc", "Electrum", "Cupronickel", "Osmium", "Aluminum", "Tungsten"};
	public BlockMetalSheet()
	{
		super(Material.iron);
		icons = new IIcon[metalNames.length];
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
	public boolean shouldSideBeRendered(IBlockAccess blockAccess, int x, int y, int z, int side)
	{
		return true;
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		if (world.getTileEntity(i, j, k) instanceof TEMetalSheet)
		{
			TEMetalSheet te = (TEMetalSheet) world.getTileEntity(i, j, k);
			if (te.sheetStack != null)
			{
				int stack = 0;
				if(te.topExists()) stack++;
				if(te.bottomExists()) stack++;
				if(te.northExists()) stack++;
				if(te.southExists()) stack++;
				if(te.eastExists()) stack++;
				if(te.westExists()) stack++;
				te.sheetStack.stackSize = stack;
				EntityItem ei = new EntityItem(world, i, j, k, te.sheetStack);
				world.spawnEntityInWorld(ei);					
			}
			else
				TerraFirmaCraft.LOG.error("Metal sheet block (" + i + ", " + j + ", " + k + ") being broken contains null sheetstack. Please report this on the forums.");
		}
	}

	@Override
	public void onBlockExploded(World world, int i, int j, int k, Explosion explosion)
	{
		TEMetalSheet te = (TEMetalSheet)world.getTileEntity(i, j, k);
		if ( te != null )
			te.clearSides();

		super.onBlockExploded(world, i, j, k, explosion);
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
			icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "metal/"+metalNames[i]);

		TFC_Textures.sheetBismuth = icons[0];
		TFC_Textures.sheetBismuthBronze = icons[1];
		TFC_Textures.sheetBlackBronze = icons[2];
		TFC_Textures.sheetBlackSteel = icons[3];
		TFC_Textures.sheetBlueSteel = icons[4];
		TFC_Textures.sheetBrass = icons[5];
		TFC_Textures.sheetBronze = icons[6];
		TFC_Textures.sheetCopper = icons[7];
		TFC_Textures.sheetGold = icons[8];
		TFC_Textures.sheetWroughtIron = icons[9];
		TFC_Textures.sheetLead = icons[10];
		TFC_Textures.sheetNickel = icons[11];
		TFC_Textures.sheetPigIron = icons[12];
		TFC_Textures.sheetPlatinum = icons[13];
		TFC_Textures.sheetRedSteel = icons[14];
		TFC_Textures.sheetRoseGold = icons[15];
		TFC_Textures.sheetSilver = icons[16];
		TFC_Textures.sheetSteel = icons[17];
		TFC_Textures.sheetSterlingSilver = icons[18];
		TFC_Textures.sheetTin = icons[19];
		TFC_Textures.sheetZinc = icons[20];
		TFC_Textures.sheetElectrum = icons[21];
		TFC_Textures.sheetCupronickel = icons[22];
		TFC_Textures.sheetOsmium = icons[23];
		TFC_Textures.sheetAluminum = icons[24];
		TFC_Textures.sheetTungsten = icons[25];
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TEMetalSheet();
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta >= 0 && meta < icons.length)
			return icons[meta];
		return icons[19];
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

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, List list)
	{
		TEMetalSheet te = (TEMetalSheet)world.getTileEntity(i, j, k);
		double f0 = 0.0625;
		double f1 = 0.9375;
		double yMax = 1;
		double yMin = 0;

		if(te.topExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0, f1, 0.0, 1.0, 1.0, 1.0), 0});
		if(te.bottomExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0.0, 0, 0.0, 1.0, f0, 1.0), 1});
		if(te.northExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0, yMin, 0, 1, yMax, f0), 2});
		if(te.southExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0, yMin, f1, 1, yMax, 1), 3});
		if(te.eastExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(0, yMin, 0, f0, yMax, 1), 4});
		if(te.westExists())
			list.add(new Object[]{AxisAlignedBB.getBoundingBox(f1, yMin, 0, 1, yMax, 1), 5});
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
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
		case 0:return te.bottomExists();
		case 1:return te.topExists();
		case 2:return te.northExists();
		case 3:return te.southExists();
		case 4:return te.eastExists();
		case 5:return te.westExists();
		default: return false;
		}
	}

	@Override
	public boolean isSideSolid(IBlockAccess world, int x, int y, int z, ForgeDirection side)
	{
		TEMetalSheet te = (TEMetalSheet)world.getTileEntity(x, y, z);
		switch(side)
		{
		case DOWN:return te.bottomExists();
		case UP:return te.topExists();
		case NORTH:return te.northExists();
		case SOUTH:return te.southExists();
		case EAST:return te.eastExists();
		case WEST:return te.westExists();
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

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addHitEffects(World worldObj, MovingObjectPosition target, EffectRenderer effectRenderer)
	{
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean addDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
	{
		return world.getBlock(x, y, z) == this;
	}
}
