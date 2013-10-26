package TFC.Blocks.Flora;

import java.util.List;

import TFC.TFCBlocks;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class BlockLogHoriz extends BlockLogVert
{
	int offset = 0;
    public BlockLogHoriz(int par1, int off)
    {
        super(par1);
        offset = off;
    }
    
    @Override
	public void registerIcons(IconRegister iconRegisterer)
    {
    	super.registerIcons(iconRegisterer);
    }

    @Override
    public Icon getIcon(int i, int j) 
    {
    	int meta = (j & 7) + offset;
    	int dir = j >> 3;
    	
    	if(dir == 0)
    	{    		
    		if(i == 0) {
				return BlockLogNatural.sideIcons[meta];
			} else if(i == 1) {
				return BlockLogNatural.sideIcons[meta];
			} else if(i == 2) {
				return BlockLogNatural.innerIcons[meta];
			} else if(i == 3) {
				return BlockLogNatural.innerIcons[meta];
			} else if(i == 4) {
				return BlockLogNatural.rotatedSideIcons[meta];
			} else {
				return BlockLogNatural.rotatedSideIcons[meta];
			}
    	}
    	else
    	{
    		if(i == 0) {
				return BlockLogNatural.rotatedSideIcons[meta];
			} else if(i == 1) {
				return BlockLogNatural.rotatedSideIcons[meta];
			} else if(i == 2) {
				return BlockLogNatural.rotatedSideIcons[meta];
			} else if(i == 3) {
				return BlockLogNatural.rotatedSideIcons[meta];
			} else if(i == 4) {
				return BlockLogNatural.innerIcons[meta];
			} else {
				return BlockLogNatural.innerIcons[meta];
			}
    	}
    }
    
    @Override
    public int damageDropped(int j) 
    {
    	if(blockID == TFCBlocks.WoodHoriz3.blockID ||blockID ==  TFCBlocks.WoodHoriz4.blockID){
    		j+=16;
    	}
        return (j & 7) + offset;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    /**
     * returns a list of blocks with the same ID, but different meta (eg: wood returns 4 blocks)
     */
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List list)
	{
		for(int i = 0; i < 8; i++) 
		{
			list.add(new ItemStack(this,1,i));
		}
	}
    
    @Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving) 
	{
    	int dir = MathHelper.floor_double(entityliving.rotationYaw * 4F / 360F + 0.5D) & 3;
    	int metadata = world.getBlockMetadata(i, j, k);
    	
    	if(dir == 1 || dir == 3)
    	world.setBlockMetadataWithNotify(i, j, k, metadata+8, 3);
    	
    	metadata = world.getBlockMetadata(i, j, k);
	}
}
