package reflect_tools;

import java.util.HashMap;

public class MultiReflector extends Reflector
{
    protected final HashMap<String, SingleReflector> map = new HashMap<>();

    public boolean add(String class_name)
    {
        try
        {
            SingleReflector singleReflector = new SingleReflector(class_name);
            map.put(class_name, singleReflector);
            return true;
        }
        catch (Exception err)
        {
            return false;
        }
    }
}
