package tools.reflection;

import java.lang.reflect.Array;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public abstract class Reflector
{
    static final String default_nick = "default";

    static Class load_class(String name)
    {
        try
        {
            return Class.forName(name);
        }
        catch (ClassNotFoundException err)
        {
            return null;
        }
    }

    static Field load_field(String class_name, String field_name)
    {
        try
        {
            Class clazz = load_class(class_name);
            return clazz.getDeclaredField(field_name);
        }
        catch (NoSuchFieldException err)
        {
            return null;
        }
    }

    static Method load_method(String class_name, String method_name, Class...types)
    {
        try
        {
            Class clazz = load_class(class_name);
            return clazz.getDeclaredMethod(method_name, types);
        }
        catch (NoSuchMethodException err)
        {
            return null;
        }
    }

    static Constructor load_constructor(String class_name, Class...types)
    {
        try
        {
            Class clazz = load_class(class_name);
            return clazz.getDeclaredConstructor(types);
        }
        catch (NoSuchMethodException err)
        {
            return null;
        }
    }

    abstract void learn();
    abstract void inspect();
    abstract void restore();

    public static Class as_array(Class clazz)
    {
        return clazz != null ? Array.newInstance(clazz, 0).getClass() : null;
    }
}

