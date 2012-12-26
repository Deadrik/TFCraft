package TFC.Blocks;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import TFC.*;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
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
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class BlockLogHoriz extends BlockLogVert
{
	int offset = 0;
    public BlockLogHoriz(int par1, int off)
    {
        super(par1);
        offset = off;
    }

    @Override
    public int getBlockTextureFromSideAndMetadata(int i, int j) 
    {
    	int meta = (j & 7) + offset;
    	int dir = j >> 3;
    	
    	if(dir == 0)
    	{    		
    		if(i == 0) {
				return 128 + meta;
			} else if(i == 1) {
				return 128 + meta;
			} else if(i == 2) {
				return 144 + meta;
			} else if(i == 3) {
				return 144 + meta;
			} else if(i == 4) {
				return 32 + meta;
			} else {
				return 32 + meta;
			}
    	}
    	else
    	{
    		if(i == 0) {
				return 32 + meta;
			} else if(i == 1) {
				return 32 + meta;
			} else if(i == 2) {
				return 32 + meta;
			} else if(i == 3) {
				return 32 + meta;
			} else if(i == 4) {
				return 144 + meta;
			} else {
				return 144 + meta;
			}
    	}
    }
    
    @Override
    public int damageDropped(int j) 
    {
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
    	int dir = MathHelper.floor_double((double)(entityliving.rotationYaw * 4F / 360F) + 0.5D) & 3;
    	int metadata = world.getBlockMetadata(i, j, k);
    	
    	if(dir == 1 || dir == 3)
    	world.setBlockMetadata(i, j, k, metadata+8);
    	
    	metadata = world.getBlockMetadata(i, j, k);
    	
		//TODO: Debug Message should go here if debug is toggled on
		if(TFC_Settings.enableDebugMode && world.isRemote)
		{
			System.out.println("Dir = "+(new StringBuilder()).append(getBlockName()).append(":").append(metadata >> 3).toString());
		}
	}
}
