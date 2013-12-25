package TFC.Handlers;

import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.item.ItemTossEvent;

public class PlayerTossEventHandler {
	
	@ForgeSubscribe
	public void onPlayerTossEvent(ItemTossEvent event)
	{
		if(event.entityItem == null)
			event.setCanceled(true);
	}

}
