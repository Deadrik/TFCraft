package TFC.GUI;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.Containers.ContainerSpecialCrafting;
import TFC.Core.TFC_Core;
import TFC.Core.Player.PlayerManagerTFC;
import TFC.Handlers.PacketHandler;

public class GuiKnapping extends GuiContainer
{
	private EntityPlayer player;

	public GuiKnapping(InventoryPlayer inventoryplayer,ItemStack is, World world, int x, int y, int z)
	{
		super(new ContainerSpecialCrafting(inventoryplayer, is, world, x, y, z));
		player = inventoryplayer.player;
	}

	@Override
	public void onGuiClosed()
	{
		PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface = new boolean[25];
		super.onGuiClosed();
	}

	@Override
	public void initGui()
	{
		super.initGui();
		boolean[] knappingInterface = PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface;

		buttonList.clear();

		for (int y = 0; y < 5; y++)
		{
			for (int x = 0; x < 5; x++)
			{
				buttonList.add(new GuiKnappingButton(x+(y*5), guiLeft+(x*16)+5, guiTop + (y*16)-5, 16, 16));
				if(PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface[(y*5)+x]) {
					resetButton((y*5)+x);
				}
			}
		}
	}

	@Override
	protected void actionPerformed(GuiButton guibutton)
	{
		resetButton(guibutton.id);
		TerraFirmaCraft.proxy.sendCustomPacket(createUpdatePacket(guibutton.id));
	}

	public void resetButton(int id)
	{
		if(PlayerManagerTFC.getInstance().getClientPlayer().specialCraftingTypeAlternate == null) {
			((GuiKnappingButton) this.buttonList.get(id)).drawButton = false;
		}
		PlayerManagerTFC.getInstance().getClientPlayer().knappingInterface[id] = true;
		((GuiKnappingButton) this.buttonList.get(id)).enabled = false;
	}

	public Packet createUpdatePacket(int id)
	{
		ByteArrayOutputStream bos=new ByteArrayOutputStream(140);
		DataOutputStream dos=new DataOutputStream(bos);

		try {
			dos.writeByte(PacketHandler.Packet_Update_Knapping);
			dos.writeByte(id);
		} catch (IOException e) {
		}

		Packet250CustomPayload pkt=new Packet250CustomPayload();
		pkt.channel="TerraFirmaCraft";
		pkt.data = bos.toByteArray();
		pkt.length= bos.size();
		pkt.isChunkDataPacket=false;
		return pkt;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int p, int j)
	{
		TFC_Core.bindTexture(new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_knapping.png"));
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
		int w = (width - 176) / 2;
		int h = (height - 184) / 2;
		drawTexturedModalRect(w, h, 0, 0, 175, 183);
	}
}
