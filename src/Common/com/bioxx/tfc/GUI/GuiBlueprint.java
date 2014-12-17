package com.bioxx.tfc.GUI;

import com.bioxx.tfc.Handlers.Network.ItemNBTPacket;
import com.bioxx.tfc.Items.ItemBlueprint;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.*;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.TerraFirmaCraft;
import com.bioxx.tfc.Core.TFC_Core;
import com.bioxx.tfc.Handlers.Network.AbstractPacket;
import com.bioxx.tfc.Handlers.Network.ItemRenamePacket;

import java.util.Arrays;
import java.util.LinkedList;

public class GuiBlueprint extends GuiScreen
{
	World world;
	EntityPlayer player;
	ItemStack stack;

	private GuiTextField nameTextField;
	private static final int done_bt_ind = 0;
	private static final int cancel_bt_ind = 1;

	private static final String done_bt_name = "gui.done";
	private static final String cancel_bt_name = "gui.cancel";


	/** The X size of the inventory window in pixels. */
	protected int xSize = 200;

	/** The Y size of the inventory window in pixels. */
	protected int ySize = 172;
	/**
	 * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiLeft() { return (this.width - this.xSize) / 2; }

	/**
	 * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiTop() { return (this.height - this.ySize) / 2; }

	public GuiBlueprint(EntityPlayer p, World world, int i, int j, int k)
	{
		//super(new ContainerBlueprint(p, world, i, j, k));
		this.world = world;
		player = p;
		stack = player.inventory.getCurrentItem();
	}

	@Override
	public void updateScreen()
	{
		this.nameTextField.updateCursorCounter();
	}

	@Override
	public void onGuiClosed()
	{
		Keyboard.enableRepeatEvents(false);
	}

	@Override
	public void initGui()
	{
		super.initGui();

		int nameTop = guiTop() + 10 + this.fontRendererObj.FONT_HEIGHT + 4;
		int namePrefixWidth = fontRendererObj.getStringWidth(ItemBlueprint.suffix) + 4;
		this.nameTextField = new GuiTextField(fontRendererObj, guiLeft() + 10 + namePrefixWidth, nameTop, 180 - namePrefixWidth, 20);
		this.nameTextField.setFocused(true);
		this.nameTextField.setCanLoseFocus(false);
		if (!stack.hasTagCompound()
						|| stack.stackTagCompound.getString(ItemBlueprint.tag_item_name).isEmpty())
		{
			this.nameTextField.setText("name_it");
			this.nameTextField.setCursorPosition(0);
			this.nameTextField.setSelectionPos(this.nameTextField.getText().length());
		}
		else
		{
			this.nameTextField.setEnabled(false);
			this.nameTextField.setText(stack.stackTagCompound.getString(ItemBlueprint.tag_item_name));
		}

		Keyboard.enableRepeatEvents(true);

		this.buttonList.clear();
		this.labelList.clear();

		int buttonsHeight = 20;

		// done
		int doneWidth = fontRendererObj.getStringWidth(StatCollector.translateToLocal(done_bt_name)) + 20;
		this.buttonList.add(new GuiButton(
						done_bt_ind,
						(width + xSize) / 2 - 10 - doneWidth, (height + ySize) / 2 - 10 - buttonsHeight,
						doneWidth, buttonsHeight,
						StatCollector.translateToLocal(done_bt_name)
		));

		// cancel
		int cancelWidth = fontRendererObj.getStringWidth(StatCollector.translateToLocal(cancel_bt_name)) + 20;
		this.buttonList.add(new GuiButton(
						cancel_bt_ind,
						(width + xSize) / 2 - 10 - doneWidth - cancelWidth - 4, (height + ySize) / 2 - 10 - buttonsHeight,
						cancelWidth, buttonsHeight,
						StatCollector.translateToLocal(cancel_bt_name)
		));
	}

	public static RenderItem itemRenderer = new RenderItem();

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.nameTextField.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		this.nameTextField.textboxKeyTyped(par1, par2);
		((GuiButton)this.buttonList.get(done_bt_ind)).enabled = this.nameTextField.getText().trim().length() > 0;
		if (par1 == 13)
			this.actionPerformed((GuiButton)this.buttonList.get(done_bt_ind));
	}

	@Override
	public boolean doesGuiPauseGame()
	{
		return false;
	}

	@Override
	public void drawCenteredString(FontRenderer fontrenderer, String s, int i, int j, int k)
	{
		fontrenderer.drawString(s, i - fontrenderer.getStringWidth(s) / 2, j, k);
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		if (!world.isRemote)
			return;

		if (guibutton.id == done_bt_ind) {
			stack.stackTagCompound.setBoolean(ItemBlueprint.tag_completed, true);
			stack.stackTagCompound.setString(ItemBlueprint.tag_item_name, nameTextField.getText());

			AbstractPacket	pkt = new ItemNBTPacket(stack.stackTagCompound);
			((ItemNBTPacket)pkt)
							.addAcceptedTag(ItemBlueprint.tag_completed)
							.addAcceptedTag(ItemBlueprint.tag_item_name);
			TerraFirmaCraft.packetPipeline.sendToServer(pkt);

			Minecraft.getMinecraft().displayGuiScreen(null);
		}
		else if (guibutton.id == cancel_bt_ind) {
			if (!stack.stackTagCompound.getBoolean(ItemBlueprint.tag_completed)) {
				stack.setTagCompound(new NBTTagCompound());
				AbstractPacket pkt = new ItemNBTPacket(stack.stackTagCompound);
				((ItemNBTPacket)pkt)
								.addRemoveTag(ItemBlueprint.tag_completed)
								.addRemoveTag(ItemBlueprint.tag_item_name)
								.addRemoveTag(ItemBlueprint.tag_data);
				TerraFirmaCraft.packetPipeline.sendToServer(pkt);
			}

			Minecraft.getMinecraft().displayGuiScreen(null);
		}
	}

	@Override
	public void drawScreen(int par1, int par2, float par3)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_blueprint.png"));

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int left = guiLeft();
		int top = guiTop();
		drawTexturedModalRect(left, top, 0, 0, xSize, ySize);

		drawCenteredString(fontRendererObj, StatCollector.translateToLocal("gui.Blueprint"), this.width / 2, top + 10, 0x000000);


		int axesNameLeft = left + 10;
		int topShift = (20 - this.fontRendererObj.FONT_HEIGHT) / 2;

		fontRendererObj.drawString(ItemBlueprint.suffix, axesNameLeft, top + topShift, 0x000000);
		this.nameTextField.drawTextBox();

		super.drawScreen(par1, par2, par3);
	}

}
