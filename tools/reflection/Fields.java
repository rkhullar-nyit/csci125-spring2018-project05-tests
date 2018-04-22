package tools.reflection;

import java.lang.reflect.Field;
import java.util.HashMap;

class Fields
{
    protected final HashMap<String, Field> map = new HashMap<>();
    protected final HashMap<String, Boolean> access = new HashMap<>();
    protected final HashMap<String, Integer> modifiers = new HashMap<>();

    protected void learn_access()
    {
        map.forEach((name, field) -> access.put(name, field.isAccessible()));
    }

    protected void inspect()
    {
        map.forEach((name, field) -> field.setAccessible(true));
    }

    protected void restore()
    {
        map.forEach((name, field) -> field.setAccessible(access.get(name)));
    }

    protected void learn_modifiers()
    {
        map.forEach((name, field) -> modifiers.put(name, field.getModifiers()));
    }

}
