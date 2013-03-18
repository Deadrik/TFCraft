package bioxx.importers;

import java.util.ArrayList;

public class FaceGroup
{
    public ArrayList Faces;
    public String GroupName;
    
    public FaceGroup(String name)
    {
        GroupName = name;
        Faces = new ArrayList<Face>();
    }
}
