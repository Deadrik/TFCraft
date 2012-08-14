package TFC.Core;

public class Manager
{
    protected static final Manager instance = new Manager();
    public static final Manager getInstance()
    {
        return instance;
    }
}
