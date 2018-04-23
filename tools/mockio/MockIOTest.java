package tools.mockio;

import org.junit.After;
import org.junit.Before;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

public class MockIOTest
{

    protected static final String line_ending = System.lineSeparator();
    private PrintStream std_out;
    private InputStream std_in;
    private ByteArrayOutputStream mock_out;
    private ByteArrayInputStream mock_in;

    public static String build_output(String... array)
    {
        StringBuilder builder = new StringBuilder();
        for(String line: array)
        {
            builder.append(line);
            builder.append(line_ending);
        }
        return builder.toString().trim();
    }

    public void set_mock_input(String... array)
    {
        byte[] buffer = build_output(array).getBytes();
        mock_in = new ByteArrayInputStream(buffer);
        System.setIn(mock_in);
    }

    public String get_mock_output()
    {
        return mock_out.toString().trim();
    }

    protected void mock_io_setup()
    {
        std_out = System.out;
        std_in = System.in;
        mock_out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(mock_out));
    }

    protected void mock_io_tear_down()
    {
        System.setOut(std_out);
        System.setIn(std_in);
    }


    @Before
    public void setUp()
    { mock_io_setup(); }

    @After
    public void tearDown()
    { mock_io_tear_down(); }

}
