package rublitio.uskaddon.events;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerVelocityEvent;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class PlayerVelocity extends SkriptEvent {

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	public boolean check(Event e) {
		return (e instanceof PlayerVelocityEvent);
	}

	@Override
	public boolean init(Literal<?>[] arg0, int arg1, ParseResult arg2) {
		return true;
	}

}
