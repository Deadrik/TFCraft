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
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockTerraContainer;
import TFC.TileEntities.TEBlastFurnace;

public class BlockBlastFurnace extends BlockTerraContainer
{
	Icon[] textureSide;
	Icon textureOn;
	Icon textureOff;

	public BlockBlastFurnace(int i)
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
		int meta = world.getBlockMetadata(i, j, k);
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;

		if(!canBlockStay(world,i,j,k))
		{
			world.setBlockToAir(i, j, k);
			world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
		}
		else if(world.getBlockTileEntity(i, j, k) != null)
		{
			TEBlastFurnace te = (TEBlastFurnace)world.getBlockTileEntity(i, j, k);
			ItemStack is = entityplayer.getCurrentEquippedItem();

			if(te.isValid)
			{
				if(equippedItem != null && (equippedItem.getItem() == TFCItems.FireStarter || equippedItem.getItem() == TFCItems.FlintSteel))
				{
					if(te.canLight())
					{
						entityplayer.getCurrentEquippedItem().damageItem(1,entityplayer);
						te.fireTemperature = 250;
					}
				}
				entityplayer.openGui(TerraFirmaCraft.instance, 26, world, i, j, k);
			}
		}
		return true;
	}

	@Override
	public boolean canBlockStay(World world, int i, int j, int k)
	{
		int meta = world.getBlockMetadata(i, j, k) & 3;

		int y = j+1;

		if(world.getBlockMaterial(i+1, y, k) == Material.rock || world.getBlockMaterial(i+1, y, k) == Material.iron)
		{
			if(world.getBlockMaterial(i-1, y, k) == Material.rock || world.getBlockMaterial(i-1, y, k) == Material.iron)
			{
				if(world.getBlockMaterial(i, y, k+1) == Material.rock || world.getBlockMaterial(i, y, k+1) == Material.iron)
				{
					if(world.getBlockMaterial(i, y, k-1) == Material.rock || world.getBlockMaterial(i, y, k-1) == Material.iron)
					{
						return true;
					}
				}
			}
		}

		return false;
	}

	@Override
	public boolean canPlaceBlockAt(World world, int i, int j, int k)
	{
		return true;
	}

	@Override
	public Icon getIcon(int i, int j)
	{
		int lit = (j & 4) == 4 ? 1 : 0;
		j = j & 3;

		if(i == 0 || i == 1) 
		{
			if(lit == 1) {
				return textureSide[1];
			} else {
				return textureSide[0];
			}
		}
		else
		{
			if(lit == 1) {
				return textureOn;
			} else {
				return textureOff;
			}
		}
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		textureSide = new Icon[2];
		textureSide[0] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Blast Furnace Bottom Off");
		textureSide[1] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Blast Furnace Bottom On");
		textureOn = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Blast Furnace On");
		textureOff = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Blast Furnace Off");
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
			else
			{
				((TEBlastFurnace)world.getBlockTileEntity(i, j, k)).slowCounter = 101;
			}
		}
	}

	@Override
	public boolean removeBlockByPlayer(World world, EntityPlayer player, int i, int j, int k) 
	{
		if(!world.isRemote)
		{
			int meta = world.getBlockMetadata(i, j, k);

			if(world.getBlockId(i, j, k) == TFCBlocks.Molten.blockID)
			{
				world.setBlockToAir(i, j, k);
			}
			if(world.getBlockId(i, j+1, k) == TFCBlocks.Molten.blockID)
			{
				world.setBlockToAir(i, j+1, k);
			}
			if(world.getBlockId(i, j+2, k) == TFCBlocks.Molten.blockID)
			{
				world.setBlockToAir(i, j+2, k);
			}
			if(world.getBlockId(i, j+3, k) == TFCBlocks.Molten.blockID)
			{
				world.setBlockToAir(i, j+3, k);
			}
			if(world.getBlockId(i, j+4, k) == TFCBlocks.Molten.blockID)
			{
				world.setBlockToAir(i, j+4, k);
			}
			world.setBlockToAir(i, j, k);
		}
		return true;
	}

	@Override
	public void onNeighborBlockChange(World world, int i, int j, int k, int l)
	{
		/*if(!world.isBlockOpaqueCube(i, j-1, k) || !world.isBlockOpaqueCube(i, j+1, k))
		{
			((TEBlastFurnace)world.getBlockTileEntity(i, j, k)).ejectContents();
			world.setBlockToAir(i, j, k);
			world.spawnEntityInWorld(new EntityItem(world,i,j,k, new ItemStack(this, 1)));
		}*/
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TEBlastFurnace();
	}
}
