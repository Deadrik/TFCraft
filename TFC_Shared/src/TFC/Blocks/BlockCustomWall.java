package TFC.Blocks;

import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Textures;
import java.util.List;
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

public class BlockCustomWall extends BlockWall
{
	int totalsubTypes = 0;
	Block block;
    public BlockCustomWall(int id, Block blk, int t)
    {
        super(id, blk);

        this.block = blk;
        totalsubTypes = t;
    }

    /**
     * From the specified side and block metadata retrieves the blocks texture. Args: side, metadata
     */
    @Override
    public Icon getBlockTextureFromSideAndMetadata(int par1, int par2)
    {
        return block.getBlockTextureFromSideAndMetadata(par1, par2);
    }
    
    @Override
	public void onBlockPlacedBy(World world, int i, int j, int k, EntityLiving entityliving, ItemStack is) 
	{
		//TODO: Debug Message should go here if debug is toggled on
		if(TFC_Settings.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(i, j, k);
			System.out.println("Meta="+(new StringBuilder()).append(this.getUnlocalizedName()).append(":").append(metadata).toString());
		}
	}
    
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z, EntityPlayer entityplayer, int side, float hitX, float hitY, float hitZ)  
    {
		if(TFC_Settings.enableDebugMode && world.isRemote)
		{
			int metadata = world.getBlockMetadata(x, y, z);
			System.out.println("Meta = "+(new StringBuilder()).append(getUnlocalizedName()).append(":").append(metadata).toString());
		}
		return false;
    }

    /**
     * The type of render function that is called for this block
     */
    @Override
    public int getRenderType()
    {
        return 32;
    }
    
    @SideOnly(Side.CLIENT)
    @Override
    public void getSubBlocks(int par1, CreativeTabs par2CreativeTabs, List par3List)
    {
    	for(int i = 0; i < totalsubTypes; i++)
    		par3List.add(new ItemStack(par1, 1, i));
    }
    
    /**
     * Determines the damage on the item the block drops. Used in cloth and wood.
     */
    public int damageDropped(int par1)
    {
        return par1;
    }
    public boolean canConnectWallTo(IBlockAccess access, int i, int j, int k)
    {
        int id = access.getBlockId(i, j, k);

        if (id != this.blockID && id != Block.fenceGate.blockID && !(Block.blocksList[id] instanceof BlockCustomWall))
        {
            Block var6 = Block.blocksList[id];
            return var6 != null && var6.blockMaterial.isOpaque() && var6.renderAsNormalBlock() ? var6.blockMaterial != Material.pumpkin : false;
        }
        else
        {
            return true;
        }
    }
}
