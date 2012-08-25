package net.minecraft.src;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import bioxx.importers.WavefrontObject;

import TFC.Core.ColorizerFoliageTFC;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.IProxy;
import TFC.Core.PacketHandler;
import TFC.Core.PlayerManagerTFC;
import TFC.Core.RenderHighlight;
import TFC.Core.SoundHandler;
import TFC.Core.TFCHeat;
import TFC.Core.TFCSeasons;
import TFC.Core.TFCSettings;
import TFC.Entities.EntityBear;
import TFC.Entities.EntityChickenTFC;
import TFC.Entities.EntityCowTFC;
import TFC.Entities.EntityDeer;
import TFC.Entities.EntityFallingDirt;
import TFC.Entities.EntityFallingStone;
import TFC.Entities.EntityFallingStone2;
import TFC.Entities.EntityPigTFC;
import TFC.Entities.EntitySheepTFC;
import TFC.Entities.EntitySquidTFC;
import TFC.Entities.EntityTerraJavelin;
import TFC.Entities.EntityWolfTFC;
import TFC.GUI.GuiCalendar;
import TFC.GUI.GuiChestTFC;
import TFC.GUI.GuiKnapping;
import TFC.GUI.GuiTerraAnvil;
import TFC.GUI.GuiTerraBloomery;
import TFC.GUI.GuiTerraFirepit;
import TFC.GUI.GuiTerraForge;
import TFC.GUI.GuiTerraLogPile;
import TFC.GUI.GuiTerraMetallurgy;
import TFC.GUI.GuiTerraScribe;
import TFC.GUI.GuiTerraSluice;
import TFC.GUI.GuiTerraWorkbench;
import TFC.Items.ItemChisel;
import TFC.Items.ItemTerra;
import TFC.Items.ItemTerraArmor;
import TFC.Render.*;
import TFC.TileEntities.*;
import TFC.WorldGen.BiomeGenJungleTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraft.src.forge.*;

public class ClientProxy implements IProxy 
{
    public KeyBinding Key_Calendar = new KeyBinding("Key_Calendar", 49);
    public KeyBinding Key_ToolMode = new KeyBinding("Key_ToolMode", 50);

    public void registerRenderInformation() 
    {
        MinecraftForgeClient.preloadTexture("/bioxx/terraRock.png");
        MinecraftForgeClient.preloadTexture("/bioxx/terratools.png");
        MinecraftForgeClient.preloadTexture("/bioxx/terratoolheads.png");
        MinecraftForgeClient.preloadTexture("/bioxx/terrasprites.png");
        MinecraftForgeClient.preloadTexture("/bioxx/terrasprites2.png");
        MinecraftForgeClient.preloadTexture("/bioxx/terrablocks.png");
        MinecraftForgeClient.preloadTexture("/bioxx/terrablocks2.png");
        MinecraftForgeClient.preloadTexture("/bioxx/terrabedsSprites.png");
        MinecraftForgeClient.preloadTexture("/bioxx/terrabeds.png");
        MinecraftForgeClient.preloadTexture("/bioxx/sluicegui.png");
        MinecraftForgeClient.preloadTexture("/bioxx/scribegui.png");
        MinecraftForgeClient.preloadTexture("/bioxx/metallurgygui.png");
        MinecraftForgeClient.preloadTexture("/bioxx/logpilegui.png");
        MinecraftForgeClient.preloadTexture("/bioxx/javelin.png");
        MinecraftForgeClient.preloadTexture("/bioxx/Forgegui.png");
        MinecraftForgeClient.preloadTexture("/bioxx/Firepitgui.png");
        MinecraftForgeClient.preloadTexture("/bioxx/Bloomerygui.png");
        MinecraftForgeClient.preloadTexture("/bioxx/anvilgui.png");
        MinecraftForgeClient.preloadTexture("/bioxx/calendargui.png");
        MinecraftForgeClient.preloadTexture("/bioxx/Vegetation.png");
        MinecraftForgeClient.preloadTexture("/bioxx/TFC_Plants.png");
        
        ColorizerFoliageTFC.getFoilageBiomeColorizer(ModLoader.getMinecraftInstance().renderEngine.getTextureContents("/misc/foliagecolor.png"));
        ColorizerGrassTFC.setGrassBiomeColorizer(ModLoader.getMinecraftInstance().renderEngine.getTextureContents("/misc/grasscolor.png"));
        
        try{
//        TFC_CoreRender.TomatoPlant = new WavefrontObject[]{
//                new WavefrontObject().parse("/models/tomato_00.obj"),
//                new WavefrontObject().parse("/models/tomato_01.obj"),
//                new WavefrontObject().parse("/models/tomato_02.obj"),
//                new WavefrontObject().parse("/models/tomato_03.obj"),
//                new WavefrontObject().parse("/models/box.obj"),
//                new WavefrontObject().parse("/models/tomato_05.obj"),
//                new WavefrontObject().parse("/models/tomato_06.obj"),
//                new WavefrontObject().parse("/models/tomato_07.obj"),
//                new WavefrontObject().parse("/models/tomato_08.obj"),
//                new WavefrontObject().parse("/models/tomato_09.obj")};
        }
        catch(Exception ex){}
    }

    @Override
    public void registerTileEntities()
    {
        ModLoader.registerTileEntity(TileEntityTerraLogPile.class, "TerraLogPile");
        ModLoader.registerTileEntity(TileEntityTerraWorkbench.class, "TerraWorkbench");
        ModLoader.registerTileEntity(TileEntityTerraFirepit.class, "TerraFirepit");
        ModLoader.registerTileEntity(TileEntityTerraAnvil.class, "TerraAnvil");
        ModLoader.registerTileEntity(TileEntityTerraScribe.class, "TerraScribe");
        ModLoader.registerTileEntity(TileEntityTerraWorkbench.class, "TerraWorkbench");
        ModLoader.registerTileEntity(TileEntityTerraForge.class, "TerraForge");
        ModLoader.registerTileEntity(TileEntityTerraMetallurgy.class, "TerraMetallurgy");
        ModLoader.registerTileEntity(TileEntityTerraBloomery.class, "TerraBloomery");
        ModLoader.registerTileEntity(TileEntityTerraSluice.class, "TerraSluice");
        ModLoader.registerTileEntity(TileEntityFarmland.class, "TileEntityFarmland");
        ModLoader.registerTileEntity(TileEntityCrop.class, "TileEntityCrop");

        ModLoader.registerTileEntity(TileEntityFruitTreeWood.class, "FruitTreeWood");
        ModLoader.registerTileEntity(TileEntityPartial.class, "Partial");
        ModLoader.registerTileEntity(TileEntityChestTFC.class, "chest", new TileEntityChestRendererTFC());
        
        MinecraftForgeClient.registerSoundHandler(new SoundHandler());
        MinecraftForgeClient.registerHighlightHandler(new RenderHighlight());
        
        ModLoader.registerEntityID(EntityCowTFC.class, "cow", 0, 0xffffff, 0xbbbbbb);
        ModLoader.registerEntityID(EntitySheepTFC.class, "sheep", 1, 0xffffff, 0xbbbbbb);
        ModLoader.registerEntityID(EntityBear.class, "bear", 2, 0xd1d003, 0x101010);
        ModLoader.registerEntityID(EntityChickenTFC.class, "chicken", 3, 0xffffff, 0xbbbbbb);
        ModLoader.registerEntityID(EntityPigTFC.class, "pig", 4, 0xffffff, 0xbbbbbb);
        ModLoader.registerEntityID(EntitySquidTFC.class, "squid", 5, 0xffffff, 0xbbbbbb);
        ModLoader.registerEntityID(EntityDeer.class, "deer", 6, 0xffffff, 0x105510);
    }

    

    @Override
    public File getMinecraftDir() {
        return Minecraft.getMinecraftDir();
    }

    @Override
    public void applyExtraDataToDrops(EntityItem entityitem, NBTTagCompound data) {
        entityitem.item.setTagCompound(data);

    }

    @Override
    public boolean isRemote() {
        return ModLoader.getMinecraftInstance().theWorld.isRemote;
    }

    @Override
    public World getCurrentWorld() {
        return ModLoader.getMinecraftInstance().theWorld;
    }

