package TFC.Blocks.Terrain;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.API.Tools.IToolChisel;
import TFC.API.Util.Helper;
import TFC.Blocks.BlockTerra;
import TFC.Core.TFC_Sounds;
import TFC.Entities.EntityFallingStone;
import TFC.Items.Tools.ItemHammer;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCobble extends BlockTerra
{
	protected BlockCobble(int i, Material material)
	{
		super(i, material);
		this.setCreativeTab(CreativeTabs.tabBlock);
	}

	protected String[] names;
	protected Icon[] icons;
	protected boolean fallInstantly = true;


	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
	 */
	public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < names.length; i++)
			list.add(new ItemStack(this,1,i));
	}

	@Override
	protected void dropBlockAsItem_do(World par1World, int par2, int par3, int par4, ItemStack is)
	{
		if(is.getItemDamage() < 8)
			super.dropBlockAsItem_do(par1World, par2, par3, par4, is);
	}

	/*
	 * Mapping from metadata value to damage value
	 */
	@Override
	public int damageDropped(int i) {
		return i & 7;
	}

	@Override
	public Icon getIcon(int i, int j) 
	{
		return icons[j & 7];
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		for(int i = 0; i < names.length; i++)
			icons[i] = iconRegisterer.registerIcon(Reference.ModID + ":" + "rocks/"+names[i]+" Cobble");
	}

	@Override
	public void onBlockAdded(World world, int i, int j, int k)
	{
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}

	@Override
	public void onBlockDestroyedByPlayer(World world, int i, int j, int k, int l)
	{

	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		world.scheduleBlockUpdate(i, j, k, blockID, tickRate(world));
	}

	public boolean canFallBelow(World world, int i, int j, int k)
	{
		int l = world.getBlockId(i, j, k);
		if (l == 0)
			return true;
		if (l == Block.fire.blockID)
			return true;
		Material material = Block.blocksList[l].blockMaterial;
		if (material == Material.water)
			return true;
		return material == Material.lava;
	}

	/**
	 * Called when the block is clicked by a player. Args: x, y, z, entityPlayer
	 */
	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9) 
	{
		boolean hasHammer = false;
		for(int i = 0; i < 9;i++)
			if(entityplayer.inventory.mainInventory[i] != null && entityplayer.inventory.mainInventory[i].getItem() instanceof ItemHammer)
				hasHammer = true;
		if(entityplayer.getCurrentEquippedItem() != null && entityplayer.getCurrentEquippedItem().getItem() instanceof IToolChisel && 
				hasHammer && !world.isRemote && ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).canChisel(entityplayer, x, y, z))
		{
			MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(entityplayer, world);
			if(objectMouseOver == null)
				return false;       
			int side = objectMouseOver.sideHit;

			int id = world.getBlockId(x, y, z);
			byte meta = (byte) world.getBlockMetadata(x, y, z);

			return ((IToolChisel)entityplayer.getCurrentEquippedItem().getItem()).onUsed(world, entityplayer, x, y, z, id, meta, side, par7, par8, par9);
		}
		return false;
	}

	private void tryToFall(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k);
		if (!BlockCollapsable.isNearSupport(world, i, j, k, 4, 0) && BlockCollapsable.canFallBelow(world, i, j - 1, k) && j >= 0)
		{
			byte byte0 = 32;
			if (fallInstantly || !world.checkChunksExist(i - byte0, j - byte0, k - byte0, i + byte0, j + byte0, k + byte0))
			{
				world.setBlock(i, j, k, 0);
				for (; canFallBelow(world, i, j - 1, k) && j > 0; j--) { }
				if (j > 0)
					world.setBlock(i, j, k, blockID);
			}
			else if (!world.isRemote)
			{
				EntityFallingStone ent = new EntityFallingStone(world, i + 0.5F, j + 0.5F, k + 0.5F, blockID, meta);
				world.spawnEntityInWorld(ent);

				Random R = new Random(i*j+k);
				world.playSoundAtEntity(ent, TFC_Sounds.FALLININGROCKSHORT, 1.0F, 0.8F + (R.nextFloat()/2));
			}
		}
	}

	@Override
	public void updateTick(World world, int i, int j, int k, Random random)
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);

			boolean isBelowAir = world.getBlockId(i, j-1, k) == 0;
			byte count = 0;
			List sides = new ArrayList<Integer>();

			if(world.getBlockId(i+1, j, k) == 0)
			{
				count++;
				if(world.getBlockId(i+1, j-1, k) == 0)
					sides.add(0);
			}
			if(world.getBlockId(i, j, k+1) == 0)
			{
				count++;
				if(world.getBlockId(i, j-1, k+1) == 0)
					sides.add(1);
			}
			if(world.getBlockId(i-1, j, k) == 0)
			{
				count++;
				if(world.getBlockId(i-1, j-1, k) == 0)
					sides.add(2);
			}
			if(world.getBlockId(i, j, k-1) == 0)
			{
				count++;
				if(world.getBlockId(i, j-1, k-1) == 0)
					sides.add(3);
			}

			if(!isBelowAir && (count > 2) && sides.size() >= 1)
				switch((Integer)sides.get(random.nextInt(sides.size())))
				{
				case 0:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i+1, j, k, blockID, meta, 0x2);
					tryToFall(world, i+1, j, k);
					break;
				}
				case 1:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i, j, k+1, blockID, meta, 0x2);
					tryToFall(world, i, j, k+1);
					break;
				}
				case 2:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i-1, j, k, blockID, meta, 3);
					tryToFall(world, i-1, j, k);
					break;
				}
				case 3:
				{
					world.setBlockToAir(i, j, k);
					world.setBlock(i, j, k-1, blockID, meta, 3);
					tryToFall(world, i, j, k-1);
					break;
				}
				}
			else if(isBelowAir)
				tryToFall(world, i, j, k);
		}
	}
}
