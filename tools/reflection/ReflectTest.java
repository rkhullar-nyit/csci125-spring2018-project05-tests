package tools.reflection;

import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;
import org.junit.After;
import org.junit.Before;

import tools.mockio.MockIOTest;

import java.lang.reflect.Member;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

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

            public void describeTo(final Description description)
            {
                description.appendText("member modifiers should be ");
                if (flags > 0)
                    description.appendValue(Modifier.toString(flags).split(" "));
                else
                    description.appendValue("default");
            }

            public void describeMismatch(final Object item, final Description description)
            {
                final Member member = (Member) item;
                int modifiers = member.getModifiers();
                String values = modifiers > 0 ? Modifier.toString(modifiers): "default";
                description.appendText("was ").appendValue(values);
            }
        };
    }

    public static String join_types(Type...types)
    {
        StringBuilder stringBuilder = new StringBuilder();
        for(Type type: types)
            stringBuilder.append(type.getTypeName()).append(',');
        stringBuilder.deleteCharAt(stringBuilder.length()-1);
        return stringBuilder.toString();
    }

    private static Type build_generic_comparable_for(Class clazz)
    {
        final Class ComparableInterface = Comparable.class;
        return new ParameterizedType()
        {
            public Type[] getActualTypeArguments()
            { return new Type[] {clazz}; }

            public Type getRawType()
            { return ComparableInterface; }

            public Type getOwnerType()
            { return null; }

            public String toString()
            {
                return String.format("%s<%s>", getRawType(), join_types(getActualTypeArguments()));
            }
        };
    }

    public static boolean class_implements_generic_comparable(Class clazz)
    {
        Set<Type> genericInterfaces = new HashSet<>();
        Collections.addAll(genericInterfaces, clazz.getGenericInterfaces());
        Type target_type = build_generic_comparable_for(clazz);

        for(Type type: genericInterfaces)
            if(type.equals(target_type))
                return true;
        return false;

        // TODO: implement equals and/or hash in order to optimize above query
        //return genericInterfaces.contains(target_type);
        //return clazz.isInstance(target_type);
    }

    public Matcher<Class> implementsGenericComparable()
    {
        return new BaseMatcher<Class>()
        {
            private Class clazz; private Type target_type;

            public boolean matches(final Object item)
            {
                final Class clazz = (Class) item; this.clazz = clazz;
                return class_implements_generic_comparable(clazz);
            }

            public void describeTo(final Description description)
            {
                target_type = build_generic_comparable_for(clazz);
                description.appendText("should implement " ).appendValue(target_type);
            }

            public void describeMismatch(final Object item, final Description description)
            {
                description.appendText("did not implement ").appendValue(target_type);
            }
        };
    }

}
