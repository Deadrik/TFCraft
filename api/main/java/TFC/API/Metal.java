package TFC.API;


public class Metal 
{
	public String Name;
	public int MeltedItemID;
	public int IngotID;
	
	public Metal(String name)
	{
		Name = name;
	}
	
	public Metal(String name, int mID, int iID)
	{
		this(name);
		MeltedItemID = mID;
		IngotID = iID;
	}
}
