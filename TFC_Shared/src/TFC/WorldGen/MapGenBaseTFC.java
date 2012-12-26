package TFC.WorldGen;

import java.util.Random;
import TFC.*;
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
import net.minecraft.world.gen.*;
import net.minecraft.world.gen.feature.*;

public class MapGenBaseTFC
{
    /** The number of Chunks to gen-check in any given direction. */
    protected int range = 8;

    /** The RNG used by the MapGen classes. */
    protected Random rand = new Random();

    /** This world object. */
    protected World worldObj;

    public void generate(IChunkProvider par1IChunkProvider, World par2World, int par3, int par4, int[] par5ArrayOfByte)
    {
        int var6 = this.range;
        this.worldObj = par2World;
        this.rand.setSeed(par2World.getSeed());
        long var7 = this.rand.nextLong();
        long var9 = this.rand.nextLong();

        for (int var11 = par3 - var6; var11 <= par3 + var6; ++var11)
        {
            for (int var12 = par4 - var6; var12 <= par4 + var6; ++var12)
            {
                long var13 = (long)var11 * var7;
                long var15 = (long)var12 * var9;
                this.rand.setSeed(var13 ^ var15 ^ par2World.getSeed());
                this.recursiveGenerate(par2World, var11, var12, par3, par4, par5ArrayOfByte);
            }
        }
    }

    /**
     * Recursively called by generate() (generate) and optionally by itself.
     */
    protected void recursiveGenerate(World par1World, int par2, int par3, int par4, int par5, int[] par6ArrayOfByte) {}
}
