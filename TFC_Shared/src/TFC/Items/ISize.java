package TFC.Items;

import TFC.Enums.EnumSize;
import TFC.Enums.EnumWeight;

public interface ISize 
{
	public EnumSize getSize();
	
	public EnumWeight getWeight();
	
	public boolean canStack();
}
