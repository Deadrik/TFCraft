package TFC.Items.Tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemTool;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Settings;
import TFC.Core.Util.StringUtil;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import TFC.Items.ISize;
import TFC.Items.ItemTerra;

public class ItemTerraTool extends ItemTool implements ISize
{

	protected ItemTerraTool(int par1, int par2,
			EnumToolMaterial par3EnumToolMaterial, Block[] par4ArrayOfBlock) 
	{
		super(par1, par2, par3EnumToolMaterial, par4ArrayOfBlock);
		this.setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
    {
		Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
		ItemTerra.addSizeInformation(this, arraylist);
		
        if(TFC_Settings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }
	
	@Override
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier;
    	else
    		return 1;
    }
	
	@Override
	public void updateIcons(IconRegister registerer)
    {
		this.itemIcon = registerer.registerIcon("tools/"+this.getUnlocalizedName().replace("item.", ""));
    }
	
	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.LARGE;
	}

	@Override
	public boolean canStack() {
		// TODO Auto-generated method stub
		return false;
	}
	
	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}
}
