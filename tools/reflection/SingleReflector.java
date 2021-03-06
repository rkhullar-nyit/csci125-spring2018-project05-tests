package tools.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class SingleReflector extends Reflector
{

    protected Class clazz;
    protected final Methods methods = new Methods();
    protected final Fields fields = new Fields();
    protected final Constructors constructors = new Constructors();

    public Class fetch_class()
    { return clazz; }

    public Field fetch_field(String name)
    { return fields.map.get(name); }

    public Method fetch_method(Alias alias)
    { return methods.map.get(alias); }

    public Constructor fetch_constructor(Alias alias)
    { return constructors.map.get(alias); }

    public SingleReflector() {}

    public SingleReflector(String class_name)
    { init_clazz(class_name); }

    public Field load_field(String field_name)
    { return clazz != null ? load_field(clazz.getName(), field_name) : null; }

    public Method load_method(String method_name, Class...types)
    { return clazz != null ? load_method(clazz.getName(), method_name, types) : null; }

    public Constructor load_constructor(Class...types)
    { return clazz != null ? load_constructor(clazz.getName(), types) : null; }

    public boolean init_clazz(String name)
    { return (clazz = load_class(name)) != null; }

    public boolean init_fields(String...names)
    {
        boolean success = true;
        for (String name: names)
        {
            Field field = load_field(name);
            boolean flag = field != null;
            if(flag) fields.map.put(name, field);
            success &= flag;
        }
        return success;
    }

    public boolean init_method(String name, String nick, Class...types)
    {
        Alias alias = new Alias(name, nick);
        Method method = load_method(name, types);
        boolean success = method != null;
        if(success) methods.map.put(alias, method);
        return success;
    }

    public boolean init_method(String name, Class...types)
    {
        return init_method(name, default_nick, types);
    }

    public boolean init_constructor(String nick, Class...types)
    {
        Alias alias = new Alias(null, nick);
        Constructor constructor = load_constructor(types);
        boolean success = constructor != null;
        if(success) constructors.map.put(alias, constructor);
        return success;
    }

    public boolean init_constructor(Class...types)
    {
        return init_constructor(default_nick, types);
    }

    public void learn()
    {
        fields.learn_access();
        fields.learn_modifiers();
        methods.learn_access();
    }

    public void inspect()
    {
        fields.inspect();
        methods.inspect();
    }

    public void restore()
    {
        fields.restore();
        methods.restore();
    }

}
