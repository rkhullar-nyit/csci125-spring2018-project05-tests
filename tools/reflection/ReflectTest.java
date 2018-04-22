package tools.reflection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;

import tools.mockio.MockIOTest;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;

public abstract class ReflectTest extends MockIOTest
{
    protected Reflector reflector;
    protected ReflectTestAdapter reflectAdapter;

    @Before
    public void setup()
    {
        reflectAdapter = buildReflectAdapter();
        reflectAdapter.init();
        reflectAdapter.load();
        reflectAdapter.link();
        reflector.learn();
        reflector.inspect();
    }

    @After
    public void tearDown()
    { reflector.restore(); }

    protected abstract ReflectTestAdapter buildReflectAdapter();

    public final byte PRIVATE = Modifier.PRIVATE, PROTECTED = Modifier.PROTECTED, PUBLIC = Modifier.PUBLIC;

    public boolean member_has_modifiers(Member member, final int flags)
    {
        if(member == null) return false;
        byte modifiers = (byte) member.getModifiers();
        return flags > 0 ? (~(modifiers ^ flags) & modifiers) > 0 : modifiers == 0;
    }

    public Matcher<Member> hasModifiers(final int flags)
    {
        return new BaseMatcher<Member>()
        {
            public boolean matches(final Object item)
            {
                final Member member = (Member) item;
                return member_has_modifiers(member, flags);
            }

            public void describeTo(Description description)
            {
                description.appendText("member modifiers should include ").appendValue(Modifier.toString(flags));
            }
        };
    }

}
