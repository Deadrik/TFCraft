package com.bioxx.tfc.Render;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import net.minecraft.item.ItemStack;

import net.minecraftforge.client.ForgeHooksClient;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Entities.EntityStand;
import com.bioxx.tfc.api.TFCItems;

@SideOnly(Side.CLIENT)
public class RenderPlayerTFC extends RenderPlayer
{
	//private ModelBiped modelBipedMain;
	private ModelBiped modelArmorChestplate;
	private ModelBiped modelArmor;
	//Should match RES_ITEM_GLINT in RenderLivingEntity
	//private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	public static String[] armorFilenamePrefix = new String[] {"cloth", "chain", "iron", "diamond", "gold"};
	public static final float NAME_TAG_RANGE = 64.0f;
	public static final float NAME_TAG_RANGE_SNEAK = 32.0f;
	private ModelRenderer plume;
	private ModelRenderer plume2;
	private ModelRenderer hornR1;
	private ModelRenderer hornL1;
	private ModelRenderer hornR2;
	private ModelRenderer hornL2;


	public RenderPlayerTFC()
	{
		super();
		this.modelBipedMain = (ModelBiped)this.mainModel;
		this.modelArmorChestplate = new ModelBiped(1.0F);
		this.modelArmor = new ModelBiped(0.5F);

		//Bronze
		plume = new ModelRenderer(modelArmorChestplate,40,0);
		plume2 = new ModelRenderer(modelArmorChestplate,40,0);
		plume.addBox(-1,-6,-10,2,6,10,0.5f);
		plume2.addBox(-1, -6, -10, 2, 6, 10);
		plume.setRotationPoint(0, -8, 2);
		plume2.setRotationPoint(0,-2,4);
		plume2.rotateAngleX = (float)(Math.PI/-3f);
		//Iron
		hornR1 = new ModelRenderer(modelArmorChestplate,40,0);
		hornR1.addBox(-6,-1.5f,-1.5f,3,3,6);
		hornL1 = new ModelRenderer(modelArmorChestplate,40,0);
		hornL1.addBox(6,-1.5f,-1.5f,3,3,6);
		hornR1.setRotationPoint(-6, -6, 5);
		hornL1.setRotationPoint(6, -6, 8);
		hornR1.rotateAngleY=(float)(Math.PI/-2);
		hornR1.rotateAngleX = (float)Math.PI*(-1f/12f);
		hornL1.rotateAngleY=(float)(Math.PI/2);
		hornL1.rotateAngleX = (float)Math.PI*(-1f/12f);
		hornR2 = new ModelRenderer(modelArmorChestplate,40,9);
		hornR2.addBox(0, 0, -5f, 2, 2, 5);
		hornR2.setRotationPoint(-6, 0f, 2f);
		hornR2.rotateAngleX = (float)Math.PI*(6f/12f);
		hornR2.rotateAngleZ = (float)Math.PI*(1f/6f);
		hornL2 = new ModelRenderer(modelArmorChestplate,40,9);
		hornL2.addBox(0, 0, -5f, 2, 2, 5);
		hornL2.setRotationPoint(7, 0f, 2f);
		hornL2.rotateAngleX = (float)Math.PI*(6f/12f);
		hornL2.rotateAngleZ = (float)Math.PI*(-1f/6f);

		modelArmorChestplate.bipedHead.addChild(plume);
		modelArmorChestplate.bipedHead.addChild(plume2);
		modelArmorChestplate.bipedHead.addChild(hornR1);
		modelArmorChestplate.bipedHead.addChild(hornL1);
		hornR1.addChild(hornR2);
		hornL1.addChild(hornL2);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		if(par1EntityLivingBase instanceof EntityStand)
		{
			return this.setArmorModelTFC((EntityStand)par1EntityLivingBase, par2, par3);
		}
		return this.shouldRenderPass/*setArmorModel*/((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
	}

	protected int setArmorModelTFC(EntityStand stand, int par2, float par3)
	{
		ItemStack itemstack = stand.getEquipmentInSlot(3 - par2);

		if (itemstack != null)
		{
			Item item = itemstack.getItem();

			if (item instanceof ItemArmor)
			{
				ItemArmor itemarmor = (ItemArmor)item;
				this.bindTexture(RenderBiped.getArmorResource(stand, itemstack, par2, null));
				ModelBiped modelbiped = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
				modelbiped.bipedHead.showModel = par2 == 0;
				modelbiped.bipedHeadwear.showModel = par2 == 0;
				modelbiped.bipedBody.showModel = par2 == 1 || par2 == 2;
				modelbiped.bipedRightArm.showModel = par2 == 1;
				modelbiped.bipedLeftArm.showModel = par2 == 1;
				modelbiped.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
				modelbiped.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
				modelbiped = ForgeHooksClient.getArmorModel(stand, itemstack, par2, modelbiped);
				this.setRenderPassModel(modelbiped);
				modelbiped.onGround = this.mainModel.onGround;
				modelbiped.isRiding = this.mainModel.isRiding;
				modelbiped.isChild = this.mainModel.isChild;
				float f1 = 1.0F;

				//Move outside if to allow for more then just CLOTH
				int j = itemarmor.getColor(itemstack);
				if (j != -1)
				{
					float f2 = (j >> 16 & 255) / 255.0F;
					float f3 = (j >> 8 & 255) / 255.0F;
					float f4 = (j & 255) / 255.0F;
					GL11.glColor3f(f1 * f2, f1 * f3, f1 * f4);

					if (itemstack.isItemEnchanted())
					{
						return 31;
					}

					return 16;
				}

				GL11.glColor3f(f1, f1, f1);

				if (itemstack.isItemEnchanted())
				{
					return 15;
				}

				return 1;
			}
		}
		return -1;
	}

	/**
	 * Should be the same as the one in RenderLivingEntity, but that was private.
	 * 
	 * 
	 * Returns a rotation angle that is inbetween two other rotation angles. par1 and par2 are the angles between which
	 * to interpolate, par3 is probably a float between 0.0 and 1.0 that tells us where "between" the two angles we are.
	 * Example: par1 = 30, par2 = 50, par3 = 0.5, then return = 40
	 */
	/*private float interpolateRotation(float par1, float par2, float par3)
	{
		float f3;

		for (f3 = par2 - par1; f3 < -180.0F; f3 += 360.0F)
		{
			;
		}

		while (f3 >= 180.0F)
		{
			f3 -= 360.0F;
		}

		return par1 + par3 * f3;
	}*/

	/**
	 * Set the specified armor model as the player model. Args: player, armorSlot, partialTick
	 */
	@Override
	protected int shouldRenderPass/*setArmorModel*/(AbstractClientPlayer par1AbstractClientPlayer, int slotIndex, float partialTick)
	{
		ItemStack itemstack;
		itemstack = par1AbstractClientPlayer.inventory.armorItemInSlot(3 - slotIndex);
		//RenderPlayerTFC.armorFilenamePrefix = RenderPlayer.armorFilenamePrefix;
		plume.showModel = false;
		plume2.showModel = false;
		hornR1.showModel = false;
		hornL1.showModel = false;
		if (itemstack != null)
		{
			Item item = itemstack.getItem();

			if (item instanceof ItemArmor)
			{
				ItemArmor itemarmor = (ItemArmor)item;
				this.bindTexture(RenderBiped.getArmorResource(par1AbstractClientPlayer, itemstack, slotIndex, null));
				ModelBiped modelbiped = slotIndex == 2 ? this.modelArmor : this.modelArmorChestplate;
				modelbiped.bipedHead.showModel = slotIndex == 0;
				plume.showModel = false;//(itemstack.getItem() == TFCItems.BronzeHelmet);
				plume2.showModel = false;//(itemstack.getItem() == TFCItems.BronzeHelmet);
				hornR1.showModel = false;//(itemstack.getItem() == TFCItems.WroughtIronHelmet);
				hornL1.showModel = false;//(itemstack.getItem() == TFCItems.WroughtIronHelmet);
				modelbiped.bipedHeadwear.showModel = slotIndex == 0 && itemstack.getItem() != TFCItems.bronzeHelmet &&
														itemstack.getItem() != TFCItems.wroughtIronHelmet;
				modelbiped.bipedBody.showModel = slotIndex == 1 || slotIndex == 2;
				modelbiped.bipedRightArm.showModel = slotIndex == 1;
				modelbiped.bipedLeftArm.showModel = slotIndex == 1;
				modelbiped.bipedRightLeg.showModel = slotIndex == 2 || slotIndex == 3;
				modelbiped.bipedLeftLeg.showModel = slotIndex == 2 || slotIndex == 3;
				this.setRenderPassModel(modelbiped);

				modelbiped.onGround = this.mainModel.onGround;
				modelbiped.isRiding = this.mainModel.isRiding;
				modelbiped.isChild = this.mainModel.isChild;

				float f1 = 1.0F;

				if (itemarmor.getArmorMaterial() == ArmorMaterial.CLOTH)
				{
					int j = itemarmor.getColor(itemstack);
					float f2 = (j >> 16 & 255) / 255.0F;
					float f3 = (j >> 8 & 255) / 255.0F;
					float f4 = (j & 255) / 255.0F;
					GL11.glColor3f(f1 * f2, f1 * f3, f1 * f4);

					if (itemstack.isItemEnchanted())
					{
						return 31;
					}

					return 16;
				}

				GL11.glColor3f(f1, f1, f1);

				if (itemstack.isItemEnchanted())
				{
					return 15;
				}

				return 1;
			}
		}
		return -1;
	}

}
