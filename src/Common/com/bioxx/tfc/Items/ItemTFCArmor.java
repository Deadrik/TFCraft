package com.bioxx.tfc.Items;

import java.util.List;

import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IIcon;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.StatCollector;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Core.TFCTabs;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.api.Armor;
import com.bioxx.tfc.api.Crafting.AnvilManager;
import com.bioxx.tfc.api.Enums.EnumItemReach;
import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;
import com.bioxx.tfc.api.Interfaces.IClothing;
import com.bioxx.tfc.api.Interfaces.ISize;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public class ItemTFCArmor extends ItemArmor implements ISize, IClothing
{
	private static final String[] leatherNames = new String[] {"leather_helmet_overlay", "leather_chestplate_overlay", "leather_leggings_overlay", "leather_boots_overlay"};
	public Armor ArmorType;
	public IIcon overlayIcon;
	private int thermal = 0;
	//private int type = 0;
	private int trueType = 0;

	public ItemTFCArmor(Armor armor, int renderIndex, int armorSlot, int thermal, int type)
	{
		super(ArmorMaterial.IRON, renderIndex, armorSlot%4);
		ArmorType = armor;
		this.trueType = armorSlot;
		this.setCreativeTab(TFCTabs.TFCArmor);
		this.setMaxDamage(ArmorType.getDurability(armorSlot));
	}

	public ItemTFCArmor(Armor armor, int renderIndex, int armorSlot, ArmorMaterial m, int thermal, int type)
	{
		super(m, renderIndex, armorSlot%4);
		ArmorType = armor;
		this.trueType = armorSlot;
		this.setCreativeTab(TFCTabs.TFCArmor);
		this.setMaxDamage(ArmorType.getDurability(armorSlot));
	}

	@Override
	public int getItemStackLimit()
	{
		if(canStack())
			return this.getSize(null).stackSize * getWeight(null).multiplier;
		else
			return 1;
	}

	@Override
	@SideOnly(Side.CLIENT)

	/**
	 * Gets an icon index based on an item's damage value and the given render pass
	 */
	public IIcon getIconFromDamageForRenderPass(int par1, int par2)
	{
		return par2 == 1 ? overlayIcon : super.getIconFromDamageForRenderPass(par1, par2);
	}

	@Override
	public void registerIcons(IIconRegister registerer)
	{
		if (this.getArmorMaterial() == ArmorMaterial.CLOTH)
		{
			this.itemIcon = registerer.registerIcon("minecraft:" + getIconString());
			overlayIcon = registerer.registerIcon("minecraft:" + leatherNames[this.armorType]);
		}
		else
			this.itemIcon = registerer.registerIcon(Reference.ModID + ":" + "armor/"+this.getUnlocalizedName().replace("item.", ""));
	}

	@Override
	public EnumSize getSize(ItemStack is)
	{
		return EnumSize.LARGE;
	}

	@Override
	/**
	 * Called whenever this item is equipped and the right mouse button is pressed. Args: itemStack, world, entityPlayer
	 */
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
	{
		int i = EntityLiving.getArmorPosition(par1ItemStack);
		ItemStack itemstack1 = par3EntityPlayer.getCurrentArmor((i-1)%4);

		if (itemstack1 == null)
		{
			par3EntityPlayer.setCurrentItemOrArmor(i /*+ 1*/, par1ItemStack.copy()); //Forge: Vanilla bug fix associated with fixed setCurrentItemOrArmor indexs for players.
			par1ItemStack.stackSize = 0;
		}

		return par1ItemStack;
	}

	@Override
	public boolean canStack() 
	{
		return false;
	}

	@Override
	public void addInformation(ItemStack is, EntityPlayer player, List arraylist, boolean flag)
	{
		ItemTerra.addSizeInformation(is, arraylist);
		ItemTerra.addHeatInformation(is, arraylist);

		if (TFC_Core.showShiftInformation()) 
		{
			arraylist.add(EnumChatFormatting.WHITE + StatCollector.translateToLocal("gui.Advanced") + ":");
			arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("gui.Armor.Pierce") + ": " + EnumChatFormatting.AQUA + ArmorType.getPiercingAR());
			arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("gui.Armor.Slash") + ": " + EnumChatFormatting.AQUA + ArmorType.getSlashingAR());
			arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("gui.Armor.Crush") + ": " + EnumChatFormatting.AQUA + ArmorType.getCrushingAR());
			arraylist.add("");
			if (is.hasTagCompound())
			{
				NBTTagCompound stackTagCompound = is.getTagCompound();

				if(stackTagCompound.hasKey("creator"))
					arraylist.add(EnumChatFormatting.ITALIC + StatCollector.translateToLocal("gui.Armor.ForgedBy") + " " + stackTagCompound.getString("creator"));
			}
		}
		else
			arraylist.add(EnumChatFormatting.DARK_GRAY + StatCollector.translateToLocal("gui.Advanced") + ": (" + StatCollector.translateToLocal("gui.Hold") + " " + 
					EnumChatFormatting.GRAY + StatCollector.translateToLocal("gui.Shift") + 
					EnumChatFormatting.DARK_GRAY + ")");

	}

	/**
	 * Copy-paste the old vanilla code
	 */
	@Override
	protected MovingObjectPosition getMovingObjectPositionFromPlayer(World par1World, EntityPlayer par2EntityPlayer, boolean par3)
	{
		float f = 1.0F;
		float f1 = par2EntityPlayer.prevRotationPitch + (par2EntityPlayer.rotationPitch - par2EntityPlayer.prevRotationPitch) * f;
		float f2 = par2EntityPlayer.prevRotationYaw + (par2EntityPlayer.rotationYaw - par2EntityPlayer.prevRotationYaw) * f;
		double d0 = par2EntityPlayer.prevPosX + (par2EntityPlayer.posX - par2EntityPlayer.prevPosX) * f;
		double d1 = par2EntityPlayer.prevPosY + (par2EntityPlayer.posY - par2EntityPlayer.prevPosY) * f + (par1World.isRemote ? par2EntityPlayer.getEyeHeight() - par2EntityPlayer.getDefaultEyeHeight() : par2EntityPlayer.getEyeHeight()); // isRemote check to revert changes to ray trace position due to adding the eye height clientside and player yOffset differences
		double d2 = par2EntityPlayer.prevPosZ + (par2EntityPlayer.posZ - par2EntityPlayer.prevPosZ) * f;
		Vec3 vec3 = Vec3.createVectorHelper(d0, d1, d2);
		float f3 = MathHelper.cos(-f2 * 0.017453292F - (float)Math.PI);
		float f4 = MathHelper.sin(-f2 * 0.017453292F - (float)Math.PI);
		float f5 = -MathHelper.cos(-f1 * 0.017453292F);
		float f6 = MathHelper.sin(-f1 * 0.017453292F);
		float f7 = f4 * f5;
		float f8 = f3 * f5;
		double d3 = 5.0D;
		if (par2EntityPlayer instanceof EntityPlayerMP)
		{
			d3 = ((EntityPlayerMP)par2EntityPlayer).theItemInWorldManager.getBlockReachDistance();
		}
		d3 *= getReach(null).multiplier;
		Vec3 vec31 = vec3.addVector(f7 * d3, f6 * d3, f8 * d3);
		return par1World.rayTraceBlocks(vec3, vec31, par3);
	}

	@Override
	public int getMaxDamage(ItemStack stack)
	{
		return (int) (super.getMaxDamage(stack)+(super.getMaxDamage(stack) * AnvilManager.getDurabilityBuff(stack)));
	}

	@Override
	public EnumWeight getWeight(ItemStack is)
	{
		if (this.getArmorMaterial() == ArmorMaterial.CLOTH)
			return EnumWeight.LIGHT;
		return EnumWeight.HEAVY;
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot, String type)
	{
		String m = ArmorType.metaltype.replace(" ", "").toLowerCase();
		return Reference.ModID + String.format(":textures/models/armor/%s_%d%s.png",
				m, (slot == 2 ? 2 : 1), type == null ? "" : String.format("_%s", type));
	}

	@Override
	public int getThermal()
	{
		return thermal;
	}

	//ItemArmor can't handle armor types >3, so this allows you to record the "true" armor type, whereas the value vanilla gets is %4
	public int getUnadjustedArmorType()
	{
		return trueType;
	}

	@Override
	public int getBodyPart()
	{
		return 3-armorType;
	}

	@Override
	public EnumItemReach getReach(ItemStack is)
	{
		return EnumItemReach.SHORT;
	}
}

