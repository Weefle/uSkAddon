package rublitio.uskaddon.events;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerChangedMainHandEvent;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class PlayerChangeMainHand extends SkriptEvent {

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return null;
	}

	@Override
	public boolean check(Event e) {
		return (e instanceof PlayerChangedMainHandEvent);
	}

	@Override
	public boolean init(Literal<?>[] arg0, int arg1, ParseResult arg2) {
		// TODO Auto-generated method stub
		return true;
	}

}
