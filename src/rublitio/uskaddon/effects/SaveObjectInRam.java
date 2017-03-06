package rublitio.uskaddon.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;
import rublitio.uskaddon.Main;

public class SaveObjectInRam extends Effect {
	private Expression<Object> object;
	private Expression<String> name;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		this.object = (Expression<Object>) exprs[0];
		this.name = (Expression<String>) exprs[1];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "save object in ram";
	}

	@Override
	protected void execute(Event e) {
		Main.objects.put(this.name.getSingle(e), this.object.getSingle(e));
		
	}

}
