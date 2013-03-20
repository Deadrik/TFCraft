package TFC.GUI;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import net.minecraft.client.Minecraft;

import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import TFC.*;
import TFC.Containers.*;
import TFC.Core.TFC_Climate;
import TFC.Core.TFC_ItemHeat;
import TFC.Core.TFC_Time;
import TFC.Core.TFC_Settings;
import TFC.Handlers.PacketHandler;
import TFC.WorldGen.DataLayer;
import TFC.WorldGen.TFCBiome;
import TFC.WorldGen.TFCWorldChunkManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.entity.*;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.gui.inventory.*;
import net.minecraft.client.model.*;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.block.*;
import net.minecraft.block.material.*;
import net.minecraft.crash.*;
import net.minecraft.creativetab.*;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.effect.*;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.player.*;
import net.minecraft.entity.projectile.*;
import net.minecraft.inventory.*;
import net.minecraft.item.*;
import net.minecraft.nbt.*;
import net.minecraft.network.*;
import net.minecraft.network.packet.*;
import net.minecraft.pathfinding.*;
import net.minecraft.potion.*;
import net.minecraft.server.*;
import net.minecraft.src.ModLoader;
import net.minecraft.stats.*;
import net.minecraft.tileentity.*;
import net.minecraft.util.*;
import net.minecraft.village.*;
import net.minecraft.world.*;

public class GuiBlueprint extends GuiScreen
{
	private GuiTextField theGuiTextField;
	World world;
	int x;
	int z;
	EntityPlayer player;

	/** The X size of the inventory window in pixels. */
	protected int xSize = 219;

	/** The Y size of the inventory window in pixels. */
	protected int ySize = 103;
	/**
	 * Starting X position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiLeft;

	/**
	 * Starting Y position for the Gui. Inconsistent use for Gui backgrounds.
	 */
	protected int guiTop;

	public GuiBlueprint(EntityPlayer p, World world, int i, int j, int k)
	{
		//super(new ContainerBlueprint(p, world, i, j, k));
		this.world = world;
		this.guiLeft = (this.width - this.xSize) / 2;
		this.guiTop = (this.height - this.ySize) / 2;
		x = i;
		z = k;
		player = p;
	}

	@Override
	public void updateScreen()
	{
		this.theGuiTextField.updateCursorCounter();
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

		StringTranslate var1 = StringTranslate.getInstance();
		Keyboard.enableRepeatEvents(true);
		this.buttonList.clear();
		this.buttonList.add(new GuiButton(0, this.width / 2 - 100, guiTop + 180, var1.translateKey("gui.done")));
		//this.controlList.add(new GuiButton(1, this.width / 2 - 100, guiTop + 210, var1.translateKey("gui.cancel")));

		this.theGuiTextField = new GuiTextField(this.fontRenderer, this.width / 2 - 90, guiTop + 150, 180, 20);
		this.theGuiTextField.setFocused(true);
		this.theGuiTextField.setText("");
	}

	public static RenderItem itemRenderer = new RenderItem();

	@Override
	protected void mouseClicked(int par1, int par2, int par3)
	{
		super.mouseClicked(par1, par2, par3);
		this.theGuiTextField.mouseClicked(par1, par2, par3);
	}

	@Override
	protected void keyTyped(char par1, int par2)
	{
		this.theGuiTextField.textboxKeyTyped(par1, par2);
		((GuiButton)this.buttonList.get(0)).enabled = this.theGuiTextField.getText().trim().length() > 0;

		if (par1 == 13)
		{
			this.actionPerformed((GuiButton)this.buttonList.get(0));
		}
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
		if (guibutton.id == 0 && world.isRemote)
		{
			ItemStack stack = player.inventory.getCurrentItem();
			stack.stackTagCompound.setString("Name", theGuiTextField.getText());

			ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
			DataOutputStream dos=new DataOutputStream(bos);
			int x=0;
			int y=0;
			int z=0;
			try 
			{
				dos.writeByte(PacketHandler.Packet_Rename_Item);
				String n = theGuiTextField.getText();
				dos.writeUTF(n);

				TerraFirmaCraft.proxy.sendCustomPacket(setupCustomPacketData(bos.toByteArray(), bos.size()));
			} 
			catch (IOException e) 
			{

			}

			player.closeScreen();
		}
		else if (guibutton.id == 1 && world.isRemote)
		{
			player.closeScreen();
		}
	}

	public Packet setupCustomPacketData(byte[] data, int length)
	{
		Packet250CustomPayload pkt=new Packet250CustomPayload();
		pkt.channel="TerraFirmaCraft";
		pkt.data = data;
		pkt.length= length;
		pkt.isChunkDataPacket=true;
		return pkt;
	}

	@Override
    public void drawScreen(int par1, int par2, float par3)
    {
//	@Override
//	protected void drawGuiContainerBackgroundLayer(float var1, int var2,
//			int var3) 
//	{
		int k = mc.renderEngine.getTexture("/bioxx/gui_blueprint.png");

		int var4 = this.guiLeft;
		int var5 = this.guiTop;

		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		mc.renderEngine.bindTexture(k);
		int l = (width - xSize) / 2;
		int i1 = (height - ySize) / 2;
		drawTexturedModalRect(l, i1, 0, 0, xSize, ySize);

		drawCenteredString(fontRenderer,"Blueprint", this.width / 2, i1+8, 0x000000);        
		this.theGuiTextField.drawTextBox();
		
		super.drawScreen(par1, par2, par3);

	}

}
