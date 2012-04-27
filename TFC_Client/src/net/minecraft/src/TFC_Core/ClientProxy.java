package net.minecraft.src.TFC_Core;

import java.io.File;
import java.util.Map;

import net.minecraft.client.Minecraft;
import net.minecraft.src.AxisAlignedBB;
import net.minecraft.src.BaseMod;
import net.minecraft.src.BiomeGenBase;
import net.minecraft.src.Block;
import net.minecraft.src.ChestItemRenderHelper;
import net.minecraft.src.EntityItem;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.IBlockAccess;
import net.minecraft.src.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.RenderBlocks;
import net.minecraft.src.TileEntity;
import net.minecraft.src.World;
import net.minecraft.src.mod_TFC_Core;
import net.minecraft.src.TFC_Game.*;
import net.minecraft.src.TFC_Mining.GuiTerraSluice;
import net.minecraft.src.TFC_Mining.TileEntityTerraSluice;
import net.minecraft.src.forge.MinecraftForgeClient;

public class ClientProxy implements IProxy {
    @Override
    public void registerRenderInformation() 
    {
        MinecraftForgeClient.preloadTexture("/cpw/mods/ironchest/sprites/block_textures.png");
        MinecraftForgeClient.preloadTexture("/cpw/mods/ironchest/sprites/item_textures.png");
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
    }

    @Override
    public void registerTranslations() {
        //    for (IronChestType typ : IronChestType.values()) {
        //      ModLoader.addLocalization(typ.name() + ".name", typ.friendlyName);
        //    }
        //    for (ChestChangerType typ : ChestChangerType.values()) {
        //      ModLoader.addLocalization("item." + typ.itemName + ".name", typ.descriptiveName);
        //    }
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
        TileEntity te = world.getBlockTileEntity(x, y, z);
        
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

        }

        return null;

    }

    @Override
    public boolean onTickInGame(float var1, Object mc)
    {

        ItemStack[] inv = ((Minecraft)mc).thePlayer.inventory.mainInventory;
        double xCoord = ((Minecraft)mc).thePlayer.posX;
        double yCoord = ((Minecraft)mc).thePlayer.posY;
        double zCoord = ((Minecraft)mc).thePlayer.posZ;

        TFCHeat.HandleContainerHeat(((Minecraft)mc).theWorld, inv, (int)xCoord,(int)yCoord,(int)zCoord);
        return true;
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

    }

    @Override
    public boolean renderWorldBlock(Object renderblocks,
            IBlockAccess iblockaccess, int i, int j, int k, Block block, int l) 
    {
        if (l == mod_TFC_Core.sulfurRenderId)
        {
            return TFC_CoreRender.RenderSulfur(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.moltenRenderId)
        {
            return TFC_CoreRender.RenderMolten(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.woodSupportRenderIdH)
        {
            return TFC_CoreRender.RenderWoodSupportBeamH(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.woodSupportRenderIdV)
        {
            return TFC_CoreRender.RenderWoodSupportBeamV(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.grassRenderId)
        {
            int var5 = block.colorMultiplier(iblockaccess, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return TFC_CoreRender.RenderGrass(block, i, j, k, var6, var7, var8, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.oreRenderId)
        {
            int var5 = block.colorMultiplier(iblockaccess, i, j, k);
            float var6 = (float)(var5 >> 16 & 255) / 255.0F;
            float var7 = (float)(var5 >> 8 & 255) / 255.0F;
            float var8 = (float)(var5 & 255) / 255.0F;
            return TFC_CoreRender.RenderOre(block, i, j, k,  var6, var7, var8, (RenderBlocks)renderblocks, iblockaccess);
        }
        if (l == mod_TFC_Core.looseRockRenderId)
        {
            return TFC_CoreRender.RenderLooseRock(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.snowRenderId)
        {
            return TFC_CoreRender.RenderSnow(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.terraFirepitRenderId)
        {
            return TFC_CoreRender.renderFirepit(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.terraForgeRenderId)
        {
            return TFC_CoreRender.renderForge(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.terraBellowsRenderId)
        {
            return TFC_CoreRender.renderBellows(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.terraAnvilRenderId)
        {
            return TFC_CoreRender.renderAnvil(block, i, j, k, (RenderBlocks)renderblocks);
        }
        if (l == mod_TFC_Core.sluiceRenderId)
        {
            return TFC_CoreRender.RenderSluice(block, i, j, k, (RenderBlocks)renderblocks);
        }


        return false;
    }

    public int blockColorMultiplier(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
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
}