    @Override
    public Object getGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) 
    {
        TileEntity te;
        try
        {
            te= world.getBlockTileEntity(x, y, z);
        }
        catch(Exception e)
        {
            te = null;
        }

        switch(ID)
        {
            case 0:
            {
                return new GuiTerraLogPile(player.inventory, (TileEntityTerraLogPile) te, world, x, y, z);
            }
            case 1:
            {
                return new GuiTerraWorkbench(player.inventory, (TileEntityTerraWorkbench) te, world, x, y, z);
            }
            case 20:
            {
                return new GuiTerraFirepit(player.inventory, (TileEntityTerraFirepit) te);
            }
            case 21:
            {
                return new GuiTerraAnvil(player.inventory, (TileEntityTerraAnvil) te);
            }
            case 22:
            {
                return new GuiTerraScribe(player.inventory, (TileEntityTerraScribe) te, world);
            }
            case 23:
            {
                return new GuiTerraForge(player.inventory, (TileEntityTerraForge) te);
            }
            case 24:
            {
                return new GuiTerraMetallurgy(player.inventory, (TileEntityTerraMetallurgy) te, world);
            }
            case 25:
            {
                return new GuiTerraSluice(player.inventory, (TileEntityTerraSluice) te);
            }
            case 26:
            {
                return new GuiTerraBloomery(player.inventory, (TileEntityTerraBloomery) te);
            }
            case 27:
            {
                return new GuiCalendar(player, world, x, y, z);
            }
            case 28:
            {
                return new GuiKnapping(player.inventory, PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player).knappingRockType ,world);
            }
            case 29:
            {
                return new GuiChestTFC(player.inventory, ((TileEntityChestTFC) te));
            }

        }

        return null;

    }

    @Override
    public String getDisplayName(ItemStack is) 
    {
        return is.getItem().getItemDisplayName(is);
    }

    @Override
    public void addRenderer(Map map) 
    {
        map.put(EntityFallingDirt.class, new RenderFallingDirt());
        map.put(EntityFallingStone.class, new RenderFallingStone());
        map.put(EntityFallingStone2.class, new RenderFallingStone2());
        map.put(EntityTerraJavelin.class, new RenderTerraJavelin());
        map.put(EntitySquidTFC.class, new RenderSquid(new ModelSquid(), 0.7F));
        map.put(EntityCowTFC.class, new RenderCowTFC(new ModelCowTFC(), 0.7F));
        map.put(EntitySheepTFC.class, new RenderSheepTFC(new ModelSheep2TFC(),new ModelSheep1TFC(), 0.7F));
        map.put(EntityWolfTFC.class, new RenderWolfTFC(new ModelWolfTFC(), 0.5F));
        map.put(EntityBear.class, new RenderBear(new ModelBear(), 0.9F));
        map.put(EntityChickenTFC.class, new RenderChickenTFC(new ModelChickenTFC(), 0.3F));
        map.put(EntityPigTFC.class, new RenderPigTFC(new ModelPigTFC(), new ModelPigTFC(0.5F), 0.7F));
        map.put(EntityDeer.class, new RenderDeer(new ModelDeer(), 0.9F));
    }

    @Override
    public boolean renderWorldBlock(Object renderblocks,
            IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) 
    {
        if (l == mod_TFC.sulfurRenderId)
        {
            return TFC_CoreRender.RenderSulfur(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.moltenRenderId)
        {
            return TFC_CoreRender.RenderMolten(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.woodSupportRenderIdH)
        {
            return TFC_CoreRender.RenderWoodSupportBeamH(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.woodSupportRenderIdV)
        {
            return TFC_CoreRender.RenderWoodSupportBeamV(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.grassRenderId)
        {
            int var5 = block.colorMultiplier(iblockaccess, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return TFC_CoreRender.RenderGrass(block, i, j, k, var6, var7, var8, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.oreRenderId)
        {
            int var5 = block.colorMultiplier(iblockaccess, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return TFC_CoreRender.RenderOre(block, i, j, k,  var6, var7, var8, (RenderBlocks)renderblocks, iblockaccess);
        }
        else if (l == mod_TFC.looseRockRenderId)
        {
            return TFC_CoreRender.RenderLooseRock(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.snowRenderId)
        {
            return TFC_CoreRender.RenderSnow(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.terraFirepitRenderId)
        {
            return TFC_CoreRender.renderFirepit(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.terraForgeRenderId)
        {
            return TFC_CoreRender.renderForge(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.terraBellowsRenderId)
        {
            return TFC_CoreRender.renderBellows(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.terraAnvilRenderId)
        {
            return TFC_CoreRender.renderAnvil(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.sluiceRenderId)
        {
            return TFC_CoreRender.RenderSluice(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.woodFruitRenderId)
        {
            return TFC_CoreRender.RenderWoodTrunk(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.leavesFruitRenderId)
        {
            int var5 = block.colorMultiplier(iblockaccess, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return TFC_CoreRender.RenderFruitLeaves(block, i, j, k, var6, var7, var8, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.toolRackRenderId)
        {
            int var5 = block.colorMultiplier(iblockaccess, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return TFC_CoreRender.RenderFruitLeaves(block, i, j, k, var6, var7, var8, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.finiteWaterRenderId)
        {
            return TFC_CoreRender.RenderFiniteWater(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.stairRenderId)
        {
            return TFC_CoreRender.renderBlockStairs(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.slabRenderId)
        {
            return TFC_CoreRender.renderBlockSlab(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.cropRenderId)
        {
            return TFC_CoreRender.RenderCrop(block, i, j, k, (RenderBlocks)renderblocks);
        }
        else if (l == mod_TFC.leavesRenderId)
        {
            int var5 = block.colorMultiplier(iblockaccess, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return TFC_CoreRender.RenderNewLeaves(block, i, j, k, var6, var7, var8, (RenderBlocks)renderblocks, getGraphicsLevel(), true);
        }


        return false;
    }

    public int waterColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;

        for (int var8 = -1; var8 <= 1; ++var8)
        {
            for (int var9 = -1; var9 <= 1; ++var9)
            {
                int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).waterColorMultiplier;
                var5 += (var10 & 16711680) >> 16;
            var6 += (var10 & 65280) >> 8;
            var7 += var10 & 255;
            }
        }
        return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
    }

    public int grassColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;

        for (int var8 = -1; var8 <= 1; ++var8)
        {
            for (int var9 = -1; var9 <= 1; ++var9)
            {
                int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).getBiomeGrassColor();
                var5 += (var10 & 16711680) >> 16;
            var6 += (var10 & 65280) >> 8;
        var7 += var10 & 255;
            }
        }
        return (var5 / 9 & 255) << 16 | (var6 / 9 & 255) << 8 | var7 / 9 & 255;
    }

    public int foliageColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
        int var5 = 0;
        int var6 = 0;
        int var7 = 0;

        int[] rgb = {0,0,0};

        int meta = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        if(par1IBlockAccess.getBlockId(par2, par3, par4) == mod_TFC.fruitTreeLeaves.blockID)
        {
            if(TFCSeasons.currentMonth >= 6 && TFCSeasons.currentMonth < 9)
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        BiomeGenBase biome  = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8);

                        int var10 = ColorizerFoliageTFC.getFoliageYellow();
                        rgb = applyColor(var10, rgb);
                    }
                }

                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
            else
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).getBiomeFoliageColor();
                        rgb = applyColor(var10, rgb);
                    }
                }
                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
        }
        else if(par1IBlockAccess.getBlockId(par2, par3, par4) == Block.vine.blockID)
        {
            if(TFCSeasons.currentMonth >= 6 && TFCSeasons.currentMonth < 9 && !(par1IBlockAccess.getBiomeGenForCoords(par2, par4) instanceof BiomeGenJungleTFC))
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        int var10 = ColorizerFoliageTFC.getFoliageDead();
                        rgb = applyColor(var10, rgb);
                    }
                }

                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
            else if(TFCSeasons.currentMonth >= 6 && !(par1IBlockAccess.getBiomeGenForCoords(par2, par4) instanceof BiomeGenJungleTFC))
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        int var10 = ColorizerFoliageTFC.getFoliageDead();
                        rgb = applyColor(var10, rgb);
                    }
                }

                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
            else
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).getBiomeFoliageColor();
                        rgb = applyColor(var10, rgb);
                    }
                }
                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
        }
        else
        {
            if(TFCSeasons.currentMonth >= 6 && (meta == 9 || meta == 8 || meta == 12))
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        BiomeGenBase biome  = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8);

                        double var1 = (double)MathHelper.clamp_float(biome.getFloatTemperature(), 0.0F, 1.0F);
                        double var3 = (double)MathHelper.clamp_float(biome.getFloatRainfall(), 0.0F, 1.0F);
                        int var10 = ColorizerFoliageTFC.getFoliageColor(var1, var3);
                        rgb = applyColor(var10, rgb);
                    }
                }

                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
            else if(TFCSeasons.currentMonth >= 6 && TFCSeasons.currentMonth < 9 && (meta == 4 || meta == 7 || meta == 5 || meta == 14))
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        int var10 = ColorizerFoliageTFC.getFoliageYellow();
                        rgb = applyColor(var10, rgb);
                    }
                }

                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
            else if(TFCSeasons.currentMonth >= 6 && TFCSeasons.currentMonth < 9 && (meta == 6))
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        int var10 = ColorizerFoliageTFC.getFoliageRed();
                        rgb = applyColor(var10, rgb);
                    }
                }
                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
            else if(TFCSeasons.currentMonth >= 6  && TFCSeasons.currentMonth < 9 && !(meta == 15))
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        int var10 = ColorizerFoliageTFC.getFoliageOrange();
                        rgb = applyColor(var10, rgb);
                    }
                }

                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
            else if(TFCSeasons.currentMonth >= 6 && !(meta == 15))
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        int var10 = ColorizerFoliageTFC.getFoliageDead();
                        rgb = applyColor(var10, rgb);
                    }
                }

                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
            else
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        int var10 = par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8).getBiomeFoliageColor();
                        rgb = applyColor(var10, rgb);
                    }
                }
                int x = (rgb[0] / 9 & 255) << 16 | (rgb[1] / 9 & 255) << 8 | rgb[2] / 9 & 255;
                return x;
            }
        }
    }

    private int[] applyColor(int c, int[] rgb)
    {        
        rgb[0] += ((c & 16711680) >> 16);
        rgb[1] += ((c & 65280) >> 8);
        rgb[2] += (c & 255);

        return rgb;
    }


    @Override
    public int blockGetMixedBrightnessForBlock(Block B,IBlockAccess par1iBlockAccess,
            int par2, int par3, int par4) {
        return B.getMixedBrightnessForBlock(par1iBlockAccess, par2, par3, par4);
    }

    @Override
    public int blockGetRenderBlockPass(Block B) {
        return B.getRenderBlockPass();
    }

    @Override
    public AxisAlignedBB blockGetSelectedBoundingBoxFromPool(Block B,World par1World,
            int par2, int par3, int par4) 
    {
        return B.getSelectedBoundingBoxFromPool(par1World, par2, par3, par4);
    }

    @Override
    public boolean areItemStacksEqual(ItemStack is1, ItemStack is2) {
        return ItemStack.func_46154_a(is1, is2);
    }

    @Override
    public int getUniqueBlockModelID(BaseMod var0, boolean var1) {
        return ModLoader.getUniqueBlockModelID(var0, var1);
    }

    @Override
    public void takenFromCrafting(EntityPlayer entityplayer,
            ItemStack itemstack, IInventory iinventory) 
    {
        ModLoader.takenFromCrafting(entityplayer, itemstack, iinventory);
    }

    @Override
    public void sendCustomPacket(Packet packet)
    {
        ModLoader.sendPacket(packet);     
    }

    @Override
    public EntityPlayer getPlayer(NetworkManager network)
    {
        return ModLoader.getMinecraftInstance().thePlayer;
    }

    @Override
    public int getArmorRenderID(int i)
    {
        String[] Names = {"bismuth", "bismuthbronze", "blackbronze", "blacksteel", "bluesteel", "bronze", "copper", "wroughtiron", "redsteel", "rosegold", "steel", "tin", "zinc"};
        return ModLoader.addArmor(Names[i]);
    }

    @Override
    public boolean getGraphicsLevel()
    {
        // TODO Auto-generated method stub
        return Minecraft.isFancyGraphicsEnabled();
    }

    @Override
    public void registerKeys(BaseMod b)
    {
        ModLoader.registerKey(b, Key_Calendar, false);
        ModLoader.registerKey(b, Key_ToolMode, false);
    }

    private long keyTime = 0;
    public void keyboardEvent(Object e)
    {
        KeyBinding bind = (KeyBinding)e;
        Minecraft minecraft = ModLoader.getMinecraftInstance();
        if(keyTime+1 < TFCSeasons.getTotalTicks() && minecraft.theWorld != null)
        {
            keyTime = TFCSeasons.getTotalTicks();
            if (bind == this.Key_Calendar && minecraft.inGameHasFocus)
            {
                minecraft.thePlayer.openGui(mod_TFC.instance, 27, minecraft.theWorld, 0, 0, 0);
            }
            else if (bind == this.Key_Calendar && minecraft.currentScreen instanceof GuiCalendar)
            {
                minecraft.thePlayer.closeScreen();
            }
            else if (bind == this.Key_ToolMode && minecraft.thePlayer.getCurrentEquippedItem() != null 
                    && minecraft.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemChisel && minecraft.inGameHasFocus)
            {
                ItemChisel.mode = ItemChisel.mode == 0 ? 1 : ItemChisel.mode == 1 ? 2 : 0;
                String type = ItemChisel.mode == 0 ? "Smoothing" : ItemChisel.mode == 1 ? "Creating Stairs" : "Creating Slabs";
                minecraft.ingameGUI.addChatMessage(type);

                if(minecraft.theWorld.isRemote)
                {
                    PacketHandler.sendKeyPress(0);
                }
            }
        }
    }

    @Override
    public int getLightBrightnessSkyBlocks(IBlockAccess par1iBlockAccess, int par2, int par3, int par4, int par5)
    {
        return par1iBlockAccess.getLightBrightnessForSkyBlocks(par2, par3, par4, 0);
    }

    @Override
    public float getLightBrightness(IBlockAccess par1iBlockAccess, int par2,
            int par3, int par4)
    {
        return par1iBlockAccess.getLightBrightness(par2, par3, par4);
    }

    @Override
    public void sendCustomPacketToPlayer(String player, Packet packet)
    {

    }
    
    @Override
    public void registerTranslations() 
    {
        ModLoader.addLocalization("Key_Calendar", "Open Calendar");

        

        //Gems
        String[] GemNames = {"Ruby","Emerald","Topaz","Sapphire","Opal","Agate",
                "Jade","Garnet","Amethyst","Beryl","Jasper","Tourmaline","Diamond"};

        for(int i= 0; i < GemNames.length; i++)
        {
            ModLoader.addLocalization("item."+GemNames[i]+".Chipped.name", "Chipped "+GemNames[i]);
            ModLoader.addLocalization("item."+GemNames[i]+".Flawed.name", "Flawed "+GemNames[i]);
            ModLoader.addLocalization("item."+GemNames[i]+".Normal.name",  GemNames[i]);
            ModLoader.addLocalization("item."+GemNames[i]+".Flawless.name", "Flawless "+GemNames[i]);
            ModLoader.addLocalization("item."+GemNames[i]+".Exquisite.name", "Exquisite "+GemNames[i]);
        }

        ModLoader.addLocalization("item.javelin.name", "Javelin");

        ModLoader.addLocalization("item.WoodenBucketEmpty.name", "Wooden Bucket (Empty)");
        ModLoader.addLocalization("item.WoodenBucketWater.name", "Wooden Bucket (Water)");
        ModLoader.addLocalization("item.WoodenBucketMilk.name", "Wooden Bucket (Milk)");

        ModLoader.addLocalization("item.SluiceItem.name", "Sluice");

        ModLoader.addLocalization("item.GoldPan.GoldPan.name", "Gold Pan - Empty");
        ModLoader.addLocalization("item.GoldPan.GoldPanSand.name", "Gold Pan - Sand");
        ModLoader.addLocalization("item.GoldPan.GoldPanGravel.name", "Gold Pan - Gravel");
        ModLoader.addLocalization("item.GoldPan.GoldPanClay.name", "Gold Pan - Clay");
        ModLoader.addLocalization("item.GoldPan.GoldPanDirt.name", "Gold Pan - Dirt");
    
        ModLoader.addLocalization("item.terraFireStarter.name", "Firestarter");
        ModLoader.addLocalization("item.terraSlag.name", "Slag");


        ModLoader.addLocalization("item.terraBellowsItem.name", "Bellows");
        ModLoader.addLocalization("tile.terraBellows.name", "Bellows");
        ModLoader.addLocalization("tile.terraScribe.name", "Scribing Table");
        ModLoader.addLocalization("item.terraInk.name", "Marking");
        ModLoader.addLocalization("item.terraClayMold.name", "Clay Mold");
        ModLoader.addLocalization("item.terraFiredClayMold.name", "Ceramic Mold");
        ModLoader.addLocalization("tile.terraForge.name", "Forge");
        ModLoader.addLocalization("tile.terraBloomery.name", "Bloomery");
        ModLoader.addLocalization("tile.terraMetallurgy.name", "Metallurgy Table");

        ModLoader.addLocalization("item.PickaxeHeadPlan.name", "Plan: Pickaxe Head");
        ModLoader.addLocalization("item.ShovelHeadPlan.name", "Plan: Shovel Head");
        ModLoader.addLocalization("item.HoeHeadPlan.name", "Plan: Hoe Head");
        ModLoader.addLocalization("item.AxeHeadPlan.name", "Plan: Axe Head");
        ModLoader.addLocalization("item.HammerHeadPlan.name", "Plan: Hammer Head");
        ModLoader.addLocalization("item.ChiselHeadPlan.name", "Plan: Chisel Head");
        ModLoader.addLocalization("item.SwordBladePlan.name", "Plan: Sword Blade");
        ModLoader.addLocalization("item.MaceHeadPlan.name", "Plan: Mace Head");
        ModLoader.addLocalization("item.SawBladePlan.name", "Plan: Saw Head");
        ModLoader.addLocalization("item.ProPickHeadPlan.name", "Plan: Prospecter's Pick Head");
        ModLoader.addLocalization("item.HelmetPlan.name", "Plan: Plate Helmet");
        ModLoader.addLocalization("item.ChestplatePlan.name", "Plan: Chestplate");
        ModLoader.addLocalization("item.GreavesPlan.name", "Plan: Plate Greaves");
        ModLoader.addLocalization("item.BootsPlan.name", "Plan: Plate Boots");
        ModLoader.addLocalization("item.ScythePlan.name", "Plan: Scythe Blade");
        ModLoader.addLocalization("item.KnifePlan.name", "Plan: Knife Blade");

        ModLoader.addLocalization("item.coke.name", "Coke");
        ModLoader.addLocalization("item.flux.name", "Flux");
        
        RegisterTerrain();
        RegisterMetal();
        RegisterFood();
        RegisterWood();
    }
    
    private void RegisterFood()
    {
        String[] FruitTreeNames = {"Red Apple","Banana", "Orange", "Green Apple","Lemon","Olive","Cherry","Peach","Plum","Cacao"};
        for(int i= 0; i < FruitTreeNames.length; i++)
        {
            ModLoader.addLocalization("item.FruitSapling1."+FruitTreeNames[i]+".name", FruitTreeNames[i] + " Tree Sapling");
            ModLoader.addLocalization("item.FruitSapling2."+FruitTreeNames[i]+".name", FruitTreeNames[i] + " Tree Sapling");
            ModLoader.addLocalization("item.Fruit."+FruitTreeNames[i]+".name", FruitTreeNames[i]);
        }
        
        ModLoader.addLocalization("item.SeedsWheat.name", "Wheat Seeds");
        ModLoader.addLocalization("item.SeedsWildWheat.name", "Wild Wheat Seeds");
        ModLoader.addLocalization("item.SeedsMaize.name", "Maize Seeds");
        ModLoader.addLocalization("item.SeedsWildMaize.name", "Wild Maize Seeds");
        ModLoader.addLocalization("item.SeedsTomato.name", "Tomato Seeds");
        ModLoader.addLocalization("item.SeedsBarley.name", "Barley Seeds");
        ModLoader.addLocalization("item.SeedsWildBarley.name", "Wild Barley Seeds");
        ModLoader.addLocalization("item.SeedsRye.name", "Rye Seeds");
        ModLoader.addLocalization("item.SeedsWildRye.name", "Wild Rye Seeds");
        ModLoader.addLocalization("item.SeedsOat.name", "Oat Seeds");
        ModLoader.addLocalization("item.SeedsWildOat.name", "Wild Oat Seeds");
        ModLoader.addLocalization("item.SeedsRice.name", "Rice Seeds");
        ModLoader.addLocalization("item.SeedsWildRice.name", "Wild Rice Seeds");
        ModLoader.addLocalization("item.SeedsPotato.name", "Potato Seeds");
        ModLoader.addLocalization("item.SeedsWildPotato.name", "Wild Potato Seeds");
        ModLoader.addLocalization("item.SeedsOnion.name", "Onion Seeds");
        ModLoader.addLocalization("item.SeedsCabbage.name", "Cabbage Seeds");
        ModLoader.addLocalization("item.SeedsGarlic.name", "Garlic Seeds");
        ModLoader.addLocalization("item.SeedsCarrot.name", "Carrot Seeds");
        ModLoader.addLocalization("item.SeedsSugarcane.name", "Sugarcane Seeds");
        ModLoader.addLocalization("item.SeedsHemp.name", "Hemp Seeds");
        ModLoader.addLocalization("item.SeedsSoybean.name", "Soybean Seeds");
        ModLoader.addLocalization("item.SeedsGreenbean.name", "Greenbean Seeds");
        ModLoader.addLocalization("item.SeedsYam.name", "Yam Seeds");
        ModLoader.addLocalization("item.SeedsBellPepper.name", "Bell Pepper Seeds");
        ModLoader.addLocalization("item.SeedsSquash.name", "Squash Seeds");
        
        
        ModLoader.addLocalization("item.Meat.EggCooked.name", "Cooked Egg");
        
        ModLoader.addLocalization("item.WheatGrain.name", "Wheat Grain");
        ModLoader.addLocalization("item.WildWheatGrain.name", "Wild Wheat Grain");
        ModLoader.addLocalization("item.WheatWhole.name", "Wheat");
        ModLoader.addLocalization("item.BarleyGrain.name", "Barley Grain");
        ModLoader.addLocalization("item.WildBarleyGrain.name", "Wild Barley Grain");
        ModLoader.addLocalization("item.BarleyWhole.name", "Barley");
        ModLoader.addLocalization("item.OatGrain.name", "Oat Grain");
        ModLoader.addLocalization("item.WildOatGrain.name", "Wild Oat Grain");
        ModLoader.addLocalization("item.OatWhole.name", "Oat");
        ModLoader.addLocalization("item.RyeGrain.name", "Rye Grain");
        ModLoader.addLocalization("item.WildRyeGrain.name", "Wild Rye Grain");
        ModLoader.addLocalization("item.RyeWhole.name", "Rye");
        ModLoader.addLocalization("item.RiceGrain.name", "Rice Grain");
        ModLoader.addLocalization("item.WildRiceGrain.name", "Wild Rice Grain");
        ModLoader.addLocalization("item.RiceWhole.name", "Rice");
        
        ModLoader.addLocalization("item.MaizeEar.name", "Maize Ear");
        ModLoader.addLocalization("item.WildMaizeEar.name", "Wild Maize Ear");
        ModLoader.addLocalization("item.Tomato.name", "Tomato");
        ModLoader.addLocalization("item.Potato.name", "Potato");
        ModLoader.addLocalization("item.WildPotato.name", "Wild Potato");
        
        ModLoader.addLocalization("item.Onion.name", "Onion");
        ModLoader.addLocalization("item.Cabbage.name", "Cabbage");
        ModLoader.addLocalization("item.Garlic.name", "Garlic");
        ModLoader.addLocalization("item.Carrot.name", "Carrot");
        ModLoader.addLocalization("item.Sugarcane.name", "Sugarcane");
        ModLoader.addLocalization("item.Hemp.name", "Hemp");
        ModLoader.addLocalization("item.Soybeans.name", "Soybeans");
        ModLoader.addLocalization("item.Greenbeans.name", "Greenbeans");
        ModLoader.addLocalization("item.Yam.name", "Yam");
        ModLoader.addLocalization("item.GreenBellPepper.name", "Green Bell Pepper");
        ModLoader.addLocalization("item.YellowBellPepper.name", "Yellow Bell Pepper");
        ModLoader.addLocalization("item.RedBellPepper.name", "Red Bell Pepper");
        ModLoader.addLocalization("item.Squash.name", "Squash");
        
    }
    
    private void RegisterTerrain()
    {
        String[] rockNames = {"Granite", "Diorite", "Gabbro", 
                "Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", 
                "Chalk", "Rhyolite", "Basalt", "Andesite", "Dacite", 
                "Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};
        
        for(int i= 0; i < rockNames.length; i++)
        {
            ModLoader.addLocalization("item.LooseRock."+rockNames[i]+".name", rockNames[i] + " Rock");
            ModLoader.addLocalization("item.FlatRock."+rockNames[i]+".name", rockNames[i] + " Rock");
        }
        ModLoader.addLocalization("tile.IgInRock.Granite.name", "Granite");
        ModLoader.addLocalization("tile.IgInRock.Diorite.name", "Diorite");
        ModLoader.addLocalization("tile.IgInRock.Gabbro.name", "Gabbro");
        ModLoader.addLocalization("tile.IgExRock.Rhyolite.name", "Rhyolite");
        ModLoader.addLocalization("tile.IgExRock.Basalt.name", "Basalt");
        ModLoader.addLocalization("tile.IgExRock.Andesite.name", "Andesite");
        ModLoader.addLocalization("tile.IgExRock.Dacite.name", "Dacite");
        ModLoader.addLocalization("tile.SedRock.Siltstone.name", "Siltstone");
        ModLoader.addLocalization("tile.SedRock.Mudstone.name", "Mudstone");
        ModLoader.addLocalization("tile.SedRock.Shale.name", "Shale");
        ModLoader.addLocalization("tile.SedRock.Claystone.name", "Claystone");
        ModLoader.addLocalization("tile.SedRock.Rock Salt.name", "Rock Salt");
        ModLoader.addLocalization("tile.SedRock.Limestone.name", "Limestone");
        ModLoader.addLocalization("tile.SedRock.Conglomerate.name", "Conglomerate");
        ModLoader.addLocalization("tile.SedRock.Dolomite.name", "Dolomite");
        ModLoader.addLocalization("tile.SedRock.Chert.name", "Chert");
        ModLoader.addLocalization("tile.SedRock.Chalk.name", "Chalk");
        ModLoader.addLocalization("tile.MMRock.Quartzite.name", "Quartzite");
        ModLoader.addLocalization("tile.MMRock.Slate.name", "Slate");
        ModLoader.addLocalization("tile.MMRock.Phyllite.name", "Phyllite");
        ModLoader.addLocalization("tile.MMRock.Schist.name", "Schist");
        ModLoader.addLocalization("tile.MMRock.Gneiss.name", "Gneiss");
        ModLoader.addLocalization("tile.MMRock.Marble.name", "Marble");
        ModLoader.addLocalization("tile.Ore.Native Copper.name", "Native Copper");
        ModLoader.addLocalization("tile.Ore.Native Gold.name", "Native Gold");
        ModLoader.addLocalization("tile.Ore.Native Platinum.name", "Native Platinum");
        ModLoader.addLocalization("tile.Ore.Hematite.name", "Hematite");
        ModLoader.addLocalization("tile.Ore.Native Silver.name", "Native Silver");
        ModLoader.addLocalization("tile.Ore.Cassiterite.name", "Cassiterite");
        ModLoader.addLocalization("tile.Ore.Galena.name", "Galena");
        ModLoader.addLocalization("tile.Ore.Bismuthinite.name", "Bismuthinite");
        ModLoader.addLocalization("tile.Ore.Garnierite.name", "Garnierite");
        ModLoader.addLocalization("tile.Ore.Malachite.name", "Malachite");
        ModLoader.addLocalization("tile.Ore.Magnetite.name", "Magnetite");
        ModLoader.addLocalization("tile.Ore.Limonite.name", "Limonite");
        ModLoader.addLocalization("tile.Ore.Sphalerite.name", "Sphalerite");
        ModLoader.addLocalization("tile.Ore.Tetrahedrite.name", "Tetrahedrite");
        ModLoader.addLocalization("tile.Ore.Bituminous Coal.name", "Bituminous Coal");
        ModLoader.addLocalization("tile.Ore.Lignite.name", "Lignite");
        ModLoader.addLocalization("tile.Ore.Kaolinite.name", "Kaolinite");
        ModLoader.addLocalization("tile.Ore.Gypsum.name", "Gypsum");
        ModLoader.addLocalization("tile.Ore.Satinspar.name", "Satinspar");
        ModLoader.addLocalization("tile.Ore.Selenite.name", "Selenite");
        ModLoader.addLocalization("tile.Ore.Graphite.name", "Graphite");
        ModLoader.addLocalization("tile.Ore.Kimberlite.name", "Kimberlite");
        ModLoader.addLocalization("tile.Ore.Petrified Wood.name", "Petrified Wood");
        ModLoader.addLocalization("tile.Ore.Sulfur.name", "Sulfur");
        ModLoader.addLocalization("tile.Ore.Jet.name", "Jet");
        ModLoader.addLocalization("tile.Ore.Microcline.name", "Microcline");
        ModLoader.addLocalization("tile.Ore.Pitchblende.name", "Pitchblende");
        ModLoader.addLocalization("tile.Ore.Cinnabar.name", "Cinnabar");
        ModLoader.addLocalization("tile.Ore.Cryolite.name", "Cryolite");
        ModLoader.addLocalization("tile.Ore.Saltpeter.name", "Saltpeter");
        ModLoader.addLocalization("tile.Ore.Serpentine.name", "Serpentine");
        ModLoader.addLocalization("tile.Ore.Sylvite.name", "Sylvite");
        ModLoader.addLocalization("tile.Ore.Borax.name", "Borax");
        ModLoader.addLocalization("tile.Ore.Olivine.name", "Olivine");
        ModLoader.addLocalization("tile.Ore.LapisLazuli.name", "Lapis Lazuli");

        ModLoader.addLocalization("item.Ore.Native Copper.name", "Native Copper");
        ModLoader.addLocalization("item.Ore.Native Gold.name", "Native Gold");
        ModLoader.addLocalization("item.Ore.Native Platinum.name", "Native Platinum");
        ModLoader.addLocalization("item.Ore.Hematite.name", "Hematite");
        ModLoader.addLocalization("item.Ore.Native Silver.name", "Native Silver");
        ModLoader.addLocalization("item.Ore.Cassiterite.name", "Cassiterite");
        ModLoader.addLocalization("item.Ore.Galena.name", "Galena");
        ModLoader.addLocalization("item.Ore.Bismuthinite.name", "Bismuthinite");
        ModLoader.addLocalization("item.Ore.Garnierite.name", "Garnierite");
        ModLoader.addLocalization("item.Ore.Malachite.name", "Malachite");
        ModLoader.addLocalization("item.Ore.Magnetite.name", "Magnetite");
        ModLoader.addLocalization("item.Ore.Limonite.name", "Limonite");
        ModLoader.addLocalization("item.Ore.Sphalerite.name", "Sphalerite");
        ModLoader.addLocalization("item.Ore.Tetrahedrite.name", "Tetrahedrite");
        ModLoader.addLocalization("item.Ore.Bituminous Coal.name", "Bituminous Coal");
        ModLoader.addLocalization("item.Ore.Lignite.name", "Lignite");
        ModLoader.addLocalization("item.Ore.Kaolinite.name", "Kaolinite");
        ModLoader.addLocalization("item.Ore.Gypsum.name", "Gypsum");
        ModLoader.addLocalization("item.Ore.Satinspar.name", "Satinspar");
        ModLoader.addLocalization("item.Ore.Selenite.name", "Selenite");
        ModLoader.addLocalization("item.Ore.Graphite.name", "Graphite");
        ModLoader.addLocalization("item.Ore.Kimberlite.name", "Kimberlite");
        ModLoader.addLocalization("item.Ore.Petrified Wood.name", "Petrified Wood");
        ModLoader.addLocalization("item.Ore.Sulfur.name", "Sulfur");
        ModLoader.addLocalization("item.Ore.Jet.name", "Jet");
        ModLoader.addLocalization("item.Ore.Microcline.name", "Microcline");
        ModLoader.addLocalization("item.Ore.Pitchblende.name", "Pitchblende");
        ModLoader.addLocalization("item.Ore.Cinnabar.name", "Cinnabar");
        ModLoader.addLocalization("item.Ore.Cryolite.name", "Cryolite");
        ModLoader.addLocalization("item.Ore.Saltpeter.name", "Saltpeter");
        ModLoader.addLocalization("item.Ore.Serpentine.name", "Serpentine");
        ModLoader.addLocalization("item.Ore.Sylvite.name", "Sylvite");
        ModLoader.addLocalization("item.Ore.Borax.name", "Borax");
        ModLoader.addLocalization("item.Ore.Olivine.name", "Olivine");
        ModLoader.addLocalization("item.Ore.LapisLazuli.name", "Lapis Lazuli");
        ModLoader.addLocalization("item.Ore.GalenaPartial.name", "Partially Smelted Galena");
        ModLoader.addLocalization("item.Ore.TetrahedritePartial.name", "Partially Smelted Tetrahedrite");
        ModLoader.addLocalization("item.Ore.MagnetitePartial.name", "Partially Smelted Magnetite");
        
        ModLoader.addLocalization("item.Small Ore.Native Copper.name", "Native Copper Nugget");
        ModLoader.addLocalization("item.Small Ore.Native Gold.name", "Native Gold Nugget");
        ModLoader.addLocalization("item.Small Ore.Native Platinum.name", "Native Platinum Nugget");
        ModLoader.addLocalization("item.Small Ore.Hematite.name", "Small Hematite");
        ModLoader.addLocalization("item.Small Ore.Native Silver.name", "Native Silver Nugget");
        ModLoader.addLocalization("item.Small Ore.Cassiterite.name", "Small Cassiterite");
        ModLoader.addLocalization("item.Small Ore.Galena.name", "Small Galena");
        ModLoader.addLocalization("item.Small Ore.Bismuthinite.name", "Small Bismuthinite");
        ModLoader.addLocalization("item.Small Ore.Garnierite.name", "Small Garnierite");
        ModLoader.addLocalization("item.Small Ore.Malachite.name", "Small Malachite");
        ModLoader.addLocalization("item.Small Ore.Magnetite.name", "Small Magnetite");
        ModLoader.addLocalization("item.Small Ore.Limonite.name", "Small Limonite");
        ModLoader.addLocalization("item.Small Ore.Sphalerite.name", "Small Sphalerite");
        ModLoader.addLocalization("item.Small Ore.Tetrahedrite.name", "Small Tetrahedrite");
        ModLoader.addLocalization("item.Small Ore.GalenaPartial.name", "Small Partially Smelted Galena");
        ModLoader.addLocalization("item.Small Ore.TetrahedritePartial.name", "Small Partially Smelted Tetrahedrite");
        ModLoader.addLocalization("item.Small Ore.MagnetitePartial.name", "Small Partially Smelted Magnetite");
        
        //Cobble
        ModLoader.addLocalization("tile.IgInRockCobble.Granite.name", "Granite Cobblestone");
        ModLoader.addLocalization("tile.IgInRockCobble.Diorite.name", "Diorite Cobblestone");
        ModLoader.addLocalization("tile.IgInRockCobble.Gabbro.name", "Gabbro Cobblestone");
        ModLoader.addLocalization("tile.IgExRockCobble.Rhyolite.name", "Rhyolite Cobblestone");
        ModLoader.addLocalization("tile.IgExRockCobble.Basalt.name", "Basalt Cobblestone");
        ModLoader.addLocalization("tile.IgExRockCobble.Andesite.name", "Andesite Cobblestone");
        ModLoader.addLocalization("tile.IgExRockCobble.Dacite.name", "Dacite Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Siltstone.name", "Siltstone Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Mudstone.name", "Mudstone Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Shale.name", "Shale Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Claystone.name", "Claystone Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Rock Salt.name", "Rock Salt Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Limestone.name", "Limestone Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Conglomerate.name", "Conglomerate Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Dolomite.name", "Dolomite Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Chert.name", "Chert Cobblestone");
        ModLoader.addLocalization("tile.SedRockCobble.Chalk.name", "Chalk Cobblestone");
        ModLoader.addLocalization("tile.MMRockCobble.Quartzite.name", "Quartzite Cobblestone");
        ModLoader.addLocalization("tile.MMRockCobble.Slate.name", "Slate Cobblestone");
        ModLoader.addLocalization("tile.MMRockCobble.Phyllite.name", "Phyllite Cobblestone");
        ModLoader.addLocalization("tile.MMRockCobble.Schist.name", "Schist Cobblestone");
        ModLoader.addLocalization("tile.MMRockCobble.Gneiss.name", "Gneiss Cobblestone");
        ModLoader.addLocalization("tile.MMRockCobble.Marble.name", "Marble Cobblestone");
        //Smooth Stone
        ModLoader.addLocalization("tile.IgInRockSmooth.Granite.name", "Smooth Granite");
        ModLoader.addLocalization("tile.IgInRockSmooth.Diorite.name", "Smooth Diorite");
        ModLoader.addLocalization("tile.IgInRockSmooth.Gabbro.name", "Smooth Gabbro");
        ModLoader.addLocalization("tile.IgExRockSmooth.Rhyolite.name", "Smooth Rhyolite");
        ModLoader.addLocalization("tile.IgExRockSmooth.Basalt.name", "Smooth Basalt");
        ModLoader.addLocalization("tile.IgExRockSmooth.Andesite.name", "Smooth Andesite");
        ModLoader.addLocalization("tile.IgExRockSmooth.Dacite.name", "Smooth Dacite");
        ModLoader.addLocalization("tile.SedRockSmooth.Siltstone.name", "Smooth Siltstone");
        ModLoader.addLocalization("tile.SedRockSmooth.Mudstone.name", "Smooth Mudstone");
        ModLoader.addLocalization("tile.SedRockSmooth.Shale.name", "Smooth Shale");
        ModLoader.addLocalization("tile.SedRockSmooth.Claystone.name", "Smooth Claystone");
        ModLoader.addLocalization("tile.SedRockSmooth.Rock Salt.name", "Smooth Rock Salt");
        ModLoader.addLocalization("tile.SedRockSmooth.Limestone.name", "Smooth Limestone");
        ModLoader.addLocalization("tile.SedRockSmooth.Conglomerate.name", "Smooth Conglomerate");
        ModLoader.addLocalization("tile.SedRockSmooth.Dolomite.name", "Smooth Dolomite");
        ModLoader.addLocalization("tile.SedRockSmooth.Chert.name", "Smooth Chert");
        ModLoader.addLocalization("tile.SedRockSmooth.Chalk.name", "Smooth Chalk");
        ModLoader.addLocalization("tile.MMRockSmooth.Quartzite.name", "Smooth Quartzite");
        ModLoader.addLocalization("tile.MMRockSmooth.Slate.name", "Smooth Slate");
        ModLoader.addLocalization("tile.MMRockSmooth.Phyllite.name", "Smooth Phyllite");
        ModLoader.addLocalization("tile.MMRockSmooth.Schist.name", "Smooth Schist");
        ModLoader.addLocalization("tile.MMRockSmooth.Gneiss.name", "Smooth Gneiss");
        ModLoader.addLocalization("tile.MMRockSmooth.Marble.name", "Smooth Marble");
        //Brick
        ModLoader.addLocalization("tile.IgInRockBrick.Granite.name", "Granite Brick");
        ModLoader.addLocalization("tile.IgInRockBrick.Diorite.name", "Diorite Brick");
        ModLoader.addLocalization("tile.IgInRockBrick.Gabbro.name", "Gabbro Brick");
        ModLoader.addLocalization("tile.IgExRockBrick.Rhyolite.name", "Rhyolite Brick");
        ModLoader.addLocalization("tile.IgExRockBrick.Basalt.name", "Basalt Brick");
        ModLoader.addLocalization("tile.IgExRockBrick.Andesite.name", "Andesite Brick");
        ModLoader.addLocalization("tile.IgExRockBrick.Dacite.name", "Dacite Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Siltstone.name", "Siltstone Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Mudstone.name", "Mudstone Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Shale.name", "Shale Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Claystone.name", "Claystone Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Rock Salt.name", "Rock Salt Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Limestone.name", "Limestone Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Conglomerate.name", "Conglomerate Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Dolomite.name", "Dolomite Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Chert.name", "Chert Brick");
        ModLoader.addLocalization("tile.SedRockBrick.Chalk.name", "Chalk Brick");
        ModLoader.addLocalization("tile.MMRockBrick.Quartzite.name", "Quartzite Brick");
        ModLoader.addLocalization("tile.MMRockBrick.Slate.name", "Slate Brick");
        ModLoader.addLocalization("tile.MMRockBrick.Phyllite.name", "Phyllite Brick");
        ModLoader.addLocalization("tile.MMRockBrick.Schist.name", "Schist Brick");
        ModLoader.addLocalization("tile.MMRockBrick.Gneiss.name", "Gneiss Brick");
        ModLoader.addLocalization("tile.MMRockBrick.Marble.name", "Marble Brick");
        //Cobble Stairs
        ModLoader.addLocalization("tile.IgInRockCobbleStairs.Granite.name", "Granite Cobblestone Stairs");
        ModLoader.addLocalization("tile.IgInRockCobbleStairs.Diorite.name", "Diorite Cobblestone Stairs");
        ModLoader.addLocalization("tile.IgInRockCobbleStairs.Gabbro.name", "Gabbro Cobblestone Stairs");
        ModLoader.addLocalization("tile.IgExRockCobbleStairs.Rhyolite.name", "Rhyolite Cobblestone Stairs");
        ModLoader.addLocalization("tile.IgExRockCobbleStairs.Basalt.name", "Basalt Cobblestone Stairs");
        ModLoader.addLocalization("tile.IgExRockCobbleStairs.Andesite.name", "Andesite Cobblestone Stairs");
        ModLoader.addLocalization("tile.IgExRockCobbleStairs.Dacite.name", "Dacite Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Siltstone.name", "Siltstone Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Mudstone.name", "Mudstone Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Shale.name", "Shale Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Claystone.name", "Claystone Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Rock Salt.name", "Rock Salt Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Limestone.name", "Limestone Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Conglomerate.name", "Conglomerate Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Dolomite.name", "Dolomite Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Chert.name", "Chert Cobblestone Stairs");
        ModLoader.addLocalization("tile.SedRockCobbleStairs.Chalk.name", "Chalk Cobblestone Stairs");
        ModLoader.addLocalization("tile.MMRockCobbleStairs.Quartzite.name", "Quartzite Cobblestone Stairs");
        ModLoader.addLocalization("tile.MMRockCobbleStairs.Slate.name", "Slate Cobblestone Stairs");
        ModLoader.addLocalization("tile.MMRockCobbleStairs.Phyllite.name", "Phyllite Cobblestone Stairs");
        ModLoader.addLocalization("tile.MMRockCobbleStairs.Schist.name", "Schist Cobblestone Stairs");
        ModLoader.addLocalization("tile.MMRockCobbleStairs.Gneiss.name", "Gneiss Cobblestone Stairs");
        ModLoader.addLocalization("tile.MMRockCobbleStairs.Marble.name", "Marble Cobblestone Stairs");

        //Dirt
        ModLoader.addLocalization("tile.dirt.Granite.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Diorite.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Gabbro.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Rhyolite.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Basalt.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Andesite.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Dacite.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Siltstone.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Mudstone.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Shale.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Claystone.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Rock Salt.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Limestone.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Conglomerate.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Dolomite.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Chert.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Chalk.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Quartzite.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Slate.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Phyllite.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Schist.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Gneiss.name", "Dirt");
        ModLoader.addLocalization("tile.dirt.Marble.name", "Dirt");
        //Clay
        ModLoader.addLocalization("tile.clay.Granite.name", "Clay");
        ModLoader.addLocalization("tile.clay.Diorite.name", "Clay");
        ModLoader.addLocalization("tile.clay.Gabbro.name", "Clay");
        ModLoader.addLocalization("tile.clay.Rhyolite.name", "Clay");
        ModLoader.addLocalization("tile.clay.Basalt.name", "Clay");
        ModLoader.addLocalization("tile.clay.Andesite.name", "Clay");
        ModLoader.addLocalization("tile.clay.Dacite.name", "Clay");
        ModLoader.addLocalization("tile.clay.Siltstone.name", "Clay");
        ModLoader.addLocalization("tile.clay.Mudstone.name", "Clay");
        ModLoader.addLocalization("tile.clay.Shale.name", "Clay");
        ModLoader.addLocalization("tile.clay.Claystone.name", "Clay");
        ModLoader.addLocalization("tile.clay.Rock Salt.name", "Clay");
        ModLoader.addLocalization("tile.clay.Limestone.name", "Clay");
        ModLoader.addLocalization("tile.clay.Conglomerate.name", "Clay");
        ModLoader.addLocalization("tile.clay.Dolomite.name", "Clay");
        ModLoader.addLocalization("tile.clay.Chert.name", "Clay");
        ModLoader.addLocalization("tile.clay.Chalk.name", "Clay");
        ModLoader.addLocalization("tile.clay.Quartzite.name", "Clay");
        ModLoader.addLocalization("tile.clay.Slate.name", "Clay");
        ModLoader.addLocalization("tile.clay.Phyllite.name", "Clay");
        ModLoader.addLocalization("tile.clay.Schist.name", "Clay");
        ModLoader.addLocalization("tile.clay.Gneiss.name", "Clay");
        ModLoader.addLocalization("tile.clay.Marble.name", "Clay");
        //Grass
        ModLoader.addLocalization("tile.Grass.Granite.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Diorite.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Gabbro.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Rhyolite.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Basalt.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Andesite.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Dacite.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Siltstone.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Mudstone.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Shale.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Claystone.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Rock Salt.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Limestone.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Conglomerate.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Dolomite.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Chert.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Chalk.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Quartzite.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Slate.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Phyllite.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Schist.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Gneiss.name", "Grass");
        ModLoader.addLocalization("tile.Grass.Marble.name", "Grass");
        //clay grass
        ModLoader.addLocalization("tile.ClayGrass.Granite.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Diorite.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Gabbro.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Rhyolite.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Basalt.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Andesite.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Dacite.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Siltstone.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Mudstone.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Shale.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Claystone.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Rock Salt.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Limestone.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Conglomerate.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Dolomite.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Chert.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Chalk.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Quartzite.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Slate.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Phyllite.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Schist.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Gneiss.name", "Clay Grass");
        ModLoader.addLocalization("tile.ClayGrass.Marble.name", "Clay Grass");
        //peat
        ModLoader.addLocalization("tile.peat.name", "Peat");
        ModLoader.addLocalization("tile.PeatGrass.name", "Peat Grass");
        ModLoader.addLocalization("item.SulfurPowder.name", "Sulfur Powder");
        ModLoader.addLocalization("item.SaltpeterPowder.name", "Saltpeter");
        ModLoader.addLocalization("item.flintPaxel.name", "Flint Tool");

        ModLoader.addLocalization("item.IgIn Stone Pick.name", "Stone Pickaxe");
        ModLoader.addLocalization("item.IgIn Stone Shovel.name", "Stone Shovel");
        ModLoader.addLocalization("item.IgIn Stone Axe.name", "Stone Axe");
        ModLoader.addLocalization("item.IgIn Stone Hoe.name", "Stone Hoe");
        ModLoader.addLocalization("item.IgIn Stone Sword.name", "Stone Sword");
        ModLoader.addLocalization("item.IgIn Stone Mace.name", "Stone Mace");
        ModLoader.addLocalization("item.Sed Stone Pick.name", "Stone Pickaxe");
        ModLoader.addLocalization("item.Sed Stone Shovel.name", "Stone Shovel");
        ModLoader.addLocalization("item.Sed Stone Axe.name", "Stone Axe");
        ModLoader.addLocalization("item.Sed Stone Hoe.name", "Stone Hoe");
        ModLoader.addLocalization("item.Sed Stone Sword.name", "Stone Sword");
        ModLoader.addLocalization("item.Sed Stone Mace.name", "Stone Mace");
        ModLoader.addLocalization("item.IgEx Stone Pick.name", "Stone Pickaxe");
        ModLoader.addLocalization("item.IgEx Stone Shovel.name", "Stone Shovel");
        ModLoader.addLocalization("item.IgEx Stone Axe.name", "Stone Axe");
        ModLoader.addLocalization("item.IgEx Stone Hoe.name", "Stone Hoe");
        ModLoader.addLocalization("item.IgEx Stone Sword.name", "Stone Sword");
        ModLoader.addLocalization("item.IgEx Stone Mace.name", "Stone Mace");
        ModLoader.addLocalization("item.MM Stone Pick.name", "Stone Pickaxe");
        ModLoader.addLocalization("item.MM Stone Shovel.name", "Stone Shovel");
        ModLoader.addLocalization("item.MM Stone Axe.name", "Stone Axe");
        ModLoader.addLocalization("item.MM Stone Hoe.name", "Stone Hoe");
        ModLoader.addLocalization("item.MM Stone Sword.name", "Stone Sword");
        ModLoader.addLocalization("item.MM Stone Mace.name", "Stone Mace");

        ModLoader.addLocalization("item.Bone IgIn Stone Pick.name", "Stone Pickaxe");
        ModLoader.addLocalization("item.Bone IgIn Stone Shovel.name", "Stone Shovel");
        ModLoader.addLocalization("item.Bone IgIn Stone Axe.name", "Stone Axe");
        ModLoader.addLocalization("item.Bone IgIn Stone Hoe.name", "Stone Hoe");

        ModLoader.addLocalization("item.Bone Sed Stone Pick.name", "Stone Pickaxe");
        ModLoader.addLocalization("item.Bone Sed Stone Shovel.name", "Stone Shovel");
        ModLoader.addLocalization("item.Bone Sed Stone Axe.name", "Stone Axe");
        ModLoader.addLocalization("item.Bone Sed Stone Hoe.name", "Stone Hoe");

        ModLoader.addLocalization("item.Bone IgEx Stone Pick.name", "Stone Pickaxe");
        ModLoader.addLocalization("item.Bone IgEx Stone Shovel.name", "Stone Shovel");
        ModLoader.addLocalization("item.Bone IgEx Stone Axe.name", "Stone Axe");
        ModLoader.addLocalization("item.Bone IgEx Stone Hoe.name", "Stone Hoe");

        ModLoader.addLocalization("item.Bone MM Stone Pick.name", "Stone Pickaxe");
        ModLoader.addLocalization("item.Bone MM Stone Shovel.name", "Stone Shovel");
        ModLoader.addLocalization("item.Bone MM Stone Axe.name", "Stone Axe");
        ModLoader.addLocalization("item.Bone MM Stone Hoe.name", "Stone Hoe");

        ModLoader.addLocalization("item.Stone Hammer.name", "Stone Hammer");
    }
    
    private void RegisterWood()
    {
        String[] WoodNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
                "Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
        for(int i= 0; i < WoodNames.length; i++)
        {
            ModLoader.addLocalization("tile.log."+WoodNames[i]+".name", WoodNames[i]);
            ModLoader.addLocalization("tile.leaves."+WoodNames[i]+".name", WoodNames[i] + " Leaves");
            ModLoader.addLocalization("tile.sapling."+WoodNames[i]+".name", WoodNames[i] + " Sapling");
            ModLoader.addLocalization("tile.wood."+WoodNames[i]+".name", WoodNames[i] + " Planks");
            ModLoader.addLocalization("item.terraWoodSupportItemV."+WoodNames[i]+".name","V. " + WoodNames[i] + " Support Beam");
            ModLoader.addLocalization("item.terraWoodSupportItemH."+WoodNames[i]+".name","H. " + WoodNames[i] + " Support Beam");
            ModLoader.addLocalization("item.Log."+WoodNames[i]+".name", WoodNames[i]);
            ModLoader.addLocalization("item.SinglePlank."+WoodNames[i]+".name", WoodNames[i] + " Plank");
        }
        ModLoader.addLocalization("item.Stick.name", "Stick");
    }
    
    private void RegisterMetal()
    {
        String[] ToolNames = {"Stone","Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel", "Bronze", "Copper"
                ,"Wrought Iron","Red Steel","Rose Gold", "Steel", "Tin", "Zinc"};
        
        String[] Names = {"Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel","Brass","Bronze","Copper","Gold"
                ,"Wrought Iron","Lead","Nickel","Pig Iron","Platinum","Red Steel","Rose Gold","Silver", "Steel", "Sterling Silver",
                "Tin", "Zinc"};
        
        ModLoader.addLocalization("item.StoneProPick.name", "Stone Prospector's Pick");
        
        ModLoader.addLocalization("tile.terraAnvil.name", "Anvil");
        ModLoader.addLocalization("item.terraStoneAnvilItem.name", "Stone Anvil");
        ModLoader.addLocalization("item.terraCopperAnvilItem.name", "Copper Anvil");
        ModLoader.addLocalization("item.terraBronzeAnvilItem.name", "Bronze Anvil");
        ModLoader.addLocalization("item.terraWroughtIronAnvilItem.name", "Wrought Iron Anvil");
        ModLoader.addLocalization("item.terraSteelAnvilItem.name", "Steel Anvil");
        ModLoader.addLocalization("item.terraBlackSteelAnvilItem.name", "Black Steel  Anvil");
        ModLoader.addLocalization("item.terraBlueSteelAnvilItem.name", "Blue Steel Anvil");
        ModLoader.addLocalization("item.terraRedSteelAnvilItem.name", "Red Steel Anvil");
        ModLoader.addLocalization("item.terraBismuthBronzeAnvilItem.name", "Bismuth Bronze Anvil");
        ModLoader.addLocalization("item.terraBlackBronzeAnvilItem.name", "Black Bronze Anvil");
        ModLoader.addLocalization("item.terraRoseGoldAnvilItem.name", "Rose Gold Anvil");
        
        for(int i= 0; i < ToolNames.length; i++)
        {
            ModLoader.addLocalization("item."+ToolNames[i]+" Pick.name", ToolNames[i] + " Pickaxe");
            ModLoader.addLocalization("item."+ToolNames[i]+" Shovel.name", ToolNames[i] + " Shovel");
            ModLoader.addLocalization("item."+ToolNames[i]+" Axe.name", ToolNames[i] + " Axe");
            ModLoader.addLocalization("item."+ToolNames[i]+" Hoe.name", ToolNames[i] + " Hoe");
            ModLoader.addLocalization("item."+ToolNames[i]+" Hammer.name", ToolNames[i] + " Hammer");
            ModLoader.addLocalization("item."+ToolNames[i]+" Sword.name", ToolNames[i] + " Sword");
            ModLoader.addLocalization("item."+ToolNames[i]+" Mace.name", ToolNames[i] + " Mace");
            ModLoader.addLocalization("item."+ToolNames[i]+" Chisel.name", ToolNames[i] + " Chisel");
            ModLoader.addLocalization("item."+ToolNames[i]+" Saw.name", ToolNames[i] + " Saw");
            ModLoader.addLocalization("item."+ToolNames[i]+" Scythe.name", ToolNames[i] + " Scythe");
            ModLoader.addLocalization("item."+ToolNames[i]+" Knife.name", ToolNames[i] + " Knife");
            ModLoader.addLocalization("item."+ToolNames[i]+" ProPick.name", ToolNames[i] + " Prospector's Pick");
            ModLoader.addLocalization("item."+ToolNames[i]+" Pickaxe Head.name", ToolNames[i] + " Pickaxe Head");
            ModLoader.addLocalization("item."+ToolNames[i]+" Shovel Head.name", ToolNames[i] + " Shovel Head");
            ModLoader.addLocalization("item."+ToolNames[i]+" Axe Head.name", ToolNames[i] + " Axe Head");
            ModLoader.addLocalization("item."+ToolNames[i]+" Hoe Head.name", ToolNames[i] + " Hoe Head");
            ModLoader.addLocalization("item."+ToolNames[i]+" Hammer Head.name", ToolNames[i] + " Hammer Head");
            ModLoader.addLocalization("item."+ToolNames[i]+" Sword Blade.name", ToolNames[i] + " Sword Blade");
            ModLoader.addLocalization("item."+ToolNames[i]+" Mace Head.name", ToolNames[i] + " Mace Head");
            ModLoader.addLocalization("item."+ToolNames[i]+" Chisel Head.name", ToolNames[i] + " Chisel Head");
            ModLoader.addLocalization("item."+ToolNames[i]+" Saw Blade.name", ToolNames[i] + " Saw Blade");
            ModLoader.addLocalization("item."+ToolNames[i]+" ProPick Head.name", ToolNames[i] + " Prospector's Pick Head");
            ModLoader.addLocalization("item."+ToolNames[i]+" Scythe Blade.name", ToolNames[i] + " Scythe Blade");
            ModLoader.addLocalization("item."+ToolNames[i]+" Knife Blade.name", ToolNames[i] + " Knife Blade");
            /**
             * Armor Related
             * */
            ModLoader.addLocalization("item."+ToolNames[i]+"Sheet.name", ToolNames[i] + " Sheet");
            ModLoader.addLocalization("item."+ToolNames[i]+"Sheet2x.name", ToolNames[i] + " Sheet 2x");
            ModLoader.addLocalization("item."+ToolNames[i]+"UnfinishedHelmet.name", "Unfinished " + ToolNames[i] + " Helmet Stage 1");
            ModLoader.addLocalization("item."+ToolNames[i]+"UnfinishedHelmet2.name", "Unfinished " + ToolNames[i] + " Helmet Stage 2");
            ModLoader.addLocalization("item."+ToolNames[i]+"Helmet.name", ToolNames[i] + " Helmet");
            ModLoader.addLocalization("item."+ToolNames[i]+"UnfinishedChestplate.name", "Unfinished " + ToolNames[i] + " Chestplate Stage 1");
            ModLoader.addLocalization("item."+ToolNames[i]+"UnfinishedChestplate2.name", "Unfinished " + ToolNames[i] + " Chestplate Stage 2");
            ModLoader.addLocalization("item."+ToolNames[i]+"Chestplate.name", ToolNames[i] + " Chestplate");
            ModLoader.addLocalization("item."+ToolNames[i]+"UnfinishedGreaves.name", "Unfinished " + ToolNames[i] + " Greaves Stage 1");
            ModLoader.addLocalization("item."+ToolNames[i]+"UnfinishedGreaves2.name", "Unfinished " + ToolNames[i] + " Greaves Stage 2");
            ModLoader.addLocalization("item."+ToolNames[i]+"Greaves.name", ToolNames[i] + " Greaves");
            ModLoader.addLocalization("item."+ToolNames[i]+"UnfinishedBoots.name", "Unfinished " + ToolNames[i] + " Boots Stage 1");
            ModLoader.addLocalization("item."+ToolNames[i]+"UnfinishedBoots2.name", "Unfinished " + ToolNames[i] + " Boots Stage 2");
            ModLoader.addLocalization("item."+ToolNames[i]+"Boots.name", ToolNames[i] + " Boots");
        }

        //meltedmetal
        for(int i= 0; i < Names.length; i++)
        {
            ModLoader.addLocalization("item.Unshaped"+Names[i].replace(" ", "")+".name", "Unshaped "+Names[i]);
            ModLoader.addLocalization("item.terra"+Names[i].replace(" ", "")+"Ingot.name", Names[i] + " Ingot");
            ModLoader.addLocalization("item.terra"+Names[i].replace(" ", "")+"Ingot2x.name", Names[i] + " Ingot 2x");
        }

        ModLoader.addLocalization("item.UnshapedHCBlackSteel.name", "Unshaped High Carbon Black Steel");
        ModLoader.addLocalization("item.UnshapedHCBlueSteel.name", "Unshaped High Carbon Blue Steel");
        ModLoader.addLocalization("item.UnshapedHCRedSteel.name", "Unshaped High Carbon Red Steel");
        ModLoader.addLocalization("item.UnshapedHCSteel.name", "Unshaped High Carbon Steel");
        ModLoader.addLocalization("item.UnshapedWeakSteel.name", "Unshaped Weak Steel");
        ModLoader.addLocalization("item.UnshapedWeakBlueSteel.name", "Unshaped Weak Blue Steel");
        ModLoader.addLocalization("item.UnshapedWeakRedSteel.name", "Unshaped Weak Red Steel");

        ModLoader.addLocalization("item.terraHCBlackSteelIngot.name", "High Carbon Black Steel Ingot");
        ModLoader.addLocalization("item.terraHCBlueSteelIngot.name", "High Carbon Blue Steel Ingot");
        ModLoader.addLocalization("item.terraHCRedSteelIngot.name", "High Carbon Red Steel Ingot");
        ModLoader.addLocalization("item.terraHCSteelIngot.name", "High Carbon Steel Ingot");
        ModLoader.addLocalization("item.terraWeakSteelIngot.name", "Weak Steel Ingot");
        ModLoader.addLocalization("item.terraWeakBlueSteelIngot.name", "Weak Blue Steel Ingot");
        ModLoader.addLocalization("item.terraWeakRedSteelIngot.name", "Weak Red Steel Ingot");
    }

    @Override
    public String getPlayerName()
    {
        return ModLoader.getMinecraftInstance().thePlayer.username;
    }

    @Override
    public void handleHealthUpdate(Class c, byte par1)
    {
        try
        {
            c.getSuperclass().getMethod("handleHealthUpdate", Byte.class).invoke(par1);
        } catch (Exception e1){}  
    }
    @Override
    public boolean aiTargetShouldExecute(EntityAITarget target, EntityLiving par1EntityLiving, boolean par2)
    {
        return target.func_48376_a(par1EntityLiving, par2);
    }
    @Override
    public boolean aiTargetfunc_48100_a(Class par1Class, EntityLiving entity)
    {
        return EntityCreeper.class != par1Class && EntityGhast.class != par1Class;
    }


}
