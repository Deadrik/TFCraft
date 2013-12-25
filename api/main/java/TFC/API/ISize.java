package TFC.API;

import TFC.API.Enums.EnumSize;
import TFC.API.Enums.EnumWeight;

public interface ISize 
{
	public EnumSize getSize();
	
	public EnumWeight getWeight();
	
	/**
	 * Allows setting weather or not this item can stack regardless of the size/weight values
	 * @return Can stacksize exceed 1
	 */
	public boolean canStack();
}
