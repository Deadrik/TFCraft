package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Items.ItemTerra;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Interfaces.ISmeltable;
import com.bioxx.tfc.api.TFCItems;
import com.bioxx.tfc.api.TFC_ItemHeat;
import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import java.util.List;

public abstract class ItemMetalBlock extends ItemTerraBlock implements ISmeltable
{
    private boolean smeltable = true;
    protected short metalAmount;

    public ItemMetalBlock(Block b)
    {
        super(b);
        metalAmount = 800;
    }

    @Override
    public EnumSize getSize(ItemStack is) {
        return EnumSize.LARGE;
    }

    @Override
    public short getMetalReturnAmount(ItemStack is) {

        return metalAmount;
    }

    @Override
    public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
    {
        ItemTerra.addSizeInformation(is, arraylist);
        addExtraInformation(is, player, arraylist);

        if (is.hasTagCompound())
        {
            if(TFC_ItemHeat.hasTemp(is))
            {
                float temp = TFC_ItemHeat.getTemp(is);
                float meltTemp = TFC_ItemHeat.isCookable(is);

                if(meltTemp != -1)
                {
                    if(is.getItem() == TFCItems.stick)
                        arraylist.add(TFC_ItemHeat.getHeatColorTorch(temp, meltTemp));
                    else
                        arraylist.add(TFC_ItemHeat.getHeatColor(temp, meltTemp));
                }
            }
        }
    }

    public void addExtraInformation(ItemStack is, EntityPlayer player, List<String> arraylist)
    {
        if(getMetalType(is) != null)
        {
            if (TFC_Core.showShiftInformation())
            {
                arraylist.add(TFC_Core.translate("gui.units") + ": " + getMetalReturnAmount(is));
            }
            else
            {
                arraylist.add(TFC_Core.translate("gui.ShowHelp"));
            }
        }
    }

    @Override
    public boolean isSmeltable(ItemStack is) {
        // TODO Auto-generated method stub
        return smeltable;
    }

    @Override
    public EnumTier getSmeltTier(ItemStack is) {
        // TODO Auto-generated method stub
        return EnumTier.TierI;
    }
}
