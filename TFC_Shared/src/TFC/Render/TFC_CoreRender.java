package TFC.Render;

import java.util.Random;

import org.lwjgl.opengl.GL11;

import bioxx.importers.WavefrontObject;

import TFC.TFCBlocks;
import TFC.TerraFirmaCraft;
import TFC.Blocks.BlockFiniteWater;
import TFC.Blocks.BlockFruitLeaves;
import TFC.Blocks.BlockAnvil;
import TFC.Blocks.BlockBellows;
import TFC.Blocks.BlockSluice;
import TFC.Core.AnvilReq;
import TFC.Core.TFC_Textures;
import TFC.Core.TFC_Time;
import TFC.Core.TFC_Settings;
import TFC.Core.TFC_Core.Direction;
import TFC.Food.CropIndex;
import TFC.Food.CropManager;
import TFC.Food.FloraIndex;
import TFC.Food.FloraManager;
import TFC.TileEntities.TileEntityCrop;
import TFC.TileEntities.TileEntityFruitTreeWood;
import TFC.TileEntities.TileEntityPartial;
import TFC.TileEntities.TileEntityTerraAnvil;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;

import net.minecraft.client.Minecraft;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
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
import net.minecraft.src.ModLoader;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.client.ForgeHooksClient;

public class TFC_CoreRender 
{
    public static boolean renderBlockSlab(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks)
    {
        TileEntityPartial te = (TileEntityPartial) renderblocks.blockAccess.getBlockTileEntity(par2, par3, par4);
        int md = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);
        
        boolean breaking = false;
        /*if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }*/

        if(te.TypeID <= 0) return false;

        int type = te.TypeID;
        int meta = te.MetaID;
        Icon tex = Block.blocksList[type].getBlockTextureFromSideAndMetadata(0, meta);
        
        //if(!breaking)
        //	ForgeHooksClient.bindTexture(Block.blocksList[type].getTextureFile(), ModLoader.getMinecraftInstance().renderEngine.getTexture(Block.blocksList[type].getTextureFile()));
        
        long extraX = (te.extraData) & 0xf;
        long extraY = (te.extraData >> 4) & 0xf;
        long extraZ = (te.extraData >> 8) & 0xf;
        long extraX2 = (te.extraData >> 12) & 0xf;
        long extraY2 = (te.extraData >> 16) & 0xf;
        long extraZ2 = (te.extraData >> 20) & 0xf;
        
        float div = 1f / 8;
        
        renderblocks.setRenderBounds(0.0F+ (div * extraX), 0.0F+ (div * extraY), 0.0F+ (div * extraZ), 1.0F-(div * extraX2), 1-(div * extraY2), 1.0F-(div * extraZ2));

        //This is the old ore code that I experimented with
        Icon over = renderblocks.overrideBlockTexture;
        if(!breaking && (type == TFCBlocks.Ore.blockID || type == TFCBlocks.Ore2.blockID || type == TFCBlocks.Ore3.blockID))
        {
            BiomeGenBase biome = renderblocks.blockAccess.getBiomeGenForCoords(par2, par4);
            renderblocks.overrideBlockTexture = getRockTexture(ModLoader.getMinecraftInstance().theWorld, par2, par3, par4);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
            renderblocks.overrideBlockTexture = over;
        }

        if(!breaking)
            renderblocks.overrideBlockTexture = tex;

        renderblocks.renderStandardBlock(par1Block, par2, par3, par4);

        renderblocks.overrideBlockTexture = over;



