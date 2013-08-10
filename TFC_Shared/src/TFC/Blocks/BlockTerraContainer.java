package TFC.Blocks;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import TFC.*;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Textures;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
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
import net.minecraft.world.chunk.*;

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
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack is) 
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
