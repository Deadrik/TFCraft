package TFC.GUI;

import java.util.Collection;
import java.util.Iterator;

import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.GL11;

import TFC.Core.TFC_Core;
import TFC.Food.TFCPotion;

public class GuiInventoryTFC  extends GuiInventory
{	
	public GuiInventoryTFC(EntityPlayer player) 
	{
		super(player);
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
			{
				var6 = 132 / (var4.size() - 1);
			}

			for (Iterator var7 = this.mc.thePlayer.getActivePotionEffects().iterator(); var7.hasNext(); var2 += var6)
			{
				PotionEffect var8 = (PotionEffect)var7.next();
				Potion var9 = Potion.potionTypes[var8.getPotionID()] instanceof TFCPotion ? 
						((TFCPotion) Potion.potionTypes[var8.getPotionID()]) : 
							Potion.potionTypes[var8.getPotionID()];
						GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
						TFC_Core.bindTexture(field_110408_a);
						this.drawTexturedModalRect(var1, var2, 0, 166, 140, 32);

						if (var9.hasStatusIcon())
						{
							int var10 = var9.getStatusIconIndex();
							this.drawTexturedModalRect(var1 + 6, var2 + 7, 0 + var10 % 8 * 18, 198 + var10 / 8 * 18, 18, 18);
						}

						String var12 = StatCollector.translateToLocal(var9.getName());

						if (var8.getAmplifier() == 1)
						{
							var12 = var12 + " II";
						}
						else if (var8.getAmplifier() == 2)
						{
							var12 = var12 + " III";
						}
						else if (var8.getAmplifier() == 3)
						{
							var12 = var12 + " IV";
						}

						this.fontRenderer.drawStringWithShadow(var12, var1 + 10 + 18, var2 + 6, 16777215);
						String var11 = Potion.getDurationString(var8);
						this.fontRenderer.drawStringWithShadow(var11, var1 + 10 + 18, var2 + 6 + 10, 8355711);
			}
		}
	}
}
