package TFC.Items.Pottery;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public class ItemPotteryLargeVessel extends ItemPotteryBase
{

    public ItemPotteryLargeVessel(int id) 
    {
        super(id);
        this.MetaNames = new String[]{"Clay Vessel [Large]", "Ceramic Vessel [Large]"};
    }
    	
	@Override
	public EnumSize getSize() 
	{
		return EnumSize.HUGE;
	}
	@Override
	public EnumWeight getWeight() 
	{
		return EnumWeight.HEAVY;
	}

}
