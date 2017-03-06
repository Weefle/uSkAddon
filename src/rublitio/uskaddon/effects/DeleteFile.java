package rublitio.uskaddon.effects;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.event.Event;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class DeleteFile extends Effect {
	private Expression<String> fname;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		this.fname = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return "delete file";
	}

	@Override
	protected void execute(Event e) {
		final File f = new File(this.fname.getSingle(e));
		if(f.exists())
			f.delete();
		
	}

}
