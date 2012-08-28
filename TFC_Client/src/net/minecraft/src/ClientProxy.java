package net.minecraft.src;

import java.io.File;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.RenderingRegistry;
import cpw.mods.fml.common.registry.EntityRegistry;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.common.registry.LanguageRegistry;

import bioxx.importers.WavefrontObject;

import TFC.Core.ColorizerFoliageTFC;
import TFC.Core.ColorizerGrassTFC;
import TFC.Core.IProxy;
import TFC.Core.KeyBindings;
import TFC.Core.PlayerManagerTFC;
import TFC.Core.TFCHeat;
import TFC.Core.TFCSeasons;
import TFC.Core.TFCSettings;
import TFC.Entities.EntityBear;
import TFC.Entities.EntityChickenTFC;
import TFC.Entities.EntityCowTFC;
import TFC.Entities.EntityDeer;
import TFC.Entities.EntityFallingDirt;
import TFC.Entities.EntityFallingStone;
import TFC.Entities.EntityFallingStone;
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
import TFC.Handlers.BlockRenderHandler;
import TFC.Handlers.KeyBindingHandler;
import TFC.Handlers.PacketHandler;
import TFC.Handlers.SoundHandler;
import TFC.Items.ItemChisel;
import TFC.Items.ItemTerra;
import TFC.Items.ItemTerraArmor;
import TFC.Render.*;
import TFC.TileEntities.*;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.Biomes.BiomeGenJungleTFC;
import net.minecraft.client.Minecraft;
import net.minecraft.src.*;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

