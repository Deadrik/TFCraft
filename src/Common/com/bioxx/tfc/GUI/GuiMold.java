package com.bioxx.tfc.GUI;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import com.bioxx.tfc.Reference;
import com.bioxx.tfc.Containers.ContainerMold;
import com.bioxx.tfc.Core.Player.PlayerInfo;
import com.bioxx.tfc.Core.Player.PlayerManagerTFC;

public class GuiMold extends GuiContainerTFC
{
	public static ResourceLocation texture = new ResourceLocation(Reference.MOD_ID, Reference.ASSET_PATH_GUI + "gui_mold.png");
	private EntityPlayer player;

	public GuiMold(InventoryPlayer inventoryplayer, World world, int x, int y, int z)
	{
		super(new ContainerMold(inventoryplayer, world, x, y, z), 176, 85);
		player = inventoryplayer.player;
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		drawGui(texture);
	}

	@Override
	protected void drawForeground(int guiLeft, int guiTop)
	{
		drawTexturedModalRect(guiLeft, guiTop, 0, 0, xSize, this.getShiftedYSize());

		drawTexturedModalRect(guiLeft + 64, guiTop + 34, 176, 0, drawArrowScaled(22) + 1, 15);
	}

	private int drawArrowScaled(int scale)
	{
		PlayerInfo pi = PlayerManagerTFC.getInstance().getPlayerInfoFromPlayer(player);
		return (pi.moldTransferTimer == 1000 ? 0 : pi.moldTransferTimer * scale) / 100;
	}
}
