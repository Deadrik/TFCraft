package TFC.Blocks;

import TFC.*;
import TFC.Chunkdata.ChunkDataManager;
import TFC.TileEntities.TileEntitySpawnMeter;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.texture.IconRegister;
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

public class BlockSpawnMeter extends BlockTerraContainer
{
	Icon iconTop;
	Icon[] icons = new Icon[9];
	
	public BlockSpawnMeter(int par1) 
	{
		super(par1, Material.rock);
		this.setCreativeTab(CreativeTabs.tabDecorations);
		this.setLightValue(1F);
	}
	
	public int getLightValue(IBlockAccess world, int x, int y, int z) 
    {
		int meta = world.getBlockMetadata(x, y, z);
        return Math.min(meta, 8);
    }
	
	public boolean isOpaqueCube()
	{
		return true;
	}

	@Override
	public int damageDropped(int j) 
	{
		return j;
	}

	@Override
    public Icon getBlockTextureFromSideAndMetadata(int i, int j) 
    {
		if(i < 2)
			return iconTop;
		
		return icons[j];
    }
	
	public void registerIcon(IconRegister iconRegisterer)
    {
		iconTop = iconRegisterer.func_94245_a("/devices/meterTop");
		for(int i = 0; i < 9; i++)
		{
			icons[i] = iconRegisterer.func_94245_a("/devices/meter"+i);
		}
    }

	@Override
	public TileEntity createNewTileEntity(World var1) {
		return new TileEntitySpawnMeter();
	}
}
