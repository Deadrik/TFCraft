package TFC.Items;

import java.util.List;

import TFC.Core.HeatIndex;
import TFC.Core.HeatManager;
import TFC.Core.TFC_ItemHeat;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

import net.minecraft.src.CreativeTabs;
import net.minecraft.src.Enchantment;
import net.minecraft.src.Entity;
import net.minecraft.src.EntityPlayer;
import net.minecraft.src.Item;
import net.minecraft.src.ItemFood;
import net.minecraft.src.ItemStack;
import net.minecraft.src.NBTTagCompound;
import net.minecraft.src.NBTTagList;
import net.minecraft.src.World;

public class ItemPlank extends ItemTerra
{
    String[] Names = {"Oak","Aspen","Birch","Chestnut","Douglas Fir","Hickory","Maple","Ash","Pine",
            "Sequoia","Spruce","Sycamore","White Cedar","White Elm","Willow","Kapok"};

    public ItemPlank(int id, String tex) 
    {
        super(id);
        texture = tex;
        this.hasSubtypes = true;
        this.setMaxDamage(0);
    }
    
    @Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}
    
    @Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}
    
    @Override
    public String getItemNameIS(ItemStack itemstack) 
    {
        String s = new StringBuilder().append(super.getItemName()).append(".").append(Names[itemstack.getItemDamage()]).toString();
        return s;
    }
    @Override
    public int getMetadata(int i) 
    {       
        return i;
    }
    
    public int getIconFromDamage(int par1)
    {        
        return this.iconIndex+par1;
    }
    
    @Override
    public void getSubItems(int par1, CreativeTabs par2CreativeTabs, List list)
    {
        for(int i = 0; i < Names.length; i++) {
            list.add(new ItemStack(this,1,i));
        }
    }
}
