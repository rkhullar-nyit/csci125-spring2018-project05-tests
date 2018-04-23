package tools.reflection;

import java.lang.reflect.Method;
import java.util.HashMap;

class Methods
{
    protected final HashMap<Alias, Method> map = new HashMap<Alias, Method>();
    protected final HashMap<Alias, Boolean> access = new HashMap<>();
    protected final HashMap<Alias, Integer> modifiers = new HashMap<>();


    protected void learn_access()
    {
        map.forEach((alias, method) -> access.put(alias, method.isAccessible()));
    }

    protected void learn_modifiers()
    {
        map.forEach((alias, method) -> modifiers.put(alias, method.getModifiers()));
    }

    protected void inspect()
    {
        map.forEach((alias, method) -> method.setAccessible(true));
    }

    protected void restore()
    {
        map.forEach((alias, method) -> method.setAccessible(access.get(alias)));
    }


}
