package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.ICustomCollision;
import TFC.Core.CollisionRayTraceStandard;
import TFC.TileEntities.TEMetalSheet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
public class BlockMetalSheet extends BlockTerraContainer implements ICustomCollision
{
	public Icon[] icons;
	public String[] metalNames = {"Bismuth Bronze","Bismuth","Black Bronze","Black Steel","Blue Steel","Brass","Bronze",
			"Copper","Gold","Lead","Nickel","Pig Iron","Platinum","Red Steel","Rose Gold","Silver","Steel",
			"Sterling Silver","Tin","Wrought Iron","Zinc"};
	public BlockMetalSheet(int i)
	{
		super(i, Material.iron);
		icons = new Icon[21];
		this.setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public int idDropped(int i, Random random, int j)
	{
		return 0;
	}

	@Override
	public void onBlockPreDestroy(World world, int i, int j, int k, int meta) 
	{
		TEMetalSheet te = (TEMetalSheet)world.getBlockTileEntity(i, j, k);
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
		TEMetalSheet te = (TEMetalSheet)world.getBlockTileEntity(i, j, k);
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
	public void registerIcons(IconRegister iconRegisterer)
	{
		for(int i = 0; i < icons.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "metal/"+metalNames[i]);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TEMetalSheet();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public Icon getBlockTexture(IBlockAccess access, int i, int j, int k, int meta)
	{
		TEMetalSheet te = (TEMetalSheet) access.getBlockTileEntity(i, j, k);
		return icons[te.metalID];
	}

	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, List list)
	{
		TEMetalSheet te = (TEMetalSheet)world.getBlockTileEntity(i, j, k);
		double f0 = 0.0625;
		double f1 = 0.9375;
		double yMax = 1;
		double yMin = 0;

		if(te.TopExists())
			list.add(AxisAlignedBB.getBoundingBox(0.0, f1, 0.0, 1.0, 1.0, 1.0));
		if(te.BottomExists())
			list.add(AxisAlignedBB.getBoundingBox(0.0, 0, 0.0, 1.0, f0, 1.0));
		if(te.NorthExists())
			list.add(AxisAlignedBB.getBoundingBox(0, yMin, 0, 1, yMax, f0));
		if(te.SouthExists())
			list.add(AxisAlignedBB.getBoundingBox(0, yMin, f1, 1, yMax, 1));
		if(te.EastExists())
			list.add(AxisAlignedBB.getBoundingBox(0, yMin, 0, f0, yMax, 1));
		if(te.WestExists())
			list.add(AxisAlignedBB.getBoundingBox(f1, yMin, 0, 1, yMax, 1));
	}



	@Override
	public void addCollisionBoxesToList(World world, int i, int j, int k, AxisAlignedBB aabb, List list, Entity entity)
	{
		ArrayList<AxisAlignedBB> l = new ArrayList<AxisAlignedBB>();
		addCollisionBoxesToList(world,i,j,k,l);
		for(AxisAlignedBB a : l)
		{
			this.setBlockBounds((float)a.minX, (float)a.minY, (float)a.minZ, (float)a.maxX, (float)a.maxY, (float)a.maxZ);
			super.addCollisionBoxesToList(world, i, j, k, aabb, list, entity);
		}
		//this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
		//setBlockBoundsBasedOnState(world,i,j,k);
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		return CollisionRayTraceStandard.collisionRayTrace(this, world, x, y, z, player, view);
	}

	/*@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int x, int y, int z)
	{
		TEMetalSheet te = (TEMetalSheet) access.getBlockTileEntity(x, y, z);
		float minX = 1;
		float maxX = 0;
		float minY = 1;
		float maxY = 0;
		float minZ = 1;
		float maxZ = 0;

		if(te.TopExists())
		{
			maxY = Math.max(1f, maxY);
			minY = Math.min(0.9375f, minY);
		}

		if(te.BottomExists())
		{
			maxY = Math.max(0.0625f, maxY);
			minY = Math.min(0, minY);
		}

		if(te.NorthExists())
		{
			maxZ = Math.max(0.0625f, maxZ);
			minZ = Math.min(0, minZ);
		}

		if(te.SouthExists())
		{
			maxZ = Math.max(1f, maxZ);
			minZ = Math.min(0.9375f, minZ);
		}

		if(te.EastExists())
		{
			maxX = Math.max(0.0625f, maxX);
			minX = Math.min(0, minX);
		}

		if(te.WestExists())
		{
			maxX = Math.max(1f, maxX);
			minX = Math.min(0.9375f, minX);
		}

		setBlockBounds(minX,minY,minZ,maxX,maxY,maxZ);
	}*/

	/*public void setBlockBoundsBasedOnSelection(IBlockAccess access, int x, int y, int z) 
	{
		setBlockBoundsBasedOnState(access,x,y,z);
	}*/
}
