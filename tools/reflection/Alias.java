package tools.reflection;

import java.util.Objects;

public class Alias
{
    protected final String real, nick;

    public Alias(String real, String nick)
    { this.real = real; this.nick = nick; }

    public String toString()
    { return String.format("{type: %s, real: %s, nick: %s}", "Alias", real, nick); }

    @Override
    public boolean equals(Object other)
    {
        if (this == other) return true;
        if (other == null || getClass() != other.getClass()) return false;
        Alias alias = (Alias) other;
        return Objects.equals(real, alias.real) &&
                Objects.equals(nick, alias.nick);
    }

    @Override
    public int hashCode()
    { return Objects.hash(real, nick); }

    public static Alias build(String real, String nick)
    { return new Alias(real, nick); }

    public static Alias build(String real)
    { return build(real, "default"); }

    public static Alias from_real(String real)
    { return build(real, null); }

    public static Alias from_nick(String nick)
    { return build(null, nick); }

    public static final Alias fake = Alias.from_nick("default");
    public static final Alias none = Alias.build(null, null);

}
