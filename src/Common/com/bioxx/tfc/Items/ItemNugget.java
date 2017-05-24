package com.bioxx.tfc.Items;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

import java.util.List;

public class ItemNugget extends ItemMetal
{
    private short metalAmount;

    public ItemNugget()
    {
        super();
        this.setWeight(EnumWeight.MEDIUM);
        this.setSize(EnumSize.TINY);
        metalAmount = 10;

    }

    @Override
    public void getSubItems(Item item, CreativeTabs tabs, List list) {
        for (int i = 0; i < metaNames.length; i++)
            list.add(new ItemStack(this, 1, i));
    }

    @Override
    public void registerIcons(IIconRegister registerer)
    {
        metaIcons = new IIcon[metaNames.length];
        for(int i = 0; i < metaNames.length; i++)
        {
            metaIcons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + textureFolder + metaNames[i] + " Nugget");
        }
    }

    @Override
    public short getMetalReturnAmount(ItemStack is) { return metalAmount; }
}