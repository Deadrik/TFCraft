package net.minecraft.src.TFC_Core.Items;

import net.minecraft.src.*;
import net.minecraft.src.forge.ITextureProvider;

public class ItemStick extends ItemTerra implements ITextureProvider
{
    String[] Names = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
            "Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};

    public ItemStick(int i)
    {
        super(i);
        setMaxDamage(0);
        setHasSubtypes(true);
    }

    public int getIconFromDamage(int i)
    {
        return 16+i;
    }

//    @Override
//    public String getItemNameIS(ItemStack itemstack) 
//    {
//        String s = new StringBuilder().append(super.getItemName()).append(".").append(Names[itemstack.getItemDamage()]).toString();
//        return s;
//    }

    public int getMetadata(int i)
    {
        return i;
    }

    @Override
    public String getTextureFile()
    {
        return "/bioxx/terrasprites.png";
    }
    
//    public void addCreativeItems(java.util.ArrayList list)
//    {
//        for(int i = 0; i < 16; i++)
//            list.add(new ItemStack(this,1,i));
//    }
}
