import tools.reflection.*;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class RectangleSorterTestBase extends ReflectTest
{

    protected Class RectangleClass, RectangleSorterClass, RectangleArrayClass;
    protected Field RectangleWidth, RectangleHeight, RectangleSorterArray;
    protected Method RectangleSorterMin, RectangleSorterSwap, RectangleSorterSort;
    protected Constructor RectangleSorterFull;

    protected ReflectTestAdapter buildReflectAdapter()
    {
        return new ReflectTestAdapter()
        {
            public void init()
            {
                reflector = new MultiReflector();
            }

            public void load()
            {
                MultiReflector multi = (MultiReflector) reflector;
                multi.add("Rectangle");
                multi.add("RectangleSorter");

                SingleReflector sorter = multi.fetch("RectangleSorter");
                sorter.init_fields("array");
                sorter.init_method("min", int.class);
                sorter.init_method("swap", int.class, int.class);
                sorter.init_method("sort");

                RectangleClass = multi.fetch("Rectangle").fetch_class();
                RectangleArrayClass = Reflector.as_array(RectangleClass);
                sorter.init_constructor("full", RectangleArrayClass);

                SingleReflector rectangle = multi.fetch("Rectangle");
                rectangle.init_fields("width", "height");
            }

            public void link()
            {
                MultiReflector multi = (MultiReflector) reflector;
                SingleReflector primary = multi.fetch("RectangleSorter");
                SingleReflector rectangle = multi.fetch("Rectangle");

                RectangleClass = rectangle.fetch_class();
                RectangleSorterClass = primary.fetch_class();

                RectangleWidth = rectangle.fetch_field("width");
                RectangleHeight = rectangle.fetch_field("height");

                RectangleSorterArray = primary.fetch_field("array");
                RectangleSorterMin = primary.fetch_method(Alias.build("min"));
                RectangleSorterSwap = primary.fetch_method(Alias.build("swap"));
                RectangleSorterSort = primary.fetch_method(Alias.build("sort"));
                RectangleSorterFull = primary.fetch_constructor(Alias.from_nick("full"));
            }
        };
    }
}
