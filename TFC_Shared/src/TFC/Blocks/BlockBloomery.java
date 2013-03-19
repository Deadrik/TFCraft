package TFC.Blocks;

import java.util.Random;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntityBloomery;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class BlockBloomery extends BlockTerraContainer
{
	Icon textureSide;
	Icon textureOn;
	Icon textureOff;
	
	public static final int headBlockToFootBlockMap[][] = {
		{
			0, 1
		}, {
			-1, 0
		}, {
			0, -1
		}, {
			1, 0
		}
	};

	public BlockBloomery(int i)
	{
		super(i, Material.rock);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
	{
		int meta = world.getBlockMetadata(x, y, z) & 4;
		if(meta == 0)
			return 0;
		else
			return 15;

	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;

		if((TileEntityBloomery)world.getBlockTileEntity(i, j, k)!=null)
		{
			TileEntityBloomery te;
			te = (TileEntityBloomery)world.getBlockTileEntity(i, j, k);
			ItemStack is = entityplayer.getCurrentEquippedItem();

			if(te.isValid)
			{
				entityplayer.openGui(TerraFirmaCraft.instance, 26, world, i, j, k);
			}
		}
		return true;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return world.isBlockOpaqueCube(i, j-1, k) && world.isBlockOpaqueCube(i, j+1, k);
	}

	@Override
	public Icon getBlockTextureFromSideAndMetadata(int i, int j)
	{
		int lit = (j & 4) == 4 ? 1 : 0;
		j = j & 3;

		if(j == 0 && i == 2	) {
			if(lit == 1)
				return textureOn;
			return textureOff;
		}
		if(j == 1 && i == 5) {
			if(lit == 1)
				return textureOn;
			return textureOff;
		}
		if(j == 2 && i == 3) {
			if(lit == 1)
				return textureOn;
			return textureOff;
		}
		if(j == 3 && i == 4) {
			if(lit == 1)
				return textureOn;
			return textureOff;
		}
		return textureSide;
	}
	
	@Override
	public void registerIcon(IconRegister iconRegisterer)
    {
		textureSide = iconRegisterer.func_94245_a("devices/Bloomery Side");
		textureOn = iconRegisterer.func_94245_a("devices/Bloomery On");
		textureOff = iconRegisterer.func_94245_a("devices/Bloomery Off");
    }

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack par6ItemStack)
	{
		if(!world.isRemote)
		{
			int l = MathHelper.floor_double((double)(entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
			world.setBlockMetadataWithNotify(i, j, k, l, 3);
		}

	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int i, int j, int k) 
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);
			int[] dir = headBlockToFootBlockMap[meta & 3];
			if(world.getBlockId(i+dir[0], j, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j, k+dir[1]);
			}
			if(world.getBlockId(i+dir[0], j+1, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+1, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j+1, k+dir[1]);
			}
			if(world.getBlockId(i+dir[0], j+2, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+2, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j+2, k+dir[1]);
			}
			if(world.getBlockId(i+dir[0], j+3, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+3, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j+3, k+dir[1]);
			}
			if(world.getBlockId(i+dir[0], j+4, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+4, k+dir[1], 0);
				world.markBlockForUpdate(i+dir[0], j+4, k+dir[1]);
			}
			world.setBlock(i, j, k, 0);
			world.markBlockForUpdate(i, j, k);
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		int meta = world.getBlockMetadata(i, j, k) & 3;
		int[] dir = headBlockToFootBlockMap[meta];


		if(!world.isBlockOpaqueCube(i, j-1, k) || !world.isBlockOpaqueCube(i, j+1, k))
		{
			((TileEntityBloomery)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlock(i, j, k, 0);
			world.markBlockForUpdate(i,j,k);
		}
	}

	@Override
	public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
	{       
		//dropBlockAsItem_do(world, i, j, k, new ItemStack(mod_TFC_Core.terraBloomery, 1));
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityBloomery();
	}
}
