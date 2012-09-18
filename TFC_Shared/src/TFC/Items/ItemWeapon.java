package TFC.Items;

import java.util.List;

import TFC.Core.TFC_Settings;
import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;
import net.minecraft.src.*;

public class ItemWeapon extends ItemSword implements ISize
{
	private int weaponDamage;
	private final EnumToolMaterial toolMaterial;

	public ItemWeapon(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, par2EnumToolMaterial);
		this.toolMaterial = par2EnumToolMaterial;
		this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
		this.weaponDamage = 4 + par2EnumToolMaterial.getDamageVsEntity();
	}
	
	public void addInformation(ItemStack is, List arraylist) 
    {
		ItemTerra.addSizeInformation(this, arraylist);
		
        if(TFC_Settings.enableDebugMode)
            arraylist.add("Damage: "+is.getItemDamage());
    }

	/**
	 * Returns if the item (tool) can harvest results from the block type.
	 */
	public boolean canHarvestBlock(Block par1Block)
	{
		return par1Block.blockID == Block.web.blockID;
	}

	/**
	 * Returns the damage against a given entity.
	 */
	public int getDamageVsEntity(Entity par1Entity)
	{
		return this.weaponDamage;
	}
	
	public int getItemStackLimit()
    {
    	if(canStack())
    		return this.getSize().stackSize * getWeight().multiplier;
    	else
    		return 1;
    }

	/**
	 * Return the enchantability factor of the item, most of the time is based on material.
	 */
	public int getItemEnchantability()
	{
		return this.toolMaterial.getEnchantability();
	}

	public EnumAction getItemUseAction(ItemStack par1ItemStack)
	{
		return EnumAction.block;
	}

	public int getMaxItemUseDuration(ItemStack par1ItemStack)
	{
		return 72000;
	}

	/**
	 * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	 * sword
	 */
	public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	{
		return par2Block.blockID == Block.web.blockID ? 15.0F : 1.5F;
	}

	public String getTextureFile() {
		return "/bioxx/terratools.png";
	}

	/**
	 * Current implementations of this method in child classes do not use the entry argument beside ev. They just raise
	 * the damage on the stack.
	 */
	public boolean hitEntity(ItemStack par1ItemStack, EntityLiving par2EntityLiving, EntityLiving par3EntityLiving)
	{
		par1ItemStack.damageItem(1, par3EntityLiving);
		return true;
	}

	/**
	 * Returns True is the item is renderer in full 3D when hold.
	 */
	public boolean isFull3D()
	{
		return true;
	}

	public boolean onBlockDestroyed(ItemStack par1ItemStack, int par2, int par3, int par4, int par5, EntityLiving par6EntityLiving)
	{
		par1ItemStack.damageItem(2, par6EntityLiving);
		return true;
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	@Override
	public EnumSize getSize() {
		// TODO Auto-generated method stub
		return EnumSize.MEDIUM;
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
