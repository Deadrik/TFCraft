package TFC.Items.Tools;

import java.util.Set;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import TFC.API.Enums.EnumItemReach;
import TFC.Core.TFCTabs;

import com.google.common.collect.Sets;

public class ItemCustomScythe extends ItemTerraTool
{
	private static final Set blocks = Sets.newHashSet( new Block[] {Blocks.leaves});

	public ItemCustomScythe(ToolMaterial e)
	{
		super(e.getDamageVsEntity(),e, blocks);
		this.setMaxDamage(e.getMaxUses()*3);
//		this.damageVsEntity = e.getDamageVsEntity();
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial();
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLivingBase par2EntityLivingBase, EntityLivingBase par3EntityLivingBase)
	{
		par1ItemStack.damageItem(1, par3EntityLivingBase);
		return true;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.FAR;
	}
}