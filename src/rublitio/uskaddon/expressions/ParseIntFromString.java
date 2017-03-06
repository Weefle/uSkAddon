package rublitio.uskaddon.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class ParseIntFromString extends SimpleExpression<Integer> {
	private Expression<String> s;
	@Override
	public Class<Integer> getReturnType() {
		return Integer.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		this.s = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "parse integer";
	}

	@Override
	@Nullable
	protected Integer[] get(Event e) {
		try {
			return new Integer[]{Integer.parseInt(this.s.getSingle(e))};
		}
		catch (IllegalArgumentException ex) {
			return null;
		}
	}

}
