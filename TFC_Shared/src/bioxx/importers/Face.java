package bioxx.importers;

public class Face
{
    public int[][] vertices;
    public int vertCount;
    
    public Face(int[] v1,int[] v2,int[] v3,int[] v4)
    {
        vertices = new int[][]{v1,v2,v3,v4};
        vertCount = 4;
    }
    public Face( int[] v1,int[] v2,int[] v3)
    {
        vertices = new int[][]{v1,v2,v3};
        vertCount = 3;
    }
}
