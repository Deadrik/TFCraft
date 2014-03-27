package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.IPipeConnectable;
import TFC.API.TFCOptions;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockPipeBasic extends BlockTerra implements IPipeConnectable
{
	Random rand = new Random();
	protected IIcon icon[] = new IIcon[3];
	public BlockPipeBasic(Material material) 
	{
		super(material);
		this.setCreativeTab(CreativeTabs.tabDecorations);
	}

	@Override
	public int onBlockPlaced(World world, int i, int j, int k, int side, float hitX, float hitY, float hitZ, int meta)
	{
		int n = 3;
		if(side == 0) {
			n = 0;
		}
		else if(side == 1) {
			n = 5;
		}
		else if(side == 2) {
			n = 3;
		}
		else if(side == 3) {
			n = 2;
		}
		else if(side == 4) {
			n = 4;
		}
		else if(side == 5) {
			n = 1;
		}
		world.setBlockMetadataWithNotify(i, j, k, n, 0);
		return n;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess w, int x, int y, int z)
	{
		return false;
	}

	@Override
	public IIcon getIcon(IBlockAccess par1IBlockAccess, int par2, int par3, int par4, int par5)
	{
		int l = getSide(par1IBlockAccess,par2, par3, par4);
		return icon[2];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		icon[0] = iconRegisterer.registerIcon(Reference.ModID + ":" + "metal/Pipe joint face");
		icon[1] = iconRegisterer.registerIcon(Reference.ModID + ":" + "metal/Pipe joint side");
		icon[2] = iconRegisterer.registerIcon(Reference.ModID + ":" + "metal/pipe copy");
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)  
	{
		if(TFCOptions.enableDebugMode && world.isRemote)
		{
			int metadata = getSide(world,x, y, z);
			System.out.println("Meta = "+(new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
		return false;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.pipeRenderId;
	}

	@Override
	public boolean canConnectTo(IBlockAccess world, int desiredFace, int x, int y, int z) {

		return (getSide(world,x, y, z)) == desiredFace;
	}


	@Override
	public boolean feed(IBlockAccess world, int fedFace, int x, int y, int z, boolean isLiquid) {
		//Simulates decay over distance and helps prevent pipe tracks that are too long.
		if((rand.nextInt(32)!=0 || isLiquid)&& getFinalBit(world,x,y,z)==0){
			//Check surrounding blocks for receiversBlock block
			ArrayList list = new ArrayList<int[]>();
			//block is the block being checked, feedFace is the direction the block would feed from.
			Block block;
			int feedFace;

			block = world.getBlock(x, y+1, z);
			feedFace = 5;

			if(block != null && Math.abs(feedFace - 5)!=fedFace && block instanceof IPipeConnectable &&
					((IPipeConnectable)block).canConnectTo(world, feedFace, x, y+1, z)){
				list.add(new int[]{x,y+1,z,((IPipeConnectable)block).hasToBeOnlyOption(),feedFace});
			}

			block = world.getBlock(x-1, y, z);
			feedFace = 4;

			if(block != null && Math.abs(feedFace - 5)!=fedFace && block instanceof IPipeConnectable &&
					((IPipeConnectable)block).canConnectTo(world, feedFace, x-1, y, z)){
				list.add(new int[]{x-1,y,z,((IPipeConnectable)block).hasToBeOnlyOption(),feedFace});
			}

			block = world.getBlock(x, y, z-1);
			feedFace = 3;

			if(block != null && Math.abs(feedFace - 5)!=fedFace && block instanceof IPipeConnectable &&
					((IPipeConnectable)block).canConnectTo(world, feedFace, x, y, z-1)){
				list.add(new int[]{x,y,z-1,((IPipeConnectable)block).hasToBeOnlyOption(),feedFace});
			}

			block = world.getBlock(x, y, z+1);
			feedFace = 2;
			if(block != null && Math.abs(feedFace - 5)!=fedFace && block instanceof IPipeConnectable &&
					((IPipeConnectable)block).canConnectTo(world, feedFace, x, y, z+1)){
				list.add(new int[]{x,y,z+1,((IPipeConnectable)block).hasToBeOnlyOption(),feedFace});
			}

			block = world.getBlock(x+1, y, z);
			feedFace = 1;

			if(block != null && Math.abs(feedFace - 5)!=fedFace && block instanceof IPipeConnectable &&
					((IPipeConnectable)block).canConnectTo(world, feedFace, x+1, y, z)){
				list.add(new int[]{x+1,y,z,((IPipeConnectable)block).hasToBeOnlyOption(),feedFace});
			}

			block = world.getBlock(x, y-1, z);
			feedFace = 0;

			if(block != null && Math.abs(feedFace - 5)!=fedFace && block instanceof IPipeConnectable &&
					((IPipeConnectable)block).canConnectTo(world, feedFace, x, y-1, z)){
				list.add(new int[]{x,y-1,z,((IPipeConnectable)block).hasToBeOnlyOption(),feedFace});
			}

			//point represents the 5 variable int[] stored in list. the first 3 represent coords, 4th is whether it needs to be singular
			//fifth is the face direction of target block from this block.
			int[] point = new int[]{-1,-1,-1,-1,-1};
			int listLengthStart = list.size();
			int index;
			while(point[0] == -1 && list.size() > 0){
				index = rand.nextInt(list.size());
				point = (int[])list.get(index);
				if(point[3]==1 && listLengthStart > 1)
				{
					list.remove(index);
					point[0]=-1;
				}
			}

			//indicates there is a valid position stored here
			if(point[0]!=-1){
				return ((IPipeConnectable)(world.getBlock(point[0], point[1], point[2]))).feed(world, getSide(world,point[0], point[1], point[2]), point[0], point[1], point[2],isLiquid);
			}
		}
		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public int hasToBeOnlyOption() {
		return 0;
	}

	@Override
	public int getSide(IBlockAccess world, int x, int y, int z) {
		int l = world.getBlockMetadata(x, y, z);
		return l&7;
	}

	@Override
	public int getFinalBit(IBlockAccess world, int x, int y, int z) {
		int l = world.getBlockMetadata(x, y, z);
		return l >> 3;
	}
}
