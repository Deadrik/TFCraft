package TFC.GUI;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerInventory;
import TFC.Core.Util.StringUtil;
import TFC.Food.TFCPotion;

public class GuiInventoryTFC  extends GuiInventory
{	
	private float xSize_lo;
	private float ySize_lo;
	private boolean hasEffect;
	protected static final ResourceLocation InventoryUpperTex = new ResourceLocation(Reference.ModID+":textures/gui/inventory.png");
	protected static final ResourceLocation InventoryEffectsTex = new ResourceLocation(Reference.ModID+":textures/gui/inv_effects.png");
	protected EntityPlayer player;

	public GuiInventoryTFC(EntityPlayer player) 
	{
		super(player);
		xSize = 176;
		ySize = 85+PlayerInventory.invYSize;
		this.player = player;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float par1, int par2, int par3)
	{
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		this.mc.getTextureManager().bindTexture(InventoryUpperTex);
		int k = this.guiLeft;
		int l = this.guiTop;
		this.drawTexturedModalRect(k, l, 0, 0, this.xSize, this.ySize);
		//Draw the player avatar
		func_110423_a(k + 51, l + 75, 30, k + 51 - this.xSize_lo, l + 75 - 50 - this.ySize_lo, this.mc.thePlayer);

		PlayerInventory.drawInventory(this, width, height, ySize-PlayerInventory.invYSize);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2)
	{
		this.fontRenderer.drawString(I18n.getString("container.crafting"), 86, 7, 4210752);
	}

	@Override
	public void initGui()
	{
		super.initGui();

		if (!this.mc.thePlayer.getActivePotionEffects().isEmpty())
		{
			//this.guiLeft = 160 + (this.width - this.xSize - 200) / 2;
			this.guiLeft = (this.width - this.xSize) / 2;
			this.hasEffect = true;
		}

		buttonList.clear();
		buttonList.add(new GuiInventoryButton(0, guiLeft+176, guiTop + 3, 25, 20, 
				0, 86, 25, 20, StringUtil.localize("gui.Inventory.Inventory")));
		buttonList.add(new GuiInventoryButton(1, guiLeft+176, guiTop + 23, 25, 20, 
				25, 86, 25, 20, StringUtil.localize("gui.Inventory.Skills")));
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (guibutton.id == 1)
			Minecraft.getMinecraft().displayGuiScreen(new GuiSkills(this.player));
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		super.drawScreen(par1, par2, par3);
		this.xSize_lo = par1;
		this.ySize_lo = par2;
		if(hasEffect)
			displayDebuffEffects();
	}

	/**
	 * Displays debuff/potion effects that are currently being applied to the player
	 */
	private void displayDebuffEffects()
	{
		int var1 = this.guiLeft - 124;
		int var2 = this.guiTop;
		Collection var4 = this.mc.thePlayer.getActivePotionEffects();

		if (!var4.isEmpty())
		{
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			GL11.glDisable(GL11.GL_LIGHTING);
			int var6 = 33;

			if (var4.size() > 5)
				var6 = 132 / (var4.size() - 1);

			for (Iterator var7 = this.mc.thePlayer.getActivePotionEffects().iterator(); var7.hasNext(); var2 += var6)
			{
				PotionEffect var8 = (PotionEffect)var7.next();
				Potion var9 = Potion.potionTypes[var8.getPotionID()] instanceof TFCPotion ? 
						((TFCPotion) Potion.potionTypes[var8.getPotionID()]) : 
							Potion.potionTypes[var8.getPotionID()];
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						TFC_Core.bindTexture(InventoryEffectsTex);
						this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

						if (var9.hasStatusIcon())
						{
							int var10 = var9.getStatusIconIndex();
							this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var10 % 8 * 18, 198 + var10 / 8 * 18, 18, 18);
						}

						String var12 = StatCollector.translateToLocal(var9.getName());

						if (var8.getAmplifier() == 1)
							var12 = var12 + " II";
						else if (var8.getAmplifier() == 2)
							var12 = var12 + " III";
						else if (var8.getAmplifier() == 3)
							var12 = var12 + " IV";

						this.fontRenderer.drawStringWithShadow(var12, var1 + 10 + 18, var2 + 6, 16777215);
						String var11 = Potion.getDurationString(var8);
						this.fontRenderer.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
			}
		}
	}
}
