package rublitio.uskaddon.effects;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.event.Event;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;

import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class LoadPlugin extends Effect {
	private Expression<String> plugin;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		this.plugin = (Expression<String>) exprs[0];
		return false;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		
		return null;
	}

	@Override
	protected void execute(Event e) {
		try {
			Bukkit.getServer().getPluginManager().loadPlugin(new File("plugins/"+plugin.getSingle(e)+".jar"));
		} catch (UnknownDependencyException | InvalidPluginException | InvalidDescriptionException e1) {
			e1.printStackTrace();
		}
	}

}
