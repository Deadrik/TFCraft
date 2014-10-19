package com.bioxx.tfc.Blocks.Vanilla;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.ITileEntityProvider;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.IIcon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TFCBlocks;
import com.bioxx.tfc.TFCItems;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TreeRegistry;
import com.bioxx.tfc.Core.TreeSchematic;
import com.bioxx.tfc.Items.Tools.ItemCustomScythe;
import com.bioxx.tfc.TileEntities.TETreeLog;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCustomLeaves extends BlockLeaves implements IShearable, ITileEntityProvider
{
	protected int adjacentTreeBlocks[][][];
	protected String[] woodNames;
	protected IIcon[] icons;
	protected IIcon[] iconsOpaque;

	public BlockCustomLeaves()
	{
		super();
		this.woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, this.woodNames, 0, 16);
		this.icons = new IIcon[16];
		this.iconsOpaque = new IIcon[16];
		this.setTickRandomly(true);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		// Leaves are not added to the creative tab
	}

	@Override
	public boolean getBlocksMovement(IBlockAccess bAccess, int x, int y, int z)
	{
		return true;
	}

	@Override
	public int colorMultiplier(IBlockAccess bAccess, int x, int y, int z)
	{
		return TerraFirmaCraft.proxy.foliageColorMultiplier(bAccess, x, y, z);
	}

	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World world, int x, int y, int z)
	{
		return null;
	}

	@Override
	public void onEntityCollidedWithBlock(World world, int x, int y, int z, Entity entity)
	{
		entity.motionX *= 0.8D;
		entity.motionZ *= 0.8D;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess world, int x, int y, int z, int side)
	{
		/*Block block = world.getBlock(x, y, z);
		if(!Minecraft.isFancyGraphicsEnabled() && block == this) 
			return false;*/

		return side == 0 && this.minY > 0.0D ? true : (side == 1 && this.maxY < 1.0D ? true : (side == 2 && this.minZ > 0.0D ? true : (side == 3 && this.maxZ < 1.0D ? true : (side == 4 && this.minX > 0.0D ? true : (side == 5 && this.maxX < 1.0D ? true : !world.getBlock(x, y, z).isOpaqueCube())))));
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		onNeighborBlockChange(world, x, y, z, null);
	}

	@Override
	public void beginLeavesDecay(World world, int x, int y, int z)
	{
		//We don't do vanilla leaves decay
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		if (!world.isRemote)
		{
			int meta = world.getBlockMetadata(x, y, z);
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TETreeLog)
			{
				TETreeLog teLeaves = (TETreeLog) te;
				TileEntity teR = world.getTileEntity(teLeaves.baseX, teLeaves.baseY, teLeaves.baseZ);
				if(teR == null) world.setBlockToAir(x, y, z);
				if(teR != null && teR instanceof TETreeLog)
				{
					TETreeLog teRoot = (TETreeLog) teR;
					TreeSchematic shem = TreeRegistry.instance.getTreeSchematic(teRoot.treeID, teRoot.schemIndex);
					if(shem != null && !world.checkChunksExist(z - shem.getCenterX(), y, z - shem.getCenterZ(), z + shem.getCenterX(), y, z + shem.getCenterZ()))
						if(!canLeavesStay(world, x, y, z, shem, meta))
							world.setBlockToAir(x, y, z);
				}
			}
		}
	}

	@Override
	public int quantityDropped(Random rand)
	{
		return rand.nextInt(20) != 0 ? 0 : 1;
	}

	@Override
	public Item getItemDropped(int i, Random rand, int j)
	{
		return Item.getItemFromBlock(TFCBlocks.Sapling);
	}

	@Override
	public void dropBlockAsItemWithChance(World world, int x, int y, int z, int meta, float f, int i1)
	{
		if (!world.isRemote) {}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int meta)
	{
		ItemStack itemstack = entityplayer.inventory.getCurrentItem();
		if (!world.isRemote && itemstack != null && itemstack.getItem() instanceof ItemCustomScythe)
		{
			for(int x = -1; x < 2; x++)
			{
				for(int z = -1; z < 2; z++)
				{
					for(int y = -1; y < 2; y++)
					{
						if(world.getBlock( i + x, j + y, k + z).getMaterial() == Material.leaves &&
								entityplayer.inventory.getStackInSlot(entityplayer.inventory.currentItem) != null)
						{
							entityplayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
							entityplayer.addExhaustion(0.045F);
							if(world.rand.nextInt(100) < 11)
								dropBlockAsItem(world, i + x, j + y, k + z, new ItemStack(TFCItems.Stick, 1));
							else if (world.rand.nextInt(100) < 4)
								dropSapling(world, i + x, j + y, k + z, meta);
							removeLeaves(world, i + x, j + y, k + z);
							super.harvestBlock(world, entityplayer, i + x, j + y, k + z, meta);

							int ss = itemstack.stackSize;
							int dam = itemstack.getItemDamage() + 2;

							if(dam >= itemstack.getItem().getMaxDamage())
								entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, null);
							else
								entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, new ItemStack(itemstack.getItem(), ss, dam));
						}
					}
				}
			}
		}
		else if(!world.isRemote)
		{
			entityplayer.addStat(StatList.mineBlockStatArray[getIdFromBlock(this)], 1);
			entityplayer.addExhaustion(0.025F);
			if(world.rand.nextInt(100) < 28)
				dropBlockAsItem(world, i, j, k, new ItemStack(TFCItems.Stick, 1));
			else if (world.rand.nextInt(100) < 6)
				dropSapling(world, i, j, k, meta);;

			super.harvestBlock(world, entityplayer, i, j, k, meta);
		}
	}

	protected void dropSapling(World world, int x, int y, int z, int meta)
	{
		if (meta != 9 && meta != 15)
			dropBlockAsItem(world, x, y, z, new ItemStack(this.getItemDropped(0, null, 0), 1, meta));
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public IIcon getIcon(int side, int meta)
	{
		if(meta > woodNames.length - 1)
			meta = 0;
		if (TerraFirmaCraft.proxy.getGraphicsLevel())
			return this.icons[meta];
		else
			return this.iconsOpaque[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister iconRegisterer)
	{
		for(int i = 0; i < this.woodNames.length; i++)
		{
			this.icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/trees/" + this.woodNames[i] + " Leaves Fancy");
			this.iconsOpaque[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "wood/trees/" + this.woodNames[i] + " Leaves");
		}
	}

	@Override
	public String[] func_150125_e()
	{
		return this.woodNames;
	}

	@Override
	public boolean isShearable(ItemStack item, IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TETreeLog();
	}


	//*****************
	// Private methods
	//*****************
	private void removeLeaves(World world, int x, int y, int z)
	{
		dropBlockAsItem(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
		if(world.rand.nextInt(100) < 30)
			dropBlockAsItem(world, x, y, z, new ItemStack(TFCItems.Stick, 1));
		world.setBlockToAir(x, y, z);
	}

	private boolean canLeavesStay(World w, int x, int y, int z, TreeSchematic shem, int meta)
	{
		boolean canStay = false;
		int rad = 1;
		Block block;

		outerloop:
		{
			for(int yy = -rad; yy <= rad; yy++)
			{
				for(int xx = -rad; xx <= rad; xx++)
				{
					for(int zz = -rad; zz <= rad; zz++)
					{
						if (xx == 0 && yy == 0 && zz == 0) continue; //don't check current square

						block = w.getBlock(x + xx, y + yy, z + zz);
						if((block == TFCBlocks.LogNatural
								|| block == TFCBlocks.LogNatural2
								|| block == TFCBlocks.Leaves
								|| block == TFCBlocks.Leaves2)
								&& meta == w.getBlockMetadata(x + xx, y + yy, z + zz))
						{
							canStay = true;
							break outerloop;
						}
					}
				}
			}
		}
		if(!canStay) return false;

		if(canStay)
		{
			//TODO If there is a path of touching leaves to a logNatural block in the schematic, then leaves can stay
		}

		return canStay;
	}
}
