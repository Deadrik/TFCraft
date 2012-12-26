package TFC.Containers;

import java.util.ArrayList;
import TFC.*;
import TFC.Enums.EnumSize;
import TFC.TileEntities.TileEntityChestTFC;
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

public class ContainerChestTFC extends ContainerTFC
{
    private IInventory lowerChestInventory;
    private int numRows;

    public ContainerChestTFC(IInventory playerInv, IInventory chestInv, World world, int x, int y, int z)
    {
    	TileEntityChestTFC chest = (TileEntityChestTFC)chestInv;
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
        
        this.numRows = lowerChestInventory.getSizeInventory() / 9;
        lowerChestInventory.openChest();
        int var3 = (this.numRows - 4) * 18;
        int var4;
        int var5;
        
        ArrayList exceptions = new ArrayList<Item>();
        exceptions.add(TFCItems.Logs);

        for (var4 = 0; var4 < this.numRows; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new SlotSize(lowerChestInventory, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18).setSize(EnumSize.MEDIUM).addItemException(exceptions));
            }
        }

        for (var4 = 0; var4 < 3; ++var4)
        {
            for (var5 = 0; var5 < 9; ++var5)
            {
                this.addSlotToContainer(new Slot(playerInv, var5 + var4 * 9 + 9, 8 + var5 * 18, 103 + var4 * 18 + var3));
            }
        }

        for (var4 = 0; var4 < 9; ++var4)
        {
            this.addSlotToContainer(new Slot(playerInv, var4, 8 + var4 * 18, 161 + var3));
        }
    }
    
    @Override
    public boolean canInteractWith(EntityPlayer par1EntityPlayer)
    {
        return this.lowerChestInventory.isUseableByPlayer(par1EntityPlayer);
    }

    /**
     * Called to transfer a stack from one inventory to the other eg. when shift clicking.
     */
    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int par1)
    {
        ItemStack var2 = null;
        Slot var3 = (Slot)this.inventorySlots.get(par1);

        if (var3 != null && var3.getHasStack())
        {
            ItemStack var4 = var3.getStack();
            var2 = var4.copy();

            if (par1 < this.numRows * 9)
            {
                if (!this.mergeItemStack(var4, this.numRows * 9, this.inventorySlots.size(), true))
                {
                    return null;
                }
            }
            else if (!this.mergeItemStack(var4, 0, this.numRows * 9, false))
            {
                return null;
            }

            if (var4.stackSize == 0)
            {
                var3.putStack((ItemStack)null);
            }
            else
            {
                var3.onSlotChanged();
            }
        }

        return var2;
    }

    /**
     * Callback for when the crafting gui is closed.
     */
    @Override
    public void onCraftGuiClosed(EntityPlayer par1EntityPlayer)
    {
        super.onCraftGuiClosed(par1EntityPlayer);
        this.lowerChestInventory.closeChest();
    }
}
