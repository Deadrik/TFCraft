package com.bioxx.tfc.api.Util;

public class ByteCoord 
{
	public byte x, y, z;

	public ByteCoord(byte x, byte y, byte z)
	{
		this.x = x;
		this.y = y;
		this.z = z;
	}

	public ByteCoord(int x, int y, int z)
	{
		this.x = (byte)x;
		this.y = (byte)y;
		this.z = (byte)z;
	}

	@Override
	public boolean equals(Object o)
	{
		return o instanceof ByteCoord &&((ByteCoord) o).x == x &&
				((ByteCoord)o).y == y &&
				((ByteCoord) o).z == z;
	}
}
