package rublitio.uskaddon.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class SetKeepXPOnDeath extends Effect {
	private Expression<Boolean> keepxp;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		keepxp = (Expression<Boolean>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		
		return null;
	}

	@Override
	protected void execute(Event e) {
		if(e instanceof PlayerDeathEvent)
			((PlayerDeathEvent) e).setKeepLevel(this.keepxp.getSingle(e));
	}

}
