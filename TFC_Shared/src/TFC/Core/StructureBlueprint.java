package TFC.Core;

import java.util.Arrays;

import net.minecraft.world.World;

public class StructureBlueprint
{
	public int[][] layout;
	public String Name;
	public StructureBlueprint(String n, int[][] l) 
	{
		Name = n;
		layout = l;
	}

	public boolean GetFinished(World world, int x, int y, int z)
	{
		int x1 = x-1;
		int z1 = z-1;
		int[][] lay = layout;
		int[][] tempLayout = new int[3][3];
		for (int h = 0; h < 4; h++)
		{
			for (int i = 0; i < layout.length; i++)
			{
				for (int j = 0; j < layout[i].length; j++)
				{
					if(lay[i][j]!= -1)
					{
						tempLayout[i][j] = world.getBlockId(x1 + i, y+1, z1 + j);

					}
				}
			}
			if(Arrays.equals(tempLayout[0], lay[0]) && Arrays.equals(tempLayout[1], lay[1]) && Arrays.equals(tempLayout[2], lay[2]))
			{
				//				Minecraft mc = ModLoader.getMinecraftInstance();
				//				mc.ingameGUI.addChatMessage("Blueprint true;");
				return true;
			} else {
				lay = Rotate90(lay);
			}		
		}
		return false;
	}

	private int[][] Rotate90(int[][] d)
	{
		int[][] w = new int[3][3];

		w[0][0] = d[0][2]; w[1][0] = d[0][1]; w[2][0] = d[0][0];
		w[0][1] = d[1][2]; w[1][1] = d[1][1]; w[2][1] = d[1][0];
		w[0][2] = d[2][2]; w[1][2] = d[2][1]; w[2][2] = d[2][0];

		return w;
	}
}
