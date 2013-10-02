package TFC.Blocks.Devices;

import java.util.Iterator;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Icon;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockTerraContainer;
import TFC.Core.Metal.MetalPair;
import TFC.TileEntities.TECrucible;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockCrucible extends BlockTerraContainer 
{
	Icon[] icons;

	public BlockCrucible(int par1) 
	{
		super(par1);
		this.setCreativeTab(CreativeTabs.tabRedstone);
		this.setBlockBounds(0.0625f, 0f, 0.0625f, 0.9375f, 0.9375f, 0.9375f);
	}

	@Override
	public boolean onBlockActivated(World world, int i, int j, int k, EntityPlayer entityplayer, int par6, float par7, float par8, float par9)
	{
		if(!world.isRemote && (TECrucible)world.getBlockTileEntity(i, j, k) != null)
		{
			TECrucible te = (TECrucible)world.getBlockTileEntity(i, j, k);
			ItemStack is = entityplayer.getCurrentEquippedItem();

			entityplayer.openGui(TerraFirmaCraft.instance, 37, world, i, j, k);
		}
		return true;
	}

	@Override
	public void breakBlock(World world, int i, int j, int k, int par5, int par6)
	{
		TECrucible te = (TECrucible)world.getBlockTileEntity(i, j, k);

		if (te != null)
		{
			ItemStack is = new ItemStack(Block.blocksList[par5], 1);
			NBTTagCompound nbt = writeCrucibleToNBT(te);
			is.setTagCompound(nbt);
			EntityItem ei = new EntityItem(world,i,j,k,is);
			world.spawnEntityInWorld(ei);

			for(int s = 0; s < te.getSizeInventory(); ++s) {
				te.setInventorySlotContents(s, null);
			}
		}
		super.breakBlock(world, i, j, k, par5, par6);
	}

	@Override
	protected void dropBlockAsItem_do(World par1World, int par2, int par3, int par4, ItemStack par5ItemStack)
	{
		/*if (!par1World.isRemote && par1World.getGameRules().getGameRuleBooleanValue("doTileDrops"))
        {
        	TECrucible te = (TECrucible)par1World.getBlockTileEntity(par2, par3, par4);

    		if (te != null)
    		{
    			NBTTagCompound nbt = writeCrucibleToNBT(te);
    			par5ItemStack.setTagCompound(nbt);
    		}
            float f = 0.7F;
            double d0 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double d1 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            double d2 = par1World.rand.nextFloat() * f + (1.0F - f) * 0.5D;
            EntityItem entityitem = new EntityItem(par1World, par2 + d0, par3 + d1, par4 + d2, par5ItemStack);
            entityitem.delayBeforeCanPickup = 10;
            par1World.spawnEntityInWorld(entityitem);
        }*/
	}

	public NBTTagCompound writeCrucibleToNBT(TECrucible te)
	{
		NBTTagCompound nbt = new NBTTagCompound();

		nbt.setInteger("temp", te.temperature);

		NBTTagList nbttaglist = new NBTTagList();
		Iterator iter = te.metals.values().iterator();
		while(iter.hasNext())
		{
			MetalPair m = (MetalPair) iter.next();
			if(m != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setInteger("ID", m.type.IngotID);
				nbttagcompound1.setFloat("AmountF", m.amount);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}
		nbt.setTag("Metals", nbttaglist);

		nbttaglist = new NBTTagList();
		for(int i = 0; i < te.storage.length; i++)
		{
			if(te.storage[i] != null)
			{
				NBTTagCompound nbttagcompound1 = new NBTTagCompound();
				nbttagcompound1.setByte("Slot", (byte)i);
				te.storage[i].writeToNBT(nbttagcompound1);
				nbttaglist.appendTag(nbttagcompound1);
			}
		}

		nbt.setTag("Items", nbttaglist);

		return nbt;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLivingBase player, ItemStack is)
	{
		super.onBlockPlacedBy(world, i, j, k, player, is);
		TECrucible te = (TECrucible)world.getBlockTileEntity(i, j, k);

		if (te != null && is.hasTagCompound())
		{
			te.readFromItemNBT(is.getTagCompound());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean shouldSideBeRendered(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5) {
		return true;
	}

	@Override
	public void registerIcons(IconRegister iconRegisterer)
	{
		icons = new Icon[2];
		icons[0] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Crucible Top");
		icons[1] = iconRegisterer.registerIcon(Reference.ModID + ":" + "devices/Crucible Side");
	}

	@Override
	public Icon getIcon(int i, int j) 
	{
		if(i < 2) {
			return icons[0];
		}

		return icons[1];
	}

	@Override
	public int getRenderType()
	{
		return TFCBlocks.crucibleRenderId;
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

	@Override
	public TileEntity createNewTileEntity(World var1)
	{
		return new TECrucible();
	}

}
