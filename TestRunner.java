import org.junit.runner.JUnitCore;
import org.junit.runner.Result;
import org.junit.runner.notification.Failure;

import java.io.PrintStream;
import java.util.HashMap;

class TestRunner
{

    private static final int max = 50, min = 2;
    private static final HashMap<String, Integer> penalties = new HashMap<>();

    static
    {
        penalties.put("test_rectangle_fields", 1);
        penalties.put("test_rectangle_accessor_signatures", 1);
        penalties.put("test_rectangle_mutator_signature", 1);
        penalties.put("test_rectangle_accessors", 1);
        penalties.put("test_rectangle_mutators", 1);
        penalties.put("test_rectangle_default_constructor", 1);
        penalties.put("test_rectangle_full_constructor", 1);
        penalties.put("test_rectangle_get_area", 2);
        penalties.put("test_rectangle_get_perimeter", 2);
        penalties.put("test_rectangle_equals_signature", 1);
        penalties.put("test_rectangle_equals", 1);
        penalties.put("test_rectangle_not_equals", 1);
        penalties.put("test_rectangle_compare_to", 3);
        penalties.put("test_rectangle_to_string", 2);
        penalties.put("test_rectangle_implements_comparable", 5);
        penalties.put("test_rectangle_draw", 5);

        penalties.put("test_rectangle_sorter_exists", 0);
        penalties.put("test_rectangle_sorter_constructor", 2);
        penalties.put("test_rectangle_sorter_members", 2);
        penalties.put("test_rectangle_sorter_method_signatures", 2);
        penalties.put("test_rectangle_sorter_swap", 5);
        penalties.put("test_rectangle_sorter_min", 10);
        penalties.put("test_rectangle_sorter_sort", 10);
    }

    public static void main(String[] args)
    {
        final PrintStream std_out = System.out;

        Result rectangle_result = JUnitCore.runClasses(RectangleTest.class);
        Result rectangle_sorter_result = JUnitCore.runClasses(RectangleSorterTest.class);
        Result[] results = new Result[]{rectangle_result, rectangle_sorter_result};

        int grade = max;

        for(Result result: results)
        {
            for (Failure failure : result.getFailures())
            {
                String name = failure.getTestHeader().split("[(]")[0];
                System.err.printf("failed test: %s\n", name);
                if(penalties.containsKey(name))
                    grade -= penalties.get(name);
            }
        }

        if(grade < min)
            grade = min;

        System.setOut(std_out);
        System.out.println(grade);
    }
}
