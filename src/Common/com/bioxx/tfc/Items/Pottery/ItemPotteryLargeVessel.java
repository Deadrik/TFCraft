package com.bioxx.tfc.Items.Pottery;

import com.bioxx.tfc.api.Enums.EnumSize;
import com.bioxx.tfc.api.Enums.EnumWeight;

public class ItemPotteryLargeVessel extends ItemPotteryBase
{

	public ItemPotteryLargeVessel() 
	{
		super();
		this.metaNames = new String[]{"Clay Vessel [Large]", "Ceramic Vessel [Large]"};
		this.setWeight(EnumWeight.HEAVY);
		this.setSize(EnumSize.HUGE);
	}
}
