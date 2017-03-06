package rublitio.uskaddon.events;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class PlayerSwapHandItem extends SkriptEvent {

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return null;
	}

	@Override
	public boolean check(Event e) {
		return (e instanceof PlayerSwapHandItemsEvent);
	}

	@Override
	public boolean init(Literal<?>[] lit, int arg1, ParseResult arg2) {
		return true;
	}

}
