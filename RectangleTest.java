import org.junit.Test;


import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class RectangleTest extends RectangleTestBase
{

    @Test
    public void test_canary()
    {
        assertThat(1, is(equalTo(1)));
    }

    @Test
    public void test_fields() throws Exception
    {
        assertThat(RectangleWidth, is(notNullValue()));
        assertThat(RectangleHeight, is(notNullValue()));

        assertThat(RectangleWidth.getType(), is(equalTo(int.class)));
        assertThat(RectangleHeight.getType(), is(equalTo(int.class)));

        assertThat(RectangleWidth, hasModifiers(PRIVATE|PROTECTED));
        assertThat(RectangleHeight, hasModifiers(PRIVATE|PROTECTED));
    }

    private Object new_rectangle(int width, int height) throws Exception
    {
        Object rectangle = RectangleDefault.newInstance();
        RectangleWidth.set(rectangle, width); RectangleHeight.set(rectangle, height);
        return rectangle;
    }

    @Test
    public void test_default_constructor() throws Exception
    {
        assertThat(RectangleDefault, is(notNullValue()));
        assertThat(RectangleDefault, hasModifiers(PUBLIC));

        Object dut = RectangleDefault.newInstance();
        assertThat(dut, is(notNullValue()));

        int actual_width = (int) RectangleWidth.get(dut);
        int actual_height = (int) RectangleHeight.get(dut);

        assertThat(actual_width, is(equalTo(0)));
        assertThat(actual_height, is(equalTo(0)));
    }

    @Test
    public void test_full_constructor() throws Exception
    {
        assertThat(RectangleFull, is(notNullValue()));

        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0}, {1,1}, {3,4}};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i];
            Object dut = RectangleFull.newInstance(input[0], input[1]);
            assertThat(dut, is(notNullValue()));
            assertThat(RectangleWidth.get(dut), is(equalTo(input[0])));
            assertThat(RectangleHeight.get(dut), is(equalTo(input[1])));
        }
    }

    @Test
    public void test_accessor_signatures() throws Exception
    {
        assertThat(RectangleGetWidth, is(notNullValue()));
        assertThat(RectangleGetHeight, is(notNullValue()));

        assertThat(RectangleGetWidth.getReturnType(), is(equalTo(int.class)));
        assertThat(RectangleGetHeight.getReturnType(), is(equalTo(int.class)));

        assertThat(RectangleGetWidth, hasModifiers(PUBLIC));
        assertThat(RectangleGetHeight, hasModifiers(PUBLIC));
    }

    @Test
    public void test_mutator_signatures() throws Exception
    {
        assertThat(RectangleSetWidth, is(notNullValue()));
        assertThat(RectangleSetHeight, is(notNullValue()));

        assertThat(RectangleSetWidth.getReturnType(), is(equalTo(void.class)));
        assertThat(RectangleSetHeight.getReturnType(), is(equalTo(void.class)));

        assertThat(RectangleSetWidth, hasModifiers(PUBLIC));
        assertThat(RectangleSetHeight, hasModifiers(PUBLIC));
    }

    @Test
    public void test_get_area() throws Exception
    {
        assertThat(RectangleGetArea, is(notNullValue()));
        assertThat(RectangleGetArea.getReturnType(), is(equalTo(int.class)));
        assertThat(RectangleGetArea, hasModifiers(PUBLIC));

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
        assertThat(RectangleGetPerimeter, is(notNullValue()));
        assertThat(RectangleGetPerimeter.getReturnType(), is(equalTo(int.class)));
        assertThat(RectangleGetPerimeter, hasModifiers(PUBLIC));

        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0}, {1,1}, {3,4}};
        final int[] expected_output = {0, 4, 14};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i];
            int expected = expected_output[i];
            Object dut = new_rectangle(input[0], input[1]);
            int actual = (int) RectangleGetPerimeter.invoke(dut);
            assertThat(actual, is(equalTo(expected)));
        }
    }

    private String rectangle_to_string(Object object) throws Exception
    {
        int width = (int) RectangleWidth.get(object), height = (int) RectangleHeight.get(object);
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
    public void test_equals_signature() throws Exception
    {
        assertThat(RectangleEquals, is(notNullValue()));
        assertThat(RectangleEquals.getReturnType(), is(equalTo(boolean.class)));
        assertThat(RectangleEquals, hasModifiers(PUBLIC));
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

    @Test
    public void test_accessors() throws Exception
    {
        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0}, {1,1}, {3,4}};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i];
            Object dut = new_rectangle(input[0], input[1]);
            assertThat(RectangleGetWidth.invoke(dut), is(equalTo(input[0])));
            assertThat(RectangleGetHeight.invoke(dut), is(equalTo(input[1])));
        }
    }

    @Test
    public void test_mutators() throws Exception
    {
        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0}, {1,1}, {3,4}};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i];
            Object dut = new_rectangle(-1, -1);
            RectangleSetWidth.invoke(dut, input[0]);
            RectangleSetHeight.invoke(dut, input[1]);
            assertThat(RectangleWidth.get(dut), is(equalTo(input[0])));
            assertThat(RectangleHeight.get(dut), is(equalTo(input[1])));
        }
    }


    @Test
    public void test_implements_comparable() throws Exception
    {
        assertThat(RectangleCompareTo, is(notNullValue()));
        assertThat(RectangleClass, implementsGenericComparable());
    }

    @Test
    public void test_compare_to() throws Exception
    {
        final int number_of_test_cases = 3;
        final int[][] test_input = {{0,0, 0,0}, {1,2, 2,1}, {1,5, 3,4}, {2,3, 4,3}, {3,4, 1,1}, {5,1, 2,2}};
        final int[] test_output = {0, 0, -1, -1, 1, 1};

        for(int i=0; i<number_of_test_cases; i++)
        {
            int[] input = test_input[i]; int expected = test_output[i];
            Object rectangle1 = new_rectangle(input[0], input[1]);
            Object rectangle2 = new_rectangle(input[2], input[3]);
            int actual = (int) RectangleCompareTo.invoke(rectangle1, rectangle2);
            assertThat(Integer.compare(actual, 0), is(equalTo(expected)));
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