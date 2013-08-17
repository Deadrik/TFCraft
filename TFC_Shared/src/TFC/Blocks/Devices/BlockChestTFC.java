package TFC.Blocks.Devices;

import net.minecraft.block.BlockChest;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import TFC.TerraFirmaCraft;
import TFC.TileEntities.TileEntityChestTFC;

public class BlockChestTFC extends BlockChest
{
	public BlockChestTFC(int par1, int par2)
	{
		super(par1, par2);
	}

	@Override
	public TileEntity createNewTileEntity(World world)
	{
		return new TileEntityChestTFC();
	}

	@Override
	public boolean onBlockActivated(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer, int par6, float par7, float par8, float par9)
	{
		if (par1World.isRemote)
		{
			return true;
		}
		else
		{
			IInventory iinventory = this.getInventory(par1World, par2, par3, par4);

			if (iinventory != null)
			{
				par5EntityPlayer.openGui(TerraFirmaCraft.instance, 29, par1World, par2, par3, par4);
			}

			return true;
		}
	}

	/**
	 * Called when the block is placed in the world.
	 */
	@Override
	public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
	{
		int l = par1World.getBlockId(par2, par3, par4 - 1);
		int i1 = par1World.getBlockId(par2, par3, par4 + 1);
		int j1 = par1World.getBlockId(par2 - 1, par3, par4);
		int k1 = par1World.getBlockId(par2 + 1, par3, par4);
		byte b0 = 0;
		int l1 = MathHelper.floor_double(par5EntityLivingBase.rotationYaw * 4.0F / 360.0F + 0.5D) & 3;

		if (l1 == 0)
		{
			b0 = 2;
		}

		if (l1 == 1)
		{
			b0 = 5;
		}

		if (l1 == 2)
		{
			b0 = 3;
		}

		if (l1 == 3)
		{
			b0 = 4;
		}

		if (l != this.blockID && i1 != this.blockID && j1 != this.blockID && k1 != this.blockID)
		{
			par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
		}
		else
		{
			if ((l == this.blockID || i1 == this.blockID) && (b0 == 4 || b0 == 5))
			{
				if (l == this.blockID)
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4 - 1, b0, 3);
				}
				else
				{
					par1World.setBlockMetadataWithNotify(par2, par3, par4 + 1, b0, 3);
				}

				par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
			}

			if ((j1 == this.blockID || k1 == this.blockID) && (b0 == 2 || b0 == 3))
			{
				if (j1 == this.blockID)
				{
					par1World.setBlockMetadataWithNotify(par2 - 1, par3, par4, b0, 3);
				}
				else
				{
					par1World.setBlockMetadataWithNotify(par2 + 1, par3, par4, b0, 3);
				}

				par1World.setBlockMetadataWithNotify(par2, par3, par4, b0, 3);
			}
		}

		if (par6ItemStack.hasDisplayName())
		{
			((TileEntityChestTFC)par1World.getBlockTileEntity(par2, par3, par4)).setChestGuiName(par6ItemStack.getDisplayName());
		}
	}
}
