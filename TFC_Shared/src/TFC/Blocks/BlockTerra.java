package TFC.Blocks;

import TFC.*;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Textures;
import net.minecraft.src.Block;
import net.minecraft.src.EntityLiving;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Material;
import net.minecraft.src.World;

public abstract class BlockTerra extends Block
{

	protected BlockTerra(int par1) 
	{
		super(par1, Material.rock);
	}
	protected BlockTerra(int par1,int par2, Material material) 
	{
		super(par1,par2, material);
	}

	protected BlockTerra(int par1, Material material) 
	{
		super(par1, material);
	}


	@Override
	public String getTextureFile()
	{
		return TFC_Textures.BlockSheet;
	}

	@Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) 
	{
		//TODO: Debug Message should go here if debug is toggled on
		if(TFC_Settings.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(i, j, k);
			System.out.println("Meta="+(new StringBuilder()).append(getBlockName()).append(":").append(metadata).toString());
		}
	}
	
	@Override
	public boolean canBeReplacedByLeaves(World w, int x, int y, int z)
	{
		return false;
	}
	
	@Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)  
    {
		if(TFC_Settings.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			System.out.println("Meta = "+(new StringBuilder()).append(getBlockName()).append(":").append(metadata).toString());
		}
		return false;
    }
}
