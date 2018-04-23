package tools.reflection;

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

    public SingleReflector fetch(String name)
    {
        return map.get(name);
    }

    void learn() { map.forEach((name, reflector) -> reflector.learn()); }
    void inspect() { map.forEach((name, reflector) -> reflector.inspect()); }
    void restore() { map.forEach((name, reflector) -> reflector.restore()); }
}
