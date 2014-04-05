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
import net.minecraftforge.common.ForgeDirection;
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
	public Icon getIcon(int side, int meta)
	{
		return icons[19];
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
		if(te!= null)
			return icons[te.metalID];
		else
			return icons[19];
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
			{
				list.add(a);
			}
		}
	}

	@Override
	public MovingObjectPosition collisionRayTrace(World world, int x, int y, int z, Vec3 player, Vec3 view)
	{
		return CollisionRayTraceStandard.collisionRayTrace(this, world, x, y, z, player, view);
	}

	@Override
	public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		TEMetalSheet te = (TEMetalSheet)world.getBlockTileEntity(x, y, z);
		switch(side)
		{
		case UP:return te.TopExists();
		case DOWN:return te.BottomExists();
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
