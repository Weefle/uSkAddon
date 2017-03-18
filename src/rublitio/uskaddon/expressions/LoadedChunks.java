package rublitio.uskaddon.expressions;

import java.util.ArrayList;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.Chunk;
import org.bukkit.World;
import org.bukkit.event.Event;

import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.skript.lang.util.SimpleExpression;
import ch.njol.util.Kleenean;

public class LoadedChunks extends SimpleExpression<Chunk> {
	private Expression<World> world;
	private boolean b;
	@Override
	public Class<? extends Chunk> getReturnType() {
		return Chunk.class;
	}

	@Override
	public boolean isSingle() {
		return true;
	}

	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] args, int i, Kleenean arg2, ParseResult arg3) {
		this.b = i == 1;
		if(this.b)
			this.world = (Expression<World>) args[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "all chunks";
	}

	@Override
	@Nullable
	protected Chunk[] get(Event e) {
		if(this.b)
			return this.world.getSingle(e).getLoadedChunks();
		final ArrayList<Chunk> chunks = new ArrayList<Chunk>();
		for(final World w : Bukkit.getServer().getWorlds())
			for(final Chunk c : w.getLoadedChunks())
				chunks.add(c);
		return (Chunk[]) chunks.toArray();
	}



}
