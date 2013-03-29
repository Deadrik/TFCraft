package TFC.Blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import TFC.TFCBlocks;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockFlora extends BlockTerra 
{
	Icon[] icons;
	String[] MetaNames;
	
	public BlockFlora(int id) 
	{
		super(id, Material.plants);
		MetaNames = new String[]{"Golden Rod", "Cat Tails"};
		icons = new Icon[MetaNames.length];
	}
	
	@Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
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
    public int getRenderType()
    {
        return 1;
    }
	
	@SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        for (int i = 0; i < icons.length; ++i)
        {
            icons[i] = par1IconRegister.registerIcon("plants/"+MetaNames[i]);
        }
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        if (par2 >= icons.length)
        {
            par2 = 0;
        }

        return icons[par2];
    }
    
    @Override
	public boolean canBlockStay(World par1World, int par2, int par3, int par4)
    {
        return (par1World.getFullBlockLightValue(par2, par3, par4) >= 8 || 
        		par1World.canBlockSeeTheSky(par2, par3, par4)) && 
        		this.canThisPlantGrowOnThisBlockID(par1World.getBlockId(par2, par3 - 1, par4));
    }
    
    protected boolean canThisPlantGrowOnThisBlockID(int par1)
    {
        return par1 == TFCBlocks.Grass.blockID || par1 == TFCBlocks.Grass2.blockID || 
                par1 == TFCBlocks.Dirt.blockID || par1 == TFCBlocks.Dirt2.blockID ||
                par1 == TFCBlocks.ClayGrass.blockID || par1 == TFCBlocks.ClayGrass2.blockID ||
                par1 == TFCBlocks.PeatGrass.blockID ||
                par1 == Block.tilledField.blockID;
    }
    
    @Override
    public int idDropped(int par1, Random par2Random, int par3)
    {
        return -1;
    }

}
