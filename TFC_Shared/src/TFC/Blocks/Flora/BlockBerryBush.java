package TFC.Blocks.Flora;

import java.util.List;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Time;
import TFC.Food.FloraIndex;
import TFC.Food.FloraManager;
import TFC.TileEntities.TEBerryBush;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockBerryBush extends BlockTerraContainer
{
	public static Icon[] icons;
	public static Icon[] iconsBerries;
	public static String[] MetaNames;

	public static final int Wintergreen = 0;
	public static final int Blueberry = 1;
	public static final int Raspberry = 2;
	public static final int Strawberry = 3;
	public static final int Blackberry = 4;
	public static final int Bunchberry = 5;
	public static final int Cranberry = 6;
	public static final int Snowberry = 7;
	public static final int Elderberry = 8;
	public static final int Gooseberry = 9;
	public static final int Cloudberry = 10;

	public BlockBerryBush(int par1)
	{
		super(par1, Material.plants);

		MetaNames = new String[]{"Wintergreen", "Blueberry", "Raspberry", "Strawberry", "Blackberry", "Bunchberry", "Cranberry", 
				"Snowberry", "Elderberry", "Gooseberry", "Cloudberry"};
		icons = new Icon[MetaNames.length];
		iconsBerries = new Icon[MetaNames.length];
		this.setTickRandomly(true);
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int id, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < MetaNames.length; i++) {
			list.add(new ItemStack(id, 1, i));
		}
	}

	@Override
	public void setBlockBoundsForItemRender() 
	{
		setBlockBounds(0, 0, 0, 1, 1, 1);
	}

	@Override
	public void setBlockBoundsBasedOnState(IBlockAccess access, int i, int j, int k) 
	{
		int _meta = access.getBlockMetadata(i, j, k);

		float minX = 0.1f;
		float minZ = 0.1f;
		float maxX = 0.9f;
		float maxZ = 0.9f;
		float maxY = 1f;

		if(isSamePlant(access, i-1, j, k, _meta)) {
			minX = 0;
		}
		if(isSamePlant(access, i+1, j, k, _meta)) {
			maxX = 1;
		}
		if(isSamePlant(access, i, j, k-1, _meta)) {
			minZ = 0;
		}
		if(isSamePlant(access, i, j, k+1, _meta)) {
			maxZ = 1;
		}
		if(isSamePlant(access, i, j+1, k, _meta)) {
			maxY = 1;
		}

		switch(_meta)
		{
		case 0://Wintergreen
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 1://Blueberries	
		{
			maxY = 0.85f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 2://Raspberries	
		{
			maxY = 0.85f;
			if(isSamePlant(access, i, j+1, k, _meta)) {
				maxY = 1;
			}
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 3://Strawberries
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 4://Blackberries	
		{
			maxY = 0.85f;
			if(isSamePlant(access, i, j+1, k, _meta)) {
				maxY = 1;
			}
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 5://Bunchberries	
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 6://Cranberries
		{
			maxY = 0.6f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 7://Snowberries	
		{
			maxY = 0.2f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 8://Elderberries	
		{
			maxY = 0.85f;
			if(isSamePlant(access, i, j+1, k, _meta)) {
				maxY = 1;
			}
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 9://Gooseberries	
		{
			maxY = 0.75f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		case 10://Cloudberries	
		{
			maxY = 0.35f;
			setBlockBounds(minX, 0, minZ, maxX, maxY, maxZ);
			return;
		}
		default:
			setBlockBounds(minX, 0, minZ, maxX, 1f, maxZ);
			return;
		}
	}

	private boolean isSamePlant(IBlockAccess access, int i, int j, int k, int meta)
	{
		if(access.getBlockId(i, j, k) == blockID && access.getBlockMetadata(i, j, k) == meta)
		{
			return true;
		}
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			FloraManager manager = FloraManager.getInstance();
			FloraIndex fi = FloraManager.getInstance().findMatchingIndex(getType(world.getBlockMetadata(i, j, k)));

			TEBerryBush te = (TEBerryBush) world.getBlockTileEntity(i, j, k);
			if(te != null && te.hasFruit)
			{
				te.hasFruit = false;
				te.dayHarvested = (int) TFC_Time.getTotalDays();
				te.broadcastPacketInRange(te.createUpdatePacket());
				dropBlockAsItem_do(world, i, j, k, fi.getOutput());
				return true;
			}
		}
		return false;
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random rand)
	{
		lifeCycle(world, i, j, k);
	}

	private void lifeCycle(World world, int i, int j, int k) {
		if(!world.isRemote)
		{
			if(!canBlockStay(world, i, j, k))
			{
				this.dropBlockAsItem(world, i, j, k, world.getBlockMetadata(i, j, k), 0);
				world.setBlockToAir(i, j, k);
				return;
			}

			TEBerryBush te = (TEBerryBush) world.getBlockTileEntity(i, j, k);
			if(te != null)
			{
				FloraIndex _fi = FloraManager.getInstance().findMatchingIndex(getType(world.getBlockMetadata(i, j, k)));
				float _temp = TFC_Climate.getHeightAdjustedTemp(i, j, k);

				if(_temp >= _fi.minTemp && _temp < _fi.maxTemp)
				{
					if(_fi.inHarvest(TFC_Time.currentMonth) && !te.hasFruit && TFC_Time.getMonthsSinceDay(te.dayHarvested) > _fi.fruitHangTime)
					{
						te.hasFruit = true;
						te.dayFruited = (int) TFC_Time.getTotalDays();
						te.broadcastPacketInRange(te.createUpdatePacket());
					}
				}
				else if(_temp < _fi.minTemp - 5 && _temp > _fi.maxTemp + 5)
				{
					if(te.hasFruit)
					{
						te.hasFruit = false;
						te.broadcastPacketInRange(te.createUpdatePacket());
					}
				}

				if(te.hasFruit && TFC_Time.getMonthsSinceDay(te.dayFruited) > _fi.fruitHangTime)
				{
					te.hasFruit = false;
					te.broadcastPacketInRange(te.createUpdatePacket());
				}
			}
		}
		else
		{
			world.getBlockTileEntity(i, j, k).validate();
		}
	}

	public String getType(int meta)
	{
		return this.MetaNames[meta];
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int i, int j, int k)
	{
		return null;
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

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister par1IconRegister)
	{
		for (int i = 0; i < icons.length; ++i)
		{
			icons[i] = par1IconRegister.registerIcon(Reference.ModID + ":" + "plants/"+MetaNames[i]);
			iconsBerries[i] = par1IconRegister.registerIcon(Reference.ModID + ":" + "plants/"+MetaNames[i]+" Berry");
		}
	}

	@SideOnly(Side.CLIENT)
	@Override
	public Icon getBlockTexture(IBlockAccess access, int i, int j, int k, int side)
	{
		int meta = access.getBlockMetadata(i, j, k);

		TEBerryBush te = (TEBerryBush) access.getBlockTileEntity(i, j, k);
		if(te != null && te.hasFruit)
		{
			return iconsBerries[meta];
		}

		return icons[meta];
	}

	@Override
	public Icon getIcon(int i, int meta)
	{
		return iconsBerries[meta];
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		return (world.getFullBlockLightValue(i, j, k) >= 8 || 
				world.canBlockSeeTheSky(i, j, k)) && 
				(this.canThisPlantGrowOnThisBlockID(world.getBlockId(i, j - 1, k)) || 
						isSamePlant(world, i, j-1, k, world.getBlockMetadata(i, j, k)));
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int par5)
	{
		super.onNeighborBlockChange(world, i, j, k, par5);
		lifeCycle(world, i, j, k);
	}

	protected boolean canThisPlantGrowOnThisBlockID(int id)
	{
		return TFC_Core.isSoil(id);
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3)
	{
		return this.blockID;
	}

	@Override
	public int damageDropped(int i) {
		return i;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TEBerryBush();
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
	{
		return true;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int i, int j, int k, Entity entity)
	{
		int _meta = world.getBlockMetadata(i, j, k);
		if(_meta == Blueberry ||_meta == Raspberry || _meta == Blackberry || 
				_meta == Elderberry || _meta == Gooseberry) {
			entity.motionX *= 0.7D;
			entity.motionZ *= 0.7D;
		}


		if(_meta == Raspberry || _meta == Blackberry) 
		{
			if(entity instanceof EntityLivingBase) {
				entity.attackEntityFrom(DamageSource.cactus, 5);
			}
		}

	}

}
