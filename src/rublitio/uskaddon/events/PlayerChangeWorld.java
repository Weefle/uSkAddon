package rublitio.uskaddon.events;

import javax.annotation.Nullable;

import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import ch.njol.skript.lang.Literal;
import ch.njol.skript.lang.SkriptEvent;
import ch.njol.skript.lang.SkriptParser.ParseResult;

public class PlayerChangeWorld extends SkriptEvent
{

	@Override
	public String toString(@Nullable Event e, boolean arg1) {
		return "PlayerChangedWorldEvent";
	}

	@Override
	public boolean check(Event e) {
		return e instanceof PlayerChangedWorldEvent;
	}

	@Override
	public boolean init(Literal<?>[] lit, int i, ParseResult parse) {
		return true;
	}

}