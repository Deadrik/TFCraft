package TFC.Blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import TFC.Core.TFC_Settings;

public abstract class BlockTerraContainer extends BlockContainer
{
	public BlockTerraContainer(int par1) 
	{
		super(par1, Material.rock);
	}

	public BlockTerraContainer(int par1, Material material) 
	{
		super(par1, material);
	}


	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase entityliving, ItemStack is) 
	{
		//TODO: Debug Message should go here if debug is toggled on
		if(TFC_Settings.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(i, j, k);
			System.out.println("Meta="+(new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
	}

	@Override
	public boolean canBeReplacedByLeaves(World w, int x, int y, int z)
	{
		return false;
	}

	@Override
	public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)  
	{
		if(TFC_Settings.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			System.out.println("Meta = "+(new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
		return false;
	}

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return null;
	}

	@Override
	public void breakBlock(World world, int x, int y, int z, int blockId, int metadata)
	{
		TileEntity te = world.getBlockTileEntity(x, y, z);
		if(te != null)
		{
			if(te instanceof IInventory)
			{
				for(int i = 0; i< ((IInventory)te).getSizeInventory(); i++)
				{
					if(((IInventory)te).getStackInSlot(i) != null)
					{
						EntityItem ei = new EntityItem(world, x, y, z, ((IInventory)te).getStackInSlot(i));
						ei.motionY = 0.4;
						world.spawnEntityInWorld(ei);
					}
				}
			}
		}
		super.breakBlock(world, x, y, z, blockId, metadata);
	}
}