public class ClientProxy extends CommonProxy 
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
        
        RenderingRegistry.registerEntityRenderingHandler(EntityFallingDirt.class, new RenderFallingDirt());
    	RenderingRegistry.registerEntityRenderingHandler(EntityFallingStone.class, new RenderFallingStone());
    	RenderingRegistry.registerEntityRenderingHandler(EntityFallingStone.class, new RenderFallingStone2());
    	RenderingRegistry.registerEntityRenderingHandler(EntityTerraJavelin.class, new RenderTerraJavelin());
    	RenderingRegistry.registerEntityRenderingHandler(EntitySquidTFC.class, new RenderSquid(new ModelSquid(), 0.7F));
    	RenderingRegistry.registerEntityRenderingHandler(EntityCowTFC.class, new RenderCowTFC(new ModelCowTFC(), 0.7F));
    	RenderingRegistry.registerEntityRenderingHandler(EntitySheepTFC.class, new RenderSheepTFC(new ModelSheep2TFC(),new ModelSheep1TFC(), 0.7F));
    	RenderingRegistry.registerEntityRenderingHandler(EntityWolfTFC.class, new RenderWolfTFC(new ModelWolfTFC(), 0.5F));
    	RenderingRegistry.registerEntityRenderingHandler(EntityBear.class, new RenderBear(new ModelBear(), 0.9F));
        RenderingRegistry.registerEntityRenderingHandler(EntityChickenTFC.class, new RenderChickenTFC(new ModelChickenTFC(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityPigTFC.class, new RenderPigTFC(new ModelPigTFC(), new ModelPigTFC(0.5F), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(EntityDeer.class, new RenderDeer(new ModelDeer(), 0.9F));
        
        RenderingRegistry.registerBlockHandler(TFCBlocks.sulfurRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodSupportRenderIdH = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodSupportRenderIdV = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.grassRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.oreRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.moltenRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.looseRockRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.terraFirepitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.terraAnvilRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.terraBellowsRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.terraScribeRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.terraForgeRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.sluiceRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.woodFruitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.leavesFruitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.finiteWaterRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.stairRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.slabRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.cropRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.cookingPitRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
		RenderingRegistry.registerBlockHandler(TFCBlocks.leavesRenderId = RenderingRegistry.getNextAvailableRenderId(), new BlockRenderHandler());
    }

    @Override
    public World getCurrentWorld() {
        return ModLoader.getMinecraftInstance().theWorld;
    }
    @Override
    public File getMinecraftDir() {
		return ModLoader.getMinecraftInstance().getMinecraftDir();
	}

    @Override
    public boolean renderWorldBlock(Object renderblocks,
            IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) 
    {
        


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
        if(par1IBlockAccess.getBlockId(par2, par3, par4) == TFCBlocks.fruitTreeLeaves.blockID)
        {
            if(TFCSeasons.currentMonth >= 6 && TFCSeasons.currentMonth < 9)
            {
                for (int var8 = -1; var8 <= 1; ++var8)
                {
                    for (int var9 = -1; var9 <= 1; ++var9)
                    {
                        TFCBiome biome  = (TFCBiome) par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8);

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
                        TFCBiome biome  = (TFCBiome) par1IBlockAccess.getBiomeGenForCoords(par2 + var9, par4 + var8);

                        double var1 = (double)MathHelper.clamp_float(biome.getFloatTemp(), 0.0F, 1.0F);
                        double var3 = (double)MathHelper.clamp_float(biome.getFloatRain(), 0.0F, 1.0F);
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
    public void sendCustomPacket(Packet packet)
    {
        ModLoader.sendPacket(packet);     
    }

    @Override
    public int getArmorRenderID(int i)
    {
        String[] Names = {"bismuth", "bismuthbronze", "blackbronze", "blacksteel", "bluesteel", "bronze", "copper", "wroughtiron", "redsteel", "rosegold", "steel", "tin", "zinc"};
        return ModLoader.addArmor(Names[i]);
    }

    @Override
    public void registerKeys()
    {
    	KeyBindings.addKeyBinding("Key_Calendar", 49);
		KeyBindings.addIsRepeating(false);
		KeyBindings.addKeyBinding("Key_ToolMode", 50);
		KeyBindings.addIsRepeating(false);
    }
    
    @Override
    public void registerKeyBindingHandler()
    {
    	KeyBindingRegistry.registerKeyBinding(new KeyBindingHandler());
        
    }
    
    @Override
    public void registerSoundHandler() {
        MinecraftForge.EVENT_BUS.register(new SoundHandler());
    }

    @Override
    public void registerTranslations() 
    {
    	LanguageRegistry LR = LanguageRegistry.instance();
    	
        LR.addStringLocalization("Key_Calendar", "Open Calendar");
        
        //Gems
        String[] GemNames = {"Ruby","Emerald","Topaz","Sapphire","Opal","Agate",
                "Jade","Garnet","Amethyst","Beryl","Jasper","Tourmaline","Diamond"};

        for(int i= 0; i < GemNames.length; i++)
        {
            LR.addStringLocalization("item."+GemNames[i]+".Chipped.name", "Chipped "+GemNames[i]);
            LR.addStringLocalization("item."+GemNames[i]+".Flawed.name", "Flawed "+GemNames[i]);
            LR.addStringLocalization("item."+GemNames[i]+".Normal.name",  GemNames[i]);
            LR.addStringLocalization("item."+GemNames[i]+".Flawless.name", "Flawless "+GemNames[i]);
            LR.addStringLocalization("item."+GemNames[i]+".Exquisite.name", "Exquisite "+GemNames[i]);
        }

        LR.addStringLocalization("item.javelin.name", "Javelin");

        LR.addStringLocalization("item.WoodenBucketEmpty.name", "Wooden Bucket (Empty)");
        LR.addStringLocalization("item.WoodenBucketWater.name", "Wooden Bucket (Water)");
        LR.addStringLocalization("item.WoodenBucketMilk.name", "Wooden Bucket (Milk)");

        LR.addStringLocalization("item.SluiceItem.name", "Sluice");

        LR.addStringLocalization("item.GoldPan.GoldPan.name", "Gold Pan - Empty");
        LR.addStringLocalization("item.GoldPan.GoldPanSand.name", "Gold Pan - Sand");
        LR.addStringLocalization("item.GoldPan.GoldPanGravel.name", "Gold Pan - Gravel");
        LR.addStringLocalization("item.GoldPan.GoldPanClay.name", "Gold Pan - Clay");
        LR.addStringLocalization("item.GoldPan.GoldPanDirt.name", "Gold Pan - Dirt");
    
        LR.addStringLocalization("item.terraFireStarter.name", "Firestarter");
        LR.addStringLocalization("item.terraSlag.name", "Slag");


        LR.addStringLocalization("item.terraBellowsItem.name", "Bellows");
        LR.addStringLocalization("tile.terraBellows.name", "Bellows");
        LR.addStringLocalization("tile.terraScribe.name", "Scribing Table");
        LR.addStringLocalization("item.terraInk.name", "Marking");
        LR.addStringLocalization("item.terraClayMold.name", "Clay Mold");
        LR.addStringLocalization("item.terraFiredClayMold.name", "Ceramic Mold");
        LR.addStringLocalization("tile.terraForge.name", "Forge");
        LR.addStringLocalization("tile.terraBloomery.name", "Bloomery");
        LR.addStringLocalization("tile.terraMetallurgy.name", "Metallurgy Table");

        LR.addStringLocalization("item.PickaxeHeadPlan.name", "Plan: Pickaxe Head");
        LR.addStringLocalization("item.ShovelHeadPlan.name", "Plan: Shovel Head");
        LR.addStringLocalization("item.HoeHeadPlan.name", "Plan: Hoe Head");
        LR.addStringLocalization("item.AxeHeadPlan.name", "Plan: Axe Head");
        LR.addStringLocalization("item.HammerHeadPlan.name", "Plan: Hammer Head");
        LR.addStringLocalization("item.ChiselHeadPlan.name", "Plan: Chisel Head");
        LR.addStringLocalization("item.SwordBladePlan.name", "Plan: Sword Blade");
        LR.addStringLocalization("item.MaceHeadPlan.name", "Plan: Mace Head");
        LR.addStringLocalization("item.SawBladePlan.name", "Plan: Saw Head");
        LR.addStringLocalization("item.ProPickHeadPlan.name", "Plan: Prospecter's Pick Head");
        LR.addStringLocalization("item.HelmetPlan.name", "Plan: Plate Helmet");
        LR.addStringLocalization("item.ChestplatePlan.name", "Plan: Chestplate");
        LR.addStringLocalization("item.GreavesPlan.name", "Plan: Plate Greaves");
        LR.addStringLocalization("item.BootsPlan.name", "Plan: Plate Boots");
        LR.addStringLocalization("item.ScythePlan.name", "Plan: Scythe Blade");
        LR.addStringLocalization("item.KnifePlan.name", "Plan: Knife Blade");

        LR.addStringLocalization("item.coke.name", "Coke");
        LR.addStringLocalization("item.flux.name", "Flux");
        
        RegisterTerrain();
        RegisterMetal();
        RegisterFood();
        RegisterWood();
    }
    
    private void RegisterFood()
    {
    	LanguageRegistry LR = LanguageRegistry.instance();
        String[] FruitTreeNames = {"Red Apple","Banana", "Orange", "Green Apple","Lemon","Olive","Cherry","Peach","Plum","Cacao"};
        for(int i= 0; i < FruitTreeNames.length; i++)
        {
            LR.addStringLocalization("item.FruitSapling1."+FruitTreeNames[i]+".name", FruitTreeNames[i] + " Tree Sapling");
            LR.addStringLocalization("item.FruitSapling2."+FruitTreeNames[i]+".name", FruitTreeNames[i] + " Tree Sapling");
            LR.addStringLocalization("item.Fruit."+FruitTreeNames[i]+".name", FruitTreeNames[i]);
        }
        
        LR.addStringLocalization("item.SeedsWheat.name", "Wheat Seeds");
        LR.addStringLocalization("item.SeedsWildWheat.name", "Wild Wheat Seeds");
        LR.addStringLocalization("item.SeedsMaize.name", "Maize Seeds");
        LR.addStringLocalization("item.SeedsWildMaize.name", "Wild Maize Seeds");
        LR.addStringLocalization("item.SeedsTomato.name", "Tomato Seeds");
        LR.addStringLocalization("item.SeedsBarley.name", "Barley Seeds");
        LR.addStringLocalization("item.SeedsWildBarley.name", "Wild Barley Seeds");
        LR.addStringLocalization("item.SeedsRye.name", "Rye Seeds");
        LR.addStringLocalization("item.SeedsWildRye.name", "Wild Rye Seeds");
        LR.addStringLocalization("item.SeedsOat.name", "Oat Seeds");
        LR.addStringLocalization("item.SeedsWildOat.name", "Wild Oat Seeds");
        LR.addStringLocalization("item.SeedsRice.name", "Rice Seeds");
        LR.addStringLocalization("item.SeedsWildRice.name", "Wild Rice Seeds");
        LR.addStringLocalization("item.SeedsPotato.name", "Potato Seeds");
        LR.addStringLocalization("item.SeedsWildPotato.name", "Wild Potato Seeds");
        LR.addStringLocalization("item.SeedsOnion.name", "Onion Seeds");
        LR.addStringLocalization("item.SeedsCabbage.name", "Cabbage Seeds");
        LR.addStringLocalization("item.SeedsGarlic.name", "Garlic Seeds");
        LR.addStringLocalization("item.SeedsCarrot.name", "Carrot Seeds");
        LR.addStringLocalization("item.SeedsSugarcane.name", "Sugarcane Seeds");
        LR.addStringLocalization("item.SeedsHemp.name", "Hemp Seeds");
        LR.addStringLocalization("item.SeedsSoybean.name", "Soybean Seeds");
        LR.addStringLocalization("item.SeedsGreenbean.name", "Greenbean Seeds");
        LR.addStringLocalization("item.SeedsYam.name", "Yam Seeds");
        LR.addStringLocalization("item.SeedsBellPepper.name", "Bell Pepper Seeds");
        LR.addStringLocalization("item.SeedsSquash.name", "Squash Seeds");
        
        
        LR.addStringLocalization("item.Meat.EggCooked.name", "Cooked Egg");
        
        LR.addStringLocalization("item.WheatGrain.name", "Wheat Grain");
        LR.addStringLocalization("item.WildWheatGrain.name", "Wild Wheat Grain");
        LR.addStringLocalization("item.WheatWhole.name", "Wheat");
        LR.addStringLocalization("item.BarleyGrain.name", "Barley Grain");
        LR.addStringLocalization("item.WildBarleyGrain.name", "Wild Barley Grain");
        LR.addStringLocalization("item.BarleyWhole.name", "Barley");
        LR.addStringLocalization("item.OatGrain.name", "Oat Grain");
        LR.addStringLocalization("item.WildOatGrain.name", "Wild Oat Grain");
        LR.addStringLocalization("item.OatWhole.name", "Oat");
        LR.addStringLocalization("item.RyeGrain.name", "Rye Grain");
        LR.addStringLocalization("item.WildRyeGrain.name", "Wild Rye Grain");
        LR.addStringLocalization("item.RyeWhole.name", "Rye");
        LR.addStringLocalization("item.RiceGrain.name", "Rice Grain");
        LR.addStringLocalization("item.WildRiceGrain.name", "Wild Rice Grain");
        LR.addStringLocalization("item.RiceWhole.name", "Rice");
        
        LR.addStringLocalization("item.MaizeEar.name", "Maize Ear");
        LR.addStringLocalization("item.WildMaizeEar.name", "Wild Maize Ear");
        LR.addStringLocalization("item.Tomato.name", "Tomato");
        LR.addStringLocalization("item.Potato.name", "Potato");
        LR.addStringLocalization("item.WildPotato.name", "Wild Potato");
        
        LR.addStringLocalization("item.Onion.name", "Onion");
        LR.addStringLocalization("item.Cabbage.name", "Cabbage");
        LR.addStringLocalization("item.Garlic.name", "Garlic");
        LR.addStringLocalization("item.Carrot.name", "Carrot");
        LR.addStringLocalization("item.Sugarcane.name", "Sugarcane");
        LR.addStringLocalization("item.Hemp.name", "Hemp");
        LR.addStringLocalization("item.Soybeans.name", "Soybeans");
        LR.addStringLocalization("item.Greenbeans.name", "Greenbeans");
        LR.addStringLocalization("item.Yam.name", "Yam");
        LR.addStringLocalization("item.GreenBellPepper.name", "Green Bell Pepper");
        LR.addStringLocalization("item.YellowBellPepper.name", "Yellow Bell Pepper");
        LR.addStringLocalization("item.RedBellPepper.name", "Red Bell Pepper");
        LR.addStringLocalization("item.Squash.name", "Squash");
        
    }
    
    private void RegisterTerrain()
    {
    	LanguageRegistry LR = LanguageRegistry.instance();
        String[] rockNames = {"Granite", "Diorite", "Gabbro", 
                "Siltstone", "Mudstone", "Shale", "Claystone", "Rock Salt", "Limestone", "Conglomerate", "Dolomite", "Chert", 
                "Chalk", "Rhyolite", "Basalt", "Andesite", "Dacite", 
                "Quartzite", "Slate", "Phyllite", "Schist", "Gneiss", "Marble"};
        
        for(int i= 0; i < rockNames.length; i++)
        {
            LR.addStringLocalization("item.LooseRock."+rockNames[i]+".name", rockNames[i] + " Rock");
            LR.addStringLocalization("item.FlatRock."+rockNames[i]+".name", rockNames[i] + " Rock");
        }
        LR.addStringLocalization("tile.IgInRock.Granite.name", "Granite");
        LR.addStringLocalization("tile.IgInRock.Diorite.name", "Diorite");
        LR.addStringLocalization("tile.IgInRock.Gabbro.name", "Gabbro");
        LR.addStringLocalization("tile.IgExRock.Rhyolite.name", "Rhyolite");
        LR.addStringLocalization("tile.IgExRock.Basalt.name", "Basalt");
        LR.addStringLocalization("tile.IgExRock.Andesite.name", "Andesite");
        LR.addStringLocalization("tile.IgExRock.Dacite.name", "Dacite");
        LR.addStringLocalization("tile.SedRock.Siltstone.name", "Siltstone");
        LR.addStringLocalization("tile.SedRock.Mudstone.name", "Mudstone");
        LR.addStringLocalization("tile.SedRock.Shale.name", "Shale");
        LR.addStringLocalization("tile.SedRock.Claystone.name", "Claystone");
        LR.addStringLocalization("tile.SedRock.Rock Salt.name", "Rock Salt");
        LR.addStringLocalization("tile.SedRock.Limestone.name", "Limestone");
        LR.addStringLocalization("tile.SedRock.Conglomerate.name", "Conglomerate");
        LR.addStringLocalization("tile.SedRock.Dolomite.name", "Dolomite");
        LR.addStringLocalization("tile.SedRock.Chert.name", "Chert");
        LR.addStringLocalization("tile.SedRock.Chalk.name", "Chalk");
        LR.addStringLocalization("tile.MMRock.Quartzite.name", "Quartzite");
        LR.addStringLocalization("tile.MMRock.Slate.name", "Slate");
        LR.addStringLocalization("tile.MMRock.Phyllite.name", "Phyllite");
        LR.addStringLocalization("tile.MMRock.Schist.name", "Schist");
        LR.addStringLocalization("tile.MMRock.Gneiss.name", "Gneiss");
        LR.addStringLocalization("tile.MMRock.Marble.name", "Marble");
        LR.addStringLocalization("tile.Ore.Native Copper.name", "Native Copper");
        LR.addStringLocalization("tile.Ore.Native Gold.name", "Native Gold");
        LR.addStringLocalization("tile.Ore.Native Platinum.name", "Native Platinum");
        LR.addStringLocalization("tile.Ore.Hematite.name", "Hematite");
        LR.addStringLocalization("tile.Ore.Native Silver.name", "Native Silver");
        LR.addStringLocalization("tile.Ore.Cassiterite.name", "Cassiterite");
        LR.addStringLocalization("tile.Ore.Galena.name", "Galena");
        LR.addStringLocalization("tile.Ore.Bismuthinite.name", "Bismuthinite");
        LR.addStringLocalization("tile.Ore.Garnierite.name", "Garnierite");
        LR.addStringLocalization("tile.Ore.Malachite.name", "Malachite");
        LR.addStringLocalization("tile.Ore.Magnetite.name", "Magnetite");
        LR.addStringLocalization("tile.Ore.Limonite.name", "Limonite");
        LR.addStringLocalization("tile.Ore.Sphalerite.name", "Sphalerite");
        LR.addStringLocalization("tile.Ore.Tetrahedrite.name", "Tetrahedrite");
        LR.addStringLocalization("tile.Ore.Bituminous Coal.name", "Bituminous Coal");
        LR.addStringLocalization("tile.Ore.Lignite.name", "Lignite");
        LR.addStringLocalization("tile.Ore.Kaolinite.name", "Kaolinite");
        LR.addStringLocalization("tile.Ore.Gypsum.name", "Gypsum");
        LR.addStringLocalization("tile.Ore.Satinspar.name", "Satinspar");
        LR.addStringLocalization("tile.Ore.Selenite.name", "Selenite");
        LR.addStringLocalization("tile.Ore.Graphite.name", "Graphite");
        LR.addStringLocalization("tile.Ore.Kimberlite.name", "Kimberlite");
        LR.addStringLocalization("tile.Ore.Petrified Wood.name", "Petrified Wood");
        LR.addStringLocalization("tile.Ore.Sulfur.name", "Sulfur");
        LR.addStringLocalization("tile.Ore.Jet.name", "Jet");
        LR.addStringLocalization("tile.Ore.Microcline.name", "Microcline");
        LR.addStringLocalization("tile.Ore.Pitchblende.name", "Pitchblende");
        LR.addStringLocalization("tile.Ore.Cinnabar.name", "Cinnabar");
        LR.addStringLocalization("tile.Ore.Cryolite.name", "Cryolite");
        LR.addStringLocalization("tile.Ore.Saltpeter.name", "Saltpeter");
        LR.addStringLocalization("tile.Ore.Serpentine.name", "Serpentine");
        LR.addStringLocalization("tile.Ore.Sylvite.name", "Sylvite");
        LR.addStringLocalization("tile.Ore.Borax.name", "Borax");
        LR.addStringLocalization("tile.Ore.Olivine.name", "Olivine");
        LR.addStringLocalization("tile.Ore.LapisLazuli.name", "Lapis Lazuli");

        LR.addStringLocalization("item.Ore.Native Copper.name", "Native Copper");
        LR.addStringLocalization("item.Ore.Native Gold.name", "Native Gold");
        LR.addStringLocalization("item.Ore.Native Platinum.name", "Native Platinum");
        LR.addStringLocalization("item.Ore.Hematite.name", "Hematite");
        LR.addStringLocalization("item.Ore.Native Silver.name", "Native Silver");
        LR.addStringLocalization("item.Ore.Cassiterite.name", "Cassiterite");
        LR.addStringLocalization("item.Ore.Galena.name", "Galena");
        LR.addStringLocalization("item.Ore.Bismuthinite.name", "Bismuthinite");
        LR.addStringLocalization("item.Ore.Garnierite.name", "Garnierite");
        LR.addStringLocalization("item.Ore.Malachite.name", "Malachite");
        LR.addStringLocalization("item.Ore.Magnetite.name", "Magnetite");
        LR.addStringLocalization("item.Ore.Limonite.name", "Limonite");
        LR.addStringLocalization("item.Ore.Sphalerite.name", "Sphalerite");
        LR.addStringLocalization("item.Ore.Tetrahedrite.name", "Tetrahedrite");
        LR.addStringLocalization("item.Ore.Bituminous Coal.name", "Bituminous Coal");
        LR.addStringLocalization("item.Ore.Lignite.name", "Lignite");
        LR.addStringLocalization("item.Ore.Kaolinite.name", "Kaolinite");
        LR.addStringLocalization("item.Ore.Gypsum.name", "Gypsum");
        LR.addStringLocalization("item.Ore.Satinspar.name", "Satinspar");
        LR.addStringLocalization("item.Ore.Selenite.name", "Selenite");
        LR.addStringLocalization("item.Ore.Graphite.name", "Graphite");
        LR.addStringLocalization("item.Ore.Kimberlite.name", "Kimberlite");
        LR.addStringLocalization("item.Ore.Petrified Wood.name", "Petrified Wood");
        LR.addStringLocalization("item.Ore.Sulfur.name", "Sulfur");
        LR.addStringLocalization("item.Ore.Jet.name", "Jet");
        LR.addStringLocalization("item.Ore.Microcline.name", "Microcline");
        LR.addStringLocalization("item.Ore.Pitchblende.name", "Pitchblende");
        LR.addStringLocalization("item.Ore.Cinnabar.name", "Cinnabar");
        LR.addStringLocalization("item.Ore.Cryolite.name", "Cryolite");
        LR.addStringLocalization("item.Ore.Saltpeter.name", "Saltpeter");
        LR.addStringLocalization("item.Ore.Serpentine.name", "Serpentine");
        LR.addStringLocalization("item.Ore.Sylvite.name", "Sylvite");
        LR.addStringLocalization("item.Ore.Borax.name", "Borax");
        LR.addStringLocalization("item.Ore.Olivine.name", "Olivine");
        LR.addStringLocalization("item.Ore.LapisLazuli.name", "Lapis Lazuli");
        LR.addStringLocalization("item.Ore.GalenaPartial.name", "Partially Smelted Galena");
        LR.addStringLocalization("item.Ore.TetrahedritePartial.name", "Partially Smelted Tetrahedrite");
        LR.addStringLocalization("item.Ore.MagnetitePartial.name", "Partially Smelted Magnetite");
        
        LR.addStringLocalization("item.Small Ore.Native Copper.name", "Native Copper Nugget");
        LR.addStringLocalization("item.Small Ore.Native Gold.name", "Native Gold Nugget");
        LR.addStringLocalization("item.Small Ore.Native Platinum.name", "Native Platinum Nugget");
        LR.addStringLocalization("item.Small Ore.Hematite.name", "Small Hematite");
        LR.addStringLocalization("item.Small Ore.Native Silver.name", "Native Silver Nugget");
        LR.addStringLocalization("item.Small Ore.Cassiterite.name", "Small Cassiterite");
        LR.addStringLocalization("item.Small Ore.Galena.name", "Small Galena");
        LR.addStringLocalization("item.Small Ore.Bismuthinite.name", "Small Bismuthinite");
        LR.addStringLocalization("item.Small Ore.Garnierite.name", "Small Garnierite");
        LR.addStringLocalization("item.Small Ore.Malachite.name", "Small Malachite");
        LR.addStringLocalization("item.Small Ore.Magnetite.name", "Small Magnetite");
        LR.addStringLocalization("item.Small Ore.Limonite.name", "Small Limonite");
        LR.addStringLocalization("item.Small Ore.Sphalerite.name", "Small Sphalerite");
        LR.addStringLocalization("item.Small Ore.Tetrahedrite.name", "Small Tetrahedrite");
        LR.addStringLocalization("item.Small Ore.GalenaPartial.name", "Small Partially Smelted Galena");
        LR.addStringLocalization("item.Small Ore.TetrahedritePartial.name", "Small Partially Smelted Tetrahedrite");
        LR.addStringLocalization("item.Small Ore.MagnetitePartial.name", "Small Partially Smelted Magnetite");
        
        //Cobble
        LR.addStringLocalization("tile.IgInRockCobble.Granite.name", "Granite Cobblestone");
        LR.addStringLocalization("tile.IgInRockCobble.Diorite.name", "Diorite Cobblestone");
        LR.addStringLocalization("tile.IgInRockCobble.Gabbro.name", "Gabbro Cobblestone");
        LR.addStringLocalization("tile.IgExRockCobble.Rhyolite.name", "Rhyolite Cobblestone");
        LR.addStringLocalization("tile.IgExRockCobble.Basalt.name", "Basalt Cobblestone");
        LR.addStringLocalization("tile.IgExRockCobble.Andesite.name", "Andesite Cobblestone");
        LR.addStringLocalization("tile.IgExRockCobble.Dacite.name", "Dacite Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Siltstone.name", "Siltstone Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Mudstone.name", "Mudstone Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Shale.name", "Shale Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Claystone.name", "Claystone Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Rock Salt.name", "Rock Salt Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Limestone.name", "Limestone Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Conglomerate.name", "Conglomerate Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Dolomite.name", "Dolomite Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Chert.name", "Chert Cobblestone");
        LR.addStringLocalization("tile.SedRockCobble.Chalk.name", "Chalk Cobblestone");
        LR.addStringLocalization("tile.MMRockCobble.Quartzite.name", "Quartzite Cobblestone");
        LR.addStringLocalization("tile.MMRockCobble.Slate.name", "Slate Cobblestone");
        LR.addStringLocalization("tile.MMRockCobble.Phyllite.name", "Phyllite Cobblestone");
        LR.addStringLocalization("tile.MMRockCobble.Schist.name", "Schist Cobblestone");
        LR.addStringLocalization("tile.MMRockCobble.Gneiss.name", "Gneiss Cobblestone");
        LR.addStringLocalization("tile.MMRockCobble.Marble.name", "Marble Cobblestone");
        //Smooth Stone
        LR.addStringLocalization("tile.IgInRockSmooth.Granite.name", "Smooth Granite");
        LR.addStringLocalization("tile.IgInRockSmooth.Diorite.name", "Smooth Diorite");
        LR.addStringLocalization("tile.IgInRockSmooth.Gabbro.name", "Smooth Gabbro");
        LR.addStringLocalization("tile.IgExRockSmooth.Rhyolite.name", "Smooth Rhyolite");
        LR.addStringLocalization("tile.IgExRockSmooth.Basalt.name", "Smooth Basalt");
        LR.addStringLocalization("tile.IgExRockSmooth.Andesite.name", "Smooth Andesite");
        LR.addStringLocalization("tile.IgExRockSmooth.Dacite.name", "Smooth Dacite");
        LR.addStringLocalization("tile.SedRockSmooth.Siltstone.name", "Smooth Siltstone");
        LR.addStringLocalization("tile.SedRockSmooth.Mudstone.name", "Smooth Mudstone");
        LR.addStringLocalization("tile.SedRockSmooth.Shale.name", "Smooth Shale");
        LR.addStringLocalization("tile.SedRockSmooth.Claystone.name", "Smooth Claystone");
        LR.addStringLocalization("tile.SedRockSmooth.Rock Salt.name", "Smooth Rock Salt");
        LR.addStringLocalization("tile.SedRockSmooth.Limestone.name", "Smooth Limestone");
        LR.addStringLocalization("tile.SedRockSmooth.Conglomerate.name", "Smooth Conglomerate");
        LR.addStringLocalization("tile.SedRockSmooth.Dolomite.name", "Smooth Dolomite");
        LR.addStringLocalization("tile.SedRockSmooth.Chert.name", "Smooth Chert");
        LR.addStringLocalization("tile.SedRockSmooth.Chalk.name", "Smooth Chalk");
        LR.addStringLocalization("tile.MMRockSmooth.Quartzite.name", "Smooth Quartzite");
        LR.addStringLocalization("tile.MMRockSmooth.Slate.name", "Smooth Slate");
        LR.addStringLocalization("tile.MMRockSmooth.Phyllite.name", "Smooth Phyllite");
        LR.addStringLocalization("tile.MMRockSmooth.Schist.name", "Smooth Schist");
        LR.addStringLocalization("tile.MMRockSmooth.Gneiss.name", "Smooth Gneiss");
        LR.addStringLocalization("tile.MMRockSmooth.Marble.name", "Smooth Marble");
        //Brick
        LR.addStringLocalization("tile.IgInRockBrick.Granite.name", "Granite Brick");
        LR.addStringLocalization("tile.IgInRockBrick.Diorite.name", "Diorite Brick");
        LR.addStringLocalization("tile.IgInRockBrick.Gabbro.name", "Gabbro Brick");
        LR.addStringLocalization("tile.IgExRockBrick.Rhyolite.name", "Rhyolite Brick");
        LR.addStringLocalization("tile.IgExRockBrick.Basalt.name", "Basalt Brick");
        LR.addStringLocalization("tile.IgExRockBrick.Andesite.name", "Andesite Brick");
        LR.addStringLocalization("tile.IgExRockBrick.Dacite.name", "Dacite Brick");
        LR.addStringLocalization("tile.SedRockBrick.Siltstone.name", "Siltstone Brick");
        LR.addStringLocalization("tile.SedRockBrick.Mudstone.name", "Mudstone Brick");
        LR.addStringLocalization("tile.SedRockBrick.Shale.name", "Shale Brick");
        LR.addStringLocalization("tile.SedRockBrick.Claystone.name", "Claystone Brick");
        LR.addStringLocalization("tile.SedRockBrick.Rock Salt.name", "Rock Salt Brick");
        LR.addStringLocalization("tile.SedRockBrick.Limestone.name", "Limestone Brick");
        LR.addStringLocalization("tile.SedRockBrick.Conglomerate.name", "Conglomerate Brick");
        LR.addStringLocalization("tile.SedRockBrick.Dolomite.name", "Dolomite Brick");
        LR.addStringLocalization("tile.SedRockBrick.Chert.name", "Chert Brick");
        LR.addStringLocalization("tile.SedRockBrick.Chalk.name", "Chalk Brick");
        LR.addStringLocalization("tile.MMRockBrick.Quartzite.name", "Quartzite Brick");
        LR.addStringLocalization("tile.MMRockBrick.Slate.name", "Slate Brick");
        LR.addStringLocalization("tile.MMRockBrick.Phyllite.name", "Phyllite Brick");
        LR.addStringLocalization("tile.MMRockBrick.Schist.name", "Schist Brick");
        LR.addStringLocalization("tile.MMRockBrick.Gneiss.name", "Gneiss Brick");
        LR.addStringLocalization("tile.MMRockBrick.Marble.name", "Marble Brick");
        //Cobble Stairs
        LR.addStringLocalization("tile.IgInRockCobbleStairs.Granite.name", "Granite Cobblestone Stairs");
        LR.addStringLocalization("tile.IgInRockCobbleStairs.Diorite.name", "Diorite Cobblestone Stairs");
        LR.addStringLocalization("tile.IgInRockCobbleStairs.Gabbro.name", "Gabbro Cobblestone Stairs");
        LR.addStringLocalization("tile.IgExRockCobbleStairs.Rhyolite.name", "Rhyolite Cobblestone Stairs");
        LR.addStringLocalization("tile.IgExRockCobbleStairs.Basalt.name", "Basalt Cobblestone Stairs");
        LR.addStringLocalization("tile.IgExRockCobbleStairs.Andesite.name", "Andesite Cobblestone Stairs");
        LR.addStringLocalization("tile.IgExRockCobbleStairs.Dacite.name", "Dacite Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Siltstone.name", "Siltstone Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Mudstone.name", "Mudstone Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Shale.name", "Shale Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Claystone.name", "Claystone Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Rock Salt.name", "Rock Salt Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Limestone.name", "Limestone Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Conglomerate.name", "Conglomerate Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Dolomite.name", "Dolomite Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Chert.name", "Chert Cobblestone Stairs");
        LR.addStringLocalization("tile.SedRockCobbleStairs.Chalk.name", "Chalk Cobblestone Stairs");
        LR.addStringLocalization("tile.MMRockCobbleStairs.Quartzite.name", "Quartzite Cobblestone Stairs");
        LR.addStringLocalization("tile.MMRockCobbleStairs.Slate.name", "Slate Cobblestone Stairs");
        LR.addStringLocalization("tile.MMRockCobbleStairs.Phyllite.name", "Phyllite Cobblestone Stairs");
        LR.addStringLocalization("tile.MMRockCobbleStairs.Schist.name", "Schist Cobblestone Stairs");
        LR.addStringLocalization("tile.MMRockCobbleStairs.Gneiss.name", "Gneiss Cobblestone Stairs");
        LR.addStringLocalization("tile.MMRockCobbleStairs.Marble.name", "Marble Cobblestone Stairs");

        //Dirt
        LR.addStringLocalization("tile.dirt.Granite.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Diorite.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Gabbro.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Rhyolite.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Basalt.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Andesite.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Dacite.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Siltstone.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Mudstone.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Shale.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Claystone.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Rock Salt.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Limestone.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Conglomerate.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Dolomite.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Chert.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Chalk.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Quartzite.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Slate.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Phyllite.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Schist.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Gneiss.name", "Dirt");
        LR.addStringLocalization("tile.dirt.Marble.name", "Dirt");
        //Clay
        LR.addStringLocalization("tile.clay.Granite.name", "Clay");
        LR.addStringLocalization("tile.clay.Diorite.name", "Clay");
        LR.addStringLocalization("tile.clay.Gabbro.name", "Clay");
        LR.addStringLocalization("tile.clay.Rhyolite.name", "Clay");
        LR.addStringLocalization("tile.clay.Basalt.name", "Clay");
        LR.addStringLocalization("tile.clay.Andesite.name", "Clay");
        LR.addStringLocalization("tile.clay.Dacite.name", "Clay");
        LR.addStringLocalization("tile.clay.Siltstone.name", "Clay");
        LR.addStringLocalization("tile.clay.Mudstone.name", "Clay");
        LR.addStringLocalization("tile.clay.Shale.name", "Clay");
        LR.addStringLocalization("tile.clay.Claystone.name", "Clay");
        LR.addStringLocalization("tile.clay.Rock Salt.name", "Clay");
        LR.addStringLocalization("tile.clay.Limestone.name", "Clay");
        LR.addStringLocalization("tile.clay.Conglomerate.name", "Clay");
        LR.addStringLocalization("tile.clay.Dolomite.name", "Clay");
        LR.addStringLocalization("tile.clay.Chert.name", "Clay");
        LR.addStringLocalization("tile.clay.Chalk.name", "Clay");
        LR.addStringLocalization("tile.clay.Quartzite.name", "Clay");
        LR.addStringLocalization("tile.clay.Slate.name", "Clay");
        LR.addStringLocalization("tile.clay.Phyllite.name", "Clay");
        LR.addStringLocalization("tile.clay.Schist.name", "Clay");
        LR.addStringLocalization("tile.clay.Gneiss.name", "Clay");
        LR.addStringLocalization("tile.clay.Marble.name", "Clay");
        //Grass
        LR.addStringLocalization("tile.Grass.Granite.name", "Grass");
        LR.addStringLocalization("tile.Grass.Diorite.name", "Grass");
        LR.addStringLocalization("tile.Grass.Gabbro.name", "Grass");
        LR.addStringLocalization("tile.Grass.Rhyolite.name", "Grass");
        LR.addStringLocalization("tile.Grass.Basalt.name", "Grass");
        LR.addStringLocalization("tile.Grass.Andesite.name", "Grass");
        LR.addStringLocalization("tile.Grass.Dacite.name", "Grass");
        LR.addStringLocalization("tile.Grass.Siltstone.name", "Grass");
        LR.addStringLocalization("tile.Grass.Mudstone.name", "Grass");
        LR.addStringLocalization("tile.Grass.Shale.name", "Grass");
        LR.addStringLocalization("tile.Grass.Claystone.name", "Grass");
        LR.addStringLocalization("tile.Grass.Rock Salt.name", "Grass");
        LR.addStringLocalization("tile.Grass.Limestone.name", "Grass");
        LR.addStringLocalization("tile.Grass.Conglomerate.name", "Grass");
        LR.addStringLocalization("tile.Grass.Dolomite.name", "Grass");
        LR.addStringLocalization("tile.Grass.Chert.name", "Grass");
        LR.addStringLocalization("tile.Grass.Chalk.name", "Grass");
        LR.addStringLocalization("tile.Grass.Quartzite.name", "Grass");
        LR.addStringLocalization("tile.Grass.Slate.name", "Grass");
        LR.addStringLocalization("tile.Grass.Phyllite.name", "Grass");
        LR.addStringLocalization("tile.Grass.Schist.name", "Grass");
        LR.addStringLocalization("tile.Grass.Gneiss.name", "Grass");
        LR.addStringLocalization("tile.Grass.Marble.name", "Grass");
        //clay grass
        LR.addStringLocalization("tile.ClayGrass.Granite.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Diorite.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Gabbro.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Rhyolite.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Basalt.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Andesite.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Dacite.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Siltstone.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Mudstone.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Shale.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Claystone.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Rock Salt.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Limestone.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Conglomerate.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Dolomite.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Chert.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Chalk.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Quartzite.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Slate.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Phyllite.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Schist.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Gneiss.name", "Clay Grass");
        LR.addStringLocalization("tile.ClayGrass.Marble.name", "Clay Grass");
        //peat
        LR.addStringLocalization("tile.peat.name", "Peat");
        LR.addStringLocalization("tile.PeatGrass.name", "Peat Grass");
        LR.addStringLocalization("item.SulfurPowder.name", "Sulfur Powder");
        LR.addStringLocalization("item.SaltpeterPowder.name", "Saltpeter");
        LR.addStringLocalization("item.flintPaxel.name", "Flint Tool");

        LR.addStringLocalization("item.IgIn Stone Pick.name", "Stone Pickaxe");
        LR.addStringLocalization("item.IgIn Stone Shovel.name", "Stone Shovel");
        LR.addStringLocalization("item.IgIn Stone Axe.name", "Stone Axe");
        LR.addStringLocalization("item.IgIn Stone Hoe.name", "Stone Hoe");
        LR.addStringLocalization("item.IgIn Stone Sword.name", "Stone Sword");
        LR.addStringLocalization("item.IgIn Stone Mace.name", "Stone Mace");
        LR.addStringLocalization("item.Sed Stone Pick.name", "Stone Pickaxe");
        LR.addStringLocalization("item.Sed Stone Shovel.name", "Stone Shovel");
        LR.addStringLocalization("item.Sed Stone Axe.name", "Stone Axe");
        LR.addStringLocalization("item.Sed Stone Hoe.name", "Stone Hoe");
        LR.addStringLocalization("item.Sed Stone Sword.name", "Stone Sword");
        LR.addStringLocalization("item.Sed Stone Mace.name", "Stone Mace");
        LR.addStringLocalization("item.IgEx Stone Pick.name", "Stone Pickaxe");
        LR.addStringLocalization("item.IgEx Stone Shovel.name", "Stone Shovel");
        LR.addStringLocalization("item.IgEx Stone Axe.name", "Stone Axe");
        LR.addStringLocalization("item.IgEx Stone Hoe.name", "Stone Hoe");
        LR.addStringLocalization("item.IgEx Stone Sword.name", "Stone Sword");
        LR.addStringLocalization("item.IgEx Stone Mace.name", "Stone Mace");
        LR.addStringLocalization("item.MM Stone Pick.name", "Stone Pickaxe");
        LR.addStringLocalization("item.MM Stone Shovel.name", "Stone Shovel");
        LR.addStringLocalization("item.MM Stone Axe.name", "Stone Axe");
        LR.addStringLocalization("item.MM Stone Hoe.name", "Stone Hoe");
        LR.addStringLocalization("item.MM Stone Sword.name", "Stone Sword");
        LR.addStringLocalization("item.MM Stone Mace.name", "Stone Mace");

        LR.addStringLocalization("item.Bone IgIn Stone Pick.name", "Stone Pickaxe");
        LR.addStringLocalization("item.Bone IgIn Stone Shovel.name", "Stone Shovel");
        LR.addStringLocalization("item.Bone IgIn Stone Axe.name", "Stone Axe");
        LR.addStringLocalization("item.Bone IgIn Stone Hoe.name", "Stone Hoe");

        LR.addStringLocalization("item.Bone Sed Stone Pick.name", "Stone Pickaxe");
        LR.addStringLocalization("item.Bone Sed Stone Shovel.name", "Stone Shovel");
        LR.addStringLocalization("item.Bone Sed Stone Axe.name", "Stone Axe");
        LR.addStringLocalization("item.Bone Sed Stone Hoe.name", "Stone Hoe");

        LR.addStringLocalization("item.Bone IgEx Stone Pick.name", "Stone Pickaxe");
        LR.addStringLocalization("item.Bone IgEx Stone Shovel.name", "Stone Shovel");
        LR.addStringLocalization("item.Bone IgEx Stone Axe.name", "Stone Axe");
        LR.addStringLocalization("item.Bone IgEx Stone Hoe.name", "Stone Hoe");

        LR.addStringLocalization("item.Bone MM Stone Pick.name", "Stone Pickaxe");
        LR.addStringLocalization("item.Bone MM Stone Shovel.name", "Stone Shovel");
        LR.addStringLocalization("item.Bone MM Stone Axe.name", "Stone Axe");
        LR.addStringLocalization("item.Bone MM Stone Hoe.name", "Stone Hoe");

        LR.addStringLocalization("item.Stone Hammer.name", "Stone Hammer");
    }
    
    private void RegisterWood()
    {
    	LanguageRegistry LR = LanguageRegistry.instance();
        String[] WoodNames = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
                "Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};
        for(int i= 0; i < WoodNames.length; i++)
        {
            LR.addStringLocalization("tile.log."+WoodNames[i]+".name", WoodNames[i]);
            LR.addStringLocalization("tile.leaves."+WoodNames[i]+".name", WoodNames[i] + " Leaves");
            LR.addStringLocalization("tile.sapling."+WoodNames[i]+".name", WoodNames[i] + " Sapling");
            LR.addStringLocalization("tile.wood."+WoodNames[i]+".name", WoodNames[i] + " Planks");
            LR.addStringLocalization("item.terraWoodSupportItemV."+WoodNames[i]+".name","V. " + WoodNames[i] + " Support Beam");
            LR.addStringLocalization("item.terraWoodSupportItemH."+WoodNames[i]+".name","H. " + WoodNames[i] + " Support Beam");
            LR.addStringLocalization("item.Log."+WoodNames[i]+".name", WoodNames[i]);
            LR.addStringLocalization("item.SinglePlank."+WoodNames[i]+".name", WoodNames[i] + " Plank");
        }
        LR.addStringLocalization("item.Stick.name", "Stick");
    }
    
    private void RegisterMetal()
    {
    	LanguageRegistry LR = LanguageRegistry.instance();
        String[] ToolNames = {"Stone","Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel", "Bronze", "Copper"
                ,"Wrought Iron","Red Steel","Rose Gold", "Steel", "Tin", "Zinc"};
        
        String[] Names = {"Bismuth","Bismuth Bronze","Black Bronze","Black Steel","Blue Steel","Brass","Bronze","Copper","Gold"
                ,"Wrought Iron","Lead","Nickel","Pig Iron","Platinum","Red Steel","Rose Gold","Silver", "Steel", "Sterling Silver",
                "Tin", "Zinc"};
        
        LR.addStringLocalization("item.StoneProPick.name", "Stone Prospector's Pick");
        
        LR.addStringLocalization("tile.terraAnvil.name", "Anvil");
        LR.addStringLocalization("item.terraStoneAnvilItem.name", "Stone Anvil");
        LR.addStringLocalization("item.terraCopperAnvilItem.name", "Copper Anvil");
        LR.addStringLocalization("item.terraBronzeAnvilItem.name", "Bronze Anvil");
        LR.addStringLocalization("item.terraWroughtIronAnvilItem.name", "Wrought Iron Anvil");
        LR.addStringLocalization("item.terraSteelAnvilItem.name", "Steel Anvil");
        LR.addStringLocalization("item.terraBlackSteelAnvilItem.name", "Black Steel  Anvil");
        LR.addStringLocalization("item.terraBlueSteelAnvilItem.name", "Blue Steel Anvil");
        LR.addStringLocalization("item.terraRedSteelAnvilItem.name", "Red Steel Anvil");
        LR.addStringLocalization("item.terraBismuthBronzeAnvilItem.name", "Bismuth Bronze Anvil");
        LR.addStringLocalization("item.terraBlackBronzeAnvilItem.name", "Black Bronze Anvil");
        LR.addStringLocalization("item.terraRoseGoldAnvilItem.name", "Rose Gold Anvil");
        
        for(int i= 0; i < ToolNames.length; i++)
        {
            LR.addStringLocalization("item."+ToolNames[i]+" Pick.name", ToolNames[i] + " Pickaxe");
            LR.addStringLocalization("item."+ToolNames[i]+" Shovel.name", ToolNames[i] + " Shovel");
            LR.addStringLocalization("item."+ToolNames[i]+" Axe.name", ToolNames[i] + " Axe");
            LR.addStringLocalization("item."+ToolNames[i]+" Hoe.name", ToolNames[i] + " Hoe");
            LR.addStringLocalization("item."+ToolNames[i]+" Hammer.name", ToolNames[i] + " Hammer");
            LR.addStringLocalization("item."+ToolNames[i]+" Sword.name", ToolNames[i] + " Sword");
            LR.addStringLocalization("item."+ToolNames[i]+" Mace.name", ToolNames[i] + " Mace");
            LR.addStringLocalization("item."+ToolNames[i]+" Chisel.name", ToolNames[i] + " Chisel");
            LR.addStringLocalization("item."+ToolNames[i]+" Saw.name", ToolNames[i] + " Saw");
            LR.addStringLocalization("item."+ToolNames[i]+" Scythe.name", ToolNames[i] + " Scythe");
            LR.addStringLocalization("item."+ToolNames[i]+" Knife.name", ToolNames[i] + " Knife");
            LR.addStringLocalization("item."+ToolNames[i]+" ProPick.name", ToolNames[i] + " Prospector's Pick");
            LR.addStringLocalization("item."+ToolNames[i]+" Pickaxe Head.name", ToolNames[i] + " Pickaxe Head");
            LR.addStringLocalization("item."+ToolNames[i]+" Shovel Head.name", ToolNames[i] + " Shovel Head");
            LR.addStringLocalization("item."+ToolNames[i]+" Axe Head.name", ToolNames[i] + " Axe Head");
            LR.addStringLocalization("item."+ToolNames[i]+" Hoe Head.name", ToolNames[i] + " Hoe Head");
            LR.addStringLocalization("item."+ToolNames[i]+" Hammer Head.name", ToolNames[i] + " Hammer Head");
            LR.addStringLocalization("item."+ToolNames[i]+" Sword Blade.name", ToolNames[i] + " Sword Blade");
            LR.addStringLocalization("item."+ToolNames[i]+" Mace Head.name", ToolNames[i] + " Mace Head");
            LR.addStringLocalization("item."+ToolNames[i]+" Chisel Head.name", ToolNames[i] + " Chisel Head");
            LR.addStringLocalization("item."+ToolNames[i]+" Saw Blade.name", ToolNames[i] + " Saw Blade");
            LR.addStringLocalization("item."+ToolNames[i]+" ProPick Head.name", ToolNames[i] + " Prospector's Pick Head");
            LR.addStringLocalization("item."+ToolNames[i]+" Scythe Blade.name", ToolNames[i] + " Scythe Blade");
            LR.addStringLocalization("item."+ToolNames[i]+" Knife Blade.name", ToolNames[i] + " Knife Blade");
            /**
             * Armor Related
             * */
            LR.addStringLocalization("item."+ToolNames[i]+"Sheet.name", ToolNames[i] + " Sheet");
            LR.addStringLocalization("item."+ToolNames[i]+"Sheet2x.name", ToolNames[i] + " Sheet 2x");
            LR.addStringLocalization("item."+ToolNames[i]+"UnfinishedHelmet.name", "Unfinished " + ToolNames[i] + " Helmet Stage 1");
            LR.addStringLocalization("item."+ToolNames[i]+"UnfinishedHelmet2.name", "Unfinished " + ToolNames[i] + " Helmet Stage 2");
            LR.addStringLocalization("item."+ToolNames[i]+"Helmet.name", ToolNames[i] + " Helmet");
            LR.addStringLocalization("item."+ToolNames[i]+"UnfinishedChestplate.name", "Unfinished " + ToolNames[i] + " Chestplate Stage 1");
            LR.addStringLocalization("item."+ToolNames[i]+"UnfinishedChestplate2.name", "Unfinished " + ToolNames[i] + " Chestplate Stage 2");
            LR.addStringLocalization("item."+ToolNames[i]+"Chestplate.name", ToolNames[i] + " Chestplate");
            LR.addStringLocalization("item."+ToolNames[i]+"UnfinishedGreaves.name", "Unfinished " + ToolNames[i] + " Greaves Stage 1");
            LR.addStringLocalization("item."+ToolNames[i]+"UnfinishedGreaves2.name", "Unfinished " + ToolNames[i] + " Greaves Stage 2");
            LR.addStringLocalization("item."+ToolNames[i]+"Greaves.name", ToolNames[i] + " Greaves");
            LR.addStringLocalization("item."+ToolNames[i]+"UnfinishedBoots.name", "Unfinished " + ToolNames[i] + " Boots Stage 1");
            LR.addStringLocalization("item."+ToolNames[i]+"UnfinishedBoots2.name", "Unfinished " + ToolNames[i] + " Boots Stage 2");
            LR.addStringLocalization("item."+ToolNames[i]+"Boots.name", ToolNames[i] + " Boots");
        }

        //meltedmetal
        for(int i= 0; i < Names.length; i++)
        {
            LR.addStringLocalization("item.Unshaped"+Names[i].replace(" ", "")+".name", "Unshaped "+Names[i]);
            LR.addStringLocalization("item.terra"+Names[i].replace(" ", "")+"Ingot.name", Names[i] + " Ingot");
            LR.addStringLocalization("item.terra"+Names[i].replace(" ", "")+"Ingot2x.name", Names[i] + " Ingot 2x");
        }

        LR.addStringLocalization("item.UnshapedHCBlackSteel.name", "Unshaped High Carbon Black Steel");
        LR.addStringLocalization("item.UnshapedHCBlueSteel.name", "Unshaped High Carbon Blue Steel");
        LR.addStringLocalization("item.UnshapedHCRedSteel.name", "Unshaped High Carbon Red Steel");
        LR.addStringLocalization("item.UnshapedHCSteel.name", "Unshaped High Carbon Steel");
        LR.addStringLocalization("item.UnshapedWeakSteel.name", "Unshaped Weak Steel");
        LR.addStringLocalization("item.UnshapedWeakBlueSteel.name", "Unshaped Weak Blue Steel");
        LR.addStringLocalization("item.UnshapedWeakRedSteel.name", "Unshaped Weak Red Steel");

        LR.addStringLocalization("item.terraHCBlackSteelIngot.name", "High Carbon Black Steel Ingot");
        LR.addStringLocalization("item.terraHCBlueSteelIngot.name", "High Carbon Blue Steel Ingot");
        LR.addStringLocalization("item.terraHCRedSteelIngot.name", "High Carbon Red Steel Ingot");
        LR.addStringLocalization("item.terraHCSteelIngot.name", "High Carbon Steel Ingot");
        LR.addStringLocalization("item.terraWeakSteelIngot.name", "Weak Steel Ingot");
        LR.addStringLocalization("item.terraWeakBlueSteelIngot.name", "Weak Blue Steel Ingot");
        LR.addStringLocalization("item.terraWeakRedSteelIngot.name", "Weak Red Steel Ingot");
    }

    public boolean getGraphicsLevel()
    {
        // TODO Auto-generated method stub
        return ModLoader.getMinecraftInstance().isFancyGraphicsEnabled();
    }


}
