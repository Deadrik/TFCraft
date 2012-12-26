package TFC.Items;

import java.util.List;

import TFC.*;
import TFC.Core.Helper;
import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
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

public class ItemWeapon extends ItemSword implements ISize
{
	public int weaponDamage;
	public final EnumToolMaterial toolMaterial;

	public ItemWeapon(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, par2EnumToolMaterial);
		this.toolMaterial = par2EnumToolMaterial;
		this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
		this.weaponDamage = par2EnumToolMaterial.getDamageVsEntity();
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
    {
		ItemTerra.addSizeInformation(this, arraylist);
		
        if(TFC_Settings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }

	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	@Override
	public boolean canHarvestBlock(Block par1Block)
	{
		return par1Block.blockID == Block.web.blockID;
	}

	/**
	 * Returns the damage against a given entity.
	 */
	@Override
	public int getDamageVsEntity(Entity par1Entity)
	{
		return this.weaponDamage;
	}
	@Override
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier;
    	else
    		return 1;
    }
	
	/**
     * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
     */
	@Override
    public ItemStack onItemRightClick(ItemStack is, World world, EntityPlayer player)
    {
    	 MovingObjectPosition objectMouseOver = Helper.getMouseOverObject(player, player.worldObj);
         
         if(objectMouseOver != null && world.getBlockId(objectMouseOver.blockX, objectMouseOver.blockY, objectMouseOver.blockZ) == TFCBlocks.ToolRack.blockID)
        	 return is;
         
         player.setItemInUse(is, this.getMaxItemUseDuration(is));
        return is;
    }

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	@Override
	public int getItemEnchantability()
	{
		return this.toolMaterial.getEnchantability();
	}
	@Override
	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.block;
	}
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	@Override
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		return par2Block.blockID == Block.web.blockID ? 15.0F : 1.5F;
	}
	@Override
	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	@Override
	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par1ItemStack.damageItem(1, par3EntityLiving);
		return true;
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	@Override
	public boolean isFull3D()
	{
		return true;
	}
	
	@Override
	public boolean onBlockDestroyed(ItemStack par1ItemStack, World par2World, int par3, int par4, int par5, int par6, EntityLiving par7EntityLiving)
    {
        if ((double)Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
        {
            par1ItemStack.damageItem(2, par7EntityLiving);
        }

        return true;
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
