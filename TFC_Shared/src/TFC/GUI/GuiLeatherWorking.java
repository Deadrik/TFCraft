package TFC.GUI;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;

import org.lwjgl.opengl.GL11;

import TFC.Reference;
import TFC.TerraFirmaCraft;
import TFC.Containers.ContainerLeatherWorking;
import TFC.Handlers.PacketHandler;

public class GuiLeatherWorking extends GuiContainer
{
    public GuiLeatherWorking(InventoryPlayer inventoryplayer,ItemStack is, World world, int x, int y, int z)
    {
        super(new ContainerLeatherWorking(inventoryplayer, is, world, x, y, z));
    }

    @Override
	public void onGuiClosed()
    {
        super.onGuiClosed();
    }
    
    @Override
	public void initGui()
	{
		super.initGui();
		buttonList.clear();
		
		for (int y = 0; y < 5; y++)
		{
			for (int x = 0; x < 5; x++)
			{
				buttonList.add(new GuiKnappingButton(x+(y*5), guiLeft+(x*16)+5, guiTop + (y*16)-5, 16, 16));
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
    	((GuiKnappingButton) this.buttonList.get(id)).drawButton = false;
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
    	this.mc.renderEngine.bindTexture(Reference.AssetPathGui + "gui_knapping.png");
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 0.5F);
        int w = (width - 176) / 2;
        int h = (height - 184) / 2;
        drawTexturedModalRect(w, h, 0, 0, 175, 183);
    }
}
