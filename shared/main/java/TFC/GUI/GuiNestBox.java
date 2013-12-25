package TFC.GUI;

import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import TFC.Reference;
import TFC.Containers.ContainerNestBox;
import TFC.TileEntities.TENestBox;

public class GuiNestBox extends GuiContainerTFC
{
	public static ResourceLocation GuiTex = new ResourceLocation(Reference.ModID, Reference.AssetPathGui + "gui_nestbox.png");

	public GuiNestBox(InventoryPlayer inventoryplayer, TENestBox te, World world, int i, int j, int k)
	{
		super(new ContainerNestBox(inventoryplayer, te, world, i, j, k), 175, 49);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j)
	{
		this.drawGui(GuiTex);
	}
}
