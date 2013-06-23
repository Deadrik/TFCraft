package TFC.Items.Tools;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumToolMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.TFCBlocks;
import TFC.API.ICausesDamage;
import TFC.API.ISize;
import TFC.API.TFCTabs;
import TFC.API.Enums.EnumDamageType;
import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;
import TFC.Core.Helper;
import TFC.Core.Util.StringUtil;
import TFC.Items.ItemTerra;

public class ItemWeapon extends ItemSword implements ISize, ICausesDamage
{
	public int weaponDamage;
	public final EnumToolMaterial toolMaterial;
	public EnumDamageType damageType = EnumDamageType.SLASHING;

	public ItemWeapon(int par1, EnumToolMaterial par2EnumToolMaterial)
	{
		super(par1, par2EnumToolMaterial);
		this.toolMaterial = par2EnumToolMaterial;
		this.setMaxDamage(par2EnumToolMaterial.getMaxUses());
		this.weaponDamage = par2EnumToolMaterial.getDamageVsEntity();
		setCreativeTab(TFCTabs.TFCTools);
	}
	
	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag) 
    {
		Minecraft.getMinecraft().gameSettings.advancedItemTooltips = false;
		
		ItemTerra.addSizeInformation(this, arraylist);
		
		ItemTerra.addHeatInformation(is, arraylist);
		
		if(is.getItem() instanceof ICausesDamage)
		{
			arraylist.add(EnumChatFormatting.AQUA + ((ICausesDamage)this).GetDamageType().toString());
		}
		
		addItemInformation(is, player, arraylist);
		
		addExtraInformation(is, player, arraylist);
    }
	
	public void addItemInformation(ItemStack is, EntityPlayer player, List arraylist)
    {
    	
    }
	
	public void addExtraInformation(ItemStack is, EntityPlayer player, List arraylist)
    {

    }
	
	@Override
    public void registerIcons(IconRegister registerer)
    {
		this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "tools/"+this.getUnlocalizedName().replace("item.", ""));
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
        if (Block.blocksList[par3].getBlockHardness(par2World, par4, par5, par6) != 0.0D)
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

	@Override
	public EnumDamageType GetDamageType() 
	{
		return damageType;
	}
	
	@Override
	public String getItemDisplayName(ItemStack itemstack) 
	{
		return StringUtil.localize(getUnlocalizedName(itemstack).replace(" ", ""));
	}
}
