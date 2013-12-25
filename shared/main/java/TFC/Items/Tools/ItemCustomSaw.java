package TFC.Items.Tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import TFC.API.ISize;
import TFC.API.TFCOptions;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Items.ItemTerra;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;

public class ItemCustomSaw extends ItemCustomAxe implements ISize
{
	public ItemCustomSaw(int i, EnumToolMaterial e)
	{
		super(i, e, 0);
		this.setMaxDamage((int)(e.getMaxUses()*0.85));
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial()*1.35F;
	}
	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		return par2Block != null && par2Block.blockMaterial == Material.wood ? this.efficiencyOnProperMaterial*1.35F : super.getStrVsBlock(par1ItemStack, par2Block);
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
	{
		ItemTerra.addSizeInformation(this, arraylist);

		if(TFCOptions.enableDebugMode) {
			arraylist.add("Damage: "+is.getItemDamage());
		}
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack()) {
			return this.getSize().stackSize * getWeight().multiplier;
		} else {
			return 1;
		}
	}

	@Override
	public EnumSize getSize() {
		return EnumSize.MEDIUM;
	}

	@Override
	public boolean canStack() 
	{
		return false;
	}

	@Override
	public EnumWeight getWeight() {
		// TODO Auto-generated method stub
		return EnumWeight.MEDIUM;
	}

	@Override
	public Multimap getItemAttributeModifiers()
	{
		Multimap multimap = HashMultimap.create();
		return multimap;
	}
}