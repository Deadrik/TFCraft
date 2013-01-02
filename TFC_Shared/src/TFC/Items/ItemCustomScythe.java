package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Core.TFCTabs;
import TFC.Core.TFC_Core;
import TFC.Core.TFC_Settings;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;
import net.minecraft.world.biome.*;
import net.minecraft.world.chunk.*;
import net.minecraft.world.gen.feature.*;

public class ItemCustomScythe extends ItemTerraTool
{
    static Block[] blocks = {Block.blocksList[18]};
	private int weaponDamage;
	public ItemCustomScythe(int i, EnumToolMaterial e)
	{
		super(i, e.getDamageVsEntity(),e, blocks);
		this.setMaxDamage(e.getMaxUses()*3);
		this.weaponDamage = e.getDamageVsEntity();
		this.efficiencyOnProperMaterial = e.getEfficiencyOnProperMaterial();
		setCreativeTab(TFCTabs.TFCTools);
	}

	@Override
	public int getDamageVsEntity(Entity par1Entity)
	{
		return this.weaponDamage;
	}

	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par1ItemStack.damageItem(1, par3EntityLiving);
		return true;
	}

}