        return true;
    }

    public static boolean renderBlockStairs(Block par1Block, int par2, int par3, int par4, RenderBlocks renderblocks)
    {
    	boolean breaking = false;
        /*if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }*/
        
        int var5 = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);
        renderblocks.renderAllFaces = true;
        int var6 = var5 & 3;
        float var7 = 0.0F;
        float var8 = 0.5F;
        float var9 = 0.5F;
        float var10 = 1.0F;

        if ((var5 & 4) != 0)
        {
            var7 = 0.5F;
            var8 = 1.0F;
            var9 = 0.0F;
            var10 = 0.5F;
        }
        TileEntityPartial te = (TileEntityPartial) renderblocks.blockAccess.getBlockTileEntity(par2, par3, par4);
        if(te.TypeID <= 0) return false;

        int type = te.TypeID;
        int meta = te.MetaID;
        Icon tex = Block.blocksList[type].getBlockTextureFromSideAndMetadata(0, meta);
        if(!breaking)
        {
        	//ForgeHooksClient.bindTexture(Block.blocksList[type].getTextureFile(), ModLoader.getMinecraftInstance().renderEngine.getTexture(Block.blocksList[type].getTextureFile()));
        	renderblocks.overrideBlockTexture = tex;
        }
        renderblocks.setRenderBounds(0.0F, var7, 0.0F, 1.0F, var8, 1.0F);
        renderblocks.renderStandardBlock(par1Block, par2, par3, par4);

        if (var6 == 0)
        {
            renderblocks.setRenderBounds(0.5F, var9, 0.0F, 1.0F, var10, 1.0F);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
        }
        else if (var6 == 1)
        {
            renderblocks.setRenderBounds(0.0F, var9, 0.0F, 0.5F, var10, 1.0F);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
        }
        else if (var6 == 2)
        {
            renderblocks.setRenderBounds(0.0F, var9, 0.5F, 1.0F, var10, 1.0F);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
        }
        else if (var6 == 3)
        {
            renderblocks.setRenderBounds(0.0F, var9, 0.0F, 1.0F, var10, 0.5F);
            renderblocks.renderStandardBlock(par1Block, par2, par3, par4);
        }
        renderblocks.clearOverrideBlockTexture();
        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        return true;
    }

    public static boolean RenderSulfur(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;
        if(blockAccess.isBlockNormalCube(i, j, k+1) && blockAccess.getBlockId(i, j, k+1) != block.blockID)
        {
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.99F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i, j, k-1) && blockAccess.getBlockId(i, j, k-1) != block.blockID)
        {
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.01F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i+1, j, k) && blockAccess.getBlockId(i+1, j, k) != block.blockID)
        {
            renderblocks.setRenderBounds(0.99F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i-1, j, k) && blockAccess.getBlockId(i-1, j, k) != block.blockID)
        {
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 0.01F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i, j+1, k) && blockAccess.getBlockId(i, j+1, k) != block.blockID)
        {
            renderblocks.setRenderBounds(0.0F, 0.99F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }
        if(blockAccess.isBlockNormalCube(i, j-1, k) && blockAccess.getBlockId(i, j-1, k) != block.blockID)
        {
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.01F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
        }

        return true;
    }

    public static boolean RenderSnow(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        int meta = renderblocks.blockAccess.getBlockMetadata(i, j, k);

        float drift = 0.04F + (meta * 0.06F);

        renderblocks.setRenderBounds(0.0F, 0.0F, 0F, 1.0F, drift, 1.0F);
        renderblocks.renderStandardBlock(block, i, j, k);
        return true;
    }

    public static boolean RenderWoodTrunk(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        if(true/*blockAccess.getBlockMaterial(i, j+1, k) == Material.leaves || blockAccess.getBlockMaterial(i, j-1, k) == Material.leaves || 
                blockAccess.getBlockId(i, j+1, k) == mod_TFC_Core.fruitTreeWood.blockID || blockAccess.getBlockId(i, j-1, k) == mod_TFC_Core.fruitTreeWood.blockID*/)
        {
            if(blockAccess.getBlockTileEntity(i, j, k) != null && (blockAccess.getBlockId(i, j-1, k) == TFCBlocks.fruitTreeWood.blockID || blockAccess.isBlockOpaqueCube(i, j-1, k)))
            {
                renderblocks.setRenderBounds(0.3F, 0.0F, 0.3F, 0.7F, 1.0F, 0.7F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            if(blockAccess.getBlockMaterial(i-1, j, k) == Material.leaves || blockAccess.getBlockId(i-1, j, k) == TFCBlocks.fruitTreeWood.blockID)
            {
                renderblocks.setRenderBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            if(blockAccess.getBlockMaterial(i+1, j, k) == Material.leaves || blockAccess.getBlockId(i+1, j, k) == TFCBlocks.fruitTreeWood.blockID)
            {
                renderblocks.setRenderBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            if(blockAccess.getBlockMaterial(i, j, k-1) == Material.leaves || blockAccess.getBlockId(i, j, k-1) == TFCBlocks.fruitTreeWood.blockID)
            {
            	renderblocks.setRenderBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            if(blockAccess.getBlockMaterial(i, j, k+1) == Material.leaves || blockAccess.getBlockId(i, j, k+1) == TFCBlocks.fruitTreeWood.blockID)
            {
            	renderblocks.setRenderBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
        }

        if(!((TileEntityFruitTreeWood)blockAccess.getBlockTileEntity(i, j, k)).isTrunk && blockAccess.getBlockId(i, j-1, k) != TFCBlocks.fruitTreeWood.blockID && !blockAccess.isBlockOpaqueCube(i, j-1, k))
        {

        	renderblocks.setRenderBounds(0.0F, 0.4F, 0.4F, 0.5F, 0.6F, 0.6F);
            renderblocks.renderStandardBlock(block, i, j, k);

            renderblocks.setRenderBounds(0.5F, 0.4F, 0.4F, 1.0F, 0.6F, 0.6F);
            renderblocks.renderStandardBlock(block, i, j, k);

            renderblocks.setRenderBounds(0.4F, 0.4F, 0.0F, 0.6F, 0.6F, 0.5F);
            renderblocks.renderStandardBlock(block, i, j, k);

            renderblocks.setRenderBounds(0.4F, 0.4F, 0.5F, 0.6F, 0.6F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);

        }

        //renderblocks.func_83020_a(0.0F, 0.0F, 0.0F, 1F, 1F, 1F);
        return true;
    }

    public static boolean RenderLooseRock(Block block, int i, int j, int k, RenderBlocks renderblocks)	
    {
    	boolean breaking = false;
        /*if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }*/
        
        int meta = renderblocks.blockAccess.getBlockMetadata(i, j, k);
        World w = ModLoader.getMinecraftInstance().theWorld;
        TFCWorldChunkManager wcm = ((TFCWorldChunkManager)w.getWorldChunkManager());
        renderblocks.renderAllFaces = true;
        
        DataLayer rockLayer1 = ((TFCWorldChunkManager)w.getWorldChunkManager()).getRockLayerAt(i, k, 0);
        if(rockLayer1 != null && Block.blocksList[rockLayer1.data1] != null && !breaking)
        	renderblocks.overrideBlockTexture = Block.blocksList[rockLayer1.data1].getBlockTextureFromSideAndMetadata(0, rockLayer1.data2);

        int seed = i*k+j;
        Random R = new Random(seed);
        
        float xOffset = (float)(R.nextInt(5) - 2) * 0.05f;
        float zOffset = (float)(R.nextInt(5) - 2) * 0.05f;
        
        float xOffset2 = (float)(R.nextInt(5) - 2) * 0.05f;
        float yOffset2 = (float)(R.nextInt(5) - 2) * 0.05f;
        float zOffset2 = (float)(R.nextInt(5) - 2) * 0.05f;
        
        renderblocks.setRenderBounds(0.35F + xOffset, 0.00F, 0.35F + zOffset, 0.65F + xOffset2, 0.15F + yOffset2, 0.65F + zOffset2);
        renderblocks.renderStandardBlock(block, i, j, k);
        //renderblocks.func_83020_a(0.20F, 0.00F, 0.2F, 0.8F, 0.25F, 0.8F);
        renderblocks.clearOverrideBlockTexture();

        return true;
    }

    public static boolean RenderWoodSupportBeamH(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        int supportIDv = TFCBlocks.WoodSupportV.blockID;
        int supportIDh = TFCBlocks.WoodSupportH.blockID;
        renderblocks.renderAllFaces = true;

        Boolean hasVerticalBeam = false;
        Boolean hasHorizontalBeamX = false;
        Boolean hasHorizontalBeamZ = false;

        //if the block directly beneath is a Vertical Support
        if((blockAccess.getBlockId(i, j-1, k) == supportIDv))
        {	
            renderblocks.setRenderBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
            renderblocks.renderStandardBlock(block, i, j, k);
            hasVerticalBeam = true;
        }

        //X
        if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ (blockAccess.getBlockId(i-1, j, k) == supportIDv || blockAccess.getBlockId(i-1, j, k) == supportIDh))//if the block above is solid and the block at -x is a support beam
        {
            if((blockAccess.getBlockId(i+1, j, k) == supportIDv || blockAccess.getBlockId(i+1, j, k) == supportIDh))//if the block above is solid and the block at +x is a support beam
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                    renderblocks.setRenderBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else if(!hasVerticalBeam)//if the block does not contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamX = true;
            }
            else//if there is only a beam at the negative x and not the positive x
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else
                {
                    renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.75F, 1.0F, 0.75F);// 3/4 block
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamX = true;
            }
        }
        else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ (blockAccess.getBlockId(i+1, j, k) == supportIDv || blockAccess.getBlockId(i+1, j, k) == supportIDh))
        {
            if(hasVerticalBeam)//if the block does contain a vertical beam
            {
                renderblocks.setRenderBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            else
            {
                renderblocks.setRenderBounds(0.25F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);// 3/4 block
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            hasHorizontalBeamX = true;
        }
        //Z
        if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ (blockAccess.getBlockId(i, j, k-1) == supportIDv || blockAccess.getBlockId(i, j, k-1) == supportIDh))
        {
            if((blockAccess.getBlockId(i, j, k+1) == supportIDv || blockAccess.getBlockId(i, j, k+1) == supportIDh))//if the block above is solid and the block at +x is a support beam
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else if(!hasVerticalBeam)//if the block does not contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 1.0F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamZ = true;
            }
            else//if there is only a beam at the negative x and not the positive x
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else
                {
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.75F);// 3/4 block
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamZ = true;
            }
        }
        else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */(blockAccess.getBlockId(i, j, k+1) == supportIDv || blockAccess.getBlockId(i, j, k+1) == supportIDh))//Top is solid and positive Z is support
        {
            if(hasVerticalBeam)//if the block does contain a vertical beam
            {
                renderblocks.setRenderBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            else
            {
                renderblocks.setRenderBounds(0.25F, 0.50F, 0.25F, 0.75F, 1.0F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            hasHorizontalBeamZ = true;
        }

        float minX = -1;
        float minY = -1;
        float minZ = -1;

        float maxX = -1;
        float maxY = -1;
        float maxZ = -1;

        if(hasHorizontalBeamX)
        {
            minX = 0F;
            maxX = 1F;
            minZ = 0.25F;
            maxZ = 0.75F;
        }
        if(hasHorizontalBeamZ)
        {
            if(maxX == -1)
            {
                minX = 0.25F;
                maxX = 0.75F;
            }

            minZ = 0F;
            maxZ = 1F;

        }
        if(hasVerticalBeam)
        {
            minY = 0F;
            maxY = 1F;
            if(maxX == -1)
            {
                minX = 0.25F;
                maxX = 0.75F;
            }
            if(maxZ == -1)
            {
                minZ = 0.25F;
                maxZ = 0.75F;
            }
        }
        else
        {
            minY = 0.5F;
            maxY = 1F;
        }

        renderblocks.setRenderBounds(minX,minY, minZ, maxX, maxY, maxZ);

        return true;
    }

    public static boolean RenderWoodSupportBeamV(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        int supportIDv = TFCBlocks.WoodSupportV.blockID;
        int supportIDh = TFCBlocks.WoodSupportH.blockID;

        Boolean hasVerticalBeam = false;
        Boolean hasHorizontalBeamX = false;
        Boolean hasHorizontalBeamZ = false;

        //if the block directly beneath is a Vertical Support or a solid block
        if((blockAccess.isBlockOpaqueCube(i, j-1, k) || blockAccess.getBlockId(i, j-1, k) == supportIDv) && block.blockID == TFCBlocks.WoodSupportV.blockID)
        {	
            renderblocks.setRenderBounds(0.25F, 0.0F, 0.25F, 0.75F, 1.0F, 0.75F);
            renderblocks.renderStandardBlock(block, i, j, k);
            hasVerticalBeam = true;
        }

        //X
        if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) &&*/ (blockAccess.getBlockId(i-1, j, k) == supportIDv || blockAccess.getBlockId(i-1, j, k) == supportIDh))//if the block above is solid and the block at -x is a support beam
        {
            if((blockAccess.getBlockId(i+1, j, k) == supportIDv || blockAccess.getBlockId(i+1, j, k) == supportIDh))//if the block above is solid and the block at +x is a support beam
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                    renderblocks.setRenderBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else if(!hasVerticalBeam)//if the block does not contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamX = true;
            }
            else//if there is only a beam at the negative x and not the positive x
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.25F, 1.0F, 0.75F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else
                {
                    renderblocks.setRenderBounds(0.0F, 0.50F, 0.25F, 0.75F, 1.0F, 0.75F);// 3/4 block
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamX = true;
            }
        }
        else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */(blockAccess.getBlockId(i+1, j, k) == supportIDv || blockAccess.getBlockId(i+1, j, k) == supportIDh))
        {
            if(hasVerticalBeam)//if the block does contain a vertical beam
            {
                renderblocks.setRenderBounds(0.75F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            else
            {
                renderblocks.setRenderBounds(0.25F, 0.50F, 0.25F, 1.0F, 1.0F, 0.75F);// 3/4 block
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            hasHorizontalBeamX = true;
        }
        //Z
        if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */(blockAccess.getBlockId(i, j, k-1) == supportIDv || blockAccess.getBlockId(i, j, k-1) == supportIDh))
        {
            if((blockAccess.getBlockId(i, j, k+1) == supportIDv || blockAccess.getBlockId(i, j, k+1) == supportIDh))//if the block above is solid and the block at +x is a support beam
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else if(!hasVerticalBeam)//if the block does not contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 1.0F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamZ = true;
            }
            else//if there is only a beam at the negative x and not the positive x
            {
                if(hasVerticalBeam)//if the block does contain a vertical beam
                {
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.25F);
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                else
                {
                    renderblocks.setRenderBounds(0.25F, 0.50F, 0.0F, 0.75F, 1.0F, 0.75F);// 3/4 block
                    renderblocks.renderStandardBlock(block, i, j, k);
                }
                hasHorizontalBeamZ = true;
            }
        }
        else if(/*TFC_Core.isBlockAboveSolid(blockAccess, i, j, k) && */(blockAccess.getBlockId(i, j, k+1) == supportIDv || blockAccess.getBlockId(i, j, k+1) == supportIDh))//Top is solid and positive Z is support
        {
            if(hasVerticalBeam)//if the block does contain a vertical beam
            {
                renderblocks.setRenderBounds(0.25F, 0.50F, 0.75F, 0.75F, 1.0F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            else
            {
                renderblocks.setRenderBounds(0.25F, 0.50F, 0.25F, 0.75F, 1.0F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
            }
            hasHorizontalBeamZ = true;
        }

        float minX = 0.25F;
        float minY = 0;
        float minZ = 0.25F;

        float maxX = 0.75F;
        float maxY = 1;
        float maxZ = 0.75F;


        renderblocks.setRenderBounds(minX,minY, minZ, maxX, maxY, maxZ);

        return true;
    }

    public static void renderBlockFallingSand(Block block,int meta, World world, int i, int j, int k)
    {
       /* float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        Tessellator tessellator = Tessellator.instance;
        tessellator.startDrawingQuads();
        tessellator.setBrightness(block.getMixedBrightnessForBlock(world, i, j, k));
        float f4 = 1.0F;
        float f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f * f5, f * f5, f * f5);
        renderer.renderBottomFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(0, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f1 * f5, f1 * f5, f1 * f5);
        renderer.renderTopFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(1, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
        renderer.renderEastFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(2, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f2 * f5, f2 * f5, f2 * f5);
        renderer.renderWestFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(3, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
        renderer.renderNorthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(4, meta));
        f5 = 1.0F;
        if (f5 < f4)
        {
            f5 = f4;
        }
        tessellator.setColorOpaque_F(f3 * f5, f3 * f5, f3 * f5);
        renderer.renderSouthFace(block, -0.5D, -0.5D, -0.5D, block.getBlockTextureFromSideAndMetadata(5, meta));
        tessellator.draw();*/
    }

    public static boolean RenderOre(Block block, int xCoord, int yCoord, int zCoord,float par5, float par6, float par7, RenderBlocks renderblocks, IBlockAccess iblockaccess)
    {
    	/*boolean breaking = false;
        if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }

        if(!breaking)
        {
        	//render the background rock
            renderblocks.overrideBlockTexture = getRockTexture(ModLoader.getMinecraftInstance().theWorld, xCoord, yCoord, zCoord);
            renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
            renderblocks.clearOverrideBlockTexture();

            //render the ore overlay
            renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
        }

        //renderblocks.renderStandardBlock(block, xCoord, yCoord, zCoord);
*/
        return true;
    }

    public static Icon getRockTexture(World worldObj, int xCoord, int yCoord, int zCoord) 
    {
        Icon var27;
        DataLayer rockLayer1 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 0);
        DataLayer rockLayer2 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 1);
        DataLayer rockLayer3 = ((TFCWorldChunkManager)worldObj.getWorldChunkManager()).getRockLayerAt(xCoord, zCoord, 2);

        if(yCoord <= TFC_Settings.RockLayer3Height)
            var27 = Block.blocksList[rockLayer3.data1].getBlockTextureFromSideAndMetadata(5, rockLayer3.data2);
        else if(yCoord <= TFC_Settings.RockLayer2Height)
            var27 = Block.blocksList[rockLayer2.data1].getBlockTextureFromSideAndMetadata(5, rockLayer2.data2);
        else
            var27 = Block.blocksList[rockLayer1.data1].getBlockTextureFromSideAndMetadata(5, rockLayer1.data2);
        return var27;
    }

    public static boolean RenderMolten(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        renderblocks.renderStandardBlock(block, i, j, k);

        //renderblocks.func_83020_a(0.0F, 0.0F, 0.0F, 0.001F, 0.001F, 0.001F);
        return true;
    }

    public static boolean renderFirepit(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
        renderblocks.renderStandardBlock(block, i, j, k);

        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.02F, 1.0F);
        return true;
    }

    public static boolean renderForge(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        IBlockAccess blockAccess = renderblocks.blockAccess;

        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
        renderblocks.renderStandardBlock(block, i, j, k);


        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9F, 1.0F);
        return true;
    }

    public static boolean renderBellows(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        /*IBlockAccess blockAccess = renderblocks.blockAccess;

        int meta = blockAccess.getBlockMetadata(i, j, k);
        int direction = ((BlockBellows)block).getDirectionFromMetadata(meta);
        
        boolean breaking = false;
        if(renderblocks.overrideBlockTexture >= 240)
        {
        	breaking = true;
        }

        if(direction == 0)
        {
            //forward
        	if(!breaking) renderblocks.overrideBlockTexture = 86;
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
            //mid
            if(!breaking) renderblocks.overrideBlockTexture = -1;
            renderblocks.setRenderBounds(0.1F, 0.1F, 0.05F, 0.9F, 0.9F, 0.95F);
            renderblocks.renderStandardBlock(block, i, j, k);
            //back
            if(!breaking) renderblocks.overrideBlockTexture = 87;
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.overrideBlockTexture = -1;
        }
        else if(direction == 1)
        {
            //forward
        	if(!breaking) renderblocks.overrideBlockTexture = 86;
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //mid
            if(!breaking) renderblocks.overrideBlockTexture = -1;
            renderblocks.setRenderBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //back
            if(!breaking) renderblocks.overrideBlockTexture = 87;
            renderblocks.setRenderBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.overrideBlockTexture = -1;

        }
        else if(direction == 2)
        {
            //forward
        	if(!breaking) renderblocks.overrideBlockTexture = 86;
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.1F);
            renderblocks.renderStandardBlock(block, i, j, k);
            //mid
            if(!breaking) renderblocks.overrideBlockTexture = -1;
            renderblocks.setRenderBounds(0.1F, 0.1F, 0.05F, 0.9F, 0.9F, 0.95F);
            renderblocks.renderStandardBlock(block, i, j, k);
            //back
            if(!breaking) renderblocks.overrideBlockTexture = 87;
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.9F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.overrideBlockTexture = -1;
        }
        else if(direction == 3)
        {
            //forward
        	if(!breaking)  renderblocks.overrideBlockTexture = 86;
            renderblocks.setRenderBounds(0.9F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //mid
            if(!breaking) renderblocks.overrideBlockTexture = -1;
            renderblocks.setRenderBounds(0.1F, 0.1F, 0.1F, 0.9F, 0.9F, 0.9F);
            renderblocks.renderStandardBlock(block, i, j, k);

            //back
            if(!breaking) renderblocks.overrideBlockTexture = 87;
            renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 0.1F, 1.0F, 1.0F);
            renderblocks.renderStandardBlock(block, i, j, k);
            renderblocks.overrideBlockTexture = -1;
        }

        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);*/
        return true;
    }

    public static boolean RenderSluice(Block block, int i, int j, int k, RenderBlocks renderblocks)
    {
        
    	double blockMinX = block.getBlockBoundsMinX();
		double blockMaxX = block.getBlockBoundsMaxX();
		double blockMinY = block.getBlockBoundsMinY();
		double blockMaxY = block.getBlockBoundsMaxY();
		double blockMinZ = block.getBlockBoundsMinZ();
		double blockMaxZ = block.getBlockBoundsMaxZ();
        IBlockAccess blockAccess = renderblocks.blockAccess;
        Tessellator tessellator = Tessellator.instance;
        int l = blockAccess.getBlockMetadata(i, j, k);
        int i1 = ((BlockSluice)block).getDirectionFromMetadata(l);
        float f = 0.5F;
        float f1 = 1.0F;
        float f2 = 0.8F;
        float f3 = 0.6F;
        int j1 = block.getMixedBrightnessForBlock(blockAccess, i, j, k);
        tessellator.setBrightness(j1);
        tessellator.setColorOpaque_F(f, f, f);

        Icon texture = block.getBlockTexture(blockAccess, i, j, k, 0);
        double texMinX = texture.func_94209_e();
        double texMaxX = texture.func_94212_f();
        double texMinY = texture.func_94206_g();
        double texMaxY = texture.func_94210_h();

        double minX = (double)i + blockMinX;
        double maxX = (double)i + blockMaxX;
        double minY = (double)j + blockMinY;
        double minZ = (double)k + blockMinZ;
        double maxZ = (double)k + blockMaxZ;
        double maxY = (double)j + blockMaxY;

        int var10 = blockAccess.getBiomeGenForCoords(i, k).waterColorMultiplier;
        int waterR = (var10 & 16711680) >> 16;
        int waterG = (var10 & 65280) >> 8;
        int waterB = var10 & 255;

        //render ramp
        if(!((BlockSluice)block).isBlockFootOfBed(l))
        {
            if(i1 == 0)
            {
                //ribs
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.75F, 1F, 0.75F, 0.8F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.45F, 1F, 0.9F, 0.5F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, maxY, minZ, texMaxX, texMinY);//d,d3
                tessellator.addVertexWithUV(minX, minY+0.5F, maxZ, texMaxX, texMaxY);//d,d2
                tessellator.addVertexWithUV(maxX, minY+0.5f, maxZ, texMinX, texMaxY);//d1,d2
                tessellator.addVertexWithUV(maxX, maxY, minZ, texMinX, texMinY);//d1,d3
                if(((BlockSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    texture = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    texMinX = texture.func_94209_e();
                    texMaxX = texture.func_94212_f();
                    texMinY = texture.func_94206_g();
                    texMaxY = texture.func_94210_h();

                    //draw water plane
                    //tessellator.setColorOpaque_F(0.8F, 0.8F, 0.8F);
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, maxY, minZ, texMaxX, texMinY);//d,d3
                    tessellator.addVertexWithUV(minX, minY+0.6F, maxZ, texMaxX, texMaxY);//d,d2
                    tessellator.addVertexWithUV(maxX, minY+0.6f, maxZ, texMinX, texMaxY);//d1,d2
                    tessellator.addVertexWithUV(maxX, maxY, minZ, texMinX, texMinY);//d1,d3
                }
            }
            else if(i1 == 1)
            {
                //ribs
                renderblocks.setRenderBounds(0.2F, 0.0F, 0.0F, 0.25F, 0.75F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.5F, 0.0F, 0.0F, 0.55F, 0.9F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY+0.5F, maxZ, texMinX, texMaxY);
                tessellator.addVertexWithUV(maxX, maxY, maxZ, texMinX, texMinY);
                tessellator.addVertexWithUV(maxX, maxY, minZ, texMaxX, texMinY);
                tessellator.addVertexWithUV(minX, minY+0.5F, minZ, texMaxX, texMaxY);

                if(((BlockSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    texture = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    texMinX = texture.func_94209_e();
                    texMaxX = texture.func_94212_f();
                    texMinY = texture.func_94206_g();
                    texMaxY = texture.func_94210_h();

                    //draw water plane
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY+0.6F, maxZ, texMinX, texMaxY);
                    tessellator.addVertexWithUV(maxX, maxY, maxZ, texMinX, texMinY);
                    tessellator.addVertexWithUV(maxX, maxY, minZ, texMaxX, texMinY);
                    tessellator.addVertexWithUV(minX, minY+0.6F, minZ, texMaxX, texMaxY);
                }
            }
            else if(i1 == 2)
            {
                //ribs
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.2F, 1F, 0.75F, 0.25F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.5F, 1F, 0.9F, 0.55F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY+0.5F, minZ, texMinX, texMaxY);
                tessellator.addVertexWithUV(minX, maxY, maxZ, texMinX, texMinY);
                tessellator.addVertexWithUV(maxX, maxY, maxZ, texMaxX, texMinY);
                tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, texMaxX, texMaxY);

                if(((BlockSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    texture = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    texMinX = texture.func_94209_e();
                    texMaxX = texture.func_94212_f();
                    texMinY = texture.func_94206_g();
                    texMaxY = texture.func_94210_h();

                    //draw water plane
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY+0.6F, minZ, texMinX, texMaxY);
                    tessellator.addVertexWithUV(minX, maxY, maxZ, texMinX, texMinY);
                    tessellator.addVertexWithUV(maxX, maxY, maxZ, texMaxX, texMinY);
                    tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, texMaxX, texMaxY);
                }


            }
            else if(i1 == 3)
            {        
                //ribs
                renderblocks.setRenderBounds(0.75F, 0.0F, 0.0F, 0.8F, 0.75F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.45F, 0.0F, 0.0F, 0.5F, 0.9F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(maxX, minY+0.5f, minZ, texMinX, texMaxY);
                tessellator.addVertexWithUV(minX, maxY, minZ, texMinX, texMinY);
                tessellator.addVertexWithUV(minX, maxY, maxZ, texMaxX, texMinY);
                tessellator.addVertexWithUV(maxX, minY+0.5F, maxZ, texMaxX, texMaxY);

                if(((BlockSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    texture = Block.waterStill.getBlockTextureFromSide(0);
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    texMinX = texture.func_94209_e();
                    texMaxX = texture.func_94212_f();
                    texMinY = texture.func_94206_g();
                    texMaxY = texture.func_94210_h();

                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(maxX, minY+0.6f, minZ, texMinX, texMaxY);
                    tessellator.addVertexWithUV(minX, maxY, minZ, texMinX, texMinY);
                    tessellator.addVertexWithUV(minX, maxY, maxZ, texMaxX, texMinY);
                    tessellator.addVertexWithUV(maxX, minY+0.6F, maxZ, texMaxX, texMaxY);
                }
            }
        }
        else
        {
            if(i1 == 0)
            {
                //ribs
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.70F, 1F, 0.3F, 0.75F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.4F, 1F, 0.45F, 0.45F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.1F, 1F, 0.6F, 0.15F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY+0.5F, minZ, texMaxX, texMinY);//d,d3
                tessellator.addVertexWithUV(minX, minY, maxZ, texMaxX, texMaxY);//d,d2
                tessellator.addVertexWithUV(maxX, minY, maxZ, texMinX, texMaxY);//d1,d2
                tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, texMinX, texMinY);//d1,d3

                if(((BlockSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    texture = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    texMinX = texture.func_94209_e();
                    texMaxX = texture.func_94212_f();
                    texMinY = texture.func_94206_g();
                    texMaxY = texture.func_94210_h();

                    //draw water plane
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY+0.6F, minZ, texMaxX, texMinY);//d,d3
                    tessellator.addVertexWithUV(minX, minY, maxZ, texMaxX, texMaxY);//d,d2
                    tessellator.addVertexWithUV(maxX, minY, maxZ, texMinX, texMaxY);//d1,d2
                    tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, texMinX, texMinY);//d1,d3
                }
            }
            if(i1 == 1)
            {
                //ribs
                renderblocks.setRenderBounds(0.9F, 0.0F, 0.0F, 0.95F, 0.6F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.6F, 0.0F, 0.0F, 0.65F, 0.45F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.3F, 0.0F, 0.0F, 0.35F, 0.3F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY, maxZ, texMinX, texMaxY);
                tessellator.addVertexWithUV(maxX, minY+0.5F, maxZ, texMinX, texMinY);
                tessellator.addVertexWithUV(maxX, minY+0.5F, minZ, texMaxX, texMinY);
                tessellator.addVertexWithUV(minX, minY, minZ, texMaxX, texMaxY);

                if(((BlockSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    texture = block.getBlockTextureFromSideAndMetadata(0, 4);
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    texMinX = texture.func_94209_e();
                    texMaxX = texture.func_94212_f();
                    texMinY = texture.func_94206_g();
                    texMaxY = texture.func_94210_h();

                    //draw water plane
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY, maxZ, texMinX, texMaxY);
                    tessellator.addVertexWithUV(maxX, minY+0.6F, maxZ, texMinX, texMinY);
                    tessellator.addVertexWithUV(maxX, minY+0.6F, minZ, texMaxX, texMinY);
                    tessellator.addVertexWithUV(minX, minY, minZ, texMaxX, texMaxY);
                }
            }
            if(i1 == 2)
            {               
                //ribs
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.3F, 1F, 0.3F, 0.35F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.6F, 1F, 0.45F, 0.65F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.0F, 0.0F, 0.9F, 1F, 0.6F, 0.95F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(minX, minY, minZ, texMinX, texMaxY);
                tessellator.addVertexWithUV(minX, minY+0.5f, maxZ, texMinX, texMinY);
                tessellator.addVertexWithUV(maxX, minY+0.5f, maxZ, texMaxX, texMinY);
                tessellator.addVertexWithUV(maxX, minY, minZ, texMaxX, texMaxY);

                if(((BlockSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    texture = Block.waterStill.getBlockTextureFromSide(0);
                    l = block.colorMultiplier(blockAccess, i, j, k);

                    //reassign the uv coords
                    texMinX = texture.func_94209_e();
                    texMaxX = texture.func_94212_f();
                    texMinY = texture.func_94206_g();
                    texMaxY = texture.func_94210_h();

                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(minX, minY, minZ, texMinX, texMaxY);
                    tessellator.addVertexWithUV(minX, minY+0.6f, maxZ, texMinX, texMinY);
                    tessellator.addVertexWithUV(maxX, minY+0.6f, maxZ, texMaxX, texMinY);
                    tessellator.addVertexWithUV(maxX, minY, minZ, texMaxX, texMaxY);
                }
            }
            if(i1 == 3)
            {               
                //ribs
                renderblocks.setRenderBounds(0.7F, 0.0F, 0.0F, 0.75F, 0.3F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.4F, 0.0F, 0.0F, 0.45F, 0.45F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);
                renderblocks.setRenderBounds(0.1F, 0.0F, 0.0F, 0.15F, 0.6F, 1.0F);
                renderblocks.renderStandardBlock(block, i, j, k);

                tessellator.setColorOpaque_F(f * f1, f * f2, f * f3);
                tessellator.addVertexWithUV(maxX, minY, minZ, texMinX, texMaxY);
                tessellator.addVertexWithUV(minX, minY+0.5f, minZ, texMinX, texMinY);
                tessellator.addVertexWithUV(minX, minY+0.5f, maxZ, texMaxX, texMinY);
                tessellator.addVertexWithUV(maxX, minY, maxZ, texMaxX, texMaxY);

                if(((BlockSluice)block).getIsRecievingWater(l))
                {
                    //get water texture
                    texture = Block.waterStill.getBlockTextureFromSide(0);
                    l = block.colorMultiplier(blockAccess, i, j, k);
                    //reassign the uv coords
                    texMinX = texture.func_94209_e();
                    texMaxX = texture.func_94212_f();
                    texMinY = texture.func_94206_g();
                    texMaxY = texture.func_94210_h();
                    tessellator.setColorOpaque(waterR, waterG, waterB);
                    tessellator.addVertexWithUV(maxX, minY, minZ, texMinX, texMaxY);
                    tessellator.addVertexWithUV(minX, minY+0.6f, minZ, texMinX, texMinY);
                    tessellator.addVertexWithUV(minX, minY+0.6f, maxZ, texMaxX, texMinY);
                    tessellator.addVertexWithUV(maxX, minY, maxZ, texMaxX, texMaxY);
                }
            }
        }


        //set the block collision box
        renderblocks.setRenderBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);

        return true;
    }

    public static boolean RenderFruitLeaves(Block block, int xCoord, int yCoord, int zCoord,float par5, float par6, float par7, RenderBlocks renderblocks)
    {
    	double blockMinX = block.getBlockBoundsMinX();
		double blockMaxX = block.getBlockBoundsMaxX();
		double blockMinY = block.getBlockBoundsMinY();
		double blockMaxY = block.getBlockBoundsMaxY();
		double blockMinZ = block.getBlockBoundsMinZ();
		double blockMaxZ = block.getBlockBoundsMaxZ();
		
        int meta = renderblocks.blockAccess.getBlockMetadata(xCoord, yCoord, zCoord);
        if(meta >= 8)
            meta-=8;
        FloraManager manager = FloraManager.getInstance();
        FloraIndex index = manager.findMatchingIndex(BlockFruitLeaves.getType(block.blockID, meta));

        if(index == null)
            return false;
        renderblocks.enableAO = true;
        boolean var8 = false;
        float var9 = renderblocks.lightValueOwn;
        float var10 = renderblocks.lightValueOwn;
        float var11 = renderblocks.lightValueOwn;
        float var12 = renderblocks.lightValueOwn;
        boolean var13 = true;
        boolean var14 = true;
        boolean var15 = true;
        boolean var16 = true;
        boolean var17 = true;
        boolean var18 = true;
        renderblocks.lightValueOwn = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord);
        renderblocks.aoLightValueXNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
        renderblocks.aoLightValueYNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
        renderblocks.aoLightValueZNeg = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
        renderblocks.aoLightValueXPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
        renderblocks.aoLightValueYPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
        renderblocks.aoLightValueZPos = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
        int var19 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord);
        int var20 = var19;
        int var21 = var19;
        int var22 = var19;
        int var23 = var19;
        int var24 = var19;
        int var25 = var19;

        if (blockMinY <= 0.0D)
        {
            var21 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
        }

        if (blockMaxY >= 1.0D)
        {
            var24 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
        }

        if (blockMinX <= 0.0D)
        {
            var20 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
        }

        if (blockMaxX >= 1.0D)
        {
            var23 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
        }

        if (blockMinZ <= 0.0D)
        {
            var22 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
        }

        if (blockMaxZ >= 1.0D)
        {
            var25 = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
        }

        Tessellator tesselator = Tessellator.instance;
        tesselator.setBrightness(983055);
        renderblocks.aoGrassXYZPPC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord + 1, zCoord)];
        renderblocks.aoGrassXYZPNC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord - 1, zCoord)];
        renderblocks.aoGrassXYZPCP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord, zCoord + 1)];
        renderblocks.aoGrassXYZPCN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord + 1, yCoord, zCoord - 1)];
        renderblocks.aoGrassXYZNPC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord + 1, zCoord)];
        renderblocks.aoGrassXYZNNC = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord - 1, zCoord)];
        renderblocks.aoGrassXYZNCN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord, zCoord - 1)];
        renderblocks.aoGrassXYZNCP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord - 1, yCoord, zCoord + 1)];
        renderblocks.aoGrassXYZCPP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord + 1, zCoord + 1)];
        renderblocks.aoGrassXYZCPN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord + 1, zCoord - 1)];
        renderblocks.aoGrassXYZCNP = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord - 1, zCoord + 1)];
        renderblocks.aoGrassXYZCNN = Block.canBlockGrass[renderblocks.blockAccess.getBlockId(xCoord, yCoord - 1, zCoord - 1)];

        Icon texIndex;
        float colorMult = 0.78F;

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord, 0))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMinY <= 0.0D)
                {
                    --yCoord;
                }

                renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);

                if (!renderblocks.aoGrassXYZCNN && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXYNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXYNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCNP && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXYNN;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXYNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZCNN && !renderblocks.aoGrassXYZPNC)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXYPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXYPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCNP && !renderblocks.aoGrassXYZPNC)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXYPN;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXYPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                }

                if (blockMinY <= 0.0D)
                {
                    ++yCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueYNeg) / 4.0F;
                var12 = (renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXYPN) / 4.0F;
                var11 = (renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNN) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueYNeg + renderblocks.aoLightValueScratchYZNN) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessYZNP, var21);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXYPN, var21);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNN, var21);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessYZNN, var21);
            }
            else
            {
                var12 = renderblocks.aoLightValueYNeg;
                var11 = renderblocks.aoLightValueYNeg;
                var10 = renderblocks.aoLightValueYNeg;
                var9 = renderblocks.aoLightValueYNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = renderblocks.aoBrightnessXYNN;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            renderblocks.renderBottomFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 0));

            if(index.inBloom(TFC_Time.currentMonth))
            {
                texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
                if(texIndex != null)
                {
                    renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                    renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                    renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                    renderblocks.renderBottomFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
                }
            }
            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord, 1))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMaxY >= 1.0D)
                {
                    ++yCoord;
                }

                renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);

                if (!renderblocks.aoGrassXYZCPN && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXYNP;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXYNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCPN && !renderblocks.aoGrassXYZPPC)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXYPP;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXYPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZCPP && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXYNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXYNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZCPP && !renderblocks.aoGrassXYZPPC)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXYPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXYPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord + 1);
                }

                if (blockMaxY >= 1.0D)
                {
                    --yCoord;
                }

                var12 = (renderblocks.aoLightValueScratchXYZNPP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueYPos) / 4.0F;
                var9 = (renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchXYZPPP + renderblocks.aoLightValueScratchXYPP) / 4.0F;
                var10 = (renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueYPos + renderblocks.aoLightValueScratchYZPN) / 4.0F;
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessYZPP, var24);
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXYZPPP, renderblocks.aoBrightnessXYPP, var24);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPN, var24);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, var24);
            }
            else
            {
                var12 = renderblocks.aoLightValueYPos;
                var11 = renderblocks.aoLightValueYPos;
                var10 = renderblocks.aoLightValueYPos;
                var9 = renderblocks.aoLightValueYPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var24;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 1);
            renderblocks.renderTopFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            if(index.inBloom(TFC_Time.currentMonth))
            {
                texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
                if(texIndex != null)
                {
                    renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                    renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                    renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                    renderblocks.renderTopFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
                }
            }
            var8 = true;
        }



        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1, 2))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMinZ <= 0.0D)
                {
                    --zCoord;
                }

                renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchYZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessYZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZCNN)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZCPN)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCN && !renderblocks.aoGrassXYZCNN)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCN && !renderblocks.aoGrassXYZCPN)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                }

                if (blockMinZ <= 0.0D)
                {
                    ++zCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchYZPN) / 4.0F;
                var10 = (renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchYZPN + renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueScratchXYZPPN) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueZNeg + renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXZPN) / 4.0F;
                var12 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueScratchYZNN + renderblocks.aoLightValueZNeg) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessYZPN, var22);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPN, renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, var22);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNN, renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXZPN, var22);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessYZNN, var22);
            }
            else
            {
                var12 = renderblocks.aoLightValueZNeg;
                var11 = renderblocks.aoLightValueZNeg;
                var10 = renderblocks.aoLightValueZNeg;
                var9 = renderblocks.aoLightValueZNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var22;
            }



            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 2);
            renderblocks.renderEastFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
            if(texIndex != null)
            {
                renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                renderblocks.renderEastFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
            }



            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1, 3))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMaxZ >= 1.0D)
                {
                    ++zCoord;
                }

                renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoLightValueScratchYZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchYZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord);
                renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord);
                renderblocks.aoBrightnessYZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessYZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZCNP)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZCPP)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord - 1, yCoord + 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCP && !renderblocks.aoGrassXYZCNP)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord - 1, zCoord);
                }

                if (!renderblocks.aoGrassXYZPCP && !renderblocks.aoGrassXYZCPP)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord + 1, yCoord + 1, zCoord);
                }

                if (blockMaxZ >= 1.0D)
                {
                    --zCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYZNPP + renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchYZPP) / 4.0F;
                var12 = (renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchYZPP + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueZPos + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueScratchXZPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchYZNP + renderblocks.aoLightValueZPos) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYZNPP, renderblocks.aoBrightnessYZPP, var25);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZPP, renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYZPPP, var25);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessYZNP, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, var25);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessYZNP, var25);
            }
            else
            {
                var12 = renderblocks.aoLightValueZPos;
                var11 = renderblocks.aoLightValueZPos;
                var10 = renderblocks.aoLightValueZPos;
                var9 = renderblocks.aoLightValueZPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var25;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 3);
            renderblocks.renderWestFace(block, (double)xCoord, (double)yCoord, (double)zCoord, block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 3));

            texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
            if(texIndex!= null)
            {
                renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                renderblocks.renderWestFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord - 1, yCoord, zCoord, 4))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMinX <= 0.0D)
                {
                    --xCoord;
                }

                renderblocks.aoLightValueScratchXYNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchXZNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchXZNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXYNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessXZNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessXZNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoBrightnessXYNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNNN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZNNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZNNC)
                {
                    renderblocks.aoLightValueScratchXYZNNP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNNP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZNNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZNCN && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPN = renderblocks.aoLightValueScratchXZNN;
                    renderblocks.aoBrightnessXYZNPN = renderblocks.aoBrightnessXZNN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZNPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZNCP && !renderblocks.aoGrassXYZNPC)
                {
                    renderblocks.aoLightValueScratchXYZNPP = renderblocks.aoLightValueScratchXZNP;
                    renderblocks.aoBrightnessXYZNPP = renderblocks.aoBrightnessXZNP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZNPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZNPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                }

                if (blockMinX <= 0.0D)
                {
                    ++xCoord;
                }

                var12 = (renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXYZNNP + renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXZNP) / 4.0F;
                var9 = (renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXZNP + renderblocks.aoLightValueScratchXYNP + renderblocks.aoLightValueScratchXYZNPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueXNeg + renderblocks.aoLightValueScratchXYZNPN + renderblocks.aoLightValueScratchXYNP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXYZNNN + renderblocks.aoLightValueScratchXYNN + renderblocks.aoLightValueScratchXZNN + renderblocks.aoLightValueXNeg) / 4.0F;
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXYZNNP, renderblocks.aoBrightnessXZNP, var20);
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNP, renderblocks.aoBrightnessXYNP, renderblocks.aoBrightnessXYZNPP, var20);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZNN, renderblocks.aoBrightnessXYZNPN, renderblocks.aoBrightnessXYNP, var20);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZNNN, renderblocks.aoBrightnessXYNN, renderblocks.aoBrightnessXZNN, var20);
            }
            else
            {
                var12 = renderblocks.aoLightValueXNeg;
                var11 = renderblocks.aoLightValueXNeg;
                var10 = renderblocks.aoLightValueXNeg;
                var9 = renderblocks.aoLightValueXNeg;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var20;
            }


            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 4);
            renderblocks.renderNorthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
            if(texIndex!= null)
            {
                renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                renderblocks.renderNorthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
            }

            var8 = true;
        }

        if (renderblocks.renderAllFaces || block.shouldSideBeRendered(renderblocks.blockAccess, xCoord + 1, yCoord, zCoord, 5))
        {
            if (renderblocks.aoType > 0)
            {
                if (blockMaxX >= 1.0D)
                {
                    ++xCoord;
                }

                renderblocks.aoLightValueScratchXYPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoLightValueScratchXZPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoLightValueScratchXZPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoLightValueScratchXYPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);
                renderblocks.aoBrightnessXYPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord);
                renderblocks.aoBrightnessXZPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord - 1);
                renderblocks.aoBrightnessXZPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord, zCoord + 1);
                renderblocks.aoBrightnessXYPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord);

                if (!renderblocks.aoGrassXYZPNC && !renderblocks.aoGrassXYZPCN)
                {
                    renderblocks.aoLightValueScratchXYZPNN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPNN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZPNN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZPNC && !renderblocks.aoGrassXYZPCP)
                {
                    renderblocks.aoLightValueScratchXYZPNP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPNP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPNP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZPNP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord - 1, zCoord + 1);
                }

                if (!renderblocks.aoGrassXYZPPC && !renderblocks.aoGrassXYZPCN)
                {
                    renderblocks.aoLightValueScratchXYZPPN = renderblocks.aoLightValueScratchXZPN;
                    renderblocks.aoBrightnessXYZPPN = renderblocks.aoBrightnessXZPN;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPN = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                    renderblocks.aoBrightnessXYZPPN = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord - 1);
                }

                if (!renderblocks.aoGrassXYZPPC && !renderblocks.aoGrassXYZPCP)
                {
                    renderblocks.aoLightValueScratchXYZPPP = renderblocks.aoLightValueScratchXZPP;
                    renderblocks.aoBrightnessXYZPPP = renderblocks.aoBrightnessXZPP;
                }
                else
                {
                    renderblocks.aoLightValueScratchXYZPPP = block.getAmbientOcclusionLightValue(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                    renderblocks.aoBrightnessXYZPPP = block.getMixedBrightnessForBlock(renderblocks.blockAccess, xCoord, yCoord + 1, zCoord + 1);
                }

                if (blockMaxX >= 1.0D)
                {
                    --xCoord;
                }

                var9 = (renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXYZPNP + renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXZPP) / 4.0F;
                var12 = (renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXZPP + renderblocks.aoLightValueScratchXYPP + renderblocks.aoLightValueScratchXYZPPP) / 4.0F;
                var11 = (renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueXPos + renderblocks.aoLightValueScratchXYZPPN + renderblocks.aoLightValueScratchXYPP) / 4.0F;
                var10 = (renderblocks.aoLightValueScratchXYZPNN + renderblocks.aoLightValueScratchXYPN + renderblocks.aoLightValueScratchXZPN + renderblocks.aoLightValueXPos) / 4.0F;
                renderblocks.brightnessTopLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXYZPNP, renderblocks.aoBrightnessXZPP, var23);
                renderblocks.brightnessTopRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPP, renderblocks.aoBrightnessXYPP, renderblocks.aoBrightnessXYZPPP, var23);
                renderblocks.brightnessBottomRight = renderblocks.getAoBrightness(renderblocks.aoBrightnessXZPN, renderblocks.aoBrightnessXYZPPN, renderblocks.aoBrightnessXYPP, var23);
                renderblocks.brightnessBottomLeft = renderblocks.getAoBrightness(renderblocks.aoBrightnessXYZPNN, renderblocks.aoBrightnessXYPN, renderblocks.aoBrightnessXZPN, var23);
            }
            else
            {
                var12 = renderblocks.aoLightValueXPos;
                var11 = renderblocks.aoLightValueXPos;
                var10 = renderblocks.aoLightValueXPos;
                var9 = renderblocks.aoLightValueXPos;
                renderblocks.brightnessTopLeft = renderblocks.brightnessBottomLeft = renderblocks.brightnessBottomRight = renderblocks.brightnessTopRight = var23;
            }

            renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = (var18 ? par5 : 1.0F) * colorMult;
            renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = (var18 ? par6 : 1.0F) * colorMult;
            renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = (var18 ? par7 : 1.0F) * colorMult;

            texIndex = block.getBlockTexture(renderblocks.blockAccess, xCoord, yCoord, zCoord, 5);
            renderblocks.renderSouthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);

            texIndex = getFruitTreeOverlay(renderblocks.blockAccess,xCoord,yCoord,zCoord);
            if(texIndex != null)
            {
                renderblocks.colorRedTopLeft = renderblocks.colorRedBottomLeft = renderblocks.colorRedBottomRight = renderblocks.colorRedTopRight = ( 1.0F) * colorMult;
                renderblocks.colorGreenTopLeft = renderblocks.colorGreenBottomLeft = renderblocks.colorGreenBottomRight = renderblocks.colorGreenTopRight = ( 1.0F) * colorMult;
                renderblocks.colorBlueTopLeft = renderblocks.colorBlueBottomLeft = renderblocks.colorBlueBottomRight = renderblocks.colorBlueTopRight = ( 1.0F) * colorMult;
                renderblocks.renderSouthFace(block, (double)xCoord, (double)yCoord, (double)zCoord, texIndex);
            }

            var8 = true;
        }

        renderblocks.enableAO = false;
        return var8;
    }

    public static Icon getFruitTreeOverlay(IBlockAccess world, int x, int y, int z)
    {
        Icon out = null;
        int meta = world.getBlockMetadata(x, y, z);
        int id = world.getBlockId(x, y, z);
        int offset = id == TFCBlocks.fruitTreeLeaves.blockID ? 0 : 8;

        FloraManager manager = FloraManager.getInstance();
        FloraIndex index = manager.findMatchingIndex(BlockFruitLeaves.getType(id, meta & 7));
        if(index != null)
        {
            if(index.inBloom(TFC_Time.currentMonth))//blooming
            {
                out = BlockFruitLeaves.iconsFlowers[(meta & 7)+offset];
            }
            else if(meta >= 8)//fruit
            {
                out = BlockFruitLeaves.iconsFruit[(meta & 7)+offset];
            }
        }
        return out;
    }

    /**
     * Renders a block based on the BlockFluids class at the given coordinates
     */
    public static boolean RenderFiniteWater(Block block, int par2, int par3, int par4, RenderBlocks renderblocks)
    {
    	/*double blockMinX = block.getBlockBoundsMinX();
		double blockMaxX = block.getBlockBoundsMaxX();
		double blockMinY = block.getBlockBoundsMinY();
		double blockMaxY = block.getBlockBoundsMaxY();
		double blockMinZ = block.getBlockBoundsMinZ();
		double blockMaxZ = block.getBlockBoundsMaxZ();
        Tessellator var5 = Tessellator.instance;
        int var6 = block.colorMultiplier(renderblocks.blockAccess, par2, par3, par4);
        float var7 = (float)(var6 >> 16 & 255) / 255.0F;
        float var8 = (float)(var6 >> 8 & 255) / 255.0F;
        float var9 = (float)(var6 & 255) / 255.0F;
        boolean var10 = block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 + 1, par4, 1);
        boolean var11 = block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3 - 1, par4, 0);
        boolean[] var12 = new boolean[] {block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 - 1, 2), block.shouldSideBeRendered(renderblocks.blockAccess, par2, par3, par4 + 1, 3), block.shouldSideBeRendered(renderblocks.blockAccess, par2 - 1, par3, par4, 4), block.shouldSideBeRendered(renderblocks.blockAccess, par2 + 1, par3, par4, 5)};

        if (!var10 && !var11 && !var12[0] && !var12[1] && !var12[2] && !var12[3])
        {
            return false;
        }
        else
        {
            boolean var13 = false;
            float var14 = 0.5F;
            float var15 = 1.0F;
            float var16 = 0.8F;
            float var17 = 0.6F;
            double var18 = 0.0D;
            double var20 = 1.0D;
            Material var22 = block.blockMaterial;
            int var23 = renderblocks.blockAccess.getBlockMetadata(par2, par3, par4);
            double var24 = (double)renderblocks.getFluidHeight(par2, par3, par4, var22);
            double var26 = (double)renderblocks.getFluidHeight(par2, par3, par4 + 1, var22);
            double var28 = (double)renderblocks.getFluidHeight(par2 + 1, par3, par4 + 1, var22);
            double var30 = (double)renderblocks.getFluidHeight(par2 + 1, par3, par4, var22);
            double var32 = 0.0010000000474974513D;
            Icon var34;
            int var35;
            float var36;
            int var37;
            double var42;
            double var40;
            double var44;

            if (renderblocks.renderAllFaces || var10)
            {
                var13 = true;
                var34 = block.getBlockTextureFromSideAndMetadata(1, var23);
                var36 = (float)BlockFiniteWater.func_293_a(renderblocks.blockAccess, par2, par3, par4, var22);

                if (var36 > -999.0F)
                {
                    var34 = block.getBlockTextureFromSideAndMetadata(2, var23);
                }

                var24 -= var32;
                var26 -= var32;
                var28 -= var32;
                var30 -= var32;
                var37 = (var34 & 15) << 4;
                var35 = var34 & 240;
                double var38 = ((double)var37 + 8.0D) / 256.0D;
                var40 = ((double)var35 + 8.0D) / 256.0D;

                if (var36 < -999.0F)
                {
                    var36 = 0.0F;
                }
                else
                {
                    var38 = (double)((float)(var37 + 16) / 256.0F);
                    var40 = (double)((float)(var35 + 16) / 256.0F);
                }

                var42 = (double)(MathHelper.sin(var36) * 8.0F) / 256.0D;
                var44 = (double)(MathHelper.cos(var36) * 8.0F) / 256.0D;
                var5.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3, par4));
                float var46 = 1.0F;
                var5.setColorOpaque_F(var15 * var46 * var7, var15 * var46 * var8, var15 * var46 * var9);
                var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var24, (double)(par4 + 0), var38 - var44 - var42, var40 - var44 + var42);
                var5.addVertexWithUV((double)(par2 + 0), (double)par3 + var26, (double)(par4 + 1), var38 - var44 + var42, var40 + var44 + var42);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var28, (double)(par4 + 1), var38 + var44 + var42, var40 + var44 - var42);
                var5.addVertexWithUV((double)(par2 + 1), (double)par3 + var30, (double)(par4 + 0), var38 + var44 - var42, var40 - var44 - var42);
            }

            if (renderblocks.renderAllFaces || var11)
            {
                var5.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, par2, par3 - 1, par4));
                var36 = 1.0F;
                var5.setColorOpaque_F(var14 * var36, var14 * var36, var14 * var36);
                renderblocks.renderBottomFace(block, (double)par2, (double)par3 + var32, (double)par4, block.getBlockTextureFromSide(0));
                var13 = true;
            }

            for (var34 = 0; var34 < 4; ++var34)
            {
                int var64 = par2;
                var35 = par4;

                if (var34 == 0)
                {
                    var35 = par4 - 1;
                }

                if (var34 == 1)
                {
                    ++var35;
                }

                if (var34 == 2)
                {
                    var64 = par2 - 1;
                }

                if (var34 == 3)
                {
                    ++var64;
                }

                var37 = block.getBlockTextureFromSideAndMetadata(var34 + 2, var23);
                int var63 = (var37 & 15) << 4;
                int var39 = var37 & 240;

                if (renderblocks.renderAllFaces || var12[var34])
                {
                    double var65;
                    double var50;
                    double var48;

                    if (var34 == 0)
                    {
                        var42 = var24;
                        var40 = var30;
                        var65 = (double)par2;
                        var50 = (double)(par2 + 1);
                        var44 = (double)par4 + var32;
                        var48 = (double)par4 + var32;
                    }
                    else if (var34 == 1)
                    {
                        var42 = var28;
                        var40 = var26;
                        var65 = (double)(par2 + 1);
                        var50 = (double)par2;
                        var44 = (double)(par4 + 1) - var32;
                        var48 = (double)(par4 + 1) - var32;
                    }
                    else if (var34 == 2)
                    {
                        var42 = var26;
                        var40 = var24;
                        var65 = (double)par2 + var32;
                        var50 = (double)par2 + var32;
                        var44 = (double)(par4 + 1);
                        var48 = (double)par4;
                    }
                    else
                    {
                        var42 = var30;
                        var40 = var28;
                        var65 = (double)(par2 + 1) - var32;
                        var50 = (double)(par2 + 1) - var32;
                        var44 = (double)par4;
                        var48 = (double)(par4 + 1);
                    }

                    var13 = true;
                    double var52 = (double)((float)(var63 + 0) / 256.0F);
                    double var54 = ((double)(var63 + 16) - 0.01D) / 256.0D;
                    double var56 = ((double)var39 + (1.0D - var42) * 16.0D) / 256.0D;
                    double var58 = ((double)var39 + (1.0D - var40) * 16.0D) / 256.0D;
                    double var60 = ((double)(var39 + 16) - 0.01D) / 256.0D;
                    var5.setBrightness(block.getMixedBrightnessForBlock(renderblocks.blockAccess, var64, par3, var35));
                    float var62 = 1.0F;

                    if (var34 < 2)
                    {
                        var62 *= var16;
                    }
                    else
                    {
                        var62 *= var17;
                    }

                    var5.setColorOpaque_F(var15 * var62 * var7, var15 * var62 * var8, var15 * var62 * var9);
                    var5.addVertexWithUV(var65, (double)par3 + var42, var44, var52, var56);
                    var5.addVertexWithUV(var50, (double)par3 + var40, var48, var54, var58);
                    var5.addVertexWithUV(var50, (double)(par3 + 0), var48, var54, var60);
                    var5.addVertexWithUV(var65, (double)(par3 + 0), var44, var52, var60);
                }
            }

            blockMinY = var18;
            blockMaxY = var20;
            return var13;
        }*/
    	
    	return true;
    }

    private static void drawCrossedSquares(Block block, int x, int y, int z, RenderBlocks renderblocks)
    {
        Tessellator var9 = Tessellator.instance;

        var9.setColorOpaque_F(1.0f, 1.0f, 1.0f);
        GL11.glColor3f(1, 1, 1);
        
        Icon index = block.getBlockTexture(renderblocks.blockAccess, x, y, z, 0);
        
        
        double minX = index.func_94209_e();
        double maxX = index.func_94212_f();
        double minY = index.func_94206_g();
        double maxY = index.func_94210_h();
        
        double xMin = x + 0.5D - 0.45D;
        double xMax = x + 0.5D + 0.45D;
        double zMin = z + 0.5D - 0.45D;
        double zMax = z + 0.5D + 0.45D;
        
        var9.addVertexWithUV(xMin, y + 0, zMin, minX, minY);
        var9.addVertexWithUV(xMin, y + 0.0D, zMin, minX, maxY);
        var9.addVertexWithUV(xMax, y + 0.0D, zMax, maxX, maxY);
        var9.addVertexWithUV(xMax, y + 0, zMax, maxX, minY);
        
        var9.addVertexWithUV(xMax, y + 0, zMax, minX, minY);
        var9.addVertexWithUV(xMax, y + 0.0D, zMax, minX, maxY);
        var9.addVertexWithUV(xMin, y + 0.0D, zMin, maxX, maxY);
        var9.addVertexWithUV(xMin, y + 0, zMin, maxX, minY);
        
        var9.addVertexWithUV(xMin, y + 0, zMax, minX, minY);
        var9.addVertexWithUV(xMin, y + 0.0D, zMax, minX, maxY);
        var9.addVertexWithUV(xMax, y + 0.0D, zMin, maxX, maxY);
        var9.addVertexWithUV(xMax, y + 0, zMin, maxX, minY);
        
        var9.addVertexWithUV(xMax, y + 0, zMin, minX, minY);
        var9.addVertexWithUV(xMax, y + 0.0D, zMin, minX, maxY);
        var9.addVertexWithUV(xMin, y + 0.0D, zMax, maxX, maxY);
        var9.addVertexWithUV(xMin, y + 0, zMax, maxX, minY);
    }
}
