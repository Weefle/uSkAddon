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

public class CreateWorld extends Effect
{
  private Expression<String> worldname;

  @SuppressWarnings("unchecked")
public boolean init(Expression<?>[] args, int arg1, Kleenean arg2, ParseResult arg3)
  {
    this.worldname = (Expression<String>) args[0];
    return true;
  }

  public String toString(@Nullable Event arg0, boolean arg1)
  {
    return "create new world";
  }

  protected void execute(Event e)
  {
	if (this.worldname.getSingle(e) == null || Bukkit.getWorlds().contains(Bukkit.getWorld(this.worldname.getSingle(e))) || new File(Bukkit.getServer().getWorldContainer().getAbsolutePath()+File.separator+this.worldname.getSingle(e)).exists())
		return;
	new WorldCreator(this.worldname.getSingle(e)).createWorld();
  }
}