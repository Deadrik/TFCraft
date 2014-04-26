package TFC.Items.Pottery;

import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public class ItemPotteryLargeVessel extends ItemPotteryBase
{

	public ItemPotteryLargeVessel() 
	{
		super();
		this.MetaNames = new String[]{"Clay Vessel [Large]", "Ceramic Vessel [Large]"};
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.HUGE);
	}
}
