import tools.reflection.Alias;
import tools.reflection.ReflectTest;
import tools.reflection.ReflectTestAdapter;
import tools.reflection.SingleReflector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RectangleTestBase extends ReflectTest
{
    protected static Class RectangleClass;
    protected static Field RectangleWidth, RectangleHeight;
    protected static Method RectangleGetWidth, RectangleSetWidth,
            RectangleGetHeight, RectangleSetHeight,
            RectangleToString, RectangleEquals, RectangleCompareTo,
            RectangleGetArea, RectangleGetPerimeter;
    protected static Constructor RectangleDefault, RectangleFull;

    protected ReflectTestAdapter buildReflectAdapter()
    {

        return new ReflectTestAdapter()
        {
            public void init()
            { reflector = new SingleReflector(); }

            public void load()
            {
                SingleReflector single = (SingleReflector) reflector;

                single.init_clazz("Rectangle");
                single.init_fields("width", "height");

                single.init_method("getWidth");
                single.init_method("getHeight");
                single.init_method("getArea");
                single.init_method("getPerimeter");

                single.init_method("setWidth", int.class);
                single.init_method("setHeight", int.class);

                single.init_method("toString");
                single.init_method("equals", single.fetch_class());
                single.init_method("compareTo", single.fetch_class());

                single.init_constructor();
                single.init_constructor("full", int.class, int.class);
            }

            public void link()
            {
                SingleReflector single = (SingleReflector) reflector;

                RectangleClass = single.fetch_class();
                RectangleDefault = single.fetch_constructor(Alias.fake);
                RectangleFull = single.fetch_constructor(Alias.from_nick("full"));

                RectangleWidth = single.fetch_field("width");
                RectangleHeight = single.fetch_field("height");

                RectangleGetWidth = single.fetch_method(Alias.build("getWidth"));
                RectangleSetWidth = single.fetch_method(Alias.build("setWidth"));
                RectangleGetHeight = single.fetch_method(Alias.build("getHeight"));
                RectangleSetHeight = single.fetch_method(Alias.build("setHeight"));

                RectangleGetArea = single.fetch_method(Alias.build("getArea"));
                RectangleGetPerimeter = single.fetch_method(Alias.build("getPerimeter"));

                RectangleEquals = single.fetch_method(Alias.build("equals"));
                RectangleCompareTo = single.fetch_method(Alias.build("compareTo"));
                RectangleToString = single.fetch_method(Alias.build("toString"));
            }
        };
    }

}
