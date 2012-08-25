package TFC.Items;

import net.minecraft.src.*;
import net.minecraftforge.common.ForgeHooks;

public class ItemCustomPaxel extends ItemTool
{
	/** Array of blocks the tool has extra effect against. */
	private Block[] blocksEffectiveAgainst;
	public float efficiencyOnProperMaterial = 4.0F;

	/** Damage versus entities. */
	public int damageVsEntity;

	/** The material this tool is made from. */
	protected EnumToolMaterial toolMaterial;

	public ItemCustomPaxel(int par1)
	{
		super(par1, 1, TFCItems.SedToolMaterial, Block.blocksList);
		this.toolMaterial = TFCItems.SedToolMaterial;
		this.blocksEffectiveAgainst = Block.blocksList;
		this.maxStackSize = 1;
		this.setMaxDamage(10);
		this.efficiencyOnProperMaterial = EnumToolMaterial.WOOD.getEfficiencyOnProperMaterial()/2;
		this.damageVsEntity = 2;
	}

	@Override
	public boolean canHarvestBlock(Block par1Block)
	{
		return true;
	}

	/**
	 * Returns the damage against a given entity.
	 */
	 public int getDamageVsEntity(Entity par1Entity)
	 {
		 return this.damageVsEntity;
	 }

	 /**
	  * Return the enchantability factor of the item, most of the time is based on material.
	  */
	 public int getItemEnchantability()
	 {
		 return this.toolMaterial.getEnchantability();
	 }

	 /**
	  * Returns the strength of the stack against a given block. 1.0F base, (Quality+1)*2 if correct blocktype, 1.5F if
	  * sword
	  */
	 public float getStrVsBlock(ItemStack par1ItemStack, Block par2Block)
	 {
		 for (int var3 = 0; var3 < this.blocksEffectiveAgainst.length; ++var3)
		 {
			 if (this.blocksEffectiveAgainst[var3] == par2Block)
			 {
				 return this.efficiencyOnProperMaterial;
			 }
		 }

		 return 1.0F;
	 }

	 /** FORGE: Overridden to allow custom tool effectiveness */
	 @Override
	 public float getStrVsBlock(ItemStack stack, Block block, int meta) 
	 {
		 if (ForgeHooks.isToolEffective(stack, block, meta))
		 {
			 return efficiencyOnProperMaterial;
		 }
		 return getStrVsBlock(stack, block);
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
		 par1ItemStack.damageItem(2, par3EntityLiving);
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
		 par1ItemStack.damageItem(1, par6EntityLiving);
		 return true;
	 }
}