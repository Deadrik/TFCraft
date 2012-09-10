package TFC.Enums;

public enum EnumSize 
{
	TINY("TINY", /*size*/1),
	
	VERYSMALL("VERYSMALL", /*size*/2),
	
	SMALL("SMALL", /*size*/4),
	
	MEDIUM("MEDIUM", /*size*/8),
	
	LARGE("LARGE", /*size*/16),
	
	VERYLARGE("VERYLARGE", /*size*/32),
	
	EXTREME("EXTREME", /*size*/64);

	public final int size;

	private static final EnumSize Sizes[] = new EnumSize[] {
		TINY,SMALL,MEDIUM};

	private EnumSize(String s, int i)
	{
		size = i;
	}
}
