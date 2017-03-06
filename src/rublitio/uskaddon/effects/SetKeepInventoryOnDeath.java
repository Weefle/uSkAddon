package rublitio.uskaddon.effects;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.entity.PlayerDeathEvent;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class SetKeepInventoryOnDeath extends Effect {
	private Expression<Boolean> keepinv;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		this.keepinv = (Expression<Boolean>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return null;
	}

	@Override
	protected void execute(Event e) {
		if(e instanceof PlayerDeathEvent)
			((PlayerDeathEvent) e).setKeepInventory(this.keepinv.getSingle(e));
	}

}
