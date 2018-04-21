import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import reflect_tools.Alias;
import reflect_tools.SingleReflector;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class RectangleTest extends MockIOTest
{

    private static final SingleReflector reflector = new SingleReflector("Rectangle");

    private static Class RectangleClass;
    private static Field RectangleWidth, RectangleHeight;
    private static Method RectangleGetWidth, RectangleSetWidth,
            RectangleGetHeight, RectangleSetHeight,
            RectangleToString, RectangleEquals, RectangleCompareTo,
            RectangleGetArea, RectangleGetPerimeter;
    private static Constructor RectangleDefault, RectangleFull;


    private static void setup_reflector()
    {
        reflector.init_fields("width", "height");

        reflector.init_method("getWidth");
        reflector.init_method("getHeight");
        reflector.init_method("getArea");
        reflector.init_method("getPerimeter");

        reflector.init_method("setWidth", int.class);
        reflector.init_method("setHeight", int.class);

        reflector.init_method("toString");
        reflector.init_method("equals", reflector.fetch_class());
        reflector.init_method("compareTo", reflector.fetch_class());

        reflector.init_constructor();
        reflector.init_constructor("full", int.class, int.class);

        reflector.learn();
    }

    private static void setup_links()
    {
        RectangleClass = reflector.fetch_class();
        RectangleDefault = reflector.fetch_constructor(Alias.fake);
        RectangleFull = reflector.fetch_constructor(Alias.from_nick("full"));

        RectangleWidth = reflector.fetch_field("width");
        RectangleHeight = reflector.fetch_field("height");

        RectangleGetWidth = reflector.fetch_method(Alias.build("getWidth"));
        RectangleSetWidth = reflector.fetch_method(Alias.build("setWidth"));
        RectangleGetHeight = reflector.fetch_method(Alias.build("getHeight"));
        RectangleSetHeight = reflector.fetch_method(Alias.build("setHeight"));

        RectangleGetArea = reflector.fetch_method(Alias.build("getArea"));
        RectangleGetPerimeter = reflector.fetch_method(Alias.build("getPerimeter"));

        RectangleEquals = reflector.fetch_method(Alias.build("equals"));
        RectangleCompareTo = reflector.fetch_method(Alias.build("compareTo"));
        RectangleToString = reflector.fetch_method(Alias.build("toString"));
    }

    @BeforeClass
    public static void setupClass()
    {
        setup_reflector();
        setup_links();
    }

    @Before
    public void setup()
    {
        reflector.inspect_fields();
    }

    @After
    public void tearDown()
    { reflector.restore_fields(); }

    @Test
    public void test_default_rectangle() throws Exception
    {
        Object dut = RectangleDefault.newInstance();
        assertThat(dut, is(notNullValue()));
        int actual_width = (int) reflector.fetch_field("width").get(dut);
        int actual_height = (int) reflector.fetch_field("height").get(dut);
        assertThat(actual_width, is(equalTo(0)));
        assertThat(actual_height, is(equalTo(0)));
    }

    @Test
    public void test_fields() throws Exception
    {
        assertThat(RectangleWidth, is(notNullValue()));
        assertThat(RectangleHeight, is(notNullValue()));
        assertThat(RectangleWidth.getType(), is(equalTo(int.class)));
        assertThat(RectangleHeight.getType(), is(equalTo(int.class)));

        int RectangleWidthModifiers = reflector.fetch_field("width").getModifiers();
        int RectangleHeightModifiers = reflector.fetch_field("height").getModifiers();

        assertTrue(Modifier.isPrivate(RectangleWidthModifiers) || Modifier.isProtected(RectangleWidthModifiers));
        assertTrue(Modifier.isPrivate(RectangleHeightModifiers) || Modifier.isProtected(RectangleHeightModifiers));
    }

    @Test
    public void test_accessors_exist() throws Exception
    {
        assertNotNull(RectangleGetWidth);
        assertNotNull(RectangleGetHeight);
    }

    @Test
    public void test_mutators_exist() throws Exception
    {
        assertNotNull(RectangleSetWidth);
        assertNotNull(RectangleSetHeight);
    }

    private Object new_rectangle(int width, int height) throws Exception
    {
        Object rectangle = RectangleDefault.newInstance();
        RectangleWidth.set(rectangle, width); RectangleHeight.set(rectangle, height);
        return rectangle;
    }

    @Test
    public void test_get_area() throws Exception
    {
        assertNotNull(RectangleGetArea);

        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0}, {1,1}, {3,4}};
        final int[] expected_output = {0, 1, 12};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i];
            int expected = expected_output[i];
            Object dut = new_rectangle(input[0], input[1]);
            int actual = (int) RectangleGetArea.invoke(dut);
            assertThat(actual, is(equalTo(expected)));
        }
    }

    @Test
    public void test_get_perimeter() throws Exception
    {
        assertNotNull(RectangleGetPerimeter);

        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0}, {1,1}, {3,4}};
        final int[] expected_output = {0, 4, 14};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i];
            int expected = expected_output[i];
            Object dut = new_rectangle(input[0], input[1]);
            int actual = (Integer) RectangleGetPerimeter.invoke(dut);
            assertThat(actual, is(equalTo(expected)));
        }
    }

    private String rectangle_to_string(Object object) throws Exception
    {
        int width = (int) RectangleWidth.get(object);
        int height = (int) RectangleHeight.get(object);
        return String.format("{type: Rectangle, width: %d, height: %d}", width, height);
    }

    @Test
    public void test_to_string() throws Exception
    {
        assertNotNull(RectangleToString);

        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0}, {1,1}, {3,4}};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i];
            Object dut = new_rectangle(input[0], input[1]);
            String actual = (String) RectangleToString.invoke(dut);
            assertThat(actual, is(equalTo(rectangle_to_string(dut))));
        }
    }

    @Test
    public void test_equals() throws Exception
    {
        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0, 0,0}, {1,1, 1,1}, {3,4, 3,4}};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i];
            Object rectangle1 = new_rectangle(input[0], input[1]);
            Object rectangle2 = new_rectangle(input[2], input[3]);
            assertTrue((boolean) RectangleEquals.invoke(rectangle1, rectangle2));
            // assertThat(rectangle1, is(equalTo(rectangle2)));
        }
    }

    @Test
    public void test_not_equals() throws Exception
    {
        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0, 0,1}, {1,1, 2,1}, {3,4, 4,3}};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i];
            Object rectangle1 = new_rectangle(input[0], input[1]);
            Object rectangle2 = new_rectangle(input[2], input[3]);
            assertFalse((boolean) RectangleEquals.invoke(rectangle1, rectangle2));
            // assertThat(rectangle1, is(not(equalTo(rectangle2))));
        }
    }





//    @Test
//    public void test_default_rectangle()
//    {
//        Rectangle dut = new Rectangle();
//        String y = get_mock_output();
//        String e = build_output("hello world");
//        assertEquals(e, y);
//    }

}