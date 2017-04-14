package rublitio.uskaddon.effects;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.event.Event;
import org.bukkit.event.world.ChunkLoadEvent;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class LoadChunk extends Effect {
	private Expression<Chunk> chunk;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		this.chunk = (Expression<Chunk>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "load chunk";
	}

	@Override
	protected void execute(Event e) {
		final ChunkLoadEvent event = new ChunkLoadEvent(this.chunk.getSingle(e), false);
		Bukkit.getPluginManager().callEvent(event);
		this.chunk.getSingle(e).load(false);
	}

}
