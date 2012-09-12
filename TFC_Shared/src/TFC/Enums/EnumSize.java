package TFC.Enums;

public enum EnumSize 
{
	TINY("TINY", /*size*/64),
	
	VERYSMALL("VERYSMALL", /*size*/32),
	
	SMALL("SMALL", /*size*/16),
	
	MEDIUM("MEDIUM", /*size*/8),
	
	LARGE("LARGE", /*size*/4),
	
	VERYLARGE("VERYLARGE", /*size*/2),
	
	EXTREME("EXTREME", /*size*/1);

	public final int stackSize;

	private static final EnumSize Sizes[] = new EnumSize[] {
		TINY,SMALL,MEDIUM};

	private EnumSize(String s, int i)
	{
		stackSize = i;
	}
}
