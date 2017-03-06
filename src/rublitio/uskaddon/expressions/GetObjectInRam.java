package rublitio.uskaddon.expressions;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;
import rublitio.uskaddon.Main;

public class GetObjectInRam extends SimpleExpression<Object> {
	private Expression<String> name;
	@Override
	public Class<Object> getReturnType() {
		return Object.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		this.name = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "get an object previously saved in ram";
	}

	@Override
	@Nullable
	protected Object[] get(Event e) {
		if(Main.objects.containsKey(this.name.getSingle(e)))
			return new Object[] {Main.objects.get(this.name.getSingle(e))};
		return null;
	}
	
}
