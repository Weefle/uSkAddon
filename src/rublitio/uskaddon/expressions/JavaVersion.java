package rublitio.uskaddon.expressions;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import org.bukkit.event.Event;

public class JavaVersion extends SimpleExpression<String> {

    protected String[] get(Event event) {
        return new String[] { getVersion() };
    }

    public boolean isSingle() {
        return true;
    }

    public Class<? extends String> getReturnType() { return String.class; }

    public String toString(Event event, boolean b) {
        return this.getClass().getName();
    }

    public boolean init(Expression<?>[] expressions, int i, Kleenean kleenean, SkriptParser.ParseResult parseResult) {
        return true;
    }

    private static String getVersion(){
        String version = System.getProperty("java.version");
        int pos = 0, count = 0;
        for ( ; pos<version.length() && count < 2; pos ++) if (version.charAt(pos) == '.') count ++;
        return version.substring (0, pos);
    }
}


	

