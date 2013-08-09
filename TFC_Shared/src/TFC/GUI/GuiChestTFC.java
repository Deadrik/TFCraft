package TFC.GUI;

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
import org.lwjgl.opengl.GL11;
import TFC.*;
import TFC.Containers.*;
import TFC.TileEntities.*;

public class GuiChestTFC extends GuiContainer
{
    private IInventory upperChestInventory;
    private IInventory lowerChestInventory;

    /**
     * window height is calculated with this values, the more rows, the heigher
     */
    private int inventoryRows = 0;

    public GuiChestTFC(IInventory par1IInventory, IInventory chestInv, World world, int x, int y, int z)
    {
        super(new ContainerChestTFC(par1IInventory, chestInv, world, x, y, z));
        
        TileEntityChestTFC chest = (TileEntityChestTFC)chestInv;
        
        this.upperChestInventory = par1IInventory;
        this.lowerChestInventory = chestInv;
        
        if (chest.adjacentChestXNeg != null)
        {
        	lowerChestInventory = new InventoryLargeChest("Large chest", chest.adjacentChestXNeg, chestInv);
        }

        if (chest.adjacentChestXPos != null)
        {
        	lowerChestInventory = new InventoryLargeChest("Large chest", chestInv, chest.adjacentChestXPos);
        }

        if (chest.adjacentChestZNeg != null)
        {
        	lowerChestInventory = new InventoryLargeChest("Large chest", chest.adjacentChestZNeg, chestInv);
        }

        if (chest.adjacentChestZPosition != null)
        {
        	lowerChestInventory = new InventoryLargeChest("Large chest", chestInv, chest.adjacentChestZPosition);
        }
        
        this.allowUserInput = false;
        short var3 = 222;
        int var4 = var3 - 108;
        this.inventoryRows = lowerChestInventory.getSizeInventory() / 9;
        this.ySize = var4 + this.inventoryRows * 18;
    }

    /**
     * Draw the foreground layer for the GuiContainer (everythin in front of the items)
     */
    protected void drawGuiContainerForegroundLayer()
    {
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.lowerChestInventory.getInvName()), 8, 6, 4210752);
        this.fontRenderer.drawString(StatCollector.translateToLocal(this.upperChestInventory.getInvName()), 8, this.ySize - 96 + 2, 4210752);
    }

    /**
     * Draw the background layer for the GuiContainer (everything behind the items)
     */
    protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
    {
    	this.mc.func_110434_K().func_110577_a(new ResourceLocation("textures/gui/container/generic_54.png"));
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        int var5 = (this.width - this.xSize) / 2;
        int var6 = (this.height - this.ySize) / 2;
        this.drawTexturedModalRect(var5, var6, 0, 0, this.xSize, this.inventoryRows * 18 + 17);
        this.drawTexturedModalRect(var5, var6 + this.inventoryRows * 18 + 17, 0, 126, this.xSize, 96);
    }
}
