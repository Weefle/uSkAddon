package rublitio.uskaddon.conditions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import ch.njol.skript.lang.Condition;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class StringStartsWith extends Condition{
	private Expression<String> string;
	private Expression<String> stringstartswith;
	private boolean b;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int i, Kleenean arg2, ParseResult arg3) {
		string = (Expression<String>) exprs[0];
		stringstartswith = (Expression<String>) exprs[1];
		this.b = i == 0;
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "string starts with another string";
	}

	@Override
	public boolean check(Event e) {
		if (b)
			return string.getSingle(e).startsWith(stringstartswith.getSingle(e));
		
		return !string.getSingle(e).startsWith(stringstartswith.getSingle(e));
	}

}
