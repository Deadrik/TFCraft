package com.bioxx.tfc.Items.ItemBlocks;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.api.Constant.Global;
import com.bioxx.tfc.api.Enums.EnumWeight;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IIcon;

public class ItemFruitTreeSapling extends ItemTerraBlock
{
    public ItemFruitTreeSapling(Block b)
    {
        super(b);
        this.metaNames = Global.FRUIT_META_NAMES;
        this.icons = new IIcon[metaNames.length];
    }

    @Override
    public IIcon getIconFromDamage(int index)
    {
        return icons[index];
    }

    @Override
    public void registerIcons(IIconRegister registerer)
    {
        for(int i = 0; i < this.metaNames.length; i++)
            icons[i] = registerer.registerIcon(Reference.MOD_ID + ":" + "wood/fruit trees/" + this.metaNames[i] + " Sapling");
    }

    @Override
    public EnumWeight getWeight(ItemStack is)
    {
        return EnumWeight.MEDIUM;
    }
}