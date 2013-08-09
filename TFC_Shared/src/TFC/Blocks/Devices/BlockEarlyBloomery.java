package TFC.Blocks.Devices;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TFCItems;
import TFC.Blocks.BlockTerraContainer;
import TFC.TileEntities.TileEntityEarlyBloomery;

public class BlockEarlyBloomery extends BlockTerraContainer
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

	public BlockEarlyBloomery(int i)
	{
		super(i, Material.rock);
		this.setCreativeTab(CreativeTabs.tabRedstone);
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
	{
		int meta = world.getBlockMetadata(x, y, z) & 4;
		if(meta == 0) {
			return 0;
		} else {
			return 15;
		}

	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(!canBlockStay(world,i,j,k))
		{
			world.setBlockToAir(i, j, k);
			world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
		}
		else if((TileEntityEarlyBloomery)world.getBlockTileEntity(i, j, k)!=null)
		{
			TileEntityEarlyBloomery te = (TileEntityEarlyBloomery)world.getBlockTileEntity(i, j, k);
			ItemStack is = entityplayer.getCurrentEquippedItem();

			if(te.isValid && is != null && (is.getItem() == TFCItems.FireStarter || is.getItem() == TFCItems.FlintSteel))
			{
				if(te.canLight()){
					entityplayer.getCurrentEquippedItem().damageItem(1,entityplayer);
				}
			}
		}
		return true;
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k) & 3;
		int[] dir = headBlockToFootBlockMap[meta];

		if(world.isBlockOpaqueCube(i, j-1, k) && world.isBlockOpaqueCube(i, j+1, k))
		{
			int centerX = i + dir[0];
			int centerY = j;
			int centerZ = k + dir[1];
			if((world.getBlockMaterial(centerX+1, centerY, centerZ) == Material.rock || world.getBlockMaterial(centerX+1, centerY, centerZ) == Material.iron || (centerX== i && centerZ == k)) 
					&& (world.getBlockId(centerX+1, centerY, centerZ) != this.blockID || (centerX+1 == i && centerZ == k)))
			{
				if((world.getBlockMaterial(centerX-1, centerY, centerZ) == Material.rock || world.getBlockMaterial(centerX-1, centerY, centerZ) == Material.iron || (centerX== i && centerZ == k)) 
						&& (world.getBlockId(centerX-1, centerY, centerZ) != this.blockID || (centerX-1 == i && centerZ == k)))
				{
					if((world.getBlockMaterial(centerX, centerY, centerZ+1) == Material.rock || world.getBlockMaterial(centerX, centerY, centerZ+1) == Material.iron || (centerX== i && centerZ == k)) 
							&& (world.getBlockId(centerX, centerY, centerZ+1) != this.blockID || (centerX == i && centerZ+1 == k)))
					{
						if((world.getBlockMaterial(centerX, centerY, centerZ-1) == Material.rock || world.getBlockMaterial(centerX, centerY, centerZ-1) == Material.iron || (centerX== i && centerZ == k)) 
								&& (world.getBlockId(centerX, centerY, centerZ-1) != this.blockID || (centerX == i && centerZ-1 == k)))
						{
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return ((world.getBlockMaterial(i, j+1, k) == Material.rock || world.getBlockMaterial(i, j+1, k) == Material.iron) 
				&& world.getBlockId(i, j+1, k) != this.blockID) && 
				((world.getBlockMaterial(i, j-1, k) == Material.rock || world.getBlockMaterial(i, j-1, k) == Material.iron) 
						&& world.getBlockId(i, j-1, k) != this.blockID);
	}

	@Override
	public Icon getIcon(int i, int j)
	{
		int lit = (j & 4) == 4 ? 1 : 0;
		j = j & 3;

		if(j == 0 && i == 2	) 
		{
			if(lit == 1) {
				return textureOn;
			}
			return textureOff;
		}
		else if(j == 1 && i == 5) 
		{
			if(lit == 1) {
				return textureOn;
			}
			return textureOff;
		}
		else if(j == 2 && i == 3) 
		{
			if(lit == 1) {
				return textureOn;
			}
			return textureOff;
		}
		else if(j == 3 && i == 4) 
		{
			if(lit == 1) {
				return textureOn;
			}
			return textureOff;
		}
		return textureSide;
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		textureSide = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Bloomery Side");
		textureOn = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Bloomery On");
		textureOff = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Bloomery Off");
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack par6ItemStack)
	{
		if(!world.isRemote)
		{
			int l = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
			world.setBlockMetadataWithNotify(i, j, k, l, 0x2);
			if(!canBlockStay(world,i,j,k))
			{
				world.setBlockToAir(i, j, k);
				world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
			}
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
				world.setBlock(i+dir[0], j, k+dir[1], 0, 0, 0x2);
			}
			if(world.getBlockId(i+dir[0], j+1, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+1, k+dir[1], 0, 0, 0x2);
			}
			if(world.getBlockId(i+dir[0], j+2, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+2, k+dir[1], 0, 0, 0x2);
			}
			if(world.getBlockId(i+dir[0], j+3, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+3, k+dir[1], 0, 0, 0x2);
			}
			if(world.getBlockId(i+dir[0], j+4, k+dir[1]) == TFCBlocks.Molten.blockID)
			{
				world.setBlock(i+dir[0], j+4, k+dir[1], 0, 0, 0x2);
			}
			world.setBlockToAir(i, j, k);
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		if(!world.isBlockOpaqueCube(i, j-1, k) || !world.isBlockOpaqueCube(i, j+1, k))
		{
			world.setBlockToAir(i, j, k);
			world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
		}
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityEarlyBloomery();
	}
}
