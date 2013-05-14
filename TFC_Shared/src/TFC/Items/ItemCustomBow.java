package TFC.Items;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBow;
import net.minecraft.util.Icon;
import TFC.Core.TFCTabs;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemCustomBow extends ItemBow implements ISize
{
	
	public static final String[] bowPullIconNameArray = new String[] {"bow_pull_0", "bow_pull_1", "bow_pull_2"};
    @SideOnly(Side.CLIENT)
    private Icon[] iconArray;
    
    public ItemCustomBow(int par1)
    {
        super(par1);
        this.maxStackSize = 1;
        this.setMaxDamage(384);
        setCreativeTab(TFCTabs.TFCTools);
    }

    @Override
	@SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        super.registerIcons(par1IconRegister);
        this.iconArray = new Icon[bowPullIconNameArray.length];

        for (int i = 0; i < this.iconArray.length; ++i)
        {
            this.iconArray[i] = par1IconRegister.registerIcon(bowPullIconNameArray[i]);
        }
        Item.bow.registerIcons(par1IconRegister);
    }

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.LARGE;
	}


	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.LIGHT;
	}


	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}
}
