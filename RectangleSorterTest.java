import org.junit.Test;

import java.lang.reflect.Array;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.Assert.*;

public class RectangleSorterTest extends RectangleSorterTestBase
{

    @Test
    public void test_canary()
    {
        assertThat(1, is(equalTo(1)));
    }

    @Test
    public void test_rectangle_exists()
    {
        assertThat(RectangleClass, is(notNullValue()));
    }

    @Test
    public void test_rectangle_sorter_exists()
    {
        assertThat(RectangleSorterClass, is(notNullValue()));
    }

    @Test
    public void test_rectangle_sorter_constructor() throws Exception
    {
        assertThat(RectangleSorterFull, is(notNullValue()));
        assertThat(RectangleSorterFull, hasModifiers(PUBLIC));

        Object array = new_rectangle_array(0);
        Object dut = RectangleSorterFull.newInstance(array);
        assertThat(dut, is(notNullValue()));
        assertThat(RectangleSorterArray.get(dut), is(notNullValue()));
    }

    @Test
    public void test_rectangle_sorter_members()
    {
        assertThat(RectangleSorterArray, is(notNullValue()));
        assertThat(RectangleSorterArray.getType(), is(equalTo(RectangleArrayClass)));
        assertThat(RectangleSorterArray, hasModifiers(PRIVATE|PROTECTED));
    }

    @Test
    public void test_rectangle_sorter_method_signatures()
    {
        assertThat(RectangleSorterSort, is(notNullValue()));
        assertThat(RectangleSorterSort.getReturnType(), is(equalTo(void.class)));
        assertThat(RectangleSorterSort, hasModifiers(PUBLIC));

        assertThat(RectangleSorterMin, is(notNullValue()));
        assertThat(RectangleSorterMin.getReturnType(), is(equalTo(int.class)));
        assertThat(RectangleSorterMin, hasModifiers(PRIVATE|PROTECTED));

        assertThat(RectangleSorterSwap, is(notNullValue()));
        assertThat(RectangleSorterSwap.getReturnType(), is(equalTo(void.class)));
        assertThat(RectangleSorterSwap, hasModifiers(PRIVATE|PROTECTED));
    }

    private Object new_rectangle(int width, int height) throws Exception
    {
        Object rectangle = RectangleClass.newInstance();
        RectangleWidth.set(rectangle, width);
        RectangleHeight.set(rectangle, height);
        return rectangle;
    }

    private Object new_rectangle_array(int size)
    {
        return Array.newInstance(RectangleClass, size);
    }

    private Object new_rectangle_array(int[][] dimension_array) throws Exception
    {
        Object array = new_rectangle_array(dimension_array.length);
        for(int i=0; i<dimension_array.length; i++)
        {
            int[] dimensions = dimension_array[i];
            Object rectangle = new_rectangle(dimensions[0], dimensions[1]);
            Array.set(array, i, rectangle);
        }
        return array;
    }

    private Object new_rectangle_sorter(Object array) throws Exception
    {
        Object sorter = RectangleSorterFull.newInstance(array);
        RectangleSorterArray.set(sorter, array);
        return sorter;
    }

    @Test
    public void test_swap() throws Exception
    {
        int[][] dimension_array = {{0,0}, {1,1}, {2,2}};
        Object array = new_rectangle_array(dimension_array);

        int i=0, j=2;
        Object A = Array.get(array, i);
        Object B = Array.get(array, j);

        Object dut = new_rectangle_sorter(array);
        RectangleSorterSwap.invoke(dut, i, j);

        assertThat(Array.get(array, i), is(equalTo(B)));
        assertThat(Array.get(array, j), is(equalTo(A)));
    }



}
