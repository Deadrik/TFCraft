package TFC.Render;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;
import net.minecraft.block.Block;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderBiped;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.scoreboard.Score;
import net.minecraft.scoreboard.ScoreObjective;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import TFC.TFCItems;
import TFC.Entities.EntityStand;
import TFC.Items.ItemQuiver;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPlayerTFC extends net.minecraft.client.renderer.entity.RenderPlayer
{
	private ModelBiped modelBipedMain;
	private ModelBiped modelArmorChestplate;
	private ModelBiped modelArmor;
	private ModelBiped quiverModel;
	//Should match RES_ITEM_GLINT in RenderLivingEntity
	private static final ResourceLocation RES_ITEM_GLINT = new ResourceLocation("textures/misc/enchanted_item_glint.png");

	public static String[] armorFilenamePrefix = new String[] {"cloth", "chain", "iron", "diamond", "gold"};
	public static float NAME_TAG_RANGE = 64.0f;
	public static float NAME_TAG_RANGE_SNEAK = 32.0f;
	ModelRenderer plume;
	ModelRenderer plume2;
	ModelRenderer HornR1;
	ModelRenderer HornL1;
	ModelRenderer HornR2;
	ModelRenderer HornL2;

	ModelRenderer quiver;
	ModelRenderer[] arrows = new ModelRenderer[16];

	public RenderPlayerTFC()
	{
		super();
		this.modelBipedMain = (ModelBiped)this.mainModel;
		this.modelArmorChestplate = new ModelBiped(1.0F);
		this.quiverModel = new ModelBiped(1.0F);
		this.modelArmor = new ModelBiped(0.5F);

		//Quiver
		quiver = new ModelRenderer(quiverModel,38,0);
		quiver.addBox(-2.5f,-6,-1.5f,5,12,3);
		quiver.setRotationPoint(0,4,4);
		quiver.rotateAngleZ = (float)(Math.PI/4) + (float)(Math.PI);
		quiver.setTextureSize(64, 32);

		for(int i = 0; i < arrows.length; i++){
			arrows[i] = new ModelRenderer(quiverModel,59,0);
			arrows[i].addBox(-1,-8,0,2,14,0);
			arrows[i].setRotationPoint(0,0,0f);
			arrows[i].setTextureSize(64,32);
			arrows[i].rotateAngleZ = (float)(Math.PI) + (float)(Math.PI/36)*(i%3)*(i%2==0?1f:-1f);
			arrows[i].rotateAngleX = (float)(Math.PI/36)*(i%3)*(i%2==(i%3)?1f:-1f);
			quiver.addChild(arrows[i]);
		}

		//Bronze
		plume = new ModelRenderer(modelArmorChestplate,40,0);
		plume2 = new ModelRenderer(modelArmorChestplate,40,0);
		plume.addBox(-1,-6,-10,2,6,10,0.5f);
		plume2.addBox(-1, -6, -10, 2, 6, 10);
		plume.setRotationPoint(0, -8, 2);
		plume2.setRotationPoint(0,-2,4);
		plume2.rotateAngleX = (float)(Math.PI/-3f);
		//Iron
		HornR1 = new ModelRenderer(modelArmorChestplate,40,0);
		HornR1.addBox(-6,-1.5f,-1.5f,3,3,6);
		HornL1 = new ModelRenderer(modelArmorChestplate,40,0);
		HornL1.addBox(6,-1.5f,-1.5f,3,3,6);       
		HornR1.setRotationPoint(-6, -6, 5);
		HornL1.setRotationPoint(6, -6, 8);
		HornR1.rotateAngleY=(float)(Math.PI/-2);
		HornR1.rotateAngleX = (float)Math.PI*(-1f/12f);
		HornL1.rotateAngleY=(float)(Math.PI/2);
		HornL1.rotateAngleX = (float)Math.PI*(-1f/12f);
		HornR2 = new ModelRenderer(modelArmorChestplate,40,9);
		HornR2.addBox(0, 0, -5f, 2, 2, 5);
		HornR2.setRotationPoint(-6, 0f, 2f);
		HornR2.rotateAngleX = (float)Math.PI*(6f/12f);
		HornR2.rotateAngleZ = (float)Math.PI*(1f/6f);
		HornL2 = new ModelRenderer(modelArmorChestplate,40,9);
		HornL2.addBox(0, 0, -5f, 2, 2, 5);
		HornL2.setRotationPoint(7, 0f, 2f);
		HornL2.rotateAngleX = (float)Math.PI*(6f/12f);
		HornL2.rotateAngleZ = (float)Math.PI*(-1f/6f);

		quiverModel.bipedHead.showModel = false;
		quiverModel.bipedHeadwear.showModel = false;
		quiverModel.bipedBody.showModel = true;//false;
		quiverModel.bipedRightArm.showModel = false;
		quiverModel.bipedLeftArm.showModel = false;
		quiverModel.bipedRightLeg.showModel = false;
		quiverModel.bipedLeftLeg.showModel = false;

		modelArmorChestplate.bipedHead.addChild(plume);
		modelArmorChestplate.bipedHead.addChild(plume2);
		modelArmorChestplate.bipedHead.addChild(HornR1);
		modelArmorChestplate.bipedHead.addChild(HornL1);
		quiverModel.bipedBody.addChild(quiver);
		HornR1.addChild(HornR2);
		HornL1.addChild(HornL2);
	}

	@Override
	protected int shouldRenderPass(EntityLivingBase par1EntityLivingBase, int par2, float par3)
	{
		if(par1EntityLivingBase instanceof EntityStand){
			return this.setArmorModelTFC((EntityStand)par1EntityLivingBase, par2, par3);
		}
		return this.setArmorModel((AbstractClientPlayer)par1EntityLivingBase, par2, par3);
	}

	protected int setArmorModelTFC(EntityStand stand, int par2, float par3)
	{
		ItemStack itemstack = stand.getCurrentItemOrArmor(3 - par2);

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
					float f2 = (float)(j >> 16 & 255) / 255.0F;
					float f3 = (float)(j >> 8 & 255) / 255.0F;
					float f4 = (float)(j & 255) / 255.0F;
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
	private float interpolateRotation(float par1, float par2, float par3)
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
	}

	private void backSlot(EntityLivingBase par1EntityLivingBase, double par2, double par4, double par6, float par8, float par9){
		int j = 4;

		float f2 = this.interpolateRotation(par1EntityLivingBase.prevRenderYawOffset, par1EntityLivingBase.renderYawOffset, par9);
		float f3 = this.interpolateRotation(par1EntityLivingBase.prevRotationYawHead, par1EntityLivingBase.rotationYawHead, par9);
		float f4;

		if (par1EntityLivingBase.isRiding() && par1EntityLivingBase.ridingEntity instanceof EntityLivingBase)
		{
			EntityLivingBase entitylivingbase1 = (EntityLivingBase)par1EntityLivingBase.ridingEntity;
			f2 = this.interpolateRotation(entitylivingbase1.prevRenderYawOffset, entitylivingbase1.renderYawOffset, par9);
			f4 = MathHelper.wrapAngleTo180_float(f3 - f2);

			if (f4 < -85.0F)
			{
				f4 = -85.0F;
			}

			if (f4 >= 85.0F)
			{
				f4 = 85.0F;
			}

			f2 = f3 - f4;

			if (f4 * f4 > 2500.0F)
			{
				f2 += f4 * 0.2F;
			}
		}

		float f5 = par1EntityLivingBase.prevRotationPitch + (par1EntityLivingBase.rotationPitch - par1EntityLivingBase.prevRotationPitch) * par9;
		f4 = this.handleRotationFloat(par1EntityLivingBase, par9);
		float f6 = 0.0625F;
		float f7 = par1EntityLivingBase.prevLimbSwingAmount + (par1EntityLivingBase.limbSwingAmount - par1EntityLivingBase.prevLimbSwingAmount) * par9;
		float f8 = par1EntityLivingBase.limbSwing - par1EntityLivingBase.limbSwingAmount * (1.0F - par9);

		if (par1EntityLivingBase.isChild())
		{
			f8 *= 3.0F;
		}

		if (f7 > 1.0F)
		{
			f7 = 1.0F;
		}

		float f9;
		int i;
		float f10;
		float f11;

		i = this.shouldRenderPass(par1EntityLivingBase, j, par9);

		if (i > 0)
		{
			this.renderPassModel.setLivingAnimations(par1EntityLivingBase, f8, f7, par9);
			this.renderPassModel.render(par1EntityLivingBase, f8, f7, f4, f3 - f2, f5, f6);

			if ((i & 240) == 16)
			{
				//this.func_82408_c(par1EntityLivingBase, j, par9);
				//this.renderPassModel.render(par1EntityLivingBase, f8, f7, f4, f3 - f2, f5, f6);
			}

			if ((i & 15) == 15)
			{
				f9 = (float)par1EntityLivingBase.ticksExisted + par9;
				this.bindTexture(RES_ITEM_GLINT);
				GL11.glEnable(GL11.GL_BLEND);
				f10 = 0.5F;
				GL11.glColor4f(f10, f10, f10, 1.0F);
				GL11.glDepthFunc(GL11.GL_EQUAL);
				GL11.glDepthMask(false);

				for (int k = 0; k < 2; ++k)
				{
					GL11.glDisable(GL11.GL_LIGHTING);
					f11 = 0.76F;
					GL11.glColor4f(0.5F * f11, 0.25F * f11, 0.8F * f11, 1.0F);
					GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
					GL11.glMatrixMode(GL11.GL_TEXTURE);
					GL11.glLoadIdentity();
					float f12 = f9 * (0.001F + (float)k * 0.003F) * 20.0F;
					float f13 = 0.33333334F;
					GL11.glScalef(f13, f13, f13);
					GL11.glRotatef(30.0F - (float)k * 60.0F, 0.0F, 0.0F, 1.0F);
					GL11.glTranslatef(0.0F, f12, 0.0F);
					GL11.glMatrixMode(GL11.GL_MODELVIEW);
					this.renderPassModel.render(par1EntityLivingBase, f8, f7, f4, f3 - f2, f5, f6);
				}

				GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
				GL11.glMatrixMode(GL11.GL_TEXTURE);
				GL11.glDepthMask(true);
				GL11.glLoadIdentity();
				GL11.glMatrixMode(GL11.GL_MODELVIEW);
				GL11.glEnable(GL11.GL_LIGHTING);
				GL11.glDisable(GL11.GL_BLEND);
				GL11.glDepthFunc(GL11.GL_LEQUAL);
			}

			GL11.glDisable(GL11.GL_BLEND);
			GL11.glEnable(GL11.GL_ALPHA_TEST);
		}
	}


	@Override
	public void func_130009_a(AbstractClientPlayer par1AbstractClientPlayer, double par2, double par4, double par6, float par8, float par9)
	{
		if (MinecraftForge.EVENT_BUS.post(new RenderPlayerEvent.Pre(par1AbstractClientPlayer, this, par9))) return;
		float f2 = 1.0F;
		GL11.glColor3f(f2, f2, f2);
		ItemStack itemstack = par1AbstractClientPlayer.inventory.getCurrentItem();
		this.quiverModel.heldItemRight = this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = itemstack != null ? 1 : 0;

		if (itemstack != null && par1AbstractClientPlayer.getItemInUseCount() > 0)
		{
			EnumAction enumaction = itemstack.getItemUseAction();

			if (enumaction == EnumAction.block)
			{
				this.quiverModel.heldItemRight = this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 3;
			}
			else if (enumaction == EnumAction.bow)
			{
				this.quiverModel.aimedBow =this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = true;
			}
		}

		this.quiverModel.isSneak = this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = par1AbstractClientPlayer.isSneaking();
		double d3 = par4 - (double)par1AbstractClientPlayer.yOffset;

		if (par1AbstractClientPlayer.isSneaking() && !(par1AbstractClientPlayer instanceof EntityPlayerSP))
		{
			d3 -= 0.125D;
		}

		super.func_130009_a(par1AbstractClientPlayer, par2, par4, par6, par8, par9);
		this.quiverModel.aimedBow = this.modelArmorChestplate.aimedBow = this.modelArmor.aimedBow = this.modelBipedMain.aimedBow = false;
		this.quiverModel.isSneak = this.modelArmorChestplate.isSneak = this.modelArmor.isSneak = this.modelBipedMain.isSneak = false;
		this.quiverModel.heldItemRight = this.modelArmorChestplate.heldItemRight = this.modelArmor.heldItemRight = this.modelBipedMain.heldItemRight = 0;
		MinecraftForge.EVENT_BUS.post(new RenderPlayerEvent.Post(par1AbstractClientPlayer, this, par9));
	}


	

	/**
	 * Set the specified armor model as the player model. Args: player, armorSlot, partialTick
	 */
	@Override
	protected int setArmorModel(AbstractClientPlayer par1AbstractClientPlayer, int par2, float par3)
	{
		ItemStack itemstack;
		itemstack = par1AbstractClientPlayer.inventory.armorItemInSlot(4 - (par2<4?par2:4));
		//RenderPlayerTFC.armorFilenamePrefix = RenderPlayer.armorFilenamePrefix;
		plume.showModel = false;
		plume2.showModel = false;
		HornR1.showModel = false;
		HornL1.showModel = false;
		quiver.showModel = false;
		if(par2 == 3){
			this.backSlot(par1AbstractClientPlayer, par1AbstractClientPlayer.posX, par1AbstractClientPlayer.posY, par1AbstractClientPlayer.posZ, 0, par3);
		}
		if (itemstack != null)
		{
			Item item = itemstack.getItem();

			if (item instanceof ItemArmor)
			{
				ItemArmor itemarmor = (ItemArmor)item;
				this.bindTexture(RenderBiped.getArmorResource(par1AbstractClientPlayer, itemstack, par2, null));
				ModelBiped modelbiped = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
				modelbiped.bipedHead.showModel = par2 == 0;
				plume.showModel = false;//(itemstack.getItem() == TFCItems.BronzeHelmet);
				plume2.showModel = false;//(itemstack.getItem() == TFCItems.BronzeHelmet);
				HornR1.showModel = false;//(itemstack.getItem() == TFCItems.WroughtIronHelmet);
				HornL1.showModel = false;//(itemstack.getItem() == TFCItems.WroughtIronHelmet);
				quiver.showModel = (itemstack.getItem() == TFCItems.Quiver);
				if(quiver.showModel){
					int n = ((ItemQuiver)TFCItems.Quiver).getQuiverArrows(itemstack);
					int arrowRenders = Math.min(n/4, 16);
					for(int i = 0; i < 16;i++){
						arrows[i].showModel = i < arrowRenders;
					}
				}
				modelbiped.bipedHeadwear.showModel = par2 == 0 && (itemstack.getItem() != TFCItems.BronzeHelmet&&itemstack.getItem() != TFCItems.WroughtIronHelmet);
				modelbiped.bipedBody.showModel = par2 == 1 || par2 == 2;
				modelbiped.bipedRightArm.showModel = par2 == 1;
				modelbiped.bipedLeftArm.showModel = par2 == 1;
				modelbiped.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
				modelbiped.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
				modelbiped = quiver.showModel?quiverModel:modelbiped;
				this.setRenderPassModel(modelbiped);

				if (modelbiped != null)
				{
					modelbiped.onGround = this.mainModel.onGround;
				}

				if (modelbiped != null)
				{
					modelbiped.isRiding = this.mainModel.isRiding;
				}

				if (modelbiped != null)
				{
					modelbiped.isChild = this.mainModel.isChild;
				}

				float f1 = 1.0F;

				if (itemarmor.getArmorMaterial() == EnumArmorMaterial.CLOTH)
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
