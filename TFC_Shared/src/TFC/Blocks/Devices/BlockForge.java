package TFC.Blocks.Devices;

import java.util.ArrayList;
import java.util.Random;

import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.TFC_Core;
import TFC.Items.Tools.ItemFirestarter;
import TFC.Items.Tools.ItemFlintSteel;
import TFC.TileEntities.TEForge;

public class BlockForge extends BlockTerraContainer
{
	Icon textureOn;
	Icon textureOff;

	public BlockForge(int i)
	{
		super(i, Material.sand);
		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1F, 0.9F, 1F);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)
	{
		int meta = world.getBlockMetadata(i, j, k);
		int xCoord = i;
		int yCoord = j;
		int zCoord = k;
		ItemStack equippedItem = entityplayer.getCurrentEquippedItem();
		int itemid;
		if(equippedItem != null)
		{
			itemid = equippedItem.itemID;
		} else {
			itemid = 0;
		}

		if(world.isRemote)
		{
			return true;
		} 
		else if(equippedItem != null && (equippedItem.getItem() instanceof ItemFirestarter || equippedItem.getItem() instanceof ItemFlintSteel))
		{
			if((TEForge)world.getBlockTileEntity(i, j, k) != null)
			{
				TEForge tileentityforge;
				tileentityforge = (TEForge)world.getBlockTileEntity(i, j, k);
				if(tileentityforge.fireTemp <= 0 && tileentityforge.fireItemStacks[7] != null && tileentityforge.isSmokeStackValid)
				{
					tileentityforge.fireTemp = 10;
					tileentityforge.fuelBurnTemp = 20;
					tileentityforge.fuelTimeLeft = 10;
					int ss = entityplayer.inventory.getCurrentItem().stackSize;
					int dam = entityplayer.inventory.getCurrentItem().getItemDamage()+1;

					if(dam >= entityplayer.getCurrentEquippedItem().getItem().getMaxDamage())
					{
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
								null);
					}
					else
					{
						entityplayer.inventory.setInventorySlotContents(entityplayer.inventory.currentItem, 
								new ItemStack(entityplayer.getCurrentEquippedItem().getItem(),ss,dam));
					}
					world.setBlockMetadataWithNotify(i, j, k, 2, 3);
				}
			}
			return true;
		}
		else
		{
			if((TEForge)world.getBlockTileEntity(i, j, k)!=null)
			{
				TEForge tileentityforge;
				tileentityforge = (TEForge)world.getBlockTileEntity(i, j, k);
				ItemStack is =entityplayer.getCurrentEquippedItem();

				if(tileentityforge.isSmokeStackValid)
				{
					entityplayer.openGui(TerraFirmaCraft.instance, 23, world, i, j, k);
					//ModLoader.openGUI(entityplayer, new GuiTerraForge(entityplayer.inventory, tileentityforge));
				}
			}
			return true;
		}
	}

	@Override
	public Icon getIcon(int i, int j)
	{
		if(j > 0) {
			return textureOn;
		}

		return textureOff;
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		textureOn = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Forge On");
		textureOff = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Forge Off");
	}

	public boolean getIsFireLit(int i)
	{
		return (i & 1) != 0;
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.ForgeRenderId;
	}

	@Override
	public boolean isOpaqueCube()
	{
		return false;
	}

	@Override
	public void onNeighborBlockChange(World world, int x, int y, int z, int l)
	{
		if(!world.isRemote)
		{
			boolean surroundSolids = (world.getBlockMaterial(x+1, y, z) == Material.rock && world.getBlockMaterial(x-1, y, z) == Material.rock && 
					world.getBlockMaterial(x, y, z+1) == Material.rock && world.getBlockMaterial(x, y, z-1) == Material.rock &&
					world.isBlockNormalCube(x, y-1, z) && (world.isBlockNormalCube(x+1, y, z) && world.isBlockNormalCube(x-1, y, z) && 
							world.isBlockNormalCube(x, y, z+1) && world.isBlockNormalCube(x, y, z-1)));

			boolean rockXP = world.getBlockId(x+1, y, z) == TFCBlocks.stoneSlabs.blockID || 
					(world.getBlockMaterial(x+1, y, z) == Material.rock && world.isBlockNormalCube(x+1, y, z));
			boolean rockXN = world.getBlockId(x-1, y, z) == TFCBlocks.stoneSlabs.blockID || 
					(world.getBlockMaterial(x-1, y, z) == Material.rock && world.isBlockNormalCube(x-1, y, z));
			boolean rockZP = world.getBlockId(x, y, z+1) == TFCBlocks.stoneSlabs.blockID || 
					(world.getBlockMaterial(x, y, z+1) == Material.rock && world.isBlockNormalCube(x, y, z+1));
			boolean rockZN = world.getBlockId(x, y, z-1) == TFCBlocks.stoneSlabs.blockID || 
					(world.getBlockMaterial(x, y, z-1) == Material.rock && world.isBlockNormalCube(x, y, z-1));
			boolean rockYN = world.getBlockId(x, y-1,  z ) == TFCBlocks.stoneSlabs.blockID ||
					(world.getBlockMaterial(x, y-1, z) == Material.rock && world.isBlockNormalCube(x, y-1,z));


			boolean validSlabs = TFC_Core.isNorthSolid(world, x, y, z) && TFC_Core.isSouthSolid(world, x, y, z) && 
					TFC_Core.isEastSolid(world, x, y, z) && TFC_Core.isWestSolid(world, x, y, z);	

			if (!(rockXP && rockXN && rockZP && rockZN && rockYN)
					|| !validSlabs)
			{
				((TEForge)world.getBlockTileEntity(x, y, z)).ejectContents();
				world.setBlock(x, y, z, 0);
			}
			else
			{
				if(world.getBlockTileEntity(x, y, z) != null)
				{
					//((TileEntityForge)world.getBlockTileEntity(x, y, z)).isValid = false;
				}
			}
		}
	}

	@Override
	public ArrayList<ItemStack> getBlockDropped(World world, int x, int y, int z, int metadata, int fortune)
	{
		ArrayList<ItemStack> ret = new ArrayList<ItemStack>();
		return ret;
	}

	@Override
	public void randomDisplayTick(World world, int i, int j, int k, Random random)
	{
		if (this.blockID == TFCBlocks.Forge.blockID)
		{
			return;
		}
		else
		{
			float f = i + 0.5F;
			float f1 = j + 0.9F + random.nextFloat() * 6F / 16F;
			float f2 = k + 0.5F;
			float f3 = 0.52F;
			float f4 = random.nextFloat() * 0.6F;
			float f5 = random.nextFloat() * -0.6F;
			float f6 = random.nextFloat() * -0.6F;
			world.spawnParticle("smoke", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("smoke", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f4 - 0.3F, 0.0D, 0.0D, 0.0D);
			if (((TEForge)world.getBlockTileEntity(i, j, k)).fireTemp > 550)
			{
				world.spawnParticle("flame", f+f5 + 0.3F , f1, f2 + f6 + 0.2F, 0.0D, 0.0D, 0.0D);
				world.spawnParticle("flame", f+f4 - 0.3F , f1, f2 + f6 + 0.1F, 0.0D, 0.0D, 0.0D);
			}
		}
	}
	@Override
	public boolean renderAsNormalBlock()
	{
		return false;
	}

	@Override
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
	{
		int meta = world.getBlockMetadata(x, y, z);
		if(meta == 0) {
			return 0;
		} else if(meta == 1) {
			return 15;
		} else {
			return 10;
		}
	}

	/**
	 * Returns a bounding box from the pool of bounding boxes (this means this box can change after the pool has been
	 * cleared to be reused)
	 */
	@Override
	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
	}

	/**
	 * Returns the bounding box of the wired rectangular prism to render.
	 */
	@Override
	public AxisAlignedBB getSelectedBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	{
		return AxisAlignedBB.getBoundingBox(par2 + this.minX, par3 + this.minY, par4 + this.minZ, par2 + this.maxX, par3 + this.maxY, par4 + this.maxZ);
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TEForge();
	}


}
