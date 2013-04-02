package TFC.Render;

import static net.minecraftforge.client.IItemRenderer.ItemRenderType.EQUIPPED;
import static net.minecraftforge.client.IItemRenderer.ItemRendererHelper.BLOCK_3D;
import net.minecraft.block.Block;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.MinecraftForgeClient;

import org.lwjgl.opengl.GL11;

import TFC.TFCItems;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderPlayerTFC extends net.minecraft.client.renderer.entity.RenderPlayer
{
    private ModelBiped modelBipedMain;
    private ModelBiped modelArmorChestplate;
    private ModelBiped modelArmor;
    public static String[] armorFilenamePrefix = new String[] {"cloth", "chain", "iron", "diamond", "gold"};
    public static float NAME_TAG_RANGE = 64.0f;
    public static float NAME_TAG_RANGE_SNEAK = 32.0f;
    ModelRenderer plume;
    ModelRenderer plume2;
    ModelRenderer HornR1;
    ModelRenderer HornL1;
    ModelRenderer HornR2;
    ModelRenderer HornL2;
    
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
        
        modelArmorChestplate.bipedHead.addChild(plume);
        modelArmorChestplate.bipedHead.addChild(plume2);
        modelArmorChestplate.bipedHead.addChild(HornR1);
        modelArmorChestplate.bipedHead.addChild(HornL1);
        HornR1.addChild(HornR2);
        HornL1.addChild(HornL2);
    }

    @Override
	protected void func_98191_a(EntityPlayer par1EntityPlayer)
    {
        this.loadDownloadableImageTexture(par1EntityPlayer.skinUrl, par1EntityPlayer.getTexture());
    }

    /**
     * Set the specified armor model as the player model. Args: player, armorSlot, partialTick
     */
    @Override
	protected int setArmorModel(EntityPlayer par1EntityPlayer, int par2, float par3)
    {
        ItemStack itemstack = par1EntityPlayer.inventory.armorItemInSlot(3 - par2);
        RenderPlayerTFC.armorFilenamePrefix = RenderPlayer.armorFilenamePrefix;
       
        if (itemstack != null)
        {
            Item item = itemstack.getItem();

            if (item instanceof ItemArmor)
            {
                ItemArmor itemarmor = (ItemArmor)item;
                this.loadTexture(ForgeHooksClient.getArmorTexture(itemstack, "/armor/" + armorFilenamePrefix[itemarmor.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + ".png"));
                ModelBiped modelbiped = par2 == 2 ? this.modelArmor : this.modelArmorChestplate;
                modelbiped.bipedHead.showModel = par2 == 0;
                plume.showModel = false;//(itemstack.getItem() == TFCItems.BronzeHelmet);
                plume2.showModel = false;//(itemstack.getItem() == TFCItems.BronzeHelmet);
                HornR1.showModel = false;//(itemstack.getItem() == TFCItems.WroughtIronHelmet);
                HornL1.showModel = false;//(itemstack.getItem() == TFCItems.WroughtIronHelmet);
                modelbiped.bipedHeadwear.showModel = par2 == 0 && (itemstack.getItem() != TFCItems.BronzeHelmet&&itemstack.getItem() != TFCItems.WroughtIronHelmet);
                modelbiped.bipedBody.showModel = par2 == 1 || par2 == 2;
                modelbiped.bipedRightArm.showModel = par2 == 1;
                modelbiped.bipedLeftArm.showModel = par2 == 1;
                modelbiped.bipedRightLeg.showModel = par2 == 2 || par2 == 3;
                modelbiped.bipedLeftLeg.showModel = par2 == 2 || par2 == 3;
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

    @Override
	protected void func_82439_b(EntityPlayer par1EntityPlayer, int par2, float par3)
    {
        ItemStack itemstack = par1EntityPlayer.inventory.armorItemInSlot(3 - par2);

        if (itemstack != null)
        {
            Item item = itemstack.getItem();

            if (item instanceof ItemArmor)
            {
                ItemArmor itemarmor = (ItemArmor)item;
                this.loadTexture(ForgeHooksClient.getArmorTexture(itemstack, "/armor/" + armorFilenamePrefix[itemarmor.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + "_b.png"));
                float f1 = 1.0F;
                GL11.glColor3f(f1, f1, f1);
            }
        }
    }

    @Override
	public void renderPlayer(EntityPlayer par1EntityPlayer, double par2, double par4, double par6, float par8, float par9)
    {
    	super.renderPlayer(par1EntityPlayer, par2, par4, par6, par8, par9);
        }

    /**
     * Method for adding special render rules
     */
    @Override
	protected void renderSpecials(EntityPlayer par1EntityPlayer, float par2)
    {
        float f1 = 1.0F;
        GL11.glColor3f(f1, f1, f1);
        //super.renderEquippedItems(par1EntityPlayer, par2);
        super.renderArrowsStuckInEntity(par1EntityPlayer, par2);
        ItemStack itemstack = par1EntityPlayer.inventory.armorItemInSlot(3);

        if (itemstack != null)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedHead.postRender(0.0625F);
            float f2;

            if (itemstack != null && itemstack.getItem() instanceof ItemBlock)
            {
                IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack, EQUIPPED);
                boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack, BLOCK_3D));

                if (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType()))
                {
                    f2 = 0.625F;
                    GL11.glTranslatef(0.0F, -0.25F, 0.0F);
                    GL11.glRotatef(90.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glScalef(f2, -f2, -f2);
                }

                this.renderManager.itemRenderer.renderItem(par1EntityPlayer, itemstack, 0);
            }
            else if (itemstack.getItem().itemID == Item.skull.itemID)
            {
                f2 = 1.0625F;
                GL11.glScalef(f2, -f2, -f2);
                String s = "";

                if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("SkullOwner"))
                {
                    s = itemstack.getTagCompound().getString("SkullOwner");
                }

                TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, 0.0F, -0.5F, 1, 180.0F, itemstack.getItemDamage(), s);
            }

            GL11.glPopMatrix();
        }

        float f3;
        float f4;

        if (par1EntityPlayer.username.equals("deadmau5") && this.loadDownloadableImageTexture(par1EntityPlayer.skinUrl, (String)null))
        {
            for (int i = 0; i < 2; ++i)
            {
                float f5 = par1EntityPlayer.prevRotationYaw + (par1EntityPlayer.rotationYaw - par1EntityPlayer.prevRotationYaw) * par2 - (par1EntityPlayer.prevRenderYawOffset + (par1EntityPlayer.renderYawOffset - par1EntityPlayer.prevRenderYawOffset) * par2);
                f3 = par1EntityPlayer.prevRotationPitch + (par1EntityPlayer.rotationPitch - par1EntityPlayer.prevRotationPitch) * par2;
                GL11.glPushMatrix();
                GL11.glRotatef(f5, 0.0F, 1.0F, 0.0F);
                GL11.glRotatef(f3, 1.0F, 0.0F, 0.0F);
                GL11.glTranslatef(0.375F * (i * 2 - 1), 0.0F, 0.0F);
                GL11.glTranslatef(0.0F, -0.375F, 0.0F);
                GL11.glRotatef(-f3, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(-f5, 0.0F, 1.0F, 0.0F);
                f4 = 1.3333334F;
                GL11.glScalef(f4, f4, f4);
                this.modelBipedMain.renderEars(0.0625F);
                GL11.glPopMatrix();
            }
        }

        float f6;

        if (this.loadDownloadableImageTexture(par1EntityPlayer.cloakUrl, (String)null) && !par1EntityPlayer.getHasActivePotion() && !par1EntityPlayer.getHideCape())
        {
            GL11.glPushMatrix();
            GL11.glTranslatef(0.0F, 0.0F, 0.125F);
            double d0 = par1EntityPlayer.field_71091_bM + (par1EntityPlayer.field_71094_bP - par1EntityPlayer.field_71091_bM) * par2 - (par1EntityPlayer.prevPosX + (par1EntityPlayer.posX - par1EntityPlayer.prevPosX) * par2);
            double d1 = par1EntityPlayer.field_71096_bN + (par1EntityPlayer.field_71095_bQ - par1EntityPlayer.field_71096_bN) * par2 - (par1EntityPlayer.prevPosY + (par1EntityPlayer.posY - par1EntityPlayer.prevPosY) * par2);
            double d2 = par1EntityPlayer.field_71097_bO + (par1EntityPlayer.field_71085_bR - par1EntityPlayer.field_71097_bO) * par2 - (par1EntityPlayer.prevPosZ + (par1EntityPlayer.posZ - par1EntityPlayer.prevPosZ) * par2);
            f6 = par1EntityPlayer.prevRenderYawOffset + (par1EntityPlayer.renderYawOffset - par1EntityPlayer.prevRenderYawOffset) * par2;
            double d3 = MathHelper.sin(f6 * (float)Math.PI / 180.0F);
            double d4 = (-MathHelper.cos(f6 * (float)Math.PI / 180.0F));
            float f7 = (float)d1 * 10.0F;

            if (f7 < -6.0F)
            {
                f7 = -6.0F;
            }

            if (f7 > 32.0F)
            {
                f7 = 32.0F;
            }

            float f8 = (float)(d0 * d3 + d2 * d4) * 100.0F;
            float f9 = (float)(d0 * d4 - d2 * d3) * 100.0F;

            if (f8 < 0.0F)
            {
                f8 = 0.0F;
            }

            float f10 = par1EntityPlayer.prevCameraYaw + (par1EntityPlayer.cameraYaw - par1EntityPlayer.prevCameraYaw) * par2;
            f7 += MathHelper.sin((par1EntityPlayer.prevDistanceWalkedModified + (par1EntityPlayer.distanceWalkedModified - par1EntityPlayer.prevDistanceWalkedModified) * par2) * 6.0F) * 32.0F * f10;

            if (par1EntityPlayer.isSneaking())
            {
                f7 += 25.0F;
            }

            GL11.glRotatef(6.0F + f8 / 2.0F + f7, 1.0F, 0.0F, 0.0F);
            GL11.glRotatef(f9 / 2.0F, 0.0F, 0.0F, 1.0F);
            GL11.glRotatef(-f9 / 2.0F, 0.0F, 1.0F, 0.0F);
            GL11.glRotatef(180.0F, 0.0F, 1.0F, 0.0F);
            this.modelBipedMain.renderCloak(0.0625F);
            GL11.glPopMatrix();
        }

        ItemStack itemstack1 = par1EntityPlayer.inventory.getCurrentItem();

        if (itemstack1 != null)
        {
            GL11.glPushMatrix();
            this.modelBipedMain.bipedRightArm.postRender(0.0625F);
            GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

            if (par1EntityPlayer.fishEntity != null)
            {
                itemstack1 = new ItemStack(Item.stick);
            }

            EnumAction enumaction = null;

            if (par1EntityPlayer.getItemInUseCount() > 0)
            {
                enumaction = itemstack1.getItemUseAction();
            }

            IItemRenderer customRenderer = MinecraftForgeClient.getItemRenderer(itemstack1, EQUIPPED);
            boolean is3D = (customRenderer != null && customRenderer.shouldUseRenderHelper(EQUIPPED, itemstack1, BLOCK_3D));

            if (itemstack1.getItem() instanceof ItemBlock && (is3D || RenderBlocks.renderItemIn3d(Block.blocksList[itemstack1.itemID].getRenderType())))
            {
                f3 = 0.5F;
                GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
                f3 *= 0.75F;
                GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(-f3, -f3, f3);
            }
            else if (itemstack1.itemID == Item.bow.itemID)
            {
                f3 = 0.625F;
                GL11.glTranslatef(0.0F, 0.125F, 0.3125F);
                GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
                GL11.glScalef(f3, -f3, f3);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else if (Item.itemsList[itemstack1.itemID].isFull3D())
            {
                f3 = 0.625F;

                if (Item.itemsList[itemstack1.itemID].shouldRotateAroundWhenRendering())
                {
                    GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
                    GL11.glTranslatef(0.0F, -0.125F, 0.0F);
                }

                if (par1EntityPlayer.getItemInUseCount() > 0 && enumaction == EnumAction.block)
                {
                    GL11.glTranslatef(0.05F, 0.0F, -0.1F);
                    GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
                    GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
                    GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
                }

                GL11.glTranslatef(0.0F, 0.1875F, 0.0F);
                GL11.glScalef(f3, -f3, f3);
                GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
            }
            else
            {
                f3 = 0.375F;
                GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
                GL11.glScalef(f3, f3, f3);
                GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
                GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
                GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
            }

            float f11;
            int j;
            float f12;

            if (itemstack1.getItem().requiresMultipleRenderPasses())
            {
                for (j = 0; j < itemstack1.getItem().getRenderPasses(itemstack1.getItemDamage()); ++j)
                {
                    int k = itemstack1.getItem().getColorFromItemStack(itemstack1, j);
                    f12 = (k >> 16 & 255) / 255.0F;
                    f11 = (k >> 8 & 255) / 255.0F;
                    f6 = (k & 255) / 255.0F;
                    GL11.glColor4f(f12, f11, f6, 1.0F);
                    this.renderManager.itemRenderer.renderItem(par1EntityPlayer, itemstack1, j);
                }
            }
            else
            {
                j = itemstack1.getItem().getColorFromItemStack(itemstack1, 0);
                f4 = (j >> 16 & 255) / 255.0F;
                f12 = (j >> 8 & 255) / 255.0F;
                f11 = (j & 255) / 255.0F;
                GL11.glColor4f(f4, f12, f11, 1.0F);
                this.renderManager.itemRenderer.renderItem(par1EntityPlayer, itemstack1, 0);
            }

            GL11.glPopMatrix();
        }
    }

    @Override
	protected void renderPlayerScale(EntityPlayer par1EntityPlayer, float par2)
    {
        float f1 = 0.9375F;
        GL11.glScalef(f1, f1, f1);
    }

    @Override
	protected void func_96450_a(EntityPlayer par1EntityPlayer, double par2, double par4, double par6, String par8Str, float par9, double par10)
    {
        if (par10 < 100.0D)
        {
            Scoreboard scoreboard = par1EntityPlayer.func_96123_co();
            ScoreObjective scoreobjective = scoreboard.func_96539_a(2);

            if (scoreobjective != null)
            {
                Score score = scoreboard.func_96529_a(par1EntityPlayer.getEntityName(), scoreobjective);

                if (par1EntityPlayer.isPlayerSleeping())
                {
                    this.renderLivingLabel(par1EntityPlayer, score.func_96652_c() + " " + scoreobjective.func_96678_d(), par2, par4 - 1.5D, par6, 64);
                }
                else
                {
                    this.renderLivingLabel(par1EntityPlayer, score.func_96652_c() + " " + scoreobjective.func_96678_d(), par2, par4, par6, 64);
                }

                par4 += this.getFontRendererFromRenderManager().FONT_HEIGHT * 1.15F * par9;
            }
        }

        super.func_96449_a(par1EntityPlayer, par2, par4, par6, par8Str, par9, par10);
    }

    @Override
	public void renderFirstPersonArm(EntityPlayer par1EntityPlayer)
    {
        float f = 1.0F;
        GL11.glColor3f(f, f, f);
        this.modelBipedMain.onGround = 0.0F;
        this.modelBipedMain.setRotationAngles(0.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0625F, par1EntityPlayer);
        this.modelBipedMain.bipedRightArm.render(0.0625F);
    }

    /**
     * Renders player with sleeping offset if sleeping
     */
    @Override
	protected void renderPlayerSleep(EntityPlayer par1EntityPlayer, double par2, double par4, double par6)
    {
    	super.renderPlayerSleep(par1EntityPlayer, par2, par4, par6);
    }

    /**
     * Rotates the player if the player is sleeping. This method is called in rotateCorpse.
     */
    @Override
	protected void rotatePlayer(EntityPlayer par1EntityPlayer, float par2, float par3, float par4)
    {
    	super.rotatePlayer(par1EntityPlayer, par2, par3, par4);
    }

    @Override
	protected void func_96449_a(EntityLiving par1EntityLiving, double par2, double par4, double par6, String par8Str, float par9, double par10)
    {
        this.func_96450_a((EntityPlayer)par1EntityLiving, par2, par4, par6, par8Str, par9, par10);
    }

    /**
     * Allows the render to do any OpenGL state modifications necessary before the model is rendered. Args:
     * entityLiving, partialTickTime
     */
    @Override
	protected void preRenderCallback(EntityLiving par1EntityLiving, float par2)
    {
        this.renderPlayerScale((EntityPlayer)par1EntityLiving, par2);
    }

    @Override
	protected void func_82408_c(EntityLiving par1EntityLiving, int par2, float par3)
    {
        this.func_82439_b((EntityPlayer)par1EntityLiving, par2, par3);
    }

    /**
     * Queries whether should render the specified pass or not.
     */
    @Override
	protected int shouldRenderPass(EntityLiving par1EntityLiving, int par2, float par3)
    {
        return this.setArmorModel((EntityPlayer)par1EntityLiving, par2, par3);
    }

    @Override
	protected void renderEquippedItems(EntityLiving par1EntityLiving, float par2)
    {
        this.renderSpecials((EntityPlayer)par1EntityLiving, par2);
    }

    @Override
	protected void rotateCorpse(EntityLiving par1EntityLiving, float par2, float par3, float par4)
    {
        this.rotatePlayer((EntityPlayer)par1EntityLiving, par2, par3, par4);
    }

    /**
     * Sets a simple glTranslate on a LivingEntity.
     */
    @Override
	protected void renderLivingAt(EntityLiving par1EntityLiving, double par2, double par4, double par6)
    {
        this.renderPlayerSleep((EntityPlayer)par1EntityLiving, par2, par4, par6);
    }

    @Override
	protected void func_98190_a(EntityLiving par1EntityLiving)
    {
        this.func_98191_a((EntityPlayer)par1EntityLiving);
    }

    @Override
	public void doRenderLiving(EntityLiving par1EntityLiving, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderPlayer((EntityPlayer)par1EntityLiving, par2, par4, par6, par8, par9);
    }

    /**
     * Actually renders the given argument. This is a synthetic bridge method, always casting down its argument and then
     * handing it off to a worker function which does the actual work. In all probabilty, the class Render is generic
     * (Render<T extends Entity) and this method has signature public void doRender(T entity, double d, double d1,
     * double d2, float f, float f1). But JAD is pre 1.5 so doesn't do that.
     */
    @Override
	public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        this.renderPlayer((EntityPlayer)par1Entity, par2, par4, par6, par8, par9);
    }
}
