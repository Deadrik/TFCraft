package com.bioxx.tfc.GUI;

import com.bioxx.tfc.Containers.ContainerCoalPile;
import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TileEntities.TECoalPile;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class GuiCoalPile extends GuiContainerTFC {
    public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_coalpile.png");

    public GuiCoalPile(InventoryPlayer inventoryplayer, TECoalPile te, World world, int x, int y, int z) {
        super(new ContainerCoalPile(inventoryplayer, te, world, x, y, z), 176, 85);
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
        this.drawGui(texture);
    }
}