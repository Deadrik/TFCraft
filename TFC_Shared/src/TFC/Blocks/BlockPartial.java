package TFC.Blocks;

import java.util.ArrayList;

import TFC.*;
import TFC.Core.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Textures;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.ItemChisel;
import TFC.TileEntities.TileEntityPartial;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.particle.EffectRenderer;
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

public class BlockPartial extends BlockTerraContainer
{
    public BlockPartial(int par1, Material m)
    {
        super(par1, m);
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
    @SideOnly(Side.CLIENT)
    public boolean addBlockDestroyEffects(World world, int x, int y, int z, int meta, EffectRenderer effectRenderer)
    {
		// TODO Include particle spawning logic, or replace this with a functional getBlockTextureFromSideAndMetadata 
        return true;
    }
    @Override
    public void onBlockAdded(World world, int par2, int par3, int par4)
    {
        super.onBlockAdded(world, par2, par3, par4);
        world.markBlockForUpdate(par2, par3, par4);
    }

    @Override
    public void onBlockDestroyedByExplosion(World world, int i, int j, int k, Explosion ex) 
    {
        if(!world.isRemote)
        {
        }
    }
    
    @Override
    public void harvestBlock(World world, EntityPlayer entityplayer, int i, int j, int k, int l)
    {   

    }
	@Override
	public TileEntity createNewTileEntity(World var1) {
		// TODO Auto-generated method stub
		return new TileEntityPartial();
	}

}
