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

    /*@Override
	protected void func_98191_a(EntityPlayer par1EntityPlayer)
    {
        this.loadDownloadableImageTexture(par1EntityPlayer.skinUrl, par1EntityPlayer.getTexture());
    }*

    /**
     * Set the specified armor model as the player model. Args: player, armorSlot, partialTick
     */
   /* @Override
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
                this.loadTexture(ForgeHooksClient.getArmorTexture(par1EntityPlayer, itemstack, "/armor/" + armorFilenamePrefix[itemarmor.renderIndex] + "_" + (par2 == 2 ? 2 : 1) + ".png"));
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
    }*/

}
