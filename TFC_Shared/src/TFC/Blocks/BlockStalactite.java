package TFC.Blocks;

import java.util.ArrayList;
import java.util.Random;

import TFC.TerraFirmaCraft;
import TFC.API.TFCOptions;
import TFC.API.Util.Helper;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInfo;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Items.Tools.ItemChisel;
import TFC.Items.Tools.ItemHammer;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityFirepit;
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
import net.minecraftforge.common.ForgeDirection;

public class BlockStalactite extends BlockSlab
{

    public BlockStalactite(int par1)
    {
        super(par1);
    }

    public void randomDisplayTick(World world, int i, int j, int k, Random random)
    {
        if ((world.isBlockNormalCube(i, j+1, k) || world.isBlockNormalCube(i, j+2, k)) && ((((TileEntityPartial)world.getBlockTileEntity(i, j, k)).extraData >> 24) & 1) == 1 && random.nextInt(80) == 0)
        {
            float f = (float)i + 0.5F;
            float f1 = (float)j-0.08f;
            float f2 = (float)k + 0.5F;
            float f3 = 0.52F;
            
            float f4 = random.nextFloat() * 0.6F;
            float f5 = random.nextFloat() * -0.6F;
            float f6 = random.nextFloat() * -0.6F;
            world.spawnParticle("dripWater", f+f4 - 0.3F, f1,  f2 + f5 + 0.3F, 0.0D, 0.0D, 0.0D);
        }
    }
    
    @Override
    public boolean canDropFromExplosion(Explosion ex)
    {
    	return false;
    }

	@Override
    public boolean isBlockSolidOnSide(World world, int x, int y, int z, ForgeDirection side)
	{
		return false;
	}
}
