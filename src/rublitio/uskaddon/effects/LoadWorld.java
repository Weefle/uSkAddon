package rublitio.uskaddon.effects;

import java.io.File;

import javax.annotation.Nullable;

import org.bukkit.Bukkit;
import org.bukkit.WorldCreator;
import org.bukkit.event.Event;
import ch.njol.skript.lang.Effect;
import ch.njol.skript.lang.Expression;
import ch.njol.skript.lang.SkriptParser.ParseResult;
import ch.njol.util.Kleenean;

public class LoadWorld extends Effect {
	private Expression<String> world;
	@SuppressWarnings("unchecked")
	@Override
	public boolean init(Expression<?>[] exprs, int arg1, Kleenean arg2, ParseResult arg3) {
		this.world = (Expression<String>) exprs[0];
		return true;
	}

	@Override
	public String toString(@Nullable Event arg0, boolean arg1) {
		return null;
	}

	@Override
	protected void execute(Event e) {
		if(new File(Bukkit.getServer().getWorldContainer().getAbsolutePath()+File.separator+this.world.getSingle(e)).exists() && !Bukkit.getServer().getWorlds().contains(Bukkit.getWorld(world.getSingle(e)))){
			new WorldCreator(world.getSingle(e)).createWorld();
		}
		
	}

}
