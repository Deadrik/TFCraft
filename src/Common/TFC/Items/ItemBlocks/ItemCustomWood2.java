package TFC.Items.ItemBlocks;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.item.ItemStack;
import TFC.API.Constant.Global;

public class ItemCustomWood2 extends ItemTerraBlock
{
	public ItemCustomWood2(Block par1)
	{
		super(par1);
		setMaxDamage(0);
		setHasSubtypes(true);
		setFolder("wood/trees");
	}
	@Override
	public String getItemStackDisplayName(ItemStack itemstack) 
	{
		String s = new StringBuilder().append(super.getItemStackDisplayName(itemstack)).append(".").append(Global.WOOD_ALL[itemstack.getItemDamage()]).toString();
		return s;
	}

	@Override
	public int getMetadata(int i)
	{
		return i;
	}

	@Override
	public void registerIcons(IIconRegister registerer)
    {

    }
}
