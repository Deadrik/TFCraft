package com.bioxx.tfc.Blocks.Flora;

import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.IIcon;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Blocks.BlockTerraContainer;
import com.bioxx.tfc.Core.Recipes;
import com.bioxx.tfc.Core.Schematic;
import com.bioxx.tfc.TileEntities.TETreeLog;
import com.bioxx.tfc.api.TFCBlocks;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TreeRegistry;
import com.bioxx.tfc.api.TreeSchematic;
import com.bioxx.tfc.api.Constant.Global;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogNatural extends BlockTerraContainer
{
	String[] woodNames;
	int searchDist = 10;
	static int damage = 0;
	boolean isStoneTool = false;
	public IIcon[] sideIcons;
	public IIcon[] innerIcons;
	public IIcon[] rotatedSideIcons;

	public BlockLogNatural()
	{
		super(Material.wood);
		this.setTickRandomly(true);
		this.woodNames = new String[16];
		System.arraycopy(Global.WOOD_ALL, 0, woodNames, 0, 16);
		this.sideIcons = new IIcon[woodNames.length];
		this.innerIcons = new IIcon[woodNames.length];
		this.rotatedSideIcons = new IIcon[woodNames.length];
	}

	@Override
	public void updateTick(World world, int x, int y, int z, Random rand)
	{
		if(!world.isRemote)
		{
			//If the base TE somehow gets destroyed and the ProcessTree was not called
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TETreeLog)
			{
				TETreeLog teLog = (TETreeLog) te;
				TileEntity teR = world.getTileEntity(teLog.baseX, teLog.baseY, teLog.baseZ);
				if(!teLog.isBase && teR == null)
					world.setBlock(x, y, z, Blocks.air, 0, 0x2);

				if(teLog.isBase)
				{
					if(!canTreeStay(world, x, y - 1, z))
						ProcessTree(world, null, x, y, z, false);
				}
			}
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1, int var2)
	{
		return new TETreeLog();
	}

	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@SideOnly(Side.CLIENT)
	@Override
	public void getSubBlocks(Item item, CreativeTabs tabs, List list)
	{
		for(int i = 0; i < woodNames.length; i++)
			list.add(new ItemStack(this, 1, i));
	}

	@Override
	public float getBlockHardness(World world, int x, int y, int z)
	{
		return this.blockHardness;
	}

	@Override
	public int damageDropped(int dmg)
	{
		return dmg;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IIcon getIcon(int side, int meta)
	{
		if (side == 0 || side == 1)
			return innerIcons[meta];
		return sideIcons[meta];
	}

	@Override
	public void registerBlockIcons(IIconRegister reg)
	{
		for(int i = 0; i < woodNames.length; i++)
		{
			sideIcons[i] = reg.registerIcon(Reference.ModID + ":" + "wood/trees/" + woodNames[i] + " Log");
			innerIcons[i] = reg.registerIcon(Reference.ModID + ":" + "wood/trees/" + woodNames[i] + " Log Top");
			rotatedSideIcons[i] = reg.registerIcon(Reference.ModID + ":" + "wood/trees/" + woodNames[i] + " Log Side");
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int x, int y, int z, int meta)
	{
	}

	@Override
	public void onBlockHarvested(World world, int x, int y, int z, int side, EntityPlayer entityplayer)
	{
	}

	@Override
	public boolean canBlockStay(World world, int x, int y, int z)
	{
		return true;
	}

	@Override
	public boolean canBeReplacedByLeaves(IBlockAccess world, int x, int y, int z)
	{
		return false;
	}

	@Override
	public void onBlockExploded(World world, int x, int y, int z, Explosion explosion)
	{
		TileEntity te = world.getTileEntity(x, y, z);
		if(te != null && te instanceof TETreeLog)
		{
			TETreeLog teLog = (TETreeLog) te;
			TileEntity teR = world.getTileEntity(teLog.baseX, teLog.baseY, teLog.baseZ);
			if(teR != null && teR instanceof TETreeLog)
			{
				TETreeLog teRoot = (TETreeLog) teR;
				if(!teRoot.getDoingExplosion())
				{
					teRoot.setDoingExplosion(true);
					isStoneTool = true;
					ProcessTree(world, null, teLog.baseX, teLog.baseY, teLog.baseZ, true);
				}
			}
		}
	}

	@Override
	public boolean removedByPlayer(World world, EntityPlayer player, int x, int y, int z, boolean willHarvest)
	{
		if(!world.isRemote)
		{
			ItemStack equip = player.getCurrentEquippedItem();
			if(equip != null)
			{
				isStoneTool = false;
				for(int cnt = 0; cnt < TFCItems.StoneTools.length && !isStoneTool; cnt++)
				{
					if(equip.getItem() == TFCItems.StoneTools[cnt])
						isStoneTool = true;
				}
			}

			boolean ret = ProcessTree(world, player, x, y, z, !player.capabilities.isCreativeMode);

			if(ret)
				dmgTool(world, player);
			/*else if(equip != null && isValidTool(equip.getItem()))
				player.addChatMessage(new ChatComponentText(StatCollector.translateToLocal("msg.noTreeChop")));*/

			return ret;
		}
		return false;
	}

	@Override
	public Item getItemDropped(int i, Random random, int j)
	{
		return TFCItems.Logs;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, Block b)
	{
		//If the ground
		if (!world.isRemote)
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TETreeLog)
			{
				TETreeLog teLog = (TETreeLog) te;
				if(y == teLog.baseY - 1)
				{
					if(!canTreeStay(world, teLog.baseX, teLog.baseY - 1, teLog.baseZ))
						ProcessTree(world, null, x, y, z, false);
				}
			}
		}
	}

	//*****************
	// Private methods
	//*****************
	private boolean ProcessTree(World world, EntityPlayer player, int x, int y, int z, boolean dropItem)
	{
		if(!world.isRemote)
		{
			TileEntity te = world.getTileEntity(x, y, z);
			if(te != null && te instanceof TETreeLog)
			{
				TETreeLog teLog = (TETreeLog) te;

				//Allow chopping a tree only 1 blocks above the base block.
				//Being able to cut down a tree by chopping a branch block is strange.
				if(!teLog.isBase && (y - teLog.baseY) > 1 && !player.capabilities.isCreativeMode) return false;

				if(!teLog.isBase)
					return ProcessTree(world, player, teLog.baseX, teLog.baseY, teLog.baseZ, dropItem);

				TreeSchematic schem = TreeRegistry.instance.getTreeSchematic(teLog.treeID, teLog.schemIndex, teLog.growthStage);
				if(schem == null)
				{
					//And this would be a BUG!
					System.out.println("Tree schematic is NULL, this is a BUG!!! -> ID:" + teLog.treeID + "  Index:" + teLog.schemIndex + "  Stage:" + teLog.growthStage);
					return false;
				}

				if(player != null && !player.capabilities.isCreativeMode)
				{
					//Check if a valid tool is being used and is not too damaged to chop this tree down
					ItemStack equip = player.getCurrentEquippedItem();

					if(equip == null)
					{
						//Breaking logs with bare hands is not allowed
						return false;
					}
					else
					{
						if(!isValidTool(equip.getItem())) return false;
						if(isValidTool(equip.getItem()) && (equip.getMaxDamage() - equip.getItemDamage()) < schem.getLogCount()) return false;
					}
				}

				int index;
				int id;
				damage = 0;
				for(int schemY = 0; schemY < schem.getSizeY(); schemY++)
				{
					for(int schemZ = 0; schemZ < schem.getSizeZ(); schemZ++)
					{
						for(int schemX = 0; schemX < schem.getSizeX(); schemX++)
						{
							index = schemX + schem.getSizeX() * (schemZ + schem.getSizeZ() * schemY);
							id = schem.getBlockArray()[index];
							if(id != 0)
								ProcessRot(world, x, y, z, teLog.treeID, schem, schemX+1, schemY, schemZ+1, teLog.rotation, dropItem);
						}
					}
				}
				world.setBlock(x, y, z, Blocks.air);
				return true;
			}
		}
		return false;
	}

	private void ProcessRot(World world, int treeX, int treeY, int treeZ, int meta,
			Schematic schem, int schemX, int schemY, int schemZ, int rot, boolean dropItem)
	{
		int localX = treeX + schem.getCenterX() - schemX;
		int localZ = treeZ + schem.getCenterZ() - schemZ;
		int localY = treeY + schemY;

		if(rot == 0)
		{
			localX = treeX - schem.getCenterX() + schemX;
			localZ = treeZ - schem.getCenterZ() + schemZ;
		}
		else if(rot == 1)
		{
			localX = treeX - schem.getCenterX() + schemX;
			localZ = treeZ + schem.getCenterZ() - schemZ;
		}
		else if(rot == 2)
		{
			localX = treeX + schem.getCenterX() - schemX;
			localZ = treeZ - schem.getCenterZ() + schemZ;
		}

		//Get the block that occupies the local coordinate
		Block localBlockID = world.getBlock(localX, localY, localZ);

		if(localBlockID == TFCBlocks.LogNatural || localBlockID == TFCBlocks.LogNatural2)
		{
			TETreeLog log = (TETreeLog) world.getTileEntity(localX, localY, localZ);
			if(log != null && ((log.baseX == treeX && log.baseY == treeY && log.baseZ == treeZ)))
			{
				damage++;
				world.setBlock(localX, localY, localZ, Blocks.air, 0, 0x2);
				if(dropItem)
				{
					if(isStoneTool)
					{
						if(world.rand.nextInt(10) != 0)
							dropBlockAsItem(world, localX, localY, localZ, new ItemStack(TFCItems.Logs, 1, meta));
					}
					else
						dropBlockAsItem(world, localX, localY, localZ, new ItemStack(TFCItems.Logs, 1, meta));
				}
			}
		}
		else if(localBlockID == TFCBlocks.Leaves || localBlockID == TFCBlocks.Leaves2)
		{
			if(meta > 15)
				meta -= 16;
			if(world.getBlockMetadata(localX, localY, localZ) == meta)
				world.setBlock(localX, localY, localZ, Blocks.air, 0, 0x2);
		}
	}

	private void dmgTool(World world, EntityPlayer entityplayer)
	{
		//Tools that will get damaged when used to chop a tree down.
		if(!world.isRemote)
		{
			ItemStack equip = entityplayer.getCurrentEquippedItem();
			if(equip!=null && isValidTool(equip.getItem()))
			{
				if(damage + equip.getItemDamage() > equip.getMaxDamage())
				{
					ItemStack stack = entityplayer.inventory.getCurrentItem();
					entityplayer.renderBrokenItemStack(stack);
					entityplayer.worldObj.playSoundAtEntity(entityplayer, "random.break", 0.8F, 0.8F + entityplayer.worldObj.rand.nextFloat() * 0.4F);
					entityplayer.destroyCurrentEquippedItem();
				}
				else
					equip.damageItem(damage, entityplayer);
			}

			//If this method gets called more then ones by mistake,
			//make sure the second call will not make any damage.
			damage = 0;
		}
	}

	private boolean isValidTool(Item tool)
	{
		boolean isValid = false;
		if(tool != null)
		{
			for(int cnt = 0; cnt < Recipes.Axes.length && !isValid; cnt++)
			{
				if(tool == Recipes.Axes[cnt]) isValid = true;
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < Recipes.Saws.length && !isValid; cnt++)
				{
					if(tool == Recipes.Saws[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < Recipes.Hammers.length && !isValid; cnt++)
				{
					if(tool == Recipes.Hammers[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < Recipes.Knives.length && !isValid; cnt++)
				{
					if(tool == Recipes.Knives[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < Recipes.Chisels.length && !isValid; cnt++)
				{
					if(tool == Recipes.Chisels[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < Recipes.Scythes.length && !isValid; cnt++)
				{
					if(tool == Recipes.Scythes[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < TFCItems.Swards.length && !isValid; cnt++)
				{
					if(tool == TFCItems.Swards[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < TFCItems.Maces.length && !isValid; cnt++)
				{
					if(tool == TFCItems.Maces[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < TFCItems.Javelins.length && !isValid; cnt++)
				{
					if(tool == TFCItems.Javelins[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < TFCItems.ProPicks.length && !isValid; cnt++)
				{
					if(tool == TFCItems.ProPicks[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < TFCItems.Picks.length && !isValid; cnt++)
				{
					if(tool == TFCItems.Picks[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < TFCItems.Shovels.length && !isValid; cnt++)
				{
					if(tool == TFCItems.Shovels[cnt]) isValid = true;
				}
			}
			if(!isValid)
			{
				for(int cnt = 0; cnt < TFCItems.Hoes.length && !isValid; cnt++)
				{
					if(tool == TFCItems.Hoes[cnt]) isValid = true;
				}
			}
		}
		return isValid;
	}

	private boolean canTreeStay(World w, int x, int y, int z)
	{
		int k = 0;
		for(int i = -1; i < 2; i++)
		{
			for(int j = -1; j < 2; j++)
			{
				if(w.isAirBlock(x - i, y, z - j)) k++;
			}
		}
		if(k < 4) return true;
		return false;
	}
}
